package j$.time;

import j$.time.chrono.C0038b;
import j$.time.format.DateTimeFormatter;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.s;
import j$.time.temporal.t;
import j$.time.temporal.u;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public final class Instant implements l, m, Comparable<Instant>, Serializable {
    public static final Instant c = new Instant(0, 0);
    private static final long serialVersionUID = -665713676816604388L;
    private final long a;
    private final int b;

    static {
        W(-31557014167219200L, 0);
        W(31556889864403199L, 999999999);
    }

    private Instant(long j, int i) {
        this.a = j;
        this.b = i;
    }

    private static Instant Q(long j, int i) {
        if ((((long) i) | j) == 0) {
            return c;
        }
        if (j >= -31557014167219200L && j <= 31556889864403199L) {
            return new Instant(j, i);
        }
        throw new DateTimeException("Instant exceeds minimum or maximum instant");
    }

    public static Instant R(TemporalAccessor temporalAccessor) {
        if (temporalAccessor instanceof Instant) {
            return (Instant) temporalAccessor;
        }
        Objects.requireNonNull(temporalAccessor, "temporal");
        try {
            return W(temporalAccessor.E(a.INSTANT_SECONDS), (long) temporalAccessor.k(a.NANO_OF_SECOND));
        } catch (DateTimeException e) {
            String name = temporalAccessor.getClass().getName();
            throw new DateTimeException("Unable to obtain Instant from TemporalAccessor: " + temporalAccessor + " of type " + name, e);
        }
    }

    public static Instant U() {
        a.b.getClass();
        return V(System.currentTimeMillis());
    }

    public static Instant V(long j) {
        long j2 = (long) 1000;
        return Q(j$.lang.a.i(j, j2), ((int) j$.lang.a.k(j, j2)) * 1000000);
    }

    public static Instant W(long j, long j2) {
        return Q(j$.lang.a.l(j, j$.lang.a.i(j2, 1000000000)), (int) j$.lang.a.k(j2, 1000000000));
    }

    private Instant X(long j, long j2) {
        if ((j | j2) == 0) {
            return this;
        }
        return W(j$.lang.a.l(j$.lang.a.l(this.a, j), j2 / 1000000000), ((long) this.b) + (j2 % 1000000000));
    }

    public static Instant ofEpochSecond(long j) {
        return Q(j, 0);
    }

    public static Instant parse(CharSequence charSequence) {
        return (Instant) DateTimeFormatter.j.e(charSequence, new e(0));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 2, this);
    }

    public final long E(q qVar) {
        int i;
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i2 = f.a[((a) qVar).ordinal()];
        int i3 = this.b;
        if (i2 == 1) {
            return (long) i3;
        }
        if (i2 == 2) {
            i = i3 / 1000;
        } else if (i2 == 3) {
            i = i3 / 1000000;
        } else if (i2 == 4) {
            return this.a;
        } else {
            throw new t(c.a("Unsupported field: ", qVar));
        }
        return (long) i;
    }

    public final Object H(s sVar) {
        if (sVar == p.j()) {
            return ChronoUnit.NANOS;
        }
        if (sVar == p.e() || sVar == p.l() || sVar == p.k() || sVar == p.i() || sVar == p.f() || sVar == p.g()) {
            return null;
        }
        return sVar.a(this);
    }

    public final long S() {
        return this.a;
    }

    public final int T() {
        return this.b;
    }

    /* renamed from: Y */
    public final Instant d(long j, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return (Instant) temporalUnit.k(this, j);
        }
        switch (f.b[((ChronoUnit) temporalUnit).ordinal()]) {
            case 1:
                return X(0, j);
            case 2:
                return X(j / 1000000, (j % 1000000) * 1000);
            case 3:
                return X(j / 1000, (j % 1000) * 1000000);
            case 4:
                return X(j, 0);
            case 5:
                return X(j$.lang.a.m(j, (long) 60), 0);
            case 6:
                return X(j$.lang.a.m(j, (long) 3600), 0);
            case 7:
                return X(j$.lang.a.m(j, (long) 43200), 0);
            case 8:
                return X(j$.lang.a.m(j, (long) 86400), 0);
            default:
                throw new t("Unsupported unit: " + temporalUnit);
        }
    }

    public final long Z() {
        long m;
        int i;
        int i2 = this.b;
        long j = this.a;
        if (j >= 0 || i2 <= 0) {
            m = j$.lang.a.m(j, (long) 1000);
            i = i2 / 1000000;
        } else {
            m = j$.lang.a.m(j + 1, (long) 1000);
            i = (i2 / 1000000) - 1000;
        }
        return j$.lang.a.l(m, (long) i);
    }

    /* access modifiers changed from: package-private */
    public final void a0(DataOutput dataOutput) {
        dataOutput.writeLong(this.a);
        dataOutput.writeInt(this.b);
    }

    public OffsetDateTime atOffset(ZoneOffset zoneOffset) {
        return OffsetDateTime.S(this, zoneOffset);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003d, code lost:
        if (r7 != r4) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
        if (r7 != r4) goto L_0x004c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final j$.time.temporal.l c(long r6, j$.time.temporal.q r8) {
        /*
            r5 = this;
            boolean r0 = r8 instanceof j$.time.temporal.a
            if (r0 == 0) goto L_0x0053
            r0 = r8
            j$.time.temporal.a r0 = (j$.time.temporal.a) r0
            r0.R(r6)
            int[] r1 = j$.time.f.a
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 1
            long r2 = r5.a
            int r4 = r5.b
            if (r0 == r1) goto L_0x0046
            r1 = 2
            if (r0 == r1) goto L_0x0040
            r1 = 3
            if (r0 == r1) goto L_0x0037
            r1 = 4
            if (r0 != r1) goto L_0x002b
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 == 0) goto L_0x0051
            j$.time.Instant r6 = Q(r6, r4)
            goto L_0x0059
        L_0x002b:
            j$.time.temporal.t r6 = new j$.time.temporal.t
            java.lang.String r7 = "Unsupported field: "
            java.lang.String r7 = j$.time.c.a(r7, r8)
            r6.<init>(r7)
            throw r6
        L_0x0037:
            int r7 = (int) r6
            r6 = 1000000(0xf4240, float:1.401298E-39)
            int r7 = r7 * r6
            if (r7 == r4) goto L_0x0051
            goto L_0x004c
        L_0x0040:
            int r7 = (int) r6
            int r7 = r7 * 1000
            if (r7 == r4) goto L_0x0051
            goto L_0x004c
        L_0x0046:
            long r0 = (long) r4
            int r8 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r8 == 0) goto L_0x0051
            int r7 = (int) r6
        L_0x004c:
            j$.time.Instant r6 = Q(r2, r7)
            goto L_0x0059
        L_0x0051:
            r6 = r5
            goto L_0x0059
        L_0x0053:
            j$.time.temporal.l r6 = r8.H(r5, r6)
            j$.time.Instant r6 = (j$.time.Instant) r6
        L_0x0059:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.Instant.c(long, j$.time.temporal.q):j$.time.temporal.l");
    }

    public final int compareTo(Object obj) {
        Instant instant = (Instant) obj;
        int compare = Long.compare(this.a, instant.a);
        return compare != 0 ? compare : this.b - instant.b;
    }

    public final boolean e(q qVar) {
        return qVar instanceof a ? qVar == a.INSTANT_SECONDS || qVar == a.NANO_OF_SECOND || qVar == a.MICRO_OF_SECOND || qVar == a.MILLI_OF_SECOND : qVar != null && qVar.k(this);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Instant)) {
            return false;
        }
        Instant instant = (Instant) obj;
        return this.a == instant.a && this.b == instant.b;
    }

    public final l g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public final int hashCode() {
        long j = this.a;
        return (this.b * 51) + ((int) (j ^ (j >>> 32)));
    }

    public final int k(q qVar) {
        if (!(qVar instanceof a)) {
            return p.d(this, qVar).a(qVar.E(this), qVar);
        }
        int i = f.a[((a) qVar).ordinal()];
        int i2 = this.b;
        if (i == 1) {
            return i2;
        }
        if (i == 2) {
            return i2 / 1000;
        }
        if (i == 3) {
            return i2 / 1000000;
        }
        if (i == 4) {
            a.INSTANT_SECONDS.Q(this.a);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final l l(LocalDate localDate) {
        return (Instant) C0038b.a(localDate, this);
    }

    public final u m(q qVar) {
        return p.d(this, qVar);
    }

    public final l p(l lVar) {
        return lVar.c(this.a, a.INSTANT_SECONDS).c((long) this.b, a.NANO_OF_SECOND);
    }

    public final String toString() {
        return DateTimeFormatter.j.format(this);
    }
}
