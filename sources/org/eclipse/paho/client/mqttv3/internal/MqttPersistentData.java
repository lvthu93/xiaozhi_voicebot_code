package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttPersistable;

public class MqttPersistentData implements MqttPersistable {
    private int hLength = 0;
    private int hOffset = 0;
    private byte[] header = null;
    private String key;
    private int pLength = 0;
    private int pOffset = 0;
    private byte[] payload = null;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: byte[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MqttPersistentData(java.lang.String r3, byte[] r4, int r5, int r6, byte[] r7, int r8, int r9) {
        /*
            r2 = this;
            r2.<init>()
            r0 = 0
            r2.header = r0
            r1 = 0
            r2.hOffset = r1
            r2.hLength = r1
            r2.payload = r0
            r2.pOffset = r1
            r2.pLength = r1
            r2.key = r3
            java.lang.Object r3 = r4.clone()
            byte[] r3 = (byte[]) r3
            r2.header = r3
            r2.hOffset = r5
            r2.hLength = r6
            if (r7 == 0) goto L_0x0028
            java.lang.Object r3 = r7.clone()
            r0 = r3
            byte[] r0 = (byte[]) r0
        L_0x0028:
            r2.payload = r0
            r2.pOffset = r8
            r2.pLength = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.MqttPersistentData.<init>(java.lang.String, byte[], int, int, byte[], int, int):void");
    }

    public byte[] getHeaderBytes() {
        return this.header;
    }

    public int getHeaderLength() {
        return this.hLength;
    }

    public int getHeaderOffset() {
        return this.hOffset;
    }

    public String getKey() {
        return this.key;
    }

    public byte[] getPayloadBytes() {
        return this.payload;
    }

    public int getPayloadLength() {
        if (this.payload == null) {
            return 0;
        }
        return this.pLength;
    }

    public int getPayloadOffset() {
        return this.pOffset;
    }
}
