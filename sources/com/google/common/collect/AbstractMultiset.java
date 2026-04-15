package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

abstract class AbstractMultiset<E> extends AbstractCollection<E> implements Multiset<E> {
    public transient Set<E> c;
    public transient Set<Multiset.Entry<E>> f;

    public class ElementSet extends Multisets.ElementSet<E> {
        public ElementSet() {
        }

        public final Multiset<E> a() {
            return AbstractMultiset.this;
        }

        public Iterator<E> iterator() {
            return AbstractMultiset.this.c();
        }
    }

    public class EntrySet extends Multisets.EntrySet<E> {
        public EntrySet() {
        }

        public Multiset<E> a() {
            return AbstractMultiset.this;
        }

        public Iterator<Multiset.Entry<E>> iterator() {
            return AbstractMultiset.this.f();
        }

        public int size() {
            return AbstractMultiset.this.b();
        }
    }

    public Set<E> a() {
        return new ElementSet();
    }

    public final boolean add(E e) {
        add(e, 1);
        return true;
    }

    public final boolean addAll(Collection<? extends E> collection) {
        Preconditions.checkNotNull(this);
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            Multiset multiset = (Multiset) collection;
            if (multiset instanceof AbstractMapBasedMultiset) {
                AbstractMapBasedMultiset abstractMapBasedMultiset = (AbstractMapBasedMultiset) multiset;
                if (abstractMapBasedMultiset.isEmpty()) {
                    return false;
                }
                Preconditions.checkNotNull(this);
                for (int b = abstractMapBasedMultiset.g.b(); b >= 0; b = abstractMapBasedMultiset.g.i(b)) {
                    add(abstractMapBasedMultiset.g.c(b), abstractMapBasedMultiset.g.d(b));
                }
            } else if (multiset.isEmpty()) {
                return false;
            } else {
                for (Multiset.Entry entry : multiset.entrySet()) {
                    add(entry.getElement(), entry.getCount());
                }
            }
            return true;
        } else if (collection.isEmpty()) {
            return false;
        } else {
            return Iterators.addAll(this, collection.iterator());
        }
    }

    public abstract int b();

    public abstract Iterator<E> c();

    public abstract void clear();

    public boolean contains(Object obj) {
        return count(obj) > 0;
    }

    public Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    public Set<E> elementSet() {
        Set<E> set = this.c;
        if (set != null) {
            return set;
        }
        Set<E> a = a();
        this.c = a;
        return a;
    }

    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<E>> set = this.f;
        if (set != null) {
            return set;
        }
        Set<Multiset.Entry<E>> createEntrySet = createEntrySet();
        this.f = createEntrySet;
        return createEntrySet;
    }

    public final boolean equals(Object obj) {
        return Multisets.a(this, obj);
    }

    public abstract Iterator<Multiset.Entry<E>> f();

    public final int hashCode() {
        return entrySet().hashCode();
    }

    public boolean isEmpty() {
        return entrySet().isEmpty();
    }

    public final boolean remove(Object obj) {
        return remove(obj, 1) > 0;
    }

    public final boolean removeAll(Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return elementSet().removeAll(collection);
    }

    public final boolean retainAll(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        return elementSet().retainAll(collection);
    }

    public int setCount(E e, int i) {
        CollectPreconditions.b(i, "count");
        int count = count(e);
        int i2 = i - count;
        if (i2 > 0) {
            add(e, i2);
        } else if (i2 < 0) {
            remove(e, -i2);
        }
        return count;
    }

    public final String toString() {
        return entrySet().toString();
    }

    public int add(E e, int i) {
        throw new UnsupportedOperationException();
    }

    public int remove(Object obj, int i) {
        throw new UnsupportedOperationException();
    }

    public boolean setCount(E e, int i, int i2) {
        CollectPreconditions.b(i, "oldCount");
        CollectPreconditions.b(i2, "newCount");
        if (count(e) != i) {
            return false;
        }
        setCount(e, i2);
        return true;
    }
}
