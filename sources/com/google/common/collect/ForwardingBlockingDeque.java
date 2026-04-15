package com.google.common.collect;

import java.util.Collection;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.TimeUnit;

@Deprecated
public abstract class ForwardingBlockingDeque<E> extends ForwardingDeque<E> implements BlockingDeque<E> {
    public int drainTo(Collection<? super E> collection) {
        return j().drainTo(collection);
    }

    /* renamed from: k */
    public abstract BlockingDeque<E> j();

    public boolean offer(E e, long j, TimeUnit timeUnit) throws InterruptedException {
        return j().offer(e, j, timeUnit);
    }

    public boolean offerFirst(E e, long j, TimeUnit timeUnit) throws InterruptedException {
        return j().offerFirst(e, j, timeUnit);
    }

    public boolean offerLast(E e, long j, TimeUnit timeUnit) throws InterruptedException {
        return j().offerLast(e, j, timeUnit);
    }

    public E poll(long j, TimeUnit timeUnit) throws InterruptedException {
        return j().poll(j, timeUnit);
    }

    public E pollFirst(long j, TimeUnit timeUnit) throws InterruptedException {
        return j().pollFirst(j, timeUnit);
    }

    public E pollLast(long j, TimeUnit timeUnit) throws InterruptedException {
        return j().pollLast(j, timeUnit);
    }

    public void put(E e) throws InterruptedException {
        j().put(e);
    }

    public void putFirst(E e) throws InterruptedException {
        j().putFirst(e);
    }

    public void putLast(E e) throws InterruptedException {
        j().putLast(e);
    }

    public int remainingCapacity() {
        return j().remainingCapacity();
    }

    public E take() throws InterruptedException {
        return j().take();
    }

    public E takeFirst() throws InterruptedException {
        return j().takeFirst();
    }

    public E takeLast() throws InterruptedException {
        return j().takeLast();
    }

    public int drainTo(Collection<? super E> collection, int i) {
        return j().drainTo(collection, i);
    }
}
