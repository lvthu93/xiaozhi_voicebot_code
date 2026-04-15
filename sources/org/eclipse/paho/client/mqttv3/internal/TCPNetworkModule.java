package org.eclipse.paho.client.mqttv3.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class TCPNetworkModule implements NetworkModule {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule";
    private int conTimeout;
    private SocketFactory factory;
    private String host;
    private Logger log;
    private int port;
    protected Socket socket;

    public TCPNetworkModule(SocketFactory socketFactory, String str, int i, String str2) {
        Logger logger = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
        this.log = logger;
        logger.setResourceName(str2);
        this.factory = socketFactory;
        this.host = str;
        this.port = i;
    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public String getServerURI() {
        return "tcp://" + this.host + ":" + this.port;
    }

    public void setConnectTimeout(int i) {
        this.conTimeout = i;
    }

    public void start() throws IOException, MqttException {
        try {
            this.log.fine(CLASS_NAME, "start", "252", new Object[]{this.host, Integer.valueOf(this.port), Long.valueOf((long) (this.conTimeout * 1000))});
            InetSocketAddress inetSocketAddress = new InetSocketAddress(this.host, this.port);
            Socket createSocket = this.factory.createSocket();
            this.socket = createSocket;
            createSocket.connect(inetSocketAddress, this.conTimeout * 1000);
            this.socket.setSoTimeout(1000);
        } catch (ConnectException e) {
            this.log.fine(CLASS_NAME, "start", "250", (Object[]) null, e);
            throw new MqttException(32103, e);
        }
    }

    public void stop() throws IOException {
        Socket socket2 = this.socket;
        if (socket2 != null) {
            socket2.close();
        }
    }
}
