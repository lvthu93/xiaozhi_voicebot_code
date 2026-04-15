package com.google.common.collect;

import java.util.Comparator;
import java.util.SortedSet;

public abstract class ForwardingSortedSet<E> extends ForwardingSet<E> implements SortedSet<E> {
    public Comparator<? super E> comparator() {
        return g().comparator();
    }

    public E first() {
        return g().first();
    }

    public SortedSet<E> headSet(E e) {
        return g().headSet(e);
    }

    /* renamed from: j */
    public abstract SortedSet<E> g();

    public E last() {
        return g().last();
    }

    public SortedSet<E> subSet(E e, E e2) {
        return g().subSet(e, e2);
    }

    public SortedSet<E> tailSet(E e) {
        return g().tailSet(e);
    }
}
