package j$.util.stream;

import java.util.function.DoubleBinaryOperator;

final class M1 extends D0 {
    final /* synthetic */ DoubleBinaryOperator h;
    final /* synthetic */ double i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    M1(C0134l3 l3Var, DoubleBinaryOperator doubleBinaryOperator, double d) {
        super(l3Var);
        this.h = doubleBinaryOperator;
        this.i = d;
    }

    public final Y1 G0() {
        return new H1(this.i, this.h);
    }
}
