package j$.util;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.u  reason: case insensitive filesystem */
public interface C0203u extends D {
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(DoubleConsumer doubleConsumer);

    Double next();

    double nextDouble();
}
