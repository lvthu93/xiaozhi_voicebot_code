package org.eclipse.paho.client.mqttv3.internal;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.spi.NetworkModuleFactory;

public class TCPNetworkModuleFactory implements NetworkModuleFactory {
    public NetworkModule createNetworkModule(URI uri, MqttConnectOptions mqttConnectOptions, String str) throws MqttException {
        String host = uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            port = 1883;
        }
        String path = uri.getPath();
        if (path == null || path.isEmpty()) {
            SocketFactory socketFactory = mqttConnectOptions.getSocketFactory();
            if (socketFactory == null) {
                socketFactory = SocketFactory.getDefault();
            } else if (socketFactory instanceof SSLSocketFactory) {
                throw ExceptionHelper.createMqttException(32105);
            }
            TCPNetworkModule tCPNetworkModule = new TCPNetworkModule(socketFactory, host, port, str);
            tCPNetworkModule.setConnectTimeout(mqttConnectOptions.getConnectionTimeout());
            return tCPNetworkModule;
        }
        throw new IllegalArgumentException(uri.toString());
    }

    public Set<String> getSupportedUriSchemes() {
        return Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"tcp"})));
    }

    public void validateURI(URI uri) throws IllegalArgumentException {
        String path = uri.getPath();
        if (path != null && !path.isEmpty()) {
            throw new IllegalArgumentException("URI path must be empty \"" + uri.toString() + "\"");
        }
    }
}
