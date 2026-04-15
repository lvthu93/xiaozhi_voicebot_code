package j$.util.stream;

/* renamed from: j$.util.stream.w  reason: case insensitive filesystem */
final class C0184w extends C0138m2 {
    public final /* synthetic */ int s;
    final /* synthetic */ Object t;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0184w(C0085c cVar, int i, Object obj, int i2) {
        super(cVar, i);
        this.s = i2;
        this.t = obj;
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        switch (this.s) {
            case 0:
                return new C0179v(this, v2Var, 0);
            case 1:
                return new C0076a0(this, v2Var, 0);
            case 2:
                return new C0136m0(this, v2Var, 0);
            case 3:
                return new C0108g2(this, v2Var, 0);
            default:
                return new C0108g2(this, v2Var, 1);
        }
    }
}
