package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

abstract class AbstractBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
    private static final long serialVersionUID = 0;
    public transient Map<K, V> c;
    public transient AbstractBiMap<V, K> f;
    public transient Set<K> g;
    public transient Set<V> h;
    public transient Set<Map.Entry<K, V>> i;

    public class BiMapEntry extends ForwardingMapEntry<K, V> {
        public final Map.Entry<K, V> c;

        public BiMapEntry(Map.Entry<K, V> entry) {
            this.c = entry;
        }

        public final Map.Entry<K, V> a() {
            return this.c;
        }

        public final Object delegate() {
            return this.c;
        }

        public V setValue(V v) {
            AbstractBiMap abstractBiMap = AbstractBiMap.this;
            abstractBiMap.f(v);
            Preconditions.checkState(abstractBiMap.entrySet().contains(this), "entry no longer in map");
            if (Objects.equal(v, getValue())) {
                return v;
            }
            Preconditions.checkArgument(!abstractBiMap.containsValue(v), "value already present: %s", (Object) v);
            V value = this.c.setValue(v);
            Preconditions.checkState(Objects.equal(v, abstractBiMap.get(getKey())), "entry no longer in map");
            Object key = getKey();
            abstractBiMap.f.c.remove(value);
            abstractBiMap.f.c.put(v, key);
            return value;
        }
    }

    public class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
        public final Set<Map.Entry<K, V>> c;

        public EntrySet() {
            this.c = AbstractBiMap.this.c.entrySet();
        }

        public final Collection a() {
            return this.c;
        }

        public void clear() {
            AbstractBiMap.this.clear();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Preconditions.checkNotNull(entry);
            return this.c.contains(new AbstractMapEntry<Object, Object>(entry) {
                public Object getKey() {
                    return r1.getKey();
                }

                public Object getValue() {
                    return r1.getValue();
                }
            });
        }

        public boolean containsAll(Collection<?> collection) {
            return Collections2.b(this, collection);
        }

        public final Object delegate() {
            return this.c;
        }

        public final Set<Map.Entry<K, V>> g() {
            return this.c;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            AbstractBiMap abstractBiMap = AbstractBiMap.this;
            return new Object(abstractBiMap.c.entrySet().iterator()) {
                public Map.Entry<Object, Object> c;
                public final /* synthetic */ Iterator f;

                {
                    this.f = r2;
                }

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return this.f.hasNext();
                }

                public void remove() {
                    boolean z;
                    if (this.c != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    CollectPreconditions.e(z);
                    Object value = this.c.getValue();
                    this.f.remove();
                    AbstractBiMap.this.f.c.remove(value);
                    this.c = null;
                }

                public Map.Entry<Object, Object> next() {
                    Map.Entry<Object, Object> entry = (Map.Entry) this.f.next();
                    this.c = entry;
                    return new BiMapEntry(entry);
                }
            };
        }

        public boolean remove(Object obj) {
            Set<Map.Entry<K, V>> set = this.c;
            if (!set.contains(obj)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            AbstractBiMap.this.f.c.remove(entry.getValue());
            set.remove(entry);
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            return Sets.c(this, (Collection) Preconditions.checkNotNull(collection));
        }

        public boolean retainAll(Collection<?> collection) {
            return Iterators.retainAll(iterator(), collection);
        }

        public <T> T[] toArray(T[] tArr) {
            return ObjectArrays.c(this, tArr);
        }

        public Object[] toArray() {
            return c();
        }
    }

    public static class Inverse<K, V> extends AbstractBiMap<K, V> {
        private static final long serialVersionUID = 0;

        public Inverse(AbstractMap abstractMap, AbstractBiMap abstractBiMap) {
            super(abstractMap, abstractBiMap);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.f = (AbstractBiMap) objectInputStream.readObject();
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(inverse());
        }

        public final K c(K k) {
            return this.f.f(k);
        }

        public final Object delegate() {
            return this.c;
        }

        public final V f(V v) {
            return this.f.c(v);
        }

        public Object readResolve() {
            return inverse().inverse();
        }

        public /* bridge */ /* synthetic */ Collection values() {
            return AbstractBiMap.super.values();
        }
    }

    public class KeySet extends ForwardingSet<K> {
        public KeySet() {
        }

        public void clear() {
            AbstractBiMap.this.clear();
        }

        /* renamed from: g */
        public final Set<K> delegate() {
            return AbstractBiMap.this.c.keySet();
        }

        public java.util.Iterator<K> iterator() {
            return new TransformedIterator<Map.Entry<Object, Object>, Object>(AbstractBiMap.this.entrySet().iterator()) {
                public final Object a(Object obj) {
                    return ((Map.Entry) obj).getKey();
                }
            };
        }

        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            AbstractBiMap abstractBiMap = AbstractBiMap.this;
            abstractBiMap.f.c.remove(abstractBiMap.c.remove(obj));
            return true;
        }

        public boolean removeAll(Collection<?> collection) {
            return Sets.c(this, (Collection) Preconditions.checkNotNull(collection));
        }

        public boolean retainAll(Collection<?> collection) {
            return Iterators.retainAll(iterator(), collection);
        }
    }

    public class ValueSet extends ForwardingSet<V> {
        public final Set<V> c;

        public ValueSet() {
            this.c = AbstractBiMap.this.f.keySet();
        }

        public final Collection a() {
            return this.c;
        }

        public final Object delegate() {
            return this.c;
        }

        public final Set<V> g() {
            return this.c;
        }

        public java.util.Iterator<V> iterator() {
            return new TransformedIterator<Map.Entry<Object, Object>, Object>(AbstractBiMap.this.entrySet().iterator()) {
                public final Object a(Object obj) {
                    return ((Map.Entry) obj).getValue();
                }
            };
        }

        public <T> T[] toArray(T[] tArr) {
            return ObjectArrays.c(this, tArr);
        }

        public String toString() {
            return f();
        }

        public Object[] toArray() {
            return c();
        }
    }

    public AbstractBiMap() {
        throw null;
    }

    public AbstractBiMap(EnumMap enumMap, AbstractMap abstractMap) {
        j(enumMap, abstractMap);
    }

    public final Map<K, V> a() {
        return this.c;
    }

    public K c(K k) {
        return k;
    }

    public void clear() {
        this.c.clear();
        this.f.c.clear();
    }

    public boolean containsValue(Object obj) {
        return this.f.containsKey(obj);
    }

    public Object delegate() {
        return this.c;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.i;
        if (set != null) {
            return set;
        }
        EntrySet entrySet = new EntrySet();
        this.i = entrySet;
        return entrySet;
    }

    public V f(V v) {
        return v;
    }

    public V forcePut(K k, V v) {
        return g(k, v, true);
    }

    public final V g(K k, V v, boolean z) {
        c(k);
        f(v);
        boolean containsKey = containsKey(k);
        if (containsKey && Objects.equal(v, get(k))) {
            return v;
        }
        if (z) {
            inverse().remove(v);
        } else {
            Preconditions.checkArgument(!containsValue(v), "value already present: %s", (Object) v);
        }
        V put = this.c.put(k, v);
        if (containsKey) {
            this.f.c.remove(put);
        }
        this.f.c.put(v, k);
        return put;
    }

    public BiMap<V, K> inverse() {
        return this.f;
    }

    public final void j(EnumMap enumMap, AbstractMap abstractMap) {
        boolean z;
        boolean z2;
        boolean z3 = false;
        if (this.c == null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkState(z);
        if (this.f == null) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkState(z2);
        Preconditions.checkArgument(enumMap.isEmpty());
        Preconditions.checkArgument(abstractMap.isEmpty());
        if (enumMap != abstractMap) {
            z3 = true;
        }
        Preconditions.checkArgument(z3);
        this.c = enumMap;
        this.f = new Inverse(abstractMap, this);
    }

    public Set<K> keySet() {
        Set<K> set = this.g;
        if (set != null) {
            return set;
        }
        KeySet keySet = new KeySet();
        this.g = keySet;
        return keySet;
    }

    public V put(K k, V v) {
        return g(k, v, false);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry next : map.entrySet()) {
            put(next.getKey(), next.getValue());
        }
    }

    public V remove(Object obj) {
        if (!containsKey(obj)) {
            return null;
        }
        V remove = this.c.remove(obj);
        this.f.c.remove(remove);
        return remove;
    }

    public Set<V> values() {
        Set<V> set = this.h;
        if (set != null) {
            return set;
        }
        ValueSet valueSet = new ValueSet();
        this.h = valueSet;
        return valueSet;
    }

    public AbstractBiMap(AbstractMap abstractMap, AbstractBiMap abstractBiMap) {
        this.c = abstractMap;
        this.f = abstractBiMap;
    }
}
