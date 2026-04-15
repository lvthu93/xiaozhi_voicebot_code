package com.google.common.collect;

import com.google.common.collect.Multiset;

final class DescendingImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
    public final transient ImmutableSortedMultiset<E> j;

    public DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> immutableSortedMultiset) {
        this.j = immutableSortedMultiset;
    }

    public int count(Object obj) {
        return this.j.count(obj);
    }

    public Multiset.Entry<E> firstEntry() {
        return this.j.lastEntry();
    }

    public final boolean isPartialView() {
        return this.j.isPartialView();
    }

    public final Multiset.Entry<E> j(int i) {
        return this.j.entrySet().asList().reverse().get(i);
    }

    public Multiset.Entry<E> lastEntry() {
        return this.j.firstEntry();
    }

    public int size() {
        return this.j.size();
    }

    public ImmutableSortedMultiset<E> descendingMultiset() {
        return this.j;
    }

    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return this.j.tailMultiset(e, boundType).descendingMultiset();
    }

    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return this.j.headMultiset(e, boundType).descendingMultiset();
    }

    public ImmutableSortedSet<E> elementSet() {
        return this.j.elementSet().descendingSet();
    }
}
