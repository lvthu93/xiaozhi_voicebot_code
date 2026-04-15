package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.SocketTimeoutException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class WebSocketReceiver implements Runnable {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketReceiver";
    private InputStream input;
    private final Object lifecycle = new Object();
    private Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private PipedOutputStream pipedOutputStream;
    private Thread receiverThread = null;
    private volatile boolean receiving;
    private boolean running = false;
    private boolean stopping = false;

    public WebSocketReceiver(InputStream inputStream, PipedInputStream pipedInputStream) throws IOException {
        this.input = inputStream;
        PipedOutputStream pipedOutputStream2 = new PipedOutputStream();
        this.pipedOutputStream = pipedOutputStream2;
        pipedInputStream.connect(pipedOutputStream2);
    }

    private void closeOutputStream() {
        try {
            this.pipedOutputStream.close();
        } catch (IOException unused) {
        }
    }

    public boolean isReceiving() {
        return this.receiving;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void run() {
        boolean z;
        while (this.running && this.input != null) {
            try {
                this.log.fine(CLASS_NAME, "run", "852");
                if (this.input.available() > 0) {
                    z = true;
                } else {
                    z = false;
                }
                this.receiving = z;
                WebSocketFrame webSocketFrame = new WebSocketFrame(this.input);
                if (!webSocketFrame.isCloseFlag()) {
                    for (byte write : webSocketFrame.getPayload()) {
                        this.pipedOutputStream.write(write);
                    }
                    this.pipedOutputStream.flush();
                } else if (!this.stopping) {
                    throw new IOException("Server sent a WebSocket Frame with the Stop OpCode");
                }
                this.receiving = false;
            } catch (SocketTimeoutException unused) {
            } catch (IOException unused2) {
                stop();
            }
        }
    }

    public void start(String str) {
        this.log.fine(CLASS_NAME, "start", "855");
        synchronized (this.lifecycle) {
            if (!this.running) {
                this.running = true;
                Thread thread = new Thread(this, str);
                this.receiverThread = thread;
                thread.start();
            }
        }
    }

    public void stop() {
        Thread thread;
        boolean z = true;
        this.stopping = true;
        synchronized (this.lifecycle) {
            this.log.fine(CLASS_NAME, "stop", "850");
            if (this.running) {
                this.running = false;
                this.receiving = false;
                closeOutputStream();
            } else {
                z = false;
            }
        }
        if (z && !Thread.currentThread().equals(this.receiverThread) && (thread = this.receiverThread) != null) {
            try {
                thread.join();
            } catch (InterruptedException unused) {
            }
        }
        this.receiverThread = null;
        this.log.fine(CLASS_NAME, "stop", "851");
    }
}
