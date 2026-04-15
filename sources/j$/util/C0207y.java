package j$.util;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.y  reason: case insensitive filesystem */
public interface C0207y extends D {
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(IntConsumer intConsumer);

    Integer next();

    int nextInt();
}
