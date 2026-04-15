package org.eclipse.paho.client.mqttv3.internal;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class CommsReceiver implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.CommsReceiver";
    private ClientComms clientComms;
    private ClientState clientState;
    private State current_state;
    private MqttInputStream in;
    private final Object lifecycle;
    private Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private Thread recThread;
    private Future<?> receiverFuture;
    private State target_state;
    private String threadName;
    private CommsTokenStore tokenStore;

    public enum State {
        STOPPED,
        RUNNING,
        STARTING,
        RECEIVING
    }

    public CommsReceiver(ClientComms clientComms2, ClientState clientState2, CommsTokenStore commsTokenStore, InputStream inputStream) {
        State state = State.STOPPED;
        this.current_state = state;
        this.target_state = state;
        this.lifecycle = new Object();
        this.clientState = null;
        this.clientComms = null;
        this.tokenStore = null;
        this.recThread = null;
        this.in = new MqttInputStream(clientState2, inputStream);
        this.clientComms = clientComms2;
        this.clientState = clientState2;
        this.tokenStore = commsTokenStore;
        this.log.setResourceName(clientComms2.getClient().getClientId());
    }

    public boolean isReceiving() {
        boolean z;
        synchronized (this.lifecycle) {
            if (this.current_state == State.RECEIVING) {
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
            if ((state == state2 || state == State.RECEIVING) && this.target_state == state2) {
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeEndlessLoop(RegionMaker.java:368)
        	at jadx.core.dex.visitors.regions.RegionMaker.processLoop(RegionMaker.java:172)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public void run() {
        /*
            r9 = this;
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r9.recThread = r0
            java.lang.String r1 = r9.threadName
            r0.setName(r1)
            java.lang.Object r0 = r9.lifecycle
            monitor-enter(r0)
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r1 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.RUNNING     // Catch:{ all -> 0x015d }
            r9.current_state = r1     // Catch:{ all -> 0x015d }
            monitor-exit(r0)     // Catch:{ all -> 0x015d }
            java.lang.Object r0 = r9.lifecycle     // Catch:{ all -> 0x0150 }
            monitor-enter(r0)     // Catch:{ all -> 0x0150 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r1 = r9.target_state     // Catch:{ all -> 0x014d }
            monitor-exit(r0)     // Catch:{ all -> 0x014d }
            r0 = 0
            r2 = r0
        L_0x001b:
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r3 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.RUNNING     // Catch:{ all -> 0x0150 }
            if (r1 != r3) goto L_0x0134
            org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream r1 = r9.in     // Catch:{ all -> 0x0150 }
            if (r1 != 0) goto L_0x0025
            goto L_0x0134
        L_0x0025:
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = r9.log     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            java.lang.String r4 = CLASS_NAME     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "852"
            r1.fine(r4, r5, r6)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream r1 = r9.in     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            int r1 = r1.available()     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r1 <= 0) goto L_0x0044
            java.lang.Object r1 = r9.lifecycle     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            monitor-enter(r1)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r5 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.RECEIVING     // Catch:{ all -> 0x0041 }
            r9.current_state = r5     // Catch:{ all -> 0x0041 }
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
            goto L_0x0044
        L_0x0041:
            r3 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0041 }
            throw r3     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
        L_0x0044:
            org.eclipse.paho.client.mqttv3.internal.wire.MqttInputStream r1 = r9.in     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r1 = r1.readMqttWireMessage()     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            java.lang.Object r5 = r9.lifecycle     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            monitor-enter(r5)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            r9.current_state = r3     // Catch:{ all -> 0x00b2 }
            monitor-exit(r5)     // Catch:{ all -> 0x00b2 }
            boolean r5 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttAck     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r5 == 0) goto L_0x0087
            org.eclipse.paho.client.mqttv3.internal.CommsTokenStore r5 = r9.tokenStore     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            org.eclipse.paho.client.mqttv3.MqttToken r2 = r5.getToken((org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r1)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r2 == 0) goto L_0x0069
            monitor-enter(r2)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            org.eclipse.paho.client.mqttv3.internal.ClientState r4 = r9.clientState     // Catch:{ all -> 0x0066 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttAck r1 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttAck) r1     // Catch:{ all -> 0x0066 }
            r4.notifyReceivedAck(r1)     // Catch:{ all -> 0x0066 }
            monitor-exit(r2)     // Catch:{ all -> 0x0066 }
            goto L_0x00a8
        L_0x0066:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0066 }
            throw r1     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
        L_0x0069:
            boolean r5 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r5 != 0) goto L_0x007d
            boolean r5 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r5 != 0) goto L_0x007d
            boolean r1 = r1 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r1 == 0) goto L_0x0076
            goto L_0x007d
        L_0x0076:
            org.eclipse.paho.client.mqttv3.MqttException r1 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            r3 = 6
            r1.<init>((int) r3)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            throw r1     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
        L_0x007d:
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = r9.log     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "857"
            r1.fine(r4, r5, r6)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            goto L_0x00a8
        L_0x0087:
            if (r1 == 0) goto L_0x008f
            org.eclipse.paho.client.mqttv3.internal.ClientState r4 = r9.clientState     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            r4.notifyReceivedMsg(r1)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            goto L_0x00a8
        L_0x008f:
            org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = r9.clientComms     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            boolean r1 = r1.isConnected()     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r1 != 0) goto L_0x00a8
            org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = r9.clientComms     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            boolean r1 = r1.isConnecting()     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            if (r1 == 0) goto L_0x00a0
            goto L_0x00a8
        L_0x00a0:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            java.lang.String r3 = "Connection is lost."
            r1.<init>(r3)     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
            throw r1     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
        L_0x00a8:
            java.lang.Object r1 = r9.lifecycle     // Catch:{ all -> 0x0150 }
            monitor-enter(r1)     // Catch:{ all -> 0x0150 }
            r9.current_state = r3     // Catch:{ all -> 0x00af }
            monitor-exit(r1)     // Catch:{ all -> 0x00af }
            goto L_0x0116
        L_0x00af:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00af }
            throw r0     // Catch:{ all -> 0x0150 }
        L_0x00b2:
            r1 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00b2 }
            throw r1     // Catch:{ MqttException -> 0x00f3, IOException -> 0x00b7 }
        L_0x00b5:
            r0 = move-exception
            goto L_0x0128
        L_0x00b7:
            r1 = move-exception
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = r9.log     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = CLASS_NAME     // Catch:{ all -> 0x00b5 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "853"
            r3.fine(r4, r5, r6)     // Catch:{ all -> 0x00b5 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r3 = r9.target_state     // Catch:{ all -> 0x00b5 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r4 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.STOPPED     // Catch:{ all -> 0x00b5 }
            if (r3 == r4) goto L_0x00e7
            java.lang.Object r3 = r9.lifecycle     // Catch:{ all -> 0x00b5 }
            monitor-enter(r3)     // Catch:{ all -> 0x00b5 }
            r9.target_state = r4     // Catch:{ all -> 0x00e4 }
            monitor-exit(r3)     // Catch:{ all -> 0x00e4 }
            org.eclipse.paho.client.mqttv3.internal.ClientComms r3 = r9.clientComms     // Catch:{ all -> 0x00b5 }
            boolean r3 = r3.isDisconnecting()     // Catch:{ all -> 0x00b5 }
            if (r3 != 0) goto L_0x00e7
            org.eclipse.paho.client.mqttv3.internal.ClientComms r3 = r9.clientComms     // Catch:{ all -> 0x00b5 }
            org.eclipse.paho.client.mqttv3.MqttException r4 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x00b5 }
            r5 = 32109(0x7d6d, float:4.4994E-41)
            r4.<init>(r5, r1)     // Catch:{ all -> 0x00b5 }
            r3.shutdownConnection(r2, r4)     // Catch:{ all -> 0x00b5 }
            goto L_0x00e7
        L_0x00e4:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00e4 }
            throw r0     // Catch:{ all -> 0x00b5 }
        L_0x00e7:
            java.lang.Object r1 = r9.lifecycle     // Catch:{ all -> 0x0150 }
            monitor-enter(r1)     // Catch:{ all -> 0x0150 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r3 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.RUNNING     // Catch:{ all -> 0x00f0 }
            r9.current_state = r3     // Catch:{ all -> 0x00f0 }
            monitor-exit(r1)     // Catch:{ all -> 0x00f0 }
            goto L_0x0116
        L_0x00f0:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00f0 }
            throw r0     // Catch:{ all -> 0x0150 }
        L_0x00f3:
            r1 = move-exception
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = r9.log     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = CLASS_NAME     // Catch:{ all -> 0x00b5 }
            java.lang.String r5 = "run"
            java.lang.String r6 = "856"
            r7 = 0
            r8 = r1
            r3.fine(r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00b5 }
            java.lang.Object r3 = r9.lifecycle     // Catch:{ all -> 0x00b5 }
            monitor-enter(r3)     // Catch:{ all -> 0x00b5 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r4 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.STOPPED     // Catch:{ all -> 0x0125 }
            r9.target_state = r4     // Catch:{ all -> 0x0125 }
            monitor-exit(r3)     // Catch:{ all -> 0x0125 }
            org.eclipse.paho.client.mqttv3.internal.ClientComms r3 = r9.clientComms     // Catch:{ all -> 0x00b5 }
            r3.shutdownConnection(r2, r1)     // Catch:{ all -> 0x00b5 }
            java.lang.Object r1 = r9.lifecycle     // Catch:{ all -> 0x0150 }
            monitor-enter(r1)     // Catch:{ all -> 0x0150 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r3 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.RUNNING     // Catch:{ all -> 0x0122 }
            r9.current_state = r3     // Catch:{ all -> 0x0122 }
            monitor-exit(r1)     // Catch:{ all -> 0x0122 }
        L_0x0116:
            java.lang.Object r1 = r9.lifecycle     // Catch:{ all -> 0x0150 }
            monitor-enter(r1)     // Catch:{ all -> 0x0150 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r3 = r9.target_state     // Catch:{ all -> 0x011f }
            monitor-exit(r1)     // Catch:{ all -> 0x011f }
            r1 = r3
            goto L_0x001b
        L_0x011f:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x011f }
            throw r0     // Catch:{ all -> 0x0150 }
        L_0x0122:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0122 }
            throw r0     // Catch:{ all -> 0x0150 }
        L_0x0125:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0125 }
            throw r0     // Catch:{ all -> 0x00b5 }
        L_0x0128:
            java.lang.Object r1 = r9.lifecycle     // Catch:{ all -> 0x0150 }
            monitor-enter(r1)     // Catch:{ all -> 0x0150 }
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r2 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.RUNNING     // Catch:{ all -> 0x0131 }
            r9.current_state = r2     // Catch:{ all -> 0x0131 }
            monitor-exit(r1)     // Catch:{ all -> 0x0131 }
            throw r0     // Catch:{ all -> 0x0150 }
        L_0x0131:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0131 }
            throw r0     // Catch:{ all -> 0x0150 }
        L_0x0134:
            java.lang.Object r1 = r9.lifecycle
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r2 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.STOPPED     // Catch:{ all -> 0x014a }
            r9.current_state = r2     // Catch:{ all -> 0x014a }
            monitor-exit(r1)     // Catch:{ all -> 0x014a }
            r9.recThread = r0
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r9.log
            java.lang.String r1 = CLASS_NAME
            java.lang.String r2 = "run"
            java.lang.String r3 = "854"
            r0.fine(r1, r2, r3)
            return
        L_0x014a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x014a }
            throw r0
        L_0x014d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x014d }
            throw r1     // Catch:{ all -> 0x0150 }
        L_0x0150:
            r0 = move-exception
            java.lang.Object r1 = r9.lifecycle
            monitor-enter(r1)
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver$State r2 = org.eclipse.paho.client.mqttv3.internal.CommsReceiver.State.STOPPED     // Catch:{ all -> 0x015a }
            r9.current_state = r2     // Catch:{ all -> 0x015a }
            monitor-exit(r1)     // Catch:{ all -> 0x015a }
            throw r0
        L_0x015a:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x015a }
            throw r0
        L_0x015d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x015d }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.CommsReceiver.run():void");
    }

    public void start(String str, ExecutorService executorService) {
        this.threadName = str;
        this.log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            State state = this.current_state;
            State state2 = State.STOPPED;
            if (state == state2 && this.target_state == state2) {
                this.target_state = State.RUNNING;
                if (executorService == null) {
                    new Thread(this).start();
                } else {
                    this.receiverFuture = executorService.submit(this);
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
            Future<?> future = this.receiverFuture;
            if (future != null) {
                future.cancel(true);
            }
            this.log.fine(CLASS_NAME, "stop", "850");
            if (isRunning()) {
                this.target_state = State.STOPPED;
            }
        }
        while (isRunning()) {
            try {
                Thread.sleep(100);
            } catch (Exception unused) {
            }
        }
        this.log.fine(CLASS_NAME, "stop", "851");
    }
}
