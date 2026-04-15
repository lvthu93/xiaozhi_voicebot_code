package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public final class EvictingQueue<E> extends ForwardingQueue<E> implements Serializable {
    private static final long serialVersionUID = 0;
    public final ArrayDeque c;
    public final int f;

    public EvictingQueue(int i) {
        boolean z;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "maxSize (%s) must >= 0", i);
        this.c = new ArrayDeque(i);
        this.f = i;
    }

    public static <E> EvictingQueue<E> create(int i) {
        return new EvictingQueue<>(i);
    }

    public final Collection a() {
        return this.c;
    }

    public boolean add(E e) {
        Preconditions.checkNotNull(e);
        int i = this.f;
        if (i == 0) {
            return true;
        }
        int size = size();
        ArrayDeque arrayDeque = this.c;
        if (size == i) {
            arrayDeque.remove();
        }
        arrayDeque.add(e);
        return true;
    }

    public boolean addAll(Collection<? extends E> collection) {
        int size = collection.size();
        int i = this.f;
        if (size < i) {
            return Iterators.addAll(this, collection.iterator());
        }
        clear();
        return Iterables.addAll(this, Iterables.skip(collection, size - i));
    }

    public boolean contains(Object obj) {
        return this.c.contains(Preconditions.checkNotNull(obj));
    }

    public final Object delegate() {
        return this.c;
    }

    public final Queue<E> g() {
        return this.c;
    }

    public boolean offer(E e) {
        return add(e);
    }

    public int remainingCapacity() {
        return this.f - size();
    }

    public boolean remove(Object obj) {
        return this.c.remove(Preconditions.checkNotNull(obj));
    }
}
