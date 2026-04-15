package com.google.common.collect;

import java.util.ListIterator;

abstract class TransformedListIterator<F, T> extends TransformedIterator<F, T> implements ListIterator<T> {
    public TransformedListIterator(ListIterator<? extends F> listIterator) {
        super(listIterator);
    }

    public void add(T t) {
        throw new UnsupportedOperationException();
    }

    public final boolean hasPrevious() {
        return ((ListIterator) this.c).hasPrevious();
    }

    public final int nextIndex() {
        return ((ListIterator) this.c).nextIndex();
    }

    public final T previous() {
        return a(((ListIterator) this.c).previous());
    }

    public final int previousIndex() {
        return ((ListIterator) this.c).previousIndex();
    }

    public void set(T t) {
        throw new UnsupportedOperationException();
    }
}
