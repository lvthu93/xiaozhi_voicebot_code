package j$.util;

import j$.util.stream.D0;
import j$.util.stream.Stream;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.Predicate;

/* renamed from: j$.util.b  reason: case insensitive filesystem */
public abstract /* synthetic */ class C0057b {
    public static void a(H h, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            h.forEachRemaining((DoubleConsumer) consumer);
        } else if (!l0.a) {
            Objects.requireNonNull(consumer);
            h.forEachRemaining((DoubleConsumer) new r(consumer));
        } else {
            l0.a(h.getClass(), "{0} calling Spliterator.OfDouble.forEachRemaining((DoubleConsumer) action::accept)");
            throw null;
        }
    }

    public static void b(K k, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            k.forEachRemaining((IntConsumer) consumer);
        } else if (!l0.a) {
            Objects.requireNonNull(consumer);
            k.forEachRemaining((IntConsumer) new C0204v(consumer));
        } else {
            l0.a(k.getClass(), "{0} calling Spliterator.OfInt.forEachRemaining((IntConsumer) action::accept)");
            throw null;
        }
    }

    public static void c(N n, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            n.forEachRemaining((LongConsumer) consumer);
        } else if (!l0.a) {
            Objects.requireNonNull(consumer);
            n.forEachRemaining((LongConsumer) new C0208z(consumer));
        } else {
            l0.a(n.getClass(), "{0} calling Spliterator.OfLong.forEachRemaining((LongConsumer) action::accept)");
            throw null;
        }
    }

    public static long d(U u) {
        if ((u.characteristics() & 64) == 0) {
            return -1;
        }
        return u.estimateSize();
    }

    public static boolean e(U u, int i) {
        return (u.characteristics() & i) == i;
    }

    public static boolean f(Collection collection, Predicate predicate) {
        if (DesugarCollections.a.isInstance(collection)) {
            return DesugarCollections.c(collection, predicate);
        }
        Objects.requireNonNull(predicate);
        Iterator it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    public static Stream g(Collection collection) {
        return D0.H0(Collection$EL.a(collection), false);
    }

    public static boolean h(H h, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            return h.tryAdvance((DoubleConsumer) consumer);
        }
        if (!l0.a) {
            Objects.requireNonNull(consumer);
            return h.tryAdvance((DoubleConsumer) new r(consumer));
        }
        l0.a(h.getClass(), "{0} calling Spliterator.OfDouble.tryAdvance((DoubleConsumer) action::accept)");
        throw null;
    }

    public static boolean i(K k, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            return k.tryAdvance((IntConsumer) consumer);
        }
        if (!l0.a) {
            Objects.requireNonNull(consumer);
            return k.tryAdvance((IntConsumer) new C0204v(consumer));
        }
        l0.a(k.getClass(), "{0} calling Spliterator.OfInt.tryAdvance((IntConsumer) action::accept)");
        throw null;
    }

    public static boolean j(N n, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            return n.tryAdvance((LongConsumer) consumer);
        }
        if (!l0.a) {
            Objects.requireNonNull(consumer);
            return n.tryAdvance((LongConsumer) new C0208z(consumer));
        }
        l0.a(n.getClass(), "{0} calling Spliterator.OfLong.tryAdvance((LongConsumer) action::accept)");
        throw null;
    }

    public int characteristics() {
        return 16448;
    }

    public long estimateSize() {
        return 0;
    }

    public void forEachRemaining(Object obj) {
        Objects.requireNonNull(obj);
    }

    public boolean tryAdvance(Object obj) {
        Objects.requireNonNull(obj);
        return false;
    }

    public U trySplit() {
        return null;
    }
}
