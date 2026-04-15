package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnect extends MqttWireMessage {
    public static final String KEY = "Con";
    private boolean cleanSession;
    private String clientId;
    private int keepAliveInterval;
    private int mqttVersion;
    private char[] password;
    private String userName;
    private String willDestination;
    private MqttMessage willMessage;

    public MqttConnect(byte b, byte[] bArr) throws IOException, MqttException {
        super((byte) 1);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        MqttWireMessage.decodeUTF8(dataInputStream);
        dataInputStream.readByte();
        dataInputStream.readByte();
        this.keepAliveInterval = dataInputStream.readUnsignedShort();
        this.clientId = MqttWireMessage.decodeUTF8(dataInputStream);
        dataInputStream.close();
    }

    public String getKey() {
        return "Con";
    }

    public byte getMessageInfo() {
        return 0;
    }

    public byte[] getPayload() throws MqttException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            MqttWireMessage.encodeUTF8(dataOutputStream, this.clientId);
            if (this.willMessage != null) {
                MqttWireMessage.encodeUTF8(dataOutputStream, this.willDestination);
                dataOutputStream.writeShort(this.willMessage.getPayload().length);
                dataOutputStream.write(this.willMessage.getPayload());
            }
            String str = this.userName;
            if (str != null) {
                MqttWireMessage.encodeUTF8(dataOutputStream, str);
                char[] cArr = this.password;
                if (cArr != null) {
                    MqttWireMessage.encodeUTF8(dataOutputStream, new String(cArr));
                }
            }
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new MqttException((Throwable) e);
        }
    }

    public byte[] getVariableHeader() throws MqttException {
        byte b;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            int i = this.mqttVersion;
            if (i == 3) {
                MqttWireMessage.encodeUTF8(dataOutputStream, "MQIsdp");
            } else if (i == 4) {
                MqttWireMessage.encodeUTF8(dataOutputStream, "MQTT");
            }
            dataOutputStream.write(this.mqttVersion);
            if (this.cleanSession) {
                b = (byte) 2;
            } else {
                b = 0;
            }
            MqttMessage mqttMessage = this.willMessage;
            if (mqttMessage != null) {
                b = (byte) (((byte) (b | 4)) | (mqttMessage.getQos() << 3));
                if (this.willMessage.isRetained()) {
                    b = (byte) (b | 32);
                }
            }
            if (this.userName != null) {
                b = (byte) (b | 128);
                if (this.password != null) {
                    b = (byte) (b | 64);
                }
            }
            dataOutputStream.write(b);
            dataOutputStream.writeShort(this.keepAliveInterval);
            dataOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new MqttException((Throwable) e);
        }
    }

    public boolean isCleanSession() {
        return this.cleanSession;
    }

    public boolean isMessageIdRequired() {
        return false;
    }

    public String toString() {
        return String.valueOf(super.toString()) + " clientId " + this.clientId + " keepAliveInterval " + this.keepAliveInterval;
    }

    public MqttConnect(String str, int i, boolean z, int i2, String str2, char[] cArr, MqttMessage mqttMessage, String str3) {
        super((byte) 1);
        this.clientId = str;
        this.cleanSession = z;
        this.keepAliveInterval = i2;
        this.userName = str2;
        if (cArr != null) {
            this.password = (char[]) cArr.clone();
        }
        this.willMessage = mqttMessage;
        this.willDestination = str3;
        this.mqttVersion = i;
    }
}
