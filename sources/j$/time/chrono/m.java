package j$.time.chrono;

import j$.time.Instant;
import j$.time.LocalDateTime;
import j$.time.ZoneId;
import j$.time.format.E;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.u;
import java.util.HashMap;
import java.util.List;

public interface m extends Comparable {
    C0039c B(TemporalAccessor temporalAccessor);

    C0042f C(LocalDateTime localDateTime);

    C0039c K(int i, int i2, int i3);

    ChronoZonedDateTime L(Instant instant, ZoneId zoneId);

    boolean O(long j);

    C0039c i(long j);

    String j();

    String n();

    C0039c o(int i, int i2);

    u s(a aVar);

    List t();

    n u(int i);

    C0039c v(HashMap hashMap, E e);

    int w(n nVar, int i);
}
