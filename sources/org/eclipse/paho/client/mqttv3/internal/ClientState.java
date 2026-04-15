package org.eclipse.paho.client.mqttv3.internal;

import java.io.EOFException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingReq;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingResp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRel;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ClientState {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.ClientState";
    private static final int MAX_MSG_ID = 65535;
    private static final int MIN_MSG_ID = 1;
    private static final String PERSISTENCE_CONFIRMED_PREFIX = "sc-";
    private static final String PERSISTENCE_RECEIVED_PREFIX = "r-";
    private static final String PERSISTENCE_SENT_BUFFERED_PREFIX = "sb-";
    private static final String PERSISTENCE_SENT_PREFIX = "s-";
    private int actualInFlight = 0;
    private CommsCallback callback = null;
    private boolean cleanSession;
    private ClientComms clientComms = null;
    private boolean connected = false;
    private HighResolutionTimer highResolutionTimer;
    private int inFlightPubRels = 0;
    private Hashtable inUseMsgIds;
    private Hashtable inboundQoS2 = null;
    private long keepAliveNanos;
    private long lastInboundActivity = 0;
    private long lastOutboundActivity = 0;
    private long lastPing = 0;
    private Logger log;
    private int maxInflight = 0;
    private int nextMsgId = 0;
    private Hashtable outboundQoS0 = null;
    private Hashtable outboundQoS1 = null;
    private Hashtable outboundQoS2 = null;
    private volatile Vector pendingFlows;
    private volatile Vector pendingMessages;
    private MqttClientPersistence persistence;
    private MqttWireMessage pingCommand;
    private int pingOutstanding = 0;
    private final Object pingOutstandingLock = new Object();
    private MqttPingSender pingSender = null;
    private final Object queueLock = new Object();
    private final Object quiesceLock = new Object();
    private boolean quiescing = false;
    private CommsTokenStore tokenStore;

    public ClientState(MqttClientPersistence mqttClientPersistence, CommsTokenStore commsTokenStore, CommsCallback commsCallback, ClientComms clientComms2, MqttPingSender mqttPingSender, HighResolutionTimer highResolutionTimer2) throws MqttException {
        String str = CLASS_NAME;
        Logger logger = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, str);
        this.log = logger;
        logger.setResourceName(clientComms2.getClient().getClientId());
        this.log.finer(str, "<Init>", "");
        this.inUseMsgIds = new Hashtable();
        this.pendingFlows = new Vector();
        this.outboundQoS2 = new Hashtable();
        this.outboundQoS1 = new Hashtable();
        this.outboundQoS0 = new Hashtable();
        this.inboundQoS2 = new Hashtable();
        this.pingCommand = new MqttPingReq();
        this.inFlightPubRels = 0;
        this.actualInFlight = 0;
        this.persistence = mqttClientPersistence;
        this.callback = commsCallback;
        this.tokenStore = commsTokenStore;
        this.clientComms = clientComms2;
        this.pingSender = mqttPingSender;
        this.highResolutionTimer = highResolutionTimer2;
        restoreState();
    }

    private void decrementInFlight() {
        synchronized (this.queueLock) {
            int i = this.actualInFlight - 1;
            this.actualInFlight = i;
            this.log.fine(CLASS_NAME, "decrementInFlight", "646", new Object[]{Integer.valueOf(i)});
            if (!checkQuiesceLock()) {
                this.queueLock.notifyAll();
            }
        }
    }

    private synchronized int getNextMessageId() throws MqttException {
        int i;
        int i2 = this.nextMsgId;
        int i3 = 0;
        do {
            int i4 = this.nextMsgId + 1;
            this.nextMsgId = i4;
            if (i4 > 65535) {
                this.nextMsgId = 1;
            }
            i = this.nextMsgId;
            if (i == i2) {
                i3++;
                if (i3 == 2) {
                    throw ExceptionHelper.createMqttException(32001);
                }
            }
        } while (this.inUseMsgIds.containsKey(Integer.valueOf(i)));
        Integer valueOf = Integer.valueOf(this.nextMsgId);
        this.inUseMsgIds.put(valueOf, valueOf);
        return this.nextMsgId;
    }

    private String getReceivedPersistenceKey(int i) {
        return y2.f(PERSISTENCE_RECEIVED_PREFIX, i);
    }

    private String getSendBufferedPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_SENT_BUFFERED_PREFIX + mqttWireMessage.getMessageId();
    }

    private String getSendConfirmPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_CONFIRMED_PREFIX + mqttWireMessage.getMessageId();
    }

    private String getSendPersistenceKey(int i) {
        return y2.f(PERSISTENCE_SENT_PREFIX, i);
    }

    private void insertInOrder(Vector vector, MqttWireMessage mqttWireMessage) {
        int messageId = mqttWireMessage.getMessageId();
        for (int i = 0; i < vector.size(); i++) {
            if (((MqttWireMessage) vector.elementAt(i)).getMessageId() > messageId) {
                vector.insertElementAt(mqttWireMessage, i);
                return;
            }
        }
        vector.addElement(mqttWireMessage);
    }

    private Vector reOrder(Vector vector) {
        int i;
        Vector vector2 = new Vector();
        if (vector.size() == 0) {
            return vector2;
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i2 < vector.size()) {
            int messageId = ((MqttWireMessage) vector.elementAt(i2)).getMessageId();
            int i6 = messageId - i3;
            if (i6 > i4) {
                i5 = i2;
                i4 = i6;
            }
            i2++;
            i3 = messageId;
        }
        if ((65535 - i3) + ((MqttWireMessage) vector.elementAt(0)).getMessageId() > i4) {
            i = 0;
        } else {
            i = i5;
        }
        for (int i7 = i; i7 < vector.size(); i7++) {
            vector2.addElement(vector.elementAt(i7));
        }
        for (int i8 = 0; i8 < i; i8++) {
            vector2.addElement(vector.elementAt(i8));
        }
        return vector2;
    }

    private synchronized void releaseMessageId(int i) {
        this.inUseMsgIds.remove(Integer.valueOf(i));
    }

    private void restoreInflightMessages() {
        this.pendingMessages = new Vector(this.maxInflight);
        this.pendingFlows = new Vector();
        Enumeration keys = this.outboundQoS2.keys();
        while (keys.hasMoreElements()) {
            Object nextElement = keys.nextElement();
            MqttWireMessage mqttWireMessage = (MqttWireMessage) this.outboundQoS2.get(nextElement);
            if (mqttWireMessage instanceof MqttPublish) {
                this.log.fine(CLASS_NAME, "restoreInflightMessages", "610", new Object[]{nextElement});
                mqttWireMessage.setDuplicate(true);
                insertInOrder(this.pendingMessages, (MqttPublish) mqttWireMessage);
            } else if (mqttWireMessage instanceof MqttPubRel) {
                this.log.fine(CLASS_NAME, "restoreInflightMessages", "611", new Object[]{nextElement});
                insertInOrder(this.pendingFlows, (MqttPubRel) mqttWireMessage);
            }
        }
        Enumeration keys2 = this.outboundQoS1.keys();
        while (keys2.hasMoreElements()) {
            Object nextElement2 = keys2.nextElement();
            MqttPublish mqttPublish = (MqttPublish) this.outboundQoS1.get(nextElement2);
            mqttPublish.setDuplicate(true);
            this.log.fine(CLASS_NAME, "restoreInflightMessages", "612", new Object[]{nextElement2});
            insertInOrder(this.pendingMessages, mqttPublish);
        }
        Enumeration keys3 = this.outboundQoS0.keys();
        while (keys3.hasMoreElements()) {
            Object nextElement3 = keys3.nextElement();
            this.log.fine(CLASS_NAME, "restoreInflightMessages", "512", new Object[]{nextElement3});
            insertInOrder(this.pendingMessages, (MqttPublish) this.outboundQoS0.get(nextElement3));
        }
        this.pendingFlows = reOrder(this.pendingFlows);
        this.pendingMessages = reOrder(this.pendingMessages);
    }

    private MqttWireMessage restoreMessage(String str, MqttPersistable mqttPersistable) throws MqttException {
        MqttWireMessage mqttWireMessage;
        try {
            mqttWireMessage = MqttWireMessage.createWireMessage(mqttPersistable);
        } catch (MqttException e) {
            this.log.fine(CLASS_NAME, "restoreMessage", "602", new Object[]{str}, e);
            if (e.getCause() instanceof EOFException) {
                if (str != null) {
                    this.persistence.remove(str);
                }
                mqttWireMessage = null;
            } else {
                throw e;
            }
        }
        this.log.fine(CLASS_NAME, "restoreMessage", "601", new Object[]{str, mqttWireMessage});
        return mqttWireMessage;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
        if (r1.keepAliveNanos <= 0) goto L_0x017a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0030, code lost:
        r7 = r1.highResolutionTimer.nanoTime();
        r4 = r1.pingOutstandingLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0038, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r9 = r1.pingOutstanding;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
        if (r9 <= 0) goto L_0x0089;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
        r5 = r1.keepAliveNanos;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x004b, code lost:
        if ((r7 - r1.lastInboundActivity) >= (((long) 100000) + r5)) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x004e, code lost:
        r1.log.severe(r3, "checkForActivity", "619", new java.lang.Object[]{java.lang.Long.valueOf(r5), java.lang.Long.valueOf(r1.lastOutboundActivity), java.lang.Long.valueOf(r1.lastInboundActivity), java.lang.Long.valueOf(r7), java.lang.Long.valueOf(r1.lastPing)});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0088, code lost:
        throw org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException(32000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0089, code lost:
        if (r9 != 0) goto L_0x00d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008b, code lost:
        r10 = r1.keepAliveNanos;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0097, code lost:
        if ((r7 - r1.lastOutboundActivity) >= (2 * r10)) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x009a, code lost:
        r1.log.severe(r3, "checkForActivity", "642", new java.lang.Object[]{java.lang.Long.valueOf(r10), java.lang.Long.valueOf(r1.lastOutboundActivity), java.lang.Long.valueOf(r1.lastInboundActivity), java.lang.Long.valueOf(r7), java.lang.Long.valueOf(r1.lastPing)});
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00d4, code lost:
        throw org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException(32002);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00d5, code lost:
        if (r9 != 0) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00e1, code lost:
        if ((r7 - r1.lastInboundActivity) >= (r1.keepAliveNanos - ((long) 100000))) goto L_0x00ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00ed, code lost:
        if ((r7 - r1.lastOutboundActivity) < (r1.keepAliveNanos - ((long) 100000))) goto L_0x0140;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ef, code lost:
        r1.log.fine(r3, "checkForActivity", "620", new java.lang.Object[]{java.lang.Long.valueOf(r1.keepAliveNanos), java.lang.Long.valueOf(r1.lastOutboundActivity), java.lang.Long.valueOf(r1.lastInboundActivity)});
        r2 = new org.eclipse.paho.client.mqttv3.MqttToken(r1.clientComms.getClient().getClientId());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0124, code lost:
        if (r0 == null) goto L_0x0129;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0126, code lost:
        r2.setActionCallback(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0129, code lost:
        r1.tokenStore.saveToken(r2, r1.pingCommand);
        r1.pendingFlows.insertElementAt(r1.pingCommand, 0);
        r5 = getKeepAlive();
        notifyQueueLock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0140, code lost:
        r1.log.fine(r3, "checkForActivity", "634", (java.lang.Object[]) null);
        r5 = java.lang.Math.max(1, getKeepAlive() - r2.toMillis(r7 - r1.lastOutboundActivity));
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x015d, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x015e, code lost:
        r1.log.fine(r3, "checkForActivity", "624", new java.lang.Object[]{java.lang.Long.valueOf(r5)});
        r1.pingSender.schedule(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        r2 = java.util.concurrent.TimeUnit.NANOSECONDS;
        r2.toMillis(r1.keepAliveNanos);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0026, code lost:
        if (r1.connected == false) goto L_0x017a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.paho.client.mqttv3.MqttToken checkForActivity(org.eclipse.paho.client.mqttv3.IMqttActionListener r18) throws org.eclipse.paho.client.mqttv3.MqttException {
        /*
            r17 = this;
            r1 = r17
            r0 = r18
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r1.log
            java.lang.String r3 = CLASS_NAME
            java.lang.String r4 = "checkForActivity"
            java.lang.String r5 = "616"
            r6 = 0
            java.lang.Object[] r7 = new java.lang.Object[r6]
            r2.fine(r3, r4, r5, r7)
            java.lang.Object r2 = r1.quiesceLock
            monitor-enter(r2)
            boolean r4 = r1.quiescing     // Catch:{ all -> 0x017d }
            r5 = 0
            if (r4 == 0) goto L_0x001c
            monitor-exit(r2)     // Catch:{ all -> 0x017d }
            return r5
        L_0x001c:
            monitor-exit(r2)     // Catch:{ all -> 0x017d }
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.NANOSECONDS
            long r7 = r1.keepAliveNanos
            r2.toMillis(r7)
            boolean r4 = r1.connected
            if (r4 == 0) goto L_0x017a
            long r7 = r1.keepAliveNanos
            r9 = 0
            int r4 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r4 <= 0) goto L_0x017a
            org.eclipse.paho.client.mqttv3.internal.HighResolutionTimer r4 = r1.highResolutionTimer
            long r7 = r4.nanoTime()
            java.lang.Object r4 = r1.pingOutstandingLock
            monitor-enter(r4)
            int r9 = r1.pingOutstanding     // Catch:{ all -> 0x0177 }
            r14 = 1
            r15 = 100000(0x186a0, float:1.4013E-40)
            if (r9 <= 0) goto L_0x0089
            long r12 = r1.lastInboundActivity     // Catch:{ all -> 0x0177 }
            long r12 = r7 - r12
            long r5 = r1.keepAliveNanos     // Catch:{ all -> 0x0177 }
            long r10 = (long) r15     // Catch:{ all -> 0x0177 }
            long r10 = r10 + r5
            int r16 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r16 >= 0) goto L_0x004e
            goto L_0x0089
        L_0x004e:
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r1.log     // Catch:{ all -> 0x0177 }
            java.lang.String r2 = "checkForActivity"
            java.lang.String r9 = "619"
            r10 = 5
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ all -> 0x0177 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0177 }
            r6 = 0
            r10[r6] = r5     // Catch:{ all -> 0x0177 }
            long r5 = r1.lastOutboundActivity     // Catch:{ all -> 0x0177 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0177 }
            r10[r14] = r5     // Catch:{ all -> 0x0177 }
            long r5 = r1.lastInboundActivity     // Catch:{ all -> 0x0177 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0177 }
            r6 = 2
            r10[r6] = r5     // Catch:{ all -> 0x0177 }
            java.lang.Long r5 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0177 }
            r6 = 3
            r10[r6] = r5     // Catch:{ all -> 0x0177 }
            long r5 = r1.lastPing     // Catch:{ all -> 0x0177 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ all -> 0x0177 }
            r6 = 4
            r10[r6] = r5     // Catch:{ all -> 0x0177 }
            r0.severe(r3, r2, r9, r10)     // Catch:{ all -> 0x0177 }
            r0 = 32000(0x7d00, float:4.4842E-41)
            org.eclipse.paho.client.mqttv3.MqttException r0 = org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException((int) r0)     // Catch:{ all -> 0x0177 }
            throw r0     // Catch:{ all -> 0x0177 }
        L_0x0089:
            if (r9 != 0) goto L_0x00d5
            long r5 = r1.lastOutboundActivity     // Catch:{ all -> 0x0177 }
            long r5 = r7 - r5
            long r10 = r1.keepAliveNanos     // Catch:{ all -> 0x0177 }
            r12 = 2
            long r12 = r12 * r10
            int r16 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r16 >= 0) goto L_0x009a
            goto L_0x00d5
        L_0x009a:
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r1.log     // Catch:{ all -> 0x0177 }
            java.lang.String r2 = "checkForActivity"
            java.lang.String r5 = "642"
            r6 = 5
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0177 }
            java.lang.Long r9 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x0177 }
            r10 = 0
            r6[r10] = r9     // Catch:{ all -> 0x0177 }
            long r9 = r1.lastOutboundActivity     // Catch:{ all -> 0x0177 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x0177 }
            r6[r14] = r9     // Catch:{ all -> 0x0177 }
            long r9 = r1.lastInboundActivity     // Catch:{ all -> 0x0177 }
            java.lang.Long r9 = java.lang.Long.valueOf(r9)     // Catch:{ all -> 0x0177 }
            r10 = 2
            r6[r10] = r9     // Catch:{ all -> 0x0177 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0177 }
            r8 = 3
            r6[r8] = r7     // Catch:{ all -> 0x0177 }
            long r7 = r1.lastPing     // Catch:{ all -> 0x0177 }
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x0177 }
            r8 = 4
            r6[r8] = r7     // Catch:{ all -> 0x0177 }
            r0.severe(r3, r2, r5, r6)     // Catch:{ all -> 0x0177 }
            r0 = 32002(0x7d02, float:4.4844E-41)
            org.eclipse.paho.client.mqttv3.MqttException r0 = org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException((int) r0)     // Catch:{ all -> 0x0177 }
            throw r0     // Catch:{ all -> 0x0177 }
        L_0x00d5:
            if (r9 != 0) goto L_0x00e3
            long r5 = r1.lastInboundActivity     // Catch:{ all -> 0x0177 }
            long r5 = r7 - r5
            long r9 = r1.keepAliveNanos     // Catch:{ all -> 0x0177 }
            long r11 = (long) r15     // Catch:{ all -> 0x0177 }
            long r9 = r9 - r11
            int r11 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r11 >= 0) goto L_0x00ef
        L_0x00e3:
            long r5 = r1.lastOutboundActivity     // Catch:{ all -> 0x0177 }
            long r5 = r7 - r5
            long r9 = r1.keepAliveNanos     // Catch:{ all -> 0x0177 }
            long r11 = (long) r15     // Catch:{ all -> 0x0177 }
            long r9 = r9 - r11
            int r11 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r11 < 0) goto L_0x0140
        L_0x00ef:
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r1.log     // Catch:{ all -> 0x0177 }
            java.lang.String r5 = "checkForActivity"
            java.lang.String r6 = "620"
            r7 = 3
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ all -> 0x0177 }
            long r8 = r1.keepAliveNanos     // Catch:{ all -> 0x0177 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0177 }
            r9 = 0
            r7[r9] = r8     // Catch:{ all -> 0x0177 }
            long r8 = r1.lastOutboundActivity     // Catch:{ all -> 0x0177 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0177 }
            r7[r14] = r8     // Catch:{ all -> 0x0177 }
            long r8 = r1.lastInboundActivity     // Catch:{ all -> 0x0177 }
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x0177 }
            r9 = 2
            r7[r9] = r8     // Catch:{ all -> 0x0177 }
            r2.fine(r3, r5, r6, r7)     // Catch:{ all -> 0x0177 }
            org.eclipse.paho.client.mqttv3.MqttToken r2 = new org.eclipse.paho.client.mqttv3.MqttToken     // Catch:{ all -> 0x0177 }
            org.eclipse.paho.client.mqttv3.internal.ClientComms r5 = r1.clientComms     // Catch:{ all -> 0x0177 }
            org.eclipse.paho.client.mqttv3.IMqttAsyncClient r5 = r5.getClient()     // Catch:{ all -> 0x0177 }
            java.lang.String r5 = r5.getClientId()     // Catch:{ all -> 0x0177 }
            r2.<init>(r5)     // Catch:{ all -> 0x0177 }
            if (r0 == 0) goto L_0x0129
            r2.setActionCallback(r0)     // Catch:{ all -> 0x0177 }
        L_0x0129:
            org.eclipse.paho.client.mqttv3.internal.CommsTokenStore r0 = r1.tokenStore     // Catch:{ all -> 0x0177 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r5 = r1.pingCommand     // Catch:{ all -> 0x0177 }
            r0.saveToken((org.eclipse.paho.client.mqttv3.MqttToken) r2, (org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r5)     // Catch:{ all -> 0x0177 }
            java.util.Vector r0 = r1.pendingFlows     // Catch:{ all -> 0x0177 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r5 = r1.pingCommand     // Catch:{ all -> 0x0177 }
            r6 = 0
            r0.insertElementAt(r5, r6)     // Catch:{ all -> 0x0177 }
            long r5 = r17.getKeepAlive()     // Catch:{ all -> 0x0177 }
            r17.notifyQueueLock()     // Catch:{ all -> 0x0177 }
            goto L_0x015d
        L_0x0140:
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r1.log     // Catch:{ all -> 0x0177 }
            java.lang.String r5 = "checkForActivity"
            java.lang.String r6 = "634"
            r9 = 0
            r0.fine(r3, r5, r6, r9)     // Catch:{ all -> 0x0177 }
            long r5 = r1.lastOutboundActivity     // Catch:{ all -> 0x0177 }
            long r7 = r7 - r5
            long r5 = r2.toMillis(r7)     // Catch:{ all -> 0x0177 }
            long r7 = r17.getKeepAlive()     // Catch:{ all -> 0x0177 }
            long r7 = r7 - r5
            r5 = 1
            long r5 = java.lang.Math.max(r5, r7)     // Catch:{ all -> 0x0177 }
            r2 = r9
        L_0x015d:
            monitor-exit(r4)     // Catch:{ all -> 0x0177 }
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r1.log
            java.lang.String r4 = "checkForActivity"
            java.lang.String r7 = "624"
            java.lang.Object[] r8 = new java.lang.Object[r14]
            java.lang.Long r9 = java.lang.Long.valueOf(r5)
            r10 = 0
            r8[r10] = r9
            r0.fine(r3, r4, r7, r8)
            org.eclipse.paho.client.mqttv3.MqttPingSender r0 = r1.pingSender
            r0.schedule(r5)
            r5 = r2
            goto L_0x017c
        L_0x0177:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0177 }
            throw r0
        L_0x017a:
            r9 = r5
            r5 = r9
        L_0x017c:
            return r5
        L_0x017d:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x017d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientState.checkForActivity(org.eclipse.paho.client.mqttv3.IMqttActionListener):org.eclipse.paho.client.mqttv3.MqttToken");
    }

    public boolean checkQuiesceLock() {
        int count = this.tokenStore.count();
        if (!this.quiescing || count != 0 || this.pendingFlows.size() != 0 || !this.callback.isQuiesced()) {
            return false;
        }
        this.log.fine(CLASS_NAME, "checkQuiesceLock", "626", new Object[]{Boolean.valueOf(this.quiescing), Integer.valueOf(this.actualInFlight), Integer.valueOf(this.pendingFlows.size()), Integer.valueOf(this.inFlightPubRels), Boolean.valueOf(this.callback.isQuiesced()), Integer.valueOf(count)});
        synchronized (this.quiesceLock) {
            this.quiesceLock.notifyAll();
        }
        return true;
    }

    public void clearState() throws MqttException {
        this.log.fine(CLASS_NAME, "clearState", ">");
        this.persistence.clear();
        this.inUseMsgIds.clear();
        this.pendingMessages.clear();
        this.pendingFlows.clear();
        this.outboundQoS2.clear();
        this.outboundQoS1.clear();
        this.outboundQoS0.clear();
        this.inboundQoS2.clear();
        this.tokenStore.clear();
    }

    public void close() {
        this.inUseMsgIds.clear();
        if (this.pendingMessages != null) {
            this.pendingMessages.clear();
        }
        this.pendingFlows.clear();
        this.outboundQoS2.clear();
        this.outboundQoS1.clear();
        this.outboundQoS0.clear();
        this.inboundQoS2.clear();
        this.tokenStore.clear();
        this.inUseMsgIds = null;
        this.pendingMessages = null;
        this.pendingFlows = null;
        this.outboundQoS2 = null;
        this.outboundQoS1 = null;
        this.outboundQoS0 = null;
        this.inboundQoS2 = null;
        this.tokenStore = null;
        this.callback = null;
        this.clientComms = null;
        this.persistence = null;
        this.pingCommand = null;
        this.highResolutionTimer = null;
    }

    public void connected() {
        this.log.fine(CLASS_NAME, "connected", "631");
        this.connected = true;
        this.pingSender.start();
    }

    public void deliveryComplete(MqttPublish mqttPublish) throws MqttPersistenceException {
        this.log.fine(CLASS_NAME, "deliveryComplete", "641", new Object[]{Integer.valueOf(mqttPublish.getMessageId())});
        this.persistence.remove(getReceivedPersistenceKey((MqttWireMessage) mqttPublish));
        this.inboundQoS2.remove(Integer.valueOf(mqttPublish.getMessageId()));
    }

    public void disconnected(MqttException mqttException) {
        this.log.fine(CLASS_NAME, "disconnected", "633", new Object[]{mqttException});
        this.connected = false;
        try {
            if (this.cleanSession) {
                clearState();
            }
            this.pendingMessages.clear();
            this.pendingFlows.clear();
            synchronized (this.pingOutstandingLock) {
                this.pingOutstanding = 0;
            }
        } catch (MqttException unused) {
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:7|(2:15|16)|17|18|(1:46)(5:20|(2:22|(1:47)(2:24|(2:48|26)))|27|(4:29|(1:31)|32|49)(2:33|(1:50)(2:35|(2:37|51)(2:38|52)))|45)|39|40|41) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0040 */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0044  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d2 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage get() throws org.eclipse.paho.client.mqttv3.MqttException {
        /*
            r10 = this;
            java.lang.Object r0 = r10.queueLock
            monitor-enter(r0)
            r1 = 0
            r2 = r1
        L_0x0005:
            if (r2 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x00df }
            return r2
        L_0x0009:
            java.util.Vector r3 = r10.pendingMessages     // Catch:{ all -> 0x00df }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00df }
            if (r3 == 0) goto L_0x0019
            java.util.Vector r3 = r10.pendingFlows     // Catch:{ all -> 0x00df }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00df }
            if (r3 != 0) goto L_0x0027
        L_0x0019:
            java.util.Vector r3 = r10.pendingFlows     // Catch:{ all -> 0x00df }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00df }
            if (r3 == 0) goto L_0x0040
            int r3 = r10.actualInFlight     // Catch:{ all -> 0x00df }
            int r4 = r10.maxInflight     // Catch:{ all -> 0x00df }
            if (r3 < r4) goto L_0x0040
        L_0x0027:
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = r10.log     // Catch:{ InterruptedException -> 0x0040 }
            java.lang.String r4 = CLASS_NAME     // Catch:{ InterruptedException -> 0x0040 }
            java.lang.String r5 = "get"
            java.lang.String r6 = "644"
            r3.fine(r4, r5, r6)     // Catch:{ InterruptedException -> 0x0040 }
            java.lang.Object r3 = r10.queueLock     // Catch:{ InterruptedException -> 0x0040 }
            r3.wait()     // Catch:{ InterruptedException -> 0x0040 }
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = r10.log     // Catch:{ InterruptedException -> 0x0040 }
            java.lang.String r5 = "get"
            java.lang.String r6 = "647"
            r3.fine(r4, r5, r6)     // Catch:{ InterruptedException -> 0x0040 }
        L_0x0040:
            java.util.Vector r3 = r10.pendingFlows     // Catch:{ all -> 0x00df }
            if (r3 == 0) goto L_0x00d2
            boolean r3 = r10.connected     // Catch:{ all -> 0x00df }
            r4 = 0
            if (r3 != 0) goto L_0x005e
            java.util.Vector r3 = r10.pendingFlows     // Catch:{ all -> 0x00df }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00df }
            if (r3 != 0) goto L_0x00d2
            java.util.Vector r3 = r10.pendingFlows     // Catch:{ all -> 0x00df }
            java.lang.Object r3 = r3.elementAt(r4)     // Catch:{ all -> 0x00df }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r3 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r3     // Catch:{ all -> 0x00df }
            boolean r3 = r3 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect     // Catch:{ all -> 0x00df }
            if (r3 != 0) goto L_0x005e
            goto L_0x00d2
        L_0x005e:
            java.util.Vector r3 = r10.pendingFlows     // Catch:{ all -> 0x00df }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00df }
            r5 = 1
            if (r3 != 0) goto L_0x0090
            java.util.Vector r2 = r10.pendingFlows     // Catch:{ all -> 0x00df }
            java.lang.Object r2 = r2.remove(r4)     // Catch:{ all -> 0x00df }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r2 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r2     // Catch:{ all -> 0x00df }
            boolean r3 = r2 instanceof org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRel     // Catch:{ all -> 0x00df }
            if (r3 == 0) goto L_0x008b
            int r3 = r10.inFlightPubRels     // Catch:{ all -> 0x00df }
            int r3 = r3 + r5
            r10.inFlightPubRels = r3     // Catch:{ all -> 0x00df }
            org.eclipse.paho.client.mqttv3.logging.Logger r6 = r10.log     // Catch:{ all -> 0x00df }
            java.lang.String r7 = CLASS_NAME     // Catch:{ all -> 0x00df }
            java.lang.String r8 = "get"
            java.lang.String r9 = "617"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00df }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x00df }
            r5[r4] = r3     // Catch:{ all -> 0x00df }
            r6.fine(r7, r8, r9, r5)     // Catch:{ all -> 0x00df }
        L_0x008b:
            r10.checkQuiesceLock()     // Catch:{ all -> 0x00df }
            goto L_0x0005
        L_0x0090:
            java.util.Vector r3 = r10.pendingMessages     // Catch:{ all -> 0x00df }
            boolean r3 = r3.isEmpty()     // Catch:{ all -> 0x00df }
            if (r3 != 0) goto L_0x0005
            int r3 = r10.actualInFlight     // Catch:{ all -> 0x00df }
            int r6 = r10.maxInflight     // Catch:{ all -> 0x00df }
            if (r3 >= r6) goto L_0x00c5
            java.util.Vector r2 = r10.pendingMessages     // Catch:{ all -> 0x00df }
            java.lang.Object r2 = r2.elementAt(r4)     // Catch:{ all -> 0x00df }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r2 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage) r2     // Catch:{ all -> 0x00df }
            java.util.Vector r3 = r10.pendingMessages     // Catch:{ all -> 0x00df }
            r3.removeElementAt(r4)     // Catch:{ all -> 0x00df }
            int r3 = r10.actualInFlight     // Catch:{ all -> 0x00df }
            int r3 = r3 + r5
            r10.actualInFlight = r3     // Catch:{ all -> 0x00df }
            org.eclipse.paho.client.mqttv3.logging.Logger r6 = r10.log     // Catch:{ all -> 0x00df }
            java.lang.String r7 = CLASS_NAME     // Catch:{ all -> 0x00df }
            java.lang.String r8 = "get"
            java.lang.String r9 = "623"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00df }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x00df }
            r5[r4] = r3     // Catch:{ all -> 0x00df }
            r6.fine(r7, r8, r9, r5)     // Catch:{ all -> 0x00df }
            goto L_0x0005
        L_0x00c5:
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = r10.log     // Catch:{ all -> 0x00df }
            java.lang.String r4 = CLASS_NAME     // Catch:{ all -> 0x00df }
            java.lang.String r5 = "get"
            java.lang.String r6 = "622"
            r3.fine(r4, r5, r6)     // Catch:{ all -> 0x00df }
            goto L_0x0005
        L_0x00d2:
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r10.log     // Catch:{ all -> 0x00df }
            java.lang.String r3 = CLASS_NAME     // Catch:{ all -> 0x00df }
            java.lang.String r4 = "get"
            java.lang.String r5 = "621"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x00df }
            monitor-exit(r0)     // Catch:{ all -> 0x00df }
            return r1
        L_0x00df:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00df }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientState.get():org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage");
    }

    public int getActualInFlight() {
        return this.actualInFlight;
    }

    public boolean getCleanSession() {
        return this.cleanSession;
    }

    public Properties getDebug() {
        Properties properties = new Properties();
        properties.put("In use msgids", this.inUseMsgIds);
        properties.put("pendingMessages", this.pendingMessages);
        properties.put("pendingFlows", this.pendingFlows);
        properties.put("maxInflight", Integer.valueOf(this.maxInflight));
        properties.put("nextMsgID", Integer.valueOf(this.nextMsgId));
        properties.put("actualInFlight", Integer.valueOf(this.actualInFlight));
        properties.put("inFlightPubRels", Integer.valueOf(this.inFlightPubRels));
        properties.put("quiescing", Boolean.valueOf(this.quiescing));
        properties.put("pingoutstanding", Integer.valueOf(this.pingOutstanding));
        properties.put("lastOutboundActivity", Long.valueOf(this.lastOutboundActivity));
        properties.put("lastInboundActivity", Long.valueOf(this.lastInboundActivity));
        properties.put("outboundQoS2", this.outboundQoS2);
        properties.put("outboundQoS1", this.outboundQoS1);
        properties.put("outboundQoS0", this.outboundQoS0);
        properties.put("inboundQoS2", this.inboundQoS2);
        properties.put("tokens", this.tokenStore);
        return properties;
    }

    public long getKeepAlive() {
        return TimeUnit.NANOSECONDS.toMillis(this.keepAliveNanos);
    }

    public int getMaxInFlight() {
        return this.maxInflight;
    }

    public void notifyComplete(MqttToken mqttToken) throws MqttException {
        MqttWireMessage wireMessage = mqttToken.internalTok.getWireMessage();
        if (wireMessage != null && (wireMessage instanceof MqttAck)) {
            Logger logger = this.log;
            String str = CLASS_NAME;
            logger.fine(str, "notifyComplete", "629", new Object[]{Integer.valueOf(wireMessage.getMessageId()), mqttToken, wireMessage});
            MqttAck mqttAck = (MqttAck) wireMessage;
            if (mqttAck instanceof MqttPubAck) {
                this.persistence.remove(getSendPersistenceKey(wireMessage));
                this.persistence.remove(getSendBufferedPersistenceKey(wireMessage));
                this.outboundQoS1.remove(Integer.valueOf(mqttAck.getMessageId()));
                decrementInFlight();
                releaseMessageId(wireMessage.getMessageId());
                this.tokenStore.removeToken(wireMessage);
                this.log.fine(str, "notifyComplete", "650", new Object[]{Integer.valueOf(mqttAck.getMessageId())});
            } else if (mqttAck instanceof MqttPubComp) {
                this.persistence.remove(getSendPersistenceKey(wireMessage));
                this.persistence.remove(getSendConfirmPersistenceKey(wireMessage));
                this.persistence.remove(getSendBufferedPersistenceKey(wireMessage));
                this.outboundQoS2.remove(Integer.valueOf(mqttAck.getMessageId()));
                this.inFlightPubRels--;
                decrementInFlight();
                releaseMessageId(wireMessage.getMessageId());
                this.tokenStore.removeToken(wireMessage);
                this.log.fine(str, "notifyComplete", "645", new Object[]{Integer.valueOf(mqttAck.getMessageId()), Integer.valueOf(this.inFlightPubRels)});
            }
            checkQuiesceLock();
        }
    }

    public void notifyQueueLock() {
        synchronized (this.queueLock) {
            this.log.fine(CLASS_NAME, "notifyQueueLock", "638");
            this.queueLock.notifyAll();
        }
    }

    public void notifyReceivedAck(MqttAck mqttAck) throws MqttException {
        this.lastInboundActivity = this.highResolutionTimer.nanoTime();
        Logger logger = this.log;
        String str = CLASS_NAME;
        logger.fine(str, "notifyReceivedAck", "627", new Object[]{Integer.valueOf(mqttAck.getMessageId()), mqttAck});
        MqttToken token = this.tokenStore.getToken((MqttWireMessage) mqttAck);
        if (token == null) {
            this.log.fine(str, "notifyReceivedAck", "662", new Object[]{Integer.valueOf(mqttAck.getMessageId())});
        } else if (mqttAck instanceof MqttPubRec) {
            send(new MqttPubRel((MqttPubRec) mqttAck), token);
        } else if ((mqttAck instanceof MqttPubAck) || (mqttAck instanceof MqttPubComp)) {
            notifyResult(mqttAck, token, (MqttException) null);
        } else if (mqttAck instanceof MqttPingResp) {
            synchronized (this.pingOutstandingLock) {
                this.pingOutstanding = Math.max(0, this.pingOutstanding - 1);
                notifyResult(mqttAck, token, (MqttException) null);
                if (this.pingOutstanding == 0) {
                    this.tokenStore.removeToken((MqttWireMessage) mqttAck);
                }
            }
            this.log.fine(str, "notifyReceivedAck", "636", new Object[]{Integer.valueOf(this.pingOutstanding)});
        } else if (mqttAck instanceof MqttConnack) {
            MqttConnack mqttConnack = (MqttConnack) mqttAck;
            int returnCode = mqttConnack.getReturnCode();
            if (returnCode == 0) {
                synchronized (this.queueLock) {
                    if (this.cleanSession) {
                        clearState();
                        this.tokenStore.saveToken(token, (MqttWireMessage) mqttAck);
                    }
                    this.inFlightPubRels = 0;
                    this.actualInFlight = 0;
                    restoreInflightMessages();
                    connected();
                }
                this.clientComms.connectComplete(mqttConnack, (MqttException) null);
                notifyResult(mqttAck, token, (MqttException) null);
                this.tokenStore.removeToken((MqttWireMessage) mqttAck);
                synchronized (this.queueLock) {
                    this.queueLock.notifyAll();
                }
            } else {
                throw ExceptionHelper.createMqttException(returnCode);
            }
        } else {
            notifyResult(mqttAck, token, (MqttException) null);
            releaseMessageId(mqttAck.getMessageId());
            this.tokenStore.removeToken((MqttWireMessage) mqttAck);
        }
        checkQuiesceLock();
    }

    public void notifyReceivedBytes(int i) {
        if (i > 0) {
            this.lastInboundActivity = this.highResolutionTimer.nanoTime();
        }
        this.log.fine(CLASS_NAME, "notifyReceivedBytes", "630", new Object[]{Integer.valueOf(i)});
    }

    public void notifyReceivedMsg(MqttWireMessage mqttWireMessage) throws MqttException {
        this.lastInboundActivity = this.highResolutionTimer.nanoTime();
        this.log.fine(CLASS_NAME, "notifyReceivedMsg", "651", new Object[]{Integer.valueOf(mqttWireMessage.getMessageId()), mqttWireMessage});
        if (this.quiescing) {
            return;
        }
        if (mqttWireMessage instanceof MqttPublish) {
            MqttPublish mqttPublish = (MqttPublish) mqttWireMessage;
            int qos = mqttPublish.getMessage().getQos();
            if (qos == 0 || qos == 1) {
                CommsCallback commsCallback = this.callback;
                if (commsCallback != null) {
                    commsCallback.messageArrived(mqttPublish);
                }
            } else if (qos == 2) {
                this.persistence.put(getReceivedPersistenceKey(mqttWireMessage), mqttPublish);
                this.inboundQoS2.put(Integer.valueOf(mqttPublish.getMessageId()), mqttPublish);
                send(new MqttPubRec(mqttPublish), (MqttToken) null);
            }
        } else if (mqttWireMessage instanceof MqttPubRel) {
            MqttPublish mqttPublish2 = (MqttPublish) this.inboundQoS2.get(Integer.valueOf(mqttWireMessage.getMessageId()));
            if (mqttPublish2 != null) {
                CommsCallback commsCallback2 = this.callback;
                if (commsCallback2 != null) {
                    commsCallback2.messageArrived(mqttPublish2);
                    return;
                }
                return;
            }
            send(new MqttPubComp(mqttWireMessage.getMessageId()), (MqttToken) null);
        }
    }

    public void notifyResult(MqttWireMessage mqttWireMessage, MqttToken mqttToken, MqttException mqttException) {
        mqttToken.internalTok.markComplete(mqttWireMessage, mqttException);
        mqttToken.internalTok.notifyComplete();
        if (mqttWireMessage != null && (mqttWireMessage instanceof MqttAck) && !(mqttWireMessage instanceof MqttPubRec)) {
            this.log.fine(CLASS_NAME, "notifyResult", "648", new Object[]{mqttToken.internalTok.getKey(), mqttWireMessage, mqttException});
            this.callback.asyncOperationComplete(mqttToken);
        }
        if (mqttWireMessage == null) {
            this.log.fine(CLASS_NAME, "notifyResult", "649", new Object[]{mqttToken.internalTok.getKey(), mqttException});
            this.callback.asyncOperationComplete(mqttToken);
        }
    }

    public void notifySent(MqttWireMessage mqttWireMessage) {
        int i;
        this.lastOutboundActivity = this.highResolutionTimer.nanoTime();
        Logger logger = this.log;
        String str = CLASS_NAME;
        logger.fine(str, "notifySent", "625", new Object[]{mqttWireMessage.getKey()});
        MqttToken token = mqttWireMessage.getToken();
        if (token != null || (token = this.tokenStore.getToken(mqttWireMessage)) != null) {
            token.internalTok.notifySent();
            if (mqttWireMessage instanceof MqttPingReq) {
                synchronized (this.pingOutstandingLock) {
                    long nanoTime = this.highResolutionTimer.nanoTime();
                    synchronized (this.pingOutstandingLock) {
                        this.lastPing = nanoTime;
                        i = this.pingOutstanding + 1;
                        this.pingOutstanding = i;
                    }
                    this.log.fine(str, "notifySent", "635", new Object[]{Integer.valueOf(i)});
                }
            } else if ((mqttWireMessage instanceof MqttPublish) && ((MqttPublish) mqttWireMessage).getMessage().getQos() == 0) {
                token.internalTok.markComplete((MqttWireMessage) null, (MqttException) null);
                this.callback.asyncOperationComplete(token);
                decrementInFlight();
                releaseMessageId(mqttWireMessage.getMessageId());
                this.tokenStore.removeToken(mqttWireMessage);
                checkQuiesceLock();
            }
        }
    }

    public void notifySentBytes(int i) {
        if (i > 0) {
            this.lastOutboundActivity = this.highResolutionTimer.nanoTime();
        }
        this.log.fine(CLASS_NAME, "notifySentBytes", "643", new Object[]{Integer.valueOf(i)});
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:1|2|3|4|5|6|7|8) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void persistBufferedMessage(org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage r8) throws org.eclipse.paho.client.mqttv3.MqttException {
        /*
            r7 = this;
            java.lang.String r0 = "persistBufferedMessage"
            java.lang.String r1 = r7.getSendBufferedPersistenceKey(r8)
            r2 = 0
            r3 = 1
            int r4 = r7.getNextMessageId()     // Catch:{ MqttException -> 0x0053 }
            r8.setMessageId(r4)     // Catch:{ MqttException -> 0x0053 }
            java.lang.String r1 = r7.getSendBufferedPersistenceKey(r8)     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.MqttClientPersistence r4 = r7.persistence     // Catch:{ MqttPersistenceException -> 0x001c }
            r5 = r8
            org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish r5 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish) r5     // Catch:{ MqttPersistenceException -> 0x001c }
            r4.put(r1, r5)     // Catch:{ MqttPersistenceException -> 0x001c }
            goto L_0x0045
        L_0x001c:
            org.eclipse.paho.client.mqttv3.logging.Logger r4 = r7.log     // Catch:{ MqttException -> 0x0053 }
            java.lang.String r5 = CLASS_NAME     // Catch:{ MqttException -> 0x0053 }
            java.lang.String r6 = "515"
            r4.fine(r5, r0, r6)     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.MqttClientPersistence r4 = r7.persistence     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.internal.ClientComms r5 = r7.clientComms     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.IMqttAsyncClient r5 = r5.getClient()     // Catch:{ MqttException -> 0x0053 }
            java.lang.String r5 = r5.getClientId()     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.internal.ClientComms r6 = r7.clientComms     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.IMqttAsyncClient r6 = r6.getClient()     // Catch:{ MqttException -> 0x0053 }
            java.lang.String r6 = r6.getServerURI()     // Catch:{ MqttException -> 0x0053 }
            r4.open(r5, r6)     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.MqttClientPersistence r4 = r7.persistence     // Catch:{ MqttException -> 0x0053 }
            org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish r8 = (org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish) r8     // Catch:{ MqttException -> 0x0053 }
            r4.put(r1, r8)     // Catch:{ MqttException -> 0x0053 }
        L_0x0045:
            org.eclipse.paho.client.mqttv3.logging.Logger r8 = r7.log     // Catch:{ MqttException -> 0x0053 }
            java.lang.String r4 = CLASS_NAME     // Catch:{ MqttException -> 0x0053 }
            java.lang.String r5 = "513"
            java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ MqttException -> 0x0053 }
            r6[r2] = r1     // Catch:{ MqttException -> 0x0053 }
            r8.fine(r4, r0, r5, r6)     // Catch:{ MqttException -> 0x0053 }
            return
        L_0x0053:
            r8 = move-exception
            org.eclipse.paho.client.mqttv3.logging.Logger r4 = r7.log
            java.lang.String r5 = CLASS_NAME
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r3[r2] = r1
            java.lang.String r1 = "514"
            r4.warning(r5, r0, r1, r3)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientState.persistBufferedMessage(org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage):void");
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    public void quiesce(long r11) {
        /*
            r10 = this;
            r0 = 0
            int r2 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r2 <= 0) goto L_0x00a3
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r10.log
            java.lang.String r1 = CLASS_NAME
            java.lang.String r2 = "quiesce"
            java.lang.String r3 = "637"
            r4 = 1
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.Long r6 = java.lang.Long.valueOf(r11)
            r7 = 0
            r5[r7] = r6
            r0.fine(r1, r2, r3, r5)
            java.lang.Object r0 = r10.queueLock
            monitor-enter(r0)
            r10.quiescing = r4     // Catch:{ all -> 0x00a0 }
            monitor-exit(r0)     // Catch:{ all -> 0x00a0 }
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r0 = r10.callback
            r0.quiesce()
            r10.notifyQueueLock()
            java.lang.Object r2 = r10.quiesceLock
            monitor-enter(r2)
            org.eclipse.paho.client.mqttv3.internal.CommsTokenStore r0 = r10.tokenStore     // Catch:{ InterruptedException -> 0x007c }
            int r0 = r0.count()     // Catch:{ InterruptedException -> 0x007c }
            if (r0 > 0) goto L_0x0044
            java.util.Vector r3 = r10.pendingFlows     // Catch:{ InterruptedException -> 0x007c }
            int r3 = r3.size()     // Catch:{ InterruptedException -> 0x007c }
            if (r3 > 0) goto L_0x0044
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r3 = r10.callback     // Catch:{ InterruptedException -> 0x007c }
            boolean r3 = r3.isQuiesced()     // Catch:{ InterruptedException -> 0x007c }
            if (r3 != 0) goto L_0x007c
        L_0x0044:
            org.eclipse.paho.client.mqttv3.logging.Logger r3 = r10.log     // Catch:{ InterruptedException -> 0x007c }
            java.lang.String r5 = "quiesce"
            java.lang.String r6 = "639"
            r8 = 4
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ InterruptedException -> 0x007c }
            int r9 = r10.actualInFlight     // Catch:{ InterruptedException -> 0x007c }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ InterruptedException -> 0x007c }
            r8[r7] = r9     // Catch:{ InterruptedException -> 0x007c }
            java.util.Vector r9 = r10.pendingFlows     // Catch:{ InterruptedException -> 0x007c }
            int r9 = r9.size()     // Catch:{ InterruptedException -> 0x007c }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ InterruptedException -> 0x007c }
            r8[r4] = r9     // Catch:{ InterruptedException -> 0x007c }
            int r4 = r10.inFlightPubRels     // Catch:{ InterruptedException -> 0x007c }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ InterruptedException -> 0x007c }
            r9 = 2
            r8[r9] = r4     // Catch:{ InterruptedException -> 0x007c }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ InterruptedException -> 0x007c }
            r4 = 3
            r8[r4] = r0     // Catch:{ InterruptedException -> 0x007c }
            r3.fine(r1, r5, r6, r8)     // Catch:{ InterruptedException -> 0x007c }
            java.lang.Object r0 = r10.quiesceLock     // Catch:{ InterruptedException -> 0x007c }
            r0.wait(r11)     // Catch:{ InterruptedException -> 0x007c }
            goto L_0x007c
        L_0x007a:
            r11 = move-exception
            goto L_0x009e
        L_0x007c:
            monitor-exit(r2)     // Catch:{ all -> 0x007a }
            java.lang.Object r11 = r10.queueLock
            monitor-enter(r11)
            java.util.Vector r12 = r10.pendingMessages     // Catch:{ all -> 0x009b }
            r12.clear()     // Catch:{ all -> 0x009b }
            java.util.Vector r12 = r10.pendingFlows     // Catch:{ all -> 0x009b }
            r12.clear()     // Catch:{ all -> 0x009b }
            r10.quiescing = r7     // Catch:{ all -> 0x009b }
            r10.actualInFlight = r7     // Catch:{ all -> 0x009b }
            monitor-exit(r11)     // Catch:{ all -> 0x009b }
            org.eclipse.paho.client.mqttv3.logging.Logger r11 = r10.log
            java.lang.String r12 = CLASS_NAME
            java.lang.String r0 = "quiesce"
            java.lang.String r1 = "640"
            r11.fine(r12, r0, r1)
            goto L_0x00a3
        L_0x009b:
            r12 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x009b }
            throw r12
        L_0x009e:
            monitor-exit(r2)     // Catch:{ all -> 0x007a }
            throw r11
        L_0x00a0:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00a0 }
            throw r11
        L_0x00a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientState.quiesce(long):void");
    }

    public boolean removeMessage(IMqttDeliveryToken iMqttDeliveryToken) throws MqttException {
        boolean z;
        boolean z2;
        MqttMessage message = iMqttDeliveryToken.getMessage();
        int messageId = iMqttDeliveryToken.getMessageId();
        synchronized (this.queueLock) {
            z = true;
            if (message.getQos() != 1 || this.outboundQoS1.remove(Integer.valueOf(messageId)) == null) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (message.getQos() == 2 && this.outboundQoS2.remove(Integer.valueOf(messageId)) != null) {
                z2 = true;
            }
            if (!this.pendingMessages.removeElement(message)) {
                z = z2;
            }
            this.persistence.remove(getSendPersistenceKey(messageId));
            this.tokenStore.removeToken(Integer.toString(messageId));
            releaseMessageId(messageId);
            decrementInFlight();
        }
        return z;
    }

    public Vector resolveOldTokens(MqttException mqttException) {
        this.log.fine(CLASS_NAME, "resolveOldTokens", "632", new Object[]{mqttException});
        if (mqttException == null) {
            mqttException = new MqttException(32102);
        }
        Vector outstandingTokens = this.tokenStore.getOutstandingTokens();
        Enumeration elements = outstandingTokens.elements();
        while (elements.hasMoreElements()) {
            MqttToken mqttToken = (MqttToken) elements.nextElement();
            synchronized (mqttToken) {
                if (!mqttToken.isComplete() && !mqttToken.internalTok.isCompletePending() && mqttToken.getException() == null) {
                    mqttToken.internalTok.setException(mqttException);
                }
            }
            if (!(mqttToken instanceof MqttDeliveryToken)) {
                this.tokenStore.removeToken(mqttToken.internalTok.getKey());
            }
        }
        return outstandingTokens;
    }

    public void restoreState() throws MqttException {
        Enumeration keys = this.persistence.keys();
        int i = this.nextMsgId;
        Vector vector = new Vector();
        this.log.fine(CLASS_NAME, "restoreState", "600");
        while (keys.hasMoreElements()) {
            String str = (String) keys.nextElement();
            MqttWireMessage restoreMessage = restoreMessage(str, this.persistence.get(str));
            if (restoreMessage != null) {
                if (str.startsWith(PERSISTENCE_RECEIVED_PREFIX)) {
                    this.log.fine(CLASS_NAME, "restoreState", "604", new Object[]{str, restoreMessage});
                    this.inboundQoS2.put(Integer.valueOf(restoreMessage.getMessageId()), restoreMessage);
                } else if (str.startsWith(PERSISTENCE_SENT_PREFIX)) {
                    MqttPublish mqttPublish = (MqttPublish) restoreMessage;
                    i = Math.max(mqttPublish.getMessageId(), i);
                    if (this.persistence.containsKey(getSendConfirmPersistenceKey(mqttPublish))) {
                        MqttPubRel mqttPubRel = (MqttPubRel) restoreMessage(str, this.persistence.get(getSendConfirmPersistenceKey(mqttPublish)));
                        if (mqttPubRel != null) {
                            this.log.fine(CLASS_NAME, "restoreState", "605", new Object[]{str, restoreMessage});
                            this.outboundQoS2.put(Integer.valueOf(mqttPubRel.getMessageId()), mqttPubRel);
                        } else {
                            this.log.fine(CLASS_NAME, "restoreState", "606", new Object[]{str, restoreMessage});
                        }
                    } else {
                        mqttPublish.setDuplicate(true);
                        if (mqttPublish.getMessage().getQos() == 2) {
                            this.log.fine(CLASS_NAME, "restoreState", "607", new Object[]{str, restoreMessage});
                            this.outboundQoS2.put(Integer.valueOf(mqttPublish.getMessageId()), mqttPublish);
                        } else {
                            this.log.fine(CLASS_NAME, "restoreState", "608", new Object[]{str, restoreMessage});
                            this.outboundQoS1.put(Integer.valueOf(mqttPublish.getMessageId()), mqttPublish);
                        }
                    }
                    this.tokenStore.restoreToken(mqttPublish).internalTok.setClient(this.clientComms.getClient());
                    this.inUseMsgIds.put(Integer.valueOf(mqttPublish.getMessageId()), Integer.valueOf(mqttPublish.getMessageId()));
                } else if (str.startsWith(PERSISTENCE_SENT_BUFFERED_PREFIX)) {
                    MqttPublish mqttPublish2 = (MqttPublish) restoreMessage;
                    i = Math.max(mqttPublish2.getMessageId(), i);
                    if (mqttPublish2.getMessage().getQos() == 2) {
                        this.log.fine(CLASS_NAME, "restoreState", "607", new Object[]{str, restoreMessage});
                        this.outboundQoS2.put(Integer.valueOf(mqttPublish2.getMessageId()), mqttPublish2);
                    } else if (mqttPublish2.getMessage().getQos() == 1) {
                        this.log.fine(CLASS_NAME, "restoreState", "608", new Object[]{str, restoreMessage});
                        this.outboundQoS1.put(Integer.valueOf(mqttPublish2.getMessageId()), mqttPublish2);
                    } else {
                        this.log.fine(CLASS_NAME, "restoreState", "511", new Object[]{str, restoreMessage});
                        this.outboundQoS0.put(Integer.valueOf(mqttPublish2.getMessageId()), mqttPublish2);
                        this.persistence.remove(str);
                    }
                    this.tokenStore.restoreToken(mqttPublish2).internalTok.setClient(this.clientComms.getClient());
                    this.inUseMsgIds.put(Integer.valueOf(mqttPublish2.getMessageId()), Integer.valueOf(mqttPublish2.getMessageId()));
                } else if (str.startsWith(PERSISTENCE_CONFIRMED_PREFIX) && !this.persistence.containsKey(getSendPersistenceKey((MqttWireMessage) (MqttPubRel) restoreMessage))) {
                    vector.addElement(str);
                }
            }
        }
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            String str2 = (String) elements.nextElement();
            this.log.fine(CLASS_NAME, "restoreState", "609", new Object[]{str2});
            this.persistence.remove(str2);
        }
        this.nextMsgId = i;
    }

    public void send(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        if (mqttWireMessage.isMessageIdRequired() && mqttWireMessage.getMessageId() == 0) {
            if ((mqttWireMessage instanceof MqttPublish) && ((MqttPublish) mqttWireMessage).getMessage().getQos() != 0) {
                mqttWireMessage.setMessageId(getNextMessageId());
            } else if ((mqttWireMessage instanceof MqttPubAck) || (mqttWireMessage instanceof MqttPubRec) || (mqttWireMessage instanceof MqttPubRel) || (mqttWireMessage instanceof MqttPubComp) || (mqttWireMessage instanceof MqttSubscribe) || (mqttWireMessage instanceof MqttSuback) || (mqttWireMessage instanceof MqttUnsubscribe) || (mqttWireMessage instanceof MqttUnsubAck)) {
                mqttWireMessage.setMessageId(getNextMessageId());
            }
        }
        if (mqttToken != null) {
            mqttWireMessage.setToken(mqttToken);
            try {
                mqttToken.internalTok.setMessageID(mqttWireMessage.getMessageId());
            } catch (Exception unused) {
            }
        }
        if (mqttWireMessage instanceof MqttPublish) {
            synchronized (this.queueLock) {
                int i = this.actualInFlight;
                if (i < this.maxInflight) {
                    MqttMessage message = ((MqttPublish) mqttWireMessage).getMessage();
                    this.log.fine(CLASS_NAME, MqttServiceConstants.SEND_ACTION, "628", new Object[]{Integer.valueOf(mqttWireMessage.getMessageId()), Integer.valueOf(message.getQos()), mqttWireMessage});
                    int qos = message.getQos();
                    if (qos == 1) {
                        this.outboundQoS1.put(Integer.valueOf(mqttWireMessage.getMessageId()), mqttWireMessage);
                        this.persistence.put(getSendPersistenceKey(mqttWireMessage), (MqttPublish) mqttWireMessage);
                        this.tokenStore.saveToken(mqttToken, mqttWireMessage);
                    } else if (qos == 2) {
                        this.outboundQoS2.put(Integer.valueOf(mqttWireMessage.getMessageId()), mqttWireMessage);
                        this.persistence.put(getSendPersistenceKey(mqttWireMessage), (MqttPublish) mqttWireMessage);
                        this.tokenStore.saveToken(mqttToken, mqttWireMessage);
                    }
                    this.pendingMessages.addElement(mqttWireMessage);
                    this.queueLock.notifyAll();
                } else {
                    this.log.fine(CLASS_NAME, MqttServiceConstants.SEND_ACTION, "613", new Object[]{Integer.valueOf(i)});
                    throw new MqttException(32202);
                }
            }
            return;
        }
        this.log.fine(CLASS_NAME, MqttServiceConstants.SEND_ACTION, "615", new Object[]{Integer.valueOf(mqttWireMessage.getMessageId()), mqttWireMessage});
        if (mqttWireMessage instanceof MqttConnect) {
            synchronized (this.queueLock) {
                this.tokenStore.saveToken(mqttToken, mqttWireMessage);
                this.pendingFlows.insertElementAt(mqttWireMessage, 0);
                this.queueLock.notifyAll();
            }
            return;
        }
        if (mqttWireMessage instanceof MqttPingReq) {
            this.pingCommand = mqttWireMessage;
        } else if (mqttWireMessage instanceof MqttPubRel) {
            this.outboundQoS2.put(Integer.valueOf(mqttWireMessage.getMessageId()), mqttWireMessage);
            this.persistence.put(getSendConfirmPersistenceKey(mqttWireMessage), (MqttPubRel) mqttWireMessage);
        } else if (mqttWireMessage instanceof MqttPubComp) {
            this.persistence.remove(getReceivedPersistenceKey(mqttWireMessage));
        }
        synchronized (this.queueLock) {
            if (!(mqttWireMessage instanceof MqttAck)) {
                this.tokenStore.saveToken(mqttToken, mqttWireMessage);
            }
            this.pendingFlows.addElement(mqttWireMessage);
            this.queueLock.notifyAll();
        }
    }

    public void setCleanSession(boolean z) {
        this.cleanSession = z;
    }

    public void setKeepAliveInterval(long j) {
        this.keepAliveNanos = TimeUnit.MILLISECONDS.toNanos(j);
    }

    public void setKeepAliveSecs(long j) {
        this.keepAliveNanos = TimeUnit.SECONDS.toNanos(j);
    }

    public void setMaxInflight(int i) {
        this.maxInflight = i;
        this.pendingMessages = new Vector(this.maxInflight);
    }

    public void unPersistBufferedMessage(MqttWireMessage mqttWireMessage) {
        try {
            this.log.fine(CLASS_NAME, "unPersistBufferedMessage", "517", new Object[]{mqttWireMessage.getKey()});
            this.persistence.remove(getSendBufferedPersistenceKey(mqttWireMessage));
        } catch (MqttPersistenceException unused) {
            this.log.fine(CLASS_NAME, "unPersistBufferedMessage", "518", new Object[]{mqttWireMessage.getKey()});
        }
    }

    public void undo(MqttPublish mqttPublish) throws MqttPersistenceException {
        synchronized (this.queueLock) {
            this.log.fine(CLASS_NAME, "undo", "618", new Object[]{Integer.valueOf(mqttPublish.getMessageId()), Integer.valueOf(mqttPublish.getMessage().getQos())});
            if (mqttPublish.getMessage().getQos() == 1) {
                this.outboundQoS1.remove(Integer.valueOf(mqttPublish.getMessageId()));
            } else {
                this.outboundQoS2.remove(Integer.valueOf(mqttPublish.getMessageId()));
            }
            this.pendingMessages.removeElement(mqttPublish);
            this.persistence.remove(getSendPersistenceKey((MqttWireMessage) mqttPublish));
            this.tokenStore.removeToken((MqttWireMessage) mqttPublish);
            if (mqttPublish.getMessage().getQos() > 0) {
                releaseMessageId(mqttPublish.getMessageId());
                mqttPublish.setMessageId(0);
            }
            checkQuiesceLock();
        }
    }

    public void deliveryComplete(int i) throws MqttPersistenceException {
        this.log.fine(CLASS_NAME, "deliveryComplete", "641", new Object[]{Integer.valueOf(i)});
        this.persistence.remove(getReceivedPersistenceKey(i));
        this.inboundQoS2.remove(Integer.valueOf(i));
    }

    private String getReceivedPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_RECEIVED_PREFIX + mqttWireMessage.getMessageId();
    }

    private String getSendPersistenceKey(MqttWireMessage mqttWireMessage) {
        return PERSISTENCE_SENT_PREFIX + mqttWireMessage.getMessageId();
    }
}
