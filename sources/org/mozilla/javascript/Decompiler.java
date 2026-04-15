package org.mozilla.javascript;

import org.eclipse.paho.client.mqttv3.MqttTopic;

public class Decompiler {
    public static final int CASE_GAP_PROP = 3;
    private static final int FUNCTION_END = 168;
    public static final int INDENT_GAP_PROP = 2;
    public static final int INITIAL_INDENT_PROP = 1;
    public static final int ONLY_BODY_FLAG = 1;
    public static final int TO_SOURCE_FLAG = 2;
    private static final boolean printSource = false;
    private char[] sourceBuffer = new char[128];
    private int sourceTop;

    private void append(char c) {
        int i = this.sourceTop;
        if (i == this.sourceBuffer.length) {
            increaseSourceCapacity(i + 1);
        }
        char[] cArr = this.sourceBuffer;
        int i2 = this.sourceTop;
        cArr[i2] = c;
        this.sourceTop = i2 + 1;
    }

    private void appendString(String str) {
        int i;
        int length = str.length();
        if (length >= 32768) {
            i = 2;
        } else {
            i = 1;
        }
        int i2 = this.sourceTop + i + length;
        if (i2 > this.sourceBuffer.length) {
            increaseSourceCapacity(i2);
        }
        if (length >= 32768) {
            char[] cArr = this.sourceBuffer;
            int i3 = this.sourceTop;
            cArr[i3] = (char) (32768 | (length >>> 16));
            this.sourceTop = i3 + 1;
        }
        char[] cArr2 = this.sourceBuffer;
        int i4 = this.sourceTop;
        cArr2[i4] = (char) length;
        int i5 = i4 + 1;
        this.sourceTop = i5;
        str.getChars(0, length, cArr2, i5);
        this.sourceTop = i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:132:0x0255, code lost:
        if (r6 != FUNCTION_END) goto L_0x048f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00eb, code lost:
        if (r0.charAt(r13) != 152) goto L_0x00f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00ed, code lost:
        r9.append("get ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00f9, code lost:
        if (r0.charAt(r13) != 153) goto L_0x0100;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00fb, code lost:
        r9.append("set ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x0100, code lost:
        r13 = printSourceString(r0, (r13 + 1) + 1, false, r9) + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String decompile(java.lang.String r18, int r19, org.mozilla.javascript.UintMap r20) {
        /*
            r0 = r18
            r1 = r20
            int r2 = r18.length()
            if (r2 != 0) goto L_0x000d
            java.lang.String r0 = ""
            return r0
        L_0x000d:
            r3 = 1
            r4 = 0
            int r5 = r1.getInt(r3, r4)
            if (r5 < 0) goto L_0x04b5
            r6 = 2
            r7 = 4
            int r8 = r1.getInt(r6, r7)
            if (r8 < 0) goto L_0x04af
            r9 = 3
            int r1 = r1.getInt(r9, r6)
            if (r1 < 0) goto L_0x04a9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r10 = r19 & 1
            if (r10 == 0) goto L_0x002f
            r10 = 1
            goto L_0x0030
        L_0x002f:
            r10 = 0
        L_0x0030:
            r11 = r19 & 2
            if (r11 == 0) goto L_0x0036
            r11 = 1
            goto L_0x0037
        L_0x0036:
            r11 = 0
        L_0x0037:
            char r12 = r0.charAt(r4)
            r13 = 137(0x89, float:1.92E-43)
            if (r12 != r13) goto L_0x0042
            r12 = -1
            r13 = 1
            goto L_0x0047
        L_0x0042:
            char r12 = r0.charAt(r3)
            r13 = 0
        L_0x0047:
            r14 = 40
            r15 = 10
            r4 = 32
            if (r11 != 0) goto L_0x005b
            r9.append(r15)
            r15 = 0
        L_0x0053:
            if (r15 >= r5) goto L_0x0060
            r9.append(r4)
            int r15 = r15 + 1
            goto L_0x0053
        L_0x005b:
            if (r12 != r6) goto L_0x0060
            r9.append(r14)
        L_0x0060:
            r15 = 0
            r17 = 0
        L_0x0063:
            if (r13 >= r2) goto L_0x0492
            char r14 = r0.charAt(r13)
            r6 = 39
            if (r14 == r3) goto L_0x043c
            if (r14 == r7) goto L_0x0429
            r7 = 50
            if (r14 == r7) goto L_0x0420
            r7 = 67
            if (r14 == r7) goto L_0x0417
            r7 = 73
            if (r14 == r7) goto L_0x040e
            r7 = 161(0xa1, float:2.26E-43)
            if (r14 == r7) goto L_0x0405
            r7 = 168(0xa8, float:2.35E-43)
            if (r14 == r7) goto L_0x022b
            r7 = 52
            if (r14 == r7) goto L_0x03fc
            r7 = 53
            if (r14 == r7) goto L_0x03f3
            r7 = 144(0x90, float:2.02E-43)
            if (r14 == r7) goto L_0x03ea
            r7 = 145(0x91, float:2.03E-43)
            if (r14 == r7) goto L_0x03e1
            r7 = 147(0x93, float:2.06E-43)
            if (r14 == r7) goto L_0x03d8
            r7 = 148(0x94, float:2.07E-43)
            if (r14 == r7) goto L_0x03cf
            switch(r14) {
                case 9: goto L_0x03c6;
                case 10: goto L_0x03bd;
                case 11: goto L_0x03b4;
                case 12: goto L_0x03ab;
                case 13: goto L_0x03a2;
                case 14: goto L_0x0399;
                case 15: goto L_0x0390;
                case 16: goto L_0x0387;
                case 17: goto L_0x037e;
                case 18: goto L_0x0375;
                case 19: goto L_0x036c;
                case 20: goto L_0x0363;
                case 21: goto L_0x035a;
                case 22: goto L_0x0351;
                case 23: goto L_0x0348;
                case 24: goto L_0x033f;
                case 25: goto L_0x0336;
                case 26: goto L_0x032d;
                case 27: goto L_0x0324;
                case 28: goto L_0x031b;
                case 29: goto L_0x0312;
                case 30: goto L_0x0309;
                case 31: goto L_0x0300;
                case 32: goto L_0x02f7;
                default: goto L_0x009e;
            }
        L_0x009e:
            switch(r14) {
                case 39: goto L_0x02e9;
                case 40: goto L_0x02e0;
                case 41: goto L_0x02d7;
                case 42: goto L_0x02ce;
                case 43: goto L_0x02c5;
                case 44: goto L_0x02bc;
                case 45: goto L_0x02b3;
                case 46: goto L_0x02aa;
                case 47: goto L_0x02a1;
                case 48: goto L_0x02e9;
                default: goto L_0x00a1;
            }
        L_0x00a1:
            switch(r14) {
                case 82: goto L_0x0298;
                case 83: goto L_0x0286;
                case 84: goto L_0x027d;
                case 85: goto L_0x0274;
                case 86: goto L_0x0262;
                case 87: goto L_0x0236;
                case 88: goto L_0x022f;
                case 89: goto L_0x021b;
                case 90: goto L_0x0215;
                case 91: goto L_0x020f;
                case 92: goto L_0x0209;
                case 93: goto L_0x0203;
                case 94: goto L_0x01fd;
                case 95: goto L_0x01f7;
                case 96: goto L_0x01f1;
                case 97: goto L_0x01eb;
                case 98: goto L_0x01e5;
                case 99: goto L_0x01df;
                case 100: goto L_0x01d9;
                case 101: goto L_0x01d3;
                case 102: goto L_0x01cd;
                case 103: goto L_0x01c7;
                case 104: goto L_0x01b3;
                case 105: goto L_0x01ac;
                case 106: goto L_0x01a5;
                case 107: goto L_0x019e;
                case 108: goto L_0x0197;
                case 109: goto L_0x0190;
                case 110: goto L_0x0187;
                default: goto L_0x00a4;
            }
        L_0x00a4:
            switch(r14) {
                case 113: goto L_0x0180;
                case 114: goto L_0x0179;
                case 115: goto L_0x0172;
                case 116: goto L_0x016b;
                case 117: goto L_0x0164;
                case 118: goto L_0x015d;
                case 119: goto L_0x0156;
                case 120: goto L_0x014f;
                case 121: goto L_0x013f;
                case 122: goto L_0x012f;
                case 123: goto L_0x0128;
                case 124: goto L_0x0121;
                case 125: goto L_0x011a;
                case 126: goto L_0x0113;
                case 127: goto L_0x010c;
                default: goto L_0x00a7;
            }
        L_0x00a7:
            switch(r14) {
                case 152: goto L_0x00e5;
                case 153: goto L_0x00e5;
                case 154: goto L_0x00de;
                case 155: goto L_0x00d7;
                default: goto L_0x00aa;
            }
        L_0x00aa:
            switch(r14) {
                case 164: goto L_0x00e5;
                case 165: goto L_0x00d0;
                case 166: goto L_0x00c9;
                default: goto L_0x00ad;
            }
        L_0x00ad:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Token: "
            r2.<init>(r3)
            char r0 = r0.charAt(r13)
            java.lang.String r0 = org.mozilla.javascript.Token.name(r0)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x00c9:
            java.lang.String r6 = "yield *"
            r9.append(r6)
            goto L_0x022b
        L_0x00d0:
            java.lang.String r6 = " => "
            r9.append(r6)
            goto L_0x022b
        L_0x00d7:
            java.lang.String r6 = "const "
            r9.append(r6)
            goto L_0x022b
        L_0x00de:
            java.lang.String r6 = "let "
            r9.append(r6)
            goto L_0x022b
        L_0x00e5:
            char r6 = r0.charAt(r13)
            r7 = 152(0x98, float:2.13E-43)
            if (r6 != r7) goto L_0x00f3
            java.lang.String r6 = "get "
            r9.append(r6)
            goto L_0x0100
        L_0x00f3:
            char r6 = r0.charAt(r13)
            r7 = 153(0x99, float:2.14E-43)
            if (r6 != r7) goto L_0x0100
            java.lang.String r6 = "set "
            r9.append(r6)
        L_0x0100:
            int r13 = r13 + 1
            int r13 = r13 + r3
            r6 = 0
            int r7 = printSourceString(r0, r13, r6, r9)
            int r13 = r7 + 1
            goto L_0x048f
        L_0x010c:
            java.lang.String r6 = "void "
            r9.append(r6)
            goto L_0x022b
        L_0x0113:
            java.lang.String r6 = "finally "
            r9.append(r6)
            goto L_0x022b
        L_0x011a:
            java.lang.String r6 = "catch "
            r9.append(r6)
            goto L_0x022b
        L_0x0121:
            java.lang.String r6 = "with "
            r9.append(r6)
            goto L_0x022b
        L_0x0128:
            java.lang.String r6 = "var "
            r9.append(r6)
            goto L_0x022b
        L_0x012f:
            java.lang.String r7 = "continue"
            r9.append(r7)
            int r7 = getNext(r0, r2, r13)
            if (r6 != r7) goto L_0x022b
            r9.append(r4)
            goto L_0x022b
        L_0x013f:
            java.lang.String r7 = "break"
            r9.append(r7)
            int r7 = getNext(r0, r2, r13)
            if (r6 != r7) goto L_0x022b
            r9.append(r4)
            goto L_0x022b
        L_0x014f:
            java.lang.String r6 = "for "
            r9.append(r6)
            goto L_0x022b
        L_0x0156:
            java.lang.String r6 = "do "
            r9.append(r6)
            goto L_0x022b
        L_0x015d:
            java.lang.String r6 = "while "
            r9.append(r6)
            goto L_0x022b
        L_0x0164:
            java.lang.String r6 = "default"
            r9.append(r6)
            goto L_0x022b
        L_0x016b:
            java.lang.String r6 = "case "
            r9.append(r6)
            goto L_0x022b
        L_0x0172:
            java.lang.String r6 = "switch "
            r9.append(r6)
            goto L_0x022b
        L_0x0179:
            java.lang.String r6 = "else "
            r9.append(r6)
            goto L_0x022b
        L_0x0180:
            java.lang.String r6 = "if "
            r9.append(r6)
            goto L_0x022b
        L_0x0187:
            int r13 = r13 + 1
            java.lang.String r6 = "function "
            r9.append(r6)
            goto L_0x048f
        L_0x0190:
            r6 = 46
            r9.append(r6)
            goto L_0x022b
        L_0x0197:
            java.lang.String r6 = "--"
            r9.append(r6)
            goto L_0x022b
        L_0x019e:
            java.lang.String r6 = "++"
            r9.append(r6)
            goto L_0x022b
        L_0x01a5:
            java.lang.String r6 = " && "
            r9.append(r6)
            goto L_0x022b
        L_0x01ac:
            java.lang.String r6 = " || "
            r9.append(r6)
            goto L_0x022b
        L_0x01b3:
            int r6 = getNext(r0, r2, r13)
            if (r3 != r6) goto L_0x01c0
            r6 = 58
            r9.append(r6)
            goto L_0x022b
        L_0x01c0:
            java.lang.String r6 = " : "
            r9.append(r6)
            goto L_0x022b
        L_0x01c7:
            java.lang.String r6 = " ? "
            r9.append(r6)
            goto L_0x022b
        L_0x01cd:
            java.lang.String r6 = " %= "
            r9.append(r6)
            goto L_0x022b
        L_0x01d3:
            java.lang.String r6 = " /= "
            r9.append(r6)
            goto L_0x022b
        L_0x01d9:
            java.lang.String r6 = " *= "
            r9.append(r6)
            goto L_0x022b
        L_0x01df:
            java.lang.String r6 = " -= "
            r9.append(r6)
            goto L_0x022b
        L_0x01e5:
            java.lang.String r6 = " += "
            r9.append(r6)
            goto L_0x022b
        L_0x01eb:
            java.lang.String r6 = " >>>= "
            r9.append(r6)
            goto L_0x022b
        L_0x01f1:
            java.lang.String r6 = " >>= "
            r9.append(r6)
            goto L_0x022b
        L_0x01f7:
            java.lang.String r6 = " <<= "
            r9.append(r6)
            goto L_0x022b
        L_0x01fd:
            java.lang.String r6 = " &= "
            r9.append(r6)
            goto L_0x022b
        L_0x0203:
            java.lang.String r6 = " ^= "
            r9.append(r6)
            goto L_0x022b
        L_0x0209:
            java.lang.String r6 = " |= "
            r9.append(r6)
            goto L_0x022b
        L_0x020f:
            java.lang.String r6 = " = "
            r9.append(r6)
            goto L_0x022b
        L_0x0215:
            java.lang.String r6 = ", "
            r9.append(r6)
            goto L_0x022b
        L_0x021b:
            r6 = 41
            r9.append(r6)
            r6 = 86
            int r7 = getNext(r0, r2, r13)
            if (r6 != r7) goto L_0x022b
            r9.append(r4)
        L_0x022b:
            r7 = 40
            goto L_0x048f
        L_0x022f:
            r7 = 40
            r9.append(r7)
            goto L_0x048f
        L_0x0236:
            r7 = 40
            int r15 = r15 + -1
            if (r10 == 0) goto L_0x0240
            if (r15 != 0) goto L_0x0240
            goto L_0x048f
        L_0x0240:
            r6 = 125(0x7d, float:1.75E-43)
            r9.append(r6)
            int r6 = getNext(r0, r2, r13)
            if (r6 == r3) goto L_0x025f
            r14 = 114(0x72, float:1.6E-43)
            if (r6 == r14) goto L_0x0259
            r14 = 118(0x76, float:1.65E-43)
            if (r6 == r14) goto L_0x0259
            r14 = 168(0xa8, float:2.35E-43)
            if (r6 == r14) goto L_0x025f
            goto L_0x048f
        L_0x0259:
            int r5 = r5 - r8
            r9.append(r4)
            goto L_0x048f
        L_0x025f:
            int r5 = r5 - r8
            goto L_0x048f
        L_0x0262:
            r7 = 40
            int r15 = r15 + 1
            int r6 = getNext(r0, r2, r13)
            if (r3 != r6) goto L_0x026d
            int r5 = r5 + r8
        L_0x026d:
            r6 = 123(0x7b, float:1.72E-43)
            r9.append(r6)
            goto L_0x048f
        L_0x0274:
            r7 = 40
            r6 = 93
            r9.append(r6)
            goto L_0x048f
        L_0x027d:
            r7 = 40
            r6 = 91
            r9.append(r6)
            goto L_0x048f
        L_0x0286:
            r7 = 40
            r6 = 59
            r9.append(r6)
            int r6 = getNext(r0, r2, r13)
            if (r3 == r6) goto L_0x048f
            r9.append(r4)
            goto L_0x048f
        L_0x0298:
            r7 = 40
            java.lang.String r6 = "try "
            r9.append(r6)
            goto L_0x048f
        L_0x02a1:
            r7 = 40
            java.lang.String r6 = " !== "
            r9.append(r6)
            goto L_0x048f
        L_0x02aa:
            r7 = 40
            java.lang.String r6 = " === "
            r9.append(r6)
            goto L_0x048f
        L_0x02b3:
            r7 = 40
            java.lang.String r6 = "true"
            r9.append(r6)
            goto L_0x048f
        L_0x02bc:
            r7 = 40
            java.lang.String r6 = "false"
            r9.append(r6)
            goto L_0x048f
        L_0x02c5:
            r7 = 40
            java.lang.String r6 = "this"
            r9.append(r6)
            goto L_0x048f
        L_0x02ce:
            r7 = 40
            java.lang.String r6 = "null"
            r9.append(r6)
            goto L_0x048f
        L_0x02d7:
            r7 = 40
            int r13 = r13 + 1
            int r13 = printSourceString(r0, r13, r3, r9)
            goto L_0x02f2
        L_0x02e0:
            r7 = 40
            int r13 = r13 + 1
            int r13 = printSourceNumber(r0, r13, r9)
            goto L_0x02f2
        L_0x02e9:
            r7 = 40
            int r13 = r13 + 1
            r6 = 0
            int r13 = printSourceString(r0, r13, r6, r9)
        L_0x02f2:
            r7 = 4
            r14 = 40
            goto L_0x0063
        L_0x02f7:
            r7 = 40
            java.lang.String r6 = "typeof "
            r9.append(r6)
            goto L_0x048f
        L_0x0300:
            r7 = 40
            java.lang.String r6 = "delete "
            r9.append(r6)
            goto L_0x048f
        L_0x0309:
            r7 = 40
            java.lang.String r6 = "new "
            r9.append(r6)
            goto L_0x048f
        L_0x0312:
            r7 = 40
            r6 = 45
            r9.append(r6)
            goto L_0x048f
        L_0x031b:
            r7 = 40
            r6 = 43
            r9.append(r6)
            goto L_0x048f
        L_0x0324:
            r7 = 40
            r6 = 126(0x7e, float:1.77E-43)
            r9.append(r6)
            goto L_0x048f
        L_0x032d:
            r7 = 40
            r6 = 33
            r9.append(r6)
            goto L_0x048f
        L_0x0336:
            r7 = 40
            java.lang.String r6 = " % "
            r9.append(r6)
            goto L_0x048f
        L_0x033f:
            r7 = 40
            java.lang.String r6 = " / "
            r9.append(r6)
            goto L_0x048f
        L_0x0348:
            r7 = 40
            java.lang.String r6 = " * "
            r9.append(r6)
            goto L_0x048f
        L_0x0351:
            r7 = 40
            java.lang.String r6 = " - "
            r9.append(r6)
            goto L_0x048f
        L_0x035a:
            r7 = 40
            java.lang.String r6 = " + "
            r9.append(r6)
            goto L_0x048f
        L_0x0363:
            r7 = 40
            java.lang.String r6 = " >>> "
            r9.append(r6)
            goto L_0x048f
        L_0x036c:
            r7 = 40
            java.lang.String r6 = " >> "
            r9.append(r6)
            goto L_0x048f
        L_0x0375:
            r7 = 40
            java.lang.String r6 = " << "
            r9.append(r6)
            goto L_0x048f
        L_0x037e:
            r7 = 40
            java.lang.String r6 = " >= "
            r9.append(r6)
            goto L_0x048f
        L_0x0387:
            r7 = 40
            java.lang.String r6 = " > "
            r9.append(r6)
            goto L_0x048f
        L_0x0390:
            r7 = 40
            java.lang.String r6 = " <= "
            r9.append(r6)
            goto L_0x048f
        L_0x0399:
            r7 = 40
            java.lang.String r6 = " < "
            r9.append(r6)
            goto L_0x048f
        L_0x03a2:
            r7 = 40
            java.lang.String r6 = " != "
            r9.append(r6)
            goto L_0x048f
        L_0x03ab:
            r7 = 40
            java.lang.String r6 = " == "
            r9.append(r6)
            goto L_0x048f
        L_0x03b4:
            r7 = 40
            java.lang.String r6 = " & "
            r9.append(r6)
            goto L_0x048f
        L_0x03bd:
            r7 = 40
            java.lang.String r6 = " ^ "
            r9.append(r6)
            goto L_0x048f
        L_0x03c6:
            r7 = 40
            java.lang.String r6 = " | "
            r9.append(r6)
            goto L_0x048f
        L_0x03cf:
            r7 = 40
            r6 = 64
            r9.append(r6)
            goto L_0x048f
        L_0x03d8:
            r7 = 40
            java.lang.String r6 = ".("
            r9.append(r6)
            goto L_0x048f
        L_0x03e1:
            r7 = 40
            java.lang.String r6 = "::"
            r9.append(r6)
            goto L_0x048f
        L_0x03ea:
            r7 = 40
            java.lang.String r6 = ".."
            r9.append(r6)
            goto L_0x048f
        L_0x03f3:
            r7 = 40
            java.lang.String r6 = " instanceof "
            r9.append(r6)
            goto L_0x048f
        L_0x03fc:
            r7 = 40
            java.lang.String r6 = " in "
            r9.append(r6)
            goto L_0x048f
        L_0x0405:
            r7 = 40
            java.lang.String r6 = "debugger;\n"
            r9.append(r6)
            goto L_0x048f
        L_0x040e:
            r7 = 40
            java.lang.String r6 = "yield "
            r9.append(r6)
            goto L_0x048f
        L_0x0417:
            r7 = 40
            java.lang.String r6 = ": "
            r9.append(r6)
            goto L_0x048f
        L_0x0420:
            r7 = 40
            java.lang.String r6 = "throw "
            r9.append(r6)
            goto L_0x048f
        L_0x0429:
            r7 = 40
            java.lang.String r6 = "return"
            r9.append(r6)
            r6 = 83
            int r14 = getNext(r0, r2, r13)
            if (r6 == r14) goto L_0x048f
            r9.append(r4)
            goto L_0x048f
        L_0x043c:
            r7 = 40
            if (r11 == 0) goto L_0x0441
            goto L_0x048f
        L_0x0441:
            r14 = 0
            if (r17 != 0) goto L_0x0452
            if (r10 == 0) goto L_0x044d
            r9.setLength(r14)
            int r5 = r5 - r8
            r16 = 0
            goto L_0x044f
        L_0x044d:
            r16 = 1
        L_0x044f:
            r17 = 1
            goto L_0x0454
        L_0x0452:
            r16 = 1
        L_0x0454:
            if (r16 == 0) goto L_0x045b
            r7 = 10
            r9.append(r7)
        L_0x045b:
            int r7 = r13 + 1
            if (r7 >= r2) goto L_0x048f
            char r7 = r0.charAt(r7)
            r14 = 116(0x74, float:1.63E-43)
            if (r7 == r14) goto L_0x0485
            r14 = 117(0x75, float:1.64E-43)
            if (r7 != r14) goto L_0x046c
            goto L_0x0485
        L_0x046c:
            r14 = 87
            if (r7 != r14) goto L_0x0471
            goto L_0x0481
        L_0x0471:
            if (r7 != r6) goto L_0x0483
            int r6 = r13 + 2
            int r6 = getSourceStringEnd(r0, r6)
            char r6 = r0.charAt(r6)
            r7 = 104(0x68, float:1.46E-43)
            if (r6 != r7) goto L_0x0483
        L_0x0481:
            r6 = r8
            goto L_0x0487
        L_0x0483:
            r6 = 0
            goto L_0x0487
        L_0x0485:
            int r6 = r8 - r1
        L_0x0487:
            if (r6 >= r5) goto L_0x048f
            r9.append(r4)
            int r6 = r6 + 1
            goto L_0x0487
        L_0x048f:
            int r13 = r13 + r3
            goto L_0x02f2
        L_0x0492:
            if (r11 != 0) goto L_0x049c
            if (r10 != 0) goto L_0x04a4
            r0 = 10
            r9.append(r0)
            goto L_0x04a4
        L_0x049c:
            r0 = 2
            if (r12 != r0) goto L_0x04a4
            r0 = 41
            r9.append(r0)
        L_0x04a4:
            java.lang.String r0 = r9.toString()
            return r0
        L_0x04a9:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            throw r0
        L_0x04af:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            throw r0
        L_0x04b5:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.Decompiler.decompile(java.lang.String, int, org.mozilla.javascript.UintMap):java.lang.String");
    }

    private static int getNext(String str, int i, int i2) {
        int i3 = i2 + 1;
        if (i3 < i) {
            return str.charAt(i3);
        }
        return 0;
    }

    private static int getSourceStringEnd(String str, int i) {
        return printSourceString(str, i, false, (StringBuilder) null);
    }

    private void increaseSourceCapacity(int i) {
        if (i <= this.sourceBuffer.length) {
            Kit.codeBug();
        }
        char[] cArr = this.sourceBuffer;
        int length = cArr.length * 2;
        if (length >= i) {
            i = length;
        }
        char[] cArr2 = new char[i];
        System.arraycopy(cArr, 0, cArr2, 0, this.sourceTop);
        this.sourceBuffer = cArr2;
    }

    private static int printSourceNumber(String str, int i, StringBuilder sb) {
        int i2;
        char charAt = str.charAt(i);
        int i3 = i + 1;
        double d = 0.0d;
        if (charAt == 'S') {
            if (sb != null) {
                d = (double) str.charAt(i3);
            }
            i2 = i3 + 1;
        } else if (charAt == 'J' || charAt == 'D') {
            if (sb != null) {
                long charAt2 = (((long) str.charAt(i3)) << 48) | (((long) str.charAt(i3 + 1)) << 32) | (((long) str.charAt(i3 + 2)) << 16) | ((long) str.charAt(i3 + 3));
                if (charAt == 'J') {
                    d = (double) charAt2;
                } else {
                    d = Double.longBitsToDouble(charAt2);
                }
            }
            i2 = i3 + 4;
        } else {
            throw new RuntimeException();
        }
        if (sb != null) {
            sb.append(ScriptRuntime.numberToString(d, 10));
        }
        return i2;
    }

    private static int printSourceString(String str, int i, boolean z, StringBuilder sb) {
        char charAt = str.charAt(i);
        int i2 = i + 1;
        if ((32768 & charAt) != 0) {
            charAt = ((charAt & 32767) << 16) | str.charAt(i2);
            i2++;
        }
        if (sb != null) {
            String substring = str.substring(i2, i2 + charAt);
            if (!z) {
                sb.append(substring);
            } else {
                sb.append('\"');
                sb.append(ScriptRuntime.escapeString(substring));
                sb.append('\"');
            }
        }
        return i2 + charAt;
    }

    private String sourceToString(int i) {
        if (i < 0 || this.sourceTop < i) {
            Kit.codeBug();
        }
        return new String(this.sourceBuffer, i, this.sourceTop - i);
    }

    public void addEOL(int i) {
        if (i < 0 || i > 167) {
            throw new IllegalArgumentException();
        }
        append((char) i);
        append(1);
    }

    public void addName(String str) {
        addToken(39);
        appendString(str);
    }

    public void addNumber(double d) {
        addToken(40);
        long j = (long) d;
        if (((double) j) != d) {
            long doubleToLongBits = Double.doubleToLongBits(d);
            append('D');
            append((char) ((int) (doubleToLongBits >> 48)));
            append((char) ((int) (doubleToLongBits >> 32)));
            append((char) ((int) (doubleToLongBits >> 16)));
            append((char) ((int) doubleToLongBits));
            return;
        }
        if (j < 0) {
            Kit.codeBug();
        }
        if (j <= 65535) {
            append('S');
            append((char) ((int) j));
            return;
        }
        append('J');
        append((char) ((int) (j >> 48)));
        append((char) ((int) (j >> 32)));
        append((char) ((int) (j >> 16)));
        append((char) ((int) j));
    }

    public void addRegexp(String str, String str2) {
        addToken(48);
        appendString(MqttTopic.TOPIC_LEVEL_SEPARATOR + str + '/' + str2);
    }

    public void addString(String str) {
        addToken(41);
        appendString(str);
    }

    public void addToken(int i) {
        if (i < 0 || i > 167) {
            throw new IllegalArgumentException();
        }
        append((char) i);
    }

    public int getCurrentOffset() {
        return this.sourceTop;
    }

    public String getEncodedSource() {
        return sourceToString(0);
    }

    public int markFunctionEnd(int i) {
        int currentOffset = getCurrentOffset();
        append(168);
        return currentOffset;
    }

    public int markFunctionStart(int i) {
        int currentOffset = getCurrentOffset();
        if (i != 4) {
            addToken(110);
            append((char) i);
        }
        return currentOffset;
    }
}
