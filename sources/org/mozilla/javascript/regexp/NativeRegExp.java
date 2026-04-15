package org.mozilla.javascript.regexp;

import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Symbol;
import org.mozilla.javascript.SymbolKey;
import org.mozilla.javascript.TopLevel;
import org.mozilla.javascript.Undefined;

public class NativeRegExp extends IdScriptableObject implements Function {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ANCHOR_BOL = -2;
    private static final int INDEX_LEN = 2;
    private static final int Id_compile = 1;
    private static final int Id_exec = 4;
    private static final int Id_global = 3;
    private static final int Id_ignoreCase = 4;
    private static final int Id_lastIndex = 1;
    private static final int Id_multiline = 5;
    private static final int Id_prefix = 6;
    private static final int Id_source = 2;
    private static final int Id_test = 5;
    private static final int Id_toSource = 3;
    private static final int Id_toString = 2;
    public static final int JSREG_FOLD = 2;
    public static final int JSREG_GLOB = 1;
    public static final int JSREG_MULTILINE = 4;
    public static final int MATCH = 1;
    private static final int MAX_INSTANCE_ID = 5;
    private static final int MAX_PROTOTYPE_ID = 8;
    public static final int PREFIX = 2;
    private static final Object REGEXP_TAG = new Object();
    private static final byte REOP_ALNUM = 9;
    private static final byte REOP_ALT = 31;
    private static final byte REOP_ALTPREREQ = 53;
    private static final byte REOP_ALTPREREQ2 = 55;
    private static final byte REOP_ALTPREREQi = 54;
    private static final byte REOP_ASSERT = 41;
    private static final byte REOP_ASSERTNOTTEST = 44;
    private static final byte REOP_ASSERTTEST = 43;
    private static final byte REOP_ASSERT_NOT = 42;
    private static final byte REOP_BACKREF = 13;
    private static final byte REOP_BOL = 2;
    private static final byte REOP_CLASS = 22;
    private static final byte REOP_DIGIT = 7;
    private static final byte REOP_DOT = 6;
    private static final byte REOP_EMPTY = 1;
    private static final byte REOP_END = 57;
    private static final byte REOP_ENDCHILD = 49;
    private static final byte REOP_EOL = 3;
    private static final byte REOP_FLAT = 14;
    private static final byte REOP_FLAT1 = 15;
    private static final byte REOP_FLAT1i = 17;
    private static final byte REOP_FLATi = 16;
    private static final byte REOP_JUMP = 32;
    private static final byte REOP_LPAREN = 29;
    private static final byte REOP_MINIMALOPT = 47;
    private static final byte REOP_MINIMALPLUS = 46;
    private static final byte REOP_MINIMALQUANT = 48;
    private static final byte REOP_MINIMALREPEAT = 52;
    private static final byte REOP_MINIMALSTAR = 45;
    private static final byte REOP_NCLASS = 23;
    private static final byte REOP_NONALNUM = 10;
    private static final byte REOP_NONDIGIT = 8;
    private static final byte REOP_NONSPACE = 12;
    private static final byte REOP_OPT = 28;
    private static final byte REOP_PLUS = 27;
    private static final byte REOP_QUANT = 25;
    private static final byte REOP_REPEAT = 51;
    private static final byte REOP_RPAREN = 30;
    private static final byte REOP_SIMPLE_END = 23;
    private static final byte REOP_SIMPLE_START = 1;
    private static final byte REOP_SPACE = 11;
    private static final byte REOP_STAR = 26;
    private static final byte REOP_UCFLAT1 = 18;
    private static final byte REOP_UCFLAT1i = 19;
    private static final byte REOP_WBDRY = 4;
    private static final byte REOP_WNONBDRY = 5;
    private static final int SymbolId_match = 7;
    private static final int SymbolId_search = 8;
    public static final int TEST = 0;
    private static final boolean debug = false;
    private static final long serialVersionUID = 4965263491464903264L;
    Object lastIndex;
    private int lastIndexAttr;
    private RECompiled re;

    public NativeRegExp(Scriptable scriptable, RECompiled rECompiled) {
        Double d = ScriptRuntime.zeroObj;
        this.lastIndex = d;
        this.lastIndexAttr = 6;
        this.re = rECompiled;
        setLastIndex(d);
        ScriptRuntime.setBuiltinProtoAndParent(this, scriptable, TopLevel.Builtins.RegExp);
    }

    private static void addCharacterRangeToCharSet(RECharSet rECharSet, char c, char c2) {
        int i = c / 8;
        int i2 = c2 / 8;
        if (c2 >= rECharSet.length || c > c2) {
            throw ScriptRuntime.constructError("SyntaxError", "invalid range in character class");
        }
        char c3 = (char) (c & 7);
        char c4 = (char) (c2 & 7);
        if (i == i2) {
            byte[] bArr = rECharSet.bits;
            bArr[i] = (byte) (((255 >> (7 - (c4 - c3))) << c3) | bArr[i]);
            return;
        }
        byte[] bArr2 = rECharSet.bits;
        bArr2[i] = (byte) ((255 << c3) | bArr2[i]);
        while (true) {
            i++;
            if (i < i2) {
                rECharSet.bits[i] = -1;
            } else {
                byte[] bArr3 = rECharSet.bits;
                bArr3[i2] = (byte) (bArr3[i2] | (255 >> (7 - c4)));
                return;
            }
        }
    }

    private static void addCharacterToCharSet(RECharSet rECharSet, char c) {
        int i = c / 8;
        if (c < rECharSet.length) {
            byte[] bArr = rECharSet.bits;
            bArr[i] = (byte) ((1 << (c & 7)) | bArr[i]);
            return;
        }
        throw ScriptRuntime.constructError("SyntaxError", "invalid range in character class");
    }

    private static int addIndex(byte[] bArr, int i, int i2) {
        if (i2 < 0) {
            throw Kit.codeBug();
        } else if (i2 <= 65535) {
            bArr[i] = (byte) (i2 >> 8);
            bArr[i + 1] = (byte) i2;
            return i + 2;
        } else {
            throw Context.reportRuntimeError("Too complex regexp");
        }
    }

    private static boolean backrefMatcher(REGlobalData rEGlobalData, int i, String str, int i2) {
        long[] jArr = rEGlobalData.parens;
        if (jArr == null || i >= jArr.length) {
            return false;
        }
        int parensIndex = rEGlobalData.parensIndex(i);
        if (parensIndex == -1) {
            return true;
        }
        int parensLength = rEGlobalData.parensLength(i);
        int i3 = rEGlobalData.cp;
        if (i3 + parensLength > i2) {
            return false;
        }
        if ((rEGlobalData.regexp.flags & 2) != 0) {
            for (int i4 = 0; i4 < parensLength; i4++) {
                char charAt = str.charAt(parensIndex + i4);
                char charAt2 = str.charAt(rEGlobalData.cp + i4);
                if (charAt != charAt2 && upcase(charAt) != upcase(charAt2)) {
                    return false;
                }
            }
        } else if (!str.regionMatches(parensIndex, str, i3, parensLength)) {
            return false;
        }
        rEGlobalData.cp += parensLength;
        return true;
    }

    /* JADX WARNING: type inference failed for: r17v0, types: [char[]] */
    /* JADX WARNING: type inference failed for: r5v0, types: [char] */
    /* JADX WARNING: type inference failed for: r8v0, types: [char] */
    /* JADX WARNING: type inference failed for: r10v3, types: [char] */
    /* JADX WARNING: type inference failed for: r1v8, types: [int, char] */
    /* JADX WARNING: type inference failed for: r10v4, types: [int, char] */
    /* JADX WARNING: type inference failed for: r1v15, types: [int, char] */
    /* JADX WARNING: type inference failed for: r1v25, types: [char] */
    /* JADX WARNING: type inference failed for: r8v18, types: [char] */
    /* JADX WARNING: type inference failed for: r8v24, types: [int, char] */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004e, code lost:
        r14 = r8;
        r8 = r1;
        r1 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005d, code lost:
        r11 = 0;
        r12 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x005f, code lost:
        if (r11 >= r1) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0061, code lost:
        if (r8 >= r2) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0063, code lost:
        r13 = r8 + 1;
        r12 = org.mozilla.javascript.Kit.xDigitToInt(r17[r8], r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x006b, code lost:
        if (r12 >= 0) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x006d, code lost:
        r1 = r13 - (r11 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0073, code lost:
        r11 = r11 + 1;
        r8 = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0077, code lost:
        r1 = r8;
        r8 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a4, code lost:
        r8 = '\\';
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=int, for r1v8, types: [int, char] */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean calculateBitmapSize(org.mozilla.javascript.regexp.CompilerState r15, org.mozilla.javascript.regexp.RENode r16, char[] r17, int r18, int r19) {
        /*
            r0 = r16
            r1 = r18
            r2 = r19
            r3 = 0
            r0.bmsize = r3
            r4 = 1
            r0.sense = r4
            if (r1 != r2) goto L_0x000f
            return r4
        L_0x000f:
            char r5 = r17[r1]
            r6 = 94
            if (r5 != r6) goto L_0x0019
            int r1 = r1 + 1
            r0.sense = r3
        L_0x0019:
            r5 = 0
            r6 = 0
            r7 = 0
        L_0x001c:
            if (r1 == r2) goto L_0x011b
            char r8 = r17[r1]
            r9 = 2
            r10 = 92
            if (r8 == r10) goto L_0x0029
            int r1 = r1 + 1
            goto L_0x00e3
        L_0x0029:
            int r1 = r1 + 1
            int r8 = r1 + 1
            char r1 = r17[r1]
            r11 = 68
            r12 = 65536(0x10000, float:9.18355E-41)
            if (r1 == r11) goto L_0x0118
            r11 = 83
            if (r1 == r11) goto L_0x0118
            r11 = 87
            if (r1 == r11) goto L_0x0118
            r11 = 102(0x66, float:1.43E-43)
            if (r1 == r11) goto L_0x00de
            r11 = 110(0x6e, float:1.54E-43)
            if (r1 == r11) goto L_0x00d8
            switch(r1) {
                case 48: goto L_0x00ad;
                case 49: goto L_0x00ad;
                case 50: goto L_0x00ad;
                case 51: goto L_0x00ad;
                case 52: goto L_0x00ad;
                case 53: goto L_0x00ad;
                case 54: goto L_0x00ad;
                case 55: goto L_0x00ad;
                default: goto L_0x0048;
            }
        L_0x0048:
            switch(r1) {
                case 98: goto L_0x00a7;
                case 99: goto L_0x0093;
                case 100: goto L_0x0088;
                default: goto L_0x004b;
            }
        L_0x004b:
            switch(r1) {
                case 114: goto L_0x0082;
                case 115: goto L_0x0118;
                case 116: goto L_0x007b;
                case 117: goto L_0x005c;
                case 118: goto L_0x0055;
                case 119: goto L_0x0118;
                case 120: goto L_0x0053;
                default: goto L_0x004e;
            }
        L_0x004e:
            r14 = r8
            r8 = r1
            r1 = r14
            goto L_0x00e3
        L_0x0053:
            r1 = 2
            goto L_0x005d
        L_0x0055:
            r1 = 11
            r1 = r8
            r8 = 11
            goto L_0x00e3
        L_0x005c:
            r1 = 4
        L_0x005d:
            r11 = 0
            r12 = 0
        L_0x005f:
            if (r11 >= r1) goto L_0x0077
            if (r8 >= r2) goto L_0x0077
            int r13 = r8 + 1
            char r8 = r17[r8]
            int r12 = org.mozilla.javascript.Kit.xDigitToInt(r8, r12)
            if (r12 >= 0) goto L_0x0073
            int r11 = r11 + 1
            int r8 = r13 - r11
            r1 = r8
            goto L_0x00a4
        L_0x0073:
            int r11 = r11 + 1
            r8 = r13
            goto L_0x005f
        L_0x0077:
            r1 = r8
            r8 = r12
            goto L_0x00e3
        L_0x007b:
            r1 = 9
            r1 = r8
            r8 = 9
            goto L_0x00e3
        L_0x0082:
            r1 = 13
            r1 = r8
            r8 = 13
            goto L_0x00e3
        L_0x0088:
            if (r6 == 0) goto L_0x008d
            r0.bmsize = r12
            return r4
        L_0x008d:
            r1 = 57
            r1 = r8
            r8 = 57
            goto L_0x00e3
        L_0x0093:
            if (r8 >= r2) goto L_0x00a2
            char r1 = r17[r8]
            boolean r1 = isControlLetter(r1)
            if (r1 == 0) goto L_0x00a2
            int r1 = r8 + 1
            char r8 = r17[r8]
            goto L_0x00a4
        L_0x00a2:
            int r1 = r8 + -1
        L_0x00a4:
            r8 = 92
            goto L_0x00e3
        L_0x00a7:
            r1 = 8
            r1 = r8
            r8 = 8
            goto L_0x00e3
        L_0x00ad:
            int r1 = r1 + -48
            char r10 = r17[r8]
            r11 = 48
            if (r11 > r10) goto L_0x004e
            r12 = 55
            if (r10 > r12) goto L_0x004e
            int r8 = r8 + 1
            int r1 = r1 * 8
            int r10 = r10 + -48
            int r10 = r10 + r1
            char r1 = r17[r8]
            if (r11 > r1) goto L_0x00d5
            if (r1 > r12) goto L_0x00d5
            int r8 = r8 + 1
            int r11 = r10 * 8
            int r1 = r1 + -48
            int r1 = r1 + r11
            r11 = 255(0xff, float:3.57E-43)
            if (r1 > r11) goto L_0x00d3
            goto L_0x004e
        L_0x00d3:
            int r8 = r8 + -1
        L_0x00d5:
            r1 = r8
            r8 = r10
            goto L_0x00e3
        L_0x00d8:
            r1 = 10
            r1 = r8
            r8 = 10
            goto L_0x00e3
        L_0x00de:
            r1 = 12
            r1 = r8
            r8 = 12
        L_0x00e3:
            if (r6 == 0) goto L_0x00f1
            if (r7 <= r8) goto L_0x00ef
            java.lang.String r0 = "msg.bad.range"
            java.lang.String r1 = ""
            reportError(r0, r1)
            return r3
        L_0x00ef:
            r6 = 0
            goto L_0x0101
        L_0x00f1:
            int r10 = r2 + -1
            if (r1 >= r10) goto L_0x0101
            char r10 = r17[r1]
            r11 = 45
            if (r10 != r11) goto L_0x0101
            int r1 = r1 + 1
            char r7 = (char) r8
            r6 = 1
            goto L_0x001c
        L_0x0101:
            r10 = r15
            int r11 = r10.flags
            r9 = r9 & r11
            if (r9 == 0) goto L_0x0113
            char r8 = (char) r8
            char r9 = upcase(r8)
            char r8 = downcase(r8)
            if (r9 < r8) goto L_0x0113
            r8 = r9
        L_0x0113:
            if (r8 <= r5) goto L_0x001c
            r5 = r8
            goto L_0x001c
        L_0x0118:
            r0.bmsize = r12
            return r4
        L_0x011b:
            int r5 = r5 + r4
            r0.bmsize = r5
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.calculateBitmapSize(org.mozilla.javascript.regexp.CompilerState, org.mozilla.javascript.regexp.RENode, char[], int, int):boolean");
    }

    private static boolean classMatcher(REGlobalData rEGlobalData, RECharSet rECharSet, char c) {
        if (!rECharSet.converted) {
            processCharSet(rEGlobalData, rECharSet);
        }
        int i = c >> 3;
        int i2 = rECharSet.length;
        boolean z = true;
        if (!(i2 == 0 || c >= i2 || (rECharSet.bits[i] & (1 << (c & 7))) == 0)) {
            z = false;
        }
        return rECharSet.sense ^ z;
    }

    public static RECompiled compileRE(Context context, String str, String str2, boolean z) {
        int i;
        int i2;
        RECompiled rECompiled = new RECompiled(str);
        int length = str.length();
        if (str2 != null) {
            i = 0;
            for (int i3 = 0; i3 < str2.length(); i3++) {
                char charAt = str2.charAt(i3);
                if (charAt == 'g') {
                    i2 = 1;
                } else if (charAt == 'i') {
                    i2 = 2;
                } else if (charAt == 'm') {
                    i2 = 4;
                } else {
                    reportError("msg.invalid.re.flag", String.valueOf(charAt));
                    i2 = 0;
                }
                if ((i & i2) != 0) {
                    reportError("msg.invalid.re.flag", String.valueOf(charAt));
                }
                i |= i2;
            }
        } else {
            i = 0;
        }
        rECompiled.flags = i;
        CompilerState compilerState = new CompilerState(context, rECompiled.source, length, i);
        if (z && length > 0) {
            RENode rENode = new RENode((byte) 14);
            compilerState.result = rENode;
            rENode.chr = compilerState.cpbegin[0];
            rENode.length = length;
            rENode.flatIndex = 0;
            compilerState.progLength += 5;
        } else if (!parseDisjunction(compilerState)) {
            return null;
        } else {
            if (compilerState.maxBackReference > compilerState.parenCount) {
                compilerState = new CompilerState(context, rECompiled.source, length, i);
                compilerState.backReferenceLimit = compilerState.parenCount;
                if (!parseDisjunction(compilerState)) {
                    return null;
                }
            }
        }
        rECompiled.program = new byte[(compilerState.progLength + 1)];
        int i4 = compilerState.classCount;
        if (i4 != 0) {
            rECompiled.classList = new RECharSet[i4];
            rECompiled.classCount = i4;
        }
        int emitREBytecode = emitREBytecode(compilerState, rECompiled, 0, compilerState.result);
        byte[] bArr = rECompiled.program;
        bArr[emitREBytecode] = REOP_END;
        rECompiled.parenCount = compilerState.parenCount;
        byte b = bArr[0];
        if (b == 2) {
            rECompiled.anchorCh = -2;
        } else if (b != 31) {
            switch (b) {
                case 14:
                case 16:
                    rECompiled.anchorCh = rECompiled.source[getIndex(bArr, 1)];
                    break;
                case 15:
                case 17:
                    rECompiled.anchorCh = (char) (bArr[1] & 255);
                    break;
                case 18:
                case 19:
                    rECompiled.anchorCh = (char) getIndex(bArr, 1);
                    break;
            }
        } else {
            RENode rENode2 = compilerState.result;
            if (rENode2.kid.op == 2 && rENode2.kid2.op == 2) {
                rECompiled.anchorCh = -2;
            }
        }
        return rECompiled;
    }

    private static void doFlat(CompilerState compilerState, char c) {
        RENode rENode = new RENode((byte) 14);
        compilerState.result = rENode;
        rENode.chr = c;
        rENode.length = 1;
        rENode.flatIndex = -1;
        compilerState.progLength += 3;
    }

    private static char downcase(char c) {
        if (c < 128) {
            return ('A' > c || c > 'Z') ? c : (char) (c + ' ');
        }
        char lowerCase = Character.toLowerCase(c);
        return lowerCase < 128 ? c : lowerCase;
    }

    private static int emitREBytecode(CompilerState compilerState, RECompiled rECompiled, int i, RENode rENode) {
        int i2;
        byte b;
        byte b2;
        byte b3;
        int i3;
        byte[] bArr = rECompiled.program;
        while (rENode != null) {
            int i4 = i + 1;
            byte b4 = rENode.op;
            bArr[i] = b4;
            boolean z = true;
            if (b4 != 1) {
                if (b4 != 22) {
                    if (b4 == 25) {
                        int i5 = rENode.min;
                        if (i5 == 0 && rENode.max == -1) {
                            int i6 = i4 - 1;
                            if (rENode.greedy) {
                                b3 = REOP_STAR;
                            } else {
                                b3 = REOP_MINIMALSTAR;
                            }
                            bArr[i6] = b3;
                        } else if (i5 == 0 && rENode.max == 1) {
                            int i7 = i4 - 1;
                            if (rENode.greedy) {
                                b2 = REOP_OPT;
                            } else {
                                b2 = REOP_MINIMALOPT;
                            }
                            bArr[i7] = b2;
                        } else if (i5 == 1 && rENode.max == -1) {
                            int i8 = i4 - 1;
                            if (rENode.greedy) {
                                b = REOP_PLUS;
                            } else {
                                b = REOP_MINIMALPLUS;
                            }
                            bArr[i8] = b;
                        } else {
                            if (!rENode.greedy) {
                                bArr[i4 - 1] = REOP_MINIMALQUANT;
                            }
                            i4 = addIndex(bArr, addIndex(bArr, i4, i5), rENode.max + 1);
                        }
                        int addIndex = addIndex(bArr, addIndex(bArr, i4, rENode.parenCount), rENode.parenIndex);
                        int emitREBytecode = emitREBytecode(compilerState, rECompiled, addIndex + 2, rENode.kid);
                        i2 = emitREBytecode + 1;
                        bArr[emitREBytecode] = REOP_ENDCHILD;
                        resolveForwardJump(bArr, addIndex, i2);
                    } else if (b4 != 29) {
                        if (b4 != 31) {
                            if (b4 == 13) {
                                i = addIndex(bArr, i4, rENode.parenIndex);
                            } else if (b4 == 14) {
                                if (rENode.flatIndex != -1) {
                                    while (true) {
                                        RENode rENode2 = rENode.next;
                                        if (rENode2 != null && rENode2.op == 14) {
                                            int i9 = rENode.flatIndex;
                                            int i10 = rENode.length;
                                            if (i9 + i10 == rENode2.flatIndex) {
                                                rENode.length = i10 + rENode2.length;
                                                rENode.next = rENode2.next;
                                            }
                                        }
                                    }
                                }
                                int i11 = rENode.flatIndex;
                                if (i11 == -1 || rENode.length <= 1) {
                                    char c = rENode.chr;
                                    if (c < 256) {
                                        if ((compilerState.flags & 2) != 0) {
                                            bArr[i4 - 1] = REOP_FLAT1i;
                                        } else {
                                            bArr[i4 - 1] = REOP_FLAT1;
                                        }
                                        i2 = i4 + 1;
                                        bArr[i4] = (byte) c;
                                    } else {
                                        if ((compilerState.flags & 2) != 0) {
                                            bArr[i4 - 1] = REOP_UCFLAT1i;
                                        } else {
                                            bArr[i4 - 1] = REOP_UCFLAT1;
                                        }
                                        i = addIndex(bArr, i4, c);
                                    }
                                } else {
                                    if ((compilerState.flags & 2) != 0) {
                                        bArr[i4 - 1] = REOP_FLATi;
                                    } else {
                                        bArr[i4 - 1] = 14;
                                    }
                                    i = addIndex(bArr, addIndex(bArr, i4, i11), rENode.length);
                                }
                            } else if (b4 == 41) {
                                int emitREBytecode2 = emitREBytecode(compilerState, rECompiled, i4 + 2, rENode.kid);
                                i2 = emitREBytecode2 + 1;
                                bArr[emitREBytecode2] = REOP_ASSERTTEST;
                                resolveForwardJump(bArr, i4, i2);
                            } else if (b4 != 42) {
                                switch (b4) {
                                    case 53:
                                    case 54:
                                    case 55:
                                        if (b4 != 54) {
                                            z = false;
                                        }
                                        char c2 = rENode.chr;
                                        if (z) {
                                            c2 = upcase(c2);
                                        }
                                        addIndex(bArr, i4, c2);
                                        int i12 = i4 + 2;
                                        if (z) {
                                            i3 = upcase((char) rENode.index);
                                        } else {
                                            i3 = rENode.index;
                                        }
                                        addIndex(bArr, i12, i3);
                                        i4 = i12 + 2;
                                        break;
                                }
                            } else {
                                int emitREBytecode3 = emitREBytecode(compilerState, rECompiled, i4 + 2, rENode.kid);
                                i2 = emitREBytecode3 + 1;
                                bArr[emitREBytecode3] = REOP_ASSERTNOTTEST;
                                resolveForwardJump(bArr, i4, i2);
                            }
                        }
                        RENode rENode3 = rENode.kid2;
                        int emitREBytecode4 = emitREBytecode(compilerState, rECompiled, i4 + 2, rENode.kid);
                        int i13 = emitREBytecode4 + 1;
                        bArr[emitREBytecode4] = REOP_JUMP;
                        int i14 = i13 + 2;
                        resolveForwardJump(bArr, i4, i14);
                        int emitREBytecode5 = emitREBytecode(compilerState, rECompiled, i14, rENode3);
                        int i15 = emitREBytecode5 + 1;
                        bArr[emitREBytecode5] = REOP_JUMP;
                        i = i15 + 2;
                        resolveForwardJump(bArr, i13, i);
                        resolveForwardJump(bArr, i15, i);
                    } else {
                        int emitREBytecode6 = emitREBytecode(compilerState, rECompiled, addIndex(bArr, i4, rENode.parenIndex), rENode.kid);
                        bArr[emitREBytecode6] = REOP_RPAREN;
                        i = addIndex(bArr, emitREBytecode6 + 1, rENode.parenIndex);
                    }
                    i = i2;
                } else {
                    if (!rENode.sense) {
                        bArr[i4 - 1] = 23;
                    }
                    i = addIndex(bArr, i4, rENode.index);
                    rECompiled.classList[rENode.index] = new RECharSet(rENode.bmsize, rENode.startIndex, rENode.kidlen, rENode.sense);
                }
                rENode = rENode.next;
            } else {
                i4--;
            }
            i = i4;
            rENode = rENode.next;
        }
        return i;
    }

    private static String escapeRegExp(Object obj) {
        String scriptRuntime = ScriptRuntime.toString(obj);
        StringBuilder sb = null;
        int i = 0;
        for (int indexOf = scriptRuntime.indexOf(47); indexOf > -1; indexOf = scriptRuntime.indexOf(47, indexOf + 1)) {
            if (indexOf == i || scriptRuntime.charAt(indexOf - 1) != '\\') {
                if (sb == null) {
                    sb = new StringBuilder();
                }
                sb.append(scriptRuntime, i, indexOf);
                sb.append("\\/");
                i = indexOf + 1;
            }
        }
        if (sb == null) {
            return scriptRuntime;
        }
        sb.append(scriptRuntime, i, scriptRuntime.length());
        return sb.toString();
    }

    private Object execSub(Context context, Scriptable scriptable, Object[] objArr, int i) {
        String str;
        double d;
        RegExpImpl impl = getImpl(context);
        if (objArr.length == 0) {
            str = impl.input;
            if (str == null) {
                str = ScriptRuntime.toString(Undefined.instance);
            }
        } else {
            str = ScriptRuntime.toString(objArr[0]);
        }
        String str2 = str;
        if ((this.re.flags & 1) != 0) {
            d = ScriptRuntime.toInteger(this.lastIndex);
        } else {
            d = 0.0d;
        }
        if (d < 0.0d || ((double) str2.length()) < d) {
            setLastIndex(ScriptRuntime.zeroObj);
            return null;
        }
        int[] iArr = {(int) d};
        Object executeRegExp = executeRegExp(context, scriptable, impl, str2, iArr, i);
        if ((this.re.flags & 1) == 0) {
            return executeRegExp;
        }
        if (executeRegExp == null || executeRegExp == Undefined.instance) {
            setLastIndex(ScriptRuntime.zeroObj);
            return executeRegExp;
        }
        setLastIndex(Double.valueOf((double) iArr[0]));
        return executeRegExp;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v33, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v35, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v52, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v61, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v62, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v63, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v64, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v66, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v69, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v70, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v72, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v74, resolved type: byte} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0325, code lost:
        r13 = r10 + getOffset(r9, r10);
        r10 = r10 + 2;
        r4 = r10 + 1;
        r2 = r9[r10];
        r10 = r7.cp;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x0337, code lost:
        if (reopIsSimple(r2) == false) goto L_0x0355;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0339, code lost:
        r0 = simpleMatch(r20, r21, r2, r9, r4, r22, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x0345, code lost:
        if (r0 >= 0) goto L_0x034c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0347, code lost:
        r10 = r13 + 1;
        r2 = r9[r13];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x034c, code lost:
        r6 = r9[r0];
        r14 = r0 + 1;
        r16 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0355, code lost:
        r6 = r2;
        r14 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x0357, code lost:
        pushBackTrackState(r20, r9[r13], r13 + 1, r10, r15, r12);
        r2 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0364, code lost:
        r10 = r14;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x0390, code lost:
        switch(r2) {
            case 25: goto L_0x03b2;
            case 26: goto L_0x03ac;
            case 27: goto L_0x03a7;
            case 28: goto L_0x03a3;
            default: goto L_0x0393;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x0393, code lost:
        switch(r2) {
            case 45: goto L_0x03a1;
            case 46: goto L_0x039f;
            case 47: goto L_0x039d;
            case 48: goto L_0x039b;
            default: goto L_0x0396;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x039a, code lost:
        throw org.mozilla.javascript.Kit.codeBug();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x039b, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x039d, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x039f, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x03a1, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x03a3, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x03a4, code lost:
        r14 = r10;
        r2 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x03a7, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x03a8, code lost:
        r14 = r10;
        r2 = -1;
        r13 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x03ac, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x03ad, code lost:
        r14 = r10;
        r2 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x03af, code lost:
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x03b0, code lost:
        r10 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x03b2, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x03b3, code lost:
        r1 = getOffset(r9, r10);
        r10 = r10 + 2;
        r2 = getOffset(r9, r10) - 1;
        r13 = r1;
        r14 = r10 + 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x03c4, code lost:
        pushProgState(r20, r13, r2, r7.cp, (org.mozilla.javascript.regexp.REBackTrackData) null, r15, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x03d1, code lost:
        if (r10 == false) goto L_0x03e4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x03d3, code lost:
        pushBackTrackState(r7, REOP_REPEAT, r14);
        r1 = r14 + 6;
        r2 = r1 + 1;
        r1 = r9[r1];
        r10 = r2;
        r12 = r14;
        r15 = 51;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x03e2, code lost:
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x03e4, code lost:
        if (r13 == 0) goto L_0x03f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x03e6, code lost:
        r0 = r14 + 6;
        r2 = r9[r0];
        r10 = r0 + 1;
        r12 = r14;
        r15 = 52;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x03f2, code lost:
        pushBackTrackState(r7, REOP_MINIMALREPEAT, r14);
        popProgState(r20);
        r14 = r14 + 4;
        r14 = r14 + getOffset(r9, r14);
        r1 = r9[r14];
        r10 = r14 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x0405, code lost:
        r11 = false;
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x0048, code lost:
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0092, code lost:
        r16 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ac, code lost:
        if (classMatcher(r7, r7.regexp.classList[r1], r3) == false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b9, code lost:
        if (r3 != r1) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0106, code lost:
        r10 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0188, code lost:
        r12 = r0;
        r15 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01a1, code lost:
        r12 = r0;
        r15 = r1;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v11, types: [byte] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v15, types: [byte] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean executeREBytecode(org.mozilla.javascript.regexp.REGlobalData r20, java.lang.String r21, int r22) {
        /*
            r7 = r20
            r8 = r22
            org.mozilla.javascript.regexp.RECompiled r0 = r7.regexp
            byte[] r9 = r0.program
            r10 = 1
            r11 = 0
            byte r12 = r9[r11]
            int r0 = r0.anchorCh
            r13 = 1
            r14 = 57
            if (r0 >= 0) goto L_0x0042
            boolean r0 = reopIsSimple(r12)
            if (r0 == 0) goto L_0x0042
        L_0x0019:
            int r0 = r7.cp
            if (r0 > r8) goto L_0x003e
            r6 = 1
            r0 = r20
            r1 = r21
            r2 = r12
            r3 = r9
            r4 = r10
            r5 = r22
            int r0 = simpleMatch(r0, r1, r2, r3, r4, r5, r6)
            if (r0 < 0) goto L_0x0033
            int r10 = r0 + 1
            byte r12 = r9[r0]
            r0 = 1
            goto L_0x003f
        L_0x0033:
            int r0 = r7.skipped
            int r0 = r0 + r13
            r7.skipped = r0
            int r0 = r7.cp
            int r0 = r0 + r13
            r7.cp = r0
            goto L_0x0019
        L_0x003e:
            r0 = 0
        L_0x003f:
            if (r0 != 0) goto L_0x0042
            return r11
        L_0x0042:
            r2 = r12
            r12 = 0
            r15 = 57
            r16 = 0
        L_0x0048:
            boolean r0 = reopIsSimple(r2)
            if (r0 == 0) goto L_0x0067
            r6 = 1
            r0 = r20
            r1 = r21
            r3 = r9
            r4 = r10
            r5 = r22
            int r0 = simpleMatch(r0, r1, r2, r3, r4, r5, r6)
            if (r0 < 0) goto L_0x005f
            r1 = 1
            goto L_0x0060
        L_0x005f:
            r1 = 0
        L_0x0060:
            if (r1 == 0) goto L_0x0063
            r10 = r0
        L_0x0063:
            r16 = r1
            goto L_0x02df
        L_0x0067:
            if (r2 == r14) goto L_0x0407
            r6 = 51
            r5 = 52
            r4 = -1
            switch(r2) {
                case 25: goto L_0x0258;
                case 26: goto L_0x0258;
                case 27: goto L_0x0258;
                case 28: goto L_0x0258;
                case 29: goto L_0x037b;
                case 30: goto L_0x0366;
                case 31: goto L_0x0325;
                case 32: goto L_0x031a;
                default: goto L_0x0071;
            }
        L_0x0071:
            r3 = 44
            switch(r2) {
                case 41: goto L_0x02ba;
                case 42: goto L_0x0274;
                case 43: goto L_0x025d;
                case 44: goto L_0x025d;
                case 45: goto L_0x0258;
                case 46: goto L_0x0258;
                case 47: goto L_0x0258;
                case 48: goto L_0x0258;
                case 49: goto L_0x0252;
                default: goto L_0x0076;
            }
        L_0x0076:
            switch(r2) {
                case 51: goto L_0x0170;
                case 52: goto L_0x00bc;
                case 53: goto L_0x0080;
                case 54: goto L_0x0080;
                case 55: goto L_0x0080;
                default: goto L_0x0079;
            }
        L_0x0079:
            java.lang.String r0 = "invalid bytecode"
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug(r0)
            throw r0
        L_0x0080:
            int r0 = getIndex(r9, r10)
            char r0 = (char) r0
            int r10 = r10 + 2
            int r1 = getIndex(r9, r10)
            char r1 = (char) r1
            int r10 = r10 + 2
            int r3 = r7.cp
            if (r3 != r8) goto L_0x0096
        L_0x0092:
            r16 = 0
            goto L_0x02df
        L_0x0096:
            r6 = r21
            char r3 = r6.charAt(r3)
            r4 = 55
            if (r2 != r4) goto L_0x00af
            if (r3 == r0) goto L_0x0325
            org.mozilla.javascript.regexp.RECompiled r0 = r7.regexp
            org.mozilla.javascript.regexp.RECharSet[] r0 = r0.classList
            r0 = r0[r1]
            boolean r0 = classMatcher(r7, r0, r3)
            if (r0 != 0) goto L_0x0325
            goto L_0x0092
        L_0x00af:
            r4 = 54
            if (r2 != r4) goto L_0x00b7
            char r3 = upcase(r3)
        L_0x00b7:
            if (r3 == r0) goto L_0x0325
            if (r3 == r1) goto L_0x0325
            goto L_0x0092
        L_0x00bc:
            r6 = r21
            org.mozilla.javascript.regexp.REProgState r12 = popProgState(r20)
            if (r16 != 0) goto L_0x0109
            int r2 = r12.max
            if (r2 == r4) goto L_0x00d1
            if (r2 <= 0) goto L_0x00cb
            goto L_0x00d1
        L_0x00cb:
            int r0 = r12.continuationPc
            int r1 = r12.continuationOp
            goto L_0x0188
        L_0x00d1:
            int r1 = r12.min
            int r3 = r7.cp
            r15 = 0
            int r0 = r12.continuationOp
            int r12 = r12.continuationPc
            r17 = r0
            r0 = r20
            r14 = -1
            r4 = r15
            r15 = 52
            r5 = r17
            r6 = r12
            pushProgState(r0, r1, r2, r3, r4, r5, r6)
            int r0 = getIndex(r9, r10)
            int r1 = r10 + 2
            int r2 = getIndex(r9, r1)
            int r1 = r1 + 4
            r3 = 0
        L_0x00f5:
            if (r3 >= r0) goto L_0x00ff
            int r4 = r2 + r3
            r7.setParens(r4, r14, r11)
            int r3 = r3 + 1
            goto L_0x00f5
        L_0x00ff:
            int r0 = r1 + 1
            byte r2 = r9[r1]
        L_0x0103:
            r12 = r10
            r14 = 57
        L_0x0106:
            r10 = r0
            goto L_0x0048
        L_0x0109:
            r14 = -1
            r15 = 52
            int r0 = r12.min
            if (r0 != 0) goto L_0x011c
            int r1 = r7.cp
            int r2 = r12.index
            if (r1 != r2) goto L_0x011c
            int r0 = r12.continuationPc
            int r1 = r12.continuationOp
            goto L_0x01a1
        L_0x011c:
            int r1 = r12.max
            if (r0 == 0) goto L_0x0122
            int r0 = r0 + -1
        L_0x0122:
            r17 = r0
            if (r1 == r14) goto L_0x0128
            int r1 = r1 + -1
        L_0x0128:
            r2 = r1
            int r3 = r7.cp
            r4 = 0
            int r5 = r12.continuationOp
            int r6 = r12.continuationPc
            r0 = r20
            r1 = r17
            pushProgState(r0, r1, r2, r3, r4, r5, r6)
            if (r17 == 0) goto L_0x0155
            int r0 = getIndex(r9, r10)
            int r1 = r10 + 2
            int r2 = getIndex(r9, r1)
            int r1 = r1 + 4
            r3 = 0
        L_0x0146:
            if (r3 >= r0) goto L_0x0150
            int r4 = r2 + r3
            r7.setParens(r4, r14, r11)
            int r3 = r3 + 1
            goto L_0x0146
        L_0x0150:
            int r0 = r1 + 1
            byte r2 = r9[r1]
            goto L_0x0103
        L_0x0155:
            int r0 = r12.continuationPc
            int r1 = r12.continuationOp
            pushBackTrackState(r7, r15, r10)
            popProgState(r20)
            int r10 = r10 + 4
            int r2 = getOffset(r9, r10)
            int r10 = r10 + r2
            int r2 = r10 + 1
            byte r3 = r9[r10]
            r12 = r0
            r15 = r1
            r10 = r2
            r2 = r3
            goto L_0x038c
        L_0x0170:
            r14 = -1
        L_0x0171:
            org.mozilla.javascript.regexp.REProgState r12 = popProgState(r20)
            if (r16 != 0) goto L_0x018c
            int r0 = r12.min
            if (r0 != 0) goto L_0x017d
            r16 = 1
        L_0x017d:
            int r0 = r12.continuationPc
            int r1 = r12.continuationOp
            int r10 = r10 + 4
            int r2 = getOffset(r9, r10)
            int r10 = r10 + r2
        L_0x0188:
            r12 = r0
            r15 = r1
            goto L_0x02df
        L_0x018c:
            int r0 = r12.min
            if (r0 != 0) goto L_0x01a5
            int r1 = r7.cp
            int r2 = r12.index
            if (r1 != r2) goto L_0x01a5
            int r0 = r12.continuationPc
            int r1 = r12.continuationOp
            int r10 = r10 + 4
            int r2 = getOffset(r9, r10)
            int r10 = r10 + r2
        L_0x01a1:
            r12 = r0
            r15 = r1
            goto L_0x0092
        L_0x01a5:
            int r1 = r12.max
            if (r0 == 0) goto L_0x01ab
            int r0 = r0 + -1
        L_0x01ab:
            r15 = r0
            if (r1 == r14) goto L_0x01b0
            int r1 = r1 + -1
        L_0x01b0:
            r17 = r1
            if (r17 != 0) goto L_0x01c5
            int r0 = r12.continuationPc
            int r1 = r12.continuationOp
            int r10 = r10 + 4
            int r2 = getOffset(r9, r10)
            int r10 = r10 + r2
            r12 = r0
            r15 = r1
            r16 = 1
            goto L_0x02df
        L_0x01c5:
            int r0 = r10 + 6
            byte r2 = r9[r0]
            int r5 = r7.cp
            boolean r1 = reopIsSimple(r2)
            if (r1 == 0) goto L_0x0201
            int r4 = r0 + 1
            r16 = 1
            r0 = r20
            r1 = r21
            r3 = r9
            r18 = r5
            r5 = r22
            r6 = r16
            int r0 = simpleMatch(r0, r1, r2, r3, r4, r5, r6)
            if (r0 >= 0) goto L_0x01fc
            if (r15 != 0) goto L_0x01ea
            r0 = 1
            goto L_0x01eb
        L_0x01ea:
            r0 = 0
        L_0x01eb:
            int r1 = r12.continuationPc
            int r2 = r12.continuationOp
            int r10 = r10 + 4
            int r3 = getOffset(r9, r10)
            int r10 = r10 + r3
            r16 = r0
            r12 = r1
            r15 = r2
            goto L_0x02df
        L_0x01fc:
            r16 = r0
            r19 = 1
            goto L_0x0207
        L_0x0201:
            r18 = r5
            r19 = r16
            r16 = r0
        L_0x0207:
            r4 = 0
            int r5 = r12.continuationOp
            int r6 = r12.continuationPc
            r0 = r20
            r1 = r15
            r2 = r17
            r3 = r18
            pushProgState(r0, r1, r2, r3, r4, r5, r6)
            if (r15 != 0) goto L_0x023b
            r1 = 51
            int r4 = r12.continuationOp
            int r5 = r12.continuationPc
            r0 = r20
            r2 = r10
            r3 = r18
            pushBackTrackState(r0, r1, r2, r3, r4, r5)
            int r0 = getIndex(r9, r10)
            int r1 = r10 + 2
            int r1 = getIndex(r9, r1)
            r2 = 0
        L_0x0231:
            if (r2 >= r0) goto L_0x023b
            int r3 = r1 + r2
            r7.setParens(r3, r14, r11)
            int r2 = r2 + 1
            goto L_0x0231
        L_0x023b:
            byte r2 = r9[r16]
            r0 = 49
            if (r2 == r0) goto L_0x024c
            int r0 = r16 + 1
            r12 = r10
            r16 = r19
            r14 = 57
            r15 = 51
            goto L_0x0106
        L_0x024c:
            r16 = r19
            r6 = 51
            goto L_0x0171
        L_0x0252:
            r10 = r12
            r2 = r15
            r16 = 1
            goto L_0x0048
        L_0x0258:
            r6 = 52
            r14 = -1
            goto L_0x0390
        L_0x025d:
            org.mozilla.javascript.regexp.REProgState r0 = popProgState(r20)
            int r1 = r0.index
            r7.cp = r1
            org.mozilla.javascript.regexp.REBackTrackData r1 = r0.backTrack
            r7.backTrackStackTop = r1
            int r12 = r0.continuationPc
            int r0 = r0.continuationOp
            if (r2 != r3) goto L_0x0271
            r16 = r16 ^ 1
        L_0x0271:
            r15 = r0
            goto L_0x02df
        L_0x0274:
            int r0 = getIndex(r9, r10)
            int r14 = r10 + r0
            int r10 = r10 + 2
            int r17 = r10 + 1
            byte r10 = r9[r10]
            boolean r0 = reopIsSimple(r10)
            if (r0 == 0) goto L_0x02a3
            r6 = 0
            r0 = r20
            r1 = r21
            r2 = r10
            r5 = 44
            r3 = r9
            r4 = r17
            r13 = 44
            r5 = r22
            int r0 = simpleMatch(r0, r1, r2, r3, r4, r5, r6)
            if (r0 < 0) goto L_0x02a5
            byte r0 = r9[r0]
            if (r0 != r13) goto L_0x02a5
            r10 = r17
            goto L_0x0092
        L_0x02a3:
            r13 = 44
        L_0x02a5:
            r1 = 0
            r2 = 0
            int r3 = r7.cp
            org.mozilla.javascript.regexp.REBackTrackData r4 = r7.backTrackStackTop
            r0 = r20
            r5 = r15
            r6 = r12
            pushProgState(r0, r1, r2, r3, r4, r5, r6)
            pushBackTrackState(r7, r13, r14)
            r2 = r10
            r10 = r17
            goto L_0x038b
        L_0x02ba:
            int r0 = getIndex(r9, r10)
            int r13 = r10 + r0
            int r10 = r10 + 2
            int r14 = r10 + 1
            byte r10 = r9[r10]
            boolean r0 = reopIsSimple(r10)
            if (r0 == 0) goto L_0x0306
            r6 = 0
            r0 = r20
            r1 = r21
            r2 = r10
            r3 = r9
            r4 = r14
            r5 = r22
            int r0 = simpleMatch(r0, r1, r2, r3, r4, r5, r6)
            if (r0 >= 0) goto L_0x0306
            r10 = r14
            goto L_0x0092
        L_0x02df:
            if (r16 != 0) goto L_0x0300
            org.mozilla.javascript.regexp.REBackTrackData r0 = r7.backTrackStackTop
            if (r0 == 0) goto L_0x02ff
            org.mozilla.javascript.regexp.REBackTrackData r1 = r0.previous
            r7.backTrackStackTop = r1
            long[] r1 = r0.parens
            r7.parens = r1
            int r1 = r0.cp
            r7.cp = r1
            org.mozilla.javascript.regexp.REProgState r1 = r0.stateStackTop
            r7.stateStackTop = r1
            int r15 = r0.continuationOp
            int r12 = r0.continuationPc
            int r10 = r0.pc
            int r2 = r0.op
            goto L_0x038b
        L_0x02ff:
            return r11
        L_0x0300:
            int r0 = r10 + 1
            byte r2 = r9[r10]
            goto L_0x038a
        L_0x0306:
            r1 = 0
            r2 = 0
            int r3 = r7.cp
            org.mozilla.javascript.regexp.REBackTrackData r4 = r7.backTrackStackTop
            r0 = r20
            r5 = r15
            r6 = r12
            pushProgState(r0, r1, r2, r3, r4, r5, r6)
            r0 = 43
            pushBackTrackState(r7, r0, r13)
            r2 = r10
            goto L_0x0364
        L_0x031a:
            int r0 = getOffset(r9, r10)
            int r10 = r10 + r0
            int r0 = r10 + 1
            byte r2 = r9[r10]
            goto L_0x038a
        L_0x0325:
            int r0 = getOffset(r9, r10)
            int r13 = r10 + r0
            int r10 = r10 + 2
            int r4 = r10 + 1
            byte r2 = r9[r10]
            int r10 = r7.cp
            boolean r0 = reopIsSimple(r2)
            if (r0 == 0) goto L_0x0355
            r6 = 1
            r0 = r20
            r1 = r21
            r3 = r9
            r5 = r22
            int r0 = simpleMatch(r0, r1, r2, r3, r4, r5, r6)
            if (r0 >= 0) goto L_0x034c
            int r10 = r13 + 1
            byte r2 = r9[r13]
            goto L_0x038b
        L_0x034c:
            int r1 = r0 + 1
            byte r0 = r9[r0]
            r6 = r0
            r14 = r1
            r16 = 1
            goto L_0x0357
        L_0x0355:
            r6 = r2
            r14 = r4
        L_0x0357:
            int r2 = r13 + 1
            byte r1 = r9[r13]
            r0 = r20
            r3 = r10
            r4 = r15
            r5 = r12
            pushBackTrackState(r0, r1, r2, r3, r4, r5)
            r2 = r6
        L_0x0364:
            r10 = r14
            goto L_0x038b
        L_0x0366:
            int r0 = getIndex(r9, r10)
            int r10 = r10 + 2
            int r1 = r7.parensIndex(r0)
            int r2 = r7.cp
            int r2 = r2 - r1
            r7.setParens(r0, r1, r2)
            int r0 = r10 + 1
            byte r2 = r9[r10]
            goto L_0x038a
        L_0x037b:
            int r0 = getIndex(r9, r10)
            int r10 = r10 + 2
            int r1 = r7.cp
            r7.setParens(r0, r1, r11)
            int r0 = r10 + 1
            byte r2 = r9[r10]
        L_0x038a:
            r10 = r0
        L_0x038b:
            r13 = 1
        L_0x038c:
            r14 = 57
            goto L_0x0048
        L_0x0390:
            switch(r2) {
                case 25: goto L_0x03b2;
                case 26: goto L_0x03ac;
                case 27: goto L_0x03a7;
                case 28: goto L_0x03a3;
                default: goto L_0x0393;
            }
        L_0x0393:
            switch(r2) {
                case 45: goto L_0x03a1;
                case 46: goto L_0x039f;
                case 47: goto L_0x039d;
                case 48: goto L_0x039b;
                default: goto L_0x0396;
            }
        L_0x0396:
            java.lang.RuntimeException r0 = org.mozilla.javascript.Kit.codeBug()
            throw r0
        L_0x039b:
            r0 = 0
            goto L_0x03b3
        L_0x039d:
            r0 = 0
            goto L_0x03a4
        L_0x039f:
            r0 = 0
            goto L_0x03a8
        L_0x03a1:
            r0 = 0
            goto L_0x03ad
        L_0x03a3:
            r0 = 1
        L_0x03a4:
            r14 = r10
            r2 = 1
            goto L_0x03af
        L_0x03a7:
            r0 = 1
        L_0x03a8:
            r14 = r10
            r2 = -1
            r13 = 1
            goto L_0x03b0
        L_0x03ac:
            r0 = 1
        L_0x03ad:
            r14 = r10
            r2 = -1
        L_0x03af:
            r13 = 0
        L_0x03b0:
            r10 = r0
            goto L_0x03c4
        L_0x03b2:
            r0 = 1
        L_0x03b3:
            int r1 = getOffset(r9, r10)
            int r10 = r10 + 2
            int r2 = getOffset(r9, r10)
            r3 = 1
            int r2 = r2 - r3
            int r10 = r10 + 2
            r13 = r1
            r14 = r10
            goto L_0x03b0
        L_0x03c4:
            int r3 = r7.cp
            r4 = 0
            r0 = r20
            r1 = r13
            r5 = r15
            r11 = 52
            r6 = r12
            pushProgState(r0, r1, r2, r3, r4, r5, r6)
            if (r10 == 0) goto L_0x03e4
            r0 = 51
            pushBackTrackState(r7, r0, r14)
            int r1 = r14 + 6
            int r2 = r1 + 1
            byte r1 = r9[r1]
            r10 = r2
            r12 = r14
            r15 = 51
        L_0x03e2:
            r2 = r1
            goto L_0x0405
        L_0x03e4:
            if (r13 == 0) goto L_0x03f2
            int r0 = r14 + 6
            int r1 = r0 + 1
            byte r0 = r9[r0]
            r2 = r0
            r10 = r1
            r12 = r14
            r15 = 52
            goto L_0x0405
        L_0x03f2:
            pushBackTrackState(r7, r11, r14)
            popProgState(r20)
            int r14 = r14 + 4
            int r0 = getOffset(r9, r14)
            int r14 = r14 + r0
            int r0 = r14 + 1
            byte r1 = r9[r14]
            r10 = r0
            goto L_0x03e2
        L_0x0405:
            r11 = 0
            goto L_0x038b
        L_0x0407:
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.executeREBytecode(org.mozilla.javascript.regexp.REGlobalData, java.lang.String, int):boolean");
    }

    private static boolean flatNIMatcher(REGlobalData rEGlobalData, int i, int i2, String str, int i3) {
        if (rEGlobalData.cp + i2 > i3) {
            return false;
        }
        char[] cArr = rEGlobalData.regexp.source;
        for (int i4 = 0; i4 < i2; i4++) {
            char c = cArr[i + i4];
            char charAt = str.charAt(rEGlobalData.cp + i4);
            if (c != charAt && upcase(c) != upcase(charAt)) {
                return false;
            }
        }
        rEGlobalData.cp += i2;
        return true;
    }

    private static boolean flatNMatcher(REGlobalData rEGlobalData, int i, int i2, String str, int i3) {
        if (rEGlobalData.cp + i2 > i3) {
            return false;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            if (rEGlobalData.regexp.source[i + i4] != str.charAt(rEGlobalData.cp + i4)) {
                return false;
            }
        }
        rEGlobalData.cp += i2;
        return true;
    }

    private static int getDecimalValue(char c, CompilerState compilerState, int i, String str) {
        int i2 = compilerState.cp;
        char[] cArr = compilerState.cpbegin;
        int i3 = c - '0';
        boolean z = false;
        while (true) {
            int i4 = compilerState.cp;
            if (i4 == compilerState.cpend) {
                break;
            }
            char c2 = cArr[i4];
            if (!isDigit(c2)) {
                break;
            }
            if (!z) {
                int i5 = (c2 - '0') + (i3 * 10);
                if (i5 < i) {
                    i3 = i5;
                } else {
                    i3 = i;
                    z = true;
                }
            }
            compilerState.cp++;
        }
        if (z) {
            reportError(str, String.valueOf(cArr, i2, compilerState.cp - i2));
        }
        return i3;
    }

    private static RegExpImpl getImpl(Context context) {
        return (RegExpImpl) ScriptRuntime.getRegExpProxy(context);
    }

    private static int getIndex(byte[] bArr, int i) {
        return (bArr[i + 1] & 255) | ((bArr[i] & 255) << 8);
    }

    private static int getOffset(byte[] bArr, int i) {
        return getIndex(bArr, i);
    }

    public static void init(Context context, Scriptable scriptable, boolean z) {
        NativeRegExp nativeRegExp = new NativeRegExp();
        nativeRegExp.re = compileRE(context, "", (String) null, false);
        nativeRegExp.activatePrototypeMap(8);
        nativeRegExp.setParentScope(scriptable);
        nativeRegExp.setPrototype(ScriptableObject.getObjectPrototype(scriptable));
        NativeRegExpCtor nativeRegExpCtor = new NativeRegExpCtor();
        nativeRegExp.defineProperty("constructor", (Object) nativeRegExpCtor, 2);
        ScriptRuntime.setFunctionProtoAndParent(nativeRegExpCtor, scriptable);
        nativeRegExpCtor.setImmunePrototypeProperty(nativeRegExp);
        if (z) {
            nativeRegExp.sealObject();
            nativeRegExpCtor.sealObject();
        }
        ScriptableObject.defineProperty(scriptable, "RegExp", nativeRegExpCtor, 2);
    }

    private static boolean isControlLetter(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
    }

    public static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isLineTerm(char c) {
        return ScriptRuntime.isJSLineTerminator(c);
    }

    private static boolean isREWhiteSpace(int i) {
        return ScriptRuntime.isJSWhitespaceOrLineTerminator(i);
    }

    private static boolean isWord(char c) {
        return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || isDigit(c) || c == '_';
    }

    private static boolean matchRegExp(REGlobalData rEGlobalData, RECompiled rECompiled, String str, int i, int i2, boolean z) {
        boolean z2;
        int i3 = rECompiled.parenCount;
        if (i3 != 0) {
            rEGlobalData.parens = new long[i3];
        } else {
            rEGlobalData.parens = null;
        }
        rEGlobalData.backTrackStackTop = null;
        rEGlobalData.stateStackTop = null;
        if (z || (rECompiled.flags & 4) != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        rEGlobalData.multiline = z2;
        rEGlobalData.regexp = rECompiled;
        int i4 = rECompiled.anchorCh;
        int i5 = i;
        while (i5 <= i2) {
            if (i4 >= 0) {
                while (i5 != i2) {
                    char charAt = str.charAt(i5);
                    if (charAt != i4 && ((rEGlobalData.regexp.flags & 2) == 0 || upcase(charAt) != upcase((char) i4))) {
                        i5++;
                    }
                }
                return false;
            }
            rEGlobalData.cp = i5;
            rEGlobalData.skipped = i5 - i;
            for (int i6 = 0; i6 < rECompiled.parenCount; i6++) {
                rEGlobalData.parens[i6] = -1;
            }
            boolean executeREBytecode = executeREBytecode(rEGlobalData, str, i2);
            rEGlobalData.backTrackStackTop = null;
            rEGlobalData.stateStackTop = null;
            if (executeREBytecode) {
                return true;
            }
            if (i4 != -2 || rEGlobalData.multiline) {
                i5 = rEGlobalData.skipped + i + 1;
            } else {
                rEGlobalData.skipped = i2;
                return false;
            }
        }
        return false;
    }

    private static boolean parseAlternative(CompilerState compilerState) {
        char c;
        char[] cArr = compilerState.cpbegin;
        RENode rENode = null;
        RENode rENode2 = null;
        while (true) {
            int i = compilerState.cp;
            if (i != compilerState.cpend && (c = cArr[i]) != '|' && (compilerState.parenNesting == 0 || c != ')')) {
                if (!parseTerm(compilerState)) {
                    return false;
                }
                if (rENode == null) {
                    rENode = compilerState.result;
                    rENode2 = rENode;
                } else {
                    rENode2.next = compilerState.result;
                }
                while (true) {
                    RENode rENode3 = rENode2.next;
                    if (rENode3 != null) {
                        rENode2 = rENode3;
                    }
                }
            }
        }
        if (rENode == null) {
            compilerState.result = new RENode((byte) 1);
        } else {
            compilerState.result = rENode;
        }
        return true;
    }

    private static boolean parseDisjunction(CompilerState compilerState) {
        int i;
        int i2;
        byte b;
        if (!parseAlternative(compilerState)) {
            return false;
        }
        char[] cArr = compilerState.cpbegin;
        int i3 = compilerState.cp;
        if (i3 != cArr.length && cArr[i3] == '|') {
            compilerState.cp = i3 + 1;
            RENode rENode = new RENode(REOP_ALT);
            rENode.kid = compilerState.result;
            if (!parseDisjunction(compilerState)) {
                return false;
            }
            RENode rENode2 = compilerState.result;
            rENode.kid2 = rENode2;
            compilerState.result = rENode;
            RENode rENode3 = rENode.kid;
            byte b2 = rENode3.op;
            if (b2 == 14 && rENode2.op == 14) {
                if ((compilerState.flags & 2) == 0) {
                    b = REOP_ALTPREREQ;
                } else {
                    b = REOP_ALTPREREQi;
                }
                rENode.op = b;
                rENode.chr = rENode3.chr;
                rENode.index = rENode2.chr;
                compilerState.progLength += 13;
            } else if (b2 == 22 && (i2 = rENode3.index) < 256 && rENode2.op == 14 && (compilerState.flags & 2) == 0) {
                rENode.op = REOP_ALTPREREQ2;
                rENode.chr = rENode2.chr;
                rENode.index = i2;
                compilerState.progLength += 13;
            } else if (b2 == 14 && rENode2.op == 22 && (i = rENode2.index) < 256 && (compilerState.flags & 2) == 0) {
                rENode.op = REOP_ALTPREREQ2;
                rENode.chr = rENode3.chr;
                rENode.index = i;
                compilerState.progLength += 13;
            } else {
                compilerState.progLength += 9;
            }
        }
        return true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v37, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v38, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v67, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v68, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v69, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v70, resolved type: char} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x0380  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x0397  */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x03d7 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:190:0x03d8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean parseTerm(org.mozilla.javascript.regexp.CompilerState r16) {
        /*
            r0 = r16
            char[] r1 = r0.cpbegin
            int r2 = r0.cp
            int r3 = r2 + 1
            r0.cp = r3
            char r2 = r1[r2]
            int r4 = r0.parenCount
            r5 = 36
            r6 = 3
            r7 = 1
            if (r2 == r5) goto L_0x03fc
            r5 = 46
            r8 = 42
            r9 = 6
            r12 = 63
            r14 = 0
            if (r2 == r5) goto L_0x02f6
            if (r2 == r12) goto L_0x02e9
            r5 = 94
            r15 = 2
            if (r2 == r5) goto L_0x02dc
            r5 = 91
            r13 = 92
            java.lang.String r10 = ""
            if (r2 == r5) goto L_0x0290
            r5 = 14
            r11 = 4
            if (r2 == r13) goto L_0x00d4
            switch(r2) {
                case 40: goto L_0x0052;
                case 41: goto L_0x004c;
                case 42: goto L_0x02e9;
                case 43: goto L_0x02e9;
                default: goto L_0x0035;
            }
        L_0x0035:
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r5)
            r0.result = r3
            r3.chr = r2
            r3.length = r7
            int r2 = r0.cp
            int r2 = r2 - r7
            r3.flatIndex = r2
            int r2 = r0.progLength
            int r2 = r2 + r6
            r0.progLength = r2
            goto L_0x0302
        L_0x004c:
            java.lang.String r0 = "msg.re.unmatched.right.paren"
            reportError(r0, r10)
            return r14
        L_0x0052:
            int r2 = r3 + 1
            int r5 = r0.cpend
            if (r2 >= r5) goto L_0x008f
            char r2 = r1[r3]
            if (r2 != r12) goto L_0x008f
            int r2 = r3 + 1
            char r2 = r1[r2]
            r5 = 61
            if (r2 == r5) goto L_0x006c
            r6 = 33
            if (r2 == r6) goto L_0x006c
            r6 = 58
            if (r2 != r6) goto L_0x008f
        L_0x006c:
            int r3 = r3 + r15
            r0.cp = r3
            if (r2 != r5) goto L_0x007e
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r3 = 41
            r2.<init>(r3)
            int r3 = r0.progLength
            int r3 = r3 + r11
            r0.progLength = r3
            goto L_0x00a3
        L_0x007e:
            r3 = 33
            if (r2 != r3) goto L_0x008d
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r2.<init>(r8)
            int r3 = r0.progLength
            int r3 = r3 + r11
            r0.progLength = r3
            goto L_0x00a3
        L_0x008d:
            r2 = 0
            goto L_0x00a3
        L_0x008f:
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r3 = 29
            r2.<init>(r3)
            int r3 = r0.progLength
            int r3 = r3 + r9
            r0.progLength = r3
            int r3 = r0.parenCount
            int r5 = r3 + 1
            r0.parenCount = r5
            r2.parenIndex = r3
        L_0x00a3:
            int r3 = r0.parenNesting
            int r3 = r3 + r7
            r0.parenNesting = r3
            boolean r3 = parseDisjunction(r16)
            if (r3 != 0) goto L_0x00af
            return r14
        L_0x00af:
            int r3 = r0.cp
            int r5 = r0.cpend
            if (r3 == r5) goto L_0x00ce
            char r5 = r1[r3]
            r6 = 41
            if (r5 == r6) goto L_0x00bc
            goto L_0x00ce
        L_0x00bc:
            int r3 = r3 + r7
            r0.cp = r3
            int r3 = r0.parenNesting
            int r3 = r3 - r7
            r0.parenNesting = r3
            if (r2 == 0) goto L_0x0302
            org.mozilla.javascript.regexp.RENode r3 = r0.result
            r2.kid = r3
            r0.result = r2
            goto L_0x0302
        L_0x00ce:
            java.lang.String r0 = "msg.unterm.paren"
            reportError(r0, r10)
            return r14
        L_0x00d4:
            int r2 = r0.cpend
            if (r3 >= r2) goto L_0x0289
            int r9 = r3 + 1
            r0.cp = r9
            char r3 = r1[r3]
            r12 = 66
            if (r3 == r12) goto L_0x027b
            r12 = 68
            if (r3 == r12) goto L_0x026b
            r12 = 83
            if (r3 == r12) goto L_0x025b
            r12 = 87
            r8 = 10
            if (r3 == r12) goto L_0x024b
            r12 = 102(0x66, float:1.43E-43)
            if (r3 == r12) goto L_0x0244
            r12 = 110(0x6e, float:1.54E-43)
            if (r3 == r12) goto L_0x023f
            r8 = 13
            java.lang.String r12 = "msg.bad.backref"
            r14 = 48
            switch(r3) {
                case 48: goto L_0x0217;
                case 49: goto L_0x01b7;
                case 50: goto L_0x01b7;
                case 51: goto L_0x01b7;
                case 52: goto L_0x01b7;
                case 53: goto L_0x01b7;
                case 54: goto L_0x01b7;
                case 55: goto L_0x01b7;
                case 56: goto L_0x01b7;
                case 57: goto L_0x01b7;
                default: goto L_0x0101;
            }
        L_0x0101:
            switch(r3) {
                case 98: goto L_0x01aa;
                case 99: goto L_0x018a;
                case 100: goto L_0x017b;
                default: goto L_0x0104;
            }
        L_0x0104:
            r2 = 11
            switch(r3) {
                case 114: goto L_0x0176;
                case 115: goto L_0x0168;
                case 116: goto L_0x0161;
                case 117: goto L_0x0137;
                case 118: goto L_0x0132;
                case 119: goto L_0x0122;
                case 120: goto L_0x0120;
                default: goto L_0x0109;
            }
        L_0x0109:
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r2.<init>(r5)
            r0.result = r2
            r2.chr = r3
            r2.length = r7
            int r3 = r0.cp
            int r3 = r3 - r7
            r2.flatIndex = r3
            int r2 = r0.progLength
            int r2 = r2 + r6
            r0.progLength = r2
            goto L_0x0302
        L_0x0120:
            r11 = 2
            goto L_0x0137
        L_0x0122:
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r3 = 9
            r2.<init>(r3)
            r0.result = r2
            int r2 = r0.progLength
            int r2 = r2 + r7
            r0.progLength = r2
            goto L_0x0302
        L_0x0132:
            doFlat(r0, r2)
            goto L_0x0302
        L_0x0137:
            r2 = 0
            r3 = 0
        L_0x0139:
            if (r2 >= r11) goto L_0x015b
            int r5 = r0.cp
            int r6 = r0.cpend
            if (r5 >= r6) goto L_0x015b
            int r6 = r5 + 1
            r0.cp = r6
            char r5 = r1[r5]
            int r3 = org.mozilla.javascript.Kit.xDigitToInt(r5, r3)
            if (r3 >= 0) goto L_0x0158
            int r3 = r0.cp
            int r2 = r2 + r15
            int r3 = r3 - r2
            int r2 = r3 + 1
            r0.cp = r2
            char r3 = r1[r3]
            goto L_0x015b
        L_0x0158:
            int r2 = r2 + 1
            goto L_0x0139
        L_0x015b:
            char r2 = (char) r3
            doFlat(r0, r2)
            goto L_0x0302
        L_0x0161:
            r2 = 9
            doFlat(r0, r2)
            goto L_0x0302
        L_0x0168:
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r2)
            r0.result = r3
            int r2 = r0.progLength
            int r2 = r2 + r7
            r0.progLength = r2
            goto L_0x0302
        L_0x0176:
            doFlat(r0, r8)
            goto L_0x0302
        L_0x017b:
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r3 = 7
            r2.<init>(r3)
            r0.result = r2
            int r2 = r0.progLength
            int r2 = r2 + r7
            r0.progLength = r2
            goto L_0x0302
        L_0x018a:
            if (r9 >= r2) goto L_0x01a0
            char r2 = r1[r9]
            boolean r2 = isControlLetter(r2)
            if (r2 == 0) goto L_0x01a0
            int r2 = r0.cp
            int r3 = r2 + 1
            r0.cp = r3
            char r2 = r1[r2]
            r2 = r2 & 31
            char r13 = (char) r2
            goto L_0x01a5
        L_0x01a0:
            int r2 = r0.cp
            int r2 = r2 - r7
            r0.cp = r2
        L_0x01a5:
            doFlat(r0, r13)
            goto L_0x0302
        L_0x01aa:
            org.mozilla.javascript.regexp.RENode r1 = new org.mozilla.javascript.regexp.RENode
            r1.<init>(r11)
            r0.result = r1
            int r1 = r0.progLength
            int r1 = r1 + r7
            r0.progLength = r1
            return r7
        L_0x01b7:
            int r9 = r9 - r7
            java.lang.String r2 = "msg.overlarge.backref"
            r5 = 65535(0xffff, float:9.1834E-41)
            int r2 = getDecimalValue(r3, r0, r5, r2)
            int r5 = r0.backReferenceLimit
            if (r2 <= r5) goto L_0x01ca
            org.mozilla.javascript.Context r5 = r0.cx
            reportWarning(r5, r12, r10)
        L_0x01ca:
            int r5 = r0.backReferenceLimit
            if (r2 <= r5) goto L_0x01ff
            r0.cp = r9
            r2 = 56
            if (r3 < r2) goto L_0x01d9
            doFlat(r0, r13)
            goto L_0x0302
        L_0x01d9:
            int r9 = r9 + r7
            r0.cp = r9
            int r3 = r3 - r14
        L_0x01dd:
            r2 = 32
            if (r3 >= r2) goto L_0x01f9
            int r2 = r0.cp
            int r5 = r0.cpend
            if (r2 >= r5) goto L_0x01f9
            char r5 = r1[r2]
            if (r5 < r14) goto L_0x01f9
            r6 = 55
            if (r5 > r6) goto L_0x01f9
            int r2 = r2 + 1
            r0.cp = r2
            int r3 = r3 * 8
            int r5 = r5 + -48
            int r3 = r3 + r5
            goto L_0x01dd
        L_0x01f9:
            char r2 = (char) r3
            doFlat(r0, r2)
            goto L_0x0302
        L_0x01ff:
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r8)
            r0.result = r3
            int r5 = r2 + -1
            r3.parenIndex = r5
            int r3 = r0.progLength
            int r3 = r3 + r6
            r0.progLength = r3
            int r3 = r0.maxBackReference
            if (r3 >= r2) goto L_0x0302
            r0.maxBackReference = r2
            goto L_0x0302
        L_0x0217:
            org.mozilla.javascript.Context r2 = r0.cx
            reportWarning(r2, r12, r10)
            r2 = 0
        L_0x021d:
            r3 = 32
            if (r2 >= r3) goto L_0x0239
            int r3 = r0.cp
            int r5 = r0.cpend
            if (r3 >= r5) goto L_0x0239
            char r5 = r1[r3]
            if (r5 < r14) goto L_0x0239
            r6 = 55
            if (r5 > r6) goto L_0x0239
            int r3 = r3 + 1
            r0.cp = r3
            int r2 = r2 * 8
            int r5 = r5 + -48
            int r2 = r2 + r5
            goto L_0x021d
        L_0x0239:
            char r2 = (char) r2
            doFlat(r0, r2)
            goto L_0x0302
        L_0x023f:
            doFlat(r0, r8)
            goto L_0x0302
        L_0x0244:
            r2 = 12
            doFlat(r0, r2)
            goto L_0x0302
        L_0x024b:
            r2 = 12
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r8)
            r0.result = r3
            int r3 = r0.progLength
            int r3 = r3 + r7
            r0.progLength = r3
            goto L_0x0302
        L_0x025b:
            r2 = 12
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r2)
            r0.result = r3
            int r2 = r0.progLength
            int r2 = r2 + r7
            r0.progLength = r2
            goto L_0x0302
        L_0x026b:
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r3 = 8
            r2.<init>(r3)
            r0.result = r2
            int r2 = r0.progLength
            int r2 = r2 + r7
            r0.progLength = r2
            goto L_0x0302
        L_0x027b:
            org.mozilla.javascript.regexp.RENode r1 = new org.mozilla.javascript.regexp.RENode
            r2 = 5
            r1.<init>(r2)
            r0.result = r1
            int r1 = r0.progLength
            int r1 = r1 + r7
            r0.progLength = r1
            return r7
        L_0x0289:
            java.lang.String r0 = "msg.trail.backslash"
            reportError(r0, r10)
        L_0x028e:
            r0 = 0
            return r0
        L_0x0290:
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r3 = 22
            r2.<init>(r3)
            r0.result = r2
            int r3 = r0.cp
            r2.startIndex = r3
        L_0x029d:
            int r2 = r0.cp
            int r5 = r0.cpend
            if (r2 != r5) goto L_0x02a9
            java.lang.String r0 = "msg.unterm.class"
            reportError(r0, r10)
            goto L_0x028e
        L_0x02a9:
            char r5 = r1[r2]
            if (r5 != r13) goto L_0x02b2
            int r2 = r2 + 1
            r0.cp = r2
            goto L_0x02d6
        L_0x02b2:
            r8 = 93
            if (r5 != r8) goto L_0x02d6
            org.mozilla.javascript.regexp.RENode r5 = r0.result
            int r8 = r2 - r3
            r5.kidlen = r8
            int r8 = r0.classCount
            int r9 = r8 + 1
            r0.classCount = r9
            r5.index = r8
            int r8 = r2 + 1
            r0.cp = r8
            boolean r2 = calculateBitmapSize(r0, r5, r1, r3, r2)
            if (r2 != 0) goto L_0x02d0
            r2 = 0
            return r2
        L_0x02d0:
            int r2 = r0.progLength
            int r2 = r2 + r6
            r0.progLength = r2
            goto L_0x0302
        L_0x02d6:
            int r2 = r0.cp
            int r2 = r2 + r7
            r0.cp = r2
            goto L_0x029d
        L_0x02dc:
            org.mozilla.javascript.regexp.RENode r1 = new org.mozilla.javascript.regexp.RENode
            r1.<init>(r15)
            r0.result = r1
            int r1 = r0.progLength
            int r1 = r1 + r7
            r0.progLength = r1
            return r7
        L_0x02e9:
            int r3 = r3 - r7
            char r0 = r1[r3]
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r1 = "msg.bad.quant"
            reportError(r1, r0)
            goto L_0x028e
        L_0x02f6:
            org.mozilla.javascript.regexp.RENode r2 = new org.mozilla.javascript.regexp.RENode
            r2.<init>(r9)
            r0.result = r2
            int r2 = r0.progLength
            int r2 = r2 + r7
            r0.progLength = r2
        L_0x0302:
            org.mozilla.javascript.regexp.RENode r2 = r0.result
            int r3 = r0.cp
            int r5 = r0.cpend
            if (r3 != r5) goto L_0x030b
            return r7
        L_0x030b:
            char r5 = r1[r3]
            r6 = -1
            r8 = 25
            r9 = 42
            if (r5 == r9) goto L_0x03c1
            r9 = 43
            if (r5 == r9) goto L_0x03ae
            r9 = 63
            if (r5 == r9) goto L_0x039a
            r9 = 123(0x7b, float:1.72E-43)
            if (r5 == r9) goto L_0x0323
            r5 = 0
            goto L_0x03d5
        L_0x0323:
            int r5 = r3 + 1
            r0.cp = r5
            int r9 = r1.length
            if (r5 >= r9) goto L_0x0394
            char r5 = r1[r5]
            boolean r9 = isDigit(r5)
            if (r9 == 0) goto L_0x0394
            int r9 = r0.cp
            int r9 = r9 + r7
            r0.cp = r9
            java.lang.String r9 = "msg.overlarge.min"
            r10 = 65535(0xffff, float:9.1834E-41)
            int r5 = getDecimalValue(r5, r0, r10, r9)
            int r9 = r0.cp
            int r10 = r1.length
            if (r9 >= r10) goto L_0x0394
            char r10 = r1[r9]
            r11 = 44
            if (r10 != r11) goto L_0x037b
            int r9 = r9 + r7
            r0.cp = r9
            int r11 = r1.length
            if (r9 >= r11) goto L_0x037b
            char r10 = r1[r9]
            boolean r9 = isDigit(r10)
            if (r9 == 0) goto L_0x037c
            int r9 = r0.cp
            int r9 = r9 + r7
            r0.cp = r9
            int r11 = r1.length
            if (r9 >= r11) goto L_0x037c
            java.lang.String r6 = "msg.overlarge.max"
            r9 = 65535(0xffff, float:9.1834E-41)
            int r6 = getDecimalValue(r10, r0, r9, r6)
            int r9 = r0.cp
            char r10 = r1[r9]
            if (r5 <= r6) goto L_0x037c
            java.lang.String r0 = "msg.max.lt.min"
            java.lang.String r1 = java.lang.String.valueOf(r10)
            reportError(r0, r1)
            goto L_0x028e
        L_0x037b:
            r6 = r5
        L_0x037c:
            r9 = 125(0x7d, float:1.75E-43)
            if (r10 != r9) goto L_0x0394
            org.mozilla.javascript.regexp.RENode r9 = new org.mozilla.javascript.regexp.RENode
            r9.<init>(r8)
            r0.result = r9
            r9.min = r5
            r9.max = r6
            int r5 = r0.progLength
            r6 = 12
            int r5 = r5 + r6
            r0.progLength = r5
            r5 = 1
            goto L_0x0395
        L_0x0394:
            r5 = 0
        L_0x0395:
            if (r5 != 0) goto L_0x03d5
            r0.cp = r3
            goto L_0x03d5
        L_0x039a:
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r8)
            r0.result = r3
            r5 = 0
            r3.min = r5
            r3.max = r7
            int r3 = r0.progLength
            r5 = 8
            int r3 = r3 + r5
            r0.progLength = r3
            goto L_0x03d4
        L_0x03ae:
            r5 = 8
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r8)
            r0.result = r3
            r3.min = r7
            r3.max = r6
            int r3 = r0.progLength
            int r3 = r3 + r5
            r0.progLength = r3
            goto L_0x03d4
        L_0x03c1:
            r5 = 8
            org.mozilla.javascript.regexp.RENode r3 = new org.mozilla.javascript.regexp.RENode
            r3.<init>(r8)
            r0.result = r3
            r8 = 0
            r3.min = r8
            r3.max = r6
            int r3 = r0.progLength
            int r3 = r3 + r5
            r0.progLength = r3
        L_0x03d4:
            r5 = 1
        L_0x03d5:
            if (r5 != 0) goto L_0x03d8
            return r7
        L_0x03d8:
            int r3 = r0.cp
            int r3 = r3 + r7
            r0.cp = r3
            org.mozilla.javascript.regexp.RENode r5 = r0.result
            r5.kid = r2
            r5.parenIndex = r4
            int r2 = r0.parenCount
            int r2 = r2 - r4
            r5.parenCount = r2
            int r2 = r0.cpend
            if (r3 >= r2) goto L_0x03f9
            char r1 = r1[r3]
            r2 = 63
            if (r1 != r2) goto L_0x03f9
            int r3 = r3 + r7
            r0.cp = r3
            r0 = 0
            r5.greedy = r0
            goto L_0x03fb
        L_0x03f9:
            r5.greedy = r7
        L_0x03fb:
            return r7
        L_0x03fc:
            org.mozilla.javascript.regexp.RENode r1 = new org.mozilla.javascript.regexp.RENode
            r1.<init>(r6)
            r0.result = r1
            int r1 = r0.progLength
            int r1 = r1 + r7
            r0.progLength = r1
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.parseTerm(org.mozilla.javascript.regexp.CompilerState):boolean");
    }

    private static REProgState popProgState(REGlobalData rEGlobalData) {
        REProgState rEProgState = rEGlobalData.stateStackTop;
        rEGlobalData.stateStackTop = rEProgState.previous;
        return rEProgState;
    }

    private static void processCharSet(REGlobalData rEGlobalData, RECharSet rECharSet) {
        synchronized (rECharSet) {
            if (!rECharSet.converted) {
                processCharSetImpl(rEGlobalData, rECharSet);
                rECharSet.converted = true;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x005f, code lost:
        r17 = r9;
        r9 = r2;
        r2 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0088, code lost:
        r8 = 0;
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x008a, code lost:
        if (r8 >= r2) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x008c, code lost:
        if (r9 >= r3) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008e, code lost:
        r16 = r9 + 1;
        r9 = toASCIIHexDigit(r0.regexp.source[r9]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x009a, code lost:
        if (r9 >= 0) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009c, code lost:
        r9 = r16 - (r8 + 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a1, code lost:
        r14 = (r14 << 4) | r9;
        r8 = r8 + 1;
        r9 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a9, code lost:
        r10 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00aa, code lost:
        r2 = (char) r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void processCharSetImpl(org.mozilla.javascript.regexp.REGlobalData r18, org.mozilla.javascript.regexp.RECharSet r19) {
        /*
            r0 = r18
            r1 = r19
            int r2 = r1.startIndex
            int r3 = r1.strlength
            int r3 = r3 + r2
            int r4 = r1.length
            int r4 = r4 + 7
            r5 = 8
            int r4 = r4 / r5
            byte[] r4 = new byte[r4]
            r1.bits = r4
            if (r2 != r3) goto L_0x0017
            return
        L_0x0017:
            org.mozilla.javascript.regexp.RECompiled r4 = r0.regexp
            char[] r4 = r4.source
            char r4 = r4[r2]
            r6 = 94
            r7 = 0
            if (r4 != r6) goto L_0x0024
            int r2 = r2 + 1
        L_0x0024:
            r4 = 0
            r6 = 0
        L_0x0026:
            if (r2 == r3) goto L_0x01d3
            org.mozilla.javascript.regexp.RECompiled r8 = r0.regexp
            char[] r8 = r8.source
            char r9 = r8[r2]
            r10 = 92
            r11 = 2
            r12 = 45
            r13 = 1
            if (r9 == r10) goto L_0x003a
            int r2 = r2 + 1
            goto L_0x0134
        L_0x003a:
            int r2 = r2 + 1
            int r9 = r2 + 1
            char r2 = r8[r2]
            r14 = 68
            if (r2 == r14) goto L_0x01bc
            r14 = 83
            if (r2 == r14) goto L_0x01a4
            r14 = 87
            if (r2 == r14) goto L_0x018c
            r14 = 102(0x66, float:1.43E-43)
            if (r2 == r14) goto L_0x012f
            r14 = 110(0x6e, float:1.54E-43)
            if (r2 == r14) goto L_0x0129
            r14 = 48
            switch(r2) {
                case 48: goto L_0x0100;
                case 49: goto L_0x0100;
                case 50: goto L_0x0100;
                case 51: goto L_0x0100;
                case 52: goto L_0x0100;
                case 53: goto L_0x0100;
                case 54: goto L_0x0100;
                case 55: goto L_0x0100;
                default: goto L_0x0059;
            }
        L_0x0059:
            switch(r2) {
                case 98: goto L_0x00fc;
                case 99: goto L_0x00df;
                case 100: goto L_0x00d2;
                default: goto L_0x005c;
            }
        L_0x005c:
            switch(r2) {
                case 114: goto L_0x00cb;
                case 115: goto L_0x00b3;
                case 116: goto L_0x00ac;
                case 117: goto L_0x0087;
                case 118: goto L_0x0080;
                case 119: goto L_0x0068;
                case 120: goto L_0x0066;
                default: goto L_0x005f;
            }
        L_0x005f:
            r17 = r9
            r9 = r2
            r2 = r17
            goto L_0x0134
        L_0x0066:
            r2 = 2
            goto L_0x0088
        L_0x0068:
            if (r4 == 0) goto L_0x006e
            addCharacterToCharSet(r1, r12)
            r4 = 0
        L_0x006e:
            int r2 = r1.length
            int r2 = r2 - r13
        L_0x0071:
            if (r2 < 0) goto L_0x01d0
            char r8 = (char) r2
            boolean r10 = isWord(r8)
            if (r10 == 0) goto L_0x007d
            addCharacterToCharSet(r1, r8)
        L_0x007d:
            int r2 = r2 + -1
            goto L_0x0071
        L_0x0080:
            r2 = 11
            r2 = r9
            r9 = 11
            goto L_0x0134
        L_0x0087:
            r2 = 4
        L_0x0088:
            r8 = 0
            r14 = 0
        L_0x008a:
            if (r8 >= r2) goto L_0x00a9
            if (r9 >= r3) goto L_0x00a9
            org.mozilla.javascript.regexp.RECompiled r15 = r0.regexp
            char[] r15 = r15.source
            int r16 = r9 + 1
            char r9 = r15[r9]
            int r9 = toASCIIHexDigit(r9)
            if (r9 >= 0) goto L_0x00a1
            int r8 = r8 + 1
            int r9 = r16 - r8
            goto L_0x00aa
        L_0x00a1:
            int r14 = r14 << 4
            r14 = r14 | r9
            int r8 = r8 + 1
            r9 = r16
            goto L_0x008a
        L_0x00a9:
            r10 = r14
        L_0x00aa:
            char r2 = (char) r10
            goto L_0x005f
        L_0x00ac:
            r2 = 9
            r2 = r9
            r9 = 9
            goto L_0x0134
        L_0x00b3:
            if (r4 == 0) goto L_0x00b9
            addCharacterToCharSet(r1, r12)
            r4 = 0
        L_0x00b9:
            int r2 = r1.length
            int r2 = r2 - r13
        L_0x00bc:
            if (r2 < 0) goto L_0x01d0
            boolean r8 = isREWhiteSpace(r2)
            if (r8 == 0) goto L_0x00c8
            char r8 = (char) r2
            addCharacterToCharSet(r1, r8)
        L_0x00c8:
            int r2 = r2 + -1
            goto L_0x00bc
        L_0x00cb:
            r2 = 13
            r2 = r9
            r9 = 13
            goto L_0x0134
        L_0x00d2:
            if (r4 == 0) goto L_0x00d8
            addCharacterToCharSet(r1, r12)
            r4 = 0
        L_0x00d8:
            r2 = 57
            addCharacterRangeToCharSet(r1, r14, r2)
            goto L_0x01d0
        L_0x00df:
            if (r9 >= r3) goto L_0x00f6
            char r2 = r8[r9]
            boolean r2 = isControlLetter(r2)
            if (r2 == 0) goto L_0x00f6
            org.mozilla.javascript.regexp.RECompiled r2 = r0.regexp
            char[] r2 = r2.source
            int r8 = r9 + 1
            char r2 = r2[r9]
            r2 = r2 & 31
            char r9 = (char) r2
            r2 = r8
            goto L_0x0134
        L_0x00f6:
            int r9 = r9 + -1
            r2 = r9
            r9 = 92
            goto L_0x0134
        L_0x00fc:
            r2 = r9
            r9 = 8
            goto L_0x0134
        L_0x0100:
            int r2 = r2 + -48
            char r10 = r8[r9]
            if (r14 > r10) goto L_0x0126
            r15 = 55
            if (r10 > r15) goto L_0x0126
            int r9 = r9 + 1
            int r2 = r2 * 8
            int r10 = r10 + -48
            int r2 = r2 + r10
            char r8 = r8[r9]
            if (r14 > r8) goto L_0x0126
            if (r8 > r15) goto L_0x0126
            int r9 = r9 + 1
            int r10 = r2 * 8
            int r8 = r8 + -48
            int r8 = r8 + r10
            r10 = 255(0xff, float:3.57E-43)
            if (r8 > r10) goto L_0x0124
            r2 = r8
            goto L_0x0126
        L_0x0124:
            int r9 = r9 + -1
        L_0x0126:
            char r2 = (char) r2
            goto L_0x005f
        L_0x0129:
            r2 = 10
            r2 = r9
            r9 = 10
            goto L_0x0134
        L_0x012f:
            r2 = 12
            r2 = r9
            r9 = 12
        L_0x0134:
            if (r4 == 0) goto L_0x0161
            org.mozilla.javascript.regexp.RECompiled r4 = r0.regexp
            int r4 = r4.flags
            r4 = r4 & r11
            if (r4 == 0) goto L_0x015b
            r4 = r6
        L_0x013e:
            if (r4 > r9) goto L_0x015e
            addCharacterToCharSet(r1, r4)
            char r8 = upcase(r4)
            char r10 = downcase(r4)
            if (r4 == r8) goto L_0x0150
            addCharacterToCharSet(r1, r8)
        L_0x0150:
            if (r4 == r10) goto L_0x0155
            addCharacterToCharSet(r1, r10)
        L_0x0155:
            int r4 = r4 + 1
            char r4 = (char) r4
            if (r4 != 0) goto L_0x013e
            goto L_0x015e
        L_0x015b:
            addCharacterRangeToCharSet(r1, r6, r9)
        L_0x015e:
            r4 = 0
            goto L_0x0026
        L_0x0161:
            org.mozilla.javascript.regexp.RECompiled r8 = r0.regexp
            int r8 = r8.flags
            r8 = r8 & r11
            if (r8 == 0) goto L_0x0177
            char r8 = upcase(r9)
            addCharacterToCharSet(r1, r8)
            char r8 = downcase(r9)
            addCharacterToCharSet(r1, r8)
            goto L_0x017a
        L_0x0177:
            addCharacterToCharSet(r1, r9)
        L_0x017a:
            int r8 = r3 + -1
            if (r2 >= r8) goto L_0x0026
            org.mozilla.javascript.regexp.RECompiled r8 = r0.regexp
            char[] r8 = r8.source
            char r8 = r8[r2]
            if (r8 != r12) goto L_0x0026
            int r2 = r2 + 1
            r6 = r9
            r4 = 1
            goto L_0x0026
        L_0x018c:
            if (r4 == 0) goto L_0x0192
            addCharacterToCharSet(r1, r12)
            r4 = 0
        L_0x0192:
            int r2 = r1.length
            int r2 = r2 - r13
        L_0x0195:
            if (r2 < 0) goto L_0x01d0
            char r8 = (char) r2
            boolean r10 = isWord(r8)
            if (r10 != 0) goto L_0x01a1
            addCharacterToCharSet(r1, r8)
        L_0x01a1:
            int r2 = r2 + -1
            goto L_0x0195
        L_0x01a4:
            if (r4 == 0) goto L_0x01aa
            addCharacterToCharSet(r1, r12)
            r4 = 0
        L_0x01aa:
            int r2 = r1.length
            int r2 = r2 - r13
        L_0x01ad:
            if (r2 < 0) goto L_0x01d0
            boolean r8 = isREWhiteSpace(r2)
            if (r8 != 0) goto L_0x01b9
            char r8 = (char) r2
            addCharacterToCharSet(r1, r8)
        L_0x01b9:
            int r2 = r2 + -1
            goto L_0x01ad
        L_0x01bc:
            if (r4 == 0) goto L_0x01c2
            addCharacterToCharSet(r1, r12)
            r4 = 0
        L_0x01c2:
            r2 = 47
            addCharacterRangeToCharSet(r1, r7, r2)
            int r2 = r1.length
            int r2 = r2 - r13
            char r2 = (char) r2
            r8 = 58
            addCharacterRangeToCharSet(r1, r8, r2)
        L_0x01d0:
            r2 = r9
            goto L_0x0026
        L_0x01d3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.processCharSetImpl(org.mozilla.javascript.regexp.REGlobalData, org.mozilla.javascript.regexp.RECharSet):void");
    }

    private static void pushBackTrackState(REGlobalData rEGlobalData, byte b, int i) {
        REProgState rEProgState = rEGlobalData.stateStackTop;
        rEGlobalData.backTrackStackTop = new REBackTrackData(rEGlobalData, b, i, rEGlobalData.cp, rEProgState.continuationOp, rEProgState.continuationPc);
    }

    private static void pushProgState(REGlobalData rEGlobalData, int i, int i2, int i3, REBackTrackData rEBackTrackData, int i4, int i5) {
        rEGlobalData.stateStackTop = new REProgState(rEGlobalData.stateStackTop, i, i2, i3, rEBackTrackData, i4, i5);
    }

    private static NativeRegExp realThis(Scriptable scriptable, IdFunctionObject idFunctionObject) {
        if (scriptable instanceof NativeRegExp) {
            return (NativeRegExp) scriptable;
        }
        throw IdScriptableObject.incompatibleCallError(idFunctionObject);
    }

    private static boolean reopIsSimple(int i) {
        return i >= 1 && i <= 23;
    }

    private static void reportError(String str, String str2) {
        throw ScriptRuntime.constructError("SyntaxError", ScriptRuntime.getMessage1(str, str2));
    }

    private static void reportWarning(Context context, String str, String str2) {
        if (context.hasFeature(11)) {
            Context.reportWarning(ScriptRuntime.getMessage1(str, str2));
        }
    }

    private static void resolveForwardJump(byte[] bArr, int i, int i2) {
        if (i <= i2) {
            addIndex(bArr, i, i2 - i);
            return;
        }
        throw Kit.codeBug();
    }

    private void setLastIndex(Object obj) {
        if ((this.lastIndexAttr & 1) == 0) {
            this.lastIndex = obj;
            return;
        }
        throw ScriptRuntime.typeError1("msg.modify.readonly", "lastIndex");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01c5, code lost:
        r3.cp = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01c7, code lost:
        return r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01c8, code lost:
        r3.cp = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01cb, code lost:
        return -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00af, code lost:
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00b2, code lost:
        r7 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0176, code lost:
        if (isWord(r4.charAt(r6)) != false) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0197, code lost:
        if (isWord(r4.charAt(r6)) != false) goto L_0x019a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x019a, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x019b, code lost:
        r1 = r1 ^ r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01ab, code lost:
        if (isLineTerm(r4.charAt(r0)) == false) goto L_0x01c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01be, code lost:
        if (isLineTerm(r4.charAt(r0 - 1)) == false) goto L_0x01c0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01c0, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01c1, code lost:
        if (r1 == false) goto L_0x01c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01c3, code lost:
        if (r9 != false) goto L_0x01c7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int simpleMatch(org.mozilla.javascript.regexp.REGlobalData r3, java.lang.String r4, int r5, byte[] r6, int r7, int r8, boolean r9) {
        /*
            int r0 = r3.cp
            r1 = 1
            r2 = 0
            switch(r5) {
                case 1: goto L_0x01c1;
                case 2: goto L_0x01ae;
                case 3: goto L_0x019d;
                case 4: goto L_0x0179;
                case 5: goto L_0x0158;
                case 6: goto L_0x0145;
                case 7: goto L_0x0132;
                case 8: goto L_0x011f;
                case 9: goto L_0x010c;
                case 10: goto L_0x00f9;
                case 11: goto L_0x00e6;
                case 12: goto L_0x00d3;
                case 13: goto L_0x00c7;
                case 14: goto L_0x00b5;
                case 15: goto L_0x009b;
                case 16: goto L_0x0089;
                case 17: goto L_0x0067;
                case 18: goto L_0x004f;
                case 19: goto L_0x002d;
                case 20: goto L_0x0007;
                case 21: goto L_0x0007;
                case 22: goto L_0x000c;
                case 23: goto L_0x000c;
                default: goto L_0x0007;
            }
        L_0x0007:
            java.lang.RuntimeException r3 = org.mozilla.javascript.Kit.codeBug()
            throw r3
        L_0x000c:
            int r5 = getIndex(r6, r7)
            int r7 = r7 + 2
            int r6 = r3.cp
            if (r6 == r8) goto L_0x01c0
            org.mozilla.javascript.regexp.RECompiled r8 = r3.regexp
            org.mozilla.javascript.regexp.RECharSet[] r8 = r8.classList
            r5 = r8[r5]
            char r4 = r4.charAt(r6)
            boolean r4 = classMatcher(r3, r5, r4)
            if (r4 == 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x002d:
            int r5 = getIndex(r6, r7)
            char r5 = (char) r5
            int r7 = r7 + 2
            int r6 = r3.cp
            if (r6 == r8) goto L_0x01c0
            char r4 = r4.charAt(r6)
            if (r5 == r4) goto L_0x0048
            char r5 = upcase(r5)
            char r4 = upcase(r4)
            if (r5 != r4) goto L_0x01c0
        L_0x0048:
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x004f:
            int r5 = getIndex(r6, r7)
            char r5 = (char) r5
            int r7 = r7 + 2
            int r6 = r3.cp
            if (r6 == r8) goto L_0x01c0
            char r4 = r4.charAt(r6)
            if (r4 != r5) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x0067:
            int r5 = r7 + 1
            byte r6 = r6[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            char r6 = (char) r6
            if (r0 == r8) goto L_0x00b2
            char r4 = r4.charAt(r0)
            if (r6 == r4) goto L_0x0083
            char r6 = upcase(r6)
            char r4 = upcase(r4)
            if (r6 != r4) goto L_0x0081
            goto L_0x0083
        L_0x0081:
            r1 = 0
            goto L_0x00af
        L_0x0083:
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x00af
        L_0x0089:
            int r5 = getIndex(r6, r7)
            int r7 = r7 + 2
            int r6 = getIndex(r6, r7)
            int r7 = r7 + 2
            boolean r1 = flatNIMatcher(r3, r5, r6, r4, r8)
            goto L_0x01c1
        L_0x009b:
            int r5 = r7 + 1
            byte r6 = r6[r7]
            r6 = r6 & 255(0xff, float:3.57E-43)
            char r6 = (char) r6
            if (r0 == r8) goto L_0x00b2
            char r4 = r4.charAt(r0)
            if (r4 != r6) goto L_0x00b2
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
        L_0x00af:
            r7 = r5
            goto L_0x01c1
        L_0x00b2:
            r7 = r5
            goto L_0x01c0
        L_0x00b5:
            int r5 = getIndex(r6, r7)
            int r7 = r7 + 2
            int r6 = getIndex(r6, r7)
            int r7 = r7 + 2
            boolean r1 = flatNMatcher(r3, r5, r6, r4, r8)
            goto L_0x01c1
        L_0x00c7:
            int r5 = getIndex(r6, r7)
            int r7 = r7 + 2
            boolean r1 = backrefMatcher(r3, r5, r4, r8)
            goto L_0x01c1
        L_0x00d3:
            if (r0 == r8) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isREWhiteSpace(r4)
            if (r4 != 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x00e6:
            if (r0 == r8) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isREWhiteSpace(r4)
            if (r4 == 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x00f9:
            if (r0 == r8) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isWord(r4)
            if (r4 != 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x010c:
            if (r0 == r8) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isWord(r4)
            if (r4 == 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x011f:
            if (r0 == r8) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isDigit(r4)
            if (r4 != 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x0132:
            if (r0 == r8) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isDigit(r4)
            if (r4 == 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x0145:
            if (r0 == r8) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isLineTerm(r4)
            if (r4 != 0) goto L_0x01c0
            int r4 = r3.cp
            int r4 = r4 + r1
            r3.cp = r4
            goto L_0x01c1
        L_0x0158:
            if (r0 == 0) goto L_0x0169
            int r5 = r0 + -1
            char r5 = r4.charAt(r5)
            boolean r5 = isWord(r5)
            if (r5 != 0) goto L_0x0167
            goto L_0x0169
        L_0x0167:
            r5 = 0
            goto L_0x016a
        L_0x0169:
            r5 = 1
        L_0x016a:
            int r6 = r3.cp
            if (r6 >= r8) goto L_0x019a
            char r4 = r4.charAt(r6)
            boolean r4 = isWord(r4)
            if (r4 == 0) goto L_0x019a
            goto L_0x019b
        L_0x0179:
            if (r0 == 0) goto L_0x018a
            int r5 = r0 + -1
            char r5 = r4.charAt(r5)
            boolean r5 = isWord(r5)
            if (r5 != 0) goto L_0x0188
            goto L_0x018a
        L_0x0188:
            r5 = 0
            goto L_0x018b
        L_0x018a:
            r5 = 1
        L_0x018b:
            int r6 = r3.cp
            if (r6 >= r8) goto L_0x019b
            char r4 = r4.charAt(r6)
            boolean r4 = isWord(r4)
            if (r4 != 0) goto L_0x019a
            goto L_0x019b
        L_0x019a:
            r1 = 0
        L_0x019b:
            r1 = r1 ^ r5
            goto L_0x01c1
        L_0x019d:
            if (r0 == r8) goto L_0x01c1
            boolean r5 = r3.multiline
            if (r5 == 0) goto L_0x01c0
            char r4 = r4.charAt(r0)
            boolean r4 = isLineTerm(r4)
            if (r4 != 0) goto L_0x01c1
            goto L_0x01c0
        L_0x01ae:
            if (r0 == 0) goto L_0x01c1
            boolean r5 = r3.multiline
            if (r5 == 0) goto L_0x01c0
            int r5 = r0 + -1
            char r4 = r4.charAt(r5)
            boolean r4 = isLineTerm(r4)
            if (r4 != 0) goto L_0x01c1
        L_0x01c0:
            r1 = 0
        L_0x01c1:
            if (r1 == 0) goto L_0x01c8
            if (r9 != 0) goto L_0x01c7
            r3.cp = r0
        L_0x01c7:
            return r7
        L_0x01c8:
            r3.cp = r0
            r3 = -1
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.simpleMatch(org.mozilla.javascript.regexp.REGlobalData, java.lang.String, int, byte[], int, int, boolean):int");
    }

    private static int toASCIIHexDigit(int i) {
        if (i < 48) {
            return -1;
        }
        if (i <= 57) {
            return i - 48;
        }
        int i2 = i | 32;
        if (97 > i2 || i2 > 102) {
            return -1;
        }
        return (i2 - 97) + 10;
    }

    private static char upcase(char c) {
        if (c < 128) {
            return ('a' > c || c > 'z') ? c : (char) (c - ' ');
        }
        char upperCase = Character.toUpperCase(c);
        return upperCase < 128 ? c : upperCase;
    }

    public Object call(Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (context.getLanguageVersion() < 200) {
            return execSub(context, scriptable, objArr, 1);
        }
        throw ScriptRuntime.notFunctionError(scriptable2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.mozilla.javascript.Scriptable compile(org.mozilla.javascript.Context r4, org.mozilla.javascript.Scriptable r5, java.lang.Object[] r6) {
        /*
            r3 = this;
            int r5 = r6.length
            r0 = 0
            r1 = 1
            if (r5 <= 0) goto L_0x0028
            r5 = r6[r0]
            boolean r2 = r5 instanceof org.mozilla.javascript.regexp.NativeRegExp
            if (r2 == 0) goto L_0x0028
            int r4 = r6.length
            if (r4 <= r1) goto L_0x001c
            r4 = r6[r1]
            java.lang.Object r6 = org.mozilla.javascript.Undefined.instance
            if (r4 != r6) goto L_0x0015
            goto L_0x001c
        L_0x0015:
            java.lang.String r4 = "msg.bad.regexp.compile"
            org.mozilla.javascript.EcmaError r4 = org.mozilla.javascript.ScriptRuntime.typeError0(r4)
            throw r4
        L_0x001c:
            org.mozilla.javascript.regexp.NativeRegExp r5 = (org.mozilla.javascript.regexp.NativeRegExp) r5
            org.mozilla.javascript.regexp.RECompiled r4 = r5.re
            r3.re = r4
            java.lang.Object r4 = r5.lastIndex
            r3.setLastIndex(r4)
            return r3
        L_0x0028:
            int r5 = r6.length
            if (r5 == 0) goto L_0x0037
            r5 = r6[r0]
            boolean r2 = r5 instanceof org.mozilla.javascript.Undefined
            if (r2 == 0) goto L_0x0032
            goto L_0x0037
        L_0x0032:
            java.lang.String r5 = escapeRegExp(r5)
            goto L_0x0039
        L_0x0037:
            java.lang.String r5 = ""
        L_0x0039:
            int r2 = r6.length
            if (r2 <= r1) goto L_0x0047
            r6 = r6[r1]
            java.lang.Object r1 = org.mozilla.javascript.Undefined.instance
            if (r6 == r1) goto L_0x0047
            java.lang.String r6 = org.mozilla.javascript.ScriptRuntime.toString((java.lang.Object) r6)
            goto L_0x0048
        L_0x0047:
            r6 = 0
        L_0x0048:
            org.mozilla.javascript.regexp.RECompiled r4 = compileRE(r4, r5, r6, r0)
            r3.re = r4
            java.lang.Double r4 = org.mozilla.javascript.ScriptRuntime.zeroObj
            r3.setLastIndex(r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.compile(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[]):org.mozilla.javascript.Scriptable");
    }

    public Scriptable construct(Context context, Scriptable scriptable, Object[] objArr) {
        if (context.getLanguageVersion() < 200) {
            return (Scriptable) execSub(context, scriptable, objArr, 1);
        }
        throw ScriptRuntime.notFunctionError(this);
    }

    public Object execIdCall(IdFunctionObject idFunctionObject, Context context, Scriptable scriptable, Scriptable scriptable2, Object[] objArr) {
        if (!idFunctionObject.hasTag(REGEXP_TAG)) {
            return super.execIdCall(idFunctionObject, context, scriptable, scriptable2, objArr);
        }
        int methodId = idFunctionObject.methodId();
        switch (methodId) {
            case 1:
                return realThis(scriptable2, idFunctionObject).compile(context, scriptable, objArr);
            case 2:
            case 3:
                return realThis(scriptable2, idFunctionObject).toString();
            case 4:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 1);
            case 5:
                Object execSub = realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 0);
                Boolean bool = Boolean.TRUE;
                if (bool.equals(execSub)) {
                    return bool;
                }
                return Boolean.FALSE;
            case 6:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 2);
            case 7:
                return realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 1);
            case 8:
                Scriptable scriptable3 = (Scriptable) realThis(scriptable2, idFunctionObject).execSub(context, scriptable, objArr, 1);
                return scriptable3.get("index", scriptable3);
            default:
                throw new IllegalArgumentException(String.valueOf(methodId));
        }
    }

    /* JADX WARNING: type inference failed for: r6v2, types: [java.lang.Boolean] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object executeRegExp(org.mozilla.javascript.Context r17, org.mozilla.javascript.Scriptable r18, org.mozilla.javascript.regexp.RegExpImpl r19, java.lang.String r20, int[] r21, int r22) {
        /*
            r16 = this;
            r0 = r16
            r1 = r19
            r8 = r20
            r9 = r22
            org.mozilla.javascript.regexp.REGlobalData r10 = new org.mozilla.javascript.regexp.REGlobalData
            r10.<init>()
            r11 = 0
            r2 = r21[r11]
            int r12 = r20.length()
            if (r2 <= r12) goto L_0x0018
            r13 = r12
            goto L_0x0019
        L_0x0018:
            r13 = r2
        L_0x0019:
            org.mozilla.javascript.regexp.RECompiled r3 = r0.re
            boolean r7 = r1.multiline
            r2 = r10
            r4 = r20
            r5 = r13
            r6 = r12
            boolean r2 = matchRegExp(r2, r3, r4, r5, r6, r7)
            r3 = 0
            if (r2 != 0) goto L_0x0030
            r1 = 2
            if (r9 == r1) goto L_0x002d
            return r3
        L_0x002d:
            java.lang.Object r1 = org.mozilla.javascript.Undefined.instance
            return r1
        L_0x0030:
            int r2 = r10.cp
            r21[r11] = r2
            int r4 = r10.skipped
            int r4 = r4 + r13
            int r4 = r2 - r4
            int r5 = r2 - r4
            if (r9 != 0) goto L_0x0044
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            r14 = r3
            r7 = r6
            r6 = r17
            goto L_0x0056
        L_0x0044:
            r6 = r17
            r7 = r18
            org.mozilla.javascript.Scriptable r7 = r6.newArray((org.mozilla.javascript.Scriptable) r7, (int) r11)
            int r14 = r5 + r4
            java.lang.String r14 = r8.substring(r5, r14)
            r7.put((int) r11, (org.mozilla.javascript.Scriptable) r7, (java.lang.Object) r14)
            r14 = r7
        L_0x0056:
            org.mozilla.javascript.regexp.RECompiled r15 = r0.re
            int r15 = r15.parenCount
            if (r15 != 0) goto L_0x0066
            r1.parens = r3
            org.mozilla.javascript.regexp.SubString r3 = new org.mozilla.javascript.regexp.SubString
            r3.<init>()
            r1.lastParen = r3
            goto L_0x00a1
        L_0x0066:
            org.mozilla.javascript.regexp.SubString[] r15 = new org.mozilla.javascript.regexp.SubString[r15]
            r1.parens = r15
            r15 = 0
        L_0x006b:
            org.mozilla.javascript.regexp.RECompiled r11 = r0.re
            int r11 = r11.parenCount
            if (r15 >= r11) goto L_0x009f
            int r11 = r10.parensIndex(r15)
            r0 = -1
            if (r11 == r0) goto L_0x0091
            int r0 = r10.parensLength(r15)
            org.mozilla.javascript.regexp.SubString r3 = new org.mozilla.javascript.regexp.SubString
            r3.<init>(r8, r11, r0)
            org.mozilla.javascript.regexp.SubString[] r0 = r1.parens
            r0[r15] = r3
            if (r9 == 0) goto L_0x009a
            int r0 = r15 + 1
            java.lang.String r11 = r3.toString()
            r14.put((int) r0, (org.mozilla.javascript.Scriptable) r14, (java.lang.Object) r11)
            goto L_0x009a
        L_0x0091:
            if (r9 == 0) goto L_0x009a
            int r0 = r15 + 1
            java.lang.Object r11 = org.mozilla.javascript.Undefined.instance
            r14.put((int) r0, (org.mozilla.javascript.Scriptable) r14, (java.lang.Object) r11)
        L_0x009a:
            int r15 = r15 + 1
            r0 = r16
            goto L_0x006b
        L_0x009f:
            r1.lastParen = r3
        L_0x00a1:
            if (r9 == 0) goto L_0x00b4
            int r0 = r10.skipped
            int r0 = r0 + r13
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            java.lang.String r3 = "index"
            r14.put((java.lang.String) r3, (org.mozilla.javascript.Scriptable) r14, (java.lang.Object) r0)
            java.lang.String r0 = "input"
            r14.put((java.lang.String) r0, (org.mozilla.javascript.Scriptable) r14, (java.lang.Object) r8)
        L_0x00b4:
            org.mozilla.javascript.regexp.SubString r0 = r1.lastMatch
            if (r0 != 0) goto L_0x00cd
            org.mozilla.javascript.regexp.SubString r0 = new org.mozilla.javascript.regexp.SubString
            r0.<init>()
            r1.lastMatch = r0
            org.mozilla.javascript.regexp.SubString r0 = new org.mozilla.javascript.regexp.SubString
            r0.<init>()
            r1.leftContext = r0
            org.mozilla.javascript.regexp.SubString r0 = new org.mozilla.javascript.regexp.SubString
            r0.<init>()
            r1.rightContext = r0
        L_0x00cd:
            org.mozilla.javascript.regexp.SubString r0 = r1.lastMatch
            r0.str = r8
            r0.index = r5
            r0.length = r4
            org.mozilla.javascript.regexp.SubString r0 = r1.leftContext
            r0.str = r8
            int r0 = r17.getLanguageVersion()
            r3 = 120(0x78, float:1.68E-43)
            if (r0 != r3) goto L_0x00ea
            org.mozilla.javascript.regexp.SubString r0 = r1.leftContext
            r0.index = r13
            int r3 = r10.skipped
            r0.length = r3
            goto L_0x00f4
        L_0x00ea:
            org.mozilla.javascript.regexp.SubString r0 = r1.leftContext
            r3 = 0
            r0.index = r3
            int r3 = r10.skipped
            int r13 = r13 + r3
            r0.length = r13
        L_0x00f4:
            org.mozilla.javascript.regexp.SubString r0 = r1.rightContext
            r0.str = r8
            r0.index = r2
            int r12 = r12 - r2
            r0.length = r12
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.executeRegExp(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.regexp.RegExpImpl, java.lang.String, int[], int):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0056  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findInstanceIdInfo(java.lang.String r9) {
        /*
            r8 = this;
            int r0 = r9.length()
            r1 = 6
            r2 = 5
            r3 = 2
            r4 = 1
            r5 = 4
            r6 = 3
            r7 = 0
            if (r0 != r1) goto L_0x0021
            char r0 = r9.charAt(r7)
            r1 = 103(0x67, float:1.44E-43)
            if (r0 != r1) goto L_0x0019
            java.lang.String r0 = "global"
            r1 = 3
            goto L_0x0043
        L_0x0019:
            r1 = 115(0x73, float:1.61E-43)
            if (r0 != r1) goto L_0x0041
            java.lang.String r0 = "source"
            r1 = 2
            goto L_0x0043
        L_0x0021:
            r1 = 9
            if (r0 != r1) goto L_0x0039
            char r0 = r9.charAt(r7)
            r1 = 108(0x6c, float:1.51E-43)
            if (r0 != r1) goto L_0x0031
            java.lang.String r0 = "lastIndex"
            r1 = 1
            goto L_0x0043
        L_0x0031:
            r1 = 109(0x6d, float:1.53E-43)
            if (r0 != r1) goto L_0x0041
            java.lang.String r0 = "multiline"
            r1 = 5
            goto L_0x0043
        L_0x0039:
            r1 = 10
            if (r0 != r1) goto L_0x0041
            java.lang.String r0 = "ignoreCase"
            r1 = 4
            goto L_0x0043
        L_0x0041:
            r0 = 0
            r1 = 0
        L_0x0043:
            if (r0 == 0) goto L_0x004e
            if (r0 == r9) goto L_0x004e
            boolean r0 = r0.equals(r9)
            if (r0 != 0) goto L_0x004e
            goto L_0x004f
        L_0x004e:
            r7 = r1
        L_0x004f:
            if (r7 != 0) goto L_0x0056
            int r9 = super.findInstanceIdInfo((java.lang.String) r9)
            return r9
        L_0x0056:
            if (r7 == r4) goto L_0x0069
            if (r7 == r3) goto L_0x0067
            if (r7 == r6) goto L_0x0067
            if (r7 == r5) goto L_0x0067
            if (r7 != r2) goto L_0x0061
            goto L_0x0067
        L_0x0061:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            r9.<init>()
            throw r9
        L_0x0067:
            r9 = 7
            goto L_0x006b
        L_0x0069:
            int r9 = r8.lastIndexAttr
        L_0x006b:
            int r9 = org.mozilla.javascript.IdScriptableObject.instanceIdInfo(r9, r7)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.findInstanceIdInfo(java.lang.String):int");
    }

    public int findPrototypeId(Symbol symbol) {
        if (SymbolKey.MATCH.equals(symbol)) {
            return 7;
        }
        return SymbolKey.SEARCH.equals(symbol) ? 8 : 0;
    }

    public String getClassName() {
        return "RegExp";
    }

    public int getFlags() {
        return this.re.flags;
    }

    public String getInstanceIdName(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? super.getInstanceIdName(i) : "multiline" : "ignoreCase" : "global" : "source" : "lastIndex";
    }

    public Object getInstanceIdValue(int i) {
        boolean z = true;
        if (i == 1) {
            return this.lastIndex;
        }
        if (i == 2) {
            return new String(this.re.source);
        }
        if (i == 3) {
            if ((this.re.flags & 1) == 0) {
                z = false;
            }
            return ScriptRuntime.wrapBoolean(z);
        } else if (i == 4) {
            if ((this.re.flags & 2) == 0) {
                z = false;
            }
            return ScriptRuntime.wrapBoolean(z);
        } else if (i != 5) {
            return super.getInstanceIdValue(i);
        } else {
            if ((this.re.flags & 4) == 0) {
                z = false;
            }
            return ScriptRuntime.wrapBoolean(z);
        }
    }

    public int getMaxInstanceId() {
        return 5;
    }

    public String getTypeOf() {
        return "object";
    }

    public void initPrototypeId(int i) {
        String str;
        String str2;
        if (i == 7) {
            initPrototypeMethod(REGEXP_TAG, i, (Symbol) SymbolKey.MATCH, "[Symbol.match]", 1);
        } else if (i == 8) {
            initPrototypeMethod(REGEXP_TAG, i, (Symbol) SymbolKey.SEARCH, "[Symbol.search]", 1);
        } else {
            int i2 = 1;
            switch (i) {
                case 1:
                    str = "compile";
                    i2 = 2;
                    break;
                case 2:
                    str2 = "toString";
                    break;
                case 3:
                    str2 = "toSource";
                    break;
                case 4:
                    str = "exec";
                    break;
                case 5:
                    str = "test";
                    break;
                case 6:
                    str = "prefix";
                    break;
                default:
                    throw new IllegalArgumentException(String.valueOf(i));
            }
            str = str2;
            i2 = 0;
            initPrototypeMethod(REGEXP_TAG, i, str, i2);
        }
    }

    public void setInstanceIdAttributes(int i, int i2) {
        if (i != 1) {
            super.setInstanceIdAttributes(i, i2);
        } else {
            this.lastIndexAttr = i2;
        }
    }

    public void setInstanceIdValue(int i, Object obj) {
        if (i == 1) {
            setLastIndex(obj);
        } else if (i != 2 && i != 3 && i != 4 && i != 5) {
            super.setInstanceIdValue(i, obj);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(MqttTopic.TOPIC_LEVEL_SEPARATOR);
        char[] cArr = this.re.source;
        if (cArr.length != 0) {
            sb.append(cArr);
        } else {
            sb.append("(?:)");
        }
        sb.append('/');
        if ((this.re.flags & 1) != 0) {
            sb.append('g');
        }
        if ((this.re.flags & 2) != 0) {
            sb.append('i');
        }
        if ((this.re.flags & 4) != 0) {
            sb.append('m');
        }
        return sb.toString();
    }

    private static void pushBackTrackState(REGlobalData rEGlobalData, byte b, int i, int i2, int i3, int i4) {
        rEGlobalData.backTrackStackTop = new REBackTrackData(rEGlobalData, b, i, i2, i3, i4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0043 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int findPrototypeId(java.lang.String r6) {
        /*
            r5 = this;
            int r0 = r6.length()
            r1 = 116(0x74, float:1.63E-43)
            r2 = 4
            r3 = 0
            if (r0 == r2) goto L_0x002e
            r2 = 6
            if (r0 == r2) goto L_0x002b
            r2 = 7
            if (r0 == r2) goto L_0x0027
            r2 = 8
            if (r0 == r2) goto L_0x0015
            goto L_0x003f
        L_0x0015:
            r2 = 3
            char r0 = r6.charAt(r2)
            r4 = 111(0x6f, float:1.56E-43)
            if (r0 != r4) goto L_0x0021
            java.lang.String r0 = "toSource"
            goto L_0x0041
        L_0x0021:
            if (r0 != r1) goto L_0x003f
            java.lang.String r0 = "toString"
            r2 = 2
            goto L_0x0041
        L_0x0027:
            java.lang.String r0 = "compile"
            r2 = 1
            goto L_0x0041
        L_0x002b:
            java.lang.String r0 = "prefix"
            goto L_0x0041
        L_0x002e:
            char r0 = r6.charAt(r3)
            r4 = 101(0x65, float:1.42E-43)
            if (r0 != r4) goto L_0x0039
            java.lang.String r0 = "exec"
            goto L_0x0041
        L_0x0039:
            if (r0 != r1) goto L_0x003f
            java.lang.String r0 = "test"
            r2 = 5
            goto L_0x0041
        L_0x003f:
            r0 = 0
            r2 = 0
        L_0x0041:
            if (r0 == 0) goto L_0x004c
            if (r0 == r6) goto L_0x004c
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x004c
            goto L_0x004d
        L_0x004c:
            r3 = r2
        L_0x004d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.regexp.NativeRegExp.findPrototypeId(java.lang.String):int");
    }

    public NativeRegExp() {
        this.lastIndex = ScriptRuntime.zeroObj;
        this.lastIndexAttr = 6;
    }
}
