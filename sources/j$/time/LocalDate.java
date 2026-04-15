package j$.time;

import j$.time.chrono.C0038b;
import j$.time.chrono.C0039c;
import j$.time.chrono.C0042f;
import j$.time.chrono.n;
import j$.time.chrono.t;
import j$.time.chrono.u;
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
import j$.time.zone.b;
import j$.util.Objects;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.mozilla.javascript.Token;

public final class LocalDate implements l, m, C0039c, Serializable {
    public static final LocalDate d = of(-999999999, 1, 1);
    public static final LocalDate e = of(999999999, 12, 31);
    private static final long serialVersionUID = 2942565459149668126L;
    private final int a;
    private final short b;
    private final short c;

    static {
        of(1970, 1, 1);
    }

    private LocalDate(int i, int i2, int i3) {
        this.a = i;
        this.b = (short) i2;
        this.c = (short) i3;
    }

    private static LocalDate R(int i, int i2, int i3) {
        int i4 = 28;
        if (i3 > 28) {
            if (i2 != 2) {
                i4 = (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) ? 30 : 31;
            } else if (t.d.O((long) i)) {
                i4 = 29;
            }
            if (i3 > i4) {
                if (i3 == 29) {
                    throw new DateTimeException("Invalid date 'February 29' as '" + i + "' is not a leap year");
                }
                String name = l.T(i2).name();
                throw new DateTimeException("Invalid date '" + name + " " + i3 + "'");
            }
        }
        return new LocalDate(i, i2, i3);
    }

    public static LocalDate S(TemporalAccessor temporalAccessor) {
        Objects.requireNonNull(temporalAccessor, "temporal");
        LocalDate localDate = (LocalDate) temporalAccessor.H(p.f());
        if (localDate != null) {
            return localDate;
        }
        String name = temporalAccessor.getClass().getName();
        throw new DateTimeException("Unable to obtain LocalDate from TemporalAccessor: " + temporalAccessor + " of type " + name);
    }

    private int T(q qVar) {
        int i;
        int i2 = g.a[((a) qVar).ordinal()];
        int i3 = this.a;
        short s = this.c;
        switch (i2) {
            case 1:
                return s;
            case 2:
                return V();
            case 3:
                i = (s - 1) / 7;
                break;
            case 4:
                return i3 >= 1 ? i3 : 1 - i3;
            case 5:
                return U().getValue();
            case 6:
                i = (s - 1) % 7;
                break;
            case 7:
                return ((V() - 1) % 7) + 1;
            case 8:
                throw new j$.time.temporal.t("Invalid field 'EpochDay' for get() method, use getLong() instead");
            case 9:
                return ((V() - 1) / 7) + 1;
            case 10:
                return this.b;
            case 11:
                throw new j$.time.temporal.t("Invalid field 'ProlepticMonth' for get() method, use getLong() instead");
            case 12:
                return i3;
            case 13:
                return i3 >= 1 ? 1 : 0;
            default:
                throw new j$.time.temporal.t(c.a("Unsupported field: ", qVar));
        }
        return i + 1;
    }

    public static LocalDate b0(b bVar) {
        Objects.requireNonNull(bVar, "clock");
        Instant V = Instant.V(System.currentTimeMillis());
        ZoneId a2 = bVar.a();
        Objects.requireNonNull(V, "instant");
        Objects.requireNonNull(a2, "zone");
        return d0(j$.lang.a.i(V.S() + ((long) a2.R().d(V).a0()), (long) 86400));
    }

    public static LocalDate c0(int i, l lVar, int i2) {
        a.YEAR.R((long) i);
        Objects.requireNonNull(lVar, "month");
        a.DAY_OF_MONTH.R((long) i2);
        return R(i, lVar.getValue(), i2);
    }

    public static LocalDate d0(long j) {
        long j2;
        long j3 = j;
        a.EPOCH_DAY.R(j3);
        long j4 = (j3 + 719528) - 60;
        if (j4 < 0) {
            long j5 = ((j4 + 1) / 146097) - 1;
            j2 = j5 * 400;
            j4 += (-j5) * 146097;
        } else {
            j2 = 0;
        }
        long j6 = ((j4 * 400) + 591) / 146097;
        long j7 = j4 - ((j6 / 400) + (((j6 / 4) + (j6 * 365)) - (j6 / 100)));
        if (j7 < 0) {
            j6--;
            j7 = j4 - ((j6 / 400) + (((j6 / 4) + (365 * j6)) - (j6 / 100)));
        }
        int i = (int) j7;
        int i2 = ((i * 5) + 2) / Token.SET;
        return new LocalDate(a.YEAR.Q(j6 + j2 + ((long) (i2 / 10))), ((i2 + 2) % 12) + 1, (i - (((i2 * 306) + 5) / 10)) + 1);
    }

    public static LocalDate e0(int i, int i2) {
        long j = (long) i;
        a.YEAR.R(j);
        a.DAY_OF_YEAR.R((long) i2);
        boolean O = t.d.O(j);
        if (i2 != 366 || O) {
            l T = l.T(((i2 - 1) / 31) + 1);
            if (i2 > (T.R(O) + T.Q(O)) - 1) {
                T = T.U();
            }
            return new LocalDate(i, T.getValue(), (i2 - T.Q(O)) + 1);
        }
        throw new DateTimeException("Invalid date 'DayOfYear 366' as '" + i + "' is not a leap year");
    }

    private static LocalDate k0(int i, int i2, int i3) {
        int i4;
        if (i2 != 2) {
            if (i2 == 4 || i2 == 6 || i2 == 9 || i2 == 11) {
                i4 = 30;
            }
            return new LocalDate(i, i2, i3);
        }
        i4 = t.d.O((long) i) ? 29 : 28;
        i3 = Math.min(i3, i4);
        return new LocalDate(i, i2, i3);
    }

    public static LocalDate of(int i, int i2, int i3) {
        a.YEAR.R((long) i);
        a.MONTH_OF_YEAR.R((long) i2);
        a.DAY_OF_MONTH.R((long) i3);
        return R(i, i2, i3);
    }

    public static LocalDate parse(CharSequence charSequence, DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return (LocalDate) dateTimeFormatter.e(charSequence, new e(1));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new r((byte) 3, this);
    }

    public final long E(q qVar) {
        return qVar instanceof a ? qVar == a.EPOCH_DAY ? F() : qVar == a.PROLEPTIC_MONTH ? ((((long) this.a) * 12) + ((long) this.b)) - 1 : (long) T(qVar) : qVar.E(this);
    }

    public final long F() {
        long j;
        long j2 = (long) this.a;
        long j3 = (long) this.b;
        long j4 = (365 * j2) + 0;
        if (j2 >= 0) {
            j = ((j2 + 399) / 400) + (((3 + j2) / 4) - ((99 + j2) / 100)) + j4;
        } else {
            j = j4 - ((j2 / -400) + ((j2 / -4) - (j2 / -100)));
        }
        long j5 = (((367 * j3) - 362) / 12) + j + ((long) (this.c - 1));
        if (j3 > 2) {
            j5--;
            if (!r()) {
                j5--;
            }
        }
        return j5 - 719528;
    }

    public final C0042f G(j jVar) {
        return LocalDateTime.Z(this, jVar);
    }

    public final Object H(s sVar) {
        return sVar == p.f() ? this : C0038b.l(this, sVar);
    }

    public final n I() {
        return this.a >= 1 ? u.CE : u.BCE;
    }

    public final int M() {
        return r() ? 366 : 365;
    }

    /* renamed from: N */
    public final int compareTo(C0039c cVar) {
        return cVar instanceof LocalDate ? Q((LocalDate) cVar) : C0038b.d(this, cVar);
    }

    /* access modifiers changed from: package-private */
    public final int Q(LocalDate localDate) {
        int i = this.a - localDate.a;
        if (i != 0) {
            return i;
        }
        int i2 = this.b - localDate.b;
        return i2 == 0 ? this.c - localDate.c : i2;
    }

    public final d U() {
        return d.Q(((int) j$.lang.a.k(F() + 3, (long) 7)) + 1);
    }

    public final int V() {
        return (l.T(this.b).Q(r()) + this.c) - 1;
    }

    public final int W() {
        return this.b;
    }

    public final int X() {
        return this.a;
    }

    public final boolean Y(LocalDate localDate) {
        return localDate instanceof LocalDate ? Q(localDate) < 0 : F() < localDate.F();
    }

    public final int Z() {
        short s = this.b;
        return s != 2 ? (s == 4 || s == 6 || s == 9 || s == 11) ? 30 : 31 : r() ? 29 : 28;
    }

    public final j$.time.chrono.m a() {
        return t.d;
    }

    /* renamed from: a0 */
    public final LocalDate g(long j, ChronoUnit chronoUnit) {
        return j == Long.MIN_VALUE ? d(Long.MAX_VALUE, chronoUnit).d(1, chronoUnit) : d(-j, chronoUnit);
    }

    public LocalDateTime atStartOfDay() {
        return LocalDateTime.Z(this, j.g);
    }

    public ZonedDateTime atStartOfDay(ZoneId zoneId) {
        b f;
        Objects.requireNonNull(zoneId, "zone");
        LocalDateTime Z = LocalDateTime.Z(this, j.g);
        if (!(zoneId instanceof ZoneOffset) && (f = zoneId.R().f(Z)) != null && f.Q()) {
            Z = f.k();
        }
        return ZonedDateTime.T(Z, zoneId, (ZoneOffset) null);
    }

    public final boolean e(q qVar) {
        return C0038b.j(this, qVar);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof LocalDate) && Q((LocalDate) obj) == 0;
    }

    /* renamed from: f0 */
    public final LocalDate d(long j, TemporalUnit temporalUnit) {
        if (!(temporalUnit instanceof ChronoUnit)) {
            return (LocalDate) temporalUnit.k(this, j);
        }
        switch (g.b[((ChronoUnit) temporalUnit).ordinal()]) {
            case 1:
                return g0(j);
            case 2:
                return i0(j);
            case 3:
                return h0(j);
            case 4:
                return j0(j);
            case 5:
                return j0(j$.lang.a.m(j, (long) 10));
            case 6:
                return j0(j$.lang.a.m(j, (long) 100));
            case 7:
                return j0(j$.lang.a.m(j, (long) 1000));
            case 8:
                a aVar = a.ERA;
                return c(j$.lang.a.l(E(aVar), j), aVar);
            default:
                throw new j$.time.temporal.t("Unsupported unit: " + temporalUnit);
        }
    }

    public final LocalDate g0(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = ((long) this.c) + j;
        if (j2 > 0) {
            short s = this.b;
            int i = this.a;
            if (j2 <= 28) {
                return new LocalDate(i, s, (int) j2);
            }
            if (j2 <= 59) {
                long Z = (long) Z();
                if (j2 <= Z) {
                    return new LocalDate(i, s, (int) j2);
                }
                if (s < 12) {
                    return new LocalDate(i, s + 1, (int) (j2 - Z));
                }
                int i2 = i + 1;
                a.YEAR.R((long) i2);
                return new LocalDate(i2, 1, (int) (j2 - Z));
            }
        }
        return d0(j$.lang.a.l(F(), j));
    }

    public final LocalDate h0(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (((long) this.a) * 12) + ((long) (this.b - 1)) + j;
        long j3 = (long) 12;
        return k0(a.YEAR.Q(j$.lang.a.i(j2, j3)), ((int) j$.lang.a.k(j2, j3)) + 1, this.c);
    }

    public final int hashCode() {
        int i = this.a;
        return (((i << 11) + (this.b << 6)) + this.c) ^ (i & -2048);
    }

    public final LocalDate i0(long j) {
        return g0(j$.lang.a.m(j, (long) 7));
    }

    public final LocalDate j0(long j) {
        return j == 0 ? this : k0(a.YEAR.Q(((long) this.a) + j), this.b, this.c);
    }

    public final int k(q qVar) {
        return qVar instanceof a ? T(qVar) : p.a(this, qVar);
    }

    /* renamed from: l0 */
    public final LocalDate c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (LocalDate) qVar.H(this, j);
        }
        a aVar = (a) qVar;
        aVar.R(j);
        int i = g.a[aVar.ordinal()];
        short s = this.b;
        short s2 = this.c;
        int i2 = this.a;
        switch (i) {
            case 1:
                int i3 = (int) j;
                return s2 == i3 ? this : of(i2, s, i3);
            case 2:
                return n0((int) j);
            case 3:
                return i0(j - E(a.ALIGNED_WEEK_OF_MONTH));
            case 4:
                if (i2 < 1) {
                    j = 1 - j;
                }
                return o0((int) j);
            case 5:
                return g0(j - ((long) U().getValue()));
            case 6:
                return g0(j - E(a.ALIGNED_DAY_OF_WEEK_IN_MONTH));
            case 7:
                return g0(j - E(a.ALIGNED_DAY_OF_WEEK_IN_YEAR));
            case 8:
                return d0(j);
            case 9:
                return i0(j - E(a.ALIGNED_WEEK_OF_YEAR));
            case 10:
                int i4 = (int) j;
                if (s == i4) {
                    return this;
                }
                a.MONTH_OF_YEAR.R((long) i4);
                return k0(i2, i4, s2);
            case 11:
                return h0(j - (((((long) i2) * 12) + ((long) s)) - 1));
            case 12:
                return o0((int) j);
            case 13:
                return E(a.ERA) == j ? this : o0(1 - i2);
            default:
                throw new j$.time.temporal.t(c.a("Unsupported field: ", qVar));
        }
    }

    public final j$.time.temporal.u m(q qVar) {
        int i;
        if (!(qVar instanceof a)) {
            return qVar.l(this);
        }
        a aVar = (a) qVar;
        if (aVar.isDateBased()) {
            int i2 = g.a[aVar.ordinal()];
            if (i2 == 1) {
                i = Z();
            } else if (i2 == 2) {
                i = M();
            } else if (i2 == 3) {
                return j$.time.temporal.u.j(1, (l.T(this.b) != l.FEBRUARY || r()) ? 5 : 4);
            } else if (i2 != 4) {
                return qVar.m();
            } else {
                return j$.time.temporal.u.j(1, this.a <= 0 ? 1000000000 : 999999999);
            }
            return j$.time.temporal.u.j(1, (long) i);
        }
        throw new j$.time.temporal.t(c.a("Unsupported field: ", qVar));
    }

    /* renamed from: m0 */
    public final LocalDate z(m mVar) {
        return mVar instanceof LocalDate ? (LocalDate) mVar : (LocalDate) mVar.p(this);
    }

    public final LocalDate n0(int i) {
        return V() == i ? this : e0(this.a, i);
    }

    public final LocalDate o0(int i) {
        if (this.a == i) {
            return this;
        }
        a.YEAR.R((long) i);
        return k0(i, this.b, this.c);
    }

    public final l p(l lVar) {
        return C0038b.a(this, lVar);
    }

    /* access modifiers changed from: package-private */
    public final void p0(DataOutput dataOutput) {
        dataOutput.writeInt(this.a);
        dataOutput.writeByte(this.b);
        dataOutput.writeByte(this.c);
    }

    public final boolean r() {
        return t.d.O((long) this.a);
    }

    public final String toString() {
        int i;
        int i2 = this.a;
        int abs = Math.abs(i2);
        StringBuilder sb = new StringBuilder(10);
        if (abs < 1000) {
            if (i2 < 0) {
                sb.append(i2 - 10000);
                i = 1;
            } else {
                sb.append(i2 + 10000);
                i = 0;
            }
            sb.deleteCharAt(i);
        } else {
            if (i2 > 9999) {
                sb.append('+');
            }
            sb.append(i2);
        }
        String str = "-0";
        short s = this.b;
        sb.append(s < 10 ? str : "-");
        sb.append(s);
        short s2 = this.c;
        if (s2 >= 10) {
            str = "-";
        }
        sb.append(str);
        sb.append(s2);
        return sb.toString();
    }

    public final C0039c x(q qVar) {
        if (qVar instanceof q) {
            return h0(qVar.e()).g0((long) qVar.b());
        }
        Objects.requireNonNull(qVar, "amountToAdd");
        return (LocalDate) qVar.a(this);
    }
}
