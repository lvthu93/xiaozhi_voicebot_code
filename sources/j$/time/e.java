package j$.time;

import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.s;

public final /* synthetic */ class e implements s {
    public final /* synthetic */ int a;

    public /* synthetic */ e(int i) {
        this.a = i;
    }

    public final Object a(TemporalAccessor temporalAccessor) {
        switch (this.a) {
            case 0:
                return Instant.R(temporalAccessor);
            case 1:
                return LocalDate.S(temporalAccessor);
            case 2:
                return LocalDateTime.R(temporalAccessor);
            case 3:
                return OffsetDateTime.Q(temporalAccessor);
            default:
                return ZonedDateTime.R(temporalAccessor);
        }
    }
}
