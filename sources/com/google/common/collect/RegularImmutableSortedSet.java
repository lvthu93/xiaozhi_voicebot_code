package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    public static final RegularImmutableSortedSet<Comparable> j = new RegularImmutableSortedSet<>(ImmutableList.of(), Ordering.natural());
    public final transient ImmutableList<E> i;

    public RegularImmutableSortedSet(ImmutableList<E> immutableList, Comparator<? super E> comparator) {
        super(comparator);
        this.i = immutableList;
    }

    public final int a(int i2, Object[] objArr) {
        return this.i.a(i2, objArr);
    }

    public ImmutableList<E> asList() {
        return this.i;
    }

    public final Object[] b() {
        return this.i.b();
    }

    public final int c() {
        return this.i.c();
    }

    public E ceiling(E e) {
        int v = v(e, true);
        if (v == size()) {
            return null;
        }
        return this.i.get(v);
    }

    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return Collections.binarySearch(this.i, obj, this.g) >= 0;
        } catch (ClassCastException unused) {
            return false;
        }
    }

    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!SortedIterables.hasSameComparator(comparator(), collection) || collection.size() <= 1) {
            return super.containsAll(collection);
        }
        UnmodifiableIterator it = iterator();
        Iterator<?> it2 = collection.iterator();
        if (!it.hasNext()) {
            return false;
        }
        Object next = it2.next();
        Object next2 = it.next();
        while (true) {
            try {
                int compare = this.g.compare(next2, next);
                if (compare < 0) {
                    if (!it.hasNext()) {
                        return false;
                    }
                    next2 = it.next();
                } else if (compare == 0) {
                    if (!it2.hasNext()) {
                        return true;
                    }
                    next = it2.next();
                } else if (compare > 0) {
                    break;
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0034 A[Catch:{ ClassCastException | NoSuchElementException -> 0x0046 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r7) {
        /*
            r6 = this;
            r0 = 1
            if (r7 != r6) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r7 instanceof java.util.Set
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            java.util.Set r7 = (java.util.Set) r7
            int r1 = r6.size()
            int r3 = r7.size()
            if (r1 == r3) goto L_0x0017
            return r2
        L_0x0017:
            boolean r1 = r6.isEmpty()
            if (r1 == 0) goto L_0x001e
            return r0
        L_0x001e:
            java.util.Comparator<? super E> r1 = r6.g
            boolean r3 = com.google.common.collect.SortedIterables.hasSameComparator(r1, r7)
            if (r3 == 0) goto L_0x0047
            java.util.Iterator r7 = r7.iterator()
            com.google.common.collect.UnmodifiableIterator r3 = r6.iterator()     // Catch:{ ClassCastException | NoSuchElementException -> 0x0046 }
        L_0x002e:
            boolean r4 = r3.hasNext()     // Catch:{ ClassCastException | NoSuchElementException -> 0x0046 }
            if (r4 == 0) goto L_0x0045
            java.lang.Object r4 = r3.next()     // Catch:{ ClassCastException | NoSuchElementException -> 0x0046 }
            java.lang.Object r5 = r7.next()     // Catch:{ ClassCastException | NoSuchElementException -> 0x0046 }
            if (r5 == 0) goto L_0x0044
            int r4 = r1.compare(r4, r5)     // Catch:{ ClassCastException | NoSuchElementException -> 0x0046 }
            if (r4 == 0) goto L_0x002e
        L_0x0044:
            return r2
        L_0x0045:
            return r0
        L_0x0046:
            return r2
        L_0x0047:
            boolean r7 = r6.containsAll(r7)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableSortedSet.equals(java.lang.Object):boolean");
    }

    public final int f() {
        return this.i.f();
    }

    public E first() {
        if (!isEmpty()) {
            return this.i.get(0);
        }
        throw new NoSuchElementException();
    }

    public E floor(E e) {
        int u = u(e, true) - 1;
        if (u == -1) {
            return null;
        }
        return this.i.get(u);
    }

    public E higher(E e) {
        int v = v(e, false);
        if (v == size()) {
            return null;
        }
        return this.i.get(v);
    }

    public final boolean isPartialView() {
        return this.i.isPartialView();
    }

    public E last() {
        if (!isEmpty()) {
            return this.i.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    public E lower(E e) {
        int u = u(e, false) - 1;
        if (u == -1) {
            return null;
        }
        return this.i.get(u);
    }

    public final ImmutableSortedSet<E> o() {
        Comparator<? super E> reverseOrder = Collections.reverseOrder(this.g);
        if (isEmpty()) {
            return ImmutableSortedSet.p(reverseOrder);
        }
        return new RegularImmutableSortedSet(this.i.reverse(), reverseOrder);
    }

    public final ImmutableSortedSet<E> q(E e, boolean z) {
        return t(0, u(e, z));
    }

    public final ImmutableSortedSet<E> r(E e, boolean z, E e2, boolean z2) {
        return s(e, z).q(e2, z2);
    }

    public final ImmutableSortedSet<E> s(E e, boolean z) {
        return t(v(e, z), size());
    }

    public int size() {
        return this.i.size();
    }

    public final RegularImmutableSortedSet<E> t(int i2, int i3) {
        if (i2 == 0 && i3 == size()) {
            return this;
        }
        Comparator<? super E> comparator = this.g;
        if (i2 < i3) {
            return new RegularImmutableSortedSet<>(this.i.subList(i2, i3), comparator);
        }
        return ImmutableSortedSet.p(comparator);
    }

    public final int u(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.i, Preconditions.checkNotNull(e), comparator());
        return binarySearch >= 0 ? z ? binarySearch + 1 : binarySearch : ~binarySearch;
    }

    public final int v(E e, boolean z) {
        int binarySearch = Collections.binarySearch(this.i, Preconditions.checkNotNull(e), comparator());
        return binarySearch >= 0 ? z ? binarySearch : binarySearch + 1 : ~binarySearch;
    }

    public UnmodifiableIterator<E> descendingIterator() {
        return this.i.reverse().iterator();
    }

    public UnmodifiableIterator<E> iterator() {
        return this.i.iterator();
    }
}
