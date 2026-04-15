package com.google.common.collect;

import WrappedCollection.WrappedIterator;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import j$.util.Iterator;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.function.Consumer;

abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
    private static final long serialVersionUID = 2447537837011683357L;
    public transient Map<K, Collection<V>> j;
    public transient int k;

    public class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        public final transient Map<K, Collection<V>> h;

        public class AsMapEntries extends Maps.EntrySet<K, Collection<V>> {
            public AsMapEntries() {
            }

            public final Map<K, Collection<V>> a() {
                return AsMap.this;
            }

            public boolean contains(Object obj) {
                return Collections2.d(obj, AsMap.this.h.entrySet());
            }

            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new AsMapIterator();
            }

            public boolean remove(Object obj) {
                Collection<V> collection;
                if (!contains(obj)) {
                    return false;
                }
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                Object key = ((Map.Entry) obj).getKey();
                Map<K, Collection<V>> map = abstractMapBasedMultimap.j;
                Preconditions.checkNotNull(map);
                try {
                    collection = map.remove(key);
                } catch (ClassCastException | NullPointerException unused) {
                    collection = null;
                }
                Collection collection2 = collection;
                if (collection2 == null) {
                    return true;
                }
                int size = collection2.size();
                collection2.clear();
                abstractMapBasedMultimap.k -= size;
                return true;
            }
        }

        public class AsMapIterator implements Iterator<Map.Entry<K, Collection<V>>>, j$.util.Iterator {
            public final Iterator<Map.Entry<K, Collection<V>>> c;
            public Collection<V> f;

            public AsMapIterator() {
                this.c = AsMap.this.h.entrySet().iterator();
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                return this.c.hasNext();
            }

            public void remove() {
                boolean z;
                if (this.f != null) {
                    z = true;
                } else {
                    z = false;
                }
                CollectPreconditions.e(z);
                this.c.remove();
                AbstractMapBasedMultimap.this.k -= this.f.size();
                this.f.clear();
                this.f = null;
            }

            public Map.Entry<K, Collection<V>> next() {
                Map.Entry next = this.c.next();
                this.f = (Collection) next.getValue();
                return AsMap.this.b(next);
            }
        }

        public AsMap(Map<K, Collection<V>> map) {
            this.h = map;
        }

        public final Map.Entry<K, Collection<V>> b(Map.Entry<K, Collection<V>> entry) {
            K key = entry.getKey();
            return Maps.immutableEntry(key, AbstractMapBasedMultimap.this.o(key, entry.getValue()));
        }

        public void clear() {
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            if (this.h == abstractMapBasedMultimap.j) {
                abstractMapBasedMultimap.clear();
            } else {
                Iterators.b(new AsMapIterator());
            }
        }

        public boolean containsKey(Object obj) {
            return Maps.h(this.h, obj);
        }

        public final Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new AsMapEntries();
        }

        public boolean equals(Object obj) {
            return this == obj || this.h.equals(obj);
        }

        public int hashCode() {
            return this.h.hashCode();
        }

        public Set<K> keySet() {
            return AbstractMapBasedMultimap.this.keySet();
        }

        public int size() {
            return this.h.size();
        }

        public String toString() {
            return this.h.toString();
        }

        public Collection<V> get(Object obj) {
            Collection collection = (Collection) Maps.i(this.h, obj);
            if (collection == null) {
                return null;
            }
            return AbstractMapBasedMultimap.this.o(obj, collection);
        }

        public Collection<V> remove(Object obj) {
            Collection remove = this.h.remove(obj);
            if (remove == null) {
                return null;
            }
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            Collection<V> h2 = abstractMapBasedMultimap.h();
            h2.addAll(remove);
            abstractMapBasedMultimap.k -= remove.size();
            remove.clear();
            return h2;
        }
    }

    public abstract class Itr<T> implements java.util.Iterator<T>, j$.util.Iterator {
        public final java.util.Iterator<Map.Entry<K, Collection<V>>> c;
        public K f = null;
        public Collection<V> g = null;
        public java.util.Iterator<V> h = Iterators.EmptyModifiableIterator.c;

        public Itr() {
            this.c = AbstractMapBasedMultimap.this.j.entrySet().iterator();
        }

        public abstract T a(K k, V v);

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            return this.c.hasNext() || this.h.hasNext();
        }

        public T next() {
            if (!this.h.hasNext()) {
                Map.Entry next = this.c.next();
                this.f = next.getKey();
                Collection<V> collection = (Collection) next.getValue();
                this.g = collection;
                this.h = collection.iterator();
            }
            return a(this.f, this.h.next());
        }

        public void remove() {
            this.h.remove();
            if (this.g.isEmpty()) {
                this.c.remove();
            }
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            abstractMapBasedMultimap.k--;
        }
    }

    public class KeySet extends Maps.KeySet<K, Collection<V>> {
        public KeySet(Map<K, Collection<V>> map) {
            super(map);
        }

        public void clear() {
            Iterators.b(iterator());
        }

        public boolean containsAll(Collection<?> collection) {
            return this.c.keySet().containsAll(collection);
        }

        public boolean equals(Object obj) {
            return this == obj || this.c.keySet().equals(obj);
        }

        public int hashCode() {
            return this.c.keySet().hashCode();
        }

        public java.util.Iterator<K> iterator() {
            final java.util.Iterator<Map.Entry<K, V>> it = this.c.entrySet().iterator();
            return new Object() {
                public Map.Entry<K, Collection<V>> c;

                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return it.hasNext();
                }

                public K next() {
                    Map.Entry<K, Collection<V>> entry = (Map.Entry) it.next();
                    this.c = entry;
                    return entry.getKey();
                }

                public void remove() {
                    boolean z;
                    if (this.c != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    CollectPreconditions.e(z);
                    Collection value = this.c.getValue();
                    it.remove();
                    AbstractMapBasedMultimap.this.k -= value.size();
                    value.clear();
                    this.c = null;
                }
            };
        }

        public boolean remove(Object obj) {
            int i;
            Collection collection = (Collection) this.c.remove(obj);
            if (collection != null) {
                i = collection.size();
                collection.clear();
                AbstractMapBasedMultimap.this.k -= i;
            } else {
                i = 0;
            }
            if (i > 0) {
                return true;
            }
            return false;
        }
    }

    public class NavigableAsMap extends AbstractMapBasedMultimap<K, V>.SortedAsMap implements NavigableMap<K, Collection<V>> {
        public NavigableAsMap(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
        }

        public final SortedSet c() {
            return new NavigableKeySet(d());
        }

        public Map.Entry<K, Collection<V>> ceilingEntry(K k) {
            Map.Entry ceilingEntry = d().ceilingEntry(k);
            if (ceilingEntry == null) {
                return null;
            }
            return b(ceilingEntry);
        }

        public K ceilingKey(K k) {
            return d().ceilingKey(k);
        }

        public final Set createKeySet() {
            return new NavigableKeySet(d());
        }

        public NavigableSet<K> descendingKeySet() {
            return descendingMap().navigableKeySet();
        }

        public NavigableMap<K, Collection<V>> descendingMap() {
            return new NavigableAsMap(d().descendingMap());
        }

        public final Map.Entry<K, Collection<V>> e(java.util.Iterator<Map.Entry<K, Collection<V>>> it) {
            if (!it.hasNext()) {
                return null;
            }
            Map.Entry next = it.next();
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            Collection h = abstractMapBasedMultimap.h();
            h.addAll((Collection) next.getValue());
            it.remove();
            return Maps.immutableEntry(next.getKey(), abstractMapBasedMultimap.n(h));
        }

        /* renamed from: f */
        public final NavigableMap<K, Collection<V>> d() {
            return (NavigableMap) ((SortedMap) this.h);
        }

        public Map.Entry<K, Collection<V>> firstEntry() {
            Map.Entry firstEntry = d().firstEntry();
            if (firstEntry == null) {
                return null;
            }
            return b(firstEntry);
        }

        public Map.Entry<K, Collection<V>> floorEntry(K k) {
            Map.Entry floorEntry = d().floorEntry(k);
            if (floorEntry == null) {
                return null;
            }
            return b(floorEntry);
        }

        public K floorKey(K k) {
            return d().floorKey(k);
        }

        public Map.Entry<K, Collection<V>> higherEntry(K k) {
            Map.Entry higherEntry = d().higherEntry(k);
            if (higherEntry == null) {
                return null;
            }
            return b(higherEntry);
        }

        public K higherKey(K k) {
            return d().higherKey(k);
        }

        public Map.Entry<K, Collection<V>> lastEntry() {
            Map.Entry lastEntry = d().lastEntry();
            if (lastEntry == null) {
                return null;
            }
            return b(lastEntry);
        }

        public Map.Entry<K, Collection<V>> lowerEntry(K k) {
            Map.Entry lowerEntry = d().lowerEntry(k);
            if (lowerEntry == null) {
                return null;
            }
            return b(lowerEntry);
        }

        public K lowerKey(K k) {
            return d().lowerKey(k);
        }

        public NavigableSet<K> navigableKeySet() {
            return keySet();
        }

        public Map.Entry<K, Collection<V>> pollFirstEntry() {
            return e(entrySet().iterator());
        }

        public Map.Entry<K, Collection<V>> pollLastEntry() {
            return e(descendingMap().entrySet().iterator());
        }

        public NavigableMap<K, Collection<V>> headMap(K k) {
            return headMap(k, false);
        }

        public NavigableMap<K, Collection<V>> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        public NavigableMap<K, Collection<V>> tailMap(K k) {
            return tailMap(k, true);
        }

        public NavigableMap<K, Collection<V>> headMap(K k, boolean z) {
            return new NavigableAsMap(d().headMap(k, z));
        }

        public NavigableSet<K> keySet() {
            return (NavigableSet) super.keySet();
        }

        public NavigableMap<K, Collection<V>> subMap(K k, boolean z, K k2, boolean z2) {
            return new NavigableAsMap(d().subMap(k, z, k2, z2));
        }

        public NavigableMap<K, Collection<V>> tailMap(K k, boolean z) {
            return new NavigableAsMap(d().tailMap(k, z));
        }
    }

    public class NavigableKeySet extends AbstractMapBasedMultimap<K, V>.SortedKeySet implements NavigableSet<K> {
        public NavigableKeySet(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
        }

        /* renamed from: c */
        public final NavigableMap<K, Collection<V>> b() {
            return (NavigableMap) ((SortedMap) this.c);
        }

        public K ceiling(K k) {
            return b().ceilingKey(k);
        }

        public java.util.Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        public NavigableSet<K> descendingSet() {
            return new NavigableKeySet(b().descendingMap());
        }

        public K floor(K k) {
            return b().floorKey(k);
        }

        public K higher(K k) {
            return b().higherKey(k);
        }

        public K lower(K k) {
            return b().lowerKey(k);
        }

        public K pollFirst() {
            return Iterators.c(iterator());
        }

        public K pollLast() {
            return Iterators.c(descendingIterator());
        }

        public NavigableSet<K> headSet(K k) {
            return headSet(k, false);
        }

        public NavigableSet<K> subSet(K k, K k2) {
            return subSet(k, true, k2, false);
        }

        public NavigableSet<K> tailSet(K k) {
            return tailSet(k, true);
        }

        public NavigableSet<K> headSet(K k, boolean z) {
            return new NavigableKeySet(b().headMap(k, z));
        }

        public NavigableSet<K> subSet(K k, boolean z, K k2, boolean z2) {
            return new NavigableKeySet(b().subMap(k, z, k2, z2));
        }

        public NavigableSet<K> tailSet(K k, boolean z) {
            return new NavigableKeySet(b().tailMap(k, z));
        }
    }

    public class RandomAccessWrappedList extends AbstractMapBasedMultimap<K, V>.WrappedList implements RandomAccess {
        public RandomAccessWrappedList(AbstractMapBasedMultimap abstractMapBasedMultimap, K k, List<V> list, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            super(k, list, wrappedCollection);
        }
    }

    public class SortedAsMap extends AbstractMapBasedMultimap<K, V>.AsMap implements SortedMap<K, Collection<V>> {
        public SortedSet<K> j;

        public SortedAsMap(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
        }

        /* renamed from: c */
        public SortedSet<K> createKeySet() {
            return new SortedKeySet(d());
        }

        public Comparator<? super K> comparator() {
            return d().comparator();
        }

        public SortedMap<K, Collection<V>> d() {
            return (SortedMap) this.h;
        }

        public K firstKey() {
            return d().firstKey();
        }

        public SortedMap<K, Collection<V>> headMap(K k2) {
            return new SortedAsMap(d().headMap(k2));
        }

        public K lastKey() {
            return d().lastKey();
        }

        public SortedMap<K, Collection<V>> subMap(K k2, K k3) {
            return new SortedAsMap(d().subMap(k2, k3));
        }

        public SortedMap<K, Collection<V>> tailMap(K k2) {
            return new SortedAsMap(d().tailMap(k2));
        }

        public SortedSet<K> keySet() {
            SortedSet<K> sortedSet = this.j;
            if (sortedSet != null) {
                return sortedSet;
            }
            SortedSet<K> c = createKeySet();
            this.j = c;
            return c;
        }
    }

    public class SortedKeySet extends AbstractMapBasedMultimap<K, V>.KeySet implements SortedSet<K> {
        public SortedKeySet(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
        }

        public SortedMap<K, Collection<V>> b() {
            return (SortedMap) this.c;
        }

        public Comparator<? super K> comparator() {
            return b().comparator();
        }

        public K first() {
            return b().firstKey();
        }

        public SortedSet<K> headSet(K k) {
            return new SortedKeySet(b().headMap(k));
        }

        public K last() {
            return b().lastKey();
        }

        public SortedSet<K> subSet(K k, K k2) {
            return new SortedKeySet(b().subMap(k, k2));
        }

        public SortedSet<K> tailSet(K k) {
            return new SortedKeySet(b().tailMap(k));
        }
    }

    public class WrappedNavigableSet extends AbstractMapBasedMultimap<K, V>.WrappedSortedSet implements NavigableSet<V> {
        public WrappedNavigableSet(K k2, NavigableSet<V> navigableSet, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            super(k2, navigableSet, wrappedCollection);
        }

        public V ceiling(V v) {
            return f().ceiling(v);
        }

        public java.util.Iterator<V> descendingIterator() {
            return new WrappedCollection.WrappedIterator(f().descendingIterator());
        }

        public NavigableSet<V> descendingSet() {
            return j(f().descendingSet());
        }

        public V floor(V v) {
            return f().floor(v);
        }

        /* renamed from: g */
        public final NavigableSet<V> f() {
            return (NavigableSet) ((SortedSet) this.f);
        }

        public NavigableSet<V> headSet(V v, boolean z) {
            return j(f().headSet(v, z));
        }

        public V higher(V v) {
            return f().higher(v);
        }

        public final NavigableSet<V> j(NavigableSet<V> navigableSet) {
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection == null) {
                wrappedCollection = this;
            }
            return new WrappedNavigableSet(this.c, navigableSet, wrappedCollection);
        }

        public V lower(V v) {
            return f().lower(v);
        }

        public V pollFirst() {
            return Iterators.c(iterator());
        }

        public V pollLast() {
            return Iterators.c(descendingIterator());
        }

        public NavigableSet<V> subSet(V v, boolean z, V v2, boolean z2) {
            return j(f().subSet(v, z, v2, z2));
        }

        public NavigableSet<V> tailSet(V v, boolean z) {
            return j(f().tailSet(v, z));
        }
    }

    public class WrappedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements Set<V> {
        public WrappedSet(K k, Set<V> set) {
            super(k, set, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
        }

        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean c = Sets.c((Set) this.f, collection);
            if (c) {
                int size2 = this.f.size();
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.k = (size2 - size) + abstractMapBasedMultimap.k;
                c();
            }
            return c;
        }
    }

    public class WrappedSortedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements SortedSet<V> {
        public WrappedSortedSet(K k, SortedSet<V> sortedSet, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            super(k, sortedSet, wrappedCollection);
        }

        public Comparator<? super V> comparator() {
            return f().comparator();
        }

        public SortedSet<V> f() {
            return (SortedSet) this.f;
        }

        public V first() {
            b();
            return f().first();
        }

        public SortedSet<V> headSet(V v) {
            b();
            SortedSet headSet = f().headSet(v);
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection == null) {
                wrappedCollection = this;
            }
            return new WrappedSortedSet(this.c, headSet, wrappedCollection);
        }

        public V last() {
            b();
            return f().last();
        }

        public SortedSet<V> subSet(V v, V v2) {
            b();
            SortedSet subSet = f().subSet(v, v2);
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection == null) {
                wrappedCollection = this;
            }
            return new WrappedSortedSet(this.c, subSet, wrappedCollection);
        }

        public SortedSet<V> tailSet(V v) {
            b();
            SortedSet tailSet = f().tailSet(v);
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection == null) {
                wrappedCollection = this;
            }
            return new WrappedSortedSet(this.c, tailSet, wrappedCollection);
        }
    }

    public AbstractMapBasedMultimap(Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty());
        this.j = map;
    }

    public Map<K, Collection<V>> a() {
        return new AsMap(this.j);
    }

    public final Collection<Map.Entry<K, V>> b() {
        if (this instanceof SetMultimap) {
            return new AbstractMultimap.EntrySet(this);
        }
        return new AbstractMultimap.Entries();
    }

    public Set<K> c() {
        return new KeySet(this.j);
    }

    public void clear() {
        for (Collection<V> clear : this.j.values()) {
            clear.clear();
        }
        this.j.clear();
        this.k = 0;
    }

    public boolean containsKey(Object obj) {
        return this.j.containsKey(obj);
    }

    public final Multiset<K> d() {
        return new Multimaps.Keys(this);
    }

    public final Collection<V> e() {
        return new AbstractMultimap.Values();
    }

    public Collection<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    public java.util.Iterator<Map.Entry<K, V>> f() {
        return new AbstractMapBasedMultimap<K, V>.Itr<Map.Entry<K, V>>(this) {
            public final Object a(Object obj, Object obj2) {
                return Maps.immutableEntry(obj, obj2);
            }
        };
    }

    public java.util.Iterator<V> g() {
        return new AbstractMapBasedMultimap<K, V>.Itr<V>(this) {
            public final V a(K k, V v) {
                return v;
            }
        };
    }

    public Collection<V> get(K k2) {
        Collection collection = this.j.get(k2);
        if (collection == null) {
            collection = i(k2);
        }
        return o(k2, collection);
    }

    public abstract Collection<V> h();

    public Collection<V> i(K k2) {
        return h();
    }

    public final Map<K, Collection<V>> j() {
        Map<K, Collection<V>> map = this.j;
        if (map instanceof NavigableMap) {
            return new NavigableAsMap((NavigableMap) this.j);
        }
        if (map instanceof SortedMap) {
            return new SortedAsMap((SortedMap) this.j);
        }
        return new AsMap(this.j);
    }

    public final Set<K> k() {
        Map<K, Collection<V>> map = this.j;
        if (map instanceof NavigableMap) {
            return new NavigableKeySet((NavigableMap) this.j);
        }
        if (map instanceof SortedMap) {
            return new SortedKeySet((SortedMap) this.j);
        }
        return new KeySet(this.j);
    }

    public Collection<V> l() {
        return n(h());
    }

    public final void m(Map<K, Collection<V>> map) {
        this.j = map;
        this.k = 0;
        for (Collection next : map.values()) {
            Preconditions.checkArgument(!next.isEmpty());
            this.k = next.size() + this.k;
        }
    }

    public <E> Collection<E> n(Collection<E> collection) {
        return Collections.unmodifiableCollection(collection);
    }

    public Collection<V> o(K k2, Collection<V> collection) {
        return new WrappedCollection(k2, collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
    }

    public boolean put(K k2, V v) {
        Collection collection = this.j.get(k2);
        if (collection == null) {
            Collection i = i(k2);
            if (i.add(v)) {
                this.k++;
                this.j.put(k2, i);
                return true;
            }
            throw new AssertionError("New Collection violated the Collection spec");
        } else if (!collection.add(v)) {
            return false;
        } else {
            this.k++;
            return true;
        }
    }

    public Collection<V> removeAll(Object obj) {
        Collection remove = this.j.remove(obj);
        if (remove == null) {
            return l();
        }
        Collection h = h();
        h.addAll(remove);
        this.k -= remove.size();
        remove.clear();
        return n(h);
    }

    public Collection<V> replaceValues(K k2, Iterable<? extends V> iterable) {
        java.util.Iterator<? extends V> it = iterable.iterator();
        if (!it.hasNext()) {
            return removeAll(k2);
        }
        Collection collection = this.j.get(k2);
        if (collection == null) {
            collection = i(k2);
            this.j.put(k2, collection);
        }
        Collection h = h();
        h.addAll(collection);
        this.k -= collection.size();
        collection.clear();
        while (it.hasNext()) {
            if (collection.add(it.next())) {
                this.k++;
            }
        }
        return n(h);
    }

    public int size() {
        return this.k;
    }

    public Collection<V> values() {
        return super.values();
    }

    public class WrappedList extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements List<V> {

        public class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.WrappedIterator implements ListIterator<V> {
            public WrappedListIterator() {
                super();
            }

            public void add(V v) {
                WrappedList wrappedList = WrappedList.this;
                boolean isEmpty = wrappedList.isEmpty();
                c().add(v);
                AbstractMapBasedMultimap.this.k++;
                if (isEmpty) {
                    wrappedList.a();
                }
            }

            /* JADX WARNING: type inference failed for: r1v0, types: [com.google.common.collect.AbstractMapBasedMultimap$WrappedList$WrappedListIterator, com.google.common.collect.AbstractMapBasedMultimap$WrappedCollection$WrappedIterator] */
            public final ListIterator<V> c() {
                a();
                return (ListIterator) this.c;
            }

            public boolean hasPrevious() {
                return c().hasPrevious();
            }

            public int nextIndex() {
                return c().nextIndex();
            }

            public V previous() {
                return c().previous();
            }

            public int previousIndex() {
                return c().previousIndex();
            }

            public void set(V v) {
                c().set(v);
            }

            public WrappedListIterator(int i) {
                super(((List) WrappedList.this.f).listIterator(i));
            }
        }

        public WrappedList(K k, List<V> list, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            super(k, list, wrappedCollection);
        }

        public void add(int i, V v) {
            b();
            boolean isEmpty = this.f.isEmpty();
            ((List) this.f).add(i, v);
            AbstractMapBasedMultimap.this.k++;
            if (isEmpty) {
                a();
            }
        }

        public boolean addAll(int i, Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = ((List) this.f).addAll(i, collection);
            if (addAll) {
                int size2 = this.f.size();
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.k = (size2 - size) + abstractMapBasedMultimap.k;
                if (size == 0) {
                    a();
                }
            }
            return addAll;
        }

        public V get(int i) {
            b();
            return ((List) this.f).get(i);
        }

        public int indexOf(Object obj) {
            b();
            return ((List) this.f).indexOf(obj);
        }

        public int lastIndexOf(Object obj) {
            b();
            return ((List) this.f).lastIndexOf(obj);
        }

        public ListIterator<V> listIterator() {
            b();
            return new WrappedListIterator();
        }

        public V remove(int i) {
            b();
            V remove = ((List) this.f).remove(i);
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            abstractMapBasedMultimap.k--;
            c();
            return remove;
        }

        public V set(int i, V v) {
            b();
            return ((List) this.f).set(i, v);
        }

        public List<V> subList(int i, int i2) {
            b();
            List subList = ((List) this.f).subList(i, i2);
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection == null) {
                wrappedCollection = this;
            }
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            abstractMapBasedMultimap.getClass();
            boolean z = subList instanceof RandomAccess;
            K k = this.c;
            if (z) {
                return new RandomAccessWrappedList(abstractMapBasedMultimap, k, subList, wrappedCollection);
            }
            return new WrappedList(k, subList, wrappedCollection);
        }

        public ListIterator<V> listIterator(int i) {
            b();
            return new WrappedListIterator(i);
        }
    }

    public class WrappedCollection extends AbstractCollection<V> {
        public final K c;
        public Collection<V> f;
        public final AbstractMapBasedMultimap<K, V>.WrappedCollection g;
        public final Collection<V> h;

        public WrappedCollection(K k, Collection<V> collection, AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection) {
            Collection<V> collection2;
            this.c = k;
            this.f = collection;
            this.g = wrappedCollection;
            if (wrappedCollection == null) {
                collection2 = null;
            } else {
                collection2 = wrappedCollection.f;
            }
            this.h = collection2;
        }

        public final void a() {
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection != null) {
                wrappedCollection.a();
            } else {
                AbstractMapBasedMultimap.this.j.put(this.c, this.f);
            }
        }

        public boolean add(V v) {
            b();
            boolean isEmpty = this.f.isEmpty();
            boolean add = this.f.add(v);
            if (add) {
                AbstractMapBasedMultimap.this.k++;
                if (isEmpty) {
                    a();
                }
            }
            return add;
        }

        public boolean addAll(Collection<? extends V> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = this.f.addAll(collection);
            if (addAll) {
                int size2 = this.f.size();
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.k = (size2 - size) + abstractMapBasedMultimap.k;
                if (size == 0) {
                    a();
                }
            }
            return addAll;
        }

        public final void b() {
            Collection<V> collection;
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection != null) {
                wrappedCollection.b();
                if (wrappedCollection.f != this.h) {
                    throw new ConcurrentModificationException();
                }
            } else if (this.f.isEmpty() && (collection = AbstractMapBasedMultimap.this.j.get(this.c)) != null) {
                this.f = collection;
            }
        }

        public final void c() {
            AbstractMapBasedMultimap<K, V>.WrappedCollection wrappedCollection = this.g;
            if (wrappedCollection != null) {
                wrappedCollection.c();
            } else if (this.f.isEmpty()) {
                AbstractMapBasedMultimap.this.j.remove(this.c);
            }
        }

        public void clear() {
            int size = size();
            if (size != 0) {
                this.f.clear();
                AbstractMapBasedMultimap.this.k -= size;
                c();
            }
        }

        public boolean contains(Object obj) {
            b();
            return this.f.contains(obj);
        }

        public boolean containsAll(Collection<?> collection) {
            b();
            return this.f.containsAll(collection);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            b();
            return this.f.equals(obj);
        }

        public int hashCode() {
            b();
            return this.f.hashCode();
        }

        public java.util.Iterator<V> iterator() {
            b();
            return new WrappedIterator();
        }

        public boolean remove(Object obj) {
            b();
            boolean remove = this.f.remove(obj);
            if (remove) {
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.k--;
                c();
            }
            return remove;
        }

        public boolean removeAll(Collection<?> collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean removeAll = this.f.removeAll(collection);
            if (removeAll) {
                int size2 = this.f.size();
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.k = (size2 - size) + abstractMapBasedMultimap.k;
                c();
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            int size = size();
            boolean retainAll = this.f.retainAll(collection);
            if (retainAll) {
                int size2 = this.f.size();
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.k = (size2 - size) + abstractMapBasedMultimap.k;
                c();
            }
            return retainAll;
        }

        public int size() {
            b();
            return this.f.size();
        }

        public String toString() {
            b();
            return this.f.toString();
        }

        public class WrappedIterator implements java.util.Iterator<V>, j$.util.Iterator {
            public final java.util.Iterator<V> c;
            public final Collection<V> f;

            public WrappedIterator() {
                java.util.Iterator<V> it;
                Collection<V> collection = WrappedCollection.this.f;
                this.f = collection;
                if (collection instanceof List) {
                    it = ((List) collection).listIterator();
                } else {
                    it = collection.iterator();
                }
                this.c = it;
            }

            public final void a() {
                WrappedCollection wrappedCollection = WrappedCollection.this;
                wrappedCollection.b();
                if (wrappedCollection.f != this.f) {
                    throw new ConcurrentModificationException();
                }
            }

            public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                Iterator.CC.$default$forEachRemaining(this, consumer);
            }

            public boolean hasNext() {
                a();
                return this.c.hasNext();
            }

            public V next() {
                a();
                return this.c.next();
            }

            public void remove() {
                this.c.remove();
                WrappedCollection wrappedCollection = WrappedCollection.this;
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                abstractMapBasedMultimap.k--;
                wrappedCollection.c();
            }

            public WrappedIterator(java.util.Iterator<V> it) {
                this.f = WrappedCollection.this.f;
                this.c = it;
            }
        }
    }
}
