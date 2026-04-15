package j$.util.stream;

import java.util.function.Function;

/* renamed from: j$.util.stream.i2  reason: case insensitive filesystem */
final class C0118i2 extends C0138m2 {
    public final /* synthetic */ int s;
    final /* synthetic */ Function t;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0118i2(C0085c cVar, int i, Function function, int i2) {
        super(cVar, i);
        this.s = i2;
        this.t = function;
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        switch (this.s) {
            case 0:
                return new C0108g2(this, v2Var, 2);
            default:
                return new C0123j2(this, v2Var);
        }
    }
}
