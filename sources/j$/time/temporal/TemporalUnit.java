package j$.time.temporal;

import j$.time.Duration;

public interface TemporalUnit {
    Duration getDuration();

    boolean isDateBased();

    l k(l lVar, long j);
}
