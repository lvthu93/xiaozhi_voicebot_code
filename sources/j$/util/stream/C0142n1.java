package j$.util.stream;

import j$.util.C0057b;
import j$.util.K;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.n1  reason: case insensitive filesystem */
final class C0142n1 extends C0152p1 implements K {
    C0142n1(J0 j0) {
        super(j0);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.b(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.i(this, consumer);
    }
}
