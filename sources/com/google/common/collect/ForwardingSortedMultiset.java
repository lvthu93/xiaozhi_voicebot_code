package com.google.common.collect;

import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultisets;
import java.util.Comparator;
import java.util.NavigableSet;

public abstract class ForwardingSortedMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {

    public abstract class StandardDescendingMultiset extends DescendingMultiset<E> {
        public StandardDescendingMultiset() {
        }

        public final SortedMultiset<E> k() {
            return ForwardingSortedMultiset.this;
        }
    }

    public class StandardElementSet extends SortedMultisets.NavigableElementSet<E> {
        public StandardElementSet(ForwardingSortedMultiset forwardingSortedMultiset) {
            super(forwardingSortedMultiset);
        }
    }

    public Comparator<? super E> comparator() {
        return g().comparator();
    }

    public SortedMultiset<E> descendingMultiset() {
        return g().descendingMultiset();
    }

    public Multiset.Entry<E> firstEntry() {
        return g().firstEntry();
    }

    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return g().headMultiset(e, boundType);
    }

    /* renamed from: j */
    public abstract SortedMultiset<E> g();

    public Multiset.Entry<E> lastEntry() {
        return g().lastEntry();
    }

    public Multiset.Entry<E> pollFirstEntry() {
        return g().pollFirstEntry();
    }

    public Multiset.Entry<E> pollLastEntry() {
        return g().pollLastEntry();
    }

    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return g().subMultiset(e, boundType, e2, boundType2);
    }

    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return g().tailMultiset(e, boundType);
    }

    public NavigableSet<E> elementSet() {
        return g().elementSet();
    }
}
