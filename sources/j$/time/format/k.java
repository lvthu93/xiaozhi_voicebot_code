package j$.time.format;

import j$.time.temporal.q;

class k implements C0054g {
    static final long[] f = {0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L};
    final q a;
    final int b;
    final int c;
    /* access modifiers changed from: private */
    public final F d;
    final int e;

    k(q qVar, int i, int i2, F f2) {
        this.a = qVar;
        this.b = i;
        this.c = i2;
        this.d = f2;
        this.e = 0;
    }

    protected k(q qVar, int i, int i2, F f2, int i3) {
        this.a = qVar;
        this.b = i;
        this.c = i2;
        this.d = f2;
        this.e = i3;
    }

    /* access modifiers changed from: package-private */
    public long b(z zVar, long j) {
        return j;
    }

    /* access modifiers changed from: package-private */
    public boolean c(x xVar) {
        int i = this.e;
        return i == -1 || (i > 0 && this.b == this.c && this.d == F.NOT_NEGATIVE);
    }

    /* access modifiers changed from: package-private */
    public int d(x xVar, long j, int i, int i2) {
        return xVar.o(this.a, j, i, i2);
    }

    /* access modifiers changed from: package-private */
    public k e() {
        return this.e == -1 ? this : new k(this.a, this.b, this.c, this.d, -1);
    }

    /* access modifiers changed from: package-private */
    public k f(int i) {
        return new k(this.a, this.b, this.c, this.d, this.e + i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x009b A[LOOP:0: B:30:0x0093->B:32:0x009b, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean k(j$.time.format.z r14, java.lang.StringBuilder r15) {
        /*
            r13 = this;
            j$.time.temporal.q r0 = r13.a
            java.lang.Long r1 = r14.e(r0)
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            long r3 = r1.longValue()
            long r3 = r13.b(r14, r3)
            j$.time.format.C r14 = r14.b()
            r5 = -9223372036854775808
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L_0x001f
            java.lang.String r1 = "9223372036854775808"
            goto L_0x0027
        L_0x001f:
            long r5 = java.lang.Math.abs(r3)
            java.lang.String r1 = java.lang.Long.toString(r5)
        L_0x0027:
            int r5 = r1.length()
            java.lang.String r6 = " cannot be printed as the value "
            java.lang.String r7 = "Field "
            int r8 = r13.c
            if (r5 > r8) goto L_0x00a7
            r14.getClass()
            r14 = 1
            r8 = 0
            int r5 = r13.b
            r10 = 2
            j$.time.format.F r11 = r13.d
            int r12 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r12 < 0) goto L_0x005e
            int[] r0 = j$.time.format.C0051d.a
            int r6 = r11.ordinal()
            r0 = r0[r6]
            if (r0 == r14) goto L_0x004f
            if (r0 == r10) goto L_0x005b
            goto L_0x0093
        L_0x004f:
            r0 = 19
            if (r5 >= r0) goto L_0x0093
            long[] r0 = f
            r6 = r0[r5]
            int r0 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r0 < 0) goto L_0x0093
        L_0x005b:
            r0 = 43
            goto L_0x0090
        L_0x005e:
            int[] r8 = j$.time.format.C0051d.a
            int r9 = r11.ordinal()
            r8 = r8[r9]
            if (r8 == r14) goto L_0x008e
            if (r8 == r10) goto L_0x008e
            r9 = 3
            if (r8 == r9) goto L_0x008e
            r9 = 4
            if (r8 == r9) goto L_0x0071
            goto L_0x0093
        L_0x0071:
            j$.time.DateTimeException r14 = new j$.time.DateTimeException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>(r7)
            r15.append(r0)
            r15.append(r6)
            r15.append(r3)
            java.lang.String r0 = " cannot be negative according to the SignStyle"
            r15.append(r0)
            java.lang.String r15 = r15.toString()
            r14.<init>(r15)
            throw r14
        L_0x008e:
            r0 = 45
        L_0x0090:
            r15.append(r0)
        L_0x0093:
            int r0 = r1.length()
            int r0 = r5 - r0
            if (r2 >= r0) goto L_0x00a3
            r0 = 48
            r15.append(r0)
            int r2 = r2 + 1
            goto L_0x0093
        L_0x00a3:
            r15.append(r1)
            return r14
        L_0x00a7:
            j$.time.DateTimeException r14 = new j$.time.DateTimeException
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>(r7)
            r15.append(r0)
            r15.append(r6)
            r15.append(r3)
            java.lang.String r0 = " exceeds the maximum print width of "
            r15.append(r0)
            r15.append(r8)
            java.lang.String r15 = r15.toString()
            r14.<init>(r15)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.k.k(j$.time.format.z, java.lang.StringBuilder):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:117:0x0165  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0183  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int l(j$.time.format.x r21, java.lang.CharSequence r22, int r23) {
        /*
            r20 = this;
            r6 = r20
            r0 = r23
            int r1 = r22.length()
            if (r0 != r1) goto L_0x000c
            int r0 = ~r0
            return r0
        L_0x000c:
            char r2 = r22.charAt(r23)
            j$.time.format.C r3 = r21.g()
            r3.getClass()
            r3 = 4
            r4 = 0
            r5 = 1
            r7 = 43
            int r8 = r6.c
            j$.time.format.F r9 = r6.d
            int r10 = r6.b
            if (r2 != r7) goto L_0x004b
            boolean r2 = r21.l()
            if (r10 != r8) goto L_0x002c
            r7 = 1
            goto L_0x002d
        L_0x002c:
            r7 = 0
        L_0x002d:
            int r11 = r9.ordinal()
            if (r11 == 0) goto L_0x003f
            if (r11 == r5) goto L_0x003b
            if (r11 == r3) goto L_0x003b
            if (r2 != 0) goto L_0x003d
            if (r7 != 0) goto L_0x003d
        L_0x003b:
            r2 = 1
            goto L_0x0042
        L_0x003d:
            r2 = 0
            goto L_0x0042
        L_0x003f:
            if (r2 != 0) goto L_0x003d
            goto L_0x003b
        L_0x0042:
            if (r2 != 0) goto L_0x0046
            int r0 = ~r0
            return r0
        L_0x0046:
            int r0 = r0 + r5
            r7 = r0
            r0 = 1
            r2 = 0
            goto L_0x0087
        L_0x004b:
            j$.time.format.C r7 = r21.g()
            r7.getClass()
            r7 = 45
            if (r2 != r7) goto L_0x0078
            boolean r2 = r21.l()
            if (r10 != r8) goto L_0x005e
            r7 = 1
            goto L_0x005f
        L_0x005e:
            r7 = 0
        L_0x005f:
            int r11 = r9.ordinal()
            if (r11 == 0) goto L_0x006d
            if (r11 == r5) goto L_0x006d
            if (r11 == r3) goto L_0x006d
            if (r2 != 0) goto L_0x006f
            if (r7 != 0) goto L_0x006f
        L_0x006d:
            r2 = 1
            goto L_0x0070
        L_0x006f:
            r2 = 0
        L_0x0070:
            if (r2 != 0) goto L_0x0074
            int r0 = ~r0
            return r0
        L_0x0074:
            int r0 = r0 + 1
            r2 = 1
            goto L_0x0085
        L_0x0078:
            j$.time.format.F r2 = j$.time.format.F.ALWAYS
            if (r9 != r2) goto L_0x0084
            boolean r2 = r21.l()
            if (r2 == 0) goto L_0x0084
            int r0 = ~r0
            return r0
        L_0x0084:
            r2 = 0
        L_0x0085:
            r7 = r0
            r0 = 0
        L_0x0087:
            boolean r3 = r21.l()
            if (r3 != 0) goto L_0x0096
            boolean r3 = r20.c(r21)
            if (r3 == 0) goto L_0x0094
            goto L_0x0096
        L_0x0094:
            r3 = 1
            goto L_0x0097
        L_0x0096:
            r3 = r10
        L_0x0097:
            int r11 = r7 + r3
            if (r11 <= r1) goto L_0x009d
            int r0 = ~r7
            return r0
        L_0x009d:
            boolean r12 = r21.l()
            if (r12 != 0) goto L_0x00ac
            boolean r12 = r20.c(r21)
            if (r12 == 0) goto L_0x00aa
            goto L_0x00ac
        L_0x00aa:
            r8 = 9
        L_0x00ac:
            int r12 = r6.e
            int r13 = java.lang.Math.max(r12, r4)
            int r13 = r13 + r8
        L_0x00b3:
            r8 = 2
            r16 = 0
            if (r4 >= r8) goto L_0x0117
            int r13 = r13 + r7
            int r8 = java.lang.Math.min(r13, r1)
            r13 = r7
            r17 = 0
        L_0x00c0:
            if (r13 >= r8) goto L_0x0103
            int r19 = r13 + 1
            r14 = r22
            char r13 = r14.charAt(r13)
            j$.time.format.C r15 = r21.g()
            int r13 = r15.a(r13)
            if (r13 >= 0) goto L_0x00da
            int r13 = r19 + -1
            if (r13 >= r11) goto L_0x0103
            int r0 = ~r7
            return r0
        L_0x00da:
            int r15 = r19 - r7
            r5 = 18
            if (r15 <= r5) goto L_0x00f8
            if (r16 != 0) goto L_0x00e6
            java.math.BigInteger r16 = java.math.BigInteger.valueOf(r17)
        L_0x00e6:
            r5 = r16
            java.math.BigInteger r15 = java.math.BigInteger.TEN
            java.math.BigInteger r5 = r5.multiply(r15)
            long r13 = (long) r13
            java.math.BigInteger r13 = java.math.BigInteger.valueOf(r13)
            java.math.BigInteger r16 = r5.add(r13)
            goto L_0x00ff
        L_0x00f8:
            r14 = 10
            long r17 = r17 * r14
            long r13 = (long) r13
            long r17 = r17 + r13
        L_0x00ff:
            r13 = r19
            r5 = 1
            goto L_0x00c0
        L_0x0103:
            if (r12 <= 0) goto L_0x0111
            if (r4 != 0) goto L_0x0111
            int r13 = r13 - r7
            int r13 = r13 - r12
            int r13 = java.lang.Math.max(r3, r13)
            int r4 = r4 + 1
            r5 = 1
            goto L_0x00b3
        L_0x0111:
            r5 = r13
            r1 = r16
            r3 = r17
            goto L_0x011c
        L_0x0117:
            r5 = r7
            r1 = r16
            r3 = 0
        L_0x011c:
            if (r2 == 0) goto L_0x014a
            if (r1 == 0) goto L_0x0138
            java.math.BigInteger r0 = java.math.BigInteger.ZERO
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0132
            boolean r0 = r21.l()
            if (r0 == 0) goto L_0x0132
            r0 = 1
            int r7 = r7 - r0
            int r0 = ~r7
            return r0
        L_0x0132:
            java.math.BigInteger r0 = r1.negate()
            r1 = r0
            goto L_0x0162
        L_0x0138:
            r0 = 1
            r8 = 0
            int r2 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1))
            if (r2 != 0) goto L_0x0148
            boolean r2 = r21.l()
            if (r2 == 0) goto L_0x0148
            int r7 = r7 - r0
            int r0 = ~r7
            return r0
        L_0x0148:
            long r2 = -r3
            goto L_0x0163
        L_0x014a:
            j$.time.format.F r2 = j$.time.format.F.EXCEEDS_PAD
            if (r9 != r2) goto L_0x0162
            boolean r2 = r21.l()
            if (r2 == 0) goto L_0x0162
            int r2 = r5 - r7
            if (r0 == 0) goto L_0x015e
            if (r2 > r10) goto L_0x0162
            r0 = 1
            int r7 = r7 - r0
            int r0 = ~r7
            return r0
        L_0x015e:
            if (r2 <= r10) goto L_0x0162
            int r0 = ~r7
            return r0
        L_0x0162:
            r2 = r3
        L_0x0163:
            if (r1 == 0) goto L_0x0183
            int r0 = r1.bitLength()
            r2 = 63
            if (r0 <= r2) goto L_0x0175
            java.math.BigInteger r0 = java.math.BigInteger.TEN
            java.math.BigInteger r1 = r1.divide(r0)
            int r5 = r5 + -1
        L_0x0175:
            long r2 = r1.longValue()
            r0 = r20
            r1 = r21
            r4 = r7
            int r0 = r0.d(r1, r2, r4, r5)
            return r0
        L_0x0183:
            r0 = r20
            r1 = r21
            r4 = r7
            int r0 = r0.d(r1, r2, r4, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.k.l(j$.time.format.x, java.lang.CharSequence, int):int");
    }

    public String toString() {
        int i = this.c;
        F f2 = this.d;
        q qVar = this.a;
        int i2 = this.b;
        if (i2 == 1 && i == 19 && f2 == F.NORMAL) {
            return "Value(" + qVar + ")";
        } else if (i2 == i && f2 == F.NOT_NEGATIVE) {
            return "Value(" + qVar + "," + i2 + ")";
        } else {
            return "Value(" + qVar + "," + i2 + "," + i + "," + f2 + ")";
        }
    }
}
