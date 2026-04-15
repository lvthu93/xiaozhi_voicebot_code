package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class Q1 extends Z1 implements Y1 {
    final /* synthetic */ Supplier b;
    final /* synthetic */ BiConsumer c;
    final /* synthetic */ BinaryOperator d;

    Q1(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator) {
        this.b = supplier;
        this.c = biConsumer;
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
        this.c.accept(this.a, obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final void c(long j) {
        this.a = this.b.get();
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final void h(Y1 y1) {
        this.a = this.d.apply(this.a, ((Q1) y1).a);
    }
}
