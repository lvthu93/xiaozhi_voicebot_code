package j$.util.function;

import j$.util.Objects;
import j$.util.concurrent.u;
import java.util.function.Consumer;

/* renamed from: j$.util.function.Consumer$-CC  reason: invalid class name */
public final /* synthetic */ class Consumer$CC<T> {
    public static Consumer $default$andThen(Consumer consumer, Consumer consumer2) {
        Objects.requireNonNull(consumer2);
        return new u(3, consumer, consumer2);
    }
}
