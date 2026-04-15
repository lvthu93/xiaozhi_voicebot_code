package j$.util;

import java.util.function.Consumer;
import java.util.function.LongConsumer;

public interface N extends Q {
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(LongConsumer longConsumer);

    boolean tryAdvance(Consumer consumer);

    boolean tryAdvance(LongConsumer longConsumer);

    N trySplit();
}
