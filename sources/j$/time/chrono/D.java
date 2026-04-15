package j$.time.chrono;

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
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

public final class D extends C0041e {
    private static final long serialVersionUID = 1300372329181994526L;
    private final transient LocalDate a;

    D(LocalDate localDate) {
        Objects.requireNonNull(localDate, "isoDate");
        this.a = localDate;
    }

    private int U() {
        return this.a.X() - 1911;
    }

    private D W(LocalDate localDate) {
        return localDate.equals(this.a) ? this : new D(localDate);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new F((byte) 7, this);
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = C.a[((a) qVar).ordinal()];
        int i2 = 1;
        if (i != 4) {
            LocalDate localDate = this.a;
            if (i == 5) {
                return ((((long) U()) * 12) + ((long) localDate.W())) - 1;
            }
            if (i == 6) {
                return (long) U();
            }
            if (i != 7) {
                return localDate.E(qVar);
            }
            if (U() < 1) {
                i2 = 0;
            }
            return (long) i2;
        }
        int U = U();
        if (U < 1) {
            U = 1 - U;
        }
        return (long) U;
    }

    public final long F() {
        return this.a.F();
    }

    public final C0042f G(j jVar) {
        return C0044h.S(this, jVar);
    }

    public final n I() {
        return U() >= 1 ? E.ROC : E.BEFORE_ROC;
    }

    /* access modifiers changed from: package-private */
    public final C0039c R(long j) {
        return W(this.a.g0(j));
    }

    /* access modifiers changed from: package-private */
    public final C0039c S(long j) {
        return W(this.a.h0(j));
    }

    /* access modifiers changed from: package-private */
    public final C0039c T(long j) {
        return W(this.a.j0(j));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        if (r2 != 7) goto L_0x0063;
     */
    /* renamed from: V */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final j$.time.chrono.D c(long r9, j$.time.temporal.q r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof j$.time.temporal.a
            if (r0 == 0) goto L_0x009c
            r0 = r11
            j$.time.temporal.a r0 = (j$.time.temporal.a) r0
            long r1 = r8.E(r0)
            int r3 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r3 != 0) goto L_0x0010
            return r8
        L_0x0010:
            int[] r1 = j$.time.chrono.C.a
            int r2 = r0.ordinal()
            r2 = r1[r2]
            j$.time.LocalDate r3 = r8.a
            r4 = 7
            r5 = 6
            r6 = 4
            if (r2 == r6) goto L_0x004c
            r7 = 5
            if (r2 == r7) goto L_0x0027
            if (r2 == r5) goto L_0x004c
            if (r2 == r4) goto L_0x004c
            goto L_0x0063
        L_0x0027:
            j$.time.chrono.B r11 = j$.time.chrono.B.d
            j$.time.temporal.u r11 = r11.s(r0)
            r11.b(r9, r0)
            int r11 = r8.U()
            long r0 = (long) r11
            r4 = 12
            long r0 = r0 * r4
            int r11 = r3.W()
            long r4 = (long) r11
            long r0 = r0 + r4
            r4 = 1
            long r0 = r0 - r4
            long r9 = r9 - r0
            j$.time.LocalDate r9 = r3.h0(r9)
            j$.time.chrono.D r9 = r8.W(r9)
            return r9
        L_0x004c:
            j$.time.chrono.B r2 = j$.time.chrono.B.d
            j$.time.temporal.u r2 = r2.s(r0)
            int r2 = r2.a(r9, r0)
            int r0 = r0.ordinal()
            r0 = r1[r0]
            r1 = 1
            if (r0 == r6) goto L_0x0087
            if (r0 == r5) goto L_0x007c
            if (r0 == r4) goto L_0x006c
        L_0x0063:
            j$.time.LocalDate r9 = r3.c(r9, r11)
            j$.time.chrono.D r9 = r8.W(r9)
            return r9
        L_0x006c:
            int r9 = r8.U()
            int r1 = r1 - r9
            int r1 = r1 + 1911
            j$.time.LocalDate r9 = r3.o0(r1)
            j$.time.chrono.D r9 = r8.W(r9)
            return r9
        L_0x007c:
            int r2 = r2 + 1911
            j$.time.LocalDate r9 = r3.o0(r2)
            j$.time.chrono.D r9 = r8.W(r9)
            return r9
        L_0x0087:
            int r9 = r8.U()
            if (r9 < r1) goto L_0x0090
            int r2 = r2 + 1911
            goto L_0x0093
        L_0x0090:
            int r1 = r1 - r2
            int r2 = r1 + 1911
        L_0x0093:
            j$.time.LocalDate r9 = r3.o0(r2)
            j$.time.chrono.D r9 = r8.W(r9)
            return r9
        L_0x009c:
            j$.time.chrono.c r9 = super.c((long) r9, (j$.time.temporal.q) r11)
            j$.time.chrono.D r9 = (j$.time.chrono.D) r9
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.D.c(long, j$.time.temporal.q):j$.time.chrono.D");
    }

    public final m a() {
        return B.d;
    }

    public final C0039c d(long j, TemporalUnit temporalUnit) {
        return (D) super.d(j, temporalUnit);
    }

    /* renamed from: d  reason: collision with other method in class */
    public final l m0d(long j, TemporalUnit temporalUnit) {
        return (D) super.d(j, temporalUnit);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof D) {
            return this.a.equals(((D) obj).a);
        }
        return false;
    }

    public final C0039c g(long j, ChronoUnit chronoUnit) {
        return (D) super.g(j, chronoUnit);
    }

    /* renamed from: g  reason: collision with other method in class */
    public final l m1g(long j, ChronoUnit chronoUnit) {
        return (D) super.g(j, chronoUnit);
    }

    public final int hashCode() {
        B.d.getClass();
        return this.a.hashCode() ^ -1990173233;
    }

    public final l l(LocalDate localDate) {
        return (D) super.l(localDate);
    }

    public final u m(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.l(this);
        }
        if (C0038b.j(this, qVar)) {
            a aVar = (a) qVar;
            int i = C.a[aVar.ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                return this.a.m(qVar);
            }
            if (i != 4) {
                return B.d.s(aVar);
            }
            u m = a.YEAR.m();
            return u.j(1, U() <= 0 ? (-m.e()) + 1 + 1911 : m.d() - 1911);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final C0039c x(j$.time.q qVar) {
        return (D) super.x(qVar);
    }

    public final C0039c z(m mVar) {
        return (D) super.l(mVar);
    }
}
