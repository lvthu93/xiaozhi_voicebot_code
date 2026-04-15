package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

public abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E> implements NavigableSet<E>, SortedIterable<E> {
    public final transient Comparator<? super E> g;
    public transient ImmutableSortedSet<E> h;

    public static class SerializedForm<E> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Comparator<? super E> c;
        public final Object[] f;

        public SerializedForm(Comparator<? super E> comparator, Object[] objArr) {
            this.c = comparator;
            this.f = objArr;
        }

        public Object readResolve() {
            return new Builder(this.c).add(this.f).build();
        }
    }

    public ImmutableSortedSet(Comparator<? super E> comparator) {
        this.g = comparator;
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(E[] eArr) {
        return m(Ordering.natural(), eArr.length, (Object[]) eArr.clone());
    }

    public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> sortedSet) {
        Comparator<? super E> comparator = SortedIterables.comparator(sortedSet);
        ImmutableList<E> copyOf = ImmutableList.copyOf(sortedSet);
        if (copyOf.isEmpty()) {
            return p(comparator);
        }
        return new RegularImmutableSortedSet(copyOf, comparator);
    }

    public static <E> ImmutableSortedSet<E> m(Comparator<? super E> comparator, int i, E... eArr) {
        if (i == 0) {
            return p(comparator);
        }
        ObjectArrays.a(i, eArr);
        Arrays.sort(eArr, 0, i, comparator);
        int i2 = 1;
        for (int i3 = 1; i3 < i; i3++) {
            E e = eArr[i3];
            if (comparator.compare(e, eArr[i2 - 1]) != 0) {
                eArr[i2] = e;
                i2++;
            }
        }
        Arrays.fill(eArr, i2, i, (Object) null);
        if (i2 < eArr.length / 2) {
            eArr = Arrays.copyOf(eArr, i2);
        }
        return new RegularImmutableSortedSet(ImmutableList.g(i2, eArr), comparator);
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static <E> ImmutableSortedSet<E> of() {
        return RegularImmutableSortedSet.j;
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator) {
        return new Builder<>(comparator);
    }

    public static <E> RegularImmutableSortedSet<E> p(Comparator<? super E> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return RegularImmutableSortedSet.j;
        }
        return new RegularImmutableSortedSet<>(ImmutableList.of(), comparator);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder<>(Collections.reverseOrder());
    }

    public E ceiling(E e) {
        return Iterables.getFirst(tailSet(e, true), null);
    }

    public Comparator<? super E> comparator() {
        return this.g;
    }

    public abstract UnmodifiableIterator<E> descendingIterator();

    public E first() {
        return iterator().next();
    }

    public E floor(E e) {
        return Iterators.getNext(headSet(e, true).descendingIterator(), null);
    }

    public E higher(E e) {
        return Iterables.getFirst(tailSet(e, false), null);
    }

    public abstract UnmodifiableIterator<E> iterator();

    public E last() {
        return descendingIterator().next();
    }

    public E lower(E e) {
        return Iterators.getNext(headSet(e, false).descendingIterator(), null);
    }

    public abstract ImmutableSortedSet<E> o();

    @Deprecated
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    public abstract ImmutableSortedSet<E> q(E e, boolean z);

    public abstract ImmutableSortedSet<E> r(E e, boolean z, E e2, boolean z2);

    public abstract ImmutableSortedSet<E> s(E e, boolean z);

    public Object writeReplace() {
        return new SerializedForm(this.g, toArray());
    }

    public static final class Builder<E> extends ImmutableSet.Builder<E> {
        public final Comparator<? super E> f;

        public Builder(Comparator<? super E> comparator) {
            this.f = (Comparator) Preconditions.checkNotNull(comparator);
        }

        public ImmutableSortedSet<E> build() {
            ImmutableSortedSet<E> m = ImmutableSortedSet.m(this.f, this.b, this.a);
            this.b = m.size();
            this.c = true;
            return m;
        }

        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll((Iterable) iterable);
            return this;
        }

        public Builder<E> add(E e) {
            super.add((Object) e);
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> it) {
            super.addAll((Iterator) it);
            return this;
        }

        public Builder<E> add(E... eArr) {
            super.add((Object[]) eArr);
            return this;
        }
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> iterable) {
        return copyOf(Ordering.natural(), iterable);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e) {
        return new RegularImmutableSortedSet(ImmutableList.of(e), Ordering.natural());
    }

    public ImmutableSortedSet<E> descendingSet() {
        ImmutableSortedSet<E> immutableSortedSet = this.h;
        if (immutableSortedSet != null) {
            return immutableSortedSet;
        }
        ImmutableSortedSet<E> o = o();
        this.h = o;
        o.h = this;
        return o;
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2) {
        return m(Ordering.natural(), 2, e, e2);
    }

    public ImmutableSortedSet<E> headSet(E e) {
        return headSet(e, false);
    }

    public ImmutableSortedSet<E> subSet(E e, E e2) {
        return subSet(e, true, e2, false);
    }

    public ImmutableSortedSet<E> tailSet(E e) {
        return tailSet(e, true);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> collection) {
        return copyOf(Ordering.natural(), collection);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3) {
        return m(Ordering.natural(), 3, e, e2, e3);
    }

    public ImmutableSortedSet<E> headSet(E e, boolean z) {
        return q(Preconditions.checkNotNull(e), z);
    }

    public ImmutableSortedSet<E> subSet(E e, boolean z, E e2, boolean z2) {
        Preconditions.checkNotNull(e);
        Preconditions.checkNotNull(e2);
        Preconditions.checkArgument(this.g.compare(e, e2) <= 0);
        return r(e, z, e2, z2);
    }

    public ImmutableSortedSet<E> tailSet(E e, boolean z) {
        return s(Preconditions.checkNotNull(e), z);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4) {
        return m(Ordering.natural(), 4, e, e2, e3, e4);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> it) {
        return copyOf(Ordering.natural(), it);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5) {
        return m(Ordering.natural(), 5, e, e2, e3, e4, e5);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        int length = eArr.length + 6;
        Comparable[] comparableArr = new Comparable[length];
        comparableArr[0] = e;
        comparableArr[1] = e2;
        comparableArr[2] = e3;
        comparableArr[3] = e4;
        comparableArr[4] = e5;
        comparableArr[5] = e6;
        System.arraycopy(eArr, 0, comparableArr, 6, eArr.length);
        return m(Ordering.natural(), length, comparableArr);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterator<? extends E> it) {
        return new Builder(comparator).addAll((Iterator) it).build();
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Iterable<? extends E>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <E> com.google.common.collect.ImmutableSortedSet<E> copyOf(java.util.Comparator<? super E> r2, java.lang.Iterable<? extends E> r3) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r2)
            boolean r0 = com.google.common.collect.SortedIterables.hasSameComparator(r2, r3)
            if (r0 == 0) goto L_0x0017
            boolean r0 = r3 instanceof com.google.common.collect.ImmutableSortedSet
            if (r0 == 0) goto L_0x0017
            r0 = r3
            com.google.common.collect.ImmutableSortedSet r0 = (com.google.common.collect.ImmutableSortedSet) r0
            boolean r1 = r0.isPartialView()
            if (r1 != 0) goto L_0x0017
            return r0
        L_0x0017:
            boolean r0 = r3 instanceof java.util.Collection
            if (r0 == 0) goto L_0x001e
            java.util.Collection r3 = (java.util.Collection) r3
            goto L_0x0026
        L_0x001e:
            java.util.Iterator r3 = r3.iterator()
            java.util.ArrayList r3 = com.google.common.collect.Lists.newArrayList(r3)
        L_0x0026:
            java.lang.Object[] r3 = r3.toArray()
            int r0 = r3.length
            com.google.common.collect.ImmutableSortedSet r2 = m(r2, r0, r3)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.lang.Iterable):com.google.common.collect.ImmutableSortedSet");
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Collection<? extends E> collection) {
        return copyOf(comparator, collection);
    }
}
