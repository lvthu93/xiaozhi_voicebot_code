package com.grack.nanojson;

import com.grack.nanojson.JsonWriterBase;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Collection;
import java.util.Map;

class JsonWriterBase<SELF extends JsonWriterBase<SELF>> implements JsonSink<SELF> {
    static final int BUFFER_SIZE = 10240;
    private static final char[] FALSE = {'f', 'a', 'l', 's', 'e'};
    private static final char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] NULL = {'n', 'u', 'l', 'l'};
    private static final char[] TRUE = {'t', 'r', 'u', 'e'};
    private static final char[] UNICODE_LARGE = {'\\', 'u'};
    private static final char[] UNICODE_SMALL = {'\\', 'u', '0', '0'};
    protected final Appendable appendable;
    private final byte[] bb;
    private int bo;
    private final StringBuilder buffer;
    private boolean first;
    private boolean inObject;
    private int indent;
    private String indentString;
    protected final OutputStream out;
    private int stateIndex;
    private BitSet states;
    protected final boolean utf8;

    public JsonWriterBase(OutputStream outputStream, String str) {
        this.bo = 0;
        this.states = new BitSet();
        this.stateIndex = 0;
        this.first = true;
        this.indent = 0;
        this.appendable = null;
        this.out = outputStream;
        this.indentString = str;
        this.utf8 = true;
        this.buffer = null;
        this.bb = new byte[BUFFER_SIZE];
    }

    public JsonWriterBase(Appendable appendable2, String str) {
        this.bo = 0;
        this.states = new BitSet();
        this.stateIndex = 0;
        this.first = true;
        this.indent = 0;
        this.appendable = appendable2;
        this.out = null;
        this.indentString = str;
        this.utf8 = false;
        this.buffer = new StringBuilder(BUFFER_SIZE);
        this.bb = null;
    }

    private void appendIndent() {
        for (int i = 0; i < this.indent; i++) {
            raw(this.indentString);
        }
    }

    private void appendNewLine() {
        raw(10);
    }

    private SELF castThis() {
        return this;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x015d, code lost:
        if (r2 == '<') goto L_0x015f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void emitStringValue(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 34
            r9.raw((char) r0)
            r1 = 0
            r2 = 0
        L_0x0007:
            int r3 = r10.length()
            if (r1 >= r3) goto L_0x0171
            char r3 = r10.charAt(r1)
            r4 = 12
            if (r3 == r4) goto L_0x0169
            r4 = 13
            if (r3 == r4) goto L_0x0166
            r4 = 92
            if (r3 == r0) goto L_0x015f
            r5 = 47
            if (r3 == r5) goto L_0x015b
            if (r3 == r4) goto L_0x015f
            switch(r3) {
                case 8: goto L_0x0155;
                case 9: goto L_0x0152;
                case 10: goto L_0x014f;
                default: goto L_0x0026;
            }
        L_0x0026:
            boolean r2 = r9.shouldBeEscaped(r3)
            if (r2 == 0) goto L_0x0070
            r2 = 256(0x100, float:3.59E-43)
            if (r3 >= r2) goto L_0x0045
            char[] r2 = UNICODE_SMALL
            r9.raw((char[]) r2)
            char[] r2 = HEX
            int r4 = r3 >> 4
            r4 = r4 & 15
            char r4 = r2[r4]
            r9.raw((char) r4)
            r4 = r3 & 15
            char r2 = r2[r4]
            goto L_0x006b
        L_0x0045:
            char[] r2 = UNICODE_LARGE
            r9.raw((char[]) r2)
            char[] r2 = HEX
            int r4 = r3 >> 12
            r4 = r4 & 15
            char r4 = r2[r4]
            r9.raw((char) r4)
            int r4 = r3 >> 8
            r4 = r4 & 15
            char r4 = r2[r4]
            r9.raw((char) r4)
            int r4 = r3 >> 4
            r4 = r4 & 15
            char r4 = r2[r4]
            r9.raw((char) r4)
            r4 = r3 & 15
            char r2 = r2[r4]
        L_0x006b:
            r9.raw((char) r2)
            goto L_0x016c
        L_0x0070:
            boolean r2 = r9.utf8
            if (r2 == 0) goto L_0x0162
            int r2 = r9.bo
            int r2 = r2 + 4
            r4 = 10240(0x2800, float:1.4349E-41)
            if (r2 <= r4) goto L_0x007f
            r9.flush()
        L_0x007f:
            r2 = 128(0x80, float:1.794E-43)
            if (r3 >= r2) goto L_0x0090
            byte[] r2 = r9.bb
            int r4 = r9.bo
            int r5 = r4 + 1
            r9.bo = r5
            byte r5 = (byte) r3
            r2[r4] = r5
            goto L_0x016c
        L_0x0090:
            r4 = 2048(0x800, float:2.87E-42)
            if (r3 >= r4) goto L_0x00ad
            byte[] r4 = r9.bb
            int r5 = r9.bo
            int r6 = r5 + 1
            int r7 = r3 >> 6
            r7 = r7 | 192(0xc0, float:2.69E-43)
            byte r7 = (byte) r7
            r4[r5] = r7
            int r5 = r6 + 1
            r9.bo = r5
            r5 = r3 & 63
            r2 = r2 | r5
            byte r2 = (byte) r2
            r4[r6] = r2
            goto L_0x016c
        L_0x00ad:
            r4 = 55296(0xd800, float:7.7486E-41)
            if (r3 >= r4) goto L_0x00d5
            byte[] r4 = r9.bb
            int r5 = r9.bo
            int r6 = r5 + 1
            int r7 = r3 >> 12
            r7 = r7 | 224(0xe0, float:3.14E-43)
            byte r7 = (byte) r7
            r4[r5] = r7
            int r5 = r6 + 1
            int r7 = r3 >> 6
            r7 = r7 & 63
            r7 = r7 | r2
            byte r7 = (byte) r7
            r4[r6] = r7
            int r6 = r5 + 1
            r9.bo = r6
            r6 = r3 & 63
            r2 = r2 | r6
            byte r2 = (byte) r2
            r4[r5] = r2
            goto L_0x016c
        L_0x00d5:
            r4 = 57343(0xdfff, float:8.0355E-41)
            if (r3 >= r4) goto L_0x012d
            int r1 = r1 + 1
            char r4 = r10.charAt(r1)
            int r4 = java.lang.Character.toCodePoint(r3, r4)
            r5 = 2097151(0x1fffff, float:2.938734E-39)
            if (r4 >= r5) goto L_0x0115
            byte[] r5 = r9.bb
            int r6 = r9.bo
            int r7 = r6 + 1
            int r8 = r4 >> 18
            r8 = r8 | 240(0xf0, float:3.36E-43)
            byte r8 = (byte) r8
            r5[r6] = r8
            int r6 = r7 + 1
            int r8 = r4 >> 12
            r8 = r8 & 63
            r8 = r8 | r2
            byte r8 = (byte) r8
            r5[r7] = r8
            int r7 = r6 + 1
            int r8 = r4 >> 6
            r8 = r8 & 63
            r8 = r8 | r2
            byte r8 = (byte) r8
            r5[r6] = r8
            int r6 = r7 + 1
            r9.bo = r6
            r4 = r4 & 63
            r2 = r2 | r4
            byte r2 = (byte) r2
            r5[r7] = r2
            goto L_0x016c
        L_0x0115:
            com.grack.nanojson.JsonWriterException r10 = new com.grack.nanojson.JsonWriterException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Unable to encode character 0x"
            r0.<init>(r1)
            java.lang.String r1 = java.lang.Integer.toHexString(r4)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r10.<init>((java.lang.String) r0)
            throw r10
        L_0x012d:
            byte[] r4 = r9.bb
            int r5 = r9.bo
            int r6 = r5 + 1
            int r7 = r3 >> 12
            r7 = r7 | 224(0xe0, float:3.14E-43)
            byte r7 = (byte) r7
            r4[r5] = r7
            int r5 = r6 + 1
            int r7 = r3 >> 6
            r7 = r7 & 63
            r7 = r7 | r2
            byte r7 = (byte) r7
            r4[r6] = r7
            int r6 = r5 + 1
            r9.bo = r6
            r6 = r3 & 63
            r2 = r2 | r6
            byte r2 = (byte) r2
            r4[r5] = r2
            goto L_0x016c
        L_0x014f:
            java.lang.String r2 = "\\n"
            goto L_0x0157
        L_0x0152:
            java.lang.String r2 = "\\t"
            goto L_0x0157
        L_0x0155:
            java.lang.String r2 = "\\b"
        L_0x0157:
            r9.raw((java.lang.String) r2)
            goto L_0x016c
        L_0x015b:
            r5 = 60
            if (r2 != r5) goto L_0x0162
        L_0x015f:
            r9.raw((char) r4)
        L_0x0162:
            r9.raw((char) r3)
            goto L_0x016c
        L_0x0166:
            java.lang.String r2 = "\\r"
            goto L_0x0157
        L_0x0169:
            java.lang.String r2 = "\\f"
            goto L_0x0157
        L_0x016c:
            int r1 = r1 + 1
            r2 = r3
            goto L_0x0007
        L_0x0171:
            r9.raw((char) r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.grack.nanojson.JsonWriterBase.emitStringValue(java.lang.String):void");
    }

    private void flush() {
        try {
            if (this.utf8) {
                this.out.write(this.bb, 0, this.bo);
                this.bo = 0;
                return;
            }
            this.appendable.append(this.buffer.toString());
            this.buffer.setLength(0);
        } catch (IOException e) {
            throw new JsonWriterException((Throwable) e);
        }
    }

    private void pre() {
        if (this.first) {
            this.first = false;
        } else if (this.stateIndex != 0) {
            raw(',');
            if (this.indentString != null && this.inObject) {
                appendNewLine();
            }
        } else {
            throw new JsonWriterException("Invalid call to emit a value in a finished JSON writer");
        }
    }

    private void preValue() {
        if (!this.inObject) {
            pre();
            return;
        }
        throw new JsonWriterException("Invalid call to emit a keyless value while writing an object");
    }

    private void preValue(String str) {
        if (this.inObject) {
            pre();
            if (this.indentString != null) {
                appendIndent();
            }
            emitStringValue(str);
            raw(':');
            return;
        }
        throw new JsonWriterException("Invalid call to emit a key value while not writing an object");
    }

    private void raw(char c) {
        if (this.utf8) {
            if (this.bo + 1 > BUFFER_SIZE) {
                flush();
            }
            byte[] bArr = this.bb;
            int i = this.bo;
            this.bo = i + 1;
            bArr[i] = (byte) c;
            return;
        }
        this.buffer.append(c);
        if (this.buffer.length() > BUFFER_SIZE) {
            flush();
        }
    }

    private void raw(String str) {
        if (this.utf8) {
            int length = str.length();
            if (this.bo + length > BUFFER_SIZE) {
                flush();
            }
            for (int i = 0; i < length; i++) {
                byte[] bArr = this.bb;
                int i2 = this.bo;
                this.bo = i2 + 1;
                bArr[i2] = (byte) str.charAt(i);
            }
            return;
        }
        this.buffer.append(str);
        if (this.buffer.length() > BUFFER_SIZE) {
            flush();
        }
    }

    private void raw(char[] cArr) {
        if (this.utf8) {
            if (this.bo + r0 > BUFFER_SIZE) {
                flush();
            }
            for (char c : cArr) {
                byte[] bArr = this.bb;
                int i = this.bo;
                this.bo = i + 1;
                bArr[i] = (byte) c;
            }
            return;
        }
        this.buffer.append(cArr);
        if (this.buffer.length() > BUFFER_SIZE) {
            flush();
        }
    }

    private boolean shouldBeEscaped(char c) {
        return c < ' ' || (c >= 128 && c < 160) || (c >= 8192 && c < 8448);
    }

    public SELF array() {
        preValue();
        BitSet bitSet = this.states;
        int i = this.stateIndex;
        this.stateIndex = i + 1;
        bitSet.set(i, this.inObject);
        this.inObject = false;
        this.first = true;
        raw('[');
        return castThis();
    }

    public SELF array(String str) {
        preValue(str);
        BitSet bitSet = this.states;
        int i = this.stateIndex;
        this.stateIndex = i + 1;
        bitSet.set(i, this.inObject);
        this.inObject = false;
        this.first = true;
        raw('[');
        return castThis();
    }

    public SELF array(String str, Collection<?> collection) {
        if (str == null) {
            array();
        } else {
            array(str);
        }
        for (Object value : collection) {
            value((Object) value);
        }
        return end();
    }

    public SELF array(Collection<?> collection) {
        return array((String) null, (Collection) collection);
    }

    public void doneInternal() {
        if (this.stateIndex > 0) {
            throw new JsonWriterException("Unclosed JSON objects and/or arrays when closing writer");
        } else if (!this.first) {
            flush();
        } else {
            throw new JsonWriterException("Nothing was written to the JSON writer");
        }
    }

    public SELF end() {
        char c;
        if (this.stateIndex != 0) {
            if (this.inObject) {
                if (this.indentString != null) {
                    this.indent--;
                    appendNewLine();
                    appendIndent();
                }
                c = '}';
            } else {
                c = ']';
            }
            raw(c);
            this.first = false;
            BitSet bitSet = this.states;
            int i = this.stateIndex - 1;
            this.stateIndex = i;
            this.inObject = bitSet.get(i);
            return castThis();
        }
        throw new JsonWriterException("Invalid call to end()");
    }

    public SELF nul() {
        preValue();
        raw(NULL);
        return castThis();
    }

    public SELF nul(String str) {
        preValue(str);
        raw(NULL);
        return castThis();
    }

    public SELF object() {
        preValue();
        BitSet bitSet = this.states;
        int i = this.stateIndex;
        this.stateIndex = i + 1;
        bitSet.set(i, this.inObject);
        this.inObject = true;
        this.first = true;
        raw('{');
        if (this.indentString != null) {
            this.indent++;
            appendNewLine();
        }
        return castThis();
    }

    public SELF object(String str) {
        preValue(str);
        BitSet bitSet = this.states;
        int i = this.stateIndex;
        this.stateIndex = i + 1;
        bitSet.set(i, this.inObject);
        this.inObject = true;
        this.first = true;
        raw('{');
        if (this.indentString != null) {
            this.indent++;
            appendNewLine();
        }
        return castThis();
    }

    public SELF object(String str, Map<?, ?> map) {
        if (str == null) {
            object();
        } else {
            object(str);
        }
        for (Map.Entry next : map.entrySet()) {
            Object value = next.getValue();
            if (!(next.getKey() instanceof String)) {
                StringBuilder sb = new StringBuilder("Invalid key type for map: ");
                sb.append(next.getKey() == null ? "null" : next.getKey().getClass());
                throw new JsonWriterException(sb.toString());
            }
            value((String) next.getKey(), value);
        }
        return end();
    }

    public SELF object(Map<?, ?> map) {
        return object((String) null, (Map) map);
    }

    public SELF value(double d) {
        preValue();
        raw(Double.toString(d));
        return castThis();
    }

    public SELF value(float f) {
        preValue();
        raw(Float.toString(f));
        return castThis();
    }

    public SELF value(int i) {
        preValue();
        raw(Integer.toString(i));
        return castThis();
    }

    public SELF value(long j) {
        preValue();
        raw(Long.toString(j));
        return castThis();
    }

    public SELF value(Number number) {
        preValue();
        if (number == null) {
            raw(NULL);
        } else {
            raw(number.toString());
        }
        return castThis();
    }

    public SELF value(Object obj) {
        if (obj == null) {
            return nul();
        }
        if (obj instanceof String) {
            return value((String) obj);
        }
        if (obj instanceof Number) {
            return value((Number) obj);
        }
        if (obj instanceof Boolean) {
            return value(((Boolean) obj).booleanValue());
        }
        if (obj instanceof Collection) {
            return array((Collection) obj);
        }
        if (obj instanceof Map) {
            return object((Map) obj);
        }
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            array();
            for (int i = 0; i < length; i++) {
                value(Array.get(obj, i));
            }
            return end();
        }
        throw new JsonWriterException("Unable to handle type: " + obj.getClass());
    }

    public SELF value(String str) {
        if (str == null) {
            return nul();
        }
        preValue();
        emitStringValue(str);
        return castThis();
    }

    public SELF value(String str, double d) {
        preValue(str);
        raw(Double.toString(d));
        return castThis();
    }

    public SELF value(String str, float f) {
        preValue(str);
        raw(Float.toString(f));
        return castThis();
    }

    public SELF value(String str, int i) {
        preValue(str);
        raw(Integer.toString(i));
        return castThis();
    }

    public SELF value(String str, long j) {
        preValue(str);
        raw(Long.toString(j));
        return castThis();
    }

    public SELF value(String str, Number number) {
        if (number == null) {
            return nul(str);
        }
        preValue(str);
        raw(number.toString());
        return castThis();
    }

    public SELF value(String str, Object obj) {
        if (obj == null) {
            return nul(str);
        }
        if (obj instanceof String) {
            return value(str, (String) obj);
        }
        if (obj instanceof Number) {
            return value(str, (Number) obj);
        }
        if (obj instanceof Boolean) {
            return value(str, ((Boolean) obj).booleanValue());
        }
        if (obj instanceof Collection) {
            return array(str, (Collection) obj);
        }
        if (obj instanceof Map) {
            return object(str, (Map) obj);
        }
        if (obj.getClass().isArray()) {
            int length = Array.getLength(obj);
            array(str);
            for (int i = 0; i < length; i++) {
                value(Array.get(obj, i));
            }
            return end();
        }
        throw new JsonWriterException("Unable to handle type: " + obj.getClass());
    }

    public SELF value(String str, String str2) {
        if (str2 == null) {
            return nul(str);
        }
        preValue(str);
        emitStringValue(str2);
        return castThis();
    }

    public SELF value(String str, boolean z) {
        preValue(str);
        raw(z ? TRUE : FALSE);
        return castThis();
    }

    public SELF value(boolean z) {
        preValue();
        raw(z ? TRUE : FALSE);
        return castThis();
    }
}
