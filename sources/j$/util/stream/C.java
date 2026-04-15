package j$.util.stream;

import j$.util.U;
import java.util.function.DoubleConsumer;

final class C extends F {
    C(U u, int i) {
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

    public final void forEach(DoubleConsumer doubleConsumer) {
        if (!isParallel()) {
            F.c1(Y0()).forEachRemaining(doubleConsumer);
        } else {
            super.forEach(doubleConsumer);
        }
    }

    public final void forEachOrdered(DoubleConsumer doubleConsumer) {
        if (!isParallel()) {
            F.c1(Y0()).forEachRemaining(doubleConsumer);
        } else {
            super.forEachOrdered(doubleConsumer);
        }
    }

    public final /* bridge */ /* synthetic */ I parallel() {
        parallel();
        return this;
    }

    public final /* bridge */ /* synthetic */ I sequential() {
        sequential();
        return this;
    }
}
