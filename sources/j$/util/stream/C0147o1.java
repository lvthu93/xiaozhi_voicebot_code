package j$.util.stream;

import j$.util.C0057b;
import j$.util.N;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.o1  reason: case insensitive filesystem */
final class C0147o1 extends C0152p1 implements N {
    C0147o1(K0 k0) {
        super(k0);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.c(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.j(this, consumer);
    }
}
