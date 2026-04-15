package j$.util;

import java.util.Comparator;
import java.util.function.Consumer;

final class e0 extends C0057b implements U {
    e0() {
    }

    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
    }

    public final Comparator getComparator() {
        throw new IllegalStateException();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return C0057b.d(this);
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return C0057b.e(this, i);
    }

    public final boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        return false;
    }
}
