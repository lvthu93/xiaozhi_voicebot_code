package com.google.common.collect;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Collection;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Set;

final class UnmodifiableSortedMultiset<E> extends Multisets.UnmodifiableMultiset<E> implements SortedMultiset<E> {
    private static final long serialVersionUID = 0;
    public transient UnmodifiableSortedMultiset<E> h;

    public UnmodifiableSortedMultiset(SortedMultiset<E> sortedMultiset) {
        super(sortedMultiset);
    }

    public final Collection a() {
        return (SortedMultiset) this.c;
    }

    public Comparator<? super E> comparator() {
        return ((SortedMultiset) this.c).comparator();
    }

    public final Object delegate() {
        return (SortedMultiset) this.c;
    }

    public SortedMultiset<E> descendingMultiset() {
        UnmodifiableSortedMultiset<E> unmodifiableSortedMultiset = this.h;
        if (unmodifiableSortedMultiset != null) {
            return unmodifiableSortedMultiset;
        }
        UnmodifiableSortedMultiset<E> unmodifiableSortedMultiset2 = new UnmodifiableSortedMultiset<>(((SortedMultiset) this.c).descendingMultiset());
        unmodifiableSortedMultiset2.h = this;
        this.h = unmodifiableSortedMultiset2;
        return unmodifiableSortedMultiset2;
    }

    public Multiset.Entry<E> firstEntry() {
        return ((SortedMultiset) this.c).firstEntry();
    }

    public final Multiset g() {
        return (SortedMultiset) this.c;
    }

    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(((SortedMultiset) this.c).headMultiset(e, boundType));
    }

    public final Set j() {
        return Sets.unmodifiableNavigableSet(((SortedMultiset) this.c).elementSet());
    }

    public Multiset.Entry<E> lastEntry() {
        return ((SortedMultiset) this.c).lastEntry();
    }

    public Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    public Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return Multisets.unmodifiableSortedMultiset(((SortedMultiset) this.c).subMultiset(e, boundType, e2, boundType2));
    }

    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(((SortedMultiset) this.c).tailMultiset(e, boundType));
    }

    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }
}
