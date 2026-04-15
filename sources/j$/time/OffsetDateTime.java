package j$.time;

import j$.time.chrono.C0038b;
import j$.time.chrono.t;
import j$.time.format.DateTimeFormatter;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;

public final class OffsetDateTime implements l, m, Comparable<OffsetDateTime>, Serializable {
    private static final long serialVersionUID = 2287754244819255394L;
    private final LocalDateTime a;
    private final ZoneOffset b;

    static {
        LocalDateTime localDateTime = LocalDateTime.c;
        ZoneOffset zoneOffset = ZoneOffset.g;
        localDateTime.getClass();
        R(localDateTime, zoneOffset);
        LocalDateTime localDateTime2 = LocalDateTime.d;
        ZoneOffset zoneOffset2 = ZoneOffset.f;
        localDateTime2.getClass();
        R(localDateTime2, zoneOffset2);
    }

    private OffsetDateTime(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        this.a = (LocalDateTime) Objects.requireNonNull(localDateTime, "dateTime");
        this.b = (ZoneOffset) Objects.requireNonNull(zoneOffset, "offset");
    }

    public static OffsetDateTime Q(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof OffsetDateTime) {
            return (OffsetDateTime) temporalAccessor;
        }
        try {
            ZoneOffset Z = ZoneOffset.Z(temporalAccessor);
            LocalDate localDate = (LocalDate) temporalAccessor.H(p.f());
            j jVar = (j) temporalAccessor.H(p.g());
            return (localDate == null || jVar == null) ? S(Instant.R(temporalAccessor), Z) : new OffsetDateTime(LocalDateTime.Z(localDate, jVar), Z);
        } catch (DateTimeException e) {
            String name = temporalAccessor.getClass().getName();
            throw new DateTimeException("Unable to obtain OffsetDateTime from TemporalAccessor: " + temporalAccessor + " of type " + name, e);
        }
    }

    public static OffsetDateTime R(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return new OffsetDateTime(localDateTime, zoneOffset);
    }

    public static OffsetDateTime S(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        ZoneOffset d = zoneId.R().d(instant);
        return new OffsetDateTime(LocalDateTime.a0(instant.S(), instant.T(), d), d);
    }

    static OffsetDateTime U(ObjectInput objectInput) {
        LocalDateTime localDateTime = LocalDateTime.c;
        LocalDate localDate = LocalDate.d;
        return new OffsetDateTime(LocalDateTime.Z(LocalDate.of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), j.g0(objectInput)), ZoneOffset.f0(objectInput));
    }

    private OffsetDateTime W(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return (this.a != localDateTime || !this.b.equals(zoneOffset)) ? new OffsetDateTime(localDateTime, zoneOffset) : this;
    }

    public static OffsetDateTime parse(CharSequence charSequence) {
        return parse(charSequence, DateTimeFormatter.i);
    }

    public static OffsetDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (OffsetDateTime) dateTimeFormatter.e(charSequence, new e(3));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 10, this);
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = o.a[((a) qVar).ordinal()];
        ZoneOffset zoneOffset = this.b;
        LocalDateTime localDateTime = this.a;
        if (i != 1) {
            return i != 2 ? localDateTime.E(qVar) : (long) zoneOffset.a0();
        }
        localDateTime.getClass();
        return C0038b.p(localDateTime, zoneOffset);
    }

    public final Object H(s sVar) {
        if (sVar == p.i() || sVar == p.k()) {
            return this.b;
        }
        if (sVar == p.l()) {
            return null;
        }
        s f = p.f();
        LocalDateTime localDateTime = this.a;
        return sVar == f ? localDateTime.g0() : sVar == p.g() ? localDateTime.b() : sVar == p.e() ? t.d : sVar == p.j() ? ChronoUnit.NANOS : sVar.a(this);
    }

    /* renamed from: T */
    public final OffsetDateTime d(long j, TemporalUnit temporalUnit) {
        return temporalUnit instanceof ChronoUnit ? W(this.a.d(j, temporalUnit), this.b) : (OffsetDateTime) temporalUnit.k(this, j);
    }

    public final LocalDateTime V() {
        return this.a;
    }

    public final l c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (OffsetDateTime) qVar.H(this, j);
        }
        a aVar = (a) qVar;
        int i = o.a[aVar.ordinal()];
        ZoneOffset zoneOffset = this.b;
        LocalDateTime localDateTime = this.a;
        return i != 1 ? i != 2 ? W(localDateTime.c(j, qVar), zoneOffset) : W(localDateTime, ZoneOffset.d0(aVar.Q(j))) : S(Instant.W(j, (long) localDateTime.S()), zoneOffset);
    }

    public final int compareTo(Object obj) {
        int i;
        OffsetDateTime offsetDateTime = (OffsetDateTime) obj;
        ZoneOffset zoneOffset = offsetDateTime.b;
        ZoneOffset zoneOffset2 = this.b;
        boolean equals = zoneOffset2.equals(zoneOffset);
        LocalDateTime localDateTime = offsetDateTime.a;
        LocalDateTime localDateTime2 = this.a;
        if (equals) {
            i = localDateTime2.compareTo(localDateTime);
        } else {
            localDateTime2.getClass();
            long p = C0038b.p(localDateTime2, zoneOffset2);
            localDateTime.getClass();
            i = Long.compare(p, C0038b.p(localDateTime, offsetDateTime.b));
            if (i == 0) {
                i = localDateTime2.b().V() - localDateTime.b().V();
            }
        }
        return i == 0 ? localDateTime2.compareTo(localDateTime) : i;
    }

    public final boolean e(q qVar) {
        return (qVar instanceof a) || (qVar != null && qVar.k(this));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OffsetDateTime)) {
            return false;
        }
        OffsetDateTime offsetDateTime = (OffsetDateTime) obj;
        return this.a.equals(offsetDateTime.a) && this.b.equals(offsetDateTime.b);
    }

    public final l g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public final int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    public final int k(q qVar) {
        if (!(qVar instanceof a)) {
            return p.a(this, qVar);
        }
        int i = o.a[((a) qVar).ordinal()];
        if (i != 1) {
            return i != 2 ? this.a.k(qVar) : this.b.a0();
        }
        throw new j$.time.temporal.t("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    public final l l(LocalDate localDate) {
        return W(this.a.l(localDate), this.b);
    }

    public final u m(q qVar) {
        return qVar instanceof a ? (qVar == a.INSTANT_SECONDS || qVar == a.OFFSET_SECONDS) ? qVar.m() : this.a.m(qVar) : qVar.l(this);
    }

    public final l p(l lVar) {
        a aVar = a.EPOCH_DAY;
        LocalDateTime localDateTime = this.a;
        return lVar.c(localDateTime.g0().F(), aVar).c(localDateTime.b().h0(), a.NANO_OF_DAY).c((long) this.b.a0(), a.OFFSET_SECONDS);
    }

    public Instant toInstant() {
        LocalDateTime localDateTime = this.a;
        localDateTime.getClass();
        return Instant.W(localDateTime.f0(this.b), (long) localDateTime.b().V());
    }

    public final String toString() {
        String localDateTime = this.a.toString();
        String zoneOffset = this.b.toString();
        return localDateTime + zoneOffset;
    }

    /* access modifiers changed from: package-private */
    public final void writeExternal(ObjectOutput objectOutput) {
        this.a.k0(objectOutput);
        this.b.g0(objectOutput);
    }
}
