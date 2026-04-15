package j$.util.stream;

import j$.lang.a;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.c2  reason: case insensitive filesystem */
final class C0088c2 extends C0098e2 implements C0177u2 {
    C0088c2() {
    }

    public final void accept(long j) {
        this.b++;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        l((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    public final Object get() {
        return Long.valueOf(this.b);
    }

    public final void h(Y1 y1) {
        this.b += ((C0098e2) y1).b;
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }
}
