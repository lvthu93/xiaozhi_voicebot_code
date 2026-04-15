package j$.util;

import j$.lang.b;
import j$.util.stream.Stream;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* renamed from: j$.util.c  reason: case insensitive filesystem */
public interface C0058c extends b {
    void forEach(Consumer consumer);

    boolean removeIf(Predicate predicate);

    U spliterator();

    Stream stream();
}
