package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsCallback implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsCallback";
    private static final int INBOUND_QUEUE_SIZE = 10;
    private Future<?> callbackFuture;
    private Thread callbackThread;
    private Hashtable<String, IMqttMessageListener> callbacks;
    private ClientComms clientComms;
    private ClientState clientState;
    private final Vector<MqttToken> completeQueue;
    private State current_state;
    private final Object lifecycle = new Object();
    private final Logger log;
    private boolean manualAcks = false;
    private final Vector<MqttWireMessage> messageQueue;
    private MqttCallback mqttCallback;
    private MqttCallbackExtended reconnectInternalCallback;
    private final Object spaceAvailable = new Object();
    private State target_state;
    private String threadName;
    private final Object workAvailable = new Object();

    public enum State {
        STOPPED,
        RUNNING,
        QUIESCING
    }

    public CommsCallback(ClientComms clientComms2) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
        this.log = logger;
        State state = State.STOPPED;
        this.current_state = state;
        this.target_state = state;
        this.clientComms = clientComms2;
        this.messageQueue = new Vector<>(10);
        this.completeQueue = new Vector<>(10);
        this.callbacks = new Hashtable<>();
        logger.setResourceName(clientComms2.getClient().getClientId());
    }

    private void handleActionComplete(MqttToken mqttToken) throws MqttException {
        synchronized (mqttToken) {
            this.log.fine(CLASS_NAME, "handleActionComplete", "705", new Object[]{mqttToken.internalTok.getKey()});
            if (mqttToken.isComplete()) {
                this.clientState.notifyComplete(mqttToken);
            }
            mqttToken.internalTok.notifyComplete();
            if (!mqttToken.internalTok.isNotified()) {
                if (this.mqttCallback != null && (mqttToken instanceof MqttDeliveryToken) && mqttToken.isComplete()) {
                    this.mqttCallback.deliveryComplete((MqttDeliveryToken) mqttToken);
                }
                fireActionEvent(mqttToken);
            }
            if (mqttToken.isComplete() && (mqttToken instanceof MqttDeliveryToken)) {
                mqttToken.internalTok.setNotified(true);
            }
        }
    }

    private void handleMessage(MqttPublish mqttPublish) throws MqttException, Exception {
        String topicName = mqttPublish.getTopicName();
        this.log.fine(CLASS_NAME, "handleMessage", "713", new Object[]{Integer.valueOf(mqttPublish.getMessageId()), topicName});
        deliverMessage(topicName, mqttPublish.getMessageId(), mqttPublish.getMessage());
        if (this.manualAcks) {
            return;
        }
        if (mqttPublish.getMessage().getQos() == 1) {
            this.clientComms.internalSend(new MqttPubAck(mqttPublish), new MqttToken(this.clientComms.getClient().getClientId()));
        } else if (mqttPublish.getMessage().getQos() == 2) {
            this.clientComms.deliveryComplete(mqttPublish);
            MqttPubComp mqttPubComp = new MqttPubComp(mqttPublish);
            ClientComms clientComms2 = this.clientComms;
            clientComms2.internalSend(mqttPubComp, new MqttToken(clientComms2.getClient().getClientId()));
        }
    }

    public void asyncOperationComplete(MqttToken mqttToken) {
        if (isRunning()) {
            this.completeQueue.addElement(mqttToken);
            synchronized (this.workAvailable) {
                this.log.fine(CLASS_NAME, "asyncOperationComplete", "715", new Object[]{mqttToken.internalTok.getKey()});
                this.workAvailable.notifyAll();
            }
            return;
        }
        try {
            handleActionComplete(mqttToken);
        } catch (Throwable th) {
            this.log.fine(CLASS_NAME, "asyncOperationComplete", "719", (Object[]) null, th);
            this.clientComms.shutdownConnection((MqttToken) null, new MqttException(th));
        }
    }

    public void connectionLost(MqttException mqttException) {
        try {
            if (!(this.mqttCallback == null || mqttException == null)) {
                this.log.fine(CLASS_NAME, "connectionLost", "708", new Object[]{mqttException});
                this.mqttCallback.connectionLost(mqttException);
            }
            MqttCallbackExtended mqttCallbackExtended = this.reconnectInternalCallback;
            if (mqttCallbackExtended != null && mqttException != null) {
                mqttCallbackExtended.connectionLost(mqttException);
            }
        } catch (Throwable th) {
            this.log.fine(CLASS_NAME, "connectionLost", "720", new Object[]{th});
        }
    }

    public boolean deliverMessage(String str, int i, MqttMessage mqttMessage) throws Exception {
        Enumeration<String> keys = this.callbacks.keys();
        boolean z = false;
        while (keys.hasMoreElements()) {
            String nextElement = keys.nextElement();
            IMqttMessageListener iMqttMessageListener = this.callbacks.get(nextElement);
            if (iMqttMessageListener != null && MqttTopic.isMatched(nextElement, str)) {
                mqttMessage.setId(i);
                iMqttMessageListener.messageArrived(str, mqttMessage);
                z = true;
            }
        }
        if (this.mqttCallback == null || z) {
            return z;
        }
        mqttMessage.setId(i);
        this.mqttCallback.messageArrived(str, mqttMessage);
        return true;
    }

    public void fireActionEvent(MqttToken mqttToken) {
        IMqttActionListener actionCallback;
        if (mqttToken != null && (actionCallback = mqttToken.getActionCallback()) != null) {
            if (mqttToken.getException() == null) {
                this.log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[]{mqttToken.internalTok.getKey()});
                actionCallback.onSuccess(mqttToken);
                return;
            }
            this.log.fine(CLASS_NAME, "fireActionEvent", "716", new Object[]{mqttToken.internalTok.getKey()});
            actionCallback.onFailure(mqttToken, mqttToken.getException());
        }
    }

    public Thread getThread() {
        return this.callbackThread;
    }

    public boolean isQuiesced() {
        return isQuiescing() && this.completeQueue.size() == 0 && this.messageQueue.size() == 0;
    }

    public boolean isQuiescing() {
        boolean z;
        synchronized (this.lifecycle) {
            if (this.current_state == State.QUIESCING) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.lifecycle) {
            State state = this.current_state;
            State state2 = State.RUNNING;
            if ((state == state2 || state == State.QUIESCING) && this.target_state == state2) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x000f */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x000f A[LOOP:0: B:6:0x000f->B:33:0x000f, LOOP_START, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void messageArrived(org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish r6) {
        /*
            r5 = this;
            org.eclipse.paho.client.mqttv3.MqttCallback r0 = r5.mqttCallback
            if (r0 != 0) goto L_0x000c
            java.util.Hashtable<java.lang.String, org.eclipse.paho.client.mqttv3.IMqttMessageListener> r0 = r5.callbacks
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x005d
        L_0x000c:
            java.lang.Object r0 = r5.spaceAvailable
            monitor-enter(r0)
        L_0x000f:
            boolean r1 = r5.isRunning()     // Catch:{ all -> 0x005e }
            if (r1 == 0) goto L_0x0039
            boolean r1 = r5.isQuiescing()     // Catch:{ all -> 0x005e }
            if (r1 != 0) goto L_0x0039
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage> r1 = r5.messageQueue     // Catch:{ all -> 0x005e }
            int r1 = r1.size()     // Catch:{ all -> 0x005e }
            r2 = 10
            if (r1 >= r2) goto L_0x0026
            goto L_0x0039
        L_0x0026:
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = r5.log     // Catch:{ InterruptedException -> 0x000f }
            java.lang.String r2 = CLASS_NAME     // Catch:{ InterruptedException -> 0x000f }
            java.lang.String r3 = "messageArrived"
            java.lang.String r4 = "709"
            r1.fine(r2, r3, r4)     // Catch:{ InterruptedException -> 0x000f }
            java.lang.Object r1 = r5.spaceAvailable     // Catch:{ InterruptedException -> 0x000f }
            r2 = 200(0xc8, double:9.9E-322)
            r1.wait(r2)     // Catch:{ InterruptedException -> 0x000f }
            goto L_0x000f
        L_0x0039:
            monitor-exit(r0)     // Catch:{ all -> 0x005e }
            boolean r0 = r5.isQuiescing()
            if (r0 != 0) goto L_0x005d
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage> r0 = r5.messageQueue
            r0.addElement(r6)
            java.lang.Object r6 = r5.workAvailable
            monitor-enter(r6)
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r5.log     // Catch:{ all -> 0x005a }
            java.lang.String r1 = CLASS_NAME     // Catch:{ all -> 0x005a }
            java.lang.String r2 = "messageArrived"
            java.lang.String r3 = "710"
            r0.fine(r1, r2, r3)     // Catch:{ all -> 0x005a }
            java.lang.Object r0 = r5.workAvailable     // Catch:{ all -> 0x005a }
            r0.notifyAll()     // Catch:{ all -> 0x005a }
            monitor-exit(r6)     // Catch:{ all -> 0x005a }
            goto L_0x005d
        L_0x005a:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x005a }
            throw r0
        L_0x005d:
            return
        L_0x005e:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005e }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.CommsCallback.messageArrived(org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish):void");
    }

    public void messageArrivedComplete(int i, int i2) throws MqttException {
        if (i2 == 1) {
            this.clientComms.internalSend(new MqttPubAck(i), new MqttToken(this.clientComms.getClient().getClientId()));
        } else if (i2 == 2) {
            this.clientComms.deliveryComplete(i);
            MqttPubComp mqttPubComp = new MqttPubComp(i);
            ClientComms clientComms2 = this.clientComms;
            clientComms2.internalSend(mqttPubComp, new MqttToken(clientComms2.getClient().getClientId()));
        }
    }

    public void quiesce() {
        synchronized (this.lifecycle) {
            if (this.current_state == State.RUNNING) {
                this.current_state = State.QUIESCING;
            }
        }
        synchronized (this.spaceAvailable) {
            this.log.fine(CLASS_NAME, "quiesce", "711");
            this.spaceAvailable.notifyAll();
        }
    }

    public void removeMessageListener(String str) {
        this.callbacks.remove(str);
    }

    public void removeMessageListeners() {
        this.callbacks.clear();
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
        	at java.base/java.util.Objects.checkIndex(Objects.java:361)
        	at java.base/java.util.ArrayList.get(ArrayList.java:427)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:225)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void run() {
        /*
            r9 = this;
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r9.callbackThread = r0
            java.lang.String r1 = r9.threadName
            r0.setName(r1)
            java.lang.Object r0 = r9.lifecycle
            monitor-enter(r0)
            org.eclipse.paho.client.mqttv3.internal.CommsCallback$State r1 = org.eclipse.paho.client.mqttv3.internal.CommsCallback.State.RUNNING     // Catch:{ all -> 0x0113 }
            r9.current_state = r1     // Catch:{ all -> 0x0113 }
            monitor-exit(r0)     // Catch:{ all -> 0x0113 }
        L_0x0013:
            boolean r0 = r9.isRunning()
            r1 = 0
            if (r0 != 0) goto L_0x0028
            java.lang.Object r0 = r9.lifecycle
            monitor-enter(r0)
            org.eclipse.paho.client.mqttv3.internal.CommsCallback$State r2 = org.eclipse.paho.client.mqttv3.internal.CommsCallback.State.STOPPED     // Catch:{ all -> 0x0025 }
            r9.current_state = r2     // Catch:{ all -> 0x0025 }
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            r9.callbackThread = r1
            return
        L_0x0025:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0025 }
            throw r1
        L_0x0028:
            java.lang.Object r0 = r9.workAvailable     // Catch:{ InterruptedException -> 0x0059 }
            monitor-enter(r0)     // Catch:{ InterruptedException -> 0x0059 }
            boolean r2 = r9.isRunning()     // Catch:{ all -> 0x0053 }
            if (r2 == 0) goto L_0x0051
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage> r2 = r9.messageQueue     // Catch:{ all -> 0x0053 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0053 }
            if (r2 == 0) goto L_0x0051
            java.util.Vector<org.eclipse.paho.client.mqttv3.MqttToken> r2 = r9.completeQueue     // Catch:{ all -> 0x0053 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x0053 }
            if (r2 == 0) goto L_0x0051
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r9.log     // Catch:{ all -> 0x0053 }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x0053 }
            java.lang.String r4 = "run"
            java.lang.String r5 = "704"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x0053 }
            java.lang.Object r2 = r9.workAvailable     // Catch:{ all -> 0x0053 }
            r2.wait()     // Catch:{ all -> 0x0053 }
        L_0x0051:
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            goto L_0x0059
        L_0x0053:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            throw r2     // Catch:{ InterruptedException -> 0x0059 }
        L_0x0056:
            r0 = move-exception
            goto L_0x00cb
        L_0x0059:
            boolean r0 = r9.isRunning()     // Catch:{ all -> 0x0056 }
            if (r0 == 0) goto L_0x00a7
            java.util.Vector<org.eclipse.paho.client.mqttv3.MqttToken> r0 = r9.completeQueue     // Catch:{ all -> 0x0056 }
            monitor-enter(r0)     // Catch:{ all -> 0x0056 }
            java.util.Vector<org.eclipse.paho.client.mqttv3.MqttToken> r2 = r9.completeQueue     // Catch:{ all -> 0x00a4 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x00a4 }
            r3 = 0
            if (r2 != 0) goto L_0x0079
            java.util.Vector<org.eclipse.paho.client.mqttv3.MqttToken> r2 = r9.completeQueue     // Catch:{ all -> 0x00a4 }
            java.lang.Object r2 = r2.elementAt(r3)     // Catch:{ all -> 0x00a4 }
            org.eclipse.paho.client.mqttv3.MqttToken r2 = (org.eclipse.paho.client.mqttv3.MqttToken) r2     // Catch:{ all -> 0x00a4 }
            java.util.Vector<org.eclipse.paho.client.mqttv3.MqttToken> r4 = r9.completeQueue     // Catch:{ all -> 0x00a4 }
            r4.removeElementAt(r3)     // Catch:{ all -> 0x00a4 }
            goto L_0x007a
        L_0x0079:
            r2 = r1
        L_0x007a:
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            if (r2 == 0) goto L_0x0080
            r9.handleActionComplete(r2)     // Catch:{ all -> 0x0056 }
        L_0x0080:
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage> r0 = r9.messageQueue     // Catch:{ all -> 0x0056 }
            monitor-enter(r0)     // Catch:{ all -> 0x0056 }
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage> r2 = r9.messageQueue     // Catch:{ all -> 0x00a1 }
            boolean r2 = r2.isEmpty()     // Catch:{ all -> 0x00a1 }
            if (r2 != 0) goto L_0x0099
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage> r2 = r9.messageQueue     // Catch:{ all -> 0x00a1 }
            java.lang.Object r2 = r2.elementAt(r3)     // Catch:{ all -> 0x00a1 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish r2 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish) r2     // Catch:{ all -> 0x00a1 }
            java.util.Vector<org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage> r4 = r9.messageQueue     // Catch:{ all -> 0x00a1 }
            r4.removeElementAt(r3)     // Catch:{ all -> 0x00a1 }
            goto L_0x009a
        L_0x0099:
            r2 = r1
        L_0x009a:
            monitor-exit(r0)     // Catch:{ all -> 0x00a1 }
            if (r2 == 0) goto L_0x00a7
            r9.handleMessage(r2)     // Catch:{ all -> 0x0056 }
            goto L_0x00a7
        L_0x00a1:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a1 }
            throw r2     // Catch:{ all -> 0x0056 }
        L_0x00a4:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a4 }
            throw r2     // Catch:{ all -> 0x0056 }
        L_0x00a7:
            boolean r0 = r9.isQuiescing()     // Catch:{ all -> 0x0056 }
            if (r0 == 0) goto L_0x00b2
            org.eclipse.paho.client.mqttv3.internal.ClientState r0 = r9.clientState     // Catch:{ all -> 0x0056 }
            r0.checkQuiesceLock()     // Catch:{ all -> 0x0056 }
        L_0x00b2:
            java.lang.Object r0 = r9.spaceAvailable
            monitor-enter(r0)
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = r9.log     // Catch:{ all -> 0x00c8 }
            java.lang.String r2 = CLASS_NAME     // Catch:{ all -> 0x00c8 }
            java.lang.String r3 = "run"
            java.lang.String r4 = "706"
            r1.fine(r2, r3, r4)     // Catch:{ all -> 0x00c8 }
            java.lang.Object r1 = r9.spaceAvailable     // Catch:{ all -> 0x00c8 }
            r1.notifyAll()     // Catch:{ all -> 0x00c8 }
            monitor-exit(r0)     // Catch:{ all -> 0x00c8 }
            goto L_0x0013
        L_0x00c8:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00c8 }
            throw r1
        L_0x00cb:
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r9.log     // Catch:{ all -> 0x00fa }
            java.lang.String r8 = CLASS_NAME     // Catch:{ all -> 0x00fa }
            java.lang.String r4 = "run"
            java.lang.String r5 = "714"
            r6 = 0
            r3 = r8
            r7 = r0
            r2.fine(r3, r4, r5, r6, r7)     // Catch:{ all -> 0x00fa }
            org.eclipse.paho.client.mqttv3.internal.ClientComms r2 = r9.clientComms     // Catch:{ all -> 0x00fa }
            org.eclipse.paho.client.mqttv3.MqttException r3 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x00fa }
            r3.<init>((java.lang.Throwable) r0)     // Catch:{ all -> 0x00fa }
            r2.shutdownConnection(r1, r3)     // Catch:{ all -> 0x00fa }
            java.lang.Object r0 = r9.spaceAvailable
            monitor-enter(r0)
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = r9.log     // Catch:{ all -> 0x00f7 }
            java.lang.String r2 = "run"
            java.lang.String r3 = "706"
            r1.fine(r8, r2, r3)     // Catch:{ all -> 0x00f7 }
            java.lang.Object r1 = r9.spaceAvailable     // Catch:{ all -> 0x00f7 }
            r1.notifyAll()     // Catch:{ all -> 0x00f7 }
            monitor-exit(r0)     // Catch:{ all -> 0x00f7 }
            goto L_0x0013
        L_0x00f7:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00f7 }
            throw r1
        L_0x00fa:
            r0 = move-exception
            java.lang.Object r1 = r9.spaceAvailable
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r9.log     // Catch:{ all -> 0x0110 }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x0110 }
            java.lang.String r4 = "run"
            java.lang.String r5 = "706"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x0110 }
            java.lang.Object r2 = r9.spaceAvailable     // Catch:{ all -> 0x0110 }
            r2.notifyAll()     // Catch:{ all -> 0x0110 }
            monitor-exit(r1)     // Catch:{ all -> 0x0110 }
            throw r0
        L_0x0110:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0110 }
            throw r0
        L_0x0113:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0113 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.CommsCallback.run():void");
    }

    public void setCallback(MqttCallback mqttCallback2) {
        this.mqttCallback = mqttCallback2;
    }

    public void setClientState(ClientState clientState2) {
        this.clientState = clientState2;
    }

    public void setManualAcks(boolean z) {
        this.manualAcks = z;
    }

    public void setMessageListener(String str, IMqttMessageListener iMqttMessageListener) {
        this.callbacks.put(str, iMqttMessageListener);
    }

    public void setReconnectCallback(MqttCallbackExtended mqttCallbackExtended) {
        this.reconnectInternalCallback = mqttCallbackExtended;
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        synchronized (this.lifecycle) {
            if (this.current_state == State.STOPPED) {
                this.messageQueue.clear();
                this.completeQueue.clear();
                this.target_state = State.RUNNING;
                if (executorService == null) {
                    new Thread(this).start();
                } else {
                    this.callbackFuture = executorService.submit(this);
                }
            }
        }
        while (!isRunning()) {
            try {
                Thread.sleep(100);
            } catch (Exception unused) {
            }
        }
    }

    public void stop() {
        synchronized (this.lifecycle) {
            Future<?> future = this.callbackFuture;
            if (future != null) {
                future.cancel(true);
            }
        }
        if (isRunning()) {
            Logger logger = this.log;
            String str = CLASS_NAME;
            logger.fine(str, "stop", "700");
            synchronized (this.lifecycle) {
                this.target_state = State.STOPPED;
            }
            if (!Thread.currentThread().equals(this.callbackThread)) {
                synchronized (this.workAvailable) {
                    this.log.fine(str, "stop", "701");
                    this.workAvailable.notifyAll();
                }
                while (isRunning()) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception unused) {
                    }
                    this.clientState.notifyQueueLock();
                }
            }
            this.log.fine(CLASS_NAME, "stop", "703");
        }
    }
}
