package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.BufferedMessage;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ClientComms {
    public static String BUILD_LEVEL = "L${build.level}";
    private static final byte CLOSED = 4;
    private static final byte CONNECTED = 0;
    private static final byte CONNECTING = 1;
    private static final byte DISCONNECTED = 3;
    private static final byte DISCONNECTING = 2;
    public static String VERSION = "${project.version}";
    /* access modifiers changed from: private */
    public final String CLASS_NAME;
    /* access modifiers changed from: private */
    public CommsCallback callback;
    private IMqttAsyncClient client;
    /* access modifiers changed from: private */
    public ClientState clientState;
    private boolean closePending = false;
    private final Object conLock = new Object();
    private MqttConnectOptions conOptions;
    private byte conState = 3;
    /* access modifiers changed from: private */
    public DisconnectedMessageBuffer disconnectedMessageBuffer;
    /* access modifiers changed from: private */
    public ExecutorService executorService;
    /* access modifiers changed from: private */
    public final Logger log;
    /* access modifiers changed from: private */
    public int networkModuleIndex;
    /* access modifiers changed from: private */
    public NetworkModule[] networkModules;
    private MqttClientPersistence persistence;
    private MqttPingSender pingSender;
    /* access modifiers changed from: private */
    public CommsReceiver receiver;
    private boolean resting = false;
    /* access modifiers changed from: private */
    public CommsSender sender;
    private boolean stoppingComms = false;
    /* access modifiers changed from: private */
    public CommsTokenStore tokenStore;

    public class ConnectBG implements Runnable {
        ClientComms clientComms;
        MqttConnect conPacket;
        MqttToken conToken;
        private String threadName;

        public ConnectBG(ClientComms clientComms2, MqttToken mqttToken, MqttConnect mqttConnect, ExecutorService executorService) {
            this.clientComms = clientComms2;
            this.conToken = mqttToken;
            this.conPacket = mqttConnect;
            this.threadName = "MQTT Con: " + ClientComms.this.getClient().getClientId();
        }

        public void run() {
            MqttException mqttException;
            Thread.currentThread().setName(this.threadName);
            ClientComms.this.log.fine(ClientComms.this.CLASS_NAME, "connectBG:run", "220");
            try {
                MqttDeliveryToken[] outstandingDelTokens = ClientComms.this.tokenStore.getOutstandingDelTokens();
                int length = outstandingDelTokens.length;
                int i = 0;
                while (true) {
                    mqttException = null;
                    if (i >= length) {
                        break;
                    }
                    outstandingDelTokens[i].internalTok.setException((MqttException) null);
                    i++;
                }
                ClientComms.this.tokenStore.saveToken(this.conToken, (MqttWireMessage) this.conPacket);
                NetworkModule networkModule = ClientComms.this.networkModules[ClientComms.this.networkModuleIndex];
                networkModule.start();
                ClientComms clientComms2 = ClientComms.this;
                clientComms2.receiver = new CommsReceiver(this.clientComms, clientComms2.clientState, ClientComms.this.tokenStore, networkModule.getInputStream());
                CommsReceiver access$8 = ClientComms.this.receiver;
                access$8.start("MQTT Rec: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms clientComms3 = ClientComms.this;
                clientComms3.sender = new CommsSender(this.clientComms, clientComms3.clientState, ClientComms.this.tokenStore, networkModule.getOutputStream());
                CommsSender access$10 = ClientComms.this.sender;
                access$10.start("MQTT Snd: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                CommsCallback access$11 = ClientComms.this.callback;
                access$11.start("MQTT Call: " + ClientComms.this.getClient().getClientId(), ClientComms.this.executorService);
                ClientComms.this.internalSend(this.conPacket, this.conToken);
            } catch (MqttException e) {
                ClientComms.this.log.fine(ClientComms.this.CLASS_NAME, "connectBG:run", "212", (Object[]) null, e);
                mqttException = e;
            } catch (Exception e2) {
                ClientComms.this.log.fine(ClientComms.this.CLASS_NAME, "connectBG:run", "209", (Object[]) null, e2);
                mqttException = ExceptionHelper.createMqttException((Throwable) e2);
            }
            if (mqttException != null) {
                ClientComms.this.shutdownConnection(this.conToken, mqttException);
            }
        }

        public void start() {
            if (ClientComms.this.executorService == null) {
                new Thread(this).start();
            } else {
                ClientComms.this.executorService.execute(this);
            }
        }
    }

    public class DisconnectBG implements Runnable {
        MqttDisconnect disconnect;
        long quiesceTimeout;
        private String threadName;
        MqttToken token;

        public DisconnectBG(MqttDisconnect mqttDisconnect, long j, MqttToken mqttToken, ExecutorService executorService) {
            this.disconnect = mqttDisconnect;
            this.quiesceTimeout = j;
            this.token = mqttToken;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0065, code lost:
            if (org.eclipse.paho.client.mqttv3.internal.ClientComms.access$10(r4.this$0).isRunning() != false) goto L_0x00b6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ad, code lost:
            if (org.eclipse.paho.client.mqttv3.internal.ClientComms.access$10(r4.this$0).isRunning() != false) goto L_0x00b6;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r4 = this;
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                java.lang.String r1 = r4.threadName
                r0.setName(r1)
                org.eclipse.paho.client.mqttv3.internal.ClientComms r0 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.logging.Logger r0 = r0.log
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                java.lang.String r1 = r1.CLASS_NAME
                java.lang.String r2 = "disconnectBG:run"
                java.lang.String r3 = "221"
                r0.fine(r1, r2, r3)
                org.eclipse.paho.client.mqttv3.internal.ClientComms r0 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.internal.ClientState r0 = r0.clientState
                long r1 = r4.quiesceTimeout
                r0.quiesce(r1)
                r0 = 0
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect r2 = r4.disconnect     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                org.eclipse.paho.client.mqttv3.MqttToken r3 = r4.token     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                r1.internalSend(r2, r3)     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                org.eclipse.paho.client.mqttv3.internal.CommsSender r1 = r1.sender     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                if (r1 == 0) goto L_0x004c
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                org.eclipse.paho.client.mqttv3.internal.CommsSender r1 = r1.sender     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                boolean r1 = r1.isRunning()     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                if (r1 == 0) goto L_0x004c
                org.eclipse.paho.client.mqttv3.MqttToken r1 = r4.token     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                org.eclipse.paho.client.mqttv3.internal.Token r1 = r1.internalTok     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
                r1.waitUntilSent()     // Catch:{ MqttException -> 0x0093, all -> 0x0068 }
            L_0x004c:
                org.eclipse.paho.client.mqttv3.MqttToken r1 = r4.token
                org.eclipse.paho.client.mqttv3.internal.Token r1 = r1.internalTok
                r1.markComplete(r0, r0)
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.internal.CommsSender r1 = r1.sender
                if (r1 == 0) goto L_0x00af
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.internal.CommsSender r1 = r1.sender
                boolean r1 = r1.isRunning()
                if (r1 != 0) goto L_0x00b6
                goto L_0x00af
            L_0x0068:
                r1 = move-exception
                org.eclipse.paho.client.mqttv3.MqttToken r2 = r4.token
                org.eclipse.paho.client.mqttv3.internal.Token r2 = r2.internalTok
                r2.markComplete(r0, r0)
                org.eclipse.paho.client.mqttv3.internal.ClientComms r2 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.internal.CommsSender r2 = r2.sender
                if (r2 == 0) goto L_0x0084
                org.eclipse.paho.client.mqttv3.internal.ClientComms r2 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.internal.CommsSender r2 = r2.sender
                boolean r2 = r2.isRunning()
                if (r2 != 0) goto L_0x008b
            L_0x0084:
                org.eclipse.paho.client.mqttv3.MqttToken r2 = r4.token
                org.eclipse.paho.client.mqttv3.internal.Token r2 = r2.internalTok
                r2.notifyComplete()
            L_0x008b:
                org.eclipse.paho.client.mqttv3.internal.ClientComms r2 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.MqttToken r3 = r4.token
                r2.shutdownConnection(r3, r0)
                throw r1
            L_0x0093:
                org.eclipse.paho.client.mqttv3.MqttToken r1 = r4.token
                org.eclipse.paho.client.mqttv3.internal.Token r1 = r1.internalTok
                r1.markComplete(r0, r0)
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.internal.CommsSender r1 = r1.sender
                if (r1 == 0) goto L_0x00af
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.internal.CommsSender r1 = r1.sender
                boolean r1 = r1.isRunning()
                if (r1 != 0) goto L_0x00b6
            L_0x00af:
                org.eclipse.paho.client.mqttv3.MqttToken r1 = r4.token
                org.eclipse.paho.client.mqttv3.internal.Token r1 = r1.internalTok
                r1.notifyComplete()
            L_0x00b6:
                org.eclipse.paho.client.mqttv3.internal.ClientComms r1 = org.eclipse.paho.client.mqttv3.internal.ClientComms.this
                org.eclipse.paho.client.mqttv3.MqttToken r2 = r4.token
                r1.shutdownConnection(r2, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientComms.DisconnectBG.run():void");
        }

        public void start() {
            this.threadName = "MQTT Disc: " + ClientComms.this.getClient().getClientId();
            if (ClientComms.this.executorService == null) {
                new Thread(this).start();
            } else {
                ClientComms.this.executorService.execute(this);
            }
        }
    }

    public class MessageDiscardedCallback implements IDiscardedBufferMessageCallback {
        public MessageDiscardedCallback() {
        }

        public void messageDiscarded(MqttWireMessage mqttWireMessage) {
            if (ClientComms.this.disconnectedMessageBuffer.isPersistBuffer()) {
                ClientComms.this.clientState.unPersistBufferedMessage(mqttWireMessage);
            }
        }
    }

    public class ReconnectDisconnectedBufferCallback implements IDisconnectedBufferCallback {
        final String methodName;

        public ReconnectDisconnectedBufferCallback(String str) {
            this.methodName = str;
        }

        public void publishBufferedMessage(BufferedMessage bufferedMessage) throws MqttException {
            if (ClientComms.this.isConnected()) {
                while (ClientComms.this.clientState.getActualInFlight() >= ClientComms.this.clientState.getMaxInFlight() - 3) {
                    Thread.yield();
                }
                ClientComms.this.log.fine(ClientComms.this.CLASS_NAME, this.methodName, "510", new Object[]{bufferedMessage.getMessage().getKey()});
                ClientComms.this.internalSend(bufferedMessage.getMessage(), bufferedMessage.getToken());
                ClientComms.this.clientState.unPersistBufferedMessage(bufferedMessage.getMessage());
                return;
            }
            ClientComms.this.log.fine(ClientComms.this.CLASS_NAME, this.methodName, "208");
            throw ExceptionHelper.createMqttException(32104);
        }
    }

    public ClientComms(IMqttAsyncClient iMqttAsyncClient, MqttClientPersistence mqttClientPersistence, MqttPingSender mqttPingSender, ExecutorService executorService2, HighResolutionTimer highResolutionTimer) throws MqttException {
        String name = ClientComms.class.getName();
        this.CLASS_NAME = name;
        Logger logger = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, name);
        this.log = logger;
        this.client = iMqttAsyncClient;
        this.persistence = mqttClientPersistence;
        this.pingSender = mqttPingSender;
        mqttPingSender.init(this);
        this.executorService = executorService2;
        this.tokenStore = new CommsTokenStore(getClient().getClientId());
        CommsCallback commsCallback = new CommsCallback(this);
        this.callback = commsCallback;
        ClientState clientState2 = new ClientState(mqttClientPersistence, this.tokenStore, commsCallback, this, mqttPingSender, highResolutionTimer);
        this.clientState = clientState2;
        this.callback.setClientState(clientState2);
        logger.setResourceName(getClient().getClientId());
    }

    private MqttToken handleOldTokens(MqttToken mqttToken, MqttException mqttException) {
        this.log.fine(this.CLASS_NAME, "handleOldTokens", "222");
        MqttToken mqttToken2 = null;
        if (mqttToken != null) {
            try {
                if (!mqttToken.isComplete() && this.tokenStore.getToken(mqttToken.internalTok.getKey()) == null) {
                    this.tokenStore.saveToken(mqttToken, mqttToken.internalTok.getKey());
                }
            } catch (Exception unused) {
            }
        }
        Enumeration elements = this.clientState.resolveOldTokens(mqttException).elements();
        while (elements.hasMoreElements()) {
            MqttToken mqttToken3 = (MqttToken) elements.nextElement();
            if (!mqttToken3.internalTok.getKey().equals(MqttDisconnect.KEY)) {
                if (!mqttToken3.internalTok.getKey().equals("Con")) {
                    this.callback.asyncOperationComplete(mqttToken3);
                }
            }
            mqttToken2 = mqttToken3;
        }
        return mqttToken2;
    }

    private void handleRunException(Exception exc) {
        MqttException mqttException;
        this.log.fine(this.CLASS_NAME, "handleRunException", "804", (Object[]) null, exc);
        if (!(exc instanceof MqttException)) {
            mqttException = new MqttException(32109, exc);
        } else {
            mqttException = (MqttException) exc;
        }
        shutdownConnection((MqttToken) null, mqttException);
    }

    private void shutdownExecutorService() {
        MqttConnectOptions mqttConnectOptions;
        this.executorService.shutdown();
        try {
            ExecutorService executorService2 = this.executorService;
            if (executorService2 != null && (mqttConnectOptions = this.conOptions) != null) {
                TimeUnit timeUnit = TimeUnit.SECONDS;
                if (!executorService2.awaitTermination((long) mqttConnectOptions.getExecutorServiceTimeout(), timeUnit)) {
                    this.executorService.shutdownNow();
                    if (!this.executorService.awaitTermination((long) this.conOptions.getExecutorServiceTimeout(), timeUnit)) {
                        this.log.fine(this.CLASS_NAME, "shutdownExecutorService", "executorService did not terminate");
                    }
                }
            }
        } catch (InterruptedException unused) {
            this.executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public MqttToken checkForActivity() {
        return checkForActivity((IMqttActionListener) null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close(boolean r5) throws org.eclipse.paho.client.mqttv3.MqttException {
        /*
            r4 = this;
            java.lang.Object r0 = r4.conLock
            monitor-enter(r0)
            boolean r1 = r4.isClosed()     // Catch:{ all -> 0x0060 }
            if (r1 != 0) goto L_0x005e
            boolean r1 = r4.isDisconnected()     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x0011
            if (r5 == 0) goto L_0x0033
        L_0x0011:
            org.eclipse.paho.client.mqttv3.logging.Logger r5 = r4.log     // Catch:{ all -> 0x0060 }
            java.lang.String r1 = r4.CLASS_NAME     // Catch:{ all -> 0x0060 }
            java.lang.String r2 = "close"
            java.lang.String r3 = "224"
            r5.fine(r1, r2, r3)     // Catch:{ all -> 0x0060 }
            boolean r5 = r4.isConnecting()     // Catch:{ all -> 0x0060 }
            if (r5 != 0) goto L_0x0056
            boolean r5 = r4.isConnected()     // Catch:{ all -> 0x0060 }
            if (r5 != 0) goto L_0x004f
            boolean r5 = r4.isDisconnecting()     // Catch:{ all -> 0x0060 }
            if (r5 == 0) goto L_0x0033
            r5 = 1
            r4.closePending = r5     // Catch:{ all -> 0x0060 }
            monitor-exit(r0)     // Catch:{ all -> 0x0060 }
            return
        L_0x0033:
            r5 = 4
            r4.conState = r5     // Catch:{ all -> 0x0060 }
            org.eclipse.paho.client.mqttv3.internal.ClientState r5 = r4.clientState     // Catch:{ all -> 0x0060 }
            r5.close()     // Catch:{ all -> 0x0060 }
            r5 = 0
            r4.clientState = r5     // Catch:{ all -> 0x0060 }
            r4.callback = r5     // Catch:{ all -> 0x0060 }
            r4.persistence = r5     // Catch:{ all -> 0x0060 }
            r4.sender = r5     // Catch:{ all -> 0x0060 }
            r4.pingSender = r5     // Catch:{ all -> 0x0060 }
            r4.receiver = r5     // Catch:{ all -> 0x0060 }
            r4.networkModules = r5     // Catch:{ all -> 0x0060 }
            r4.conOptions = r5     // Catch:{ all -> 0x0060 }
            r4.tokenStore = r5     // Catch:{ all -> 0x0060 }
            goto L_0x005e
        L_0x004f:
            r5 = 32100(0x7d64, float:4.4982E-41)
            org.eclipse.paho.client.mqttv3.MqttException r5 = org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException((int) r5)     // Catch:{ all -> 0x0060 }
            throw r5     // Catch:{ all -> 0x0060 }
        L_0x0056:
            org.eclipse.paho.client.mqttv3.MqttException r5 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x0060 }
            r1 = 32110(0x7d6e, float:4.4996E-41)
            r5.<init>((int) r1)     // Catch:{ all -> 0x0060 }
            throw r5     // Catch:{ all -> 0x0060 }
        L_0x005e:
            monitor-exit(r0)     // Catch:{ all -> 0x0060 }
            return
        L_0x0060:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0060 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientComms.close(boolean):void");
    }

    public void connect(MqttConnectOptions mqttConnectOptions, MqttToken mqttToken) throws MqttException {
        synchronized (this.conLock) {
            if (!isDisconnected() || this.closePending) {
                this.log.fine(this.CLASS_NAME, MqttServiceConstants.CONNECT_ACTION, "207", new Object[]{Byte.valueOf(this.conState)});
                if (isClosed() || this.closePending) {
                    throw new MqttException(32111);
                } else if (isConnecting()) {
                    throw new MqttException(32110);
                } else if (isDisconnecting()) {
                    throw new MqttException(32102);
                } else {
                    throw ExceptionHelper.createMqttException(32100);
                }
            } else {
                this.log.fine(this.CLASS_NAME, MqttServiceConstants.CONNECT_ACTION, "214");
                this.conState = 1;
                this.conOptions = mqttConnectOptions;
                MqttConnect mqttConnect = new MqttConnect(this.client.getClientId(), this.conOptions.getMqttVersion(), this.conOptions.isCleanSession(), this.conOptions.getKeepAliveInterval(), this.conOptions.getUserName(), this.conOptions.getPassword(), this.conOptions.getWillMessage(), this.conOptions.getWillDestination());
                this.clientState.setKeepAliveSecs((long) this.conOptions.getKeepAliveInterval());
                this.clientState.setCleanSession(this.conOptions.isCleanSession());
                this.clientState.setMaxInflight(this.conOptions.getMaxInflight());
                this.tokenStore.open();
                new ConnectBG(this, mqttToken, mqttConnect, this.executorService).start();
            }
        }
    }

    public void connectComplete(MqttConnack mqttConnack, MqttException mqttException) throws MqttException {
        int returnCode = mqttConnack.getReturnCode();
        synchronized (this.conLock) {
            if (returnCode == 0) {
                this.log.fine(this.CLASS_NAME, "connectComplete", "215");
                this.conState = CONNECTED;
                return;
            }
            this.log.fine(this.CLASS_NAME, "connectComplete", "204", new Object[]{Integer.valueOf(returnCode)});
            throw mqttException;
        }
    }

    public void deleteBufferedMessage(int i) {
        this.disconnectedMessageBuffer.deleteMessage(i);
    }

    public void deliveryComplete(MqttPublish mqttPublish) throws MqttPersistenceException {
        this.clientState.deliveryComplete(mqttPublish);
    }

    public void disconnect(MqttDisconnect mqttDisconnect, long j, MqttToken mqttToken) throws MqttException {
        synchronized (this.conLock) {
            if (isClosed()) {
                this.log.fine(this.CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "223");
                throw ExceptionHelper.createMqttException(32111);
            } else if (isDisconnected()) {
                this.log.fine(this.CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "211");
                throw ExceptionHelper.createMqttException(32101);
            } else if (isDisconnecting()) {
                this.log.fine(this.CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "219");
                throw ExceptionHelper.createMqttException(32102);
            } else if (Thread.currentThread() != this.callback.getThread()) {
                this.log.fine(this.CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "218");
                this.conState = 2;
                new DisconnectBG(mqttDisconnect, j, mqttToken, this.executorService).start();
            } else {
                this.log.fine(this.CLASS_NAME, MqttServiceConstants.DISCONNECT_ACTION, "210");
                throw ExceptionHelper.createMqttException(32107);
            }
        }
    }

    public void disconnectForcibly(long j, long j2) throws MqttException {
        disconnectForcibly(j, j2, true);
    }

    public int getActualInFlight() {
        return this.clientState.getActualInFlight();
    }

    public MqttMessage getBufferedMessage(int i) {
        return ((MqttPublish) this.disconnectedMessageBuffer.getMessage(i).getMessage()).getMessage();
    }

    public int getBufferedMessageCount() {
        return this.disconnectedMessageBuffer.getMessageCount();
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    public ClientState getClientState() {
        return this.clientState;
    }

    public MqttConnectOptions getConOptions() {
        return this.conOptions;
    }

    public Properties getDebug() {
        Properties properties = new Properties();
        properties.put("conState", Integer.valueOf(this.conState));
        properties.put("serverURI", getClient().getServerURI());
        properties.put("callback", this.callback);
        properties.put("stoppingComms", Boolean.valueOf(this.stoppingComms));
        return properties;
    }

    public long getKeepAlive() {
        return this.clientState.getKeepAlive();
    }

    public int getNetworkModuleIndex() {
        return this.networkModuleIndex;
    }

    public NetworkModule[] getNetworkModules() {
        return this.networkModules;
    }

    public MqttDeliveryToken[] getPendingDeliveryTokens() {
        return this.tokenStore.getOutstandingDelTokens();
    }

    public CommsReceiver getReceiver() {
        return this.receiver;
    }

    public MqttTopic getTopic(String str) {
        return new MqttTopic(str, this);
    }

    public void internalSend(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        this.log.fine(this.CLASS_NAME, "internalSend", "200", new Object[]{mqttWireMessage.getKey(), mqttWireMessage, mqttToken});
        if (mqttToken.getClient() == null) {
            mqttToken.internalTok.setClient(getClient());
            try {
                this.clientState.send(mqttWireMessage, mqttToken);
            } catch (MqttException e) {
                mqttToken.internalTok.setClient((IMqttAsyncClient) null);
                if (mqttWireMessage instanceof MqttPublish) {
                    this.clientState.undo((MqttPublish) mqttWireMessage);
                }
                throw e;
            }
        } else {
            this.log.fine(this.CLASS_NAME, "internalSend", "213", new Object[]{mqttWireMessage.getKey(), mqttWireMessage, mqttToken});
            throw new MqttException(32201);
        }
    }

    public boolean isClosed() {
        boolean z;
        synchronized (this.conLock) {
            if (this.conState == 4) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.conLock) {
            if (this.conState == 0) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.conLock) {
            z = true;
            if (this.conState != 1) {
                z = false;
            }
        }
        return z;
    }

    public boolean isDisconnected() {
        boolean z;
        synchronized (this.conLock) {
            if (this.conState == 3) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean isDisconnecting() {
        boolean z;
        synchronized (this.conLock) {
            if (this.conState == 2) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean isResting() {
        boolean z;
        synchronized (this.conLock) {
            z = this.resting;
        }
        return z;
    }

    public void messageArrivedComplete(int i, int i2) throws MqttException {
        this.callback.messageArrivedComplete(i, i2);
    }

    public void notifyConnect() {
        if (this.disconnectedMessageBuffer != null) {
            this.log.fine(this.CLASS_NAME, "notifyConnect", "509", (Object[]) null);
            this.disconnectedMessageBuffer.setPublishCallback(new ReconnectDisconnectedBufferCallback("notifyConnect"));
            this.disconnectedMessageBuffer.setMessageDiscardedCallBack(new MessageDiscardedCallback());
            ExecutorService executorService2 = this.executorService;
            if (executorService2 == null) {
                new Thread(this.disconnectedMessageBuffer).start();
            } else {
                executorService2.execute(this.disconnectedMessageBuffer);
            }
        }
    }

    public boolean removeMessage(IMqttDeliveryToken iMqttDeliveryToken) throws MqttException {
        return this.clientState.removeMessage(iMqttDeliveryToken);
    }

    public void removeMessageListener(String str) {
        this.callback.removeMessageListener(str);
    }

    public void sendNoWait(MqttWireMessage mqttWireMessage, MqttToken mqttToken) throws MqttException {
        if (isConnected() || ((!isConnected() && (mqttWireMessage instanceof MqttConnect)) || (isDisconnecting() && (mqttWireMessage instanceof MqttDisconnect)))) {
            DisconnectedMessageBuffer disconnectedMessageBuffer2 = this.disconnectedMessageBuffer;
            if (disconnectedMessageBuffer2 == null || disconnectedMessageBuffer2.getMessageCount() == 0) {
                internalSend(mqttWireMessage, mqttToken);
                return;
            }
            this.log.fine(this.CLASS_NAME, "sendNoWait", "507", new Object[]{mqttWireMessage.getKey()});
            if (this.disconnectedMessageBuffer.isPersistBuffer()) {
                this.clientState.persistBufferedMessage(mqttWireMessage);
            }
            this.disconnectedMessageBuffer.putMessage(mqttWireMessage, mqttToken);
        } else if (this.disconnectedMessageBuffer != null) {
            this.log.fine(this.CLASS_NAME, "sendNoWait", "508", new Object[]{mqttWireMessage.getKey()});
            if (this.disconnectedMessageBuffer.isPersistBuffer()) {
                this.clientState.persistBufferedMessage(mqttWireMessage);
            }
            this.disconnectedMessageBuffer.putMessage(mqttWireMessage, mqttToken);
        } else {
            this.log.fine(this.CLASS_NAME, "sendNoWait", "208");
            throw ExceptionHelper.createMqttException(32104);
        }
    }

    public void setCallback(MqttCallback mqttCallback) {
        this.callback.setCallback(mqttCallback);
    }

    public void setDisconnectedMessageBuffer(DisconnectedMessageBuffer disconnectedMessageBuffer2) {
        this.disconnectedMessageBuffer = disconnectedMessageBuffer2;
    }

    public void setManualAcks(boolean z) {
        this.callback.setManualAcks(z);
    }

    public void setMessageListener(String str, IMqttMessageListener iMqttMessageListener) {
        this.callback.setMessageListener(str, iMqttMessageListener);
    }

    public void setNetworkModuleIndex(int i) {
        this.networkModuleIndex = i;
    }

    public void setNetworkModules(NetworkModule[] networkModuleArr) {
        this.networkModules = (NetworkModule[]) networkModuleArr.clone();
    }

    public void setReconnectCallback(MqttCallbackExtended mqttCallbackExtended) {
        this.callback.setReconnectCallback(mqttCallbackExtended);
    }

    public void setRestingState(boolean z) {
        this.resting = z;
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processHandlersOutBlocks(RegionMaker.java:1008)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:978)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    /* JADX WARNING: Missing exception handler attribute for start block: B:74:0x00ca */
    public void shutdownConnection(org.eclipse.paho.client.mqttv3.MqttToken r9, org.eclipse.paho.client.mqttv3.MqttException r10) {
        /*
            r8 = this;
            java.lang.Object r0 = r8.conLock
            monitor-enter(r0)
            boolean r1 = r8.stoppingComms     // Catch:{ all -> 0x00d4 }
            if (r1 != 0) goto L_0x00d2
            boolean r1 = r8.closePending     // Catch:{ all -> 0x00d4 }
            if (r1 != 0) goto L_0x00d2
            boolean r1 = r8.isClosed()     // Catch:{ all -> 0x00d4 }
            if (r1 == 0) goto L_0x0013
            goto L_0x00d2
        L_0x0013:
            r1 = 1
            r8.stoppingComms = r1     // Catch:{ all -> 0x00d4 }
            org.eclipse.paho.client.mqttv3.logging.Logger r2 = r8.log     // Catch:{ all -> 0x00d4 }
            java.lang.String r3 = r8.CLASS_NAME     // Catch:{ all -> 0x00d4 }
            java.lang.String r4 = "shutdownConnection"
            java.lang.String r5 = "216"
            r2.fine(r3, r4, r5)     // Catch:{ all -> 0x00d4 }
            boolean r2 = r8.isConnected()     // Catch:{ all -> 0x00d4 }
            r3 = 0
            if (r2 != 0) goto L_0x0030
            boolean r2 = r8.isDisconnecting()     // Catch:{ all -> 0x00d4 }
            if (r2 != 0) goto L_0x0030
            r2 = 0
            goto L_0x0031
        L_0x0030:
            r2 = 1
        L_0x0031:
            r4 = 2
            r8.conState = r4     // Catch:{ all -> 0x00d4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            if (r9 == 0) goto L_0x0042
            boolean r0 = r9.isComplete()
            if (r0 != 0) goto L_0x0042
            org.eclipse.paho.client.mqttv3.internal.Token r0 = r9.internalTok
            r0.setException(r10)
        L_0x0042:
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r0 = r8.callback
            if (r0 == 0) goto L_0x0049
            r0.stop()
        L_0x0049:
            org.eclipse.paho.client.mqttv3.internal.CommsReceiver r0 = r8.receiver
            if (r0 == 0) goto L_0x0050
            r0.stop()
        L_0x0050:
            org.eclipse.paho.client.mqttv3.internal.NetworkModule[] r0 = r8.networkModules     // Catch:{ Exception -> 0x005d }
            if (r0 == 0) goto L_0x005d
            int r4 = r8.networkModuleIndex     // Catch:{ Exception -> 0x005d }
            r0 = r0[r4]     // Catch:{ Exception -> 0x005d }
            if (r0 == 0) goto L_0x005d
            r0.stop()     // Catch:{ Exception -> 0x005d }
        L_0x005d:
            org.eclipse.paho.client.mqttv3.internal.CommsTokenStore r0 = r8.tokenStore
            org.eclipse.paho.client.mqttv3.MqttException r4 = new org.eclipse.paho.client.mqttv3.MqttException
            r5 = 32102(0x7d66, float:4.4984E-41)
            r4.<init>((int) r5)
            r0.quiesce(r4)
            org.eclipse.paho.client.mqttv3.MqttToken r9 = r8.handleOldTokens(r9, r10)
            org.eclipse.paho.client.mqttv3.internal.ClientState r0 = r8.clientState     // Catch:{ Exception -> 0x0080 }
            r0.disconnected(r10)     // Catch:{ Exception -> 0x0080 }
            org.eclipse.paho.client.mqttv3.internal.ClientState r0 = r8.clientState     // Catch:{ Exception -> 0x0080 }
            boolean r0 = r0.getCleanSession()     // Catch:{ Exception -> 0x0080 }
            if (r0 == 0) goto L_0x0081
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r0 = r8.callback     // Catch:{ Exception -> 0x0080 }
            r0.removeMessageListeners()     // Catch:{ Exception -> 0x0080 }
            goto L_0x0081
        L_0x0080:
        L_0x0081:
            org.eclipse.paho.client.mqttv3.internal.CommsSender r0 = r8.sender
            if (r0 == 0) goto L_0x0088
            r0.stop()
        L_0x0088:
            org.eclipse.paho.client.mqttv3.MqttPingSender r0 = r8.pingSender
            if (r0 == 0) goto L_0x008f
            r0.stop()
        L_0x008f:
            org.eclipse.paho.client.mqttv3.internal.DisconnectedMessageBuffer r0 = r8.disconnectedMessageBuffer     // Catch:{ Exception -> 0x009a }
            if (r0 != 0) goto L_0x009a
            org.eclipse.paho.client.mqttv3.MqttClientPersistence r0 = r8.persistence     // Catch:{ Exception -> 0x009a }
            if (r0 == 0) goto L_0x009a
            r0.close()     // Catch:{ Exception -> 0x009a }
        L_0x009a:
            java.lang.Object r4 = r8.conLock
            monitor-enter(r4)
            org.eclipse.paho.client.mqttv3.logging.Logger r0 = r8.log     // Catch:{ all -> 0x00cf }
            java.lang.String r5 = r8.CLASS_NAME     // Catch:{ all -> 0x00cf }
            java.lang.String r6 = "shutdownConnection"
            java.lang.String r7 = "217"
            r0.fine(r5, r6, r7)     // Catch:{ all -> 0x00cf }
            r0 = 3
            r8.conState = r0     // Catch:{ all -> 0x00cf }
            r8.stoppingComms = r3     // Catch:{ all -> 0x00cf }
            monitor-exit(r4)     // Catch:{ all -> 0x00cf }
            if (r9 == 0) goto L_0x00b7
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r0 = r8.callback
            if (r0 == 0) goto L_0x00b7
            r0.asyncOperationComplete(r9)
        L_0x00b7:
            if (r2 == 0) goto L_0x00c0
            org.eclipse.paho.client.mqttv3.internal.CommsCallback r9 = r8.callback
            if (r9 == 0) goto L_0x00c0
            r9.connectionLost(r10)
        L_0x00c0:
            java.lang.Object r9 = r8.conLock
            monitor-enter(r9)
            boolean r10 = r8.closePending     // Catch:{ all -> 0x00cc }
            if (r10 == 0) goto L_0x00ca
            r8.close(r1)     // Catch:{ Exception -> 0x00ca }
        L_0x00ca:
            monitor-exit(r9)     // Catch:{ all -> 0x00cc }
            return
        L_0x00cc:
            r10 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x00cc }
            throw r10
        L_0x00cf:
            r9 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00cf }
            throw r9
        L_0x00d2:
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            return
        L_0x00d4:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.ClientComms.shutdownConnection(org.eclipse.paho.client.mqttv3.MqttToken, org.eclipse.paho.client.mqttv3.MqttException):void");
    }

    public MqttToken checkForActivity(IMqttActionListener iMqttActionListener) {
        try {
            return this.clientState.checkForActivity(iMqttActionListener);
        } catch (MqttException e) {
            handleRunException(e);
            return null;
        } catch (Exception e2) {
            handleRunException(e2);
            return null;
        }
    }

    public void deliveryComplete(int i) throws MqttPersistenceException {
        this.clientState.deliveryComplete(i);
    }

    public void disconnectForcibly(long j, long j2, boolean z) throws MqttException {
        this.conState = 2;
        ClientState clientState2 = this.clientState;
        if (clientState2 != null) {
            clientState2.quiesce(j);
        }
        MqttToken mqttToken = new MqttToken(this.client.getClientId());
        if (z) {
            try {
                internalSend(new MqttDisconnect(), mqttToken);
                mqttToken.waitForCompletion(j2);
            } catch (Exception unused) {
            } catch (Throwable th) {
                mqttToken.internalTok.markComplete((MqttWireMessage) null, (MqttException) null);
                shutdownConnection(mqttToken, (MqttException) null);
                throw th;
            }
        }
        mqttToken.internalTok.markComplete((MqttWireMessage) null, (MqttException) null);
        shutdownConnection(mqttToken, (MqttException) null);
    }
}
