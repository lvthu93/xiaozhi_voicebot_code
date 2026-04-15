package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;
import org.eclipse.paho.client.mqttv3.spi.NetworkModuleFactory;
import org.java_websocket.WebSocketImpl;

public class WebSocketSecureNetworkModuleFactory implements NetworkModuleFactory {
    public NetworkModule createNetworkModule(URI uri, MqttConnectOptions mqttConnectOptions, String str) throws MqttException {
        int i;
        SSLSocketFactoryFactory sSLSocketFactoryFactory;
        String[] enabledCipherSuites;
        String host = uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            i = WebSocketImpl.DEFAULT_WSS_PORT;
        } else {
            i = port;
        }
        SocketFactory socketFactory = mqttConnectOptions.getSocketFactory();
        if (socketFactory == null) {
            SSLSocketFactoryFactory sSLSocketFactoryFactory2 = new SSLSocketFactoryFactory();
            Properties sSLProperties = mqttConnectOptions.getSSLProperties();
            if (sSLProperties != null) {
                sSLSocketFactoryFactory2.initialize(sSLProperties, (String) null);
            }
            sSLSocketFactoryFactory = sSLSocketFactoryFactory2;
            socketFactory = sSLSocketFactoryFactory2.createSocketFactory((String) null);
        } else if (socketFactory instanceof SSLSocketFactory) {
            sSLSocketFactoryFactory = null;
        } else {
            throw ExceptionHelper.createMqttException(32105);
        }
        WebSocketSecureNetworkModule webSocketSecureNetworkModule = new WebSocketSecureNetworkModule((SSLSocketFactory) socketFactory, uri.toString(), host, i, str, mqttConnectOptions.getCustomWebSocketHeaders());
        webSocketSecureNetworkModule.setSSLhandshakeTimeout(mqttConnectOptions.getConnectionTimeout());
        webSocketSecureNetworkModule.setSSLHostnameVerifier(mqttConnectOptions.getSSLHostnameVerifier());
        webSocketSecureNetworkModule.setHttpsHostnameVerificationEnabled(mqttConnectOptions.isHttpsHostnameVerificationEnabled());
        if (!(sSLSocketFactoryFactory == null || (enabledCipherSuites = sSLSocketFactoryFactory.getEnabledCipherSuites((String) null)) == null)) {
            webSocketSecureNetworkModule.setEnabledCiphers(enabledCipherSuites);
        }
        return webSocketSecureNetworkModule;
    }

    public Set<String> getSupportedUriSchemes() {
        return Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"wss"})));
    }

    public void validateURI(URI uri) throws IllegalArgumentException {
    }
}
