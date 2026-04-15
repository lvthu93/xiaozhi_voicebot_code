package j$.time.temporal;

import j$.lang.a;
import j$.time.Duration;

enum h implements TemporalUnit {
    WEEK_BASED_YEARS("WeekBasedYears", Duration.p(31556952)),
    QUARTER_YEARS("QuarterYears", Duration.p(7889238));
    
    private final String a;
    private final Duration b;

    private h(String str, Duration duration) {
        this.a = str;
        this.b = duration;
    }

    public final Duration getDuration() {
        return this.b;
    }

    public final boolean isDateBased() {
        return true;
    }

    public final l k(l lVar, long j) {
        int i = b.a[ordinal()];
        if (i == 1) {
            q qVar = i.c;
            return lVar.c(a.l((long) lVar.k(qVar), j), qVar);
        } else if (i == 2) {
            return lVar.d(j / 4, ChronoUnit.YEARS).d((j % 4) * 3, ChronoUnit.MONTHS);
        } else {
            throw new IllegalStateException("Unreachable");
        }
    }

    public final String toString() {
        return this.a;
    }
}
