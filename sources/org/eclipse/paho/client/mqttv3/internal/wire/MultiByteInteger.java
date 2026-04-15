package org.eclipse.paho.client.mqttv3.internal.wire;

public class MultiByteInteger {
    private int length;
    private int value;

    public MultiByteInteger(int i) {
        this(i, -1);
    }

    public int getEncodedLength() {
        return this.length;
    }

    public int getValue() {
        return this.value;
    }

    public MultiByteInteger(int i, int i2) {
        this.value = i;
        this.length = i2;
    }
}
