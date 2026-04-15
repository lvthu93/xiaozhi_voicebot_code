package com.google.common.collect;

import java.io.Serializable;

final class NullsLastOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    public final Ordering<? super T> c;

    public NullsLastOrdering(Ordering<? super T> ordering) {
        this.c = ordering;
    }

    public int compare(T t, T t2) {
        if (t == t2) {
            return 0;
        }
        if (t == null) {
            return 1;
        }
        if (t2 == null) {
            return -1;
        }
        return this.c.compare(t, t2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof NullsLastOrdering) {
            return this.c.equals(((NullsLastOrdering) obj).c);
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode() ^ -921210296;
    }

    public <S extends T> Ordering<S> nullsFirst() {
        return this.c.nullsFirst();
    }

    public <S extends T> Ordering<S> nullsLast() {
        return this;
    }

    public <S extends T> Ordering<S> reverse() {
        return this.c.reverse().nullsFirst();
    }

    public String toString() {
        return this.c + ".nullsLast()";
    }
}
