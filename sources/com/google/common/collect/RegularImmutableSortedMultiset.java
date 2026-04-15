package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import java.util.Collections;
import java.util.Comparator;

final class RegularImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
    public static final long[] n = {0};
    public static final ImmutableSortedMultiset<Comparable> o = new RegularImmutableSortedMultiset(Ordering.natural());
    public final transient RegularImmutableSortedSet<E> j;
    public final transient long[] k;
    public final transient int l;
    public final transient int m;

    public RegularImmutableSortedMultiset(Comparator<? super E> comparator) {
        this.j = ImmutableSortedSet.p(comparator);
        this.k = n;
        this.l = 0;
        this.m = 0;
    }

    public int count(Object obj) {
        RegularImmutableSortedSet<E> regularImmutableSortedSet = this.j;
        regularImmutableSortedSet.getClass();
        int i = -1;
        if (obj != null) {
            try {
                int binarySearch = Collections.binarySearch(regularImmutableSortedSet.i, obj, regularImmutableSortedSet.g);
                if (binarySearch >= 0) {
                    i = binarySearch;
                }
            } catch (ClassCastException unused) {
            }
        }
        if (i < 0) {
            return 0;
        }
        int i2 = this.l + i;
        long[] jArr = this.k;
        return (int) (jArr[i2 + 1] - jArr[i2]);
    }

    public Multiset.Entry<E> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return j(0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
        r0 = r3.k.length - 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isPartialView() {
        /*
            r3 = this;
            int r0 = r3.l
            r1 = 1
            if (r0 > 0) goto L_0x000f
            long[] r0 = r3.k
            int r0 = r0.length
            int r0 = r0 - r1
            int r2 = r3.m
            if (r2 >= r0) goto L_0x000e
            goto L_0x000f
        L_0x000e:
            r1 = 0
        L_0x000f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableSortedMultiset.isPartialView():boolean");
    }

    public final Multiset.Entry<E> j(int i) {
        E e = this.j.asList().get(i);
        int i2 = this.l + i;
        long[] jArr = this.k;
        return Multisets.immutableEntry(e, (int) (jArr[i2 + 1] - jArr[i2]));
    }

    public Multiset.Entry<E> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return j(this.m - 1);
    }

    public final ImmutableSortedMultiset<E> m(int i, int i2) {
        int i3 = this.m;
        Preconditions.checkPositionIndexes(i, i2, i3);
        if (i == i2) {
            return ImmutableSortedMultiset.l(comparator());
        }
        if (i == 0 && i2 == i3) {
            return this;
        }
        return new RegularImmutableSortedMultiset(this.j.t(i, i2), this.k, this.l + i, i2 - i);
    }

    public int size() {
        int i = this.m;
        int i2 = this.l;
        long[] jArr = this.k;
        return y3.b(jArr[i + i2] - jArr[i2]);
    }

    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return m(0, this.j.u(e, Preconditions.checkNotNull(boundType) == BoundType.CLOSED));
    }

    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return m(this.j.v(e, Preconditions.checkNotNull(boundType) == BoundType.CLOSED), this.m);
    }

    public ImmutableSortedSet<E> elementSet() {
        return this.j;
    }

    public RegularImmutableSortedMultiset(RegularImmutableSortedSet<E> regularImmutableSortedSet, long[] jArr, int i, int i2) {
        this.j = regularImmutableSortedSet;
        this.k = jArr;
        this.l = i;
        this.m = i2;
    }
}
