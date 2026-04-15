package j$.util.stream;

import j$.util.C0057b;
import j$.util.H;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.m1  reason: case insensitive filesystem */
final class C0137m1 extends C0152p1 implements H {
    C0137m1(I0 i0) {
        super(i0);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.a(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.h(this, consumer);
    }
}
