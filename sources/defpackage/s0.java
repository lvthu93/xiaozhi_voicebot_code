package defpackage;

import androidx.core.internal.view.SupportMenu;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.mozilla.javascript.ObjToIntMap;
import org.mozilla.javascript.UintMap;

/* renamed from: s0  reason: default package */
public final class s0 {
    public final l0 a;
    public final UintMap b = new UintMap();
    public final ObjToIntMap c = new ObjToIntMap();
    public final ObjToIntMap d = new ObjToIntMap();
    public final ObjToIntMap e = new ObjToIntMap();
    public final ObjToIntMap f = new ObjToIntMap();
    public int g;
    public int h;
    public final UintMap i;
    public final UintMap j;
    public byte[] k;

    public s0(l0 l0Var) {
        new ObjToIntMap();
        this.i = new UintMap();
        this.j = new UintMap();
        this.a = l0Var;
        this.h = 1;
        this.k = new byte[256];
        this.g = 0;
    }

    public static int f(int i2, int i3, String str) {
        int i4 = (i3 - i2) * 3;
        int i5 = SupportMenu.USER_MASK;
        if (i4 <= 65535) {
            return i3;
        }
        while (i2 != i3) {
            char charAt = str.charAt(i2);
            i5 = (charAt == 0 || charAt > 127) ? charAt < 2047 ? i5 - 2 : i5 - 3 : i5 - 1;
            if (i5 < 0) {
                return i2;
            }
            i2++;
        }
        return i3;
    }

    public final short a(String str) {
        String str2;
        ObjToIntMap objToIntMap = this.f;
        int i2 = objToIntMap.get(str, -1);
        if (i2 == -1) {
            if (str.indexOf(46) > 0) {
                String replace = str.replace('.', '/');
                int i3 = objToIntMap.get(replace, -1);
                if (i3 != -1) {
                    objToIntMap.put(str, i3);
                }
                int i4 = i3;
                str2 = replace;
                i2 = i4;
            } else {
                str2 = str;
            }
            if (i2 == -1) {
                short c2 = c(str2);
                d(3);
                byte[] bArr = this.k;
                int i5 = this.g;
                int i6 = i5 + 1;
                this.g = i6;
                bArr[i5] = 7;
                this.g = l0.ak(bArr, c2, i6);
                i2 = this.h;
                this.h = i2 + 1;
                objToIntMap.put(str2, i2);
                if (!str.equals(str2)) {
                    objToIntMap.put(str, i2);
                }
            }
        }
        this.i.put(i2, (Object) str);
        this.j.put(i2, 7);
        return (short) i2;
    }

    public final short b(String str, String str2) {
        short c2 = c(str);
        short c3 = c(str2);
        d(5);
        byte[] bArr = this.k;
        int i2 = this.g;
        int i3 = i2 + 1;
        this.g = i3;
        bArr[i2] = MqttWireMessage.MESSAGE_TYPE_PINGREQ;
        int ak = l0.ak(bArr, c2, i3);
        this.g = ak;
        this.g = l0.ak(this.k, c3, ak);
        this.j.put(this.h, 12);
        int i4 = this.h;
        this.h = i4 + 1;
        return (short) i4;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final short c(java.lang.String r14) {
        /*
            r13 = this;
            org.mozilla.javascript.ObjToIntMap r0 = r13.c
            r1 = -1
            int r2 = r0.get(r14, r1)
            r3 = 1
            if (r2 != r1) goto L_0x00bb
            int r1 = r14.length()
            r4 = 65535(0xffff, float:9.1834E-41)
            if (r1 <= r4) goto L_0x0015
            goto L_0x0096
        L_0x0015:
            int r5 = r1 * 3
            int r5 = r5 + 3
            r13.d(r5)
            int r5 = r13.g
            byte[] r6 = r13.k
            int r7 = r5 + 1
            r6[r5] = r3
            int r7 = r7 + 2
            l0 r5 = r13.a
            char[] r6 = r5.ab
            int r8 = r6.length
            if (r1 <= r8) goto L_0x0037
            int r6 = r6.length
            int r6 = r6 * 2
            if (r1 <= r6) goto L_0x0033
            r6 = r1
        L_0x0033:
            char[] r6 = new char[r6]
            r5.ab = r6
        L_0x0037:
            char[] r5 = r5.ab
            r6 = 0
            r14.getChars(r6, r1, r5, r6)
            r8 = 0
        L_0x003e:
            if (r8 == r1) goto L_0x008c
            char r9 = r5[r8]
            if (r9 == 0) goto L_0x0050
            r10 = 127(0x7f, float:1.78E-43)
            if (r9 > r10) goto L_0x0050
            byte[] r10 = r13.k
            int r11 = r7 + 1
            byte r9 = (byte) r9
            r10[r7] = r9
            goto L_0x0073
        L_0x0050:
            r10 = 2047(0x7ff, float:2.868E-42)
            if (r9 <= r10) goto L_0x0075
            byte[] r10 = r13.k
            int r11 = r7 + 1
            int r12 = r9 >> 12
            r12 = r12 | 224(0xe0, float:3.14E-43)
            byte r12 = (byte) r12
            r10[r7] = r12
            int r7 = r11 + 1
            int r12 = r9 >> 6
            r12 = r12 & 63
            r12 = r12 | 128(0x80, float:1.794E-43)
            byte r12 = (byte) r12
            r10[r11] = r12
            int r11 = r7 + 1
            r9 = r9 & 63
            r9 = r9 | 128(0x80, float:1.794E-43)
            byte r9 = (byte) r9
            r10[r7] = r9
        L_0x0073:
            r7 = r11
            goto L_0x0089
        L_0x0075:
            byte[] r10 = r13.k
            int r11 = r7 + 1
            int r12 = r9 >> 6
            r12 = r12 | 192(0xc0, float:2.69E-43)
            byte r12 = (byte) r12
            r10[r7] = r12
            int r7 = r11 + 1
            r9 = r9 & 63
            r9 = r9 | 128(0x80, float:1.794E-43)
            byte r9 = (byte) r9
            r10[r11] = r9
        L_0x0089:
            int r8 = r8 + 1
            goto L_0x003e
        L_0x008c:
            int r1 = r13.g
            int r5 = r1 + 1
            int r8 = r5 + 2
            int r8 = r7 - r8
            if (r8 <= r4) goto L_0x0098
        L_0x0096:
            r6 = 1
            goto L_0x00b0
        L_0x0098:
            byte[] r2 = r13.k
            int r4 = r8 >>> 8
            byte r4 = (byte) r4
            r2[r5] = r4
            int r1 = r1 + 2
            byte r4 = (byte) r8
            r2[r1] = r4
            r13.g = r7
            int r1 = r13.h
            int r2 = r1 + 1
            r13.h = r2
            r0.put(r14, r1)
            r2 = r1
        L_0x00b0:
            if (r6 != 0) goto L_0x00b3
            goto L_0x00bb
        L_0x00b3:
            java.lang.IllegalArgumentException r14 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "Too big string"
            r14.<init>(r0)
            throw r14
        L_0x00bb:
            org.mozilla.javascript.UintMap r0 = r13.i
            r0.put((int) r2, (java.lang.Object) r14)
            org.mozilla.javascript.UintMap r14 = r13.j
            r14.put((int) r2, (int) r3)
            short r14 = (short) r2
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.s0.c(java.lang.String):short");
    }

    public final void d(int i2) {
        int i3 = this.g;
        int i4 = i3 + i2;
        byte[] bArr = this.k;
        if (i4 > bArr.length) {
            int length = bArr.length * 2;
            if (i3 + i2 > length) {
                length = i3 + i2;
            }
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, i3);
            this.k = bArr2;
        }
    }

    public final Object e(int i2) {
        return this.i.getObject(i2);
    }
}
