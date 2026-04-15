package j$.time.chrono;

import j$.lang.a;
import j$.time.LocalDate;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.j;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;

/* renamed from: j$.time.chrono.h  reason: case insensitive filesystem */
final class C0044h implements C0042f, l, m, Serializable {
    private static final long serialVersionUID = 4556003607393004514L;
    private final transient C0039c a;
    private final transient j b;

    private C0044h(C0039c cVar, j jVar) {
        Objects.requireNonNull(cVar, "date");
        Objects.requireNonNull(jVar, "time");
        this.a = cVar;
        this.b = jVar;
    }

    static C0044h Q(m mVar, l lVar) {
        C0044h hVar = (C0044h) lVar;
        C0037a aVar = (C0037a) mVar;
        if (aVar.equals(hVar.a())) {
            return hVar;
        }
        String j = aVar.j();
        String j2 = hVar.a().j();
        throw new ClassCastException("Chronology mismatch, required: " + j + ", actual: " + j2);
    }

    static C0044h S(C0039c cVar, j jVar) {
        return new C0044h(cVar, jVar);
    }

    private C0044h V(C0039c cVar, long j, long j2, long j3, long j4) {
        C0039c cVar2 = cVar;
        j jVar = this.b;
        if ((j | j2 | j3 | j4) == 0) {
            return Y(cVar2, jVar);
        }
        long j5 = j2 / 1440;
        long j6 = j / 24;
        long j7 = (j2 % 1440) * 60000000000L;
        long j8 = ((j % 24) * 3600000000000L) + j7 + ((j3 % 86400) * 1000000000) + (j4 % 86400000000000L);
        long h0 = jVar.h0();
        long j9 = j8 + h0;
        long i = a.i(j9, 86400000000000L) + j6 + j5 + (j3 / 86400) + (j4 / 86400000000000L);
        long k = a.k(j9, 86400000000000L);
        if (k != h0) {
            jVar = j.Z(k);
        }
        return Y(cVar2.d(i, ChronoUnit.DAYS), jVar);
    }

    private C0044h Y(l lVar, j jVar) {
        C0039c cVar = this.a;
        return (cVar == lVar && this.b == jVar) ? this : new C0044h(C0041e.Q(cVar.a(), lVar), jVar);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new F((byte) 2, this);
    }

    public final ChronoZonedDateTime A(ZoneId zoneId) {
        return l.S(zoneId, (ZoneOffset) null, this);
    }

    public final long E(q qVar) {
        return qVar instanceof j$.time.temporal.a ? ((j$.time.temporal.a) qVar).isTimeBased() ? this.b.E(qVar) : this.a.E(qVar) : qVar.E(this);
    }

    public final /* synthetic */ Object H(s sVar) {
        return C0038b.m(this, sVar);
    }

    /* renamed from: J */
    public final /* synthetic */ int compareTo(C0042f fVar) {
        return C0038b.e(this, fVar);
    }

    /* renamed from: R */
    public final C0042f g(long j, TemporalUnit temporalUnit) {
        return Q(a(), p.b(this, j, (ChronoUnit) temporalUnit));
    }

    /* renamed from: T */
    public final C0044h d(long j, TemporalUnit temporalUnit) {
        long j2 = j;
        TemporalUnit temporalUnit2 = temporalUnit;
        boolean z = temporalUnit2 instanceof ChronoUnit;
        C0039c cVar = this.a;
        if (!z) {
            return Q(cVar.a(), temporalUnit2.k(this, j2));
        }
        int i = C0043g.a[((ChronoUnit) temporalUnit2).ordinal()];
        j jVar = this.b;
        switch (i) {
            case 1:
                return V(this.a, 0, 0, 0, j);
            case 2:
                C0044h Y = Y(cVar.d(j2 / 86400000000L, ChronoUnit.DAYS), jVar);
                return Y.V(Y.a, 0, 0, 0, (j2 % 86400000000L) * 1000);
            case 3:
                C0044h Y2 = Y(cVar.d(j2 / 86400000, ChronoUnit.DAYS), jVar);
                return Y2.V(Y2.a, 0, 0, 0, (j2 % 86400000) * 1000000);
            case 4:
                return U(j);
            case 5:
                return V(this.a, 0, j, 0, 0);
            case 6:
                return V(this.a, j, 0, 0, 0);
            case 7:
                C0044h Y3 = Y(cVar.d(j2 / 256, ChronoUnit.DAYS), jVar);
                return Y3.V(Y3.a, (j2 % 256) * 12, 0, 0, 0);
            default:
                return Y(cVar.d(j2, temporalUnit2), jVar);
        }
    }

    /* access modifiers changed from: package-private */
    public final C0044h U(long j) {
        return V(this.a, 0, 0, j, 0);
    }

    public final /* synthetic */ long W(ZoneOffset zoneOffset) {
        return C0038b.p(this, zoneOffset);
    }

    /* renamed from: X */
    public final C0044h c(long j, q qVar) {
        boolean z = qVar instanceof j$.time.temporal.a;
        C0039c cVar = this.a;
        if (!z) {
            return Q(cVar.a(), qVar.H(this, j));
        }
        boolean isTimeBased = ((j$.time.temporal.a) qVar).isTimeBased();
        j jVar = this.b;
        return isTimeBased ? Y(cVar, jVar.c(j, qVar)) : Y(cVar.c(j, qVar), jVar);
    }

    public final m a() {
        return f().a();
    }

    public final j b() {
        return this.b;
    }

    public final boolean e(q qVar) {
        if (!(qVar instanceof j$.time.temporal.a)) {
            return qVar != null && qVar.k(this);
        }
        j$.time.temporal.a aVar = (j$.time.temporal.a) qVar;
        return aVar.isDateBased() || aVar.isTimeBased();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0042f) && C0038b.e(this, (C0042f) obj) == 0;
    }

    public final C0039c f() {
        return this.a;
    }

    public final int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    public final int k(q qVar) {
        return qVar instanceof j$.time.temporal.a ? ((j$.time.temporal.a) qVar).isTimeBased() ? this.b.k(qVar) : this.a.k(qVar) : m(qVar).a(E(qVar), qVar);
    }

    public final l l(LocalDate localDate) {
        return Y(localDate, this.b);
    }

    public final u m(q qVar) {
        if (!(qVar instanceof j$.time.temporal.a)) {
            return qVar.l(this);
        }
        if (!((j$.time.temporal.a) qVar).isTimeBased()) {
            return this.a.m(qVar);
        }
        j jVar = this.b;
        jVar.getClass();
        return p.d(jVar, qVar);
    }

    public final /* synthetic */ l p(l lVar) {
        return C0038b.b(this, lVar);
    }

    public final String toString() {
        String cVar = this.a.toString();
        String jVar = this.b.toString();
        return cVar + "T" + jVar;
    }

    /* access modifiers changed from: package-private */
    public final void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeObject(this.a);
        objectOutput.writeObject(this.b);
    }
}
