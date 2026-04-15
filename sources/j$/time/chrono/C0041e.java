package j$.time.chrono;

import j$.time.c;
import j$.time.j;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import java.io.Serializable;

/* renamed from: j$.time.chrono.e  reason: case insensitive filesystem */
abstract class C0041e implements C0039c, l, m, Serializable {
    private static final long serialVersionUID = 6282433883239719096L;

    C0041e() {
    }

    static C0039c Q(m mVar, l lVar) {
        C0039c cVar = (C0039c) lVar;
        C0037a aVar = (C0037a) mVar;
        if (aVar.equals(cVar.a())) {
            return cVar;
        }
        String j = aVar.j();
        String j2 = cVar.a().j();
        throw new ClassCastException("Chronology mismatch, expected: " + j + ", actual: " + j2);
    }

    public long F() {
        return E(a.EPOCH_DAY);
    }

    public C0042f G(j jVar) {
        return C0044h.S(this, jVar);
    }

    public final /* synthetic */ Object H(s sVar) {
        return C0038b.l(this, sVar);
    }

    public n I() {
        return a().u(k(a.ERA));
    }

    public int M() {
        return r() ? 366 : 365;
    }

    /* renamed from: N */
    public final /* synthetic */ int compareTo(C0039c cVar) {
        return C0038b.d(this, cVar);
    }

    /* access modifiers changed from: package-private */
    public abstract C0039c R(long j);

    /* access modifiers changed from: package-private */
    public abstract C0039c S(long j);

    /* access modifiers changed from: package-private */
    public abstract C0039c T(long j);

    public C0039c c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return Q(a(), qVar.H(this, j));
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public C0039c d(long j, TemporalUnit temporalUnit) {
        boolean z = temporalUnit instanceof ChronoUnit;
        if (z) {
            switch (C0040d.a[((ChronoUnit) temporalUnit).ordinal()]) {
                case 1:
                    return R(j);
                case 2:
                    return R(j$.lang.a.m(j, (long) 7));
                case 3:
                    return S(j);
                case 4:
                    return T(j);
                case 5:
                    return T(j$.lang.a.m(j, (long) 10));
                case 6:
                    return T(j$.lang.a.m(j, (long) 100));
                case 7:
                    return T(j$.lang.a.m(j, (long) 1000));
                case 8:
                    a aVar = a.ERA;
                    return c(j$.lang.a.l(E(aVar), j), (q) aVar);
                default:
                    throw new t("Unsupported unit: " + temporalUnit);
            }
        } else if (!z) {
            return Q(a(), temporalUnit.k(this, j));
        } else {
            throw new t("Unsupported unit: " + temporalUnit);
        }
    }

    public /* synthetic */ boolean e(q qVar) {
        return C0038b.j(this, qVar);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0039c) && C0038b.d(this, (C0039c) obj) == 0;
    }

    public C0039c g(long j, ChronoUnit chronoUnit) {
        return Q(a(), p.b(this, j, chronoUnit));
    }

    public int hashCode() {
        long F = F();
        return ((C0037a) a()).hashCode() ^ ((int) (F ^ (F >>> 32)));
    }

    public final /* synthetic */ int k(q qVar) {
        return p.a(this, qVar);
    }

    public /* synthetic */ u m(q qVar) {
        return p.d(this, qVar);
    }

    public final /* synthetic */ l p(l lVar) {
        return C0038b.a(this, lVar);
    }

    public boolean r() {
        return a().O(E(a.YEAR));
    }

    public String toString() {
        long E = E(a.YEAR_OF_ERA);
        long E2 = E(a.MONTH_OF_YEAR);
        long E3 = E(a.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder(30);
        sb.append(((C0037a) a()).j());
        sb.append(" ");
        sb.append(I());
        sb.append(" ");
        sb.append(E);
        String str = "-0";
        sb.append(E2 < 10 ? str : "-");
        sb.append(E2);
        if (E3 >= 10) {
            str = "-";
        }
        sb.append(str);
        sb.append(E3);
        return sb.toString();
    }

    public C0039c x(j$.time.q qVar) {
        return Q(a(), qVar.a(this));
    }

    /* renamed from: z */
    public C0039c l(m mVar) {
        return Q(a(), mVar.p(this));
    }
}
