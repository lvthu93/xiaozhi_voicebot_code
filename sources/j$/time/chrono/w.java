package j$.time.chrono;

import j$.desugar.sun.nio.fs.c;
import j$.time.DateTimeException;
import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.LocalDateTime;
import j$.time.ZoneId;
import j$.time.b;
import j$.time.format.E;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.o;
import j$.time.temporal.p;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public final class w extends C0037a implements Serializable {
    public static final w d = new w();
    private static final long serialVersionUID = 459996390165777884L;

    private w() {
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public final C0039c B(TemporalAccessor temporalAccessor) {
        return temporalAccessor instanceof y ? (y) temporalAccessor : new y(LocalDate.S(temporalAccessor));
    }

    public final C0042f C(LocalDateTime localDateTime) {
        return super.C(localDateTime);
    }

    public final C0039c K(int i, int i2, int i3) {
        return new y(LocalDate.of(i, i2, i3));
    }

    public final ChronoZonedDateTime L(Instant instant, ZoneId zoneId) {
        return l.T(this, instant, zoneId);
    }

    public final boolean O(long j) {
        return t.d.O(j);
    }

    /* access modifiers changed from: package-private */
    public final C0039c S(HashMap hashMap, E e) {
        y yVar;
        a aVar = a.ERA;
        Long l = (Long) hashMap.get(aVar);
        z s = l != null ? z.s(s(aVar).a(l.longValue(), aVar)) : null;
        a aVar2 = a.YEAR_OF_ERA;
        Long l2 = (Long) hashMap.get(aVar2);
        int a = l2 != null ? s(aVar2).a(l2.longValue(), aVar2) : 0;
        if (s == null && l2 != null && !hashMap.containsKey(a.YEAR) && e != E.STRICT) {
            s = z.v()[z.v().length - 1];
        }
        if (!(l2 == null || s == null)) {
            a aVar3 = a.MONTH_OF_YEAR;
            if (hashMap.containsKey(aVar3)) {
                a aVar4 = a.DAY_OF_MONTH;
                if (hashMap.containsKey(aVar4)) {
                    hashMap.remove(aVar);
                    hashMap.remove(aVar2);
                    if (e == E.LENIENT) {
                        return new y(LocalDate.of((s.n().X() + a) - 1, 1, 1)).d(j$.lang.a.n(((Long) hashMap.remove(aVar3)).longValue(), 1), ChronoUnit.MONTHS).d(j$.lang.a.n(((Long) hashMap.remove(aVar4)).longValue(), 1), ChronoUnit.DAYS);
                    }
                    int a2 = s(aVar3).a(((Long) hashMap.remove(aVar3)).longValue(), aVar3);
                    int a3 = s(aVar4).a(((Long) hashMap.remove(aVar4)).longValue(), aVar4);
                    if (e != E.SMART) {
                        LocalDate localDate = y.d;
                        Objects.requireNonNull(s, "era");
                        LocalDate of = LocalDate.of((s.n().X() + a) - 1, a2, a3);
                        if (!of.Y(s.n()) && s == z.i(of)) {
                            return new y(s, a, of);
                        }
                        throw new DateTimeException("year, month, and day not valid for Era");
                    } else if (a >= 1) {
                        int X = (s.n().X() + a) - 1;
                        try {
                            yVar = new y(LocalDate.of(X, a2, a3));
                        } catch (DateTimeException unused) {
                            yVar = new y(LocalDate.of(X, a2, 1)).z(new o());
                        }
                        if (yVar.U() == s || p.a(yVar, a.YEAR_OF_ERA) <= 1 || a <= 1) {
                            return yVar;
                        }
                        throw new DateTimeException("Invalid YearOfEra for Era: " + s + " " + a);
                    } else {
                        throw new DateTimeException("Invalid YearOfEra: " + a);
                    }
                }
            }
            a aVar5 = a.DAY_OF_YEAR;
            if (hashMap.containsKey(aVar5)) {
                hashMap.remove(aVar);
                hashMap.remove(aVar2);
                if (e == E.LENIENT) {
                    return new y(LocalDate.e0((s.n().X() + a) - 1, 1)).d(j$.lang.a.n(((Long) hashMap.remove(aVar5)).longValue(), 1), ChronoUnit.DAYS);
                }
                int a4 = s(aVar5).a(((Long) hashMap.remove(aVar5)).longValue(), aVar5);
                LocalDate localDate2 = y.d;
                Objects.requireNonNull(s, "era");
                int X2 = s.n().X();
                LocalDate e0 = a == 1 ? LocalDate.e0(X2, (s.n().V() + a4) - 1) : LocalDate.e0((X2 + a) - 1, a4);
                if (!e0.Y(s.n()) && s == z.i(e0)) {
                    return new y(s, a, e0);
                }
                throw new DateTimeException("Invalid parameters");
            }
        }
        return null;
    }

    public final C0039c i(long j) {
        return new y(LocalDate.d0(j));
    }

    public final String j() {
        return "Japanese";
    }

    public final C0039c m() {
        LocalDate b0 = LocalDate.b0(b.c());
        return b0 instanceof y ? (y) b0 : new y(LocalDate.S(b0));
    }

    public final String n() {
        return "japanese";
    }

    public final C0039c o(int i, int i2) {
        return new y(LocalDate.e0(i, i2));
    }

    public final u s(a aVar) {
        switch (v.a[aVar.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                throw new t("Unsupported field: " + aVar);
            case 5:
                return u.l(z.u(), (long) (999999999 - z.j().n().X()));
            case 6:
                return u.l(z.t(), a.DAY_OF_YEAR.m().d());
            case 7:
                return u.j((long) y.d.X(), 999999999);
            case 8:
                return u.j((long) z.d.getValue(), (long) z.j().getValue());
            default:
                return aVar.m();
        }
    }

    public final List t() {
        return c.a(z.v());
    }

    public final n u(int i) {
        return z.s(i);
    }

    public final C0039c v(HashMap hashMap, E e) {
        return (y) super.v(hashMap, e);
    }

    public final int w(n nVar, int i) {
        if (nVar instanceof z) {
            z zVar = (z) nVar;
            int X = (zVar.n().X() + i) - 1;
            if (i == 1) {
                return X;
            }
            if (X >= -999999999 && X <= 999999999 && X >= zVar.n().X() && nVar == z.i(LocalDate.of(X, 1, 1))) {
                return X;
            }
            throw new DateTimeException("Invalid yearOfEra value");
        }
        throw new ClassCastException("Era must be JapaneseEra");
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new F((byte) 1, this);
    }
}
