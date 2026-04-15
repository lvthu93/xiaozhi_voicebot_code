package j$.time.temporal;

public enum a implements q {
    NANO_OF_SECOND("NanoOfSecond", r12, r21, u.j(0, 999999999)),
    NANO_OF_DAY("NanoOfDay", r12, r3, u.j(0, 86399999999999L)),
    MICRO_OF_SECOND("MicroOfSecond", r26, r18, u.j(0, 999999)),
    MICRO_OF_DAY("MicroOfDay", r26, r27, u.j(0, 86399999999L)),
    MILLI_OF_SECOND("MilliOfSecond", r26, r18, u.j(0, 999)),
    MILLI_OF_DAY("MilliOfDay", r26, r27, u.j(0, 86399999)),
    SECOND_OF_MINUTE("SecondOfMinute", r17, r12, u.j(0, 59), 0),
    SECOND_OF_DAY("SecondOfDay", r17, r3, u.j(0, 86399)),
    MINUTE_OF_HOUR("MinuteOfHour", r26, r30, u.j(0, 59), 0),
    MINUTE_OF_DAY("MinuteOfDay", r26, r27, u.j(0, 1439)),
    HOUR_OF_AMPM("HourOfAmPm", r17, r18, u.j(0, 11)),
    CLOCK_HOUR_OF_AMPM("ClockHourOfAmPm", r17, r18, u.j(1, 12)),
    HOUR_OF_DAY("HourOfDay", r26, r27, u.j(0, 23), 0),
    CLOCK_HOUR_OF_DAY("ClockHourOfDay", r26, r27, u.j(1, 24)),
    AMPM_OF_DAY("AmPmOfDay", r31, r27, u.j(0, 1), 0),
    DAY_OF_WEEK("DayOfWeek", r26, r27, u.j(1, 7), 0),
    ALIGNED_DAY_OF_WEEK_IN_MONTH("AlignedDayOfWeekInMonth", r26, r27, u.j(1, 7)),
    ALIGNED_DAY_OF_WEEK_IN_YEAR("AlignedDayOfWeekInYear", r26, r27, u.j(1, 7)),
    DAY_OF_MONTH("DayOfMonth", r26, r14, u.l(28, 31), 0),
    DAY_OF_YEAR("DayOfYear", r26, r2, u.l(365, 366)),
    EPOCH_DAY("EpochDay", r26, r6, u.j(-365243219162L, 365241780471L)),
    ALIGNED_WEEK_OF_MONTH("AlignedWeekOfMonth", r40, r14, u.l(4, 5)),
    ALIGNED_WEEK_OF_YEAR("AlignedWeekOfYear", r40, r2, u.j(1, 53)),
    MONTH_OF_YEAR("MonthOfYear", r14, r2, u.j(1, 12), 0),
    PROLEPTIC_MONTH("ProlepticMonth", r14, r41, u.j(-11999999988L, 11999999999L)),
    YEAR_OF_ERA("YearOfEra", r2, r41, u.l(999999999, 1000000000)),
    YEAR("Year", r2, r50, u.j(-999999999, 999999999), 0),
    ERA("Era", ChronoUnit.ERAS, r50, u.j(0, 1), 0),
    INSTANT_SECONDS("InstantSeconds", r17, r18, u.j(Long.MIN_VALUE, Long.MAX_VALUE)),
    OFFSET_SECONDS("OffsetSeconds", r17, r18, u.j(-64800, 64800));
    
    private final String a;
    private final TemporalUnit b;
    private final TemporalUnit c;
    private final u d;

    private a(String str, ChronoUnit chronoUnit, ChronoUnit chronoUnit2, u uVar) {
        this.a = str;
        this.b = chronoUnit;
        this.c = chronoUnit2;
        this.d = uVar;
    }

    private a(String str, ChronoUnit chronoUnit, ChronoUnit chronoUnit2, u uVar, int i) {
        this.a = str;
        this.b = chronoUnit;
        this.c = chronoUnit2;
        this.d = uVar;
    }

    public final long E(TemporalAccessor temporalAccessor) {
        return temporalAccessor.E(this);
    }

    public final l H(l lVar, long j) {
        return lVar.c(j, this);
    }

    public final int Q(long j) {
        return this.d.a(j, this);
    }

    public final void R(long j) {
        this.d.b(j, this);
    }

    public final boolean isDateBased() {
        return ordinal() >= DAY_OF_WEEK.ordinal() && ordinal() <= ERA.ordinal();
    }

    public final boolean isTimeBased() {
        return ordinal() < DAY_OF_WEEK.ordinal();
    }

    public final boolean k(TemporalAccessor temporalAccessor) {
        return temporalAccessor.e(this);
    }

    public final u l(TemporalAccessor temporalAccessor) {
        return temporalAccessor.m(this);
    }

    public final u m() {
        return this.d;
    }

    public final String toString() {
        return this.a;
    }
}
