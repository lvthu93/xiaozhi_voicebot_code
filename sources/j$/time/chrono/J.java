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

public final class J extends C0041e {
    private static final long serialVersionUID = -8722293800195731463L;
    private final transient LocalDate a;

    J(LocalDate localDate) {
        Objects.requireNonNull(localDate, "isoDate");
        this.a = localDate;
    }

    private int U() {
        return this.a.X() + 543;
    }

    private J W(LocalDate localDate) {
        return localDate.equals(this.a) ? this : new J(localDate);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new F((byte) 8, this);
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = I.a[((a) qVar).ordinal()];
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
        return U() >= 1 ? K.BE : K.BEFORE_BE;
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
    public final j$.time.chrono.J c(long r9, j$.time.temporal.q r11) {
        /*
            r8 = this;
            boolean r0 = r11 instanceof j$.time.temporal.a
            if (r0 == 0) goto L_0x009b
            r0 = r11
            j$.time.temporal.a r0 = (j$.time.temporal.a) r0
            long r1 = r8.E(r0)
            int r3 = (r1 > r9 ? 1 : (r1 == r9 ? 0 : -1))
            if (r3 != 0) goto L_0x0010
            return r8
        L_0x0010:
            int[] r1 = j$.time.chrono.I.a
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
            j$.time.chrono.H r11 = j$.time.chrono.H.d
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
            j$.time.chrono.J r9 = r8.W(r9)
            return r9
        L_0x004c:
            j$.time.chrono.H r2 = j$.time.chrono.H.d
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
            j$.time.chrono.J r9 = r8.W(r9)
            return r9
        L_0x006c:
            int r9 = r8.U()
            int r1 = r1 - r9
            int r1 = r1 + -543
            j$.time.LocalDate r9 = r3.o0(r1)
            j$.time.chrono.J r9 = r8.W(r9)
            return r9
        L_0x007c:
            int r2 = r2 + -543
            j$.time.LocalDate r9 = r3.o0(r2)
            j$.time.chrono.J r9 = r8.W(r9)
            return r9
        L_0x0087:
            int r9 = r8.U()
            if (r9 < r1) goto L_0x008e
            goto L_0x0090
        L_0x008e:
            int r2 = 1 - r2
        L_0x0090:
            int r2 = r2 + -543
            j$.time.LocalDate r9 = r3.o0(r2)
            j$.time.chrono.J r9 = r8.W(r9)
            return r9
        L_0x009b:
            j$.time.chrono.c r9 = super.c((long) r9, (j$.time.temporal.q) r11)
            j$.time.chrono.J r9 = (j$.time.chrono.J) r9
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.J.c(long, j$.time.temporal.q):j$.time.chrono.J");
    }

    public final m a() {
        return H.d;
    }

    public final C0039c d(long j, TemporalUnit temporalUnit) {
        return (J) super.d(j, temporalUnit);
    }

    /* renamed from: d  reason: collision with other method in class */
    public final l m2d(long j, TemporalUnit temporalUnit) {
        return (J) super.d(j, temporalUnit);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof J) {
            return this.a.equals(((J) obj).a);
        }
        return false;
    }

    public final C0039c g(long j, ChronoUnit chronoUnit) {
        return (J) super.g(j, chronoUnit);
    }

    /* renamed from: g  reason: collision with other method in class */
    public final l m3g(long j, ChronoUnit chronoUnit) {
        return (J) super.g(j, chronoUnit);
    }

    public final int hashCode() {
        H.d.getClass();
        return this.a.hashCode() ^ 146118545;
    }

    public final l l(LocalDate localDate) {
        return (J) super.l(localDate);
    }

    public final u m(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.l(this);
        }
        if (C0038b.j(this, qVar)) {
            a aVar = (a) qVar;
            int i = I.a[aVar.ordinal()];
            if (i == 1 || i == 2 || i == 3) {
                return this.a.m(qVar);
            }
            if (i != 4) {
                return H.d.s(aVar);
            }
            u m = a.YEAR.m();
            return u.j(1, U() <= 0 ? (-(m.e() + 543)) + 1 : 543 + m.d());
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final C0039c x(j$.time.q qVar) {
        return (J) super.x(qVar);
    }

    public final C0039c z(m mVar) {
        return (J) super.l(mVar);
    }
}
