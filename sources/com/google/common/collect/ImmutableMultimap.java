package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Serialization;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class ImmutableMultimap<K, V> extends BaseImmutableMultimap<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    public final transient ImmutableMap<K, ? extends ImmutableCollection<V>> j;
    public final transient int k;

    public static class EntryCollection<K, V> extends ImmutableCollection<Map.Entry<K, V>> {
        private static final long serialVersionUID = 0;
        public final ImmutableMultimap<K, V> f;

        public EntryCollection(ImmutableMultimap<K, V> immutableMultimap) {
            this.f = immutableMultimap;
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return this.f.containsEntry(entry.getKey(), entry.getValue());
        }

        public final boolean isPartialView() {
            return this.f.j.f();
        }

        public int size() {
            return this.f.size();
        }

        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            ImmutableMultimap<K, V> immutableMultimap = this.f;
            immutableMultimap.getClass();
            return new UnmodifiableIterator<Map.Entry<Object, Object>>(immutableMultimap) {
                public final UnmodifiableIterator c;
                public Object f = null;
                public UnmodifiableIterator g = Iterators.ArrayItr.i;

                {
                    this.c = r1.j.entrySet().iterator();
                }

                public boolean hasNext() {
                    return this.g.hasNext() || this.c.hasNext();
                }

                public Map.Entry<Object, Object> next() {
                    if (!this.g.hasNext()) {
                        Map.Entry entry = (Map.Entry) this.c.next();
                        this.f = entry.getKey();
                        this.g = ((ImmutableCollection) entry.getValue()).iterator();
                    }
                    return Maps.immutableEntry(this.f, this.g.next());
                }
            };
        }
    }

    public static class FieldSettersHolder {
        public static final Serialization.FieldSetter<ImmutableMultimap> a;
        public static final Serialization.FieldSetter<ImmutableMultimap> b;

        static {
            Class<ImmutableMultimap> cls = ImmutableMultimap.class;
            a = Serialization.a(cls, "map");
            b = Serialization.a(cls, "size");
        }
    }

    public class Keys extends ImmutableMultiset<K> {
        public Keys() {
        }

        public boolean contains(Object obj) {
            return ImmutableMultimap.this.containsKey(obj);
        }

        public int count(Object obj) {
            Collection collection = (Collection) ImmutableMultimap.this.j.get(obj);
            if (collection == null) {
                return 0;
            }
            return collection.size();
        }

        public final boolean isPartialView() {
            return true;
        }

        public final Multiset.Entry<K> j(int i2) {
            Map.Entry entry = ImmutableMultimap.this.j.entrySet().asList().get(i2);
            return Multisets.immutableEntry(entry.getKey(), ((Collection) entry.getValue()).size());
        }

        public int size() {
            return ImmutableMultimap.this.size();
        }

        public Object writeReplace() {
            return new KeysSerializedForm(ImmutableMultimap.this);
        }

        public ImmutableSet<K> elementSet() {
            return ImmutableMultimap.this.keySet();
        }
    }

    public static final class KeysSerializedForm implements Serializable {
        public final ImmutableMultimap<?, ?> c;

        public KeysSerializedForm(ImmutableMultimap<?, ?> immutableMultimap) {
            this.c = immutableMultimap;
        }

        public Object readResolve() {
            return this.c.keys();
        }
    }

    public static final class Values<K, V> extends ImmutableCollection<V> {
        private static final long serialVersionUID = 0;
        public final transient ImmutableMultimap<K, V> f;

        public Values(ImmutableMultimap<K, V> immutableMultimap) {
            this.f = immutableMultimap;
        }

        public final int a(int i, Object[] objArr) {
            UnmodifiableIterator<? extends ImmutableCollection<V>> it = this.f.j.values().iterator();
            while (it.hasNext()) {
                i = ((ImmutableCollection) it.next()).a(i, objArr);
            }
            return i;
        }

        public boolean contains(Object obj) {
            return this.f.containsValue(obj);
        }

        public final boolean isPartialView() {
            return true;
        }

        public int size() {
            return this.f.size();
        }

        public UnmodifiableIterator<V> iterator() {
            ImmutableMultimap<K, V> immutableMultimap = this.f;
            immutableMultimap.getClass();
            return new UnmodifiableIterator<Object>(immutableMultimap) {
                public final UnmodifiableIterator c;
                public UnmodifiableIterator f = Iterators.ArrayItr.i;

                {
                    this.c = r1.j.values().iterator();
                }

                public boolean hasNext() {
                    return this.f.hasNext() || this.c.hasNext();
                }

                public Object next() {
                    if (!this.f.hasNext()) {
                        this.f = ((ImmutableCollection) this.c.next()).iterator();
                    }
                    return this.f.next();
                }
            };
        }
    }

    public ImmutableMultimap(int i, ImmutableMap immutableMap) {
        this.j = immutableMap;
        this.k = i;
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        if (multimap instanceof ImmutableMultimap) {
            ImmutableMultimap<K, V> immutableMultimap = (ImmutableMultimap) multimap;
            if (!immutableMultimap.j.f()) {
                return immutableMultimap;
            }
        }
        return ImmutableListMultimap.copyOf(multimap);
    }

    public static <K, V> ImmutableMultimap<K, V> of() {
        return ImmutableListMultimap.of();
    }

    public final Map<K, Collection<V>> a() {
        throw new AssertionError("should never be called");
    }

    public final Collection b() {
        return new EntryCollection(this);
    }

    public final Set<K> c() {
        throw new AssertionError("unreachable");
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public /* bridge */ /* synthetic */ boolean containsEntry(Object obj, Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public boolean containsKey(Object obj) {
        return this.j.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        return obj != null && super.containsValue(obj);
    }

    public final Multiset d() {
        return new Keys();
    }

    public final Collection e() {
        return new Values(this);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final Iterator f() {
        return new UnmodifiableIterator<Map.Entry<Object, Object>>(this) {
            public final UnmodifiableIterator c;
            public Object f = null;
            public UnmodifiableIterator g = Iterators.ArrayItr.i;

            {
                this.c = r1.j.entrySet().iterator();
            }

            public boolean hasNext() {
                return this.g.hasNext() || this.c.hasNext();
            }

            public Map.Entry<Object, Object> next() {
                if (!this.g.hasNext()) {
                    Map.Entry entry = (Map.Entry) this.c.next();
                    this.f = entry.getKey();
                    this.g = ((ImmutableCollection) entry.getValue()).iterator();
                }
                return Maps.immutableEntry(this.f, this.g.next());
            }
        };
    }

    public final Iterator g() {
        return new UnmodifiableIterator<Object>(this) {
            public final UnmodifiableIterator c;
            public UnmodifiableIterator f = Iterators.ArrayItr.i;

            {
                this.c = r1.j.values().iterator();
            }

            public boolean hasNext() {
                return this.f.hasNext() || this.c.hasNext();
            }

            public Object next() {
                if (!this.f.hasNext()) {
                    this.f = ((ImmutableCollection) this.c.next()).iterator();
                }
                return this.f.next();
            }
        };
    }

    public abstract ImmutableCollection<V> get(K k2);

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public abstract ImmutableMultimap<V, K> inverse();

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Deprecated
    public boolean put(K k2, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public boolean putAll(K k2, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.k;
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public static class Builder<K, V> {
        public final Map<K, Collection<V>> a = CompactHashMap.create();
        public Comparator<? super K> b;
        public Comparator<? super V> c;

        public Collection<V> a() {
            return new ArrayList();
        }

        public ImmutableMultimap<K, V> build() {
            Collection entrySet = this.a.entrySet();
            Comparator comparator = this.b;
            if (comparator != null) {
                entrySet = Ordering.from(comparator).onResultOf(Maps.EntryFunction.c).immutableSortedCopy(entrySet);
            }
            return ImmutableListMultimap.h(this.c, entrySet);
        }

        public Builder<K, V> orderKeysBy(Comparator<? super K> comparator) {
            this.b = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            this.c = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        public Builder<K, V> put(K k, V v) {
            CollectPreconditions.a(k, v);
            Map<K, Collection<V>> map = this.a;
            Collection collection = map.get(k);
            if (collection == null) {
                collection = a();
                map.put(k, collection);
            }
            collection.add(v);
            return this;
        }

        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            for (Map.Entry put : iterable) {
                put(put);
            }
            return this;
        }

        public Builder<K, V> putAll(K k, Iterable<? extends V> iterable) {
            if (k != null) {
                Map<K, Collection<V>> map = this.a;
                Collection collection = map.get(k);
                if (collection != null) {
                    for (Object next : iterable) {
                        CollectPreconditions.a(k, next);
                        collection.add(next);
                    }
                    return this;
                }
                Iterator<? extends V> it = iterable.iterator();
                if (!it.hasNext()) {
                    return this;
                }
                Collection a2 = a();
                while (it.hasNext()) {
                    Object next2 = it.next();
                    CollectPreconditions.a(k, next2);
                    a2.add(next2);
                }
                map.put(k, a2);
                return this;
            }
            throw new NullPointerException("null key in entry: null=" + Iterables.toString(iterable));
        }

        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }

        public Builder<K, V> putAll(K k, V... vArr) {
            return putAll(k, Arrays.asList(vArr));
        }

        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            for (Map.Entry next : multimap.asMap().entrySet()) {
                putAll(next.getKey(), (Iterable) next.getValue());
            }
            return this;
        }
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k2, V v) {
        return ImmutableListMultimap.of(k2, v);
    }

    public ImmutableMap<K, Collection<V>> asMap() {
        return this.j;
    }

    public ImmutableCollection<Map.Entry<K, V>> entries() {
        return (ImmutableCollection) super.entries();
    }

    public ImmutableSet<K> keySet() {
        return this.j.keySet();
    }

    public ImmutableMultiset<K> keys() {
        return (ImmutableMultiset) super.keys();
    }

    @Deprecated
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public ImmutableCollection<V> removeAll(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public ImmutableCollection<V> replaceValues(K k2, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    public ImmutableCollection<V> values() {
        return (ImmutableCollection) super.values();
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k2, V v, K k3, V v2) {
        return ImmutableListMultimap.of(k2, v, k3, v2);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k2, V v, K k3, V v2, K k4, V v3) {
        return ImmutableListMultimap.of(k2, v, k3, v2, k4, v3);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k2, V v, K k3, V v2, K k4, V v3, K k5, V v4) {
        return ImmutableListMultimap.of(k2, v, k3, v2, k4, v3, k5, v4);
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ImmutableListMultimap.copyOf(iterable);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k2, V v, K k3, V v2, K k4, V v3, K k5, V v4, K k6, V v5) {
        return ImmutableListMultimap.of(k2, v, k3, v2, k4, v3, k5, v4, k6, v5);
    }
}
