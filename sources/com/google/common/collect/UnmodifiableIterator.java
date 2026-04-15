package com.google.common.collect;

import j$.util.Iterator;
import java.util.Iterator;
import java.util.function.Consumer;

public abstract class UnmodifiableIterator<E> implements Iterator<E>, j$.util.Iterator {
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        Iterator.CC.$default$forEachRemaining(this, consumer);
    }

    @Deprecated
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
