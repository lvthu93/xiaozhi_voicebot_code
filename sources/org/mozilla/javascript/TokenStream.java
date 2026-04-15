package org.mozilla.javascript;

import java.io.IOException;
import java.io.Reader;
import org.mozilla.javascript.Token;

class TokenStream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final char BYTE_ORDER_MARK = '﻿';
    private static final int EOF_CHAR = -1;
    private ObjToIntMap allStrings = new ObjToIntMap(50);
    private int commentCursor = -1;
    private String commentPrefix = "";
    Token.CommentType commentType;
    int cursor;
    private boolean dirtyLine;
    private boolean hitEOF = false;
    private boolean isBinary;
    private boolean isHex;
    private boolean isOctal;
    private boolean isOldOctal;
    private int lineEndChar = -1;
    private int lineStart = 0;
    int lineno;
    private double number;
    private Parser parser;
    private int quoteChar;
    String regExpFlags;
    private char[] sourceBuffer;
    int sourceCursor;
    private int sourceEnd;
    private Reader sourceReader;
    private String sourceString;
    private String string = "";
    private char[] stringBuffer = new char[128];
    private int stringBufferTop;
    int tokenBeg;
    int tokenEnd;
    private final int[] ungetBuffer = new int[3];
    private int ungetCursor;
    private boolean xmlIsAttribute;
    private boolean xmlIsTagContent;
    private int xmlOpenTagsCount;

    public TokenStream(Parser parser2, Reader reader, String str, int i) {
        this.parser = parser2;
        this.lineno = i;
        if (reader != null) {
            if (str != null) {
                Kit.codeBug();
            }
            this.sourceReader = reader;
            this.sourceBuffer = new char[512];
            this.sourceEnd = 0;
        } else {
            if (str == null) {
                Kit.codeBug();
            }
            this.sourceString = str;
            this.sourceEnd = str.length();
        }
        this.cursor = 0;
        this.sourceCursor = 0;
    }

    private void addToString(int i) {
        int i2 = this.stringBufferTop;
        char[] cArr = this.stringBuffer;
        if (i2 == cArr.length) {
            char[] cArr2 = new char[(cArr.length * 2)];
            System.arraycopy(cArr, 0, cArr2, 0, i2);
            this.stringBuffer = cArr2;
        }
        this.stringBuffer[i2] = (char) i;
        this.stringBufferTop = i2 + 1;
    }

    private boolean canUngetChar() {
        int i = this.ungetCursor;
        return i == 0 || this.ungetBuffer[i - 1] != 10;
    }

    private final int charAt(int i) {
        if (i < 0) {
            return -1;
        }
        String str = this.sourceString;
        if (str == null) {
            if (i >= this.sourceEnd) {
                int i2 = this.sourceCursor;
                try {
                    if (!fillSourceBuffer()) {
                        return -1;
                    }
                    i -= i2 - this.sourceCursor;
                } catch (IOException unused) {
                    return -1;
                }
            }
            return this.sourceBuffer[i];
        } else if (i >= this.sourceEnd) {
            return -1;
        } else {
            return str.charAt(i);
        }
    }

    private static String convertLastCharToHex(String str) {
        int length = str.length() - 1;
        StringBuilder sb = new StringBuilder(str.substring(0, length));
        sb.append("\\u");
        String hexString = Integer.toHexString(str.charAt(length));
        for (int i = 0; i < 4 - hexString.length(); i++) {
            sb.append('0');
        }
        sb.append(hexString);
        return sb.toString();
    }

    private boolean fillSourceBuffer() throws IOException {
        if (this.sourceString != null) {
            Kit.codeBug();
        }
        if (this.sourceEnd == this.sourceBuffer.length) {
            if (this.lineStart == 0 || isMarkingComment()) {
                char[] cArr = this.sourceBuffer;
                char[] cArr2 = new char[(cArr.length * 2)];
                System.arraycopy(cArr, 0, cArr2, 0, this.sourceEnd);
                this.sourceBuffer = cArr2;
            } else {
                char[] cArr3 = this.sourceBuffer;
                int i = this.lineStart;
                System.arraycopy(cArr3, i, cArr3, 0, this.sourceEnd - i);
                int i2 = this.sourceEnd;
                int i3 = this.lineStart;
                this.sourceEnd = i2 - i3;
                this.sourceCursor -= i3;
                this.lineStart = 0;
            }
        }
        Reader reader = this.sourceReader;
        char[] cArr4 = this.sourceBuffer;
        int i4 = this.sourceEnd;
        int read = reader.read(cArr4, i4, cArr4.length - i4);
        if (read < 0) {
            return false;
        }
        this.sourceEnd += read;
        return true;
    }

    private int getChar() throws IOException {
        return getChar(true);
    }

    private int getCharIgnoreLineEnd() throws IOException {
        char c;
        int i = this.ungetCursor;
        if (i != 0) {
            this.cursor++;
            int[] iArr = this.ungetBuffer;
            int i2 = i - 1;
            this.ungetCursor = i2;
            return iArr[i2];
        }
        while (true) {
            String str = this.sourceString;
            if (str != null) {
                int i3 = this.sourceCursor;
                if (i3 == this.sourceEnd) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                this.sourceCursor = i3 + 1;
                c = str.charAt(i3);
            } else if (this.sourceCursor != this.sourceEnd || fillSourceBuffer()) {
                this.cursor++;
                char[] cArr = this.sourceBuffer;
                int i4 = this.sourceCursor;
                this.sourceCursor = i4 + 1;
                c = cArr[i4];
            } else {
                this.hitEOF = true;
                return -1;
            }
            if (c <= 127) {
                if (c != 10 && c != 13) {
                    return c;
                }
                this.lineEndChar = c;
            } else if (c == 65279) {
                return c;
            } else {
                if (!isJSFormatChar(c)) {
                    if (!ScriptRuntime.isJSLineTerminator(c)) {
                        return c;
                    }
                    this.lineEndChar = c;
                }
            }
        }
        return 10;
    }

    private String getStringFromBuffer() {
        this.tokenEnd = this.cursor;
        return new String(this.stringBuffer, 0, this.stringBufferTop);
    }

    private static boolean isAlpha(int i) {
        return i <= 90 ? 65 <= i : 97 <= i && i <= 122;
    }

    public static boolean isDigit(int i) {
        return 48 <= i && i <= 57;
    }

    private static boolean isJSFormatChar(int i) {
        return i > 127 && Character.getType((char) i) == 16;
    }

    public static boolean isJSSpace(int i) {
        return i <= 127 ? i == 32 || i == 9 || i == 12 || i == 11 : i == 160 || i == 65279 || Character.getType((char) i) == 12;
    }

    public static boolean isKeyword(String str, int i, boolean z) {
        return stringToKeyword(str, i, z) != 0;
    }

    private boolean isMarkingComment() {
        return this.commentCursor != -1;
    }

    private void markCommentStart() {
        markCommentStart("");
    }

    private boolean matchChar(int i) throws IOException {
        int charIgnoreLineEnd = getCharIgnoreLineEnd();
        if (charIgnoreLineEnd == i) {
            this.tokenEnd = this.cursor;
            return true;
        }
        ungetCharIgnoreLineEnd(charIgnoreLineEnd);
        return false;
    }

    private int peekChar() throws IOException {
        int i = getChar();
        ungetChar(i);
        return i;
    }

    private boolean readCDATA() throws IOException {
        int i = getChar();
        while (i != -1) {
            addToString(i);
            if (i == 93 && peekChar() == 93) {
                i = getChar();
                addToString(i);
                if (peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                i = getChar();
            }
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readEntity() throws IOException {
        int i = getChar();
        int i2 = 1;
        while (i != -1) {
            addToString(i);
            if (i == 60) {
                i2++;
            } else if (i == 62 && i2 - 1 == 0) {
                return true;
            }
            i = getChar();
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    private boolean readPI() throws IOException {
        while (true) {
            int i = getChar();
            if (i != -1) {
                addToString(i);
                if (i == 63 && peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return false;
            }
        }
    }

    private boolean readQuotedString(int i) throws IOException {
        int i2;
        do {
            i2 = getChar();
            if (i2 != -1) {
                addToString(i2);
            } else {
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return false;
            }
        } while (i2 != i);
        return true;
    }

    private boolean readXmlComment() throws IOException {
        int i = getChar();
        while (i != -1) {
            addToString(i);
            if (i == 45 && peekChar() == 45) {
                i = getChar();
                addToString(i);
                if (peekChar() == 62) {
                    addToString(getChar());
                    return true;
                }
            } else {
                i = getChar();
            }
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return false;
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    private void skipLine() throws java.io.IOException {
        /*
            r2 = this;
        L_0x0000:
            int r0 = r2.getChar()
            r1 = -1
            if (r0 == r1) goto L_0x000c
            r1 = 10
            if (r0 == r1) goto L_0x000c
            goto L_0x0000
        L_0x000c:
            r2.ungetChar(r0)
            int r0 = r2.cursor
            r2.tokenEnd = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.skipLine():void");
    }

    private static int stringToKeyword(String str, int i, boolean z) {
        if (i < 200) {
            return stringToKeywordForJS(str);
        }
        return stringToKeywordForES(str, z);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x01aa, code lost:
        if (r0.charAt(1) == 'l') goto L_0x027e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0271, code lost:
        if (r1 == null) goto L_0x027d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0273, code lost:
        if (r1 == r0) goto L_0x027d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0279, code lost:
        if (r1.equals(r0) != false) goto L_0x027d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x027b, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x027d, code lost:
        r6 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x027e, code lost:
        if (r6 != 0) goto L_0x0281;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x0280, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x0283, code lost:
        return r6 & 255;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x007b, code lost:
        if (r1 != 'x') goto L_0x026f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0102, code lost:
        r2 = 128;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int stringToKeywordForES(java.lang.String r17, boolean r18) {
        /*
            r0 = r17
            int r1 = r17.length()
            r2 = 112(0x70, float:1.57E-43)
            r4 = 100
            r5 = 109(0x6d, float:1.53E-43)
            r7 = 99
            r10 = 102(0x66, float:1.43E-43)
            r12 = 117(0x75, float:1.64E-43)
            r13 = 97
            r14 = 105(0x69, float:1.47E-43)
            r15 = 116(0x74, float:1.63E-43)
            r11 = 110(0x6e, float:1.54E-43)
            r6 = 114(0x72, float:1.6E-43)
            r8 = 101(0x65, float:1.42E-43)
            r16 = 128(0x80, float:1.794E-43)
            r9 = 1
            r3 = 0
            switch(r1) {
                case 2: goto L_0x0246;
                case 3: goto L_0x01da;
                case 4: goto L_0x013e;
                case 5: goto L_0x00db;
                case 6: goto L_0x009b;
                case 7: goto L_0x006d;
                case 8: goto L_0x004f;
                case 9: goto L_0x003b;
                case 10: goto L_0x0027;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x026f
        L_0x0027:
            char r1 = r0.charAt(r9)
            if (r1 != r5) goto L_0x0033
            if (r18 == 0) goto L_0x0033
            java.lang.String r1 = "implements"
            goto L_0x0102
        L_0x0033:
            if (r1 != r11) goto L_0x026f
            java.lang.String r1 = "instanceof"
            r2 = 53
            goto L_0x0271
        L_0x003b:
            char r1 = r0.charAt(r3)
            if (r1 != r14) goto L_0x0047
            if (r18 == 0) goto L_0x0047
            java.lang.String r1 = "interface"
            goto L_0x0102
        L_0x0047:
            if (r1 != r2) goto L_0x026f
            if (r18 == 0) goto L_0x026f
            java.lang.String r1 = "protected"
            goto L_0x0102
        L_0x004f:
            char r1 = r0.charAt(r3)
            if (r1 == r7) goto L_0x0067
            if (r1 == r4) goto L_0x0061
            if (r1 == r10) goto L_0x005b
            goto L_0x026f
        L_0x005b:
            java.lang.String r1 = "function"
            r2 = 110(0x6e, float:1.54E-43)
            goto L_0x0271
        L_0x0061:
            java.lang.String r1 = "debugger"
            r2 = 161(0xa1, float:2.26E-43)
            goto L_0x0271
        L_0x0067:
            java.lang.String r1 = "continue"
            r2 = 122(0x7a, float:1.71E-43)
            goto L_0x0271
        L_0x006d:
            char r1 = r0.charAt(r9)
            if (r1 == r13) goto L_0x008f
            if (r1 == r8) goto L_0x0095
            if (r1 == r14) goto L_0x0089
            if (r1 == r6) goto L_0x007f
            r2 = 120(0x78, float:1.68E-43)
            if (r1 == r2) goto L_0x0085
            goto L_0x026f
        L_0x007f:
            if (r18 == 0) goto L_0x0085
            java.lang.String r1 = "private"
            goto L_0x0102
        L_0x0085:
            java.lang.String r1 = "extends"
            goto L_0x0102
        L_0x0089:
            java.lang.String r1 = "finally"
            r2 = 126(0x7e, float:1.77E-43)
            goto L_0x0271
        L_0x008f:
            if (r18 == 0) goto L_0x0095
            java.lang.String r1 = "package"
            goto L_0x0102
        L_0x0095:
            java.lang.String r1 = "default"
            r2 = 117(0x75, float:1.64E-43)
            goto L_0x0271
        L_0x009b:
            char r1 = r0.charAt(r9)
            if (r1 == r8) goto L_0x00c8
            if (r1 == r5) goto L_0x00c5
            if (r1 == r15) goto L_0x00b5
            if (r1 == r12) goto L_0x00ba
            switch(r1) {
                case 119: goto L_0x00bf;
                case 120: goto L_0x00b2;
                case 121: goto L_0x00ac;
                default: goto L_0x00aa;
            }
        L_0x00aa:
            goto L_0x026f
        L_0x00ac:
            java.lang.String r1 = "typeof"
            r2 = 32
            goto L_0x0271
        L_0x00b2:
            java.lang.String r1 = "export"
            goto L_0x0102
        L_0x00b5:
            if (r18 == 0) goto L_0x00ba
            java.lang.String r1 = "static"
            goto L_0x0102
        L_0x00ba:
            if (r18 == 0) goto L_0x00bf
            java.lang.String r1 = "public"
            goto L_0x0102
        L_0x00bf:
            java.lang.String r1 = "switch"
            r2 = 115(0x73, float:1.61E-43)
            goto L_0x0271
        L_0x00c5:
            java.lang.String r1 = "import"
            goto L_0x0102
        L_0x00c8:
            char r1 = r0.charAt(r3)
            if (r1 != r4) goto L_0x00d4
            java.lang.String r1 = "delete"
            r2 = 31
            goto L_0x0271
        L_0x00d4:
            if (r1 != r6) goto L_0x026f
            java.lang.String r1 = "return"
            r2 = 4
            goto L_0x0271
        L_0x00db:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 == r13) goto L_0x0130
            if (r1 == r8) goto L_0x0118
            if (r1 == r14) goto L_0x0112
            r4 = 108(0x6c, float:1.51E-43)
            if (r1 == r4) goto L_0x010c
            if (r1 == r11) goto L_0x0106
            if (r1 == r2) goto L_0x0100
            if (r1 == r6) goto L_0x00fa
            if (r1 == r15) goto L_0x00f4
            goto L_0x026f
        L_0x00f4:
            java.lang.String r1 = "catch"
            r2 = 125(0x7d, float:1.75E-43)
            goto L_0x0271
        L_0x00fa:
            java.lang.String r1 = "throw"
            r2 = 50
            goto L_0x0271
        L_0x0100:
            java.lang.String r1 = "super"
        L_0x0102:
            r2 = 128(0x80, float:1.794E-43)
            goto L_0x0271
        L_0x0106:
            java.lang.String r1 = "const"
            r2 = 155(0x9b, float:2.17E-43)
            goto L_0x0271
        L_0x010c:
            java.lang.String r1 = "false"
            r2 = 44
            goto L_0x0271
        L_0x0112:
            java.lang.String r1 = "while"
            r2 = 118(0x76, float:1.65E-43)
            goto L_0x0271
        L_0x0118:
            char r1 = r0.charAt(r3)
            r2 = 98
            if (r1 != r2) goto L_0x0126
            java.lang.String r1 = "break"
            r2 = 121(0x79, float:1.7E-43)
            goto L_0x0271
        L_0x0126:
            r2 = 121(0x79, float:1.7E-43)
            if (r1 != r2) goto L_0x026f
            java.lang.String r1 = "yield"
            r2 = 73
            goto L_0x0271
        L_0x0130:
            char r1 = r0.charAt(r3)
            if (r1 != r7) goto L_0x0139
            java.lang.String r1 = "class"
            goto L_0x0102
        L_0x0139:
            if (r1 != r13) goto L_0x026f
            java.lang.String r1 = "await"
            goto L_0x0102
        L_0x013e:
            char r1 = r0.charAt(r3)
            r2 = 3
            if (r1 == r7) goto L_0x01c1
            if (r1 == r8) goto L_0x0195
            if (r1 == r11) goto L_0x018f
            if (r1 == r15) goto L_0x0161
            r4 = 118(0x76, float:1.65E-43)
            if (r1 == r4) goto L_0x015b
            r2 = 119(0x77, float:1.67E-43)
            if (r1 == r2) goto L_0x0155
            goto L_0x026f
        L_0x0155:
            java.lang.String r1 = "with"
            r2 = 124(0x7c, float:1.74E-43)
            goto L_0x0271
        L_0x015b:
            java.lang.String r1 = "void"
            r2 = 127(0x7f, float:1.78E-43)
            goto L_0x0271
        L_0x0161:
            char r1 = r0.charAt(r2)
            if (r1 != r8) goto L_0x0178
            r2 = 2
            char r1 = r0.charAt(r2)
            if (r1 != r12) goto L_0x026f
            char r1 = r0.charAt(r9)
            if (r1 != r6) goto L_0x026f
            r6 = 45
            goto L_0x027e
        L_0x0178:
            r2 = 2
            r4 = 115(0x73, float:1.61E-43)
            if (r1 != r4) goto L_0x026f
            char r1 = r0.charAt(r2)
            if (r1 != r14) goto L_0x026f
            char r1 = r0.charAt(r9)
            r2 = 104(0x68, float:1.46E-43)
            if (r1 != r2) goto L_0x026f
            r6 = 43
            goto L_0x027e
        L_0x018f:
            java.lang.String r1 = "null"
            r2 = 42
            goto L_0x0271
        L_0x0195:
            char r1 = r0.charAt(r2)
            if (r1 != r8) goto L_0x01ae
            r4 = 2
            char r1 = r0.charAt(r4)
            r2 = 115(0x73, float:1.61E-43)
            if (r1 != r2) goto L_0x026f
            char r1 = r0.charAt(r9)
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 != r2) goto L_0x026f
            goto L_0x027e
        L_0x01ae:
            r4 = 2
            if (r1 != r5) goto L_0x026f
            char r1 = r0.charAt(r4)
            if (r1 != r12) goto L_0x026f
            char r1 = r0.charAt(r9)
            if (r1 != r11) goto L_0x026f
            r6 = 128(0x80, float:1.794E-43)
            goto L_0x027e
        L_0x01c1:
            r4 = 2
            char r1 = r0.charAt(r2)
            if (r1 != r8) goto L_0x026f
            char r1 = r0.charAt(r4)
            r2 = 115(0x73, float:1.61E-43)
            if (r1 != r2) goto L_0x026f
            char r1 = r0.charAt(r9)
            if (r1 != r13) goto L_0x026f
            r6 = 116(0x74, float:1.63E-43)
            goto L_0x027e
        L_0x01da:
            char r1 = r0.charAt(r3)
            if (r1 == r10) goto L_0x0234
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 == r2) goto L_0x0224
            if (r1 == r11) goto L_0x0212
            if (r1 == r15) goto L_0x01ff
            r2 = 118(0x76, float:1.65E-43)
            if (r1 == r2) goto L_0x01ee
            goto L_0x026f
        L_0x01ee:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r6) goto L_0x026f
            char r1 = r0.charAt(r9)
            if (r1 != r13) goto L_0x026f
            r6 = 123(0x7b, float:1.72E-43)
            goto L_0x027e
        L_0x01ff:
            r1 = 2
            char r1 = r0.charAt(r1)
            r2 = 121(0x79, float:1.7E-43)
            if (r1 != r2) goto L_0x026f
            char r1 = r0.charAt(r9)
            if (r1 != r6) goto L_0x026f
            r6 = 82
            goto L_0x027e
        L_0x0212:
            r1 = 2
            char r1 = r0.charAt(r1)
            r2 = 119(0x77, float:1.67E-43)
            if (r1 != r2) goto L_0x026f
            char r1 = r0.charAt(r9)
            if (r1 != r8) goto L_0x026f
            r6 = 30
            goto L_0x027e
        L_0x0224:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r15) goto L_0x026f
            char r1 = r0.charAt(r9)
            if (r1 != r8) goto L_0x026f
            r6 = 154(0x9a, float:2.16E-43)
            goto L_0x027e
        L_0x0234:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r6) goto L_0x026f
            char r1 = r0.charAt(r9)
            r2 = 111(0x6f, float:1.56E-43)
            if (r1 != r2) goto L_0x026f
            r6 = 120(0x78, float:1.68E-43)
            goto L_0x027e
        L_0x0246:
            r2 = 119(0x77, float:1.67E-43)
            char r1 = r0.charAt(r9)
            if (r1 != r10) goto L_0x0257
            char r1 = r0.charAt(r3)
            if (r1 != r14) goto L_0x026f
            r6 = 113(0x71, float:1.58E-43)
            goto L_0x027e
        L_0x0257:
            if (r1 != r11) goto L_0x0262
            char r1 = r0.charAt(r3)
            if (r1 != r14) goto L_0x026f
            r6 = 52
            goto L_0x027e
        L_0x0262:
            r5 = 111(0x6f, float:1.56E-43)
            if (r1 != r5) goto L_0x026f
            char r1 = r0.charAt(r3)
            if (r1 != r4) goto L_0x026f
            r6 = 119(0x77, float:1.67E-43)
            goto L_0x027e
        L_0x026f:
            r1 = 0
            r2 = 0
        L_0x0271:
            if (r1 == 0) goto L_0x027d
            if (r1 == r0) goto L_0x027d
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x027d
            r6 = 0
            goto L_0x027e
        L_0x027d:
            r6 = r2
        L_0x027e:
            if (r6 != 0) goto L_0x0281
            return r3
        L_0x0281:
            r0 = r6 & 255(0xff, float:3.57E-43)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.stringToKeywordForES(java.lang.String, boolean):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:143:0x0207, code lost:
        if (r0.charAt(1) == 'n') goto L_0x02a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0233, code lost:
        if (r0.charAt(1) == 'h') goto L_0x02a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x02a1, code lost:
        if (r0.charAt(1) == 'n') goto L_0x02a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:189:0x02a3, code lost:
        r6 = 128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x02dd, code lost:
        r1 = null;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:209:0x02df, code lost:
        if (r1 == null) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:210:0x02e1, code lost:
        if (r1 == r0) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x02e7, code lost:
        if (r1.equals(r0) != false) goto L_0x02eb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:213:0x02e9, code lost:
        r6 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x02eb, code lost:
        r6 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x02ec, code lost:
        if (r6 != 0) goto L_0x02ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x02ee, code lost:
        return 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x02f1, code lost:
        return r6 & 255;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0029, code lost:
        r2 = 128;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int stringToKeywordForJS(java.lang.String r17) {
        /*
            r0 = r17
            int r1 = r17.length()
            r3 = 100
            r4 = 109(0x6d, float:1.53E-43)
            r7 = 99
            r9 = 111(0x6f, float:1.56E-43)
            r10 = 118(0x76, float:1.65E-43)
            r13 = 102(0x66, float:1.43E-43)
            r14 = 97
            r15 = 110(0x6e, float:1.54E-43)
            r6 = 105(0x69, float:1.47E-43)
            r2 = 116(0x74, float:1.63E-43)
            r12 = 114(0x72, float:1.6E-43)
            r11 = 101(0x65, float:1.42E-43)
            r8 = 1
            r16 = 128(0x80, float:1.794E-43)
            r5 = 0
            switch(r1) {
                case 2: goto L_0x02b6;
                case 3: goto L_0x023b;
                case 4: goto L_0x0171;
                case 5: goto L_0x00fd;
                case 6: goto L_0x00a7;
                case 7: goto L_0x007b;
                case 8: goto L_0x0053;
                case 9: goto L_0x003e;
                case 10: goto L_0x002d;
                case 11: goto L_0x0025;
                case 12: goto L_0x0027;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x02dd
        L_0x0027:
            java.lang.String r1 = "synchronized"
        L_0x0029:
            r2 = 128(0x80, float:1.794E-43)
            goto L_0x02df
        L_0x002d:
            char r1 = r0.charAt(r8)
            if (r1 != r4) goto L_0x0036
            java.lang.String r1 = "implements"
            goto L_0x0029
        L_0x0036:
            if (r1 != r15) goto L_0x02dd
            java.lang.String r1 = "instanceof"
            r2 = 53
            goto L_0x02df
        L_0x003e:
            char r1 = r0.charAt(r5)
            if (r1 != r6) goto L_0x0047
            java.lang.String r1 = "interface"
            goto L_0x0029
        L_0x0047:
            r3 = 112(0x70, float:1.57E-43)
            if (r1 != r3) goto L_0x004e
            java.lang.String r1 = "protected"
            goto L_0x0029
        L_0x004e:
            if (r1 != r2) goto L_0x02dd
            java.lang.String r1 = "transient"
            goto L_0x0029
        L_0x0053:
            char r1 = r0.charAt(r5)
            if (r1 == r14) goto L_0x0078
            if (r1 == r13) goto L_0x0072
            if (r1 == r10) goto L_0x006f
            if (r1 == r7) goto L_0x0069
            if (r1 == r3) goto L_0x0063
            goto L_0x02dd
        L_0x0063:
            java.lang.String r1 = "debugger"
            r2 = 161(0xa1, float:2.26E-43)
            goto L_0x02df
        L_0x0069:
            java.lang.String r1 = "continue"
            r2 = 122(0x7a, float:1.71E-43)
            goto L_0x02df
        L_0x006f:
            java.lang.String r1 = "volatile"
            goto L_0x0029
        L_0x0072:
            java.lang.String r1 = "function"
            r2 = 110(0x6e, float:1.54E-43)
            goto L_0x02df
        L_0x0078:
            java.lang.String r1 = "abstract"
            goto L_0x0029
        L_0x007b:
            char r1 = r0.charAt(r8)
            if (r1 == r14) goto L_0x00a4
            if (r1 == r11) goto L_0x009e
            if (r1 == r6) goto L_0x0098
            if (r1 == r9) goto L_0x0095
            if (r1 == r12) goto L_0x0092
            r2 = 120(0x78, float:1.68E-43)
            if (r1 == r2) goto L_0x008f
            goto L_0x02dd
        L_0x008f:
            java.lang.String r1 = "extends"
            goto L_0x0029
        L_0x0092:
            java.lang.String r1 = "private"
            goto L_0x0029
        L_0x0095:
            java.lang.String r1 = "boolean"
            goto L_0x0029
        L_0x0098:
            java.lang.String r1 = "finally"
            r2 = 126(0x7e, float:1.77E-43)
            goto L_0x02df
        L_0x009e:
            java.lang.String r1 = "default"
            r2 = 117(0x75, float:1.64E-43)
            goto L_0x02df
        L_0x00a4:
            java.lang.String r1 = "package"
            goto L_0x0029
        L_0x00a7:
            char r1 = r0.charAt(r8)
            if (r1 == r14) goto L_0x00f9
            if (r1 == r11) goto L_0x00e6
            r6 = 104(0x68, float:1.46E-43)
            if (r1 == r6) goto L_0x00e2
            if (r1 == r4) goto L_0x00de
            if (r1 == r9) goto L_0x00da
            if (r1 == r2) goto L_0x00d6
            r2 = 117(0x75, float:1.64E-43)
            if (r1 == r2) goto L_0x00d2
            switch(r1) {
                case 119: goto L_0x00cc;
                case 120: goto L_0x00c8;
                case 121: goto L_0x00c2;
                default: goto L_0x00c0;
            }
        L_0x00c0:
            goto L_0x02dd
        L_0x00c2:
            java.lang.String r1 = "typeof"
            r2 = 32
            goto L_0x02df
        L_0x00c8:
            java.lang.String r1 = "export"
            goto L_0x0029
        L_0x00cc:
            java.lang.String r1 = "switch"
            r2 = 115(0x73, float:1.61E-43)
            goto L_0x02df
        L_0x00d2:
            java.lang.String r1 = "public"
            goto L_0x0029
        L_0x00d6:
            java.lang.String r1 = "static"
            goto L_0x0029
        L_0x00da:
            java.lang.String r1 = "double"
            goto L_0x0029
        L_0x00de:
            java.lang.String r1 = "import"
            goto L_0x0029
        L_0x00e2:
            java.lang.String r1 = "throws"
            goto L_0x0029
        L_0x00e6:
            char r1 = r0.charAt(r5)
            if (r1 != r3) goto L_0x00f2
            java.lang.String r1 = "delete"
            r2 = 31
            goto L_0x02df
        L_0x00f2:
            if (r1 != r12) goto L_0x02dd
            java.lang.String r1 = "return"
            r2 = 4
            goto L_0x02df
        L_0x00f9:
            java.lang.String r1 = "native"
            goto L_0x0029
        L_0x00fd:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 == r14) goto L_0x016d
            if (r1 == r11) goto L_0x0155
            if (r1 == r6) goto L_0x014f
            r3 = 108(0x6c, float:1.51E-43)
            if (r1 == r3) goto L_0x0149
            if (r1 == r12) goto L_0x0143
            if (r1 == r2) goto L_0x013d
            switch(r1) {
                case 110: goto L_0x012b;
                case 111: goto L_0x0119;
                case 112: goto L_0x0115;
                default: goto L_0x0113;
            }
        L_0x0113:
            goto L_0x02dd
        L_0x0115:
            java.lang.String r1 = "super"
            goto L_0x0029
        L_0x0119:
            char r1 = r0.charAt(r5)
            if (r1 != r13) goto L_0x0123
            java.lang.String r1 = "float"
            goto L_0x0029
        L_0x0123:
            r2 = 115(0x73, float:1.61E-43)
            if (r1 != r2) goto L_0x02dd
            java.lang.String r1 = "short"
            goto L_0x0029
        L_0x012b:
            char r1 = r0.charAt(r5)
            if (r1 != r7) goto L_0x0137
            java.lang.String r1 = "const"
            r2 = 155(0x9b, float:2.17E-43)
            goto L_0x02df
        L_0x0137:
            if (r1 != r13) goto L_0x02dd
            java.lang.String r1 = "final"
            goto L_0x0029
        L_0x013d:
            java.lang.String r1 = "catch"
            r2 = 125(0x7d, float:1.75E-43)
            goto L_0x02df
        L_0x0143:
            java.lang.String r1 = "throw"
            r2 = 50
            goto L_0x02df
        L_0x0149:
            java.lang.String r1 = "false"
            r2 = 44
            goto L_0x02df
        L_0x014f:
            java.lang.String r1 = "while"
            r2 = 118(0x76, float:1.65E-43)
            goto L_0x02df
        L_0x0155:
            char r1 = r0.charAt(r5)
            r2 = 98
            if (r1 != r2) goto L_0x0163
            java.lang.String r1 = "break"
            r2 = 121(0x79, float:1.7E-43)
            goto L_0x02df
        L_0x0163:
            r2 = 121(0x79, float:1.7E-43)
            if (r1 != r2) goto L_0x02dd
            java.lang.String r1 = "yield"
            r2 = 73
            goto L_0x02df
        L_0x016d:
            java.lang.String r1 = "class"
            goto L_0x0029
        L_0x0171:
            char r1 = r0.charAt(r5)
            r3 = 98
            if (r1 == r3) goto L_0x0237
            if (r1 == r7) goto L_0x020b
            if (r1 == r11) goto L_0x01dc
            r3 = 103(0x67, float:1.44E-43)
            if (r1 == r3) goto L_0x01d8
            r3 = 108(0x6c, float:1.51E-43)
            if (r1 == r3) goto L_0x01d4
            if (r1 == r15) goto L_0x01ce
            if (r1 == r2) goto L_0x019d
            if (r1 == r10) goto L_0x0197
            r2 = 119(0x77, float:1.67E-43)
            if (r1 == r2) goto L_0x0191
            goto L_0x02dd
        L_0x0191:
            java.lang.String r1 = "with"
            r2 = 124(0x7c, float:1.74E-43)
            goto L_0x02df
        L_0x0197:
            java.lang.String r1 = "void"
            r2 = 127(0x7f, float:1.78E-43)
            goto L_0x02df
        L_0x019d:
            r1 = 3
            char r1 = r0.charAt(r1)
            if (r1 != r11) goto L_0x01b7
            r2 = 2
            char r1 = r0.charAt(r2)
            r2 = 117(0x75, float:1.64E-43)
            if (r1 != r2) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r12) goto L_0x02dd
            r6 = 45
            goto L_0x02ec
        L_0x01b7:
            r2 = 2
            r3 = 115(0x73, float:1.61E-43)
            if (r1 != r3) goto L_0x02dd
            char r1 = r0.charAt(r2)
            if (r1 != r6) goto L_0x02dd
            char r1 = r0.charAt(r8)
            r2 = 104(0x68, float:1.46E-43)
            if (r1 != r2) goto L_0x02dd
            r6 = 43
            goto L_0x02ec
        L_0x01ce:
            java.lang.String r1 = "null"
            r2 = 42
            goto L_0x02df
        L_0x01d4:
            java.lang.String r1 = "long"
            goto L_0x0029
        L_0x01d8:
            java.lang.String r1 = "goto"
            goto L_0x0029
        L_0x01dc:
            r1 = 3
            char r1 = r0.charAt(r1)
            if (r1 != r11) goto L_0x01f8
            r3 = 2
            char r1 = r0.charAt(r3)
            r2 = 115(0x73, float:1.61E-43)
            if (r1 != r2) goto L_0x02dd
            char r1 = r0.charAt(r8)
            r2 = 108(0x6c, float:1.51E-43)
            if (r1 != r2) goto L_0x02dd
            r6 = 114(0x72, float:1.6E-43)
            goto L_0x02ec
        L_0x01f8:
            r3 = 2
            if (r1 != r4) goto L_0x02dd
            char r1 = r0.charAt(r3)
            r2 = 117(0x75, float:1.64E-43)
            if (r1 != r2) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r15) goto L_0x02dd
            goto L_0x02a3
        L_0x020b:
            r3 = 2
            r1 = 3
            char r1 = r0.charAt(r1)
            if (r1 != r11) goto L_0x0225
            char r1 = r0.charAt(r3)
            r3 = 115(0x73, float:1.61E-43)
            if (r1 != r3) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r14) goto L_0x02dd
            r6 = 116(0x74, float:1.63E-43)
            goto L_0x02ec
        L_0x0225:
            if (r1 != r12) goto L_0x02dd
            char r1 = r0.charAt(r3)
            if (r1 != r14) goto L_0x02dd
            char r1 = r0.charAt(r8)
            r2 = 104(0x68, float:1.46E-43)
            if (r1 != r2) goto L_0x02dd
            goto L_0x02a3
        L_0x0237:
            java.lang.String r1 = "byte"
            goto L_0x0029
        L_0x023b:
            char r1 = r0.charAt(r5)
            if (r1 == r13) goto L_0x02a6
            if (r1 == r6) goto L_0x0296
            r3 = 108(0x6c, float:1.51E-43)
            if (r1 == r3) goto L_0x0286
            if (r1 == r15) goto L_0x0273
            if (r1 == r2) goto L_0x0260
            if (r1 == r10) goto L_0x024f
            goto L_0x02dd
        L_0x024f:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r12) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r14) goto L_0x02dd
            r6 = 123(0x7b, float:1.72E-43)
            goto L_0x02ec
        L_0x0260:
            r1 = 2
            char r1 = r0.charAt(r1)
            r2 = 121(0x79, float:1.7E-43)
            if (r1 != r2) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r12) goto L_0x02dd
            r6 = 82
            goto L_0x02ec
        L_0x0273:
            r1 = 2
            char r1 = r0.charAt(r1)
            r2 = 119(0x77, float:1.67E-43)
            if (r1 != r2) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r11) goto L_0x02dd
            r6 = 30
            goto L_0x02ec
        L_0x0286:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r2) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r11) goto L_0x02dd
            r6 = 154(0x9a, float:2.16E-43)
            goto L_0x02ec
        L_0x0296:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r2) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r15) goto L_0x02dd
        L_0x02a3:
            r6 = 128(0x80, float:1.794E-43)
            goto L_0x02ec
        L_0x02a6:
            r1 = 2
            char r1 = r0.charAt(r1)
            if (r1 != r12) goto L_0x02dd
            char r1 = r0.charAt(r8)
            if (r1 != r9) goto L_0x02dd
            r6 = 120(0x78, float:1.68E-43)
            goto L_0x02ec
        L_0x02b6:
            r2 = 119(0x77, float:1.67E-43)
            char r1 = r0.charAt(r8)
            if (r1 != r13) goto L_0x02c7
            char r1 = r0.charAt(r5)
            if (r1 != r6) goto L_0x02dd
            r6 = 113(0x71, float:1.58E-43)
            goto L_0x02ec
        L_0x02c7:
            if (r1 != r15) goto L_0x02d2
            char r1 = r0.charAt(r5)
            if (r1 != r6) goto L_0x02dd
            r6 = 52
            goto L_0x02ec
        L_0x02d2:
            if (r1 != r9) goto L_0x02dd
            char r1 = r0.charAt(r5)
            if (r1 != r3) goto L_0x02dd
            r6 = 119(0x77, float:1.67E-43)
            goto L_0x02ec
        L_0x02dd:
            r1 = 0
            r2 = 0
        L_0x02df:
            if (r1 == 0) goto L_0x02eb
            if (r1 == r0) goto L_0x02eb
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x02eb
            r6 = 0
            goto L_0x02ec
        L_0x02eb:
            r6 = r2
        L_0x02ec:
            if (r6 != 0) goto L_0x02ef
            return r5
        L_0x02ef:
            r0 = r6 & 255(0xff, float:3.57E-43)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.stringToKeywordForJS(java.lang.String):int");
    }

    private final String substring(int i, int i2) {
        String str = this.sourceString;
        if (str != null) {
            return str.substring(i, i2);
        }
        return new String(this.sourceBuffer, i, i2 - i);
    }

    private void ungetChar(int i) {
        int i2 = this.ungetCursor;
        if (i2 != 0 && this.ungetBuffer[i2 - 1] == 10) {
            Kit.codeBug();
        }
        int[] iArr = this.ungetBuffer;
        int i3 = this.ungetCursor;
        this.ungetCursor = i3 + 1;
        iArr[i3] = i;
        this.cursor--;
    }

    private void ungetCharIgnoreLineEnd(int i) {
        int[] iArr = this.ungetBuffer;
        int i2 = this.ungetCursor;
        this.ungetCursor = i2 + 1;
        iArr[i2] = i;
        this.cursor--;
    }

    public final boolean eof() {
        return this.hitEOF;
    }

    public final String getAndResetCurrentComment() {
        if (this.sourceString != null) {
            if (isMarkingComment()) {
                Kit.codeBug();
            }
            return this.sourceString.substring(this.tokenBeg, this.tokenEnd);
        }
        if (!isMarkingComment()) {
            Kit.codeBug();
        }
        StringBuilder sb = new StringBuilder(this.commentPrefix);
        sb.append(this.sourceBuffer, this.commentCursor, getTokenLength() - this.commentPrefix.length());
        this.commentCursor = -1;
        return sb.toString();
    }

    public Token.CommentType getCommentType() {
        return this.commentType;
    }

    public int getCursor() {
        return this.cursor;
    }

    public int getFirstXMLToken() throws IOException {
        this.xmlOpenTagsCount = 0;
        this.xmlIsAttribute = false;
        this.xmlIsTagContent = false;
        if (!canUngetChar()) {
            return -1;
        }
        ungetChar(60);
        return getNextXMLToken();
    }

    public final String getLine() {
        int i;
        int i2 = this.sourceCursor;
        int i3 = this.lineEndChar;
        if (i3 >= 0) {
            i = i2 - 1;
            if (i3 == 10 && charAt(i - 1) == 13) {
                i--;
            }
        } else {
            int i4 = i2 - this.lineStart;
            while (true) {
                int charAt = charAt(this.lineStart + i4);
                if (charAt == -1 || ScriptRuntime.isJSLineTerminator(charAt)) {
                    i = i4 + this.lineStart;
                } else {
                    i4++;
                }
            }
            i = i4 + this.lineStart;
        }
        return substring(this.lineStart, i);
    }

    public final int getLineno() {
        return this.lineno;
    }

    public int getNextXMLToken() throws IOException {
        this.tokenBeg = this.cursor;
        this.stringBufferTop = 0;
        while (true) {
            int i = getChar();
            if (i == -1) {
                this.tokenEnd = this.cursor;
                this.stringBufferTop = 0;
                this.string = null;
                this.parser.addError("msg.XML.bad.form");
                return -1;
            } else if (this.xmlIsTagContent) {
                if (i == 9 || i == 10 || i == 13 || i == 32) {
                    addToString(i);
                } else if (i == 34 || i == 39) {
                    addToString(i);
                    if (!readQuotedString(i)) {
                        return -1;
                    }
                } else if (i == 47) {
                    addToString(i);
                    if (peekChar() == 62) {
                        addToString(getChar());
                        this.xmlIsTagContent = false;
                        this.xmlOpenTagsCount--;
                    }
                } else if (i == 123) {
                    ungetChar(i);
                    this.string = getStringFromBuffer();
                    return Token.XML;
                } else if (i == 61) {
                    addToString(i);
                    this.xmlIsAttribute = true;
                } else if (i != 62) {
                    addToString(i);
                    this.xmlIsAttribute = false;
                } else {
                    addToString(i);
                    this.xmlIsTagContent = false;
                    this.xmlIsAttribute = false;
                }
                if (!this.xmlIsTagContent && this.xmlOpenTagsCount == 0) {
                    this.string = getStringFromBuffer();
                    return Token.XMLEND;
                }
            } else if (i == 60) {
                addToString(i);
                int peekChar = peekChar();
                if (peekChar == 33) {
                    addToString(getChar());
                    int peekChar2 = peekChar();
                    if (peekChar2 == 45) {
                        addToString(getChar());
                        int i2 = getChar();
                        if (i2 == 45) {
                            addToString(i2);
                            if (!readXmlComment()) {
                                return -1;
                            }
                        } else {
                            this.stringBufferTop = 0;
                            this.string = null;
                            this.parser.addError("msg.XML.bad.form");
                            return -1;
                        }
                    } else if (peekChar2 == 91) {
                        addToString(getChar());
                        if (getChar() == 67 && getChar() == 68 && getChar() == 65 && getChar() == 84 && getChar() == 65 && getChar() == 91) {
                            addToString(67);
                            addToString(68);
                            addToString(65);
                            addToString(84);
                            addToString(65);
                            addToString(91);
                            if (!readCDATA()) {
                                return -1;
                            }
                        } else {
                            this.stringBufferTop = 0;
                            this.string = null;
                            this.parser.addError("msg.XML.bad.form");
                        }
                    } else if (!readEntity()) {
                        return -1;
                    }
                } else if (peekChar == 47) {
                    addToString(getChar());
                    int i3 = this.xmlOpenTagsCount;
                    if (i3 == 0) {
                        this.stringBufferTop = 0;
                        this.string = null;
                        this.parser.addError("msg.XML.bad.form");
                        return -1;
                    }
                    this.xmlIsTagContent = true;
                    this.xmlOpenTagsCount = i3 - 1;
                } else if (peekChar != 63) {
                    this.xmlIsTagContent = true;
                    this.xmlOpenTagsCount++;
                } else {
                    addToString(getChar());
                    if (!readPI()) {
                        return -1;
                    }
                }
            } else if (i != 123) {
                addToString(i);
            } else {
                ungetChar(i);
                this.string = getStringFromBuffer();
                return Token.XML;
            }
        }
        this.stringBufferTop = 0;
        this.string = null;
        this.parser.addError("msg.XML.bad.form");
        return -1;
    }

    public final double getNumber() {
        return this.number;
    }

    public final int getOffset() {
        int i = this.sourceCursor - this.lineStart;
        if (this.lineEndChar >= 0) {
            return i - 1;
        }
        return i;
    }

    public final char getQuoteChar() {
        return (char) this.quoteChar;
    }

    public final String getSourceString() {
        return this.sourceString;
    }

    public final String getString() {
        return this.string;
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x02a2  */
    /* JADX WARNING: Removed duplicated region for block: B:347:0x04d0  */
    /* JADX WARNING: Removed duplicated region for block: B:351:0x04e0  */
    /* JADX WARNING: Removed duplicated region for block: B:380:0x052d  */
    /* JADX WARNING: Removed duplicated region for block: B:382:0x0533 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:434:0x02ab A[SYNTHETIC] */
    public final int getToken() throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
        L_0x0002:
            int r1 = r16.getChar()
            r2 = -1
            r3 = 0
            if (r1 != r2) goto L_0x0013
            int r1 = r0.cursor
            int r2 = r1 + -1
            r0.tokenBeg = r2
            r0.tokenEnd = r1
            return r3
        L_0x0013:
            r4 = 10
            r5 = 1
            if (r1 != r4) goto L_0x0023
            r0.dirtyLine = r3
            int r1 = r0.cursor
            int r2 = r1 + -1
            r0.tokenBeg = r2
            r0.tokenEnd = r1
            return r5
        L_0x0023:
            boolean r6 = isJSSpace(r1)
            if (r6 != 0) goto L_0x0002
            r6 = 45
            if (r1 == r6) goto L_0x002f
            r0.dirtyLine = r5
        L_0x002f:
            int r7 = r0.cursor
            int r8 = r7 + -1
            r0.tokenBeg = r8
            r0.tokenEnd = r7
            r7 = 64
            if (r1 != r7) goto L_0x003e
            r1 = 148(0x94, float:2.07E-43)
            return r1
        L_0x003e:
            r7 = 117(0x75, float:1.64E-43)
            r8 = 92
            if (r1 != r8) goto L_0x0056
            int r1 = r16.getChar()
            if (r1 != r7) goto L_0x004f
            r0.stringBufferTop = r3
            r9 = 1
            r10 = 1
            goto L_0x0063
        L_0x004f:
            r0.ungetChar(r1)
            r1 = 92
            r9 = 0
            goto L_0x0062
        L_0x0056:
            char r9 = (char) r1
            boolean r9 = java.lang.Character.isJavaIdentifierStart(r9)
            if (r9 == 0) goto L_0x0062
            r0.stringBufferTop = r3
            r0.addToString(r1)
        L_0x0062:
            r10 = 0
        L_0x0063:
            r11 = 4
            r12 = 39
            if (r9 == 0) goto L_0x013e
            r1 = r10
        L_0x0069:
            if (r10 == 0) goto L_0x008c
            r4 = 0
            r6 = 0
        L_0x006d:
            if (r4 == r11) goto L_0x007d
            int r9 = r16.getChar()
            int r6 = org.mozilla.javascript.Kit.xDigitToInt(r9, r6)
            if (r6 >= 0) goto L_0x007a
            goto L_0x007d
        L_0x007a:
            int r4 = r4 + 1
            goto L_0x006d
        L_0x007d:
            if (r6 >= 0) goto L_0x0087
            org.mozilla.javascript.Parser r1 = r0.parser
            java.lang.String r3 = "msg.invalid.escape"
            r1.addError(r3)
            return r2
        L_0x0087:
            r0.addToString(r6)
            r10 = 0
            goto L_0x0069
        L_0x008c:
            int r4 = r16.getChar()
            if (r4 != r8) goto L_0x00a3
            int r1 = r16.getChar()
            if (r1 != r7) goto L_0x009b
            r1 = 1
            r10 = 1
            goto L_0x0069
        L_0x009b:
            org.mozilla.javascript.Parser r3 = r0.parser
            java.lang.String r4 = "msg.illegal.character"
            r3.addError((java.lang.String) r4, (int) r1)
            return r2
        L_0x00a3:
            if (r4 == r2) goto L_0x00b6
            r6 = 65279(0xfeff, float:9.1475E-41)
            if (r4 == r6) goto L_0x00b6
            char r6 = (char) r4
            boolean r6 = java.lang.Character.isJavaIdentifierPart(r6)
            if (r6 != 0) goto L_0x00b2
            goto L_0x00b6
        L_0x00b2:
            r0.addToString(r4)
            goto L_0x0069
        L_0x00b6:
            r0.ungetChar(r4)
            java.lang.String r2 = r16.getStringFromBuffer()
            if (r1 != 0) goto L_0x011b
            org.mozilla.javascript.Parser r1 = r0.parser
            org.mozilla.javascript.CompilerEnvirons r1 = r1.compilerEnv
            int r1 = r1.getLanguageVersion()
            org.mozilla.javascript.Parser r3 = r0.parser
            boolean r3 = r3.inUseStrictDirective()
            int r1 = stringToKeyword(r2, r1, r3)
            if (r1 == 0) goto L_0x0133
            r3 = 154(0x9a, float:2.16E-43)
            if (r1 == r3) goto L_0x00db
            r3 = 73
            if (r1 != r3) goto L_0x00f4
        L_0x00db:
            org.mozilla.javascript.Parser r3 = r0.parser
            org.mozilla.javascript.CompilerEnvirons r3 = r3.compilerEnv
            int r3 = r3.getLanguageVersion()
            r4 = 170(0xaa, float:2.38E-43)
            if (r3 >= r4) goto L_0x00f4
            r3 = 154(0x9a, float:2.16E-43)
            if (r1 != r3) goto L_0x00ee
            java.lang.String r1 = "let"
            goto L_0x00f0
        L_0x00ee:
            java.lang.String r1 = "yield"
        L_0x00f0:
            r0.string = r1
            r1 = 39
        L_0x00f4:
            org.mozilla.javascript.ObjToIntMap r3 = r0.allStrings
            java.lang.Object r3 = r3.intern(r2)
            java.lang.String r3 = (java.lang.String) r3
            r0.string = r3
            r3 = 128(0x80, float:1.794E-43)
            if (r1 == r3) goto L_0x0103
            return r1
        L_0x0103:
            org.mozilla.javascript.Parser r3 = r0.parser
            org.mozilla.javascript.CompilerEnvirons r3 = r3.compilerEnv
            int r3 = r3.getLanguageVersion()
            r4 = 200(0xc8, float:2.8E-43)
            if (r3 < r4) goto L_0x0110
            return r1
        L_0x0110:
            org.mozilla.javascript.Parser r3 = r0.parser
            org.mozilla.javascript.CompilerEnvirons r3 = r3.compilerEnv
            boolean r3 = r3.isReservedKeywordAsIdentifier()
            if (r3 != 0) goto L_0x0133
            return r1
        L_0x011b:
            org.mozilla.javascript.Parser r1 = r0.parser
            org.mozilla.javascript.CompilerEnvirons r1 = r1.compilerEnv
            int r1 = r1.getLanguageVersion()
            org.mozilla.javascript.Parser r3 = r0.parser
            boolean r3 = r3.inUseStrictDirective()
            boolean r1 = isKeyword(r2, r1, r3)
            if (r1 == 0) goto L_0x0133
            java.lang.String r2 = convertLastCharToHex(r2)
        L_0x0133:
            org.mozilla.javascript.ObjToIntMap r1 = r0.allStrings
            java.lang.Object r1 = r1.intern(r2)
            java.lang.String r1 = (java.lang.String) r1
            r0.string = r1
            return r12
        L_0x013e:
            boolean r9 = isDigit(r1)
            r10 = 16
            r13 = 2
            r14 = 120(0x78, float:1.68E-43)
            r7 = 46
            r15 = 48
            if (r9 != 0) goto L_0x0469
            if (r1 != r7) goto L_0x015b
            int r9 = r16.peekChar()
            boolean r9 = isDigit(r9)
            if (r9 == 0) goto L_0x015b
            goto L_0x0469
        L_0x015b:
            r9 = 34
            if (r1 == r9) goto L_0x0378
            if (r1 == r12) goto L_0x0378
            r9 = 96
            if (r1 != r9) goto L_0x0167
            goto L_0x0378
        L_0x0167:
            r9 = 47
            r12 = 33
            r14 = 61
            if (r1 == r12) goto L_0x0365
            r15 = 91
            if (r1 == r15) goto L_0x0362
            r15 = 37
            if (r1 == r15) goto L_0x0356
            r15 = 38
            if (r1 == r15) goto L_0x033f
            r15 = 93
            if (r1 == r15) goto L_0x033c
            r15 = 94
            if (r1 == r15) goto L_0x0332
            r4 = 62
            r15 = 162(0xa2, float:2.27E-43)
            switch(r1) {
                case 40: goto L_0x032f;
                case 41: goto L_0x032c;
                case 42: goto L_0x0320;
                case 43: goto L_0x0309;
                case 44: goto L_0x0306;
                case 45: goto L_0x02d8;
                case 46: goto L_0x02c1;
                case 47: goto L_0x0259;
                default: goto L_0x018a;
            }
        L_0x018a:
            switch(r1) {
                case 58: goto L_0x024b;
                case 59: goto L_0x0248;
                case 60: goto L_0x0203;
                case 61: goto L_0x01e7;
                case 62: goto L_0x01b9;
                case 63: goto L_0x01b6;
                default: goto L_0x018d;
            }
        L_0x018d:
            switch(r1) {
                case 123: goto L_0x01b3;
                case 124: goto L_0x019e;
                case 125: goto L_0x019b;
                case 126: goto L_0x0198;
                default: goto L_0x0190;
            }
        L_0x0190:
            org.mozilla.javascript.Parser r3 = r0.parser
            java.lang.String r4 = "msg.illegal.character"
            r3.addError((java.lang.String) r4, (int) r1)
            return r2
        L_0x0198:
            r1 = 27
            return r1
        L_0x019b:
            r1 = 87
            return r1
        L_0x019e:
            r1 = 124(0x7c, float:1.74E-43)
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x01a9
            r1 = 105(0x69, float:1.47E-43)
            return r1
        L_0x01a9:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x01b0
            return r8
        L_0x01b0:
            r1 = 9
            return r1
        L_0x01b3:
            r1 = 86
            return r1
        L_0x01b6:
            r1 = 103(0x67, float:1.44E-43)
            return r1
        L_0x01b9:
            boolean r1 = r0.matchChar(r4)
            if (r1 == 0) goto L_0x01dd
            boolean r1 = r0.matchChar(r4)
            if (r1 == 0) goto L_0x01d1
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x01ce
            r1 = 97
            return r1
        L_0x01ce:
            r1 = 20
            return r1
        L_0x01d1:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x01da
            r1 = 96
            return r1
        L_0x01da:
            r1 = 19
            return r1
        L_0x01dd:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x01e6
            r1 = 17
            return r1
        L_0x01e6:
            return r10
        L_0x01e7:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x01f7
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x01f4
            return r7
        L_0x01f4:
            r1 = 12
            return r1
        L_0x01f7:
            boolean r1 = r0.matchChar(r4)
            if (r1 == 0) goto L_0x0200
            r1 = 165(0xa5, float:2.31E-43)
            return r1
        L_0x0200:
            r1 = 91
            return r1
        L_0x0203:
            boolean r1 = r0.matchChar(r12)
            if (r1 == 0) goto L_0x0228
            boolean r1 = r0.matchChar(r6)
            if (r1 == 0) goto L_0x0225
            boolean r1 = r0.matchChar(r6)
            if (r1 == 0) goto L_0x0222
            int r1 = r0.cursor
            int r1 = r1 - r11
            r0.tokenBeg = r1
            r16.skipLine()
            org.mozilla.javascript.Token$CommentType r1 = org.mozilla.javascript.Token.CommentType.HTML
            r0.commentType = r1
            return r15
        L_0x0222:
            r0.ungetCharIgnoreLineEnd(r6)
        L_0x0225:
            r0.ungetCharIgnoreLineEnd(r12)
        L_0x0228:
            r1 = 60
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x023c
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x0239
            r1 = 95
            return r1
        L_0x0239:
            r1 = 18
            return r1
        L_0x023c:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x0245
            r1 = 15
            return r1
        L_0x0245:
            r1 = 14
            return r1
        L_0x0248:
            r1 = 83
            return r1
        L_0x024b:
            r1 = 58
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x0256
            r1 = 145(0x91, float:2.03E-43)
            return r1
        L_0x0256:
            r1 = 104(0x68, float:1.46E-43)
            return r1
        L_0x0259:
            r16.markCommentStart()
            boolean r1 = r0.matchChar(r9)
            if (r1 == 0) goto L_0x026f
            int r1 = r0.cursor
            int r1 = r1 - r13
            r0.tokenBeg = r1
            r16.skipLine()
            org.mozilla.javascript.Token$CommentType r1 = org.mozilla.javascript.Token.CommentType.LINE
            r0.commentType = r1
            return r15
        L_0x026f:
            r1 = 42
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x02b5
            int r1 = r0.cursor
            int r1 = r1 - r13
            r0.tokenBeg = r1
            r1 = 42
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x028a
            org.mozilla.javascript.Token$CommentType r1 = org.mozilla.javascript.Token.CommentType.JSDOC
            r0.commentType = r1
        L_0x0288:
            r1 = 1
            goto L_0x028f
        L_0x028a:
            org.mozilla.javascript.Token$CommentType r1 = org.mozilla.javascript.Token.CommentType.BLOCK_COMMENT
            r0.commentType = r1
        L_0x028e:
            r1 = 0
        L_0x028f:
            int r4 = r16.getChar()
            if (r4 != r2) goto L_0x02a2
            int r1 = r0.cursor
            int r1 = r1 - r5
            r0.tokenEnd = r1
            org.mozilla.javascript.Parser r1 = r0.parser
            java.lang.String r2 = "msg.unterminated.comment"
            r1.addError(r2)
            return r15
        L_0x02a2:
            r6 = 42
            if (r4 != r6) goto L_0x02a7
            goto L_0x0288
        L_0x02a7:
            if (r4 != r9) goto L_0x02b0
            if (r1 == 0) goto L_0x028f
            int r1 = r0.cursor
            r0.tokenEnd = r1
            return r15
        L_0x02b0:
            int r1 = r0.cursor
            r0.tokenEnd = r1
            goto L_0x028e
        L_0x02b5:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x02be
            r1 = 101(0x65, float:1.42E-43)
            return r1
        L_0x02be:
            r1 = 24
            return r1
        L_0x02c1:
            boolean r1 = r0.matchChar(r7)
            if (r1 == 0) goto L_0x02ca
            r1 = 144(0x90, float:2.02E-43)
            return r1
        L_0x02ca:
            r1 = 40
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x02d5
            r1 = 147(0x93, float:2.06E-43)
            return r1
        L_0x02d5:
            r1 = 109(0x6d, float:1.53E-43)
            return r1
        L_0x02d8:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x02e1
            r1 = 99
            goto L_0x0303
        L_0x02e1:
            boolean r1 = r0.matchChar(r6)
            if (r1 == 0) goto L_0x0301
            boolean r1 = r0.dirtyLine
            if (r1 != 0) goto L_0x02fe
            boolean r1 = r0.matchChar(r4)
            if (r1 == 0) goto L_0x02fe
            java.lang.String r1 = "--"
            r0.markCommentStart(r1)
            r16.skipLine()
            org.mozilla.javascript.Token$CommentType r1 = org.mozilla.javascript.Token.CommentType.HTML
            r0.commentType = r1
            return r15
        L_0x02fe:
            r1 = 108(0x6c, float:1.51E-43)
            goto L_0x0303
        L_0x0301:
            r1 = 22
        L_0x0303:
            r0.dirtyLine = r5
            return r1
        L_0x0306:
            r1 = 90
            return r1
        L_0x0309:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x0312
            r1 = 98
            return r1
        L_0x0312:
            r1 = 43
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x031d
            r1 = 107(0x6b, float:1.5E-43)
            return r1
        L_0x031d:
            r1 = 21
            return r1
        L_0x0320:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x0329
            r1 = 100
            return r1
        L_0x0329:
            r1 = 23
            return r1
        L_0x032c:
            r1 = 89
            return r1
        L_0x032f:
            r1 = 88
            return r1
        L_0x0332:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x033b
            r1 = 93
            return r1
        L_0x033b:
            return r4
        L_0x033c:
            r1 = 85
            return r1
        L_0x033f:
            r1 = 38
            boolean r1 = r0.matchChar(r1)
            if (r1 == 0) goto L_0x034a
            r1 = 106(0x6a, float:1.49E-43)
            return r1
        L_0x034a:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x0353
            r1 = 94
            return r1
        L_0x0353:
            r1 = 11
            return r1
        L_0x0356:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x035f
            r1 = 102(0x66, float:1.43E-43)
            return r1
        L_0x035f:
            r1 = 25
            return r1
        L_0x0362:
            r1 = 84
            return r1
        L_0x0365:
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x0375
            boolean r1 = r0.matchChar(r14)
            if (r1 == 0) goto L_0x0372
            return r9
        L_0x0372:
            r1 = 13
            return r1
        L_0x0375:
            r1 = 26
            return r1
        L_0x0378:
            r0.quoteChar = r1
            r0.stringBufferTop = r3
            int r1 = r0.getChar(r3)
        L_0x0380:
            int r5 = r0.quoteChar
            if (r1 == r5) goto L_0x0458
            if (r1 == r4) goto L_0x0449
            if (r1 != r2) goto L_0x038a
            goto L_0x0449
        L_0x038a:
            if (r1 != r8) goto L_0x043e
            int r1 = r16.getChar()
            if (r1 == r4) goto L_0x0436
            r5 = 98
            if (r1 == r5) goto L_0x0431
            r5 = 102(0x66, float:1.43E-43)
            if (r1 == r5) goto L_0x042c
            r5 = 110(0x6e, float:1.54E-43)
            if (r1 == r5) goto L_0x0427
            r5 = 114(0x72, float:1.6E-43)
            if (r1 == r5) goto L_0x0422
            if (r1 == r14) goto L_0x03fe
            switch(r1) {
                case 116: goto L_0x03f9;
                case 117: goto L_0x03d8;
                case 118: goto L_0x03d4;
                default: goto L_0x03a7;
            }
        L_0x03a7:
            if (r15 > r1) goto L_0x043e
            r5 = 56
            if (r1 >= r5) goto L_0x043e
            int r1 = r1 + -48
            int r6 = r16.getChar()
            if (r15 > r6) goto L_0x03cf
            if (r6 >= r5) goto L_0x03cf
            int r1 = r1 * 8
            int r1 = r1 + r6
            int r1 = r1 - r15
            int r6 = r16.getChar()
            if (r15 > r6) goto L_0x03cf
            if (r6 >= r5) goto L_0x03cf
            r5 = 31
            if (r1 > r5) goto L_0x03cf
            int r1 = r1 * 8
            int r1 = r1 + r6
            int r1 = r1 - r15
            int r6 = r16.getChar()
        L_0x03cf:
            r0.ungetChar(r6)
            goto L_0x043e
        L_0x03d4:
            r1 = 11
            goto L_0x043e
        L_0x03d8:
            int r1 = r0.stringBufferTop
            r5 = 117(0x75, float:1.64E-43)
            r0.addToString(r5)
            r6 = 0
            r7 = 0
        L_0x03e1:
            if (r7 == r11) goto L_0x03f5
            int r9 = r16.getChar()
            int r6 = org.mozilla.javascript.Kit.xDigitToInt(r9, r6)
            if (r6 >= 0) goto L_0x03ef
            r1 = r9
            goto L_0x0380
        L_0x03ef:
            r0.addToString(r9)
            int r7 = r7 + 1
            goto L_0x03e1
        L_0x03f5:
            r0.stringBufferTop = r1
        L_0x03f7:
            r1 = r6
            goto L_0x0440
        L_0x03f9:
            r5 = 117(0x75, float:1.64E-43)
            r1 = 9
            goto L_0x0440
        L_0x03fe:
            r5 = 117(0x75, float:1.64E-43)
            int r1 = r16.getChar()
            int r6 = org.mozilla.javascript.Kit.xDigitToInt(r1, r3)
            if (r6 >= 0) goto L_0x040f
            r0.addToString(r14)
            goto L_0x0380
        L_0x040f:
            int r7 = r16.getChar()
            int r6 = org.mozilla.javascript.Kit.xDigitToInt(r7, r6)
            if (r6 >= 0) goto L_0x03f7
            r0.addToString(r14)
            r0.addToString(r1)
            r1 = r7
            goto L_0x0380
        L_0x0422:
            r5 = 117(0x75, float:1.64E-43)
            r1 = 13
            goto L_0x0440
        L_0x0427:
            r5 = 117(0x75, float:1.64E-43)
            r1 = 10
            goto L_0x0440
        L_0x042c:
            r5 = 117(0x75, float:1.64E-43)
            r1 = 12
            goto L_0x0440
        L_0x0431:
            r5 = 117(0x75, float:1.64E-43)
            r1 = 8
            goto L_0x0440
        L_0x0436:
            r5 = 117(0x75, float:1.64E-43)
            int r1 = r16.getChar()
            goto L_0x0380
        L_0x043e:
            r5 = 117(0x75, float:1.64E-43)
        L_0x0440:
            r0.addToString(r1)
            int r1 = r0.getChar(r3)
            goto L_0x0380
        L_0x0449:
            r0.ungetChar(r1)
            int r1 = r0.cursor
            r0.tokenEnd = r1
            org.mozilla.javascript.Parser r1 = r0.parser
            java.lang.String r3 = "msg.unterminated.string.lit"
            r1.addError(r3)
            return r2
        L_0x0458:
            java.lang.String r1 = r16.getStringFromBuffer()
            org.mozilla.javascript.ObjToIntMap r2 = r0.allStrings
            java.lang.Object r1 = r2.intern(r1)
            java.lang.String r1 = (java.lang.String) r1
            r0.string = r1
            r1 = 41
            return r1
        L_0x0469:
            r0.stringBufferTop = r3
            r0.isBinary = r3
            r0.isOctal = r3
            r0.isOldOctal = r3
            r0.isHex = r3
            org.mozilla.javascript.Parser r8 = r0.parser
            org.mozilla.javascript.CompilerEnvirons r8 = r8.compilerEnv
            int r8 = r8.getLanguageVersion()
            r9 = 200(0xc8, float:2.8E-43)
            if (r8 < r9) goto L_0x0481
            r8 = 1
            goto L_0x0482
        L_0x0481:
            r8 = 0
        L_0x0482:
            if (r1 != r15) goto L_0x04ca
            int r1 = r16.getChar()
            if (r1 == r14) goto L_0x04c1
            r9 = 88
            if (r1 != r9) goto L_0x048f
            goto L_0x04c1
        L_0x048f:
            if (r8 == 0) goto L_0x04a2
            r9 = 111(0x6f, float:1.56E-43)
            if (r1 == r9) goto L_0x0499
            r9 = 79
            if (r1 != r9) goto L_0x04a2
        L_0x0499:
            r0.isOctal = r5
            int r1 = r16.getChar()
        L_0x049f:
            r8 = 8
            goto L_0x04cc
        L_0x04a2:
            if (r8 == 0) goto L_0x04b4
            r8 = 98
            if (r1 == r8) goto L_0x04ac
            r8 = 66
            if (r1 != r8) goto L_0x04b4
        L_0x04ac:
            r0.isBinary = r5
            int r1 = r16.getChar()
            r8 = 2
            goto L_0x04cc
        L_0x04b4:
            boolean r8 = isDigit(r1)
            if (r8 == 0) goto L_0x04bd
            r0.isOldOctal = r5
            goto L_0x049f
        L_0x04bd:
            r0.addToString(r15)
            goto L_0x04ca
        L_0x04c1:
            r0.isHex = r5
            int r1 = r16.getChar()
            r8 = 16
            goto L_0x04cc
        L_0x04ca:
            r8 = 10
        L_0x04cc:
            java.lang.String r9 = "msg.caught.nfe"
            if (r8 != r10) goto L_0x04e0
            r10 = 1
        L_0x04d1:
            int r11 = org.mozilla.javascript.Kit.xDigitToInt(r1, r3)
            if (r11 < 0) goto L_0x051f
            r0.addToString(r1)
            int r1 = r16.getChar()
            r10 = 0
            goto L_0x04d1
        L_0x04e0:
            r10 = 1
        L_0x04e1:
            if (r15 > r1) goto L_0x051f
            r11 = 57
            if (r1 > r11) goto L_0x051f
            r11 = 8
            r12 = 56
            if (r8 != r11) goto L_0x050a
            if (r1 < r12) goto L_0x050a
            boolean r8 = r0.isOldOctal
            if (r8 == 0) goto L_0x0504
            org.mozilla.javascript.Parser r8 = r0.parser
            if (r1 != r12) goto L_0x04fa
            java.lang.String r10 = "8"
            goto L_0x04fc
        L_0x04fa:
            java.lang.String r10 = "9"
        L_0x04fc:
            java.lang.String r14 = "msg.bad.octal.literal"
            r8.addWarning(r14, r10)
            r8 = 10
            goto L_0x0516
        L_0x0504:
            org.mozilla.javascript.Parser r1 = r0.parser
            r1.addError(r9)
            return r2
        L_0x050a:
            if (r8 != r13) goto L_0x0516
            r10 = 50
            if (r1 < r10) goto L_0x0516
            org.mozilla.javascript.Parser r1 = r0.parser
            r1.addError(r9)
            return r2
        L_0x0516:
            r0.addToString(r1)
            int r1 = r16.getChar()
            r10 = 0
            goto L_0x04e1
        L_0x051f:
            if (r10 == 0) goto L_0x0533
            boolean r10 = r0.isBinary
            if (r10 != 0) goto L_0x052d
            boolean r10 = r0.isOctal
            if (r10 != 0) goto L_0x052d
            boolean r10 = r0.isHex
            if (r10 == 0) goto L_0x0533
        L_0x052d:
            org.mozilla.javascript.Parser r1 = r0.parser
            r1.addError(r9)
            return r2
        L_0x0533:
            if (r8 != r4) goto L_0x0589
            if (r1 == r7) goto L_0x053f
            r10 = 101(0x65, float:1.42E-43)
            if (r1 == r10) goto L_0x053f
            r10 = 69
            if (r1 != r10) goto L_0x0589
        L_0x053f:
            if (r1 != r7) goto L_0x054e
        L_0x0541:
            r0.addToString(r1)
            int r1 = r16.getChar()
            boolean r5 = isDigit(r1)
            if (r5 != 0) goto L_0x0541
        L_0x054e:
            r5 = 101(0x65, float:1.42E-43)
            if (r1 == r5) goto L_0x0559
            r5 = 69
            if (r1 != r5) goto L_0x0557
            goto L_0x0559
        L_0x0557:
            r5 = 0
            goto L_0x0589
        L_0x0559:
            r0.addToString(r1)
            int r1 = r16.getChar()
            r5 = 43
            if (r1 == r5) goto L_0x0566
            if (r1 != r6) goto L_0x056d
        L_0x0566:
            r0.addToString(r1)
            int r1 = r16.getChar()
        L_0x056d:
            boolean r5 = isDigit(r1)
            if (r5 != 0) goto L_0x057b
            org.mozilla.javascript.Parser r1 = r0.parser
            java.lang.String r3 = "msg.missing.exponent"
            r1.addError(r3)
            return r2
        L_0x057b:
            r0.addToString(r1)
            int r1 = r16.getChar()
            boolean r5 = isDigit(r1)
            if (r5 != 0) goto L_0x057b
            goto L_0x0557
        L_0x0589:
            r0.ungetChar(r1)
            java.lang.String r1 = r16.getStringFromBuffer()
            r0.string = r1
            if (r8 != r4) goto L_0x05a1
            if (r5 != 0) goto L_0x05a1
            double r1 = java.lang.Double.parseDouble(r1)     // Catch:{ NumberFormatException -> 0x059b }
            goto L_0x05a5
        L_0x059b:
            org.mozilla.javascript.Parser r1 = r0.parser
            r1.addError(r9)
            return r2
        L_0x05a1:
            double r1 = org.mozilla.javascript.ScriptRuntime.stringPrefixToNumber(r1, r3, r8)
        L_0x05a5:
            r0.number = r1
            r1 = 40
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.getToken():int");
    }

    public int getTokenBeg() {
        return this.tokenBeg;
    }

    public int getTokenEnd() {
        return this.tokenEnd;
    }

    public int getTokenLength() {
        return this.tokenEnd - this.tokenBeg;
    }

    public final boolean isNumberBinary() {
        return this.isBinary;
    }

    public final boolean isNumberHex() {
        return this.isHex;
    }

    public final boolean isNumberOctal() {
        return this.isOctal;
    }

    public final boolean isNumberOldOctal() {
        return this.isOldOctal;
    }

    public boolean isXMLAttribute() {
        return this.xmlIsAttribute;
    }

    public String readAndClearRegExpFlags() {
        String str = this.regExpFlags;
        this.regExpFlags = null;
        return str;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00fb, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void readRegExp(int r9) throws java.io.IOException {
        /*
            r8 = this;
            int r0 = r8.tokenBeg
            r1 = 0
            r8.stringBufferTop = r1
            r2 = 101(0x65, float:1.42E-43)
            java.lang.String r3 = "msg.unterminated.re.lit"
            r4 = 1
            if (r9 != r2) goto L_0x0012
            r9 = 61
            r8.addToString(r9)
            goto L_0x0037
        L_0x0012:
            r2 = 24
            if (r9 == r2) goto L_0x0019
            org.mozilla.javascript.Kit.codeBug()
        L_0x0019:
            int r9 = r8.peekChar()
            r2 = 42
            if (r9 != r2) goto L_0x0037
            int r9 = r8.cursor
            int r9 = r9 - r4
            r8.tokenEnd = r9
            java.lang.String r9 = new java.lang.String
            char[] r0 = r8.stringBuffer
            int r2 = r8.stringBufferTop
            r9.<init>(r0, r1, r2)
            r8.string = r9
            org.mozilla.javascript.Parser r9 = r8.parser
            r9.reportError(r3)
            return
        L_0x0037:
            r9 = 0
        L_0x0038:
            int r2 = r8.getChar()
            r5 = 47
            if (r2 != r5) goto L_0x00a3
            if (r9 == 0) goto L_0x0043
            goto L_0x00a3
        L_0x0043:
            int r9 = r8.stringBufferTop
        L_0x0045:
            r2 = 103(0x67, float:1.44E-43)
            boolean r3 = r8.matchChar(r2)
            if (r3 == 0) goto L_0x0051
            r8.addToString(r2)
            goto L_0x0045
        L_0x0051:
            r2 = 105(0x69, float:1.47E-43)
            boolean r3 = r8.matchChar(r2)
            if (r3 == 0) goto L_0x005d
            r8.addToString(r2)
            goto L_0x0045
        L_0x005d:
            r2 = 109(0x6d, float:1.53E-43)
            boolean r3 = r8.matchChar(r2)
            if (r3 == 0) goto L_0x0069
            r8.addToString(r2)
            goto L_0x0045
        L_0x0069:
            r2 = 121(0x79, float:1.7E-43)
            boolean r3 = r8.matchChar(r2)
            if (r3 == 0) goto L_0x0075
            r8.addToString(r2)
            goto L_0x0045
        L_0x0075:
            int r2 = r8.stringBufferTop
            int r0 = r0 + r2
            int r0 = r0 + 2
            r8.tokenEnd = r0
            int r0 = r8.peekChar()
            boolean r0 = isAlpha(r0)
            if (r0 == 0) goto L_0x008d
            org.mozilla.javascript.Parser r0 = r8.parser
            java.lang.String r2 = "msg.invalid.re.flag"
            r0.reportError(r2)
        L_0x008d:
            java.lang.String r0 = new java.lang.String
            char[] r2 = r8.stringBuffer
            r0.<init>(r2, r1, r9)
            r8.string = r0
            java.lang.String r0 = new java.lang.String
            char[] r1 = r8.stringBuffer
            int r2 = r8.stringBufferTop
            int r2 = r2 - r9
            r0.<init>(r1, r9, r2)
            r8.regExpFlags = r0
            return
        L_0x00a3:
            r5 = 10
            if (r2 == r5) goto L_0x00e3
            r6 = -1
            if (r2 != r6) goto L_0x00ab
            goto L_0x00e3
        L_0x00ab:
            r7 = 92
            if (r2 != r7) goto L_0x00d3
            r8.addToString(r2)
            int r2 = r8.getChar()
            if (r2 == r5) goto L_0x00ba
            if (r2 != r6) goto L_0x00de
        L_0x00ba:
            r8.ungetChar(r2)
            int r9 = r8.cursor
            int r9 = r9 - r4
            r8.tokenEnd = r9
            java.lang.String r9 = new java.lang.String
            char[] r0 = r8.stringBuffer
            int r2 = r8.stringBufferTop
            r9.<init>(r0, r1, r2)
            r8.string = r9
            org.mozilla.javascript.Parser r9 = r8.parser
            r9.reportError(r3)
            return
        L_0x00d3:
            r5 = 91
            if (r2 != r5) goto L_0x00d9
            r9 = 1
            goto L_0x00de
        L_0x00d9:
            r5 = 93
            if (r2 != r5) goto L_0x00de
            r9 = 0
        L_0x00de:
            r8.addToString(r2)
            goto L_0x0038
        L_0x00e3:
            r8.ungetChar(r2)
            int r9 = r8.cursor
            int r9 = r9 - r4
            r8.tokenEnd = r9
            java.lang.String r9 = new java.lang.String
            char[] r0 = r8.stringBuffer
            int r2 = r8.stringBufferTop
            r9.<init>(r0, r1, r2)
            r8.string = r9
            org.mozilla.javascript.Parser r9 = r8.parser
            r9.reportError(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.TokenStream.readRegExp(int):void");
    }

    public String tokenToString(int i) {
        return "";
    }

    private int getChar(boolean z) throws IOException {
        char c;
        int i = this.ungetCursor;
        if (i != 0) {
            this.cursor++;
            int[] iArr = this.ungetBuffer;
            int i2 = i - 1;
            this.ungetCursor = i2;
            return iArr[i2];
        }
        while (true) {
            String str = this.sourceString;
            if (str != null) {
                int i3 = this.sourceCursor;
                if (i3 == this.sourceEnd) {
                    this.hitEOF = true;
                    return -1;
                }
                this.cursor++;
                this.sourceCursor = i3 + 1;
                c = str.charAt(i3);
            } else if (this.sourceCursor != this.sourceEnd || fillSourceBuffer()) {
                this.cursor++;
                char[] cArr = this.sourceBuffer;
                int i4 = this.sourceCursor;
                this.sourceCursor = i4 + 1;
                c = cArr[i4];
            } else {
                this.hitEOF = true;
                return -1;
            }
            int i5 = this.lineEndChar;
            if (i5 >= 0) {
                if (i5 == 13 && c == 10) {
                    this.lineEndChar = 10;
                } else {
                    this.lineEndChar = -1;
                    this.lineStart = this.sourceCursor - 1;
                    this.lineno++;
                }
            }
            if (c <= 127) {
                if (c != 10 && c != 13) {
                    return c;
                }
                this.lineEndChar = c;
            } else if (c == 65279) {
                return c;
            } else {
                if (!z || !isJSFormatChar(c)) {
                }
            }
        }
        if (!ScriptRuntime.isJSLineTerminator(c)) {
            return c;
        }
        this.lineEndChar = c;
        return 10;
    }

    private void markCommentStart(String str) {
        if (this.parser.compilerEnv.isRecordingComments() && this.sourceReader != null) {
            this.commentPrefix = str;
            this.commentCursor = this.sourceCursor - 1;
        }
    }

    public final String getLine(int i, int[] iArr) {
        int i2 = (this.cursor + this.ungetCursor) - i;
        int i3 = this.sourceCursor;
        if (i2 > i3) {
            return null;
        }
        int i4 = 0;
        int i5 = 0;
        while (i2 > 0) {
            int charAt = charAt(i3 - 1);
            if (ScriptRuntime.isJSLineTerminator(charAt)) {
                if (charAt == 10 && charAt(i3 - 2) == 13) {
                    i2--;
                    i3--;
                }
                i4++;
                i5 = i3 - 1;
            }
            i2--;
            i3--;
        }
        int i6 = 0;
        while (true) {
            if (i3 <= 0) {
                i3 = 0;
                break;
            } else if (ScriptRuntime.isJSLineTerminator(charAt(i3 - 1))) {
                break;
            } else {
                i3--;
                i6++;
            }
        }
        iArr[0] = (this.lineno - i4) + (this.lineEndChar >= 0 ? 1 : 0);
        iArr[1] = i6;
        if (i4 == 0) {
            return getLine();
        }
        return substring(i3, i5);
    }
}
