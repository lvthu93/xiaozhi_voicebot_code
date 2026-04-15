package org.eclipse.paho.client.mqttv3.internal;

import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsSender implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsSender";
    private ClientComms clientComms;
    private ClientState clientState;
    private State current_state;
    private final Object lifecycle;
    private Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private MqttOutputStream out;
    private Thread sendThread;
    private Future<?> senderFuture;
    private State target_state;
    private String threadName;
    private CommsTokenStore tokenStore;

    public enum State {
        STOPPED,
        RUNNING,
        STARTING
    }

    public CommsSender(ClientComms clientComms2, ClientState clientState2, CommsTokenStore commsTokenStore, OutputStream outputStream) {
        State state = State.STOPPED;
        this.current_state = state;
        this.target_state = state;
        this.lifecycle = new Object();
        this.sendThread = null;
        this.clientState = null;
        this.clientComms = null;
        this.tokenStore = null;
        this.out = new MqttOutputStream(clientState2, outputStream);
        this.clientComms = clientComms2;
        this.clientState = clientState2;
        this.tokenStore = commsTokenStore;
        this.log.setResourceName(clientComms2.getClient().getClientId());
    }

    private void handleRunException(MqttWireMessage mqttWireMessage, Exception exc) {
        MqttException mqttException;
        this.log.fine(CLASS_NAME, "handleRunException", "804", (Object[]) null, exc);
        if (!(exc instanceof MqttException)) {
            mqttException = new MqttException(32109, exc);
        } else {
            mqttException = (MqttException) exc;
        }
        synchronized (this.lifecycle) {
            this.target_state = State.STOPPED;
        }
        this.clientComms.shutdownConnection((MqttToken) null, mqttException);
    }

    public boolean isRunning() {
        boolean z;
        synchronized (this.lifecycle) {
            State state = this.current_state;
            State state2 = State.RUNNING;
            if (state == state2 && this.target_state == state2) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
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
            r9.sendThread = r0
            java.lang.String r1 = r9.threadName
            r0.setName(r1)
            java.lang.Object r0 = r9.lifecycle
            monitor-enter(r0)
            org.eclipse.paho.client.mqttv3.internal.CommsSender$State r1 = org.eclipse.paho.client.mqttv3.internal.CommsSender.State.RUNNING     // Catch:{ all -> 0x00d5 }
            r9.current_state = r1     // Catch:{ all -> 0x00d5 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d5 }
            r0 = 0
            java.lang.Object r1 = r9.lifecycle     // Catch:{ all -> 0x00c6 }
            monitor-enter(r1)     // Catch:{ all -> 0x00c6 }
            org.eclipse.paho.client.mqttv3.internal.CommsSender$State r2 = r9.target_state     // Catch:{ all -> 0x00c3 }
            monitor-exit(r1)     // Catch:{ all -> 0x00c3 }
            r1 = r0
        L_0x001b:
            org.eclipse.paho.client.mqttv3.internal.CommsSender$State r3 = org.eclipse.paho.client.mqttv3.internal.CommsSender.State.RUNNING     // Catch:{ all -> 0x00c6 }
            if (r2 != r3) goto L_0x00aa
            org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream r2 = r9.out     // Catch:{ all -> 0x00c6 }
            if (r2 != 0) goto L_0x0025
            goto L_0x00aa
        L_0x0025:
            org.eclipse.paho.client.mqttv3.internal.ClientState r2 = r9.clientState     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r1 = r2.get()     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            if (r1 == 0) goto L_0x007e
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r9.log     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            java.lang.String r3 = CLASS_NAME     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            java.lang.String r4 = "run"
            java.lang.String r5 = "802"
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            java.lang.String r7 = r1.getKey()     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            r8 = 0
            r6[r8] = r7     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            r7 = 1
            r6[r7] = r1     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            r2.fine(r3, r4, r5, r6)     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            boolean r2 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttAck     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            if (r2 == 0) goto L_0x0054
            org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream r2 = r9.out     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            r2.write((org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r1)     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream r2 = r9.out     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            r2.flush()     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            goto L_0x009e
        L_0x0054:
            org.eclipse.paho.client.mqttv3.MqttToken r2 = r1.getToken()     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            if (r2 != 0) goto L_0x0060
            org.eclipse.paho.client.mqttv3.internal.CommsTokenStore r2 = r9.tokenStore     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            org.eclipse.paho.client.mqttv3.MqttToken r2 = r2.getToken((org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r1)     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
        L_0x0060:
            if (r2 == 0) goto L_0x009e
            monitor-enter(r2)     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream r3 = r9.out     // Catch:{ all -> 0x007b }
            r3.write((org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r1)     // Catch:{ all -> 0x007b }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttOutputStream r3 = r9.out     // Catch:{ IOException -> 0x006e }
            r3.flush()     // Catch:{ IOException -> 0x006e }
            goto L_0x0073
        L_0x006e:
            r3 = move-exception
            boolean r4 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect     // Catch:{ all -> 0x007b }
            if (r4 == 0) goto L_0x007a
        L_0x0073:
            org.eclipse.paho.client.mqttv3.internal.ClientState r3 = r9.clientState     // Catch:{ all -> 0x007b }
            r3.notifySent(r1)     // Catch:{ all -> 0x007b }
            monitor-exit(r2)     // Catch:{ all -> 0x007b }
            goto L_0x009e
        L_0x007a:
            throw r3     // Catch:{ all -> 0x007b }
        L_0x007b:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x007b }
            throw r3     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
        L_0x007e:
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r9.log     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            java.lang.String r3 = CLASS_NAME     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            java.lang.String r4 = "run"
            java.lang.String r5 = "803"
            r2.fine(r3, r4, r5)     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            java.lang.Object r2 = r9.lifecycle     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            monitor-enter(r2)     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
            org.eclipse.paho.client.mqttv3.internal.CommsSender$State r3 = org.eclipse.paho.client.mqttv3.internal.CommsSender.State.STOPPED     // Catch:{ all -> 0x0092 }
            r9.target_state = r3     // Catch:{ all -> 0x0092 }
            monitor-exit(r2)     // Catch:{ all -> 0x0092 }
            goto L_0x009e
        L_0x0092:
            r3 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0092 }
            throw r3     // Catch:{ MqttException -> 0x009a, Exception -> 0x0095 }
        L_0x0095:
            r2 = move-exception
            r9.handleRunException(r1, r2)     // Catch:{ all -> 0x00c6 }
            goto L_0x009e
        L_0x009a:
            r2 = move-exception
            r9.handleRunException(r1, r2)     // Catch:{ all -> 0x00c6 }
        L_0x009e:
            java.lang.Object r2 = r9.lifecycle     // Catch:{ all -> 0x00c6 }
            monitor-enter(r2)     // Catch:{ all -> 0x00c6 }
            org.eclipse.paho.client.mqttv3.internal.CommsSender$State r3 = r9.target_state     // Catch:{ all -> 0x00a7 }
            monitor-exit(r2)     // Catch:{ all -> 0x00a7 }
            r2 = r3
            goto L_0x001b
        L_0x00a7:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00a7 }
            throw r1     // Catch:{ all -> 0x00c6 }
        L_0x00aa:
            java.lang.Object r1 = r9.lifecycle
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.internal.CommsSender$State r2 = org.eclipse.paho.client.mqttv3.internal.CommsSender.State.STOPPED     // Catch:{ all -> 0x00c0 }
            r9.current_state = r2     // Catch:{ all -> 0x00c0 }
            r9.sendThread = r0     // Catch:{ all -> 0x00c0 }
            monitor-exit(r1)     // Catch:{ all -> 0x00c0 }
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r9.log
            java.lang.String r1 = CLASS_NAME
            java.lang.String r2 = "run"
            java.lang.String r3 = "805"
            r0.fine(r1, r2, r3)
            return
        L_0x00c0:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00c0 }
            throw r0
        L_0x00c3:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00c3 }
            throw r2     // Catch:{ all -> 0x00c6 }
        L_0x00c6:
            r1 = move-exception
            java.lang.Object r2 = r9.lifecycle
            monitor-enter(r2)
            org.eclipse.paho.client.mqttv3.internal.CommsSender$State r3 = org.eclipse.paho.client.mqttv3.internal.CommsSender.State.STOPPED     // Catch:{ all -> 0x00d2 }
            r9.current_state = r3     // Catch:{ all -> 0x00d2 }
            r9.sendThread = r0     // Catch:{ all -> 0x00d2 }
            monitor-exit(r2)     // Catch:{ all -> 0x00d2 }
            throw r1
        L_0x00d2:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00d2 }
            throw r0
        L_0x00d5:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d5 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.CommsSender.run():void");
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        synchronized (this.lifecycle) {
            State state = this.current_state;
            State state2 = State.STOPPED;
            if (state == state2 && this.target_state == state2) {
                this.target_state = State.RUNNING;
                if (executorService == null) {
                    new Thread(this).start();
                } else {
                    this.senderFuture = executorService.submit(this);
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
        if (isRunning()) {
            synchronized (this.lifecycle) {
                Future<?> future = this.senderFuture;
                if (future != null) {
                    future.cancel(true);
                }
                this.log.fine(CLASS_NAME, "stop", "800");
                if (isRunning()) {
                    this.target_state = State.STOPPED;
                    this.clientState.notifyQueueLock();
                }
            }
            while (isRunning()) {
                try {
                    Thread.sleep(100);
                } catch (Exception unused) {
                }
                this.clientState.notifyQueueLock();
            }
            this.log.fine(CLASS_NAME, "stop", "801");
        }
    }
}
