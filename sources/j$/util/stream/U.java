package j$.util.stream;

import j$.lang.a;
import java.util.function.LongConsumer;

final class U extends W implements C0177u2 {
    final LongConsumer b;

    U(LongConsumer longConsumer, boolean z) {
        super(z);
        this.b = longConsumer;
    }

    public final void accept(long j) {
        this.b.accept(j);
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        l((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    public final /* bridge */ /* synthetic */ Object k(D0 d0, j$.util.U u) {
        a(d0, u);
        return null;
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }

    public final Object y(D0 d0, j$.util.U u) {
        d0.I0(u, this);
        return null;
    }
}
