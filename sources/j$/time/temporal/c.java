package j$.time.temporal;

import j$.lang.a;
import j$.time.DateTimeException;
import j$.time.LocalDate;
import j$.time.chrono.t;
import j$.time.format.E;
import java.util.HashMap;

/* 'enum' modifier removed */
final class c extends g {
    c() {
        super("DAY_OF_QUARTER", 0);
    }

    public final long E(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return (long) (temporalAccessor.k(a.DAY_OF_YEAR) - g.a[((temporalAccessor.k(a.MONTH_OF_YEAR) - 1) / 3) + (t.d.O(temporalAccessor.E(a.YEAR)) ? 4 : 0)]);
        }
        throw new t("Unsupported field: DayOfQuarter");
    }

    public final l H(l lVar, long j) {
        long E = E(lVar);
        m().b(j, this);
        a aVar = a.DAY_OF_YEAR;
        return lVar.c((j - E) + lVar.E(aVar), aVar);
    }

    public final boolean k(TemporalAccessor temporalAccessor) {
        return temporalAccessor.e(a.DAY_OF_YEAR) && temporalAccessor.e(a.MONTH_OF_YEAR) && temporalAccessor.e(a.YEAR) && i.a(temporalAccessor);
    }

    public final u l(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            long E = temporalAccessor.E(g.QUARTER_OF_YEAR);
            if (E != 1) {
                return E == 2 ? u.j(1, 91) : (E == 3 || E == 4) ? u.j(1, 92) : m();
            }
            return t.d.O(temporalAccessor.E(a.YEAR)) ? u.j(1, 91) : u.j(1, 90);
        }
        throw new t("Unsupported field: DayOfQuarter");
    }

    public final u m() {
        return u.l(90, 92);
    }

    public final TemporalAccessor p(HashMap hashMap, TemporalAccessor temporalAccessor, E e) {
        long j;
        LocalDate localDate;
        HashMap hashMap2 = hashMap;
        E e2 = e;
        a aVar = a.YEAR;
        Long l = (Long) hashMap2.get(aVar);
        g gVar = g.QUARTER_OF_YEAR;
        Long l2 = (Long) hashMap2.get(gVar);
        if (l == null || l2 == null) {
            return null;
        }
        int Q = aVar.Q(l.longValue());
        long longValue = ((Long) hashMap2.get(g.DAY_OF_QUARTER)).longValue();
        if (i.a(temporalAccessor)) {
            if (e2 == E.LENIENT) {
                localDate = LocalDate.of(Q, 1, 1).h0(a.m(a.n(l2.longValue(), 1), (long) 3));
                j = a.n(longValue, 1);
            } else {
                LocalDate of = LocalDate.of(Q, ((gVar.m().a(l2.longValue(), gVar) - 1) * 3) + 1, 1);
                if (longValue < 1 || longValue > 90) {
                    (e2 == E.STRICT ? l(of) : m()).b(longValue, this);
                }
                j = longValue - 1;
                localDate = of;
            }
            hashMap2.remove(this);
            hashMap2.remove(aVar);
            hashMap2.remove(gVar);
            return localDate.g0(j);
        }
        throw new DateTimeException("Resolve requires IsoChronology");
    }

    public final String toString() {
        return "DayOfQuarter";
    }
}
