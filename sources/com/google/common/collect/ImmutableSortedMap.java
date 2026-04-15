package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMap;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;

public final class ImmutableSortedMap<K, V> extends ImmutableSortedMapFauxverideShim<K, V> implements NavigableMap<K, V> {
    public static final Ordering m = Ordering.natural();
    public static final ImmutableSortedMap<Comparable, Object> n = new ImmutableSortedMap<>(ImmutableSortedSet.p(Ordering.natural()), ImmutableList.of(), (ImmutableSortedMap) null);
    private static final long serialVersionUID = 0;
    public final transient RegularImmutableSortedSet<K> j;
    public final transient ImmutableList<V> k;
    public final transient ImmutableSortedMap<K, V> l;

    public static class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        public transient Object[] e = new Object[4];
        public transient Object[] f = new Object[4];
        public final Comparator<? super K> g;

        public Builder(Comparator<? super K> comparator) {
            this.g = (Comparator) Preconditions.checkNotNull(comparator);
        }

        public ImmutableSortedMap<K, V> build() {
            int i = this.c;
            Comparator<? super K> comparator = this.g;
            if (i == 0) {
                return ImmutableSortedMap.i(comparator);
            }
            if (i == 1) {
                return ImmutableSortedMap.l(this.e[0], this.f[0], comparator);
            }
            Object[] copyOf = Arrays.copyOf(this.e, i);
            Arrays.sort(copyOf, comparator);
            int i2 = this.c;
            Object[] objArr = new Object[i2];
            for (int i3 = 0; i3 < this.c; i3++) {
                if (i3 > 0) {
                    int i4 = i3 - 1;
                    if (comparator.compare(copyOf[i4], copyOf[i3]) == 0) {
                        throw new IllegalArgumentException("keys required to be distinct but compared as equal: " + copyOf[i4] + " and " + copyOf[i3]);
                    }
                }
                objArr[Arrays.binarySearch(copyOf, this.e[i3], comparator)] = this.f[i3];
            }
            return new ImmutableSortedMap<>(new RegularImmutableSortedSet(ImmutableList.g(copyOf.length, copyOf), comparator), ImmutableList.g(i2, objArr), (ImmutableSortedMap) null);
        }

        @Deprecated
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
        }

        public Builder<K, V> put(K k, V v) {
            int i = this.c + 1;
            Object[] objArr = this.e;
            if (i > objArr.length) {
                int a = ImmutableCollection.Builder.a(objArr.length, i);
                this.e = Arrays.copyOf(this.e, a);
                this.f = Arrays.copyOf(this.f, a);
            }
            CollectPreconditions.a(k, v);
            Object[] objArr2 = this.e;
            int i2 = this.c;
            objArr2[i2] = k;
            this.f[i2] = v;
            this.c = i2 + 1;
            return this;
        }

        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }

        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll(iterable);
            return this;
        }

        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put(entry);
            return this;
        }
    }

    public static class SerializedForm extends ImmutableMap.SerializedForm {
        private static final long serialVersionUID = 0;
        public final Comparator<Object> g;

        public SerializedForm(ImmutableSortedMap<?, ?> immutableSortedMap) {
            super(immutableSortedMap);
            this.g = immutableSortedMap.comparator();
        }

        public Object readResolve() {
            return a(new Builder(this.g));
        }
    }

    public ImmutableSortedMap() {
        throw null;
    }

    public ImmutableSortedMap(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList, ImmutableSortedMap<K, V> immutableSortedMap) {
        this.j = regularImmutableSortedSet;
        this.k = immutableList;
        this.l = immutableSortedMap;
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        return h(map, m);
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.util.SortedMap<K, ? extends V>, java.util.SortedMap] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <K, V> com.google.common.collect.ImmutableSortedMap<K, V> copyOfSorted(java.util.SortedMap<K, ? extends V> r3) {
        /*
            java.util.Comparator r0 = r3.comparator()
            if (r0 != 0) goto L_0x0008
            com.google.common.collect.Ordering r0 = m
        L_0x0008:
            boolean r1 = r3 instanceof com.google.common.collect.ImmutableSortedMap
            if (r1 == 0) goto L_0x0016
            r1 = r3
            com.google.common.collect.ImmutableSortedMap r1 = (com.google.common.collect.ImmutableSortedMap) r1
            boolean r2 = r1.f()
            if (r2 != 0) goto L_0x0016
            return r1
        L_0x0016:
            java.util.Set r3 = r3.entrySet()
            java.util.Map$Entry<?, ?>[] r1 = com.google.common.collect.ImmutableMap.i
            boolean r2 = r3 instanceof java.util.Collection
            if (r2 == 0) goto L_0x0021
            goto L_0x0029
        L_0x0021:
            java.util.Iterator r3 = r3.iterator()
            java.util.ArrayList r3 = com.google.common.collect.Lists.newArrayList(r3)
        L_0x0029:
            java.lang.Object[] r3 = r3.toArray(r1)
            java.util.Map$Entry[] r3 = (java.util.Map.Entry[]) r3
            java.util.Map$Entry[] r3 = (java.util.Map.Entry[]) r3
            int r1 = r3.length
            r2 = 1
            com.google.common.collect.ImmutableSortedMap r3 = j(r0, r2, r3, r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableSortedMap.copyOfSorted(java.util.SortedMap):com.google.common.collect.ImmutableSortedMap");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.util.Map, java.util.Map<? extends K, ? extends V>] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0028 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0034  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <K, V> com.google.common.collect.ImmutableSortedMap<K, V> h(java.util.Map<? extends K, ? extends V> r3, java.util.Comparator<? super K> r4) {
        /*
            boolean r0 = r3 instanceof java.util.SortedMap
            if (r0 == 0) goto L_0x0018
            r0 = r3
            java.util.SortedMap r0 = (java.util.SortedMap) r0
            java.util.Comparator r0 = r0.comparator()
            if (r0 != 0) goto L_0x0013
            com.google.common.collect.Ordering r0 = m
            if (r4 != r0) goto L_0x0018
            r0 = 1
            goto L_0x0019
        L_0x0013:
            boolean r0 = r4.equals(r0)
            goto L_0x0019
        L_0x0018:
            r0 = 0
        L_0x0019:
            if (r0 == 0) goto L_0x0029
            boolean r1 = r3 instanceof com.google.common.collect.ImmutableSortedMap
            if (r1 == 0) goto L_0x0029
            r1 = r3
            com.google.common.collect.ImmutableSortedMap r1 = (com.google.common.collect.ImmutableSortedMap) r1
            boolean r2 = r1.f()
            if (r2 != 0) goto L_0x0029
            return r1
        L_0x0029:
            java.util.Set r3 = r3.entrySet()
            java.util.Map$Entry<?, ?>[] r1 = com.google.common.collect.ImmutableMap.i
            boolean r2 = r3 instanceof java.util.Collection
            if (r2 == 0) goto L_0x0034
            goto L_0x003c
        L_0x0034:
            java.util.Iterator r3 = r3.iterator()
            java.util.ArrayList r3 = com.google.common.collect.Lists.newArrayList(r3)
        L_0x003c:
            java.lang.Object[] r3 = r3.toArray(r1)
            java.util.Map$Entry[] r3 = (java.util.Map.Entry[]) r3
            java.util.Map$Entry[] r3 = (java.util.Map.Entry[]) r3
            int r1 = r3.length
            com.google.common.collect.ImmutableSortedMap r3 = j(r4, r0, r3, r1)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableSortedMap.h(java.util.Map, java.util.Comparator):com.google.common.collect.ImmutableSortedMap");
    }

    public static <K, V> ImmutableSortedMap<K, V> i(Comparator<? super K> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return of();
        }
        return new ImmutableSortedMap<>(ImmutableSortedSet.p(comparator), ImmutableList.of(), (ImmutableSortedMap) null);
    }

    public static <K, V> ImmutableSortedMap<K, V> j(final Comparator<? super K> comparator, boolean z, Map.Entry<K, V>[] entryArr, int i) {
        boolean z2;
        if (i == 0) {
            return i(comparator);
        }
        if (i == 1) {
            return l(entryArr[0].getKey(), entryArr[0].getValue(), comparator);
        }
        Object[] objArr = new Object[i];
        Object[] objArr2 = new Object[i];
        if (z) {
            for (int i2 = 0; i2 < i; i2++) {
                K key = entryArr[i2].getKey();
                V value = entryArr[i2].getValue();
                CollectPreconditions.a(key, value);
                objArr[i2] = key;
                objArr2[i2] = value;
            }
        } else {
            Arrays.sort(entryArr, 0, i, new Comparator<Map.Entry<K, V>>() {
                public int compare(Map.Entry<K, V> entry, Map.Entry<K, V> entry2) {
                    return comparator.compare(entry.getKey(), entry2.getKey());
                }
            });
            K key2 = entryArr[0].getKey();
            objArr[0] = key2;
            V value2 = entryArr[0].getValue();
            objArr2[0] = value2;
            CollectPreconditions.a(objArr[0], value2);
            int i3 = 1;
            while (i3 < i) {
                K key3 = entryArr[i3].getKey();
                V value3 = entryArr[i3].getValue();
                CollectPreconditions.a(key3, value3);
                objArr[i3] = key3;
                objArr2[i3] = value3;
                if (comparator.compare(key2, key3) != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                Map.Entry<K, V> entry = entryArr[i3 - 1];
                Map.Entry<K, V> entry2 = entryArr[i3];
                if (z2) {
                    i3++;
                    key2 = key3;
                } else {
                    throw new IllegalArgumentException("Multiple entries with same key: " + entry + " and " + entry2);
                }
            }
        }
        return new ImmutableSortedMap<>(new RegularImmutableSortedSet(ImmutableList.g(i, objArr), comparator), ImmutableList.g(i, objArr2), (ImmutableSortedMap) null);
    }

    public static ImmutableSortedMap l(Object obj, Object obj2, Comparator comparator) {
        return new ImmutableSortedMap(new RegularImmutableSortedSet(ImmutableList.of(obj), (Comparator) Preconditions.checkNotNull(comparator)), ImmutableList.of(obj2), (ImmutableSortedMap) null);
    }

    public static <K extends Comparable<?>, V> Builder<K, V> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static <K, V> ImmutableSortedMap<K, V> of() {
        return n;
    }

    public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
        return new Builder<>(comparator);
    }

    public static <K extends Comparable<?>, V> Builder<K, V> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    public final ImmutableSet<Map.Entry<K, V>> a() {
        return isEmpty() ? ImmutableSet.of() : new ImmutableMapEntrySet<K, V>() {
            public final ImmutableList<Map.Entry<K, V>> k() {
                return new ImmutableList<Map.Entry<K, V>>() {
                    public final boolean isPartialView() {
                        return true;
                    }

                    public int size() {
                        return ImmutableSortedMap.this.size();
                    }

                    public Map.Entry<K, V> get(int i) {
                        AnonymousClass1EntrySet r1 = AnonymousClass1EntrySet.this;
                        return new AbstractMap.SimpleImmutableEntry(ImmutableSortedMap.this.j.asList().get(i), ImmutableSortedMap.this.k.get(i));
                    }
                };
            }

            public final ImmutableMap<K, V> m() {
                return ImmutableSortedMap.this;
            }

            public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                return asList().iterator();
            }
        };
    }

    public final ImmutableSet<K> b() {
        throw new AssertionError("should never be called");
    }

    public final ImmutableCollection<V> c() {
        throw new AssertionError("should never be called");
    }

    public Map.Entry<K, V> ceilingEntry(K k2) {
        return tailMap(k2, true).firstEntry();
    }

    public K ceilingKey(K k2) {
        return Maps.f(ceilingEntry(k2));
    }

    public Comparator<? super K> comparator() {
        return keySet().comparator();
    }

    public final boolean f() {
        return this.j.isPartialView() || this.k.isPartialView();
    }

    public Map.Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Map.Entry) entrySet().asList().get(0);
    }

    public K firstKey() {
        return keySet().first();
    }

    public Map.Entry<K, V> floorEntry(K k2) {
        return headMap(k2, true).lastEntry();
    }

    public K floorKey(K k2) {
        return Maps.f(floorEntry(k2));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0011, code lost:
        if (r4 >= 0) goto L_0x0015;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V get(java.lang.Object r4) {
        /*
            r3 = this;
            com.google.common.collect.RegularImmutableSortedSet<K> r0 = r3.j
            r0.getClass()
            r1 = -1
            if (r4 != 0) goto L_0x0009
            goto L_0x0014
        L_0x0009:
            com.google.common.collect.ImmutableList<E> r2 = r0.i     // Catch:{ ClassCastException -> 0x0014 }
            java.util.Comparator<? super E> r0 = r0.g     // Catch:{ ClassCastException -> 0x0014 }
            int r4 = java.util.Collections.binarySearch(r2, r4, r0)     // Catch:{ ClassCastException -> 0x0014 }
            if (r4 < 0) goto L_0x0014
            goto L_0x0015
        L_0x0014:
            r4 = -1
        L_0x0015:
            if (r4 != r1) goto L_0x0019
            r4 = 0
            goto L_0x001f
        L_0x0019:
            com.google.common.collect.ImmutableList<V> r0 = r3.k
            java.lang.Object r4 = r0.get(r4)
        L_0x001f:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableSortedMap.get(java.lang.Object):java.lang.Object");
    }

    public Map.Entry<K, V> higherEntry(K k2) {
        return tailMap(k2, false).firstEntry();
    }

    public K higherKey(K k2) {
        return Maps.f(higherEntry(k2));
    }

    public final ImmutableSortedMap<K, V> k(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        if (i == i2) {
            return i(comparator());
        }
        return new ImmutableSortedMap<>(this.j.t(i, i2), this.k.subList(i, i2), (ImmutableSortedMap) null);
    }

    public Map.Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Map.Entry) entrySet().asList().get(size() - 1);
    }

    public K lastKey() {
        return keySet().last();
    }

    public Map.Entry<K, V> lowerEntry(K k2) {
        return headMap(k2, false).lastEntry();
    }

    public K lowerKey(K k2) {
        return Maps.f(lowerEntry(k2));
    }

    @Deprecated
    public final Map.Entry<K, V> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final Map.Entry<K, V> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.k.size();
    }

    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k2, V v) {
        return l(k2, v, Ordering.natural());
    }

    public ImmutableSortedSet<K> descendingKeySet() {
        return this.j.descendingSet();
    }

    public ImmutableSortedMap<K, V> descendingMap() {
        ImmutableSortedMap<K, V> immutableSortedMap = this.l;
        if (immutableSortedMap != null) {
            return immutableSortedMap;
        }
        if (isEmpty()) {
            return i(Ordering.from(comparator()).reverse());
        }
        return new ImmutableSortedMap<>((RegularImmutableSortedSet) this.j.descendingSet(), this.k.reverse(), this);
    }

    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    public ImmutableSortedSet<K> navigableKeySet() {
        return this.j;
    }

    public ImmutableCollection<V> values() {
        return this.k;
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        return h(map, (Comparator) Preconditions.checkNotNull(comparator));
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k2, V v, K k3, V v2) {
        return j(Ordering.natural(), false, new Map.Entry[]{ImmutableMap.d(k2, v), ImmutableMap.d(k3, v2)}, 2);
    }

    public ImmutableSortedMap<K, V> headMap(K k2) {
        return headMap(k2, false);
    }

    public ImmutableSortedSet<K> keySet() {
        return this.j;
    }

    public ImmutableSortedMap<K, V> subMap(K k2, K k3) {
        return subMap(k2, true, k3, false);
    }

    public ImmutableSortedMap<K, V> tailMap(K k2) {
        return tailMap(k2, true);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return copyOf(iterable, m);
    }

    public ImmutableSortedMap<K, V> headMap(K k2, boolean z) {
        return k(0, this.j.u(Preconditions.checkNotNull(k2), z));
    }

    public ImmutableSortedMap<K, V> subMap(K k2, boolean z, K k3, boolean z2) {
        Preconditions.checkNotNull(k2);
        Preconditions.checkNotNull(k3);
        Preconditions.checkArgument(comparator().compare(k2, k3) <= 0, "expected fromKey <= toKey but %s > %s", (Object) k2, (Object) k3);
        return headMap(k3, z2).tailMap(k2, z);
    }

    public ImmutableSortedMap<K, V> tailMap(K k2, boolean z) {
        return k(this.j.v(Preconditions.checkNotNull(k2), z), size());
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k2, V v, K k3, V v2, K k4, V v3) {
        return j(Ordering.natural(), false, new Map.Entry[]{ImmutableMap.d(k2, v), ImmutableMap.d(k3, v2), ImmutableMap.d(k4, v3)}, 3);
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.lang.Iterable<? extends java.util.Map$Entry<? extends K, ? extends V>>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <K, V> com.google.common.collect.ImmutableSortedMap<K, V> copyOf(java.lang.Iterable<? extends java.util.Map.Entry<? extends K, ? extends V>> r2, java.util.Comparator<? super K> r3) {
        /*
            java.lang.Object r3 = com.google.common.base.Preconditions.checkNotNull(r3)
            java.util.Comparator r3 = (java.util.Comparator) r3
            java.util.Map$Entry<?, ?>[] r0 = com.google.common.collect.ImmutableMap.i
            boolean r1 = r2 instanceof java.util.Collection
            if (r1 == 0) goto L_0x000f
            java.util.Collection r2 = (java.util.Collection) r2
            goto L_0x0017
        L_0x000f:
            java.util.Iterator r2 = r2.iterator()
            java.util.ArrayList r2 = com.google.common.collect.Lists.newArrayList(r2)
        L_0x0017:
            java.lang.Object[] r2 = r2.toArray(r0)
            java.util.Map$Entry[] r2 = (java.util.Map.Entry[]) r2
            java.util.Map$Entry[] r2 = (java.util.Map.Entry[]) r2
            int r0 = r2.length
            r1 = 0
            com.google.common.collect.ImmutableSortedMap r2 = j(r3, r1, r2, r0)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableSortedMap.copyOf(java.lang.Iterable, java.util.Comparator):com.google.common.collect.ImmutableSortedMap");
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k2, V v, K k3, V v2, K k4, V v3, K k5, V v4) {
        return j(Ordering.natural(), false, new Map.Entry[]{ImmutableMap.d(k2, v), ImmutableMap.d(k3, v2), ImmutableMap.d(k4, v3), ImmutableMap.d(k5, v4)}, 4);
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k2, V v, K k3, V v2, K k4, V v3, K k5, V v4, K k6, V v5) {
        return j(Ordering.natural(), false, new Map.Entry[]{ImmutableMap.d(k2, v), ImmutableMap.d(k3, v2), ImmutableMap.d(k4, v3), ImmutableMap.d(k5, v4), ImmutableMap.d(k6, v5)}, 5);
    }
}
