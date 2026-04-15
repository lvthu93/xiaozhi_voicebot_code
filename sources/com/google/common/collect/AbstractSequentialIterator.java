package com.google.common.collect;

import java.util.NoSuchElementException;

public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T> {
    public T c;

    public AbstractSequentialIterator(T t) {
        this.c = t;
    }

    public abstract Comparable a(Object obj);

    public final boolean hasNext() {
        return this.c != null;
    }

    public final T next() {
        if (hasNext()) {
            try {
                T t = this.c;
                this.c = a(t);
                return t;
            } catch (Throwable th) {
                this.c = a(this.c);
                throw th;
            }
        } else {
            throw new NoSuchElementException();
        }
    }
}
