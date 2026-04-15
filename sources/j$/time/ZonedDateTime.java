package j$.time;

import j$.time.chrono.C0038b;
import j$.time.chrono.C0039c;
import j$.time.chrono.C0042f;
import j$.time.chrono.ChronoZonedDateTime;
import j$.time.chrono.m;
import j$.time.format.DateTimeFormatter;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.time.zone.b;
import j$.time.zone.f;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

public final class ZonedDateTime implements l, ChronoZonedDateTime<LocalDate>, Serializable {
    private static final long serialVersionUID = -6260982410461394882L;
    private final LocalDateTime a;
    private final ZoneOffset b;
    private final ZoneId c;

    private ZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId, ZoneOffset zoneOffset) {
        this.a = localDateTime;
        this.b = zoneOffset;
        this.c = zoneId;
    }

    private static ZonedDateTime Q(long j, int i, ZoneId zoneId) {
        ZoneOffset d = zoneId.R().d(Instant.W(j, (long) i));
        return new ZonedDateTime(LocalDateTime.a0(j, i, d), zoneId, d);
    }

    public static ZonedDateTime R(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof ZonedDateTime) {
            return (ZonedDateTime) temporalAccessor;
        }
        try {
            ZoneId Q = ZoneId.Q(temporalAccessor);
            a aVar = a.INSTANT_SECONDS;
            return temporalAccessor.e(aVar) ? Q(temporalAccessor.E(aVar), temporalAccessor.k(a.NANO_OF_SECOND), Q) : T(LocalDateTime.Z(LocalDate.S(temporalAccessor), j.S(temporalAccessor)), Q, (ZoneOffset) null);
        } catch (DateTimeException e) {
            String name = temporalAccessor.getClass().getName();
            throw new DateTimeException("Unable to obtain ZonedDateTime from TemporalAccessor: " + temporalAccessor + " of type " + name, e);
        }
    }

    public static ZonedDateTime S(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return Q(instant.S(), instant.T(), zoneId);
    }

    public static ZonedDateTime T(LocalDateTime localDateTime, ZoneId zoneId, ZoneOffset zoneOffset) {
        Object requireNonNull;
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new ZonedDateTime(localDateTime, zoneId, (ZoneOffset) zoneId);
        }
        f R = zoneId.R();
        List g = R.g(localDateTime);
        if (g.size() == 1) {
            requireNonNull = g.get(0);
        } else {
            if (g.size() == 0) {
                b f = R.f(localDateTime);
                localDateTime = localDateTime.d0(f.m().l());
                zoneOffset = f.p();
            } else if (zoneOffset == null || !g.contains(zoneOffset)) {
                requireNonNull = Objects.requireNonNull((ZoneOffset) g.get(0), "offset");
            }
            return new ZonedDateTime(localDateTime, zoneId, zoneOffset);
        }
        zoneOffset = (ZoneOffset) requireNonNull;
        return new ZonedDateTime(localDateTime, zoneId, zoneOffset);
    }

    static ZonedDateTime V(ObjectInput objectInput) {
        LocalDateTime localDateTime = LocalDateTime.c;
        LocalDate localDate = LocalDate.d;
        LocalDateTime Z = LocalDateTime.Z(LocalDate.of(objectInput.readInt(), objectInput.readByte(), objectInput.readByte()), j.g0(objectInput));
        ZoneOffset f0 = ZoneOffset.f0(objectInput);
        ZoneId zoneId = (ZoneId) r.a(objectInput);
        Objects.requireNonNull(Z, "localDateTime");
        Objects.requireNonNull(f0, "offset");
        Objects.requireNonNull(zoneId, "zone");
        if (!(zoneId instanceof ZoneOffset) || f0.equals(zoneId)) {
            return new ZonedDateTime(Z, zoneId, f0);
        }
        throw new IllegalArgumentException("ZoneId must match ZoneOffset");
    }

    private ZonedDateTime W(ZoneOffset zoneOffset) {
        if (!zoneOffset.equals(this.b)) {
            ZoneId zoneId = this.c;
            f R = zoneId.R();
            LocalDateTime localDateTime = this.a;
            if (R.g(localDateTime).contains(zoneOffset)) {
                return new ZonedDateTime(localDateTime, zoneId, zoneOffset);
            }
        }
        return this;
    }

    public static ZonedDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (ZonedDateTime) dateTimeFormatter.e(charSequence, new e(4));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 6, this);
    }

    public final ZoneId D() {
        return this.c;
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = x.a[((a) qVar).ordinal()];
        return i != 1 ? i != 2 ? this.a.E(qVar) : (long) this.b.a0() : C0038b.q(this);
    }

    public final Object H(s sVar) {
        return sVar == p.f() ? this.a.g0() : C0038b.n(this, sVar);
    }

    public final /* synthetic */ long P() {
        return C0038b.q(this);
    }

    /* renamed from: U */
    public final ZonedDateTime d(long j, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return (ZonedDateTime) temporalUnit.k(this, j);
        }
        boolean isDateBased = temporalUnit.isDateBased();
        LocalDateTime b0 = this.a.d(j, temporalUnit);
        ZoneId zoneId = this.c;
        ZoneOffset zoneOffset = this.b;
        if (isDateBased) {
            return T(b0, zoneId, zoneOffset);
        }
        Objects.requireNonNull(b0, "localDateTime");
        Objects.requireNonNull(zoneOffset, "offset");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId.R().g(b0).contains(zoneOffset)) {
            return new ZonedDateTime(b0, zoneId, zoneOffset);
        }
        b0.getClass();
        return Q(C0038b.p(b0, zoneOffset), b0.S(), zoneId);
    }

    public final LocalDateTime X() {
        return this.a;
    }

    /* renamed from: Y */
    public final ZonedDateTime l(LocalDate localDate) {
        return T(LocalDateTime.Z(localDate, this.a.b()), this.c, this.b);
    }

    /* access modifiers changed from: package-private */
    public final void Z(DataOutput dataOutput) {
        this.a.k0(dataOutput);
        this.b.g0(dataOutput);
        this.c.X(dataOutput);
    }

    public final m a() {
        return ((LocalDate) f()).a();
    }

    public final j b() {
        return this.a.b();
    }

    public final l c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (ZonedDateTime) qVar.H(this, j);
        }
        a aVar = (a) qVar;
        int i = x.a[aVar.ordinal()];
        ZoneId zoneId = this.c;
        LocalDateTime localDateTime = this.a;
        return i != 1 ? i != 2 ? T(localDateTime.c(j, qVar), zoneId, this.b) : W(ZoneOffset.d0(aVar.Q(j))) : Q(j, localDateTime.S(), zoneId);
    }

    public final boolean e(q qVar) {
        return (qVar instanceof a) || (qVar != null && qVar.k(this));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZonedDateTime)) {
            return false;
        }
        ZonedDateTime zonedDateTime = (ZonedDateTime) obj;
        return this.a.equals(zonedDateTime.a) && this.b.equals(zonedDateTime.b) && this.c.equals(zonedDateTime.c);
    }

    public final C0039c f() {
        return this.a.g0();
    }

    public final l g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public final ZoneOffset h() {
        return this.b;
    }

    public final int hashCode() {
        return (this.a.hashCode() ^ this.b.hashCode()) ^ Integer.rotateLeft(this.c.hashCode(), 3);
    }

    public final int k(q qVar) {
        if (!(qVar instanceof a)) {
            return C0038b.g(this, qVar);
        }
        int i = x.a[((a) qVar).ordinal()];
        if (i != 1) {
            return i != 2 ? this.a.k(qVar) : this.b.a0();
        }
        throw new t("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    public final u m(q qVar) {
        return qVar instanceof a ? (qVar == a.INSTANT_SECONDS || qVar == a.OFFSET_SECONDS) ? qVar.m() : this.a.m(qVar) : qVar.l(this);
    }

    /* renamed from: p */
    public final /* synthetic */ int compareTo(ChronoZonedDateTime chronoZonedDateTime) {
        return C0038b.f(this, chronoZonedDateTime);
    }

    public final C0042f q() {
        return this.a;
    }

    public final /* synthetic */ Instant toInstant() {
        return C0038b.r(this);
    }

    public final String toString() {
        String localDateTime = this.a.toString();
        ZoneOffset zoneOffset = this.b;
        String str = localDateTime + zoneOffset.toString();
        ZoneId zoneId = this.c;
        if (zoneOffset == zoneId) {
            return str;
        }
        return str + "[" + zoneId.toString() + "]";
    }

    public final ChronoZonedDateTime y(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        return this.c.equals(zoneId) ? this : T(this.a, zoneId, this.b);
    }
}
