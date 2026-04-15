package com.google.common.collect;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.NavigableSet;

public abstract class ForwardingNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E> {

    public class StandardDescendingSet extends Sets.DescendingSet<E> {
        public StandardDescendingSet(ForwardingNavigableSet forwardingNavigableSet) {
            super(forwardingNavigableSet);
        }
    }

    public E ceiling(E e) {
        return j().ceiling(e);
    }

    public Iterator<E> descendingIterator() {
        return j().descendingIterator();
    }

    public NavigableSet<E> descendingSet() {
        return j().descendingSet();
    }

    public E floor(E e) {
        return j().floor(e);
    }

    public NavigableSet<E> headSet(E e, boolean z) {
        return j().headSet(e, z);
    }

    public E higher(E e) {
        return j().higher(e);
    }

    /* renamed from: k */
    public abstract NavigableSet<E> j();

    public E lower(E e) {
        return j().lower(e);
    }

    public E pollFirst() {
        return j().pollFirst();
    }

    public E pollLast() {
        return j().pollLast();
    }

    public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
        return j().subSet(e, z, e2, z2);
    }

    public NavigableSet<E> tailSet(E e, boolean z) {
        return j().tailSet(e, z);
    }
}
