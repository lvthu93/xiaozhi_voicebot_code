package j$.time.temporal;

import j$.lang.a;
import j$.time.DateTimeException;
import j$.time.chrono.C0038b;
import j$.time.chrono.C0039c;
import j$.time.chrono.m;
import j$.time.format.E;
import java.util.HashMap;

final class v implements q {
    private static final u f = u.j(1, 7);
    private static final u g = u.k(0, 4, 6);
    private static final u h = u.k(0, 52, 54);
    private static final u i = u.l(52, 53);
    private final String a;
    private final w b;
    private final TemporalUnit c;
    private final TemporalUnit d;
    private final u e;

    private v(String str, w wVar, TemporalUnit temporalUnit, TemporalUnit temporalUnit2, u uVar) {
        this.a = str;
        this.b = wVar;
        this.c = temporalUnit;
        this.d = temporalUnit2;
        this.e = uVar;
    }

    private static int a(int i2, int i3) {
        return ((i3 - 1) + (i2 + 7)) / 7;
    }

    private int b(TemporalAccessor temporalAccessor) {
        return p.h(temporalAccessor.k(a.DAY_OF_WEEK) - this.b.e().getValue()) + 1;
    }

    private int c(TemporalAccessor temporalAccessor) {
        int b2 = b(temporalAccessor);
        int k = temporalAccessor.k(a.YEAR);
        a aVar = a.DAY_OF_YEAR;
        int k2 = temporalAccessor.k(aVar);
        int r = r(k2, b2);
        int a2 = a(r, k2);
        if (a2 == 0) {
            return k - 1;
        }
        return a2 >= a(r, this.b.f() + ((int) temporalAccessor.m(aVar).d())) ? k + 1 : k;
    }

    private long d(TemporalAccessor temporalAccessor) {
        int b2 = b(temporalAccessor);
        int k = temporalAccessor.k(a.DAY_OF_MONTH);
        return (long) a(r(k, b2), k);
    }

    private int e(TemporalAccessor temporalAccessor) {
        int b2 = b(temporalAccessor);
        a aVar = a.DAY_OF_YEAR;
        int k = temporalAccessor.k(aVar);
        int r = r(k, b2);
        int a2 = a(r, k);
        if (a2 == 0) {
            return e(C0038b.s(temporalAccessor).B(temporalAccessor).g((long) k, ChronoUnit.DAYS));
        }
        if (a2 <= 50) {
            return a2;
        }
        int a3 = a(r, this.b.f() + ((int) temporalAccessor.m(aVar).d()));
        return a2 >= a3 ? (a2 - a3) + 1 : a2;
    }

    private long f(TemporalAccessor temporalAccessor) {
        int b2 = b(temporalAccessor);
        int k = temporalAccessor.k(a.DAY_OF_YEAR);
        return (long) a(r(k, b2), k);
    }

    static v g(w wVar) {
        return new v("DayOfWeek", wVar, ChronoUnit.DAYS, ChronoUnit.WEEKS, f);
    }

    private C0039c h(m mVar, int i2, int i3, int i4) {
        C0039c K = mVar.K(i2, 1, 1);
        int r = r(1, b(K));
        int i5 = i4 - 1;
        return K.d((long) (((Math.min(i3, a(r, this.b.f() + K.M()) - 1) - 1) * 7) + i5 + (-r)), ChronoUnit.DAYS);
    }

    static v i(w wVar) {
        return new v("WeekBasedYear", wVar, i.d, ChronoUnit.FOREVER, a.YEAR.m());
    }

    static v j(w wVar) {
        return new v("WeekOfMonth", wVar, ChronoUnit.WEEKS, ChronoUnit.MONTHS, g);
    }

    static v n(w wVar) {
        return new v("WeekOfWeekBasedYear", wVar, ChronoUnit.WEEKS, i.d, i);
    }

    private u o(TemporalAccessor temporalAccessor, a aVar) {
        int r = r(temporalAccessor.k(aVar), b(temporalAccessor));
        u m = temporalAccessor.m(aVar);
        return u.j((long) a(r, (int) m.e()), (long) a(r, (int) m.d()));
    }

    private u q(TemporalAccessor temporalAccessor) {
        a aVar = a.DAY_OF_YEAR;
        if (!temporalAccessor.e(aVar)) {
            return h;
        }
        int b2 = b(temporalAccessor);
        int k = temporalAccessor.k(aVar);
        int r = r(k, b2);
        int a2 = a(r, k);
        if (a2 == 0) {
            return q(C0038b.s(temporalAccessor).B(temporalAccessor).g((long) (k + 7), ChronoUnit.DAYS));
        }
        int d2 = (int) temporalAccessor.m(aVar).d();
        int a3 = a(r, this.b.f() + d2);
        return a2 >= a3 ? q(C0038b.s(temporalAccessor).B(temporalAccessor).d((long) ((d2 - k) + 1 + 7), ChronoUnit.DAYS)) : u.j(1, (long) (a3 - 1));
    }

    private int r(int i2, int i3) {
        int h2 = p.h(i2 - i3);
        return h2 + 1 > this.b.f() ? 7 - h2 : -h2;
    }

    public final long E(TemporalAccessor temporalAccessor) {
        int c2;
        ChronoUnit chronoUnit = ChronoUnit.WEEKS;
        TemporalUnit temporalUnit = this.d;
        if (temporalUnit == chronoUnit) {
            c2 = b(temporalAccessor);
        } else if (temporalUnit == ChronoUnit.MONTHS) {
            return d(temporalAccessor);
        } else {
            if (temporalUnit == ChronoUnit.YEARS) {
                return f(temporalAccessor);
            }
            if (temporalUnit == w.h) {
                c2 = e(temporalAccessor);
            } else if (temporalUnit == ChronoUnit.FOREVER) {
                c2 = c(temporalAccessor);
            } else {
                throw new IllegalStateException("unreachable, rangeUnit: " + temporalUnit + ", this: " + this);
            }
        }
        return (long) c2;
    }

    public final l H(l lVar, long j) {
        int a2 = this.e.a(j, this);
        int k = lVar.k(this);
        if (a2 == k) {
            return lVar;
        }
        if (this.d != ChronoUnit.FOREVER) {
            return lVar.d((long) (a2 - k), this.c);
        }
        w wVar = this.b;
        int k2 = lVar.k(wVar.c);
        return h(C0038b.s(lVar), (int) j, lVar.k(wVar.e), k2);
    }

    public final boolean isDateBased() {
        return true;
    }

    public final boolean isTimeBased() {
        return false;
    }

    public final boolean k(TemporalAccessor temporalAccessor) {
        a aVar;
        if (!temporalAccessor.e(a.DAY_OF_WEEK)) {
            return false;
        }
        ChronoUnit chronoUnit = ChronoUnit.WEEKS;
        TemporalUnit temporalUnit = this.d;
        if (temporalUnit == chronoUnit) {
            return true;
        }
        if (temporalUnit == ChronoUnit.MONTHS) {
            aVar = a.DAY_OF_MONTH;
        } else if (temporalUnit == ChronoUnit.YEARS || temporalUnit == w.h) {
            aVar = a.DAY_OF_YEAR;
        } else if (temporalUnit != ChronoUnit.FOREVER) {
            return false;
        } else {
            aVar = a.YEAR;
        }
        return temporalAccessor.e(aVar);
    }

    public final u l(TemporalAccessor temporalAccessor) {
        ChronoUnit chronoUnit = ChronoUnit.WEEKS;
        TemporalUnit temporalUnit = this.d;
        if (temporalUnit == chronoUnit) {
            return this.e;
        }
        if (temporalUnit == ChronoUnit.MONTHS) {
            return o(temporalAccessor, a.DAY_OF_MONTH);
        }
        if (temporalUnit == ChronoUnit.YEARS) {
            return o(temporalAccessor, a.DAY_OF_YEAR);
        }
        if (temporalUnit == w.h) {
            return q(temporalAccessor);
        }
        if (temporalUnit == ChronoUnit.FOREVER) {
            return a.YEAR.m();
        }
        throw new IllegalStateException("unreachable, rangeUnit: " + temporalUnit + ", this: " + this);
    }

    public final u m() {
        return this.e;
    }

    public final TemporalAccessor p(HashMap hashMap, TemporalAccessor temporalAccessor, E e2) {
        C0039c cVar;
        C0039c cVar2;
        a aVar;
        C0039c cVar3;
        HashMap hashMap2 = hashMap;
        E e3 = e2;
        long longValue = ((Long) hashMap2.get(this)).longValue();
        int h2 = a.h(longValue);
        ChronoUnit chronoUnit = ChronoUnit.WEEKS;
        u uVar = this.e;
        w wVar = this.b;
        TemporalUnit temporalUnit = this.d;
        if (temporalUnit == chronoUnit) {
            hashMap2.remove(this);
            hashMap2.put(a.DAY_OF_WEEK, Long.valueOf((long) (p.h((uVar.a(longValue, this) - 1) + (wVar.e().getValue() - 1)) + 1)));
        } else {
            a aVar2 = a.DAY_OF_WEEK;
            if (hashMap2.containsKey(aVar2)) {
                int h3 = p.h(aVar2.Q(((Long) hashMap2.get(aVar2)).longValue()) - wVar.e().getValue()) + 1;
                m s = C0038b.s(temporalAccessor);
                a aVar3 = a.YEAR;
                if (hashMap2.containsKey(aVar3)) {
                    int Q = aVar3.Q(((Long) hashMap2.get(aVar3)).longValue());
                    ChronoUnit chronoUnit2 = ChronoUnit.MONTHS;
                    if (temporalUnit == chronoUnit2) {
                        a aVar4 = a.MONTH_OF_YEAR;
                        if (hashMap2.containsKey(aVar4)) {
                            a aVar5 = aVar4;
                            long longValue2 = ((Long) hashMap2.get(aVar4)).longValue();
                            u uVar2 = uVar;
                            long j = (long) h2;
                            if (e3 == E.LENIENT) {
                                C0039c d2 = s.K(Q, 1, 1).d(a.n(longValue2, 1), chronoUnit2);
                                cVar3 = d2.d(a.l(a.m(a.n(j, d(d2)), (long) 7), (long) (h3 - b(d2))), ChronoUnit.DAYS);
                                aVar = aVar5;
                            } else {
                                aVar = aVar5;
                                C0039c K = s.K(Q, aVar.Q(longValue2), 1);
                                C0039c d3 = K.d((long) ((((int) (((long) uVar2.a(j, this)) - d(K))) * 7) + (h3 - b(K))), ChronoUnit.DAYS);
                                if (e3 != E.STRICT || d3.E(aVar) == longValue2) {
                                    cVar3 = d3;
                                } else {
                                    throw new DateTimeException("Strict mode rejected resolved date as it is in a different month");
                                }
                            }
                            hashMap2.remove(this);
                            hashMap2.remove(aVar3);
                            hashMap2.remove(aVar);
                            hashMap2.remove(aVar2);
                            return cVar3;
                        }
                    }
                    u uVar3 = uVar;
                    if (temporalUnit == ChronoUnit.YEARS) {
                        long j2 = (long) h2;
                        C0039c K2 = s.K(Q, 1, 1);
                        if (e3 == E.LENIENT) {
                            cVar2 = K2.d(a.l(a.m(a.n(j2, f(K2)), (long) 7), (long) (h3 - b(K2))), ChronoUnit.DAYS);
                        } else {
                            C0039c d4 = K2.d((long) ((((int) (((long) uVar3.a(j2, this)) - f(K2))) * 7) + (h3 - b(K2))), ChronoUnit.DAYS);
                            if (e3 != E.STRICT || d4.E(aVar3) == ((long) Q)) {
                                cVar2 = d4;
                            } else {
                                throw new DateTimeException("Strict mode rejected resolved date as it is in a different year");
                            }
                        }
                        hashMap2.remove(this);
                        hashMap2.remove(aVar3);
                        hashMap2.remove(aVar2);
                        return cVar2;
                    }
                } else if ((temporalUnit == w.h || temporalUnit == ChronoUnit.FOREVER) && hashMap2.containsKey(wVar.f) && hashMap2.containsKey(wVar.e)) {
                    int a2 = ((v) wVar.f).e.a(((Long) hashMap2.get(wVar.f)).longValue(), wVar.f);
                    if (e3 == E.LENIENT) {
                        cVar = h(s, a2, 1, h3).d(a.n(((Long) hashMap2.get(wVar.e)).longValue(), 1), chronoUnit);
                    } else {
                        C0039c h4 = h(s, a2, ((v) wVar.e).e.a(((Long) hashMap2.get(wVar.e)).longValue(), wVar.e), h3);
                        if (e3 != E.STRICT || c(h4) == a2) {
                            cVar = h4;
                        } else {
                            throw new DateTimeException("Strict mode rejected resolved date as it is in a different week-based-year");
                        }
                    }
                    hashMap2.remove(this);
                    hashMap2.remove(wVar.f);
                    hashMap2.remove(wVar.e);
                    hashMap2.remove(aVar2);
                    return cVar;
                }
            }
        }
        return null;
    }

    public final String toString() {
        String wVar = this.b.toString();
        return this.a + "[" + wVar + "]";
    }
}
