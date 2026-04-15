package j$.util.stream;

import j$.util.C0057b;
import j$.util.N;
import j$.util.Q;
import j$.util.i0;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.b3  reason: case insensitive filesystem */
final class C0084b3 extends C0094d3 implements N {
    final /* synthetic */ C0089c3 g;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0084b3(C0089c3 c3Var, int i, int i2, int i3, int i4) {
        super(c3Var, i, i2, i3, i4);
        this.g = c3Var;
    }

    /* access modifiers changed from: package-private */
    public final void a(int i, Object obj, Object obj2) {
        ((LongConsumer) obj2).accept(((long[]) obj)[i]);
    }

    /* access modifiers changed from: package-private */
    public final Q b(Object obj, int i, int i2) {
        return i0.l((long[]) obj, i, i2 + i);
    }

    /* access modifiers changed from: package-private */
    public final Q c(int i, int i2, int i3, int i4) {
        return new C0084b3(this.g, i, i2, i3, i4);
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        C0057b.c(this, consumer);
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return C0057b.j(this, consumer);
    }
}
