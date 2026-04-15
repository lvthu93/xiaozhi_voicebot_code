package j$.util.stream;

import j$.lang.a;
import j$.util.U;
import java.util.function.IntConsumer;

final class T extends W implements C0172t2 {
    final IntConsumer b;

    T(IntConsumer intConsumer, boolean z) {
        super(z);
        this.b = intConsumer;
    }

    public final void accept(int i) {
        this.b.accept(i);
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        d((Integer) obj);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    public final /* synthetic */ void d(Integer num) {
        D0.C(this, num);
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
