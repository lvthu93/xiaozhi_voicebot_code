package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Cut;
import com.google.common.collect.Iterators;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

public class TreeRangeSet<C extends Comparable<?>> extends AbstractRangeSet<C> implements Serializable {
    public final NavigableMap<Cut<C>, Range<C>> c;
    public transient Set<Range<C>> f;
    public transient Set<Range<C>> g;
    public transient RangeSet<C> h;

    public final class AsRanges extends ForwardingCollection<Range<C>> implements Set<Range<C>> {
        public final Collection<Range<C>> c;

        public AsRanges(Collection collection) {
            this.c = collection;
        }

        public final Collection<Range<C>> a() {
            return this.c;
        }

        public final Object delegate() {
            return this.c;
        }

        public boolean equals(Object obj) {
            return Sets.a(this, obj);
        }

        public int hashCode() {
            return Sets.b(this);
        }
    }

    public final class Complement extends TreeRangeSet<C> {
        public Complement() {
            super(new ComplementRangesByLowerBound(TreeRangeSet.this.c, Range.all()));
        }

        public void add(Range<C> range) {
            TreeRangeSet.this.remove(range);
        }

        public RangeSet<C> complement() {
            return TreeRangeSet.this;
        }

        public boolean contains(C c) {
            return !TreeRangeSet.this.contains(c);
        }

        public void remove(Range<C> range) {
            TreeRangeSet.this.add(range);
        }
    }

    public static final class ComplementRangesByLowerBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
        public final NavigableMap<Cut<C>, Range<C>> c;
        public final NavigableMap<Cut<C>, Range<C>> f;
        public final Range<Cut<C>> g;

        public ComplementRangesByLowerBound() {
            throw null;
        }

        public ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.c = navigableMap;
            this.f = new RangesByUpperBound(navigableMap);
            this.g = range;
        }

        public final Iterator<Map.Entry<Cut<C>, Range<C>>> a() {
            Collection<Range<C>> collection;
            boolean z;
            Range<Cut<C>> range = this.g;
            boolean hasLowerBound = range.hasLowerBound();
            NavigableMap<Cut<C>, Range<C>> navigableMap = this.f;
            if (hasLowerBound) {
                Cut<C> lowerEndpoint = range.lowerEndpoint();
                if (range.lowerBoundType() == BoundType.CLOSED) {
                    z = true;
                } else {
                    z = false;
                }
                collection = navigableMap.tailMap(lowerEndpoint, z).values();
            } else {
                collection = ((AbstractMap) navigableMap).values();
            }
            PeekingIterator<T> peekingIterator = Iterators.peekingIterator(collection.iterator());
            Cut<C> cut = Cut.BelowAll.f;
            if (!range.contains(cut) || (peekingIterator.hasNext() && ((Range) peekingIterator.peek()).c == cut)) {
                if (!peekingIterator.hasNext()) {
                    return Iterators.ArrayItr.i;
                }
                cut = ((Range) peekingIterator.next()).f;
            }
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(cut, peekingIterator) {
                public Cut<C> g;
                public final /* synthetic */ PeekingIterator h;

                {
                    this.h = r3;
                    this.g = r2;
                }

                public final Object computeNext() {
                    Cut.AboveAll aboveAll;
                    Range range;
                    if (ComplementRangesByLowerBound.this.g.f.m(this.g) || this.g == (aboveAll = Cut.AboveAll.f)) {
                        this.c = AbstractIterator.State.DONE;
                        return null;
                    }
                    PeekingIterator peekingIterator = this.h;
                    if (peekingIterator.hasNext()) {
                        Range range2 = (Range) peekingIterator.next();
                        range = new Range(this.g, range2.c);
                        this.g = range2.f;
                    } else {
                        range = new Range(this.g, aboveAll);
                        this.g = aboveAll;
                    }
                    return Maps.immutableEntry(range.c, range);
                }
            };
        }

        public final Iterator<Map.Entry<Cut<C>, Range<C>>> b() {
            Object obj;
            boolean z;
            Cut<C> cut;
            Range<Cut<C>> range = this.g;
            if (range.hasUpperBound()) {
                obj = range.upperEndpoint();
            } else {
                obj = Cut.AboveAll.f;
            }
            if (!range.hasUpperBound() || range.upperBoundType() != BoundType.CLOSED) {
                z = false;
            } else {
                z = true;
            }
            PeekingIterator<T> peekingIterator = Iterators.peekingIterator(this.f.headMap(obj, z).descendingMap().values().iterator());
            boolean hasNext = peekingIterator.hasNext();
            NavigableMap<Cut<C>, Range<C>> navigableMap = this.c;
            if (!hasNext) {
                Cut.BelowAll belowAll = Cut.BelowAll.f;
                if (!range.contains(belowAll) || navigableMap.containsKey(belowAll)) {
                    return Iterators.ArrayItr.i;
                }
                cut = navigableMap.higherKey(belowAll);
            } else if (((Range) peekingIterator.peek()).f == Cut.AboveAll.f) {
                cut = ((Range) peekingIterator.next()).c;
            } else {
                cut = navigableMap.higherKey(((Range) peekingIterator.peek()).f);
            }
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>((Cut) MoreObjects.firstNonNull(cut, Cut.AboveAll.f), peekingIterator) {
                public Cut<C> g;
                public final /* synthetic */ PeekingIterator h;

                {
                    this.h = r3;
                    this.g = r2;
                }

                public final Object computeNext() {
                    Cut<C> cut = this.g;
                    Cut.BelowAll belowAll = Cut.BelowAll.f;
                    AbstractIterator.State state = AbstractIterator.State.DONE;
                    if (cut == belowAll) {
                        this.c = state;
                    } else {
                        PeekingIterator peekingIterator = this.h;
                        boolean hasNext = peekingIterator.hasNext();
                        ComplementRangesByLowerBound complementRangesByLowerBound = ComplementRangesByLowerBound.this;
                        if (hasNext) {
                            Range range = (Range) peekingIterator.next();
                            Range range2 = new Range(range.f, this.g);
                            this.g = range.c;
                            Cut<C> cut2 = complementRangesByLowerBound.g.c;
                            Cut<C> cut3 = range2.c;
                            if (cut2.m(cut3)) {
                                return Maps.immutableEntry(cut3, range2);
                            }
                        } else if (complementRangesByLowerBound.g.c.m(belowAll)) {
                            Range range3 = new Range(belowAll, this.g);
                            this.g = belowAll;
                            return Maps.immutableEntry(belowAll, range3);
                        }
                        this.c = state;
                    }
                    return null;
                }
            };
        }

        public final NavigableMap<Cut<C>, Range<C>> c(Range<Cut<C>> range) {
            Range<Cut<C>> range2 = this.g;
            if (!range2.isConnected(range)) {
                return ImmutableSortedMap.of();
            }
            return new ComplementRangesByLowerBound(this.c, range.intersection(range2));
        }

        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        public boolean containsKey(Object obj) {
            return get(obj) != null;
        }

        public int size() {
            return Iterators.size(a());
        }

        public Range<C> get(Object obj) {
            if (obj instanceof Cut) {
                try {
                    Cut cut = (Cut) obj;
                    Map.Entry firstEntry = tailMap(cut, true).firstEntry();
                    if (firstEntry != null && ((Cut) firstEntry.getKey()).equals(cut)) {
                        return (Range) firstEntry.getValue();
                    }
                } catch (ClassCastException unused) {
                }
            }
            return null;
        }

        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean z) {
            return c(Range.upTo(cut, BoundType.b(z)));
        }

        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean z, Cut<C> cut2, boolean z2) {
            return c(Range.range(cut, BoundType.b(z), cut2, BoundType.b(z2)));
        }

        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean z) {
            return c(Range.downTo(cut, BoundType.b(z)));
        }
    }

    public static final class RangesByUpperBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
        public final NavigableMap<Cut<C>, Range<C>> c;
        public final Range<Cut<C>> f;

        public RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.c = navigableMap;
            this.f = Range.all();
        }

        public final Iterator<Map.Entry<Cut<C>, Range<C>>> a() {
            final Iterator<V> it;
            Range<Cut<C>> range = this.f;
            boolean hasLowerBound = range.hasLowerBound();
            NavigableMap navigableMap = this.c;
            if (!hasLowerBound) {
                it = navigableMap.values().iterator();
            } else {
                Map.Entry<K, V> lowerEntry = navigableMap.lowerEntry(range.lowerEndpoint());
                if (lowerEntry == null) {
                    it = navigableMap.values().iterator();
                } else if (range.c.m(((Range) lowerEntry.getValue()).f)) {
                    it = navigableMap.tailMap(lowerEntry.getKey(), true).values().iterator();
                } else {
                    it = navigableMap.tailMap(range.lowerEndpoint(), true).values().iterator();
                }
            }
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>() {
                public final Object computeNext() {
                    Iterator it = it;
                    boolean hasNext = it.hasNext();
                    AbstractIterator.State state = AbstractIterator.State.DONE;
                    if (!hasNext) {
                        this.c = state;
                    } else {
                        Range range = (Range) it.next();
                        if (!RangesByUpperBound.this.f.f.m(range.f)) {
                            return Maps.immutableEntry(range.f, range);
                        }
                        this.c = state;
                    }
                    return null;
                }
            };
        }

        public final Iterator<Map.Entry<Cut<C>, Range<C>>> b() {
            Collection<V> collection;
            Range<Cut<C>> range = this.f;
            boolean hasUpperBound = range.hasUpperBound();
            NavigableMap navigableMap = this.c;
            if (hasUpperBound) {
                collection = navigableMap.headMap(range.upperEndpoint(), false).descendingMap().values();
            } else {
                collection = navigableMap.descendingMap().values();
            }
            final PeekingIterator<T> peekingIterator = Iterators.peekingIterator(collection.iterator());
            if (peekingIterator.hasNext() && range.f.m(((Range) peekingIterator.peek()).f)) {
                peekingIterator.next();
            }
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>() {
                public final Object computeNext() {
                    PeekingIterator peekingIterator = peekingIterator;
                    boolean hasNext = peekingIterator.hasNext();
                    AbstractIterator.State state = AbstractIterator.State.DONE;
                    if (!hasNext) {
                        this.c = state;
                    } else {
                        Range range = (Range) peekingIterator.next();
                        if (RangesByUpperBound.this.f.c.m(range.f)) {
                            return Maps.immutableEntry(range.f, range);
                        }
                        this.c = state;
                    }
                    return null;
                }
            };
        }

        public final NavigableMap<Cut<C>, Range<C>> c(Range<Cut<C>> range) {
            Range<Cut<C>> range2 = this.f;
            if (range.isConnected(range2)) {
                return new RangesByUpperBound(this.c, range.intersection(range2));
            }
            return ImmutableSortedMap.of();
        }

        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        public boolean containsKey(Object obj) {
            return get(obj) != null;
        }

        public boolean isEmpty() {
            if (this.f.equals(Range.all())) {
                return this.c.isEmpty();
            }
            if (!a().hasNext()) {
                return true;
            }
            return false;
        }

        public int size() {
            if (this.f.equals(Range.all())) {
                return this.c.size();
            }
            return Iterators.size(a());
        }

        public Range<C> get(Object obj) {
            Map.Entry<Cut<C>, Range<C>> lowerEntry;
            if (obj instanceof Cut) {
                try {
                    Cut cut = (Cut) obj;
                    if (this.f.contains(cut) && (lowerEntry = this.c.lowerEntry(cut)) != null && lowerEntry.getValue().f.equals(cut)) {
                        return lowerEntry.getValue();
                    }
                } catch (ClassCastException unused) {
                }
            }
            return null;
        }

        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean z) {
            return c(Range.upTo(cut, BoundType.b(z)));
        }

        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean z, Cut<C> cut2, boolean z2) {
            return c(Range.range(cut, BoundType.b(z), cut2, BoundType.b(z2)));
        }

        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean z) {
            return c(Range.downTo(cut, BoundType.b(z)));
        }

        public RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.c = navigableMap;
            this.f = range;
        }
    }

    public final class SubRangeSet extends TreeRangeSet<C> {
        public final Range<C> i;

        public SubRangeSet(Range<C> range) {
            super(new SubRangeSetRangesByLowerBound(Range.all(), range, TreeRangeSet.this.c));
            this.i = range;
        }

        public void add(Range<C> range) {
            Range<C> range2 = this.i;
            Preconditions.checkArgument(range2.encloses(range), "Cannot add range %s to subRangeSet(%s)", (Object) range, (Object) range2);
            TreeRangeSet.super.add(range);
        }

        public void clear() {
            TreeRangeSet.this.remove(this.i);
        }

        public boolean contains(C c) {
            return this.i.contains(c) && TreeRangeSet.this.contains(c);
        }

        public boolean encloses(Range<C> range) {
            Range range2;
            Range<C> range3 = this.i;
            if (range3.isEmpty() || !range3.encloses(range)) {
                return false;
            }
            TreeRangeSet treeRangeSet = TreeRangeSet.this;
            treeRangeSet.getClass();
            Preconditions.checkNotNull(range);
            Map.Entry<K, V> floorEntry = treeRangeSet.c.floorEntry(range.c);
            if (floorEntry == null || !((Range) floorEntry.getValue()).encloses(range)) {
                range2 = null;
            } else {
                range2 = (Range) floorEntry.getValue();
            }
            if (range2 == null || range2.intersection(range3).isEmpty()) {
                return false;
            }
            return true;
        }

        public Range<C> rangeContaining(C c) {
            Range rangeContaining;
            Range<C> range = this.i;
            if (range.contains(c) && (rangeContaining = TreeRangeSet.this.rangeContaining(c)) != null) {
                return rangeContaining.intersection(range);
            }
            return null;
        }

        public void remove(Range<C> range) {
            Range<C> range2 = this.i;
            if (range.isConnected(range2)) {
                TreeRangeSet.this.remove(range.intersection(range2));
            }
        }

        public RangeSet<C> subRangeSet(Range<C> range) {
            Range<C> range2 = this.i;
            if (range.encloses(range2)) {
                return this;
            }
            if (range.isConnected(range2)) {
                return new SubRangeSet(range2.intersection(range));
            }
            return ImmutableRangeSet.of();
        }
    }

    public static final class SubRangeSetRangesByLowerBound<C extends Comparable<?>> extends AbstractNavigableMap<Cut<C>, Range<C>> {
        public final Range<Cut<C>> c;
        public final Range<C> f;
        public final NavigableMap<Cut<C>, Range<C>> g;
        public final NavigableMap<Cut<C>, Range<C>> h;

        public SubRangeSetRangesByLowerBound(Range<Cut<C>> range, Range<C> range2, NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.c = (Range) Preconditions.checkNotNull(range);
            this.f = (Range) Preconditions.checkNotNull(range2);
            this.g = (NavigableMap) Preconditions.checkNotNull(navigableMap);
            this.h = new RangesByUpperBound(navigableMap);
        }

        public final Iterator<Map.Entry<Cut<C>, Range<C>>> a() {
            final Iterator<Range<C>> it;
            Range<C> range = this.f;
            if (range.isEmpty()) {
                return Iterators.ArrayItr.i;
            }
            Range<Cut<C>> range2 = this.c;
            if (range2.f.m(range.c)) {
                return Iterators.ArrayItr.i;
            }
            boolean z = false;
            if (range2.c.m(range.c)) {
                it = this.h.tailMap(range.c, false).values().iterator();
            } else {
                C h2 = range2.c.h();
                if (range2.lowerBoundType() == BoundType.CLOSED) {
                    z = true;
                }
                it = this.g.tailMap(h2, z).values().iterator();
            }
            final Cut cut = (Cut) Ordering.natural().min(range2.f, new Cut.BelowValue(range.f));
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>() {
                public final Object computeNext() {
                    Iterator it = it;
                    boolean hasNext = it.hasNext();
                    AbstractIterator.State state = AbstractIterator.State.DONE;
                    if (!hasNext) {
                        this.c = state;
                    } else {
                        Range range = (Range) it.next();
                        if (cut.m(range.c)) {
                            this.c = state;
                        } else {
                            Range<C> intersection = range.intersection(SubRangeSetRangesByLowerBound.this.f);
                            return Maps.immutableEntry(intersection.c, intersection);
                        }
                    }
                    return null;
                }
            };
        }

        public final Iterator<Map.Entry<Cut<C>, Range<C>>> b() {
            boolean z;
            Range<C> range = this.f;
            if (range.isEmpty()) {
                return Iterators.ArrayItr.i;
            }
            Cut cut = (Cut) Ordering.natural().min(this.c.f, new Cut.BelowValue(range.f));
            Comparable h2 = cut.h();
            if (cut.p() == BoundType.CLOSED) {
                z = true;
            } else {
                z = false;
            }
            final Iterator<Range<C>> it = this.g.headMap(h2, z).descendingMap().values().iterator();
            return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>() {
                public final Object computeNext() {
                    Iterator it = it;
                    boolean hasNext = it.hasNext();
                    AbstractIterator.State state = AbstractIterator.State.DONE;
                    if (!hasNext) {
                        this.c = state;
                    } else {
                        Range range = (Range) it.next();
                        SubRangeSetRangesByLowerBound subRangeSetRangesByLowerBound = SubRangeSetRangesByLowerBound.this;
                        if (subRangeSetRangesByLowerBound.f.c.compareTo(range.f) >= 0) {
                            this.c = state;
                        } else {
                            Range<C> intersection = range.intersection(subRangeSetRangesByLowerBound.f);
                            if (subRangeSetRangesByLowerBound.c.contains(intersection.c)) {
                                return Maps.immutableEntry(intersection.c, intersection);
                            }
                            this.c = state;
                        }
                    }
                    return null;
                }
            };
        }

        public final NavigableMap<Cut<C>, Range<C>> c(Range<Cut<C>> range) {
            Range<Cut<C>> range2 = this.c;
            if (!range.isConnected(range2)) {
                return ImmutableSortedMap.of();
            }
            return new SubRangeSetRangesByLowerBound(range2.intersection(range), this.f, this.g);
        }

        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        public boolean containsKey(Object obj) {
            return get(obj) != null;
        }

        public int size() {
            return Iterators.size(a());
        }

        public Range<C> get(Object obj) {
            Range<C> range;
            Range<C> range2 = this.f;
            if (obj instanceof Cut) {
                try {
                    Cut cut = (Cut) obj;
                    if (this.c.contains(cut) && cut.compareTo(range2.c) >= 0) {
                        if (cut.compareTo(range2.f) < 0) {
                            boolean equals = cut.equals(range2.c);
                            NavigableMap<Cut<C>, Range<C>> navigableMap = this.g;
                            if (equals) {
                                Map.Entry<Cut<C>, Range<C>> floorEntry = navigableMap.floorEntry(cut);
                                if (floorEntry == null) {
                                    range = null;
                                } else {
                                    range = floorEntry.getValue();
                                }
                                Range range3 = range;
                                if (range3 != null && range3.f.compareTo(range2.c) > 0) {
                                    return range3.intersection(range2);
                                }
                            } else {
                                Range range4 = navigableMap.get(cut);
                                if (range4 != null) {
                                    return range4.intersection(range2);
                                }
                            }
                        }
                    }
                } catch (ClassCastException unused) {
                }
            }
            return null;
        }

        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean z) {
            return c(Range.upTo(cut, BoundType.b(z)));
        }

        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean z, Cut<C> cut2, boolean z2) {
            return c(Range.range(cut, BoundType.b(z), cut2, BoundType.b(z2)));
        }

        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean z) {
            return c(Range.downTo(cut, BoundType.b(z)));
        }
    }

    public /* synthetic */ TreeRangeSet() {
        throw null;
    }

    public TreeRangeSet(NavigableMap<Cut<C>, Range<C>> navigableMap) {
        this.c = navigableMap;
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create() {
        return new TreeRangeSet<>(new TreeMap());
    }

    public final void a(Range<C> range) {
        boolean isEmpty = range.isEmpty();
        NavigableMap<Cut<C>, Range<C>> navigableMap = this.c;
        Cut<C> cut = range.c;
        if (isEmpty) {
            navigableMap.remove(cut);
        } else {
            navigableMap.put(cut, range);
        }
    }

    public void add(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (!range.isEmpty()) {
            NavigableMap<Cut<C>, Range<C>> navigableMap = this.c;
            Cut cut = range.c;
            Map.Entry<Cut<C>, Range<C>> lowerEntry = navigableMap.lowerEntry(cut);
            Cut<C> cut2 = range.f;
            if (lowerEntry != null) {
                Range value = lowerEntry.getValue();
                if (value.f.compareTo(cut) >= 0) {
                    Cut<C> cut3 = value.f;
                    if (cut3.compareTo(cut2) >= 0) {
                        cut2 = cut3;
                    }
                    cut = value.c;
                }
            }
            Map.Entry<Cut<C>, Range<C>> floorEntry = navigableMap.floorEntry(cut2);
            if (floorEntry != null) {
                Range value2 = floorEntry.getValue();
                if (value2.f.compareTo(cut2) >= 0) {
                    cut2 = value2.f;
                }
            }
            navigableMap.subMap(cut, cut2).clear();
            a(new Range(cut, cut2));
        }
    }

    public /* bridge */ /* synthetic */ void addAll(RangeSet rangeSet) {
        super.addAll(rangeSet);
    }

    public Set<Range<C>> asDescendingSetOfRanges() {
        Set<Range<C>> set = this.g;
        if (set != null) {
            return set;
        }
        AsRanges asRanges = new AsRanges(this.c.descendingMap().values());
        this.g = asRanges;
        return asRanges;
    }

    public Set<Range<C>> asRanges() {
        Set<Range<C>> set = this.f;
        if (set != null) {
            return set;
        }
        AsRanges asRanges = new AsRanges(this.c.values());
        this.f = asRanges;
        return asRanges;
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public RangeSet<C> complement() {
        RangeSet<C> rangeSet = this.h;
        if (rangeSet != null) {
            return rangeSet;
        }
        Complement complement = new Complement();
        this.h = complement;
        return complement;
    }

    public /* bridge */ /* synthetic */ boolean contains(Comparable comparable) {
        return super.contains(comparable);
    }

    public boolean encloses(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry<K, V> floorEntry = this.c.floorEntry(range.c);
        if (floorEntry == null || !((Range) floorEntry.getValue()).encloses(range)) {
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

    public boolean intersects(Range<C> range) {
        Preconditions.checkNotNull(range);
        Cut<C> cut = range.c;
        NavigableMap navigableMap = this.c;
        Map.Entry<K, V> ceilingEntry = navigableMap.ceilingEntry(cut);
        if (ceilingEntry != null && ((Range) ceilingEntry.getValue()).isConnected(range) && !((Range) ceilingEntry.getValue()).intersection(range).isEmpty()) {
            return true;
        }
        Map.Entry<K, V> lowerEntry = navigableMap.lowerEntry(range.c);
        if (lowerEntry == null || !((Range) lowerEntry.getValue()).isConnected(range) || ((Range) lowerEntry.getValue()).intersection(range).isEmpty()) {
            return false;
        }
        return true;
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public Range<C> rangeContaining(C c2) {
        Preconditions.checkNotNull(c2);
        Map.Entry<Cut<C>, Range<C>> floorEntry = this.c.floorEntry(new Cut.BelowValue(c2));
        if (floorEntry == null || !floorEntry.getValue().contains(c2)) {
            return null;
        }
        return floorEntry.getValue();
    }

    public void remove(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (!range.isEmpty()) {
            NavigableMap<Cut<C>, Range<C>> navigableMap = this.c;
            Cut cut = range.c;
            Map.Entry<Cut<C>, Range<C>> lowerEntry = navigableMap.lowerEntry(cut);
            Cut cut2 = range.f;
            if (lowerEntry != null) {
                Range value = lowerEntry.getValue();
                if (value.f.compareTo(cut) >= 0) {
                    if (range.hasUpperBound()) {
                        Cut<C> cut3 = value.f;
                        if (cut3.compareTo((Cut<C>) cut2) >= 0) {
                            a(new Range(cut2, cut3));
                        }
                    }
                    a(new Range(value.c, cut));
                }
            }
            Map.Entry<Cut<C>, Range<C>> floorEntry = navigableMap.floorEntry(cut2);
            if (floorEntry != null) {
                Range value2 = floorEntry.getValue();
                if (range.hasUpperBound() && value2.f.compareTo(cut2) >= 0) {
                    a(new Range(cut2, value2.f));
                }
            }
            navigableMap.subMap(cut, cut2).clear();
        }
    }

    public /* bridge */ /* synthetic */ void removeAll(RangeSet rangeSet) {
        super.removeAll(rangeSet);
    }

    public Range<C> span() {
        NavigableMap<Cut<C>, Range<C>> navigableMap = this.c;
        Map.Entry<Cut<C>, Range<C>> firstEntry = navigableMap.firstEntry();
        Map.Entry<Cut<C>, Range<C>> lastEntry = navigableMap.lastEntry();
        if (firstEntry != null) {
            return new Range<>(firstEntry.getValue().c, lastEntry.getValue().f);
        }
        throw new NoSuchElementException();
    }

    public RangeSet<C> subRangeSet(Range<C> range) {
        return range.equals(Range.all()) ? this : new SubRangeSet(range);
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(RangeSet<C> rangeSet) {
        TreeRangeSet<C> create = create();
        create.addAll((RangeSet) rangeSet);
        return create;
    }

    public /* bridge */ /* synthetic */ void addAll(Iterable iterable) {
        super.addAll(iterable);
    }

    public /* bridge */ /* synthetic */ boolean enclosesAll(Iterable iterable) {
        return super.enclosesAll(iterable);
    }

    public /* bridge */ /* synthetic */ void removeAll(Iterable iterable) {
        super.removeAll(iterable);
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(Iterable<Range<C>> iterable) {
        TreeRangeSet<C> create = create();
        create.addAll((Iterable) iterable);
        return create;
    }
}
