package com.google.common.collect;

import java.util.Queue;

public abstract class ForwardingQueue<E> extends ForwardingCollection<E> implements Queue<E> {
    public E element() {
        return delegate().element();
    }

    /* renamed from: g */
    public abstract Queue<E> delegate();

    public boolean offer(E e) {
        return delegate().offer(e);
    }

    public E peek() {
        return delegate().peek();
    }

    public E poll() {
        return delegate().poll();
    }

    public E remove() {
        return delegate().remove();
    }
}
