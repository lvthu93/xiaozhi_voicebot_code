package j$.time.temporal;

import j$.time.chrono.C0037a;
import j$.time.chrono.C0038b;
import j$.time.chrono.t;

public abstract class i {
    public static final q a = g.QUARTER_OF_YEAR;
    public static final q b = g.WEEK_OF_WEEK_BASED_YEAR;
    public static final q c = g.WEEK_BASED_YEAR;
    public static final TemporalUnit d = h.WEEK_BASED_YEARS;

    static {
        g gVar = g.DAY_OF_QUARTER;
        h hVar = h.WEEK_BASED_YEARS;
    }

    static boolean a(TemporalAccessor temporalAccessor) {
        return ((C0037a) C0038b.s(temporalAccessor)).equals(t.d);
    }
}
