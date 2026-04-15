package info.dourok.voicebot.java.services;

import android.content.Context;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ZaloMqttClient {
    private static final int QOS = 0;
    private static final String TAG = "ZaloMqttClient";
    private String brokerHost;
    private int brokerPort;
    private final Context context;
    /* access modifiers changed from: private */
    public boolean isConnected = false;
    private String macAddress;
    /* access modifiers changed from: private */
    public MessageCallback messageCallback;
    private MqttAsyncClient mqttClient;
    private String password;
    private ScheduledExecutorService reconnectExecutor;
    private String subscribeTopic;

    public interface MessageCallback {
        void onMessage(String str, String str2);
    }

    public ZaloMqttClient(Context context2) {
        this.context = context2;
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleReconnect$0() {
        if (!this.isConnected) {
            reloadMacAddress();
            start(this.brokerHost, this.brokerPort, this.macAddress, this.password);
        }
    }

    private void reloadMacAddress() {
        try {
            String d = new c2(this.context).d();
            if (d != null && !d.isEmpty() && !d.equals(this.macAddress)) {
                this.macAddress = d;
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /* access modifiers changed from: private */
    public void scheduleReconnect() {
        ScheduledExecutorService scheduledExecutorService = this.reconnectExecutor;
        if (scheduledExecutorService == null || scheduledExecutorService.isShutdown()) {
            ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            this.reconnectExecutor = newSingleThreadScheduledExecutor;
            newSingleThreadScheduledExecutor.schedule(new qb(12, this), 5, TimeUnit.SECONDS);
        }
    }

    /* access modifiers changed from: private */
    public void subscribe() {
        MqttAsyncClient mqttAsyncClient = this.mqttClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                IMqttToken subscribe = this.mqttClient.subscribe(this.subscribeTopic, 0);
                subscribe.waitForCompletion(5000);
                if (!subscribe.isComplete() || subscribe.getException() != null) {
                    subscribe.getException();
                }
            } catch (MqttException unused) {
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.mqttClient;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isConnected() {
        /*
            r1 = this;
            boolean r0 = r1.isConnected
            if (r0 == 0) goto L_0x0010
            org.eclipse.paho.client.mqttv3.MqttAsyncClient r0 = r1.mqttClient
            if (r0 == 0) goto L_0x0010
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.services.ZaloMqttClient.isConnected():boolean");
    }

    public boolean publish(String str, String str2) {
        MqttAsyncClient mqttAsyncClient = this.mqttClient;
        if (mqttAsyncClient != null && mqttAsyncClient.isConnected()) {
            try {
                MqttMessage mqttMessage = new MqttMessage(str2.getBytes("UTF-8"));
                mqttMessage.setQos(0);
                mqttMessage.setRetained(false);
                IMqttDeliveryToken publish = this.mqttClient.publish(str, mqttMessage);
                publish.waitForCompletion(5000);
                if (publish.isComplete() && publish.getException() == null) {
                    return true;
                }
                publish.getException();
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public void reconnectWithNewMac() {
        if (this.isConnected) {
            stop();
            reloadMacAddress();
            start(this.brokerHost, this.brokerPort, this.macAddress, this.password);
            return;
        }
        reloadMacAddress();
    }

    public void setMessageCallback(MessageCallback messageCallback2) {
        this.messageCallback = messageCallback2;
    }

    public boolean start(String str, int i, String str2, String str3) {
        this.brokerHost = str;
        this.brokerPort = i;
        this.macAddress = str2;
        this.password = str3;
        String upperCase = str2.replace(":", "_").toUpperCase();
        this.subscribeTopic = y2.i("devices/p2p/", upperCase);
        String str4 = "zalo_" + upperCase;
        str3.isEmpty();
        try {
            MqttAsyncClient mqttAsyncClient = new MqttAsyncClient("tcp://" + str + ":" + i, str4, new MemoryPersistence());
            this.mqttClient = mqttAsyncClient;
            mqttAsyncClient.setCallback(new MqttCallbackExtended() {
                public void connectComplete(boolean z, String str) {
                    ZaloMqttClient.this.isConnected = true;
                    ZaloMqttClient.this.subscribe();
                }

                public void connectionLost(Throwable th) {
                    ZaloMqttClient.this.isConnected = false;
                    ZaloMqttClient.this.scheduleReconnect();
                }

                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                }

                public void messageArrived(String str, MqttMessage mqttMessage) throws Exception {
                    String str2 = new String(mqttMessage.getPayload(), "UTF-8");
                    if (ZaloMqttClient.this.messageCallback != null) {
                        ZaloMqttClient.this.messageCallback.onMessage(str, str2);
                    }
                }
            });
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setUserName(upperCase);
            mqttConnectOptions.setPassword(str3.toCharArray());
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setCleanSession(true);
            mqttConnectOptions.setConnectionTimeout(10);
            mqttConnectOptions.setKeepAliveInterval(60);
            IMqttToken connect = this.mqttClient.connect(mqttConnectOptions);
            connect.waitForCompletion(10000);
            if (connect.isComplete() && connect.getException() == null) {
                return true;
            }
            connect.getException();
            return false;
        } catch (MqttException unused) {
        }
    }

    public void stop() {
        ScheduledExecutorService scheduledExecutorService = this.reconnectExecutor;
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            this.reconnectExecutor = null;
        }
        MqttAsyncClient mqttAsyncClient = this.mqttClient;
        if (mqttAsyncClient != null) {
            try {
                if (mqttAsyncClient.isConnected()) {
                    this.mqttClient.disconnect();
                }
                this.mqttClient.close();
            } catch (MqttException unused) {
            }
            this.mqttClient = null;
        }
        this.isConnected = false;
    }
}
