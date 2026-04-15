package j$.util;

import java.util.function.Consumer;
import java.util.function.LongConsumer;

public interface C extends D {
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(LongConsumer longConsumer);

    Long next();

    long nextLong();
}
