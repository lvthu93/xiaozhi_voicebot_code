package j$.time.format;

import j$.time.DateTimeException;
import j$.time.LocalDate;
import j$.time.chrono.C0038b;
import j$.time.chrono.C0039c;
import j$.util.Objects;

final class q extends k {
    static final LocalDate i = LocalDate.of(2000, 1, 1);
    private final int g;
    private final C0039c h;

    private q(j$.time.temporal.q qVar, int i2, int i3, int i4, C0039c cVar, int i5) {
        super(qVar, i2, i3, F.NOT_NEGATIVE, i5);
        this.g = i4;
        this.h = cVar;
    }

    q(j$.time.temporal.q qVar, LocalDate localDate) {
        this(qVar, 2, 2, 0, localDate, 0);
        if (localDate == null) {
            long j = (long) 0;
            if (!qVar.m().i(j)) {
                throw new IllegalArgumentException("The base value must be within the range of the field");
            } else if (j + k.f[2] > 2147483647L) {
                throw new DateTimeException("Unable to add printer-parser as the range exceeds the capacity of an int");
            }
        }
    }

    /* synthetic */ q(j$.time.temporal.q qVar, LocalDate localDate, int i2) {
        this(qVar, 2, 2, 0, localDate, i2);
    }

    /* access modifiers changed from: package-private */
    public final long b(z zVar, long j) {
        long abs = Math.abs(j);
        C0039c cVar = this.h;
        long k = (long) (cVar != null ? C0038b.s(zVar.d()).B(cVar).k(this.a) : this.g);
        long[] jArr = k.f;
        if (j >= k) {
            long j2 = jArr[this.b];
            if (j < k + j2) {
                return abs % j2;
            }
        }
        return abs % jArr[this.c];
    }

    /* access modifiers changed from: package-private */
    public final boolean c(x xVar) {
        if (!xVar.l()) {
            return false;
        }
        return super.c(xVar);
    }

    /* access modifiers changed from: package-private */
    public final int d(x xVar, long j, int i2, int i3) {
        int i4;
        C0039c cVar = this.h;
        if (cVar != null) {
            i4 = xVar.h().B(cVar).k(this.a);
            xVar.a(new p(this, xVar, j, i2, i3));
        } else {
            i4 = this.g;
        }
        int i5 = i3 - i2;
        int i6 = this.b;
        if (i5 == i6 && j >= 0) {
            long j2 = k.f[i6];
            long j3 = (long) i4;
            long j4 = j3 - (j3 % j2);
            j = i4 > 0 ? j4 + j : j4 - j;
            if (j < j3) {
                j += j2;
            }
        }
        return xVar.o(this.a, j, i2, i3);
    }

    /* access modifiers changed from: package-private */
    public final k e() {
        return this.e == -1 ? this : new q(this.a, this.b, this.c, this.g, this.h, -1);
    }

    /* access modifiers changed from: package-private */
    public final k f(int i2) {
        return new q(this.a, this.b, this.c, this.g, this.h, this.e + i2);
    }

    public final String toString() {
        Object requireNonNullElse = Objects.requireNonNullElse(this.h, Integer.valueOf(this.g));
        return "ReducedValue(" + this.a + "," + this.b + "," + this.c + "," + requireNonNullElse + ")";
    }
}
