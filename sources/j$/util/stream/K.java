package j$.util.stream;

import j$.lang.a;
import j$.util.C0071o;
import java.util.function.DoubleConsumer;

final class K extends P implements C0167s2 {
    static final J c;
    static final J d;

    static {
        C0134l3 l3Var = C0134l3.DOUBLE_VALUE;
        c = new J(true, l3Var, C0071o.a(), new R0(29), new C0080b(13));
        d = new J(false, l3Var, C0071o.a(), new R0(29), new C0080b(13));
    }

    K() {
    }

    public final void accept(double d2) {
        m((Object) Double.valueOf(d2));
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public final Object get() {
        if (this.a) {
            return C0071o.d(((Double) this.b).doubleValue());
        }
        return null;
    }
}
