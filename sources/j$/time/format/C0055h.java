package j$.time.format;

import j$.time.temporal.a;
import j$.time.temporal.q;
import j$.time.temporal.u;
import j$.util.Objects;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* renamed from: j$.time.format.h  reason: case insensitive filesystem */
final class C0055h extends k {
    private final boolean g;

    C0055h(a aVar, int i, int i2, boolean z) {
        this(aVar, i, i2, z, 0);
        Objects.requireNonNull(aVar, "field");
        if (!aVar.m().g()) {
            throw new IllegalArgumentException("Field must have a fixed set of values: " + aVar);
        } else if (i < 0 || i > 9) {
            throw new IllegalArgumentException("Minimum width must be from 0 to 9 inclusive but was " + i);
        } else if (i2 < 1 || i2 > 9) {
            throw new IllegalArgumentException("Maximum width must be from 1 to 9 inclusive but was " + i2);
        } else if (i2 < i) {
            throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " + i2 + " < " + i);
        }
    }

    C0055h(q qVar, int i, int i2, boolean z, int i3) {
        super(qVar, i, i2, F.NOT_NEGATIVE, i3);
        this.g = z;
    }

    /* access modifiers changed from: package-private */
    public final boolean c(x xVar) {
        return xVar.l() && this.b == this.c && !this.g;
    }

    /* access modifiers changed from: package-private */
    public final k e() {
        return this.e == -1 ? this : new C0055h(this.a, this.b, this.c, this.g, -1);
    }

    /* access modifiers changed from: package-private */
    public final k f(int i) {
        return new C0055h(this.a, this.b, this.c, this.g, this.e + i);
    }

    public final boolean k(z zVar, StringBuilder sb) {
        q qVar = this.a;
        Long e = zVar.e(qVar);
        if (e == null) {
            return false;
        }
        C b = zVar.b();
        long longValue = e.longValue();
        u m = qVar.m();
        m.b(longValue, qVar);
        BigDecimal valueOf = BigDecimal.valueOf(m.e());
        BigDecimal divide = BigDecimal.valueOf(longValue).subtract(valueOf).divide(BigDecimal.valueOf(m.d()).subtract(valueOf).add(BigDecimal.ONE), 9, RoundingMode.FLOOR);
        BigDecimal stripTrailingZeros = divide.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : divide.stripTrailingZeros();
        int scale = stripTrailingZeros.scale();
        boolean z = this.g;
        int i = this.b;
        if (scale != 0) {
            String substring = stripTrailingZeros.setScale(Math.min(Math.max(stripTrailingZeros.scale(), i), this.c), RoundingMode.FLOOR).toPlainString().substring(2);
            b.getClass();
            if (z) {
                sb.append('.');
            }
            sb.append(substring);
            return true;
        } else if (i <= 0) {
            return true;
        } else {
            if (z) {
                b.getClass();
                sb.append('.');
            }
            for (int i2 = 0; i2 < i; i2++) {
                b.getClass();
                sb.append('0');
            }
            return true;
        }
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        int i2;
        int i3 = (xVar.l() || c(xVar)) ? this.b : 0;
        int i4 = (xVar.l() || c(xVar)) ? this.c : 9;
        int length = charSequence.length();
        if (i == length) {
            return i3 > 0 ? ~i : i;
        }
        if (this.g) {
            char charAt = charSequence.charAt(i);
            xVar.g().getClass();
            if (charAt != '.') {
                return i3 > 0 ? ~i : i;
            }
            i++;
        }
        int i5 = i;
        int i6 = i3 + i5;
        if (i6 > length) {
            return ~i5;
        }
        int min = Math.min(i4 + i5, length);
        int i7 = i5;
        int i8 = 0;
        while (true) {
            if (i7 >= min) {
                i2 = i7;
                break;
            }
            int i9 = i7 + 1;
            int a = xVar.g().a(charSequence.charAt(i7));
            if (a >= 0) {
                i8 = (i8 * 10) + a;
                i7 = i9;
            } else if (i9 < i6) {
                return ~i5;
            } else {
                i2 = i9 - 1;
            }
        }
        BigDecimal movePointLeft = new BigDecimal(i8).movePointLeft(i2 - i5);
        u m = this.a.m();
        BigDecimal valueOf = BigDecimal.valueOf(m.e());
        return xVar.o(this.a, movePointLeft.multiply(BigDecimal.valueOf(m.d()).subtract(valueOf).add(BigDecimal.ONE)).setScale(0, RoundingMode.FLOOR).add(valueOf).longValueExact(), i5, i2);
    }

    public final String toString() {
        String str = this.g ? ",DecimalPoint" : "";
        return "Fraction(" + this.a + "," + this.b + "," + this.c + str + ")";
    }
}
