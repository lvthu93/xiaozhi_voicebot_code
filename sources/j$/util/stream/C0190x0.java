package j$.util.stream;

import j$.lang.a;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.x0  reason: case insensitive filesystem */
final class C0190x0 extends C0199z0 implements C0177u2 {
    C0190x0(A0 a0) {
        super(a0);
    }

    public final void accept(long j) {
        if (!this.a) {
            throw null;
        }
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        l((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }
}
