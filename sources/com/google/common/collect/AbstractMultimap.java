package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimaps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
    public transient Collection<Map.Entry<K, V>> c;
    public transient Set<K> f;
    public transient Multiset<K> g;
    public transient Collection<V> h;
    public transient Map<K, Collection<V>> i;

    public class Entries extends Multimaps.Entries<K, V> {
        public Entries() {
        }

        public final Multimap<K, V> a() {
            return AbstractMultimap.this;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractMultimap.this.f();
        }
    }

    public class EntrySet extends AbstractMultimap<K, V>.Entries implements Set<Map.Entry<K, V>> {
        public EntrySet(AbstractMultimap abstractMultimap) {
            super();
        }

        public boolean equals(Object obj) {
            return Sets.a(this, obj);
        }

        public int hashCode() {
            return Sets.b(this);
        }
    }

    public class Values extends AbstractCollection<V> {
        public Values() {
        }

        public void clear() {
            AbstractMultimap.this.clear();
        }

        public boolean contains(Object obj) {
            return AbstractMultimap.this.containsValue(obj);
        }

        public Iterator<V> iterator() {
            return AbstractMultimap.this.g();
        }

        public int size() {
            return AbstractMultimap.this.size();
        }
    }

    public abstract Map<K, Collection<V>> a();

    public Map<K, Collection<V>> asMap() {
        Map<K, Collection<V>> map = this.i;
        if (map != null) {
            return map;
        }
        Map<K, Collection<V>> a = a();
        this.i = a;
        return a;
    }

    public abstract Collection<Map.Entry<K, V>> b();

    public abstract Set<K> c();

    public boolean containsEntry(Object obj, Object obj2) {
        Collection collection = (Collection) asMap().get(obj);
        if (collection == null || !collection.contains(obj2)) {
            return false;
        }
        return true;
    }

    public boolean containsValue(Object obj) {
        for (Collection contains : asMap().values()) {
            if (contains.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    public abstract Multiset<K> d();

    public abstract Collection<V> e();

    public Collection<Map.Entry<K, V>> entries() {
        Collection<Map.Entry<K, V>> collection = this.c;
        if (collection != null) {
            return collection;
        }
        Collection<Map.Entry<K, V>> b = b();
        this.c = b;
        return b;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Multimap) {
            return asMap().equals(((Multimap) obj).asMap());
        }
        return false;
    }

    public abstract Iterator<Map.Entry<K, V>> f();

    public Iterator<V> g() {
        return new TransformedIterator<Map.Entry<Object, Object>, Object>(entries().iterator()) {
            public final Object a(Object obj) {
                return ((Map.Entry) obj).getValue();
            }
        };
    }

    public int hashCode() {
        return asMap().hashCode();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Set<K> keySet() {
        Set<K> set = this.f;
        if (set != null) {
            return set;
        }
        Set<K> c2 = c();
        this.f = c2;
        return c2;
    }

    public Multiset<K> keys() {
        Multiset<K> multiset = this.g;
        if (multiset != null) {
            return multiset;
        }
        Multiset<K> d = d();
        this.g = d;
        return d;
    }

    public boolean put(K k, V v) {
        return get(k).add(v);
    }

    public boolean putAll(K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            if (collection.isEmpty() || !get(k).addAll(collection)) {
                return false;
            }
            return true;
        }
        Iterator<? extends V> it = iterable.iterator();
        if (!it.hasNext() || !Iterators.addAll(get(k), it)) {
            return false;
        }
        return true;
    }

    public boolean remove(Object obj, Object obj2) {
        Collection collection = (Collection) asMap().get(obj);
        if (collection == null || !collection.remove(obj2)) {
            return false;
        }
        return true;
    }

    public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        Preconditions.checkNotNull(iterable);
        Collection<V> removeAll = removeAll(k);
        putAll(k, iterable);
        return removeAll;
    }

    public String toString() {
        return asMap().toString();
    }

    public Collection<V> values() {
        Collection<V> collection = this.h;
        if (collection != null) {
            return collection;
        }
        Collection<V> e = e();
        this.h = e;
        return e;
    }

    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        boolean z = false;
        for (Map.Entry next : multimap.entries()) {
            z |= put(next.getKey(), next.getValue());
        }
        return z;
    }
}
