package com.google.common.collect;

import java.io.Serializable;
import java.util.List;

final class AllEqualOrdering extends Ordering<Object> implements Serializable {
    public static final AllEqualOrdering c = new AllEqualOrdering();
    private static final long serialVersionUID = 0;

    private Object readResolve() {
        return c;
    }

    public int compare(Object obj, Object obj2) {
        return 0;
    }

    public <E> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
        return ImmutableList.copyOf(iterable);
    }

    public <S> Ordering<S> reverse() {
        return this;
    }

    public <E> List<E> sortedCopy(Iterable<E> iterable) {
        return Lists.newArrayList(iterable);
    }

    public String toString() {
        return "Ordering.allEqual()";
    }
}
