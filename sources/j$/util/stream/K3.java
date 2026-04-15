package j$.util.stream;

import j$.lang.a;
import j$.util.C0057b;
import j$.util.K;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

final class K3 extends M3 implements K, IntConsumer {
    int f;

    K3(K k, long j, long j2) {
        super(k, j, j2);
    }

    K3(K k, K3 k3) {
        super(k, k3);
    }

    public final void accept(int i) {
        this.f = i;
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    /* access modifiers changed from: protected */
    public final U b(U u) {
        return new K3((K) u, this);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.b(this, consumer);
    }

    /* access modifiers changed from: protected */
    public final void g(Object obj) {
        ((IntConsumer) obj).accept(this.f);
    }

    /* access modifiers changed from: protected */
    public final C0159q3 i(int i) {
        return new C0149o3(i);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.i(this, consumer);
    }
}
