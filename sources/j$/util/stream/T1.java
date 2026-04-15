package j$.util.stream;

import java.util.function.IntBinaryOperator;

final class T1 extends D0 {
    final /* synthetic */ IntBinaryOperator h;
    final /* synthetic */ int i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    T1(C0134l3 l3Var, IntBinaryOperator intBinaryOperator, int i2) {
        super(l3Var);
        this.h = intBinaryOperator;
        this.i = i2;
    }

    public final Y1 G0() {
        return new S1(this.i, this.h);
    }
}
