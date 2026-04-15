package j$.util.stream;

import j$.lang.a;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.a2  reason: case insensitive filesystem */
final class C0078a2 extends C0098e2 implements C0167s2 {
    C0078a2() {
    }

    public final void accept(double d) {
        this.b++;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        m((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public final Object get() {
        return Long.valueOf(this.b);
    }

    public final void h(Y1 y1) {
        this.b += ((C0098e2) y1).b;
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }
}
