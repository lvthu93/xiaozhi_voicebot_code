package defpackage;

import java.nio.ShortBuffer;
import java.util.Arrays;

/* renamed from: gb  reason: default package */
public final class gb {
    public final int a;
    public final int b;
    public final float c;
    public final float d;
    public final float e;
    public final int f;
    public final int g;
    public final int h;
    public final short[] i;
    public short[] j;
    public int k;
    public short[] l;
    public int m;
    public short[] n;
    public int o;
    public int p;
    public int q;
    public int r;
    public int s;
    public int t;
    public int u;
    public int v;

    public gb(int i2, int i3, float f2, float f3, int i4) {
        this.a = i2;
        this.b = i3;
        this.c = f2;
        this.d = f3;
        this.e = ((float) i2) / ((float) i4);
        this.f = i2 / 400;
        int i5 = i2 / 65;
        this.g = i5;
        int i6 = i5 * 2;
        this.h = i6;
        this.i = new short[i6];
        this.j = new short[(i6 * i3)];
        this.l = new short[(i6 * i3)];
        this.n = new short[(i6 * i3)];
    }

    public static void e(int i2, int i3, short[] sArr, int i4, short[] sArr2, int i5, short[] sArr3, int i6) {
        for (int i7 = 0; i7 < i3; i7++) {
            int i8 = (i4 * i3) + i7;
            int i9 = (i6 * i3) + i7;
            int i10 = (i5 * i3) + i7;
            for (int i11 = 0; i11 < i2; i11++) {
                sArr[i8] = (short) (((sArr3[i9] * i11) + ((i2 - i11) * sArr2[i10])) / i2);
                i8 += i3;
                i10 += i3;
                i9 += i3;
            }
        }
    }

    public final void a(short[] sArr, int i2, int i3) {
        short[] c2 = c(this.l, this.m, i3);
        this.l = c2;
        int i4 = this.b;
        System.arraycopy(sArr, i2 * i4, c2, this.m * i4, i4 * i3);
        this.m += i3;
    }

    public final void b(short[] sArr, int i2, int i3) {
        int i4 = this.h / i3;
        int i5 = this.b;
        int i6 = i3 * i5;
        int i7 = i2 * i5;
        for (int i8 = 0; i8 < i4; i8++) {
            int i9 = 0;
            for (int i10 = 0; i10 < i6; i10++) {
                i9 += sArr[(i8 * i6) + i7 + i10];
            }
            this.i[i8] = (short) (i9 / i6);
        }
    }

    public final short[] c(short[] sArr, int i2, int i3) {
        int length = sArr.length;
        int i4 = this.b;
        int i5 = length / i4;
        if (i2 + i3 <= i5) {
            return sArr;
        }
        return Arrays.copyOf(sArr, (((i5 * 3) / 2) + i3) * i4);
    }

    public final int d(short[] sArr, int i2, int i3, int i4) {
        int i5 = i2 * this.b;
        int i6 = 255;
        int i7 = 1;
        int i8 = 0;
        int i9 = 0;
        while (i3 <= i4) {
            int i10 = 0;
            for (int i11 = 0; i11 < i3; i11++) {
                i10 += Math.abs(sArr[i5 + i11] - sArr[(i5 + i3) + i11]);
            }
            if (i10 * i8 < i7 * i3) {
                i8 = i3;
                i7 = i10;
            }
            if (i10 * i6 > i9 * i3) {
                i6 = i3;
                i9 = i10;
            }
            i3++;
        }
        this.u = i7 / i8;
        this.v = i9 / i6;
        return i8;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: CodeShrinkVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Don't wrap MOVE or CONST insns: 0x0191: MOVE  (r3v1 int) = (r23v0 int)
        	at jadx.core.dex.instructions.args.InsnArg.wrapArg(InsnArg.java:164)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.assignInline(CodeShrinkVisitor.java:133)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.checkInline(CodeShrinkVisitor.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:65)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    /* JADX WARNING: Removed duplicated region for block: B:100:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0197  */
    public final void f() {
        /*
            r25 = this;
            r0 = r25
            int r1 = r0.m
            float r2 = r0.c
            float r3 = r0.d
            float r2 = r2 / r3
            float r4 = r0.e
            float r4 = r4 * r3
            double r5 = (double) r2
            r7 = 4607182463836013682(0x3ff0000a7c5ac472, double:1.00001)
            int r9 = r0.a
            int r10 = r0.b
            r11 = 1
            r12 = 0
            int r13 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r13 > 0) goto L_0x0038
            r7 = 4607182328728024861(0x3fefffeb074a771d, double:0.99999)
            int r13 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r13 >= 0) goto L_0x0027
            goto L_0x0038
        L_0x0027:
            short[] r2 = r0.j
            int r5 = r0.k
            r0.a(r2, r12, r5)
            r0.k = r12
        L_0x0030:
            r23 = r1
            r24 = r4
            r22 = r9
            goto L_0x0189
        L_0x0038:
            int r7 = r0.k
            int r8 = r0.h
            if (r7 >= r8) goto L_0x003f
            goto L_0x0030
        L_0x003f:
            r15 = 0
        L_0x0040:
            int r13 = r0.r
            if (r13 <= 0) goto L_0x005b
            int r13 = java.lang.Math.min(r8, r13)
            short[] r14 = r0.j
            r0.a(r14, r15, r13)
            int r14 = r0.r
            int r14 = r14 - r13
            r0.r = r14
            int r15 = r15 + r13
            r23 = r1
            r24 = r4
            r22 = r9
            goto L_0x0176
        L_0x005b:
            short[] r13 = r0.j
            r14 = 4000(0xfa0, float:5.605E-42)
            if (r9 <= r14) goto L_0x0064
            int r14 = r9 / 4000
            goto L_0x0065
        L_0x0064:
            r14 = 1
        L_0x0065:
            int r3 = r0.g
            int r12 = r0.f
            if (r10 != r11) goto L_0x0078
            if (r14 != r11) goto L_0x0078
            int r3 = r0.d(r13, r15, r12, r3)
            r23 = r1
            r24 = r4
            r22 = r9
            goto L_0x00b0
        L_0x0078:
            r0.b(r13, r15, r14)
            int r11 = r12 / r14
            r22 = r9
            int r9 = r3 / r14
            r23 = r1
            short[] r1 = r0.i
            r24 = r4
            r4 = 0
            int r9 = r0.d(r1, r4, r11, r9)
            r4 = 1
            if (r14 == r4) goto L_0x00af
            int r9 = r9 * r14
            int r14 = r14 * 4
            int r4 = r9 - r14
            int r9 = r9 + r14
            if (r4 >= r12) goto L_0x0099
            goto L_0x009a
        L_0x0099:
            r12 = r4
        L_0x009a:
            if (r9 <= r3) goto L_0x009d
            goto L_0x009e
        L_0x009d:
            r3 = r9
        L_0x009e:
            r4 = 1
            if (r10 != r4) goto L_0x00a6
            int r3 = r0.d(r13, r15, r12, r3)
            goto L_0x00b0
        L_0x00a6:
            r0.b(r13, r15, r4)
            r4 = 0
            int r3 = r0.d(r1, r4, r12, r3)
            goto L_0x00b0
        L_0x00af:
            r3 = r9
        L_0x00b0:
            int r1 = r0.u
            int r4 = r0.v
            if (r1 == 0) goto L_0x00cb
            int r9 = r0.s
            if (r9 != 0) goto L_0x00bb
            goto L_0x00cb
        L_0x00bb:
            int r9 = r1 * 3
            if (r4 <= r9) goto L_0x00c0
            goto L_0x00cb
        L_0x00c0:
            int r4 = r1 * 2
            int r9 = r0.t
            int r9 = r9 * 3
            if (r4 > r9) goto L_0x00c9
            goto L_0x00cb
        L_0x00c9:
            r4 = 1
            goto L_0x00cc
        L_0x00cb:
            r4 = 0
        L_0x00cc:
            if (r4 == 0) goto L_0x00d1
            int r4 = r0.s
            goto L_0x00d2
        L_0x00d1:
            r4 = r3
        L_0x00d2:
            r0.t = r1
            r0.s = r3
            r11 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r1 = 1073741824(0x40000000, float:2.0)
            int r3 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r3 <= 0) goto L_0x0120
            short[] r3 = r0.j
            int r9 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r9 < 0) goto L_0x00ec
            float r1 = (float) r4
            r9 = 1065353216(0x3f800000, float:1.0)
            float r11 = r2 - r9
            float r1 = r1 / r11
            int r1 = (int) r1
            goto L_0x00f9
        L_0x00ec:
            r9 = 1065353216(0x3f800000, float:1.0)
            float r11 = (float) r4
            float r1 = r1 - r2
            float r1 = r1 * r11
            float r11 = r2 - r9
            float r1 = r1 / r11
            int r1 = (int) r1
            r0.r = r1
            r1 = r4
        L_0x00f9:
            short[] r9 = r0.l
            int r11 = r0.m
            short[] r9 = r0.c(r9, r11, r1)
            r0.l = r9
            int r14 = r0.b
            int r11 = r0.m
            int r20 = r15 + r4
            r13 = r1
            r12 = r15
            r15 = r9
            r16 = r11
            r17 = r3
            r18 = r12
            r19 = r3
            e(r13, r14, r15, r16, r17, r18, r19, r20)
            int r3 = r0.m
            int r3 = r3 + r1
            r0.m = r3
            int r4 = r4 + r1
            int r4 = r4 + r12
            r15 = r4
            goto L_0x0176
        L_0x0120:
            r12 = r15
            short[] r3 = r0.j
            r9 = 1056964608(0x3f000000, float:0.5)
            int r9 = (r2 > r9 ? 1 : (r2 == r9 ? 0 : -1))
            if (r9 >= 0) goto L_0x0133
            float r1 = (float) r4
            float r1 = r1 * r2
            r9 = 1065353216(0x3f800000, float:1.0)
            float r11 = r9 - r2
            float r1 = r1 / r11
            int r1 = (int) r1
            goto L_0x0142
        L_0x0133:
            r9 = 1065353216(0x3f800000, float:1.0)
            float r11 = (float) r4
            float r1 = r1 * r2
            float r1 = r1 - r9
            float r1 = r1 * r11
            float r11 = r9 - r2
            float r1 = r1 / r11
            int r1 = (int) r1
            r0.r = r1
            r1 = r4
        L_0x0142:
            short[] r9 = r0.l
            int r11 = r0.m
            int r15 = r4 + r1
            short[] r9 = r0.c(r9, r11, r15)
            r0.l = r9
            int r11 = r12 * r10
            int r13 = r0.m
            int r13 = r13 * r10
            int r14 = r10 * r4
            java.lang.System.arraycopy(r3, r11, r9, r13, r14)
            int r14 = r0.b
            short[] r9 = r0.l
            int r11 = r0.m
            int r16 = r11 + r4
            int r18 = r12 + r4
            r13 = r1
            r4 = r15
            r15 = r9
            r17 = r3
            r19 = r3
            r20 = r12
            e(r13, r14, r15, r16, r17, r18, r19, r20)
            int r3 = r0.m
            int r3 = r3 + r4
            r0.m = r3
            int r15 = r12 + r1
        L_0x0176:
            int r1 = r15 + r8
            if (r1 <= r7) goto L_0x0255
            int r1 = r0.k
            int r1 = r1 - r15
            short[] r2 = r0.j
            int r15 = r15 * r10
            int r3 = r10 * r1
            r4 = 0
            java.lang.System.arraycopy(r2, r15, r2, r4, r3)
            r0.k = r1
        L_0x0189:
            r1 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r24 > r1 ? 1 : (r24 == r1 ? 0 : -1))
            if (r1 == 0) goto L_0x0254
            int r1 = r0.m
            r3 = r23
            if (r1 != r3) goto L_0x0197
            goto L_0x0254
        L_0x0197:
            r4 = r22
            float r1 = (float) r4
            float r1 = r1 / r24
            int r1 = (int) r1
            r9 = r4
        L_0x019e:
            r2 = 16384(0x4000, float:2.2959E-41)
            if (r1 > r2) goto L_0x024c
            if (r9 <= r2) goto L_0x01a6
            goto L_0x024c
        L_0x01a6:
            int r2 = r0.m
            int r2 = r2 - r3
            short[] r4 = r0.n
            int r5 = r0.o
            short[] r4 = r0.c(r4, r5, r2)
            r0.n = r4
            short[] r5 = r0.l
            int r6 = r3 * r10
            int r7 = r0.o
            int r7 = r7 * r10
            int r8 = r10 * r2
            java.lang.System.arraycopy(r5, r6, r4, r7, r8)
            r0.m = r3
            int r3 = r0.o
            int r3 = r3 + r2
            r0.o = r3
            r4 = 0
        L_0x01c8:
            int r2 = r0.o
            int r3 = r2 + -1
            if (r4 >= r3) goto L_0x0238
        L_0x01ce:
            int r2 = r0.p
            r3 = 1
            int r2 = r2 + r3
            int r5 = r2 * r1
            int r6 = r0.q
            int r7 = r6 * r9
            if (r5 <= r7) goto L_0x0221
            short[] r2 = r0.l
            int r5 = r0.m
            short[] r2 = r0.c(r2, r5, r3)
            r0.l = r2
            r2 = 0
        L_0x01e5:
            if (r2 >= r10) goto L_0x0215
            short[] r3 = r0.l
            int r5 = r0.m
            int r5 = r5 * r10
            int r5 = r5 + r2
            short[] r6 = r0.n
            int r7 = r4 * r10
            int r7 = r7 + r2
            short r8 = r6[r7]
            int r7 = r7 + r10
            short r6 = r6[r7]
            int r7 = r0.q
            int r7 = r7 * r9
            int r11 = r0.p
            int r12 = r11 * r1
            r13 = 1
            int r11 = r11 + r13
            int r11 = r11 * r1
            int r7 = r11 - r7
            int r11 = r11 - r12
            int r8 = r8 * r7
            int r7 = r11 - r7
            int r7 = r7 * r6
            int r7 = r7 + r8
            int r7 = r7 / r11
            short r6 = (short) r7
            r3[r5] = r6
            int r2 = r2 + 1
            goto L_0x01e5
        L_0x0215:
            int r2 = r0.q
            r11 = 1
            int r2 = r2 + r11
            r0.q = r2
            int r2 = r0.m
            int r2 = r2 + r11
            r0.m = r2
            goto L_0x01ce
        L_0x0221:
            r11 = 1
            r0.p = r2
            if (r2 != r9) goto L_0x0235
            r2 = 0
            r0.p = r2
            if (r6 != r1) goto L_0x022e
            r21 = 1
            goto L_0x0230
        L_0x022e:
            r21 = 0
        L_0x0230:
            com.google.android.exoplayer2.util.Assertions.checkState(r21)
            r0.q = r2
        L_0x0235:
            int r4 = r4 + 1
            goto L_0x01c8
        L_0x0238:
            if (r3 != 0) goto L_0x023b
            goto L_0x0254
        L_0x023b:
            short[] r1 = r0.n
            int r4 = r3 * r10
            int r2 = r2 - r3
            int r2 = r2 * r10
            r12 = 0
            java.lang.System.arraycopy(r1, r4, r1, r12, r2)
            int r1 = r0.o
            int r1 = r1 - r3
            r0.o = r1
            goto L_0x0254
        L_0x024c:
            r11 = 1
            r12 = 0
            int r1 = r1 / 2
            int r9 = r9 / 2
            goto L_0x019e
        L_0x0254:
            return
        L_0x0255:
            r9 = r22
            r1 = r23
            r4 = r24
            r11 = 1
            r12 = 0
            goto L_0x0040
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.gb.f():void");
    }

    public void flush() {
        this.k = 0;
        this.m = 0;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.v = 0;
    }

    public void getOutput(ShortBuffer shortBuffer) {
        int remaining = shortBuffer.remaining();
        int i2 = this.b;
        int min = Math.min(remaining / i2, this.m);
        shortBuffer.put(this.l, 0, min * i2);
        int i3 = this.m - min;
        this.m = i3;
        short[] sArr = this.l;
        System.arraycopy(sArr, min * i2, sArr, 0, i3 * i2);
    }

    public int getOutputSize() {
        return this.m * this.b * 2;
    }

    public int getPendingInputBytes() {
        return this.k * this.b * 2;
    }

    public void queueEndOfStream() {
        int i2 = this.k;
        float f2 = this.c;
        float f3 = this.d;
        float f4 = this.e * f3;
        int i3 = this.m + ((int) ((((((float) i2) / (f2 / f3)) + ((float) this.o)) / f4) + 0.5f));
        short[] sArr = this.j;
        int i4 = this.h;
        this.j = c(sArr, i2, (i4 * 2) + i2);
        int i5 = 0;
        while (true) {
            int i6 = this.b;
            if (i5 >= i4 * 2 * i6) {
                break;
            }
            this.j[(i6 * i2) + i5] = 0;
            i5++;
        }
        this.k = (i4 * 2) + this.k;
        f();
        if (this.m > i3) {
            this.m = i3;
        }
        this.k = 0;
        this.r = 0;
        this.o = 0;
    }

    public void queueInput(ShortBuffer shortBuffer) {
        int remaining = shortBuffer.remaining();
        int i2 = this.b;
        int i3 = remaining / i2;
        short[] c2 = c(this.j, this.k, i3);
        this.j = c2;
        shortBuffer.get(c2, this.k * i2, ((i3 * i2) * 2) / 2);
        this.k += i3;
        f();
    }
}
