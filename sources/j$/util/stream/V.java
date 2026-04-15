package j$.util.stream;

import j$.util.U;
import java.util.function.Consumer;

final class V extends W {
    final Consumer b;

    V(Consumer consumer, boolean z) {
        super(z);
        this.b = consumer;
    }

    public final void accept(Object obj) {
        this.b.accept(obj);
    }

    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    public final /* bridge */ /* synthetic */ Object k(D0 d0, U u) {
        a(d0, u);
        return null;
    }

    public final Object y(D0 d0, U u) {
        d0.I0(u, this);
        return null;
    }
}
