package com.grack.nanojson;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

final class JsonTokener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BUFFER_ROOM = 256;
    static final int BUFFER_SIZE = 32768;
    static final char[] FALSE = {'a', 'l', 's', 'e'};
    static final int MAX_ESCAPE = 5;
    static final char[] NULL = {'u', 'l', 'l'};
    static final int TOKEN_ARRAY_END = 4;
    static final int TOKEN_ARRAY_START = 11;
    static final int TOKEN_COLON = 2;
    static final int TOKEN_COMMA = 1;
    static final int TOKEN_EOF = 0;
    static final int TOKEN_FALSE = 7;
    static final int TOKEN_NULL = 5;
    static final int TOKEN_NUMBER = 9;
    static final int TOKEN_OBJECT_END = 3;
    static final int TOKEN_OBJECT_START = 10;
    static final int TOKEN_SEMI_STRING = 12;
    static final int TOKEN_STRING = 8;
    static final int TOKEN_TRUE = 6;
    static final int TOKEN_VALUE_MIN = 5;
    static final char[] TRUE = {'r', 'u', 'e'};
    private final char[] buffer = new char[32768];
    private int bufferLength;
    private int charOffset;
    private boolean eof;
    protected int index;
    protected boolean isDouble;
    private int linePos = 1;
    private final Reader reader;
    protected StringBuilder reusableBuffer = new StringBuilder();
    private int rowPos;
    private int tokenCharOffset;
    private int tokenCharPos;
    private final boolean utf8;
    private int utf8adjust;

    public static final class PseudoUtf8Reader extends Reader {
        private byte[] buf = new byte[32768];
        private final InputStream buffered;

        public PseudoUtf8Reader(InputStream inputStream) {
            this.buffered = inputStream;
        }

        public void close() throws IOException {
        }

        public int read(char[] cArr, int i, int i2) throws IOException {
            int read = this.buffered.read(this.buf, i, i2);
            for (int i3 = i; i3 < i + read; i3++) {
                cArr[i3] = (char) this.buf[i3];
            }
            return read;
        }
    }

    public JsonTokener(InputStream inputStream) throws JsonParserException {
        Charset charset;
        Charset charset2;
        if (!(inputStream instanceof BufferedInputStream) && !(inputStream instanceof ByteArrayInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        inputStream.mark(4);
        try {
            int read = inputStream.read();
            int[] iArr = {inputStream.read(), inputStream.read(), inputStream.read(), read};
            int i = iArr[0];
            if (i == 239 && iArr[1] == 187 && iArr[2] == 191) {
                inputStream.reset();
                inputStream.read();
                inputStream.read();
                inputStream.read();
                this.reader = new PseudoUtf8Reader(inputStream);
                this.utf8 = true;
                init();
                return;
            }
            if (i == 0) {
                if (iArr[1] == 0 && iArr[2] == 254 && read == 255) {
                    charset = Charset.forName("UTF-32BE");
                    this.reader = new InputStreamReader(inputStream, charset);
                    this.utf8 = false;
                    init();
                }
            }
            if (i == 255) {
                if (iArr[1] == 254 && iArr[2] == 0 && read == 0) {
                    charset = Charset.forName("UTF-32LE");
                    this.reader = new InputStreamReader(inputStream, charset);
                    this.utf8 = false;
                    init();
                }
            }
            if (i == 254 && iArr[1] == 255) {
                charset = StandardCharsets.UTF_16BE;
                inputStream.reset();
                inputStream.read();
            } else if (i == 255 && iArr[1] == 254) {
                charset = StandardCharsets.UTF_16LE;
                inputStream.reset();
                inputStream.read();
            } else {
                if (i == 0 && iArr[1] == 0 && iArr[2] == 0 && read != 0) {
                    charset2 = Charset.forName("UTF-32BE");
                } else if (i != 0 && iArr[1] == 0 && iArr[2] == 0 && read == 0) {
                    charset2 = Charset.forName("UTF-32LE");
                } else if (i == 0 && iArr[1] != 0 && iArr[2] == 0 && read != 0) {
                    charset2 = StandardCharsets.UTF_16BE;
                } else if (i == 0 || iArr[1] != 0 || iArr[2] == 0 || read != 0) {
                    inputStream.reset();
                    this.reader = new PseudoUtf8Reader(inputStream);
                    this.utf8 = true;
                    init();
                    return;
                } else {
                    charset2 = StandardCharsets.UTF_16LE;
                }
                inputStream.reset();
                this.reader = new InputStreamReader(inputStream, charset);
                this.utf8 = false;
                init();
            }
            inputStream.read();
            this.reader = new InputStreamReader(inputStream, charset);
            this.utf8 = false;
            init();
        } catch (IOException e) {
            throw new JsonParserException(e, "IOException while detecting charset", 1, 1, 0);
        }
    }

    public JsonTokener(Reader reader2) throws JsonParserException {
        this.reader = reader2;
        this.utf8 = false;
        init();
    }

    private int advanceChar() throws JsonParserException {
        if (this.eof) {
            return -1;
        }
        char[] cArr = this.buffer;
        int i = this.index;
        char c = cArr[i];
        if (c == 10) {
            this.linePos++;
            this.rowPos = i + 1 + this.charOffset;
            this.utf8adjust = 0;
        }
        int i2 = i + 1;
        this.index = i2;
        if (i2 >= this.bufferLength) {
            this.eof = refillBuffer();
        }
        return c;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x018f A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0190  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void consumeTokenStringUtf8Char(char r9) throws com.grack.nanojson.JsonParserException {
        /*
            r8 = this;
            r0 = 5
            r8.ensureBuffer(r0)
            r1 = r9 & 240(0xf0, float:3.36E-43)
            r2 = 128(0x80, float:1.794E-43)
            r3 = 0
            r4 = 0
            if (r1 == r2) goto L_0x01ae
            r2 = 144(0x90, float:2.02E-43)
            if (r1 == r2) goto L_0x01ae
            r2 = 160(0xa0, float:2.24E-43)
            if (r1 == r2) goto L_0x01ae
            r2 = 176(0xb0, float:2.47E-43)
            if (r1 == r2) goto L_0x01ae
            r2 = 192(0xc0, float:2.69E-43)
            java.lang.String r5 = "Illegal UTF-8 byte: 0x"
            r6 = 1
            if (r1 == r2) goto L_0x0169
            r2 = 208(0xd0, float:2.91E-43)
            if (r1 == r2) goto L_0x016d
            r2 = 224(0xe0, float:3.14E-43)
            r7 = 2
            if (r1 == r2) goto L_0x0117
            r2 = 240(0xf0, float:3.36E-43)
            if (r1 == r2) goto L_0x002e
            goto L_0x0189
        L_0x002e:
            r1 = r9 & 15
            if (r1 >= r0) goto L_0x0100
            r0 = r9 & 12
            int r0 = r0 >> r7
            r1 = 3
            if (r0 == 0) goto L_0x00d3
            if (r0 == r6) goto L_0x00d3
            java.lang.String r2 = " in a Java string"
            java.lang.String r5 = "Unable to represent codepoint 0x"
            if (r0 == r7) goto L_0x0090
            if (r0 == r1) goto L_0x0044
            goto L_0x0189
        L_0x0044:
            r9 = r9 & r6
            int r9 = r9 << 30
            char[] r0 = r8.buffer
            int r1 = r8.index
            int r6 = r1 + 1
            char r1 = r0[r1]
            r1 = r1 & 63
            int r1 = r1 << 24
            r9 = r9 | r1
            int r1 = r6 + 1
            char r6 = r0[r6]
            r6 = r6 & 63
            int r6 = r6 << 18
            r9 = r9 | r6
            int r6 = r1 + 1
            char r1 = r0[r1]
            r1 = r1 & 63
            int r1 = r1 << 12
            r9 = r9 | r1
            int r1 = r6 + 1
            char r6 = r0[r6]
            r6 = r6 & 63
            int r6 = r6 << 6
            r9 = r9 | r6
            int r6 = r1 + 1
            r8.index = r6
            char r0 = r0[r1]
            r0 = r0 & 63
            r9 = r9 | r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r5)
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r0.append(r9)
            r0.append(r2)
            java.lang.String r9 = r0.toString()
            com.grack.nanojson.JsonParserException r9 = r8.createParseException(r4, r9, r3)
            throw r9
        L_0x0090:
            r9 = r9 & r1
            int r9 = r9 << 24
            char[] r0 = r8.buffer
            int r1 = r8.index
            int r6 = r1 + 1
            char r1 = r0[r1]
            r1 = r1 & 63
            int r1 = r1 << 18
            r9 = r9 | r1
            int r1 = r6 + 1
            char r6 = r0[r6]
            r6 = r6 & 63
            int r6 = r6 << 12
            r9 = r9 | r6
            int r6 = r1 + 1
            char r1 = r0[r1]
            r1 = r1 & 63
            int r1 = r1 << 6
            r9 = r9 | r1
            int r1 = r6 + 1
            r8.index = r1
            char r0 = r0[r6]
            r0 = r0 & 63
            r9 = r9 | r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r5)
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r0.append(r9)
            r0.append(r2)
            java.lang.String r9 = r0.toString()
            com.grack.nanojson.JsonParserException r9 = r8.createParseException(r4, r9, r3)
            throw r9
        L_0x00d3:
            java.lang.StringBuilder r0 = r8.reusableBuffer
            r9 = r9 & 7
            int r9 = r9 << 18
            char[] r2 = r8.buffer
            int r5 = r8.index
            int r6 = r5 + 1
            char r5 = r2[r5]
            r5 = r5 & 63
            int r5 = r5 << 12
            r9 = r9 | r5
            int r5 = r6 + 1
            char r6 = r2[r6]
            r6 = r6 & 63
            int r6 = r6 << 6
            r9 = r9 | r6
            int r6 = r5 + 1
            r8.index = r6
            char r2 = r2[r5]
            r2 = r2 & 63
            r9 = r9 | r2
            r0.appendCodePoint(r9)
            int r9 = r8.utf8adjust
            int r9 = r9 + r1
            goto L_0x0187
        L_0x0100:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r5)
            r9 = r9 & 255(0xff, float:3.57E-43)
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            com.grack.nanojson.JsonParserException r9 = r8.createParseException(r4, r9, r3)
            throw r9
        L_0x0117:
            r9 = r9 & 15
            int r9 = r9 << 12
            char[] r0 = r8.buffer
            int r1 = r8.index
            int r2 = r1 + 1
            char r1 = r0[r1]
            r1 = r1 & 63
            int r1 = r1 << 6
            r9 = r9 | r1
            int r1 = r2 + 1
            r8.index = r1
            char r0 = r0[r2]
            r0 = r0 & 63
            r9 = r9 | r0
            char r9 = (char) r9
            int r0 = r8.utf8adjust
            int r0 = r0 + r7
            r8.utf8adjust = r0
            r0 = 55296(0xd800, float:7.7486E-41)
            if (r9 < r0) goto L_0x0141
            r0 = 56319(0xdbff, float:7.892E-41)
            if (r9 <= r0) goto L_0x014c
        L_0x0141:
            r0 = 56320(0xdc00, float:7.8921E-41)
            if (r9 < r0) goto L_0x0163
            r0 = 57343(0xdfff, float:8.0355E-41)
            if (r9 <= r0) goto L_0x014c
            goto L_0x0163
        L_0x014c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Illegal UTF-8 codepoint: 0x"
            r0.<init>(r1)
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            com.grack.nanojson.JsonParserException r9 = r8.createParseException(r4, r9, r3)
            throw r9
        L_0x0163:
            java.lang.StringBuilder r0 = r8.reusableBuffer
            r0.append(r9)
            goto L_0x0189
        L_0x0169:
            r0 = r9 & 14
            if (r0 == 0) goto L_0x0197
        L_0x016d:
            r9 = r9 & 31
            int r9 = r9 << 6
            char[] r0 = r8.buffer
            int r1 = r8.index
            int r2 = r1 + 1
            r8.index = r2
            char r0 = r0[r1]
            r0 = r0 & 63
            r9 = r9 | r0
            char r9 = (char) r9
            java.lang.StringBuilder r0 = r8.reusableBuffer
            r0.append(r9)
            int r9 = r8.utf8adjust
            int r9 = r9 + r6
        L_0x0187:
            r8.utf8adjust = r9
        L_0x0189:
            int r9 = r8.index
            int r0 = r8.bufferLength
            if (r9 > r0) goto L_0x0190
            return
        L_0x0190:
            java.lang.String r9 = "UTF-8 codepoint was truncated"
            com.grack.nanojson.JsonParserException r9 = r8.createParseException(r4, r9, r3)
            throw r9
        L_0x0197:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r5)
            r9 = r9 & 255(0xff, float:3.57E-43)
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            com.grack.nanojson.JsonParserException r9 = r8.createParseException(r4, r9, r3)
            throw r9
        L_0x01ae:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Illegal UTF-8 continuation byte: 0x"
            r0.<init>(r1)
            r9 = r9 & 255(0xff, float:3.57E-43)
            java.lang.String r9 = java.lang.Integer.toHexString(r9)
            r0.append(r9)
            java.lang.String r9 = r0.toString()
            com.grack.nanojson.JsonParserException r9 = r8.createParseException(r4, r9, r3)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.grack.nanojson.JsonTokener.consumeTokenStringUtf8Char(char):void");
    }

    private void consumeWhitespace() throws JsonParserException {
        int ensureBuffer;
        do {
            ensureBuffer = ensureBuffer(256);
            for (int i = 0; i < ensureBuffer; i++) {
                char c = this.buffer[this.index];
                if (!isWhitespace(c)) {
                    fixupAfterRawBufferRead();
                    return;
                }
                if (c == 10) {
                    this.linePos++;
                    this.rowPos = this.index + 1 + this.charOffset;
                    this.utf8adjust = 0;
                }
                this.index++;
            }
        } while (ensureBuffer > 0);
        this.eof = true;
    }

    private void init() throws JsonParserException {
        this.eof = refillBuffer();
        consumeWhitespace();
    }

    private boolean isDigitCharacter(int i) {
        return (i >= 48 && i <= 57) || i == 101 || i == 69 || i == 46 || i == 43 || i == 45;
    }

    private int peekChar() {
        if (this.eof) {
            return -1;
        }
        return this.buffer[this.index];
    }

    private boolean refillBuffer() throws JsonParserException {
        try {
            Reader reader2 = this.reader;
            char[] cArr = this.buffer;
            int read = reader2.read(cArr, 0, cArr.length);
            if (read <= 0) {
                return true;
            }
            this.charOffset += this.bufferLength;
            this.index = 0;
            this.bufferLength = read;
            return false;
        } catch (IOException e) {
            throw createParseException(e, "IOException", true);
        }
    }

    private char stringChar() throws JsonParserException {
        char[] cArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        char c = cArr[i];
        if (c < ' ') {
            throwControlCharacterException(c);
        }
        return c;
    }

    private void throwControlCharacterException(char c) throws JsonParserException {
        if (c == 10) {
            this.linePos++;
            this.rowPos = this.index + 1 + this.charOffset;
            this.utf8adjust = 0;
        }
        throw createParseException((Exception) null, "Strings may not contain control characters: 0x" + Integer.toString(c, 16), false);
    }

    public int advanceCharFast() {
        char[] cArr = this.buffer;
        int i = this.index;
        char c = cArr[i];
        if (c == 10) {
            this.linePos++;
            this.rowPos = i + 1 + this.charOffset;
            this.utf8adjust = 0;
        }
        this.index = i + 1;
        return c;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x007a, code lost:
        consumeTokenNumber((char) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:?, code lost:
        return 9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int advanceToToken(boolean r7) throws com.grack.nanojson.JsonParserException {
        /*
            r6 = this;
        L_0x0000:
            int r0 = r6.advanceChar()
            boolean r1 = r6.isWhitespace(r0)
            if (r1 == 0) goto L_0x000b
            goto L_0x0000
        L_0x000b:
            int r1 = r6.index
            int r2 = r6.charOffset
            int r3 = r1 + r2
            int r4 = r6.rowPos
            int r3 = r3 - r4
            int r4 = r6.utf8adjust
            int r3 = r3 - r4
            r6.tokenCharPos = r3
            int r2 = r2 + r1
            r6.tokenCharOffset = r2
            r2 = -1
            r3 = 0
            if (r0 == r2) goto L_0x00d3
            r2 = 34
            if (r0 == r2) goto L_0x00cd
            r2 = 39
            if (r0 == r2) goto L_0x00cd
            r2 = 91
            if (r0 == r2) goto L_0x00ca
            r2 = 93
            if (r0 == r2) goto L_0x00c8
            r2 = 102(0x66, float:1.43E-43)
            r4 = 12
            r5 = 1
            if (r0 == r2) goto L_0x00bb
            r2 = 110(0x6e, float:1.54E-43)
            if (r0 == r2) goto L_0x00ae
            r2 = 116(0x74, float:1.63E-43)
            if (r0 == r2) goto L_0x00a1
            r2 = 123(0x7b, float:1.72E-43)
            if (r0 == r2) goto L_0x009e
            r2 = 125(0x7d, float:1.75E-43)
            if (r0 == r2) goto L_0x009c
            r2 = 0
            switch(r0) {
                case 43: goto L_0x0083;
                case 44: goto L_0x0081;
                case 45: goto L_0x007a;
                case 46: goto L_0x0083;
                default: goto L_0x004b;
            }
        L_0x004b:
            switch(r0) {
                case 48: goto L_0x007a;
                case 49: goto L_0x007a;
                case 50: goto L_0x007a;
                case 51: goto L_0x007a;
                case 52: goto L_0x007a;
                case 53: goto L_0x007a;
                case 54: goto L_0x007a;
                case 55: goto L_0x007a;
                case 56: goto L_0x007a;
                case 57: goto L_0x007a;
                case 58: goto L_0x0078;
                default: goto L_0x004e;
            }
        L_0x004e:
            if (r7 == 0) goto L_0x0058
        L_0x0050:
            int r1 = r1 - r5
            r6.index = r1
            r6.consumeTokenSemiString()
            goto L_0x00d2
        L_0x0058:
            boolean r7 = r6.isAsciiLetter(r0)
            if (r7 == 0) goto L_0x0064
            char r7 = (char) r0
            com.grack.nanojson.JsonParserException r7 = r6.createHelpfulException(r7, r2, r3)
            throw r7
        L_0x0064:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r1 = "Unexpected character: "
            r7.<init>(r1)
            char r0 = (char) r0
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            com.grack.nanojson.JsonParserException r7 = r6.createParseException(r2, r7, r5)
            throw r7
        L_0x0078:
            r4 = 2
            goto L_0x00d2
        L_0x007a:
            char r7 = (char) r0
            r6.consumeTokenNumber(r7)
            r4 = 9
            goto L_0x00d2
        L_0x0081:
            r4 = 1
            goto L_0x00d2
        L_0x0083:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r1 = "Numbers may not start with '"
            r7.<init>(r1)
            char r0 = (char) r0
            r7.append(r0)
            java.lang.String r0 = "'"
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            com.grack.nanojson.JsonParserException r7 = r6.createParseException(r2, r7, r5)
            throw r7
        L_0x009c:
            r4 = 3
            goto L_0x00d2
        L_0x009e:
            r4 = 10
            goto L_0x00d2
        L_0x00a1:
            char r0 = (char) r0
            char[] r2 = TRUE     // Catch:{ JsonParserException -> 0x00a9 }
            r6.consumeKeyword(r0, r2)     // Catch:{ JsonParserException -> 0x00a9 }
            r4 = 6
            goto L_0x00d2
        L_0x00a9:
            r0 = move-exception
            if (r7 == 0) goto L_0x00ad
            goto L_0x0050
        L_0x00ad:
            throw r0
        L_0x00ae:
            char r0 = (char) r0
            char[] r2 = NULL     // Catch:{ JsonParserException -> 0x00b6 }
            r6.consumeKeyword(r0, r2)     // Catch:{ JsonParserException -> 0x00b6 }
            r4 = 5
            goto L_0x00d2
        L_0x00b6:
            r0 = move-exception
            if (r7 == 0) goto L_0x00ba
            goto L_0x0050
        L_0x00ba:
            throw r0
        L_0x00bb:
            char r0 = (char) r0
            char[] r2 = FALSE     // Catch:{ JsonParserException -> 0x00c3 }
            r6.consumeKeyword(r0, r2)     // Catch:{ JsonParserException -> 0x00c3 }
            r4 = 7
            goto L_0x00d2
        L_0x00c3:
            r0 = move-exception
            if (r7 == 0) goto L_0x00c7
            goto L_0x0050
        L_0x00c7:
            throw r0
        L_0x00c8:
            r4 = 4
            goto L_0x00d2
        L_0x00ca:
            r4 = 11
            goto L_0x00d2
        L_0x00cd:
            r6.consumeTokenString(r0)
            r4 = 8
        L_0x00d2:
            return r4
        L_0x00d3:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.grack.nanojson.JsonTokener.advanceToToken(boolean):int");
    }

    public void consumeKeyword(char c, char[] cArr) throws JsonParserException {
        int i = 0;
        if (ensureBuffer(cArr.length) >= cArr.length) {
            while (i < cArr.length) {
                char[] cArr2 = this.buffer;
                int i2 = this.index;
                this.index = i2 + 1;
                if (cArr2[i2] == cArr[i]) {
                    i++;
                } else {
                    throw createHelpfulException(c, cArr, i);
                }
            }
            fixupAfterRawBufferRead();
            int peekChar = peekChar();
            if (peekChar != 9 && peekChar != 10 && peekChar != 13 && peekChar != 32 && peekChar != 44 && peekChar != 58 && peekChar != 91 && peekChar != 93 && peekChar != 123 && peekChar != 125) {
                throw createHelpfulException(c, cArr, cArr.length);
            }
            return;
        }
        throw createHelpfulException(c, cArr, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0072, code lost:
        if (r3 <= '9') goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0074, code lost:
        r8 = 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0082, code lost:
        if (r3 <= '9') goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0090, code lost:
        if (r3 != 'E') goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0092, code lost:
        if (r8 != 5) goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00a7, code lost:
        if (r3 != 'E') goto L_0x00b7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00a9, code lost:
        r0.isDouble = true;
        r8 = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00b3, code lost:
        if (r3 <= '9') goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00b5, code lost:
        r8 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00b7, code lost:
        r8 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b8, code lost:
        r0.reusableBuffer.append(r3);
        r0.index++;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00c3, code lost:
        if (r8 == 65535) goto L_0x00ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00c5, code lost:
        r14 = r14 + 1;
        r2 = 3;
        r11 = null;
        r12 = 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e1, code lost:
        throw createParseException((java.lang.Exception) null, "Malformed number: " + r0.reusableBuffer, true);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consumeTokenNumber(char r17) throws com.grack.nanojson.JsonParserException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            java.lang.StringBuilder r2 = r0.reusableBuffer
            r3 = 0
            r2.setLength(r3)
            java.lang.StringBuilder r2 = r0.reusableBuffer
            r2.append(r1)
            r0.isDouble = r3
            r2 = 3
            r4 = 2
            r5 = 48
            r6 = 1
            r7 = 45
            if (r1 != r7) goto L_0x001c
            r8 = 1
            goto L_0x0021
        L_0x001c:
            if (r1 != r5) goto L_0x0020
            r8 = 3
            goto L_0x0021
        L_0x0020:
            r8 = 2
        L_0x0021:
            r9 = 256(0x100, float:3.59E-43)
            int r9 = r0.ensureBuffer(r9)
            java.lang.String r10 = "Malformed number: "
            r11 = 0
            r12 = 8
            r13 = 5
            if (r9 != 0) goto L_0x0030
            goto L_0x003f
        L_0x0030:
            r14 = 0
        L_0x0031:
            if (r14 >= r9) goto L_0x0021
            char[] r15 = r0.buffer
            int r3 = r0.index
            char r3 = r15[r3]
            boolean r15 = r0.isDigitCharacter(r3)
            if (r15 != 0) goto L_0x0065
        L_0x003f:
            if (r8 == r4) goto L_0x005b
            if (r8 == r2) goto L_0x005b
            if (r8 == r13) goto L_0x005b
            if (r8 != r12) goto L_0x0048
            goto L_0x005b
        L_0x0048:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r10)
            java.lang.StringBuilder r2 = r0.reusableBuffer
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r11, r1, r6)
            throw r1
        L_0x005b:
            if (r8 != r2) goto L_0x0061
            if (r1 != r7) goto L_0x0061
            r0.isDouble = r6
        L_0x0061:
            r16.fixupAfterRawBufferRead()
            return
        L_0x0065:
            r15 = 69
            r2 = 101(0x65, float:1.42E-43)
            r11 = 6
            r12 = 57
            switch(r8) {
                case 1: goto L_0x00ad;
                case 2: goto L_0x0095;
                case 3: goto L_0x0095;
                case 4: goto L_0x0088;
                case 5: goto L_0x0088;
                case 6: goto L_0x0077;
                case 7: goto L_0x0077;
                case 8: goto L_0x0070;
                default: goto L_0x006f;
            }
        L_0x006f:
            goto L_0x00b7
        L_0x0070:
            if (r3 < r5) goto L_0x00b7
            if (r3 > r12) goto L_0x00b7
        L_0x0074:
            r8 = 8
            goto L_0x00b8
        L_0x0077:
            r2 = 43
            if (r3 == r2) goto L_0x0085
            if (r3 != r7) goto L_0x0080
            if (r8 != r11) goto L_0x0080
            goto L_0x0085
        L_0x0080:
            if (r3 < r5) goto L_0x00b7
            if (r3 > r12) goto L_0x00b7
            goto L_0x0074
        L_0x0085:
            r2 = 7
            r8 = 7
            goto L_0x00b8
        L_0x0088:
            if (r3 < r5) goto L_0x008e
            if (r3 > r12) goto L_0x008e
            r8 = 5
            goto L_0x00b8
        L_0x008e:
            if (r3 == r2) goto L_0x0092
            if (r3 != r15) goto L_0x00b7
        L_0x0092:
            if (r8 != r13) goto L_0x00b7
            goto L_0x00a9
        L_0x0095:
            if (r3 < r5) goto L_0x009c
            if (r3 > r12) goto L_0x009c
            if (r8 != r4) goto L_0x009c
            goto L_0x00b5
        L_0x009c:
            r8 = 46
            if (r3 != r8) goto L_0x00a5
            r0.isDouble = r6
            r2 = 4
            r8 = 4
            goto L_0x00b8
        L_0x00a5:
            if (r3 == r2) goto L_0x00a9
            if (r3 != r15) goto L_0x00b7
        L_0x00a9:
            r0.isDouble = r6
            r8 = 6
            goto L_0x00b8
        L_0x00ad:
            if (r3 != r5) goto L_0x00b1
            r8 = 3
            goto L_0x00b8
        L_0x00b1:
            if (r3 <= r5) goto L_0x00b7
            if (r3 > r12) goto L_0x00b7
        L_0x00b5:
            r8 = 2
            goto L_0x00b8
        L_0x00b7:
            r8 = -1
        L_0x00b8:
            java.lang.StringBuilder r2 = r0.reusableBuffer
            r2.append(r3)
            int r2 = r0.index
            int r2 = r2 + r6
            r0.index = r2
            r2 = -1
            if (r8 == r2) goto L_0x00ce
            int r14 = r14 + 1
            r2 = 3
            r3 = 0
            r11 = 0
            r12 = 8
            goto L_0x0031
        L_0x00ce:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r10)
            java.lang.StringBuilder r2 = r0.reusableBuffer
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r2, r1, r6)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.grack.nanojson.JsonTokener.consumeTokenNumber(char):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x016c, code lost:
        r0.reusableBuffer.append(9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0172, code lost:
        r1 = r0.reusableBuffer;
        r3 = 13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0178, code lost:
        r0.reusableBuffer.append(10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x017e, code lost:
        r1 = r0.reusableBuffer;
        r3 = 12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0184, code lost:
        r1 = r0.reusableBuffer;
        r3 = 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x018a, code lost:
        r1 = 256;
        r9 = ':';
        r10 = ',';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01a3, code lost:
        throw createParseException((java.lang.Exception) null, "Invalid character in semi-string: " + r3, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01a4, code lost:
        fixupAfterRawBufferRead();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01a7, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01a8, code lost:
        r1 = r0.bufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01aa, code lost:
        if (r3 > r1) goto L_0x01b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01ac, code lost:
        r1 = 256;
        r9 = ':';
        r10 = ',';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x01b5, code lost:
        r0.index = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01bb, code lost:
        throw createParseException((java.lang.Exception) null, "EOF encountered in the middle of a string escape", false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01c1, code lost:
        throw createParseException((java.lang.Exception) null, "String was not terminated before end of input", true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
        throw createParseException((java.lang.Exception) null, "Invalid character in semi-string: " + r8, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006e, code lost:
        r3 = ensureBuffer(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0072, code lost:
        if (r3 == 0) goto L_0x01bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0074, code lost:
        r7 = r0.index + r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0077, code lost:
        r3 = r0.index;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x007b, code lost:
        if (r3 >= r7) goto L_0x01a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x007d, code lost:
        r3 = stringChar();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0083, code lost:
        if (r0.utf8 == false) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0087, code lost:
        if ((r3 & 128) == 0) goto L_0x008d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0089, code lost:
        consumeTokenStringUtf8Char(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x008f, code lost:
        if (r3 == 9) goto L_0x01a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0093, code lost:
        if (r3 == 10) goto L_0x01a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0097, code lost:
        if (r3 == 13) goto L_0x01a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009b, code lost:
        if (r3 == ' ') goto L_0x01a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x009d, code lost:
        if (r3 == r10) goto L_0x0193;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x009f, code lost:
        if (r3 == r9) goto L_0x01a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a1, code lost:
        if (r3 == '{') goto L_0x0193;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a3, code lost:
        if (r3 == '}') goto L_0x0193;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00a5, code lost:
        switch(r3) {
            case 91: goto L_0x0193;
            case 92: goto L_0x00af;
            case 93: goto L_0x0193;
            default: goto L_0x00a8;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00a8, code lost:
        r1 = r0.reusableBuffer;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00aa, code lost:
        r1.append(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00b6, code lost:
        if ((r7 - r0.index) >= 5) goto L_0x00d4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00b8, code lost:
        r3 = ensureBuffer(5);
        r7 = r0.index;
        r16 = r7 + r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c4, code lost:
        if (r0.buffer[r7] != 'u') goto L_0x00d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00c6, code lost:
        if (r3 < 5) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00c9, code lost:
        r0.index = r0.bufferLength;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00d1, code lost:
        throw createParseException((java.lang.Exception) null, "EOF encountered in the middle of a string escape", false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00d2, code lost:
        r7 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00d4, code lost:
        r3 = r0.buffer;
        r8 = r0.index;
        r0.index = r8 + 1;
        r3 = r3[r8];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00e0, code lost:
        if (r3 == '\"') goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x00e4, code lost:
        if (r3 == '/') goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00e6, code lost:
        if (r3 == '\\') goto L_0x00a8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00ea, code lost:
        if (r3 == 'b') goto L_0x0184;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00ee, code lost:
        if (r3 == 'f') goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00f2, code lost:
        if (r3 == 'n') goto L_0x0178;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00f6, code lost:
        if (r3 == 'r') goto L_0x0172;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00fa, code lost:
        if (r3 == 't') goto L_0x016c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00fc, code lost:
        if (r3 != 'u') goto L_0x0159;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00fe, code lost:
        r3 = 0;
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0101, code lost:
        if (r3 >= 4) goto L_0x0154;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x0103, code lost:
        r6 = r6 << 4;
        r9 = r0.buffer;
        r10 = r0.index;
        r0.index = r10 + 1;
        r9 = r9[r10];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0111, code lost:
        if (r9 < '0') goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0115, code lost:
        if (r9 > '9') goto L_0x011b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0117, code lost:
        r9 = r9 - '0';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0119, code lost:
        r6 = r6 | r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x011d, code lost:
        if (r9 < 'A') goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0121, code lost:
        if (r9 > 'F') goto L_0x0127;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0123, code lost:
        r9 = r9 - 'A';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0125, code lost:
        r9 = r9 + 10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0129, code lost:
        if (r9 < 'a') goto L_0x0133;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x012b, code lost:
        if (r9 > 'f') goto L_0x0133;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x012d, code lost:
        r9 = r9 - 'a';
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0130, code lost:
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0153, code lost:
        throw createParseException((java.lang.Exception) null, "Expected unicode hex escape character: " + ((char) r9) + " (" + r9 + ")", false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x0154, code lost:
        r1 = r0.reusableBuffer;
        r3 = (char) r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x016b, code lost:
        throw createParseException((java.lang.Exception) null, "Invalid escape: \\" + r3, false);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void consumeTokenSemiString() throws com.grack.nanojson.JsonParserException {
        /*
            r17 = this;
            r0 = r17
            java.lang.StringBuilder r1 = r0.reusableBuffer
            r2 = 0
            r1.setLength(r2)
        L_0x0008:
            r1 = 256(0x100, float:3.59E-43)
            int r3 = r0.ensureBuffer(r1)
            java.lang.String r4 = "String was not terminated before end of input"
            r5 = 0
            r6 = 1
            if (r3 == 0) goto L_0x01de
            r7 = 0
        L_0x0015:
            if (r7 >= r3) goto L_0x01d2
            char r8 = r17.stringChar()
            boolean r9 = r0.isWhitespace(r8)
            if (r9 != 0) goto L_0x01c2
            r9 = 58
            if (r8 != r9) goto L_0x0027
            goto L_0x01c2
        L_0x0027:
            r10 = 44
            r11 = 125(0x7d, float:1.75E-43)
            r12 = 123(0x7b, float:1.72E-43)
            java.lang.String r13 = "Invalid character in semi-string: "
            r14 = 92
            if (r8 == r14) goto L_0x005e
            boolean r15 = r0.utf8
            if (r15 == 0) goto L_0x003c
            r15 = r8 & 128(0x80, float:1.794E-43)
            if (r15 == 0) goto L_0x003c
            goto L_0x005e
        L_0x003c:
            r9 = 91
            if (r8 == r9) goto L_0x004d
            r9 = 93
            if (r8 == r9) goto L_0x004d
            if (r8 == r12) goto L_0x004d
            if (r8 == r11) goto L_0x004d
            if (r8 == r10) goto L_0x004d
            int r7 = r7 + 1
            goto L_0x0015
        L_0x004d:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r13)
            r1.append(r8)
            java.lang.String r1 = r1.toString()
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r1, r2)
            throw r1
        L_0x005e:
            java.lang.StringBuilder r3 = r0.reusableBuffer
            char[] r8 = r0.buffer
            int r15 = r0.index
            int r15 = r15 - r7
            int r15 = r15 - r6
            r3.append(r8, r15, r7)
            int r3 = r0.index
            int r3 = r3 - r6
            r0.index = r3
        L_0x006e:
            int r3 = r0.ensureBuffer(r1)
            if (r3 == 0) goto L_0x01bc
            int r7 = r0.index
            int r7 = r7 + r3
        L_0x0077:
            int r3 = r0.index
            java.lang.String r8 = "EOF encountered in the middle of a string escape"
            if (r3 >= r7) goto L_0x01a8
            char r3 = r17.stringChar()
            boolean r15 = r0.utf8
            if (r15 == 0) goto L_0x008d
            r15 = r3 & 128(0x80, float:1.794E-43)
            if (r15 == 0) goto L_0x008d
            r0.consumeTokenStringUtf8Char(r3)
            goto L_0x006e
        L_0x008d:
            r15 = 9
            if (r3 == r15) goto L_0x01a4
            r1 = 10
            if (r3 == r1) goto L_0x01a4
            r6 = 13
            if (r3 == r6) goto L_0x01a4
            r6 = 32
            if (r3 == r6) goto L_0x01a4
            if (r3 == r10) goto L_0x0193
            if (r3 == r9) goto L_0x01a4
            if (r3 == r12) goto L_0x0193
            if (r3 == r11) goto L_0x0193
            switch(r3) {
                case 91: goto L_0x0193;
                case 92: goto L_0x00af;
                case 93: goto L_0x0193;
                default: goto L_0x00a8;
            }
        L_0x00a8:
            java.lang.StringBuilder r1 = r0.reusableBuffer
        L_0x00aa:
            r1.append(r3)
            goto L_0x018a
        L_0x00af:
            int r3 = r0.index
            int r3 = r7 - r3
            r6 = 117(0x75, float:1.64E-43)
            r9 = 5
            if (r3 >= r9) goto L_0x00d4
            int r3 = r0.ensureBuffer(r9)
            int r7 = r0.index
            int r16 = r7 + r3
            char[] r10 = r0.buffer
            char r7 = r10[r7]
            if (r7 != r6) goto L_0x00d2
            if (r3 < r9) goto L_0x00c9
            goto L_0x00d2
        L_0x00c9:
            int r1 = r0.bufferLength
            r0.index = r1
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r8, r2)
            throw r1
        L_0x00d2:
            r7 = r16
        L_0x00d4:
            char[] r3 = r0.buffer
            int r8 = r0.index
            int r9 = r8 + 1
            r0.index = r9
            char r3 = r3[r8]
            r8 = 34
            if (r3 == r8) goto L_0x00a8
            r8 = 47
            if (r3 == r8) goto L_0x00a8
            if (r3 == r14) goto L_0x00a8
            r8 = 98
            if (r3 == r8) goto L_0x0184
            r8 = 102(0x66, float:1.43E-43)
            if (r3 == r8) goto L_0x017e
            r9 = 110(0x6e, float:1.54E-43)
            if (r3 == r9) goto L_0x0178
            r9 = 114(0x72, float:1.6E-43)
            if (r3 == r9) goto L_0x0172
            r9 = 116(0x74, float:1.63E-43)
            if (r3 == r9) goto L_0x016c
            if (r3 != r6) goto L_0x0159
            r3 = 0
            r6 = 0
        L_0x0100:
            r9 = 4
            if (r3 >= r9) goto L_0x0154
            int r6 = r6 << 4
            char[] r9 = r0.buffer
            int r10 = r0.index
            int r15 = r10 + 1
            r0.index = r15
            char r9 = r9[r10]
            r10 = 48
            if (r9 < r10) goto L_0x011b
            r10 = 57
            if (r9 > r10) goto L_0x011b
            int r9 = r9 + -48
        L_0x0119:
            r6 = r6 | r9
            goto L_0x0130
        L_0x011b:
            r10 = 65
            if (r9 < r10) goto L_0x0127
            r10 = 70
            if (r9 > r10) goto L_0x0127
            int r9 = r9 + -65
        L_0x0125:
            int r9 = r9 + r1
            goto L_0x0119
        L_0x0127:
            r10 = 97
            if (r9 < r10) goto L_0x0133
            if (r9 > r8) goto L_0x0133
            int r9 = r9 + -97
            goto L_0x0125
        L_0x0130:
            int r3 = r3 + 1
            goto L_0x0100
        L_0x0133:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "Expected unicode hex escape character: "
            r1.<init>(r3)
            char r3 = (char) r9
            r1.append(r3)
            java.lang.String r3 = " ("
            r1.append(r3)
            r1.append(r9)
            java.lang.String r3 = ")"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r1, r2)
            throw r1
        L_0x0154:
            java.lang.StringBuilder r1 = r0.reusableBuffer
            char r3 = (char) r6
            goto L_0x00aa
        L_0x0159:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r4 = "Invalid escape: \\"
            r1.<init>(r4)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r1, r2)
            throw r1
        L_0x016c:
            java.lang.StringBuilder r1 = r0.reusableBuffer
            r1.append(r15)
            goto L_0x018a
        L_0x0172:
            java.lang.StringBuilder r1 = r0.reusableBuffer
            r3 = 13
            goto L_0x00aa
        L_0x0178:
            java.lang.StringBuilder r3 = r0.reusableBuffer
            r3.append(r1)
            goto L_0x018a
        L_0x017e:
            java.lang.StringBuilder r1 = r0.reusableBuffer
            r3 = 12
            goto L_0x00aa
        L_0x0184:
            java.lang.StringBuilder r1 = r0.reusableBuffer
            r3 = 8
            goto L_0x00aa
        L_0x018a:
            r1 = 256(0x100, float:3.59E-43)
            r6 = 1
            r9 = 58
            r10 = 44
            goto L_0x0077
        L_0x0193:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>(r13)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r1, r2)
            throw r1
        L_0x01a4:
            r17.fixupAfterRawBufferRead()
            return
        L_0x01a8:
            int r1 = r0.bufferLength
            if (r3 > r1) goto L_0x01b5
            r1 = 256(0x100, float:3.59E-43)
            r6 = 1
            r9 = 58
            r10 = 44
            goto L_0x006e
        L_0x01b5:
            r0.index = r1
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r8, r2)
            throw r1
        L_0x01bc:
            r1 = 1
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r4, r1)
            throw r1
        L_0x01c2:
            r1 = 1
            java.lang.StringBuilder r2 = r0.reusableBuffer
            char[] r3 = r0.buffer
            int r4 = r0.index
            int r4 = r4 - r7
            int r4 = r4 - r1
            r2.append(r3, r4, r7)
            r17.fixupAfterRawBufferRead()
            return
        L_0x01d2:
            java.lang.StringBuilder r1 = r0.reusableBuffer
            char[] r4 = r0.buffer
            int r5 = r0.index
            int r5 = r5 - r3
            r1.append(r4, r5, r3)
            goto L_0x0008
        L_0x01de:
            r1 = 1
            com.grack.nanojson.JsonParserException r1 = r0.createParseException(r5, r4, r1)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.grack.nanojson.JsonTokener.consumeTokenSemiString():void");
    }

    public void consumeTokenString(int i) throws JsonParserException {
        int i2;
        int i3;
        StringBuilder sb;
        char c;
        int i4;
        int i5;
        int i6 = i;
        this.reusableBuffer.setLength(0);
        loop0:
        while (true) {
            i2 = 256;
            int ensureBuffer = ensureBuffer(256);
            if (ensureBuffer != 0) {
                i3 = 0;
                while (i3 < ensureBuffer) {
                    char stringChar = stringChar();
                    if (stringChar == i6) {
                        this.reusableBuffer.append(this.buffer, (this.index - i3) - 1, i3);
                        fixupAfterRawBufferRead();
                        return;
                    } else if (stringChar == '\\' || (this.utf8 && (stringChar & 128) != 0)) {
                        this.reusableBuffer.append(this.buffer, (this.index - i3) - 1, i3);
                        this.index--;
                    } else {
                        i3++;
                    }
                }
                this.reusableBuffer.append(this.buffer, this.index - ensureBuffer, ensureBuffer);
            } else {
                throw createParseException((Exception) null, "String was not terminated before end of input", true);
            }
        }
        this.reusableBuffer.append(this.buffer, (this.index - i3) - 1, i3);
        this.index--;
        loop2:
        while (true) {
            int ensureBuffer2 = ensureBuffer(i2);
            if (ensureBuffer2 != 0) {
                int i7 = this.index + ensureBuffer2;
                while (true) {
                    int i8 = this.index;
                    if (i8 < i7) {
                        char stringChar2 = stringChar();
                        if (this.utf8 && (stringChar2 & 128) != 0) {
                            consumeTokenStringUtf8Char(stringChar2);
                            break;
                        }
                        if (stringChar2 != '\"' && stringChar2 != '\'') {
                            if (stringChar2 != '\\') {
                                this.reusableBuffer.append(stringChar2);
                            } else {
                                if (i7 - this.index < 5) {
                                    int ensureBuffer3 = ensureBuffer(5);
                                    int i9 = this.index;
                                    int i10 = i9 + ensureBuffer3;
                                    if (this.buffer[i9] != 'u' || ensureBuffer3 >= 5) {
                                        i7 = i10;
                                    } else {
                                        this.index = this.bufferLength;
                                        throw createParseException((Exception) null, "EOF encountered in the middle of a string escape", false);
                                    }
                                }
                                char[] cArr = this.buffer;
                                int i11 = this.index;
                                this.index = i11 + 1;
                                char c2 = cArr[i11];
                                if (c2 == '\"' || c2 == '\'' || c2 == '/' || c2 == '\\') {
                                    this.reusableBuffer.append(c2);
                                } else if (c2 == 'b') {
                                    sb = this.reusableBuffer;
                                    stringChar2 = 8;
                                } else if (c2 == 'f') {
                                    sb = this.reusableBuffer;
                                    stringChar2 = 12;
                                } else if (c2 == 'n') {
                                    this.reusableBuffer.append(10);
                                } else if (c2 == 'r') {
                                    sb = this.reusableBuffer;
                                    stringChar2 = 13;
                                } else if (c2 == 't') {
                                    sb = this.reusableBuffer;
                                    stringChar2 = 9;
                                } else if (c2 == 'u') {
                                    int i12 = 0;
                                    for (int i13 = 0; i13 < 4; i13++) {
                                        int i14 = i12 << 4;
                                        char[] cArr2 = this.buffer;
                                        int i15 = this.index;
                                        this.index = i15 + 1;
                                        c = cArr2[i15];
                                        if (c < '0' || c > '9') {
                                            if (c >= 'A' && c <= 'F') {
                                                i4 = c - 'A';
                                            } else if (c < 'a' || c > 'f') {
                                            } else {
                                                i4 = c - 'a';
                                            }
                                            i5 = i4 + 10;
                                        } else {
                                            i5 = c - '0';
                                        }
                                        i12 = i14 | i5;
                                    }
                                    sb = this.reusableBuffer;
                                    stringChar2 = (char) i12;
                                } else {
                                    throw createParseException((Exception) null, "Invalid escape: \\" + c2, false);
                                }
                            }
                            i2 = 256;
                        } else if (stringChar2 == i6) {
                            fixupAfterRawBufferRead();
                            return;
                        } else {
                            sb = this.reusableBuffer;
                        }
                        sb.append(stringChar2);
                        i2 = 256;
                    } else {
                        int i16 = this.bufferLength;
                        if (i8 <= i16) {
                            i2 = 256;
                        } else {
                            this.index = i16;
                            throw createParseException((Exception) null, "EOF encountered in the middle of a string escape", false);
                        }
                    }
                }
            } else {
                throw createParseException((Exception) null, "String was not terminated before end of input", true);
            }
        }
        throw createParseException((Exception) null, "Expected unicode hex escape character: " + ((char) c) + " (" + c + ")", false);
    }

    public JsonParserException createHelpfulException(char c, char[] cArr, int i) throws JsonParserException {
        StringBuilder sb = new StringBuilder();
        sb.append(c);
        String str = "";
        sb.append(cArr == null ? str : new String(cArr, 0, i));
        StringBuilder sb2 = new StringBuilder(sb.toString());
        while (isAsciiLetter(peekChar()) && sb2.length() < 15) {
            sb2.append((char) advanceChar());
        }
        StringBuilder sb3 = new StringBuilder("Unexpected token '");
        sb3.append(sb2);
        sb3.append("'");
        if (cArr != null) {
            str = ". Did you mean '" + c + new String(cArr) + "'?";
        }
        sb3.append(str);
        return createParseException((Exception) null, sb3.toString(), true);
    }

    public JsonParserException createParseException(Exception exc, String str, boolean z) {
        if (z) {
            StringBuilder o = y2.o(str, " on line ");
            o.append(this.linePos);
            o.append(", char ");
            o.append(this.tokenCharPos);
            return new JsonParserException(exc, o.toString(), this.linePos, this.tokenCharPos, this.tokenCharOffset);
        }
        int max = Math.max(1, ((this.index + this.charOffset) - this.rowPos) - this.utf8adjust);
        StringBuilder o2 = y2.o(str, " on line ");
        o2.append(this.linePos);
        o2.append(", char ");
        o2.append(max);
        return new JsonParserException(exc, o2.toString(), this.linePos, max, this.index + this.charOffset);
    }

    public int ensureBuffer(int i) throws JsonParserException {
        int i2;
        int i3 = this.bufferLength;
        int i4 = i3 - i;
        int i5 = this.index;
        if (i4 >= i5) {
            return i;
        }
        if (i5 > 0) {
            this.charOffset += i5;
            int i6 = i3 - i5;
            this.bufferLength = i6;
            char[] cArr = this.buffer;
            System.arraycopy(cArr, i5, cArr, 0, i6);
            this.index = 0;
        }
        do {
            try {
                char[] cArr2 = this.buffer;
                int length = cArr2.length;
                int i7 = this.bufferLength;
                if (length > i7) {
                    int read = this.reader.read(cArr2, i7, cArr2.length - i7);
                    if (read <= 0) {
                        return this.bufferLength - this.index;
                    }
                    i2 = this.bufferLength + read;
                    this.bufferLength = i2;
                } else {
                    throw new IOException("Unexpected internal error");
                }
            } catch (IOException e) {
                throw createParseException(e, "IOException", true);
            }
        } while (i2 <= i);
        return i2 - this.index;
    }

    public void fixupAfterRawBufferRead() throws JsonParserException {
        if (this.index >= this.bufferLength) {
            this.eof = refillBuffer();
        }
    }

    public boolean isAsciiLetter(int i) {
        return (i >= 65 && i <= 90) || (i >= 97 && i <= 122);
    }

    public boolean isWhitespace(int i) {
        return i == 32 || i == 10 || i == 13 || i == 9;
    }

    public int tokenChar() throws JsonParserException {
        int advanceChar;
        do {
            advanceChar = advanceChar();
        } while (isWhitespace(advanceChar));
        return advanceChar;
    }
}
