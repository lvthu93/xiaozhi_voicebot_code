package org.mozilla.javascript.v8dtoa;

public final class DoubleConversion {
    private static final int kDenormalExponent = -1074;
    private static final int kExponentBias = 1075;
    private static final long kExponentMask = 9218868437227405312L;
    private static final long kHiddenBit = 4503599627370496L;
    private static final int kPhysicalSignificandSize = 52;
    private static final long kSignMask = Long.MIN_VALUE;
    private static final long kSignificandMask = 4503599627370495L;
    private static final int kSignificandSize = 53;

    private DoubleConversion() {
    }

    public static int doubleToInt32(double d) {
        long j;
        int i = (int) d;
        if (((double) i) == d) {
            return i;
        }
        long doubleToLongBits = Double.doubleToLongBits(d);
        int exponent = exponent(doubleToLongBits);
        if (exponent <= -53 || exponent > 31) {
            return 0;
        }
        long significand = significand(doubleToLongBits);
        int sign = sign(doubleToLongBits);
        if (exponent < 0) {
            j = significand >> (-exponent);
        } else {
            j = significand << exponent;
        }
        return sign * ((int) j);
    }

    private static int exponent(long j) {
        return isDenormal(j) ? kDenormalExponent : ((int) ((j & kExponentMask) >> 52)) - 1075;
    }

    private static boolean isDenormal(long j) {
        return (j & kExponentMask) == 0;
    }

    private static int sign(long j) {
        return (j & kSignMask) == 0 ? 1 : -1;
    }

    private static long significand(long j) {
        long j2 = kSignificandMask & j;
        return !isDenormal(j) ? j2 + kHiddenBit : j2;
    }
}
