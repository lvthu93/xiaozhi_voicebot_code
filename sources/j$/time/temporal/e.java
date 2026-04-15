package j$.time.temporal;

import j$.lang.a;
import j$.time.DateTimeException;
import j$.time.LocalDate;
import j$.time.format.E;
import java.util.HashMap;

/* 'enum' modifier removed */
final class e extends g {
    e() {
        super("WEEK_OF_WEEK_BASED_YEAR", 2);
    }

    public final long E(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return (long) g.R(LocalDate.S(temporalAccessor));
        }
        throw new t("Unsupported field: WeekOfWeekBasedYear");
    }

    public final l H(l lVar, long j) {
        m().b(j, this);
        return lVar.d(a.n(j, E(lVar)), ChronoUnit.WEEKS);
    }

    public final boolean k(TemporalAccessor temporalAccessor) {
        return temporalAccessor.e(a.EPOCH_DAY) && i.a(temporalAccessor);
    }

    public final u l(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return g.U(LocalDate.S(temporalAccessor));
        }
        throw new t("Unsupported field: WeekOfWeekBasedYear");
    }

    public final u m() {
        return u.l(52, 53);
    }

    public final TemporalAccessor p(HashMap hashMap, TemporalAccessor temporalAccessor, E e) {
        LocalDate localDate;
        long j;
        LocalDate i0;
        long j2;
        HashMap hashMap2 = hashMap;
        E e2 = e;
        g gVar = g.WEEK_BASED_YEAR;
        Long l = (Long) hashMap2.get(gVar);
        a aVar = a.DAY_OF_WEEK;
        Long l2 = (Long) hashMap2.get(aVar);
        if (l == null || l2 == null) {
            return null;
        }
        int a = gVar.m().a(l.longValue(), gVar);
        long longValue = ((Long) hashMap2.get(g.WEEK_OF_WEEK_BASED_YEAR)).longValue();
        if (i.a(temporalAccessor)) {
            LocalDate of = LocalDate.of(a, 1, 4);
            if (e2 == E.LENIENT) {
                long longValue2 = l2.longValue();
                if (longValue2 > 7) {
                    j2 = longValue2 - 1;
                    i0 = of.i0(j2 / 7);
                } else {
                    j = 1;
                    if (longValue2 < 1) {
                        i0 = of.i0(a.n(longValue2, 7) / 7);
                        j2 = longValue2 + 6;
                    }
                    localDate = of.i0(a.n(longValue, j)).c(longValue2, aVar);
                }
                of = i0;
                j = 1;
                longValue2 = (j2 % 7) + 1;
                localDate = of.i0(a.n(longValue, j)).c(longValue2, aVar);
            } else {
                int Q = aVar.Q(l2.longValue());
                if (longValue < 1 || longValue > 52) {
                    (e2 == E.STRICT ? g.U(of) : m()).b(longValue, this);
                }
                localDate = of.i0(longValue - 1).c((long) Q, aVar);
            }
            hashMap2.remove(this);
            hashMap2.remove(gVar);
            hashMap2.remove(aVar);
            return localDate;
        }
        throw new DateTimeException("Resolve requires IsoChronology");
    }

    public final String toString() {
        return "WeekOfWeekBasedYear";
    }
}
