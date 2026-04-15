package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;

final class H1 implements Y1, C0167s2 {
    private double a;
    final /* synthetic */ double b;
    final /* synthetic */ DoubleBinaryOperator c;

    H1(double d, DoubleBinaryOperator doubleBinaryOperator) {
        this.b = d;
        this.c = doubleBinaryOperator;
    }

    public final void accept(double d) {
        this.a = ((R0) this.c).b(this.a, d);
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        m((Double) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public final void c(long j) {
        this.a = this.b;
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final Object get() {
        return Double.valueOf(this.a);
    }

    public final void h(Y1 y1) {
        accept(((H1) y1).a);
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }
}
