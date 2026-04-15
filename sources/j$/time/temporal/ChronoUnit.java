package j$.time.temporal;

import j$.time.Duration;

public enum ChronoUnit implements TemporalUnit {
    NANOS("Nanos", Duration.m(1)),
    MICROS("Micros", Duration.m(1000)),
    MILLIS("Millis", Duration.m(1000000)),
    SECONDS("Seconds", Duration.p(1)),
    MINUTES("Minutes", Duration.p(60)),
    HOURS("Hours", Duration.p(3600)),
    HALF_DAYS("HalfDays", Duration.p(43200)),
    DAYS("Days", Duration.p(86400)),
    WEEKS("Weeks", Duration.p(604800)),
    MONTHS("Months", Duration.p(2629746)),
    YEARS("Years", Duration.p(31556952)),
    DECADES("Decades", Duration.p(315569520)),
    CENTURIES("Centuries", Duration.p(3155695200L)),
    MILLENNIA("Millennia", Duration.p(31556952000L)),
    ERAS("Eras", Duration.p(31556952000000000L)),
    FOREVER("Forever", Duration.E(Long.MAX_VALUE, 999999999));
    
    private final String a;
    private final Duration b;

    private ChronoUnit(String str, Duration duration) {
        this.a = str;
        this.b = duration;
    }

    public final Duration getDuration() {
        return this.b;
    }

    public boolean isDateBased() {
        return compareTo(DAYS) >= 0 && this != FOREVER;
    }

    public final l k(l lVar, long j) {
        return lVar.d(j, this);
    }

    public final String toString() {
        return this.a;
    }
}
