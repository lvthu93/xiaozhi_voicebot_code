package j$.util.stream;

/* renamed from: j$.util.stream.y2  reason: case insensitive filesystem */
final class C0197y2 extends C0153p2 {
    long b;
    long c;
    final /* synthetic */ C0201z2 d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0197y2(C0201z2 z2Var, C0182v2 v2Var) {
        super(v2Var);
        this.d = z2Var;
        this.b = z2Var.s;
        long j = z2Var.t;
        this.c = j < 0 ? Long.MAX_VALUE : j;
    }

    public final void accept(int i) {
        long j = this.b;
        if (j == 0) {
            long j2 = this.c;
            if (j2 > 0) {
                this.c = j2 - 1;
                this.a.accept(i);
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
