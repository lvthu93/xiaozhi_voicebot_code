package j$.util.stream;

import j$.util.U;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.e0  reason: case insensitive filesystem */
final class C0096e0 extends C0111h0 {
    C0096e0(U u, int i) {
        super(u, i);
    }

    /* access modifiers changed from: package-private */
    public final boolean V0() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        throw new UnsupportedOperationException();
    }

    public final void forEach(IntConsumer intConsumer) {
        if (!isParallel()) {
            C0111h0.c1(Y0()).forEachRemaining(intConsumer);
        } else {
            super.forEach(intConsumer);
        }
    }

    public final void forEachOrdered(IntConsumer intConsumer) {
        if (!isParallel()) {
            C0111h0.c1(Y0()).forEachRemaining(intConsumer);
        } else {
            super.forEachOrdered(intConsumer);
        }
    }

    public final /* bridge */ /* synthetic */ C0126k0 parallel() {
        parallel();
        return this;
    }

    public final /* bridge */ /* synthetic */ C0126k0 sequential() {
        sequential();
        return this;
    }
}
