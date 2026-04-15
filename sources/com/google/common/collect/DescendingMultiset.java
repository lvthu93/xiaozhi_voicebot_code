package com.google.common.collect;

import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SortedMultisets;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

abstract class DescendingMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
    public transient Ordering c;
    public transient NavigableSet<E> f;
    public transient Set<Multiset.Entry<E>> g;

    public final Collection a() {
        return k();
    }

    public Comparator<? super E> comparator() {
        Ordering ordering = this.c;
        if (ordering != null) {
            return ordering;
        }
        Ordering reverse = Ordering.from(k().comparator()).reverse();
        this.c = reverse;
        return reverse;
    }

    public final Object delegate() {
        return k();
    }

    public SortedMultiset<E> descendingMultiset() {
        return k();
    }

    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<E>> set = this.g;
        if (set != null) {
            return set;
        }
        AnonymousClass1EntrySetImpl r0 = new Multisets.EntrySet<Object>() {
            public final Multiset<Object> a() {
                return DescendingMultiset.this;
            }

            public Iterator<Multiset.Entry<Object>> iterator() {
                return DescendingMultiset.this.j();
            }

            public int size() {
                return DescendingMultiset.this.k().entrySet().size();
            }
        };
        this.g = r0;
        return r0;
    }

    public Multiset.Entry<E> firstEntry() {
        return k().lastEntry();
    }

    public final Multiset<E> g() {
        return k();
    }

    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return k().tailMultiset(e, boundType).descendingMultiset();
    }

    public Iterator<E> iterator() {
        return Multisets.c(this);
    }

    public abstract Iterator<Multiset.Entry<E>> j();

    public abstract SortedMultiset<E> k();

    public Multiset.Entry<E> lastEntry() {
        return k().firstEntry();
    }

    public Multiset.Entry<E> pollFirstEntry() {
        return k().pollLastEntry();
    }

    public Multiset.Entry<E> pollLastEntry() {
        return k().pollFirstEntry();
    }

    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return k().subMultiset(e2, boundType2, e, boundType).descendingMultiset();
    }

    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return k().headMultiset(e, boundType).descendingMultiset();
    }

    public Object[] toArray() {
        return c();
    }

    public String toString() {
        return entrySet().toString();
    }

    public <T> T[] toArray(T[] tArr) {
        return ObjectArrays.c(this, tArr);
    }

    public NavigableSet<E> elementSet() {
        NavigableSet<E> navigableSet = this.f;
        if (navigableSet != null) {
            return navigableSet;
        }
        SortedMultisets.NavigableElementSet navigableElementSet = new SortedMultisets.NavigableElementSet(this);
        this.f = navigableElementSet;
        return navigableElementSet;
    }
}
