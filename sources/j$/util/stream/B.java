package j$.util.stream;

final class B extends E {
    public final /* synthetic */ int s;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ B(C0085c cVar, int i, int i2) {
        super(cVar, i);
        this.s = i2;
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        switch (this.s) {
            case 0:
                return v2Var;
            case 1:
                return new C0076a0(this, v2Var, 3);
            default:
                return new C0136m0(this, v2Var, 1);
        }
    }
}
