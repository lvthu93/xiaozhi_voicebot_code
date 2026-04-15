package com.google.common.collect;

final class DescendingImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    public final ImmutableSortedSet<E> i;

    public DescendingImmutableSortedSet(ImmutableSortedSet<E> immutableSortedSet) {
        super(Ordering.from(immutableSortedSet.comparator()).reverse());
        this.i = immutableSortedSet;
    }

    public E ceiling(E e) {
        return this.i.floor(e);
    }

    public boolean contains(Object obj) {
        return this.i.contains(obj);
    }

    public E floor(E e) {
        return this.i.ceiling(e);
    }

    public E higher(E e) {
        return this.i.lower(e);
    }

    public final boolean isPartialView() {
        return this.i.isPartialView();
    }

    public E lower(E e) {
        return this.i.higher(e);
    }

    public final ImmutableSortedSet<E> o() {
        throw new AssertionError("should never be called");
    }

    public final ImmutableSortedSet<E> q(E e, boolean z) {
        return this.i.tailSet(e, z).descendingSet();
    }

    public final ImmutableSortedSet<E> r(E e, boolean z, E e2, boolean z2) {
        return this.i.subSet(e2, z2, e, z).descendingSet();
    }

    public final ImmutableSortedSet<E> s(E e, boolean z) {
        return this.i.headSet(e, z).descendingSet();
    }

    public int size() {
        return this.i.size();
    }

    public UnmodifiableIterator<E> descendingIterator() {
        return this.i.iterator();
    }

    public ImmutableSortedSet<E> descendingSet() {
        return this.i;
    }

    public UnmodifiableIterator<E> iterator() {
        return this.i.descendingIterator();
    }
}
