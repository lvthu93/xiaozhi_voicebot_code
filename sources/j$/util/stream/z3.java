package j$.util.stream;

import j$.util.C0057b;
import j$.util.N;
import j$.util.Objects;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

final class z3 extends C0139m3 implements N {
    z3(D0 d0, U u, boolean z) {
        super(d0, u, z);
    }

    z3(D0 d0, C0075a aVar, boolean z) {
        super(d0, aVar, z);
    }

    /* access modifiers changed from: package-private */
    public final void d() {
        C0089c3 c3Var = new C0089c3();
        this.h = c3Var;
        Objects.requireNonNull(c3Var);
        this.e = this.b.J0(new y3(c3Var, 0));
        this.f = new C0075a(this, 6);
    }

    /* access modifiers changed from: package-private */
    public final C0139m3 e(U u) {
        return new z3(this.b, u, this.a);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.c(this, consumer);
    }

    public final void forEachRemaining(LongConsumer longConsumer) {
        if (this.h != null || this.i) {
            do {
            } while (tryAdvance(longConsumer));
            return;
        }
        Objects.requireNonNull(longConsumer);
        c();
        Objects.requireNonNull(longConsumer);
        y3 y3Var = new y3(longConsumer, 1);
        this.b.I0(this.d, y3Var);
        this.i = true;
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.j(this, consumer);
    }

    public final boolean tryAdvance(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        boolean a = a();
        if (a) {
            C0089c3 c3Var = (C0089c3) this.h;
            long j = this.g;
            int q = c3Var.q(j);
            longConsumer.accept((c3Var.c == 0 && q == 0) ? ((long[]) c3Var.e)[(int) j] : ((long[][]) c3Var.f)[q][(int) (j - c3Var.d[q])]);
        }
        return a;
    }

    public final N trySplit() {
        return (N) super.trySplit();
    }
}
