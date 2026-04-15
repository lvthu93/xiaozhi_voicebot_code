package j$.lang;

import j$.util.Objects;
import java.util.Collection;
import java.util.function.Consumer;

/* renamed from: j$.lang.Iterable$-EL  reason: invalid class name */
public final /* synthetic */ class Iterable$EL {
    public static void forEach(Iterable iterable, Consumer consumer) {
        if (iterable instanceof b) {
            ((b) iterable).forEach(consumer);
        } else if (iterable instanceof Collection) {
            Objects.requireNonNull(consumer);
            for (Object accept : (Collection) iterable) {
                consumer.accept(accept);
            }
        } else {
            Objects.requireNonNull(consumer);
            for (Object accept2 : iterable) {
                consumer.accept(accept2);
            }
        }
    }
}
