package j$.util.stream;

import j$.lang.a;
import j$.util.C0072p;
import java.util.function.IntConsumer;

final class M extends P implements C0172t2 {
    static final J c;
    static final J d;

    static {
        C0134l3 l3Var = C0134l3.INT_VALUE;
        c = new J(true, l3Var, C0072p.a(), new L(0), new C0080b(14));
        d = new J(false, l3Var, C0072p.a(), new L(0), new C0080b(14));
    }

    M() {
    }

    public final void accept(int i) {
        m((Object) Integer.valueOf(i));
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    public final Object get() {
        if (this.a) {
            return C0072p.d(((Integer) this.b).intValue());
        }
        return null;
    }
}
