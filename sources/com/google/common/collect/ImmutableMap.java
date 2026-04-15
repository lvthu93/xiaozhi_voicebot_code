package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Maps;
import j$.util.Map;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable, j$.util.Map {
    public static final Map.Entry<?, ?>[] i = new Map.Entry[0];
    public transient ImmutableSet<Map.Entry<K, V>> c;
    public transient ImmutableSet<K> f;
    public transient ImmutableCollection<V> g;
    public transient ImmutableSetMultimap<K, V> h;

    public static class Builder<K, V> {
        public Comparator<? super V> a;
        public Object[] b;
        public int c;
        public boolean d;

        public Builder() {
            this(4);
        }

        public final void a() {
            int i;
            if (this.a != null) {
                if (this.d) {
                    this.b = Arrays.copyOf(this.b, this.c * 2);
                }
                Map.Entry[] entryArr = new Map.Entry[this.c];
                int i2 = 0;
                while (true) {
                    i = this.c;
                    if (i2 >= i) {
                        break;
                    }
                    Object[] objArr = this.b;
                    int i3 = i2 * 2;
                    entryArr[i2] = new AbstractMap.SimpleImmutableEntry(objArr[i3], objArr[i3 + 1]);
                    i2++;
                }
                Arrays.sort(entryArr, 0, i, Ordering.from(this.a).onResultOf(Maps.EntryFunction.f));
                for (int i4 = 0; i4 < this.c; i4++) {
                    int i5 = i4 * 2;
                    this.b[i5] = entryArr[i4].getKey();
                    this.b[i5 + 1] = entryArr[i4].getValue();
                }
            }
        }

        public ImmutableMap<K, V> build() {
            a();
            this.d = true;
            return RegularImmutableMap.h(this.c, this.b);
        }

        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            boolean z;
            if (this.a == null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z, "valueComparator was already set");
            this.a = (Comparator) Preconditions.checkNotNull(comparator, "valueComparator");
            return this;
        }

        public Builder<K, V> put(K k, V v) {
            int i = (this.c + 1) * 2;
            Object[] objArr = this.b;
            if (i > objArr.length) {
                this.b = Arrays.copyOf(objArr, ImmutableCollection.Builder.a(objArr.length, i));
                this.d = false;
            }
            CollectPreconditions.a(k, v);
            Object[] objArr2 = this.b;
            int i2 = this.c;
            int i3 = i2 * 2;
            objArr2[i3] = k;
            objArr2[i3 + 1] = v;
            this.c = i2 + 1;
            return this;
        }

        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            return putAll(map.entrySet());
        }

        public Builder(int i) {
            this.b = new Object[(i * 2)];
            this.c = 0;
            this.d = false;
        }

        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            if (iterable instanceof Collection) {
                int size = (((Collection) iterable).size() + this.c) * 2;
                Object[] objArr = this.b;
                if (size > objArr.length) {
                    this.b = Arrays.copyOf(objArr, ImmutableCollection.Builder.a(objArr.length, size));
                    this.d = false;
                }
            }
            for (Map.Entry put : iterable) {
                put(put);
            }
            return this;
        }

        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }
    }

    public static abstract class IteratorBasedImmutableMap<K, V> extends ImmutableMap<K, V> {
        public final ImmutableSet<Map.Entry<K, V>> a() {
            return new ImmutableMapEntrySet<K, V>() {
                public final ImmutableMap<K, V> m() {
                    return IteratorBasedImmutableMap.this;
                }

                public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedImmutableMap.this.h();
                }
            };
        }

        public ImmutableSet<K> b() {
            return new ImmutableMapKeySet(this);
        }

        public final ImmutableCollection<V> c() {
            return new ImmutableMapValues(this);
        }

        public /* bridge */ /* synthetic */ Set entrySet() {
            return ImmutableMap.super.entrySet();
        }

        public abstract UnmodifiableIterator<Map.Entry<K, V>> h();

        public /* bridge */ /* synthetic */ Set keySet() {
            return ImmutableMap.super.keySet();
        }

        public /* bridge */ /* synthetic */ Collection values() {
            return ImmutableMap.super.values();
        }
    }

    public final class MapViewOfValuesAsSingletonSets extends IteratorBasedImmutableMap<K, ImmutableSet<V>> {
        public MapViewOfValuesAsSingletonSets() {
        }

        public final ImmutableSet<K> b() {
            return ImmutableMap.this.keySet();
        }

        public boolean containsKey(Object obj) {
            return ImmutableMap.this.containsKey(obj);
        }

        public final boolean e() {
            return ImmutableMap.this.e();
        }

        public final boolean f() {
            return ImmutableMap.this.f();
        }

        public final UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>> h() {
            final UnmodifiableIterator it = ImmutableMap.this.entrySet().iterator();
            return new UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>>() {
                public boolean hasNext() {
                    return it.hasNext();
                }

                public Map.Entry<K, ImmutableSet<V>> next() {
                    final Map.Entry entry = (Map.Entry) it.next();
                    return new AbstractMapEntry<K, ImmutableSet<V>>() {
                        public K getKey() {
                            return entry.getKey();
                        }

                        public ImmutableSet<V> getValue() {
                            return ImmutableSet.of(entry.getValue());
                        }
                    };
                }
            };
        }

        public int hashCode() {
            return ImmutableMap.this.hashCode();
        }

        public int size() {
            return ImmutableMap.this.size();
        }

        public ImmutableSet<V> get(Object obj) {
            Object obj2 = ImmutableMap.this.get(obj);
            if (obj2 == null) {
                return null;
            }
            return ImmutableSet.of(obj2);
        }
    }

    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        public final Object[] c;
        public final Object[] f;

        public SerializedForm(ImmutableMap<?, ?> immutableMap) {
            this.c = new Object[immutableMap.size()];
            this.f = new Object[immutableMap.size()];
            UnmodifiableIterator<Map.Entry<?, ?>> it = immutableMap.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry next = it.next();
                this.c[i] = next.getKey();
                this.f[i] = next.getValue();
                i++;
            }
        }

        public final ImmutableMap a(Builder builder) {
            int i = 0;
            while (true) {
                Object[] objArr = this.c;
                if (i >= objArr.length) {
                    return builder.build();
                }
                builder.put(objArr[i], this.f[i]);
                i++;
            }
        }

        public Object readResolve() {
            return a(new Builder(this.c.length));
        }
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> Builder<K, V> builderWithExpectedSize(int i2) {
        CollectPreconditions.b(i2, "expectedSize");
        return new Builder<>(i2);
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if ((map instanceof ImmutableMap) && !(map instanceof SortedMap)) {
            ImmutableMap<K, V> immutableMap = (ImmutableMap) map;
            if (!immutableMap.f()) {
                return immutableMap;
            }
        }
        return copyOf(map.entrySet());
    }

    public static AbstractMap.SimpleImmutableEntry d(Object obj, Object obj2) {
        CollectPreconditions.a(obj, obj2);
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    public static <K, V> ImmutableMap<K, V> of() {
        return RegularImmutableMap.m;
    }

    public abstract ImmutableSet<Map.Entry<K, V>> a();

    public ImmutableSetMultimap<K, V> asMultimap() {
        if (isEmpty()) {
            return ImmutableSetMultimap.of();
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap = this.h;
        if (immutableSetMultimap != null) {
            return immutableSetMultimap;
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap2 = new ImmutableSetMultimap<>(new MapViewOfValuesAsSingletonSets(), size(), (Comparator) null);
        this.h = immutableSetMultimap2;
        return immutableSetMultimap2;
    }

    public abstract ImmutableSet<K> b();

    public abstract ImmutableCollection<V> c();

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
        return Map.CC.$default$compute(this, obj, biFunction);
    }

    public final /* synthetic */ Object computeIfAbsent(Object obj, Function function) {
        return Map.CC.$default$computeIfAbsent(this, obj, function);
    }

    public final /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
        return Map.CC.$default$computeIfPresent(this, obj, biFunction);
    }

    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    public boolean containsValue(Object obj) {
        return values().contains(obj);
    }

    public boolean e() {
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof java.util.Map) {
            return entrySet().equals(((java.util.Map) obj).entrySet());
        }
        return false;
    }

    public abstract boolean f();

    public final /* synthetic */ void forEach(BiConsumer biConsumer) {
        Map.CC.$default$forEach(this, biConsumer);
    }

    public UnmodifiableIterator<K> g() {
        final UnmodifiableIterator it = entrySet().iterator();
        return new UnmodifiableIterator<K>() {
            public boolean hasNext() {
                return UnmodifiableIterator.this.hasNext();
            }

            public K next() {
                return ((Map.Entry) UnmodifiableIterator.this.next()).getKey();
            }
        };
    }

    public abstract V get(Object obj);

    public final V getOrDefault(Object obj, V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    public int hashCode() {
        return Sets.b(entrySet());
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public final /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
        return Map.CC.$default$merge(this, obj, obj2, biFunction);
    }

    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(java.util.Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ Object putIfAbsent(Object obj, Object obj2) {
        return Map.CC.$default$putIfAbsent(this, obj, obj2);
    }

    @Deprecated
    public final V remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final /* synthetic */ boolean remove(Object obj, Object obj2) {
        return Map.CC.$default$remove(this, obj, obj2);
    }

    public final /* synthetic */ Object replace(Object obj, Object obj2) {
        return Map.CC.$default$replace(this, obj, obj2);
    }

    public final /* synthetic */ boolean replace(Object obj, Object obj2, Object obj3) {
        return Map.CC.$default$replace(this, obj, obj2, obj3);
    }

    public final /* synthetic */ void replaceAll(BiFunction biFunction) {
        Map.CC.$default$replaceAll(this, biFunction);
    }

    public String toString() {
        return Maps.j(this);
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v) {
        CollectPreconditions.a(k, v);
        return RegularImmutableMap.h(1, new Object[]{k, v});
    }

    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> immutableSet = this.c;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<Map.Entry<K, V>> a = a();
        this.c = a;
        return a;
    }

    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet = this.f;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet<K> b = b();
        this.f = b;
        return b;
    }

    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection = this.g;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        ImmutableCollection<V> c2 = c();
        this.g = c2;
        return c2;
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2) {
        CollectPreconditions.a(k, v);
        CollectPreconditions.a(k2, v2);
        return RegularImmutableMap.h(2, new Object[]{k, v, k2, v2});
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        Builder builder = new Builder(iterable instanceof Collection ? ((Collection) iterable).size() : 4);
        builder.putAll(iterable);
        return builder.build();
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        CollectPreconditions.a(k, v);
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        return RegularImmutableMap.h(3, new Object[]{k, v, k2, v2, k3, v3});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        CollectPreconditions.a(k, v);
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        return RegularImmutableMap.h(4, new Object[]{k, v, k2, v2, k3, v3, k4, v4});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        CollectPreconditions.a(k, v);
        CollectPreconditions.a(k2, v2);
        CollectPreconditions.a(k3, v3);
        CollectPreconditions.a(k4, v4);
        CollectPreconditions.a(k5, v5);
        return RegularImmutableMap.h(5, new Object[]{k, v, k2, v2, k3, v3, k4, v4, k5, v5});
    }
}
