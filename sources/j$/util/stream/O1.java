package j$.util.stream;

import j$.util.Optional;
import j$.util.function.Consumer$CC;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

final class O1 implements Y1 {
    private boolean a;
    private Object b;
    final /* synthetic */ BinaryOperator c;

    O1(BinaryOperator binaryOperator) {
        this.c = binaryOperator;
    }

    public final /* synthetic */ void accept(double d) {
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
        if (this.a) {
            this.a = false;
        } else {
            obj = this.c.apply(this.b, obj);
        }
        this.b = obj;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final void c(long j) {
        this.a = true;
        this.b = null;
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final Object get() {
        return this.a ? Optional.empty() : Optional.of(this.b);
    }

    public final void h(Y1 y1) {
        O1 o1 = (O1) y1;
        if (!o1.a) {
            accept(o1.b);
        }
    }
}
