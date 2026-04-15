package j$.util.stream;

import j$.util.U;
import java.util.function.Supplier;

final class B0 implements U3 {
    final A0 a;
    final Supplier b;

    B0(C0134l3 l3Var, A0 a0, C0150p pVar) {
        this.a = a0;
        this.b = pVar;
    }

    public final Object k(D0 d0, U u) {
        return (Boolean) new C0(this, d0, u).invoke();
    }

    public final int o() {
        return C0129k3.u | C0129k3.r;
    }

    public final Object y(D0 d0, U u) {
        return Boolean.valueOf(((C0199z0) d0.I0(u, (C0199z0) this.b.get())).b);
    }
}
