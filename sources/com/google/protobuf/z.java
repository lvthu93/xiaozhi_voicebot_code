package com.google.protobuf;

import com.google.protobuf.d;
import com.google.protobuf.p;
import com.google.protobuf.q;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import sun.misc.Unsafe;

public final class z<T> implements ac<T> {
    public static final int[] q = new int[0];
    public static final Unsafe r = fd.q();
    public final int[] a;
    public final Object[] b;
    public final int c;
    public final int d;
    public final x e;
    public final boolean f;
    public final boolean g;
    public final boolean h;
    public final int[] i;
    public final int j;
    public final int k;
    public final g7 l;
    public final g4 m;
    public final ag<?, ?> n;
    public final j<?> o;
    public final q4 p;

    public z(int[] iArr, Object[] objArr, int i2, int i3, x xVar, int[] iArr2, int i4, int i5, g7 g7Var, g4 g4Var, ag agVar, j jVar, q4 q4Var) {
        boolean z;
        this.a = iArr;
        this.b = objArr;
        this.c = i2;
        this.d = i3;
        this.g = xVar instanceof n;
        if (jVar == null || !jVar.e(xVar)) {
            z = false;
        } else {
            z = true;
        }
        this.f = z;
        this.h = false;
        this.i = iArr2;
        this.j = i4;
        this.k = i5;
        this.l = g7Var;
        this.m = g4Var;
        this.n = agVar;
        this.o = jVar;
        this.e = xVar;
        this.p = q4Var;
    }

    /* JADX WARNING: Removed duplicated region for block: B:119:0x0259  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x025c  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0274  */
    /* JADX WARNING: Removed duplicated region for block: B:124:0x0277  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x032d  */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x032f  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0336  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x037c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> com.google.protobuf.z<T> aa(defpackage.x9 r32, defpackage.g7 r33, defpackage.g4 r34, com.google.protobuf.ag<?, ?> r35, com.google.protobuf.j<?> r36, defpackage.q4 r37) {
        /*
            java.lang.String r0 = r32.e()
            int r1 = r0.length()
            r2 = 0
            char r3 = r0.charAt(r2)
            r5 = 55296(0xd800, float:7.7486E-41)
            if (r3 < r5) goto L_0x001d
            r3 = 1
        L_0x0013:
            int r6 = r3 + 1
            char r3 = r0.charAt(r3)
            if (r3 < r5) goto L_0x001e
            r3 = r6
            goto L_0x0013
        L_0x001d:
            r6 = 1
        L_0x001e:
            int r3 = r6 + 1
            char r6 = r0.charAt(r6)
            if (r6 < r5) goto L_0x003d
            r6 = r6 & 8191(0x1fff, float:1.1478E-41)
            r8 = 13
        L_0x002a:
            int r9 = r3 + 1
            char r3 = r0.charAt(r3)
            if (r3 < r5) goto L_0x003a
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r8
            r6 = r6 | r3
            int r8 = r8 + 13
            r3 = r9
            goto L_0x002a
        L_0x003a:
            int r3 = r3 << r8
            r6 = r6 | r3
            r3 = r9
        L_0x003d:
            if (r6 != 0) goto L_0x004b
            int[] r6 = q
            r14 = r6
            r6 = 0
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            r13 = 0
            r15 = 0
            goto L_0x0154
        L_0x004b:
            int r6 = r3 + 1
            char r3 = r0.charAt(r3)
            if (r3 < r5) goto L_0x006a
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            r8 = 13
        L_0x0057:
            int r9 = r6 + 1
            char r6 = r0.charAt(r6)
            if (r6 < r5) goto L_0x0067
            r6 = r6 & 8191(0x1fff, float:1.1478E-41)
            int r6 = r6 << r8
            r3 = r3 | r6
            int r8 = r8 + 13
            r6 = r9
            goto L_0x0057
        L_0x0067:
            int r6 = r6 << r8
            r3 = r3 | r6
            r6 = r9
        L_0x006a:
            int r8 = r6 + 1
            char r6 = r0.charAt(r6)
            if (r6 < r5) goto L_0x0089
            r6 = r6 & 8191(0x1fff, float:1.1478E-41)
            r9 = 13
        L_0x0076:
            int r10 = r8 + 1
            char r8 = r0.charAt(r8)
            if (r8 < r5) goto L_0x0086
            r8 = r8 & 8191(0x1fff, float:1.1478E-41)
            int r8 = r8 << r9
            r6 = r6 | r8
            int r9 = r9 + 13
            r8 = r10
            goto L_0x0076
        L_0x0086:
            int r8 = r8 << r9
            r6 = r6 | r8
            r8 = r10
        L_0x0089:
            int r9 = r8 + 1
            char r8 = r0.charAt(r8)
            if (r8 < r5) goto L_0x00a8
            r8 = r8 & 8191(0x1fff, float:1.1478E-41)
            r10 = 13
        L_0x0095:
            int r11 = r9 + 1
            char r9 = r0.charAt(r9)
            if (r9 < r5) goto L_0x00a5
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            int r9 = r9 << r10
            r8 = r8 | r9
            int r10 = r10 + 13
            r9 = r11
            goto L_0x0095
        L_0x00a5:
            int r9 = r9 << r10
            r8 = r8 | r9
            r9 = r11
        L_0x00a8:
            int r10 = r9 + 1
            char r9 = r0.charAt(r9)
            if (r9 < r5) goto L_0x00c7
            r9 = r9 & 8191(0x1fff, float:1.1478E-41)
            r11 = 13
        L_0x00b4:
            int r12 = r10 + 1
            char r10 = r0.charAt(r10)
            if (r10 < r5) goto L_0x00c4
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            int r10 = r10 << r11
            r9 = r9 | r10
            int r11 = r11 + 13
            r10 = r12
            goto L_0x00b4
        L_0x00c4:
            int r10 = r10 << r11
            r9 = r9 | r10
            r10 = r12
        L_0x00c7:
            int r11 = r10 + 1
            char r10 = r0.charAt(r10)
            if (r10 < r5) goto L_0x00e6
            r10 = r10 & 8191(0x1fff, float:1.1478E-41)
            r12 = 13
        L_0x00d3:
            int r13 = r11 + 1
            char r11 = r0.charAt(r11)
            if (r11 < r5) goto L_0x00e3
            r11 = r11 & 8191(0x1fff, float:1.1478E-41)
            int r11 = r11 << r12
            r10 = r10 | r11
            int r12 = r12 + 13
            r11 = r13
            goto L_0x00d3
        L_0x00e3:
            int r11 = r11 << r12
            r10 = r10 | r11
            r11 = r13
        L_0x00e6:
            int r12 = r11 + 1
            char r11 = r0.charAt(r11)
            if (r11 < r5) goto L_0x0105
            r11 = r11 & 8191(0x1fff, float:1.1478E-41)
            r13 = 13
        L_0x00f2:
            int r14 = r12 + 1
            char r12 = r0.charAt(r12)
            if (r12 < r5) goto L_0x0102
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            int r12 = r12 << r13
            r11 = r11 | r12
            int r13 = r13 + 13
            r12 = r14
            goto L_0x00f2
        L_0x0102:
            int r12 = r12 << r13
            r11 = r11 | r12
            r12 = r14
        L_0x0105:
            int r13 = r12 + 1
            char r12 = r0.charAt(r12)
            if (r12 < r5) goto L_0x0124
            r12 = r12 & 8191(0x1fff, float:1.1478E-41)
            r14 = 13
        L_0x0111:
            int r15 = r13 + 1
            char r13 = r0.charAt(r13)
            if (r13 < r5) goto L_0x0121
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            int r13 = r13 << r14
            r12 = r12 | r13
            int r14 = r14 + 13
            r13 = r15
            goto L_0x0111
        L_0x0121:
            int r13 = r13 << r14
            r12 = r12 | r13
            r13 = r15
        L_0x0124:
            int r14 = r13 + 1
            char r13 = r0.charAt(r13)
            if (r13 < r5) goto L_0x0145
            r13 = r13 & 8191(0x1fff, float:1.1478E-41)
            r15 = 13
        L_0x0130:
            int r16 = r14 + 1
            char r14 = r0.charAt(r14)
            if (r14 < r5) goto L_0x0141
            r14 = r14 & 8191(0x1fff, float:1.1478E-41)
            int r14 = r14 << r15
            r13 = r13 | r14
            int r15 = r15 + 13
            r14 = r16
            goto L_0x0130
        L_0x0141:
            int r14 = r14 << r15
            r13 = r13 | r14
            r14 = r16
        L_0x0145:
            int r15 = r13 + r11
            int r15 = r15 + r12
            int[] r12 = new int[r15]
            int r15 = r3 * 2
            int r15 = r15 + r6
            r6 = r3
            r3 = r14
            r14 = r12
            r12 = r8
            r8 = r15
            r15 = r13
            r13 = r9
        L_0x0154:
            java.lang.Object[] r9 = r32.d()
            com.google.protobuf.x r16 = r32.b()
            java.lang.Class r2 = r16.getClass()
            int r7 = r10 * 3
            int[] r7 = new int[r7]
            int r10 = r10 * 2
            java.lang.Object[] r10 = new java.lang.Object[r10]
            int r18 = r15 + r11
            r20 = r15
            r21 = r18
            r11 = 0
            r19 = 0
        L_0x0171:
            if (r3 >= r1) goto L_0x03d3
            int r22 = r3 + 1
            char r3 = r0.charAt(r3)
            if (r3 < r5) goto L_0x0199
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            r4 = r22
            r22 = 13
        L_0x0181:
            int r24 = r4 + 1
            char r4 = r0.charAt(r4)
            if (r4 < r5) goto L_0x0193
            r4 = r4 & 8191(0x1fff, float:1.1478E-41)
            int r4 = r4 << r22
            r3 = r3 | r4
            int r22 = r22 + 13
            r4 = r24
            goto L_0x0181
        L_0x0193:
            int r4 = r4 << r22
            r3 = r3 | r4
            r4 = r24
            goto L_0x019b
        L_0x0199:
            r4 = r22
        L_0x019b:
            int r22 = r4 + 1
            char r4 = r0.charAt(r4)
            if (r4 < r5) goto L_0x01c8
            r4 = r4 & 8191(0x1fff, float:1.1478E-41)
            r5 = r22
            r22 = 13
        L_0x01a9:
            int r25 = r5 + 1
            char r5 = r0.charAt(r5)
            r26 = r1
            r1 = 55296(0xd800, float:7.7486E-41)
            if (r5 < r1) goto L_0x01c2
            r1 = r5 & 8191(0x1fff, float:1.1478E-41)
            int r1 = r1 << r22
            r4 = r4 | r1
            int r22 = r22 + 13
            r5 = r25
            r1 = r26
            goto L_0x01a9
        L_0x01c2:
            int r1 = r5 << r22
            r4 = r4 | r1
            r1 = r25
            goto L_0x01cc
        L_0x01c8:
            r26 = r1
            r1 = r22
        L_0x01cc:
            r5 = r4 & 255(0xff, float:3.57E-43)
            r22 = r15
            r15 = r4 & 1024(0x400, float:1.435E-42)
            if (r15 == 0) goto L_0x01da
            int r15 = r19 + 1
            r14[r19] = r11
            r19 = r15
        L_0x01da:
            r15 = 51
            r28 = r13
            sun.misc.Unsafe r13 = r
            if (r5 < r15) goto L_0x028e
            int r15 = r1 + 1
            char r1 = r0.charAt(r1)
            r29 = r15
            r15 = 55296(0xd800, float:7.7486E-41)
            if (r1 < r15) goto L_0x0214
            r1 = r1 & 8191(0x1fff, float:1.1478E-41)
            r15 = r29
            r29 = 13
        L_0x01f5:
            int r30 = r15 + 1
            char r15 = r0.charAt(r15)
            r31 = r12
            r12 = 55296(0xd800, float:7.7486E-41)
            if (r15 < r12) goto L_0x020e
            r12 = r15 & 8191(0x1fff, float:1.1478E-41)
            int r12 = r12 << r29
            r1 = r1 | r12
            int r29 = r29 + 13
            r15 = r30
            r12 = r31
            goto L_0x01f5
        L_0x020e:
            int r12 = r15 << r29
            r1 = r1 | r12
            r15 = r30
            goto L_0x0218
        L_0x0214:
            r31 = r12
            r15 = r29
        L_0x0218:
            int r12 = r5 + -51
            r29 = r15
            r15 = 9
            if (r12 == r15) goto L_0x0244
            r15 = 17
            if (r12 != r15) goto L_0x0225
            goto L_0x0244
        L_0x0225:
            r15 = 12
            if (r12 != r15) goto L_0x0251
            int r12 = r32.c()
            r15 = 1
            boolean r12 = defpackage.y2.a(r12, r15)
            if (r12 != 0) goto L_0x0238
            r12 = r4 & 2048(0x800, float:2.87E-42)
            if (r12 == 0) goto L_0x0251
        L_0x0238:
            int r12 = r11 / 3
            int r12 = r12 * 2
            int r12 = r12 + r15
            int r15 = r8 + 1
            r8 = r9[r8]
            r10[r12] = r8
            goto L_0x0250
        L_0x0244:
            int r12 = r11 / 3
            int r12 = r12 * 2
            r15 = 1
            int r12 = r12 + r15
            int r15 = r8 + 1
            r8 = r9[r8]
            r10[r12] = r8
        L_0x0250:
            r8 = r15
        L_0x0251:
            int r1 = r1 * 2
            r12 = r9[r1]
            boolean r15 = r12 instanceof java.lang.reflect.Field
            if (r15 == 0) goto L_0x025c
            java.lang.reflect.Field r12 = (java.lang.reflect.Field) r12
            goto L_0x0264
        L_0x025c:
            java.lang.String r12 = (java.lang.String) r12
            java.lang.reflect.Field r12 = am(r2, r12)
            r9[r1] = r12
        L_0x0264:
            r15 = r7
            r25 = r8
            long r7 = r13.objectFieldOffset(r12)
            int r8 = (int) r7
            int r1 = r1 + 1
            r7 = r9[r1]
            boolean r12 = r7 instanceof java.lang.reflect.Field
            if (r12 == 0) goto L_0x0277
            java.lang.reflect.Field r7 = (java.lang.reflect.Field) r7
            goto L_0x027f
        L_0x0277:
            java.lang.String r7 = (java.lang.String) r7
            java.lang.reflect.Field r7 = am(r2, r7)
            r9[r1] = r7
        L_0x027f:
            long r12 = r13.objectFieldOffset(r7)
            int r1 = (int) r12
            r7 = r25
            r27 = r29
            r25 = r3
            r3 = r1
            r1 = 0
            goto L_0x0390
        L_0x028e:
            r15 = r7
            r31 = r12
            int r7 = r8 + 1
            r8 = r9[r8]
            java.lang.String r8 = (java.lang.String) r8
            java.lang.reflect.Field r8 = am(r2, r8)
            r12 = 9
            if (r5 == r12) goto L_0x0314
            r12 = 17
            if (r5 != r12) goto L_0x02a5
            goto L_0x0314
        L_0x02a5:
            r12 = 27
            if (r5 == r12) goto L_0x0302
            r12 = 49
            if (r5 != r12) goto L_0x02ae
            goto L_0x0302
        L_0x02ae:
            r12 = 12
            if (r5 == r12) goto L_0x02e9
            r12 = 30
            if (r5 == r12) goto L_0x02e9
            r12 = 44
            if (r5 != r12) goto L_0x02bb
            goto L_0x02e9
        L_0x02bb:
            r12 = 50
            if (r5 != r12) goto L_0x02e5
            int r12 = r20 + 1
            r14[r20] = r11
            int r20 = r11 / 3
            int r20 = r20 * 2
            int r25 = r7 + 1
            r7 = r9[r7]
            r10[r20] = r7
            r7 = r4 & 2048(0x800, float:2.87E-42)
            if (r7 == 0) goto L_0x02de
            int r20 = r20 + 1
            int r7 = r25 + 1
            r25 = r9[r25]
            r10[r20] = r25
            r25 = r3
            r20 = r12
            goto L_0x0322
        L_0x02de:
            r20 = r12
            r7 = r25
            r25 = r3
            goto L_0x0322
        L_0x02e5:
            r25 = r3
            r3 = 1
            goto L_0x0322
        L_0x02e9:
            int r12 = r32.c()
            r25 = r3
            r3 = 1
            if (r12 == r3) goto L_0x02f6
            r12 = r4 & 2048(0x800, float:2.87E-42)
            if (r12 == 0) goto L_0x0322
        L_0x02f6:
            int r12 = r11 / 3
            int r12 = r12 * 2
            int r12 = r12 + r3
            int r23 = r7 + 1
            r7 = r9[r7]
            r10[r12] = r7
            goto L_0x0310
        L_0x0302:
            r25 = r3
            r3 = 1
            int r12 = r11 / 3
            int r12 = r12 * 2
            int r12 = r12 + r3
            int r23 = r7 + 1
            r7 = r9[r7]
            r10[r12] = r7
        L_0x0310:
            r12 = r4
            r7 = r23
            goto L_0x0323
        L_0x0314:
            r25 = r3
            r3 = 1
            int r12 = r11 / 3
            int r12 = r12 * 2
            int r12 = r12 + r3
            java.lang.Class r23 = r8.getType()
            r10[r12] = r23
        L_0x0322:
            r12 = r4
        L_0x0323:
            long r3 = r13.objectFieldOffset(r8)
            int r8 = (int) r3
            r4 = r12
            r3 = r4 & 4096(0x1000, float:5.74E-42)
            if (r3 == 0) goto L_0x032f
            r3 = 1
            goto L_0x0330
        L_0x032f:
            r3 = 0
        L_0x0330:
            if (r3 == 0) goto L_0x037c
            r3 = 17
            if (r5 > r3) goto L_0x037c
            int r3 = r1 + 1
            char r1 = r0.charAt(r1)
            r12 = 55296(0xd800, float:7.7486E-41)
            if (r1 < r12) goto L_0x035b
            r1 = r1 & 8191(0x1fff, float:1.1478E-41)
            r24 = 13
        L_0x0345:
            int r27 = r3 + 1
            char r3 = r0.charAt(r3)
            if (r3 < r12) goto L_0x0357
            r3 = r3 & 8191(0x1fff, float:1.1478E-41)
            int r3 = r3 << r24
            r1 = r1 | r3
            int r24 = r24 + 13
            r3 = r27
            goto L_0x0345
        L_0x0357:
            int r3 = r3 << r24
            r1 = r1 | r3
            goto L_0x035d
        L_0x035b:
            r27 = r3
        L_0x035d:
            int r3 = r6 * 2
            int r24 = r1 / 32
            int r24 = r24 + r3
            r3 = r9[r24]
            boolean r12 = r3 instanceof java.lang.reflect.Field
            if (r12 == 0) goto L_0x036c
            java.lang.reflect.Field r3 = (java.lang.reflect.Field) r3
            goto L_0x0374
        L_0x036c:
            java.lang.String r3 = (java.lang.String) r3
            java.lang.reflect.Field r3 = am(r2, r3)
            r9[r24] = r3
        L_0x0374:
            long r12 = r13.objectFieldOffset(r3)
            int r3 = (int) r12
            int r1 = r1 % 32
            goto L_0x0382
        L_0x037c:
            r3 = 1048575(0xfffff, float:1.469367E-39)
            r27 = r1
            r1 = 0
        L_0x0382:
            r12 = 18
            if (r5 < r12) goto L_0x0390
            r12 = 49
            if (r5 > r12) goto L_0x0390
            int r12 = r21 + 1
            r14[r21] = r8
            r21 = r12
        L_0x0390:
            int r12 = r11 + 1
            r15[r11] = r25
            int r11 = r12 + 1
            r13 = r4 & 512(0x200, float:7.175E-43)
            if (r13 == 0) goto L_0x039d
            r13 = 536870912(0x20000000, float:1.0842022E-19)
            goto L_0x039e
        L_0x039d:
            r13 = 0
        L_0x039e:
            r24 = r0
            r0 = r4 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L_0x03a7
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            goto L_0x03a8
        L_0x03a7:
            r0 = 0
        L_0x03a8:
            r0 = r0 | r13
            r4 = r4 & 2048(0x800, float:2.87E-42)
            if (r4 == 0) goto L_0x03b0
            r4 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x03b1
        L_0x03b0:
            r4 = 0
        L_0x03b1:
            r0 = r0 | r4
            int r4 = r5 << 20
            r0 = r0 | r4
            r0 = r0 | r8
            r15[r12] = r0
            int r0 = r11 + 1
            int r1 = r1 << 20
            r1 = r1 | r3
            r15[r11] = r1
            r11 = r0
            r8 = r7
            r7 = r15
            r15 = r22
            r0 = r24
            r1 = r26
            r3 = r27
            r13 = r28
            r12 = r31
            r5 = 55296(0xd800, float:7.7486E-41)
            goto L_0x0171
        L_0x03d3:
            r31 = r12
            r28 = r13
            r22 = r15
            r15 = r7
            com.google.protobuf.z r0 = new com.google.protobuf.z
            com.google.protobuf.x r13 = r32.b()
            r8 = r0
            r9 = r15
            r11 = r31
            r12 = r28
            r15 = r22
            r16 = r18
            r17 = r33
            r18 = r34
            r19 = r35
            r20 = r36
            r21 = r37
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.aa(x9, g7, g4, com.google.protobuf.ag, com.google.protobuf.j, q4):com.google.protobuf.z");
    }

    public static long ab(int i2) {
        return (long) (i2 & 1048575);
    }

    public static int ac(long j2, Object obj) {
        return ((Integer) fd.p(j2, obj)).intValue();
    }

    public static long ad(long j2, Object obj) {
        return ((Long) fd.p(j2, obj)).longValue();
    }

    public static Field am(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e2) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            throw new RuntimeException("Field " + str + " for " + cls.getName() + " not found. Known fields are " + Arrays.toString(declaredFields), e2);
        }
    }

    public static void l(Object obj) {
        if (!t(obj)) {
            throw new IllegalArgumentException("Mutating immutable message: " + obj);
        }
    }

    public static ah q(Object obj) {
        n nVar = (n) obj;
        ah ahVar = nVar.unknownFields;
        if (ahVar != ah.f) {
            return ahVar;
        }
        ah ahVar2 = new ah();
        nVar.unknownFields = ahVar2;
        return ahVar2;
    }

    public static boolean t(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof n) {
            return ((n) obj).isMutable();
        }
        return true;
    }

    public final void a(T t, T t2) {
        l(t);
        t2.getClass();
        int i2 = 0;
        while (true) {
            int[] iArr = this.a;
            if (i2 < iArr.length) {
                int as = as(i2);
                long j2 = (long) (1048575 & as);
                int i3 = iArr[i2];
                switch ((as & 267386880) >>> 20) {
                    case 0:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.v(t, j2, fd.l(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 1:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.w(t, j2, fd.m(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 2:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.y(t, j2, fd.o(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 3:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.y(t, j2, fd.o(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 4:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.x(fd.n(j2, t2), j2, t);
                            an(i2, t);
                            break;
                        }
                    case 5:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.y(t, j2, fd.o(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 6:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.x(fd.n(j2, t2), j2, t);
                            an(i2, t);
                            break;
                        }
                    case 7:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.r(t, j2, fd.g(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 8:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.z(j2, t, fd.p(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 9:
                        w(i2, t, t2);
                        break;
                    case 10:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.z(j2, t, fd.p(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 11:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.x(fd.n(j2, t2), j2, t);
                            an(i2, t);
                            break;
                        }
                    case 12:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.x(fd.n(j2, t2), j2, t);
                            an(i2, t);
                            break;
                        }
                    case 13:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.x(fd.n(j2, t2), j2, t);
                            an(i2, t);
                            break;
                        }
                    case 14:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.y(t, j2, fd.o(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 15:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.x(fd.n(j2, t2), j2, t);
                            an(i2, t);
                            break;
                        }
                    case 16:
                        if (!r(i2, t2)) {
                            break;
                        } else {
                            fd.y(t, j2, fd.o(j2, t2));
                            an(i2, t);
                            break;
                        }
                    case 17:
                        w(i2, t, t2);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.m.b(j2, t, t2);
                        break;
                    case 50:
                        Class<?> cls = ad.a;
                        fd.z(j2, t, this.p.a(fd.p(j2, t), fd.p(j2, t2)));
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!u(i3, i2, t2)) {
                            break;
                        } else {
                            fd.z(j2, t, fd.p(j2, t2));
                            ao(i3, i2, t);
                            break;
                        }
                    case 60:
                        x(i2, t, t2);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!u(i3, i2, t2)) {
                            break;
                        } else {
                            fd.z(j2, t, fd.p(j2, t2));
                            ao(i3, i2, t);
                            break;
                        }
                    case 68:
                        x(i2, t, t2);
                        break;
                }
                i2 += 3;
            } else {
                Class<?> cls2 = ad.a;
                ag<?, ?> agVar = this.n;
                agVar.o(t, agVar.k(agVar.g(t), agVar.g(t2)));
                if (this.f) {
                    ad.ab(this.o, t, t2);
                    return;
                }
                return;
            }
        }
    }

    public final void ae(Object obj, byte[] bArr, int i2, int i3, int i4, long j2, d.a aVar) throws IOException {
        Object o2 = o(i4);
        Unsafe unsafe = r;
        Object object = unsafe.getObject(obj, j2);
        q4 q4Var = this.p;
        if (q4Var.g(object)) {
            w d2 = q4Var.d();
            q4Var.a(d2, object);
            unsafe.putObject(obj, j2, d2);
            object = d2;
        }
        q4Var.c(o2);
        q4Var.e(object);
        int ai = d.ai(bArr, i2, aVar);
        int i5 = aVar.a;
        if (i5 < 0 || i5 > i3 - ai) {
            throw q.h();
        }
        throw null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:0x02be, code lost:
        r0 = r6 + 4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02d8, code lost:
        r0 = r6 + 8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02da, code lost:
        r1 = r24 | r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x02dc, code lost:
        r6 = r0;
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x02de, code lost:
        r29 = r6;
        r6 = r0;
        r0 = r29;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02e3, code lost:
        r1 = r35;
        r2 = r4;
        r3 = r7;
        r4 = r12;
        r12 = r13;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02ed, code lost:
        r1 = r35;
        r5 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0397, code lost:
        if (r0 != r15) goto L_0x03e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x03e1, code lost:
        if (r0 != r15) goto L_0x03e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0143, code lost:
        r4 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0148, code lost:
        r4 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x01ae, code lost:
        r10 = r4;
        r13 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x0267, code lost:
        r10 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0268, code lost:
        r4 = r10;
     */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x0424  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x0434  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int af(T r31, byte[] r32, int r33, int r34, int r35, com.google.protobuf.d.a r36) throws java.io.IOException {
        /*
            r30 = this;
            r15 = r30
            r14 = r31
            r12 = r32
            r13 = r34
            r11 = r36
            l(r31)
            sun.misc.Unsafe r9 = r
            r0 = r33
            r1 = r35
            r2 = 0
            r3 = -1
            r4 = 0
            r5 = 1048575(0xfffff, float:1.469367E-39)
            r6 = 0
        L_0x001a:
            if (r0 >= r13) goto L_0x0458
            int r2 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0029
            int r0 = com.google.protobuf.d.ah(r0, r12, r2, r11)
            int r2 = r11.a
            goto L_0x002e
        L_0x0029:
            r29 = r2
            r2 = r0
            r0 = r29
        L_0x002e:
            int r7 = r2 >>> 3
            r10 = r2 & 7
            int r8 = r15.d
            r33 = r0
            int r0 = r15.c
            r19 = r1
            r1 = 3
            if (r7 <= r3) goto L_0x004d
            int r4 = r4 / r1
            if (r7 < r0) goto L_0x0049
            if (r7 > r8) goto L_0x0049
            int r0 = r15.ap(r7, r4)
            r4 = r0
            r3 = -1
            goto L_0x004b
        L_0x0049:
            r3 = -1
            r4 = -1
        L_0x004b:
            r8 = 0
            goto L_0x005b
        L_0x004d:
            if (r7 < r0) goto L_0x0057
            if (r7 > r8) goto L_0x0057
            r8 = 0
            int r0 = r15.ap(r7, r8)
            goto L_0x0059
        L_0x0057:
            r8 = 0
            r0 = -1
        L_0x0059:
            r4 = r0
            r3 = -1
        L_0x005b:
            if (r4 != r3) goto L_0x006a
            r4 = r2
            r24 = r6
            r13 = r12
            r1 = r19
            r12 = 0
            r17 = -1
            r6 = r33
            goto L_0x02f1
        L_0x006a:
            int r0 = r4 + 1
            int[] r3 = r15.a
            r0 = r3[r0]
            r18 = 267386880(0xff00000, float:2.3665827E-29)
            r18 = r0 & r18
            int r1 = r18 >>> 20
            r16 = 1048575(0xfffff, float:1.469367E-39)
            r8 = r0 & r16
            long r11 = (long) r8
            r8 = 17
            r21 = r2
            if (r1 > r8) goto L_0x0300
            int r8 = r4 + 2
            r3 = r3[r8]
            int r8 = r3 >>> 20
            r2 = 1
            int r8 = r2 << r8
            r22 = r11
            r11 = 1048575(0xfffff, float:1.469367E-39)
            r3 = r3 & r11
            if (r3 == r5) goto L_0x00ad
            if (r5 == r11) goto L_0x009e
            r16 = r3
            long r2 = (long) r5
            r9.putInt(r14, r2, r6)
            r2 = r16
            goto L_0x009f
        L_0x009e:
            r2 = r3
        L_0x009f:
            if (r2 != r11) goto L_0x00a3
            r3 = 0
            goto L_0x00a8
        L_0x00a3:
            long r5 = (long) r2
            int r3 = r9.getInt(r14, r5)
        L_0x00a8:
            r16 = r2
            r24 = r3
            goto L_0x00b1
        L_0x00ad:
            r16 = r5
            r24 = r6
        L_0x00b1:
            r2 = 5
            switch(r1) {
                case 0: goto L_0x02c1;
                case 1: goto L_0x02a8;
                case 2: goto L_0x0284;
                case 3: goto L_0x0284;
                case 4: goto L_0x026b;
                case 5: goto L_0x0246;
                case 6: goto L_0x022e;
                case 7: goto L_0x020b;
                case 8: goto L_0x01e1;
                case 9: goto L_0x01b2;
                case 10: goto L_0x0192;
                case 11: goto L_0x026b;
                case 12: goto L_0x014c;
                case 13: goto L_0x022e;
                case 14: goto L_0x0246;
                case 15: goto L_0x0126;
                case 16: goto L_0x00f9;
                case 17: goto L_0x00c2;
                default: goto L_0x00b5;
            }
        L_0x00b5:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r17 = -1
            goto L_0x02ed
        L_0x00c2:
            r1 = 3
            if (r10 != r1) goto L_0x00ef
            java.lang.Object r10 = r15.y(r4, r14)
            int r0 = r7 << 3
            r5 = r0 | 4
            com.google.protobuf.ac r1 = r15.p(r4)
            r6 = r33
            r0 = r10
            r12 = r21
            r2 = r32
            r17 = -1
            r3 = r6
            r6 = r4
            r4 = r34
            r12 = r6
            r6 = r36
            int r0 = com.google.protobuf.d.am(r0, r1, r2, r3, r4, r5, r6)
            r15.aq(r12, r14, r10)
            r1 = r24 | r8
            r13 = r32
            r11 = r36
            goto L_0x0143
        L_0x00ef:
            r6 = r33
            r12 = r4
            r17 = -1
            r13 = r32
            r11 = r36
            goto L_0x0148
        L_0x00f9:
            r6 = r33
            r12 = r4
            r17 = -1
            if (r10 != 0) goto L_0x011e
            r10 = r32
            r4 = r36
            r2 = r22
            int r6 = com.google.protobuf.d.ak(r10, r6, r4)
            long r0 = r4.b
            long r19 = com.google.protobuf.f.c(r0)
            r0 = r9
            r1 = r31
            r11 = r4
            r4 = r19
            r0.putLong(r1, r2, r4)
            r1 = r24 | r8
            r0 = r6
            r13 = r10
            goto L_0x0143
        L_0x011e:
            r11 = r36
            r13 = r32
            r10 = r21
            goto L_0x0268
        L_0x0126:
            r5 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r2 = r22
            r17 = -1
            if (r10 != 0) goto L_0x0147
            int r0 = com.google.protobuf.d.ai(r5, r6, r11)
            int r1 = r11.a
            int r1 = com.google.protobuf.f.b(r1)
            r9.putInt(r14, r2, r1)
            r1 = r24 | r8
            r13 = r5
        L_0x0143:
            r4 = r21
            goto L_0x02dc
        L_0x0147:
            r13 = r5
        L_0x0148:
            r4 = r21
            goto L_0x02ed
        L_0x014c:
            r5 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r2 = r22
            r1 = 1
            r17 = -1
            if (r10 != 0) goto L_0x01ae
            int r6 = com.google.protobuf.d.ai(r5, r6, r11)
            int r10 = r11.a
            com.google.protobuf.p$e r19 = r15.n(r12)
            r20 = -2147483648(0xffffffff80000000, float:-0.0)
            r0 = r0 & r20
            if (r0 == 0) goto L_0x016d
            goto L_0x016e
        L_0x016d:
            r1 = 0
        L_0x016e:
            if (r1 == 0) goto L_0x018b
            if (r19 == 0) goto L_0x018b
            boolean r0 = r19.a()
            if (r0 == 0) goto L_0x0179
            goto L_0x018b
        L_0x0179:
            com.google.protobuf.ah r0 = q(r31)
            long r1 = (long) r10
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
            r0.f(r4, r1)
            r13 = r5
            r0 = r6
            r6 = r24
            goto L_0x02e3
        L_0x018b:
            r9.putInt(r14, r2, r10)
            r13 = r5
            r0 = r6
            goto L_0x02da
        L_0x0192:
            r5 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r2 = r22
            r0 = 2
            r17 = -1
            if (r10 != r0) goto L_0x01ae
            int r0 = com.google.protobuf.d.b(r5, r6, r11)
            java.lang.Object r1 = r11.c
            r9.putObject(r14, r2, r1)
            r13 = r5
            goto L_0x02da
        L_0x01ae:
            r10 = r4
            r13 = r5
            goto L_0x0268
        L_0x01b2:
            r5 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r0 = 2
            r17 = -1
            if (r10 != r0) goto L_0x01de
            java.lang.Object r10 = r15.y(r12, r14)
            com.google.protobuf.ac r1 = r15.p(r12)
            r0 = r10
            r2 = r32
            r3 = r6
            r6 = r4
            r4 = r34
            r13 = r5
            r5 = r36
            int r0 = com.google.protobuf.d.an(r0, r1, r2, r3, r4, r5)
            r15.aq(r12, r14, r10)
            r1 = r24 | r8
            r4 = r6
            goto L_0x02dc
        L_0x01de:
            r13 = r5
            goto L_0x0267
        L_0x01e1:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r2 = r22
            r1 = 1
            r5 = 2
            r17 = -1
            if (r10 != r5) goto L_0x0267
            r5 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r0 & r5
            if (r0 == 0) goto L_0x01f8
            goto L_0x01f9
        L_0x01f8:
            r1 = 0
        L_0x01f9:
            if (r1 == 0) goto L_0x0200
            int r0 = com.google.protobuf.d.af(r13, r6, r11)
            goto L_0x0204
        L_0x0200:
            int r0 = com.google.protobuf.d.ac(r13, r6, r11)
        L_0x0204:
            java.lang.Object r1 = r11.c
            r9.putObject(r14, r2, r1)
            goto L_0x02da
        L_0x020b:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r2 = r22
            r1 = 1
            r17 = -1
            if (r10 != 0) goto L_0x0267
            int r0 = com.google.protobuf.d.ak(r13, r6, r11)
            long r5 = r11.b
            r19 = 0
            int r10 = (r5 > r19 ? 1 : (r5 == r19 ? 0 : -1))
            if (r10 == 0) goto L_0x0228
            goto L_0x0229
        L_0x0228:
            r1 = 0
        L_0x0229:
            defpackage.fd.r(r14, r2, r1)
            goto L_0x02da
        L_0x022e:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r0 = r22
            r17 = -1
            if (r10 != r2) goto L_0x0267
            int r2 = com.google.protobuf.d.h(r13, r6)
            r9.putInt(r14, r0, r2)
            goto L_0x02be
        L_0x0246:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r0 = r22
            r2 = 1
            r17 = -1
            if (r10 != r2) goto L_0x0267
            long r19 = com.google.protobuf.d.j(r13, r6)
            r2 = r0
            r0 = r9
            r1 = r31
            r10 = r4
            r4 = r19
            r0.putLong(r1, r2, r4)
            r4 = r10
            goto L_0x02d8
        L_0x0267:
            r10 = r4
        L_0x0268:
            r4 = r10
            goto L_0x02ed
        L_0x026b:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r2 = r22
            r17 = -1
            if (r10 != 0) goto L_0x02ed
            int r0 = com.google.protobuf.d.ai(r13, r6, r11)
            int r1 = r11.a
            r9.putInt(r14, r2, r1)
            goto L_0x02da
        L_0x0284:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r2 = r22
            r17 = -1
            if (r10 != 0) goto L_0x02ed
            int r6 = com.google.protobuf.d.ak(r13, r6, r11)
            long r0 = r11.b
            r19 = r0
            r0 = r9
            r1 = r31
            r10 = r4
            r4 = r19
            r0.putLong(r1, r2, r4)
            r0 = r24 | r8
            r4 = r10
            goto L_0x02de
        L_0x02a8:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r0 = r22
            r17 = -1
            if (r10 != r2) goto L_0x02ed
            float r2 = com.google.protobuf.d.l(r13, r6)
            defpackage.fd.w(r14, r0, r2)
        L_0x02be:
            int r0 = r6 + 4
            goto L_0x02da
        L_0x02c1:
            r13 = r32
            r6 = r33
            r11 = r36
            r12 = r4
            r4 = r21
            r0 = r22
            r2 = 1
            r17 = -1
            if (r10 != r2) goto L_0x02ed
            double r2 = com.google.protobuf.d.d(r13, r6)
            defpackage.fd.v(r14, r0, r2)
        L_0x02d8:
            int r0 = r6 + 8
        L_0x02da:
            r1 = r24 | r8
        L_0x02dc:
            r6 = r0
            r0 = r1
        L_0x02de:
            r29 = r6
            r6 = r0
            r0 = r29
        L_0x02e3:
            r1 = r35
            r2 = r4
            r3 = r7
            r4 = r12
            r12 = r13
            r5 = r16
            goto L_0x0351
        L_0x02ed:
            r1 = r35
            r5 = r16
        L_0x02f1:
            r8 = r4
            r16 = r5
            r2 = r6
            r27 = r7
            r28 = r9
            r20 = r24
            r18 = 0
            r7 = r1
            goto L_0x0404
        L_0x0300:
            r13 = r32
            r8 = r33
            r16 = r5
            r2 = r11
            r17 = -1
            r11 = r36
            r12 = r4
            r4 = r21
            r5 = 27
            if (r1 != r5) goto L_0x0364
            r5 = 2
            if (r10 != r5) goto L_0x0355
            java.lang.Object r0 = r9.getObject(r14, r2)
            com.google.protobuf.p$i r0 = (com.google.protobuf.p.i) r0
            boolean r1 = r0.y()
            if (r1 != 0) goto L_0x0333
            int r1 = r0.size()
            if (r1 != 0) goto L_0x032a
            r1 = 10
            goto L_0x032c
        L_0x032a:
            int r1 = r1 * 2
        L_0x032c:
            com.google.protobuf.p$i r0 = r0.h(r1)
            r9.putObject(r14, r2, r0)
        L_0x0333:
            r5 = r0
            com.google.protobuf.ac r0 = r15.p(r12)
            r1 = r4
            r2 = r32
            r3 = r8
            r10 = r4
            r4 = r34
            r20 = r6
            r6 = r36
            int r0 = com.google.protobuf.d.q(r0, r1, r2, r3, r4, r5, r6)
            r1 = r35
            r3 = r7
            r2 = r10
            r4 = r12
            r12 = r13
            r5 = r16
            r6 = r20
        L_0x0351:
            r13 = r34
            goto L_0x001a
        L_0x0355:
            r20 = r6
            r26 = r4
            r27 = r7
            r15 = r8
            r28 = r9
            r24 = r12
            r18 = 0
            goto L_0x03b2
        L_0x0364:
            r20 = r6
            r6 = r4
            r4 = 49
            if (r1 > r4) goto L_0x039a
            long r4 = (long) r0
            r0 = r30
            r21 = r1
            r1 = r31
            r22 = r2
            r2 = r32
            r3 = r8
            r24 = r4
            r4 = r34
            r5 = r6
            r26 = r6
            r6 = r7
            r27 = r7
            r7 = r10
            r10 = r8
            r18 = 0
            r8 = r12
            r28 = r9
            r15 = r10
            r9 = r24
            r11 = r21
            r24 = r12
            r12 = r22
            r14 = r36
            int r0 = r0.ah(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            if (r0 == r15) goto L_0x03fd
            goto L_0x03e3
        L_0x039a:
            r21 = r1
            r22 = r2
            r26 = r6
            r27 = r7
            r15 = r8
            r28 = r9
            r24 = r12
            r18 = 0
            r1 = 50
            r9 = r21
            if (r9 != r1) goto L_0x03c8
            r1 = 2
            if (r10 == r1) goto L_0x03b4
        L_0x03b2:
            r0 = r15
            goto L_0x03fd
        L_0x03b4:
            r0 = r30
            r1 = r31
            r2 = r32
            r3 = r15
            r4 = r34
            r5 = r24
            r6 = r22
            r8 = r36
            r0.ae(r1, r2, r3, r4, r5, r6, r8)
            r0 = 0
            throw r0
        L_0x03c8:
            r8 = r0
            r0 = r30
            r1 = r31
            r2 = r32
            r3 = r15
            r4 = r34
            r5 = r26
            r6 = r27
            r7 = r10
            r10 = r22
            r12 = r24
            r13 = r36
            int r0 = r0.ag(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 == r15) goto L_0x03fd
        L_0x03e3:
            r15 = r30
            r14 = r31
            r12 = r32
            r13 = r34
            r1 = r35
            r11 = r36
            r5 = r16
            r6 = r20
            r4 = r24
            r2 = r26
            r3 = r27
            r9 = r28
            goto L_0x001a
        L_0x03fd:
            r7 = r35
            r2 = r0
            r12 = r24
            r8 = r26
        L_0x0404:
            if (r8 != r7) goto L_0x0414
            if (r7 == 0) goto L_0x0414
            r1 = 1048575(0xfffff, float:1.469367E-39)
            r9 = r30
            r6 = r2
            r5 = r16
            r0 = r20
            goto L_0x046a
        L_0x0414:
            r9 = r30
            boolean r0 = r9.f
            r10 = r36
            if (r0 == 0) goto L_0x0434
            com.google.protobuf.i r0 = r10.d
            com.google.protobuf.i r1 = com.google.protobuf.i.a()
            if (r0 == r1) goto L_0x0434
            com.google.protobuf.x r5 = r9.e
            r0 = r8
            r1 = r32
            r3 = r34
            r4 = r31
            r6 = r36
            int r0 = com.google.protobuf.d.g(r0, r1, r2, r3, r4, r5, r6)
            goto L_0x0443
        L_0x0434:
            com.google.protobuf.ah r4 = q(r31)
            r0 = r8
            r1 = r32
            r3 = r34
            r5 = r36
            int r0 = com.google.protobuf.d.ag(r0, r1, r2, r3, r4, r5)
        L_0x0443:
            r14 = r31
            r13 = r34
            r1 = r7
            r2 = r8
            r15 = r9
            r11 = r10
            r4 = r12
            r5 = r16
            r6 = r20
            r3 = r27
            r9 = r28
            r12 = r32
            goto L_0x001a
        L_0x0458:
            r19 = r1
            r16 = r5
            r20 = r6
            r28 = r9
            r9 = r15
            r6 = r0
            r8 = r2
            r7 = r19
            r0 = r20
            r1 = 1048575(0xfffff, float:1.469367E-39)
        L_0x046a:
            if (r5 == r1) goto L_0x0475
            long r1 = (long) r5
            r10 = r31
            r3 = r28
            r3.putInt(r10, r1, r0)
            goto L_0x0477
        L_0x0475:
            r10 = r31
        L_0x0477:
            r11 = 0
            int r0 = r9.j
            r12 = r0
        L_0x047b:
            int r0 = r9.k
            if (r12 >= r0) goto L_0x0492
            int[] r0 = r9.i
            r2 = r0[r12]
            com.google.protobuf.ag<?, ?> r4 = r9.n
            r0 = r30
            r1 = r31
            r3 = r11
            r5 = r31
            r0.m(r1, r2, r3, r4, r5)
            int r12 = r12 + 1
            goto L_0x047b
        L_0x0492:
            if (r7 != 0) goto L_0x049e
            r0 = r34
            if (r6 != r0) goto L_0x0499
            goto L_0x04a4
        L_0x0499:
            com.google.protobuf.q r0 = com.google.protobuf.q.g()
            throw r0
        L_0x049e:
            r0 = r34
            if (r6 > r0) goto L_0x04a5
            if (r8 != r7) goto L_0x04a5
        L_0x04a4:
            return r6
        L_0x04a5:
            com.google.protobuf.q r0 = com.google.protobuf.q.g()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.af(java.lang.Object, byte[], int, int, int, com.google.protobuf.d$a):int");
    }

    public final int ag(T t, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j2, int i9, d.a aVar) throws IOException {
        T t2 = t;
        byte[] bArr2 = bArr;
        int i10 = i2;
        int i11 = i4;
        int i12 = i5;
        int i13 = i6;
        long j3 = j2;
        int i14 = i9;
        d.a aVar2 = aVar;
        long j4 = (long) (this.a[i14 + 2] & 1048575);
        Unsafe unsafe = r;
        switch (i8) {
            case 51:
                if (i13 == 1) {
                    unsafe.putObject(t2, j3, Double.valueOf(d.d(bArr, i2)));
                    int i15 = i10 + 8;
                    unsafe.putInt(t2, j4, i12);
                    return i15;
                }
                break;
            case 52:
                if (i13 == 5) {
                    unsafe.putObject(t2, j3, Float.valueOf(d.l(bArr, i2)));
                    int i16 = i10 + 4;
                    unsafe.putInt(t2, j4, i12);
                    return i16;
                }
                break;
            case 53:
            case 54:
                if (i13 == 0) {
                    int ak = d.ak(bArr2, i10, aVar2);
                    unsafe.putObject(t2, j3, Long.valueOf(aVar2.b));
                    unsafe.putInt(t2, j4, i12);
                    return ak;
                }
                break;
            case 55:
            case 62:
                if (i13 == 0) {
                    int ai = d.ai(bArr2, i10, aVar2);
                    unsafe.putObject(t2, j3, Integer.valueOf(aVar2.a));
                    unsafe.putInt(t2, j4, i12);
                    return ai;
                }
                break;
            case 56:
            case 65:
                if (i13 == 1) {
                    unsafe.putObject(t2, j3, Long.valueOf(d.j(bArr, i2)));
                    int i17 = i10 + 8;
                    unsafe.putInt(t2, j4, i12);
                    return i17;
                }
                break;
            case 57:
            case 64:
                if (i13 == 5) {
                    unsafe.putObject(t2, j3, Integer.valueOf(d.h(bArr, i2)));
                    int i18 = i10 + 4;
                    unsafe.putInt(t2, j4, i12);
                    return i18;
                }
                break;
            case 58:
                if (i13 == 0) {
                    int ak2 = d.ak(bArr2, i10, aVar2);
                    unsafe.putObject(t2, j3, Boolean.valueOf(aVar2.b != 0));
                    unsafe.putInt(t2, j4, i12);
                    return ak2;
                }
                break;
            case 59:
                if (i13 == 2) {
                    int ai2 = d.ai(bArr2, i10, aVar2);
                    int i19 = aVar2.a;
                    if (i19 == 0) {
                        unsafe.putObject(t2, j3, "");
                    } else if ((i7 & 536870912) == 0 || gd.f(bArr2, ai2, ai2 + i19)) {
                        unsafe.putObject(t2, j3, new String(bArr2, ai2, i19, p.a));
                        ai2 += i19;
                    } else {
                        throw q.c();
                    }
                    unsafe.putInt(t2, j4, i12);
                    return ai2;
                }
                break;
            case 60:
                if (i13 == 2) {
                    Object z = z(i12, i14, t2);
                    int an = d.an(z, p(i14), bArr, i2, i3, aVar);
                    ar(i12, t2, z, i14);
                    return an;
                }
                break;
            case 61:
                if (i13 == 2) {
                    int b2 = d.b(bArr2, i10, aVar2);
                    unsafe.putObject(t2, j3, aVar2.c);
                    unsafe.putInt(t2, j4, i12);
                    return b2;
                }
                break;
            case 63:
                if (i13 == 0) {
                    int ai3 = d.ai(bArr2, i10, aVar2);
                    int i20 = aVar2.a;
                    p.e n2 = n(i14);
                    if (n2 == null || n2.a()) {
                        unsafe.putObject(t2, j3, Integer.valueOf(i20));
                        unsafe.putInt(t2, j4, i12);
                    } else {
                        q(t).f(i11, Long.valueOf((long) i20));
                    }
                    return ai3;
                }
                break;
            case 66:
                if (i13 == 0) {
                    int ai4 = d.ai(bArr2, i10, aVar2);
                    unsafe.putObject(t2, j3, Integer.valueOf(f.b(aVar2.a)));
                    unsafe.putInt(t2, j4, i12);
                    return ai4;
                }
                break;
            case 67:
                if (i13 == 0) {
                    int ak3 = d.ak(bArr2, i10, aVar2);
                    unsafe.putObject(t2, j3, Long.valueOf(f.c(aVar2.b)));
                    unsafe.putInt(t2, j4, i12);
                    return ak3;
                }
                break;
            case 68:
                if (i13 == 3) {
                    Object z2 = z(i12, i14, t2);
                    int am = d.am(z2, p(i14), bArr, i2, i3, (i11 & -8) | 4, aVar);
                    ar(i12, t2, z2, i14);
                    return am;
                }
                break;
        }
        return i10;
    }

    public final int ah(T t, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7, long j2, int i8, long j3, d.a aVar) throws IOException {
        int i9;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i10 = i2;
        int i11 = i6;
        int i12 = i7;
        long j4 = j3;
        d.a aVar2 = aVar;
        Unsafe unsafe = r;
        p.i iVar = (p.i) unsafe.getObject(t, j4);
        if (!iVar.y()) {
            iVar = iVar.h(iVar.size() * 2);
            unsafe.putObject(t, j4, iVar);
        }
        switch (i8) {
            case 18:
            case 35:
                if (i11 == 2) {
                    return d.s(bArr, i10, iVar, aVar2);
                }
                if (i11 == 1) {
                    return d.e(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 19:
            case 36:
                if (i11 == 2) {
                    return d.v(bArr, i10, iVar, aVar2);
                }
                if (i11 == 5) {
                    return d.m(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i11 == 2) {
                    return d.z(bArr, i10, iVar, aVar2);
                }
                if (i11 == 0) {
                    return d.al(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i11 == 2) {
                    return d.y(bArr, i10, iVar, aVar2);
                }
                if (i11 == 0) {
                    return d.aj(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i11 == 2) {
                    return d.u(bArr, i10, iVar, aVar2);
                }
                if (i11 == 1) {
                    return d.k(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i11 == 2) {
                    return d.t(bArr, i10, iVar, aVar2);
                }
                if (i11 == 5) {
                    return d.i(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 25:
            case 42:
                if (i11 == 2) {
                    return d.r(bArr, i10, iVar, aVar2);
                }
                if (i11 == 0) {
                    return d.a(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 26:
                if (i11 == 2) {
                    if ((j2 & 536870912) == 0) {
                        return d.ad(i4, bArr, i2, i3, iVar, aVar);
                    }
                    return d.ae(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 27:
                if (i11 == 2) {
                    return d.q(p(i12), i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 28:
                if (i11 == 2) {
                    return d.c(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 30:
            case 44:
                if (i11 == 2) {
                    i9 = d.y(bArr, i10, iVar, aVar2);
                } else if (i11 == 0) {
                    i9 = d.aj(i4, bArr, i2, i3, iVar, aVar);
                }
                ad.aa(t, i5, iVar, n(i12), null, this.n);
                return i9;
            case 33:
            case 47:
                if (i11 == 2) {
                    return d.w(bArr, i10, iVar, aVar2);
                }
                if (i11 == 0) {
                    return d.aa(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 34:
            case 48:
                if (i11 == 2) {
                    return d.x(bArr, i10, iVar, aVar2);
                }
                if (i11 == 0) {
                    return d.ab(i4, bArr, i2, i3, iVar, aVar);
                }
                break;
            case 49:
                if (i11 == 3) {
                    return d.o(p(i12), i4, bArr, i2, i3, iVar, aVar);
                }
                break;
        }
        return i10;
    }

    public final <E> void ai(Object obj, long j2, y9 y9Var, ac<E> acVar, i iVar) throws IOException {
        int aa;
        p.i a2 = this.m.a(j2, obj);
        g gVar = (g) y9Var;
        int i2 = gVar.b;
        if ((i2 & 7) == 3) {
            do {
                E i3 = acVar.i();
                gVar.b(i3, acVar, iVar);
                acVar.b(i3);
                a2.add(i3);
                f fVar = gVar.a;
                if (!fVar.e() && gVar.d == 0) {
                    aa = fVar.aa();
                } else {
                    return;
                }
            } while (aa == i2);
            gVar.d = aa;
            return;
        }
        int i4 = q.f;
        throw new q.a();
    }

    public final <E> void aj(Object obj, int i2, y9 y9Var, ac<E> acVar, i iVar) throws IOException {
        int aa;
        p.i a2 = this.m.a((long) (i2 & 1048575), obj);
        g gVar = (g) y9Var;
        int i3 = gVar.b;
        if ((i3 & 7) == 2) {
            do {
                E i4 = acVar.i();
                gVar.c(i4, acVar, iVar);
                acVar.b(i4);
                a2.add(i4);
                f fVar = gVar.a;
                if (!fVar.e() && gVar.d == 0) {
                    aa = fVar.aa();
                } else {
                    return;
                }
            } while (aa == i3);
            gVar.d = aa;
            return;
        }
        int i5 = q.f;
        throw new q.a();
    }

    public final void ak(int i2, y9 y9Var, Object obj) throws IOException {
        boolean z;
        if ((536870912 & i2) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            g gVar = (g) y9Var;
            gVar.aa(2);
            fd.z((long) (i2 & 1048575), obj, gVar.a.z());
        } else if (this.g) {
            g gVar2 = (g) y9Var;
            gVar2.aa(2);
            fd.z((long) (i2 & 1048575), obj, gVar2.a.y());
        } else {
            fd.z((long) (i2 & 1048575), obj, ((g) y9Var).e());
        }
    }

    public final void al(int i2, y9 y9Var, Object obj) throws IOException {
        boolean z;
        if ((536870912 & i2) != 0) {
            z = true;
        } else {
            z = false;
        }
        g4 g4Var = this.m;
        if (z) {
            ((g) y9Var).v(g4Var.a((long) (i2 & 1048575), obj), true);
            return;
        }
        ((g) y9Var).v(g4Var.a((long) (i2 & 1048575), obj), false);
    }

    public final void an(int i2, Object obj) {
        int i3 = this.a[i2 + 2];
        long j2 = (long) (1048575 & i3);
        if (j2 != 1048575) {
            fd.x((1 << (i3 >>> 20)) | fd.n(j2, obj), j2, obj);
        }
    }

    public final void ao(int i2, int i3, Object obj) {
        fd.x(i2, (long) (this.a[i3 + 2] & 1048575), obj);
    }

    public final int ap(int i2, int i3) {
        int[] iArr = this.a;
        int length = (iArr.length / 3) - 1;
        while (i3 <= length) {
            int i4 = (length + i3) >>> 1;
            int i5 = i4 * 3;
            int i6 = iArr[i5];
            if (i2 == i6) {
                return i5;
            }
            if (i2 < i6) {
                length = i4 - 1;
            } else {
                i3 = i4 + 1;
            }
        }
        return -1;
    }

    public final void aq(int i2, Object obj, Object obj2) {
        r.putObject(obj, (long) (as(i2) & 1048575), obj2);
        an(i2, obj);
    }

    public final void ar(int i2, Object obj, Object obj2, int i3) {
        r.putObject(obj, (long) (as(i3) & 1048575), obj2);
        ao(i2, i3, obj);
    }

    public final int as(int i2) {
        return this.a[i2 + 1];
    }

    /* JADX WARNING: Removed duplicated region for block: B:186:0x0718  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void at(T r26, defpackage.jf r27) throws java.io.IOException {
        /*
            r25 = this;
            r6 = r25
            r7 = r26
            r8 = r27
            boolean r0 = r6.f
            com.google.protobuf.j<?> r10 = r6.o
            if (r0 == 0) goto L_0x0022
            com.google.protobuf.l r0 = r10.c(r7)
            boolean r1 = r0.h()
            if (r1 != 0) goto L_0x0022
            java.util.Iterator r0 = r0.k()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            r11 = r0
            goto L_0x0024
        L_0x0022:
            r1 = 0
            r11 = 0
        L_0x0024:
            int[] r12 = r6.a
            int r13 = r12.length
            r0 = 0
            r2 = 1048575(0xfffff, float:1.469367E-39)
            r5 = 0
        L_0x002c:
            if (r5 >= r13) goto L_0x0710
            int r3 = r6.as(r5)
            r4 = r12[r5]
            r16 = 267386880(0xff00000, float:2.3665827E-29)
            r16 = r3 & r16
            int r15 = r16 >>> 20
            r9 = 17
            sun.misc.Unsafe r14 = r
            if (r15 > r9) goto L_0x0070
            int r9 = r5 + 2
            r9 = r12[r9]
            r20 = r0
            r21 = r1
            r0 = 1048575(0xfffff, float:1.469367E-39)
            r1 = r9 & r0
            if (r1 == r2) goto L_0x005e
            r22 = r12
            r23 = r13
            if (r1 != r0) goto L_0x0057
            r0 = 0
            goto L_0x005c
        L_0x0057:
            long r12 = (long) r1
            int r0 = r14.getInt(r7, r12)
        L_0x005c:
            r2 = r1
            goto L_0x0064
        L_0x005e:
            r22 = r12
            r23 = r13
            r0 = r20
        L_0x0064:
            int r1 = r9 >>> 20
            r9 = 1
            int r1 = r9 << r1
            r20 = r0
            r13 = r1
            r12 = r2
            r9 = r21
            goto L_0x007c
        L_0x0070:
            r20 = r0
            r21 = r1
            r22 = r12
            r23 = r13
            r12 = r2
            r9 = r21
            r13 = 0
        L_0x007c:
            if (r9 == 0) goto L_0x0097
            int r0 = r10.a(r9)
            if (r0 > r4) goto L_0x0097
            r10.j(r8, r9)
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x0095
            java.lang.Object r0 = r11.next()
            r9 = r0
            java.util.Map$Entry r9 = (java.util.Map.Entry) r9
            goto L_0x007c
        L_0x0095:
            r9 = 0
            goto L_0x007c
        L_0x0097:
            r19 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r3 & r19
            long r2 = (long) r0
            switch(r15) {
                case 0: goto L_0x06db;
                case 1: goto L_0x06b6;
                case 2: goto L_0x0691;
                case 3: goto L_0x066b;
                case 4: goto L_0x0645;
                case 5: goto L_0x061f;
                case 6: goto L_0x05f9;
                case 7: goto L_0x05d3;
                case 8: goto L_0x059b;
                case 9: goto L_0x0571;
                case 10: goto L_0x0549;
                case 11: goto L_0x0523;
                case 12: goto L_0x04fd;
                case 13: goto L_0x04d7;
                case 14: goto L_0x04b1;
                case 15: goto L_0x048b;
                case 16: goto L_0x0465;
                case 17: goto L_0x043a;
                case 18: goto L_0x042b;
                case 19: goto L_0x041c;
                case 20: goto L_0x040d;
                case 21: goto L_0x03fe;
                case 22: goto L_0x03ef;
                case 23: goto L_0x03e0;
                case 24: goto L_0x03d1;
                case 25: goto L_0x03c2;
                case 26: goto L_0x03b4;
                case 27: goto L_0x03a2;
                case 28: goto L_0x0394;
                case 29: goto L_0x0385;
                case 30: goto L_0x0376;
                case 31: goto L_0x0367;
                case 32: goto L_0x0358;
                case 33: goto L_0x0349;
                case 34: goto L_0x033a;
                case 35: goto L_0x032b;
                case 36: goto L_0x031c;
                case 37: goto L_0x030d;
                case 38: goto L_0x02fe;
                case 39: goto L_0x02ef;
                case 40: goto L_0x02e0;
                case 41: goto L_0x02d1;
                case 42: goto L_0x02c2;
                case 43: goto L_0x02b3;
                case 44: goto L_0x02a4;
                case 45: goto L_0x0295;
                case 46: goto L_0x0286;
                case 47: goto L_0x0277;
                case 48: goto L_0x0268;
                case 49: goto L_0x0256;
                case 50: goto L_0x0217;
                case 51: goto L_0x01ff;
                case 52: goto L_0x01e7;
                case 53: goto L_0x01d5;
                case 54: goto L_0x01c3;
                case 55: goto L_0x01b1;
                case 56: goto L_0x019f;
                case 57: goto L_0x018d;
                case 58: goto L_0x0175;
                case 59: goto L_0x0151;
                case 60: goto L_0x013b;
                case 61: goto L_0x0127;
                case 62: goto L_0x0115;
                case 63: goto L_0x0104;
                case 64: goto L_0x00f3;
                case 65: goto L_0x00e2;
                case 66: goto L_0x00d1;
                case 67: goto L_0x00c0;
                case 68: goto L_0x00ab;
                default: goto L_0x00a0;
            }
        L_0x00a0:
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r11 = r5
            goto L_0x06ff
        L_0x00ab:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = r14.getObject(r7, r2)
            com.google.protobuf.ac r1 = r6.p(r5)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.i(r4, r1, r0)
            goto L_0x00a0
        L_0x00c0:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            long r0 = ad(r2, r7)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.q(r4, r0)
            goto L_0x00a0
        L_0x00d1:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            int r0 = ac(r2, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.p(r4, r0)
            goto L_0x00a0
        L_0x00e2:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            long r0 = ad(r2, r7)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.o(r4, r0)
            goto L_0x00a0
        L_0x00f3:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            int r0 = ac(r2, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.n(r4, r0)
            goto L_0x00a0
        L_0x0104:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            int r0 = ac(r2, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.e(r4, r0)
            goto L_0x00a0
        L_0x0115:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            int r0 = ac(r2, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.s(r4, r0)
            goto L_0x00a0
        L_0x0127:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = r14.getObject(r7, r2)
            cp r0 = (defpackage.cp) r0
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.b(r4, r0)
            goto L_0x00a0
        L_0x013b:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = r14.getObject(r7, r2)
            com.google.protobuf.ac r1 = r6.p(r5)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.l(r4, r1, r0)
            goto L_0x00a0
        L_0x0151:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = r14.getObject(r7, r2)
            boolean r1 = r0 instanceof java.lang.String
            if (r1 == 0) goto L_0x016b
            java.lang.String r0 = (java.lang.String) r0
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            n0 r1 = r1.a
            r1.aj(r4, r0)
            goto L_0x00a0
        L_0x016b:
            cp r0 = (defpackage.cp) r0
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.b(r4, r0)
            goto L_0x00a0
        L_0x0175:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = defpackage.fd.p(r2, r7)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.a(r4, r0)
            goto L_0x00a0
        L_0x018d:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            int r0 = ac(r2, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.f(r4, r0)
            goto L_0x00a0
        L_0x019f:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            long r0 = ad(r2, r7)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.g(r4, r0)
            goto L_0x00a0
        L_0x01b1:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            int r0 = ac(r2, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.j(r4, r0)
            goto L_0x00a0
        L_0x01c3:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            long r0 = ad(r2, r7)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.t(r4, r0)
            goto L_0x00a0
        L_0x01d5:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            long r0 = ad(r2, r7)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.k(r4, r0)
            goto L_0x00a0
        L_0x01e7:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = defpackage.fd.p(r2, r7)
            java.lang.Float r0 = (java.lang.Float) r0
            float r0 = r0.floatValue()
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.h(r0, r4)
            goto L_0x00a0
        L_0x01ff:
            boolean r0 = r6.u(r4, r5, r7)
            if (r0 == 0) goto L_0x00a0
            java.lang.Object r0 = defpackage.fd.p(r2, r7)
            java.lang.Double r0 = (java.lang.Double) r0
            double r0 = r0.doubleValue()
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.c(r4, r0)
            goto L_0x00a0
        L_0x0217:
            java.lang.Object r0 = r14.getObject(r7, r2)
            if (r0 == 0) goto L_0x0253
            java.lang.Object r1 = r6.o(r5)
            q4 r2 = r6.p
            r2.c(r1)
            com.google.protobuf.w r0 = r2.h(r0)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            n0 r1 = r1.a
            r1.getClass()
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
            boolean r2 = r0.hasNext()
            if (r2 != 0) goto L_0x0241
            goto L_0x0253
        L_0x0241:
            java.lang.Object r0 = r0.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            r2 = 2
            r1.ak(r4, r2)
            r0.getKey()
            r0.getValue()
            r15 = 0
            throw r15
        L_0x0253:
            r15 = 0
            goto L_0x00a0
        L_0x0256:
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ac r2 = r6.p(r5)
            com.google.protobuf.ad.al(r0, r1, r8, r2)
            goto L_0x00a0
        L_0x0268:
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            r4 = 1
            com.google.protobuf.ad.as(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0277:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ar(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0286:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.aq(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0295:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ap(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x02a4:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ah(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x02b3:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.au(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x02c2:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ae(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x02d1:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ai(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x02e0:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.aj(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x02ef:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.am(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x02fe:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.av(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x030d:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.an(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x031c:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ak(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x032b:
            r4 = 1
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ag(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x033a:
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            r4 = 0
            com.google.protobuf.ad.as(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0349:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ar(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0358:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.aq(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0367:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ap(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0376:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ah(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0385:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.au(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x0394:
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.af(r0, r1, r8)
            goto L_0x00a0
        L_0x03a2:
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ac r2 = r6.p(r5)
            com.google.protobuf.ad.ao(r0, r1, r8, r2)
            goto L_0x00a0
        L_0x03b4:
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.at(r0, r1, r8)
            goto L_0x00a0
        L_0x03c2:
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            r4 = 0
            com.google.protobuf.ad.ae(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x03d1:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ai(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x03e0:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.aj(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x03ef:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.am(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x03fe:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.av(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x040d:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.an(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x041c:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ak(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x042b:
            r4 = 0
            r15 = 0
            r0 = r22[r5]
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            com.google.protobuf.ad.ag(r0, r1, r8, r4)
            goto L_0x00a0
        L_0x043a:
            r15 = 0
            r16 = 0
            r0 = r25
            r1 = r26
            r18 = r9
            r17 = r10
            r9 = r2
            r2 = r5
            r3 = r12
            r15 = r4
            r4 = r20
            r24 = r11
            r11 = r5
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            java.lang.Object r0 = r14.getObject(r7, r9)
            com.google.protobuf.ac r1 = r6.p(r11)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.i(r15, r1, r0)
            goto L_0x06ff
        L_0x0465:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            long r0 = r14.getLong(r7, r9)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.q(r15, r0)
            goto L_0x06ff
        L_0x048b:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            int r0 = r14.getInt(r7, r9)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.p(r15, r0)
            goto L_0x06ff
        L_0x04b1:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            long r0 = r14.getLong(r7, r9)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.o(r15, r0)
            goto L_0x06ff
        L_0x04d7:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            int r0 = r14.getInt(r7, r9)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.n(r15, r0)
            goto L_0x06ff
        L_0x04fd:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            int r0 = r14.getInt(r7, r9)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.e(r15, r0)
            goto L_0x06ff
        L_0x0523:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            int r0 = r14.getInt(r7, r9)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.s(r15, r0)
            goto L_0x06ff
        L_0x0549:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            java.lang.Object r0 = r14.getObject(r7, r9)
            cp r0 = (defpackage.cp) r0
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.b(r15, r0)
            goto L_0x06ff
        L_0x0571:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            java.lang.Object r0 = r14.getObject(r7, r9)
            com.google.protobuf.ac r1 = r6.p(r11)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.l(r15, r1, r0)
            goto L_0x06ff
        L_0x059b:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            java.lang.Object r0 = r14.getObject(r7, r9)
            boolean r1 = r0 instanceof java.lang.String
            if (r1 == 0) goto L_0x05c9
            java.lang.String r0 = (java.lang.String) r0
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            n0 r1 = r1.a
            r1.aj(r15, r0)
            goto L_0x06ff
        L_0x05c9:
            cp r0 = (defpackage.cp) r0
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.b(r15, r0)
            goto L_0x06ff
        L_0x05d3:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            boolean r0 = defpackage.fd.g(r9, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.a(r15, r0)
            goto L_0x06ff
        L_0x05f9:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            int r0 = r14.getInt(r7, r9)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.f(r15, r0)
            goto L_0x06ff
        L_0x061f:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            long r0 = r14.getLong(r7, r9)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.g(r15, r0)
            goto L_0x06ff
        L_0x0645:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            int r0 = r14.getInt(r7, r9)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.j(r15, r0)
            goto L_0x06ff
        L_0x066b:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            long r0 = r14.getLong(r7, r9)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.t(r15, r0)
            goto L_0x06ff
        L_0x0691:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            long r0 = r14.getLong(r7, r9)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.k(r15, r0)
            goto L_0x06ff
        L_0x06b6:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            float r0 = defpackage.fd.m(r9, r7)
            r1 = r8
            o0 r1 = (defpackage.o0) r1
            r1.h(r0, r15)
            goto L_0x06ff
        L_0x06db:
            r15 = r4
            r18 = r9
            r17 = r10
            r24 = r11
            r16 = 0
            r9 = r2
            r11 = r5
            r0 = r25
            r1 = r26
            r2 = r11
            r3 = r12
            r4 = r20
            r5 = r13
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x06ff
            double r0 = defpackage.fd.l(r9, r7)
            r2 = r8
            o0 r2 = (defpackage.o0) r2
            r2.c(r15, r0)
        L_0x06ff:
            int r5 = r11 + 3
            r2 = r12
            r10 = r17
            r1 = r18
            r0 = r20
            r12 = r22
            r13 = r23
            r11 = r24
            goto L_0x002c
        L_0x0710:
            r21 = r1
            r17 = r10
            r24 = r11
        L_0x0716:
            if (r1 == 0) goto L_0x0730
            r0 = r17
            r0.j(r8, r1)
            boolean r1 = r24.hasNext()
            if (r1 == 0) goto L_0x072c
            java.lang.Object r1 = r24.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            r17 = r0
            goto L_0x0716
        L_0x072c:
            r17 = r0
            r1 = 0
            goto L_0x0716
        L_0x0730:
            com.google.protobuf.ag<?, ?> r0 = r6.n
            com.google.protobuf.ah r1 = r0.g(r7)
            r0.s(r1, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.at(java.lang.Object, jf):void");
    }

    public final void b(T t) {
        if (t(t)) {
            if (t instanceof n) {
                n nVar = (n) t;
                nVar.clearMemoizedSerializedSize();
                nVar.clearMemoizedHashCode();
                nVar.markImmutable();
            }
            int[] iArr = this.a;
            int length = iArr.length;
            for (int i2 = 0; i2 < length; i2 += 3) {
                int as = as(i2);
                long j2 = (long) (1048575 & as);
                int i3 = (as & 267386880) >>> 20;
                Unsafe unsafe = r;
                if (i3 != 9) {
                    if (i3 == 60 || i3 == 68) {
                        if (u(iArr[i2], i2, t)) {
                            p(i2).b(unsafe.getObject(t, j2));
                        }
                    } else {
                        switch (i3) {
                            case 17:
                                break;
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                            case 24:
                            case 25:
                            case 26:
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                            case 32:
                            case 33:
                            case 34:
                            case 35:
                            case 36:
                            case 37:
                            case 38:
                            case 39:
                            case 40:
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                                this.m.c(j2, t);
                                continue;
                            case 50:
                                Object object = unsafe.getObject(t, j2);
                                if (object != null) {
                                    unsafe.putObject(t, j2, this.p.b(object));
                                    break;
                                } else {
                                    continue;
                                }
                        }
                    }
                }
                if (r(i2, t)) {
                    p(i2).b(unsafe.getObject(t, j2));
                }
            }
            this.n.j(t);
            if (this.f) {
                this.o.f(t);
            }
        }
    }

    public final boolean c(T t) {
        int i2;
        int i3;
        boolean z;
        T t2 = t;
        int i4 = 1048575;
        int i5 = 0;
        int i6 = 0;
        while (true) {
            boolean z2 = true;
            if (i6 < this.j) {
                int i7 = this.i[i6];
                int[] iArr = this.a;
                int i8 = iArr[i7];
                int as = as(i7);
                int i9 = iArr[i7 + 2];
                int i10 = i9 & 1048575;
                int i11 = 1 << (i9 >>> 20);
                if (i10 != i4) {
                    if (i10 != 1048575) {
                        i5 = r.getInt(t2, (long) i10);
                    }
                    i2 = i5;
                    i3 = i10;
                } else {
                    i3 = i4;
                    i2 = i5;
                }
                if ((268435456 & as) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (z && !s(t, i7, i3, i2, i11)) {
                    return false;
                }
                int i12 = (267386880 & as) >>> 20;
                if (i12 != 9 && i12 != 17) {
                    if (i12 != 27) {
                        if (i12 == 60 || i12 == 68) {
                            if (u(i8, i7, t2) && !p(i7).c(fd.p((long) (as & 1048575), t2))) {
                                return false;
                            }
                        } else if (i12 != 49) {
                            if (i12 != 50) {
                                continue;
                            } else {
                                Object p2 = fd.p((long) (as & 1048575), t2);
                                q4 q4Var = this.p;
                                if (!q4Var.h(p2).isEmpty()) {
                                    q4Var.c(o(i7));
                                    throw null;
                                }
                            }
                        }
                    }
                    List list = (List) fd.p((long) (as & 1048575), t2);
                    if (!list.isEmpty()) {
                        ac p3 = p(i7);
                        int i13 = 0;
                        while (true) {
                            if (i13 >= list.size()) {
                                break;
                            } else if (!p3.c(list.get(i13))) {
                                z2 = false;
                                break;
                            } else {
                                i13++;
                            }
                        }
                    }
                    if (!z2) {
                        return false;
                    }
                } else if (s(t, i7, i3, i2, i11) && !p(i7).c(fd.p((long) (as & 1048575), t2))) {
                    return false;
                }
                i6++;
                i4 = i3;
                i5 = i2;
            } else if (!this.f || this.o.c(t2).i()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public final void d(T t, byte[] bArr, int i2, int i3, d.a aVar) throws IOException {
        af(t, bArr, i2, i3, 0, aVar);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: y9} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: com.google.protobuf.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: com.google.protobuf.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: com.google.protobuf.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: com.google.protobuf.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: com.google.protobuf.g} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v16, resolved type: com.google.protobuf.g} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Can't wrap try/catch for region: R(10:102|101|170|171|(0)|174|(0)|180|194|187) */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0403, code lost:
        r7 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x069b, code lost:
        r7 = r10.f(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x06a7, code lost:
        r14 = r22;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x06a9, code lost:
        if (r14 < r13) goto L_0x06ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x06ab, code lost:
        m(r24, r12[r14], r7, r10, r24);
        r14 = r14 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x06bb, code lost:
        if (r7 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x06c8, code lost:
        m(r24, r12[r14], r7, r10, r24);
        r14 = r14 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x06da, code lost:
        r10.n(r9, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x014c, code lost:
        r22 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01a3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:?, code lost:
        r6 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x027f, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0280, code lost:
        r15 = r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:170:0x0696 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x069b A[Catch:{ all -> 0x01a3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x06c8 A[LOOP:5: B:182:0x06c6->B:183:0x06c8, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:185:0x06da  */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x06a7 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:76:? A[ExcHandler: a (unused com.google.protobuf.q$a), PHI: r6 r22 
      PHI: (r6v5 com.google.protobuf.g) = (r6v7 com.google.protobuf.g), (r6v7 com.google.protobuf.g), (r6v16 com.google.protobuf.g) binds: [B:71:0x01b1, B:60:0x0154, B:2:0x001b] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r22v7 int) = (r22v1 int), (r22v73 int), (r22v80 int) binds: [B:2:0x001b, B:71:0x01b1, B:60:0x0154] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:60:0x0154] */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x027f A[Catch:{ a -> 0x02a5 }, ExcHandler: all (th java.lang.Throwable), PHI: r22 
      PHI: (r22v63 int) = (r22v69 int), (r22v73 int) binds: [B:79:0x0212, B:71:0x01b1] A[DONT_GENERATE, DONT_INLINE], Splitter:B:71:0x01b1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void e(T r24, defpackage.y9 r25, com.google.protobuf.i r26) throws java.io.IOException {
        /*
            r23 = this;
            r8 = r23
            r9 = r24
            r0 = r26
            r26.getClass()
            l(r24)
            com.google.protobuf.ag<?, ?> r10 = r8.n
            com.google.protobuf.j<?> r11 = r8.o
            int[] r12 = r8.i
            int r13 = r8.k
            int r14 = r8.j
            r7 = 0
            r16 = 0
        L_0x0019:
            r6 = r25
            com.google.protobuf.g r6 = (com.google.protobuf.g) r6     // Catch:{ all -> 0x0163 }
            int r2 = r6.a()     // Catch:{ all -> 0x0163 }
            int r1 = r8.c     // Catch:{ all -> 0x0163 }
            r5 = 0
            if (r2 < r1) goto L_0x002f
            int r1 = r8.d     // Catch:{ all -> 0x0163 }
            if (r2 > r1) goto L_0x002f
            int r1 = r8.ap(r2, r5)     // Catch:{ all -> 0x0163 }
            goto L_0x0030
        L_0x002f:
            r1 = -1
        L_0x0030:
            if (r1 >= 0) goto L_0x009c
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r2 != r1) goto L_0x0050
        L_0x0037:
            if (r14 >= r13) goto L_0x0049
            r3 = r12[r14]
            r1 = r23
            r2 = r24
            r4 = r7
            r5 = r10
            r6 = r24
            r1.m(r2, r3, r4, r5, r6)
            int r14 = r14 + 1
            goto L_0x0037
        L_0x0049:
            if (r7 == 0) goto L_0x06bf
        L_0x004b:
            r10.n(r9, r7)
            goto L_0x06bf
        L_0x0050:
            boolean r1 = r8.f     // Catch:{ all -> 0x0163 }
            if (r1 != 0) goto L_0x0056
            r3 = 0
            goto L_0x005d
        L_0x0056:
            com.google.protobuf.x r1 = r8.e     // Catch:{ all -> 0x0163 }
            com.google.protobuf.n$e r1 = r11.b(r0, r1, r2)     // Catch:{ all -> 0x0163 }
            r3 = r1
        L_0x005d:
            if (r3 == 0) goto L_0x0072
            if (r16 != 0) goto L_0x0065
            com.google.protobuf.l r16 = r11.d(r9)     // Catch:{ all -> 0x0163 }
        L_0x0065:
            r1 = r11
            r2 = r6
            r4 = r26
            r5 = r16
            r6 = r7
            java.lang.Object r1 = r1.g(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0163 }
            r7 = r1
            goto L_0x0019
        L_0x0072:
            r10.p()     // Catch:{ all -> 0x0163 }
            if (r7 != 0) goto L_0x007b
            com.google.protobuf.ah r7 = r10.f(r9)     // Catch:{ all -> 0x0163 }
        L_0x007b:
            boolean r1 = r10.l(r5, r6, r7)     // Catch:{ all -> 0x0163 }
            if (r1 == 0) goto L_0x0082
            goto L_0x0019
        L_0x0082:
            if (r14 >= r13) goto L_0x0094
            r3 = r12[r14]
            r1 = r23
            r2 = r24
            r4 = r7
            r5 = r10
            r6 = r24
            r1.m(r2, r3, r4, r5, r6)
            int r14 = r14 + 1
            goto L_0x0082
        L_0x0094:
            if (r7 == 0) goto L_0x06bf
            goto L_0x06bd
        L_0x0098:
            r22 = r14
            goto L_0x06c4
        L_0x009c:
            int r3 = r8.as(r1)     // Catch:{ all -> 0x0163 }
            r4 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = r4 & r3
            int r4 = r4 >>> 20
            r21 = 1048575(0xfffff, float:1.469367E-39)
            com.google.protobuf.f r5 = r6.a
            g4 r15 = r8.m
            switch(r4) {
                case 0: goto L_0x0660;
                case 1: goto L_0x0648;
                case 2: goto L_0x0634;
                case 3: goto L_0x061c;
                case 4: goto L_0x0608;
                case 5: goto L_0x05f4;
                case 6: goto L_0x05e0;
                case 7: goto L_0x05c8;
                case 8: goto L_0x05bb;
                case 9: goto L_0x05a0;
                case 10: goto L_0x058c;
                case 11: goto L_0x0578;
                case 12: goto L_0x054c;
                case 13: goto L_0x0534;
                case 14: goto L_0x051c;
                case 15: goto L_0x0504;
                case 16: goto L_0x04ec;
                case 17: goto L_0x04d1;
                case 18: goto L_0x04c0;
                case 19: goto L_0x04af;
                case 20: goto L_0x049e;
                case 21: goto L_0x048d;
                case 22: goto L_0x047c;
                case 23: goto L_0x046b;
                case 24: goto L_0x045a;
                case 25: goto L_0x0448;
                case 26: goto L_0x043e;
                case 27: goto L_0x0429;
                case 28: goto L_0x0418;
                case 29: goto L_0x0406;
                case 30: goto L_0x03e8;
                case 31: goto L_0x03d7;
                case 32: goto L_0x03c6;
                case 33: goto L_0x03b5;
                case 34: goto L_0x03a4;
                case 35: goto L_0x0393;
                case 36: goto L_0x0382;
                case 37: goto L_0x0371;
                case 38: goto L_0x0360;
                case 39: goto L_0x034f;
                case 40: goto L_0x033e;
                case 41: goto L_0x032d;
                case 42: goto L_0x031c;
                case 43: goto L_0x030a;
                case 44: goto L_0x02ed;
                case 45: goto L_0x02dc;
                case 46: goto L_0x02cb;
                case 47: goto L_0x02ba;
                case 48: goto L_0x02a8;
                case 49: goto L_0x0282;
                case 50: goto L_0x026f;
                case 51: goto L_0x0255;
                case 52: goto L_0x023b;
                case 53: goto L_0x0225;
                case 54: goto L_0x020c;
                case 55: goto L_0x01f7;
                case 56: goto L_0x01de;
                case 57: goto L_0x01c8;
                case 58: goto L_0x01af;
                case 59: goto L_0x01a6;
                case 60: goto L_0x018c;
                case 61: goto L_0x017b;
                case 62: goto L_0x0166;
                case 63: goto L_0x0132;
                case 64: goto L_0x0117;
                case 65: goto L_0x0100;
                case 66: goto L_0x00e9;
                case 67: goto L_0x00d2;
                case 68: goto L_0x00bc;
                default: goto L_0x00af;
            }
        L_0x00af:
            r22 = r14
            r17 = 0
            r14 = r6
            if (r7 != 0) goto L_0x0678
            com.google.protobuf.ah r1 = r10.f(r9)     // Catch:{ a -> 0x0696 }
            goto L_0x0677
        L_0x00bc:
            java.lang.Object r3 = r8.z(r2, r1, r9)     // Catch:{ a -> 0x012e }
            com.google.protobuf.x r3 = (com.google.protobuf.x) r3     // Catch:{ a -> 0x012e }
            com.google.protobuf.ac r4 = r8.p(r1)     // Catch:{ a -> 0x012e }
            r5 = 3
            r6.aa(r5)     // Catch:{ a -> 0x012e }
            r6.b(r3, r4, r0)     // Catch:{ a -> 0x012e }
            r8.ar(r2, r9, r3, r1)     // Catch:{ a -> 0x012e }
            goto L_0x014c
        L_0x00d2:
            long r3 = ab(r3)     // Catch:{ a -> 0x012e }
            r15 = 0
            r6.aa(r15)     // Catch:{ a -> 0x012e }
            long r19 = r5.x()     // Catch:{ a -> 0x012e }
            java.lang.Long r5 = java.lang.Long.valueOf(r19)     // Catch:{ a -> 0x012e }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x012e }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x012e }
            goto L_0x014c
        L_0x00e9:
            long r3 = ab(r3)     // Catch:{ a -> 0x012e }
            r15 = 0
            r6.aa(r15)     // Catch:{ a -> 0x012e }
            int r5 = r5.w()     // Catch:{ a -> 0x012e }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ a -> 0x012e }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x012e }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x012e }
            goto L_0x014c
        L_0x0100:
            long r3 = ab(r3)     // Catch:{ a -> 0x012e }
            r15 = 1
            r6.aa(r15)     // Catch:{ a -> 0x012e }
            long r19 = r5.v()     // Catch:{ a -> 0x012e }
            java.lang.Long r5 = java.lang.Long.valueOf(r19)     // Catch:{ a -> 0x012e }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x012e }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x012e }
            goto L_0x014c
        L_0x0117:
            long r3 = ab(r3)     // Catch:{ a -> 0x012e }
            r15 = 5
            r6.aa(r15)     // Catch:{ a -> 0x012e }
            int r5 = r5.u()     // Catch:{ a -> 0x012e }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ a -> 0x012e }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x012e }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x012e }
            goto L_0x014c
        L_0x012e:
            r22 = r14
            goto L_0x01f4
        L_0x0132:
            r4 = 0
            r6.aa(r4)     // Catch:{ a -> 0x012e }
            int r4 = r5.n()     // Catch:{ a -> 0x012e }
            com.google.protobuf.p$e r5 = r8.n(r1)     // Catch:{ a -> 0x012e }
            if (r5 == 0) goto L_0x0152
            boolean r5 = r5.a()     // Catch:{ a -> 0x012e }
            if (r5 == 0) goto L_0x0147
            goto L_0x0152
        L_0x0147:
            java.lang.Object r1 = com.google.protobuf.ad.ad(r9, r2, r4, r7, r10)     // Catch:{ a -> 0x012e }
            r7 = r1
        L_0x014c:
            r22 = r14
        L_0x014e:
            r17 = 0
            goto L_0x06c0
        L_0x0152:
            r22 = r14
            long r14 = ab(r3)     // Catch:{ a -> 0x01f4 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r4)     // Catch:{ a -> 0x01f4 }
            defpackage.fd.z(r14, r9, r3)     // Catch:{ a -> 0x01f4 }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4 }
            goto L_0x014e
        L_0x0163:
            r0 = move-exception
            goto L_0x0098
        L_0x0166:
            r22 = r14
            long r3 = ab(r3)     // Catch:{ a -> 0x01f4 }
            int r5 = r6.w()     // Catch:{ a -> 0x01f4 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ a -> 0x01f4 }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x01f4 }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4 }
            goto L_0x014e
        L_0x017b:
            r22 = r14
            long r3 = ab(r3)     // Catch:{ a -> 0x01f4 }
            cp r5 = r6.e()     // Catch:{ a -> 0x01f4 }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x01f4 }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4 }
            goto L_0x014e
        L_0x018c:
            r22 = r14
            java.lang.Object r3 = r8.z(r2, r1, r9)     // Catch:{ a -> 0x01f4 }
            com.google.protobuf.x r3 = (com.google.protobuf.x) r3     // Catch:{ a -> 0x01f4 }
            com.google.protobuf.ac r4 = r8.p(r1)     // Catch:{ a -> 0x01f4 }
            r5 = 2
            r6.aa(r5)     // Catch:{ a -> 0x01f4 }
            r6.c(r3, r4, r0)     // Catch:{ a -> 0x01f4 }
            r8.ar(r2, r9, r3, r1)     // Catch:{ a -> 0x01f4 }
            goto L_0x014e
        L_0x01a3:
            r0 = move-exception
            goto L_0x06c4
        L_0x01a6:
            r22 = r14
            r8.ak(r3, r6, r9)     // Catch:{ a -> 0x01f4 }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4 }
            goto L_0x014e
        L_0x01af:
            r22 = r14
            long r3 = ab(r3)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            r14 = 0
            r6.aa(r14)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            boolean r5 = r5.k()     // Catch:{ a -> 0x01f4, all -> 0x027f }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            goto L_0x014e
        L_0x01c8:
            r22 = r14
            long r3 = ab(r3)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            int r5 = r6.i()     // Catch:{ a -> 0x01f4, all -> 0x027f }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            goto L_0x014e
        L_0x01de:
            r22 = r14
            long r3 = ab(r3)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            long r14 = r6.k()     // Catch:{ a -> 0x01f4, all -> 0x027f }
            java.lang.Long r5 = java.lang.Long.valueOf(r14)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            goto L_0x014e
        L_0x01f4:
            r14 = 0
            goto L_0x027c
        L_0x01f7:
            r22 = r14
            r3 = r3 & r21
            long r3 = (long) r3     // Catch:{ a -> 0x01f4, all -> 0x027f }
            int r5 = r6.n()     // Catch:{ a -> 0x01f4, all -> 0x027f }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x01f4, all -> 0x027f }
            goto L_0x014e
        L_0x020c:
            r22 = r14
            r3 = r3 & r21
            long r3 = (long) r3
            r14 = 0
            r6.aa(r14)     // Catch:{ a -> 0x027c, all -> 0x027f }
            long r18 = r5.ac()     // Catch:{ a -> 0x027c, all -> 0x027f }
            java.lang.Long r5 = java.lang.Long.valueOf(r18)     // Catch:{ a -> 0x027c, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x027c, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x027c, all -> 0x027f }
            goto L_0x014e
        L_0x0225:
            r22 = r14
            r14 = 0
            r3 = r3 & r21
            long r3 = (long) r3     // Catch:{ a -> 0x027c, all -> 0x027f }
            long r18 = r6.p()     // Catch:{ a -> 0x027c, all -> 0x027f }
            java.lang.Long r5 = java.lang.Long.valueOf(r18)     // Catch:{ a -> 0x027c, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x027c, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x027c, all -> 0x027f }
            goto L_0x014e
        L_0x023b:
            r22 = r14
            r14 = 0
            r3 = r3 & r21
            long r3 = (long) r3     // Catch:{ a -> 0x027c, all -> 0x027f }
            r15 = 5
            r6.aa(r15)     // Catch:{ a -> 0x027c, all -> 0x027f }
            float r5 = r5.q()     // Catch:{ a -> 0x027c, all -> 0x027f }
            java.lang.Float r5 = java.lang.Float.valueOf(r5)     // Catch:{ a -> 0x027c, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x027c, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x027c, all -> 0x027f }
            goto L_0x014e
        L_0x0255:
            r22 = r14
            r14 = 0
            r3 = r3 & r21
            long r3 = (long) r3     // Catch:{ a -> 0x027c, all -> 0x027f }
            r15 = 1
            r6.aa(r15)     // Catch:{ a -> 0x027c, all -> 0x027f }
            double r18 = r5.m()     // Catch:{ a -> 0x027c, all -> 0x027f }
            java.lang.Double r5 = java.lang.Double.valueOf(r18)     // Catch:{ a -> 0x027c, all -> 0x027f }
            defpackage.fd.z(r3, r9, r5)     // Catch:{ a -> 0x027c, all -> 0x027f }
            r8.ao(r2, r1, r9)     // Catch:{ a -> 0x027c, all -> 0x027f }
            goto L_0x014e
        L_0x026f:
            r22 = r14
            r14 = 0
            java.lang.Object r2 = r8.o(r1)     // Catch:{ a -> 0x027c, all -> 0x027f }
            r8.v(r9, r1, r2, r6)     // Catch:{ a -> 0x027c }
            r17 = 0
            throw r17     // Catch:{ a -> 0x02a5 }
        L_0x027c:
            r17 = 0
            goto L_0x02a5
        L_0x027f:
            r0 = move-exception
            r15 = r7
            goto L_0x029f
        L_0x0282:
            r22 = r14
            r14 = 0
            r17 = 0
            r2 = r3 & r21
            long r3 = (long) r2     // Catch:{ a -> 0x02a5 }
            com.google.protobuf.ac r15 = r8.p(r1)     // Catch:{ a -> 0x02a5 }
            r1 = r23
            r2 = r24
            r5 = r6
            r14 = r6
            r6 = r15
            r15 = r7
            r7 = r26
            r1.ai(r2, r3, r5, r6, r7)     // Catch:{ a -> 0x02a2, all -> 0x029e }
            r7 = r15
            goto L_0x06c0
        L_0x029e:
            r0 = move-exception
        L_0x029f:
            r7 = r15
            goto L_0x06c4
        L_0x02a2:
            r7 = r15
            goto L_0x0696
        L_0x02a5:
            r14 = r6
            goto L_0x0696
        L_0x02a8:
            r22 = r14
            r17 = 0
            r14 = r6
            long r1 = ab(r3)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.u(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x02ba:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.t(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x02cb:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.s(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x02dc:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.r(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x02ed:
            r22 = r14
            r17 = 0
            r14 = r6
            r3 = r3 & r21
            long r3 = (long) r3     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r3 = r15.a(r3, r9)     // Catch:{ a -> 0x0696 }
            r14.h(r3)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$e r4 = r8.n(r1)     // Catch:{ a -> 0x0696 }
            r1 = r24
            r5 = r7
            r6 = r10
            java.lang.Object r1 = com.google.protobuf.ad.aa(r1, r2, r3, r4, r5, r6)     // Catch:{ a -> 0x0696 }
            goto L_0x0403
        L_0x030a:
            r22 = r14
            r17 = 0
            r14 = r6
            long r1 = ab(r3)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.x(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x031c:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.d(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x032d:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.j(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x033e:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.l(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x034f:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.o(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0360:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.y(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0371:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.q(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0382:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.m(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0393:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.g(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x03a4:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.u(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x03b5:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.t(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x03c6:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.s(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x03d7:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.r(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x03e8:
            r22 = r14
            r17 = 0
            r14 = r6
            r3 = r3 & r21
            long r3 = (long) r3     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r3 = r15.a(r3, r9)     // Catch:{ a -> 0x0696 }
            r14.h(r3)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$e r4 = r8.n(r1)     // Catch:{ a -> 0x0696 }
            r1 = r24
            r5 = r7
            r6 = r10
            java.lang.Object r1 = com.google.protobuf.ad.aa(r1, r2, r3, r4, r5, r6)     // Catch:{ a -> 0x0696 }
        L_0x0403:
            r7 = r1
            goto L_0x06c0
        L_0x0406:
            r22 = r14
            r17 = 0
            r14 = r6
            long r1 = ab(r3)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.x(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0418:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.f(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0429:
            r22 = r14
            r17 = 0
            r14 = r6
            com.google.protobuf.ac r5 = r8.p(r1)     // Catch:{ a -> 0x0696 }
            r1 = r23
            r2 = r24
            r4 = r14
            r6 = r26
            r1.aj(r2, r3, r4, r5, r6)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x043e:
            r22 = r14
            r17 = 0
            r14 = r6
            r8.al(r3, r14, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0448:
            r22 = r14
            r17 = 0
            r14 = r6
            long r1 = ab(r3)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.d(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x045a:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.j(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x046b:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.l(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x047c:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.o(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x048d:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.y(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x049e:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.q(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x04af:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.m(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x04c0:
            r22 = r14
            r17 = 0
            r14 = r6
            r1 = r3 & r21
            long r1 = (long) r1     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$i r1 = r15.a(r1, r9)     // Catch:{ a -> 0x0696 }
            r14.g(r1)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x04d1:
            r22 = r14
            r17 = 0
            r14 = r6
            java.lang.Object r2 = r8.y(r1, r9)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.x r2 = (com.google.protobuf.x) r2     // Catch:{ a -> 0x0696 }
            com.google.protobuf.ac r3 = r8.p(r1)     // Catch:{ a -> 0x0696 }
            r4 = 3
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            r14.b(r2, r3, r0)     // Catch:{ a -> 0x0696 }
            r8.aq(r1, r9, r2)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x04ec:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 0
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            long r4 = r5.x()     // Catch:{ a -> 0x0696 }
            defpackage.fd.y(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0504:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 0
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            int r4 = r5.w()     // Catch:{ a -> 0x0696 }
            defpackage.fd.x(r4, r2, r9)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x051c:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 1
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            long r4 = r5.v()     // Catch:{ a -> 0x0696 }
            defpackage.fd.y(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0534:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 5
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            int r4 = r5.u()     // Catch:{ a -> 0x0696 }
            defpackage.fd.x(r4, r2, r9)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x054c:
            r22 = r14
            r17 = 0
            r14 = r6
            r4 = 0
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            int r4 = r5.n()     // Catch:{ a -> 0x0696 }
            com.google.protobuf.p$e r5 = r8.n(r1)     // Catch:{ a -> 0x0696 }
            if (r5 == 0) goto L_0x056c
            boolean r5 = r5.a()     // Catch:{ a -> 0x0696 }
            if (r5 == 0) goto L_0x0566
            goto L_0x056c
        L_0x0566:
            java.lang.Object r7 = com.google.protobuf.ad.ad(r9, r2, r4, r7, r10)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x056c:
            long r2 = ab(r3)     // Catch:{ a -> 0x0696 }
            defpackage.fd.x(r4, r2, r9)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0578:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            int r4 = r14.w()     // Catch:{ a -> 0x0696 }
            defpackage.fd.x(r4, r2, r9)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x058c:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            cp r4 = r14.e()     // Catch:{ a -> 0x0696 }
            defpackage.fd.z(r2, r9, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x05a0:
            r22 = r14
            r17 = 0
            r14 = r6
            java.lang.Object r2 = r8.y(r1, r9)     // Catch:{ a -> 0x0696 }
            com.google.protobuf.x r2 = (com.google.protobuf.x) r2     // Catch:{ a -> 0x0696 }
            com.google.protobuf.ac r3 = r8.p(r1)     // Catch:{ a -> 0x0696 }
            r4 = 2
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            r14.c(r2, r3, r0)     // Catch:{ a -> 0x0696 }
            r8.aq(r1, r9, r2)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x05bb:
            r22 = r14
            r17 = 0
            r14 = r6
            r8.ak(r3, r14, r9)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x05c8:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 0
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            boolean r4 = r5.k()     // Catch:{ a -> 0x0696 }
            defpackage.fd.r(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x05e0:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            int r4 = r14.i()     // Catch:{ a -> 0x0696 }
            defpackage.fd.x(r4, r2, r9)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x05f4:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            long r4 = r14.k()     // Catch:{ a -> 0x0696 }
            defpackage.fd.y(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0608:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            int r4 = r14.n()     // Catch:{ a -> 0x0696 }
            defpackage.fd.x(r4, r2, r9)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x061c:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 0
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            long r4 = r5.ac()     // Catch:{ a -> 0x0696 }
            defpackage.fd.y(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0634:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            long r4 = r14.p()     // Catch:{ a -> 0x0696 }
            defpackage.fd.y(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0648:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 5
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            float r4 = r5.q()     // Catch:{ a -> 0x0696 }
            defpackage.fd.w(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0660:
            r22 = r14
            r17 = 0
            r14 = r6
            r2 = r3 & r21
            long r2 = (long) r2     // Catch:{ a -> 0x0696 }
            r4 = 1
            r14.aa(r4)     // Catch:{ a -> 0x0696 }
            double r4 = r5.m()     // Catch:{ a -> 0x0696 }
            defpackage.fd.v(r9, r2, r4)     // Catch:{ a -> 0x0696 }
            r8.an(r1, r9)     // Catch:{ a -> 0x0696 }
            goto L_0x06c0
        L_0x0677:
            r7 = r1
        L_0x0678:
            r1 = 0
            boolean r1 = r10.l(r1, r14, r7)     // Catch:{ a -> 0x0696 }
            if (r1 != 0) goto L_0x06c0
            r14 = r22
        L_0x0681:
            if (r14 >= r13) goto L_0x0693
            r3 = r12[r14]
            r1 = r23
            r2 = r24
            r4 = r7
            r5 = r10
            r6 = r24
            r1.m(r2, r3, r4, r5, r6)
            int r14 = r14 + 1
            goto L_0x0681
        L_0x0693:
            if (r7 == 0) goto L_0x06bf
            goto L_0x06bd
        L_0x0696:
            r10.p()     // Catch:{ all -> 0x01a3 }
            if (r7 != 0) goto L_0x06a0
            com.google.protobuf.ah r1 = r10.f(r9)     // Catch:{ all -> 0x01a3 }
            r7 = r1
        L_0x06a0:
            r1 = 0
            boolean r1 = r10.l(r1, r14, r7)     // Catch:{ all -> 0x01a3 }
            if (r1 != 0) goto L_0x06c0
            r14 = r22
        L_0x06a9:
            if (r14 >= r13) goto L_0x06bb
            r3 = r12[r14]
            r1 = r23
            r2 = r24
            r4 = r7
            r5 = r10
            r6 = r24
            r1.m(r2, r3, r4, r5, r6)
            int r14 = r14 + 1
            goto L_0x06a9
        L_0x06bb:
            if (r7 == 0) goto L_0x06bf
        L_0x06bd:
            goto L_0x004b
        L_0x06bf:
            return
        L_0x06c0:
            r14 = r22
            goto L_0x0019
        L_0x06c4:
            r14 = r22
        L_0x06c6:
            if (r14 >= r13) goto L_0x06d8
            r3 = r12[r14]
            r1 = r23
            r2 = r24
            r4 = r7
            r5 = r10
            r6 = r24
            r1.m(r2, r3, r4, r5, r6)
            int r14 = r14 + 1
            goto L_0x06c6
        L_0x06d8:
            if (r7 == 0) goto L_0x06dd
            r10.n(r9, r7)
        L_0x06dd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.e(java.lang.Object, y9, com.google.protobuf.i):void");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003d, code lost:
        if (com.google.protobuf.ad.ac(defpackage.fd.p(r7, r11), defpackage.fd.p(r7, r12)) != false) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006f, code lost:
        if (com.google.protobuf.ad.ac(defpackage.fd.p(r7, r11), defpackage.fd.p(r7, r12)) != false) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0083, code lost:
        if (defpackage.fd.o(r7, r11) == defpackage.fd.o(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0095, code lost:
        if (defpackage.fd.n(r7, r11) == defpackage.fd.n(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a9, code lost:
        if (defpackage.fd.o(r7, r11) == defpackage.fd.o(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00bb, code lost:
        if (defpackage.fd.n(r7, r11) == defpackage.fd.n(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00cd, code lost:
        if (defpackage.fd.n(r7, r11) == defpackage.fd.n(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00df, code lost:
        if (defpackage.fd.n(r7, r11) == defpackage.fd.n(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00f5, code lost:
        if (com.google.protobuf.ad.ac(defpackage.fd.p(r7, r11), defpackage.fd.p(r7, r12)) != false) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x010b, code lost:
        if (com.google.protobuf.ad.ac(defpackage.fd.p(r7, r11), defpackage.fd.p(r7, r12)) != false) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0121, code lost:
        if (com.google.protobuf.ad.ac(defpackage.fd.p(r7, r11), defpackage.fd.p(r7, r12)) != false) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x0133, code lost:
        if (defpackage.fd.g(r7, r11) == defpackage.fd.g(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0145, code lost:
        if (defpackage.fd.n(r7, r11) == defpackage.fd.n(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0159, code lost:
        if (defpackage.fd.o(r7, r11) == defpackage.fd.o(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x016b, code lost:
        if (defpackage.fd.n(r7, r11) == defpackage.fd.n(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x017e, code lost:
        if (defpackage.fd.o(r7, r11) == defpackage.fd.o(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0191, code lost:
        if (defpackage.fd.o(r7, r11) == defpackage.fd.o(r7, r12)) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01aa, code lost:
        if (java.lang.Float.floatToIntBits(defpackage.fd.m(r7, r11)) == java.lang.Float.floatToIntBits(defpackage.fd.m(r7, r12))) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01c5, code lost:
        if (java.lang.Double.doubleToLongBits(defpackage.fd.l(r7, r11)) == java.lang.Double.doubleToLongBits(defpackage.fd.l(r7, r12))) goto L_0x01c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01c8, code lost:
        r4 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean f(T r11, T r12) {
        /*
            r10 = this;
            int[] r0 = r10.a
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L_0x0005:
            r4 = 1
            if (r3 >= r1) goto L_0x01d0
            int r5 = r10.as(r3)
            r6 = 1048575(0xfffff, float:1.469367E-39)
            r7 = r5 & r6
            long r7 = (long) r7
            r9 = 267386880(0xff00000, float:2.3665827E-29)
            r5 = r5 & r9
            int r5 = r5 >>> 20
            switch(r5) {
                case 0: goto L_0x01ad;
                case 1: goto L_0x0194;
                case 2: goto L_0x0181;
                case 3: goto L_0x016e;
                case 4: goto L_0x015d;
                case 5: goto L_0x0149;
                case 6: goto L_0x0137;
                case 7: goto L_0x0125;
                case 8: goto L_0x010f;
                case 9: goto L_0x00f9;
                case 10: goto L_0x00e3;
                case 11: goto L_0x00d1;
                case 12: goto L_0x00bf;
                case 13: goto L_0x00ad;
                case 14: goto L_0x0099;
                case 15: goto L_0x0087;
                case 16: goto L_0x0073;
                case 17: goto L_0x005d;
                case 18: goto L_0x004f;
                case 19: goto L_0x004f;
                case 20: goto L_0x004f;
                case 21: goto L_0x004f;
                case 22: goto L_0x004f;
                case 23: goto L_0x004f;
                case 24: goto L_0x004f;
                case 25: goto L_0x004f;
                case 26: goto L_0x004f;
                case 27: goto L_0x004f;
                case 28: goto L_0x004f;
                case 29: goto L_0x004f;
                case 30: goto L_0x004f;
                case 31: goto L_0x004f;
                case 32: goto L_0x004f;
                case 33: goto L_0x004f;
                case 34: goto L_0x004f;
                case 35: goto L_0x004f;
                case 36: goto L_0x004f;
                case 37: goto L_0x004f;
                case 38: goto L_0x004f;
                case 39: goto L_0x004f;
                case 40: goto L_0x004f;
                case 41: goto L_0x004f;
                case 42: goto L_0x004f;
                case 43: goto L_0x004f;
                case 44: goto L_0x004f;
                case 45: goto L_0x004f;
                case 46: goto L_0x004f;
                case 47: goto L_0x004f;
                case 48: goto L_0x004f;
                case 49: goto L_0x004f;
                case 50: goto L_0x0041;
                case 51: goto L_0x001c;
                case 52: goto L_0x001c;
                case 53: goto L_0x001c;
                case 54: goto L_0x001c;
                case 55: goto L_0x001c;
                case 56: goto L_0x001c;
                case 57: goto L_0x001c;
                case 58: goto L_0x001c;
                case 59: goto L_0x001c;
                case 60: goto L_0x001c;
                case 61: goto L_0x001c;
                case 62: goto L_0x001c;
                case 63: goto L_0x001c;
                case 64: goto L_0x001c;
                case 65: goto L_0x001c;
                case 66: goto L_0x001c;
                case 67: goto L_0x001c;
                case 68: goto L_0x001c;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x01c9
        L_0x001c:
            int r5 = r3 + 2
            r5 = r0[r5]
            r5 = r5 & r6
            long r5 = (long) r5
            int r9 = defpackage.fd.n(r5, r11)
            int r5 = defpackage.fd.n(r5, r12)
            if (r9 != r5) goto L_0x002e
            r5 = 1
            goto L_0x002f
        L_0x002e:
            r5 = 0
        L_0x002f:
            if (r5 == 0) goto L_0x01c8
            java.lang.Object r5 = defpackage.fd.p(r7, r11)
            java.lang.Object r6 = defpackage.fd.p(r7, r12)
            boolean r5 = com.google.protobuf.ad.ac(r5, r6)
            if (r5 == 0) goto L_0x01c8
            goto L_0x01c9
        L_0x0041:
            java.lang.Object r4 = defpackage.fd.p(r7, r11)
            java.lang.Object r5 = defpackage.fd.p(r7, r12)
            boolean r4 = com.google.protobuf.ad.ac(r4, r5)
            goto L_0x01c9
        L_0x004f:
            java.lang.Object r4 = defpackage.fd.p(r7, r11)
            java.lang.Object r5 = defpackage.fd.p(r7, r12)
            boolean r4 = com.google.protobuf.ad.ac(r4, r5)
            goto L_0x01c9
        L_0x005d:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            java.lang.Object r5 = defpackage.fd.p(r7, r11)
            java.lang.Object r6 = defpackage.fd.p(r7, r12)
            boolean r5 = com.google.protobuf.ad.ac(r5, r6)
            if (r5 == 0) goto L_0x01c8
            goto L_0x01c9
        L_0x0073:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            long r5 = defpackage.fd.o(r7, r11)
            long r7 = defpackage.fd.o(r7, r12)
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x01c8
            goto L_0x01c9
        L_0x0087:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            int r5 = defpackage.fd.n(r7, r11)
            int r6 = defpackage.fd.n(r7, r12)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x0099:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            long r5 = defpackage.fd.o(r7, r11)
            long r7 = defpackage.fd.o(r7, r12)
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x01c8
            goto L_0x01c9
        L_0x00ad:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            int r5 = defpackage.fd.n(r7, r11)
            int r6 = defpackage.fd.n(r7, r12)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x00bf:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            int r5 = defpackage.fd.n(r7, r11)
            int r6 = defpackage.fd.n(r7, r12)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x00d1:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            int r5 = defpackage.fd.n(r7, r11)
            int r6 = defpackage.fd.n(r7, r12)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x00e3:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            java.lang.Object r5 = defpackage.fd.p(r7, r11)
            java.lang.Object r6 = defpackage.fd.p(r7, r12)
            boolean r5 = com.google.protobuf.ad.ac(r5, r6)
            if (r5 == 0) goto L_0x01c8
            goto L_0x01c9
        L_0x00f9:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            java.lang.Object r5 = defpackage.fd.p(r7, r11)
            java.lang.Object r6 = defpackage.fd.p(r7, r12)
            boolean r5 = com.google.protobuf.ad.ac(r5, r6)
            if (r5 == 0) goto L_0x01c8
            goto L_0x01c9
        L_0x010f:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            java.lang.Object r5 = defpackage.fd.p(r7, r11)
            java.lang.Object r6 = defpackage.fd.p(r7, r12)
            boolean r5 = com.google.protobuf.ad.ac(r5, r6)
            if (r5 == 0) goto L_0x01c8
            goto L_0x01c9
        L_0x0125:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            boolean r5 = defpackage.fd.g(r7, r11)
            boolean r6 = defpackage.fd.g(r7, r12)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x0137:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            int r5 = defpackage.fd.n(r7, r11)
            int r6 = defpackage.fd.n(r7, r12)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x0149:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            long r5 = defpackage.fd.o(r7, r11)
            long r7 = defpackage.fd.o(r7, r12)
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x01c8
            goto L_0x01c9
        L_0x015d:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            int r5 = defpackage.fd.n(r7, r11)
            int r6 = defpackage.fd.n(r7, r12)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x016e:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            long r5 = defpackage.fd.o(r7, r11)
            long r7 = defpackage.fd.o(r7, r12)
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x01c8
            goto L_0x01c9
        L_0x0181:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            long r5 = defpackage.fd.o(r7, r11)
            long r7 = defpackage.fd.o(r7, r12)
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x01c8
            goto L_0x01c9
        L_0x0194:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            float r5 = defpackage.fd.m(r7, r11)
            int r5 = java.lang.Float.floatToIntBits(r5)
            float r6 = defpackage.fd.m(r7, r12)
            int r6 = java.lang.Float.floatToIntBits(r6)
            if (r5 != r6) goto L_0x01c8
            goto L_0x01c9
        L_0x01ad:
            boolean r5 = r10.k(r3, r11, r12)
            if (r5 == 0) goto L_0x01c8
            double r5 = defpackage.fd.l(r7, r11)
            long r5 = java.lang.Double.doubleToLongBits(r5)
            double r7 = defpackage.fd.l(r7, r12)
            long r7 = java.lang.Double.doubleToLongBits(r7)
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 != 0) goto L_0x01c8
            goto L_0x01c9
        L_0x01c8:
            r4 = 0
        L_0x01c9:
            if (r4 != 0) goto L_0x01cc
            return r2
        L_0x01cc:
            int r3 = r3 + 3
            goto L_0x0005
        L_0x01d0:
            com.google.protobuf.ag<?, ?> r0 = r10.n
            com.google.protobuf.ah r1 = r0.g(r11)
            com.google.protobuf.ah r0 = r0.g(r12)
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L_0x01e1
            return r2
        L_0x01e1:
            boolean r0 = r10.f
            if (r0 == 0) goto L_0x01f4
            com.google.protobuf.j<?> r0 = r10.o
            com.google.protobuf.l r11 = r0.c(r11)
            com.google.protobuf.l r12 = r0.c(r12)
            boolean r11 = r11.equals(r12)
            return r11
        L_0x01f4:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.f(java.lang.Object, java.lang.Object):boolean");
    }

    public final void g(T t, jf jfVar) throws IOException {
        jfVar.getClass();
        at(t, jfVar);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0320, code lost:
        r11 = ((r2 + r0) + r1) + r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:199:0x0508, code lost:
        r11 = r0 + r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int h(T r18) {
        /*
            r17 = this;
            r6 = r17
            r7 = r18
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r0 = 1048575(0xfffff, float:1.469367E-39)
            r1 = 0
            r10 = 0
            r11 = 0
        L_0x000d:
            int[] r2 = r6.a
            int r3 = r2.length
            if (r10 >= r3) goto L_0x05c8
            int r3 = r6.as(r10)
            r4 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = r4 & r3
            int r4 = r4 >>> 20
            r12 = r2[r10]
            int r5 = r10 + 2
            r2 = r2[r5]
            r5 = r2 & r8
            r13 = 17
            sun.misc.Unsafe r14 = r
            if (r4 > r13) goto L_0x0040
            if (r5 == r0) goto L_0x0036
            if (r5 != r8) goto L_0x002f
            r1 = 0
            goto L_0x0035
        L_0x002f:
            long r0 = (long) r5
            int r0 = r14.getInt(r7, r0)
            r1 = r0
        L_0x0035:
            r0 = r5
        L_0x0036:
            int r2 = r2 >>> 20
            r13 = 1
            int r2 = r13 << r2
            r13 = r0
            r15 = r1
            r16 = r2
            goto L_0x0044
        L_0x0040:
            r13 = r0
            r15 = r1
            r16 = 0
        L_0x0044:
            r0 = r3 & r8
            long r2 = (long) r0
            z2 r0 = defpackage.z2.DOUBLE_LIST_PACKED
            int r0 = r0.b()
            if (r4 < r0) goto L_0x0058
            z2 r0 = defpackage.z2.SINT64_LIST_PACKED
            int r0 = r0.b()
            if (r4 > r0) goto L_0x0058
            goto L_0x0059
        L_0x0058:
            r5 = 0
        L_0x0059:
            boolean r0 = r6.h
            switch(r4) {
                case 0: goto L_0x05ab;
                case 1: goto L_0x0597;
                case 2: goto L_0x057e;
                case 3: goto L_0x0565;
                case 4: goto L_0x054b;
                case 5: goto L_0x0536;
                case 6: goto L_0x0521;
                case 7: goto L_0x050c;
                case 8: goto L_0x04e3;
                case 9: goto L_0x04c5;
                case 10: goto L_0x04a9;
                case 11: goto L_0x048f;
                case 12: goto L_0x0475;
                case 13: goto L_0x0460;
                case 14: goto L_0x044b;
                case 15: goto L_0x0431;
                case 16: goto L_0x0417;
                case 17: goto L_0x03f6;
                case 18: goto L_0x03ea;
                case 19: goto L_0x03de;
                case 20: goto L_0x03d2;
                case 21: goto L_0x03c6;
                case 22: goto L_0x03ba;
                case 23: goto L_0x03ae;
                case 24: goto L_0x03a2;
                case 25: goto L_0x0396;
                case 26: goto L_0x038a;
                case 27: goto L_0x037a;
                case 28: goto L_0x036e;
                case 29: goto L_0x0362;
                case 30: goto L_0x0356;
                case 31: goto L_0x034a;
                case 32: goto L_0x033e;
                case 33: goto L_0x0332;
                case 34: goto L_0x0326;
                case 35: goto L_0x0306;
                case 36: goto L_0x02eb;
                case 37: goto L_0x02d0;
                case 38: goto L_0x02b5;
                case 39: goto L_0x029a;
                case 40: goto L_0x027e;
                case 41: goto L_0x0262;
                case 42: goto L_0x0246;
                case 43: goto L_0x022a;
                case 44: goto L_0x020e;
                case 45: goto L_0x01f2;
                case 46: goto L_0x01d6;
                case 47: goto L_0x01ba;
                case 48: goto L_0x019e;
                case 49: goto L_0x018e;
                case 50: goto L_0x017e;
                case 51: goto L_0x0172;
                case 52: goto L_0x0166;
                case 53: goto L_0x0156;
                case 54: goto L_0x0146;
                case 55: goto L_0x0136;
                case 56: goto L_0x012a;
                case 57: goto L_0x011e;
                case 58: goto L_0x0112;
                case 59: goto L_0x00f4;
                case 60: goto L_0x00e0;
                case 61: goto L_0x00ce;
                case 62: goto L_0x00be;
                case 63: goto L_0x00ae;
                case 64: goto L_0x00a2;
                case 65: goto L_0x0096;
                case 66: goto L_0x0086;
                case 67: goto L_0x0076;
                case 68: goto L_0x0060;
                default: goto L_0x005e;
            }
        L_0x005e:
            goto L_0x05bf
        L_0x0060:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r2)
            com.google.protobuf.x r0 = (com.google.protobuf.x) r0
            com.google.protobuf.ac r1 = r6.p(r10)
            int r0 = com.google.protobuf.ad.a(r12, r0, r1)
            goto L_0x05be
        L_0x0076:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            long r0 = ad(r2, r7)
            int r0 = defpackage.n0.o(r12, r0)
            goto L_0x05be
        L_0x0086:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = ac(r2, r7)
            int r0 = defpackage.n0.m(r12, r0)
            goto L_0x05be
        L_0x0096:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.l(r12)
            goto L_0x05be
        L_0x00a2:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.k(r12)
            goto L_0x05be
        L_0x00ae:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = ac(r2, r7)
            int r0 = defpackage.n0.e(r12, r0)
            goto L_0x05be
        L_0x00be:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = ac(r2, r7)
            int r0 = defpackage.n0.t(r12, r0)
            goto L_0x05be
        L_0x00ce:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r2)
            cp r0 = (defpackage.cp) r0
            int r0 = defpackage.n0.c(r12, r0)
            goto L_0x05be
        L_0x00e0:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r2)
            com.google.protobuf.ac r1 = r6.p(r10)
            int r0 = com.google.protobuf.ad.p(r12, r1, r0)
            goto L_0x05be
        L_0x00f4:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r2)
            boolean r1 = r0 instanceof defpackage.cp
            if (r1 == 0) goto L_0x010a
            cp r0 = (defpackage.cp) r0
            int r0 = defpackage.n0.c(r12, r0)
            goto L_0x0508
        L_0x010a:
            java.lang.String r0 = (java.lang.String) r0
            int r0 = defpackage.n0.q(r12, r0)
            goto L_0x0508
        L_0x0112:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.b(r12)
            goto L_0x05be
        L_0x011e:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.f(r12)
            goto L_0x05be
        L_0x012a:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.g(r12)
            goto L_0x05be
        L_0x0136:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = ac(r2, r7)
            int r0 = defpackage.n0.i(r12, r0)
            goto L_0x05be
        L_0x0146:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            long r0 = ad(r2, r7)
            int r0 = defpackage.n0.v(r12, r0)
            goto L_0x05be
        L_0x0156:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            long r0 = ad(r2, r7)
            int r0 = defpackage.n0.j(r12, r0)
            goto L_0x05be
        L_0x0166:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.h(r12)
            goto L_0x05be
        L_0x0172:
            boolean r0 = r6.u(r12, r10, r7)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.d(r12)
            goto L_0x05be
        L_0x017e:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.lang.Object r1 = r6.o(r10)
            q4 r2 = r6.p
            r2.f(r12, r0, r1)
            r0 = 0
            goto L_0x05be
        L_0x018e:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            com.google.protobuf.ac r1 = r6.p(r10)
            int r0 = com.google.protobuf.ad.k(r12, r0, r1)
            goto L_0x05be
        L_0x019e:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.u(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x01b0
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x01b0:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x01ba:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.s(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x01cc
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x01cc:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x01d6:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.j(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x01e8
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x01e8:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x01f2:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.h(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x0204
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x0204:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x020e:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.f(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x0220
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x0220:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x022a:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.x(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x023c
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x023c:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x0246:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.c(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x0258
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x0258:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x0262:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.h(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x0274
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x0274:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x027e:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.j(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x0290
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x0290:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x029a:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.m(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x02ac
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x02ac:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x02b5:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.z(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x02c7
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x02c7:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x02d0:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.o(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x02e2
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x02e2:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x02eb:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.h(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x02fd
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x02fd:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
            goto L_0x0320
        L_0x0306:
            java.lang.Object r1 = r14.getObject(r7, r2)
            java.util.List r1 = (java.util.List) r1
            int r1 = com.google.protobuf.ad.j(r1)
            if (r1 <= 0) goto L_0x05bf
            if (r0 == 0) goto L_0x0318
            long r2 = (long) r5
            r14.putInt(r7, r2, r1)
        L_0x0318:
            int r0 = defpackage.n0.s(r12)
            int r2 = defpackage.n0.u(r1)
        L_0x0320:
            int r2 = r2 + r0
            int r2 = r2 + r1
            int r2 = r2 + r11
            r11 = r2
            goto L_0x05bf
        L_0x0326:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.t(r12, r0)
            goto L_0x05be
        L_0x0332:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.r(r12, r0)
            goto L_0x05be
        L_0x033e:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.i(r12, r0)
            goto L_0x05be
        L_0x034a:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.g(r12, r0)
            goto L_0x05be
        L_0x0356:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.e(r12, r0)
            goto L_0x05be
        L_0x0362:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.w(r12, r0)
            goto L_0x05be
        L_0x036e:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.d(r12, r0)
            goto L_0x05be
        L_0x037a:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            com.google.protobuf.ac r1 = r6.p(r10)
            int r0 = com.google.protobuf.ad.q(r12, r0, r1)
            goto L_0x05be
        L_0x038a:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.v(r12, r0)
            goto L_0x05be
        L_0x0396:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.b(r12, r0)
            goto L_0x05be
        L_0x03a2:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.g(r12, r0)
            goto L_0x05be
        L_0x03ae:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.i(r12, r0)
            goto L_0x05be
        L_0x03ba:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.l(r12, r0)
            goto L_0x05be
        L_0x03c6:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.y(r12, r0)
            goto L_0x05be
        L_0x03d2:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.n(r12, r0)
            goto L_0x05be
        L_0x03de:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.g(r12, r0)
            goto L_0x05be
        L_0x03ea:
            java.lang.Object r0 = r14.getObject(r7, r2)
            java.util.List r0 = (java.util.List) r0
            int r0 = com.google.protobuf.ad.i(r12, r0)
            goto L_0x05be
        L_0x03f6:
            r0 = r17
            r1 = r18
            r4 = r2
            r2 = r10
            r3 = r13
            r8 = r4
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r8)
            com.google.protobuf.x r0 = (com.google.protobuf.x) r0
            com.google.protobuf.ac r1 = r6.p(r10)
            int r0 = com.google.protobuf.ad.a(r12, r0, r1)
            goto L_0x05be
        L_0x0417:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            long r0 = r14.getLong(r7, r8)
            int r0 = defpackage.n0.o(r12, r0)
            goto L_0x05be
        L_0x0431:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = r14.getInt(r7, r8)
            int r0 = defpackage.n0.m(r12, r0)
            goto L_0x05be
        L_0x044b:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.l(r12)
            goto L_0x05be
        L_0x0460:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.k(r12)
            goto L_0x05be
        L_0x0475:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = r14.getInt(r7, r8)
            int r0 = defpackage.n0.e(r12, r0)
            goto L_0x05be
        L_0x048f:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = r14.getInt(r7, r8)
            int r0 = defpackage.n0.t(r12, r0)
            goto L_0x05be
        L_0x04a9:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r8)
            cp r0 = (defpackage.cp) r0
            int r0 = defpackage.n0.c(r12, r0)
            goto L_0x05be
        L_0x04c5:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r8)
            com.google.protobuf.ac r1 = r6.p(r10)
            int r0 = com.google.protobuf.ad.p(r12, r1, r0)
            goto L_0x05be
        L_0x04e3:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            java.lang.Object r0 = r14.getObject(r7, r8)
            boolean r1 = r0 instanceof defpackage.cp
            if (r1 == 0) goto L_0x0502
            cp r0 = (defpackage.cp) r0
            int r0 = defpackage.n0.c(r12, r0)
            goto L_0x0508
        L_0x0502:
            java.lang.String r0 = (java.lang.String) r0
            int r0 = defpackage.n0.q(r12, r0)
        L_0x0508:
            int r0 = r0 + r11
            r11 = r0
            goto L_0x05bf
        L_0x050c:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.b(r12)
            goto L_0x05be
        L_0x0521:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.f(r12)
            goto L_0x05be
        L_0x0536:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.g(r12)
            goto L_0x05be
        L_0x054b:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = r14.getInt(r7, r8)
            int r0 = defpackage.n0.i(r12, r0)
            goto L_0x05be
        L_0x0565:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            long r0 = r14.getLong(r7, r8)
            int r0 = defpackage.n0.v(r12, r0)
            goto L_0x05be
        L_0x057e:
            r8 = r2
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            long r0 = r14.getLong(r7, r8)
            int r0 = defpackage.n0.j(r12, r0)
            goto L_0x05be
        L_0x0597:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.h(r12)
            goto L_0x05be
        L_0x05ab:
            r0 = r17
            r1 = r18
            r2 = r10
            r3 = r13
            r4 = r15
            r5 = r16
            boolean r0 = r0.s(r1, r2, r3, r4, r5)
            if (r0 == 0) goto L_0x05bf
            int r0 = defpackage.n0.d(r12)
        L_0x05be:
            int r11 = r11 + r0
        L_0x05bf:
            int r10 = r10 + 3
            r0 = r13
            r1 = r15
            r8 = 1048575(0xfffff, float:1.469367E-39)
            goto L_0x000d
        L_0x05c8:
            com.google.protobuf.ag<?, ?> r0 = r6.n
            com.google.protobuf.ah r1 = r0.g(r7)
            int r0 = r0.h(r1)
            int r0 = r0 + r11
            boolean r1 = r6.f
            if (r1 == 0) goto L_0x05e2
            com.google.protobuf.j<?> r1 = r6.o
            com.google.protobuf.l r1 = r1.c(r7)
            int r1 = r1.g()
            int r0 = r0 + r1
        L_0x05e2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.h(java.lang.Object):int");
    }

    public final T i() {
        return this.l.a(this.e);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00da, code lost:
        if (r4 != false) goto L_0x01f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01d6, code lost:
        r4 = 37;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x01d8, code lost:
        r3 = (r3 * 53) + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01f2, code lost:
        if (r4 != false) goto L_0x01f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01f5, code lost:
        r8 = 1237;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01f7, code lost:
        r4 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0241, code lost:
        r3 = r4 + r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0243, code lost:
        r2 = r2 + 3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int j(T r11) {
        /*
            r10 = this;
            int[] r0 = r10.a
            int r1 = r0.length
            r2 = 0
            r3 = 0
        L_0x0005:
            if (r2 >= r1) goto L_0x0247
            int r4 = r10.as(r2)
            r5 = r0[r2]
            r6 = 1048575(0xfffff, float:1.469367E-39)
            r6 = r6 & r4
            long r6 = (long) r6
            r8 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = r4 & r8
            int r4 = r4 >>> 20
            r8 = 1231(0x4cf, float:1.725E-42)
            r9 = 1237(0x4d5, float:1.733E-42)
            switch(r4) {
                case 0: goto L_0x0233;
                case 1: goto L_0x0228;
                case 2: goto L_0x021d;
                case 3: goto L_0x0212;
                case 4: goto L_0x020b;
                case 5: goto L_0x0200;
                case 6: goto L_0x01f9;
                case 7: goto L_0x01ea;
                case 8: goto L_0x01dd;
                case 9: goto L_0x01cb;
                case 10: goto L_0x01bf;
                case 11: goto L_0x01b7;
                case 12: goto L_0x01af;
                case 13: goto L_0x01a7;
                case 14: goto L_0x019b;
                case 15: goto L_0x0193;
                case 16: goto L_0x0187;
                case 17: goto L_0x017c;
                case 18: goto L_0x0170;
                case 19: goto L_0x0170;
                case 20: goto L_0x0170;
                case 21: goto L_0x0170;
                case 22: goto L_0x0170;
                case 23: goto L_0x0170;
                case 24: goto L_0x0170;
                case 25: goto L_0x0170;
                case 26: goto L_0x0170;
                case 27: goto L_0x0170;
                case 28: goto L_0x0170;
                case 29: goto L_0x0170;
                case 30: goto L_0x0170;
                case 31: goto L_0x0170;
                case 32: goto L_0x0170;
                case 33: goto L_0x0170;
                case 34: goto L_0x0170;
                case 35: goto L_0x0170;
                case 36: goto L_0x0170;
                case 37: goto L_0x0170;
                case 38: goto L_0x0170;
                case 39: goto L_0x0170;
                case 40: goto L_0x0170;
                case 41: goto L_0x0170;
                case 42: goto L_0x0170;
                case 43: goto L_0x0170;
                case 44: goto L_0x0170;
                case 45: goto L_0x0170;
                case 46: goto L_0x0170;
                case 47: goto L_0x0170;
                case 48: goto L_0x0170;
                case 49: goto L_0x0170;
                case 50: goto L_0x0164;
                case 51: goto L_0x0148;
                case 52: goto L_0x0130;
                case 53: goto L_0x011e;
                case 54: goto L_0x010c;
                case 55: goto L_0x00fe;
                case 56: goto L_0x00ec;
                case 57: goto L_0x00de;
                case 58: goto L_0x00c6;
                case 59: goto L_0x00b2;
                case 60: goto L_0x00a0;
                case 61: goto L_0x008e;
                case 62: goto L_0x0080;
                case 63: goto L_0x0072;
                case 64: goto L_0x0064;
                case 65: goto L_0x0052;
                case 66: goto L_0x0044;
                case 67: goto L_0x0032;
                case 68: goto L_0x0020;
                default: goto L_0x001e;
            }
        L_0x001e:
            goto L_0x0243
        L_0x0020:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            int r3 = r3 * 53
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x0032:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            long r4 = ad(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x0044:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            int r4 = ac(r6, r11)
            goto L_0x0241
        L_0x0052:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            long r4 = ad(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x0064:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            int r4 = ac(r6, r11)
            goto L_0x0241
        L_0x0072:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            int r4 = ac(r6, r11)
            goto L_0x0241
        L_0x0080:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            int r4 = ac(r6, r11)
            goto L_0x0241
        L_0x008e:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x00a0:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            int r3 = r3 * 53
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x00b2:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            java.lang.String r4 = (java.lang.String) r4
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x00c6:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            java.lang.Boolean r4 = (java.lang.Boolean) r4
            boolean r4 = r4.booleanValue()
            java.nio.charset.Charset r5 = com.google.protobuf.p.a
            if (r4 == 0) goto L_0x01f5
            goto L_0x01f7
        L_0x00de:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            int r4 = ac(r6, r11)
            goto L_0x0241
        L_0x00ec:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            long r4 = ad(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x00fe:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            int r4 = ac(r6, r11)
            goto L_0x0241
        L_0x010c:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            long r4 = ad(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x011e:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            long r4 = ad(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x0130:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            java.lang.Float r4 = (java.lang.Float) r4
            float r4 = r4.floatValue()
            int r4 = java.lang.Float.floatToIntBits(r4)
            goto L_0x0241
        L_0x0148:
            boolean r4 = r10.u(r5, r2, r11)
            if (r4 == 0) goto L_0x0243
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            java.lang.Double r4 = (java.lang.Double) r4
            double r4 = r4.doubleValue()
            long r4 = java.lang.Double.doubleToLongBits(r4)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x0164:
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x0170:
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x017c:
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            if (r4 == 0) goto L_0x01d6
            int r4 = r4.hashCode()
            goto L_0x01d8
        L_0x0187:
            int r3 = r3 * 53
            long r4 = defpackage.fd.o(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x0193:
            int r3 = r3 * 53
            int r4 = defpackage.fd.n(r6, r11)
            goto L_0x0241
        L_0x019b:
            int r3 = r3 * 53
            long r4 = defpackage.fd.o(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x01a7:
            int r3 = r3 * 53
            int r4 = defpackage.fd.n(r6, r11)
            goto L_0x0241
        L_0x01af:
            int r3 = r3 * 53
            int r4 = defpackage.fd.n(r6, r11)
            goto L_0x0241
        L_0x01b7:
            int r3 = r3 * 53
            int r4 = defpackage.fd.n(r6, r11)
            goto L_0x0241
        L_0x01bf:
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x01cb:
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            if (r4 == 0) goto L_0x01d6
            int r4 = r4.hashCode()
            goto L_0x01d8
        L_0x01d6:
            r4 = 37
        L_0x01d8:
            int r3 = r3 * 53
            int r3 = r3 + r4
            goto L_0x0243
        L_0x01dd:
            int r3 = r3 * 53
            java.lang.Object r4 = defpackage.fd.p(r6, r11)
            java.lang.String r4 = (java.lang.String) r4
            int r4 = r4.hashCode()
            goto L_0x0241
        L_0x01ea:
            int r3 = r3 * 53
            boolean r4 = defpackage.fd.g(r6, r11)
            java.nio.charset.Charset r5 = com.google.protobuf.p.a
            if (r4 == 0) goto L_0x01f5
            goto L_0x01f7
        L_0x01f5:
            r8 = 1237(0x4d5, float:1.733E-42)
        L_0x01f7:
            r4 = r8
            goto L_0x0241
        L_0x01f9:
            int r3 = r3 * 53
            int r4 = defpackage.fd.n(r6, r11)
            goto L_0x0241
        L_0x0200:
            int r3 = r3 * 53
            long r4 = defpackage.fd.o(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x020b:
            int r3 = r3 * 53
            int r4 = defpackage.fd.n(r6, r11)
            goto L_0x0241
        L_0x0212:
            int r3 = r3 * 53
            long r4 = defpackage.fd.o(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x021d:
            int r3 = r3 * 53
            long r4 = defpackage.fd.o(r6, r11)
            int r4 = com.google.protobuf.p.a(r4)
            goto L_0x0241
        L_0x0228:
            int r3 = r3 * 53
            float r4 = defpackage.fd.m(r6, r11)
            int r4 = java.lang.Float.floatToIntBits(r4)
            goto L_0x0241
        L_0x0233:
            int r3 = r3 * 53
            double r4 = defpackage.fd.l(r6, r11)
            long r4 = java.lang.Double.doubleToLongBits(r4)
            int r4 = com.google.protobuf.p.a(r4)
        L_0x0241:
            int r4 = r4 + r3
            r3 = r4
        L_0x0243:
            int r2 = r2 + 3
            goto L_0x0005
        L_0x0247:
            int r3 = r3 * 53
            com.google.protobuf.ag<?, ?> r0 = r10.n
            com.google.protobuf.ah r0 = r0.g(r11)
            int r0 = r0.hashCode()
            int r0 = r0 + r3
            boolean r1 = r10.f
            if (r1 == 0) goto L_0x0265
            int r0 = r0 * 53
            com.google.protobuf.j<?> r1 = r10.o
            com.google.protobuf.l r11 = r1.c(r11)
            int r11 = r11.hashCode()
            int r0 = r0 + r11
        L_0x0265:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.z.j(java.lang.Object):int");
    }

    public final boolean k(int i2, Object obj, Object obj2) {
        return r(i2, obj) == r(i2, obj2);
    }

    public final void m(Object obj, int i2, Object obj2, ag agVar, Object obj3) {
        p.e n2;
        int i3 = this.a[i2];
        Object p2 = fd.p((long) (as(i2) & 1048575), obj);
        if (p2 != null && (n2 = n(i2)) != null) {
            q4 q4Var = this.p;
            w e2 = q4Var.e(p2);
            q4Var.c(o(i2));
            for (Map.Entry entry : e2.entrySet()) {
                ((Integer) entry.getValue()).intValue();
                if (!n2.a()) {
                    if (obj2 == null) {
                        agVar.f(obj3);
                    }
                    entry.getKey();
                    entry.getValue();
                    throw null;
                }
            }
        }
    }

    public final p.e n(int i2) {
        return (p.e) this.b[((i2 / 3) * 2) + 1];
    }

    public final Object o(int i2) {
        return this.b[(i2 / 3) * 2];
    }

    public final ac p(int i2) {
        int i3 = (i2 / 3) * 2;
        Object[] objArr = this.b;
        ac acVar = (ac) objArr[i3];
        if (acVar != null) {
            return acVar;
        }
        ac a2 = f9.c.a((Class) objArr[i3 + 1]);
        objArr[i3] = a2;
        return a2;
    }

    public final boolean r(int i2, Object obj) {
        boolean equals;
        int i3 = this.a[i2 + 2];
        long j2 = (long) (i3 & 1048575);
        if (j2 == 1048575) {
            int as = as(i2);
            long j3 = (long) (as & 1048575);
            switch ((as & 267386880) >>> 20) {
                case 0:
                    if (Double.doubleToRawLongBits(fd.l(j3, obj)) != 0) {
                        return true;
                    }
                    return false;
                case 1:
                    if (Float.floatToRawIntBits(fd.m(j3, obj)) != 0) {
                        return true;
                    }
                    return false;
                case 2:
                    if (fd.o(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 3:
                    if (fd.o(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 4:
                    if (fd.n(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 5:
                    if (fd.o(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 6:
                    if (fd.n(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 7:
                    return fd.g(j3, obj);
                case 8:
                    Object p2 = fd.p(j3, obj);
                    if (p2 instanceof String) {
                        equals = ((String) p2).isEmpty();
                        break;
                    } else if (p2 instanceof cp) {
                        equals = cp.f.equals(p2);
                        break;
                    } else {
                        throw new IllegalArgumentException();
                    }
                case 9:
                    if (fd.p(j3, obj) != null) {
                        return true;
                    }
                    return false;
                case 10:
                    equals = cp.f.equals(fd.p(j3, obj));
                    break;
                case 11:
                    if (fd.n(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 12:
                    if (fd.n(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 13:
                    if (fd.n(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 14:
                    if (fd.o(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 15:
                    if (fd.n(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 16:
                    if (fd.o(j3, obj) != 0) {
                        return true;
                    }
                    return false;
                case 17:
                    if (fd.p(j3, obj) != null) {
                        return true;
                    }
                    return false;
                default:
                    throw new IllegalArgumentException();
            }
            return !equals;
        } else if (((1 << (i3 >>> 20)) & fd.n(j2, obj)) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean s(T t, int i2, int i3, int i4, int i5) {
        return i3 == 1048575 ? r(i2, t) : (i4 & i5) != 0;
    }

    public final boolean u(int i2, int i3, Object obj) {
        if (fd.n((long) (this.a[i3 + 2] & 1048575), obj) == i2) {
            return true;
        }
        return false;
    }

    public final void v(Object obj, int i2, Object obj2, y9 y9Var) throws IOException {
        long as = (long) (as(i2) & 1048575);
        Object p2 = fd.p(as, obj);
        q4 q4Var = this.p;
        if (p2 == null) {
            p2 = q4Var.d();
            fd.z(as, obj, p2);
        } else if (q4Var.g(p2)) {
            w d2 = q4Var.d();
            q4Var.a(d2, p2);
            fd.z(as, obj, d2);
            p2 = d2;
        }
        q4Var.e(p2);
        q4Var.c(obj2);
        g gVar = (g) y9Var;
        gVar.aa(2);
        f fVar = gVar.a;
        fVar.j(fVar.ab());
        throw null;
    }

    public final void w(int i2, Object obj, Object obj2) {
        if (r(i2, obj2)) {
            long as = (long) (as(i2) & 1048575);
            Unsafe unsafe = r;
            Object object = unsafe.getObject(obj2, as);
            if (object != null) {
                ac p2 = p(i2);
                if (!r(i2, obj)) {
                    if (!t(object)) {
                        unsafe.putObject(obj, as, object);
                    } else {
                        Object i3 = p2.i();
                        p2.a(i3, object);
                        unsafe.putObject(obj, as, i3);
                    }
                    an(i2, obj);
                    return;
                }
                Object object2 = unsafe.getObject(obj, as);
                if (!t(object2)) {
                    Object i4 = p2.i();
                    p2.a(i4, object2);
                    unsafe.putObject(obj, as, i4);
                    object2 = i4;
                }
                p2.a(object2, object);
                return;
            }
            throw new IllegalStateException("Source subfield " + this.a[i2] + " is present but null: " + obj2);
        }
    }

    public final void x(int i2, Object obj, Object obj2) {
        int[] iArr = this.a;
        int i3 = iArr[i2];
        if (u(i3, i2, obj2)) {
            long as = (long) (as(i2) & 1048575);
            Unsafe unsafe = r;
            Object object = unsafe.getObject(obj2, as);
            if (object != null) {
                ac p2 = p(i2);
                if (!u(i3, i2, obj)) {
                    if (!t(object)) {
                        unsafe.putObject(obj, as, object);
                    } else {
                        Object i4 = p2.i();
                        p2.a(i4, object);
                        unsafe.putObject(obj, as, i4);
                    }
                    ao(i3, i2, obj);
                    return;
                }
                Object object2 = unsafe.getObject(obj, as);
                if (!t(object2)) {
                    Object i5 = p2.i();
                    p2.a(i5, object2);
                    unsafe.putObject(obj, as, i5);
                    object2 = i5;
                }
                p2.a(object2, object);
                return;
            }
            throw new IllegalStateException("Source subfield " + iArr[i2] + " is present but null: " + obj2);
        }
    }

    public final Object y(int i2, Object obj) {
        ac p2 = p(i2);
        long as = (long) (as(i2) & 1048575);
        if (!r(i2, obj)) {
            return p2.i();
        }
        Object object = r.getObject(obj, as);
        if (t(object)) {
            return object;
        }
        Object i3 = p2.i();
        if (object != null) {
            p2.a(i3, object);
        }
        return i3;
    }

    public final Object z(int i2, int i3, Object obj) {
        ac p2 = p(i3);
        if (!u(i2, i3, obj)) {
            return p2.i();
        }
        Object object = r.getObject(obj, (long) (as(i3) & 1048575));
        if (t(object)) {
            return object;
        }
        Object i4 = p2.i();
        if (object != null) {
            p2.a(i4, object);
        }
        return i4;
    }
}
