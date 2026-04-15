package j$.util.stream;

import j$.lang.a;
import j$.util.C0073q;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;

final class X1 implements Y1, C0177u2 {
    private boolean a;
    private long b;
    final /* synthetic */ LongBinaryOperator c;

    X1(LongBinaryOperator longBinaryOperator) {
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
        if (this.a) {
            this.a = false;
        } else {
            j = ((L) this.c).f(this.b, j);
        }
        this.b = j;
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
        this.a = true;
        this.b = 0;
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final Object get() {
        return this.a ? C0073q.a() : C0073q.d(this.b);
    }

    public final void h(Y1 y1) {
        X1 x1 = (X1) y1;
        if (!x1.a) {
            accept(x1.b);
        }
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }
}
