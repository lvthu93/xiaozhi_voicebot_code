package j$.util.stream;

import j$.util.U;
import java.util.function.IntFunction;

final class S0 extends U0 {
    public static final /* synthetic */ int l = 0;
    public final /* synthetic */ int k;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public S0(int i, U u, D0 d0) {
        super(d0, u, new C0080b(29), new R0(0));
        this.k = i;
        if (i == 1) {
            super(d0, u, new R0(1), new R0(2));
        } else if (i != 2) {
        } else {
            super(d0, u, new R0(3), new R0(4));
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public S0(U u, D0 d0, IntFunction intFunction) {
        super(d0, u, new T0(intFunction), new R0(5));
        this.k = 3;
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ Object a() {
        switch (this.k) {
            case 0:
                return a();
            case 1:
                return a();
            case 2:
                return a();
            default:
                return a();
        }
    }

    /* access modifiers changed from: protected */
    public final C0100f e(U u) {
        switch (this.k) {
            case 0:
                return new U0(this, u);
            case 1:
                return new U0(this, u);
            case 2:
                return new U0(this, u);
            default:
                return new U0(this, u);
        }
    }
}
