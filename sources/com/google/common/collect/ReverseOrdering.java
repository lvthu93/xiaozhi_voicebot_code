package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Iterator;

final class ReverseOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    public final Ordering<? super T> c;

    public ReverseOrdering(Ordering<? super T> ordering) {
        this.c = (Ordering) Preconditions.checkNotNull(ordering);
    }

    public int compare(T t, T t2) {
        return this.c.compare(t2, t);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ReverseOrdering) {
            return this.c.equals(((ReverseOrdering) obj).c);
        }
        return false;
    }

    public int hashCode() {
        return -this.c.hashCode();
    }

    public <E extends T> E max(E e, E e2) {
        return this.c.min(e, e2);
    }

    public <E extends T> E min(E e, E e2) {
        return this.c.max(e, e2);
    }

    public <S extends T> Ordering<S> reverse() {
        return this.c;
    }

    public String toString() {
        return this.c + ".reverse()";
    }

    public <E extends T> E max(E e, E e2, E e3, E... eArr) {
        return this.c.min(e, e2, e3, eArr);
    }

    public <E extends T> E min(E e, E e2, E e3, E... eArr) {
        return this.c.max(e, e2, e3, eArr);
    }

    public <E extends T> E max(Iterator<E> it) {
        return this.c.min(it);
    }

    public <E extends T> E min(Iterator<E> it) {
        return this.c.max(it);
    }

    public <E extends T> E max(Iterable<E> iterable) {
        return this.c.min(iterable);
    }

    public <E extends T> E min(Iterable<E> iterable) {
        return this.c.max(iterable);
    }
}
