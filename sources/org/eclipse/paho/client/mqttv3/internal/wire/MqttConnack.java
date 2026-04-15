package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConnack extends MqttAck {
    public static final String KEY = "Con";
    private int returnCode;
    private boolean sessionPresent;

    public MqttConnack(byte b, byte[] bArr) throws IOException {
        super((byte) 2);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        this.sessionPresent = (dataInputStream.readUnsignedByte() & 1) != 1 ? false : true;
        this.returnCode = dataInputStream.readUnsignedByte();
        dataInputStream.close();
    }

    public String getKey() {
        return "Con";
    }

    public int getReturnCode() {
        return this.returnCode;
    }

    public boolean getSessionPresent() {
        return this.sessionPresent;
    }

    public byte[] getVariableHeader() throws MqttException {
        return new byte[0];
    }

    public boolean isMessageIdRequired() {
        return false;
    }

    public String toString() {
        return String.valueOf(super.toString()) + " session present:" + this.sessionPresent + " return code: " + this.returnCode;
    }
}
