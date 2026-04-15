package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

final class N1 extends Z1 implements Y1 {
    final /* synthetic */ Object b;
    final /* synthetic */ BiFunction c;
    final /* synthetic */ BinaryOperator d;

    N1(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        this.b = obj;
        this.c = biFunction;
        this.d = binaryOperator;
    }

    public final /* synthetic */ void accept(double d2) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final void accept(Object obj) {
        this.a = this.c.apply(this.a, obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final void c(long j) {
        this.a = this.b;
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final void h(Y1 y1) {
        this.a = this.d.apply(this.a, ((N1) y1).a);
    }
}
