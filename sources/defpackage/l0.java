package defpackage;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.view.ViewCompat;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ObjArray;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.Token;
import org.mozilla.javascript.UintMap;

/* renamed from: l0  reason: default package */
public final class l0 {
    public static final int ac;
    public static final int ad;
    public static final boolean ae;
    public int[] a = null;
    public ObjArray aa;
    public char[] ab = new char[64];
    public int b = 0;
    public UintMap c = null;
    public final String d;
    public e2[] e;
    public int f;
    public int[] g;
    public int h;
    public byte[] i = new byte[256];
    public int j;
    public final s0 k;
    public k0 l;
    public short m;
    public short n;
    public short o;
    public final ObjArray p = new ObjArray();
    public final ObjArray q = new ObjArray();
    public final ObjArray r = new ObjArray();
    public final short s;
    public final short t;
    public final short u;
    public final short v;
    public int[] w;
    public int x;
    public long[] y;
    public int z;

    /* renamed from: l0$a */
    public static class a extends RuntimeException {
        private static final long serialVersionUID = 1263998431033790599L;

        public a(String str) {
            super(str);
        }
    }

    /* renamed from: l0$b */
    public final class b {
        public int[] a = null;
        public int b = 0;
        public int[] c = null;
        public int d = 0;
        public jc[] e = null;
        public int f = 0;
        public jc[] g = null;
        public byte[] h = null;
        public int i = 0;
        public boolean j = false;

        public b() {
        }

        public final void a(jc jcVar) {
            if (!jcVar.g) {
                jcVar.g = true;
                jcVar.f = true;
                int i2 = this.f;
                jc[] jcVarArr = this.e;
                if (i2 == jcVarArr.length) {
                    jc[] jcVarArr2 = new jc[(i2 * 2)];
                    System.arraycopy(jcVarArr, 0, jcVarArr2, 0, i2);
                    this.e = jcVarArr2;
                }
                jc[] jcVarArr3 = this.e;
                int i3 = this.f;
                this.f = i3 + 1;
                jcVarArr3[i3] = jcVar;
            }
        }

        public final void b(int i2) {
            int i3;
            if (i2 < this.b) {
                i3 = this.a[i2];
            } else {
                i3 = 0;
            }
            int i4 = i3 & 255;
            if (i4 == 7 || i4 == 6 || i4 == 8 || i4 == 5) {
                j(i3);
                return;
            }
            throw new IllegalStateException("bad local variable type: " + i3 + " at index: " + i2);
        }

        public final void c(int i2, int i3) {
            h();
            int i4 = this.b;
            if (i2 >= i4) {
                int i5 = i2 + 1;
                int[] iArr = new int[i5];
                System.arraycopy(this.a, 0, iArr, 0, i4);
                this.a = iArr;
                this.b = i5;
            }
            this.a[i2] = i3;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:118:0x035b, code lost:
            h();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x0363, code lost:
            h();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:122:0x036b, code lost:
            h();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:124:0x0373, code lost:
            h();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:150:0x03e5, code lost:
            j(3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:151:0x03ea, code lost:
            j(2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:152:0x03ef, code lost:
            j(4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:153:0x03f4, code lost:
            j(1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:155:0x03fc, code lost:
            r10 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:156:0x03fd, code lost:
            if (r10 != 0) goto L_0x042f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:157:0x03ff, code lost:
            r2 = r0.j;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:158:0x0403, code lost:
            if (r5 == 254) goto L_0x042d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:160:0x0407, code lost:
            if (r5 == 255) goto L_0x042d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:161:0x0409, code lost:
            switch(r5) {
                case 0: goto L_0x042d;
                case 1: goto L_0x042d;
                case 2: goto L_0x042d;
                case 3: goto L_0x042d;
                case 4: goto L_0x042d;
                case 5: goto L_0x042d;
                case 6: goto L_0x042d;
                case 7: goto L_0x042d;
                case 8: goto L_0x042d;
                case 9: goto L_0x042d;
                case 10: goto L_0x042d;
                case 11: goto L_0x042d;
                case 12: goto L_0x042d;
                case 13: goto L_0x042d;
                case 14: goto L_0x042d;
                case 15: goto L_0x042d;
                case 16: goto L_0x0424;
                case 17: goto L_0x042a;
                case 18: goto L_0x0424;
                case 19: goto L_0x042a;
                case 20: goto L_0x042a;
                case 21: goto L_0x041e;
                case 22: goto L_0x041e;
                case 23: goto L_0x041e;
                case 24: goto L_0x041e;
                case 25: goto L_0x041e;
                case 26: goto L_0x042d;
                case 27: goto L_0x042d;
                case 28: goto L_0x042d;
                case 29: goto L_0x042d;
                case 30: goto L_0x042d;
                case 31: goto L_0x042d;
                case 32: goto L_0x042d;
                case 33: goto L_0x042d;
                case 34: goto L_0x042d;
                case 35: goto L_0x042d;
                case 36: goto L_0x042d;
                case 37: goto L_0x042d;
                case 38: goto L_0x042d;
                case 39: goto L_0x042d;
                case 40: goto L_0x042d;
                case 41: goto L_0x042d;
                case 42: goto L_0x042d;
                case 43: goto L_0x042d;
                case 44: goto L_0x042d;
                case 45: goto L_0x042d;
                case 46: goto L_0x042d;
                case 47: goto L_0x042d;
                case 48: goto L_0x042d;
                case 49: goto L_0x042d;
                case 50: goto L_0x042d;
                case 51: goto L_0x042d;
                case 52: goto L_0x042d;
                case 53: goto L_0x042d;
                case 54: goto L_0x041e;
                case 55: goto L_0x041e;
                case 56: goto L_0x041e;
                case 57: goto L_0x041e;
                case 58: goto L_0x041e;
                case 59: goto L_0x042d;
                case 60: goto L_0x042d;
                case 61: goto L_0x042d;
                case 62: goto L_0x042d;
                case 63: goto L_0x042d;
                case 64: goto L_0x042d;
                case 65: goto L_0x042d;
                case 66: goto L_0x042d;
                case 67: goto L_0x042d;
                case 68: goto L_0x042d;
                case 69: goto L_0x042d;
                case 70: goto L_0x042d;
                case 71: goto L_0x042d;
                case 72: goto L_0x042d;
                case 73: goto L_0x042d;
                case 74: goto L_0x042d;
                case 75: goto L_0x042d;
                case 76: goto L_0x042d;
                case 77: goto L_0x042d;
                case 78: goto L_0x042d;
                case 79: goto L_0x042d;
                case 80: goto L_0x042d;
                case 81: goto L_0x042d;
                case 82: goto L_0x042d;
                case 83: goto L_0x042d;
                case 84: goto L_0x042d;
                case 85: goto L_0x042d;
                case 86: goto L_0x042d;
                case 87: goto L_0x042d;
                case 88: goto L_0x042d;
                case 89: goto L_0x042d;
                case 90: goto L_0x042d;
                case 91: goto L_0x042d;
                case 92: goto L_0x042d;
                case 93: goto L_0x042d;
                case 94: goto L_0x042d;
                case 95: goto L_0x042d;
                case 96: goto L_0x042d;
                case 97: goto L_0x042d;
                case 98: goto L_0x042d;
                case 99: goto L_0x042d;
                case 100: goto L_0x042d;
                case 101: goto L_0x042d;
                case 102: goto L_0x042d;
                case 103: goto L_0x042d;
                case 104: goto L_0x042d;
                case 105: goto L_0x042d;
                case 106: goto L_0x042d;
                case 107: goto L_0x042d;
                case 108: goto L_0x042d;
                case 109: goto L_0x042d;
                case 110: goto L_0x042d;
                case 111: goto L_0x042d;
                case 112: goto L_0x042d;
                case 113: goto L_0x042d;
                case 114: goto L_0x042d;
                case 115: goto L_0x042d;
                case 116: goto L_0x042d;
                case 117: goto L_0x042d;
                case 118: goto L_0x042d;
                case 119: goto L_0x042d;
                case 120: goto L_0x042d;
                case 121: goto L_0x042d;
                case 122: goto L_0x042d;
                case 123: goto L_0x042d;
                case 124: goto L_0x042d;
                case org.mozilla.javascript.Token.CATCH :int: goto L_0x042d;
                case org.mozilla.javascript.Token.FINALLY :int: goto L_0x042d;
                case org.mozilla.javascript.Token.VOID :int: goto L_0x042d;
                case 128: goto L_0x042d;
                case org.mozilla.javascript.Token.EMPTY :int: goto L_0x042d;
                case 130: goto L_0x042d;
                case org.mozilla.javascript.Token.LABEL :int: goto L_0x042d;
                case org.mozilla.javascript.Token.TARGET :int: goto L_0x041b;
                case org.mozilla.javascript.Token.LOOP :int: goto L_0x042d;
                case org.mozilla.javascript.Token.EXPR_VOID :int: goto L_0x042d;
                case org.mozilla.javascript.Token.EXPR_RESULT :int: goto L_0x042d;
                case org.mozilla.javascript.Token.JSR :int: goto L_0x042d;
                case org.mozilla.javascript.Token.SCRIPT :int: goto L_0x042d;
                case org.mozilla.javascript.Token.TYPEOFNAME :int: goto L_0x042d;
                case org.mozilla.javascript.Token.USE_STACK :int: goto L_0x042d;
                case 140: goto L_0x042d;
                case org.mozilla.javascript.Token.SETELEM_OP :int: goto L_0x042d;
                case org.mozilla.javascript.Token.LOCAL_BLOCK :int: goto L_0x042d;
                case org.mozilla.javascript.Token.SET_REF_OP :int: goto L_0x042d;
                case org.mozilla.javascript.Token.DOTDOT :int: goto L_0x042d;
                case org.mozilla.javascript.Token.COLONCOLON :int: goto L_0x042d;
                case org.mozilla.javascript.Token.XML :int: goto L_0x042d;
                case org.mozilla.javascript.Token.DOTQUERY :int: goto L_0x042d;
                case org.mozilla.javascript.Token.XMLATTR :int: goto L_0x042d;
                case org.mozilla.javascript.Token.XMLEND :int: goto L_0x042d;
                case 150: goto L_0x042d;
                case org.mozilla.javascript.Token.TO_DOUBLE :int: goto L_0x042d;
                case org.mozilla.javascript.Token.GET :int: goto L_0x042d;
                case org.mozilla.javascript.Token.SET :int: goto L_0x042a;
                case org.mozilla.javascript.Token.LET :int: goto L_0x042a;
                case org.mozilla.javascript.Token.CONST :int: goto L_0x042a;
                case org.mozilla.javascript.Token.SETCONST :int: goto L_0x042a;
                case org.mozilla.javascript.Token.SETCONSTVAR :int: goto L_0x042a;
                case org.mozilla.javascript.Token.ARRAYCOMP :int: goto L_0x042a;
                case org.mozilla.javascript.Token.LETEXPR :int: goto L_0x042a;
                case 160: goto L_0x042a;
                case org.mozilla.javascript.Token.DEBUGGER :int: goto L_0x042a;
                case org.mozilla.javascript.Token.COMMENT :int: goto L_0x042a;
                case org.mozilla.javascript.Token.GENEXPR :int: goto L_0x042a;
                case org.mozilla.javascript.Token.METHOD :int: goto L_0x042a;
                case org.mozilla.javascript.Token.ARROW :int: goto L_0x042a;
                case org.mozilla.javascript.Token.YIELD_STAR :int: goto L_0x042a;
                case org.mozilla.javascript.Token.LAST_TOKEN :int: goto L_0x042a;
                case 168: goto L_0x042a;
                case 169: goto L_0x041e;
                default: goto L_0x040c;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:162:0x040c, code lost:
            switch(r5) {
                case 172: goto L_0x042d;
                case 173: goto L_0x042d;
                case 174: goto L_0x042d;
                case 175: goto L_0x042d;
                case 176: goto L_0x042d;
                case 177: goto L_0x042d;
                case 178: goto L_0x042a;
                case 179: goto L_0x042a;
                case org.mozilla.javascript.Context.VERSION_1_8 :int: goto L_0x042a;
                case 181: goto L_0x042a;
                case 182: goto L_0x042a;
                case 183: goto L_0x042a;
                case 184: goto L_0x042a;
                case 185: goto L_0x0427;
                case 186: goto L_0x0427;
                case 187: goto L_0x042a;
                case 188: goto L_0x0424;
                case 189: goto L_0x042a;
                case 190: goto L_0x042d;
                case 191: goto L_0x042d;
                case 192: goto L_0x042a;
                case 193: goto L_0x042a;
                case 194: goto L_0x042d;
                case 195: goto L_0x042d;
                case 196: goto L_0x042d;
                case 197: goto L_0x0421;
                case 198: goto L_0x042a;
                case 199: goto L_0x042a;
                case 200: goto L_0x0427;
                case 201: goto L_0x0427;
                case 202: goto L_0x042d;
                default: goto L_0x040f;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:164:0x041a, code lost:
            throw new java.lang.IllegalArgumentException(defpackage.y2.f("Bad opcode: ", r5));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:165:0x041b, code lost:
            if (r2 == false) goto L_0x042a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:166:0x041e, code lost:
            if (r2 == false) goto L_0x0424;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:167:0x0421, code lost:
            r10 = 4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:168:0x0424, code lost:
            r10 = 2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:169:0x0427, code lost:
            r10 = 5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:170:0x042a, code lost:
            r10 = 3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:171:0x042d, code lost:
            r10 = 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:173:0x0431, code lost:
            if (r0.j == false) goto L_0x043a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:175:0x0435, code lost:
            if (r5 == 196) goto L_0x043a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:176:0x0437, code lost:
            r0.j = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:177:0x043a, code lost:
            switch(r5) {
                case org.mozilla.javascript.Token.SET :int: goto L_0x0442;
                case org.mozilla.javascript.Token.LET :int: goto L_0x0442;
                case org.mozilla.javascript.Token.CONST :int: goto L_0x0442;
                case org.mozilla.javascript.Token.SETCONST :int: goto L_0x0442;
                case org.mozilla.javascript.Token.SETCONSTVAR :int: goto L_0x0442;
                case org.mozilla.javascript.Token.ARRAYCOMP :int: goto L_0x0442;
                case org.mozilla.javascript.Token.LETEXPR :int: goto L_0x0442;
                case 160: goto L_0x0442;
                case org.mozilla.javascript.Token.DEBUGGER :int: goto L_0x0442;
                case org.mozilla.javascript.Token.COMMENT :int: goto L_0x0442;
                case org.mozilla.javascript.Token.GENEXPR :int: goto L_0x0442;
                case org.mozilla.javascript.Token.METHOD :int: goto L_0x0442;
                case org.mozilla.javascript.Token.ARROW :int: goto L_0x0442;
                case org.mozilla.javascript.Token.YIELD_STAR :int: goto L_0x0442;
                case org.mozilla.javascript.Token.LAST_TOKEN :int: goto L_0x0442;
                default: goto L_0x043d;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:178:0x043d, code lost:
            switch(r5) {
                case 198: goto L_0x0442;
                case 199: goto L_0x0442;
                case 200: goto L_0x0442;
                default: goto L_0x0440;
            };
         */
        /* JADX WARNING: Code restructure failed: missing block: B:179:0x0440, code lost:
            r2 = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:180:0x0442, code lost:
            r2 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:181:0x0443, code lost:
            if (r2 == false) goto L_0x0468;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:183:0x044d, code lost:
            if ((r4.i[r3] & 255) != 200) goto L_0x0457;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:184:0x044f, code lost:
            r2 = f(r3 + 1, 4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:185:0x0457, code lost:
            r2 = (short) f(r3 + 1, 2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:186:0x045f, code lost:
            e(g(r2 + r3));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:188:0x046a, code lost:
            if (r5 != 170) goto L_0x04a4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:189:0x046c, code lost:
            r2 = (r3 + 1) + ((~r3) & 3);
            e(g(f(r2, 4) + r3));
            r11 = (f(r2 + 8, 4) - f(r2 + 4, 4)) + 1;
            r2 = r2 + 12;
            r8 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:190:0x0490, code lost:
            if (r8 >= r11) goto L_0x04a4;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:191:0x0492, code lost:
            e(g(f((r8 * 4) + r2, 4) + r3));
            r8 = r8 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:192:0x04a4, code lost:
            r2 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:194:0x04a7, code lost:
            if (r2 >= r4.f) goto L_0x04f3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:195:0x04a9, code lost:
            r7 = r4.e[r2];
            r8 = r4.ae(r7.a);
            r11 = r4.ae(r7.b);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:196:0x04b9, code lost:
            if (r3 < r8) goto L_0x04f0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:197:0x04bb, code lost:
            if (r3 < r11) goto L_0x04be;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:198:0x04be, code lost:
            r8 = g(r4.ae(r7.c));
            r7 = r7.d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:199:0x04ca, code lost:
            if (r7 != 0) goto L_0x04d7;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:200:0x04cc, code lost:
            r7 = defpackage.cg.b(r9.a("java/lang/Throwable"));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:201:0x04d7, code lost:
            r7 = defpackage.cg.b(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:202:0x04db, code lost:
            r8.b(r0.a, r0.b, new int[]{r7}, 1, r4.k);
            a(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:203:0x04f0, code lost:
            r2 = r2 + 1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:204:0x04f3, code lost:
            r3 = r3 + r10;
            r2 = 0;
            r4 = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:0x01b1, code lost:
            j(defpackage.cg.i(defpackage.l0.ad(((defpackage.x2) r9.e(f(r3 + 1, 2))).c), r9));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:74:0x026b, code lost:
            h();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:0x026e, code lost:
            h();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void d() {
            /*
                r17 = this;
                r0 = r17
            L_0x0002:
                int r1 = r0.f
                if (r1 <= 0) goto L_0x0523
                jc[] r2 = r0.e
                int r1 = r1 + -1
                r0.f = r1
                r1 = r2[r1]
                r2 = 0
                r1.g = r2
                int[] r3 = r1.d
                int r4 = r3.length
                int[] r4 = new int[r4]
                int r5 = r3.length
                java.lang.System.arraycopy(r3, r2, r4, r2, r5)
                r0.a = r4
                int[] r3 = r1.e
                int r4 = r3.length
                int[] r5 = new int[r4]
                int r6 = r3.length
                java.lang.System.arraycopy(r3, r2, r5, r2, r6)
                r0.c = r5
                int[] r3 = r0.a
                int r3 = r3.length
                r0.b = r3
                r0.d = r4
                int r3 = r1.b
                r4 = 0
            L_0x0031:
                int r5 = r1.c
                r6 = 1
                if (r3 >= r5) goto L_0x04f8
                l0 r4 = defpackage.l0.this
                byte[] r5 = r4.i
                byte r5 = r5[r3]
                r5 = r5 & 255(0xff, float:3.57E-43)
                r7 = 8
                r8 = 2
                s0 r9 = r4.k
                java.lang.String r10 = "V"
                r11 = 41
                r12 = 6
                switch(r5) {
                    case 0: goto L_0x03fc;
                    case 1: goto L_0x03f8;
                    case 2: goto L_0x03f4;
                    case 3: goto L_0x03f4;
                    case 4: goto L_0x03f4;
                    case 5: goto L_0x03f4;
                    case 6: goto L_0x03f4;
                    case 7: goto L_0x03f4;
                    case 8: goto L_0x03f4;
                    case 9: goto L_0x03ef;
                    case 10: goto L_0x03ef;
                    case 11: goto L_0x03ea;
                    case 12: goto L_0x03ea;
                    case 13: goto L_0x03ea;
                    case 14: goto L_0x03e5;
                    case 15: goto L_0x03e5;
                    case 16: goto L_0x03f4;
                    case 17: goto L_0x03f4;
                    case 18: goto L_0x0391;
                    case 19: goto L_0x0391;
                    case 20: goto L_0x0391;
                    case 21: goto L_0x03f4;
                    case 22: goto L_0x03ef;
                    case 23: goto L_0x03ea;
                    case 24: goto L_0x03e5;
                    case 25: goto L_0x037f;
                    case 26: goto L_0x03f4;
                    case 27: goto L_0x03f4;
                    case 28: goto L_0x03f4;
                    case 29: goto L_0x03f4;
                    case 30: goto L_0x03ef;
                    case 31: goto L_0x03ef;
                    case 32: goto L_0x03ef;
                    case 33: goto L_0x03ef;
                    case 34: goto L_0x03ea;
                    case 35: goto L_0x03ea;
                    case 36: goto L_0x03ea;
                    case 37: goto L_0x03ea;
                    case 38: goto L_0x03e5;
                    case 39: goto L_0x03e5;
                    case 40: goto L_0x03e5;
                    case 41: goto L_0x03e5;
                    case 42: goto L_0x0378;
                    case 43: goto L_0x0378;
                    case 44: goto L_0x0378;
                    case 45: goto L_0x0378;
                    case 46: goto L_0x0370;
                    case 47: goto L_0x0368;
                    case 48: goto L_0x0360;
                    case 49: goto L_0x0358;
                    case 50: goto L_0x0324;
                    case 51: goto L_0x0370;
                    case 52: goto L_0x0370;
                    case 53: goto L_0x0370;
                    case 54: goto L_0x0312;
                    case 55: goto L_0x02ff;
                    case 56: goto L_0x02ec;
                    case 57: goto L_0x02d9;
                    case 58: goto L_0x02b0;
                    case 59: goto L_0x02a9;
                    case 60: goto L_0x02a9;
                    case 61: goto L_0x02a9;
                    case 62: goto L_0x02a9;
                    case 63: goto L_0x02a1;
                    case 64: goto L_0x02a1;
                    case 65: goto L_0x02a1;
                    case 66: goto L_0x02a1;
                    case 67: goto L_0x0299;
                    case 68: goto L_0x0299;
                    case 69: goto L_0x0299;
                    case 70: goto L_0x0299;
                    case 71: goto L_0x0291;
                    case 72: goto L_0x0291;
                    case 73: goto L_0x0291;
                    case 74: goto L_0x0291;
                    case 75: goto L_0x0273;
                    case 76: goto L_0x0273;
                    case 77: goto L_0x0273;
                    case 78: goto L_0x0273;
                    case 79: goto L_0x0268;
                    case 80: goto L_0x0268;
                    case 81: goto L_0x0268;
                    case 82: goto L_0x0268;
                    case 83: goto L_0x0268;
                    case 84: goto L_0x0268;
                    case 85: goto L_0x0268;
                    case 86: goto L_0x0268;
                    case 87: goto L_0x026e;
                    case 88: goto L_0x0263;
                    case 89: goto L_0x0257;
                    case 90: goto L_0x0244;
                    case 91: goto L_0x0231;
                    case 92: goto L_0x0225;
                    case 93: goto L_0x0212;
                    case 94: goto L_0x01ff;
                    case 95: goto L_0x01ef;
                    case 96: goto L_0x0370;
                    case 97: goto L_0x0368;
                    case 98: goto L_0x0360;
                    case 99: goto L_0x0358;
                    case 100: goto L_0x0370;
                    case 101: goto L_0x0368;
                    case 102: goto L_0x0360;
                    case 103: goto L_0x0358;
                    case 104: goto L_0x0370;
                    case 105: goto L_0x0368;
                    case 106: goto L_0x0360;
                    case 107: goto L_0x0358;
                    case 108: goto L_0x0370;
                    case 109: goto L_0x0368;
                    case 110: goto L_0x0360;
                    case 111: goto L_0x0358;
                    case 112: goto L_0x0370;
                    case 113: goto L_0x0368;
                    case 114: goto L_0x0360;
                    case 115: goto L_0x0358;
                    case 116: goto L_0x0373;
                    case 117: goto L_0x036b;
                    case 118: goto L_0x0363;
                    case 119: goto L_0x035b;
                    case 120: goto L_0x0370;
                    case 121: goto L_0x0368;
                    case 122: goto L_0x0370;
                    case 123: goto L_0x0368;
                    case 124: goto L_0x0370;
                    case 125: goto L_0x0368;
                    case 126: goto L_0x0370;
                    case 127: goto L_0x0368;
                    case 128: goto L_0x0370;
                    case 129: goto L_0x0368;
                    case 130: goto L_0x0370;
                    case 131: goto L_0x0368;
                    case 132: goto L_0x03fc;
                    case 133: goto L_0x036b;
                    case 134: goto L_0x0363;
                    case 135: goto L_0x035b;
                    case 136: goto L_0x0373;
                    case 137: goto L_0x0363;
                    case 138: goto L_0x035b;
                    case 139: goto L_0x0373;
                    case 140: goto L_0x036b;
                    case 141: goto L_0x035b;
                    case 142: goto L_0x0373;
                    case 143: goto L_0x036b;
                    case 144: goto L_0x0363;
                    case 145: goto L_0x0373;
                    case 146: goto L_0x0373;
                    case 147: goto L_0x0373;
                    case 148: goto L_0x0370;
                    case 149: goto L_0x0370;
                    case 150: goto L_0x0370;
                    case 151: goto L_0x0370;
                    case 152: goto L_0x0370;
                    case 153: goto L_0x026e;
                    case 154: goto L_0x026e;
                    case 155: goto L_0x026e;
                    case 156: goto L_0x026e;
                    case 157: goto L_0x026e;
                    case 158: goto L_0x026e;
                    case 159: goto L_0x026b;
                    case 160: goto L_0x026b;
                    case 161: goto L_0x026b;
                    case 162: goto L_0x026b;
                    case 163: goto L_0x026b;
                    case 164: goto L_0x026b;
                    case 165: goto L_0x026b;
                    case 166: goto L_0x026b;
                    case 167: goto L_0x03fc;
                    case 168: goto L_0x004b;
                    case 169: goto L_0x004b;
                    case 170: goto L_0x01d1;
                    case 171: goto L_0x004b;
                    case 172: goto L_0x01cd;
                    case 173: goto L_0x01cd;
                    case 174: goto L_0x01cd;
                    case 175: goto L_0x01cd;
                    case 176: goto L_0x01cd;
                    case 177: goto L_0x01cd;
                    case 178: goto L_0x01b1;
                    case 179: goto L_0x026e;
                    case 180: goto L_0x01ae;
                    case 181: goto L_0x026b;
                    case 182: goto L_0x0130;
                    case 183: goto L_0x0130;
                    case 184: goto L_0x0130;
                    case 185: goto L_0x0130;
                    case 186: goto L_0x00f9;
                    case 187: goto L_0x00ee;
                    case 188: goto L_0x00a7;
                    case 189: goto L_0x0078;
                    case 190: goto L_0x0373;
                    case 191: goto L_0x006d;
                    case 192: goto L_0x005b;
                    case 193: goto L_0x0373;
                    case 194: goto L_0x026e;
                    case 195: goto L_0x026e;
                    case 196: goto L_0x0057;
                    case 197: goto L_0x004b;
                    case 198: goto L_0x026e;
                    case 199: goto L_0x026e;
                    case 200: goto L_0x03fc;
                    default: goto L_0x004b;
                }
            L_0x004b:
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                java.lang.String r2 = "bad opcode: "
                java.lang.String r2 = defpackage.y2.f(r2, r5)
                r1.<init>(r2)
                throw r1
            L_0x0057:
                r0.j = r6
                goto L_0x03fc
            L_0x005b:
                r17.h()
                int r2 = r3 + 1
                int r2 = r0.f(r2, r8)
                int r2 = defpackage.cg.b(r2)
                r0.j(r2)
                goto L_0x03fc
            L_0x006d:
                int r7 = r17.h()
                r0.d = r2
                r0.j(r7)
                goto L_0x03fc
            L_0x0078:
                int r2 = r3 + 1
                int r2 = r0.f(r2, r8)
                java.lang.Object r2 = r9.e(r2)
                java.lang.String r2 = (java.lang.String) r2
                r17.h()
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                java.lang.String r8 = "[L"
                r7.<init>(r8)
                r7.append(r2)
                r2 = 59
                r7.append(r2)
                java.lang.String r2 = r7.toString()
                short r2 = r9.a(r2)
                int r2 = defpackage.cg.b(r2)
                r0.j(r2)
                goto L_0x03fc
            L_0x00a7:
                r17.h()
                byte[] r2 = r4.i
                int r7 = r3 + 1
                byte r2 = r2[r7]
                switch(r2) {
                    case 4: goto L_0x00d0;
                    case 5: goto L_0x00cd;
                    case 6: goto L_0x00ca;
                    case 7: goto L_0x00c7;
                    case 8: goto L_0x00c4;
                    case 9: goto L_0x00c1;
                    case 10: goto L_0x00be;
                    case 11: goto L_0x00bb;
                    default: goto L_0x00b3;
                }
            L_0x00b3:
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                java.lang.String r2 = "bad operand"
                r1.<init>(r2)
                throw r1
            L_0x00bb:
                r2 = 74
                goto L_0x00d2
            L_0x00be:
                r2 = 73
                goto L_0x00d2
            L_0x00c1:
                r2 = 83
                goto L_0x00d2
            L_0x00c4:
                r2 = 66
                goto L_0x00d2
            L_0x00c7:
                r2 = 68
                goto L_0x00d2
            L_0x00ca:
                r2 = 70
                goto L_0x00d2
            L_0x00cd:
                r2 = 67
                goto L_0x00d2
            L_0x00d0:
                r2 = 90
            L_0x00d2:
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                java.lang.String r8 = "["
                r7.<init>(r8)
                r7.append(r2)
                java.lang.String r2 = r7.toString()
                short r2 = r9.a(r2)
                short r2 = (short) r2
                int r2 = defpackage.cg.b(r2)
                r0.j(r2)
                goto L_0x03fc
            L_0x00ee:
                r2 = 65535(0xffff, float:9.1834E-41)
                r2 = r2 & r3
                int r2 = r2 << r7
                r2 = r2 | r7
                r0.j(r2)
                goto L_0x03fc
            L_0x00f9:
                int r2 = r3 + 1
                int r2 = r0.f(r2, r8)
                java.lang.Object r2 = r9.e(r2)
                java.lang.String r2 = (java.lang.String) r2
                int r7 = defpackage.l0.an(r2)
                int r7 = r7 >>> 16
                r8 = 0
            L_0x010c:
                if (r8 >= r7) goto L_0x0114
                r17.h()
                int r8 = r8 + 1
                goto L_0x010c
            L_0x0114:
                int r7 = r2.indexOf(r11)
                int r7 = r7 + r6
                java.lang.String r2 = r2.substring(r7)
                java.lang.String r2 = defpackage.l0.ad(r2)
                boolean r7 = r2.equals(r10)
                if (r7 != 0) goto L_0x03fc
                int r2 = defpackage.cg.i(r2, r9)
                r0.j(r2)
                goto L_0x03fc
            L_0x0130:
                int r13 = r3 + 1
                int r8 = r0.f(r13, r8)
                java.lang.Object r8 = r9.e(r8)
                x2 r8 = (defpackage.x2) r8
                java.lang.String r13 = r8.c
                int r14 = defpackage.l0.an(r13)
                int r14 = r14 >>> 16
            L_0x0144:
                if (r2 >= r14) goto L_0x014c
                r17.h()
                int r2 = r2 + 1
                goto L_0x0144
            L_0x014c:
                r2 = 184(0xb8, float:2.58E-43)
                if (r5 == r2) goto L_0x0192
                int r2 = r17.h()
                r14 = r2 & 255(0xff, float:3.57E-43)
                if (r14 == r7) goto L_0x015a
                if (r14 != r12) goto L_0x0192
            L_0x015a:
                java.lang.String r7 = "<init>"
                java.lang.String r8 = r8.b
                boolean r7 = r7.equals(r8)
                if (r7 == 0) goto L_0x018a
                short r7 = r4.t
                int r7 = defpackage.cg.b(r7)
                int[] r8 = r0.a
                int r12 = r0.b
                r14 = 0
            L_0x016f:
                if (r14 >= r12) goto L_0x017a
                r15 = r8[r14]
                if (r15 != r2) goto L_0x0177
                r8[r14] = r7
            L_0x0177:
                int r14 = r14 + 1
                goto L_0x016f
            L_0x017a:
                int[] r8 = r0.c
                int r12 = r0.d
                r14 = 0
            L_0x017f:
                if (r14 >= r12) goto L_0x0192
                r15 = r8[r14]
                if (r15 != r2) goto L_0x0187
                r8[r14] = r7
            L_0x0187:
                int r14 = r14 + 1
                goto L_0x017f
            L_0x018a:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                java.lang.String r2 = "bad instance"
                r1.<init>(r2)
                throw r1
            L_0x0192:
                int r2 = r13.indexOf(r11)
                int r2 = r2 + r6
                java.lang.String r2 = r13.substring(r2)
                java.lang.String r2 = defpackage.l0.ad(r2)
                boolean r7 = r2.equals(r10)
                if (r7 != 0) goto L_0x03fc
                int r2 = defpackage.cg.i(r2, r9)
                r0.j(r2)
                goto L_0x03fc
            L_0x01ae:
                r17.h()
            L_0x01b1:
                int r2 = r3 + 1
                r7 = 2
                int r2 = r0.f(r2, r7)
                java.lang.Object r2 = r9.e(r2)
                x2 r2 = (defpackage.x2) r2
                java.lang.String r2 = r2.c
                java.lang.String r2 = defpackage.l0.ad(r2)
                int r2 = defpackage.cg.i(r2, r9)
                r0.j(r2)
                goto L_0x03fc
            L_0x01cd:
                r0.d = r2
                goto L_0x03fc
            L_0x01d1:
                int r2 = r3 + 1
                int r7 = ~r3
                r7 = r7 & 3
                int r2 = r2 + r7
                int r7 = r2 + 4
                r8 = 4
                int r7 = r0.f(r7, r8)
                int r10 = r2 + 8
                int r10 = r0.f(r10, r8)
                int r10 = r10 - r7
                int r10 = r10 + r8
                int r10 = r10 * 4
                int r10 = r10 + r2
                int r10 = r10 - r3
                r17.h()
                goto L_0x03fd
            L_0x01ef:
                int r2 = r17.h()
                int r7 = r17.h()
                r0.j(r2)
                r0.j(r7)
                goto L_0x03fc
            L_0x01ff:
                long r7 = r17.i()
                long r10 = r17.i()
                r0.k(r7)
                r0.k(r10)
                r0.k(r7)
                goto L_0x03fc
            L_0x0212:
                long r7 = r17.i()
                int r2 = r17.h()
                r0.k(r7)
                r0.j(r2)
                r0.k(r7)
                goto L_0x03fc
            L_0x0225:
                long r7 = r17.i()
                r0.k(r7)
                r0.k(r7)
                goto L_0x03fc
            L_0x0231:
                int r2 = r17.h()
                long r7 = r17.i()
                r0.j(r2)
                r0.k(r7)
                r0.j(r2)
                goto L_0x03fc
            L_0x0244:
                int r2 = r17.h()
                int r7 = r17.h()
                r0.j(r2)
                r0.j(r7)
                r0.j(r2)
                goto L_0x03fc
            L_0x0257:
                int r2 = r17.h()
                r0.j(r2)
                r0.j(r2)
                goto L_0x03fc
            L_0x0263:
                r17.i()
                goto L_0x03fc
            L_0x0268:
                r17.h()
            L_0x026b:
                r17.h()
            L_0x026e:
                r17.h()
                goto L_0x03fc
            L_0x0273:
                int r2 = r5 + -75
                int r7 = r17.h()
                int r8 = r0.b
                if (r2 < r8) goto L_0x028b
                int r10 = r2 + 1
                int[] r11 = new int[r10]
                int[] r12 = r0.a
                r13 = 0
                java.lang.System.arraycopy(r12, r13, r11, r13, r8)
                r0.a = r11
                r0.b = r10
            L_0x028b:
                int[] r8 = r0.a
                r8[r2] = r7
                goto L_0x03fc
            L_0x0291:
                int r2 = r5 + -71
                r7 = 3
                r0.c(r2, r7)
                goto L_0x03fc
            L_0x0299:
                int r2 = r5 + -67
                r7 = 2
                r0.c(r2, r7)
                goto L_0x03fc
            L_0x02a1:
                int r2 = r5 + -63
                r7 = 4
                r0.c(r2, r7)
                goto L_0x03fc
            L_0x02a9:
                int r2 = r5 + -59
                r0.c(r2, r6)
                goto L_0x03fc
            L_0x02b0:
                int r2 = r3 + 1
                boolean r7 = r0.j
                if (r7 == 0) goto L_0x02b8
                r7 = 2
                goto L_0x02b9
            L_0x02b8:
                r7 = 1
            L_0x02b9:
                int r2 = r0.f(r2, r7)
                int r7 = r17.h()
                int r8 = r0.b
                if (r2 < r8) goto L_0x02d3
                int r10 = r2 + 1
                int[] r11 = new int[r10]
                int[] r12 = r0.a
                r13 = 0
                java.lang.System.arraycopy(r12, r13, r11, r13, r8)
                r0.a = r11
                r0.b = r10
            L_0x02d3:
                int[] r8 = r0.a
                r8[r2] = r7
                goto L_0x03fc
            L_0x02d9:
                int r2 = r3 + 1
                boolean r7 = r0.j
                if (r7 == 0) goto L_0x02e1
                r7 = 2
                goto L_0x02e2
            L_0x02e1:
                r7 = 1
            L_0x02e2:
                int r2 = r0.f(r2, r7)
                r7 = 3
                r0.c(r2, r7)
                goto L_0x03fc
            L_0x02ec:
                int r2 = r3 + 1
                boolean r7 = r0.j
                if (r7 == 0) goto L_0x02f4
                r7 = 2
                goto L_0x02f5
            L_0x02f4:
                r7 = 1
            L_0x02f5:
                int r2 = r0.f(r2, r7)
                r7 = 2
                r0.c(r2, r7)
                goto L_0x03fc
            L_0x02ff:
                int r2 = r3 + 1
                boolean r7 = r0.j
                if (r7 == 0) goto L_0x0307
                r7 = 2
                goto L_0x0308
            L_0x0307:
                r7 = 1
            L_0x0308:
                int r2 = r0.f(r2, r7)
                r7 = 4
                r0.c(r2, r7)
                goto L_0x03fc
            L_0x0312:
                int r2 = r3 + 1
                boolean r7 = r0.j
                if (r7 == 0) goto L_0x031a
                r7 = 2
                goto L_0x031b
            L_0x031a:
                r7 = 1
            L_0x031b:
                int r2 = r0.f(r2, r7)
                r0.c(r2, r6)
                goto L_0x03fc
            L_0x0324:
                r17.h()
                int r2 = r17.h()
                int r2 = r2 >>> r7
                java.lang.Object r2 = r9.e(r2)
                java.lang.String r2 = (java.lang.String) r2
                r7 = 0
                char r7 = r2.charAt(r7)
                r8 = 91
                if (r7 != r8) goto L_0x0350
                java.lang.String r2 = r2.substring(r6)
                java.lang.String r2 = defpackage.l0.ad(r2)
                short r2 = r9.a(r2)
                int r2 = defpackage.cg.b(r2)
                r0.j(r2)
                goto L_0x03fc
            L_0x0350:
                java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
                java.lang.String r2 = "bad array type"
                r1.<init>(r2)
                throw r1
            L_0x0358:
                r17.h()
            L_0x035b:
                r17.h()
                goto L_0x03e5
            L_0x0360:
                r17.h()
            L_0x0363:
                r17.h()
                goto L_0x03ea
            L_0x0368:
                r17.h()
            L_0x036b:
                r17.h()
                goto L_0x03ef
            L_0x0370:
                r17.h()
            L_0x0373:
                r17.h()
                goto L_0x03f4
            L_0x0378:
                int r2 = r5 + -42
                r0.b(r2)
                goto L_0x03fc
            L_0x037f:
                int r2 = r3 + 1
                boolean r7 = r0.j
                if (r7 == 0) goto L_0x0387
                r7 = 2
                goto L_0x0388
            L_0x0387:
                r7 = 1
            L_0x0388:
                int r2 = r0.f(r2, r7)
                r0.b(r2)
                goto L_0x03fc
            L_0x0391:
                r2 = 18
                if (r5 != r2) goto L_0x039c
                int r2 = r3 + 1
                int r2 = r0.f(r2, r6)
                goto L_0x03a3
            L_0x039c:
                int r2 = r3 + 1
                r8 = 2
                int r2 = r0.f(r2, r8)
            L_0x03a3:
                org.mozilla.javascript.UintMap r8 = r9.j
                r10 = 0
                int r2 = r8.getInt(r2, r10)
                byte r2 = (byte) r2
                r8 = 3
                if (r2 == r8) goto L_0x03e1
                r8 = 4
                if (r2 == r8) goto L_0x03dc
                r8 = 5
                if (r2 == r8) goto L_0x03d7
                if (r2 == r12) goto L_0x03d2
                if (r2 != r7) goto L_0x03c6
                java.lang.String r2 = "java/lang/String"
                short r2 = r9.a(r2)
                int r2 = defpackage.cg.b(r2)
                r0.j(r2)
                goto L_0x03fc
            L_0x03c6:
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                java.lang.String r3 = "bad const type "
                java.lang.String r2 = defpackage.y2.f(r3, r2)
                r1.<init>(r2)
                throw r1
            L_0x03d2:
                r2 = 3
                r0.j(r2)
                goto L_0x03fc
            L_0x03d7:
                r2 = 4
                r0.j(r2)
                goto L_0x03fc
            L_0x03dc:
                r2 = 2
                r0.j(r2)
                goto L_0x03fc
            L_0x03e1:
                r0.j(r6)
                goto L_0x03fc
            L_0x03e5:
                r2 = 3
                r0.j(r2)
                goto L_0x03fc
            L_0x03ea:
                r2 = 2
                r0.j(r2)
                goto L_0x03fc
            L_0x03ef:
                r2 = 4
                r0.j(r2)
                goto L_0x03fc
            L_0x03f4:
                r0.j(r6)
                goto L_0x03fc
            L_0x03f8:
                r2 = 5
                r0.j(r2)
            L_0x03fc:
                r10 = 0
            L_0x03fd:
                if (r10 != 0) goto L_0x042f
                boolean r2 = r0.j
                r7 = 254(0xfe, float:3.56E-43)
                if (r5 == r7) goto L_0x042d
                r7 = 255(0xff, float:3.57E-43)
                if (r5 == r7) goto L_0x042d
                switch(r5) {
                    case 0: goto L_0x042d;
                    case 1: goto L_0x042d;
                    case 2: goto L_0x042d;
                    case 3: goto L_0x042d;
                    case 4: goto L_0x042d;
                    case 5: goto L_0x042d;
                    case 6: goto L_0x042d;
                    case 7: goto L_0x042d;
                    case 8: goto L_0x042d;
                    case 9: goto L_0x042d;
                    case 10: goto L_0x042d;
                    case 11: goto L_0x042d;
                    case 12: goto L_0x042d;
                    case 13: goto L_0x042d;
                    case 14: goto L_0x042d;
                    case 15: goto L_0x042d;
                    case 16: goto L_0x0424;
                    case 17: goto L_0x042a;
                    case 18: goto L_0x0424;
                    case 19: goto L_0x042a;
                    case 20: goto L_0x042a;
                    case 21: goto L_0x041e;
                    case 22: goto L_0x041e;
                    case 23: goto L_0x041e;
                    case 24: goto L_0x041e;
                    case 25: goto L_0x041e;
                    case 26: goto L_0x042d;
                    case 27: goto L_0x042d;
                    case 28: goto L_0x042d;
                    case 29: goto L_0x042d;
                    case 30: goto L_0x042d;
                    case 31: goto L_0x042d;
                    case 32: goto L_0x042d;
                    case 33: goto L_0x042d;
                    case 34: goto L_0x042d;
                    case 35: goto L_0x042d;
                    case 36: goto L_0x042d;
                    case 37: goto L_0x042d;
                    case 38: goto L_0x042d;
                    case 39: goto L_0x042d;
                    case 40: goto L_0x042d;
                    case 41: goto L_0x042d;
                    case 42: goto L_0x042d;
                    case 43: goto L_0x042d;
                    case 44: goto L_0x042d;
                    case 45: goto L_0x042d;
                    case 46: goto L_0x042d;
                    case 47: goto L_0x042d;
                    case 48: goto L_0x042d;
                    case 49: goto L_0x042d;
                    case 50: goto L_0x042d;
                    case 51: goto L_0x042d;
                    case 52: goto L_0x042d;
                    case 53: goto L_0x042d;
                    case 54: goto L_0x041e;
                    case 55: goto L_0x041e;
                    case 56: goto L_0x041e;
                    case 57: goto L_0x041e;
                    case 58: goto L_0x041e;
                    case 59: goto L_0x042d;
                    case 60: goto L_0x042d;
                    case 61: goto L_0x042d;
                    case 62: goto L_0x042d;
                    case 63: goto L_0x042d;
                    case 64: goto L_0x042d;
                    case 65: goto L_0x042d;
                    case 66: goto L_0x042d;
                    case 67: goto L_0x042d;
                    case 68: goto L_0x042d;
                    case 69: goto L_0x042d;
                    case 70: goto L_0x042d;
                    case 71: goto L_0x042d;
                    case 72: goto L_0x042d;
                    case 73: goto L_0x042d;
                    case 74: goto L_0x042d;
                    case 75: goto L_0x042d;
                    case 76: goto L_0x042d;
                    case 77: goto L_0x042d;
                    case 78: goto L_0x042d;
                    case 79: goto L_0x042d;
                    case 80: goto L_0x042d;
                    case 81: goto L_0x042d;
                    case 82: goto L_0x042d;
                    case 83: goto L_0x042d;
                    case 84: goto L_0x042d;
                    case 85: goto L_0x042d;
                    case 86: goto L_0x042d;
                    case 87: goto L_0x042d;
                    case 88: goto L_0x042d;
                    case 89: goto L_0x042d;
                    case 90: goto L_0x042d;
                    case 91: goto L_0x042d;
                    case 92: goto L_0x042d;
                    case 93: goto L_0x042d;
                    case 94: goto L_0x042d;
                    case 95: goto L_0x042d;
                    case 96: goto L_0x042d;
                    case 97: goto L_0x042d;
                    case 98: goto L_0x042d;
                    case 99: goto L_0x042d;
                    case 100: goto L_0x042d;
                    case 101: goto L_0x042d;
                    case 102: goto L_0x042d;
                    case 103: goto L_0x042d;
                    case 104: goto L_0x042d;
                    case 105: goto L_0x042d;
                    case 106: goto L_0x042d;
                    case 107: goto L_0x042d;
                    case 108: goto L_0x042d;
                    case 109: goto L_0x042d;
                    case 110: goto L_0x042d;
                    case 111: goto L_0x042d;
                    case 112: goto L_0x042d;
                    case 113: goto L_0x042d;
                    case 114: goto L_0x042d;
                    case 115: goto L_0x042d;
                    case 116: goto L_0x042d;
                    case 117: goto L_0x042d;
                    case 118: goto L_0x042d;
                    case 119: goto L_0x042d;
                    case 120: goto L_0x042d;
                    case 121: goto L_0x042d;
                    case 122: goto L_0x042d;
                    case 123: goto L_0x042d;
                    case 124: goto L_0x042d;
                    case 125: goto L_0x042d;
                    case 126: goto L_0x042d;
                    case 127: goto L_0x042d;
                    case 128: goto L_0x042d;
                    case 129: goto L_0x042d;
                    case 130: goto L_0x042d;
                    case 131: goto L_0x042d;
                    case 132: goto L_0x041b;
                    case 133: goto L_0x042d;
                    case 134: goto L_0x042d;
                    case 135: goto L_0x042d;
                    case 136: goto L_0x042d;
                    case 137: goto L_0x042d;
                    case 138: goto L_0x042d;
                    case 139: goto L_0x042d;
                    case 140: goto L_0x042d;
                    case 141: goto L_0x042d;
                    case 142: goto L_0x042d;
                    case 143: goto L_0x042d;
                    case 144: goto L_0x042d;
                    case 145: goto L_0x042d;
                    case 146: goto L_0x042d;
                    case 147: goto L_0x042d;
                    case 148: goto L_0x042d;
                    case 149: goto L_0x042d;
                    case 150: goto L_0x042d;
                    case 151: goto L_0x042d;
                    case 152: goto L_0x042d;
                    case 153: goto L_0x042a;
                    case 154: goto L_0x042a;
                    case 155: goto L_0x042a;
                    case 156: goto L_0x042a;
                    case 157: goto L_0x042a;
                    case 158: goto L_0x042a;
                    case 159: goto L_0x042a;
                    case 160: goto L_0x042a;
                    case 161: goto L_0x042a;
                    case 162: goto L_0x042a;
                    case 163: goto L_0x042a;
                    case 164: goto L_0x042a;
                    case 165: goto L_0x042a;
                    case 166: goto L_0x042a;
                    case 167: goto L_0x042a;
                    case 168: goto L_0x042a;
                    case 169: goto L_0x041e;
                    default: goto L_0x040c;
                }
            L_0x040c:
                switch(r5) {
                    case 172: goto L_0x042d;
                    case 173: goto L_0x042d;
                    case 174: goto L_0x042d;
                    case 175: goto L_0x042d;
                    case 176: goto L_0x042d;
                    case 177: goto L_0x042d;
                    case 178: goto L_0x042a;
                    case 179: goto L_0x042a;
                    case 180: goto L_0x042a;
                    case 181: goto L_0x042a;
                    case 182: goto L_0x042a;
                    case 183: goto L_0x042a;
                    case 184: goto L_0x042a;
                    case 185: goto L_0x0427;
                    case 186: goto L_0x0427;
                    case 187: goto L_0x042a;
                    case 188: goto L_0x0424;
                    case 189: goto L_0x042a;
                    case 190: goto L_0x042d;
                    case 191: goto L_0x042d;
                    case 192: goto L_0x042a;
                    case 193: goto L_0x042a;
                    case 194: goto L_0x042d;
                    case 195: goto L_0x042d;
                    case 196: goto L_0x042d;
                    case 197: goto L_0x0421;
                    case 198: goto L_0x042a;
                    case 199: goto L_0x042a;
                    case 200: goto L_0x0427;
                    case 201: goto L_0x0427;
                    case 202: goto L_0x042d;
                    default: goto L_0x040f;
                }
            L_0x040f:
                java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
                java.lang.String r2 = "Bad opcode: "
                java.lang.String r2 = defpackage.y2.f(r2, r5)
                r1.<init>(r2)
                throw r1
            L_0x041b:
                if (r2 == 0) goto L_0x042a
                goto L_0x0427
            L_0x041e:
                if (r2 == 0) goto L_0x0424
                goto L_0x042a
            L_0x0421:
                r2 = 4
                r10 = 4
                goto L_0x042f
            L_0x0424:
                r2 = 2
                r10 = 2
                goto L_0x042f
            L_0x0427:
                r2 = 5
                r10 = 5
                goto L_0x042f
            L_0x042a:
                r2 = 3
                r10 = 3
                goto L_0x042f
            L_0x042d:
                r2 = 1
                r10 = 1
            L_0x042f:
                boolean r2 = r0.j
                if (r2 == 0) goto L_0x043a
                r2 = 196(0xc4, float:2.75E-43)
                if (r5 == r2) goto L_0x043a
                r2 = 0
                r0.j = r2
            L_0x043a:
                switch(r5) {
                    case 153: goto L_0x0442;
                    case 154: goto L_0x0442;
                    case 155: goto L_0x0442;
                    case 156: goto L_0x0442;
                    case 157: goto L_0x0442;
                    case 158: goto L_0x0442;
                    case 159: goto L_0x0442;
                    case 160: goto L_0x0442;
                    case 161: goto L_0x0442;
                    case 162: goto L_0x0442;
                    case 163: goto L_0x0442;
                    case 164: goto L_0x0442;
                    case 165: goto L_0x0442;
                    case 166: goto L_0x0442;
                    case 167: goto L_0x0442;
                    default: goto L_0x043d;
                }
            L_0x043d:
                switch(r5) {
                    case 198: goto L_0x0442;
                    case 199: goto L_0x0442;
                    case 200: goto L_0x0442;
                    default: goto L_0x0440;
                }
            L_0x0440:
                r2 = 0
                goto L_0x0443
            L_0x0442:
                r2 = 1
            L_0x0443:
                if (r2 == 0) goto L_0x0468
                byte[] r2 = r4.i
                byte r2 = r2[r3]
                r2 = r2 & 255(0xff, float:3.57E-43)
                r7 = 200(0xc8, float:2.8E-43)
                if (r2 != r7) goto L_0x0457
                int r2 = r3 + 1
                r7 = 4
                int r2 = r0.f(r2, r7)
                goto L_0x045f
            L_0x0457:
                int r2 = r3 + 1
                r7 = 2
                int r2 = r0.f(r2, r7)
                short r2 = (short) r2
            L_0x045f:
                int r2 = r2 + r3
                jc r2 = r0.g(r2)
                r0.e(r2)
                goto L_0x04a4
            L_0x0468:
                r2 = 170(0xaa, float:2.38E-43)
                if (r5 != r2) goto L_0x04a4
                int r2 = r3 + 1
                int r7 = ~r3
                r7 = r7 & 3
                int r2 = r2 + r7
                r7 = 4
                int r8 = r0.f(r2, r7)
                int r8 = r8 + r3
                jc r8 = r0.g(r8)
                r0.e(r8)
                int r8 = r2 + 4
                int r8 = r0.f(r8, r7)
                int r11 = r2 + 8
                int r11 = r0.f(r11, r7)
                int r11 = r11 - r8
                int r11 = r11 + r6
                int r2 = r2 + 12
                r8 = 0
            L_0x0490:
                if (r8 >= r11) goto L_0x04a4
                int r12 = r8 * 4
                int r12 = r12 + r2
                int r12 = r0.f(r12, r7)
                int r12 = r12 + r3
                jc r12 = r0.g(r12)
                r0.e(r12)
                int r8 = r8 + 1
                goto L_0x0490
            L_0x04a4:
                r2 = 0
            L_0x04a5:
                int r7 = r4.f
                if (r2 >= r7) goto L_0x04f3
                e2[] r7 = r4.e
                r7 = r7[r2]
                int r8 = r7.a
                int r8 = r4.ae(r8)
                int r11 = r7.b
                int r11 = r4.ae(r11)
                if (r3 < r8) goto L_0x04f0
                if (r3 < r11) goto L_0x04be
                goto L_0x04f0
            L_0x04be:
                int r8 = r7.c
                int r8 = r4.ae(r8)
                jc r8 = r0.g(r8)
                short r7 = r7.d
                if (r7 != 0) goto L_0x04d7
                java.lang.String r7 = "java/lang/Throwable"
                short r7 = r9.a(r7)
                int r7 = defpackage.cg.b(r7)
                goto L_0x04db
            L_0x04d7:
                int r7 = defpackage.cg.b(r7)
            L_0x04db:
                int[] r12 = r0.a
                int r13 = r0.b
                int[] r14 = new int[r6]
                r11 = 0
                r14[r11] = r7
                r15 = 1
                s0 r7 = r4.k
                r11 = r8
                r16 = r7
                r11.b(r12, r13, r14, r15, r16)
                r0.a(r8)
            L_0x04f0:
                int r2 = r2 + 1
                goto L_0x04a5
            L_0x04f3:
                int r3 = r3 + r10
                r2 = 0
                r4 = r5
                goto L_0x0031
            L_0x04f8:
                r2 = 167(0xa7, float:2.34E-43)
                if (r4 == r2) goto L_0x0511
                r2 = 191(0xbf, float:2.68E-43)
                if (r4 == r2) goto L_0x0511
                r2 = 200(0xc8, float:2.8E-43)
                if (r4 == r2) goto L_0x0511
                r2 = 176(0xb0, float:2.47E-43)
                if (r4 == r2) goto L_0x0511
                r2 = 177(0xb1, float:2.48E-43)
                if (r4 == r2) goto L_0x0511
                switch(r4) {
                    case 170: goto L_0x0511;
                    case 171: goto L_0x0511;
                    case 172: goto L_0x0511;
                    case 173: goto L_0x0511;
                    case 174: goto L_0x0511;
                    default: goto L_0x050f;
                }
            L_0x050f:
                r2 = 0
                goto L_0x0512
            L_0x0511:
                r2 = 1
            L_0x0512:
                if (r2 != 0) goto L_0x0002
                int r1 = r1.a
                int r1 = r1 + r6
                jc[] r2 = r0.g
                int r3 = r2.length
                if (r1 >= r3) goto L_0x0002
                r1 = r2[r1]
                r0.e(r1)
                goto L_0x0002
            L_0x0523:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.l0.b.d():void");
        }

        public final void e(jc jcVar) {
            if (jcVar.b(this.a, this.b, this.c, this.d, l0.this.k)) {
                a(jcVar);
            }
        }

        public final int f(int i2, int i3) {
            if (i3 <= 4) {
                byte b2 = 0;
                for (int i4 = 0; i4 < i3; i4++) {
                    b2 = (b2 << 8) | (l0.this.i[i2 + i4] & 255);
                }
                return b2;
            }
            throw new IllegalArgumentException("bad operand size");
        }

        public final jc g(int i2) {
            jc jcVar;
            int i3 = 0;
            while (true) {
                jc[] jcVarArr = this.g;
                if (i3 < jcVarArr.length && (jcVar = jcVarArr[i3]) != null) {
                    if (i2 >= jcVar.b && i2 < jcVar.c) {
                        return jcVar;
                    }
                    i3++;
                }
            }
            throw new IllegalArgumentException(y2.f("bad offset: ", i2));
        }

        public final int h() {
            int[] iArr = this.c;
            int i2 = this.d - 1;
            this.d = i2;
            return iArr[i2];
        }

        public final long i() {
            long h2 = (long) h();
            if (cg.k((int) h2)) {
                return h2;
            }
            return (h2 << 32) | ((long) (h() & ViewCompat.MEASURED_SIZE_MASK));
        }

        public final void j(int i2) {
            int i3 = this.d;
            if (i3 == this.c.length) {
                int[] iArr = new int[Math.max(i3 * 2, 4)];
                System.arraycopy(this.c, 0, iArr, 0, this.d);
                this.c = iArr;
            }
            int[] iArr2 = this.c;
            int i4 = this.d;
            this.d = i4 + 1;
            iArr2[i4] = i2;
        }

        public final void k(long j2) {
            j((int) (j2 & 16777215));
            long j3 = j2 >>> 32;
            if (j3 != 0) {
                j((int) (j3 & 16777215));
            }
        }

        public final void l(int[] iArr, int[] iArr2, int i2) {
            byte[] bArr = this.h;
            int i3 = this.i;
            int i4 = i3 + 1;
            this.i = i4;
            bArr[i3] = -1;
            int ak = l0.ak(bArr, i2, i4);
            this.i = ak;
            this.i = l0.ak(this.h, iArr.length, ak);
            int n = n(iArr, 0);
            this.i = n;
            this.i = l0.ak(this.h, iArr2.length, n);
            this.i = n(iArr2, 0);
        }

        public final int m(int i2) {
            int i3 = i2 & 255;
            byte[] bArr = this.h;
            int i4 = this.i;
            int i5 = i4 + 1;
            this.i = i5;
            bArr[i4] = (byte) i3;
            if (i3 == 7 || i3 == 8) {
                this.i = l0.ak(bArr, i2 >>> 8, i5);
            }
            return this.i;
        }

        public final int n(int[] iArr, int i2) {
            while (i2 < iArr.length) {
                this.i = m(iArr[i2]);
                i2++;
            }
            return this.i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x005d A[SYNTHETIC, Splitter:B:28:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    static {
        /*
            r0 = 0
            r1 = 48
            r2 = 0
            java.lang.Class<l0> r3 = defpackage.l0.class
            java.lang.String r4 = "ClassFileWriter.class"
            java.io.InputStream r0 = r3.getResourceAsStream(r4)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            if (r0 != 0) goto L_0x0014
            java.lang.String r3 = "org/mozilla/classfile/ClassFileWriter.class"
            java.io.InputStream r0 = java.lang.ClassLoader.getSystemResourceAsStream(r3)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
        L_0x0014:
            r3 = 8
            byte[] r4 = new byte[r3]     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            r5 = 0
        L_0x0019:
            if (r5 >= r3) goto L_0x002b
            int r6 = 8 - r5
            int r6 = r0.read(r4, r5, r6)     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            if (r6 < 0) goto L_0x0025
            int r5 = r5 + r6
            goto L_0x0019
        L_0x0025:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            r3.<init>()     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            throw r3     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
        L_0x002b:
            r5 = 4
            byte r5 = r4[r5]     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            int r5 = r5 << r3
            r6 = 5
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x0061, all -> 0x0053 }
            r6 = r6 & 255(0xff, float:3.57E-43)
            r5 = r5 | r6
            r6 = 6
            byte r6 = r4[r6]     // Catch:{ Exception -> 0x0062, all -> 0x0051 }
            int r3 = r6 << 8
            r6 = 7
            byte r1 = r4[r6]     // Catch:{ Exception -> 0x0062, all -> 0x0051 }
            r1 = r1 & 255(0xff, float:3.57E-43)
            r1 = r1 | r3
            ad = r5
            ac = r1
            r3 = 50
            if (r1 < r3) goto L_0x0049
            r2 = 1
        L_0x0049:
            ae = r2
            if (r0 == 0) goto L_0x006b
        L_0x004d:
            r0.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006b
        L_0x0051:
            r3 = move-exception
            goto L_0x0055
        L_0x0053:
            r3 = move-exception
            r5 = 0
        L_0x0055:
            ad = r5
            ac = r1
            ae = r2
            if (r0 == 0) goto L_0x0060
            r0.close()     // Catch:{ IOException -> 0x0060 }
        L_0x0060:
            throw r3
        L_0x0061:
            r5 = 0
        L_0x0062:
            ad = r5
            ac = r1
            ae = r2
            if (r0 == 0) goto L_0x006b
            goto L_0x004d
        L_0x006b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l0.<clinit>():void");
    }

    public l0(String str, String str2, String str3) {
        this.d = str;
        s0 s0Var = new s0(this);
        this.k = s0Var;
        this.t = s0Var.a(str);
        this.u = s0Var.a(str2);
        if (str3 != null) {
            this.v = s0Var.c(str3);
        }
        this.s = 33;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x009e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int[] a(defpackage.l0 r10) {
        /*
            short r0 = r10.o
            int[] r0 = new int[r0]
            k0 r1 = r10.l
            short r2 = r1.e
            r2 = r2 & 8
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x0026
            java.lang.String r1 = r1.a
            java.lang.String r2 = "<init>"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L_0x001c
            r1 = 6
            r0[r3] = r1
            goto L_0x0024
        L_0x001c:
            short r1 = r10.t
            int r1 = defpackage.cg.b(r1)
            r0[r3] = r1
        L_0x0024:
            r1 = 1
            goto L_0x0027
        L_0x0026:
            r1 = 0
        L_0x0027:
            k0 r2 = r10.l
            java.lang.String r2 = r2.b
            r5 = 40
            int r5 = r2.indexOf(r5)
            r6 = 41
            int r6 = r2.indexOf(r6)
            if (r5 != 0) goto L_0x00a6
            if (r6 < 0) goto L_0x00a6
            int r5 = r5 + r4
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
        L_0x0041:
            if (r5 >= r6) goto L_0x00a5
            char r8 = r2.charAt(r5)
            r9 = 70
            if (r8 == r9) goto L_0x007d
            r9 = 76
            if (r8 == r9) goto L_0x006d
            r9 = 83
            if (r8 == r9) goto L_0x007d
            r9 = 73
            if (r8 == r9) goto L_0x007d
            r9 = 74
            if (r8 == r9) goto L_0x007d
            r9 = 90
            if (r8 == r9) goto L_0x007d
            r9 = 91
            if (r8 == r9) goto L_0x0067
            switch(r8) {
                case 66: goto L_0x007d;
                case 67: goto L_0x007d;
                case 68: goto L_0x007d;
                default: goto L_0x0066;
            }
        L_0x0066:
            goto L_0x0086
        L_0x0067:
            r7.append(r9)
            int r5 = r5 + 1
            goto L_0x0041
        L_0x006d:
            r8 = 59
            int r8 = r2.indexOf(r8, r5)
            int r8 = r8 + r4
            java.lang.String r5 = r2.substring(r5, r8)
            r7.append(r5)
            r5 = r8
            goto L_0x0086
        L_0x007d:
            char r8 = r2.charAt(r5)
            r7.append(r8)
            int r5 = r5 + 1
        L_0x0086:
            java.lang.String r8 = r7.toString()
            java.lang.String r8 = ad(r8)
            s0 r9 = r10.k
            int r8 = defpackage.cg.i(r8, r9)
            int r9 = r1 + 1
            r0[r1] = r8
            boolean r1 = defpackage.cg.k(r8)
            if (r1 == 0) goto L_0x00a0
            int r9 = r9 + 1
        L_0x00a0:
            r1 = r9
            r7.setLength(r3)
            goto L_0x0041
        L_0x00a5:
            return r0
        L_0x00a6:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "bad method type"
            r10.<init>(r0)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l0.a(l0):int[]");
    }

    public static void ac(int i2) {
        String str;
        if (i2 < 0) {
            str = y2.f("Stack underflow: ", i2);
        } else {
            str = y2.f("Too big stack: ", i2);
        }
        throw new IllegalStateException(str);
    }

    public static String ad(String str) {
        char charAt = str.charAt(0);
        if (charAt == 'F') {
            return str;
        }
        if (charAt == 'L') {
            return str.substring(1, str.length() - 1);
        }
        if (charAt == 'S' || charAt == 'V' || charAt == 'I' || charAt == 'J' || charAt == 'Z' || charAt == '[') {
            return str;
        }
        switch (charAt) {
            case 'B':
            case 'C':
            case 'D':
                return str;
            default:
                throw new IllegalArgumentException("bad descriptor:".concat(str));
        }
    }

    public static int ak(byte[] bArr, int i2, int i3) {
        bArr[i3 + 0] = (byte) (i2 >>> 8);
        bArr[i3 + 1] = (byte) i2;
        return i3 + 2;
    }

    public static int al(byte[] bArr, int i2, int i3) {
        bArr[i3 + 0] = (byte) (i2 >>> 24);
        bArr[i3 + 1] = (byte) (i2 >>> 16);
        bArr[i3 + 2] = (byte) (i2 >>> 8);
        bArr[i3 + 3] = (byte) i2;
        return i3 + 4;
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int an(java.lang.String r15) {
        /*
            int r0 = r15.length()
            r1 = 41
            int r1 = r15.lastIndexOf(r1)
            r2 = 3
            if (r2 > r0) goto L_0x00b2
            r2 = 0
            char r3 = r15.charAt(r2)
            r4 = 40
            if (r3 != r4) goto L_0x00b2
            r3 = 1
            if (r3 > r1) goto L_0x00b2
            int r4 = r1 + 1
            if (r4 >= r0) goto L_0x00b2
            r0 = 1
            r5 = 0
            r6 = 0
        L_0x0020:
            r7 = 91
            r8 = 90
            r9 = 74
            r10 = 73
            r11 = 83
            r12 = 76
            r13 = 70
            if (r0 == r1) goto L_0x0084
            char r14 = r15.charAt(r0)
            if (r14 == r13) goto L_0x007d
            if (r14 == r12) goto L_0x0069
            if (r14 == r11) goto L_0x007d
            if (r14 == r10) goto L_0x007d
            if (r14 == r9) goto L_0x0066
            if (r14 == r8) goto L_0x007d
            if (r14 == r7) goto L_0x0047
            switch(r14) {
                case 66: goto L_0x007d;
                case 67: goto L_0x007d;
                case 68: goto L_0x0066;
                default: goto L_0x0045;
            }
        L_0x0045:
            r0 = 0
            goto L_0x0085
        L_0x0047:
            int r0 = r0 + 1
            char r14 = r15.charAt(r0)
        L_0x004d:
            if (r14 != r7) goto L_0x0056
            int r0 = r0 + 1
            char r14 = r15.charAt(r0)
            goto L_0x004d
        L_0x0056:
            if (r14 == r13) goto L_0x007d
            if (r14 == r12) goto L_0x0069
            if (r14 == r11) goto L_0x007d
            if (r14 == r8) goto L_0x007d
            if (r14 == r10) goto L_0x007d
            if (r14 == r9) goto L_0x007d
            switch(r14) {
                case 66: goto L_0x007d;
                case 67: goto L_0x007d;
                case 68: goto L_0x007d;
                default: goto L_0x0065;
            }
        L_0x0065:
            goto L_0x0045
        L_0x0066:
            int r5 = r5 + -1
            goto L_0x007d
        L_0x0069:
            int r5 = r5 + -1
            int r6 = r6 + 1
            int r0 = r0 + r3
            r14 = 59
            int r14 = r15.indexOf(r14, r0)
            int r0 = r0 + r3
            if (r0 > r14) goto L_0x0045
            if (r14 < r1) goto L_0x007a
            goto L_0x0045
        L_0x007a:
            int r0 = r14 + 1
            goto L_0x0020
        L_0x007d:
            int r5 = r5 + -1
            int r6 = r6 + 1
            int r0 = r0 + 1
            goto L_0x0020
        L_0x0084:
            r0 = 1
        L_0x0085:
            if (r0 == 0) goto L_0x00b2
            char r1 = r15.charAt(r4)
            if (r1 == r13) goto L_0x00a6
            if (r1 == r12) goto L_0x00a6
            if (r1 == r11) goto L_0x00a6
            r4 = 86
            if (r1 == r4) goto L_0x00a4
            if (r1 == r10) goto L_0x00a6
            if (r1 == r9) goto L_0x00a1
            if (r1 == r8) goto L_0x00a6
            if (r1 == r7) goto L_0x00a6
            switch(r1) {
                case 66: goto L_0x00a6;
                case 67: goto L_0x00a6;
                case 68: goto L_0x00a1;
                default: goto L_0x00a0;
            }
        L_0x00a0:
            goto L_0x00a8
        L_0x00a1:
            int r5 = r5 + 1
            goto L_0x00a6
        L_0x00a4:
            r2 = r0
            goto L_0x00a8
        L_0x00a6:
            int r5 = r5 + r3
            goto L_0x00a4
        L_0x00a8:
            if (r2 == 0) goto L_0x00b2
            int r15 = r6 << 16
            r0 = 65535(0xffff, float:9.1834E-41)
            r0 = r0 & r5
            r15 = r15 | r0
            return r15
        L_0x00b2:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Bad parameter signature: "
            java.lang.String r15 = r1.concat(r15)
            r0.<init>(r15)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l0.an(java.lang.String):int");
    }

    public static int ao(int i2) {
        if (i2 == 254 || i2 == 255) {
            return 0;
        }
        switch (i2) {
            case 0:
            case 47:
            case 49:
            case 95:
            case 116:
            case 117:
            case 118:
            case 119:
            case Token.TARGET /*132*/:
            case Token.EXPR_VOID /*134*/:
            case Token.TYPEOFNAME /*138*/:
            case Token.USE_STACK /*139*/:
            case Token.SET_REF_OP /*143*/:
            case Token.COLONCOLON /*145*/:
            case Token.XML /*146*/:
            case Token.DOTQUERY /*147*/:
            case Token.LAST_TOKEN /*167*/:
            case 169:
            case 177:
            case 178:
            case 179:
            case 184:
            case 186:
            case 188:
            case 189:
            case 190:
            case 192:
            case 193:
            case 196:
            case 200:
            case 202:
                return 0;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
            case 12:
            case 13:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 34:
            case 35:
            case 36:
            case 37:
            case 42:
            case 43:
            case 44:
            case 45:
            case 89:
            case 90:
            case 91:
            case Token.LOOP /*133*/:
            case Token.EXPR_RESULT /*135*/:
            case 140:
            case Token.SETELEM_OP /*141*/:
            case 168:
            case 187:
            case 197:
            case 201:
                return 1;
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 22:
            case 24:
            case 30:
            case 31:
            case 32:
            case 33:
            case 38:
            case 39:
            case 40:
            case 41:
            case 92:
            case 93:
            case 94:
                return 2;
            case 46:
            case 48:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 67:
            case 68:
            case 69:
            case 70:
            case 75:
            case 76:
            case 77:
            case 78:
            case 87:
            case 96:
            case 98:
            case 100:
            case 102:
            case 104:
            case 106:
            case 108:
            case 110:
            case 112:
            case 114:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case Token.CATCH /*125*/:
            case Token.FINALLY /*126*/:
            case 128:
            case 130:
            case Token.JSR /*136*/:
            case Token.SCRIPT /*137*/:
            case Token.LOCAL_BLOCK /*142*/:
            case Token.DOTDOT /*144*/:
            case Token.XMLEND /*149*/:
            case 150:
            case Token.SET /*153*/:
            case Token.LET /*154*/:
            case Token.CONST /*155*/:
            case Token.SETCONST /*156*/:
            case Token.SETCONSTVAR /*157*/:
            case Token.ARRAYCOMP /*158*/:
            case Context.VERSION_1_7 /*170*/:
            case 171:
            case 172:
            case 174:
            case 176:
            case Context.VERSION_1_8 /*180*/:
            case 181:
            case 182:
            case 183:
            case 185:
            case 191:
            case 194:
            case 195:
            case 198:
            case 199:
                return -1;
            case 55:
            case 57:
            case 63:
            case 64:
            case 65:
            case 66:
            case 71:
            case 72:
            case 73:
            case 74:
            case 88:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case Token.VOID /*127*/:
            case Token.EMPTY /*129*/:
            case Token.LABEL /*131*/:
            case Token.LETEXPR /*159*/:
            case 160:
            case Token.DEBUGGER /*161*/:
            case Token.COMMENT /*162*/:
            case Token.GENEXPR /*163*/:
            case Token.METHOD /*164*/:
            case Token.ARROW /*165*/:
            case Token.YIELD_STAR /*166*/:
            case 173:
            case 175:
                return -2;
            case 79:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
            case Token.XMLATTR /*148*/:
            case Token.TO_DOUBLE /*151*/:
            case Token.GET /*152*/:
                return -3;
            case 80:
            case 82:
                return -4;
            default:
                throw new IllegalArgumentException(y2.f("Bad opcode: ", i2));
        }
    }

    public final void aa(String str, int i2, int i3, String str2) {
        s0 s0Var = this.k;
        int[] iArr = {s0Var.c(str), s0Var.c(str2), i2, i3};
        if (this.aa == null) {
            this.aa = new ObjArray();
        }
        this.aa.add(iArr);
    }

    public final void ab() {
        int i2 = this.m - 1;
        if (i2 < 0 || 32767 < i2) {
            ac(i2);
            throw null;
        }
        short s2 = (short) i2;
        this.m = s2;
        if (i2 > this.n) {
            this.n = s2;
        }
    }

    public final int ae(int i2) {
        if (i2 < 0) {
            int i3 = i2 & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if (i3 < this.x) {
                return this.w[i3];
            }
            throw new IllegalArgumentException("Bad label");
        }
        throw new IllegalArgumentException("Bad label, no biscuit");
    }

    public final void af(int i2) {
        if (i2 < 0) {
            int i3 = i2 & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if (i3 <= this.x) {
                int[] iArr = this.w;
                if (iArr[i3] == -1) {
                    iArr[i3] = this.j;
                    return;
                }
                throw new IllegalStateException("Can only mark label once");
            }
            throw new IllegalArgumentException("Bad label");
        }
        throw new IllegalArgumentException("Bad label, no biscuit");
    }

    public final void ag(int i2, short s2) {
        af(i2);
        this.m = s2;
    }

    public final void ah(int i2, int i3) {
        w(this.j);
        this.c.put(this.j, i2);
        am(i2, i3, this.j);
    }

    public final void ai(int i2, int i3, int i4) {
        if (i4 < 0 || i4 > this.n) {
            throw new IllegalArgumentException(y2.f("Bad stack index: ", i4));
        }
        this.m = (short) i4;
        w(this.j);
        this.c.put(this.j, i2);
        am(i2, i3, this.j);
    }

    public final void aj(int i2) {
        w(this.j);
        this.c.put(this.j, i2);
        am(i2, -1, this.j);
    }

    public final void am(int i2, int i3, int i4) {
        int i5;
        if (i4 < 0 || (i5 = this.j) < i4) {
            throw new IllegalArgumentException(y2.f("Bad jump target: ", i4));
        } else if (i3 >= -1) {
            int i6 = (~i2) & 3;
            int i7 = i2 + 1 + i6;
            if (i3 >= 0) {
                i7 += (i3 + 3) * 4;
            }
            if (i2 < 0 || ((i5 - 16) - i6) - 1 < i2) {
                throw new IllegalArgumentException(i2 + " is outside a possible range of tableswitch in already generated code");
            }
            byte[] bArr = this.i;
            if ((bArr[i2] & 255) != 170) {
                throw new IllegalArgumentException(i2 + " is not offset of tableswitch statement");
            } else if (i7 < 0 || i5 < i7 + 4) {
                throw new a(y2.f("Too big case index: ", i3));
            } else {
                al(bArr, i4 - i2, i7);
            }
        } else {
            throw new IllegalArgumentException(y2.f("Bad case index: ", i3));
        }
    }

    public final void ap(String str, String str2, short s2) {
        s0 s0Var = this.k;
        this.l = new k0(str, s0Var.c(str), str2, s0Var.c(str2), s2);
        this.c = new UintMap();
        this.p.add(this.l);
        w(0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:136:0x02d4  */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x043d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void aq(short r18) {
        /*
            r17 = this;
            r0 = r17
            k0 r1 = r0.l
            if (r1 == 0) goto L_0x0445
            byte[] r1 = r0.i
            r2 = 0
            r3 = 0
        L_0x000a:
            int r4 = r0.z
            r5 = -1
            if (r3 >= r4) goto L_0x004a
            long[] r4 = r0.y
            r6 = r4[r3]
            r4 = 32
            long r8 = r6 >> r4
            int r4 = (int) r8
            int r7 = (int) r6
            int[] r6 = r0.w
            r4 = r6[r4]
            if (r4 == r5) goto L_0x0042
            r0.w(r4)
            org.mozilla.javascript.UintMap r5 = r0.c
            int r6 = r7 + -1
            r5.put((int) r4, (int) r6)
            int r4 = r4 - r6
            short r5 = (short) r4
            if (r5 != r4) goto L_0x003a
            int r5 = r4 >> 8
            byte r5 = (byte) r5
            r1[r7] = r5
            int r7 = r7 + 1
            byte r4 = (byte) r4
            r1[r7] = r4
            int r3 = r3 + 1
            goto L_0x000a
        L_0x003a:
            l0$a r1 = new l0$a
            java.lang.String r2 = "Program too complex: too big jump offset"
            r1.<init>(r2)
            throw r1
        L_0x0042:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "unlocated label"
            r1.<init>(r2)
            throw r1
        L_0x004a:
            r0.z = r2
            r1 = r18
            r0.o = r1
            s0 r1 = r0.k
            r4 = 1
            boolean r6 = ae
            if (r6 == 0) goto L_0x0195
            if (r6 == 0) goto L_0x009e
            r6 = 0
        L_0x005a:
            int r7 = r0.f
            if (r6 >= r7) goto L_0x006e
            e2[] r7 = r0.e
            r7 = r7[r6]
            int r7 = r7.c
            int r7 = r0.ae(r7)
            r0.w(r7)
            int r6 = r6 + 1
            goto L_0x005a
        L_0x006e:
            int[] r6 = r0.a
            int r7 = r0.b
            java.util.Arrays.sort(r6, r2, r7)
            int[] r6 = r0.a
            r6 = r6[r2]
            r7 = 1
            r8 = 1
        L_0x007b:
            int r9 = r0.b
            if (r7 >= r9) goto L_0x008f
            int[] r9 = r0.a
            r10 = r9[r7]
            if (r6 == r10) goto L_0x008c
            if (r8 == r7) goto L_0x0089
            r9[r8] = r10
        L_0x0089:
            int r8 = r8 + 1
            r6 = r10
        L_0x008c:
            int r7 = r7 + 1
            goto L_0x007b
        L_0x008f:
            r0.b = r8
            int[] r6 = r0.a
            int r7 = r8 + -1
            r6 = r6[r7]
            int r7 = r0.j
            if (r6 != r7) goto L_0x009e
            int r8 = r8 - r4
            r0.b = r8
        L_0x009e:
            l0$b r6 = new l0$b
            r6.<init>()
            int r7 = r0.b
            jc[] r7 = new defpackage.jc[r7]
            r6.g = r7
            int[] r7 = a(r17)
            r8 = 0
        L_0x00ae:
            int r9 = r0.b
            if (r8 >= r9) goto L_0x00cd
            int[] r10 = r0.a
            r11 = r10[r8]
            int r9 = r9 + -1
            if (r8 != r9) goto L_0x00bd
            int r9 = r0.j
            goto L_0x00c1
        L_0x00bd:
            int r9 = r8 + 1
            r9 = r10[r9]
        L_0x00c1:
            jc[] r10 = r6.g
            jc r12 = new jc
            r12.<init>(r7, r8, r11, r9)
            r10[r8] = r12
            int r8 = r8 + 1
            goto L_0x00ae
        L_0x00cd:
            int[] r10 = a(r17)
            jc[] r7 = r6.g
            r9 = r7[r2]
            int r11 = r10.length
            int[] r12 = new int[r2]
            r13 = 0
            s0 r14 = r0.k
            r9.b(r10, r11, r12, r13, r14)
            jc[] r7 = new defpackage.jc[r4]
            jc[] r8 = r6.g
            r8 = r8[r2]
            r7[r2] = r8
            r6.e = r7
            r6.f = r4
            r6.d()
            r7 = 0
        L_0x00ee:
            jc[] r8 = r6.g
            int r9 = r8.length
            if (r7 >= r9) goto L_0x0191
            r10 = r8[r7]
            boolean r8 = r10.f
            if (r8 != 0) goto L_0x018d
            int[] r8 = new int[r2]
            int[] r13 = new int[r4]
            java.lang.String r9 = "java/lang/Throwable"
            short r9 = r1.a(r9)
            int r9 = defpackage.cg.b(r9)
            r13[r2] = r9
            r9 = 0
        L_0x010a:
            int r11 = r0.f
            int r15 = r10.c
            int r14 = r10.b
            if (r9 >= r11) goto L_0x0145
            e2[] r11 = r0.e
            r11 = r11[r9]
            int r12 = r11.a
            int r12 = r0.ae(r12)
            int r3 = r11.b
            int r3 = r0.ae(r3)
            int r11 = r11.c
            int r11 = r0.ae(r11)
            jc r11 = r6.g(r11)
            if (r14 <= r12) goto L_0x0130
            if (r14 < r3) goto L_0x0138
        L_0x0130:
            if (r12 <= r14) goto L_0x0142
            if (r12 >= r15) goto L_0x0142
            boolean r3 = r11.f
            if (r3 == 0) goto L_0x0142
        L_0x0138:
            int[] r3 = r11.d
            int r8 = r3.length
            int[] r8 = new int[r8]
            int r9 = r3.length
            java.lang.System.arraycopy(r3, r2, r8, r2, r9)
            goto L_0x0145
        L_0x0142:
            int r9 = r9 + 1
            goto L_0x010a
        L_0x0145:
            r11 = r8
            r3 = 0
        L_0x0147:
            int r8 = r0.f
            if (r3 >= r8) goto L_0x0170
            e2[] r8 = r0.e
            r8 = r8[r3]
            int r8 = r8.a
            int r8 = r0.ae(r8)
            if (r8 != r14) goto L_0x016e
            int r8 = r3 + 1
        L_0x0159:
            int r9 = r0.f
            if (r8 >= r9) goto L_0x0168
            e2[] r9 = r0.e
            int r12 = r8 + -1
            r16 = r9[r8]
            r9[r12] = r16
            int r8 = r8 + 1
            goto L_0x0159
        L_0x0168:
            int r9 = r9 + -1
            r0.f = r9
            int r3 = r3 + -1
        L_0x016e:
            int r3 = r3 + r4
            goto L_0x0147
        L_0x0170:
            int r12 = r11.length
            s0 r3 = r0.k
            r8 = 1
            r9 = r14
            r14 = r8
            r8 = r15
            r15 = r3
            r10.b(r11, r12, r13, r14, r15)
            int r15 = r8 + -1
            byte[] r3 = r0.i
            r8 = -65
            r3[r15] = r8
            r14 = r9
        L_0x0184:
            if (r14 >= r15) goto L_0x018d
            byte[] r3 = r0.i
            r3[r14] = r2
            int r14 = r14 + 1
            goto L_0x0184
        L_0x018d:
            int r7 = r7 + 1
            goto L_0x00ee
        L_0x0191:
            r6.d()
            goto L_0x0196
        L_0x0195:
            r6 = 0
        L_0x0196:
            int[] r3 = r0.g
            if (r3 == 0) goto L_0x01a1
            int r3 = r0.h
            int r3 = r3 * 4
            int r3 = r3 + 8
            goto L_0x01a2
        L_0x01a1:
            r3 = 0
        L_0x01a2:
            org.mozilla.javascript.ObjArray r7 = r0.aa
            if (r7 == 0) goto L_0x01af
            int r7 = r7.size()
            int r7 = r7 * 10
            int r7 = r7 + 8
            goto L_0x01b0
        L_0x01af:
            r7 = 0
        L_0x01b0:
            r8 = 3
            if (r6 == 0) goto L_0x02c0
            jc[] r10 = r6.g
            int r11 = r10.length
            int r11 = r11 + r5
            l0 r12 = defpackage.l0.this
            short r13 = r12.o
            int r13 = r13 * 3
            int r13 = r13 + 7
            short r12 = r12.n
            int r12 = r12 * 3
            int r12 = r12 + r13
            int r12 = r12 * r11
            byte[] r11 = new byte[r12]
            r6.h = r11
            r10 = r10[r2]
            int[] r10 = r10.a()
            r11 = 1
            r12 = -1
        L_0x01d2:
            jc[] r13 = r6.g
            int r14 = r13.length
            if (r11 >= r14) goto L_0x02b7
            r13 = r13[r11]
            int[] r14 = r13.a()
            int[] r15 = r13.e
            int r5 = r15.length
            int[] r9 = new int[r5]
            int r8 = r15.length
            java.lang.System.arraycopy(r15, r2, r9, r2, r8)
            int r8 = r13.b
            int r12 = r8 - r12
            int r12 = r12 - r4
            if (r5 != 0) goto L_0x0272
            int r5 = r10.length
            int r15 = r14.length
            if (r5 <= r15) goto L_0x01f3
            int r5 = r14.length
            goto L_0x01f4
        L_0x01f3:
            int r5 = r10.length
        L_0x01f4:
            int r15 = r10.length
            int r2 = r14.length
            int r15 = r15 - r2
            int r2 = java.lang.Math.abs(r15)
            r15 = 0
        L_0x01fc:
            if (r15 >= r5) goto L_0x0209
            r4 = r10[r15]
            r13 = r14[r15]
            if (r4 == r13) goto L_0x0205
            goto L_0x0209
        L_0x0205:
            int r15 = r15 + 1
            r4 = 1
            goto L_0x01fc
        L_0x0209:
            int r4 = r14.length
            if (r15 != r4) goto L_0x0232
            if (r2 != 0) goto L_0x0232
            r4 = 63
            if (r12 > r4) goto L_0x021f
            byte[] r2 = r6.h
            int r4 = r6.i
            int r5 = r4 + 1
            r6.i = r5
            byte r5 = (byte) r12
            r2[r4] = r5
            goto L_0x02ad
        L_0x021f:
            byte[] r2 = r6.h
            int r4 = r6.i
            int r5 = r4 + 1
            r6.i = r5
            r9 = -5
            r2[r4] = r9
            int r2 = ak(r2, r12, r5)
            r6.i = r2
            goto L_0x02ad
        L_0x0232:
            int r4 = r14.length
            if (r15 != r4) goto L_0x024c
            r4 = 3
            if (r2 > r4) goto L_0x024c
            byte[] r4 = r6.h
            int r5 = r6.i
            int r9 = r5 + 1
            r6.i = r9
            int r2 = 251 - r2
            byte r2 = (byte) r2
            r4[r5] = r2
            int r2 = ak(r4, r12, r9)
            r6.i = r2
            goto L_0x02ad
        L_0x024c:
            int r4 = r10.length
            if (r15 != r4) goto L_0x026e
            r4 = 3
            if (r2 > r4) goto L_0x026e
            int r4 = r14.length
            int r4 = r4 - r2
            byte[] r5 = r6.h
            int r9 = r6.i
            int r10 = r9 + 1
            r6.i = r10
            int r2 = r2 + 251
            byte r2 = (byte) r2
            r5[r9] = r2
            int r2 = ak(r5, r12, r10)
            r6.i = r2
            int r2 = r6.n(r14, r4)
            r6.i = r2
            goto L_0x02ad
        L_0x026e:
            r6.l(r14, r9, r12)
            goto L_0x02ad
        L_0x0272:
            r2 = 1
            if (r5 != r2) goto L_0x02aa
            boolean r2 = java.util.Arrays.equals(r10, r14)
            if (r2 == 0) goto L_0x02a6
            r2 = 63
            if (r12 > r2) goto L_0x028d
            byte[] r2 = r6.h
            int r4 = r6.i
            int r5 = r4 + 1
            r6.i = r5
            int r12 = r12 + 64
            byte r5 = (byte) r12
            r2[r4] = r5
            goto L_0x029f
        L_0x028d:
            byte[] r2 = r6.h
            int r4 = r6.i
            int r5 = r4 + 1
            r6.i = r5
            r10 = -9
            r2[r4] = r10
            int r2 = ak(r2, r12, r5)
            r6.i = r2
        L_0x029f:
            r2 = 0
            r4 = r9[r2]
            r6.m(r4)
            goto L_0x02ad
        L_0x02a6:
            r6.l(r14, r9, r12)
            goto L_0x02ad
        L_0x02aa:
            r6.l(r14, r9, r12)
        L_0x02ad:
            int r11 = r11 + 1
            r12 = r8
            r10 = r14
            r2 = 0
            r4 = 1
            r5 = -1
            r8 = 3
            goto L_0x01d2
        L_0x02b7:
            int r2 = r6.i
            r4 = 2
            int r2 = r2 + r4
            if (r2 <= 0) goto L_0x02c1
            int r2 = r2 + 6
            goto L_0x02c2
        L_0x02c0:
            r4 = 2
        L_0x02c1:
            r2 = 0
        L_0x02c2:
            int r5 = r0.j
            int r5 = r5 + 14
            int r5 = r5 + r4
            int r8 = r0.f
            int r8 = r8 * 8
            int r8 = r8 + r5
            int r8 = r8 + r4
            int r8 = r8 + r3
            int r8 = r8 + r7
            int r8 = r8 + r2
            r3 = 65536(0x10000, float:9.18355E-41)
            if (r8 > r3) goto L_0x043d
            byte[] r3 = new byte[r8]
            java.lang.String r4 = "Code"
            short r4 = r1.c(r4)
            r5 = 0
            int r4 = ak(r3, r4, r5)
            int r8 = r8 + -6
            int r4 = al(r3, r8, r4)
            short r5 = r0.n
            int r4 = ak(r3, r5, r4)
            short r5 = r0.o
            int r4 = ak(r3, r5, r4)
            int r5 = r0.j
            int r4 = al(r3, r5, r4)
            byte[] r5 = r0.i
            int r7 = r0.j
            r8 = 0
            java.lang.System.arraycopy(r5, r8, r3, r4, r7)
            int r5 = r0.j
            int r4 = r4 + r5
            int r5 = r0.f
            if (r5 <= 0) goto L_0x035b
            int r4 = ak(r3, r5, r4)
            r5 = 0
        L_0x030d:
            int r7 = r0.f
            if (r5 >= r7) goto L_0x0360
            e2[] r7 = r0.e
            r7 = r7[r5]
            int r8 = r7.a
            int r8 = r0.ae(r8)
            int r9 = r7.b
            int r9 = r0.ae(r9)
            int r10 = r7.c
            int r10 = r0.ae(r10)
            r11 = -1
            if (r8 == r11) goto L_0x0353
            if (r9 == r11) goto L_0x034b
            if (r10 == r11) goto L_0x0343
            int r4 = ak(r3, r8, r4)
            int r4 = ak(r3, r9, r4)
            int r4 = ak(r3, r10, r4)
            short r7 = r7.d
            int r4 = ak(r3, r7, r4)
            int r5 = r5 + 1
            goto L_0x030d
        L_0x0343:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "handler label not defined"
            r1.<init>(r2)
            throw r1
        L_0x034b:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "end label not defined"
            r1.<init>(r2)
            throw r1
        L_0x0353:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "start label not defined"
            r1.<init>(r2)
            throw r1
        L_0x035b:
            r5 = 0
            int r4 = ak(r3, r5, r4)
        L_0x0360:
            int[] r5 = r0.g
            if (r5 == 0) goto L_0x0366
            r5 = 1
            goto L_0x0367
        L_0x0366:
            r5 = 0
        L_0x0367:
            org.mozilla.javascript.ObjArray r7 = r0.aa
            if (r7 == 0) goto L_0x036d
            int r5 = r5 + 1
        L_0x036d:
            if (r2 <= 0) goto L_0x0371
            int r5 = r5 + 1
        L_0x0371:
            int r4 = ak(r3, r5, r4)
            int[] r5 = r0.g
            if (r5 == 0) goto L_0x03a3
            java.lang.String r5 = "LineNumberTable"
            short r5 = r1.c(r5)
            int r4 = ak(r3, r5, r4)
            int r5 = r0.h
            int r5 = r5 * 4
            r7 = 2
            int r5 = r5 + r7
            int r4 = al(r3, r5, r4)
            int r5 = r0.h
            int r4 = ak(r3, r5, r4)
            r5 = 0
        L_0x0394:
            int r7 = r0.h
            if (r5 >= r7) goto L_0x03a3
            int[] r7 = r0.g
            r7 = r7[r5]
            int r4 = al(r3, r7, r4)
            int r5 = r5 + 1
            goto L_0x0394
        L_0x03a3:
            org.mozilla.javascript.ObjArray r5 = r0.aa
            if (r5 == 0) goto L_0x03f6
            java.lang.String r5 = "LocalVariableTable"
            short r5 = r1.c(r5)
            int r4 = ak(r3, r5, r4)
            org.mozilla.javascript.ObjArray r5 = r0.aa
            int r5 = r5.size()
            int r7 = r5 * 10
            r8 = 2
            int r7 = r7 + r8
            int r4 = al(r3, r7, r4)
            int r4 = ak(r3, r5, r4)
            r7 = r4
            r4 = 0
        L_0x03c5:
            if (r4 >= r5) goto L_0x03f5
            org.mozilla.javascript.ObjArray r9 = r0.aa
            java.lang.Object r9 = r9.get(r4)
            int[] r9 = (int[]) r9
            r10 = 0
            r11 = r9[r10]
            r10 = 1
            r12 = r9[r10]
            r13 = r9[r8]
            r8 = 3
            r9 = r9[r8]
            int r14 = r0.j
            int r14 = r14 - r13
            int r7 = ak(r3, r13, r7)
            int r7 = ak(r3, r14, r7)
            int r7 = ak(r3, r11, r7)
            int r7 = ak(r3, r12, r7)
            int r7 = ak(r3, r9, r7)
            int r4 = r4 + 1
            r8 = 2
            goto L_0x03c5
        L_0x03f5:
            r4 = r7
        L_0x03f6:
            if (r2 <= 0) goto L_0x041c
            java.lang.String r2 = "StackMapTable"
            short r1 = r1.c(r2)
            int r1 = ak(r3, r1, r4)
            int r2 = r6.i
            r4 = 2
            int r2 = r2 + r4
            int r1 = al(r3, r2, r1)
            jc[] r2 = r6.g
            int r2 = r2.length
            r4 = -1
            int r2 = r2 + r4
            int r1 = ak(r3, r2, r1)
            byte[] r2 = r6.h
            int r4 = r6.i
            r5 = 0
            java.lang.System.arraycopy(r2, r5, r3, r1, r4)
            goto L_0x041d
        L_0x041c:
            r5 = 0
        L_0x041d:
            k0 r1 = r0.l
            r1.f = r3
            r1 = 0
            r0.e = r1
            r0.f = r5
            r0.h = r5
            r0.j = r5
            r0.l = r1
            r0.n = r5
            r0.m = r5
            r0.x = r5
            r0.z = r5
            r0.aa = r1
            r0.a = r1
            r0.b = r5
            r0.c = r1
            return
        L_0x043d:
            l0$a r1 = new l0$a
            java.lang.String r2 = "generated bytecode for method exceeds 64K limit."
            r1.<init>(r2)
            throw r1
        L_0x0445:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "No method to stop"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l0.aq(short):void");
    }

    public final byte[] ar() {
        int i2;
        short s2;
        ObjArray objArray;
        ObjArray objArray2;
        s0 s0Var = this.k;
        short s3 = this.v;
        if (s3 != 0) {
            s2 = s0Var.c("SourceFile");
            i2 = 1;
        } else {
            s2 = 0;
            i2 = 0;
        }
        if (s3 != 0) {
            s0Var.c("SourceFile");
        }
        ObjArray objArray3 = this.r;
        int size = (objArray3.size() * 2) + s0Var.g + 2 + 8 + 2 + 2 + 2 + 2 + 2;
        int i3 = 0;
        while (true) {
            objArray = this.q;
            if (i3 >= objArray.size()) {
                break;
            }
            ((j0) objArray.get(i3)).getClass();
            size += 8;
            i3++;
        }
        int i4 = size + 2;
        int i5 = 0;
        while (true) {
            objArray2 = this.p;
            if (i5 >= objArray2.size()) {
                break;
            }
            i4 += ((k0) objArray2.get(i5)).f.length + 8;
            i5++;
        }
        int i6 = i4 + 2;
        if (s3 != 0) {
            i6 = i6 + 2 + 4 + 2;
        }
        byte[] bArr = new byte[i6];
        int ak = ak(bArr, (short) s0Var.h, ak(bArr, ac, ak(bArr, ad, al(bArr, -889275714, 0))));
        System.arraycopy(s0Var.k, 0, bArr, ak, s0Var.g);
        int ak2 = ak(bArr, objArray3.size(), ak(bArr, this.u, ak(bArr, this.t, ak(bArr, this.s, ak + s0Var.g))));
        for (int i7 = 0; i7 < objArray3.size(); i7++) {
            ak2 = ak(bArr, ((Short) objArray3.get(i7)).shortValue(), ak2);
        }
        int ak3 = ak(bArr, objArray.size(), ak2);
        for (int i8 = 0; i8 < objArray.size(); i8++) {
            j0 j0Var = (j0) objArray.get(i8);
            ak3 = ak(bArr, 0, ak(bArr, j0Var.b, ak(bArr, j0Var.a, ak(bArr, j0Var.c, ak3))));
        }
        int ak4 = ak(bArr, objArray2.size(), ak3);
        for (int i9 = 0; i9 < objArray2.size(); i9++) {
            k0 k0Var = (k0) objArray2.get(i9);
            int ak5 = ak(bArr, 1, ak(bArr, k0Var.d, ak(bArr, k0Var.c, ak(bArr, k0Var.e, ak4))));
            byte[] bArr2 = k0Var.f;
            System.arraycopy(bArr2, 0, bArr, ak5, bArr2.length);
            ak4 = ak5 + k0Var.f.length;
        }
        int ak6 = ak(bArr, i2, ak4);
        if (s3 != 0) {
            ak6 = ak(bArr, s3, al(bArr, 2, ak(bArr, s2, ak6)));
        }
        if (ak6 == i6) {
            return bArr;
        }
        throw new RuntimeException();
    }

    public final void as(int i2, int i3, int i4) {
        if (i4 == 0) {
            c(i2);
        } else if (i4 == 1) {
            c(i2 + 1);
        } else if (i4 == 2) {
            c(i2 + 2);
        } else if (i4 != 3) {
            d(i3, i4);
        } else {
            c(i2 + 3);
        }
    }

    public final int b() {
        int i2 = this.x;
        int[] iArr = this.w;
        if (iArr == null || i2 == iArr.length) {
            if (iArr == null) {
                this.w = new int[32];
            } else {
                int[] iArr2 = new int[(iArr.length * 2)];
                System.arraycopy(iArr, 0, iArr2, 0, i2);
                this.w = iArr2;
            }
        }
        this.x = i2 + 1;
        this.w[i2] = -1;
        return i2 | Integer.MIN_VALUE;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
        r0 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
        r0 = 2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void c(int r4) {
        /*
            r3 = this;
            r0 = 254(0xfe, float:3.56E-43)
            if (r4 == r0) goto L_0x0020
            r0 = 255(0xff, float:3.57E-43)
            if (r4 == r0) goto L_0x0020
            switch(r4) {
                case 0: goto L_0x0020;
                case 1: goto L_0x0020;
                case 2: goto L_0x0020;
                case 3: goto L_0x0020;
                case 4: goto L_0x0020;
                case 5: goto L_0x0020;
                case 6: goto L_0x0020;
                case 7: goto L_0x0020;
                case 8: goto L_0x0020;
                case 9: goto L_0x0020;
                case 10: goto L_0x0020;
                case 11: goto L_0x0020;
                case 12: goto L_0x0020;
                case 13: goto L_0x0020;
                case 14: goto L_0x0020;
                case 15: goto L_0x0020;
                case 16: goto L_0x001e;
                case 17: goto L_0x001e;
                case 18: goto L_0x001e;
                case 19: goto L_0x001e;
                case 20: goto L_0x001e;
                case 21: goto L_0x001e;
                case 22: goto L_0x001e;
                case 23: goto L_0x001e;
                case 24: goto L_0x001e;
                case 25: goto L_0x001e;
                case 26: goto L_0x0020;
                case 27: goto L_0x0020;
                case 28: goto L_0x0020;
                case 29: goto L_0x0020;
                case 30: goto L_0x0020;
                case 31: goto L_0x0020;
                case 32: goto L_0x0020;
                case 33: goto L_0x0020;
                case 34: goto L_0x0020;
                case 35: goto L_0x0020;
                case 36: goto L_0x0020;
                case 37: goto L_0x0020;
                case 38: goto L_0x0020;
                case 39: goto L_0x0020;
                case 40: goto L_0x0020;
                case 41: goto L_0x0020;
                case 42: goto L_0x0020;
                case 43: goto L_0x0020;
                case 44: goto L_0x0020;
                case 45: goto L_0x0020;
                case 46: goto L_0x0020;
                case 47: goto L_0x0020;
                case 48: goto L_0x0020;
                case 49: goto L_0x0020;
                case 50: goto L_0x0020;
                case 51: goto L_0x0020;
                case 52: goto L_0x0020;
                case 53: goto L_0x0020;
                case 54: goto L_0x001e;
                case 55: goto L_0x001e;
                case 56: goto L_0x001e;
                case 57: goto L_0x001e;
                case 58: goto L_0x001e;
                case 59: goto L_0x0020;
                case 60: goto L_0x0020;
                case 61: goto L_0x0020;
                case 62: goto L_0x0020;
                case 63: goto L_0x0020;
                case 64: goto L_0x0020;
                case 65: goto L_0x0020;
                case 66: goto L_0x0020;
                case 67: goto L_0x0020;
                case 68: goto L_0x0020;
                case 69: goto L_0x0020;
                case 70: goto L_0x0020;
                case 71: goto L_0x0020;
                case 72: goto L_0x0020;
                case 73: goto L_0x0020;
                case 74: goto L_0x0020;
                case 75: goto L_0x0020;
                case 76: goto L_0x0020;
                case 77: goto L_0x0020;
                case 78: goto L_0x0020;
                case 79: goto L_0x0020;
                case 80: goto L_0x0020;
                case 81: goto L_0x0020;
                case 82: goto L_0x0020;
                case 83: goto L_0x0020;
                case 84: goto L_0x0020;
                case 85: goto L_0x0020;
                case 86: goto L_0x0020;
                case 87: goto L_0x0020;
                case 88: goto L_0x0020;
                case 89: goto L_0x0020;
                case 90: goto L_0x0020;
                case 91: goto L_0x0020;
                case 92: goto L_0x0020;
                case 93: goto L_0x0020;
                case 94: goto L_0x0020;
                case 95: goto L_0x0020;
                case 96: goto L_0x0020;
                case 97: goto L_0x0020;
                case 98: goto L_0x0020;
                case 99: goto L_0x0020;
                case 100: goto L_0x0020;
                case 101: goto L_0x0020;
                case 102: goto L_0x0020;
                case 103: goto L_0x0020;
                case 104: goto L_0x0020;
                case 105: goto L_0x0020;
                case 106: goto L_0x0020;
                case 107: goto L_0x0020;
                case 108: goto L_0x0020;
                case 109: goto L_0x0020;
                case 110: goto L_0x0020;
                case 111: goto L_0x0020;
                case 112: goto L_0x0020;
                case 113: goto L_0x0020;
                case 114: goto L_0x0020;
                case 115: goto L_0x0020;
                case 116: goto L_0x0020;
                case 117: goto L_0x0020;
                case 118: goto L_0x0020;
                case 119: goto L_0x0020;
                case 120: goto L_0x0020;
                case 121: goto L_0x0020;
                case 122: goto L_0x0020;
                case 123: goto L_0x0020;
                case 124: goto L_0x0020;
                case 125: goto L_0x0020;
                case 126: goto L_0x0020;
                case 127: goto L_0x0020;
                case 128: goto L_0x0020;
                case 129: goto L_0x0020;
                case 130: goto L_0x0020;
                case 131: goto L_0x0020;
                case 132: goto L_0x001c;
                case 133: goto L_0x0020;
                case 134: goto L_0x0020;
                case 135: goto L_0x0020;
                case 136: goto L_0x0020;
                case 137: goto L_0x0020;
                case 138: goto L_0x0020;
                case 139: goto L_0x0020;
                case 140: goto L_0x0020;
                case 141: goto L_0x0020;
                case 142: goto L_0x0020;
                case 143: goto L_0x0020;
                case 144: goto L_0x0020;
                case 145: goto L_0x0020;
                case 146: goto L_0x0020;
                case 147: goto L_0x0020;
                case 148: goto L_0x0020;
                case 149: goto L_0x0020;
                case 150: goto L_0x0020;
                case 151: goto L_0x0020;
                case 152: goto L_0x0020;
                case 153: goto L_0x001e;
                case 154: goto L_0x001e;
                case 155: goto L_0x001e;
                case 156: goto L_0x001e;
                case 157: goto L_0x001e;
                case 158: goto L_0x001e;
                case 159: goto L_0x001e;
                case 160: goto L_0x001e;
                case 161: goto L_0x001e;
                case 162: goto L_0x001e;
                case 163: goto L_0x001e;
                case 164: goto L_0x001e;
                case 165: goto L_0x001e;
                case 166: goto L_0x001e;
                case 167: goto L_0x001e;
                case 168: goto L_0x001e;
                case 169: goto L_0x001e;
                case 170: goto L_0x001a;
                case 171: goto L_0x001a;
                case 172: goto L_0x0020;
                case 173: goto L_0x0020;
                case 174: goto L_0x0020;
                case 175: goto L_0x0020;
                case 176: goto L_0x0020;
                case 177: goto L_0x0020;
                case 178: goto L_0x001e;
                case 179: goto L_0x001e;
                case 180: goto L_0x001e;
                case 181: goto L_0x001e;
                case 182: goto L_0x001e;
                case 183: goto L_0x001e;
                case 184: goto L_0x001e;
                case 185: goto L_0x001e;
                default: goto L_0x000b;
            }
        L_0x000b:
            switch(r4) {
                case 187: goto L_0x001e;
                case 188: goto L_0x001e;
                case 189: goto L_0x001e;
                case 190: goto L_0x0020;
                case 191: goto L_0x0020;
                case 192: goto L_0x001e;
                case 193: goto L_0x001e;
                case 194: goto L_0x0020;
                case 195: goto L_0x0020;
                case 196: goto L_0x0020;
                case 197: goto L_0x001c;
                case 198: goto L_0x001e;
                case 199: goto L_0x001e;
                case 200: goto L_0x001e;
                case 201: goto L_0x001e;
                case 202: goto L_0x0020;
                default: goto L_0x000e;
            }
        L_0x000e:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Bad opcode: "
            java.lang.String r4 = defpackage.y2.f(r1, r4)
            r0.<init>(r4)
            throw r0
        L_0x001a:
            r0 = -1
            goto L_0x0021
        L_0x001c:
            r0 = 2
            goto L_0x0021
        L_0x001e:
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            if (r0 != 0) goto L_0x004b
            short r0 = r3.m
            int r1 = ao(r4)
            int r1 = r1 + r0
            if (r1 < 0) goto L_0x0046
            r0 = 32767(0x7fff, float:4.5916E-41)
            if (r0 < r1) goto L_0x0046
            r3.y(r4)
            short r0 = (short) r1
            r3.m = r0
            short r2 = r3.n
            if (r1 <= r2) goto L_0x003c
            r3.n = r0
        L_0x003c:
            r0 = 191(0xbf, float:2.68E-43)
            if (r4 != r0) goto L_0x0045
            int r4 = r3.j
            r3.w(r4)
        L_0x0045:
            return
        L_0x0046:
            ac(r1)
            r4 = 0
            throw r4
        L_0x004b:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Unexpected operands"
            r4.<init>(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l0.c(int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0041, code lost:
        if (r10 < 0) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        if (65536 <= r10) goto L_0x005c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0045, code lost:
        if (r10 < 256) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0047, code lost:
        y(196);
        y(r9);
        z(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0054, code lost:
        y(r9);
        y(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0063, code lost:
        throw new defpackage.l0.a("out of range variable");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void d(int r9, int r10) {
        /*
            r8 = this;
            short r0 = r8.m
            int r1 = ao(r9)
            int r1 = r1 + r0
            if (r1 < 0) goto L_0x0170
            r0 = 32767(0x7fff, float:4.5916E-41)
            if (r0 < r1) goto L_0x0170
            r0 = 180(0xb4, float:2.52E-43)
            r2 = 65536(0x10000, float:9.18355E-41)
            if (r9 == r0) goto L_0x0154
            r0 = 181(0xb5, float:2.54E-43)
            if (r9 == r0) goto L_0x0154
            r0 = 188(0xbc, float:2.63E-43)
            java.lang.String r3 = "out of range index"
            r4 = 256(0x100, float:3.59E-43)
            if (r9 == r0) goto L_0x0143
            r0 = 198(0xc6, float:2.77E-43)
            if (r9 == r0) goto L_0x00b7
            r0 = 199(0xc7, float:2.79E-43)
            if (r9 == r0) goto L_0x00b7
            switch(r9) {
                case 16: goto L_0x00a4;
                case 17: goto L_0x0091;
                case 18: goto L_0x0064;
                case 19: goto L_0x0064;
                case 20: goto L_0x0064;
                case 21: goto L_0x0041;
                case 22: goto L_0x0041;
                case 23: goto L_0x0041;
                case 24: goto L_0x0041;
                case 25: goto L_0x0041;
                default: goto L_0x002a;
            }
        L_0x002a:
            switch(r9) {
                case 54: goto L_0x0041;
                case 55: goto L_0x0041;
                case 56: goto L_0x0041;
                case 57: goto L_0x0041;
                case 58: goto L_0x0041;
                default: goto L_0x002d;
            }
        L_0x002d:
            switch(r9) {
                case 153: goto L_0x00b7;
                case 154: goto L_0x00b7;
                case 155: goto L_0x00b7;
                case 156: goto L_0x00b7;
                case 157: goto L_0x00b7;
                case 158: goto L_0x00b7;
                case 159: goto L_0x00b7;
                case 160: goto L_0x00b7;
                case 161: goto L_0x00b7;
                case 162: goto L_0x00b7;
                case 163: goto L_0x00b7;
                case 164: goto L_0x00b7;
                case 165: goto L_0x00b7;
                case 166: goto L_0x00b7;
                case 167: goto L_0x0038;
                case 168: goto L_0x00b7;
                case 169: goto L_0x0041;
                default: goto L_0x0030;
            }
        L_0x0030:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Unexpected opcode for 1 operand"
            r9.<init>(r10)
            throw r9
        L_0x0038:
            int r0 = r8.j
            int r0 = r0 + 3
            r8.w(r0)
            goto L_0x00b7
        L_0x0041:
            if (r10 < 0) goto L_0x005c
            if (r2 <= r10) goto L_0x005c
            if (r10 < r4) goto L_0x0054
            r0 = 196(0xc4, float:2.75E-43)
            r8.y(r0)
            r8.y(r9)
            r8.z(r10)
            goto L_0x015e
        L_0x0054:
            r8.y(r9)
            r8.y(r10)
            goto L_0x015e
        L_0x005c:
            l0$a r9 = new l0$a
            java.lang.String r10 = "out of range variable"
            r9.<init>(r10)
            throw r9
        L_0x0064:
            if (r10 < 0) goto L_0x008b
            if (r10 >= r2) goto L_0x008b
            r0 = 19
            if (r10 >= r4) goto L_0x007b
            if (r9 == r0) goto L_0x007b
            r2 = 20
            if (r9 != r2) goto L_0x0073
            goto L_0x007b
        L_0x0073:
            r8.y(r9)
            r8.y(r10)
            goto L_0x015e
        L_0x007b:
            r2 = 18
            if (r9 != r2) goto L_0x0083
            r8.y(r0)
            goto L_0x0086
        L_0x0083:
            r8.y(r9)
        L_0x0086:
            r8.z(r10)
            goto L_0x015e
        L_0x008b:
            l0$a r9 = new l0$a
            r9.<init>(r3)
            throw r9
        L_0x0091:
            short r0 = (short) r10
            if (r0 != r10) goto L_0x009c
            r8.y(r9)
            r8.z(r10)
            goto L_0x015e
        L_0x009c:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "out of range short"
            r9.<init>(r10)
            throw r9
        L_0x00a4:
            byte r0 = (byte) r10
            if (r0 != r10) goto L_0x00af
            r8.y(r9)
            r8.y(r0)
            goto L_0x015e
        L_0x00af:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "out of range byte"
            r9.<init>(r10)
            throw r9
        L_0x00b7:
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = r10 & r0
            if (r2 == r0) goto L_0x00cd
            if (r10 < 0) goto L_0x00c5
            r3 = 65535(0xffff, float:9.1834E-41)
            if (r10 > r3) goto L_0x00c5
            goto L_0x00cd
        L_0x00c5:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Bad label for branch"
            r9.<init>(r10)
            throw r9
        L_0x00cd:
            int r3 = r8.j
            r8.y(r9)
            if (r2 == r0) goto L_0x00e2
            r8.z(r10)
            int r10 = r10 + r3
            r8.w(r10)
            org.mozilla.javascript.UintMap r9 = r8.c
            r9.put((int) r10, (int) r3)
            goto L_0x015e
        L_0x00e2:
            int r9 = r8.ae(r10)
            r0 = -1
            if (r9 == r0) goto L_0x00f7
            int r10 = r9 - r3
            r8.z(r10)
            r8.w(r9)
            org.mozilla.javascript.UintMap r10 = r8.c
            r10.put((int) r9, (int) r3)
            goto L_0x015e
        L_0x00f7:
            int r3 = r3 + 1
            if (r10 >= 0) goto L_0x013b
            r9 = 2147483647(0x7fffffff, float:NaN)
            r9 = r9 & r10
            int r10 = r8.x
            if (r9 >= r10) goto L_0x0133
            int r10 = r8.z
            long[] r0 = r8.y
            r2 = 0
            if (r0 == 0) goto L_0x010d
            int r4 = r0.length
            if (r10 != r4) goto L_0x0120
        L_0x010d:
            if (r0 != 0) goto L_0x0116
            r0 = 40
            long[] r0 = new long[r0]
            r8.y = r0
            goto L_0x0120
        L_0x0116:
            int r4 = r0.length
            int r4 = r4 * 2
            long[] r4 = new long[r4]
            java.lang.System.arraycopy(r0, r2, r4, r2, r10)
            r8.y = r4
        L_0x0120:
            int r0 = r10 + 1
            r8.z = r0
            long[] r0 = r8.y
            long r4 = (long) r9
            r9 = 32
            long r4 = r4 << r9
            long r6 = (long) r3
            long r3 = r6 | r4
            r0[r10] = r3
            r8.z(r2)
            goto L_0x015e
        L_0x0133:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Bad label"
            r9.<init>(r10)
            throw r9
        L_0x013b:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "Bad label, no biscuit"
            r9.<init>(r10)
            throw r9
        L_0x0143:
            if (r10 < 0) goto L_0x014e
            if (r10 >= r4) goto L_0x014e
            r8.y(r9)
            r8.y(r10)
            goto L_0x015e
        L_0x014e:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r3)
            throw r9
        L_0x0154:
            if (r10 < 0) goto L_0x0168
            if (r10 >= r2) goto L_0x0168
            r8.y(r9)
            r8.z(r10)
        L_0x015e:
            short r9 = (short) r1
            r8.m = r9
            short r10 = r8.n
            if (r1 <= r10) goto L_0x0167
            r8.n = r9
        L_0x0167:
            return
        L_0x0168:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r10 = "out of range field"
            r9.<init>(r10)
            throw r9
        L_0x0170:
            ac(r1)
            r9 = 0
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l0.d(int, int):void");
    }

    public final void e(int i2, String str) {
        int ao = ao(i2) + this.m;
        if (ao < 0 || 32767 < ao) {
            ac(ao);
            throw null;
        } else if (i2 == 187 || i2 == 189 || i2 == 192 || i2 == 193) {
            short a2 = this.k.a(str);
            y(i2);
            z(a2);
            short s2 = (short) ao;
            this.m = s2;
            if (ao > this.n) {
                this.n = s2;
            }
        } else {
            throw new IllegalArgumentException("bad opcode for class reference");
        }
    }

    public final void f(String str, int i2, String str2, String str3) {
        int i3;
        int i4;
        int ao = ao(i2) + this.m;
        char charAt = str3.charAt(0);
        if (charAt == 'J' || charAt == 'D') {
            i3 = 2;
        } else {
            i3 = 1;
        }
        switch (i2) {
            case 178:
            case Context.VERSION_1_8 /*180*/:
                i4 = ao + i3;
                break;
            case 179:
            case 181:
                i4 = ao - i3;
                break;
            default:
                throw new IllegalArgumentException("bad opcode for field reference");
        }
        if (i4 < 0 || 32767 < i4) {
            ac(i4);
            throw null;
        }
        s0 s0Var = this.k;
        s0Var.getClass();
        x2 x2Var = new x2(str, str2, str3);
        ObjToIntMap objToIntMap = s0Var.d;
        int i5 = objToIntMap.get(x2Var, -1);
        if (i5 == -1) {
            short b2 = s0Var.b(str2, str3);
            short a2 = s0Var.a(str);
            s0Var.d(5);
            byte[] bArr = s0Var.k;
            int i6 = s0Var.g;
            int i7 = i6 + 1;
            s0Var.g = i7;
            bArr[i6] = 9;
            int ak = ak(bArr, a2, i7);
            s0Var.g = ak;
            s0Var.g = ak(s0Var.k, b2, ak);
            i5 = s0Var.h;
            s0Var.h = i5 + 1;
            objToIntMap.put(x2Var, i5);
        }
        s0Var.i.put(i5, (Object) x2Var);
        s0Var.j.put(i5, 9);
        y(i2);
        z((short) i5);
        short s2 = (short) i4;
        this.m = s2;
        if (i4 > this.n) {
            this.n = s2;
        }
    }

    public final void g(int i2) {
        as(42, 25, i2);
    }

    public final void h(int i2) {
        as(75, 58, i2);
    }

    public final void i(int i2) {
        as(38, 24, i2);
    }

    public final void j(int i2, int i3, int i4, String str) {
        short s2;
        if ((i2 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad startLabel");
        } else if ((i3 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Bad endLabel");
        } else if ((i4 & Integer.MIN_VALUE) == Integer.MIN_VALUE) {
            if (str == null) {
                s2 = 0;
            } else {
                s2 = this.k.a(str);
            }
            e2 e2Var = new e2(i2, i3, i4, s2);
            int i5 = this.f;
            if (i5 == 0) {
                this.e = new e2[4];
            } else {
                e2[] e2VarArr = this.e;
                if (i5 == e2VarArr.length) {
                    e2[] e2VarArr2 = new e2[(i5 * 2)];
                    System.arraycopy(e2VarArr, 0, e2VarArr2, 0, i5);
                    this.e = e2VarArr2;
                }
            }
            this.e[i5] = e2Var;
            this.f = i5 + 1;
        } else {
            throw new IllegalArgumentException("Bad handlerLabel");
        }
    }

    public final void k(String str, String str2, short s2) {
        s0 s0Var = this.k;
        this.q.add(new j0(s0Var.c(str), s0Var.c(str2), s2));
    }

    public final void l(int i2) {
        as(26, 21, i2);
    }

    public final void m(String str, int i2, String str2, String str3) {
        int an = an(str3);
        int i3 = an >>> 16;
        int ao = ao(i2) + this.m + ((short) an);
        if (ao < 0 || 32767 < ao) {
            ac(ao);
            throw null;
        }
        switch (i2) {
            case 182:
            case 183:
            case 184:
            case 185:
                y(i2);
                s0 s0Var = this.k;
                if (i2 == 185) {
                    short b2 = s0Var.b(str2, str3);
                    short a2 = s0Var.a(str);
                    s0Var.d(5);
                    byte[] bArr = s0Var.k;
                    int i4 = s0Var.g;
                    int i5 = i4 + 1;
                    s0Var.g = i5;
                    bArr[i4] = MqttWireMessage.MESSAGE_TYPE_UNSUBACK;
                    int ak = ak(bArr, a2, i5);
                    s0Var.g = ak;
                    s0Var.g = ak(s0Var.k, b2, ak);
                    x2 x2Var = new x2(str, str2, str3);
                    s0Var.i.put(s0Var.h, (Object) x2Var);
                    s0Var.j.put(s0Var.h, 11);
                    int i6 = s0Var.h;
                    s0Var.h = i6 + 1;
                    z((short) i6);
                    y(i3 + 1);
                    y(0);
                } else {
                    s0Var.getClass();
                    x2 x2Var2 = new x2(str, str2, str3);
                    ObjToIntMap objToIntMap = s0Var.e;
                    int i7 = objToIntMap.get(x2Var2, -1);
                    if (i7 == -1) {
                        short b3 = s0Var.b(str2, str3);
                        short a3 = s0Var.a(str);
                        s0Var.d(5);
                        byte[] bArr2 = s0Var.k;
                        int i8 = s0Var.g;
                        int i9 = i8 + 1;
                        s0Var.g = i9;
                        bArr2[i8] = 10;
                        int ak2 = ak(bArr2, a3, i9);
                        s0Var.g = ak2;
                        s0Var.g = ak(s0Var.k, b3, ak2);
                        i7 = s0Var.h;
                        s0Var.h = i7 + 1;
                        objToIntMap.put(x2Var2, i7);
                    }
                    s0Var.i.put(i7, (Object) x2Var2);
                    s0Var.j.put(i7, 10);
                    z((short) i7);
                }
                short s2 = (short) ao;
                this.m = s2;
                if (ao > this.n) {
                    this.n = s2;
                    return;
                }
                return;
            default:
                throw new IllegalArgumentException("bad opcode for method reference");
        }
    }

    public final void n(short s2) {
        if (this.l != null) {
            int i2 = this.h;
            if (i2 == 0) {
                this.g = new int[16];
            } else {
                int[] iArr = this.g;
                if (i2 == iArr.length) {
                    int[] iArr2 = new int[(i2 * 2)];
                    System.arraycopy(iArr, 0, iArr2, 0, i2);
                    this.g = iArr2;
                }
            }
            this.g[i2] = (this.j << 16) + s2;
            this.h = i2 + 1;
            return;
        }
        throw new IllegalArgumentException("No method to stop");
    }

    public final void o(int i2) {
        if (i2 == 0) {
            c(3);
        } else if (i2 == 1) {
            c(4);
        } else if (i2 == 2) {
            c(5);
        } else if (i2 == 3) {
            c(6);
        } else if (i2 == 4) {
            c(7);
        } else if (i2 != 5) {
            s0 s0Var = this.k;
            s0Var.d(5);
            byte[] bArr = s0Var.k;
            int i3 = s0Var.g;
            int i4 = i3 + 1;
            s0Var.g = i4;
            bArr[i3] = 3;
            s0Var.g = al(bArr, i2, i4);
            s0Var.j.put(s0Var.h, 3);
            int i5 = s0Var.h;
            s0Var.h = i5 + 1;
            d(18, (short) i5);
        } else {
            c(8);
        }
    }

    public final void p(String str) {
        s0 s0Var = this.k;
        short c2 = s0Var.c(str) & 65535;
        UintMap uintMap = s0Var.b;
        int i2 = uintMap.getInt(c2, -1);
        if (i2 == -1) {
            i2 = s0Var.h;
            s0Var.h = i2 + 1;
            s0Var.d(3);
            byte[] bArr = s0Var.k;
            int i3 = s0Var.g;
            int i4 = i3 + 1;
            s0Var.g = i4;
            bArr[i3] = 8;
            s0Var.g = ak(bArr, c2, i4);
            uintMap.put((int) c2, i2);
        }
        s0Var.j.put(i2, 8);
        d(18, i2);
    }

    public final void q() {
        c(42);
    }

    public final void r(double d2) {
        if (d2 == 0.0d) {
            c(14);
            if (1.0d / d2 < 0.0d) {
                c(119);
            }
        } else if (d2 == 1.0d || d2 == -1.0d) {
            c(15);
            if (d2 < 0.0d) {
                c(119);
            }
        } else {
            s0 s0Var = this.k;
            s0Var.d(9);
            byte[] bArr = s0Var.k;
            int i2 = s0Var.g;
            s0Var.g = i2 + 1;
            bArr[i2] = 6;
            long doubleToLongBits = Double.doubleToLongBits(d2);
            byte[] bArr2 = s0Var.k;
            int i3 = (int) doubleToLongBits;
            s0Var.g = al(bArr2, i3, al(bArr2, (int) (doubleToLongBits >>> 32), s0Var.g));
            int i4 = s0Var.h;
            s0Var.h = i4 + 2;
            s0Var.j.put(i4, 6);
            d(20, i4);
        }
    }

    public final void s(int i2) {
        byte b2 = (byte) i2;
        if (b2 != i2) {
            short s2 = (short) i2;
            if (s2 == i2) {
                d(17, s2);
            } else {
                o(i2);
            }
        } else if (i2 == -1) {
            c(2);
        } else if (i2 < 0 || i2 > 5) {
            d(16, b2);
        } else {
            c((byte) (i2 + 3));
        }
    }

    public final void t(String str) {
        int length = str.length();
        this.k.getClass();
        int i2 = 0;
        int f2 = s0.f(0, length, str);
        if (f2 == length) {
            p(str);
            return;
        }
        e(187, "java/lang/StringBuilder");
        c(89);
        s(length);
        m("java/lang/StringBuilder", 183, "<init>", "(I)V");
        while (true) {
            c(89);
            p(str.substring(i2, f2));
            m("java/lang/StringBuilder", 182, "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
            c(87);
            if (f2 == length) {
                m("java/lang/StringBuilder", 182, "toString", "()Ljava/lang/String;");
                return;
            }
            int i3 = f2;
            f2 = s0.f(f2, length, str);
            i2 = i3;
        }
    }

    public final void u(boolean z2) {
        c(z2 ? 4 : 3);
    }

    public final int v(int i2) {
        if (this.l != null) {
            int i3 = this.j;
            int i4 = i2 + i3;
            byte[] bArr = this.i;
            if (i4 > bArr.length) {
                int length = bArr.length * 2;
                if (i4 > length) {
                    length = i4;
                }
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 0, bArr2, 0, i3);
                this.i = bArr2;
            }
            this.j = i4;
            return i3;
        }
        throw new IllegalArgumentException("No method to add to");
    }

    public final void w(int i2) {
        if (ae) {
            int[] iArr = this.a;
            if (iArr == null) {
                this.a = new int[4];
            } else {
                int length = iArr.length;
                int i3 = this.b;
                if (length == i3) {
                    int[] iArr2 = new int[(i3 * 2)];
                    System.arraycopy(iArr, 0, iArr2, 0, i3);
                    this.a = iArr2;
                }
            }
            int[] iArr3 = this.a;
            int i4 = this.b;
            this.b = i4 + 1;
            iArr3[i4] = i2;
        }
    }

    public final int x(int i2, int i3) {
        if (i2 <= i3) {
            int ao = ao(Context.VERSION_1_7) + this.m;
            if (ao < 0 || 32767 < ao) {
                ac(ao);
                throw null;
            }
            int i4 = (~this.j) & 3;
            int v2 = v((((i3 - i2) + 1 + 3) * 4) + i4 + 1);
            int i5 = v2 + 1;
            this.i[v2] = -86;
            while (i4 != 0) {
                this.i[i5] = 0;
                i4--;
                i5++;
            }
            al(this.i, i3, al(this.i, i2, i5 + 4));
            short s2 = (short) ao;
            this.m = s2;
            if (ao > this.n) {
                this.n = s2;
            }
            return v2;
        }
        throw new a("Bad bounds: " + i2 + ' ' + i3);
    }

    public final void y(int i2) {
        this.i[v(1)] = (byte) i2;
    }

    public final void z(int i2) {
        ak(this.i, i2, v(2));
    }
}
