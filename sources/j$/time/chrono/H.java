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

public final class H extends C0037a implements Serializable {
    public static final H d = new H();
    private static final long serialVersionUID = 2775954514031616474L;

    static {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        hashMap.put("en", new String[]{"BB", "BE"});
        hashMap.put("th", new String[]{"BB", "BE"});
        hashMap2.put("en", new String[]{"B.B.", "B.E."});
        hashMap2.put("th", new String[]{"พ.ศ.", "ปีก่อนคริสต์กาลที่"});
        hashMap3.put("en", new String[]{"Before Buddhist", "Budhhist Era"});
        hashMap3.put("th", new String[]{"พุทธศักราช", "ปีก่อนคริสต์กาลที่"});
    }

    private H() {
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public final C0039c B(TemporalAccessor temporalAccessor) {
        return temporalAccessor instanceof J ? (J) temporalAccessor : new J(LocalDate.S(temporalAccessor));
    }

    public final C0042f C(LocalDateTime localDateTime) {
        return super.C(localDateTime);
    }

    public final C0039c K(int i, int i2, int i3) {
        return new J(LocalDate.of(i - 543, i2, i3));
    }

    public final ChronoZonedDateTime L(Instant instant, ZoneId zoneId) {
        return l.T(this, instant, zoneId);
    }

    public final boolean O(long j) {
        return t.d.O(j - 543);
    }

    public final C0039c i(long j) {
        return new J(LocalDate.d0(j));
    }

    public final String j() {
        return "ThaiBuddhist";
    }

    public final C0039c m() {
        LocalDate b0 = LocalDate.b0(b.c());
        return b0 instanceof J ? (J) b0 : new J(LocalDate.S(b0));
    }

    public final String n() {
        return "buddhist";
    }

    public final C0039c o(int i, int i2) {
        return new J(LocalDate.e0(i - 543, i2));
    }

    public final u s(a aVar) {
        int i = G.a[aVar.ordinal()];
        if (i == 1) {
            u m = a.PROLEPTIC_MONTH.m();
            return u.j(m.e() + 6516, m.d() + 6516);
        } else if (i == 2) {
            u m2 = a.YEAR.m();
            return u.l((-(m2.e() + 543)) + 1, m2.d() + 543);
        } else if (i != 3) {
            return aVar.m();
        } else {
            u m3 = a.YEAR.m();
            return u.j(m3.e() + 543, m3.d() + 543);
        }
    }

    public final List t() {
        return c.a(K.values());
    }

    public final n u(int i) {
        if (i == 0) {
            return K.BEFORE_BE;
        }
        if (i == 1) {
            return K.BE;
        }
        throw new DateTimeException("Invalid era: " + i);
    }

    public final C0039c v(HashMap hashMap, E e) {
        return (J) super.v(hashMap, e);
    }

    public final int w(n nVar, int i) {
        if (nVar instanceof K) {
            return nVar == K.BE ? i : 1 - i;
        }
        throw new ClassCastException("Era must be BuddhistEra");
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new F((byte) 1, this);
    }
}
