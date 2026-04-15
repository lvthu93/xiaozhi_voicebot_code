package com.google.common.base;

import com.google.common.base.AbstractIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public abstract class Optional<T> implements Serializable {
    private static final long serialVersionUID = 0;

    public static <T> Optional<T> absent() {
        return Absent.c;
    }

    public static <T> Optional<T> fromNullable(T t) {
        return t == null ? absent() : new Present(t);
    }

    public static <T> Optional<T> of(T t) {
        return new Present(Preconditions.checkNotNull(t));
    }

    public static <T> Iterable<T> presentInstances(final Iterable<? extends Optional<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterable<T>() {
            public Iterator<T> iterator() {
                return new AbstractIterator<T>(this) {
                    public final Iterator<? extends Optional<? extends T>> g;

                    {
                        this.g = (Iterator) Preconditions.checkNotNull(iterable.iterator());
                    }

                    public final T a() {
                        Optional optional;
                        do {
                            Iterator<? extends Optional<? extends T>> it = this.g;
                            if (it.hasNext()) {
                                optional = (Optional) it.next();
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (!optional.isPresent());
                        return optional.get();
                    }
                };
            }
        };
    }

    public abstract Set<T> asSet();

    public abstract boolean equals(Object obj);

    public abstract T get();

    public abstract int hashCode();

    public abstract boolean isPresent();

    public abstract Optional<T> or(Optional<? extends T> optional);

    public abstract T or(Supplier<? extends T> supplier);

    public abstract T or(T t);

    public abstract T orNull();

    public abstract String toString();

    public abstract <V> Optional<V> transform(Function<? super T, V> function);
}
