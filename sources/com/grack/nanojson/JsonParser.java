package com.grack.nanojson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.net.URL;

public final class JsonParser {
    private boolean lazyNumbers;
    private int token;
    private JsonTokener tokener;
    private Object value;

    public static final class JsonParserContext<T> {
        private final Class<T> clazz;
        private boolean lazyNumbers;

        public JsonParserContext(Class<T> cls) {
            this.clazz = cls;
        }

        public T from(InputStream inputStream) throws JsonParserException {
            return new JsonParser(new JsonTokener(inputStream), this.lazyNumbers).parse(this.clazz);
        }

        public T from(Reader reader) throws JsonParserException {
            return new JsonParser(new JsonTokener(reader), this.lazyNumbers).parse(this.clazz);
        }

        public T from(String str) throws JsonParserException {
            return new JsonParser(new JsonTokener((Reader) new StringReader(str)), this.lazyNumbers).parse(this.clazz);
        }

        public T from(URL url) throws JsonParserException {
            InputStream openStream;
            try {
                openStream = url.openStream();
                T from = from(openStream);
                openStream.close();
                return from;
            } catch (IOException e) {
                throw new JsonParserException(e, "IOException opening URL", 1, 1, 0);
            } catch (Throwable th) {
                openStream.close();
                throw th;
            }
        }

        public JsonParserContext<T> withLazyNumbers() {
            this.lazyNumbers = true;
            return this;
        }
    }

    public JsonParser(JsonTokener jsonTokener, boolean z) throws JsonParserException {
        this.tokener = jsonTokener;
        this.lazyNumbers = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0071, code lost:
        r8.token = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0073, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x015e, code lost:
        r8.value = r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0165, code lost:
        return r8.token;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int advanceToken(boolean r9, boolean r10) throws com.grack.nanojson.JsonParserException {
        /*
            r8 = this;
            r0 = 1
            if (r10 == 0) goto L_0x000a
            com.grack.nanojson.JsonTokener r10 = r8.tokener
            int r1 = r10.index
            int r1 = r1 - r0
            r10.index = r1
        L_0x000a:
            com.grack.nanojson.JsonTokener r10 = r8.tokener
            int r9 = r10.advanceToToken(r9)
            r8.token = r9
            r10 = 12
            r1 = 0
            r2 = 0
            switch(r9) {
                case 5: goto L_0x0161;
                case 6: goto L_0x015c;
                case 7: goto L_0x0159;
                case 8: goto L_0x0150;
                case 9: goto L_0x0135;
                case 10: goto L_0x0074;
                case 11: goto L_0x001b;
                case 12: goto L_0x0150;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x0163
        L_0x001b:
            com.grack.nanojson.JsonArray r9 = new com.grack.nanojson.JsonArray
            r9.<init>()
            int r3 = r8.advanceToken(r2, r2)
            r4 = 4
            if (r3 == r4) goto L_0x006d
        L_0x0027:
            java.lang.Object r3 = r8.currentValue()
            r9.add(r3)
            int r3 = r8.token
            if (r3 == r10) goto L_0x0064
            int r3 = r8.advanceToken(r2, r2)
            if (r3 != r4) goto L_0x0039
            goto L_0x006d
        L_0x0039:
            int r3 = r8.token
            if (r3 != r0) goto L_0x004d
            int r3 = r8.advanceToken(r2, r2)
            if (r3 == r4) goto L_0x0044
            goto L_0x0027
        L_0x0044:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.String r10 = "Trailing comma found in array"
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x004d:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r2 = "Expected a comma or end of the array instead of "
            r10.<init>(r2)
            int r2 = r8.token
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x0064:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.String r10 = "Semi-string is not allowed in array"
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x006d:
            r8.value = r9
            r9 = 11
        L_0x0071:
            r8.token = r9
            return r9
        L_0x0074:
            com.grack.nanojson.JsonObject r9 = new com.grack.nanojson.JsonObject
            r9.<init>()
            int r3 = r8.advanceToken(r0, r2)
            r4 = 3
            if (r3 == r4) goto L_0x012f
        L_0x0080:
            int r3 = r8.token
            r5 = 5
            if (r3 == r5) goto L_0x00a9
            r5 = 6
            if (r3 == r5) goto L_0x00a9
            r5 = 7
            if (r3 == r5) goto L_0x00a9
            r5 = 8
            if (r3 == r5) goto L_0x00b1
            if (r3 != r10) goto L_0x0092
            goto L_0x00b1
        L_0x0092:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r2 = "Expected STRING, got "
            r10.<init>(r2)
            int r2 = r8.token
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x00a9:
            java.lang.Object r3 = r8.value
            java.lang.String r3 = r3.toString()
            r8.value = r3
        L_0x00b1:
            java.lang.Object r3 = r8.value
            java.lang.String r3 = (java.lang.String) r3
            int r5 = r8.token
            java.lang.String r6 = "Expected COLON, got "
            r7 = 2
            if (r5 != r10) goto L_0x00d8
            int r5 = r8.advanceToken(r2, r0)
            if (r5 != r7) goto L_0x00c3
            goto L_0x00de
        L_0x00c3:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r6)
            int r2 = r8.token
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x00d8:
            int r5 = r8.advanceToken(r2, r2)
            if (r5 != r7) goto L_0x011a
        L_0x00de:
            r8.advanceToken(r2, r2)
            java.lang.Object r5 = r8.currentValue()
            r9.put(r3, r5)
            int r3 = r8.advanceToken(r2, r2)
            if (r3 != r4) goto L_0x00ef
            goto L_0x012f
        L_0x00ef:
            int r3 = r8.token
            if (r3 != r0) goto L_0x0103
            int r3 = r8.advanceToken(r0, r2)     // Catch:{ all -> 0x0166 }
            if (r3 == r4) goto L_0x00fa
            goto L_0x0080
        L_0x00fa:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.String r10 = "Trailing object found in array"
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x0103:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r2 = "Expected a comma or end of the object instead of "
            r10.<init>(r2)
            int r2 = r8.token
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x011a:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>(r6)
            int r2 = r8.token
            r10.append(r2)
            java.lang.String r10 = r10.toString()
            com.grack.nanojson.JsonParserException r9 = r9.createParseException(r1, r10, r0)
            throw r9
        L_0x012f:
            r8.value = r9
            r9 = 10
            goto L_0x0071
        L_0x0135:
            boolean r9 = r8.lazyNumbers
            if (r9 == 0) goto L_0x014b
            com.grack.nanojson.JsonLazyNumber r9 = new com.grack.nanojson.JsonLazyNumber
            com.grack.nanojson.JsonTokener r10 = r8.tokener
            java.lang.StringBuilder r10 = r10.reusableBuffer
            java.lang.String r10 = r10.toString()
            com.grack.nanojson.JsonTokener r0 = r8.tokener
            boolean r0 = r0.isDouble
            r9.<init>(r10, r0)
            goto L_0x015e
        L_0x014b:
            java.lang.Number r9 = r8.parseNumber()
            goto L_0x015e
        L_0x0150:
            com.grack.nanojson.JsonTokener r9 = r8.tokener
            java.lang.StringBuilder r9 = r9.reusableBuffer
            java.lang.String r9 = r9.toString()
            goto L_0x015e
        L_0x0159:
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            goto L_0x015e
        L_0x015c:
            java.lang.Boolean r9 = java.lang.Boolean.TRUE
        L_0x015e:
            r8.value = r9
            goto L_0x0163
        L_0x0161:
            r8.value = r1
        L_0x0163:
            int r9 = r8.token
            return r9
        L_0x0166:
            r9 = move-exception
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.grack.nanojson.JsonParser.advanceToken(boolean, boolean):int");
    }

    public static JsonParserContext<Object> any() {
        return new JsonParserContext<>(Object.class);
    }

    public static JsonParserContext<JsonArray> array() {
        return new JsonParserContext<>(JsonArray.class);
    }

    private Object currentValue() throws JsonParserException {
        if (this.token >= 5) {
            return this.value;
        }
        JsonTokener jsonTokener = this.tokener;
        throw jsonTokener.createParseException((Exception) null, "Expected JSON value, got " + this.token, true);
    }

    public static JsonParserContext<JsonObject> object() {
        return new JsonParserContext<>(JsonObject.class);
    }

    private Number parseNumber() throws JsonParserException {
        int i;
        int i2;
        int i3;
        String sb = this.tokener.reusableBuffer.toString();
        try {
            if (this.tokener.isDouble) {
                return Double.valueOf(Double.parseDouble(sb));
            }
            if (sb.length() == 1) {
                return Integer.valueOf(sb.charAt(0) - '0');
            }
            if (sb.length() == 2 && sb.charAt(0) == '-') {
                return Integer.valueOf('0' - sb.charAt(1));
            }
            if (sb.charAt(0) == '-') {
                i = 1;
            } else {
                i = 0;
            }
            if (i != 0) {
                i2 = sb.length() - 1;
            } else {
                i2 = sb.length();
            }
            if (i2 >= 10) {
                if (i2 == 10) {
                    if (i != 0) {
                        i3 = 1;
                    } else {
                        i3 = 0;
                    }
                    if (sb.charAt(i3) < '2') {
                    }
                }
                if (i2 >= 19) {
                    if (i2 != 19 || sb.charAt(i) >= '9') {
                        return new BigInteger(sb);
                    }
                }
                return Long.valueOf(Long.parseLong(sb));
            }
            return Integer.valueOf(Integer.parseInt(sb));
        } catch (NumberFormatException e) {
            throw this.tokener.createParseException(e, y2.i("Malformed number: ", sb), true);
        }
    }

    public <T> T parse(Class<T> cls) throws JsonParserException {
        advanceToken(false, false);
        Object currentValue = currentValue();
        if (advanceToken(false, false) != 0) {
            JsonTokener jsonTokener = this.tokener;
            throw jsonTokener.createParseException((Exception) null, "Expected end of input, got " + this.token, true);
        } else if (cls == Object.class || (currentValue != null && cls.isAssignableFrom(currentValue.getClass()))) {
            return cls.cast(currentValue);
        } else {
            JsonTokener jsonTokener2 = this.tokener;
            throw jsonTokener2.createParseException((Exception) null, "JSON did not contain the correct type, expected " + cls.getSimpleName() + ".", true);
        }
    }
}
