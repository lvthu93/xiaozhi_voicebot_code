package org.eclipse.paho.client.mqttv3.internal.wire;

import j$.io.DesugarInputStream;
import j$.io.InputStreamRetargetInterface;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.ClientState;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class MqttInputStream extends InputStream implements InputStreamRetargetInterface {
    private final String CLASS_NAME;
    private ByteArrayOutputStream bais = new ByteArrayOutputStream();
    private ClientState clientState;
    private DataInputStream in;
    private final Logger log;
    private byte[] packet;
    private int packetLen;
    private int remLen = -1;

    public MqttInputStream(ClientState clientState2, InputStream inputStream) {
        String name = MqttInputStream.class.getName();
        this.CLASS_NAME = name;
        this.log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, name);
        this.clientState = clientState2;
        this.in = new DataInputStream(inputStream);
    }

    private void readFully() throws IOException {
        int size = this.bais.size();
        int i = this.packetLen;
        int i2 = size + i;
        int i3 = this.remLen - i;
        if (i3 >= 0) {
            int i4 = 0;
            while (i4 < i3) {
                try {
                    int read = this.in.read(this.packet, i2 + i4, i3 - i4);
                    if (read >= 0) {
                        this.clientState.notifyReceivedBytes(read);
                        i4 += read;
                    } else {
                        throw new EOFException();
                    }
                } catch (SocketTimeoutException e) {
                    this.packetLen += i4;
                    throw e;
                }
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    public int available() throws IOException {
        return this.in.available();
    }

    public void close() throws IOException {
        this.in.close();
    }

    public int read() throws IOException {
        return this.in.read();
    }

    public MqttWireMessage readMqttWireMessage() throws IOException, MqttException {
        try {
            if (this.remLen < 0) {
                this.bais.reset();
                byte readByte = this.in.readByte();
                this.clientState.notifyReceivedBytes(1);
                byte b = (byte) ((readByte >>> 4) & 15);
                if (b < 1 || b > 14) {
                    throw ExceptionHelper.createMqttException(32108);
                }
                this.remLen = MqttWireMessage.readMBI(this.in).getValue();
                this.bais.write(readByte);
                this.bais.write(MqttWireMessage.encodeMBI((long) this.remLen));
                this.packet = new byte[(this.bais.size() + this.remLen)];
                this.packetLen = 0;
            }
            if (this.remLen < 0) {
                return null;
            }
            readFully();
            this.remLen = -1;
            byte[] byteArray = this.bais.toByteArray();
            System.arraycopy(byteArray, 0, this.packet, 0, byteArray.length);
            MqttWireMessage createWireMessage = MqttWireMessage.createWireMessage(this.packet);
            this.log.fine(this.CLASS_NAME, "readMqttWireMessage", "301", new Object[]{createWireMessage});
            return createWireMessage;
        } catch (SocketTimeoutException unused) {
            return null;
        }
    }

    public final /* synthetic */ long transferTo(OutputStream outputStream) {
        return DesugarInputStream.transferTo(this, outputStream);
    }
}
