package j$.util.stream;

class C1 extends D1 {
    public final /* synthetic */ int c;
    private final Object d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1(C1 c1, L0 l0, int i) {
        super(c1, l0, i);
        this.c = 0;
        this.d = c1.d;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1(C1 c1, M0 m0, int i) {
        super(c1, m0, i);
        this.c = 1;
        this.d = (Object[]) c1.d;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1(L0 l0, Object obj) {
        this((M0) l0, obj, 0);
        this.c = 0;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1(M0 m0, Object obj, int i) {
        super(m0);
        this.c = i;
        this.d = obj;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1(M0 m0, Object[] objArr) {
        this(m0, (Object) objArr, 1);
        this.c = 1;
    }

    /* access modifiers changed from: package-private */
    public final void a() {
        switch (this.c) {
            case 0:
                ((L0) this.a).f(this.d, this.b);
                return;
            default:
                this.a.j((Object[]) this.d, this.b);
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public final C1 b(int i, int i2) {
        switch (this.c) {
            case 0:
                return new C1(this, ((L0) this.a).a(i), i2);
            default:
                return new C1(this, this.a.a(i), i2);
        }
    }
}
