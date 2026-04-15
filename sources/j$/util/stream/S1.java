package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;

final class S1 implements Y1, C0172t2 {
    private int a;
    final /* synthetic */ int b;
    final /* synthetic */ IntBinaryOperator c;

    S1(int i, IntBinaryOperator intBinaryOperator) {
        this.b = i;
        this.c = intBinaryOperator;
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final void accept(int i) {
        this.a = ((L) this.c).b(this.a, i);
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        d((Integer) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    public final void c(long j) {
        this.a = this.b;
    }

    public final /* synthetic */ void d(Integer num) {
        D0.C(this, num);
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final Object get() {
        return Integer.valueOf(this.a);
    }

    public final void h(Y1 y1) {
        accept(((S1) y1).a);
    }
}
