package j$.util.stream;

import j$.lang.a;
import j$.util.C0057b;
import j$.util.N;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

final class L3 extends M3 implements N, LongConsumer {
    long f;

    L3(N n, long j, long j2) {
        super(n, j, j2);
    }

    L3(N n, L3 l3) {
        super(n, l3);
    }

    public final void accept(long j) {
        this.f = j;
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    /* access modifiers changed from: protected */
    public final U b(U u) {
        return new L3((N) u, this);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.c(this, consumer);
    }

    /* access modifiers changed from: protected */
    public final void g(Object obj) {
        ((LongConsumer) obj).accept(this.f);
    }

    /* access modifiers changed from: protected */
    public final C0159q3 i(int i) {
        return new C0154p3(i);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.j(this, consumer);
    }
}
