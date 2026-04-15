package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.NoSuchElementException;

abstract class AbstractIndexedListIterator<E> extends UnmodifiableListIterator<E> {
    public final int c;
    public int f;

    public AbstractIndexedListIterator() {
        throw null;
    }

    public AbstractIndexedListIterator(int i, int i2) {
        Preconditions.checkPositionIndex(i2, i);
        this.c = i;
        this.f = i2;
    }

    public abstract E get(int i);

    public final boolean hasNext() {
        return this.f < this.c;
    }

    public final boolean hasPrevious() {
        return this.f > 0;
    }

    public final E next() {
        if (hasNext()) {
            int i = this.f;
            this.f = i + 1;
            return get(i);
        }
        throw new NoSuchElementException();
    }

    public final int nextIndex() {
        return this.f;
    }

    public final E previous() {
        if (hasPrevious()) {
            int i = this.f - 1;
            this.f = i;
            return get(i);
        }
        throw new NoSuchElementException();
    }

    public final int previousIndex() {
        return this.f - 1;
    }
}
