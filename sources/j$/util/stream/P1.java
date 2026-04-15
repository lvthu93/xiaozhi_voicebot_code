package j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

final class P1 extends D0 {
    final /* synthetic */ BinaryOperator h;
    final /* synthetic */ BiConsumer i;
    final /* synthetic */ Supplier j;
    final /* synthetic */ Collector k;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    P1(C0134l3 l3Var, BinaryOperator binaryOperator, BiConsumer biConsumer, Supplier supplier, Collector collector) {
        super(l3Var);
        this.h = binaryOperator;
        this.i = biConsumer;
        this.j = supplier;
        this.k = collector;
    }

    public final Y1 G0() {
        return new Q1(this.j, this.i, this.h);
    }

    public final int o() {
        if (this.k.characteristics().contains(C0125k.UNORDERED)) {
            return C0129k3.r;
        }
        return 0;
    }
}
