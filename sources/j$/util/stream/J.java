package j$.util.stream;

import j$.util.U;
import java.util.function.Predicate;
import java.util.function.Supplier;

final class J implements U3 {
    final int a;
    final Object b;
    final Predicate c;
    final Supplier d;

    J(boolean z, C0134l3 l3Var, Object obj, Predicate predicate, C0080b bVar) {
        this.a = (z ? 0 : C0129k3.r) | C0129k3.u;
        this.b = obj;
        this.c = predicate;
        this.d = bVar;
    }

    public final Object k(D0 d0, U u) {
        return new Q(this, C0129k3.ORDERED.p(d0.s0()), d0, u).invoke();
    }

    public final int o() {
        return this.a;
    }

    public final Object y(D0 d0, U u) {
        V3 v3 = (V3) this.d.get();
        d0.I0(u, v3);
        Object obj = v3.get();
        return obj != null ? obj : this.b;
    }
}
