package j$.util.stream;

import j$.util.C0065i;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.stream.IntStream;

public final /* synthetic */ class T0 implements LongFunction, IntFunction {
    public IntFunction a;

    public /* synthetic */ T0(IntFunction intFunction) {
        this.a = intFunction;
    }

    public final Object apply(int i) {
        Object apply = this.a.apply(i);
        if (apply == null) {
            return null;
        }
        if (apply instanceof C0126k0) {
            return C0121j0.k((C0126k0) apply);
        }
        if (apply instanceof IntStream) {
            return C0116i0.k((IntStream) apply);
        }
        C0065i.a(apply.getClass(), "java.util.stream.IntStream");
        throw null;
    }

    public final Object apply(long j) {
        IntFunction intFunction = this.a;
        int i = S0.l;
        return D0.Z(j, intFunction);
    }
}
