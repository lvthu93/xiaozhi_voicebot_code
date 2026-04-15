package j$.util.stream;

final class A2 extends C0158q2 {
    long b;
    long c;
    final /* synthetic */ B2 d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    A2(B2 b2, C0182v2 v2Var) {
        super(v2Var);
        this.d = b2;
        this.b = b2.s;
        long j = b2.t;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    public final void accept(long j) {
        long j2 = this.b;
        if (j2 == 0) {
            long j3 = this.c;
            if (j3 > 0) {
                this.c = j3 - 1;
                this.a.accept(j);
                return;
            }
            return;
        }
        this.b = j2 - 1;
    }

    public final void c(long j) {
        this.a.c(D0.W(j, this.d.s, this.c));
    }

    public final boolean e() {
        return this.c == 0 || this.a.e();
    }
}
