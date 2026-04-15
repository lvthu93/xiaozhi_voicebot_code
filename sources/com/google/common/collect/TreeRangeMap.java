package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Cut;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

public final class TreeRangeMap<K extends Comparable, V> implements RangeMap<K, V> {
    public static final RangeMap f = new RangeMap() {
        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        public void clear() {
        }

        public Object get(Comparable comparable) {
            return null;
        }

        public Map.Entry<Range, Object> getEntry(Comparable comparable) {
            return null;
        }

        public void put(Range range, Object obj) {
            Preconditions.checkNotNull(range);
            throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
        }

        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
            }
        }

        public void putCoalescing(Range range, Object obj) {
            Preconditions.checkNotNull(range);
            throw new IllegalArgumentException("Cannot insert range " + range + " into an empty subRangeMap");
        }

        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        public Range span() {
            throw new NoSuchElementException();
        }

        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    };
    public final TreeMap c = Maps.newTreeMap();

    public final class AsMapOfRanges extends Maps.IteratorBasedAbstractMap<Range<K>, V> {
        public final Iterable<Map.Entry<Range<K>, V>> c;

        public AsMapOfRanges(Collection collection) {
            this.c = collection;
        }

        public final Iterator<Map.Entry<Range<K>, V>> a() {
            return this.c.iterator();
        }

        public boolean containsKey(Object obj) {
            return get(obj) != null;
        }

        public V get(Object obj) {
            if (!(obj instanceof Range)) {
                return null;
            }
            Range range = (Range) obj;
            RangeMapEntry rangeMapEntry = (RangeMapEntry) TreeRangeMap.this.c.get(range.c);
            if (rangeMapEntry == null || !rangeMapEntry.getKey().equals(range)) {
                return null;
            }
            return rangeMapEntry.getValue();
        }

        public int size() {
            return TreeRangeMap.this.c.size();
        }
    }

    public static final class RangeMapEntry<K extends Comparable, V> extends AbstractMapEntry<Range<K>, V> {
        public final Range<K> c;
        public final V f;

        public RangeMapEntry() {
            throw null;
        }

        public RangeMapEntry(Range<K> range, V v) {
            this.c = range;
            this.f = v;
        }

        public boolean contains(K k) {
            return this.c.contains(k);
        }

        public V getValue() {
            return this.f;
        }

        public Range<K> getKey() {
            return this.c;
        }
    }

    public class SubRangeMap implements RangeMap<K, V> {
        public final Range<K> c;

        public class SubRangeMapAsMap extends AbstractMap<Range<K>, V> {
            public SubRangeMapAsMap() {
            }

            public static boolean a(SubRangeMapAsMap subRangeMapAsMap, Predicate predicate) {
                subRangeMapAsMap.getClass();
                ArrayList<Range> newArrayList = Lists.newArrayList();
                for (Map.Entry entry : subRangeMapAsMap.entrySet()) {
                    if (predicate.apply(entry)) {
                        newArrayList.add(entry.getKey());
                    }
                }
                for (Range remove : newArrayList) {
                    TreeRangeMap.this.remove(remove);
                }
                return !newArrayList.isEmpty();
            }

            public UnmodifiableIterator b() {
                SubRangeMap subRangeMap = SubRangeMap.this;
                if (subRangeMap.c.isEmpty()) {
                    return Iterators.ArrayItr.i;
                }
                TreeRangeMap treeRangeMap = TreeRangeMap.this;
                TreeMap treeMap = treeRangeMap.c;
                Range<K> range = subRangeMap.c;
                final Iterator it = treeRangeMap.c.tailMap((Cut) MoreObjects.firstNonNull(treeMap.floorKey(range.c), range.c), true).values().iterator();
                return new AbstractIterator<Map.Entry<Range<Comparable>, Object>>() {
                    public final Object computeNext() {
                        RangeMapEntry rangeMapEntry;
                        Cut<C> cut;
                        SubRangeMap subRangeMap;
                        do {
                            Iterator it = it;
                            boolean hasNext = it.hasNext();
                            AbstractIterator.State state = AbstractIterator.State.DONE;
                            if (hasNext) {
                                rangeMapEntry = (RangeMapEntry) it.next();
                                Cut<C> cut2 = rangeMapEntry.c.c;
                                SubRangeMapAsMap subRangeMapAsMap = SubRangeMapAsMap.this;
                                if (cut2.compareTo(SubRangeMap.this.c.f) >= 0) {
                                    this.c = state;
                                } else {
                                    cut = rangeMapEntry.c.f;
                                    subRangeMap = SubRangeMap.this;
                                }
                            } else {
                                this.c = state;
                            }
                            return null;
                        } while (cut.compareTo(subRangeMap.c.c) <= 0);
                        return Maps.immutableEntry(rangeMapEntry.getKey().intersection(subRangeMap.c), rangeMapEntry.getValue());
                    }
                };
            }

            public void clear() {
                SubRangeMap.this.clear();
            }

            public boolean containsKey(Object obj) {
                return get(obj) != null;
            }

            public Set<Map.Entry<Range<K>, V>> entrySet() {
                return new Maps.EntrySet<Range<K>, V>() {
                    public final Map<Range<K>, V> a() {
                        return SubRangeMapAsMap.this;
                    }

                    public boolean isEmpty() {
                        return !iterator().hasNext();
                    }

                    public Iterator<Map.Entry<Range<K>, V>> iterator() {
                        return SubRangeMapAsMap.this.b();
                    }

                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.a(SubRangeMapAsMap.this, Predicates.not(Predicates.in(collection)));
                    }

                    public int size() {
                        return Iterators.size(iterator());
                    }
                };
            }

            public V get(Object obj) {
                RangeMapEntry rangeMapEntry;
                SubRangeMap subRangeMap = SubRangeMap.this;
                try {
                    if (obj instanceof Range) {
                        Range range = (Range) obj;
                        Range<K> range2 = subRangeMap.c;
                        Range<K> range3 = subRangeMap.c;
                        if (range2.encloses(range)) {
                            boolean isEmpty = range.isEmpty();
                            Cut<C> cut = range.c;
                            if (!isEmpty) {
                                int compareTo = cut.compareTo(range3.c);
                                TreeRangeMap treeRangeMap = TreeRangeMap.this;
                                if (compareTo == 0) {
                                    Map.Entry<K, V> floorEntry = treeRangeMap.c.floorEntry(cut);
                                    if (floorEntry != null) {
                                        rangeMapEntry = (RangeMapEntry) floorEntry.getValue();
                                    } else {
                                        rangeMapEntry = null;
                                    }
                                } else {
                                    rangeMapEntry = (RangeMapEntry) treeRangeMap.c.get(cut);
                                }
                                if (rangeMapEntry != null && rangeMapEntry.getKey().isConnected(range3) && rangeMapEntry.getKey().intersection(range3).equals(range)) {
                                    return rangeMapEntry.getValue();
                                }
                            }
                        }
                    }
                } catch (ClassCastException unused) {
                }
                return null;
            }

            public Set<Range<K>> keySet() {
                return new Maps.KeySet<Range<K>, V>(this) {
                    public boolean remove(Object obj) {
                        return SubRangeMapAsMap.this.remove(obj) != null;
                    }

                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.a(SubRangeMapAsMap.this, Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.EntryFunction.c));
                    }
                };
            }

            public V remove(Object obj) {
                V v = get(obj);
                if (v == null) {
                    return null;
                }
                TreeRangeMap.this.remove((Range) obj);
                return v;
            }

            public Collection<V> values() {
                return new Maps.Values<Range<K>, V>(this) {
                    public boolean removeAll(Collection<?> collection) {
                        return SubRangeMapAsMap.a(SubRangeMapAsMap.this, Predicates.compose(Predicates.in(collection), Maps.EntryFunction.f));
                    }

                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.a(SubRangeMapAsMap.this, Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.EntryFunction.f));
                    }
                };
            }
        }

        public SubRangeMap(Range<K> range) {
            this.c = range;
        }

        /* JADX WARNING: type inference failed for: r0v0, types: [java.util.Map<com.google.common.collect.Range<K>, V>, com.google.common.collect.TreeRangeMap$SubRangeMap$1] */
        public Map<Range<K>, V> asDescendingMapOfRanges() {
            return new TreeRangeMap<K, V>.SubRangeMap.SubRangeMapAsMap() {
                public final UnmodifiableIterator b() {
                    SubRangeMap subRangeMap = SubRangeMap.this;
                    if (subRangeMap.c.isEmpty()) {
                        return Iterators.ArrayItr.i;
                    }
                    final Iterator<V> it = TreeRangeMap.this.c.headMap(subRangeMap.c.f, false).descendingMap().values().iterator();
                    return new AbstractIterator<Map.Entry<Range<Comparable>, Object>>() {
                        public final Object computeNext() {
                            Iterator it = it;
                            boolean hasNext = it.hasNext();
                            AbstractIterator.State state = AbstractIterator.State.DONE;
                            if (hasNext) {
                                RangeMapEntry rangeMapEntry = (RangeMapEntry) it.next();
                                Cut<C> cut = rangeMapEntry.c.f;
                                AnonymousClass1 r3 = AnonymousClass1.this;
                                if (cut.compareTo(SubRangeMap.this.c.c) > 0) {
                                    return Maps.immutableEntry(rangeMapEntry.getKey().intersection(SubRangeMap.this.c), rangeMapEntry.getValue());
                                }
                                this.c = state;
                            } else {
                                this.c = state;
                            }
                            return null;
                        }
                    };
                }
            };
        }

        public Map<Range<K>, V> asMapOfRanges() {
            return new SubRangeMapAsMap();
        }

        public void clear() {
            TreeRangeMap.this.remove(this.c);
        }

        public boolean equals(Object obj) {
            if (obj instanceof RangeMap) {
                return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
            }
            return false;
        }

        public V get(K k) {
            if (this.c.contains(k)) {
                return TreeRangeMap.this.get(k);
            }
            return null;
        }

        public Map.Entry<Range<K>, V> getEntry(K k) {
            Map.Entry entry;
            Range<K> range = this.c;
            if (!range.contains(k) || (entry = TreeRangeMap.this.getEntry(k)) == null) {
                return null;
            }
            return Maps.immutableEntry(((Range) entry.getKey()).intersection(range), entry.getValue());
        }

        public int hashCode() {
            return asMapOfRanges().hashCode();
        }

        public void put(Range<K> range, V v) {
            Range<K> range2 = this.c;
            Preconditions.checkArgument(range2.encloses(range), "Cannot put range %s into a subRangeMap(%s)", (Object) range, (Object) range2);
            TreeRangeMap.this.put(range, v);
        }

        public void putAll(RangeMap<K, V> rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                Range<K> span = rangeMap.span();
                Range<K> range = this.c;
                Preconditions.checkArgument(range.encloses(span), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", (Object) span, (Object) range);
                TreeRangeMap.this.putAll(rangeMap);
            }
        }

        public void putCoalescing(Range<K> range, V v) {
            TreeRangeMap treeRangeMap = TreeRangeMap.this;
            if (!treeRangeMap.c.isEmpty() && !range.isEmpty()) {
                Range<K> range2 = this.c;
                if (range2.encloses(range)) {
                    Object checkNotNull = Preconditions.checkNotNull(v);
                    TreeMap treeMap = treeRangeMap.c;
                    put(TreeRangeMap.a(TreeRangeMap.a(range, checkNotNull, treeMap.lowerEntry(range.c)), checkNotNull, treeMap.floorEntry(range.f)).intersection(range2), v);
                    return;
                }
            }
            put(range, v);
        }

        public void remove(Range<K> range) {
            Range<K> range2 = this.c;
            if (range.isConnected(range2)) {
                TreeRangeMap.this.remove(range.intersection(range2));
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: com.google.common.collect.Cut<C>} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.common.collect.Range<K> span() {
            /*
                r4 = this;
                com.google.common.collect.TreeRangeMap r0 = com.google.common.collect.TreeRangeMap.this
                java.util.TreeMap r1 = r0.c
                com.google.common.collect.Range<K> r2 = r4.c
                com.google.common.collect.Cut<C> r3 = r2.c
                java.util.Map$Entry r1 = r1.floorEntry(r3)
                java.util.TreeMap r0 = r0.c
                com.google.common.collect.Cut<C> r3 = r2.c
                com.google.common.collect.Cut<C> r2 = r2.f
                if (r1 == 0) goto L_0x0025
                java.lang.Object r1 = r1.getValue()
                com.google.common.collect.TreeRangeMap$RangeMapEntry r1 = (com.google.common.collect.TreeRangeMap.RangeMapEntry) r1
                com.google.common.collect.Range<K> r1 = r1.c
                com.google.common.collect.Cut<C> r1 = r1.f
                int r1 = r1.compareTo(r3)
                if (r1 <= 0) goto L_0x0025
                goto L_0x0034
            L_0x0025:
                java.lang.Object r1 = r0.ceilingKey(r3)
                r3 = r1
                com.google.common.collect.Cut r3 = (com.google.common.collect.Cut) r3
                if (r3 == 0) goto L_0x0061
                int r1 = r3.compareTo(r2)
                if (r1 >= 0) goto L_0x0061
            L_0x0034:
                java.util.Map$Entry r0 = r0.lowerEntry(r2)
                if (r0 == 0) goto L_0x005b
                java.lang.Object r1 = r0.getValue()
                com.google.common.collect.TreeRangeMap$RangeMapEntry r1 = (com.google.common.collect.TreeRangeMap.RangeMapEntry) r1
                com.google.common.collect.Range<K> r1 = r1.c
                com.google.common.collect.Cut<C> r1 = r1.f
                int r1 = r1.compareTo(r2)
                if (r1 < 0) goto L_0x004b
                goto L_0x0055
            L_0x004b:
                java.lang.Object r0 = r0.getValue()
                com.google.common.collect.TreeRangeMap$RangeMapEntry r0 = (com.google.common.collect.TreeRangeMap.RangeMapEntry) r0
                com.google.common.collect.Range<K> r0 = r0.c
                com.google.common.collect.Cut<C> r2 = r0.f
            L_0x0055:
                com.google.common.collect.Range r0 = new com.google.common.collect.Range
                r0.<init>(r3, r2)
                return r0
            L_0x005b:
                java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
                r0.<init>()
                throw r0
            L_0x0061:
                java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
                r0.<init>()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.TreeRangeMap.SubRangeMap.span():com.google.common.collect.Range");
        }

        public RangeMap<K, V> subRangeMap(Range<K> range) {
            Range<K> range2 = this.c;
            boolean isConnected = range.isConnected(range2);
            TreeRangeMap treeRangeMap = TreeRangeMap.this;
            if (isConnected) {
                return treeRangeMap.subRangeMap(range.intersection(range2));
            }
            RangeMap rangeMap = TreeRangeMap.f;
            treeRangeMap.getClass();
            return TreeRangeMap.f;
        }

        public String toString() {
            return asMapOfRanges().toString();
        }
    }

    public static <K extends Comparable, V> Range<K> a(Range<K> range, V v, Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry) {
        if (entry == null || !entry.getValue().getKey().isConnected(range) || !entry.getValue().getValue().equals(v)) {
            return range;
        }
        return range.span(entry.getValue().getKey());
    }

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap<>();
    }

    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new AsMapOfRanges(this.c.descendingMap().values());
    }

    public Map<Range<K>, V> asMapOfRanges() {
        return new AsMapOfRanges(this.c.values());
    }

    public void clear() {
        this.c.clear();
    }

    public boolean equals(Object obj) {
        if (obj instanceof RangeMap) {
            return asMapOfRanges().equals(((RangeMap) obj).asMapOfRanges());
        }
        return false;
    }

    public V get(K k) {
        Map.Entry entry = getEntry(k);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    public Map.Entry<Range<K>, V> getEntry(K k) {
        Map.Entry floorEntry = this.c.floorEntry(new Cut.BelowValue(k));
        if (floorEntry == null || !((RangeMapEntry) floorEntry.getValue()).contains(k)) {
            return null;
        }
        return (Map.Entry) floorEntry.getValue();
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public void put(Range<K> range, V v) {
        if (!range.isEmpty()) {
            Preconditions.checkNotNull(v);
            remove(range);
            RangeMapEntry rangeMapEntry = new RangeMapEntry(range, v);
            this.c.put(range.c, rangeMapEntry);
        }
    }

    public void putAll(RangeMap<K, V> rangeMap) {
        for (Map.Entry next : rangeMap.asMapOfRanges().entrySet()) {
            put((Range) next.getKey(), next.getValue());
        }
    }

    public void putCoalescing(Range<K> range, V v) {
        TreeMap treeMap = this.c;
        if (treeMap.isEmpty()) {
            put(range, v);
            return;
        }
        Object checkNotNull = Preconditions.checkNotNull(v);
        put(a(a(range, checkNotNull, treeMap.lowerEntry(range.c)), checkNotNull, treeMap.floorEntry(range.f)), v);
    }

    public void remove(Range<K> range) {
        if (!range.isEmpty()) {
            TreeMap treeMap = this.c;
            Cut<C> cut = range.c;
            Map.Entry<K, V> lowerEntry = treeMap.lowerEntry(cut);
            Cut<C> cut2 = range.f;
            if (lowerEntry != null) {
                RangeMapEntry rangeMapEntry = (RangeMapEntry) lowerEntry.getValue();
                if (rangeMapEntry.c.f.compareTo(cut) > 0) {
                    Range<K> range2 = rangeMapEntry.c;
                    if (range2.f.compareTo(cut2) > 0) {
                        treeMap.put(cut2, new RangeMapEntry(new Range(cut2, range2.f), ((RangeMapEntry) lowerEntry.getValue()).getValue()));
                    }
                    Object value = ((RangeMapEntry) lowerEntry.getValue()).getValue();
                    Cut<C> cut3 = range2.c;
                    treeMap.put(cut3, new RangeMapEntry(new Range(cut3, cut), value));
                }
            }
            Map.Entry<K, V> lowerEntry2 = treeMap.lowerEntry(cut2);
            if (lowerEntry2 != null) {
                RangeMapEntry rangeMapEntry2 = (RangeMapEntry) lowerEntry2.getValue();
                if (rangeMapEntry2.c.f.compareTo(cut2) > 0) {
                    Cut<C> cut4 = rangeMapEntry2.c.f;
                    treeMap.put(cut2, new RangeMapEntry(new Range(cut2, cut4), ((RangeMapEntry) lowerEntry2.getValue()).getValue()));
                }
            }
            treeMap.subMap(cut, cut2).clear();
        }
    }

    public Range<K> span() {
        TreeMap treeMap = this.c;
        Map.Entry firstEntry = treeMap.firstEntry();
        Map.Entry lastEntry = treeMap.lastEntry();
        if (firstEntry != null) {
            return new Range<>(((RangeMapEntry) firstEntry.getValue()).getKey().c, ((RangeMapEntry) lastEntry.getValue()).getKey().f);
        }
        throw new NoSuchElementException();
    }

    public RangeMap<K, V> subRangeMap(Range<K> range) {
        if (range.equals(Range.all())) {
            return this;
        }
        return new SubRangeMap(range);
    }

    public String toString() {
        return this.c.values().toString();
    }
}
