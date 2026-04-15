package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Cut;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.SortedLists;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
    public static final ImmutableRangeMap<Comparable<?>, Object> g = new ImmutableRangeMap<>(ImmutableList.of(), ImmutableList.of());
    private static final long serialVersionUID = 0;
    public final transient ImmutableList<Range<K>> c;
    public final transient ImmutableList<V> f;

    public static final class Builder<K extends Comparable<?>, V> {
        public final ArrayList a = Lists.newArrayList();

        public ImmutableRangeMap<K, V> build() {
            Range<Comparable> range = Range.g;
            Ordering<F> onResultOf = Range.RangeLexOrdering.c.onResultOf(Maps.EntryFunction.c);
            ArrayList arrayList = this.a;
            Collections.sort(arrayList, onResultOf);
            ImmutableList.Builder builder = new ImmutableList.Builder(arrayList.size());
            ImmutableList.Builder builder2 = new ImmutableList.Builder(arrayList.size());
            for (int i = 0; i < arrayList.size(); i++) {
                Range range2 = (Range) ((Map.Entry) arrayList.get(i)).getKey();
                if (i > 0) {
                    Range range3 = (Range) ((Map.Entry) arrayList.get(i - 1)).getKey();
                    if (range2.isConnected(range3) && !range2.intersection(range3).isEmpty()) {
                        throw new IllegalArgumentException("Overlapping ranges: range " + range3 + " overlaps with entry " + range2);
                    }
                }
                builder.add((Object) range2);
                builder2.add(((Map.Entry) arrayList.get(i)).getValue());
            }
            return new ImmutableRangeMap<>(builder.build(), builder2.build());
        }

        public Builder<K, V> put(Range<K> range, V v) {
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(v);
            Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", (Object) range);
            this.a.add(Maps.immutableEntry(range, v));
            return this;
        }

        public Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
            for (Map.Entry next : rangeMap.asMapOfRanges().entrySet()) {
                put((Range) next.getKey(), next.getValue());
            }
            return this;
        }
    }

    public static class SerializedForm<K extends Comparable<?>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final ImmutableMap<Range<K>, V> c;

        public SerializedForm(ImmutableMap<Range<K>, V> immutableMap) {
            this.c = immutableMap;
        }

        public Object readResolve() {
            ImmutableMap<Range<K>, V> immutableMap = this.c;
            if (immutableMap.isEmpty()) {
                return ImmutableRangeMap.of();
            }
            Builder builder = new Builder();
            UnmodifiableIterator<Map.Entry<Range<K>, V>> it = immutableMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry next = it.next();
                builder.put((Range) next.getKey(), next.getValue());
            }
            return builder.build();
        }
    }

    public ImmutableRangeMap(ImmutableList<Range<K>> immutableList, ImmutableList<V> immutableList2) {
        this.c = immutableList;
        this.f = immutableList2;
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap) rangeMap;
        }
        Map<Range<K>, ? extends V> asMapOfRanges = rangeMap.asMapOfRanges();
        ImmutableList.Builder builder = new ImmutableList.Builder(asMapOfRanges.size());
        ImmutableList.Builder builder2 = new ImmutableList.Builder(asMapOfRanges.size());
        for (Map.Entry next : asMapOfRanges.entrySet()) {
            builder.add(next.getKey());
            builder2.add(next.getValue());
        }
        return new ImmutableRangeMap<>(builder.build(), builder2.build());
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
        return g;
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        if (obj instanceof RangeMap) {
            return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
        }
        return false;
    }

    public V get(K k) {
        Range<Comparable> range = Range.g;
        Range.LowerBoundFn lowerBoundFn = Range.LowerBoundFn.c;
        Cut.BelowValue belowValue = new Cut.BelowValue(k);
        SortedLists.KeyPresentBehavior.AnonymousClass1 r2 = SortedLists.KeyPresentBehavior.c;
        SortedLists.KeyAbsentBehavior.AnonymousClass1 r3 = SortedLists.KeyAbsentBehavior.c;
        ImmutableList<Range<K>> immutableList = this.c;
        int binarySearch = SortedLists.binarySearch(immutableList, lowerBoundFn, belowValue, (SortedLists.KeyPresentBehavior) r2, (SortedLists.KeyAbsentBehavior) r3);
        if (binarySearch != -1 && immutableList.get(binarySearch).contains(k)) {
            return this.f.get(binarySearch);
        }
        return null;
    }

    public Map.Entry<Range<K>, V> getEntry(K k) {
        Range<Comparable> range = Range.g;
        Range.LowerBoundFn lowerBoundFn = Range.LowerBoundFn.c;
        Cut.BelowValue belowValue = new Cut.BelowValue(k);
        SortedLists.KeyPresentBehavior.AnonymousClass1 r2 = SortedLists.KeyPresentBehavior.c;
        SortedLists.KeyAbsentBehavior.AnonymousClass1 r3 = SortedLists.KeyAbsentBehavior.c;
        ImmutableList<Range<K>> immutableList = this.c;
        int binarySearch = SortedLists.binarySearch(immutableList, lowerBoundFn, belowValue, (SortedLists.KeyPresentBehavior) r2, (SortedLists.KeyAbsentBehavior) r3);
        if (binarySearch == -1) {
            return null;
        }
        Range range2 = immutableList.get(binarySearch);
        if (range2.contains(k)) {
            return Maps.immutableEntry(range2, this.f.get(binarySearch));
        }
        return null;
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    @Deprecated
    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void putCoalescing(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    public Range<K> span() {
        ImmutableList<Range<K>> immutableList = this.c;
        if (!immutableList.isEmpty()) {
            return new Range<>(immutableList.get(0).c, immutableList.get(immutableList.size() - 1).f);
        }
        throw new NoSuchElementException();
    }

    public String toString() {
        return asMapOfRanges().toString();
    }

    public Object writeReplace() {
        return new SerializedForm(asMapOfRanges());
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V v) {
        return new ImmutableRangeMap<>(ImmutableList.of(range), ImmutableList.of(v));
    }

    public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
        ImmutableList<Range<K>> immutableList = this.c;
        if (immutableList.isEmpty()) {
            return ImmutableMap.of();
        }
        ImmutableList<Range<K>> reverse = immutableList.reverse();
        Range<Comparable> range = Range.g;
        return new ImmutableSortedMap(new RegularImmutableSortedSet(reverse, Range.RangeLexOrdering.c.reverse()), this.f.reverse(), (ImmutableSortedMap) null);
    }

    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        ImmutableList<Range<K>> immutableList = this.c;
        if (immutableList.isEmpty()) {
            return ImmutableMap.of();
        }
        Range<Comparable> range = Range.g;
        return new ImmutableSortedMap(new RegularImmutableSortedSet(immutableList, Range.RangeLexOrdering.c), this.f, (ImmutableSortedMap) null);
    }

    public ImmutableRangeMap<K, V> subRangeMap(final Range<K> range) {
        if (((Range) Preconditions.checkNotNull(range)).isEmpty()) {
            return of();
        }
        ImmutableList<Range<K>> immutableList = this.c;
        if (immutableList.isEmpty() || range.encloses(span())) {
            return this;
        }
        Range.UpperBoundFn upperBoundFn = Range.UpperBoundFn.c;
        SortedLists.KeyPresentBehavior.AnonymousClass4 r2 = SortedLists.KeyPresentBehavior.h;
        SortedLists.KeyAbsentBehavior.AnonymousClass2 r3 = SortedLists.KeyAbsentBehavior.f;
        final int binarySearch = SortedLists.binarySearch(immutableList, upperBoundFn, range.c, (SortedLists.KeyPresentBehavior) r2, (SortedLists.KeyAbsentBehavior) r3);
        int binarySearch2 = SortedLists.binarySearch(immutableList, Range.LowerBoundFn.c, range.f, (SortedLists.KeyPresentBehavior) SortedLists.KeyPresentBehavior.c, (SortedLists.KeyAbsentBehavior) r3);
        if (binarySearch >= binarySearch2) {
            return of();
        }
        final int i = binarySearch2 - binarySearch;
        return new ImmutableRangeMap<K, V>(new ImmutableList<Range<K>>() {
            public final boolean isPartialView() {
                return true;
            }

            public int size() {
                return i;
            }

            public Range<K> get(int i2) {
                int i3 = i;
                Preconditions.checkElementIndex(i2, i3);
                int i4 = binarySearch;
                ImmutableRangeMap immutableRangeMap = ImmutableRangeMap.this;
                if (i2 == 0 || i2 == i3 - 1) {
                    return immutableRangeMap.c.get(i2 + i4).intersection(range);
                }
                return immutableRangeMap.c.get(i2 + i4);
            }
        }, this.f.subList(binarySearch, binarySearch2)) {
            public /* bridge */ /* synthetic */ Map asDescendingMapOfRanges() {
                return ImmutableRangeMap.super.asDescendingMapOfRanges();
            }

            public /* bridge */ /* synthetic */ Map asMapOfRanges() {
                return ImmutableRangeMap.super.asMapOfRanges();
            }

            public ImmutableRangeMap<K, V> subRangeMap(Range<K> range) {
                Range range2 = range;
                if (range2.isConnected(range)) {
                    return this.subRangeMap(range.intersection(range2));
                }
                return ImmutableRangeMap.of();
            }
        };
    }
}
