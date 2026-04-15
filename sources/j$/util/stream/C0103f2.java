package j$.util.stream;

import j$.util.U;
import java.util.concurrent.CountedCompleter;

/* renamed from: j$.util.stream.f2  reason: case insensitive filesystem */
final class C0103f2 extends C0100f {
    private final D0 h;

    C0103f2(D0 d0, D0 d02, U u) {
        super(d02, u);
        this.h = d0;
    }

    C0103f2(C0103f2 f2Var, U u) {
        super((C0100f) f2Var, u);
        this.h = f2Var.h;
    }

    /* access modifiers changed from: protected */
    public final Object a() {
        D0 d0 = this.a;
        Y1 G0 = this.h.G0();
        d0.I0(this.b, G0);
        return G0;
    }

    /* access modifiers changed from: protected */
    public final C0100f e(U u) {
        return new C0103f2(this, u);
    }

    public final void onCompletion(CountedCompleter countedCompleter) {
        C0100f fVar = this.d;
        if (!(fVar == null)) {
            Y1 y1 = (Y1) ((C0103f2) fVar).c();
            y1.h((Y1) ((C0103f2) this.e).c());
            f(y1);
        }
        super.onCompletion(countedCompleter);
    }
}
