package j$.time.temporal;

import j$.time.LocalDate;

/* 'enum' modifier removed */
final class f extends g {
    f() {
        super("WEEK_BASED_YEAR", 3);
    }

    public final long E(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return (long) g.V(LocalDate.S(temporalAccessor));
        }
        throw new t("Unsupported field: WeekBasedYear");
    }

    public final l H(l lVar, long j) {
        if (k(lVar)) {
            int a = m().a(j, g.WEEK_BASED_YEAR);
            LocalDate S = LocalDate.S(lVar);
            a aVar = a.DAY_OF_WEEK;
            int k = S.k(aVar);
            int R = g.R(S);
            if (R == 53 && g.W(a) == 52) {
                R = 52;
            }
            LocalDate of = LocalDate.of(a, 1, 4);
            return lVar.l(of.g0((long) (((R - 1) * 7) + (k - of.k(aVar)))));
        }
        throw new t("Unsupported field: WeekBasedYear");
    }

    public final boolean k(TemporalAccessor temporalAccessor) {
        return temporalAccessor.e(a.EPOCH_DAY) && i.a(temporalAccessor);
    }

    public final u l(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return m();
        }
        throw new t("Unsupported field: WeekBasedYear");
    }

    public final u m() {
        return a.YEAR.m();
    }

    public final String toString() {
        return "WeekBasedYear";
    }
}
