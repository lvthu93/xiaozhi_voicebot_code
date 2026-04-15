package org.mozilla.javascript;

import java.math.BigInteger;

class DToA {
    private static final int Bias = 1023;
    private static final int Bletch = 16;
    private static final int Bndry_mask = 1048575;
    static final int DTOSTR_EXPONENTIAL = 3;
    static final int DTOSTR_FIXED = 2;
    static final int DTOSTR_PRECISION = 4;
    static final int DTOSTR_STANDARD = 0;
    static final int DTOSTR_STANDARD_EXPONENTIAL = 1;
    private static final int Exp_11 = 1072693248;
    private static final int Exp_mask = 2146435072;
    private static final int Exp_mask_shifted = 2047;
    private static final int Exp_msk1 = 1048576;
    private static final long Exp_msk1L = 4503599627370496L;
    private static final int Exp_shift = 20;
    private static final int Exp_shift1 = 20;
    private static final int Exp_shiftL = 52;
    private static final int Frac_mask = 1048575;
    private static final int Frac_mask1 = 1048575;
    private static final long Frac_maskL = 4503599627370495L;
    private static final int Int_max = 14;
    private static final int Log2P = 1;
    private static final int P = 53;
    private static final int Quick_max = 14;
    private static final int Sign_bit = Integer.MIN_VALUE;
    private static final int Ten_pmax = 22;
    private static final double[] bigtens = {1.0E16d, 1.0E32d, 1.0E64d, 1.0E128d, 1.0E256d};
    private static final int[] dtoaModes = {0, 0, 3, 2, 2};
    private static final int n_bigtens = 5;
    private static final double[] tens = {1.0d, 10.0d, 100.0d, 1000.0d, 10000.0d, 100000.0d, 1000000.0d, 1.0E7d, 1.0E8d, 1.0E9d, 1.0E10d, 1.0E11d, 1.0E12d, 1.0E13d, 1.0E14d, 1.0E15d, 1.0E16d, 1.0E17d, 1.0E18d, 1.0E19d, 1.0E20d, 1.0E21d, 1.0E22d};

    private static char BASEDIGIT(int i) {
        return (char) (i >= 10 ? i + 87 : i + 48);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:362:0x061c, code lost:
        r1.append(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:363:0x0620, code lost:
        return r2 + r5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x0210  */
    /* JADX WARNING: Removed duplicated region for block: B:120:0x0238  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x023c  */
    /* JADX WARNING: Removed duplicated region for block: B:157:0x030f  */
    /* JADX WARNING: Removed duplicated region for block: B:160:0x031c  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x0320  */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x03ce  */
    /* JADX WARNING: Removed duplicated region for block: B:269:0x04c4  */
    /* JADX WARNING: Removed duplicated region for block: B:271:0x04ca  */
    /* JADX WARNING: Removed duplicated region for block: B:276:0x04d8  */
    /* JADX WARNING: Removed duplicated region for block: B:280:0x04ef  */
    /* JADX WARNING: Removed duplicated region for block: B:284:0x04f6  */
    /* JADX WARNING: Removed duplicated region for block: B:295:0x0523  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int JS_dtoa(double r41, int r43, boolean r44, int r45, boolean[] r46, java.lang.StringBuilder r47) {
        /*
            r0 = r43
            r1 = r47
            r2 = 1
            int[] r3 = new int[r2]
            int[] r4 = new int[r2]
            int r5 = word0(r41)
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r5 & r6
            r6 = 0
            if (r5 == 0) goto L_0x0024
            r46[r6] = r2
            int r5 = word0(r41)
            r7 = 2147483647(0x7fffffff, float:NaN)
            r5 = r5 & r7
            r7 = r41
            double r7 = setWord0(r7, r5)
            goto L_0x0028
        L_0x0024:
            r7 = r41
            r46[r6] = r6
        L_0x0028:
            int r5 = word0(r7)
            r9 = 2146435072(0x7ff00000, float:NaN)
            r5 = r5 & r9
            r10 = 1048575(0xfffff, float:1.469367E-39)
            if (r5 != r9) goto L_0x004c
            int r0 = word1(r7)
            if (r0 != 0) goto L_0x0044
            int r0 = word0(r7)
            r0 = r0 & r10
            if (r0 != 0) goto L_0x0044
            java.lang.String r0 = "Infinity"
            goto L_0x0046
        L_0x0044:
            java.lang.String r0 = "NaN"
        L_0x0046:
            r1.append(r0)
            r0 = 9999(0x270f, float:1.4012E-41)
            return r0
        L_0x004c:
            r11 = 0
            r5 = 48
            int r9 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r9 != 0) goto L_0x005b
            r1.setLength(r6)
            r1.append(r5)
            return r2
        L_0x005b:
            java.math.BigInteger r9 = d2b(r7, r3, r4)
            int r13 = word0(r7)
            int r13 = r13 >>> 20
            r13 = r13 & 2047(0x7ff, float:2.868E-42)
            r14 = 32
            if (r13 == 0) goto L_0x007f
            int r15 = word0(r7)
            r15 = r15 & r10
            r16 = 1072693248(0x3ff00000, float:1.875)
            r15 = r15 | r16
            double r15 = setWord0(r7, r15)
            int r13 = r13 + -1023
            r17 = r3
            r2 = r15
            r10 = 0
            goto L_0x00b7
        L_0x007f:
            r13 = r4[r6]
            r15 = r3[r6]
            int r13 = r13 + r15
            int r13 = r13 + 1074
            if (r13 <= r14) goto L_0x009e
            int r15 = word0(r7)
            long r14 = (long) r15
            int r16 = 64 - r13
            long r14 = r14 << r16
            int r16 = word1(r7)
            int r17 = r13 + -32
            int r10 = r16 >>> r17
            r17 = r3
            long r2 = (long) r10
            long r2 = r2 | r14
            goto L_0x00a8
        L_0x009e:
            r17 = r3
            int r2 = word1(r7)
            long r2 = (long) r2
            int r10 = 32 - r13
            long r2 = r2 << r10
        L_0x00a8:
            double r2 = (double) r2
            int r10 = word0(r2)
            r14 = 32505856(0x1f00000, float:8.8162076E-38)
            int r10 = r10 - r14
            double r2 = setWord0(r2, r10)
            int r13 = r13 + -1075
            r10 = 1
        L_0x00b7:
            r14 = 4609434218613702656(0x3ff8000000000000, double:1.5)
            double r2 = r2 - r14
            r14 = 4598887322496222049(0x3fd287a7636f4361, double:0.289529654602168)
            double r2 = r2 * r14
            r14 = 4595512376519870643(0x3fc68a288b60c8b3, double:0.1760912590558)
            double r2 = r2 + r14
            double r14 = (double) r13
            r18 = 4599094494223104507(0x3fd34413509f79fb, double:0.301029995663981)
            double r14 = r14 * r18
            double r14 = r14 + r2
            int r2 = (int) r14
            int r3 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r3 >= 0) goto L_0x00dc
            double r11 = (double) r2
            int r3 = (r14 > r11 ? 1 : (r14 == r11 ? 0 : -1))
            if (r3 == 0) goto L_0x00dc
            int r2 = r2 + -1
        L_0x00dc:
            if (r2 < 0) goto L_0x00ee
            r3 = 22
            if (r2 > r3) goto L_0x00ee
            double[] r3 = tens
            r11 = r3[r2]
            int r3 = (r7 > r11 ? 1 : (r7 == r11 ? 0 : -1))
            if (r3 >= 0) goto L_0x00ec
            int r2 = r2 + -1
        L_0x00ec:
            r3 = 0
            goto L_0x00ef
        L_0x00ee:
            r3 = 1
        L_0x00ef:
            r11 = r4[r6]
            int r11 = r11 - r13
            r12 = 1
            int r11 = r11 - r12
            if (r11 < 0) goto L_0x00f9
            r12 = r11
            r11 = 0
            goto L_0x00fb
        L_0x00f9:
            int r11 = -r11
            r12 = 0
        L_0x00fb:
            if (r2 < 0) goto L_0x0101
            int r12 = r12 + r2
            r14 = r2
            r13 = 0
            goto L_0x0104
        L_0x0101:
            int r11 = r11 - r2
            int r13 = -r2
            r14 = 0
        L_0x0104:
            if (r0 < 0) goto L_0x010a
            r15 = 9
            if (r0 <= r15) goto L_0x010b
        L_0x010a:
            r0 = 0
        L_0x010b:
            r15 = 5
            if (r0 <= r15) goto L_0x0113
            int r0 = r0 + -4
            r20 = 0
            goto L_0x0115
        L_0x0113:
            r20 = 1
        L_0x0115:
            r5 = 3
            r6 = 2
            if (r0 == 0) goto L_0x014e
            r15 = 1
            if (r0 == r15) goto L_0x014e
            if (r0 == r6) goto L_0x0141
            if (r0 == r5) goto L_0x0130
            r5 = 4
            if (r0 == r5) goto L_0x0142
            r5 = 5
            if (r0 == r5) goto L_0x012e
            r5 = r45
            r24 = r9
            r6 = 0
            r23 = 0
            goto L_0x0156
        L_0x012e:
            r5 = 1
            goto L_0x0131
        L_0x0130:
            r5 = 0
        L_0x0131:
            int r16 = r45 + r2
            int r23 = r16 + 1
            int r15 = r23 + -1
            r24 = r9
            r6 = r23
            r23 = r15
            r15 = r5
            r5 = r45
            goto L_0x0156
        L_0x0141:
            r15 = 0
        L_0x0142:
            if (r45 > 0) goto L_0x0146
            r5 = 1
            goto L_0x0148
        L_0x0146:
            r5 = r45
        L_0x0148:
            r6 = r5
            r23 = r6
            r24 = r9
            goto L_0x0156
        L_0x014e:
            r5 = -1
            r24 = r9
            r5 = 0
            r6 = -1
            r15 = 1
            r23 = -1
        L_0x0156:
            r25 = 48
            r27 = 4621819117588971520(0x4024000000000000, double:10.0)
            if (r6 < 0) goto L_0x0326
            r9 = 14
            if (r6 > r9) goto L_0x0326
            if (r20 == 0) goto L_0x0326
            if (r2 <= 0) goto L_0x019d
            double[] r9 = tens
            r20 = r2 & 15
            r29 = r9[r20]
            int r9 = r2 >> 4
            r20 = r9 & 16
            if (r20 == 0) goto L_0x017f
            r9 = r9 & 15
            double[] r20 = bigtens
            r21 = 4
            r31 = r20[r21]
            double r31 = r7 / r31
            r20 = 0
            r22 = 3
            goto L_0x0185
        L_0x017f:
            r31 = r7
            r20 = 0
            r22 = 2
        L_0x0185:
            if (r9 == 0) goto L_0x0198
            r33 = r9 & 1
            if (r33 == 0) goto L_0x0193
            int r22 = r22 + 1
            double[] r33 = bigtens
            r34 = r33[r20]
            double r29 = r29 * r34
        L_0x0193:
            int r9 = r9 >> 1
            int r20 = r20 + 1
            goto L_0x0185
        L_0x0198:
            double r31 = r31 / r29
            r9 = r22
            goto L_0x01c9
        L_0x019d:
            int r9 = -r2
            if (r9 == 0) goto L_0x01c6
            double[] r20 = tens
            r22 = r9 & 15
            r29 = r20[r22]
            double r29 = r29 * r7
            r20 = 4
            int r9 = r9 >> 4
            r20 = r9
            r31 = r29
            r9 = 2
            r22 = 0
        L_0x01b3:
            if (r20 == 0) goto L_0x01c9
            r29 = r20 & 1
            if (r29 == 0) goto L_0x01c1
            int r9 = r9 + 1
            double[] r29 = bigtens
            r33 = r29[r22]
            double r31 = r31 * r33
        L_0x01c1:
            int r20 = r20 >> 1
            int r22 = r22 + 1
            goto L_0x01b3
        L_0x01c6:
            r31 = r7
            r9 = 2
        L_0x01c9:
            if (r3 == 0) goto L_0x01f1
            r29 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            int r20 = (r31 > r29 ? 1 : (r31 == r29 ? 0 : -1))
            if (r20 >= 0) goto L_0x01f1
            if (r6 <= 0) goto L_0x01f1
            if (r23 > 0) goto L_0x01df
            r22 = r2
            r29 = r22
            r2 = r6
            r33 = r7
            r20 = 1
            goto L_0x01fa
        L_0x01df:
            int r20 = r2 + -1
            double r31 = r31 * r27
            int r9 = r9 + 1
            r29 = r2
            r33 = r7
            r22 = r20
            r2 = r23
            r20 = 0
            r8 = r6
            goto L_0x01fb
        L_0x01f1:
            r22 = r2
            r29 = r22
            r2 = r6
            r33 = r7
            r20 = 0
        L_0x01fa:
            r8 = r2
        L_0x01fb:
            double r6 = (double) r9
            double r6 = r6 * r31
            r35 = 4619567317775286272(0x401c000000000000, double:7.0)
            double r6 = r6 + r35
            int r9 = word0(r6)
            r30 = 54525952(0x3400000, float:5.642373E-37)
            int r9 = r9 - r30
            double r6 = setWord0(r6, r9)
            if (r2 != 0) goto L_0x0238
            r35 = 4617315517961601024(0x4014000000000000, double:5.0)
            double r31 = r31 - r35
            int r9 = (r31 > r6 ? 1 : (r31 == r6 ? 0 : -1))
            if (r9 <= 0) goto L_0x0223
            r9 = 49
            r1.append(r9)
            r9 = 1
            int r22 = r22 + 1
        L_0x0220:
            int r22 = r22 + 1
            return r22
        L_0x0223:
            r30 = r10
            double r9 = -r6
            int r20 = (r31 > r9 ? 1 : (r31 == r9 ? 0 : -1))
            if (r20 >= 0) goto L_0x0235
            r9 = 0
            r1.setLength(r9)
            r0 = 48
            r1.append(r0)
            r0 = 1
            return r0
        L_0x0235:
            r20 = 1
            goto L_0x023a
        L_0x0238:
            r30 = r10
        L_0x023a:
            if (r20 != 0) goto L_0x030f
            r9 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            if (r15 == 0) goto L_0x02a5
            double[] r20 = tens
            int r35 = r2 + -1
            r35 = r20[r35]
            double r9 = r9 / r35
            double r9 = r9 - r6
            r35 = r8
            r36 = r11
            r37 = r12
            r7 = r31
            r6 = 0
        L_0x0252:
            long r11 = (long) r7
            r38 = r13
            r39 = r14
            double r13 = (double) r11
            double r31 = r7 - r13
            long r11 = r11 + r25
            int r7 = (int) r11
            char r7 = (char) r7
            r1.append(r7)
            int r7 = (r31 > r9 ? 1 : (r31 == r9 ? 0 : -1))
            if (r7 >= 0) goto L_0x0267
            r7 = 1
            goto L_0x0220
        L_0x0267:
            r7 = 1
            r11 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            double r11 = r11 - r31
            int r8 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r8 >= 0) goto L_0x0297
        L_0x0270:
            int r0 = r47.length()
            int r0 = r0 - r7
            char r0 = r1.charAt(r0)
            int r2 = r47.length()
            int r2 = r2 - r7
            r1.setLength(r2)
            r2 = 57
            if (r0 == r2) goto L_0x0287
            r5 = r0
            goto L_0x0291
        L_0x0287:
            int r0 = r47.length()
            if (r0 != 0) goto L_0x0270
            int r22 = r22 + 1
            r5 = 48
        L_0x0291:
            int r5 = r5 + r7
            char r0 = (char) r5
            r1.append(r0)
            goto L_0x0220
        L_0x0297:
            int r6 = r6 + r7
            if (r6 < r2) goto L_0x029c
            goto L_0x0305
        L_0x029c:
            double r9 = r9 * r27
            double r7 = r31 * r27
            r13 = r38
            r14 = r39
            goto L_0x0252
        L_0x02a5:
            r35 = r8
            r36 = r11
            r37 = r12
            r38 = r13
            r39 = r14
            double[] r8 = tens
            int r11 = r2 + -1
            r11 = r8[r11]
            double r6 = r6 * r11
            r11 = r31
            r8 = 1
        L_0x02ba:
            long r13 = (long) r11
            double r9 = (double) r13
            double r31 = r11 - r9
            long r13 = r13 + r25
            int r9 = (int) r13
            char r9 = (char) r9
            r1.append(r9)
            if (r8 != r2) goto L_0x0308
            r9 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            double r11 = r6 + r9
            int r8 = (r31 > r11 ? 1 : (r31 == r11 ? 0 : -1))
            if (r8 <= 0) goto L_0x02f8
        L_0x02cf:
            int r0 = r47.length()
            r8 = 1
            int r0 = r0 - r8
            char r0 = r1.charAt(r0)
            int r2 = r47.length()
            int r2 = r2 - r8
            r1.setLength(r2)
            r2 = 57
            if (r0 == r2) goto L_0x02e7
            r5 = r0
            goto L_0x02f1
        L_0x02e7:
            int r0 = r47.length()
            if (r0 != 0) goto L_0x02cf
            int r22 = r22 + 1
            r5 = 48
        L_0x02f1:
            int r5 = r5 + r8
            char r0 = (char) r5
            r1.append(r0)
            goto L_0x0220
        L_0x02f8:
            r8 = 1
            r9 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            double r9 = r9 - r6
            int r6 = (r31 > r9 ? 1 : (r31 == r9 ? 0 : -1))
            if (r6 >= 0) goto L_0x0305
            stripTrailingZeroes(r47)
            goto L_0x0220
        L_0x0305:
            r20 = 1
            goto L_0x0319
        L_0x0308:
            r9 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r8 = r8 + 1
            double r11 = r31 * r27
            goto L_0x02ba
        L_0x030f:
            r35 = r8
            r36 = r11
            r37 = r12
            r38 = r13
            r39 = r14
        L_0x0319:
            r6 = 0
            if (r20 == 0) goto L_0x0320
            r1.setLength(r6)
            goto L_0x0337
        L_0x0320:
            r9 = r2
            r2 = r22
            r7 = r31
            goto L_0x033d
        L_0x0326:
            r29 = r2
            r35 = r6
            r33 = r7
            r30 = r10
            r36 = r11
            r37 = r12
            r38 = r13
            r39 = r14
            r6 = 0
        L_0x0337:
            r2 = r29
            r7 = r33
            r9 = r35
        L_0x033d:
            r10 = r17[r6]
            r11 = 1
            if (r10 < 0) goto L_0x03ce
            r6 = 14
            if (r2 > r6) goto L_0x03ce
            double[] r0 = tens
            r13 = r0[r2]
            if (r5 >= 0) goto L_0x0374
            if (r9 > 0) goto L_0x0374
            if (r9 < 0) goto L_0x0369
            r3 = 4617315517961601024(0x4014000000000000, double:5.0)
            double r13 = r13 * r3
            int r0 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r0 < 0) goto L_0x0369
            if (r44 != 0) goto L_0x0360
            int r0 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r0 != 0) goto L_0x0360
            goto L_0x0369
        L_0x0360:
            r0 = 49
            r1.append(r0)
            r0 = 1
            int r2 = r2 + r0
            int r2 = r2 + r0
            return r2
        L_0x0369:
            r0 = 1
            r2 = 0
            r1.setLength(r2)
            r2 = 48
            r1.append(r2)
            return r0
        L_0x0374:
            r0 = 1
        L_0x0375:
            double r3 = r7 / r13
            long r3 = (long) r3
            double r5 = (double) r3
            double r5 = r5 * r13
            double r7 = r7 - r5
            long r5 = r3 + r25
            int r6 = (int) r5
            char r5 = (char) r6
            r1.append(r5)
            if (r0 != r9) goto L_0x03c0
            double r7 = r7 + r7
            int r0 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r0 > 0) goto L_0x0398
            if (r0 != 0) goto L_0x0396
            long r3 = r3 & r11
            r5 = 0
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 != 0) goto L_0x0398
            if (r44 == 0) goto L_0x0396
            goto L_0x0398
        L_0x0396:
            r3 = 1
            goto L_0x03c9
        L_0x0398:
            int r0 = r47.length()
            r3 = 1
            int r0 = r0 - r3
            char r0 = r1.charAt(r0)
            int r4 = r47.length()
            int r4 = r4 - r3
            r1.setLength(r4)
            r4 = 57
            if (r0 == r4) goto L_0x03b0
            r5 = r0
            goto L_0x03ba
        L_0x03b0:
            int r0 = r47.length()
            if (r0 != 0) goto L_0x0398
            int r2 = r2 + 1
            r5 = 48
        L_0x03ba:
            int r5 = r5 + r3
            char r0 = (char) r5
            r1.append(r0)
            goto L_0x03c9
        L_0x03c0:
            r3 = 1
            double r7 = r7 * r27
            r5 = 0
            int r4 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r4 != 0) goto L_0x03cb
        L_0x03c9:
            int r2 = r2 + r3
            return r2
        L_0x03cb:
            int r0 = r0 + 1
            goto L_0x0375
        L_0x03ce:
            if (r15 == 0) goto L_0x0410
            r5 = 2
            if (r0 >= r5) goto L_0x03e5
            if (r30 == 0) goto L_0x03d8
            int r10 = r10 + 1075
            goto L_0x03de
        L_0x03d8:
            r5 = 0
            r4 = r4[r5]
            int r4 = 54 - r4
            r10 = r4
        L_0x03de:
            r5 = r36
            r13 = r38
            r14 = r39
            goto L_0x0403
        L_0x03e5:
            int r4 = r9 + -1
            r13 = r38
            if (r13 < r4) goto L_0x03f0
            int r4 = r13 - r4
            r14 = r39
            goto L_0x03f5
        L_0x03f0:
            int r4 = r4 - r13
            int r14 = r39 + r4
            int r13 = r13 + r4
            r4 = 0
        L_0x03f5:
            if (r9 >= 0) goto L_0x03fd
            int r5 = r36 - r9
            r38 = r13
            r10 = 0
            goto L_0x0402
        L_0x03fd:
            r10 = r9
            r38 = r13
            r5 = r36
        L_0x0402:
            r13 = r4
        L_0x0403:
            int r4 = r36 + r10
            int r6 = r37 + r10
            java.math.BigInteger r10 = java.math.BigInteger.valueOf(r11)
            r40 = r14
            r14 = r38
            goto L_0x041b
        L_0x0410:
            r13 = r38
            r10 = 0
            r14 = r13
            r4 = r36
            r5 = r4
            r6 = r37
            r40 = r39
        L_0x041b:
            if (r5 <= 0) goto L_0x042c
            if (r6 <= 0) goto L_0x042c
            if (r5 >= r6) goto L_0x0424
            r17 = r5
            goto L_0x0426
        L_0x0424:
            r17 = r6
        L_0x0426:
            int r4 = r4 - r17
            int r5 = r5 - r17
            int r6 = r6 - r17
        L_0x042c:
            if (r14 <= 0) goto L_0x044e
            if (r15 == 0) goto L_0x0447
            if (r13 <= 0) goto L_0x043d
            java.math.BigInteger r10 = pow5mult(r10, r13)
            r11 = r24
            java.math.BigInteger r11 = r10.multiply(r11)
            goto L_0x043f
        L_0x043d:
            r11 = r24
        L_0x043f:
            int r14 = r14 - r13
            if (r14 == 0) goto L_0x0450
            java.math.BigInteger r11 = pow5mult(r11, r14)
            goto L_0x0450
        L_0x0447:
            r11 = r24
            java.math.BigInteger r11 = pow5mult(r11, r14)
            goto L_0x0450
        L_0x044e:
            r11 = r24
        L_0x0450:
            r12 = 1
            java.math.BigInteger r12 = java.math.BigInteger.valueOf(r12)
            r14 = r40
            if (r14 <= 0) goto L_0x045e
            java.math.BigInteger r12 = pow5mult(r12, r14)
        L_0x045e:
            r13 = 2
            if (r0 >= r13) goto L_0x0484
            int r13 = word1(r7)
            if (r13 != 0) goto L_0x0484
            int r13 = word0(r7)
            r17 = 1048575(0xfffff, float:1.469367E-39)
            r13 = r13 & r17
            if (r13 != 0) goto L_0x0484
            int r13 = word0(r7)
            r17 = 2145386496(0x7fe00000, float:NaN)
            r13 = r13 & r17
            if (r13 == 0) goto L_0x0484
            int r4 = r4 + 1
            int r6 = r6 + 1
            r42 = r9
            r13 = 1
            goto L_0x0487
        L_0x0484:
            r42 = r9
            r13 = 0
        L_0x0487:
            byte[] r9 = r12.toByteArray()
            r17 = r7
            r45 = r13
            r7 = 0
            r8 = 0
        L_0x0491:
            r13 = 4
            if (r7 >= r13) goto L_0x04a1
            int r8 = r8 << 8
            int r13 = r9.length
            if (r7 >= r13) goto L_0x049e
            byte r13 = r9[r7]
            r13 = r13 & 255(0xff, float:3.57E-43)
            r8 = r8 | r13
        L_0x049e:
            int r7 = r7 + 1
            goto L_0x0491
        L_0x04a1:
            if (r14 == 0) goto L_0x04ac
            int r7 = hi0bits(r8)
            r8 = 32
            int r7 = 32 - r7
            goto L_0x04ad
        L_0x04ac:
            r7 = 1
        L_0x04ad:
            int r7 = r7 + r6
            r7 = r7 & 31
            if (r7 == 0) goto L_0x04b4
            int r7 = 32 - r7
        L_0x04b4:
            r8 = 4
            if (r7 <= r8) goto L_0x04bd
            int r7 = r7 + -4
        L_0x04b9:
            int r4 = r4 + r7
            int r5 = r5 + r7
            int r6 = r6 + r7
            goto L_0x04c2
        L_0x04bd:
            if (r7 >= r8) goto L_0x04c2
            int r7 = r7 + 28
            goto L_0x04b9
        L_0x04c2:
            if (r4 <= 0) goto L_0x04c8
            java.math.BigInteger r11 = r11.shiftLeft(r4)
        L_0x04c8:
            if (r6 <= 0) goto L_0x04ce
            java.math.BigInteger r12 = r12.shiftLeft(r6)
        L_0x04ce:
            r6 = 10
            if (r3 == 0) goto L_0x04ef
            int r3 = r11.compareTo(r12)
            if (r3 >= 0) goto L_0x04ef
            int r2 = r2 + -1
            java.math.BigInteger r3 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r11 = r11.multiply(r3)
            if (r15 == 0) goto L_0x04ec
            java.math.BigInteger r3 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r10 = r10.multiply(r3)
        L_0x04ec:
            r3 = r23
            goto L_0x04f1
        L_0x04ef:
            r3 = r42
        L_0x04f1:
            if (r3 > 0) goto L_0x0523
            r4 = 2
            if (r0 <= r4) goto L_0x0523
            if (r3 < 0) goto L_0x0518
            r3 = 5
            java.math.BigInteger r0 = java.math.BigInteger.valueOf(r3)
            java.math.BigInteger r0 = r12.multiply(r0)
            int r0 = r11.compareTo(r0)
            if (r0 < 0) goto L_0x0518
            if (r0 != 0) goto L_0x050f
            if (r44 != 0) goto L_0x050f
            r0 = 0
            r4 = 1
            goto L_0x051a
        L_0x050f:
            r0 = 49
            r1.append(r0)
            r4 = 1
            int r2 = r2 + r4
            int r2 = r2 + r4
            return r2
        L_0x0518:
            r4 = 1
            r0 = 0
        L_0x051a:
            r1.setLength(r0)
            r0 = 48
            r1.append(r0)
            return r4
        L_0x0523:
            r4 = 1
            if (r15 == 0) goto L_0x0621
            if (r5 <= 0) goto L_0x052c
            java.math.BigInteger r10 = r10.shiftLeft(r5)
        L_0x052c:
            if (r45 == 0) goto L_0x0533
            java.math.BigInteger r5 = r10.shiftLeft(r4)
            goto L_0x0534
        L_0x0533:
            r5 = r10
        L_0x0534:
            r8 = 1
        L_0x0535:
            java.math.BigInteger[] r9 = r11.divideAndRemainder(r12)
            r11 = r9[r4]
            r4 = 0
            r9 = r9[r4]
            int r4 = r9.intValue()
            r9 = 48
            int r4 = r4 + r9
            char r4 = (char) r4
            int r9 = r11.compareTo(r10)
            java.math.BigInteger r13 = r12.subtract(r5)
            int r14 = r13.signum()
            if (r14 > 0) goto L_0x0556
            r13 = 1
            goto L_0x055a
        L_0x0556:
            int r13 = r11.compareTo(r13)
        L_0x055a:
            if (r13 != 0) goto L_0x0586
            if (r0 != 0) goto L_0x0586
            int r14 = word1(r17)
            r15 = 1
            r14 = r14 & r15
            if (r14 != 0) goto L_0x0587
            r14 = 57
            if (r4 != r14) goto L_0x057c
            r1.append(r14)
            boolean r0 = roundOff(r47)
            if (r0 == 0) goto L_0x057a
            int r2 = r2 + 1
            r0 = 49
            r1.append(r0)
        L_0x057a:
            int r2 = r2 + r15
            return r2
        L_0x057c:
            if (r9 <= 0) goto L_0x0581
            int r4 = r4 + 1
            char r4 = (char) r4
        L_0x0581:
            r1.append(r4)
            int r2 = r2 + r15
            return r2
        L_0x0586:
            r15 = 1
        L_0x0587:
            if (r9 < 0) goto L_0x05e9
            if (r9 != 0) goto L_0x0595
            if (r0 != 0) goto L_0x0595
            int r9 = word1(r17)
            r9 = r9 & r15
            if (r9 != 0) goto L_0x0595
            goto L_0x05e9
        L_0x0595:
            if (r13 <= 0) goto L_0x05b6
            r9 = 57
            if (r4 != r9) goto L_0x05ae
            r1.append(r9)
            boolean r0 = roundOff(r47)
            if (r0 == 0) goto L_0x05ab
            int r2 = r2 + 1
            r0 = 49
            r1.append(r0)
        L_0x05ab:
            r0 = 1
            int r2 = r2 + r0
            return r2
        L_0x05ae:
            r0 = 1
            int r4 = r4 + r0
            char r3 = (char) r4
            r1.append(r3)
            int r2 = r2 + r0
            return r2
        L_0x05b6:
            r1.append(r4)
            if (r8 != r3) goto L_0x05be
            r5 = 1
            goto L_0x0639
        L_0x05be:
            java.math.BigInteger r4 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r11 = r11.multiply(r4)
            if (r10 != r5) goto L_0x05d3
            java.math.BigInteger r4 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r4 = r5.multiply(r4)
            r5 = r4
            r10 = r5
            goto L_0x05e4
        L_0x05d3:
            java.math.BigInteger r4 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r4 = r10.multiply(r4)
            java.math.BigInteger r9 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r5 = r5.multiply(r9)
            r10 = r4
        L_0x05e4:
            int r8 = r8 + 1
            r4 = 1
            goto L_0x0535
        L_0x05e9:
            if (r13 <= 0) goto L_0x061b
            r0 = 1
            java.math.BigInteger r3 = r11.shiftLeft(r0)
            int r3 = r3.compareTo(r12)
            if (r3 > 0) goto L_0x05fe
            if (r3 != 0) goto L_0x061b
            r3 = r4 & 1
            if (r3 == r0) goto L_0x05fe
            if (r44 == 0) goto L_0x061b
        L_0x05fe:
            int r0 = r4 + 1
            char r0 = (char) r0
            r3 = 57
            if (r4 != r3) goto L_0x0618
            r1.append(r3)
            boolean r0 = roundOff(r47)
            if (r0 == 0) goto L_0x0615
            int r2 = r2 + 1
            r0 = 49
            r1.append(r0)
        L_0x0615:
            r5 = 1
            int r2 = r2 + r5
            return r2
        L_0x0618:
            r5 = 1
            r4 = r0
            goto L_0x061c
        L_0x061b:
            r5 = 1
        L_0x061c:
            r1.append(r4)
            int r2 = r2 + r5
            return r2
        L_0x0621:
            r5 = 1
            r0 = 1
        L_0x0623:
            java.math.BigInteger[] r4 = r11.divideAndRemainder(r12)
            r11 = r4[r5]
            r8 = 0
            r4 = r4[r8]
            int r4 = r4.intValue()
            r9 = 48
            int r4 = r4 + r9
            char r4 = (char) r4
            r1.append(r4)
            if (r0 < r3) goto L_0x0660
        L_0x0639:
            java.math.BigInteger r0 = r11.shiftLeft(r5)
            int r0 = r0.compareTo(r12)
            if (r0 > 0) goto L_0x0650
            if (r0 != 0) goto L_0x064c
            r0 = r4 & 1
            if (r0 == r5) goto L_0x0650
            if (r44 == 0) goto L_0x064c
            goto L_0x0650
        L_0x064c:
            stripTrailingZeroes(r47)
            goto L_0x065e
        L_0x0650:
            boolean r0 = roundOff(r47)
            if (r0 == 0) goto L_0x065e
            int r2 = r2 + r5
            r4 = 49
            r1.append(r4)
            int r2 = r2 + r5
            return r2
        L_0x065e:
            int r2 = r2 + r5
            return r2
        L_0x0660:
            r4 = 49
            java.math.BigInteger r10 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r11 = r11.multiply(r10)
            int r0 = r0 + 1
            goto L_0x0623
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.DToA.JS_dtoa(double, int, boolean, int, boolean[], java.lang.StringBuilder):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0114, code lost:
        if (r7 > 0) goto L_0x012f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0120, code lost:
        if (r9 > 0) goto L_0x012f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x012d, code lost:
        if (r6.compareTo(r3) > 0) goto L_0x012f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0140 A[LOOP:0: B:52:0x00d8->B:78:0x0140, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x013b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String JS_dtobasestr(int r11, double r12) {
        /*
            r0 = 2
            if (r0 > r11) goto L_0x0142
            r0 = 36
            if (r11 > r0) goto L_0x0142
            boolean r0 = java.lang.Double.isNaN(r12)
            if (r0 == 0) goto L_0x0010
            java.lang.String r11 = "NaN"
            return r11
        L_0x0010:
            boolean r0 = java.lang.Double.isInfinite(r12)
            r1 = 0
            if (r0 == 0) goto L_0x0022
            int r11 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r11 <= 0) goto L_0x001f
            java.lang.String r11 = "Infinity"
            goto L_0x0021
        L_0x001f:
            java.lang.String r11 = "-Infinity"
        L_0x0021:
            return r11
        L_0x0022:
            int r0 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r0 != 0) goto L_0x0029
            java.lang.String r11 = "0"
            return r11
        L_0x0029:
            r1 = 0
            r2 = 1
            if (r0 < 0) goto L_0x002f
            r0 = 0
            goto L_0x0031
        L_0x002f:
            double r12 = -r12
            r0 = 1
        L_0x0031:
            double r3 = java.lang.Math.floor(r12)
            long r5 = (long) r3
            double r7 = (double) r5
            int r9 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r9 != 0) goto L_0x0043
            if (r0 == 0) goto L_0x003e
            long r5 = -r5
        L_0x003e:
            java.lang.String r0 = java.lang.Long.toString(r5, r11)
            goto L_0x0076
        L_0x0043:
            long r5 = java.lang.Double.doubleToLongBits(r3)
            r7 = 52
            long r7 = r5 >> r7
            int r8 = (int) r7
            r7 = r8 & 2047(0x7ff, float:2.868E-42)
            r8 = 4503599627370495(0xfffffffffffff, double:2.225073858507201E-308)
            long r5 = r5 & r8
            if (r7 != 0) goto L_0x0058
            long r5 = r5 << r2
            goto L_0x005b
        L_0x0058:
            r8 = 4503599627370496(0x10000000000000, double:2.2250738585072014E-308)
            long r5 = r5 | r8
        L_0x005b:
            if (r0 == 0) goto L_0x005e
            long r5 = -r5
        L_0x005e:
            int r7 = r7 + -1075
            java.math.BigInteger r0 = java.math.BigInteger.valueOf(r5)
            if (r7 <= 0) goto L_0x006b
            java.math.BigInteger r0 = r0.shiftLeft(r7)
            goto L_0x0072
        L_0x006b:
            if (r7 >= 0) goto L_0x0072
            int r5 = -r7
            java.math.BigInteger r0 = r0.shiftRight(r5)
        L_0x0072:
            java.lang.String r0 = r0.toString(r11)
        L_0x0076:
            int r5 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x007b
            return r0
        L_0x007b:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r0)
            r0 = 46
            r5.append(r0)
            double r3 = r12 - r3
            long r12 = java.lang.Double.doubleToLongBits(r12)
            r0 = 32
            long r6 = r12 >> r0
            int r0 = (int) r6
            int r13 = (int) r12
            int[] r12 = new int[r2]
            int[] r6 = new int[r2]
            java.math.BigInteger r3 = d2b(r3, r12, r6)
            int r4 = r0 >>> 20
            r4 = r4 & 2047(0x7ff, float:2.868E-42)
            int r4 = -r4
            if (r4 != 0) goto L_0x00a4
            r4 = -1
        L_0x00a4:
            int r4 = r4 + 1076
            r6 = 1
            java.math.BigInteger r8 = java.math.BigInteger.valueOf(r6)
            if (r13 != 0) goto L_0x00c2
            r9 = 1048575(0xfffff, float:1.469367E-39)
            r9 = r9 & r0
            if (r9 != 0) goto L_0x00c2
            r9 = 2145386496(0x7fe00000, float:NaN)
            r0 = r0 & r9
            if (r0 == 0) goto L_0x00c2
            int r4 = r4 + 1
            r9 = 2
            java.math.BigInteger r0 = java.math.BigInteger.valueOf(r9)
            goto L_0x00c3
        L_0x00c2:
            r0 = r8
        L_0x00c3:
            r12 = r12[r1]
            int r12 = r12 + r4
            java.math.BigInteger r12 = r3.shiftLeft(r12)
            java.math.BigInteger r3 = java.math.BigInteger.valueOf(r6)
            java.math.BigInteger r3 = r3.shiftLeft(r4)
            long r6 = (long) r11
            java.math.BigInteger r4 = java.math.BigInteger.valueOf(r6)
            r11 = 0
        L_0x00d8:
            java.math.BigInteger r12 = r12.multiply(r4)
            java.math.BigInteger[] r12 = r12.divideAndRemainder(r3)
            r6 = r12[r2]
            r12 = r12[r1]
            int r12 = r12.intValue()
            char r12 = (char) r12
            if (r8 != r0) goto L_0x00f1
            java.math.BigInteger r0 = r8.multiply(r4)
            r8 = r0
            goto L_0x00fa
        L_0x00f1:
            java.math.BigInteger r7 = r8.multiply(r4)
            java.math.BigInteger r0 = r0.multiply(r4)
            r8 = r7
        L_0x00fa:
            int r7 = r6.compareTo(r8)
            java.math.BigInteger r9 = r3.subtract(r0)
            int r10 = r9.signum()
            if (r10 > 0) goto L_0x010a
            r9 = 1
            goto L_0x010e
        L_0x010a:
            int r9 = r6.compareTo(r9)
        L_0x010e:
            if (r9 != 0) goto L_0x0117
            r10 = r13 & 1
            if (r10 != 0) goto L_0x0117
            if (r7 <= 0) goto L_0x0131
            goto L_0x012f
        L_0x0117:
            if (r7 < 0) goto L_0x0123
            if (r7 != 0) goto L_0x0120
            r7 = r13 & 1
            if (r7 != 0) goto L_0x0120
            goto L_0x0123
        L_0x0120:
            if (r9 <= 0) goto L_0x0132
            goto L_0x012f
        L_0x0123:
            if (r9 <= 0) goto L_0x0131
            java.math.BigInteger r6 = r6.shiftLeft(r2)
            int r11 = r6.compareTo(r3)
            if (r11 <= 0) goto L_0x0131
        L_0x012f:
            int r12 = r12 + 1
        L_0x0131:
            r11 = 1
        L_0x0132:
            char r12 = BASEDIGIT(r12)
            r5.append(r12)
            if (r11 == 0) goto L_0x0140
            java.lang.String r11 = r5.toString()
            return r11
        L_0x0140:
            r12 = r6
            goto L_0x00d8
        L_0x0142:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.String r13 = "Bad base: "
            java.lang.String r11 = defpackage.y2.f(r13, r11)
            r12.<init>(r11)
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.DToA.JS_dtobasestr(int, double):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0045, code lost:
        if (r1 <= r13) goto L_0x0057;
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x005f A[LOOP:0: B:37:0x005f->B:38:0x0066, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0083  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void JS_dtostr(java.lang.StringBuilder r11, int r12, int r13, double r14) {
        /*
            r0 = 1
            boolean[] r8 = new boolean[r0]
            r9 = 0
            r10 = 2
            if (r12 != r10) goto L_0x001a
            r1 = 4921056587992461136(0x444b1ae4d6e2ef50, double:1.0E21)
            int r3 = (r14 > r1 ? 1 : (r14 == r1 ? 0 : -1))
            if (r3 >= 0) goto L_0x0019
            r1 = -4302315448862314672(0xc44b1ae4d6e2ef50, double:-1.0E21)
            int r3 = (r14 > r1 ? 1 : (r14 == r1 ? 0 : -1))
            if (r3 > 0) goto L_0x001a
        L_0x0019:
            r12 = 0
        L_0x001a:
            int[] r1 = dtoaModes
            r3 = r1[r12]
            if (r12 < r10) goto L_0x0022
            r4 = 1
            goto L_0x0023
        L_0x0022:
            r4 = 0
        L_0x0023:
            r1 = r14
            r5 = r13
            r6 = r8
            r7 = r11
            int r1 = JS_dtoa(r1, r3, r4, r5, r6, r7)
            int r2 = r11.length()
            r3 = 9999(0x270f, float:1.4012E-41)
            if (r1 == r3) goto L_0x0099
            r3 = -5
            if (r12 == 0) goto L_0x004f
            if (r12 == r0) goto L_0x004c
            if (r12 == r10) goto L_0x0048
            r4 = 3
            if (r12 == r4) goto L_0x004d
            r4 = 4
            if (r12 == r4) goto L_0x0043
            r12 = 0
        L_0x0041:
            r13 = 0
            goto L_0x005b
        L_0x0043:
            if (r1 < r3) goto L_0x004d
            if (r1 <= r13) goto L_0x0057
            goto L_0x004d
        L_0x0048:
            if (r13 < 0) goto L_0x0056
            int r13 = r13 + r1
            goto L_0x0057
        L_0x004c:
            r13 = 0
        L_0x004d:
            r12 = 1
            goto L_0x005b
        L_0x004f:
            if (r1 < r3) goto L_0x0059
            r12 = 21
            if (r1 <= r12) goto L_0x0056
            goto L_0x0059
        L_0x0056:
            r13 = r1
        L_0x0057:
            r12 = 0
            goto L_0x005b
        L_0x0059:
            r12 = 1
            goto L_0x0041
        L_0x005b:
            r3 = 48
            if (r2 >= r13) goto L_0x0069
        L_0x005f:
            r11.append(r3)
            int r2 = r11.length()
            if (r2 != r13) goto L_0x005f
            r2 = r13
        L_0x0069:
            r13 = 46
            if (r12 == 0) goto L_0x0083
            if (r2 == r0) goto L_0x0072
            r11.insert(r0, r13)
        L_0x0072:
            r12 = 101(0x65, float:1.42E-43)
            r11.append(r12)
            int r1 = r1 - r0
            if (r1 < 0) goto L_0x007f
            r12 = 43
            r11.append(r12)
        L_0x007f:
            r11.append(r1)
            goto L_0x0099
        L_0x0083:
            if (r1 == r2) goto L_0x0099
            if (r1 <= 0) goto L_0x008b
            r11.insert(r1, r13)
            goto L_0x0099
        L_0x008b:
            r12 = 0
        L_0x008c:
            int r2 = 1 - r1
            if (r12 >= r2) goto L_0x0096
            r11.insert(r9, r3)
            int r12 = r12 + 1
            goto L_0x008c
        L_0x0096:
            r11.insert(r0, r13)
        L_0x0099:
            boolean r12 = r8[r9]
            if (r12 == 0) goto L_0x00c9
            int r12 = word0(r14)
            r13 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r12 != r13) goto L_0x00ab
            int r12 = word1(r14)
            if (r12 == 0) goto L_0x00c9
        L_0x00ab:
            int r12 = word0(r14)
            r13 = 2146435072(0x7ff00000, float:NaN)
            r12 = r12 & r13
            if (r12 != r13) goto L_0x00c4
            int r12 = word1(r14)
            if (r12 != 0) goto L_0x00c9
            int r12 = word0(r14)
            r13 = 1048575(0xfffff, float:1.469367E-39)
            r12 = r12 & r13
            if (r12 != 0) goto L_0x00c9
        L_0x00c4:
            r12 = 45
            r11.insert(r9, r12)
        L_0x00c9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.DToA.JS_dtostr(java.lang.StringBuilder, int, int, double):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0058  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.math.BigInteger d2b(double r8, int[] r10, int[] r11) {
        /*
            long r8 = java.lang.Double.doubleToLongBits(r8)
            r0 = 32
            long r1 = r8 >>> r0
            int r2 = (int) r1
            int r9 = (int) r8
            r8 = 1048575(0xfffff, float:1.469367E-39)
            r8 = r8 & r2
            r1 = 2147483647(0x7fffffff, float:NaN)
            r1 = r1 & r2
            int r1 = r1 >>> 20
            if (r1 == 0) goto L_0x0019
            r2 = 1048576(0x100000, float:1.469368E-39)
            r8 = r8 | r2
        L_0x0019:
            r2 = 1
            r3 = 4
            r4 = 0
            if (r9 == 0) goto L_0x003d
            r5 = 8
            byte[] r5 = new byte[r5]
            int r6 = lo0bits(r9)
            int r9 = r9 >>> r6
            if (r6 == 0) goto L_0x0033
            int r7 = 32 - r6
            int r7 = r8 << r7
            r9 = r9 | r7
            stuffBits(r5, r3, r9)
            int r8 = r8 >> r6
            goto L_0x0036
        L_0x0033:
            stuffBits(r5, r3, r9)
        L_0x0036:
            stuffBits(r5, r4, r8)
            if (r8 == 0) goto L_0x0049
            r9 = 2
            goto L_0x004a
        L_0x003d:
            byte[] r5 = new byte[r3]
            int r9 = lo0bits(r8)
            int r8 = r8 >>> r9
            stuffBits(r5, r4, r8)
            int r6 = r9 + 32
        L_0x0049:
            r9 = 1
        L_0x004a:
            if (r1 == 0) goto L_0x0058
            int r1 = r1 + -1023
            int r1 = r1 + -52
            int r1 = r1 + r6
            r10[r4] = r1
            int r8 = 53 - r6
            r11[r4] = r8
            goto L_0x0069
        L_0x0058:
            int r1 = r1 + -1023
            int r1 = r1 + -52
            int r1 = r1 + r2
            int r1 = r1 + r6
            r10[r4] = r1
            int r9 = r9 * 32
            int r8 = hi0bits(r8)
            int r9 = r9 - r8
            r11[r4] = r9
        L_0x0069:
            java.math.BigInteger r8 = new java.math.BigInteger
            r8.<init>(r5)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.DToA.d2b(double, int[], int[]):java.math.BigInteger");
    }

    private static int hi0bits(int i) {
        int i2;
        if ((-65536 & i) == 0) {
            i <<= 16;
            i2 = 16;
        } else {
            i2 = 0;
        }
        if ((-16777216 & i) == 0) {
            i2 += 8;
            i <<= 8;
        }
        if ((-268435456 & i) == 0) {
            i2 += 4;
            i <<= 4;
        }
        if ((-1073741824 & i) == 0) {
            i2 += 2;
            i <<= 2;
        }
        if ((Integer.MIN_VALUE & i) == 0) {
            i2++;
            if ((i & 1073741824) == 0) {
                return 32;
            }
        }
        return i2;
    }

    private static int lo0bits(int i) {
        int i2 = 0;
        if ((i & 7) == 0) {
            if ((65535 & i) == 0) {
                i >>>= 16;
                i2 = 16;
            }
            if ((i & 255) == 0) {
                i2 += 8;
                i >>>= 8;
            }
            if ((i & 15) == 0) {
                i2 += 4;
                i >>>= 4;
            }
            if ((i & 3) == 0) {
                i2 += 2;
                i >>>= 2;
            }
            if ((i & 1) == 0) {
                i2++;
                if (((i >>> 1) & 1) == 0) {
                    return 32;
                }
            }
            return i2;
        } else if ((i & 1) != 0) {
            return 0;
        } else {
            return (i & 2) != 0 ? 1 : 2;
        }
    }

    public static BigInteger pow5mult(BigInteger bigInteger, int i) {
        return bigInteger.multiply(BigInteger.valueOf(5).pow(i));
    }

    public static boolean roundOff(StringBuilder sb) {
        int length = sb.length();
        while (length != 0) {
            length--;
            char charAt = sb.charAt(length);
            if (charAt != '9') {
                sb.setCharAt(length, (char) (charAt + 1));
                sb.setLength(length + 1);
                return false;
            }
        }
        sb.setLength(0);
        return true;
    }

    public static double setWord0(double d, int i) {
        return Double.longBitsToDouble((Double.doubleToLongBits(d) & 4294967295L) | (((long) i) << 32));
    }

    private static void stripTrailingZeroes(StringBuilder sb) {
        int i;
        int length = sb.length();
        while (true) {
            i = length - 1;
            if (length <= 0 || sb.charAt(i) != '0') {
                sb.setLength(i + 1);
            } else {
                length = i;
            }
        }
        sb.setLength(i + 1);
    }

    private static void stuffBits(byte[] bArr, int i, int i2) {
        bArr[i] = (byte) (i2 >> 24);
        bArr[i + 1] = (byte) (i2 >> 16);
        bArr[i + 2] = (byte) (i2 >> 8);
        bArr[i + 3] = (byte) i2;
    }

    public static int word0(double d) {
        return (int) (Double.doubleToLongBits(d) >> 32);
    }

    public static int word1(double d) {
        return (int) Double.doubleToLongBits(d);
    }
}
