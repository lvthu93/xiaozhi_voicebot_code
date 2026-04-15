package j$.util.stream;

import j$.util.C0057b;
import j$.util.H;
import j$.util.U;
import java.util.function.Consumer;

final class B3 extends G3 implements H {
    B3(H h, long j, long j2) {
        super(h, j, j2);
    }

    B3(H h, long j, long j2, long j3, long j4) {
        super(h, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public final U a(U u, long j, long j2, long j3, long j4) {
        return new B3((H) u, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public final Object b() {
        return new A3(0);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.a(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.h(this, consumer);
    }
}
