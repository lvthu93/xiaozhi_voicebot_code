package j$.util.stream;

import j$.lang.a;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.y0  reason: case insensitive filesystem */
final class C0195y0 extends C0199z0 implements C0167s2 {
    C0195y0(A0 a0) {
        super(a0);
    }

    public final void accept(double d) {
        if (!this.a) {
            throw null;
        }
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        m((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }
}
