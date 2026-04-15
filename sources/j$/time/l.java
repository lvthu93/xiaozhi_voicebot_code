package j$.time;

import j$.time.chrono.C0037a;
import j$.time.chrono.C0038b;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;

public enum l implements TemporalAccessor, m {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;
    
    private static final l[] a = null;

    static {
        a = values();
    }

    public static l T(int i) {
        if (i >= 1 && i <= 12) {
            return a[i - 1];
        }
        throw new DateTimeException("Invalid value for MonthOfYear: " + i);
    }

    public final long E(q qVar) {
        if (qVar == a.MONTH_OF_YEAR) {
            return (long) getValue();
        }
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final Object H(s sVar) {
        return sVar == p.e() ? j$.time.chrono.t.d : sVar == p.j() ? ChronoUnit.MONTHS : p.c(this, sVar);
    }

    public final int Q(boolean z) {
        switch (k.a[ordinal()]) {
            case 1:
                return 32;
            case 2:
                return (z ? 1 : 0) + true;
            case 3:
                return z + true;
            case 4:
                return z + true;
            case 5:
                return z + true;
            case 6:
                return 1;
            case 7:
                return z + true;
            case 8:
                return z + true;
            case 9:
                return z + true;
            case 10:
                return z + true;
            case 11:
                return z + true;
            default:
                return z + true;
        }
    }

    public final int R(boolean z) {
        int i = k.a[ordinal()];
        return i != 1 ? (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31 : z ? 29 : 28;
    }

    public final int S() {
        int i = k.a[ordinal()];
        if (i != 1) {
            return (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31;
        }
        return 29;
    }

    public final l U() {
        int i = ((int) 1) + 12;
        return a[(i + ordinal()) % 12];
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar == a.MONTH_OF_YEAR : qVar != null && qVar.k(this);
    }

    public final int getValue() {
        return ordinal() + 1;
    }

    public final int k(q qVar) {
        return qVar == a.MONTH_OF_YEAR ? getValue() : p.a(this, qVar);
    }

    public final u m(q qVar) {
        return qVar == a.MONTH_OF_YEAR ? qVar.m() : p.d(this, qVar);
    }

    public final j$.time.temporal.l p(j$.time.temporal.l lVar) {
        if (((C0037a) C0038b.s(lVar)).equals(j$.time.chrono.t.d)) {
            return lVar.c((long) getValue(), a.MONTH_OF_YEAR);
        }
        throw new DateTimeException("Adjustment only supported on ISO date-time");
    }
}
