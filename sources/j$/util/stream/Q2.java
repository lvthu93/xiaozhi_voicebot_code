package j$.util.stream;

import j$.util.Comparator$CC;
import j$.util.Objects;
import j$.util.U;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;

final class Q2 extends C0133l2 {
    private final boolean s;
    private final Comparator t;

    Q2(C0085c cVar) {
        super(cVar, C0129k3.q | C0129k3.o);
        this.s = true;
        this.t = Comparator$CC.a();
    }

    Q2(C0085c cVar, Comparator comparator) {
        super(cVar, C0129k3.q | C0129k3.p);
        this.s = false;
        this.t = (Comparator) Objects.requireNonNull(comparator);
    }

    public final M0 T0(U u, C0085c cVar, IntFunction intFunction) {
        if (C0129k3.SORTED.p(cVar.s0()) && this.s) {
            return cVar.K0(u, false, intFunction);
        }
        Object[] k = cVar.K0(u, true, intFunction).k(intFunction);
        Arrays.sort(k, this.t);
        return new P0(k);
    }

    public final C0182v2 W0(int i, C0182v2 v2Var) {
        Objects.requireNonNull(v2Var);
        if (C0129k3.SORTED.p(i) && this.s) {
            return v2Var;
        }
        boolean p = C0129k3.SIZED.p(i);
        Comparator comparator = this.t;
        return p ? new V2(v2Var, comparator) : new R2(v2Var, comparator);
    }
}
