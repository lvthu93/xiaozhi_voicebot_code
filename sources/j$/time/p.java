package j$.time;

import j$.time.chrono.C0038b;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;

public final class p implements l, m, Comparable, Serializable {
    private static final long serialVersionUID = 7264499704384272492L;
    private final j a;
    private final ZoneOffset b;

    static {
        j jVar = j.e;
        ZoneOffset zoneOffset = ZoneOffset.g;
        jVar.getClass();
        Q(jVar, zoneOffset);
        j jVar2 = j.f;
        ZoneOffset zoneOffset2 = ZoneOffset.f;
        jVar2.getClass();
        Q(jVar2, zoneOffset2);
    }

    private p(j jVar, ZoneOffset zoneOffset) {
        this.a = (j) Objects.requireNonNull(jVar, "time");
        this.b = (ZoneOffset) Objects.requireNonNull(zoneOffset, "offset");
    }

    public static p Q(j jVar, ZoneOffset zoneOffset) {
        return new p(jVar, zoneOffset);
    }

    static p S(ObjectInput objectInput) {
        return new p(j.g0(objectInput), ZoneOffset.f0(objectInput));
    }

    private p T(j jVar, ZoneOffset zoneOffset) {
        return (this.a != jVar || !this.b.equals(zoneOffset)) ? new p(jVar, zoneOffset) : this;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 9, this);
    }

    public final long E(q qVar) {
        return qVar instanceof a ? qVar == a.OFFSET_SECONDS ? (long) this.b.a0() : this.a.E(qVar) : qVar.E(this);
    }

    public final Object H(s sVar) {
        if (sVar == j$.time.temporal.p.i() || sVar == j$.time.temporal.p.k()) {
            return this.b;
        }
        boolean z = true;
        boolean z2 = sVar == j$.time.temporal.p.l();
        if (sVar != j$.time.temporal.p.e()) {
            z = false;
        }
        if ((z2 || z) || sVar == j$.time.temporal.p.f()) {
            return null;
        }
        return sVar == j$.time.temporal.p.g() ? this.a : sVar == j$.time.temporal.p.j() ? ChronoUnit.NANOS : sVar.a(this);
    }

    /* renamed from: R */
    public final p d(long j, TemporalUnit temporalUnit) {
        return temporalUnit instanceof ChronoUnit ? T(this.a.d(j, temporalUnit), this.b) : (p) temporalUnit.k(this, j);
    }

    public final l c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (p) qVar.H(this, j);
        }
        a aVar = a.OFFSET_SECONDS;
        j jVar = this.a;
        return qVar == aVar ? T(jVar, ZoneOffset.d0(((a) qVar).Q(j))) : T(jVar.c(j, qVar), this.b);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0011, code lost:
        r11 = java.lang.Long.compare(r2.h0() - (((long) r1.a0()) * 1000000000), r3.h0() - (((long) r11.b.a0()) * 1000000000));
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int compareTo(java.lang.Object r11) {
        /*
            r10 = this;
            j$.time.p r11 = (j$.time.p) r11
            j$.time.ZoneOffset r0 = r11.b
            j$.time.ZoneOffset r1 = r10.b
            boolean r0 = r1.equals(r0)
            j$.time.j r2 = r10.a
            j$.time.j r3 = r11.a
            if (r0 == 0) goto L_0x0011
            goto L_0x0034
        L_0x0011:
            long r4 = r2.h0()
            int r0 = r1.a0()
            long r0 = (long) r0
            r6 = 1000000000(0x3b9aca00, double:4.94065646E-315)
            long r0 = r0 * r6
            long r4 = r4 - r0
            long r0 = r3.h0()
            j$.time.ZoneOffset r11 = r11.b
            int r11 = r11.a0()
            long r8 = (long) r11
            long r8 = r8 * r6
            long r0 = r0 - r8
            int r11 = java.lang.Long.compare(r4, r0)
            if (r11 != 0) goto L_0x0038
        L_0x0034:
            int r11 = r2.compareTo(r3)
        L_0x0038:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.p.compareTo(java.lang.Object):int");
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar.isTimeBased() || qVar == a.OFFSET_SECONDS : qVar != null && qVar.k(this);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof p)) {
            return false;
        }
        p pVar = (p) obj;
        return this.a.equals(pVar.a) && this.b.equals(pVar.b);
    }

    public final l g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public final int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }

    public final int k(q qVar) {
        return j$.time.temporal.p.a(this, qVar);
    }

    public final l l(LocalDate localDate) {
        if (localDate instanceof j) {
            return T((j) localDate, this.b);
        }
        if (localDate instanceof ZoneOffset) {
            return T(this.a, (ZoneOffset) localDate);
        }
        boolean z = localDate instanceof p;
        TemporalAccessor temporalAccessor = localDate;
        if (!z) {
            temporalAccessor = C0038b.a(localDate, this);
        }
        return (p) temporalAccessor;
    }

    public final u m(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.l(this);
        }
        if (qVar == a.OFFSET_SECONDS) {
            return qVar.m();
        }
        j jVar = this.a;
        jVar.getClass();
        return j$.time.temporal.p.d(jVar, qVar);
    }

    public final l p(l lVar) {
        return lVar.c(this.a.h0(), a.NANO_OF_DAY).c((long) this.b.a0(), a.OFFSET_SECONDS);
    }

    public final String toString() {
        String jVar = this.a.toString();
        String zoneOffset = this.b.toString();
        return jVar + zoneOffset;
    }

    /* access modifiers changed from: package-private */
    public final void writeExternal(ObjectOutput objectOutput) {
        this.a.l0(objectOutput);
        this.b.g0(objectOutput);
    }
}
