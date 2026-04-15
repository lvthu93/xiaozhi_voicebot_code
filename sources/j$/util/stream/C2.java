package j$.util.stream;

final class C2 extends C0148o2 {
    long b;
    long c;
    final /* synthetic */ D2 d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C2(D2 d2, C0182v2 v2Var) {
        super(v2Var);
        this.d = d2;
        this.b = d2.s;
        long j = d2.t;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    public final void accept(double d2) {
        long j = this.b;
        if (j == 0) {
            long j2 = this.c;
            if (j2 > 0) {
                this.c = j2 - 1;
                this.a.accept(d2);
                return;
            }
            return;
        }
        this.b = j - 1;
    }

    public final void c(long j) {
        this.a.c(D0.W(j, this.d.s, this.c));
    }

    public final boolean e() {
        return this.c == 0 || this.a.e();
    }
}
