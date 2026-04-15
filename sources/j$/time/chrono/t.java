package j$.time.chrono;

import j$.desugar.sun.nio.fs.c;
import j$.time.DateTimeException;
import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.LocalDateTime;
import j$.time.ZoneId;
import j$.time.ZonedDateTime;
import j$.time.b;
import j$.time.format.E;
import j$.time.l;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public final class t extends C0037a implements Serializable {
    public static final t d = new t();
    private static final long serialVersionUID = -1440403870442975015L;

    private t() {
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public final C0039c B(TemporalAccessor temporalAccessor) {
        return LocalDate.S(temporalAccessor);
    }

    public final C0042f C(LocalDateTime localDateTime) {
        return LocalDateTime.R(localDateTime);
    }

    public final C0039c K(int i, int i2, int i3) {
        return LocalDate.of(i, i2, i3);
    }

    public final ChronoZonedDateTime L(Instant instant, ZoneId zoneId) {
        return ZonedDateTime.S(instant, zoneId);
    }

    public final boolean O(long j) {
        return (3 & j) == 0 && (j % 100 != 0 || j % 400 == 0);
    }

    /* access modifiers changed from: package-private */
    public final void Q(HashMap hashMap, E e) {
        a aVar = a.PROLEPTIC_MONTH;
        Long l = (Long) hashMap.remove(aVar);
        if (l != null) {
            if (e != E.LENIENT) {
                aVar.R(l.longValue());
            }
            long j = (long) 12;
            C0037a.k(hashMap, a.MONTH_OF_YEAR, (long) (((int) j$.lang.a.k(l.longValue(), j)) + 1));
            C0037a.k(hashMap, a.YEAR, j$.lang.a.i(l.longValue(), j));
        }
    }

    /* access modifiers changed from: package-private */
    public final C0039c R(HashMap hashMap, E e) {
        int i;
        a aVar = a.YEAR;
        int Q = aVar.Q(((Long) hashMap.remove(aVar)).longValue());
        boolean z = true;
        if (e == E.LENIENT) {
            long n = j$.lang.a.n(((Long) hashMap.remove(a.MONTH_OF_YEAR)).longValue(), 1);
            return LocalDate.of(Q, 1, 1).h0(n).g0(j$.lang.a.n(((Long) hashMap.remove(a.DAY_OF_MONTH)).longValue(), 1));
        }
        a aVar2 = a.MONTH_OF_YEAR;
        int Q2 = aVar2.Q(((Long) hashMap.remove(aVar2)).longValue());
        a aVar3 = a.DAY_OF_MONTH;
        int Q3 = aVar3.Q(((Long) hashMap.remove(aVar3)).longValue());
        if (e == E.SMART) {
            if (Q2 == 4 || Q2 == 6 || Q2 == 9 || Q2 == 11) {
                i = 30;
            } else if (Q2 == 2) {
                l lVar = l.FEBRUARY;
                long j = (long) Q;
                int i2 = j$.time.t.b;
                if ((3 & j) != 0 || (j % 100 == 0 && j % 400 != 0)) {
                    z = false;
                }
                i = lVar.R(z);
            }
            Q3 = Math.min(Q3, i);
        }
        return LocalDate.of(Q, Q2, Q3);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003b, code lost:
        if (r11 > 0) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final j$.time.chrono.C0039c S(java.util.HashMap r10, j$.time.format.E r11) {
        /*
            r9 = this;
            j$.time.temporal.a r0 = j$.time.temporal.a.YEAR_OF_ERA
            java.lang.Object r1 = r10.remove(r0)
            java.lang.Long r1 = (java.lang.Long) r1
            if (r1 == 0) goto L_0x0097
            j$.time.format.E r2 = j$.time.format.E.LENIENT
            if (r11 == r2) goto L_0x0015
            long r2 = r1.longValue()
            r0.R(r2)
        L_0x0015:
            j$.time.temporal.a r2 = j$.time.temporal.a.ERA
            java.lang.Object r2 = r10.remove(r2)
            java.lang.Long r2 = (java.lang.Long) r2
            r3 = 0
            r5 = 1
            if (r2 != 0) goto L_0x005e
            j$.time.temporal.a r2 = j$.time.temporal.a.YEAR
            java.lang.Object r7 = r10.get(r2)
            java.lang.Long r7 = (java.lang.Long) r7
            j$.time.format.E r8 = j$.time.format.E.STRICT
            if (r11 != r8) goto L_0x0042
            if (r7 == 0) goto L_0x003e
            long r7 = r7.longValue()
            int r11 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            long r0 = r1.longValue()
            if (r11 <= 0) goto L_0x0051
            goto L_0x005a
        L_0x003e:
            r10.put(r0, r1)
            goto L_0x00ac
        L_0x0042:
            if (r7 == 0) goto L_0x0056
            long r7 = r7.longValue()
            int r11 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r11 <= 0) goto L_0x004d
            goto L_0x0056
        L_0x004d:
            long r0 = r1.longValue()
        L_0x0051:
            long r0 = j$.lang.a.n(r5, r0)
            goto L_0x005a
        L_0x0056:
            long r0 = r1.longValue()
        L_0x005a:
            j$.time.chrono.C0037a.k(r10, r2, r0)
            goto L_0x00ac
        L_0x005e:
            long r7 = r2.longValue()
            int r11 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r11 != 0) goto L_0x006d
            j$.time.temporal.a r11 = j$.time.temporal.a.YEAR
            long r0 = r1.longValue()
            goto L_0x007f
        L_0x006d:
            long r7 = r2.longValue()
            int r11 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r11 != 0) goto L_0x0083
            j$.time.temporal.a r11 = j$.time.temporal.a.YEAR
            long r0 = r1.longValue()
            long r0 = j$.lang.a.n(r5, r0)
        L_0x007f:
            j$.time.chrono.C0037a.k(r10, r11, r0)
            goto L_0x00ac
        L_0x0083:
            j$.time.DateTimeException r10 = new j$.time.DateTimeException
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r0 = "Invalid value for era: "
            r11.<init>(r0)
            r11.append(r2)
            java.lang.String r11 = r11.toString()
            r10.<init>(r11)
            throw r10
        L_0x0097:
            j$.time.temporal.a r11 = j$.time.temporal.a.ERA
            boolean r0 = r10.containsKey(r11)
            if (r0 == 0) goto L_0x00ac
            java.lang.Object r10 = r10.get(r11)
            java.lang.Long r10 = (java.lang.Long) r10
            long r0 = r10.longValue()
            r11.R(r0)
        L_0x00ac:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.t.S(java.util.HashMap, j$.time.format.E):j$.time.chrono.c");
    }

    public final C0039c i(long j) {
        return LocalDate.d0(j);
    }

    public final String j() {
        return "ISO";
    }

    public final C0039c m() {
        b c = b.c();
        Objects.requireNonNull(c, "clock");
        return LocalDate.S(LocalDate.b0(c));
    }

    public final String n() {
        return "iso8601";
    }

    public final C0039c o(int i, int i2) {
        return LocalDate.e0(i, i2);
    }

    public final u s(a aVar) {
        return aVar.m();
    }

    public final List t() {
        return c.a(u.values());
    }

    public final n u(int i) {
        if (i == 0) {
            return u.BCE;
        }
        if (i == 1) {
            return u.CE;
        }
        throw new DateTimeException("Invalid era: " + i);
    }

    public final C0039c v(HashMap hashMap, E e) {
        return (LocalDate) super.v(hashMap, e);
    }

    public final int w(n nVar, int i) {
        if (nVar instanceof u) {
            return nVar == u.CE ? i : 1 - i;
        }
        throw new ClassCastException("Era must be IsoEra");
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new F((byte) 1, this);
    }
}
