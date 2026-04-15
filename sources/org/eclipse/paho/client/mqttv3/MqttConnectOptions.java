package org.eclipse.paho.client.mqttv3;

import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import org.eclipse.paho.client.mqttv3.internal.NetworkModuleService;
import org.eclipse.paho.client.mqttv3.util.Debug;

public class MqttConnectOptions {
    public static final boolean CLEAN_SESSION_DEFAULT = true;
    public static final int CONNECTION_TIMEOUT_DEFAULT = 30;
    public static final int KEEP_ALIVE_INTERVAL_DEFAULT = 60;
    public static final int MAX_INFLIGHT_DEFAULT = 10;
    public static final int MQTT_VERSION_3_1 = 3;
    public static final int MQTT_VERSION_3_1_1 = 4;
    public static final int MQTT_VERSION_DEFAULT = 0;
    private boolean automaticReconnect = false;
    private boolean cleanSession = true;
    private int connectionTimeout = 30;
    private Properties customWebSocketHeaders = null;
    private int executorServiceTimeout = 1;
    private boolean httpsHostnameVerificationEnabled = true;
    private int keepAliveInterval = 60;
    private int maxInflight = 10;
    private int maxReconnectDelay = 128000;
    private int mqttVersion = 0;
    private char[] password;
    private String[] serverURIs = null;
    private SocketFactory socketFactory;
    private Properties sslClientProps = null;
    private HostnameVerifier sslHostnameVerifier = null;
    private String userName;
    private String willDestination = null;
    private MqttMessage willMessage = null;

    private void validateWill(String str, Object obj) {
        if (str == null || obj == null) {
            throw new IllegalArgumentException();
        }
        MqttTopic.validate(str, false);
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public Properties getCustomWebSocketHeaders() {
        return this.customWebSocketHeaders;
    }

    public Properties getDebug() {
        String str;
        String str2;
        Properties properties = new Properties();
        properties.put("MqttVersion", Integer.valueOf(getMqttVersion()));
        properties.put("CleanSession", Boolean.valueOf(isCleanSession()));
        properties.put("ConTimeout", Integer.valueOf(getConnectionTimeout()));
        properties.put("KeepAliveInterval", Integer.valueOf(getKeepAliveInterval()));
        if (getUserName() == null) {
            str = "null";
        } else {
            str = getUserName();
        }
        properties.put("UserName", str);
        if (getWillDestination() == null) {
            str2 = "null";
        } else {
            str2 = getWillDestination();
        }
        properties.put("WillDestination", str2);
        if (getSocketFactory() == null) {
            properties.put("SocketFactory", "null");
        } else {
            properties.put("SocketFactory", getSocketFactory());
        }
        if (getSSLProperties() == null) {
            properties.put("SSLProperties", "null");
        } else {
            properties.put("SSLProperties", getSSLProperties());
        }
        return properties;
    }

    public int getExecutorServiceTimeout() {
        return this.executorServiceTimeout;
    }

    public int getKeepAliveInterval() {
        return this.keepAliveInterval;
    }

    public int getMaxInflight() {
        return this.maxInflight;
    }

    public int getMaxReconnectDelay() {
        return this.maxReconnectDelay;
    }

    public int getMqttVersion() {
        return this.mqttVersion;
    }

    public char[] getPassword() {
        return this.password;
    }

    public HostnameVerifier getSSLHostnameVerifier() {
        return this.sslHostnameVerifier;
    }

    public Properties getSSLProperties() {
        return this.sslClientProps;
    }

    public String[] getServerURIs() {
        return this.serverURIs;
    }

    public SocketFactory getSocketFactory() {
        return this.socketFactory;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getWillDestination() {
        return this.willDestination;
    }

    public MqttMessage getWillMessage() {
        return this.willMessage;
    }

    public boolean isAutomaticReconnect() {
        return this.automaticReconnect;
    }

    public boolean isCleanSession() {
        return this.cleanSession;
    }

    public boolean isHttpsHostnameVerificationEnabled() {
        return this.httpsHostnameVerificationEnabled;
    }

    public void setAutomaticReconnect(boolean z) {
        this.automaticReconnect = z;
    }

    public void setCleanSession(boolean z) {
        this.cleanSession = z;
    }

    public void setConnectionTimeout(int i) {
        if (i >= 0) {
            this.connectionTimeout = i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setCustomWebSocketHeaders(Properties properties) {
        this.customWebSocketHeaders = properties;
    }

    public void setExecutorServiceTimeout(int i) {
        this.executorServiceTimeout = i;
    }

    public void setHttpsHostnameVerificationEnabled(boolean z) {
        this.httpsHostnameVerificationEnabled = z;
    }

    public void setKeepAliveInterval(int i) throws IllegalArgumentException {
        if (i >= 0) {
            this.keepAliveInterval = i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setMaxInflight(int i) {
        if (i >= 0) {
            this.maxInflight = i;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setMaxReconnectDelay(int i) {
        this.maxReconnectDelay = i;
    }

    public void setMqttVersion(int i) throws IllegalArgumentException {
        if (i == 0 || i == 3 || i == 4) {
            this.mqttVersion = i;
            return;
        }
        throw new IllegalArgumentException("An incorrect version was used \"" + i + "\". Acceptable version options are 0, 3 and 4.");
    }

    public void setPassword(char[] cArr) {
        this.password = (char[]) cArr.clone();
    }

    public void setSSLHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.sslHostnameVerifier = hostnameVerifier;
    }

    public void setSSLProperties(Properties properties) {
        this.sslClientProps = properties;
    }

    public void setServerURIs(String[] strArr) {
        for (String validateURI : strArr) {
            NetworkModuleService.validateURI(validateURI);
        }
        this.serverURIs = (String[]) strArr.clone();
    }

    public void setSocketFactory(SocketFactory socketFactory2) {
        this.socketFactory = socketFactory2;
    }

    public void setUserName(String str) {
        this.userName = str;
    }

    public void setWill(MqttTopic mqttTopic, byte[] bArr, int i, boolean z) {
        String name = mqttTopic.getName();
        validateWill(name, bArr);
        setWill(name, new MqttMessage(bArr), i, z);
    }

    public String toString() {
        return Debug.dumpProperties(getDebug(), "Connection options");
    }

    public void setWill(String str, byte[] bArr, int i, boolean z) {
        validateWill(str, bArr);
        setWill(str, new MqttMessage(bArr), i, z);
    }

    public void setWill(String str, MqttMessage mqttMessage, int i, boolean z) {
        this.willDestination = str;
        this.willMessage = mqttMessage;
        mqttMessage.setQos(i);
        this.willMessage.setRetained(z);
        this.willMessage.setMutable(false);
    }
}
