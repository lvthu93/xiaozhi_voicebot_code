package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;

final class NaturalOrdering extends Ordering<Comparable> implements Serializable {
    public static final NaturalOrdering g = new NaturalOrdering();
    private static final long serialVersionUID = 0;
    public transient Ordering<Comparable> c;
    public transient Ordering<Comparable> f;

    private Object readResolve() {
        return g;
    }

    public <S extends Comparable> Ordering<S> nullsFirst() {
        Ordering<Comparable> ordering = this.c;
        if (ordering != null) {
            return ordering;
        }
        Ordering<Comparable> nullsFirst = super.nullsFirst();
        this.c = nullsFirst;
        return nullsFirst;
    }

    public <S extends Comparable> Ordering<S> nullsLast() {
        Ordering<Comparable> ordering = this.f;
        if (ordering != null) {
            return ordering;
        }
        Ordering<Comparable> nullsLast = super.nullsLast();
        this.f = nullsLast;
        return nullsLast;
    }

    public <S extends Comparable> Ordering<S> reverse() {
        return ReverseNaturalOrdering.c;
    }

    public String toString() {
        return "Ordering.natural()";
    }

    public int compare(Comparable comparable, Comparable comparable2) {
        Preconditions.checkNotNull(comparable);
        Preconditions.checkNotNull(comparable2);
        return comparable.compareTo(comparable2);
    }
}
