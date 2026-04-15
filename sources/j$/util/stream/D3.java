package j$.util.stream;

import j$.util.C0057b;
import j$.util.K;
import j$.util.U;
import java.util.function.Consumer;

final class D3 extends G3 implements K {
    D3(K k, long j, long j2) {
        super(k, j, j2);
    }

    D3(K k, long j, long j2, long j3, long j4) {
        super(k, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public final U a(U u, long j, long j2, long j3, long j4) {
        return new D3((K) u, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public final Object b() {
        return new C3(0);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.b(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.i(this, consumer);
    }
}
