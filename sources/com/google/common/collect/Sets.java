package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Synchronized;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

public final class Sets {

    public static final class CartesianSet<E> extends ForwardingCollection<List<E>> implements Set<List<E>> {
        public final transient ImmutableList<ImmutableSet<E>> c;
        public final transient CartesianList<E> f;

        public CartesianSet(ImmutableList<ImmutableSet<E>> immutableList, CartesianList<E> cartesianList) {
            this.c = immutableList;
            this.f = cartesianList;
        }

        public final Collection<List<E>> a() {
            return this.f;
        }

        public final Object delegate() {
            return this.f;
        }

        public boolean equals(Object obj) {
            if (obj instanceof CartesianSet) {
                return this.c.equals(((CartesianSet) obj).c);
            }
            return super.equals(obj);
        }

        public int hashCode() {
            ImmutableList<ImmutableSet<E>> immutableList;
            int i = 1;
            int size = size() - 1;
            int i2 = 0;
            while (true) {
                immutableList = this.c;
                if (i2 >= immutableList.size()) {
                    break;
                }
                size = ~(~(size * 31));
                i2++;
            }
            UnmodifiableIterator<ImmutableSet<E>> it = immutableList.iterator();
            while (it.hasNext()) {
                Set next = it.next();
                i = ~(~((next.hashCode() * (size() / next.size())) + (i * 31)));
            }
            return ~(~(i + size));
        }
    }

    public static class DescendingSet<E> extends ForwardingNavigableSet<E> {
        public final NavigableSet<E> c;

        public DescendingSet(NavigableSet<E> navigableSet) {
            this.c = navigableSet;
        }

        public final Collection a() {
            return this.c;
        }

        public E ceiling(E e) {
            return this.c.floor(e);
        }

        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator = this.c.comparator();
            if (comparator == null) {
                return Ordering.natural().reverse();
            }
            return Ordering.from(comparator).reverse();
        }

        public final Object delegate() {
            return this.c;
        }

        public Iterator<E> descendingIterator() {
            return this.c.iterator();
        }

        public NavigableSet<E> descendingSet() {
            return this.c;
        }

        public E first() {
            return this.c.last();
        }

        public E floor(E e) {
            return this.c.ceiling(e);
        }

        public final Set g() {
            return this.c;
        }

        public SortedSet<E> headSet(E e) {
            return headSet(e, false);
        }

        public E higher(E e) {
            return this.c.lower(e);
        }

        public Iterator<E> iterator() {
            return this.c.descendingIterator();
        }

        public final SortedSet j() {
            return this.c;
        }

        public final NavigableSet<E> k() {
            return this.c;
        }

        public E last() {
            return this.c.first();
        }

        public E lower(E e) {
            return this.c.higher(e);
        }

        public E pollFirst() {
            return this.c.pollLast();
        }

        public E pollLast() {
            return this.c.pollFirst();
        }

        public SortedSet<E> subSet(E e, E e2) {
            return subSet(e, true, e2, false);
        }

        public SortedSet<E> tailSet(E e) {
            return tailSet(e, true);
        }

        public <T> T[] toArray(T[] tArr) {
            return ObjectArrays.c(this, tArr);
        }

        public String toString() {
            return f();
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            return this.c.tailSet(e, z).descendingSet();
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return this.c.subSet(e2, z2, e, z).descendingSet();
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            return this.c.headSet(e, z).descendingSet();
        }

        public Object[] toArray() {
            return c();
        }
    }

    public static class FilteredNavigableSet<E> extends FilteredSortedSet<E> implements NavigableSet<E> {
        public FilteredNavigableSet(NavigableSet<E> navigableSet, Predicate<? super E> predicate) {
            super(navigableSet, predicate);
        }

        public E ceiling(E e) {
            return Iterables.find(((NavigableSet) this.c).tailSet(e, true), this.f, null);
        }

        public Iterator<E> descendingIterator() {
            return Iterators.filter(((NavigableSet) this.c).descendingIterator(), this.f);
        }

        public NavigableSet<E> descendingSet() {
            return Sets.filter(((NavigableSet) this.c).descendingSet(), this.f);
        }

        public E floor(E e) {
            return Iterators.find(((NavigableSet) this.c).headSet(e, true).descendingIterator(), this.f, null);
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            return Sets.filter(((NavigableSet) this.c).headSet(e, z), this.f);
        }

        public E higher(E e) {
            return Iterables.find(((NavigableSet) this.c).tailSet(e, false), this.f, null);
        }

        public E last() {
            return Iterators.find(((NavigableSet) this.c).descendingIterator(), this.f);
        }

        public E lower(E e) {
            return Iterators.find(((NavigableSet) this.c).headSet(e, false).descendingIterator(), this.f, null);
        }

        public E pollFirst() {
            return Iterables.a((NavigableSet) this.c, this.f);
        }

        public E pollLast() {
            return Iterables.a(((NavigableSet) this.c).descendingSet(), this.f);
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return Sets.filter(((NavigableSet) this.c).subSet(e, z, e2, z2), this.f);
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            return Sets.filter(((NavigableSet) this.c).tailSet(e, z), this.f);
        }
    }

    public static class FilteredSet<E> extends Collections2.FilteredCollection<E> implements Set<E> {
        public FilteredSet(Set<E> set, Predicate<? super E> predicate) {
            super(set, predicate);
        }

        public boolean equals(Object obj) {
            return Sets.a(this, obj);
        }

        public int hashCode() {
            return Sets.b(this);
        }
    }

    public static class FilteredSortedSet<E> extends FilteredSet<E> implements SortedSet<E> {
        public FilteredSortedSet(SortedSet<E> sortedSet, Predicate<? super E> predicate) {
            super(sortedSet, predicate);
        }

        public Comparator<? super E> comparator() {
            return ((SortedSet) this.c).comparator();
        }

        public E first() {
            return Iterators.find(this.c.iterator(), this.f);
        }

        public SortedSet<E> headSet(E e) {
            return new FilteredSortedSet(((SortedSet) this.c).headSet(e), this.f);
        }

        public E last() {
            SortedSet sortedSet = (SortedSet) this.c;
            while (true) {
                E last = sortedSet.last();
                if (this.f.apply(last)) {
                    return last;
                }
                sortedSet = sortedSet.headSet(last);
            }
        }

        public SortedSet<E> subSet(E e, E e2) {
            return new FilteredSortedSet(((SortedSet) this.c).subSet(e, e2), this.f);
        }

        public SortedSet<E> tailSet(E e) {
            return new FilteredSortedSet(((SortedSet) this.c).tailSet(e), this.f);
        }
    }

    public static abstract class ImprovedAbstractSet<E> extends AbstractSet<E> {
        public boolean removeAll(Collection<?> collection) {
            return Sets.c(this, collection);
        }

        public boolean retainAll(Collection<?> collection) {
            return super.retainAll((Collection) Preconditions.checkNotNull(collection));
        }
    }

    public static final class PowerSet<E> extends AbstractSet<Set<E>> {
        public final ImmutableMap<E, Integer> c;

        public PowerSet(Set<E> set) {
            boolean z;
            if (set.size() <= 30) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "Too many elements to create power set: %s > 30", set.size());
            this.c = Maps.e(set);
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Set)) {
                return false;
            }
            return this.c.keySet().containsAll((Set) obj);
        }

        public boolean equals(Object obj) {
            if (obj instanceof PowerSet) {
                return this.c.equals(((PowerSet) obj).c);
            }
            return super.equals(obj);
        }

        public int hashCode() {
            ImmutableMap<E, Integer> immutableMap = this.c;
            return immutableMap.keySet().hashCode() << (immutableMap.size() - 1);
        }

        public boolean isEmpty() {
            return false;
        }

        public Iterator<Set<E>> iterator() {
            return new AbstractIndexedListIterator<Set<E>>(size()) {
                public final Object get(int i) {
                    return new SubSet(i, PowerSet.this.c);
                }
            };
        }

        public int size() {
            return 1 << this.c.size();
        }

        public String toString() {
            return "powerSet(" + this.c + ")";
        }
    }

    public static abstract class SetView<E> extends AbstractSet<E> {
        @Deprecated
        public final boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public final boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        public <S extends Set<E>> S copyInto(S s) {
            s.addAll(this);
            return s;
        }

        public ImmutableSet<E> immutableCopy() {
            return ImmutableSet.copyOf(this);
        }

        public abstract UnmodifiableIterator<E> iterator();

        @Deprecated
        public final boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public final boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public final boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class SubSet<E> extends AbstractSet<E> {
        public final ImmutableMap<E, Integer> c;
        public final int f;

        public SubSet(int i, ImmutableMap immutableMap) {
            this.c = immutableMap;
            this.f = i;
        }

        public boolean contains(Object obj) {
            Integer num = this.c.get(obj);
            if (num == null || ((1 << num.intValue()) & this.f) == 0) {
                return false;
            }
            return true;
        }

        public Iterator<E> iterator() {
            return new UnmodifiableIterator<E>(this) {
                public final ImmutableList<E> c;
                public int f;

                {
                    this.c = r2.c.keySet().asList();
                    this.f = r2.f;
                }

                public boolean hasNext() {
                    return this.f != 0;
                }

                public E next() {
                    int numberOfTrailingZeros = Integer.numberOfTrailingZeros(this.f);
                    if (numberOfTrailingZeros != 32) {
                        this.f &= ~(1 << numberOfTrailingZeros);
                        return this.c.get(numberOfTrailingZeros);
                    }
                    throw new NoSuchElementException();
                }
            };
        }

        public int size() {
            return Integer.bitCount(this.f);
        }
    }

    public static final class UnmodifiableNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E>, Serializable {
        private static final long serialVersionUID = 0;
        public final NavigableSet<E> c;
        public final SortedSet<E> f;
        public transient UnmodifiableNavigableSet<E> g;

        public UnmodifiableNavigableSet(NavigableSet<E> navigableSet) {
            this.c = (NavigableSet) Preconditions.checkNotNull(navigableSet);
            this.f = Collections.unmodifiableSortedSet(navigableSet);
        }

        public final Collection a() {
            return this.f;
        }

        public E ceiling(E e) {
            return this.c.ceiling(e);
        }

        public final Object delegate() {
            return this.f;
        }

        public Iterator<E> descendingIterator() {
            return Iterators.unmodifiableIterator(this.c.descendingIterator());
        }

        public NavigableSet<E> descendingSet() {
            UnmodifiableNavigableSet<E> unmodifiableNavigableSet = this.g;
            if (unmodifiableNavigableSet != null) {
                return unmodifiableNavigableSet;
            }
            UnmodifiableNavigableSet<E> unmodifiableNavigableSet2 = new UnmodifiableNavigableSet<>(this.c.descendingSet());
            this.g = unmodifiableNavigableSet2;
            unmodifiableNavigableSet2.g = this;
            return unmodifiableNavigableSet2;
        }

        public E floor(E e) {
            return this.c.floor(e);
        }

        public final Set g() {
            return this.f;
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            return Sets.unmodifiableNavigableSet(this.c.headSet(e, z));
        }

        public E higher(E e) {
            return this.c.higher(e);
        }

        public final SortedSet<E> j() {
            return this.f;
        }

        public E lower(E e) {
            return this.c.lower(e);
        }

        public E pollFirst() {
            throw new UnsupportedOperationException();
        }

        public E pollLast() {
            throw new UnsupportedOperationException();
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            return Sets.unmodifiableNavigableSet(this.c.subSet(e, z, e2, z2));
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            return Sets.unmodifiableNavigableSet(this.c.tailSet(e, z));
        }
    }

    public static boolean a(Set<?> set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() != set2.size() || !set.containsAll(set2)) {
                    return false;
                }
                return true;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public static int b(Set<?> set) {
        int i;
        int i2 = 0;
        for (Object next : set) {
            if (next != null) {
                i = next.hashCode();
            } else {
                i = 0;
            }
            i2 = ~(~(i2 + i));
        }
        return i2;
    }

    public static boolean c(Set<?> set, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if (!(collection instanceof Set) || collection.size() <= set.size()) {
            return d(set, collection.iterator());
        }
        return Iterators.removeAll(set.iterator(), collection);
    }

    @SafeVarargs
    public static <B> Set<List<B>> cartesianProduct(Set<? extends B>... setArr) {
        return cartesianProduct(Arrays.asList(setArr));
    }

    public static <E> Set<Set<E>> combinations(Set<E> set, final int i) {
        boolean z;
        final ImmutableMap<E, Integer> e = Maps.e(set);
        CollectPreconditions.b(i, "size");
        if (i <= e.size()) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "size (%s) must be <= set.size() (%s)", i, e.size());
        if (i == 0) {
            return ImmutableSet.of(ImmutableSet.of());
        }
        if (i == e.size()) {
            return ImmutableSet.of(e.keySet());
        }
        return new AbstractSet<Set<E>>() {
            public boolean contains(Object obj) {
                if (!(obj instanceof Set)) {
                    return false;
                }
                Set set = (Set) obj;
                if (set.size() != i || !e.keySet().containsAll(set)) {
                    return false;
                }
                return true;
            }

            public Iterator<Set<E>> iterator() {
                return new AbstractIterator<Set<E>>() {
                    public final BitSet g;

                    {
                        this.g = new BitSet(e.size());
                    }

                    public final Object computeNext() {
                        BitSet bitSet = this.g;
                        boolean isEmpty = bitSet.isEmpty();
                        AnonymousClass5 r2 = AnonymousClass5.this;
                        if (isEmpty) {
                            bitSet.set(0, i);
                        } else {
                            int nextSetBit = bitSet.nextSetBit(0);
                            int nextClearBit = bitSet.nextClearBit(nextSetBit);
                            if (nextClearBit == e.size()) {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                            int i = (nextClearBit - nextSetBit) - 1;
                            bitSet.set(0, i);
                            bitSet.clear(i, nextClearBit);
                            bitSet.set(nextClearBit);
                        }
                        final BitSet bitSet2 = (BitSet) bitSet.clone();
                        return new AbstractSet<Object>() {
                            public boolean contains(Object obj) {
                                Integer num = (Integer) e.get(obj);
                                if (num == null || !bitSet2.get(num.intValue())) {
                                    return false;
                                }
                                return true;
                            }

                            public Iterator<Object> iterator() {
                                return new AbstractIterator<Object>() {
                                    public int g = -1;

                                    public final Object computeNext() {
                                        AnonymousClass1 r0 = AnonymousClass1.this;
                                        int nextSetBit = bitSet2.nextSetBit(this.g + 1);
                                        this.g = nextSetBit;
                                        if (nextSetBit != -1) {
                                            return e.keySet().asList().get(this.g);
                                        }
                                        this.c = AbstractIterator.State.DONE;
                                        return null;
                                    }
                                };
                            }

                            public int size() {
                                return i;
                            }
                        };
                    }
                };
            }

            public int size() {
                return w3.a(e.size(), i);
            }

            public String toString() {
                return "Sets.combinations(" + e.keySet() + ", " + i + ")";
            }
        };
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet) collection);
        }
        Preconditions.checkArgument(!collection.isEmpty(), "collection is empty; use the other version of this method");
        EnumSet<E> allOf = EnumSet.allOf(((Enum) collection.iterator().next()).getDeclaringClass());
        allOf.removeAll(collection);
        return allOf;
    }

    public static boolean d(Set<?> set, Iterator<?> it) {
        boolean z = false;
        while (it.hasNext()) {
            z |= set.remove(it.next());
        }
        return z;
    }

    public static <E> SetView<E> difference(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() {
            public boolean contains(Object obj) {
                return set.contains(obj) && !set2.contains(obj);
            }

            public boolean isEmpty() {
                return set2.containsAll(set);
            }

            public int size() {
                int i = 0;
                for (Object contains : set) {
                    if (!set2.contains(contains)) {
                        i++;
                    }
                }
                return i;
            }

            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>() {
                    public final Iterator<E> g;

                    {
                        this.g = set.iterator();
                    }

                    public final E computeNext() {
                        E next;
                        do {
                            Iterator<E> it = this.g;
                            if (it.hasNext()) {
                                next = it.next();
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (set2.contains(next));
                        return next;
                    }
                };
            }
        };
    }

    public static <E> Set<E> filter(Set<E> set, Predicate<? super E> predicate) {
        if (set instanceof SortedSet) {
            return filter((SortedSet) set, predicate);
        }
        if (!(set instanceof FilteredSet)) {
            return new FilteredSet((Set) Preconditions.checkNotNull(set), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredSet filteredSet = (FilteredSet) set;
        return new FilteredSet((Set) filteredSet.c, Predicates.and(filteredSet.f, predicate));
    }

    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E e, E... eArr) {
        return ImmutableEnumSet.m(EnumSet.of(e, eArr));
    }

    public static <E> SetView<E> intersection(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() {
            public boolean contains(Object obj) {
                return set.contains(obj) && set2.contains(obj);
            }

            public boolean containsAll(Collection<?> collection) {
                return set.containsAll(collection) && set2.containsAll(collection);
            }

            public boolean isEmpty() {
                return Collections.disjoint(set2, set);
            }

            public int size() {
                int i = 0;
                for (Object contains : set) {
                    if (set2.contains(contains)) {
                        i++;
                    }
                }
                return i;
            }

            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>() {
                    public final Iterator<E> g;

                    {
                        this.g = set.iterator();
                    }

                    public final E computeNext() {
                        E next;
                        do {
                            Iterator<E> it = this.g;
                            if (it.hasNext()) {
                                next = it.next();
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (!set2.contains(next));
                        return next;
                    }
                };
            }
        };
    }

    public static <E> Set<E> newConcurrentHashSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap());
    }

    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> cls) {
        EnumSet<E> noneOf = EnumSet.noneOf(cls);
        Iterables.addAll(noneOf, iterable);
        return noneOf;
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>();
    }

    public static <E> HashSet<E> newHashSetWithExpectedSize(int i) {
        return new HashSet<>(Maps.c(i));
    }

    public static <E> Set<E> newIdentityHashSet() {
        return Collections.newSetFromMap(Maps.newIdentityHashMap());
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int i) {
        return new LinkedHashSet<>(Maps.c(i));
    }

    @Deprecated
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return Collections.newSetFromMap(map);
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet<>();
    }

    public static <E> Set<Set<E>> powerSet(Set<E> set) {
        return new PowerSet(set);
    }

    public static <K extends Comparable<? super K>> NavigableSet<K> subSet(NavigableSet<K> navigableSet, Range<K> range) {
        boolean z;
        boolean z2;
        boolean z3 = true;
        if (navigableSet.comparator() != null && navigableSet.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            if (navigableSet.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            Preconditions.checkArgument(z2, "set is using a custom comparator which is inconsistent with the natural ordering.");
        }
        boolean hasLowerBound = range.hasLowerBound();
        BoundType boundType = BoundType.CLOSED;
        if (hasLowerBound && range.hasUpperBound()) {
            K lowerEndpoint = range.lowerEndpoint();
            if (range.lowerBoundType() == boundType) {
                z = true;
            } else {
                z = false;
            }
            K upperEndpoint = range.upperEndpoint();
            if (range.upperBoundType() != boundType) {
                z3 = false;
            }
            return navigableSet.subSet(lowerEndpoint, z, upperEndpoint, z3);
        } else if (range.hasLowerBound()) {
            K lowerEndpoint2 = range.lowerEndpoint();
            if (range.lowerBoundType() != boundType) {
                z3 = false;
            }
            return navigableSet.tailSet(lowerEndpoint2, z3);
        } else if (!range.hasUpperBound()) {
            return (NavigableSet) Preconditions.checkNotNull(navigableSet);
        } else {
            K upperEndpoint2 = range.upperEndpoint();
            if (range.upperBoundType() != boundType) {
                z3 = false;
            }
            return navigableSet.headSet(upperEndpoint2, z3);
        }
    }

    public static <E> SetView<E> symmetricDifference(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() {
            public boolean contains(Object obj) {
                return set2.contains(obj) ^ set.contains(obj);
            }

            public boolean isEmpty() {
                return set.equals(set2);
            }

            public int size() {
                Set<Object> set;
                Set set2 = set;
                Iterator it = set2.iterator();
                int i = 0;
                while (true) {
                    boolean hasNext = it.hasNext();
                    set = set2;
                    if (!hasNext) {
                        break;
                    } else if (!set.contains(it.next())) {
                        i++;
                    }
                }
                for (Object contains : set) {
                    if (!set2.contains(contains)) {
                        i++;
                    }
                }
                return i;
            }

            public UnmodifiableIterator<E> iterator() {
                final Iterator it = set.iterator();
                final Iterator it2 = set2.iterator();
                return new AbstractIterator<E>() {
                    public E computeNext() {
                        AnonymousClass4 r2;
                        E next;
                        E next2;
                        do {
                            Iterator it = it;
                            boolean hasNext = it.hasNext();
                            r2 = AnonymousClass4.this;
                            if (hasNext) {
                                next2 = it.next();
                            } else {
                                do {
                                    Iterator it2 = it2;
                                    if (it2.hasNext()) {
                                        next = it2.next();
                                    } else {
                                        this.c = AbstractIterator.State.DONE;
                                        return null;
                                    }
                                } while (set.contains(next));
                                return next;
                            }
                        } while (set2.contains(next2));
                        return next2;
                    }
                };
            }
        };
    }

    public static <E> NavigableSet<E> synchronizedNavigableSet(NavigableSet<E> navigableSet) {
        return new Synchronized.SynchronizedNavigableSet(navigableSet, (Object) null);
    }

    public static <E> SetView<E> union(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>() {
            public boolean contains(Object obj) {
                return set.contains(obj) || set2.contains(obj);
            }

            public <S extends Set<E>> S copyInto(S s) {
                s.addAll(set);
                s.addAll(set2);
                return s;
            }

            public ImmutableSet<E> immutableCopy() {
                return new ImmutableSet.Builder().addAll((Iterable) set).addAll((Iterable) set2).build();
            }

            public boolean isEmpty() {
                return set.isEmpty() && set2.isEmpty();
            }

            public int size() {
                Set set = set;
                int size = set.size();
                for (Object contains : set2) {
                    if (!set.contains(contains)) {
                        size++;
                    }
                }
                return size;
            }

            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>() {
                    public final Iterator<? extends E> g;
                    public final Iterator<? extends E> h;

                    {
                        this.g = set.iterator();
                        this.h = set2.iterator();
                    }

                    public final E computeNext() {
                        E next;
                        Iterator<? extends E> it = this.g;
                        if (it.hasNext()) {
                            return it.next();
                        }
                        do {
                            Iterator<? extends E> it2 = this.h;
                            if (it2.hasNext()) {
                                next = it2.next();
                            } else {
                                this.c = AbstractIterator.State.DONE;
                                return null;
                            }
                        } while (set.contains(next));
                        return next;
                    }
                };
            }
        };
    }

    public static <E> NavigableSet<E> unmodifiableNavigableSet(NavigableSet<E> navigableSet) {
        if ((navigableSet instanceof ImmutableCollection) || (navigableSet instanceof UnmodifiableNavigableSet)) {
            return navigableSet;
        }
        return new UnmodifiableNavigableSet(navigableSet);
    }

    public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> list) {
        ImmutableList.Builder builder = new ImmutableList.Builder(list.size());
        for (Set copyOf : list) {
            ImmutableSet copyOf2 = ImmutableSet.copyOf(copyOf);
            if (copyOf2.isEmpty()) {
                return ImmutableSet.of();
            }
            builder.add((Object) copyOf2);
        }
        ImmutableList build = builder.build();
        return new CartesianSet(build, new CartesianList(new ImmutableList<List<Object>>() {
            public final boolean isPartialView() {
                return true;
            }

            public int size() {
                return ImmutableList.this.size();
            }

            public List<Object> get(int i) {
                return ((ImmutableSet) ImmutableList.this.get(i)).asList();
            }
        }));
    }

    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> iterable) {
        if (iterable instanceof ImmutableEnumSet) {
            return (ImmutableEnumSet) iterable;
        }
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (collection.isEmpty()) {
                return ImmutableSet.of();
            }
            return ImmutableEnumSet.m(EnumSet.copyOf(collection));
        }
        Iterator<E> it = iterable.iterator();
        if (!it.hasNext()) {
            return ImmutableSet.of();
        }
        EnumSet of = EnumSet.of((Enum) it.next());
        Iterators.addAll(of, it);
        return ImmutableEnumSet.m(of);
    }

    public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> iterable) {
        Set<E> newConcurrentHashSet = newConcurrentHashSet();
        Iterables.addAll(newConcurrentHashSet, iterable);
        return newConcurrentHashSet;
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [java.lang.Iterable<? extends E>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <E> java.util.concurrent.CopyOnWriteArraySet<E> newCopyOnWriteArraySet(java.lang.Iterable<? extends E> r1) {
        /*
            boolean r0 = r1 instanceof java.util.Collection
            if (r0 == 0) goto L_0x0007
            java.util.Collection r1 = (java.util.Collection) r1
            goto L_0x000b
        L_0x0007:
            java.util.ArrayList r1 = com.google.common.collect.Lists.newArrayList(r1)
        L_0x000b:
            java.util.concurrent.CopyOnWriteArraySet r0 = new java.util.concurrent.CopyOnWriteArraySet
            r0.<init>(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Sets.newCopyOnWriteArraySet(java.lang.Iterable):java.util.concurrent.CopyOnWriteArraySet");
    }

    public static <E> HashSet<E> newHashSet(E... eArr) {
        HashSet<E> newHashSetWithExpectedSize = newHashSetWithExpectedSize(eArr.length);
        Collections.addAll(newHashSetWithExpectedSize, eArr);
        return newHashSetWithExpectedSize;
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet<>((Collection) iterable);
        }
        LinkedHashSet<E> newLinkedHashSet = newLinkedHashSet();
        Iterables.addAll(newLinkedHashSet, iterable);
        return newLinkedHashSet;
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> iterable) {
        TreeSet<E> newTreeSet = newTreeSet();
        Iterables.addAll(newTreeSet, iterable);
        return newTreeSet;
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new HashSet<>((Collection) iterable);
        }
        return newHashSet(iterable.iterator());
    }

    public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
        return new TreeSet<>((Comparator) Preconditions.checkNotNull(comparator));
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> cls) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet) collection);
        }
        EnumSet<E> allOf = EnumSet.allOf(cls);
        allOf.removeAll(collection);
        return allOf;
    }

    public static <E> SortedSet<E> filter(SortedSet<E> sortedSet, Predicate<? super E> predicate) {
        if (!(sortedSet instanceof FilteredSet)) {
            return new FilteredSortedSet((SortedSet) Preconditions.checkNotNull(sortedSet), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredSet filteredSet = (FilteredSet) sortedSet;
        return new FilteredSortedSet((SortedSet) filteredSet.c, Predicates.and(filteredSet.f, predicate));
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> it) {
        HashSet<E> newHashSet = newHashSet();
        Iterators.addAll(newHashSet, it);
        return newHashSet;
    }

    public static <E> NavigableSet<E> filter(NavigableSet<E> navigableSet, Predicate<? super E> predicate) {
        if (!(navigableSet instanceof FilteredSet)) {
            return new FilteredNavigableSet((NavigableSet) Preconditions.checkNotNull(navigableSet), (Predicate) Preconditions.checkNotNull(predicate));
        }
        FilteredSet filteredSet = (FilteredSet) navigableSet;
        return new FilteredNavigableSet((NavigableSet) filteredSet.c, Predicates.and(filteredSet.f, predicate));
    }
}
