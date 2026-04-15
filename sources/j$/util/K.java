package j$.util;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public interface K extends Q {
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(IntConsumer intConsumer);

    boolean tryAdvance(Consumer consumer);

    boolean tryAdvance(IntConsumer intConsumer);

    K trySplit();
}
