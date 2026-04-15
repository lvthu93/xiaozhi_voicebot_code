package org.eclipse.paho.android.service;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;

class MqttTokenAndroid implements IMqttToken {
    private MqttAndroidClient client;
    private IMqttToken delegate;
    private volatile boolean isComplete;
    private volatile MqttException lastException;
    private IMqttActionListener listener;
    private MqttException pendingException;
    private String[] topics;
    private Object userContext;
    private Object waitObject;

    public MqttTokenAndroid(MqttAndroidClient mqttAndroidClient, Object obj, IMqttActionListener iMqttActionListener) {
        this(mqttAndroidClient, obj, iMqttActionListener, (String[]) null);
    }

    public IMqttActionListener getActionCallback() {
        return this.listener;
    }

    public IMqttAsyncClient getClient() {
        return this.client;
    }

    public MqttException getException() {
        return this.lastException;
    }

    public int[] getGrantedQos() {
        return this.delegate.getGrantedQos();
    }

    public int getMessageId() {
        IMqttToken iMqttToken = this.delegate;
        if (iMqttToken != null) {
            return iMqttToken.getMessageId();
        }
        return 0;
    }

    public MqttWireMessage getResponse() {
        return this.delegate.getResponse();
    }

    public boolean getSessionPresent() {
        return this.delegate.getSessionPresent();
    }

    public String[] getTopics() {
        return this.topics;
    }

    public Object getUserContext() {
        return this.userContext;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    public void notifyComplete() {
        synchronized (this.waitObject) {
            this.isComplete = true;
            this.waitObject.notifyAll();
            IMqttActionListener iMqttActionListener = this.listener;
            if (iMqttActionListener != null) {
                iMqttActionListener.onSuccess(this);
            }
        }
    }

    public void notifyFailure(Throwable th) {
        synchronized (this.waitObject) {
            this.isComplete = true;
            if (th instanceof MqttException) {
                this.pendingException = (MqttException) th;
            } else {
                this.pendingException = new MqttException(th);
            }
            this.waitObject.notifyAll();
            if (th instanceof MqttException) {
                this.lastException = (MqttException) th;
            }
            IMqttActionListener iMqttActionListener = this.listener;
            if (iMqttActionListener != null) {
                iMqttActionListener.onFailure(this, th);
            }
        }
    }

    public void setActionCallback(IMqttActionListener iMqttActionListener) {
        this.listener = iMqttActionListener;
    }

    public void setComplete(boolean z) {
        this.isComplete = z;
    }

    public void setDelegate(IMqttToken iMqttToken) {
        this.delegate = iMqttToken;
    }

    public void setException(MqttException mqttException) {
        this.lastException = mqttException;
    }

    public void setUserContext(Object obj) {
        this.userContext = obj;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x000b */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0011  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0010 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitForCompletion() throws org.eclipse.paho.client.mqttv3.MqttException, org.eclipse.paho.client.mqttv3.MqttSecurityException {
        /*
            r2 = this;
            java.lang.Object r0 = r2.waitObject
            monitor-enter(r0)
            java.lang.Object r1 = r2.waitObject     // Catch:{ InterruptedException -> 0x000b }
            r1.wait()     // Catch:{ InterruptedException -> 0x000b }
            goto L_0x000b
        L_0x0009:
            r1 = move-exception
            goto L_0x0012
        L_0x000b:
            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
            org.eclipse.paho.client.mqttv3.MqttException r0 = r2.pendingException
            if (r0 != 0) goto L_0x0011
            return
        L_0x0011:
            throw r0
        L_0x0012:
            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.android.service.MqttTokenAndroid.waitForCompletion():void");
    }

    public MqttTokenAndroid(MqttAndroidClient mqttAndroidClient, Object obj, IMqttActionListener iMqttActionListener, String[] strArr) {
        this.waitObject = new Object();
        this.client = mqttAndroidClient;
        this.userContext = obj;
        this.listener = iMqttActionListener;
        this.topics = strArr;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x000b */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0016  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x000f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void waitForCompletion(long r3) throws org.eclipse.paho.client.mqttv3.MqttException, org.eclipse.paho.client.mqttv3.MqttSecurityException {
        /*
            r2 = this;
            java.lang.Object r0 = r2.waitObject
            monitor-enter(r0)
            java.lang.Object r1 = r2.waitObject     // Catch:{ InterruptedException -> 0x000b }
            r1.wait(r3)     // Catch:{ InterruptedException -> 0x000b }
            goto L_0x000b
        L_0x0009:
            r3 = move-exception
            goto L_0x001e
        L_0x000b:
            boolean r3 = r2.isComplete     // Catch:{ all -> 0x0009 }
            if (r3 == 0) goto L_0x0016
            org.eclipse.paho.client.mqttv3.MqttException r3 = r2.pendingException     // Catch:{ all -> 0x0009 }
            if (r3 != 0) goto L_0x0015
            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
            return
        L_0x0015:
            throw r3     // Catch:{ all -> 0x0009 }
        L_0x0016:
            org.eclipse.paho.client.mqttv3.MqttException r3 = new org.eclipse.paho.client.mqttv3.MqttException     // Catch:{ all -> 0x0009 }
            r4 = 32000(0x7d00, float:4.4842E-41)
            r3.<init>((int) r4)     // Catch:{ all -> 0x0009 }
            throw r3     // Catch:{ all -> 0x0009 }
        L_0x001e:
            monitor-exit(r0)     // Catch:{ all -> 0x0009 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.android.service.MqttTokenAndroid.waitForCompletion(long):void");
    }
}
