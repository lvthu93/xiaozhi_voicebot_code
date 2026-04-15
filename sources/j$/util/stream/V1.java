package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

final class V1 extends Z1 implements Y1, C0172t2 {
    final /* synthetic */ Supplier b;
    final /* synthetic */ ObjIntConsumer c;
    final /* synthetic */ BinaryOperator d;

    V1(Supplier supplier, ObjIntConsumer objIntConsumer, BinaryOperator binaryOperator) {
        this.b = supplier;
        this.c = objIntConsumer;
        this.d = binaryOperator;
    }

    public final /* synthetic */ void accept(double d2) {
        D0.z();
        throw null;
    }

    public final void accept(int i) {
        this.c.accept(this.a, i);
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
        this.a = this.b.get();
    }

    public final /* synthetic */ void d(Integer num) {
        D0.C(this, num);
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final void h(Y1 y1) {
        this.a = this.d.apply(this.a, ((V1) y1).a);
    }
}
