package j$.util.stream;

import j$.lang.a;
import j$.util.C0072p;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;

final class U1 implements Y1, C0172t2 {
    private boolean a;
    private int b;
    final /* synthetic */ IntBinaryOperator c;

    U1(IntBinaryOperator intBinaryOperator) {
        this.c = intBinaryOperator;
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final void accept(int i) {
        if (this.a) {
            this.a = false;
        } else {
            i = ((L) this.c).b(this.b, i);
        }
        this.b = i;
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
        this.a = true;
        this.b = 0;
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
        return this.a ? C0072p.a() : C0072p.d(this.b);
    }

    public final void h(Y1 y1) {
        U1 u1 = (U1) y1;
        if (!u1.a) {
            accept(u1.b);
        }
    }
}
