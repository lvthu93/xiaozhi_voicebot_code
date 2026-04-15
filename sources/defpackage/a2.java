package defpackage;

import org.mozilla.javascript.Kit;
import org.mozilla.javascript.ScriptRuntime;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.utils.jsextractor.Token;

/* renamed from: a2  reason: default package */
public final class a2 {
    public boolean a;
    public char[] b = new char[128];
    public int c;
    public final int[] d = new int[3];
    public int e;
    public int f = -1;
    public int g;
    public final String h;
    public int i;
    public int j;
    public int k;
    public int l;
    public final boolean m;

    public a2(String str) {
        this.h = str;
        this.i = 0;
        this.j = 0;
        this.g = 0;
        this.m = false;
    }

    public static boolean e(int i2) {
        return 48 <= i2 && i2 <= 57;
    }

    public static boolean f(int i2, int i3) {
        if (i2 == 10 && e(i3)) {
            return true;
        }
        if (i2 == 16) {
            if ((48 <= i3 && i3 <= 57) || (97 <= i3 && i3 <= 102) || (65 <= i3 && i3 <= 70)) {
                return true;
            }
        }
        if (i2 == 8) {
            if (48 <= i3 && i3 <= 55) {
                return true;
            }
        }
        if (i2 == 2) {
            if (48 == i3 || i3 == 49) {
                return true;
            }
        }
        return false;
    }

    public final void a(int i2) {
        int i3 = this.c;
        char[] cArr = this.b;
        if (i3 == cArr.length) {
            char[] cArr2 = new char[(cArr.length * 2)];
            System.arraycopy(cArr, 0, cArr2, 0, i3);
            this.b = cArr2;
        }
        this.b[i3] = (char) i2;
        this.c = i3 + 1;
    }

    public final int b() {
        return c(true, false);
    }

    public final int c(boolean z, boolean z2) {
        char charAt;
        boolean z3;
        int i2;
        int i3 = this.e;
        if (i3 != 0) {
            this.j++;
            int i4 = i3 - 1;
            this.e = i4;
            return this.d[i4];
        }
        while (true) {
            int i5 = this.i;
            String str = this.h;
            if (i5 != str.length()) {
                this.j++;
                int i6 = this.i;
                this.i = i6 + 1;
                charAt = str.charAt(i6);
                if (!z2 && (i2 = this.f) >= 0) {
                    if (i2 == 13 && charAt == 10) {
                        this.f = 10;
                    } else {
                        this.f = -1;
                        this.g++;
                    }
                }
                if (charAt > 127) {
                    if (charAt != 65279) {
                        if (z) {
                            if (charAt <= 127 || Character.getType((char) charAt) != 16) {
                                z3 = false;
                            } else {
                                z3 = true;
                            }
                            if (!z3) {
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        return charAt;
                    }
                } else if (charAt != 10 && charAt != 13) {
                    return charAt;
                } else {
                    this.f = charAt;
                }
            } else {
                return -1;
            }
        }
        if (!ScriptRuntime.isJSLineTerminator(charAt)) {
            return charAt;
        }
        this.f = charAt;
        return 10;
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x048b  */
    /* JADX WARNING: Removed duplicated region for block: B:486:0x064d  */
    /* JADX WARNING: Removed duplicated region for block: B:642:0x0495 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:643:0x049f A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:648:0x0707 A[SYNTHETIC] */
    public final org.schabi.newpipe.extractor.utils.jsextractor.Token d() throws org.schabi.newpipe.extractor.exceptions.ParsingException {
        /*
            r17 = this;
            r0 = r17
        L_0x0002:
            int r1 = r17.b()
            org.schabi.newpipe.extractor.utils.jsextractor.Token r2 = org.schabi.newpipe.extractor.utils.jsextractor.Token.EOF
            r3 = -1
            if (r1 != r3) goto L_0x0014
            int r1 = r0.j
            int r3 = r1 + -1
            r0.k = r3
            r0.l = r1
            return r2
        L_0x0014:
            r4 = 10
            r5 = 0
            if (r1 != r4) goto L_0x0026
            r0.a = r5
            int r1 = r0.j
            int r2 = r1 + -1
            r0.k = r2
            r0.l = r1
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.EOL
            return r1
        L_0x0026:
            r6 = 127(0x7f, float:1.78E-43)
            r7 = 65279(0xfeff, float:9.1475E-41)
            r8 = 32
            r9 = 11
            r10 = 12
            r11 = 9
            r12 = 1
            if (r1 > r6) goto L_0x003f
            if (r1 == r8) goto L_0x004f
            if (r1 == r11) goto L_0x004f
            if (r1 == r10) goto L_0x004f
            if (r1 != r9) goto L_0x004d
            goto L_0x004f
        L_0x003f:
            r6 = 160(0xa0, float:2.24E-43)
            if (r1 == r6) goto L_0x004f
            if (r1 == r7) goto L_0x004f
            char r6 = (char) r1
            int r6 = java.lang.Character.getType(r6)
            if (r6 != r10) goto L_0x004d
            goto L_0x004f
        L_0x004d:
            r6 = 0
            goto L_0x0050
        L_0x004f:
            r6 = 1
        L_0x0050:
            if (r6 != 0) goto L_0x0002
            r6 = 45
            if (r1 == r6) goto L_0x0058
            r0.a = r12
        L_0x0058:
            int r13 = r0.j
            int r14 = r13 + -1
            r0.k = r14
            r0.l = r13
            r13 = 92
            r14 = 117(0x75, float:1.64E-43)
            if (r1 != r13) goto L_0x0079
            int r1 = r17.b()
            if (r1 != r14) goto L_0x0072
            r0.c = r5
            r15 = 1
            r16 = 1
            goto L_0x0087
        L_0x0072:
            r0.j(r1)
            r1 = 92
            r15 = 0
            goto L_0x0085
        L_0x0079:
            char r15 = (char) r1
            boolean r15 = java.lang.Character.isJavaIdentifierStart(r15)
            if (r15 == 0) goto L_0x0085
            r0.c = r5
            r0.a(r1)
        L_0x0085:
            r16 = 0
        L_0x0087:
            r8 = 4
            r9 = 33
            r11 = 38
            r4 = 2
            r10 = 42
            if (r15 == 0) goto L_0x03f9
            r1 = r16
        L_0x0093:
            if (r16 == 0) goto L_0x00b9
            r6 = 0
            r15 = 0
        L_0x0097:
            if (r15 == r8) goto L_0x00a8
            int r8 = r17.b()
            int r6 = org.mozilla.javascript.Kit.xDigitToInt(r8, r6)
            if (r6 >= 0) goto L_0x00a4
            goto L_0x00a8
        L_0x00a4:
            int r15 = r15 + 1
            r8 = 4
            goto L_0x0097
        L_0x00a8:
            if (r6 < 0) goto L_0x00b1
            r0.a(r6)
            r8 = 4
            r16 = 0
            goto L_0x0093
        L_0x00b1:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r2 = "invalid unicode escape"
            r1.<init>(r2)
            throw r1
        L_0x00b9:
            int r6 = r17.b()
            if (r6 != r13) goto L_0x00de
            int r1 = r17.b()
            if (r1 != r14) goto L_0x00ca
            r1 = 1
            r8 = 4
            r16 = 1
            goto L_0x0093
        L_0x00ca:
            org.schabi.newpipe.extractor.exceptions.ParsingException r2 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.Object[] r3 = new java.lang.Object[r12]
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r3[r5] = r1
            java.lang.String r1 = "illegal character: '%c'"
            java.lang.String r1 = java.lang.String.format(r1, r3)
            r2.<init>(r1)
            throw r2
        L_0x00de:
            if (r6 == r3) goto L_0x00ef
            if (r6 == r7) goto L_0x00ef
            char r8 = (char) r6
            boolean r8 = java.lang.Character.isJavaIdentifierPart(r8)
            if (r8 != 0) goto L_0x00ea
            goto L_0x00ef
        L_0x00ea:
            r0.a(r6)
            r8 = 4
            goto L_0x0093
        L_0x00ef:
            r0.j(r6)
            int r6 = r0.j
            r0.l = r6
            java.lang.String r6 = new java.lang.String
            char[] r7 = r0.b
            int r8 = r0.c
            r6.<init>(r7, r5, r8)
            if (r1 != 0) goto L_0x03f6
            int r1 = r6.hashCode()
            switch(r1) {
                case -1335458389: goto L_0x036b;
                case -1305664359: goto L_0x0360;
                case -1289153612: goto L_0x0355;
                case -1184795739: goto L_0x034a;
                case -977423767: goto L_0x033f;
                case -934396624: goto L_0x0334;
                case -915384400: goto L_0x0329;
                case -892481938: goto L_0x031e;
                case -889473228: goto L_0x0310;
                case -858802543: goto L_0x0302;
                case -853259901: goto L_0x02f4;
                case -807062458: goto L_0x02e6;
                case -608539730: goto L_0x02d8;
                case -567202649: goto L_0x02ca;
                case -314497661: goto L_0x02bc;
                case 3211: goto L_0x02ae;
                case 3357: goto L_0x02a0;
                case 3365: goto L_0x0292;
                case 101577: goto L_0x0284;
                case 107035: goto L_0x0276;
                case 108960: goto L_0x0268;
                case 115131: goto L_0x025a;
                case 116519: goto L_0x024c;
                case 3046192: goto L_0x023e;
                case 3116345: goto L_0x0230;
                case 3118337: goto L_0x0222;
                case 3392903: goto L_0x0214;
                case 3559070: goto L_0x0206;
                case 3569038: goto L_0x01f8;
                case 3625364: goto L_0x01ea;
                case 3649734: goto L_0x01dc;
                case 93223254: goto L_0x01ce;
                case 94001407: goto L_0x01c0;
                case 94432955: goto L_0x01b2;
                case 94742904: goto L_0x01a4;
                case 94844771: goto L_0x0196;
                case 97196323: goto L_0x0188;
                case 109801339: goto L_0x017a;
                case 110339814: goto L_0x016c;
                case 113101617: goto L_0x015e;
                case 114974605: goto L_0x0150;
                case 502623545: goto L_0x0142;
                case 547812385: goto L_0x0134;
                case 902025516: goto L_0x0126;
                case 1380938712: goto L_0x0118;
                case 1544803905: goto L_0x010a;
                default: goto L_0x0108;
            }
        L_0x0108:
            goto L_0x0375
        L_0x010a:
            java.lang.String r1 = "default"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0114
            goto L_0x0375
        L_0x0114:
            r3 = 45
            goto L_0x0375
        L_0x0118:
            java.lang.String r1 = "function"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0122
            goto L_0x0375
        L_0x0122:
            r3 = 44
            goto L_0x0375
        L_0x0126:
            java.lang.String r1 = "instanceof"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0130
            goto L_0x0375
        L_0x0130:
            r3 = 43
            goto L_0x0375
        L_0x0134:
            java.lang.String r1 = "debugger"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x013e
            goto L_0x0375
        L_0x013e:
            r3 = 42
            goto L_0x0375
        L_0x0142:
            java.lang.String r1 = "interface"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x014c
            goto L_0x0375
        L_0x014c:
            r3 = 41
            goto L_0x0375
        L_0x0150:
            java.lang.String r1 = "yield"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x015a
            goto L_0x0375
        L_0x015a:
            r3 = 40
            goto L_0x0375
        L_0x015e:
            java.lang.String r1 = "while"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0168
            goto L_0x0375
        L_0x0168:
            r3 = 39
            goto L_0x0375
        L_0x016c:
            java.lang.String r1 = "throw"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0176
            goto L_0x0375
        L_0x0176:
            r3 = 38
            goto L_0x0375
        L_0x017a:
            java.lang.String r1 = "super"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0184
            goto L_0x0375
        L_0x0184:
            r3 = 37
            goto L_0x0375
        L_0x0188:
            java.lang.String r1 = "false"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0192
            goto L_0x0375
        L_0x0192:
            r3 = 36
            goto L_0x0375
        L_0x0196:
            java.lang.String r1 = "const"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x01a0
            goto L_0x0375
        L_0x01a0:
            r3 = 35
            goto L_0x0375
        L_0x01a4:
            java.lang.String r1 = "class"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x01ae
            goto L_0x0375
        L_0x01ae:
            r3 = 34
            goto L_0x0375
        L_0x01b2:
            java.lang.String r1 = "catch"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x01bc
            goto L_0x0375
        L_0x01bc:
            r3 = 33
            goto L_0x0375
        L_0x01c0:
            java.lang.String r1 = "break"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x01ca
            goto L_0x0375
        L_0x01ca:
            r3 = 32
            goto L_0x0375
        L_0x01ce:
            java.lang.String r1 = "await"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x01d8
            goto L_0x0375
        L_0x01d8:
            r3 = 31
            goto L_0x0375
        L_0x01dc:
            java.lang.String r1 = "with"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x01e6
            goto L_0x0375
        L_0x01e6:
            r3 = 30
            goto L_0x0375
        L_0x01ea:
            java.lang.String r1 = "void"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x01f4
            goto L_0x0375
        L_0x01f4:
            r3 = 29
            goto L_0x0375
        L_0x01f8:
            java.lang.String r1 = "true"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0202
            goto L_0x0375
        L_0x0202:
            r3 = 28
            goto L_0x0375
        L_0x0206:
            java.lang.String r1 = "this"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0210
            goto L_0x0375
        L_0x0210:
            r3 = 27
            goto L_0x0375
        L_0x0214:
            java.lang.String r1 = "null"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x021e
            goto L_0x0375
        L_0x021e:
            r3 = 26
            goto L_0x0375
        L_0x0222:
            java.lang.String r1 = "enum"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x022c
            goto L_0x0375
        L_0x022c:
            r3 = 25
            goto L_0x0375
        L_0x0230:
            java.lang.String r1 = "else"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x023a
            goto L_0x0375
        L_0x023a:
            r3 = 24
            goto L_0x0375
        L_0x023e:
            java.lang.String r1 = "case"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0248
            goto L_0x0375
        L_0x0248:
            r3 = 23
            goto L_0x0375
        L_0x024c:
            java.lang.String r1 = "var"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0256
            goto L_0x0375
        L_0x0256:
            r3 = 22
            goto L_0x0375
        L_0x025a:
            java.lang.String r1 = "try"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0264
            goto L_0x0375
        L_0x0264:
            r3 = 21
            goto L_0x0375
        L_0x0268:
            java.lang.String r1 = "new"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0272
            goto L_0x0375
        L_0x0272:
            r3 = 20
            goto L_0x0375
        L_0x0276:
            java.lang.String r1 = "let"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0280
            goto L_0x0375
        L_0x0280:
            r3 = 19
            goto L_0x0375
        L_0x0284:
            java.lang.String r1 = "for"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x028e
            goto L_0x0375
        L_0x028e:
            r3 = 18
            goto L_0x0375
        L_0x0292:
            java.lang.String r1 = "in"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x029c
            goto L_0x0375
        L_0x029c:
            r3 = 17
            goto L_0x0375
        L_0x02a0:
            java.lang.String r1 = "if"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x02aa
            goto L_0x0375
        L_0x02aa:
            r3 = 16
            goto L_0x0375
        L_0x02ae:
            java.lang.String r1 = "do"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x02b8
            goto L_0x0375
        L_0x02b8:
            r3 = 15
            goto L_0x0375
        L_0x02bc:
            java.lang.String r1 = "private"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x02c6
            goto L_0x0375
        L_0x02c6:
            r3 = 14
            goto L_0x0375
        L_0x02ca:
            java.lang.String r1 = "continue"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x02d4
            goto L_0x0375
        L_0x02d4:
            r3 = 13
            goto L_0x0375
        L_0x02d8:
            java.lang.String r1 = "protected"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x02e2
            goto L_0x0375
        L_0x02e2:
            r3 = 12
            goto L_0x0375
        L_0x02e6:
            java.lang.String r1 = "package"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x02f0
            goto L_0x0375
        L_0x02f0:
            r3 = 11
            goto L_0x0375
        L_0x02f4:
            java.lang.String r1 = "finally"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x02fe
            goto L_0x0375
        L_0x02fe:
            r3 = 10
            goto L_0x0375
        L_0x0302:
            java.lang.String r1 = "typeof"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x030c
            goto L_0x0375
        L_0x030c:
            r3 = 9
            goto L_0x0375
        L_0x0310:
            java.lang.String r1 = "switch"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x031a
            goto L_0x0375
        L_0x031a:
            r3 = 8
            goto L_0x0375
        L_0x031e:
            java.lang.String r1 = "static"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0327
            goto L_0x0375
        L_0x0327:
            r3 = 7
            goto L_0x0375
        L_0x0329:
            java.lang.String r1 = "implements"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0332
            goto L_0x0375
        L_0x0332:
            r3 = 6
            goto L_0x0375
        L_0x0334:
            java.lang.String r1 = "return"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x033d
            goto L_0x0375
        L_0x033d:
            r3 = 5
            goto L_0x0375
        L_0x033f:
            java.lang.String r1 = "public"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0348
            goto L_0x0375
        L_0x0348:
            r3 = 4
            goto L_0x0375
        L_0x034a:
            java.lang.String r1 = "import"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0353
            goto L_0x0375
        L_0x0353:
            r3 = 3
            goto L_0x0375
        L_0x0355:
            java.lang.String r1 = "export"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x035e
            goto L_0x0375
        L_0x035e:
            r3 = 2
            goto L_0x0375
        L_0x0360:
            java.lang.String r1 = "extends"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0369
            goto L_0x0375
        L_0x0369:
            r3 = 1
            goto L_0x0375
        L_0x036b:
            java.lang.String r1 = "delete"
            boolean r1 = r6.equals(r1)
            if (r1 != 0) goto L_0x0374
            goto L_0x0375
        L_0x0374:
            r3 = 0
        L_0x0375:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RESERVED
            switch(r3) {
                case 0: goto L_0x03ef;
                case 1: goto L_0x03f3;
                case 2: goto L_0x03ec;
                case 3: goto L_0x03e9;
                case 4: goto L_0x03e4;
                case 5: goto L_0x03e1;
                case 6: goto L_0x03e4;
                case 7: goto L_0x03e4;
                case 8: goto L_0x03de;
                case 9: goto L_0x03db;
                case 10: goto L_0x03d8;
                case 11: goto L_0x03e4;
                case 12: goto L_0x03e4;
                case 13: goto L_0x03d5;
                case 14: goto L_0x03e4;
                case 15: goto L_0x03d2;
                case 16: goto L_0x03cf;
                case 17: goto L_0x03cc;
                case 18: goto L_0x03c9;
                case 19: goto L_0x03c6;
                case 20: goto L_0x03c3;
                case 21: goto L_0x03c0;
                case 22: goto L_0x03bd;
                case 23: goto L_0x03ba;
                case 24: goto L_0x03b7;
                case 25: goto L_0x03f3;
                case 26: goto L_0x03b4;
                case 27: goto L_0x03b1;
                case 28: goto L_0x03ae;
                case 29: goto L_0x03ab;
                case 30: goto L_0x03a8;
                case 31: goto L_0x03f3;
                case 32: goto L_0x03a4;
                case 33: goto L_0x03a0;
                case 34: goto L_0x03f3;
                case 35: goto L_0x039c;
                case 36: goto L_0x0398;
                case 37: goto L_0x03f3;
                case 38: goto L_0x0394;
                case 39: goto L_0x0390;
                case 40: goto L_0x038c;
                case 41: goto L_0x03e4;
                case 42: goto L_0x0388;
                case 43: goto L_0x0384;
                case 44: goto L_0x0380;
                case 45: goto L_0x037c;
                default: goto L_0x037a;
            }
        L_0x037a:
            goto L_0x03f2
        L_0x037c:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DEFAULT
            goto L_0x03f3
        L_0x0380:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.FUNCTION
            goto L_0x03f3
        L_0x0384:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.INSTANCEOF
            goto L_0x03f3
        L_0x0388:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DEBUGGER
            goto L_0x03f3
        L_0x038c:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.YIELD
            goto L_0x03f3
        L_0x0390:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.WHILE
            goto L_0x03f3
        L_0x0394:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.THROW
            goto L_0x03f3
        L_0x0398:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.FALSE
            goto L_0x03f3
        L_0x039c:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.CONST
            goto L_0x03f3
        L_0x03a0:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.CATCH
            goto L_0x03f3
        L_0x03a4:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.BREAK
            goto L_0x03f3
        L_0x03a8:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.WITH
            goto L_0x03f3
        L_0x03ab:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.VOID
            goto L_0x03f3
        L_0x03ae:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.TRUE
            goto L_0x03f3
        L_0x03b1:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.THIS
            goto L_0x03f3
        L_0x03b4:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.NULL
            goto L_0x03f3
        L_0x03b7:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ELSE
            goto L_0x03f3
        L_0x03ba:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.CASE
            goto L_0x03f3
        L_0x03bd:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.VAR
            goto L_0x03f3
        L_0x03c0:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.TRY
            goto L_0x03f3
        L_0x03c3:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.NEW
            goto L_0x03f3
        L_0x03c6:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LET
            goto L_0x03f3
        L_0x03c9:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.FOR
            goto L_0x03f3
        L_0x03cc:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.IN
            goto L_0x03f3
        L_0x03cf:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.IF
            goto L_0x03f3
        L_0x03d2:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DO
            goto L_0x03f3
        L_0x03d5:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.CONTINUE
            goto L_0x03f3
        L_0x03d8:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.FINALLY
            goto L_0x03f3
        L_0x03db:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.TYPEOF
            goto L_0x03f3
        L_0x03de:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.SWITCH
            goto L_0x03f3
        L_0x03e1:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RETURN
            goto L_0x03f3
        L_0x03e4:
            boolean r3 = r0.m
            if (r3 == 0) goto L_0x03f2
            goto L_0x03f3
        L_0x03e9:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.IMPORT
            goto L_0x03f3
        L_0x03ec:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.EXPORT
            goto L_0x03f3
        L_0x03ef:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DELPROP
            goto L_0x03f3
        L_0x03f2:
            r1 = r2
        L_0x03f3:
            if (r1 == r2) goto L_0x03f6
            return r1
        L_0x03f6:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.NAME
            return r1
        L_0x03f9:
            boolean r2 = e(r1)
            r6 = 46
            r7 = 56
            r8 = 120(0x78, float:1.68E-43)
            r15 = 48
            if (r2 != 0) goto L_0x071d
            if (r1 != r6) goto L_0x0418
            int r2 = r17.b()
            r0.j(r2)
            boolean r2 = e(r2)
            if (r2 == 0) goto L_0x0418
            goto L_0x071d
        L_0x0418:
            r2 = 34
            if (r1 == r2) goto L_0x0622
            r2 = 39
            if (r1 == r2) goto L_0x0622
            r2 = 96
            if (r1 != r2) goto L_0x0426
            goto L_0x0622
        L_0x0426:
            r2 = 61
            if (r1 == r9) goto L_0x060d
            r6 = 91
            if (r1 == r6) goto L_0x060a
            r6 = 37
            if (r1 == r6) goto L_0x05fe
            if (r1 == r11) goto L_0x05e9
            r6 = 93
            if (r1 == r6) goto L_0x05e6
            r6 = 94
            if (r1 == r6) goto L_0x05da
            org.schabi.newpipe.extractor.utils.jsextractor.Token r6 = org.schabi.newpipe.extractor.utils.jsextractor.Token.COMMENT
            r7 = 62
            switch(r1) {
                case 40: goto L_0x051c;
                case 41: goto L_0x0519;
                case 42: goto L_0x04fb;
                case 43: goto L_0x04e4;
                case 44: goto L_0x04e1;
                case 45: goto L_0x04bb;
                case 46: goto L_0x04b8;
                case 47: goto L_0x045d;
                default: goto L_0x0443;
            }
        L_0x0443:
            switch(r1) {
                case 58: goto L_0x05b7;
                case 59: goto L_0x05b4;
                case 60: goto L_0x0570;
                case 61: goto L_0x0552;
                case 62: goto L_0x0522;
                case 63: goto L_0x051f;
                default: goto L_0x0446;
            }
        L_0x0446:
            switch(r1) {
                case 123: goto L_0x05d7;
                case 124: goto L_0x05c0;
                case 125: goto L_0x05bd;
                case 126: goto L_0x05ba;
                default: goto L_0x0449;
            }
        L_0x0449:
            org.schabi.newpipe.extractor.exceptions.ParsingException r2 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.Object[] r3 = new java.lang.Object[r12]
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r3[r5] = r1
            java.lang.String r1 = "illegal character: '%c'"
            java.lang.String r1 = java.lang.String.format(r1, r3)
            r2.<init>(r1)
            throw r2
        L_0x045d:
            r1 = 47
            boolean r1 = r0.g(r1)
            if (r1 == 0) goto L_0x046e
            int r1 = r0.j
            int r1 = r1 - r4
            r0.k = r1
            r17.i()
            return r6
        L_0x046e:
            boolean r1 = r0.g(r10)
            if (r1 == 0) goto L_0x04ac
            int r1 = r0.j
            int r1 = r1 - r4
            r0.k = r1
            boolean r1 = r0.g(r10)
            if (r1 == 0) goto L_0x0482
            r1 = r0
            r2 = r1
            goto L_0x048d
        L_0x0482:
            r1 = r0
            r2 = r1
        L_0x0484:
            r4 = 0
        L_0x0485:
            int r7 = r1.b()
            if (r7 == r3) goto L_0x049f
            if (r7 != r10) goto L_0x048f
        L_0x048d:
            r4 = 1
            goto L_0x0485
        L_0x048f:
            r8 = 47
            if (r7 != r8) goto L_0x049a
            if (r4 == 0) goto L_0x0485
            int r1 = r2.j
            r2.l = r1
            return r6
        L_0x049a:
            int r4 = r2.j
            r2.l = r4
            goto L_0x0484
        L_0x049f:
            int r1 = r2.j
            int r1 = r1 - r12
            r2.l = r1
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r2 = "unterminated comment"
            r1.<init>(r2)
            throw r1
        L_0x04ac:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x04b5
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_DIV
            return r1
        L_0x04b5:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DIV
            return r1
        L_0x04b8:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DOT
            return r1
        L_0x04bb:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.SUB
            boolean r2 = r0.g(r2)
            if (r2 == 0) goto L_0x04c6
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_SUB
            goto L_0x04de
        L_0x04c6:
            r2 = 45
            boolean r2 = r0.g(r2)
            if (r2 == 0) goto L_0x04de
            boolean r1 = r0.a
            if (r1 != 0) goto L_0x04dc
            boolean r1 = r0.g(r7)
            if (r1 == 0) goto L_0x04dc
            r17.i()
            return r6
        L_0x04dc:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.DEC
        L_0x04de:
            r0.a = r12
            return r1
        L_0x04e1:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.COMMA
            return r1
        L_0x04e4:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x04ed
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_ADD
            return r1
        L_0x04ed:
            r1 = 43
            boolean r1 = r0.g(r1)
            if (r1 == 0) goto L_0x04f8
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.INC
            return r1
        L_0x04f8:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ADD
            return r1
        L_0x04fb:
            boolean r1 = r0.g(r10)
            if (r1 == 0) goto L_0x050d
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x050a
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_EXP
            return r1
        L_0x050a:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.EXP
            return r1
        L_0x050d:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x0516
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_MUL
            return r1
        L_0x0516:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.MUL
            return r1
        L_0x0519:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RP
            return r1
        L_0x051c:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LP
            return r1
        L_0x051f:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.HOOK
            return r1
        L_0x0522:
            boolean r1 = r0.g(r7)
            if (r1 == 0) goto L_0x0546
            boolean r1 = r0.g(r7)
            if (r1 == 0) goto L_0x053a
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x0537
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_URSH
            return r1
        L_0x0537:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.URSH
            return r1
        L_0x053a:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x0543
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_RSH
            return r1
        L_0x0543:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RSH
            return r1
        L_0x0546:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x054f
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.GE
            return r1
        L_0x054f:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.GT
            return r1
        L_0x0552:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x0564
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x0561
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.SHEQ
            return r1
        L_0x0561:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.EQ
            return r1
        L_0x0564:
            boolean r1 = r0.g(r7)
            if (r1 == 0) goto L_0x056d
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ARROW
            return r1
        L_0x056d:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN
            return r1
        L_0x0570:
            boolean r1 = r0.g(r9)
            if (r1 == 0) goto L_0x0594
            r1 = 45
            boolean r3 = r0.g(r1)
            if (r3 == 0) goto L_0x0591
            boolean r3 = r0.g(r1)
            if (r3 == 0) goto L_0x058e
            int r1 = r0.j
            r2 = 4
            int r1 = r1 - r2
            r0.k = r1
            r17.i()
            return r6
        L_0x058e:
            r0.k(r1)
        L_0x0591:
            r0.k(r9)
        L_0x0594:
            r1 = 60
            boolean r1 = r0.g(r1)
            if (r1 == 0) goto L_0x05a8
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x05a5
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_LSH
            return r1
        L_0x05a5:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LSH
            return r1
        L_0x05a8:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x05b1
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LE
            return r1
        L_0x05b1:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LT
            return r1
        L_0x05b4:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.SEMI
            return r1
        L_0x05b7:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.COLON
            return r1
        L_0x05ba:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.BITNOT
            return r1
        L_0x05bd:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RC
            return r1
        L_0x05c0:
            r1 = 124(0x7c, float:1.74E-43)
            boolean r1 = r0.g(r1)
            if (r1 == 0) goto L_0x05cb
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.OR
            return r1
        L_0x05cb:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x05d4
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_BITOR
            return r1
        L_0x05d4:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.BITOR
            return r1
        L_0x05d7:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LC
            return r1
        L_0x05da:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x05e3
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_BITXOR
            return r1
        L_0x05e3:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.BITXOR
            return r1
        L_0x05e6:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.RB
            return r1
        L_0x05e9:
            boolean r1 = r0.g(r11)
            if (r1 == 0) goto L_0x05f2
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.AND
            return r1
        L_0x05f2:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x05fb
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_BITAND
            return r1
        L_0x05fb:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.BITAND
            return r1
        L_0x05fe:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x0607
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.ASSIGN_MOD
            return r1
        L_0x0607:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.MOD
            return r1
        L_0x060a:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.LB
            return r1
        L_0x060d:
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x061f
            boolean r1 = r0.g(r2)
            if (r1 == 0) goto L_0x061c
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.SHNE
            return r1
        L_0x061c:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.NE
            return r1
        L_0x061f:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.NOT
            return r1
        L_0x0622:
            r0.c = r5
            int r2 = r0.c(r5, r12)
        L_0x0628:
            if (r2 == r1) goto L_0x070f
            if (r2 != r3) goto L_0x062f
        L_0x062c:
            r4 = 13
            goto L_0x0646
        L_0x062f:
            r4 = 10
            if (r2 != r4) goto L_0x0648
            int r6 = r0.f
            if (r6 == r4) goto L_0x062c
            r4 = 13
            if (r6 == r4) goto L_0x0646
            r9 = 8232(0x2028, float:1.1535E-41)
            if (r6 == r9) goto L_0x0644
            r9 = 8233(0x2029, float:1.1537E-41)
            if (r6 == r9) goto L_0x0644
            goto L_0x064a
        L_0x0644:
            r2 = r6
            goto L_0x064a
        L_0x0646:
            r6 = 1
            goto L_0x064b
        L_0x0648:
            r4 = 13
        L_0x064a:
            r6 = 0
        L_0x064b:
            if (r6 != 0) goto L_0x0707
            if (r2 != r13) goto L_0x06fd
            int r2 = r17.b()
            r6 = 10
            if (r2 == r6) goto L_0x06f6
            r6 = 98
            if (r2 == r6) goto L_0x06f2
            r6 = 102(0x66, float:1.43E-43)
            if (r2 == r6) goto L_0x06ee
            r6 = 110(0x6e, float:1.54E-43)
            if (r2 == r6) goto L_0x06ea
            r6 = 114(0x72, float:1.6E-43)
            if (r2 == r6) goto L_0x06e6
            if (r2 == r8) goto L_0x06c1
            switch(r2) {
                case 116: goto L_0x06b9;
                case 117: goto L_0x0698;
                case 118: goto L_0x0694;
                default: goto L_0x066c;
            }
        L_0x066c:
            r10 = 4
            if (r15 > r2) goto L_0x06fe
            if (r2 >= r7) goto L_0x06fe
            int r2 = r2 + -48
            int r6 = r17.b()
            if (r15 > r6) goto L_0x06bd
            if (r6 >= r7) goto L_0x06bd
            int r2 = r2 * 8
            int r2 = r2 + r6
            int r2 = r2 - r15
            int r6 = r17.b()
            if (r15 > r6) goto L_0x06bd
            if (r6 >= r7) goto L_0x06bd
            r9 = 31
            if (r2 > r9) goto L_0x06bd
            int r2 = r2 * 8
            int r2 = r2 + r6
            int r2 = r2 - r15
            int r6 = r17.b()
            goto L_0x06bd
        L_0x0694:
            r2 = 11
            goto L_0x06fd
        L_0x0698:
            int r2 = r0.c
            r0.a(r14)
            r6 = 0
            r9 = 0
            r10 = 4
        L_0x06a0:
            if (r6 == r10) goto L_0x06b5
            int r11 = r17.b()
            int r9 = org.mozilla.javascript.Kit.xDigitToInt(r11, r9)
            if (r9 >= 0) goto L_0x06af
            r2 = r11
            goto L_0x0628
        L_0x06af:
            r0.a(r11)
            int r6 = r6 + 1
            goto L_0x06a0
        L_0x06b5:
            r0.c = r2
            r6 = r9
            goto L_0x06e4
        L_0x06b9:
            r10 = 4
            r2 = 9
            goto L_0x06fe
        L_0x06bd:
            r0.j(r6)
            goto L_0x06fe
        L_0x06c1:
            r10 = 4
            int r2 = r17.b()
            int r6 = org.mozilla.javascript.Kit.xDigitToInt(r2, r5)
            if (r6 >= 0) goto L_0x06d1
            r0.a(r8)
            goto L_0x0628
        L_0x06d1:
            int r9 = r17.b()
            int r6 = org.mozilla.javascript.Kit.xDigitToInt(r9, r6)
            if (r6 >= 0) goto L_0x06e4
            r0.a(r8)
            r0.a(r2)
            r2 = r9
            goto L_0x0628
        L_0x06e4:
            r2 = r6
            goto L_0x06fe
        L_0x06e6:
            r10 = 4
            r2 = 13
            goto L_0x06fe
        L_0x06ea:
            r10 = 4
            r2 = 10
            goto L_0x06fe
        L_0x06ee:
            r10 = 4
            r2 = 12
            goto L_0x06fe
        L_0x06f2:
            r10 = 4
            r2 = 8
            goto L_0x06fe
        L_0x06f6:
            r10 = 4
            int r2 = r17.b()
            goto L_0x0628
        L_0x06fd:
            r10 = 4
        L_0x06fe:
            r0.a(r2)
            int r2 = r0.c(r5, r5)
            goto L_0x0628
        L_0x0707:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r2 = "unterminated string literal"
            r1.<init>(r2)
            throw r1
        L_0x070f:
            int r2 = r0.j
            r0.l = r2
            r2 = 96
            if (r1 != r2) goto L_0x071a
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.TEMPLATE_LITERAL
            goto L_0x071c
        L_0x071a:
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.STRING
        L_0x071c:
            return r1
        L_0x071d:
            r0.c = r5
            if (r1 != r15) goto L_0x0760
            int r1 = r17.b()
            if (r1 == r8) goto L_0x0759
            r2 = 88
            if (r1 != r2) goto L_0x072c
            goto L_0x0759
        L_0x072c:
            r2 = 111(0x6f, float:1.56E-43)
            if (r1 == r2) goto L_0x0752
            r2 = 79
            if (r1 != r2) goto L_0x0735
            goto L_0x0752
        L_0x0735:
            r2 = 98
            if (r1 == r2) goto L_0x074c
            r2 = 66
            if (r1 != r2) goto L_0x073e
            goto L_0x074c
        L_0x073e:
            boolean r2 = e(r1)
            if (r2 == 0) goto L_0x0748
            r2 = 8
            r5 = 1
            goto L_0x0762
        L_0x0748:
            r0.a(r15)
            goto L_0x0760
        L_0x074c:
            int r1 = r17.b()
            r2 = 2
            goto L_0x0762
        L_0x0752:
            int r1 = r17.b()
            r2 = 8
            goto L_0x0762
        L_0x0759:
            int r1 = r17.b()
            r2 = 16
            goto L_0x0762
        L_0x0760:
            r2 = 10
        L_0x0762:
            int r3 = r0.c
            r8 = -2
            java.lang.String r9 = "number format error"
            r10 = 10
            if (r2 == r10) goto L_0x0799
            r10 = 16
            if (r2 == r10) goto L_0x0799
            r10 = 8
            if (r2 != r10) goto L_0x0775
            if (r5 == 0) goto L_0x0799
        L_0x0775:
            if (r2 != r4) goto L_0x0778
            goto L_0x0799
        L_0x0778:
            boolean r4 = e(r1)
            if (r4 == 0) goto L_0x079f
            if (r1 < r7) goto L_0x0791
            r4 = 10
            int r1 = r0.h(r4, r1)
            if (r1 == r8) goto L_0x078b
            r4 = 10
            goto L_0x07a0
        L_0x078b:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r1.<init>(r9)
            throw r1
        L_0x0791:
            r0.a(r1)
            int r1 = r17.b()
            goto L_0x0778
        L_0x0799:
            int r1 = r0.h(r2, r1)
            if (r1 == r8) goto L_0x081f
        L_0x079f:
            r4 = r2
        L_0x07a0:
            int r2 = r0.c
            if (r2 != r3) goto L_0x07af
            r2 = 10
            if (r4 != r2) goto L_0x07a9
            goto L_0x07b1
        L_0x07a9:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r1.<init>(r9)
            throw r1
        L_0x07af:
            r2 = 10
        L_0x07b1:
            r3 = 110(0x6e, float:1.54E-43)
            if (r1 != r3) goto L_0x07ba
            int r1 = r17.b()
            goto L_0x0815
        L_0x07ba:
            if (r4 != r2) goto L_0x0815
            if (r1 == r6) goto L_0x07c6
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x07c6
            r2 = 69
            if (r1 != r2) goto L_0x0815
        L_0x07c6:
            if (r1 != r6) goto L_0x07dc
            r0.a(r1)
            int r1 = r17.b()
            int r1 = r0.h(r4, r1)
            if (r1 == r8) goto L_0x07d6
            goto L_0x07dc
        L_0x07d6:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r1.<init>(r9)
            throw r1
        L_0x07dc:
            r2 = 101(0x65, float:1.42E-43)
            if (r1 == r2) goto L_0x07e4
            r2 = 69
            if (r1 != r2) goto L_0x0815
        L_0x07e4:
            r0.a(r1)
            int r1 = r17.b()
            r2 = 43
            if (r1 == r2) goto L_0x07f3
            r2 = 45
            if (r1 != r2) goto L_0x07fa
        L_0x07f3:
            r0.a(r1)
            int r1 = r17.b()
        L_0x07fa:
            boolean r2 = e(r1)
            if (r2 == 0) goto L_0x080d
            int r1 = r0.h(r4, r1)
            if (r1 == r8) goto L_0x0807
            goto L_0x0815
        L_0x0807:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r1.<init>(r9)
            throw r1
        L_0x080d:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            java.lang.String r2 = "missing exponent"
            r1.<init>(r2)
            throw r1
        L_0x0815:
            r0.j(r1)
            int r1 = r0.j
            r0.l = r1
            org.schabi.newpipe.extractor.utils.jsextractor.Token r1 = org.schabi.newpipe.extractor.utils.jsextractor.Token.NUMBER
            return r1
        L_0x081f:
            org.schabi.newpipe.extractor.exceptions.ParsingException r1 = new org.schabi.newpipe.extractor.exceptions.ParsingException
            r1.<init>(r9)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.a2.d():org.schabi.newpipe.extractor.utils.jsextractor.Token");
    }

    public final boolean g(int i2) {
        int c2 = c(true, true);
        if (c2 == i2) {
            this.l = this.j;
            return true;
        }
        k(c2);
        return false;
    }

    public final int h(int i2, int i3) {
        if (f(i2, i3)) {
            a(i3);
            i3 = b();
            if (i3 != -1) {
                while (true) {
                    if (i3 != 95) {
                        if (!f(i2, i3)) {
                            break;
                        }
                        a(i3);
                        i3 = b();
                        if (i3 == -1) {
                            return -1;
                        }
                    } else {
                        i3 = b();
                        if (i3 == 10 || i3 == -1) {
                            return -2;
                        }
                        if (!f(i2, i3)) {
                            j(i3);
                            return 95;
                        }
                        a(95);
                    }
                }
            } else {
                return -1;
            }
        }
        return i3;
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public final void i() {
        /*
            r2 = this;
        L_0x0000:
            int r0 = r2.b()
            r1 = -1
            if (r0 == r1) goto L_0x000c
            r1 = 10
            if (r0 == r1) goto L_0x000c
            goto L_0x0000
        L_0x000c:
            r2.j(r0)
            int r0 = r2.j
            r2.l = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.a2.i():void");
    }

    public final void j(int i2) {
        int i3 = this.e;
        int[] iArr = this.d;
        if (i3 != 0 && iArr[i3 - 1] == 10) {
            Kit.codeBug();
        }
        int i4 = this.e;
        this.e = i4 + 1;
        iArr[i4] = i2;
        this.j--;
    }

    public final void k(int i2) {
        int i3 = this.e;
        this.e = i3 + 1;
        this.d[i3] = i2;
        this.j--;
    }

    public Token nextToken() throws ParsingException {
        Token d2 = d();
        while (true) {
            if (d2 != Token.EOL && d2 != Token.COMMENT) {
                return d2;
            }
            d2 = d();
        }
    }
}
