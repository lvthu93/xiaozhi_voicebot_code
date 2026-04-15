package com.google.common.collect;

import java.io.Serializable;

final class NullsFirstOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    public final Ordering<? super T> c;

    public NullsFirstOrdering(Ordering<? super T> ordering) {
        this.c = ordering;
    }

    public int compare(T t, T t2) {
        if (t == t2) {
            return 0;
        }
        if (t == null) {
            return -1;
        }
        if (t2 == null) {
            return 1;
        }
        return this.c.compare(t, t2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof NullsFirstOrdering) {
            return this.c.equals(((NullsFirstOrdering) obj).c);
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode() ^ 957692532;
    }

    public <S extends T> Ordering<S> nullsFirst() {
        return this;
    }

    public <S extends T> Ordering<S> nullsLast() {
        return this.c.nullsLast();
    }

    public <S extends T> Ordering<S> reverse() {
        return this.c.reverse().nullsLast();
    }

    public String toString() {
        return this.c + ".nullsFirst()";
    }
}
