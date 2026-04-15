package j$.util.stream;

import j$.lang.a;
import j$.util.C0073q;
import java.util.function.LongConsumer;

final class N extends P implements C0177u2 {
    static final J c;
    static final J d;

    static {
        C0134l3 l3Var = C0134l3.LONG_VALUE;
        c = new J(true, l3Var, C0073q.a(), new L(1), new C0080b(15));
        d = new J(false, l3Var, C0073q.a(), new L(1), new C0080b(15));
    }

    N() {
    }

    public final void accept(long j) {
        m((Object) Long.valueOf(j));
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    public final Object get() {
        if (this.a) {
            return C0073q.d(((Long) this.b).longValue());
        }
        return null;
    }
}
