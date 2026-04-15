package j$.util.stream;

import j$.util.U;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.k2  reason: case insensitive filesystem */
final class C0128k2 extends C0143n2 {
    C0128k2(U u, int i, boolean z) {
        super(u, i, z);
    }

    /* access modifiers changed from: package-private */
    public final boolean V0() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 W0(int i, C0182v2 v2Var) {
        throw new UnsupportedOperationException();
    }

    public final void forEach(Consumer consumer) {
        if (!isParallel()) {
            Y0().forEachRemaining(consumer);
        } else {
            super.forEach(consumer);
        }
    }

    public final void forEachOrdered(Consumer consumer) {
        if (!isParallel()) {
            Y0().forEachRemaining(consumer);
        } else {
            super.forEachOrdered(consumer);
        }
    }
}
