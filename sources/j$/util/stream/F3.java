package j$.util.stream;

import j$.util.C0057b;
import j$.util.N;
import j$.util.U;
import java.util.function.Consumer;

final class F3 extends G3 implements N {
    F3(N n, long j, long j2) {
        super(n, j, j2);
    }

    F3(N n, long j, long j2, long j3, long j4) {
        super(n, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public final U a(U u, long j, long j2, long j3, long j4) {
        return new F3((N) u, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public final Object b() {
        return new E3(0);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.c(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.j(this, consumer);
    }
}
