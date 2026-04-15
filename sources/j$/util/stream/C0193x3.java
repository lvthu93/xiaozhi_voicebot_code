package j$.util.stream;

import j$.util.C0057b;
import j$.util.K;
import j$.util.Objects;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.x3  reason: case insensitive filesystem */
final class C0193x3 extends C0139m3 implements K {
    C0193x3(D0 d0, U u, boolean z) {
        super(d0, u, z);
    }

    C0193x3(D0 d0, C0075a aVar, boolean z) {
        super(d0, aVar, z);
    }

    /* access modifiers changed from: package-private */
    public final void d() {
        C0079a3 a3Var = new C0079a3();
        this.h = a3Var;
        Objects.requireNonNull(a3Var);
        this.e = this.b.J0(new C0188w3(a3Var, 0));
        this.f = new C0075a(this, 5);
    }

    /* access modifiers changed from: package-private */
    public final C0139m3 e(U u) {
        return new C0193x3(this.b, u, this.a);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.b(this, consumer);
    }

    public final void forEachRemaining(IntConsumer intConsumer) {
        if (this.h != null || this.i) {
            do {
            } while (tryAdvance(intConsumer));
            return;
        }
        Objects.requireNonNull(intConsumer);
        c();
        Objects.requireNonNull(intConsumer);
        C0188w3 w3Var = new C0188w3(intConsumer, 1);
        this.b.I0(this.d, w3Var);
        this.i = true;
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.i(this, consumer);
    }

    public final boolean tryAdvance(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        boolean a = a();
        if (a) {
            C0079a3 a3Var = (C0079a3) this.h;
            long j = this.g;
            int q = a3Var.q(j);
            intConsumer.accept((a3Var.c == 0 && q == 0) ? ((int[]) a3Var.e)[(int) j] : ((int[][]) a3Var.f)[q][(int) (j - a3Var.d[q])]);
        }
        return a;
    }

    public final K trySplit() {
        return (K) super.trySplit();
    }
}
