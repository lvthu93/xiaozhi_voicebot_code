package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultisets;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;

abstract class AbstractSortedMultiset<E> extends AbstractMultiset<E> implements SortedMultiset<E> {
    @GwtTransient
    public final Comparator<? super E> g;
    public transient SortedMultiset<E> h;

    public AbstractSortedMultiset() {
        this(Ordering.natural());
    }

    public final Set a() {
        return new SortedMultisets.NavigableElementSet(this);
    }

    public Comparator<? super E> comparator() {
        return this.g;
    }

    public SortedMultiset<E> descendingMultiset() {
        SortedMultiset<E> sortedMultiset = this.h;
        if (sortedMultiset != null) {
            return sortedMultiset;
        }
        AnonymousClass1DescendingMultisetImpl r0 = new DescendingMultiset<Object>() {
            public Iterator<Object> iterator() {
                return Multisets.c(AbstractSortedMultiset.this.descendingMultiset());
            }

            public final Iterator<Multiset.Entry<Object>> j() {
                return AbstractSortedMultiset.this.g();
            }

            public final SortedMultiset<Object> k() {
                return AbstractSortedMultiset.this;
            }
        };
        this.h = r0;
        return r0;
    }

    public Multiset.Entry<E> firstEntry() {
        Iterator f = f();
        if (f.hasNext()) {
            return (Multiset.Entry) f.next();
        }
        return null;
    }

    public abstract Iterator<Multiset.Entry<E>> g();

    public Multiset.Entry<E> lastEntry() {
        Iterator g2 = g();
        if (g2.hasNext()) {
            return (Multiset.Entry) g2.next();
        }
        return null;
    }

    public Multiset.Entry<E> pollFirstEntry() {
        Iterator f = f();
        if (!f.hasNext()) {
            return null;
        }
        Multiset.Entry entry = (Multiset.Entry) f.next();
        Multiset.Entry<E> immutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        f.remove();
        return immutableEntry;
    }

    public Multiset.Entry<E> pollLastEntry() {
        Iterator g2 = g();
        if (!g2.hasNext()) {
            return null;
        }
        Multiset.Entry entry = (Multiset.Entry) g2.next();
        Multiset.Entry<E> immutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        g2.remove();
        return immutableEntry;
    }

    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        Preconditions.checkNotNull(boundType);
        Preconditions.checkNotNull(boundType2);
        return tailMultiset(e, boundType).headMultiset(e2, boundType2);
    }

    public AbstractSortedMultiset(Comparator<? super E> comparator) {
        this.g = (Comparator) Preconditions.checkNotNull(comparator);
    }

    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }
}
