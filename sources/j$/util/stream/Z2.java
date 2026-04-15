package j$.util.stream;

import j$.util.C0057b;
import j$.util.K;
import j$.util.Q;
import j$.util.i0;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

final class Z2 extends C0094d3 implements K {
    final /* synthetic */ C0079a3 g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    Z2(C0079a3 a3Var, int i, int i2, int i3, int i4) {
        super(a3Var, i, i2, i3, i4);
        this.g = a3Var;
    }

    /* access modifiers changed from: package-private */
    public final void a(int i, Object obj, Object obj2) {
        ((IntConsumer) obj2).accept(((int[]) obj)[i]);
    }

    /* access modifiers changed from: package-private */
    public final Q b(Object obj, int i, int i2) {
        return i0.k((int[]) obj, i, i2 + i);
    }

    /* access modifiers changed from: package-private */
    public final Q c(int i, int i2, int i3, int i4) {
        return new Z2(this.g, i, i2, i3, i4);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.b(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.i(this, consumer);
    }
}
