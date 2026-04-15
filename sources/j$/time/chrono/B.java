package j$.time.chrono;

import j$.desugar.sun.nio.fs.c;
import j$.time.DateTimeException;
import j$.time.Instant;
import j$.time.LocalDate;
import j$.time.LocalDateTime;
import j$.time.ZoneId;
import j$.time.b;
import j$.time.format.E;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.a;
import j$.time.temporal.u;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public final class B extends C0037a implements Serializable {
    public static final B d = new B();
    private static final long serialVersionUID = 1039765215346859963L;

    private B() {
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public final C0039c B(TemporalAccessor temporalAccessor) {
        return temporalAccessor instanceof D ? (D) temporalAccessor : new D(LocalDate.S(temporalAccessor));
    }

    public final C0042f C(LocalDateTime localDateTime) {
        return super.C(localDateTime);
    }

    public final C0039c K(int i, int i2, int i3) {
        return new D(LocalDate.of(i + 1911, i2, i3));
    }

    public final ChronoZonedDateTime L(Instant instant, ZoneId zoneId) {
        return l.T(this, instant, zoneId);
    }

    public final boolean O(long j) {
        return t.d.O(j + 1911);
    }

    public final C0039c i(long j) {
        return new D(LocalDate.d0(j));
    }

    public final String j() {
        return "Minguo";
    }

    public final C0039c m() {
        LocalDate b0 = LocalDate.b0(b.c());
        return b0 instanceof D ? (D) b0 : new D(LocalDate.S(b0));
    }

    public final String n() {
        return "roc";
    }

    public final C0039c o(int i, int i2) {
        return new D(LocalDate.e0(i + 1911, i2));
    }

    public final u s(a aVar) {
        int i = A.a[aVar.ordinal()];
        if (i == 1) {
            u m = a.PROLEPTIC_MONTH.m();
            return u.j(m.e() - 22932, m.d() - 22932);
        } else if (i == 2) {
            u m2 = a.YEAR.m();
            return u.l(m2.d() - 1911, (-m2.e()) + 1 + 1911);
        } else if (i != 3) {
            return aVar.m();
        } else {
            u m3 = a.YEAR.m();
            return u.j(m3.e() - 1911, m3.d() - 1911);
        }
    }

    public final List t() {
        return c.a(E.values());
    }

    public final n u(int i) {
        if (i == 0) {
            return E.BEFORE_ROC;
        }
        if (i == 1) {
            return E.ROC;
        }
        throw new DateTimeException("Invalid era: " + i);
    }

    public final C0039c v(HashMap hashMap, E e) {
        return (D) super.v(hashMap, e);
    }

    public final int w(n nVar, int i) {
        if (nVar instanceof E) {
            return nVar == E.ROC ? i : 1 - i;
        }
        throw new ClassCastException("Era must be MinguoEra");
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new F((byte) 1, this);
    }
}
