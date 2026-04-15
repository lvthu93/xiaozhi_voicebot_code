package j$.time.chrono;

import j$.lang.a;
import j$.time.DateTimeException;
import j$.time.LocalDateTime;
import j$.time.d;
import j$.time.format.E;
import j$.time.j;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.n;
import j$.time.temporal.o;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* renamed from: j$.time.chrono.a  reason: case insensitive filesystem */
public abstract class C0037a implements m {
    private static final ConcurrentHashMap a = new ConcurrentHashMap();
    private static final ConcurrentHashMap b = new ConcurrentHashMap();
    public static final /* synthetic */ int c = 0;

    static {
        new Locale("ja", "JP", "JP");
    }

    protected C0037a() {
    }

    static m E(C0037a aVar, String str) {
        String n;
        m mVar = (m) a.putIfAbsent(str, aVar);
        if (mVar == null && (n = aVar.n()) != null) {
            b.putIfAbsent(n, aVar);
        }
        return mVar;
    }

    static C0039c H(C0039c cVar, long j, long j2, long j3) {
        long j4;
        C0039c d = cVar.d(j, ChronoUnit.MONTHS);
        ChronoUnit chronoUnit = ChronoUnit.WEEKS;
        C0039c d2 = d.d(j2, chronoUnit);
        if (j3 > 7) {
            j4 = j3 - 1;
            d2 = d2.d(j4 / 7, chronoUnit);
        } else {
            if (j3 < 1) {
                d2 = d2.d(a.n(j3, 7) / 7, chronoUnit);
                j4 = j3 + 6;
            }
            return d2.z(new n(d.Q((int) j3).getValue(), 0));
        }
        j3 = (j4 % 7) + 1;
        return d2.z(new n(d.Q((int) j3).getValue(), 0));
    }

    static void k(HashMap hashMap, j$.time.temporal.a aVar, long j) {
        Long l = (Long) hashMap.get(aVar);
        if (l == null || l.longValue() == j) {
            hashMap.put(aVar, Long.valueOf(j));
            return;
        }
        throw new DateTimeException("Conflict found: " + aVar + " " + l + " differs from " + aVar + " " + j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x008d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static j$.time.chrono.m p(java.lang.String r4) {
        /*
            java.lang.String r0 = "id"
            j$.util.Objects.requireNonNull(r4, r0)
        L_0x0005:
            j$.util.concurrent.ConcurrentHashMap r0 = a
            java.lang.Object r1 = r0.get(r4)
            j$.time.chrono.m r1 = (j$.time.chrono.m) r1
            if (r1 != 0) goto L_0x0017
            j$.util.concurrent.ConcurrentHashMap r1 = b
            java.lang.Object r1 = r1.get(r4)
            j$.time.chrono.m r1 = (j$.time.chrono.m) r1
        L_0x0017:
            if (r1 == 0) goto L_0x001a
            return r1
        L_0x001a:
            java.lang.String r1 = "ISO"
            java.lang.Object r0 = r0.get(r1)
            if (r0 != 0) goto L_0x007a
            j$.time.chrono.p r0 = j$.time.chrono.p.o
            java.lang.String r2 = r0.j()
            E(r0, r2)
            j$.time.chrono.w r0 = j$.time.chrono.w.d
            java.lang.String r2 = r0.j()
            E(r0, r2)
            j$.time.chrono.B r0 = j$.time.chrono.B.d
            java.lang.String r2 = r0.j()
            E(r0, r2)
            j$.time.chrono.H r0 = j$.time.chrono.H.d
            java.lang.String r2 = r0.j()
            E(r0, r2)
            java.lang.Class<j$.time.chrono.a> r0 = j$.time.chrono.C0037a.class
            r2 = 0
            java.util.ServiceLoader r0 = java.util.ServiceLoader.load(r0, r2)
            java.util.Iterator r0 = r0.iterator()
        L_0x0051:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x006f
            java.lang.Object r2 = r0.next()
            j$.time.chrono.a r2 = (j$.time.chrono.C0037a) r2
            java.lang.String r3 = r2.j()
            boolean r3 = r3.equals(r1)
            if (r3 != 0) goto L_0x0051
            java.lang.String r3 = r2.j()
            E(r2, r3)
            goto L_0x0051
        L_0x006f:
            j$.time.chrono.t r0 = j$.time.chrono.t.d
            java.lang.String r1 = r0.j()
            E(r0, r1)
            r0 = 1
            goto L_0x007b
        L_0x007a:
            r0 = 0
        L_0x007b:
            if (r0 != 0) goto L_0x0005
            java.lang.Class<j$.time.chrono.m> r0 = j$.time.chrono.m.class
            java.util.ServiceLoader r0 = java.util.ServiceLoader.load(r0)
            java.util.Iterator r0 = r0.iterator()
        L_0x0087:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x00a8
            java.lang.Object r1 = r0.next()
            j$.time.chrono.m r1 = (j$.time.chrono.m) r1
            java.lang.String r2 = r1.j()
            boolean r2 = r4.equals(r2)
            if (r2 != 0) goto L_0x00a7
            java.lang.String r2 = r1.n()
            boolean r2 = r4.equals(r2)
            if (r2 == 0) goto L_0x0087
        L_0x00a7:
            return r1
        L_0x00a8:
            j$.time.DateTimeException r0 = new j$.time.DateTimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Unknown chronology: "
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.C0037a.p(java.lang.String):j$.time.chrono.m");
    }

    public C0042f C(LocalDateTime localDateTime) {
        try {
            return B(localDateTime).G(j.S(localDateTime));
        } catch (DateTimeException e) {
            throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + LocalDateTime.class, e);
        }
    }

    /* access modifiers changed from: package-private */
    public void Q(HashMap hashMap, E e) {
        j$.time.temporal.a aVar = j$.time.temporal.a.PROLEPTIC_MONTH;
        Long l = (Long) hashMap.remove(aVar);
        if (l != null) {
            if (e != E.LENIENT) {
                aVar.R(l.longValue());
            }
            C0039c c2 = m().c(1, j$.time.temporal.a.DAY_OF_MONTH).c(l.longValue(), aVar);
            j$.time.temporal.a aVar2 = j$.time.temporal.a.MONTH_OF_YEAR;
            k(hashMap, aVar2, (long) c2.k(aVar2));
            j$.time.temporal.a aVar3 = j$.time.temporal.a.YEAR;
            k(hashMap, aVar3, (long) c2.k(aVar3));
        }
    }

    /* access modifiers changed from: package-private */
    public C0039c R(HashMap hashMap, E e) {
        j$.time.temporal.a aVar = j$.time.temporal.a.YEAR;
        int a2 = s(aVar).a(((Long) hashMap.remove(aVar)).longValue(), aVar);
        if (e == E.LENIENT) {
            long n = a.n(((Long) hashMap.remove(j$.time.temporal.a.MONTH_OF_YEAR)).longValue(), 1);
            return K(a2, 1, 1).d(n, ChronoUnit.MONTHS).d(a.n(((Long) hashMap.remove(j$.time.temporal.a.DAY_OF_MONTH)).longValue(), 1), ChronoUnit.DAYS);
        }
        j$.time.temporal.a aVar2 = j$.time.temporal.a.MONTH_OF_YEAR;
        int a3 = s(aVar2).a(((Long) hashMap.remove(aVar2)).longValue(), aVar2);
        j$.time.temporal.a aVar3 = j$.time.temporal.a.DAY_OF_MONTH;
        int a4 = s(aVar3).a(((Long) hashMap.remove(aVar3)).longValue(), aVar3);
        if (e != E.SMART) {
            return K(a2, a3, a4);
        }
        try {
            return K(a2, a3, a4);
        } catch (DateTimeException unused) {
            return K(a2, a3, 1).z(new o());
        }
    }

    /* access modifiers changed from: package-private */
    public C0039c S(HashMap hashMap, E e) {
        long j;
        n nVar;
        j$.time.temporal.a aVar = j$.time.temporal.a.YEAR_OF_ERA;
        Long l = (Long) hashMap.remove(aVar);
        if (l != null) {
            j$.time.temporal.a aVar2 = j$.time.temporal.a.ERA;
            Long l2 = (Long) hashMap.remove(aVar2);
            int a2 = e != E.LENIENT ? s(aVar).a(l.longValue(), aVar) : a.h(l.longValue());
            if (l2 != null) {
                k(hashMap, j$.time.temporal.a.YEAR, (long) w(u(s(aVar2).a(l2.longValue(), aVar2)), a2));
                return null;
            }
            j$.time.temporal.a aVar3 = j$.time.temporal.a.YEAR;
            if (hashMap.containsKey(aVar3)) {
                nVar = o(s(aVar3).a(((Long) hashMap.get(aVar3)).longValue(), aVar3), 1).I();
            } else if (e == E.STRICT) {
                hashMap.put(aVar, l);
                return null;
            } else {
                List t = t();
                if (t.isEmpty()) {
                    j = (long) a2;
                    k(hashMap, aVar3, j);
                    return null;
                }
                nVar = (n) t.get(t.size() - 1);
            }
            j = (long) w(nVar, a2);
            k(hashMap, aVar3, j);
            return null;
        }
        j$.time.temporal.a aVar4 = j$.time.temporal.a.ERA;
        if (!hashMap.containsKey(aVar4)) {
            return null;
        }
        s(aVar4).b(((Long) hashMap.get(aVar4)).longValue(), aVar4);
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C0037a) && compareTo((C0037a) obj) == 0;
    }

    public final int hashCode() {
        return getClass().hashCode() ^ j().hashCode();
    }

    /* renamed from: l */
    public final int compareTo(m mVar) {
        return j().compareTo(mVar.j());
    }

    public abstract /* synthetic */ C0039c m();

    public final String toString() {
        return j();
    }

    public C0039c v(HashMap hashMap, E e) {
        HashMap hashMap2 = hashMap;
        E e2 = e;
        j$.time.temporal.a aVar = j$.time.temporal.a.EPOCH_DAY;
        if (hashMap2.containsKey(aVar)) {
            return i(((Long) hashMap2.remove(aVar)).longValue());
        }
        Q(hashMap, e);
        C0039c S = S(hashMap, e);
        if (S != null) {
            return S;
        }
        j$.time.temporal.a aVar2 = j$.time.temporal.a.YEAR;
        if (!hashMap2.containsKey(aVar2)) {
            return null;
        }
        j$.time.temporal.a aVar3 = j$.time.temporal.a.MONTH_OF_YEAR;
        if (hashMap2.containsKey(aVar3)) {
            if (hashMap2.containsKey(j$.time.temporal.a.DAY_OF_MONTH)) {
                return R(hashMap, e);
            }
            j$.time.temporal.a aVar4 = j$.time.temporal.a.ALIGNED_WEEK_OF_MONTH;
            if (hashMap2.containsKey(aVar4)) {
                j$.time.temporal.a aVar5 = j$.time.temporal.a.ALIGNED_DAY_OF_WEEK_IN_MONTH;
                if (hashMap2.containsKey(aVar5)) {
                    int a2 = s(aVar2).a(((Long) hashMap2.remove(aVar2)).longValue(), aVar2);
                    if (e2 == E.LENIENT) {
                        long n = a.n(((Long) hashMap2.remove(aVar3)).longValue(), 1);
                        long n2 = a.n(((Long) hashMap2.remove(aVar4)).longValue(), 1);
                        return K(a2, 1, 1).d(n, ChronoUnit.MONTHS).d(n2, ChronoUnit.WEEKS).d(a.n(((Long) hashMap2.remove(aVar5)).longValue(), 1), ChronoUnit.DAYS);
                    }
                    int a3 = s(aVar3).a(((Long) hashMap2.remove(aVar3)).longValue(), aVar3);
                    int a4 = s(aVar4).a(((Long) hashMap2.remove(aVar4)).longValue(), aVar4);
                    C0039c d = K(a2, a3, 1).d((long) ((s(aVar5).a(((Long) hashMap2.remove(aVar5)).longValue(), aVar5) - 1) + ((a4 - 1) * 7)), ChronoUnit.DAYS);
                    if (e2 != E.STRICT || d.k(aVar3) == a3) {
                        return d;
                    }
                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
                }
                j$.time.temporal.a aVar6 = j$.time.temporal.a.DAY_OF_WEEK;
                if (hashMap2.containsKey(aVar6)) {
                    int a5 = s(aVar2).a(((Long) hashMap2.remove(aVar2)).longValue(), aVar2);
                    if (e2 == E.LENIENT) {
                        return H(K(a5, 1, 1), a.n(((Long) hashMap2.remove(aVar3)).longValue(), 1), a.n(((Long) hashMap2.remove(aVar4)).longValue(), 1), a.n(((Long) hashMap2.remove(aVar6)).longValue(), 1));
                    }
                    int a6 = s(aVar3).a(((Long) hashMap2.remove(aVar3)).longValue(), aVar3);
                    C0039c z = K(a5, a6, 1).d((long) ((s(aVar4).a(((Long) hashMap2.remove(aVar4)).longValue(), aVar4) - 1) * 7), ChronoUnit.DAYS).z(new n(d.Q(s(aVar6).a(((Long) hashMap2.remove(aVar6)).longValue(), aVar6)).getValue(), 0));
                    if (e2 != E.STRICT || z.k(aVar3) == a6) {
                        return z;
                    }
                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
                }
            }
        }
        j$.time.temporal.a aVar7 = j$.time.temporal.a.DAY_OF_YEAR;
        if (hashMap2.containsKey(aVar7)) {
            int a7 = s(aVar2).a(((Long) hashMap2.remove(aVar2)).longValue(), aVar2);
            if (e2 != E.LENIENT) {
                return o(a7, s(aVar7).a(((Long) hashMap2.remove(aVar7)).longValue(), aVar7));
            }
            return o(a7, 1).d(a.n(((Long) hashMap2.remove(aVar7)).longValue(), 1), ChronoUnit.DAYS);
        }
        j$.time.temporal.a aVar8 = j$.time.temporal.a.ALIGNED_WEEK_OF_YEAR;
        if (!hashMap2.containsKey(aVar8)) {
            return null;
        }
        j$.time.temporal.a aVar9 = j$.time.temporal.a.ALIGNED_DAY_OF_WEEK_IN_YEAR;
        if (hashMap2.containsKey(aVar9)) {
            int a8 = s(aVar2).a(((Long) hashMap2.remove(aVar2)).longValue(), aVar2);
            if (e2 == E.LENIENT) {
                long n3 = a.n(((Long) hashMap2.remove(aVar8)).longValue(), 1);
                return o(a8, 1).d(n3, ChronoUnit.WEEKS).d(a.n(((Long) hashMap2.remove(aVar9)).longValue(), 1), ChronoUnit.DAYS);
            }
            int a9 = s(aVar8).a(((Long) hashMap2.remove(aVar8)).longValue(), aVar8);
            C0039c d2 = o(a8, 1).d((long) ((s(aVar9).a(((Long) hashMap2.remove(aVar9)).longValue(), aVar9) - 1) + ((a9 - 1) * 7)), ChronoUnit.DAYS);
            if (e2 != E.STRICT || d2.k(aVar2) == a8) {
                return d2;
            }
            throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
        }
        j$.time.temporal.a aVar10 = j$.time.temporal.a.DAY_OF_WEEK;
        if (!hashMap2.containsKey(aVar10)) {
            return null;
        }
        int a10 = s(aVar2).a(((Long) hashMap2.remove(aVar2)).longValue(), aVar2);
        if (e2 == E.LENIENT) {
            return H(o(a10, 1), 0, a.n(((Long) hashMap2.remove(aVar8)).longValue(), 1), a.n(((Long) hashMap2.remove(aVar10)).longValue(), 1));
        }
        C0039c z2 = o(a10, 1).d((long) ((s(aVar8).a(((Long) hashMap2.remove(aVar8)).longValue(), aVar8) - 1) * 7), ChronoUnit.DAYS).z(new n(d.Q(s(aVar10).a(((Long) hashMap2.remove(aVar10)).longValue(), aVar10)).getValue(), 0));
        if (e2 != E.STRICT || z2.k(aVar2) == a10) {
            return z2;
        }
        throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
    }
}
