package j$.util.stream;

import j$.util.U;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.o0  reason: case insensitive filesystem */
final class C0146o0 extends C0160r0 {
    C0146o0(U u, int i) {
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

    public final void forEach(LongConsumer longConsumer) {
        if (!isParallel()) {
            C0160r0.c1(Y0()).forEachRemaining(longConsumer);
        } else {
            super.forEach(longConsumer);
        }
    }

    public final void forEachOrdered(LongConsumer longConsumer) {
        if (!isParallel()) {
            C0160r0.c1(Y0()).forEachRemaining(longConsumer);
        } else {
            super.forEachOrdered(longConsumer);
        }
    }

    public final /* bridge */ /* synthetic */ C0175u0 parallel() {
        parallel();
        return this;
    }

    public final /* bridge */ /* synthetic */ C0175u0 sequential() {
        sequential();
        return this;
    }
}
