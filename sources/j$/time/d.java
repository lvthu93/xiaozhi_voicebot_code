package j$.time;

import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;

public enum d implements TemporalAccessor, m {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;
    
    private static final d[] a = null;

    static {
        a = values();
    }

    public static d Q(int i) {
        if (i >= 1 && i <= 7) {
            return a[i - 1];
        }
        throw new DateTimeException("Invalid value for DayOfWeek: " + i);
    }

    public final long E(q qVar) {
        if (qVar == a.DAY_OF_WEEK) {
            return (long) getValue();
        }
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final Object H(s sVar) {
        return sVar == p.j() ? ChronoUnit.DAYS : p.c(this, sVar);
    }

    public final d R(long j) {
        int i = ((int) (j % 7)) + 7;
        return a[(i + ordinal()) % 7];
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar == a.DAY_OF_WEEK : qVar != null && qVar.k(this);
    }

    public final int getValue() {
        return ordinal() + 1;
    }

    public final int k(q qVar) {
        return qVar == a.DAY_OF_WEEK ? getValue() : p.a(this, qVar);
    }

    public final u m(q qVar) {
        return qVar == a.DAY_OF_WEEK ? qVar.m() : p.d(this, qVar);
    }

    public final l p(l lVar) {
        return lVar.c((long) getValue(), a.DAY_OF_WEEK);
    }
}
