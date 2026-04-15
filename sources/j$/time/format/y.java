package j$.time.format;

import j$.time.ZoneId;
import j$.time.chrono.C0039c;
import j$.time.chrono.m;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.u;

final class y implements TemporalAccessor {
    final /* synthetic */ C0039c a;
    final /* synthetic */ TemporalAccessor b;
    final /* synthetic */ m c;
    final /* synthetic */ ZoneId d;

    y(C0039c cVar, TemporalAccessor temporalAccessor, m mVar, ZoneId zoneId) {
        this.a = cVar;
        this.b = temporalAccessor;
        this.c = mVar;
        this.d = zoneId;
    }

    public final long E(q qVar) {
        C0039c cVar = this.a;
        return (cVar == null || !qVar.isDateBased()) ? this.b.E(qVar) : cVar.E(qVar);
    }

    public final Object H(s sVar) {
        return sVar == p.e() ? this.c : sVar == p.l() ? this.d : sVar == p.j() ? this.b.H(sVar) : sVar.a(this);
    }

    public final boolean e(q qVar) {
        C0039c cVar = this.a;
        return (cVar == null || !qVar.isDateBased()) ? this.b.e(qVar) : cVar.e(qVar);
    }

    public final /* synthetic */ int k(q qVar) {
        return p.a(this, qVar);
    }

    public final u m(q qVar) {
        C0039c cVar = this.a;
        return (cVar == null || !qVar.isDateBased()) ? this.b.m(qVar) : cVar.m(qVar);
    }

    public final String toString() {
        String str;
        String str2 = "";
        m mVar = this.c;
        if (mVar != null) {
            str = " with chronology " + mVar;
        } else {
            str = str2;
        }
        ZoneId zoneId = this.d;
        if (zoneId != null) {
            str2 = " with zone " + zoneId;
        }
        return this.b + str + str2;
    }
}
