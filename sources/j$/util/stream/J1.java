package j$.util.stream;

import j$.lang.a;
import j$.util.C0071o;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;

final class J1 implements Y1, C0167s2 {
    private boolean a;
    private double b;
    final /* synthetic */ DoubleBinaryOperator c;

    J1(DoubleBinaryOperator doubleBinaryOperator) {
        this.c = doubleBinaryOperator;
    }

    public final void accept(double d) {
        if (this.a) {
            this.a = false;
        } else {
            d = ((R0) this.c).b(this.b, d);
        }
        this.b = d;
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
        this.a = true;
        this.b = 0.0d;
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final Object get() {
        return this.a ? C0071o.a() : C0071o.d(this.b);
    }

    public final void h(Y1 y1) {
        J1 j1 = (J1) y1;
        if (!j1.a) {
            accept(j1.b);
        }
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }
}
