package j$.util.stream;

import j$.lang.a;
import j$.util.U;
import java.util.function.DoubleConsumer;

final class S extends W implements C0167s2 {
    final DoubleConsumer b;

    S(DoubleConsumer doubleConsumer, boolean z) {
        super(z);
        this.b = doubleConsumer;
    }

    public final void accept(double d) {
        this.b.accept(d);
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        m((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    public final /* bridge */ /* synthetic */ Object k(D0 d0, U u) {
        a(d0, u);
        return null;
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }

    public final Object y(D0 d0, U u) {
        d0.I0(u, this);
        return null;
    }
}
