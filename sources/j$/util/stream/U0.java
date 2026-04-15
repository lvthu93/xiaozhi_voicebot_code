package j$.util.stream;

import j$.util.U;
import java.util.concurrent.CountedCompleter;
import java.util.function.BinaryOperator;
import java.util.function.LongFunction;

class U0 extends C0100f {
    protected final D0 h;
    protected final LongFunction i;
    protected final BinaryOperator j;

    U0(D0 d0, U u, LongFunction longFunction, R0 r0) {
        super(d0, u);
        this.h = d0;
        this.i = longFunction;
        this.j = r0;
    }

    U0(U0 u0, U u) {
        super((C0100f) u0, u);
        this.h = u0.h;
        this.i = u0.i;
        this.j = u0.j;
    }

    /* access modifiers changed from: protected */
    public C0100f e(U u) {
        return new U0(this, u);
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public final M0 a() {
        H0 h0 = (H0) this.i.apply(this.h.k0(this.b));
        this.h.I0(this.b, h0);
        return h0.build();
    }

    public final void onCompletion(CountedCompleter countedCompleter) {
        C0100f fVar = this.d;
        if (!(fVar == null)) {
            f((M0) this.j.apply((M0) ((U0) fVar).c(), (M0) ((U0) this.e).c()));
        }
        super.onCompletion(countedCompleter);
    }
}
