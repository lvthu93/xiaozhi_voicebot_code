package j$.time.temporal;

import j$.time.LocalDate;

public interface l extends TemporalAccessor {
    l c(long j, q qVar);

    l d(long j, TemporalUnit temporalUnit);

    l g(long j, ChronoUnit chronoUnit);

    l l(LocalDate localDate);
}
