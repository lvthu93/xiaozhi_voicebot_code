package com.google.common.collect;

import j$.util.Iterator;
import java.util.Iterator;
import java.util.function.Consumer;

public abstract class ForwardingIterator<T> extends ForwardingObject implements Iterator<T>, j$.util.Iterator {
    /* renamed from: a */
    public abstract Iterator<T> delegate();

    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.CC.$default$forEachRemaining(this, consumer);
    }

    public boolean hasNext() {
        return delegate().hasNext();
    }

    public T next() {
        return delegate().next();
    }

    public void remove() {
        delegate().remove();
    }
}
