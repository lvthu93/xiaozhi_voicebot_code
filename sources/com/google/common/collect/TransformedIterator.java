package com.google.common.collect;

import com.google.common.base.Preconditions;
import j$.util.Iterator;
import java.util.Iterator;
import java.util.function.Consumer;

abstract class TransformedIterator<F, T> implements Iterator<T>, j$.util.Iterator {
    public final Iterator<? extends F> c;

    public TransformedIterator(Iterator<? extends F> it) {
        this.c = (Iterator) Preconditions.checkNotNull(it);
    }

    public abstract T a(F f);

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.CC.$default$forEachRemaining(this, consumer);
    }

    public final boolean hasNext() {
        return this.c.hasNext();
    }

    public final T next() {
        return a(this.c.next());
    }

    public final void remove() {
        this.c.remove();
    }
}
