package j$.util.stream;

import j$.lang.a;
import j$.util.C0057b;
import j$.util.H;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

final class J3 extends M3 implements H, DoubleConsumer {
    double f;

    J3(H h, long j, long j2) {
        super(h, j, j2);
    }

    J3(H h, J3 j3) {
        super(h, j3);
    }

    public final void accept(double d) {
        this.f = d;
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    /* access modifiers changed from: protected */
    public final U b(U u) {
        return new J3((H) u, this);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.a(this, consumer);
    }

    /* access modifiers changed from: protected */
    public final void g(Object obj) {
        ((DoubleConsumer) obj).accept(this.f);
    }

    /* access modifiers changed from: protected */
    public final C0159q3 i(int i) {
        return new C0144n3(i);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.h(this, consumer);
    }
}
