package com.google.common.collect;

import com.google.common.collect.Iterators;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import java.util.Set;

final class EmptyContiguousSet<C extends Comparable> extends ContiguousSet<C> {

    public static final class SerializedForm<C extends Comparable> implements Serializable {
        private static final long serialVersionUID = 0;
        public final DiscreteDomain<C> c;

        public SerializedForm() {
            throw null;
        }

        public SerializedForm(DiscreteDomain discreteDomain) {
            this.c = discreteDomain;
        }

        private Object readResolve() {
            return new EmptyContiguousSet(this.c);
        }
    }

    public EmptyContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
    }

    public ImmutableList<C> asList() {
        return ImmutableList.of();
    }

    public boolean contains(Object obj) {
        return false;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Set) {
            return ((Set) obj).isEmpty();
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        return this;
    }

    public boolean isEmpty() {
        return true;
    }

    public final boolean isPartialView() {
        return false;
    }

    public final ImmutableSortedSet<C> o() {
        return ImmutableSortedSet.p(Ordering.natural().reverse());
    }

    public final ImmutableSortedSet q(Object obj, boolean z) {
        Comparable comparable = (Comparable) obj;
        return this;
    }

    public final ImmutableSortedSet r(Object obj, boolean z, Object obj2, boolean z2) {
        Comparable comparable = (Comparable) obj;
        Comparable comparable2 = (Comparable) obj2;
        return this;
    }

    public Range<C> range() {
        throw new NoSuchElementException();
    }

    public final ImmutableSortedSet s(Object obj, boolean z) {
        Comparable comparable = (Comparable) obj;
        return this;
    }

    public int size() {
        return 0;
    }

    public final ContiguousSet<C> t(C c, boolean z) {
        return this;
    }

    public String toString() {
        return "[]";
    }

    public final ContiguousSet<C> u(C c, boolean z, C c2, boolean z2) {
        return this;
    }

    public final ContiguousSet<C> v(C c, boolean z) {
        return this;
    }

    public Object writeReplace() {
        return new SerializedForm(this.i);
    }

    public UnmodifiableIterator<C> descendingIterator() {
        return Iterators.ArrayItr.i;
    }

    public C first() {
        throw new NoSuchElementException();
    }

    public UnmodifiableIterator<C> iterator() {
        return Iterators.ArrayItr.i;
    }

    public C last() {
        throw new NoSuchElementException();
    }

    public Range<C> range(BoundType boundType, BoundType boundType2) {
        throw new NoSuchElementException();
    }
}
