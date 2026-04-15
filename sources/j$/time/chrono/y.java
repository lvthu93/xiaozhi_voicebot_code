package j$.time.chrono;

import j$.time.DateTimeException;
import j$.time.LocalDate;
import j$.time.c;
import j$.time.j;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.a;
import j$.time.temporal.l;
import j$.time.temporal.m;
import j$.time.temporal.q;
import j$.time.temporal.t;
import j$.time.temporal.u;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

public final class y extends C0041e {
    static final LocalDate d = LocalDate.of(1873, 1, 1);
    private static final long serialVersionUID = -305327627230580483L;
    private final transient LocalDate a;
    private transient z b;
    private transient int c;

    y(LocalDate localDate) {
        if (!localDate.Y(d)) {
            this.b = z.i(localDate);
            this.c = (localDate.X() - this.b.n().X()) + 1;
            this.a = localDate;
            return;
        }
        throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
    }

    y(z zVar, int i, LocalDate localDate) {
        if (!localDate.Y(d)) {
            this.b = zVar;
            this.c = i;
            this.a = localDate;
            return;
        }
        throw new DateTimeException("JapaneseDate before Meiji 6 is not supported");
    }

    private y X(LocalDate localDate) {
        return localDate.equals(this.a) ? this : new y(localDate);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new F((byte) 4, this);
    }

    public final long E(q qVar) {
        int i;
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i2 = x.a[((a) qVar).ordinal()];
        LocalDate localDate = this.a;
        switch (i2) {
            case 2:
                if (this.c != 1) {
                    i = localDate.V();
                    break;
                } else {
                    i = (localDate.V() - this.b.n().V()) + 1;
                    break;
                }
            case 3:
                i = this.c;
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                throw new t(c.a("Unsupported field: ", qVar));
            case 8:
                i = this.b.getValue();
                break;
            default:
                return localDate.E(qVar);
        }
        return (long) i;
    }

    public final long F() {
        return this.a.F();
    }

    public final C0042f G(j jVar) {
        return C0044h.S(this, jVar);
    }

    public final n I() {
        return this.b;
    }

    public final int M() {
        z o = this.b.o();
        LocalDate localDate = this.a;
        int M = (o == null || o.n().X() != localDate.X()) ? localDate.M() : o.n().V() - 1;
        return this.c == 1 ? M - (this.b.n().V() - 1) : M;
    }

    /* access modifiers changed from: package-private */
    public final C0039c R(long j) {
        return X(this.a.g0(j));
    }

    /* access modifiers changed from: package-private */
    public final C0039c S(long j) {
        return X(this.a.h0(j));
    }

    /* access modifiers changed from: package-private */
    public final C0039c T(long j) {
        return X(this.a.j0(j));
    }

    public final z U() {
        return this.b;
    }

    /* renamed from: V */
    public final y d(long j, TemporalUnit temporalUnit) {
        return (y) super.d(j, temporalUnit);
    }

    /* renamed from: W */
    public final y c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (y) super.c(j, qVar);
        }
        a aVar = (a) qVar;
        if (E(aVar) == j) {
            return this;
        }
        int[] iArr = x.a;
        int i = iArr[aVar.ordinal()];
        LocalDate localDate = this.a;
        if (i == 3 || i == 8 || i == 9) {
            w wVar = w.d;
            int a2 = wVar.s(aVar).a(j, aVar);
            int i2 = iArr[aVar.ordinal()];
            if (i2 == 3) {
                return X(localDate.o0(wVar.w(this.b, a2)));
            }
            if (i2 == 8) {
                return X(localDate.o0(wVar.w(z.s(a2), this.c)));
            }
            if (i2 == 9) {
                return X(localDate.o0(a2));
            }
        }
        return X(localDate.c(j, qVar));
    }

    /* renamed from: Y */
    public final y z(m mVar) {
        return (y) super.l(mVar);
    }

    public final m a() {
        return w.d;
    }

    public final boolean e(q qVar) {
        if (qVar == a.ALIGNED_DAY_OF_WEEK_IN_MONTH || qVar == a.ALIGNED_DAY_OF_WEEK_IN_YEAR || qVar == a.ALIGNED_WEEK_OF_MONTH || qVar == a.ALIGNED_WEEK_OF_YEAR) {
            return false;
        }
        return qVar instanceof a ? qVar.isDateBased() : qVar != null && qVar.k(this);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof y) {
            return this.a.equals(((y) obj).a);
        }
        return false;
    }

    public final C0039c g(long j, ChronoUnit chronoUnit) {
        return (y) super.g(j, chronoUnit);
    }

    /* renamed from: g  reason: collision with other method in class */
    public final l m6g(long j, ChronoUnit chronoUnit) {
        return (y) super.g(j, chronoUnit);
    }

    public final int hashCode() {
        w.d.getClass();
        return this.a.hashCode() ^ -688086063;
    }

    public final u m(q qVar) {
        long j;
        int i;
        if (!(qVar instanceof a)) {
            return qVar.l(this);
        }
        if (e(qVar)) {
            a aVar = (a) qVar;
            int i2 = x.a[aVar.ordinal()];
            if (i2 == 1) {
                i = this.a.Z();
            } else if (i2 == 2) {
                i = M();
            } else if (i2 != 3) {
                return w.d.s(aVar);
            } else {
                int X = this.b.n().X();
                z o = this.b.o();
                j = (long) (o != null ? (o.n().X() - X) + 1 : 999999999 - X);
                return u.j(1, j);
            }
            j = (long) i;
            return u.j(1, j);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final C0039c x(j$.time.q qVar) {
        return (y) super.x(qVar);
    }
}
