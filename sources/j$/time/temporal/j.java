package j$.time.temporal;

import j$.lang.a;
import j$.time.DateTimeException;
import j$.time.chrono.C0038b;
import j$.time.chrono.m;
import j$.time.format.E;
import java.util.HashMap;

enum j implements q {
    JULIAN_DAY("JulianDay", r17, r18, 2440588),
    MODIFIED_JULIAN_DAY("ModifiedJulianDay", r13, r14, 40587),
    RATA_DIE("RataDie", r13, r14, 719163);
    
    private static final long serialVersionUID = -7501623920830201812L;
    private final transient String a;
    private final transient TemporalUnit b;
    private final transient TemporalUnit c;
    private final transient u d;
    private final transient long e;

    private j(String str, ChronoUnit chronoUnit, ChronoUnit chronoUnit2, long j) {
        this.a = str;
        this.b = chronoUnit;
        this.c = chronoUnit2;
        this.d = u.j(-365243219162L + j, 365241780471L + j);
        this.e = j;
    }

    public final long E(TemporalAccessor temporalAccessor) {
        return temporalAccessor.E(a.EPOCH_DAY) + this.e;
    }

    public final l H(l lVar, long j) {
        if (this.d.i(j)) {
            return lVar.c(a.n(j, this.e), a.EPOCH_DAY);
        }
        throw new DateTimeException("Invalid value: " + this.a + " " + j);
    }

    public final boolean isDateBased() {
        return true;
    }

    public final boolean isTimeBased() {
        return false;
    }

    public final boolean k(TemporalAccessor temporalAccessor) {
        return temporalAccessor.e(a.EPOCH_DAY);
    }

    public final u l(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return this.d;
        }
        throw new DateTimeException("Unsupported field: " + this);
    }

    public final u m() {
        return this.d;
    }

    public final TemporalAccessor p(HashMap hashMap, TemporalAccessor temporalAccessor, E e2) {
        long longValue = ((Long) hashMap.remove(this)).longValue();
        m s = C0038b.s(temporalAccessor);
        E e3 = E.LENIENT;
        long j = this.e;
        if (e2 == e3) {
            return s.i(a.n(longValue, j));
        }
        this.d.b(longValue, this);
        return s.i(longValue - j);
    }

    public final String toString() {
        return this.a;
    }
}
