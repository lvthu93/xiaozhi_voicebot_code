package org.mozilla.javascript.v8dtoa;

public class FastDtoa {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kFastDtoaMaximalLength = 17;
    static final int kTen4 = 10000;
    static final int kTen5 = 100000;
    static final int kTen6 = 1000000;
    static final int kTen7 = 10000000;
    static final int kTen8 = 100000000;
    static final int kTen9 = 1000000000;
    static final int maximal_target_exponent = -32;
    static final int minimal_target_exponent = -60;

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0066, code lost:
        if (1 <= r4) goto L_0x006c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long biggestPowerTen(int r4, int r5) {
        /*
            r0 = 1
            r1 = 0
            switch(r5) {
                case 0: goto L_0x0069;
                case 1: goto L_0x0066;
                case 2: goto L_0x0066;
                case 3: goto L_0x0066;
                case 4: goto L_0x005e;
                case 5: goto L_0x005e;
                case 6: goto L_0x005e;
                case 7: goto L_0x0055;
                case 8: goto L_0x0055;
                case 9: goto L_0x0055;
                case 10: goto L_0x004c;
                case 11: goto L_0x004c;
                case 12: goto L_0x004c;
                case 13: goto L_0x004c;
                case 14: goto L_0x0043;
                case 15: goto L_0x0043;
                case 16: goto L_0x0043;
                case 17: goto L_0x0038;
                case 18: goto L_0x0038;
                case 19: goto L_0x0038;
                case 20: goto L_0x002d;
                case 21: goto L_0x002d;
                case 22: goto L_0x002d;
                case 23: goto L_0x002d;
                case 24: goto L_0x0022;
                case 25: goto L_0x0022;
                case 26: goto L_0x0022;
                case 27: goto L_0x0015;
                case 28: goto L_0x0015;
                case 29: goto L_0x0015;
                case 30: goto L_0x0008;
                case 31: goto L_0x0008;
                case 32: goto L_0x0008;
                default: goto L_0x0005;
            }
        L_0x0005:
            r0 = 0
            goto L_0x006c
        L_0x0008:
            r5 = 1000000000(0x3b9aca00, float:0.0047237873)
            if (r5 > r4) goto L_0x0015
            r0 = 9
            r0 = 1000000000(0x3b9aca00, float:0.0047237873)
            r1 = 9
            goto L_0x006c
        L_0x0015:
            r5 = 100000000(0x5f5e100, float:2.3122341E-35)
            if (r5 > r4) goto L_0x0022
            r0 = 8
            r0 = 100000000(0x5f5e100, float:2.3122341E-35)
            r1 = 8
            goto L_0x006c
        L_0x0022:
            r5 = 10000000(0x989680, float:1.4012985E-38)
            if (r5 > r4) goto L_0x002d
            r0 = 7
            r0 = 10000000(0x989680, float:1.4012985E-38)
            r1 = 7
            goto L_0x006c
        L_0x002d:
            r5 = 1000000(0xf4240, float:1.401298E-39)
            if (r5 > r4) goto L_0x0038
            r0 = 6
            r0 = 1000000(0xf4240, float:1.401298E-39)
            r1 = 6
            goto L_0x006c
        L_0x0038:
            r5 = 100000(0x186a0, float:1.4013E-40)
            if (r5 > r4) goto L_0x0043
            r0 = 5
            r0 = 100000(0x186a0, float:1.4013E-40)
            r1 = 5
            goto L_0x006c
        L_0x0043:
            r5 = 10000(0x2710, float:1.4013E-41)
            if (r5 > r4) goto L_0x004c
            r0 = 4
            r0 = 10000(0x2710, float:1.4013E-41)
            r1 = 4
            goto L_0x006c
        L_0x004c:
            r5 = 1000(0x3e8, float:1.401E-42)
            if (r5 > r4) goto L_0x0055
            r0 = 3
            r0 = 1000(0x3e8, float:1.401E-42)
            r1 = 3
            goto L_0x006c
        L_0x0055:
            r5 = 100
            if (r5 > r4) goto L_0x005e
            r0 = 2
            r0 = 100
            r1 = 2
            goto L_0x006c
        L_0x005e:
            r5 = 10
            if (r5 > r4) goto L_0x0066
            r0 = 10
            r1 = 1
            goto L_0x006c
        L_0x0066:
            if (r0 > r4) goto L_0x0069
            goto L_0x006c
        L_0x0069:
            r0 = -1
            r0 = 0
            r1 = -1
        L_0x006c:
            long r4 = (long) r0
            r0 = 32
            long r4 = r4 << r0
            r2 = 4294967295(0xffffffff, double:2.1219957905E-314)
            long r0 = (long) r1
            long r0 = r0 & r2
            long r4 = r4 | r0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mozilla.javascript.v8dtoa.FastDtoa.biggestPowerTen(int, int):long");
    }

    public static boolean digitGen(DiyFp diyFp, DiyFp diyFp2, DiyFp diyFp3, FastDtoaBuilder fastDtoaBuilder, int i) {
        DiyFp diyFp4 = diyFp2;
        FastDtoaBuilder fastDtoaBuilder2 = fastDtoaBuilder;
        DiyFp diyFp5 = new DiyFp(diyFp.f() - 1, diyFp.e());
        DiyFp diyFp6 = new DiyFp(diyFp3.f() + 1, diyFp3.e());
        DiyFp minus = DiyFp.minus(diyFp6, diyFp5);
        DiyFp diyFp7 = new DiyFp(1 << (-diyFp2.e()), diyFp2.e());
        int f = (int) ((diyFp6.f() >>> (-diyFp7.e())) & 4294967295L);
        long f2 = diyFp6.f() & (diyFp7.f() - 1);
        long biggestPowerTen = biggestPowerTen(f, 64 - (-diyFp7.e()));
        int i2 = (int) ((biggestPowerTen >>> 32) & 4294967295L);
        int i3 = ((int) (biggestPowerTen & 4294967295L)) + 1;
        while (i3 > 0) {
            fastDtoaBuilder2.append((char) ((f / i2) + 48));
            f %= i2;
            i3--;
            long j = (((long) f) << (-diyFp7.e())) + f2;
            if (j < minus.f()) {
                fastDtoaBuilder2.point = (fastDtoaBuilder2.end - i) + i3;
                return roundWeed(fastDtoaBuilder, DiyFp.minus(diyFp6, diyFp4).f(), minus.f(), j, ((long) i2) << (-diyFp7.e()), 1);
            }
            i2 /= 10;
        }
        long j2 = 1;
        while (true) {
            long j3 = f2 * 5;
            j2 *= 5;
            minus.setF(minus.f() * 5);
            minus.setE(minus.e() + 1);
            diyFp7.setF(diyFp7.f() >>> 1);
            diyFp7.setE(diyFp7.e() + 1);
            fastDtoaBuilder2.append((char) (((int) ((j3 >>> (-diyFp7.e())) & 4294967295L)) + 48));
            f2 = j3 & (diyFp7.f() - 1);
            i3--;
            if (f2 < minus.f()) {
                fastDtoaBuilder2.point = (fastDtoaBuilder2.end - i) + i3;
                return roundWeed(fastDtoaBuilder, DiyFp.minus(diyFp6, diyFp4).f() * j2, minus.f(), f2, diyFp7.f(), j2);
            }
        }
    }

    public static boolean dtoa(double d, FastDtoaBuilder fastDtoaBuilder) {
        return grisu3(d, fastDtoaBuilder);
    }

    public static boolean grisu3(double d, FastDtoaBuilder fastDtoaBuilder) {
        long doubleToLongBits = Double.doubleToLongBits(d);
        DiyFp asNormalizedDiyFp = DoubleHelper.asNormalizedDiyFp(doubleToLongBits);
        DiyFp diyFp = new DiyFp();
        DiyFp diyFp2 = new DiyFp();
        DoubleHelper.normalizedBoundaries(doubleToLongBits, diyFp, diyFp2);
        DiyFp diyFp3 = new DiyFp();
        int cachedPower = CachedPowers.getCachedPower(asNormalizedDiyFp.e() + 64, minimal_target_exponent, maximal_target_exponent, diyFp3);
        return digitGen(DiyFp.times(diyFp, diyFp3), DiyFp.times(asNormalizedDiyFp, diyFp3), DiyFp.times(diyFp2, diyFp3), fastDtoaBuilder, cachedPower);
    }

    public static String numberToString(double d) {
        FastDtoaBuilder fastDtoaBuilder = new FastDtoaBuilder();
        if (numberToString(d, fastDtoaBuilder)) {
            return fastDtoaBuilder.format();
        }
        return null;
    }

    public static boolean roundWeed(FastDtoaBuilder fastDtoaBuilder, long j, long j2, long j3, long j4, long j5) {
        long j6 = j - j5;
        long j7 = j + j5;
        long j8 = j3;
        while (j8 < j6 && j2 - j8 >= j4) {
            long j9 = j8 + j4;
            if (j9 >= j6 && j6 - j8 < j9 - j6) {
                break;
            }
            fastDtoaBuilder.decreaseLast();
            j8 = j9;
        }
        if (j8 < j7 && j2 - j8 >= j4) {
            long j10 = j8 + j4;
            if (j10 < j7 || j7 - j8 > j10 - j7) {
                return false;
            }
        }
        return 2 * j5 <= j8 && j8 <= j2 - (4 * j5);
    }

    private static boolean uint64_lte(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        if (i == 0) {
            return true;
        }
        return (((j > 0 ? 1 : (j == 0 ? 0 : -1)) < 0) ^ (i < 0)) ^ ((j2 > 0 ? 1 : (j2 == 0 ? 0 : -1)) < 0);
    }

    public static boolean numberToString(double d, FastDtoaBuilder fastDtoaBuilder) {
        fastDtoaBuilder.reset();
        if (d < 0.0d) {
            fastDtoaBuilder.append('-');
            d = -d;
        }
        return dtoa(d, fastDtoaBuilder);
    }
}
