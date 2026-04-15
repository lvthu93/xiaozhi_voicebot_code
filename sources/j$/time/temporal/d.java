package j$.time.temporal;

/* 'enum' modifier removed */
final class d extends g {
    d() {
        super("QUARTER_OF_YEAR", 1);
    }

    public final long E(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return (temporalAccessor.E(a.MONTH_OF_YEAR) + 2) / 3;
        }
        throw new t("Unsupported field: QuarterOfYear");
    }

    public final l H(l lVar, long j) {
        long E = E(lVar);
        m().b(j, this);
        a aVar = a.MONTH_OF_YEAR;
        return lVar.c(((j - E) * 3) + lVar.E(aVar), aVar);
    }

    public final boolean k(TemporalAccessor temporalAccessor) {
        return temporalAccessor.e(a.MONTH_OF_YEAR) && i.a(temporalAccessor);
    }

    public final u l(TemporalAccessor temporalAccessor) {
        if (k(temporalAccessor)) {
            return m();
        }
        throw new t("Unsupported field: QuarterOfYear");
    }

    public final u m() {
        return u.j(1, 4);
    }

    public final String toString() {
        return "QuarterOfYear";
    }
}
