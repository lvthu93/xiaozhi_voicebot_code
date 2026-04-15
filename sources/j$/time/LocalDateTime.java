package j$.time;

import j$.time.chrono.C0038b;
import j$.time.chrono.C0039c;
import j$.time.chrono.C0042f;
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
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public final class LocalDateTime implements l, m, C0042f, Serializable {
    public static final LocalDateTime c = Z(LocalDate.d, j.e);
    public static final LocalDateTime d = Z(LocalDate.e, j.f);
    private static final long serialVersionUID = 6207766400415563566L;
    private final LocalDate a;
    private final j b;

    private LocalDateTime(LocalDate localDate, j jVar) {
        this.a = localDate;
        this.b = jVar;
    }

    private int Q(LocalDateTime localDateTime) {
        int Q = this.a.Q(localDateTime.a);
        return Q == 0 ? this.b.compareTo(localDateTime.b) : Q;
    }

    public static LocalDateTime R(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof LocalDateTime) {
            return (LocalDateTime) temporalAccessor;
        }
        if (temporalAccessor instanceof ZonedDateTime) {
            return ((ZonedDateTime) temporalAccessor).X();
        }
        if (temporalAccessor instanceof OffsetDateTime) {
            return ((OffsetDateTime) temporalAccessor).V();
        }
        try {
            return new LocalDateTime(LocalDate.S(temporalAccessor), j.S(temporalAccessor));
        } catch (DateTimeException e) {
            String name = temporalAccessor.getClass().getName();
            throw new DateTimeException("Unable to obtain LocalDateTime from TemporalAccessor: " + temporalAccessor + " of type " + name, e);
        }
    }

    public static LocalDateTime X(int i) {
        return new LocalDateTime(LocalDate.of(i, 12, 31), j.X(0));
    }

    public static LocalDateTime Y(int i, int i2, int i3, int i4, int i5, int i6) {
        return new LocalDateTime(LocalDate.of(i, i2, i3), j.Y(i4, i5, i6, 0));
    }

    public static LocalDateTime Z(LocalDate localDate, j jVar) {
        Objects.requireNonNull(localDate, "date");
        Objects.requireNonNull(jVar, "time");
        return new LocalDateTime(localDate, jVar);
    }

    public static LocalDateTime a0(long j, int i, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        long j2 = (long) i;
        a.NANO_OF_SECOND.R(j2);
        long a0 = j + ((long) zoneOffset.a0());
        long j3 = (long) 86400;
        return new LocalDateTime(LocalDate.d0(j$.lang.a.i(a0, j3)), j.Z((((long) ((int) j$.lang.a.k(a0, j3))) * 1000000000) + j2));
    }

    private LocalDateTime e0(LocalDate localDate, long j, long j2, long j3, long j4) {
        LocalDate localDate2 = localDate;
        j jVar = this.b;
        if ((j | j2 | j3 | j4) == 0) {
            return j0(localDate2, jVar);
        }
        long j5 = j2 / 1440;
        long j6 = j / 24;
        long j7 = j6 + j5 + (j3 / 86400) + (j4 / 86400000000000L);
        long j8 = (long) 1;
        long j9 = (j2 % 1440) * 60000000000L;
        long j10 = ((j % 24) * 3600000000000L) + j9 + ((j3 % 86400) * 1000000000) + (j4 % 86400000000000L);
        long h0 = jVar.h0();
        long j11 = (j10 * j8) + h0;
        long i = j$.lang.a.i(j11, 86400000000000L) + (j7 * j8);
        long k = j$.lang.a.k(j11, 86400000000000L);
        if (k != h0) {
            jVar = j.Z(k);
        }
        return j0(localDate2.g0(i), jVar);
    }

    private LocalDateTime j0(LocalDate localDate, j jVar) {
        return (this.a == localDate && this.b == jVar) ? this : new LocalDateTime(localDate, jVar);
    }

    public static LocalDateTime now() {
        b c2 = b.c();
        Objects.requireNonNull(c2, "clock");
        Instant V = Instant.V(System.currentTimeMillis());
        return a0(V.S(), V.T(), c2.a().R().d(V));
    }

    public static LocalDateTime ofInstant(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return a0(instant.S(), instant.T(), zoneId.R().d(instant));
    }

    public static LocalDateTime parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (LocalDateTime) dateTimeFormatter.e(charSequence, new e(2));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 5, this);
    }

    public final long E(q qVar) {
        return qVar instanceof a ? ((a) qVar).isTimeBased() ? this.b.E(qVar) : this.a.E(qVar) : qVar.E(this);
    }

    public final Object H(s sVar) {
        return sVar == p.f() ? this.a : C0038b.m(this, sVar);
    }

    /* renamed from: J */
    public final int compareTo(C0042f fVar) {
        return fVar instanceof LocalDateTime ? Q((LocalDateTime) fVar) : C0038b.e(this, fVar);
    }

    public final int S() {
        return this.b.V();
    }

    public final int T() {
        return this.b.W();
    }

    public final int U() {
        return this.a.X();
    }

    public final boolean V(LocalDateTime localDateTime) {
        if (localDateTime instanceof LocalDateTime) {
            return Q(localDateTime) > 0;
        }
        int i = (this.a.F() > localDateTime.a.F() ? 1 : (this.a.F() == localDateTime.a.F() ? 0 : -1));
        if (i <= 0) {
            return i == 0 && this.b.h0() > localDateTime.b.h0();
        }
        return true;
    }

    public final boolean W(LocalDateTime localDateTime) {
        if (localDateTime instanceof LocalDateTime) {
            return Q(localDateTime) < 0;
        }
        int i = (this.a.F() > localDateTime.a.F() ? 1 : (this.a.F() == localDateTime.a.F() ? 0 : -1));
        if (i >= 0) {
            return i == 0 && this.b.h0() < localDateTime.b.h0();
        }
        return true;
    }

    public final j$.time.chrono.m a() {
        return ((LocalDate) f()).a();
    }

    /* renamed from: atZone */
    public ZonedDateTime A(ZoneId zoneId) {
        return ZonedDateTime.T(this, zoneId, (ZoneOffset) null);
    }

    public final j b() {
        return this.b;
    }

    /* renamed from: b0 */
    public final LocalDateTime d(long j, TemporalUnit temporalUnit) {
        long j2 = j;
        TemporalUnit temporalUnit2 = temporalUnit;
        if (!(temporalUnit2 instanceof ChronoUnit)) {
            return (LocalDateTime) temporalUnit2.k(this, j2);
        }
        switch (h.a[((ChronoUnit) temporalUnit2).ordinal()]) {
            case 1:
                return e0(this.a, 0, 0, 0, j);
            case 2:
                LocalDateTime c0 = c0(j2 / 86400000000L);
                return c0.e0(c0.a, 0, 0, 0, (j2 % 86400000000L) * 1000);
            case 3:
                LocalDateTime c02 = c0(j2 / 86400000);
                return c02.e0(c02.a, 0, 0, 0, (j2 % 86400000) * 1000000);
            case 4:
                return d0(j);
            case 5:
                return e0(this.a, 0, j, 0, 0);
            case 6:
                return e0(this.a, j, 0, 0, 0);
            case 7:
                LocalDateTime c03 = c0(j2 / 256);
                return c03.e0(c03.a, (j2 % 256) * 12, 0, 0, 0);
            default:
                return j0(this.a.d(j2, temporalUnit2), this.b);
        }
    }

    public final LocalDateTime c0(long j) {
        return j0(this.a.g0(j), this.b);
    }

    public final LocalDateTime d0(long j) {
        return e0(this.a, 0, 0, j, 0);
    }

    public final boolean e(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar != null && qVar.k(this);
        }
        a aVar = (a) qVar;
        return aVar.isDateBased() || aVar.isTimeBased();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LocalDateTime)) {
            return false;
        }
        LocalDateTime localDateTime = (LocalDateTime) obj;
        return this.a.equals(localDateTime.a) && this.b.equals(localDateTime.b);
    }

    public final C0039c f() {
        return this.a;
    }

    public final /* synthetic */ long f0(ZoneOffset zoneOffset) {
        return C0038b.p(this, zoneOffset);
    }

    public final LocalDate g0() {
        return this.a;
    }

    /* renamed from: h0 */
    public final LocalDateTime c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (LocalDateTime) qVar.H(this, j);
        }
        boolean isTimeBased = ((a) qVar).isTimeBased();
        j jVar = this.b;
        LocalDate localDate = this.a;
        return isTimeBased ? j0(localDate, jVar.c(j, qVar)) : j0(localDate.c(j, qVar), jVar);
    }

    public final int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    /* renamed from: i0 */
    public final LocalDateTime l(LocalDate localDate) {
        return j0(localDate, this.b);
    }

    public final int k(q qVar) {
        return qVar instanceof a ? ((a) qVar).isTimeBased() ? this.b.k(qVar) : this.a.k(qVar) : p.a(this, qVar);
    }

    /* access modifiers changed from: package-private */
    public final void k0(DataOutput dataOutput) {
        this.a.p0(dataOutput);
        this.b.l0(dataOutput);
    }

    public final u m(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.l(this);
        }
        if (!((a) qVar).isTimeBased()) {
            return this.a.m(qVar);
        }
        j jVar = this.b;
        jVar.getClass();
        return p.d(jVar, qVar);
    }

    /* renamed from: minus */
    public LocalDateTime g(long j, TemporalUnit temporalUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, temporalUnit).d(1, temporalUnit) : d(-j, temporalUnit);
    }

    public LocalDateTime minusDays(long j) {
        return j == Long.MIN_VALUE ? c0(Long.MAX_VALUE).c0(1) : c0(-j);
    }

    public LocalDateTime minusYears(long j) {
        j jVar = this.b;
        LocalDate localDate = this.a;
        if (j != Long.MIN_VALUE) {
            return j0(localDate.j0(-j), jVar);
        }
        LocalDateTime j0 = j0(localDate.j0(Long.MAX_VALUE), jVar);
        return j0.j0(j0.a.j0(1), j0.b);
    }

    public final l p(l lVar) {
        return C0038b.b(this, lVar);
    }

    public final String toString() {
        String localDate = this.a.toString();
        String jVar = this.b.toString();
        return localDate + "T" + jVar;
    }

    public LocalDateTime truncatedTo(TemporalUnit temporalUnit) {
        j jVar = this.b;
        jVar.getClass();
        if (temporalUnit != ChronoUnit.NANOS) {
            Duration duration = temporalUnit.getDuration();
            if (duration.l() <= 86400) {
                long H = duration.H();
                if (86400000000000L % H == 0) {
                    jVar = j.Z((jVar.h0() / H) * H);
                } else {
                    throw new t("Unit must divide into a standard day without remainder");
                }
            } else {
                throw new t("Unit is too large to be used for truncation");
            }
        }
        return j0(this.a, jVar);
    }
}
