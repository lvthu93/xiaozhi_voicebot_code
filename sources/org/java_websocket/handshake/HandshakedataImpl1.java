package org.java_websocket.handshake;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;

public class HandshakedataImpl1 implements HandshakeBuilder {
    private byte[] content;
    private TreeMap<String, String> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public byte[] getContent() {
        return this.content;
    }

    public String getFieldValue(String str) {
        String str2 = this.map.get(str);
        return str2 == null ? "" : str2;
    }

    public boolean hasFieldValue(String str) {
        return this.map.containsKey(str);
    }

    public Iterator<String> iterateHttpFields() {
        return Collections.unmodifiableSet(this.map.keySet()).iterator();
    }

    public void put(String str, String str2) {
        this.map.put(str, str2);
    }

    public void setContent(byte[] bArr) {
        this.content = bArr;
    }
}
