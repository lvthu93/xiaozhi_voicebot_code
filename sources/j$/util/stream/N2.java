package j$.util.stream;

import j$.util.Objects;
import j$.util.U;
import java.util.Arrays;
import java.util.function.IntFunction;

final class N2 extends D {
    N2(C0085c cVar) {
        super(cVar, C0129k3.q | C0129k3.o);
    }

    public final M0 T0(U u, C0085c cVar, IntFunction intFunction) {
        if (C0129k3.SORTED.p(cVar.s0())) {
            return cVar.K0(u, false, intFunction);
        }
        double[] dArr = (double[]) ((I0) cVar.K0(u, true, intFunction)).b();
        Arrays.sort(dArr);
        return new C0077a1(dArr);
    }

    public final C0182v2 W0(int i, C0182v2 v2Var) {
        Objects.requireNonNull(v2Var);
        return C0129k3.SORTED.p(i) ? v2Var : C0129k3.SIZED.p(i) ? new S2(v2Var) : new K2(v2Var);
    }
}
