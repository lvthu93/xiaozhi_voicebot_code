package j$.util.stream;

import j$.lang.a;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;

final class W1 implements Y1, C0177u2 {
    private long a;
    final /* synthetic */ long b;
    final /* synthetic */ LongBinaryOperator c;

    W1(long j, LongBinaryOperator longBinaryOperator) {
        this.b = j;
        this.c = longBinaryOperator;
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final void accept(long j) {
        this.a = ((L) this.c).f(this.a, j);
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        l((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
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
        return Long.valueOf(this.a);
    }

    public final void h(Y1 y1) {
        accept(((W1) y1).a);
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }
}
