package j$.util.stream;

import j$.util.U;
import java.util.Iterator;

/* renamed from: j$.util.stream.i  reason: case insensitive filesystem */
public interface C0115i extends AutoCloseable {
    void close();

    boolean isParallel();

    Iterator iterator();

    C0115i onClose(Runnable runnable);

    C0115i parallel();

    C0115i sequential();

    U spliterator();

    C0115i unordered();
}
