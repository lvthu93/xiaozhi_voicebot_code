package j$.util;

import java.util.Comparator;
import java.util.function.Consumer;

public interface U {
    int characteristics();

    long estimateSize();

    void forEachRemaining(Consumer consumer);

    Comparator getComparator();

    long getExactSizeIfKnown();

    boolean hasCharacteristics(int i);

    boolean tryAdvance(Consumer consumer);

    U trySplit();
}
