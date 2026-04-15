package j$.time.chrono;

import j$.time.Instant;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.chrono.C0039c;
import j$.time.j;
import j$.time.temporal.l;

public interface ChronoZonedDateTime<D extends C0039c> extends l, Comparable<ChronoZonedDateTime<?>> {
    ZoneId D();

    long P();

    m a();

    j b();

    C0039c f();

    ZoneOffset h();

    C0042f q();

    Instant toInstant();

    ChronoZonedDateTime y(ZoneId zoneId);
}
