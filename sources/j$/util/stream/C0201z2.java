package j$.util.stream;

import j$.util.K;
import j$.util.U;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.z2  reason: case insensitive filesystem */
final class C0201z2 extends C0101f0 {
    final /* synthetic */ long s;
    final /* synthetic */ long t;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0201z2(C0085c cVar, int i, long j, long j2) {
        super(cVar, i);
        this.s = j;
        this.t = j2;
    }

    static K e1(K k, long j, long j2, long j3) {
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
        return new K3(k, j5, j4);
    }

    /* access modifiers changed from: package-private */
    public final M0 T0(U u, C0085c cVar, IntFunction intFunction) {
        U u2 = u;
        C0085c cVar2 = cVar;
        long k0 = cVar.k0(u);
        if (k0 > 0 && u.hasCharacteristics(16384)) {
            return D0.c0(cVar, D0.Y(cVar.Q0(), u, this.s, this.t), true);
        } else if (!C0129k3.ORDERED.p(cVar.s0())) {
            return D0.c0(this, e1((K) cVar.a1(u), this.s, this.t, k0), true);
        } else {
            return (M0) new F2(this, cVar, u, intFunction, this.s, this.t).invoke();
        }
    }

    /* access modifiers changed from: package-private */
    public final U U0(C0085c cVar, U u) {
        long k0 = cVar.k0(u);
        if (k0 > 0 && u.hasCharacteristics(16384)) {
            long j = this.s;
            return new D3((K) cVar.a1(u), j, D0.X(j, this.t));
        } else if (!C0129k3.ORDERED.p(cVar.s0())) {
            return e1((K) cVar.a1(u), this.s, this.t, k0);
        } else {
            return ((M0) new F2(this, cVar, u, new R0(7), this.s, this.t).invoke()).spliterator();
        }
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        return new C0197y2(this, v2Var);
    }
}
