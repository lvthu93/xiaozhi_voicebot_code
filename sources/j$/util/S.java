package j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;

public final /* synthetic */ class S implements U {
    public final /* synthetic */ Spliterator a;

    private /* synthetic */ S(Spliterator spliterator) {
        this.a = spliterator;
    }

    public static /* synthetic */ U a(Spliterator spliterator) {
        if (spliterator == null) {
            return null;
        }
        return spliterator instanceof T ? ((T) spliterator).a : spliterator instanceof Spliterator.OfPrimitive ? O.a((Spliterator.OfPrimitive) spliterator) : new S(spliterator);
    }

    public final /* synthetic */ int characteristics() {
        return this.a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof S) {
            obj = ((S) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ long estimateSize() {
        return this.a.estimateSize();
    }

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.a.forEachRemaining(consumer);
    }

    public final /* synthetic */ Comparator getComparator() {
        return this.a.getComparator();
    }

    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.a.getExactSizeIfKnown();
    }

    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.a.tryAdvance(consumer);
    }

    public final /* synthetic */ U trySplit() {
        return a(this.a.trySplit());
    }
}
