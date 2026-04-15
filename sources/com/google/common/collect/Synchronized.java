package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

final class Synchronized {

    public static class SynchronizedAsMap<K, V> extends SynchronizedMap<K, Collection<V>> {
        private static final long serialVersionUID = 0;
        public transient Set<Map.Entry<K, Collection<V>>> j;
        public transient Collection<Collection<V>> k;

        public SynchronizedAsMap(Map<K, Collection<V>> map, Object obj) {
            super(map, obj);
        }

        public boolean containsValue(Object obj) {
            return values().contains(obj);
        }

        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            Set<Map.Entry<K, Collection<V>>> set;
            synchronized (this.f) {
                if (this.j == null) {
                    this.j = new SynchronizedAsMapEntries(((Map) this.c).entrySet(), this.f);
                }
                set = this.j;
            }
            return set;
        }

        public Collection<Collection<V>> values() {
            Collection<Collection<V>> collection;
            synchronized (this.f) {
                if (this.k == null) {
                    this.k = new SynchronizedAsMapValues(this.f, ((Map) this.c).values());
                }
                collection = this.k;
            }
            return collection;
        }

        public Collection<V> get(Object obj) {
            Collection<V> collection;
            synchronized (this.f) {
                Collection collection2 = (Collection) super.get(obj);
                if (collection2 == null) {
                    collection = null;
                } else {
                    collection = Synchronized.b(this.f, collection2);
                }
            }
            return collection;
        }
    }

    public static class SynchronizedAsMapValues<V> extends SynchronizedCollection<Collection<V>> {
        private static final long serialVersionUID = 0;

        public SynchronizedAsMapValues(Object obj, Collection collection) {
            super(collection, obj);
        }

        public Iterator<Collection<V>> iterator() {
            return new TransformedIterator<Collection<V>, Collection<V>>(super.iterator()) {
                public final Object a(Object obj) {
                    return Synchronized.b(SynchronizedAsMapValues.this.f, (Collection) obj);
                }
            };
        }
    }

    public static class SynchronizedBiMap<K, V> extends SynchronizedMap<K, V> implements BiMap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Set<V> j;
        public transient BiMap<V, K> k;

        public SynchronizedBiMap(BiMap<K, V> biMap, Object obj, BiMap<V, K> biMap2) {
            super(biMap, obj);
            this.k = biMap2;
        }

        public final Map a() {
            return (BiMap) ((Map) this.c);
        }

        public V forcePut(K k2, V v) {
            V forcePut;
            synchronized (this.f) {
                forcePut = ((BiMap) ((Map) this.c)).forcePut(k2, v);
            }
            return forcePut;
        }

        public BiMap<V, K> inverse() {
            BiMap<V, K> biMap;
            synchronized (this.f) {
                if (this.k == null) {
                    this.k = new SynchronizedBiMap(((BiMap) ((Map) this.c)).inverse(), this.f, this);
                }
                biMap = this.k;
            }
            return biMap;
        }

        public Set<V> values() {
            Set<V> set;
            synchronized (this.f) {
                if (this.j == null) {
                    this.j = new SynchronizedSet(((BiMap) ((Map) this.c)).values(), this.f);
                }
                set = this.j;
            }
            return set;
        }
    }

    public static final class SynchronizedDeque<E> extends SynchronizedQueue<E> implements Deque<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedDeque(Deque deque) {
            super(deque);
        }

        public void addFirst(E e) {
            synchronized (this.f) {
                b().addFirst(e);
            }
        }

        public void addLast(E e) {
            synchronized (this.f) {
                b().addLast(e);
            }
        }

        /* renamed from: c */
        public final Deque<E> b() {
            return (Deque) super.a();
        }

        public Iterator<E> descendingIterator() {
            Iterator<E> descendingIterator;
            synchronized (this.f) {
                descendingIterator = b().descendingIterator();
            }
            return descendingIterator;
        }

        public E getFirst() {
            E first;
            synchronized (this.f) {
                first = b().getFirst();
            }
            return first;
        }

        public E getLast() {
            E last;
            synchronized (this.f) {
                last = b().getLast();
            }
            return last;
        }

        public boolean offerFirst(E e) {
            boolean offerFirst;
            synchronized (this.f) {
                offerFirst = b().offerFirst(e);
            }
            return offerFirst;
        }

        public boolean offerLast(E e) {
            boolean offerLast;
            synchronized (this.f) {
                offerLast = b().offerLast(e);
            }
            return offerLast;
        }

        public E peekFirst() {
            E peekFirst;
            synchronized (this.f) {
                peekFirst = b().peekFirst();
            }
            return peekFirst;
        }

        public E peekLast() {
            E peekLast;
            synchronized (this.f) {
                peekLast = b().peekLast();
            }
            return peekLast;
        }

        public E pollFirst() {
            E pollFirst;
            synchronized (this.f) {
                pollFirst = b().pollFirst();
            }
            return pollFirst;
        }

        public E pollLast() {
            E pollLast;
            synchronized (this.f) {
                pollLast = b().pollLast();
            }
            return pollLast;
        }

        public E pop() {
            E pop;
            synchronized (this.f) {
                pop = b().pop();
            }
            return pop;
        }

        public void push(E e) {
            synchronized (this.f) {
                b().push(e);
            }
        }

        public E removeFirst() {
            E removeFirst;
            synchronized (this.f) {
                removeFirst = b().removeFirst();
            }
            return removeFirst;
        }

        public boolean removeFirstOccurrence(Object obj) {
            boolean removeFirstOccurrence;
            synchronized (this.f) {
                removeFirstOccurrence = b().removeFirstOccurrence(obj);
            }
            return removeFirstOccurrence;
        }

        public E removeLast() {
            E removeLast;
            synchronized (this.f) {
                removeLast = b().removeLast();
            }
            return removeLast;
        }

        public boolean removeLastOccurrence(Object obj) {
            boolean removeLastOccurrence;
            synchronized (this.f) {
                removeLastOccurrence = b().removeLastOccurrence(obj);
            }
            return removeLastOccurrence;
        }
    }

    public static class SynchronizedEntry<K, V> extends SynchronizedObject implements Map.Entry<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedEntry(Map.Entry<K, V> entry, Object obj) {
            super(entry, obj);
        }

        public boolean equals(Object obj) {
            boolean equals;
            synchronized (this.f) {
                equals = ((Map.Entry) this.c).equals(obj);
            }
            return equals;
        }

        public K getKey() {
            K key;
            synchronized (this.f) {
                key = ((Map.Entry) this.c).getKey();
            }
            return key;
        }

        public V getValue() {
            V value;
            synchronized (this.f) {
                value = ((Map.Entry) this.c).getValue();
            }
            return value;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.f) {
                hashCode = ((Map.Entry) this.c).hashCode();
            }
            return hashCode;
        }

        public V setValue(V v) {
            V value;
            synchronized (this.f) {
                value = ((Map.Entry) this.c).setValue(v);
            }
            return value;
        }
    }

    public static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedList(List<E> list, Object obj) {
            super(list, obj);
        }

        public void add(int i, E e) {
            synchronized (this.f) {
                a().add(i, e);
            }
        }

        public boolean addAll(int i, Collection<? extends E> collection) {
            boolean addAll;
            synchronized (this.f) {
                addAll = a().addAll(i, collection);
            }
            return addAll;
        }

        /* renamed from: b */
        public final List<E> a() {
            return (List) ((Collection) this.c);
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.f) {
                equals = a().equals(obj);
            }
            return equals;
        }

        public E get(int i) {
            E e;
            synchronized (this.f) {
                e = a().get(i);
            }
            return e;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.f) {
                hashCode = a().hashCode();
            }
            return hashCode;
        }

        public int indexOf(Object obj) {
            int indexOf;
            synchronized (this.f) {
                indexOf = a().indexOf(obj);
            }
            return indexOf;
        }

        public int lastIndexOf(Object obj) {
            int lastIndexOf;
            synchronized (this.f) {
                lastIndexOf = a().lastIndexOf(obj);
            }
            return lastIndexOf;
        }

        public ListIterator<E> listIterator() {
            return a().listIterator();
        }

        public E remove(int i) {
            E remove;
            synchronized (this.f) {
                remove = a().remove(i);
            }
            return remove;
        }

        public E set(int i, E e) {
            E e2;
            synchronized (this.f) {
                e2 = a().set(i, e);
            }
            return e2;
        }

        public List<E> subList(int i, int i2) {
            List<E> d;
            synchronized (this.f) {
                d = Synchronized.d(a().subList(i, i2), this.f);
            }
            return d;
        }

        public ListIterator<E> listIterator(int i) {
            return a().listIterator(i);
        }
    }

    public static class SynchronizedListMultimap<K, V> extends SynchronizedMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedListMultimap(ListMultimap listMultimap) {
            super(listMultimap);
        }

        public final Multimap a() {
            return (ListMultimap) ((Multimap) this.c);
        }

        public List<V> get(K k) {
            List<V> d;
            synchronized (this.f) {
                d = Synchronized.d(((ListMultimap) ((Multimap) this.c)).get(k), this.f);
            }
            return d;
        }

        public List<V> removeAll(Object obj) {
            List<V> removeAll;
            synchronized (this.f) {
                removeAll = ((ListMultimap) ((Multimap) this.c)).removeAll(obj);
            }
            return removeAll;
        }

        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            List<V> replaceValues;
            synchronized (this.f) {
                replaceValues = ((ListMultimap) ((Multimap) this.c)).replaceValues(k, iterable);
            }
            return replaceValues;
        }
    }

    public static class SynchronizedMap<K, V> extends SynchronizedObject implements Map<K, V> {
        private static final long serialVersionUID = 0;
        public transient Set<K> g;
        public transient Collection<V> h;
        public transient Set<Map.Entry<K, V>> i;

        public SynchronizedMap(Map<K, V> map, Object obj) {
            super(map, obj);
        }

        public Map<K, V> a() {
            return (Map) this.c;
        }

        public void clear() {
            synchronized (this.f) {
                a().clear();
            }
        }

        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.f) {
                containsKey = a().containsKey(obj);
            }
            return containsKey;
        }

        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.f) {
                containsValue = a().containsValue(obj);
            }
            return containsValue;
        }

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.f) {
                if (this.i == null) {
                    this.i = new SynchronizedSet(a().entrySet(), this.f);
                }
                set = this.i;
            }
            return set;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.f) {
                equals = a().equals(obj);
            }
            return equals;
        }

        public V get(Object obj) {
            V v;
            synchronized (this.f) {
                v = a().get(obj);
            }
            return v;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.f) {
                hashCode = a().hashCode();
            }
            return hashCode;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.f) {
                isEmpty = a().isEmpty();
            }
            return isEmpty;
        }

        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.f) {
                if (this.g == null) {
                    this.g = new SynchronizedSet(a().keySet(), this.f);
                }
                set = this.g;
            }
            return set;
        }

        public V put(K k, V v) {
            V put;
            synchronized (this.f) {
                put = a().put(k, v);
            }
            return put;
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            synchronized (this.f) {
                a().putAll(map);
            }
        }

        public V remove(Object obj) {
            V remove;
            synchronized (this.f) {
                remove = a().remove(obj);
            }
            return remove;
        }

        public int size() {
            int size;
            synchronized (this.f) {
                size = a().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.f) {
                if (this.h == null) {
                    this.h = new SynchronizedCollection(a().values(), this.f);
                }
                collection = this.h;
            }
            return collection;
        }
    }

    public static class SynchronizedObject implements Serializable {
        private static final long serialVersionUID = 0;
        public final Object c;
        public final Object f;

        public SynchronizedObject(Object obj, Object obj2) {
            this.c = Preconditions.checkNotNull(obj);
            this.f = obj2 == null ? this : obj2;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.f) {
                objectOutputStream.defaultWriteObject();
            }
        }

        public String toString() {
            String obj;
            synchronized (this.f) {
                obj = this.c.toString();
            }
            return obj;
        }
    }

    public static class SynchronizedQueue<E> extends SynchronizedCollection<E> implements Queue<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedQueue(Queue queue) {
            super(queue, (Object) null);
        }

        /* renamed from: b */
        public Queue<E> a() {
            return (Queue) ((Collection) this.c);
        }

        public E element() {
            E element;
            synchronized (this.f) {
                element = a().element();
            }
            return element;
        }

        public boolean offer(E e) {
            boolean offer;
            synchronized (this.f) {
                offer = a().offer(e);
            }
            return offer;
        }

        public E peek() {
            E peek;
            synchronized (this.f) {
                peek = a().peek();
            }
            return peek;
        }

        public E poll() {
            E poll;
            synchronized (this.f) {
                poll = a().poll();
            }
            return poll;
        }

        public E remove() {
            E remove;
            synchronized (this.f) {
                remove = a().remove();
            }
            return remove;
        }
    }

    public static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess {
        private static final long serialVersionUID = 0;

        public SynchronizedRandomAccessList(List<E> list, Object obj) {
            super(list, obj);
        }
    }

    public static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedSet(Set<E> set, Object obj) {
            super(set, obj);
        }

        /* renamed from: b */
        public Set<E> a() {
            return (Set) ((Collection) this.c);
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.f) {
                equals = a().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.f) {
                hashCode = a().hashCode();
            }
            return hashCode;
        }
    }

    public static class SynchronizedSetMultimap<K, V> extends SynchronizedMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Set<Map.Entry<K, V>> l;

        public SynchronizedSetMultimap(SetMultimap setMultimap) {
            super(setMultimap);
        }

        /* renamed from: b */
        public SetMultimap<K, V> a() {
            return (SetMultimap) ((Multimap) this.c);
        }

        public Set<Map.Entry<K, V>> entries() {
            Set<Map.Entry<K, V>> set;
            synchronized (this.f) {
                if (this.l == null) {
                    this.l = new SynchronizedSet(a().entries(), this.f);
                }
                set = this.l;
            }
            return set;
        }

        public Set<V> get(K k) {
            SynchronizedSet synchronizedSet;
            synchronized (this.f) {
                synchronizedSet = new SynchronizedSet(a().get(k), this.f);
            }
            return synchronizedSet;
        }

        public Set<V> removeAll(Object obj) {
            Set<V> removeAll;
            synchronized (this.f) {
                removeAll = a().removeAll(obj);
            }
            return removeAll;
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            Set<V> replaceValues;
            synchronized (this.f) {
                replaceValues = a().replaceValues(k, iterable);
            }
            return replaceValues;
        }
    }

    public static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedSortedMap(SortedMap<K, V> sortedMap, Object obj) {
            super(sortedMap, obj);
        }

        /* renamed from: b */
        public SortedMap<K, V> a() {
            return (SortedMap) ((Map) this.c);
        }

        public Comparator<? super K> comparator() {
            Comparator<? super K> comparator;
            synchronized (this.f) {
                comparator = a().comparator();
            }
            return comparator;
        }

        public K firstKey() {
            K firstKey;
            synchronized (this.f) {
                firstKey = a().firstKey();
            }
            return firstKey;
        }

        public SortedMap<K, V> headMap(K k) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.f) {
                synchronizedSortedMap = new SynchronizedSortedMap(a().headMap(k), this.f);
            }
            return synchronizedSortedMap;
        }

        public K lastKey() {
            K lastKey;
            synchronized (this.f) {
                lastKey = a().lastKey();
            }
            return lastKey;
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.f) {
                synchronizedSortedMap = new SynchronizedSortedMap(a().subMap(k, k2), this.f);
            }
            return synchronizedSortedMap;
        }

        public SortedMap<K, V> tailMap(K k) {
            SynchronizedSortedMap synchronizedSortedMap;
            synchronized (this.f) {
                synchronizedSortedMap = new SynchronizedSortedMap(a().tailMap(k), this.f);
            }
            return synchronizedSortedMap;
        }
    }

    public static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedSortedSet(SortedSet<E> sortedSet, Object obj) {
            super(sortedSet, obj);
        }

        /* renamed from: c */
        public SortedSet<E> b() {
            return (SortedSet) super.a();
        }

        public Comparator<? super E> comparator() {
            Comparator<? super E> comparator;
            synchronized (this.f) {
                comparator = b().comparator();
            }
            return comparator;
        }

        public E first() {
            E first;
            synchronized (this.f) {
                first = b().first();
            }
            return first;
        }

        public SortedSet<E> headSet(E e) {
            SynchronizedSortedSet synchronizedSortedSet;
            synchronized (this.f) {
                synchronizedSortedSet = new SynchronizedSortedSet(b().headSet(e), this.f);
            }
            return synchronizedSortedSet;
        }

        public E last() {
            E last;
            synchronized (this.f) {
                last = b().last();
            }
            return last;
        }

        public SortedSet<E> subSet(E e, E e2) {
            SynchronizedSortedSet synchronizedSortedSet;
            synchronized (this.f) {
                synchronizedSortedSet = new SynchronizedSortedSet(b().subSet(e, e2), this.f);
            }
            return synchronizedSortedSet;
        }

        public SortedSet<E> tailSet(E e) {
            SynchronizedSortedSet synchronizedSortedSet;
            synchronized (this.f) {
                synchronizedSortedSet = new SynchronizedSortedSet(b().tailSet(e), this.f);
            }
            return synchronizedSortedSet;
        }
    }

    public static final class SynchronizedTable<R, C, V> extends SynchronizedObject implements Table<R, C, V> {
        public SynchronizedTable(Table table) {
            super(table, (Object) null);
        }

        public Set<Table.Cell<R, C, V>> cellSet() {
            SynchronizedSet synchronizedSet;
            synchronized (this.f) {
                synchronizedSet = new SynchronizedSet(((Table) this.c).cellSet(), this.f);
            }
            return synchronizedSet;
        }

        public void clear() {
            synchronized (this.f) {
                ((Table) this.c).clear();
            }
        }

        public Map<R, V> column(C c) {
            SynchronizedMap synchronizedMap;
            synchronized (this.f) {
                synchronizedMap = new SynchronizedMap(((Table) this.c).column(c), this.f);
            }
            return synchronizedMap;
        }

        public Set<C> columnKeySet() {
            SynchronizedSet synchronizedSet;
            synchronized (this.f) {
                synchronizedSet = new SynchronizedSet(((Table) this.c).columnKeySet(), this.f);
            }
            return synchronizedSet;
        }

        public Map<C, Map<R, V>> columnMap() {
            SynchronizedMap synchronizedMap;
            synchronized (this.f) {
                synchronizedMap = new SynchronizedMap(Maps.transformValues(((Table) this.c).columnMap(), new Function<Map<R, V>, Map<R, V>>() {
                    public Map<R, V> apply(Map<R, V> map) {
                        return new SynchronizedMap(map, SynchronizedTable.this.f);
                    }
                }), this.f);
            }
            return synchronizedMap;
        }

        public boolean contains(Object obj, Object obj2) {
            boolean contains;
            synchronized (this.f) {
                contains = ((Table) this.c).contains(obj, obj2);
            }
            return contains;
        }

        public boolean containsColumn(Object obj) {
            boolean containsColumn;
            synchronized (this.f) {
                containsColumn = ((Table) this.c).containsColumn(obj);
            }
            return containsColumn;
        }

        public boolean containsRow(Object obj) {
            boolean containsRow;
            synchronized (this.f) {
                containsRow = ((Table) this.c).containsRow(obj);
            }
            return containsRow;
        }

        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.f) {
                containsValue = ((Table) this.c).containsValue(obj);
            }
            return containsValue;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (this == obj) {
                return true;
            }
            synchronized (this.f) {
                equals = ((Table) this.c).equals(obj);
            }
            return equals;
        }

        public V get(Object obj, Object obj2) {
            V v;
            synchronized (this.f) {
                v = ((Table) this.c).get(obj, obj2);
            }
            return v;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.f) {
                hashCode = ((Table) this.c).hashCode();
            }
            return hashCode;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.f) {
                isEmpty = ((Table) this.c).isEmpty();
            }
            return isEmpty;
        }

        public V put(R r, C c, V v) {
            V put;
            synchronized (this.f) {
                put = ((Table) this.c).put(r, c, v);
            }
            return put;
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            synchronized (this.f) {
                ((Table) this.c).putAll(table);
            }
        }

        public V remove(Object obj, Object obj2) {
            V remove;
            synchronized (this.f) {
                remove = ((Table) this.c).remove(obj, obj2);
            }
            return remove;
        }

        public Map<C, V> row(R r) {
            SynchronizedMap synchronizedMap;
            synchronized (this.f) {
                synchronizedMap = new SynchronizedMap(((Table) this.c).row(r), this.f);
            }
            return synchronizedMap;
        }

        public Set<R> rowKeySet() {
            SynchronizedSet synchronizedSet;
            synchronized (this.f) {
                synchronizedSet = new SynchronizedSet(((Table) this.c).rowKeySet(), this.f);
            }
            return synchronizedSet;
        }

        public Map<R, Map<C, V>> rowMap() {
            SynchronizedMap synchronizedMap;
            synchronized (this.f) {
                synchronizedMap = new SynchronizedMap(Maps.transformValues(((Table) this.c).rowMap(), new Function<Map<C, V>, Map<C, V>>() {
                    public Map<C, V> apply(Map<C, V> map) {
                        return new SynchronizedMap(map, SynchronizedTable.this.f);
                    }
                }), this.f);
            }
            return synchronizedMap;
        }

        public int size() {
            int size;
            synchronized (this.f) {
                size = ((Table) this.c).size();
            }
            return size;
        }

        public Collection<V> values() {
            SynchronizedCollection synchronizedCollection;
            synchronized (this.f) {
                synchronizedCollection = new SynchronizedCollection(((Table) this.c).values(), this.f);
            }
            return synchronizedCollection;
        }
    }

    public static Set a(Set set, Object obj) {
        if (set instanceof SortedSet) {
            return new SynchronizedSortedSet((SortedSet) set, obj);
        }
        return new SynchronizedSet(set, obj);
    }

    public static Collection b(Object obj, Collection collection) {
        if (collection instanceof SortedSet) {
            return new SynchronizedSortedSet((SortedSet) collection, obj);
        }
        if (collection instanceof Set) {
            return new SynchronizedSet((Set) collection, obj);
        }
        if (collection instanceof List) {
            return d((List) collection, obj);
        }
        return new SynchronizedCollection(collection, obj);
    }

    public static Map.Entry c(Map.Entry entry, Object obj) {
        if (entry == null) {
            return null;
        }
        return new SynchronizedEntry(entry, obj);
    }

    public static <E> List<E> d(List<E> list, Object obj) {
        return list instanceof RandomAccess ? new SynchronizedRandomAccessList(list, obj) : new SynchronizedList(list, obj);
    }

    public static class SynchronizedSortedSetMultimap<K, V> extends SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        public SynchronizedSortedSetMultimap(SortedSetMultimap sortedSetMultimap) {
            super(sortedSetMultimap);
        }

        /* renamed from: c */
        public final SortedSetMultimap<K, V> b() {
            return (SortedSetMultimap) super.a();
        }

        public Comparator<? super V> valueComparator() {
            Comparator<? super V> valueComparator;
            synchronized (this.f) {
                valueComparator = b().valueComparator();
            }
            return valueComparator;
        }

        public SortedSet<V> get(K k) {
            SynchronizedSortedSet synchronizedSortedSet;
            synchronized (this.f) {
                synchronizedSortedSet = new SynchronizedSortedSet(b().get(k), this.f);
            }
            return synchronizedSortedSet;
        }

        public SortedSet<V> removeAll(Object obj) {
            SortedSet<V> removeAll;
            synchronized (this.f) {
                removeAll = b().removeAll(obj);
            }
            return removeAll;
        }

        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            SortedSet<V> replaceValues;
            synchronized (this.f) {
                replaceValues = b().replaceValues(k, iterable);
            }
            return replaceValues;
        }
    }

    public static class SynchronizedCollection<E> extends SynchronizedObject implements Collection<E> {
        private static final long serialVersionUID = 0;

        public SynchronizedCollection(Collection collection, Object obj) {
            super(collection, obj);
        }

        public Collection<E> a() {
            return (Collection) this.c;
        }

        public boolean add(E e) {
            boolean add;
            synchronized (this.f) {
                add = a().add(e);
            }
            return add;
        }

        public boolean addAll(Collection<? extends E> collection) {
            boolean addAll;
            synchronized (this.f) {
                addAll = a().addAll(collection);
            }
            return addAll;
        }

        public void clear() {
            synchronized (this.f) {
                a().clear();
            }
        }

        public boolean contains(Object obj) {
            boolean contains;
            synchronized (this.f) {
                contains = a().contains(obj);
            }
            return contains;
        }

        public boolean containsAll(Collection<?> collection) {
            boolean containsAll;
            synchronized (this.f) {
                containsAll = a().containsAll(collection);
            }
            return containsAll;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.f) {
                isEmpty = a().isEmpty();
            }
            return isEmpty;
        }

        public Iterator<E> iterator() {
            return a().iterator();
        }

        public boolean remove(Object obj) {
            boolean remove;
            synchronized (this.f) {
                remove = a().remove(obj);
            }
            return remove;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean removeAll;
            synchronized (this.f) {
                removeAll = a().removeAll(collection);
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> collection) {
            boolean retainAll;
            synchronized (this.f) {
                retainAll = a().retainAll(collection);
            }
            return retainAll;
        }

        public int size() {
            int size;
            synchronized (this.f) {
                size = a().size();
            }
            return size;
        }

        public Object[] toArray() {
            Object[] array;
            synchronized (this.f) {
                array = a().toArray();
            }
            return array;
        }

        public <T> T[] toArray(T[] tArr) {
            T[] array;
            synchronized (this.f) {
                array = a().toArray(tArr);
            }
            return array;
        }
    }

    public static class SynchronizedMultimap<K, V> extends SynchronizedObject implements Multimap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Set<K> g;
        public transient Collection<V> h;
        public transient Collection<Map.Entry<K, V>> i;
        public transient Map<K, Collection<V>> j;
        public transient Multiset<K> k;

        public SynchronizedMultimap(Multimap multimap) {
            super(multimap, (Object) null);
        }

        public Multimap<K, V> a() {
            return (Multimap) this.c;
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map;
            synchronized (this.f) {
                if (this.j == null) {
                    this.j = new SynchronizedAsMap(a().asMap(), this.f);
                }
                map = this.j;
            }
            return map;
        }

        public void clear() {
            synchronized (this.f) {
                a().clear();
            }
        }

        public boolean containsEntry(Object obj, Object obj2) {
            boolean containsEntry;
            synchronized (this.f) {
                containsEntry = a().containsEntry(obj, obj2);
            }
            return containsEntry;
        }

        public boolean containsKey(Object obj) {
            boolean containsKey;
            synchronized (this.f) {
                containsKey = a().containsKey(obj);
            }
            return containsKey;
        }

        public boolean containsValue(Object obj) {
            boolean containsValue;
            synchronized (this.f) {
                containsValue = a().containsValue(obj);
            }
            return containsValue;
        }

        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection;
            synchronized (this.f) {
                if (this.i == null) {
                    this.i = Synchronized.b(this.f, a().entries());
                }
                collection = this.i;
            }
            return collection;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.f) {
                equals = a().equals(obj);
            }
            return equals;
        }

        public Collection<V> get(K k2) {
            Collection<V> b;
            synchronized (this.f) {
                b = Synchronized.b(this.f, a().get(k2));
            }
            return b;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.f) {
                hashCode = a().hashCode();
            }
            return hashCode;
        }

        public boolean isEmpty() {
            boolean isEmpty;
            synchronized (this.f) {
                isEmpty = a().isEmpty();
            }
            return isEmpty;
        }

        public Set<K> keySet() {
            Set<K> set;
            synchronized (this.f) {
                if (this.g == null) {
                    this.g = Synchronized.a(a().keySet(), this.f);
                }
                set = this.g;
            }
            return set;
        }

        public Multiset<K> keys() {
            Multiset<K> multiset;
            synchronized (this.f) {
                if (this.k == null) {
                    Multiset<K> keys = a().keys();
                    Object obj = this.f;
                    if (!(keys instanceof SynchronizedMultiset)) {
                        if (!(keys instanceof ImmutableMultiset)) {
                            keys = new SynchronizedMultiset<>(keys, obj);
                        }
                    }
                    this.k = keys;
                }
                multiset = this.k;
            }
            return multiset;
        }

        public boolean put(K k2, V v) {
            boolean put;
            synchronized (this.f) {
                put = a().put(k2, v);
            }
            return put;
        }

        public boolean putAll(K k2, Iterable<? extends V> iterable) {
            boolean putAll;
            synchronized (this.f) {
                putAll = a().putAll(k2, iterable);
            }
            return putAll;
        }

        public boolean remove(Object obj, Object obj2) {
            boolean remove;
            synchronized (this.f) {
                remove = a().remove(obj, obj2);
            }
            return remove;
        }

        public Collection<V> removeAll(Object obj) {
            Collection<V> removeAll;
            synchronized (this.f) {
                removeAll = a().removeAll(obj);
            }
            return removeAll;
        }

        public Collection<V> replaceValues(K k2, Iterable<? extends V> iterable) {
            Collection<V> replaceValues;
            synchronized (this.f) {
                replaceValues = a().replaceValues(k2, iterable);
            }
            return replaceValues;
        }

        public int size() {
            int size;
            synchronized (this.f) {
                size = a().size();
            }
            return size;
        }

        public Collection<V> values() {
            Collection<V> collection;
            synchronized (this.f) {
                if (this.h == null) {
                    this.h = new SynchronizedCollection(a().values(), this.f);
                }
                collection = this.h;
            }
            return collection;
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            boolean putAll;
            synchronized (this.f) {
                putAll = a().putAll(multimap);
            }
            return putAll;
        }
    }

    public static class SynchronizedMultiset<E> extends SynchronizedCollection<E> implements Multiset<E> {
        private static final long serialVersionUID = 0;
        public transient Set<E> g;
        public transient Set<Multiset.Entry<E>> h;

        public SynchronizedMultiset(Multiset<E> multiset, Object obj) {
            super(multiset, obj);
        }

        public int add(E e, int i) {
            int add;
            synchronized (this.f) {
                add = a().add(e, i);
            }
            return add;
        }

        /* renamed from: b */
        public final Multiset<E> a() {
            return (Multiset) ((Collection) this.c);
        }

        public int count(Object obj) {
            int count;
            synchronized (this.f) {
                count = a().count(obj);
            }
            return count;
        }

        public Set<E> elementSet() {
            Set<E> set;
            synchronized (this.f) {
                if (this.g == null) {
                    this.g = Synchronized.a(a().elementSet(), this.f);
                }
                set = this.g;
            }
            return set;
        }

        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<E>> set;
            synchronized (this.f) {
                if (this.h == null) {
                    this.h = Synchronized.a(a().entrySet(), this.f);
                }
                set = this.h;
            }
            return set;
        }

        public boolean equals(Object obj) {
            boolean equals;
            if (obj == this) {
                return true;
            }
            synchronized (this.f) {
                equals = a().equals(obj);
            }
            return equals;
        }

        public int hashCode() {
            int hashCode;
            synchronized (this.f) {
                hashCode = a().hashCode();
            }
            return hashCode;
        }

        public int remove(Object obj, int i) {
            int remove;
            synchronized (this.f) {
                remove = a().remove(obj, i);
            }
            return remove;
        }

        public int setCount(E e, int i) {
            int count;
            synchronized (this.f) {
                count = a().setCount(e, i);
            }
            return count;
        }

        public boolean setCount(E e, int i, int i2) {
            boolean count;
            synchronized (this.f) {
                count = a().setCount(e, i, i2);
            }
            return count;
        }
    }

    public static class SynchronizedAsMapEntries<K, V> extends SynchronizedSet<Map.Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0;

        public SynchronizedAsMapEntries(Set<Map.Entry<K, Collection<V>>> set, Object obj) {
            super(set, obj);
        }

        public boolean contains(Object obj) {
            boolean z;
            synchronized (this.f) {
                Set b = a();
                if (!(obj instanceof Map.Entry)) {
                    z = false;
                } else {
                    Map.Entry entry = (Map.Entry) obj;
                    Preconditions.checkNotNull(entry);
                    z = b.contains(new AbstractMapEntry<Object, Object>(entry) {
                        public Object getKey() {
                            return r1.getKey();
                        }

                        public Object getValue() {
                            return r1.getValue();
                        }
                    });
                }
            }
            return z;
        }

        public boolean containsAll(Collection<?> collection) {
            boolean b;
            synchronized (this.f) {
                b = Collections2.b(a(), collection);
            }
            return b;
        }

        public boolean equals(Object obj) {
            boolean a;
            if (obj == this) {
                return true;
            }
            synchronized (this.f) {
                a = Sets.a(a(), obj);
            }
            return a;
        }

        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Map.Entry<K, Collection<V>>>(super.iterator()) {
                public final Object a(Object obj) {
                    final Map.Entry entry = (Map.Entry) obj;
                    return new ForwardingMapEntry<Object, Collection<Object>>() {
                        public final Map.Entry<Object, Collection<Object>> a() {
                            return entry;
                        }

                        public final Object delegate() {
                            return entry;
                        }

                        public Collection<Object> getValue() {
                            return Synchronized.b(SynchronizedAsMapEntries.this.f, (Collection) entry.getValue());
                        }
                    };
                }
            };
        }

        public boolean remove(Object obj) {
            boolean z;
            synchronized (this.f) {
                Set b = a();
                if (!(obj instanceof Map.Entry)) {
                    z = false;
                } else {
                    Map.Entry entry = (Map.Entry) obj;
                    Preconditions.checkNotNull(entry);
                    z = b.remove(new AbstractMapEntry<Object, Object>(entry) {
                        public Object getKey() {
                            return r1.getKey();
                        }

                        public Object getValue() {
                            return r1.getValue();
                        }
                    });
                }
            }
            return z;
        }

        public boolean removeAll(Collection<?> collection) {
            boolean removeAll;
            synchronized (this.f) {
                removeAll = Iterators.removeAll(a().iterator(), collection);
            }
            return removeAll;
        }

        public boolean retainAll(Collection<?> collection) {
            boolean retainAll;
            synchronized (this.f) {
                retainAll = Iterators.retainAll(a().iterator(), collection);
            }
            return retainAll;
        }

        public Object[] toArray() {
            Object[] objArr;
            synchronized (this.f) {
                Set b = a();
                objArr = new Object[b.size()];
                ObjectArrays.b(b, objArr);
            }
            return objArr;
        }

        public <T> T[] toArray(T[] tArr) {
            T[] c;
            synchronized (this.f) {
                c = ObjectArrays.c(a(), tArr);
            }
            return c;
        }
    }

    public static class SynchronizedNavigableMap<K, V> extends SynchronizedSortedMap<K, V> implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0;
        public transient NavigableSet<K> j;
        public transient NavigableMap<K, V> k;
        public transient NavigableSet<K> l;

        public SynchronizedNavigableMap(NavigableMap<K, V> navigableMap, Object obj) {
            super(navigableMap, obj);
        }

        /* renamed from: c */
        public final NavigableMap<K, V> b() {
            return (NavigableMap) super.a();
        }

        public Map.Entry<K, V> ceilingEntry(K k2) {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().ceilingEntry(k2), this.f);
            }
            return c;
        }

        public K ceilingKey(K k2) {
            K ceilingKey;
            synchronized (this.f) {
                ceilingKey = b().ceilingKey(k2);
            }
            return ceilingKey;
        }

        public NavigableSet<K> descendingKeySet() {
            synchronized (this.f) {
                NavigableSet<K> navigableSet = this.j;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(b().descendingKeySet(), this.f);
                this.j = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        public NavigableMap<K, V> descendingMap() {
            synchronized (this.f) {
                NavigableMap<K, V> navigableMap = this.k;
                if (navigableMap != null) {
                    return navigableMap;
                }
                SynchronizedNavigableMap synchronizedNavigableMap = new SynchronizedNavigableMap(b().descendingMap(), this.f);
                this.k = synchronizedNavigableMap;
                return synchronizedNavigableMap;
            }
        }

        public Map.Entry<K, V> firstEntry() {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().firstEntry(), this.f);
            }
            return c;
        }

        public Map.Entry<K, V> floorEntry(K k2) {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().floorEntry(k2), this.f);
            }
            return c;
        }

        public K floorKey(K k2) {
            K floorKey;
            synchronized (this.f) {
                floorKey = b().floorKey(k2);
            }
            return floorKey;
        }

        public NavigableMap<K, V> headMap(K k2, boolean z) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.f) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(b().headMap(k2, z), this.f);
            }
            return synchronizedNavigableMap;
        }

        public Map.Entry<K, V> higherEntry(K k2) {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().higherEntry(k2), this.f);
            }
            return c;
        }

        public K higherKey(K k2) {
            K higherKey;
            synchronized (this.f) {
                higherKey = b().higherKey(k2);
            }
            return higherKey;
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public Map.Entry<K, V> lastEntry() {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().lastEntry(), this.f);
            }
            return c;
        }

        public Map.Entry<K, V> lowerEntry(K k2) {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().lowerEntry(k2), this.f);
            }
            return c;
        }

        public K lowerKey(K k2) {
            K lowerKey;
            synchronized (this.f) {
                lowerKey = b().lowerKey(k2);
            }
            return lowerKey;
        }

        public NavigableSet<K> navigableKeySet() {
            synchronized (this.f) {
                NavigableSet<K> navigableSet = this.l;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(b().navigableKeySet(), this.f);
                this.l = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        public Map.Entry<K, V> pollFirstEntry() {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().pollFirstEntry(), this.f);
            }
            return c;
        }

        public Map.Entry<K, V> pollLastEntry() {
            Map.Entry<K, V> c;
            synchronized (this.f) {
                c = Synchronized.c(b().pollLastEntry(), this.f);
            }
            return c;
        }

        public NavigableMap<K, V> subMap(K k2, boolean z, K k3, boolean z2) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.f) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(b().subMap(k2, z, k3, z2), this.f);
            }
            return synchronizedNavigableMap;
        }

        public NavigableMap<K, V> tailMap(K k2, boolean z) {
            SynchronizedNavigableMap synchronizedNavigableMap;
            synchronized (this.f) {
                synchronizedNavigableMap = new SynchronizedNavigableMap(b().tailMap(k2, z), this.f);
            }
            return synchronizedNavigableMap;
        }

        public SortedMap<K, V> headMap(K k2) {
            return headMap(k2, false);
        }

        public SortedMap<K, V> subMap(K k2, K k3) {
            return subMap(k2, true, k3, false);
        }

        public SortedMap<K, V> tailMap(K k2) {
            return tailMap(k2, true);
        }
    }

    public static class SynchronizedNavigableSet<E> extends SynchronizedSortedSet<E> implements NavigableSet<E> {
        private static final long serialVersionUID = 0;
        public transient NavigableSet<E> g;

        public SynchronizedNavigableSet(NavigableSet<E> navigableSet, Object obj) {
            super(navigableSet, obj);
        }

        public E ceiling(E e) {
            E ceiling;
            synchronized (this.f) {
                ceiling = c().ceiling(e);
            }
            return ceiling;
        }

        public Iterator<E> descendingIterator() {
            return c().descendingIterator();
        }

        public NavigableSet<E> descendingSet() {
            synchronized (this.f) {
                NavigableSet<E> navigableSet = this.g;
                if (navigableSet != null) {
                    return navigableSet;
                }
                SynchronizedNavigableSet synchronizedNavigableSet = new SynchronizedNavigableSet(c().descendingSet(), this.f);
                this.g = synchronizedNavigableSet;
                return synchronizedNavigableSet;
            }
        }

        /* renamed from: f */
        public final NavigableSet<E> c() {
            return (NavigableSet) super.b();
        }

        public E floor(E e) {
            E floor;
            synchronized (this.f) {
                floor = c().floor(e);
            }
            return floor;
        }

        public NavigableSet<E> headSet(E e, boolean z) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.f) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(c().headSet(e, z), this.f);
            }
            return synchronizedNavigableSet;
        }

        public E higher(E e) {
            E higher;
            synchronized (this.f) {
                higher = c().higher(e);
            }
            return higher;
        }

        public E lower(E e) {
            E lower;
            synchronized (this.f) {
                lower = c().lower(e);
            }
            return lower;
        }

        public E pollFirst() {
            E pollFirst;
            synchronized (this.f) {
                pollFirst = c().pollFirst();
            }
            return pollFirst;
        }

        public E pollLast() {
            E pollLast;
            synchronized (this.f) {
                pollLast = c().pollLast();
            }
            return pollLast;
        }

        public NavigableSet<E> subSet(E e, boolean z, E e2, boolean z2) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.f) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(c().subSet(e, z, e2, z2), this.f);
            }
            return synchronizedNavigableSet;
        }

        public NavigableSet<E> tailSet(E e, boolean z) {
            SynchronizedNavigableSet synchronizedNavigableSet;
            synchronized (this.f) {
                synchronizedNavigableSet = new SynchronizedNavigableSet(c().tailSet(e, z), this.f);
            }
            return synchronizedNavigableSet;
        }

        public SortedSet<E> headSet(E e) {
            return headSet(e, false);
        }

        public SortedSet<E> subSet(E e, E e2) {
            return subSet(e, true, e2, false);
        }

        public SortedSet<E> tailSet(E e) {
            return tailSet(e, true);
        }
    }
}
