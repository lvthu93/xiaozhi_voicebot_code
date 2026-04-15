package j$.util.stream;

import j$.util.C0057b;
import j$.util.H;
import j$.util.Objects;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.v3  reason: case insensitive filesystem */
final class C0183v3 extends C0139m3 implements H {
    C0183v3(D0 d0, U u, boolean z) {
        super(d0, u, z);
    }

    C0183v3(D0 d0, C0075a aVar, boolean z) {
        super(d0, aVar, z);
    }

    /* access modifiers changed from: package-private */
    public final void d() {
        Y2 y2 = new Y2();
        this.h = y2;
        Objects.requireNonNull(y2);
        this.e = this.b.J0(new C0178u3(y2, 0));
        this.f = new C0075a(this, 4);
    }

    /* access modifiers changed from: package-private */
    public final C0139m3 e(U u) {
        return new C0183v3(this.b, u, this.a);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.a(this, consumer);
    }

    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        if (this.h != null || this.i) {
            do {
            } while (tryAdvance(doubleConsumer));
            return;
        }
        Objects.requireNonNull(doubleConsumer);
        c();
        Objects.requireNonNull(doubleConsumer);
        C0178u3 u3Var = new C0178u3(doubleConsumer, 1);
        this.b.I0(this.d, u3Var);
        this.i = true;
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.h(this, consumer);
    }

    public final boolean tryAdvance(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        boolean a = a();
        if (a) {
            Y2 y2 = (Y2) this.h;
            long j = this.g;
            int q = y2.q(j);
            doubleConsumer.accept((y2.c == 0 && q == 0) ? ((double[]) y2.e)[(int) j] : ((double[][]) y2.f)[q][(int) (j - y2.d[q])]);
        }
        return a;
    }

    public final H trySplit() {
        return (H) super.trySplit();
    }
}
