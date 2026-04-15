package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Cut;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Range;
import com.google.common.collect.SortedLists;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public final class ImmutableRangeSet<C extends Comparable> extends AbstractRangeSet<C> implements Serializable {
    public static final ImmutableRangeSet<Comparable<?>> g = new ImmutableRangeSet<>(ImmutableList.of());
    public static final ImmutableRangeSet<Comparable<?>> h = new ImmutableRangeSet<>(ImmutableList.of(Range.all()));
    public final transient ImmutableList<Range<C>> c;
    public transient ImmutableRangeSet<C> f;

    public final class AsSet extends ImmutableSortedSet<C> {
        public final DiscreteDomain<C> i;
        public transient Integer j;

        public AsSet(DiscreteDomain<C> discreteDomain) {
            super(Ordering.natural());
            this.i = discreteDomain;
        }

        public boolean contains(Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                return ImmutableRangeSet.this.contains((Comparable) obj);
            } catch (ClassCastException unused) {
                return false;
            }
        }

        public final boolean isPartialView() {
            return ImmutableRangeSet.this.c.isPartialView();
        }

        public final ImmutableSortedSet<C> o() {
            return new DescendingImmutableSortedSet(this);
        }

        public final ImmutableSortedSet q(Object obj, boolean z) {
            return ImmutableRangeSet.this.subRangeSet(Range.upTo((Comparable) obj, BoundType.b(z))).asSet(this.i);
        }

        public final ImmutableSortedSet r(Object obj, boolean z, Object obj2, boolean z2) {
            Comparable comparable = (Comparable) obj;
            Comparable comparable2 = (Comparable) obj2;
            if (!z && !z2) {
                Range<Comparable> range = Range.g;
                if (comparable.compareTo(comparable2) == 0) {
                    return ImmutableSortedSet.of();
                }
            }
            return ImmutableRangeSet.this.subRangeSet(Range.range(comparable, BoundType.b(z), comparable2, BoundType.b(z2))).asSet(this.i);
        }

        public final ImmutableSortedSet s(Object obj, boolean z) {
            return ImmutableRangeSet.this.subRangeSet(Range.downTo((Comparable) obj, BoundType.b(z))).asSet(this.i);
        }

        public int size() {
            Integer num = this.j;
            if (num == null) {
                UnmodifiableIterator<Range<C>> it = ImmutableRangeSet.this.c.iterator();
                long j2 = 0;
                while (it.hasNext()) {
                    j2 += (long) ContiguousSet.create(it.next(), this.i).size();
                    if (j2 >= 2147483647L) {
                        break;
                    }
                }
                num = Integer.valueOf(y3.b(j2));
                this.j = num;
            }
            return num.intValue();
        }

        public String toString() {
            return ImmutableRangeSet.this.c.toString();
        }

        public Object writeReplace() {
            return new AsSetSerializedForm(ImmutableRangeSet.this.c, this.i);
        }

        public UnmodifiableIterator<C> descendingIterator() {
            return new AbstractIterator<C>() {
                public final UnmodifiableIterator g;
                public UnmodifiableIterator h = Iterators.ArrayItr.i;

                {
                    this.g = ImmutableRangeSet.this.c.reverse().iterator();
                }

                public final Object computeNext() {
                    while (!this.h.hasNext()) {
                        UnmodifiableIterator unmodifiableIterator = this.g;
                        if (unmodifiableIterator.hasNext()) {
                            this.h = ContiguousSet.create((Range) unmodifiableIterator.next(), AsSet.this.i).descendingIterator();
                        } else {
                            this.c = AbstractIterator.State.DONE;
                            return null;
                        }
                    }
                    return (Comparable) this.h.next();
                }
            };
        }

        public UnmodifiableIterator<C> iterator() {
            return new AbstractIterator<C>() {
                public final UnmodifiableIterator g;
                public UnmodifiableIterator h = Iterators.ArrayItr.i;

                {
                    this.g = ImmutableRangeSet.this.c.iterator();
                }

                public final Object computeNext() {
                    while (!this.h.hasNext()) {
                        UnmodifiableIterator unmodifiableIterator = this.g;
                        if (unmodifiableIterator.hasNext()) {
                            this.h = ContiguousSet.create((Range) unmodifiableIterator.next(), AsSet.this.i).iterator();
                        } else {
                            this.c = AbstractIterator.State.DONE;
                            return null;
                        }
                    }
                    return (Comparable) this.h.next();
                }
            };
        }
    }

    public static class AsSetSerializedForm<C extends Comparable> implements Serializable {
        public final ImmutableList<Range<C>> c;
        public final DiscreteDomain<C> f;

        public AsSetSerializedForm(ImmutableList<Range<C>> immutableList, DiscreteDomain<C> discreteDomain) {
            this.c = immutableList;
            this.f = discreteDomain;
        }

        public Object readResolve() {
            return new ImmutableRangeSet(this.c).asSet(this.f);
        }
    }

    public static class Builder<C extends Comparable<?>> {
        public final ArrayList a = Lists.newArrayList();

        public Builder<C> add(Range<C> range) {
            Preconditions.checkArgument(!range.isEmpty(), "range must not be empty, but was %s", (Object) range);
            this.a.add(range);
            return this;
        }

        public Builder<C> addAll(RangeSet<C> rangeSet) {
            return addAll(rangeSet.asRanges());
        }

        public ImmutableRangeSet<C> build() {
            ArrayList arrayList = this.a;
            ImmutableList.Builder builder = new ImmutableList.Builder(arrayList.size());
            Range<Comparable> range = Range.g;
            Collections.sort(arrayList, Range.RangeLexOrdering.c);
            PeekingIterator peekingIterator = Iterators.peekingIterator(arrayList.iterator());
            while (peekingIterator.hasNext()) {
                Range range2 = (Range) peekingIterator.next();
                while (peekingIterator.hasNext()) {
                    Range range3 = (Range) peekingIterator.peek();
                    if (!range2.isConnected(range3)) {
                        break;
                    }
                    Preconditions.checkArgument(range2.intersection(range3).isEmpty(), "Overlapping ranges not permitted but found %s overlapping %s", (Object) range2, (Object) range3);
                    range2 = range2.span((Range) peekingIterator.next());
                }
                builder.add((Object) range2);
            }
            ImmutableList build = builder.build();
            if (build.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (build.size() != 1 || !((Range) Iterables.getOnlyElement(build)).equals(Range.all())) {
                return new ImmutableRangeSet<>(build);
            }
            return ImmutableRangeSet.h;
        }

        public Builder<C> addAll(Iterable<Range<C>> iterable) {
            for (Range<C> add : iterable) {
                add(add);
            }
            return this;
        }
    }

    public final class ComplementRanges extends ImmutableList<Range<C>> {
        public final boolean g;
        public final boolean h;
        public final int i;

        public ComplementRanges() {
            boolean hasLowerBound = ImmutableRangeSet.this.c.get(0).hasLowerBound();
            this.g = hasLowerBound;
            ImmutableList<Range<C>> immutableList = ImmutableRangeSet.this.c;
            boolean hasUpperBound = ((Range) Iterables.getLast(immutableList)).hasUpperBound();
            this.h = hasUpperBound;
            int size = immutableList.size() - 1;
            size = hasLowerBound ? size + 1 : size;
            this.i = hasUpperBound ? size + 1 : size;
        }

        public final boolean isPartialView() {
            return true;
        }

        public int size() {
            return this.i;
        }

        public Range<C> get(int i2) {
            Cut cut;
            Cut cut2;
            int i3 = this.i;
            Preconditions.checkElementIndex(i2, i3);
            ImmutableRangeSet immutableRangeSet = ImmutableRangeSet.this;
            boolean z = this.g;
            if (!z) {
                cut = immutableRangeSet.c.get(i2).f;
            } else if (i2 == 0) {
                cut = Cut.BelowAll.f;
            } else {
                cut = immutableRangeSet.c.get(i2 - 1).f;
            }
            if (!this.h || i2 != i3 - 1) {
                cut2 = immutableRangeSet.c.get(i2 + (z ^ true ? 1 : 0)).c;
            } else {
                cut2 = Cut.AboveAll.f;
            }
            return new Range<>(cut, cut2);
        }
    }

    public static final class SerializedForm<C extends Comparable> implements Serializable {
        public final ImmutableList<Range<C>> c;

        public SerializedForm(ImmutableList<Range<C>> immutableList) {
            this.c = immutableList;
        }

        public Object readResolve() {
            ImmutableList<Range<C>> immutableList = this.c;
            if (immutableList.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (immutableList.equals(ImmutableList.of(Range.all()))) {
                return ImmutableRangeSet.h;
            }
            return new ImmutableRangeSet(immutableList);
        }
    }

    public ImmutableRangeSet(ImmutableList<Range<C>> immutableList) {
        this.c = immutableList;
    }

    public static <C extends Comparable<?>> Builder<C> builder() {
        return new Builder<>();
    }

    public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> rangeSet) {
        Preconditions.checkNotNull(rangeSet);
        if (rangeSet.isEmpty()) {
            return of();
        }
        if (rangeSet.encloses(Range.all())) {
            return h;
        }
        if (rangeSet instanceof ImmutableRangeSet) {
            ImmutableRangeSet<C> immutableRangeSet = (ImmutableRangeSet) rangeSet;
            if (!immutableRangeSet.c.isPartialView()) {
                return immutableRangeSet;
            }
        }
        return new ImmutableRangeSet<>(ImmutableList.copyOf(rangeSet.asRanges()));
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of() {
        return g;
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> unionOf(Iterable<Range<C>> iterable) {
        return copyOf(TreeRangeSet.create(iterable));
    }

    @Deprecated
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void addAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    public ImmutableSortedSet<C> asSet(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        if (isEmpty()) {
            return ImmutableSortedSet.of();
        }
        Range<C> canonical = span().canonical(discreteDomain);
        if (canonical.hasLowerBound()) {
            if (!canonical.hasUpperBound()) {
                try {
                    discreteDomain.maxValue();
                } catch (NoSuchElementException unused) {
                    throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
                }
            }
            return new AsSet(discreteDomain);
        }
        throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean contains(Comparable comparable) {
        return super.contains(comparable);
    }

    public ImmutableRangeSet<C> difference(RangeSet<C> rangeSet) {
        TreeRangeSet create = TreeRangeSet.create(this);
        create.removeAll(rangeSet);
        return copyOf(create);
    }

    public boolean encloses(Range<C> range) {
        ImmutableList<Range<C>> immutableList = this.c;
        Range<Comparable> range2 = Range.g;
        int binarySearch = SortedLists.binarySearch(immutableList, Range.LowerBoundFn.c, range.c, Ordering.natural(), SortedLists.KeyPresentBehavior.c, SortedLists.KeyAbsentBehavior.c);
        if (binarySearch == -1 || !this.c.get(binarySearch).encloses(range)) {
            return false;
        }
        return true;
    }

    public /* bridge */ /* synthetic */ boolean enclosesAll(RangeSet rangeSet) {
        return super.enclosesAll(rangeSet);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public ImmutableRangeSet<C> intersection(RangeSet<C> rangeSet) {
        TreeRangeSet create = TreeRangeSet.create(this);
        create.removeAll(rangeSet.complement());
        return copyOf(create);
    }

    public boolean intersects(Range<C> range) {
        ImmutableList<Range<C>> immutableList = this.c;
        Range<Comparable> range2 = Range.g;
        int binarySearch = SortedLists.binarySearch(immutableList, Range.LowerBoundFn.c, range.c, Ordering.natural(), SortedLists.KeyPresentBehavior.c, SortedLists.KeyAbsentBehavior.f);
        ImmutableList<Range<C>> immutableList2 = this.c;
        if (binarySearch < immutableList2.size() && immutableList2.get(binarySearch).isConnected(range) && !immutableList2.get(binarySearch).intersection(range).isEmpty()) {
            return true;
        }
        if (binarySearch > 0) {
            int i = binarySearch - 1;
            if (!immutableList2.get(i).isConnected(range) || immutableList2.get(i).intersection(range).isEmpty()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    public Range<C> rangeContaining(C c2) {
        ImmutableList<Range<C>> immutableList = this.c;
        Range<Comparable> range = Range.g;
        int binarySearch = SortedLists.binarySearch(immutableList, Range.LowerBoundFn.c, new Cut.BelowValue(c2), Ordering.natural(), SortedLists.KeyPresentBehavior.c, SortedLists.KeyAbsentBehavior.c);
        if (binarySearch != -1) {
            Range<C> range2 = this.c.get(binarySearch);
            if (range2.contains(c2)) {
                return range2;
            }
        }
        return null;
    }

    @Deprecated
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void removeAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    public Range<C> span() {
        ImmutableList<Range<C>> immutableList = this.c;
        if (!immutableList.isEmpty()) {
            return new Range<>(immutableList.get(0).c, immutableList.get(immutableList.size() - 1).f);
        }
        throw new NoSuchElementException();
    }

    public ImmutableRangeSet<C> union(RangeSet<C> rangeSet) {
        return unionOf(Iterables.concat(asRanges(), rangeSet.asRanges()));
    }

    public Object writeReplace() {
        return new SerializedForm(this.c);
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return of();
        }
        if (range.equals(Range.all())) {
            return h;
        }
        return new ImmutableRangeSet<>(ImmutableList.of(range));
    }

    @Deprecated
    public void addAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    public ImmutableSet<Range<C>> asDescendingSetOfRanges() {
        ImmutableList<Range<C>> immutableList = this.c;
        if (immutableList.isEmpty()) {
            return ImmutableSet.of();
        }
        ImmutableList<Range<C>> reverse = immutableList.reverse();
        Range<Comparable> range = Range.g;
        return new RegularImmutableSortedSet(reverse, Range.RangeLexOrdering.c.reverse());
    }

    public ImmutableSet<Range<C>> asRanges() {
        ImmutableList<Range<C>> immutableList = this.c;
        if (immutableList.isEmpty()) {
            return ImmutableSet.of();
        }
        Range<Comparable> range = Range.g;
        return new RegularImmutableSortedSet(immutableList, Range.RangeLexOrdering.c);
    }

    public ImmutableRangeSet<C> complement() {
        ImmutableRangeSet<C> immutableRangeSet = this.f;
        if (immutableRangeSet != null) {
            return immutableRangeSet;
        }
        ImmutableList<Range<C>> immutableList = this.c;
        if (immutableList.isEmpty()) {
            ImmutableRangeSet<Comparable<?>> immutableRangeSet2 = h;
            this.f = immutableRangeSet2;
            return immutableRangeSet2;
        } else if (immutableList.size() != 1 || !immutableList.get(0).equals(Range.all())) {
            ImmutableRangeSet<C> immutableRangeSet3 = new ImmutableRangeSet<>(new ComplementRanges(), this);
            this.f = immutableRangeSet3;
            return immutableRangeSet3;
        } else {
            ImmutableRangeSet<C> of = of();
            this.f = of;
            return of;
        }
    }

    public /* bridge */ /* synthetic */ boolean enclosesAll(Iterable iterable) {
        return super.enclosesAll(iterable);
    }

    @Deprecated
    public void removeAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    public ImmutableRangeSet<C> subRangeSet(final Range<C> range) {
        final int i;
        int i2;
        if (!isEmpty()) {
            Range span = span();
            if (range.encloses(span)) {
                return this;
            }
            if (range.isConnected(span)) {
                ImmutableList<Range<C>> immutableList = this.c;
                if (immutableList.isEmpty() || range.isEmpty()) {
                    immutableList = ImmutableList.of();
                } else if (!range.encloses(span())) {
                    boolean hasLowerBound = range.hasLowerBound();
                    SortedLists.KeyAbsentBehavior.AnonymousClass2 r3 = SortedLists.KeyAbsentBehavior.f;
                    if (hasLowerBound) {
                        i = SortedLists.binarySearch(immutableList, Range.UpperBoundFn.c, range.c, (SortedLists.KeyPresentBehavior) SortedLists.KeyPresentBehavior.h, (SortedLists.KeyAbsentBehavior) r3);
                    } else {
                        i = 0;
                    }
                    if (range.hasUpperBound()) {
                        i2 = SortedLists.binarySearch(immutableList, Range.LowerBoundFn.c, range.f, (SortedLists.KeyPresentBehavior) SortedLists.KeyPresentBehavior.g, (SortedLists.KeyAbsentBehavior) r3);
                    } else {
                        i2 = immutableList.size();
                    }
                    final int i3 = i2 - i;
                    if (i3 == 0) {
                        immutableList = ImmutableList.of();
                    } else {
                        immutableList = new ImmutableList<Range<Comparable>>() {
                            public final boolean isPartialView() {
                                return true;
                            }

                            public int size() {
                                return i3;
                            }

                            public Range<Comparable> get(int i2) {
                                int i3 = i3;
                                Preconditions.checkElementIndex(i2, i3);
                                int i4 = i;
                                ImmutableRangeSet immutableRangeSet = ImmutableRangeSet.this;
                                if (i2 == 0 || i2 == i3 - 1) {
                                    return immutableRangeSet.c.get(i2 + i4).intersection(range);
                                }
                                return immutableRangeSet.c.get(i2 + i4);
                            }
                        };
                    }
                }
                return new ImmutableRangeSet<>(immutableList);
            }
        }
        return of();
    }

    public ImmutableRangeSet(ImmutableList<Range<C>> immutableList, ImmutableRangeSet<C> immutableRangeSet) {
        this.c = immutableList;
        this.f = immutableRangeSet;
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> copyOf(Iterable<Range<C>> iterable) {
        return new Builder().addAll(iterable).build();
    }
}
