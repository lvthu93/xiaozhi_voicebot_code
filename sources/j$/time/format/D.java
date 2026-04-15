package j$.time.format;

import j$.time.DateTimeException;
import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.c;
import j$.time.chrono.C0037a;
import j$.time.chrono.C0039c;
import j$.time.chrono.ChronoZonedDateTime;
import j$.time.chrono.m;
import j$.time.j;
import j$.time.q;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.p;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.util.Objects;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class D implements TemporalAccessor {
    final HashMap a = new HashMap();
    ZoneId b;
    m c;
    private E d;
    private C0039c e;
    private j f;
    q g = q.d;

    D() {
    }

    private void i(TemporalAccessor temporalAccessor) {
        Iterator it = this.a.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            j$.time.temporal.q qVar = (j$.time.temporal.q) entry.getKey();
            if (temporalAccessor.e(qVar)) {
                try {
                    long E = temporalAccessor.E(qVar);
                    long longValue = ((Long) entry.getValue()).longValue();
                    if (E == longValue) {
                        it.remove();
                    } else {
                        throw new DateTimeException("Conflict found: Field " + qVar + " " + E + " differs from " + qVar + " " + longValue + " derived from " + temporalAccessor);
                    }
                } catch (RuntimeException unused) {
                    continue;
                }
            }
        }
    }

    private void n() {
        HashMap hashMap = this.a;
        if (hashMap.containsKey(a.INSTANT_SECONDS)) {
            ZoneId zoneId = this.b;
            if (zoneId != null) {
                o(zoneId);
                return;
            }
            Long l = (Long) hashMap.get(a.OFFSET_SECONDS);
            if (l != null) {
                o(ZoneOffset.d0(l.intValue()));
            }
        }
    }

    private void o(ZoneId zoneId) {
        HashMap hashMap = this.a;
        a aVar = a.INSTANT_SECONDS;
        ChronoZonedDateTime L = this.c.L(Instant.ofEpochSecond(((Long) hashMap.remove(aVar)).longValue()), zoneId);
        u(L.f());
        v(aVar, a.SECOND_OF_DAY, Long.valueOf((long) L.b().i0()));
    }

    private void p(long j, long j2, long j3, long j4) {
        j Y;
        q qVar;
        if (this.d == E.LENIENT) {
            long l = j$.lang.a.l(j$.lang.a.l(j$.lang.a.l(j$.lang.a.m(j, 3600000000000L), j$.lang.a.m(j2, 60000000000L)), j$.lang.a.m(j3, 1000000000)), j4);
            Y = j.Z(j$.lang.a.k(l, 86400000000000L));
            qVar = q.c((int) j$.lang.a.i(l, 86400000000000L));
        } else {
            int Q = a.MINUTE_OF_HOUR.Q(j2);
            int Q2 = a.NANO_OF_SECOND.Q(j4);
            if (this.d == E.SMART && j == 24 && Q == 0 && j3 == 0 && Q2 == 0) {
                Y = j.g;
                qVar = q.c(1);
            } else {
                Y = j.Y(a.HOUR_OF_DAY.Q(j), Q, a.SECOND_OF_MINUTE.Q(j3), Q2);
                qVar = q.d;
            }
        }
        t(Y, qVar);
    }

    private void s() {
        long j;
        a aVar;
        HashMap hashMap = this.a;
        a aVar2 = a.CLOCK_HOUR_OF_DAY;
        long j2 = 0;
        if (hashMap.containsKey(aVar2)) {
            long longValue = ((Long) hashMap.remove(aVar2)).longValue();
            E e2 = this.d;
            if (e2 == E.STRICT || (e2 == E.SMART && longValue != 0)) {
                aVar2.R(longValue);
            }
            a aVar3 = a.HOUR_OF_DAY;
            if (longValue == 24) {
                longValue = 0;
            }
            v(aVar2, aVar3, Long.valueOf(longValue));
        }
        a aVar4 = a.CLOCK_HOUR_OF_AMPM;
        if (hashMap.containsKey(aVar4)) {
            long longValue2 = ((Long) hashMap.remove(aVar4)).longValue();
            E e3 = this.d;
            if (e3 == E.STRICT || (e3 == E.SMART && longValue2 != 0)) {
                aVar4.R(longValue2);
            }
            a aVar5 = a.HOUR_OF_AMPM;
            if (longValue2 != 12) {
                j2 = longValue2;
            }
            v(aVar4, aVar5, Long.valueOf(j2));
        }
        a aVar6 = a.AMPM_OF_DAY;
        if (hashMap.containsKey(aVar6)) {
            a aVar7 = a.HOUR_OF_AMPM;
            if (hashMap.containsKey(aVar7)) {
                long longValue3 = ((Long) hashMap.remove(aVar6)).longValue();
                long longValue4 = ((Long) hashMap.remove(aVar7)).longValue();
                if (this.d == E.LENIENT) {
                    aVar = a.HOUR_OF_DAY;
                    j = j$.lang.a.l(j$.lang.a.m(longValue3, (long) 12), longValue4);
                } else {
                    aVar6.R(longValue3);
                    aVar7.R(longValue3);
                    aVar = a.HOUR_OF_DAY;
                    j = (longValue3 * 12) + longValue4;
                }
                v(aVar6, aVar, Long.valueOf(j));
            }
        }
        a aVar8 = a.NANO_OF_DAY;
        if (hashMap.containsKey(aVar8)) {
            long longValue5 = ((Long) hashMap.remove(aVar8)).longValue();
            if (this.d != E.LENIENT) {
                aVar8.R(longValue5);
            }
            v(aVar8, a.HOUR_OF_DAY, Long.valueOf(longValue5 / 3600000000000L));
            v(aVar8, a.MINUTE_OF_HOUR, Long.valueOf((longValue5 / 60000000000L) % 60));
            v(aVar8, a.SECOND_OF_MINUTE, Long.valueOf((longValue5 / 1000000000) % 60));
            v(aVar8, a.NANO_OF_SECOND, Long.valueOf(longValue5 % 1000000000));
        }
        a aVar9 = a.MICRO_OF_DAY;
        if (hashMap.containsKey(aVar9)) {
            long longValue6 = ((Long) hashMap.remove(aVar9)).longValue();
            if (this.d != E.LENIENT) {
                aVar9.R(longValue6);
            }
            v(aVar9, a.SECOND_OF_DAY, Long.valueOf(longValue6 / 1000000));
            v(aVar9, a.MICRO_OF_SECOND, Long.valueOf(longValue6 % 1000000));
        }
        a aVar10 = a.MILLI_OF_DAY;
        if (hashMap.containsKey(aVar10)) {
            long longValue7 = ((Long) hashMap.remove(aVar10)).longValue();
            if (this.d != E.LENIENT) {
                aVar10.R(longValue7);
            }
            v(aVar10, a.SECOND_OF_DAY, Long.valueOf(longValue7 / 1000));
            v(aVar10, a.MILLI_OF_SECOND, Long.valueOf(longValue7 % 1000));
        }
        a aVar11 = a.SECOND_OF_DAY;
        if (hashMap.containsKey(aVar11)) {
            long longValue8 = ((Long) hashMap.remove(aVar11)).longValue();
            if (this.d != E.LENIENT) {
                aVar11.R(longValue8);
            }
            v(aVar11, a.HOUR_OF_DAY, Long.valueOf(longValue8 / 3600));
            v(aVar11, a.MINUTE_OF_HOUR, Long.valueOf((longValue8 / 60) % 60));
            v(aVar11, a.SECOND_OF_MINUTE, Long.valueOf(longValue8 % 60));
        }
        a aVar12 = a.MINUTE_OF_DAY;
        if (hashMap.containsKey(aVar12)) {
            long longValue9 = ((Long) hashMap.remove(aVar12)).longValue();
            if (this.d != E.LENIENT) {
                aVar12.R(longValue9);
            }
            v(aVar12, a.HOUR_OF_DAY, Long.valueOf(longValue9 / 60));
            v(aVar12, a.MINUTE_OF_HOUR, Long.valueOf(longValue9 % 60));
        }
        a aVar13 = a.NANO_OF_SECOND;
        if (hashMap.containsKey(aVar13)) {
            long longValue10 = ((Long) hashMap.get(aVar13)).longValue();
            E e4 = this.d;
            E e5 = E.LENIENT;
            if (e4 != e5) {
                aVar13.R(longValue10);
            }
            a aVar14 = a.MICRO_OF_SECOND;
            if (hashMap.containsKey(aVar14)) {
                long longValue11 = ((Long) hashMap.remove(aVar14)).longValue();
                if (this.d != e5) {
                    aVar14.R(longValue11);
                }
                longValue10 = (longValue10 % 1000) + (longValue11 * 1000);
                v(aVar14, aVar13, Long.valueOf(longValue10));
            }
            a aVar15 = a.MILLI_OF_SECOND;
            if (hashMap.containsKey(aVar15)) {
                long longValue12 = ((Long) hashMap.remove(aVar15)).longValue();
                if (this.d != e5) {
                    aVar15.R(longValue12);
                }
                v(aVar15, aVar13, Long.valueOf((longValue10 % 1000000) + (longValue12 * 1000000)));
            }
        }
        a aVar16 = a.HOUR_OF_DAY;
        if (hashMap.containsKey(aVar16)) {
            a aVar17 = a.MINUTE_OF_HOUR;
            if (hashMap.containsKey(aVar17)) {
                a aVar18 = a.SECOND_OF_MINUTE;
                if (hashMap.containsKey(aVar18) && hashMap.containsKey(aVar13)) {
                    p(((Long) hashMap.remove(aVar16)).longValue(), ((Long) hashMap.remove(aVar17)).longValue(), ((Long) hashMap.remove(aVar18)).longValue(), ((Long) hashMap.remove(aVar13)).longValue());
                }
            }
        }
    }

    private void t(j jVar, q qVar) {
        j jVar2 = this.f;
        if (jVar2 == null) {
            this.f = jVar;
        } else if (jVar2.equals(jVar)) {
            q qVar2 = this.g;
            qVar2.getClass();
            q qVar3 = q.d;
            boolean z = true;
            if (!(qVar2 == qVar3)) {
                if (qVar != qVar3) {
                    z = false;
                }
                if (!z && !this.g.equals(qVar)) {
                    q qVar4 = this.g;
                    throw new DateTimeException("Conflict found: Fields resolved to different excess periods: " + qVar4 + " " + qVar);
                }
            }
        } else {
            j jVar3 = this.f;
            throw new DateTimeException("Conflict found: Fields resolved to different times: " + jVar3 + " " + jVar);
        }
        this.g = qVar;
    }

    private void u(C0039c cVar) {
        C0039c cVar2 = this.e;
        if (cVar2 != null) {
            if (cVar != null && !cVar2.equals(cVar)) {
                C0039c cVar3 = this.e;
                throw new DateTimeException("Conflict found: Fields resolved to two different dates: " + cVar3 + " " + cVar);
            }
        } else if (cVar != null) {
            if (((C0037a) this.c).equals(cVar.a())) {
                this.e = cVar;
                return;
            }
            m mVar = this.c;
            throw new DateTimeException("ChronoLocalDate must use the effective parsed chronology: " + mVar);
        }
    }

    private void v(a aVar, a aVar2, Long l) {
        Long l2 = (Long) this.a.put(aVar2, l);
        if (l2 != null && l2.longValue() != l.longValue()) {
            throw new DateTimeException("Conflict found: " + aVar2 + " " + l2 + " differs from " + aVar2 + " " + l + " while resolving  " + aVar);
        }
    }

    public final long E(j$.time.temporal.q qVar) {
        Objects.requireNonNull(qVar, "field");
        Long l = (Long) this.a.get(qVar);
        if (l != null) {
            return l.longValue();
        }
        C0039c cVar = this.e;
        if (cVar != null && cVar.e(qVar)) {
            return this.e.E(qVar);
        }
        j jVar = this.f;
        if (jVar != null && jVar.e(qVar)) {
            return this.f.E(qVar);
        }
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final Object H(s sVar) {
        if (sVar == p.l()) {
            return this.b;
        }
        if (sVar == p.e()) {
            return this.c;
        }
        if (sVar == p.f()) {
            C0039c cVar = this.e;
            if (cVar != null) {
                return LocalDate.S(cVar);
            }
            return null;
        } else if (sVar == p.g()) {
            return this.f;
        } else {
            if (sVar == p.i()) {
                Long l = (Long) this.a.get(a.OFFSET_SECONDS);
                if (l != null) {
                    return ZoneOffset.d0(l.intValue());
                }
                ZoneId zoneId = this.b;
                return zoneId instanceof ZoneOffset ? zoneId : sVar.a(this);
            } else if (sVar == p.k()) {
                return sVar.a(this);
            } else {
                if (sVar == p.j()) {
                    return null;
                }
                return sVar.a(this);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0008, code lost:
        r0 = r1.e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r0 = r1.f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean e(j$.time.temporal.q r2) {
        /*
            r1 = this;
            java.util.HashMap r0 = r1.a
            boolean r0 = r0.containsKey(r2)
            if (r0 != 0) goto L_0x002c
            j$.time.chrono.c r0 = r1.e
            if (r0 == 0) goto L_0x0012
            boolean r0 = r0.e(r2)
            if (r0 != 0) goto L_0x002c
        L_0x0012:
            j$.time.j r0 = r1.f
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.e(r2)
            if (r0 == 0) goto L_0x001d
            goto L_0x002c
        L_0x001d:
            if (r2 == 0) goto L_0x002a
            boolean r0 = r2 instanceof j$.time.temporal.a
            if (r0 != 0) goto L_0x002a
            boolean r2 = r2.k(r1)
            if (r2 == 0) goto L_0x002a
            goto L_0x002c
        L_0x002a:
            r2 = 0
            goto L_0x002d
        L_0x002c:
            r2 = 1
        L_0x002d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.D.e(j$.time.temporal.q):boolean");
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x0235  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0237  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x023a  */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0289  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x02aa  */
    /* JADX WARNING: Removed duplicated region for block: B:145:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0205  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x020c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void j(j$.time.format.E r25, java.util.Set r26) {
        /*
            r24 = this;
            r9 = r24
            r0 = r26
            java.util.HashMap r10 = r9.a
            if (r0 == 0) goto L_0x000f
            java.util.Set r1 = r10.keySet()
            r1.retainAll(r0)
        L_0x000f:
            r0 = r25
            r9.d = r0
            r24.n()
            j$.time.chrono.m r0 = r9.c
            j$.time.format.E r1 = r9.d
            j$.time.chrono.c r0 = r0.v(r10, r1)
            r9.u(r0)
            r24.s()
            int r0 = r10.size()
            if (r0 <= 0) goto L_0x00e4
            r0 = 0
        L_0x002b:
            r1 = 50
            if (r0 >= r1) goto L_0x00c6
            java.util.Set r2 = r10.entrySet()
            java.util.Iterator r2 = r2.iterator()
        L_0x0037:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x00c6
            java.lang.Object r3 = r2.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r3 = r3.getKey()
            j$.time.temporal.q r3 = (j$.time.temporal.q) r3
            j$.time.format.E r4 = r9.d
            j$.time.temporal.TemporalAccessor r4 = r3.p(r10, r9, r4)
            if (r4 == 0) goto L_0x00bc
            boolean r1 = r4 instanceof j$.time.chrono.ChronoZonedDateTime
            if (r1 == 0) goto L_0x0087
            j$.time.chrono.ChronoZonedDateTime r4 = (j$.time.chrono.ChronoZonedDateTime) r4
            j$.time.ZoneId r1 = r9.b
            if (r1 != 0) goto L_0x0062
            j$.time.ZoneId r1 = r4.D()
            r9.b = r1
            goto L_0x006c
        L_0x0062:
            j$.time.ZoneId r2 = r4.D()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0071
        L_0x006c:
            j$.time.chrono.f r4 = r4.q()
            goto L_0x0087
        L_0x0071:
            j$.time.DateTimeException r0 = new j$.time.DateTimeException
            j$.time.ZoneId r1 = r9.b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "ChronoZonedDateTime must use the effective parsed zone: "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x0087:
            boolean r1 = r4 instanceof j$.time.chrono.C0042f
            if (r1 == 0) goto L_0x009e
            j$.time.chrono.f r4 = (j$.time.chrono.C0042f) r4
            j$.time.j r1 = r4.b()
            j$.time.q r2 = j$.time.q.d
            r9.t(r1, r2)
            j$.time.chrono.c r1 = r4.f()
            r9.u(r1)
            goto L_0x00c2
        L_0x009e:
            boolean r1 = r4 instanceof j$.time.chrono.C0039c
            if (r1 == 0) goto L_0x00a8
            j$.time.chrono.c r4 = (j$.time.chrono.C0039c) r4
            r9.u(r4)
            goto L_0x00c2
        L_0x00a8:
            boolean r1 = r4 instanceof j$.time.j
            if (r1 == 0) goto L_0x00b4
            j$.time.j r4 = (j$.time.j) r4
            j$.time.q r1 = j$.time.q.d
            r9.t(r4, r1)
            goto L_0x00c2
        L_0x00b4:
            j$.time.DateTimeException r0 = new j$.time.DateTimeException
            java.lang.String r1 = "Method resolve() can only return ChronoZonedDateTime, ChronoLocalDateTime, ChronoLocalDate or LocalTime"
            r0.<init>(r1)
            throw r0
        L_0x00bc:
            boolean r3 = r10.containsKey(r3)
            if (r3 != 0) goto L_0x0037
        L_0x00c2:
            int r0 = r0 + 1
            goto L_0x002b
        L_0x00c6:
            if (r0 == r1) goto L_0x00dc
            if (r0 <= 0) goto L_0x00e4
            r24.n()
            j$.time.chrono.m r0 = r9.c
            j$.time.format.E r1 = r9.d
            j$.time.chrono.c r0 = r0.v(r10, r1)
            r9.u(r0)
            r24.s()
            goto L_0x00e4
        L_0x00dc:
            j$.time.DateTimeException r0 = new j$.time.DateTimeException
            java.lang.String r1 = "One of the parsed fields has an incorrectly implemented resolve method"
            r0.<init>(r1)
            throw r0
        L_0x00e4:
            j$.time.j r0 = r9.f
            r12 = 1000(0x3e8, double:4.94E-321)
            r14 = 1000000(0xf4240, double:4.940656E-318)
            r16 = 0
            if (r0 != 0) goto L_0x01c1
            j$.time.temporal.a r0 = j$.time.temporal.a.MILLI_OF_SECOND
            boolean r1 = r10.containsKey(r0)
            if (r1 == 0) goto L_0x0136
            java.lang.Object r1 = r10.remove(r0)
            java.lang.Long r1 = (java.lang.Long) r1
            long r1 = r1.longValue()
            j$.time.temporal.a r3 = j$.time.temporal.a.MICRO_OF_SECOND
            boolean r4 = r10.containsKey(r3)
            if (r4 == 0) goto L_0x012a
            long r1 = r1 * r12
            java.lang.Object r4 = r10.get(r3)
            java.lang.Long r4 = (java.lang.Long) r4
            long r4 = r4.longValue()
            long r4 = r4 % r12
            long r4 = r4 + r1
            java.lang.Long r1 = java.lang.Long.valueOf(r4)
            r9.v(r0, r3, r1)
            r10.remove(r3)
            j$.time.temporal.a r0 = j$.time.temporal.a.NANO_OF_SECOND
            long r4 = r4 * r12
            java.lang.Long r1 = java.lang.Long.valueOf(r4)
            goto L_0x0132
        L_0x012a:
            j$.time.temporal.a r0 = j$.time.temporal.a.NANO_OF_SECOND
            long r1 = r1 * r14
            java.lang.Long r1 = java.lang.Long.valueOf(r1)
        L_0x0132:
            r10.put(r0, r1)
            goto L_0x0153
        L_0x0136:
            j$.time.temporal.a r0 = j$.time.temporal.a.MICRO_OF_SECOND
            boolean r1 = r10.containsKey(r0)
            if (r1 == 0) goto L_0x0153
            java.lang.Object r0 = r10.remove(r0)
            java.lang.Long r0 = (java.lang.Long) r0
            long r0 = r0.longValue()
            j$.time.temporal.a r2 = j$.time.temporal.a.NANO_OF_SECOND
            long r0 = r0 * r12
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r10.put(r2, r0)
        L_0x0153:
            j$.time.temporal.a r7 = j$.time.temporal.a.HOUR_OF_DAY
            java.lang.Object r0 = r10.get(r7)
            java.lang.Long r0 = (java.lang.Long) r0
            if (r0 == 0) goto L_0x01c1
            j$.time.temporal.a r8 = j$.time.temporal.a.MINUTE_OF_HOUR
            java.lang.Object r1 = r10.get(r8)
            java.lang.Long r1 = (java.lang.Long) r1
            j$.time.temporal.a r5 = j$.time.temporal.a.SECOND_OF_MINUTE
            java.lang.Object r2 = r10.get(r5)
            java.lang.Long r2 = (java.lang.Long) r2
            j$.time.temporal.a r6 = j$.time.temporal.a.NANO_OF_SECOND
            java.lang.Object r3 = r10.get(r6)
            java.lang.Long r3 = (java.lang.Long) r3
            if (r1 != 0) goto L_0x017b
            if (r2 != 0) goto L_0x0201
            if (r3 != 0) goto L_0x0201
        L_0x017b:
            if (r1 == 0) goto L_0x0183
            if (r2 != 0) goto L_0x0183
            if (r3 == 0) goto L_0x0183
            goto L_0x0201
        L_0x0183:
            if (r1 == 0) goto L_0x018a
            long r18 = r1.longValue()
            goto L_0x018c
        L_0x018a:
            r18 = r16
        L_0x018c:
            if (r2 == 0) goto L_0x0195
            long r1 = r2.longValue()
            r20 = r1
            goto L_0x0197
        L_0x0195:
            r20 = r16
        L_0x0197:
            if (r3 == 0) goto L_0x01a0
            long r1 = r3.longValue()
            r22 = r1
            goto L_0x01a2
        L_0x01a0:
            r22 = r16
        L_0x01a2:
            long r1 = r0.longValue()
            r0 = r24
            r3 = r18
            r11 = r5
            r14 = r6
            r5 = r20
            r15 = r7
            r12 = r8
            r7 = r22
            r0.p(r1, r3, r5, r7)
            r10.remove(r15)
            r10.remove(r12)
            r10.remove(r11)
            r10.remove(r14)
        L_0x01c1:
            j$.time.format.E r0 = r9.d
            j$.time.format.E r1 = j$.time.format.E.LENIENT
            if (r0 == r1) goto L_0x0201
            int r0 = r10.size()
            if (r0 <= 0) goto L_0x0201
            java.util.Set r0 = r10.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x01d5:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0201
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            java.lang.Object r2 = r1.getKey()
            j$.time.temporal.q r2 = (j$.time.temporal.q) r2
            boolean r3 = r2 instanceof j$.time.temporal.a
            if (r3 == 0) goto L_0x01d5
            boolean r3 = r2.isTimeBased()
            if (r3 == 0) goto L_0x01d5
            j$.time.temporal.a r2 = (j$.time.temporal.a) r2
            java.lang.Object r1 = r1.getValue()
            java.lang.Long r1 = (java.lang.Long) r1
            long r3 = r1.longValue()
            r2.R(r3)
            goto L_0x01d5
        L_0x0201:
            j$.time.chrono.c r0 = r9.e
            if (r0 == 0) goto L_0x0208
            r9.i(r0)
        L_0x0208:
            j$.time.j r0 = r9.f
            if (r0 == 0) goto L_0x0224
            r9.i(r0)
            j$.time.chrono.c r0 = r9.e
            if (r0 == 0) goto L_0x0224
            int r0 = r10.size()
            if (r0 <= 0) goto L_0x0224
            j$.time.chrono.c r0 = r9.e
            j$.time.j r1 = r9.f
            j$.time.chrono.f r0 = r0.G(r1)
            r9.i(r0)
        L_0x0224:
            j$.time.chrono.c r0 = r9.e
            if (r0 == 0) goto L_0x0246
            j$.time.j r0 = r9.f
            if (r0 == 0) goto L_0x0246
            j$.time.q r0 = r9.g
            r0.getClass()
            j$.time.q r1 = j$.time.q.d
            if (r0 != r1) goto L_0x0237
            r11 = 1
            goto L_0x0238
        L_0x0237:
            r11 = 0
        L_0x0238:
            if (r11 != 0) goto L_0x0246
            j$.time.chrono.c r0 = r9.e
            j$.time.q r2 = r9.g
            j$.time.chrono.c r0 = r0.x(r2)
            r9.e = r0
            r9.g = r1
        L_0x0246:
            j$.time.j r0 = r9.f
            if (r0 != 0) goto L_0x02a2
            j$.time.temporal.a r0 = j$.time.temporal.a.INSTANT_SECONDS
            boolean r0 = r10.containsKey(r0)
            if (r0 != 0) goto L_0x0262
            j$.time.temporal.a r0 = j$.time.temporal.a.SECOND_OF_DAY
            boolean r0 = r10.containsKey(r0)
            if (r0 != 0) goto L_0x0262
            j$.time.temporal.a r0 = j$.time.temporal.a.SECOND_OF_MINUTE
            boolean r0 = r10.containsKey(r0)
            if (r0 == 0) goto L_0x02a2
        L_0x0262:
            j$.time.temporal.a r0 = j$.time.temporal.a.NANO_OF_SECOND
            boolean r1 = r10.containsKey(r0)
            if (r1 == 0) goto L_0x0289
            java.lang.Object r0 = r10.get(r0)
            java.lang.Long r0 = (java.lang.Long) r0
            long r0 = r0.longValue()
            j$.time.temporal.a r2 = j$.time.temporal.a.MICRO_OF_SECOND
            r3 = 1000(0x3e8, double:4.94E-321)
            long r3 = r0 / r3
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            r10.put(r2, r3)
            j$.time.temporal.a r2 = j$.time.temporal.a.MILLI_OF_SECOND
            r3 = 1000000(0xf4240, double:4.940656E-318)
            long r16 = r0 / r3
            goto L_0x029b
        L_0x0289:
            java.lang.Long r1 = java.lang.Long.valueOf(r16)
            r10.put(r0, r1)
            j$.time.temporal.a r0 = j$.time.temporal.a.MICRO_OF_SECOND
            java.lang.Long r1 = java.lang.Long.valueOf(r16)
            r10.put(r0, r1)
            j$.time.temporal.a r2 = j$.time.temporal.a.MILLI_OF_SECOND
        L_0x029b:
            java.lang.Long r0 = java.lang.Long.valueOf(r16)
            r10.put(r2, r0)
        L_0x02a2:
            j$.time.chrono.c r0 = r9.e
            if (r0 == 0) goto L_0x02e8
            j$.time.j r0 = r9.f
            if (r0 == 0) goto L_0x02e8
            j$.time.temporal.a r0 = j$.time.temporal.a.OFFSET_SECONDS
            java.lang.Object r0 = r10.get(r0)
            java.lang.Long r0 = (java.lang.Long) r0
            if (r0 == 0) goto L_0x02c9
            int r0 = r0.intValue()
            j$.time.ZoneOffset r0 = j$.time.ZoneOffset.d0(r0)
            j$.time.chrono.c r1 = r9.e
            j$.time.j r2 = r9.f
            j$.time.chrono.f r1 = r1.G(r2)
            j$.time.chrono.ChronoZonedDateTime r0 = r1.A(r0)
            goto L_0x02db
        L_0x02c9:
            j$.time.ZoneId r0 = r9.b
            if (r0 == 0) goto L_0x02e8
            j$.time.chrono.c r0 = r9.e
            j$.time.j r1 = r9.f
            j$.time.chrono.f r0 = r0.G(r1)
            j$.time.ZoneId r1 = r9.b
            j$.time.chrono.ChronoZonedDateTime r0 = r0.A(r1)
        L_0x02db:
            long r0 = r0.P()
            j$.time.temporal.a r2 = j$.time.temporal.a.INSTANT_SECONDS
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r10.put(r2, r0)
        L_0x02e8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.D.j(j$.time.format.E, java.util.Set):void");
    }

    public final /* synthetic */ int k(j$.time.temporal.q qVar) {
        return p.a(this, qVar);
    }

    public final /* synthetic */ u m(j$.time.temporal.q qVar) {
        return p.d(this, qVar);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(this.a);
        sb.append(',');
        sb.append(this.c);
        if (this.b != null) {
            sb.append(',');
            sb.append(this.b);
        }
        if (!(this.e == null && this.f == null)) {
            sb.append(" resolved to ");
            C0039c cVar = this.e;
            if (cVar != null) {
                sb.append(cVar);
                if (this.f != null) {
                    sb.append('T');
                }
            }
            sb.append(this.f);
        }
        return sb.toString();
    }
}
