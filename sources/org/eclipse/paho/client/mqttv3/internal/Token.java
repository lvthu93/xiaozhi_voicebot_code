package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Token {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.Token";
    private IMqttActionListener callback = null;
    private IMqttAsyncClient client = null;
    private volatile boolean completed = false;
    private MqttException exception = null;
    private String key;
    private Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    protected MqttMessage message = null;
    private int messageID = 0;
    private boolean notified = false;
    private boolean pendingComplete = false;
    private MqttWireMessage response = null;
    private final Object responseLock = new Object();
    private boolean sent = false;
    private final Object sentLock = new Object();
    private String[] topics = null;
    private Object userContext = null;

    public Token(String str) {
        this.log.setResourceName(str);
    }

    public boolean checkResult() throws MqttException {
        if (getException() == null) {
            return true;
        }
        throw getException();
    }

    public IMqttActionListener getActionCallback() {
        return this.callback;
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    public MqttException getException() {
        return this.exception;
    }

    public int[] getGrantedQos() {
        int[] iArr = new int[0];
        MqttWireMessage mqttWireMessage = this.response;
        if (mqttWireMessage instanceof MqttSuback) {
            return ((MqttSuback) mqttWireMessage).getGrantedQos();
        }
        return iArr;
    }

    public String getKey() {
        return this.key;
    }

    public MqttMessage getMessage() {
        return this.message;
    }

    public int getMessageID() {
        return this.messageID;
    }

    public MqttWireMessage getResponse() {
        return this.response;
    }

    public boolean getSessionPresent() {
        MqttWireMessage mqttWireMessage = this.response;
        if (mqttWireMessage instanceof MqttConnack) {
            return ((MqttConnack) mqttWireMessage).getSessionPresent();
        }
        return false;
    }

    public String[] getTopics() {
        return this.topics;
    }

    public Object getUserContext() {
        return this.userContext;
    }

    public MqttWireMessage getWireMessage() {
        return this.response;
    }

    public boolean isComplete() {
        return this.completed;
    }

    public boolean isCompletePending() {
        return this.pendingComplete;
    }

    public boolean isInUse() {
        return getClient() != null && !isComplete();
    }

    public boolean isNotified() {
        return this.notified;
    }

    public void markComplete(MqttWireMessage mqttWireMessage, MqttException mqttException) {
        this.log.fine(CLASS_NAME, "markComplete", "404", new Object[]{getKey(), mqttWireMessage, mqttException});
        synchronized (this.responseLock) {
            if (mqttWireMessage instanceof MqttAck) {
                this.message = null;
            }
            this.pendingComplete = true;
            this.response = mqttWireMessage;
            this.exception = mqttException;
        }
    }

    public void notifyComplete() {
        this.log.fine(CLASS_NAME, "notifyComplete", "404", new Object[]{getKey(), this.response, this.exception});
        synchronized (this.responseLock) {
            if (this.exception != null || !this.pendingComplete) {
                this.pendingComplete = false;
            } else {
                this.completed = true;
                this.pendingComplete = false;
            }
            this.responseLock.notifyAll();
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    public void notifySent() {
        this.log.fine(CLASS_NAME, "notifySent", "403", new Object[]{getKey()});
        synchronized (this.responseLock) {
            this.response = null;
            this.completed = false;
        }
        synchronized (this.sentLock) {
            this.sent = true;
            this.sentLock.notifyAll();
        }
    }

    public void reset() throws MqttException {
        if (!isInUse()) {
            this.log.fine(CLASS_NAME, "reset", "410", new Object[]{getKey()});
            this.client = null;
            this.completed = false;
            this.response = null;
            this.sent = false;
            this.exception = null;
            this.userContext = null;
            return;
        }
        throw new MqttException(32201);
    }

    public void setActionCallback(IMqttActionListener iMqttActionListener) {
        this.callback = iMqttActionListener;
    }

    public void setClient(IMqttAsyncClient iMqttAsyncClient) {
        this.client = iMqttAsyncClient;
    }

    public void setException(MqttException mqttException) {
        synchronized (this.responseLock) {
            this.exception = mqttException;
        }
    }

    public void setKey(String str) {
        this.key = str;
    }

    public void setMessage(MqttMessage mqttMessage) {
        this.message = mqttMessage;
    }

    public void setMessageID(int i) {
        this.messageID = i;
    }

    public void setNotified(boolean z) {
        this.notified = z;
    }

    public void setTopics(String[] strArr) {
        this.topics = (String[]) strArr.clone();
    }

    public void setUserContext(Object obj) {
        this.userContext = obj;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("key=");
        stringBuffer.append(getKey());
        stringBuffer.append(" ,topics=");
        if (getTopics() != null) {
            for (String append : getTopics()) {
                stringBuffer.append(append);
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append(" ,usercontext=");
        stringBuffer.append(getUserContext());
        stringBuffer.append(" ,isComplete=");
        stringBuffer.append(isComplete());
        stringBuffer.append(" ,isNotified=");
        stringBuffer.append(isNotified());
        stringBuffer.append(" ,exception=");
        stringBuffer.append(getException());
        stringBuffer.append(" ,actioncallback=");
        stringBuffer.append(getActionCallback());
        return stringBuffer.toString();
    }

    public void waitForCompletion() throws MqttException {
        waitForCompletion(-1);
    }

    public MqttWireMessage waitForResponse() throws MqttException {
        return waitForResponse(-1);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:9|10|(2:32|(2:13|(2:15|16)(1:17))(1:18))(4:20|21|33|31)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x000b */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x000b A[LOOP:0: B:9:0x000b->B:31:0x000b, LOOP_START, SYNTHETIC, Splitter:B:9:0x000b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitUntilSent() throws org.eclipse.paho.client.mqttv3.MqttException {
        /*
            r8 = this;
            java.lang.Object r0 = r8.sentLock
            monitor-enter(r0)
            java.lang.Object r1 = r8.responseLock     // Catch:{ all -> 0x003d }
            monitor-enter(r1)     // Catch:{ all -> 0x003d }
            org.eclipse.paho.client.mqttv3.MqttException r2 = r8.exception     // Catch:{ all -> 0x003a }
            if (r2 != 0) goto L_0x0039
            monitor-exit(r1)     // Catch:{ all -> 0x003a }
        L_0x000b:
            boolean r1 = r8.sent     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x001e
            if (r1 != 0) goto L_0x001c
            org.eclipse.paho.client.mqttv3.MqttException r1 = r8.exception     // Catch:{ all -> 0x003d }
            if (r1 != 0) goto L_0x001b
            r1 = 6
            org.eclipse.paho.client.mqttv3.MqttException r1 = org.eclipse.paho.client.mqttv3.internal.ExceptionHelper.createMqttException((int) r1)     // Catch:{ all -> 0x003d }
            throw r1     // Catch:{ all -> 0x003d }
        L_0x001b:
            throw r1     // Catch:{ all -> 0x003d }
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x003d }
            return
        L_0x001e:
            org.eclipse.paho.client.mqttv3.logging.Logger r1 = r8.log     // Catch:{ InterruptedException -> 0x000b }
            java.lang.String r2 = CLASS_NAME     // Catch:{ InterruptedException -> 0x000b }
            java.lang.String r3 = "waitUntilSent"
            java.lang.String r4 = "409"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InterruptedException -> 0x000b }
            java.lang.String r6 = r8.getKey()     // Catch:{ InterruptedException -> 0x000b }
            r7 = 0
            r5[r7] = r6     // Catch:{ InterruptedException -> 0x000b }
            r1.fine(r2, r3, r4, r5)     // Catch:{ InterruptedException -> 0x000b }
            java.lang.Object r1 = r8.sentLock     // Catch:{ InterruptedException -> 0x000b }
            r1.wait()     // Catch:{ InterruptedException -> 0x000b }
            goto L_0x000b
        L_0x0039:
            throw r2     // Catch:{ all -> 0x003a }
        L_0x003a:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x003a }
            throw r2     // Catch:{ all -> 0x003d }
        L_0x003d:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003d }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.Token.waitUntilSent():void");
    }

    public void waitForCompletion(long j) throws MqttException {
        Logger logger = this.log;
        String str = CLASS_NAME;
        logger.fine(str, "waitForCompletion", "407", new Object[]{getKey(), Long.valueOf(j), this});
        if (waitForResponse(j) != null || this.completed) {
            checkResult();
            return;
        }
        this.log.fine(str, "waitForCompletion", "406", new Object[]{getKey(), this});
        MqttException mqttException = new MqttException(32000);
        this.exception = mqttException;
        throw mqttException;
    }

    public MqttWireMessage waitForResponse(long j) throws MqttException {
        long j2 = j;
        synchronized (this.responseLock) {
            Logger logger = this.log;
            String str = CLASS_NAME;
            Object[] objArr = new Object[7];
            objArr[0] = getKey();
            objArr[1] = Long.valueOf(j);
            objArr[2] = Boolean.valueOf(this.sent);
            objArr[3] = Boolean.valueOf(this.completed);
            MqttException mqttException = this.exception;
            objArr[4] = mqttException == null ? "false" : "true";
            objArr[5] = this.response;
            objArr[6] = this;
            logger.fine(str, "waitForResponse", "400", objArr, mqttException);
            while (true) {
                if (this.completed) {
                    break;
                }
                if (this.exception == null) {
                    try {
                        this.log.fine(CLASS_NAME, "waitForResponse", "408", new Object[]{getKey(), Long.valueOf(j)});
                        if (j2 <= 0) {
                            this.responseLock.wait();
                        } else {
                            this.responseLock.wait(j2);
                        }
                    } catch (InterruptedException e) {
                        this.exception = new MqttException((Throwable) e);
                    }
                }
                if (!this.completed) {
                    MqttException mqttException2 = this.exception;
                    if (mqttException2 != null) {
                        this.log.fine(CLASS_NAME, "waitForResponse", "401", (Object[]) null, mqttException2);
                        throw this.exception;
                    } else if (j2 > 0) {
                        break;
                    }
                }
            }
        }
        this.log.fine(CLASS_NAME, "waitForResponse", "402", new Object[]{getKey(), this.response});
        return this.response;
    }
}
