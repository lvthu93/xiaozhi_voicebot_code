package j$.time.chrono;

import j$.time.Instant;
import j$.time.ZoneOffset;
import j$.time.c;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.util.Objects;

/* renamed from: j$.time.chrono.b  reason: case insensitive filesystem */
public abstract /* synthetic */ class C0038b {
    public static l a(C0039c cVar, l lVar) {
        return lVar.c(cVar.F(), a.EPOCH_DAY);
    }

    public static l b(C0042f fVar, l lVar) {
        return lVar.c(fVar.f().F(), a.EPOCH_DAY).c(fVar.b().h0(), a.NANO_OF_DAY);
    }

    public static l c(n nVar, l lVar) {
        return lVar.c((long) nVar.getValue(), a.ERA);
    }

    public static int d(C0039c cVar, C0039c cVar2) {
        int compare = Long.compare(cVar.F(), cVar2.F());
        if (compare != 0) {
            return compare;
        }
        return ((C0037a) cVar.a()).compareTo(cVar2.a());
    }

    public static int e(C0042f fVar, C0042f fVar2) {
        int N = fVar.f().N(fVar2.f());
        if (N != 0) {
            return N;
        }
        int Q = fVar.b().compareTo(fVar2.b());
        if (Q != 0) {
            return Q;
        }
        return ((C0037a) fVar.a()).compareTo(fVar2.a());
    }

    public static int f(ChronoZonedDateTime chronoZonedDateTime, ChronoZonedDateTime chronoZonedDateTime2) {
        int compare = Long.compare(chronoZonedDateTime.P(), chronoZonedDateTime2.P());
        if (compare != 0) {
            return compare;
        }
        int V = chronoZonedDateTime.b().V() - chronoZonedDateTime2.b().V();
        if (V != 0) {
            return V;
        }
        int J = chronoZonedDateTime.q().J(chronoZonedDateTime2.q());
        if (J != 0) {
            return J;
        }
        int compareTo = chronoZonedDateTime.D().j().compareTo(chronoZonedDateTime2.D().j());
        if (compareTo != 0) {
            return compareTo;
        }
        return ((C0037a) chronoZonedDateTime.a()).compareTo(chronoZonedDateTime2.a());
    }

    public static int g(ChronoZonedDateTime chronoZonedDateTime, q qVar) {
        if (!(qVar instanceof a)) {
            return p.a(chronoZonedDateTime, qVar);
        }
        int i = C0046j.a[((a) qVar).ordinal()];
        if (i != 1) {
            return i != 2 ? chronoZonedDateTime.q().k(qVar) : chronoZonedDateTime.h().a0();
        }
        throw new t("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
    }

    public static int h(n nVar, a aVar) {
        return aVar == a.ERA ? nVar.getValue() : p.a(nVar, aVar);
    }

    public static long i(n nVar, q qVar) {
        if (qVar == a.ERA) {
            return (long) nVar.getValue();
        }
        if (!(qVar instanceof a)) {
            return qVar.E(nVar);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public static boolean j(C0039c cVar, q qVar) {
        return qVar instanceof a ? qVar.isDateBased() : qVar != null && qVar.k(cVar);
    }

    public static boolean k(n nVar, q qVar) {
        return qVar instanceof a ? qVar == a.ERA : qVar != null && qVar.k(nVar);
    }

    public static Object l(C0039c cVar, s sVar) {
        if (sVar == p.l() || sVar == p.k() || sVar == p.i() || sVar == p.g()) {
            return null;
        }
        return sVar == p.e() ? cVar.a() : sVar == p.j() ? ChronoUnit.DAYS : sVar.a(cVar);
    }

    public static Object m(C0042f fVar, s sVar) {
        if (sVar == p.l() || sVar == p.k() || sVar == p.i()) {
            return null;
        }
        return sVar == p.g() ? fVar.b() : sVar == p.e() ? fVar.a() : sVar == p.j() ? ChronoUnit.NANOS : sVar.a(fVar);
    }

    public static Object n(ChronoZonedDateTime chronoZonedDateTime, s sVar) {
        return (sVar == p.k() || sVar == p.l()) ? chronoZonedDateTime.D() : sVar == p.i() ? chronoZonedDateTime.h() : sVar == p.g() ? chronoZonedDateTime.b() : sVar == p.e() ? chronoZonedDateTime.a() : sVar == p.j() ? ChronoUnit.NANOS : sVar.a(chronoZonedDateTime);
    }

    public static Object o(n nVar, s sVar) {
        return sVar == p.j() ? ChronoUnit.ERAS : p.c(nVar, sVar);
    }

    public static long p(C0042f fVar, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        return ((fVar.f().F() * 86400) + ((long) fVar.b().i0())) - ((long) zoneOffset.a0());
    }

    public static long q(ChronoZonedDateTime chronoZonedDateTime) {
        return ((chronoZonedDateTime.f().F() * 86400) + ((long) chronoZonedDateTime.b().i0())) - ((long) chronoZonedDateTime.h().a0());
    }

    public static Instant r(ChronoZonedDateTime chronoZonedDateTime) {
        return Instant.W(chronoZonedDateTime.P(), (long) chronoZonedDateTime.b().V());
    }

    public static m s(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        return (m) Objects.requireNonNullElse((m) temporalAccessor.H(p.e()), t.d);
    }
}
