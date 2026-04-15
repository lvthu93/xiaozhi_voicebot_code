package j$.util.stream;

import j$.util.U;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.x2  reason: case insensitive filesystem */
final class C0192x2 extends C0133l2 {
    final /* synthetic */ long s;
    final /* synthetic */ long t;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0192x2(C0085c cVar, int i, long j, long j2) {
        super(cVar, i);
        this.s = j;
        this.t = j2;
    }

    static U c1(U u, long j, long j2, long j3) {
        long j4;
        long j5;
        if (j <= j3) {
            long j6 = j3 - j;
            j4 = j2 >= 0 ? Math.min(j2, j6) : j6;
            j5 = 0;
        } else {
            j5 = j;
            j4 = j2;
        }
        return new N3(u, j5, j4);
    }

    /* access modifiers changed from: package-private */
    public final M0 T0(U u, C0085c cVar, IntFunction intFunction) {
        U u2 = u;
        C0085c cVar2 = cVar;
        IntFunction intFunction2 = intFunction;
        long k0 = cVar2.k0(u2);
        if (k0 > 0 && u2.hasCharacteristics(16384)) {
            return D0.a0(cVar2, D0.Y(cVar.Q0(), u, this.s, this.t), true, intFunction2);
        } else if (!C0129k3.ORDERED.p(cVar.s0())) {
            return D0.a0(this, c1(cVar2.a1(u2), this.s, this.t, k0), true, intFunction2);
        } else {
            return (M0) new F2(this, cVar, u, intFunction, this.s, this.t).invoke();
        }
    }

    /* access modifiers changed from: package-private */
    public final U U0(C0085c cVar, U u) {
        long k0 = cVar.k0(u);
        if (k0 > 0 && u.hasCharacteristics(16384)) {
            U a1 = cVar.a1(u);
            long j = this.s;
            return new H3(a1, j, D0.X(j, this.t));
        } else if (!C0129k3.ORDERED.p(cVar.s0())) {
            return c1(cVar.a1(u), this.s, this.t, k0);
        } else {
            return ((M0) new F2(this, cVar, u, new C0080b(28), this.s, this.t).invoke()).spliterator();
        }
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        return new C0187w2(this, v2Var);
    }
}
