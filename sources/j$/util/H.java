package j$.util;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

public interface H extends Q {
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(DoubleConsumer doubleConsumer);

    boolean tryAdvance(Consumer consumer);

    boolean tryAdvance(DoubleConsumer doubleConsumer);

    H trySplit();
}
