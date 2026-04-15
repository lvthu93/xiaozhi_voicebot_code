package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedSet;
import java.lang.Comparable;
import java.util.NoSuchElementException;

public abstract class ContiguousSet<C extends Comparable> extends ImmutableSortedSet<C> {
    public final DiscreteDomain<C> i;

    public ContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(Ordering.natural());
        this.i = discreteDomain;
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    public static ContiguousSet<Integer> closed(int i2, int i3) {
        return create(Range.closed(Integer.valueOf(i2), Integer.valueOf(i3)), DiscreteDomain.integers());
    }

    public static ContiguousSet<Integer> closedOpen(int i2, int i3) {
        return create(Range.closedOpen(Integer.valueOf(i2), Integer.valueOf(i3)), DiscreteDomain.integers());
    }

    public static <C extends Comparable> ContiguousSet<C> create(Range<C> range, DiscreteDomain<C> discreteDomain) {
        Range<C> range2;
        boolean z;
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(discreteDomain);
        try {
            if (!range.hasLowerBound()) {
                range2 = range.intersection(Range.atLeast(discreteDomain.minValue()));
            } else {
                range2 = range;
            }
            if (!range.hasUpperBound()) {
                range2 = range2.intersection(Range.atMost(discreteDomain.maxValue()));
            }
            if (range2.isEmpty() || range.c.n(discreteDomain).compareTo(range.f.i(discreteDomain)) > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return new EmptyContiguousSet(discreteDomain);
            }
            return new RegularContiguousSet(range2, discreteDomain);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet);

    public ImmutableSortedSet<C> o() {
        return new DescendingImmutableSortedSet(this);
    }

    public abstract Range<C> range();

    public abstract Range<C> range(BoundType boundType, BoundType boundType2);

    /* renamed from: t */
    public abstract ContiguousSet<C> q(C c, boolean z);

    public String toString() {
        return range().toString();
    }

    /* renamed from: u */
    public abstract ContiguousSet<C> r(C c, boolean z, C c2, boolean z2);

    /* renamed from: v */
    public abstract ContiguousSet<C> s(C c, boolean z);

    public static ContiguousSet<Long> closed(long j, long j2) {
        return create(Range.closed(Long.valueOf(j), Long.valueOf(j2)), DiscreteDomain.longs());
    }

    public static ContiguousSet<Long> closedOpen(long j, long j2) {
        return create(Range.closedOpen(Long.valueOf(j), Long.valueOf(j2)), DiscreteDomain.longs());
    }

    public ContiguousSet<C> headSet(C c) {
        return q((Comparable) Preconditions.checkNotNull(c), false);
    }

    public ContiguousSet<C> subSet(C c, C c2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        Preconditions.checkArgument(comparator().compare(c, c2) <= 0);
        return r(c, true, c2, false);
    }

    public ContiguousSet<C> tailSet(C c) {
        return s((Comparable) Preconditions.checkNotNull(c), true);
    }

    public ContiguousSet<C> headSet(C c, boolean z) {
        return q((Comparable) Preconditions.checkNotNull(c), z);
    }

    public ContiguousSet<C> tailSet(C c, boolean z) {
        return s((Comparable) Preconditions.checkNotNull(c), z);
    }

    public ContiguousSet<C> subSet(C c, boolean z, C c2, boolean z2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        Preconditions.checkArgument(comparator().compare(c, c2) <= 0);
        return r(c, z, c2, z2);
    }
}
