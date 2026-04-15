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
import j$.time.temporal.p;
import j$.time.temporal.q;
import j$.time.temporal.t;
import j$.time.temporal.u;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;

public final class r extends C0041e {
    private static final long serialVersionUID = -5207853542612002020L;
    private final transient p a;
    private final transient int b;
    private final transient int c;
    private final transient int d;

    private r(p pVar, int i, int i2, int i3) {
        pVar.W(i, i2, i3);
        this.a = pVar;
        this.b = i;
        this.c = i2;
        this.d = i3;
    }

    private r(p pVar, long j) {
        int[] X = pVar.X((int) j);
        this.a = pVar;
        this.b = X[0];
        this.c = X[1];
        this.d = X[2];
    }

    private int U() {
        return this.a.V(this.b, this.c) + this.d;
    }

    static r V(p pVar, int i, int i2, int i3) {
        return new r(pVar, i, i2, i3);
    }

    static r W(p pVar, long j) {
        return new r(pVar, j);
    }

    private r Z(int i, int i2, int i3) {
        p pVar = this.a;
        int a0 = pVar.a0(i, i2);
        if (i3 > a0) {
            i3 = a0;
        }
        return new r(pVar, i, i2, i3);
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new F((byte) 6, this);
    }

    public final long E(q qVar) {
        if (!(qVar instanceof a)) {
            return qVar.E(this);
        }
        int i = q.a[((a) qVar).ordinal()];
        int i2 = this.c;
        int i3 = 1;
        int i4 = this.b;
        int i5 = this.d;
        switch (i) {
            case 1:
                return (long) i5;
            case 2:
                return (long) U();
            case 3:
                return (long) (((i5 - 1) / 7) + 1);
            case 4:
                return (long) (((int) j$.lang.a.k(F() + 3, (long) 7)) + 1);
            case 5:
                return (long) (((i5 - 1) % 7) + 1);
            case 6:
                return (long) (((U() - 1) % 7) + 1);
            case 7:
                return F();
            case 8:
                return (long) (((U() - 1) / 7) + 1);
            case 9:
                return (long) i2;
            case 10:
                return ((((long) i4) * 12) + ((long) i2)) - 1;
            case 11:
                return (long) i4;
            case 12:
                return (long) i4;
            case 13:
                if (i4 <= 1) {
                    i3 = 0;
                }
                return (long) i3;
            default:
                throw new t(c.a("Unsupported field: ", qVar));
        }
    }

    public final long F() {
        return this.a.W(this.b, this.c, this.d);
    }

    public final C0042f G(j jVar) {
        return C0044h.S(this, jVar);
    }

    public final n I() {
        return s.AH;
    }

    public final int M() {
        return this.a.b0(this.b);
    }

    /* access modifiers changed from: package-private */
    public final C0039c T(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = ((long) this.b) + ((long) ((int) j));
        int i = (int) j2;
        if (j2 == ((long) i)) {
            return Z(i, this.c, this.d);
        }
        throw new ArithmeticException();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: X */
    public final r R(long j) {
        return new r(this.a, F() + j);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: Y */
    public final r S(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (((long) this.b) * 12) + ((long) (this.c - 1)) + j;
        long i = j$.lang.a.i(j2, 12);
        p pVar = this.a;
        if (i >= ((long) pVar.Z()) && i <= ((long) pVar.Y())) {
            return Z((int) i, ((int) j$.lang.a.k(j2, 12)) + 1, this.d);
        }
        throw new DateTimeException("Invalid Hijrah year: " + i);
    }

    public final m a() {
        return this.a;
    }

    /* renamed from: a0 */
    public final r c(long j, q qVar) {
        if (!(qVar instanceof a)) {
            return (r) super.c(j, qVar);
        }
        a aVar = (a) qVar;
        p pVar = this.a;
        pVar.s(aVar).b(j, aVar);
        int i = (int) j;
        int i2 = q.a[aVar.ordinal()];
        int i3 = this.d;
        int i4 = this.c;
        int i5 = this.b;
        switch (i2) {
            case 1:
                return Z(i5, i4, i);
            case 2:
                return R((long) (Math.min(i, M()) - U()));
            case 3:
                return R((j - E(a.ALIGNED_WEEK_OF_MONTH)) * 7);
            case 4:
                return R(j - ((long) (((int) j$.lang.a.k(F() + 3, (long) 7)) + 1)));
            case 5:
                return R(j - E(a.ALIGNED_DAY_OF_WEEK_IN_MONTH));
            case 6:
                return R(j - E(a.ALIGNED_DAY_OF_WEEK_IN_YEAR));
            case 7:
                return new r(pVar, j);
            case 8:
                return R((j - E(a.ALIGNED_WEEK_OF_YEAR)) * 7);
            case 9:
                return Z(i5, i, i3);
            case 10:
                return S(j - (((((long) i5) * 12) + ((long) i4)) - 1));
            case 11:
                if (i5 < 1) {
                    i = 1 - i;
                }
                return Z(i, i4, i3);
            case 12:
                return Z(i, i4, i3);
            case 13:
                return Z(1 - i5, i4, i3);
            default:
                throw new t(c.a("Unsupported field: ", qVar));
        }
    }

    public final C0039c d(long j, TemporalUnit temporalUnit) {
        return (r) super.d(j, temporalUnit);
    }

    /* renamed from: d  reason: collision with other method in class */
    public final l m4d(long j, TemporalUnit temporalUnit) {
        return (r) super.d(j, temporalUnit);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (j$.time.chrono.r) r5;
        r1 = r5.b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof j$.time.chrono.r
            r2 = 0
            if (r1 == 0) goto L_0x002a
            j$.time.chrono.r r5 = (j$.time.chrono.r) r5
            int r1 = r5.b
            int r3 = r4.b
            if (r3 != r1) goto L_0x0028
            int r1 = r4.c
            int r3 = r5.c
            if (r1 != r3) goto L_0x0028
            int r1 = r4.d
            int r3 = r5.d
            if (r1 != r3) goto L_0x0028
            j$.time.chrono.p r1 = r4.a
            j$.time.chrono.p r5 = r5.a
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x0028
            goto L_0x0029
        L_0x0028:
            r0 = 0
        L_0x0029:
            return r0
        L_0x002a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.r.equals(java.lang.Object):boolean");
    }

    public final C0039c g(long j, ChronoUnit chronoUnit) {
        return (r) super.g(j, chronoUnit);
    }

    /* renamed from: g  reason: collision with other method in class */
    public final l m5g(long j, ChronoUnit chronoUnit) {
        return (r) super.g(j, chronoUnit);
    }

    public final int hashCode() {
        int hashCode = this.a.j().hashCode();
        int i = this.b;
        return (hashCode ^ (i & -2048)) ^ (((i << 11) + (this.c << 6)) + this.d);
    }

    public final l l(LocalDate localDate) {
        return (r) super.l(localDate);
    }

    public final u m(q qVar) {
        long j;
        int i;
        if (!(qVar instanceof a)) {
            return qVar.l(this);
        }
        if (C0038b.j(this, qVar)) {
            a aVar = (a) qVar;
            int i2 = q.a[aVar.ordinal()];
            p pVar = this.a;
            if (i2 == 1) {
                i = pVar.a0(this.b, this.c);
            } else if (i2 == 2) {
                i = M();
            } else if (i2 != 3) {
                return pVar.s(aVar);
            } else {
                j = 5;
                return u.j(1, j);
            }
            j = (long) i;
            return u.j(1, j);
        }
        throw new t(c.a("Unsupported field: ", qVar));
    }

    public final boolean r() {
        return this.a.O((long) this.b);
    }

    /* access modifiers changed from: package-private */
    public final void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeObject(this.a);
        objectOutput.writeInt(p.a(this, a.YEAR));
        objectOutput.writeByte(p.a(this, a.MONTH_OF_YEAR));
        objectOutput.writeByte(p.a(this, a.DAY_OF_MONTH));
    }

    public final C0039c x(j$.time.q qVar) {
        return (r) super.x(qVar);
    }

    public final C0039c z(m mVar) {
        return (r) super.l(mVar);
    }
}
