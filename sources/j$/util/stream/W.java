package j$.util.stream;

import j$.util.U;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

abstract class W implements U3, V3 {
    private final boolean a;

    protected W(boolean z) {
        this.a = z;
    }

    public final void a(D0 d0, U u) {
        (this.a ? new X(d0, u, (C0182v2) this) : new Y(d0, u, d0.J0(this))).invoke();
    }

    public /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final /* synthetic */ void c(long j) {
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }

    public final int o() {
        if (this.a) {
            return 0;
        }
        return C0129k3.r;
    }
}
