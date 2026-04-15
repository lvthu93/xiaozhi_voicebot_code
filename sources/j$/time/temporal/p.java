package j$.time.temporal;

import j$.time.DateTimeException;
import j$.time.c;
import j$.util.Objects;

public abstract class p {
    static final s a = new r(0);
    static final s b = new r(1);
    static final s c = new r(2);
    static final s d = new r(3);
    static final s e = new r(4);
    static final s f = new r(5);
    static final s g = new r(6);

    public static int a(TemporalAccessor temporalAccessor, q qVar) {
        u m = temporalAccessor.m(qVar);
        if (m.h()) {
            long E = temporalAccessor.E(qVar);
            if (m.i(E)) {
                return (int) E;
            }
            throw new DateTimeException("Invalid value for " + qVar + " (valid values " + m + "): " + E);
        }
        throw new t("Invalid field " + qVar + " for get() method, use getLong() instead");
    }

    public static l b(l lVar, long j, ChronoUnit chronoUnit) {
        long j2;
        if (j == Long.MIN_VALUE) {
            lVar = lVar.d(Long.MAX_VALUE, chronoUnit);
            j2 = 1;
        } else {
            j2 = -j;
        }
        return lVar.d(j2, chronoUnit);
    }

    public static Object c(TemporalAccessor temporalAccessor, s sVar) {
        if (sVar == a || sVar == b || sVar == c) {
            return null;
        }
        return sVar.a(temporalAccessor);
    }

    public static u d(TemporalAccessor temporalAccessor, q qVar) {
        if (!(qVar instanceof a)) {
            Objects.requireNonNull(qVar, "field");
            return qVar.l(temporalAccessor);
        } else if (temporalAccessor.e(qVar)) {
            return qVar.m();
        } else {
            throw new t(c.a("Unsupported field: ", qVar));
        }
    }

    public static s e() {
        return b;
    }

    public static s f() {
        return f;
    }

    public static s g() {
        return g;
    }

    public static /* synthetic */ int h(int i) {
        int i2 = i % 7;
        if (i2 == 0) {
            return 0;
        }
        return (((i ^ 7) >> 31) | 1) > 0 ? i2 : i2 + 7;
    }

    public static s i() {
        return d;
    }

    public static s j() {
        return c;
    }

    public static s k() {
        return e;
    }

    public static s l() {
        return a;
    }
}
