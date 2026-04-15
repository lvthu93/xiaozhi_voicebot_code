package j$.util.stream;

import java.util.function.LongBinaryOperator;

final class E1 extends D0 {
    final /* synthetic */ LongBinaryOperator h;
    final /* synthetic */ long i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    E1(C0134l3 l3Var, LongBinaryOperator longBinaryOperator, long j) {
        super(l3Var);
        this.h = longBinaryOperator;
        this.i = j;
    }

    public final Y1 G0() {
        return new W1(this.i, this.h);
    }
}
