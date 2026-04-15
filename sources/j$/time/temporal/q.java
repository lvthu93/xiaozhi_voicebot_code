package j$.time.temporal;

import j$.time.format.E;
import java.util.HashMap;

public interface q {
    long E(TemporalAccessor temporalAccessor);

    l H(l lVar, long j);

    boolean isDateBased();

    boolean isTimeBased();

    boolean k(TemporalAccessor temporalAccessor);

    u l(TemporalAccessor temporalAccessor);

    u m();

    TemporalAccessor p(HashMap hashMap, TemporalAccessor temporalAccessor, E e);
}
