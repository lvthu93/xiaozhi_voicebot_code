package j$.time.chrono;

import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.LocalDateTime;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.j;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;

final class l implements ChronoZonedDateTime, Serializable {
    private static final long serialVersionUID = -5261813987200935591L;
    private final transient C0044h a;
    private final transient ZoneOffset b;
    private final transient ZoneId c;

    private l(ZoneId zoneId, ZoneOffset zoneOffset, C0044h hVar) {
        this.a = (C0044h) Objects.requireNonNull(hVar, "dateTime");
        this.b = (ZoneOffset) Objects.requireNonNull(zoneOffset, "offset");
        this.c = (ZoneId) Objects.requireNonNull(zoneId, "zone");
    }

    static l Q(m mVar, j$.time.temporal.l lVar) {
        l lVar2 = (l) lVar;
        C0037a aVar = (C0037a) mVar;
        if (aVar.equals(lVar2.a())) {
            return lVar2;
        }
        String j = aVar.j();
        String j2 = lVar2.a().j();
        throw new ClassCastException("Chronology mismatch, required: " + j + ", actual: " + j2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0053, code lost:
        if (r2.contains(r7) != false) goto L_0x0055;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static j$.time.chrono.ChronoZonedDateTime S(j$.time.ZoneId r6, j$.time.ZoneOffset r7, j$.time.chrono.C0044h r8) {
        /*
            java.lang.String r0 = "localDateTime"
            j$.util.Objects.requireNonNull(r8, r0)
            java.lang.String r0 = "zone"
            j$.util.Objects.requireNonNull(r6, r0)
            boolean r0 = r6 instanceof j$.time.ZoneOffset
            if (r0 == 0) goto L_0x0017
            j$.time.chrono.l r7 = new j$.time.chrono.l
            r0 = r6
            j$.time.ZoneOffset r0 = (j$.time.ZoneOffset) r0
            r7.<init>(r6, r0, r8)
            return r7
        L_0x0017:
            j$.time.zone.f r0 = r6.R()
            j$.time.LocalDateTime r1 = j$.time.LocalDateTime.R(r8)
            java.util.List r2 = r0.g(r1)
            int r3 = r2.size()
            r4 = 1
            r5 = 0
            if (r3 != r4) goto L_0x0032
        L_0x002b:
            java.lang.Object r7 = r2.get(r5)
            j$.time.ZoneOffset r7 = (j$.time.ZoneOffset) r7
            goto L_0x0055
        L_0x0032:
            int r3 = r2.size()
            if (r3 != 0) goto L_0x004d
            j$.time.zone.b r7 = r0.f(r1)
            j$.time.Duration r0 = r7.m()
            long r0 = r0.l()
            j$.time.chrono.h r8 = r8.U(r0)
            j$.time.ZoneOffset r7 = r7.p()
            goto L_0x0055
        L_0x004d:
            if (r7 == 0) goto L_0x002b
            boolean r0 = r2.contains(r7)
            if (r0 == 0) goto L_0x002b
        L_0x0055:
            java.lang.String r0 = "offset"
            j$.util.Objects.requireNonNull(r7, r0)
            j$.time.chrono.l r0 = new j$.time.chrono.l
            r0.<init>(r6, r7, r8)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.l.S(j$.time.ZoneId, j$.time.ZoneOffset, j$.time.chrono.h):j$.time.chrono.ChronoZonedDateTime");
    }

    static l T(m mVar, Instant instant, ZoneId zoneId) {
        ZoneOffset d = zoneId.R().d(instant);
        Objects.requireNonNull(d, "offset");
        return new l(zoneId, d, (C0044h) mVar.C(LocalDateTime.a0(instant.S(), instant.T(), d)));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new F((byte) 3, this);
    }

    public final ZoneId D() {
        return this.c;
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = C0046j.a[((a) qVar).ordinal()];
        return i != 1 ? i != 2 ? ((C0044h) q()).E(qVar) : (long) h().a0() : P();
    }

    public final /* synthetic */ Object H(s sVar) {
        return C0038b.n(this, sVar);
    }

    public final /* synthetic */ long P() {
        return C0038b.q(this);
    }

    /* renamed from: R */
    public final ChronoZonedDateTime g(long j, TemporalUnit temporalUnit) {
        return Q(a(), p.b(this, j, (ChronoUnit) temporalUnit));
    }

    /* renamed from: U */
    public final ChronoZonedDateTime d(long j, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return Q(a(), temporalUnit.k(this, j));
        }
        return Q(a(), this.a.d(j, temporalUnit).p(this));
    }

    public final m a() {
        return f().a();
    }

    public final j b() {
        return ((C0044h) q()).b();
    }

    public final j$.time.temporal.l c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return Q(a(), qVar.H(this, j));
        }
        a aVar = (a) qVar;
        int i = C0047k.a[aVar.ordinal()];
        if (i == 1) {
            return d(j - C0038b.q(this), ChronoUnit.SECONDS);
        }
        ZoneId zoneId = this.c;
        C0044h hVar = this.a;
        if (i != 2) {
            return S(zoneId, this.b, hVar.c(j, qVar));
        }
        ZoneOffset d0 = ZoneOffset.d0(aVar.Q(j));
        hVar.getClass();
        return T(a(), Instant.W(hVar.W(d0), (long) hVar.b().V()), zoneId);
    }

    public final boolean e(q qVar) {
        return (qVar instanceof a) || (qVar != null && qVar.k(this));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoZonedDateTime) && C0038b.f(this, (ChronoZonedDateTime) obj) == 0;
    }

    public final C0039c f() {
        return ((C0044h) q()).f();
    }

    public final ZoneOffset h() {
        return this.b;
    }

    public final int hashCode() {
        return (this.a.hashCode() ^ this.b.hashCode()) ^ Integer.rotateLeft(this.c.hashCode(), 3);
    }

    public final /* synthetic */ int k(q qVar) {
        return C0038b.g(this, qVar);
    }

    public final j$.time.temporal.l l(LocalDate localDate) {
        return Q(a(), localDate.p(this));
    }

    public final u m(q qVar) {
        return qVar instanceof a ? (qVar == a.INSTANT_SECONDS || qVar == a.OFFSET_SECONDS) ? qVar.m() : ((C0044h) q()).m(qVar) : qVar.l(this);
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
        String hVar = this.a.toString();
        ZoneOffset zoneOffset = this.b;
        String str = hVar + zoneOffset.toString();
        ZoneId zoneId = this.c;
        if (zoneOffset == zoneId) {
            return str;
        }
        return str + "[" + zoneId.toString() + "]";
    }

    /* access modifiers changed from: package-private */
    public final void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeObject(this.a);
        objectOutput.writeObject(this.b);
        objectOutput.writeObject(this.c);
    }

    public final ChronoZonedDateTime y(ZoneId zoneId) {
        return S(zoneId, this.b, this.a);
    }
}
