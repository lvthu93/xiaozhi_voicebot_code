package j$.util.stream;

import j$.util.C0057b;
import j$.util.H;
import j$.util.Q;
import j$.util.i0;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

final class X2 extends C0094d3 implements H {
    final /* synthetic */ Y2 g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    X2(Y2 y2, int i, int i2, int i3, int i4) {
        super(y2, i, i2, i3, i4);
        this.g = y2;
    }

    /* access modifiers changed from: package-private */
    public final void a(int i, Object obj, Object obj2) {
        ((DoubleConsumer) obj2).accept(((double[]) obj)[i]);
    }

    /* access modifiers changed from: package-private */
    public final Q b(Object obj, int i, int i2) {
        return i0.j((double[]) obj, i, i2 + i);
    }

    /* access modifiers changed from: package-private */
    public final Q c(int i, int i2, int i3, int i4) {
        return new X2(this.g, i, i2, i3, i4);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.a(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.h(this, consumer);
    }
}
