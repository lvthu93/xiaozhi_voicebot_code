package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Sets;
import com.google.common.collect.Synchronized;
import j$.util.Iterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Consumer;

public final class Multimaps {

    public static final class AsMap<K, V> extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        public final Multimap<K, V> h;

        public class EntrySet extends Maps.EntrySet<K, Collection<V>> {
            public EntrySet() {
            }

            public final Map<K, Collection<V>> a() {
                return AsMap.this;
            }

            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                Set<K> keySet = AsMap.this.h.keySet();
                return new TransformedIterator<Object, Map.Entry<Object, Object>>(keySet.iterator(), new Function<K, Collection<V>>() {
                    public Collection<V> apply(K k) {
                        return AsMap.this.h.get(k);
                    }
                }) {
                    public final /* synthetic */ Function f;

                    {
                        this.f = r2;
                    }

                    public final Object a(Object obj) {
                        return Maps.immutableEntry(obj, this.f.apply(obj));
                    }
                };
            }

            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                AsMap.this.h.keySet().remove(((Map.Entry) obj).getKey());
                return true;
            }
        }

        public AsMap(Multimap<K, V> multimap) {
            this.h = (Multimap) Preconditions.checkNotNull(multimap);
        }

        public void clear() {
            this.h.clear();
        }

        public boolean containsKey(Object obj) {
            return this.h.containsKey(obj);
        }

        public final Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new EntrySet();
        }

        public boolean isEmpty() {
            return this.h.isEmpty();
        }

        public Set<K> keySet() {
            return this.h.keySet();
        }

        public int size() {
            return this.h.keySet().size();
        }

        public Collection<V> get(Object obj) {
            if (containsKey(obj)) {
                return this.h.get(obj);
            }
            return null;
        }

        public Collection<V> remove(Object obj) {
            if (containsKey(obj)) {
                return this.h.removeAll(obj);
            }
            return null;
        }
    }

    public static class CustomListMultimap<K, V> extends AbstractListMultimap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Supplier<? extends List<V>> l;

        public CustomListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
            super(map);
            this.l = (Supplier) Preconditions.checkNotNull(supplier);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.l = (Supplier) objectInputStream.readObject();
            m((Map) objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.l);
            objectOutputStream.writeObject(this.j);
        }

        public final Map<K, Collection<V>> a() {
            return j();
        }

        public final Set<K> c() {
            return k();
        }

        /* renamed from: p */
        public final List<V> h() {
            return (List) this.l.get();
        }
    }

    public static class CustomMultimap<K, V> extends AbstractMapBasedMultimap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Supplier<? extends Collection<V>> l;

        public CustomMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
            super(map);
            this.l = (Supplier) Preconditions.checkNotNull(supplier);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.l = (Supplier) objectInputStream.readObject();
            m((Map) objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.l);
            objectOutputStream.writeObject(this.j);
        }

        public final Map<K, Collection<V>> a() {
            return j();
        }

        public final Set<K> c() {
            return k();
        }

        public final Collection<V> h() {
            return (Collection) this.l.get();
        }

        public final <E> Collection<E> n(Collection<E> collection) {
            if (collection instanceof NavigableSet) {
                return Sets.unmodifiableNavigableSet((NavigableSet) collection);
            }
            if (collection instanceof SortedSet) {
                return Collections.unmodifiableSortedSet((SortedSet) collection);
            }
            if (collection instanceof Set) {
                return Collections.unmodifiableSet((Set) collection);
            }
            if (collection instanceof List) {
                return Collections.unmodifiableList((List) collection);
            }
            return Collections.unmodifiableCollection(collection);
        }

        public final Collection<V> o(K k, Collection<V> collection) {
            if (collection instanceof List) {
                List list = (List) collection;
                if (list instanceof RandomAccess) {
                    return new AbstractMapBasedMultimap.RandomAccessWrappedList(this, k, list, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
                }
                return new AbstractMapBasedMultimap.WrappedList(k, list, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
            } else if (collection instanceof NavigableSet) {
                return new AbstractMapBasedMultimap.WrappedNavigableSet(k, (NavigableSet) collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
            } else {
                if (collection instanceof SortedSet) {
                    return new AbstractMapBasedMultimap.WrappedSortedSet(k, (SortedSet) collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
                }
                if (collection instanceof Set) {
                    return new AbstractMapBasedMultimap.WrappedSet(k, (Set) collection);
                }
                return new AbstractMapBasedMultimap.WrappedCollection(k, collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
            }
        }
    }

    public static class CustomSetMultimap<K, V> extends AbstractSetMultimap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Supplier<? extends Set<V>> l;

        public CustomSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
            super(map);
            this.l = (Supplier) Preconditions.checkNotNull(supplier);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.l = (Supplier) objectInputStream.readObject();
            m((Map) objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.l);
            objectOutputStream.writeObject(this.j);
        }

        public final Map<K, Collection<V>> a() {
            return j();
        }

        public final Set<K> c() {
            return k();
        }

        public final <E> Collection<E> n(Collection<E> collection) {
            if (collection instanceof NavigableSet) {
                return Sets.unmodifiableNavigableSet((NavigableSet) collection);
            }
            if (collection instanceof SortedSet) {
                return Collections.unmodifiableSortedSet((SortedSet) collection);
            }
            return Collections.unmodifiableSet((Set) collection);
        }

        public final Collection<V> o(K k, Collection<V> collection) {
            if (collection instanceof NavigableSet) {
                return new AbstractMapBasedMultimap.WrappedNavigableSet(k, (NavigableSet) collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
            }
            if (collection instanceof SortedSet) {
                return new AbstractMapBasedMultimap.WrappedSortedSet(k, (SortedSet) collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
            }
            return new AbstractMapBasedMultimap.WrappedSet(k, (Set) collection);
        }

        /* renamed from: p */
        public final Set<V> h() {
            return (Set) this.l.get();
        }
    }

    public static class CustomSortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;
        public transient Supplier<? extends SortedSet<V>> l;
        public transient Comparator<? super V> m;

        public CustomSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
            super(map);
            this.l = (Supplier) Preconditions.checkNotNull(supplier);
            this.m = ((SortedSet) supplier.get()).comparator();
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            Supplier<? extends SortedSet<V>> supplier = (Supplier) objectInputStream.readObject();
            this.l = supplier;
            this.m = ((SortedSet) supplier.get()).comparator();
            m((Map) objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.l);
            objectOutputStream.writeObject(this.j);
        }

        public final Map<K, Collection<V>> a() {
            return j();
        }

        public final Set<K> c() {
            return k();
        }

        /* renamed from: r */
        public final SortedSet<V> p() {
            return (SortedSet) this.l.get();
        }

        public Comparator<? super V> valueComparator() {
            return this.m;
        }
    }

    public static abstract class Entries<K, V> extends AbstractCollection<Map.Entry<K, V>> {
        public abstract Multimap<K, V> a();

        public void clear() {
            a().clear();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return a().containsEntry(entry.getKey(), entry.getValue());
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            return a().remove(entry.getKey(), entry.getValue());
        }

        public int size() {
            return a().size();
        }
    }

    public static class Keys<K, V> extends AbstractMultiset<K> {
        public final Multimap<K, V> g;

        public Keys(Multimap<K, V> multimap) {
            this.g = multimap;
        }

        public final int b() {
            return this.g.asMap().size();
        }

        public final Iterator<K> c() {
            throw new AssertionError("should never be called");
        }

        public void clear() {
            this.g.clear();
        }

        public boolean contains(Object obj) {
            return this.g.containsKey(obj);
        }

        public int count(Object obj) {
            Collection collection = (Collection) Maps.i(this.g.asMap(), obj);
            if (collection == null) {
                return 0;
            }
            return collection.size();
        }

        public Set<K> elementSet() {
            return this.g.keySet();
        }

        public final Iterator<Multiset.Entry<K>> f() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Multiset.Entry<K>>(this.g.asMap().entrySet().iterator()) {
                public final Object a(Object obj) {
                    final Map.Entry entry = (Map.Entry) obj;
                    return new Multisets.AbstractEntry<Object>() {
                        public int getCount() {
                            return ((Collection) entry.getValue()).size();
                        }

                        public Object getElement() {
                            return entry.getKey();
                        }
                    };
                }
            };
        }

        public Iterator<K> iterator() {
            return new TransformedIterator<Map.Entry<Object, Object>, Object>(this.g.entries().iterator()) {
                public final Object a(Object obj) {
                    return ((Map.Entry) obj).getKey();
                }
            };
        }

        public int remove(Object obj, int i) {
            CollectPreconditions.b(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            Collection collection = (Collection) Maps.i(this.g.asMap(), obj);
            if (collection == null) {
                return 0;
            }
            int size = collection.size();
            if (i >= size) {
                collection.clear();
            } else {
                Iterator it = collection.iterator();
                for (int i2 = 0; i2 < i; i2++) {
                    it.next();
                    it.remove();
                }
            }
            return size;
        }

        public int size() {
            return this.g.size();
        }
    }

    public static class MapMultimap<K, V> extends AbstractMultimap<K, V> implements SetMultimap<K, V>, Serializable {
        private static final long serialVersionUID = 7845222491160860175L;
        public final Map<K, V> j;

        public MapMultimap(Map<K, V> map) {
            this.j = (Map) Preconditions.checkNotNull(map);
        }

        public final Map<K, Collection<V>> a() {
            return new AsMap(this);
        }

        public final Collection<Map.Entry<K, V>> b() {
            throw new AssertionError("unreachable");
        }

        public final Set<K> c() {
            return this.j.keySet();
        }

        public void clear() {
            this.j.clear();
        }

        public boolean containsEntry(Object obj, Object obj2) {
            return this.j.entrySet().contains(Maps.immutableEntry(obj, obj2));
        }

        public boolean containsKey(Object obj) {
            return this.j.containsKey(obj);
        }

        public boolean containsValue(Object obj) {
            return this.j.containsValue(obj);
        }

        public final Multiset<K> d() {
            return new Keys(this);
        }

        public final Collection<V> e() {
            return this.j.values();
        }

        public final Iterator<Map.Entry<K, V>> f() {
            return this.j.entrySet().iterator();
        }

        public int hashCode() {
            return this.j.hashCode();
        }

        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object obj, Object obj2) {
            return this.j.entrySet().remove(Maps.immutableEntry(obj, obj2));
        }

        public int size() {
            return this.j.size();
        }

        public Set<Map.Entry<K, V>> entries() {
            return this.j.entrySet();
        }

        public Set<V> get(final K k) {
            return new Sets.ImprovedAbstractSet<V>() {
                public Iterator<V> iterator() {
                    return new Object() {
                        public int c;

                        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                            Iterator.CC.$default$forEachRemaining(this, consumer);
                        }

                        public boolean hasNext() {
                            if (this.c == 0) {
                                AnonymousClass1 r0 = AnonymousClass1.this;
                                if (MapMultimap.this.j.containsKey(k)) {
                                    return true;
                                }
                            }
                            return false;
                        }

                        public V next() {
                            if (hasNext()) {
                                this.c++;
                                AnonymousClass1 r0 = AnonymousClass1.this;
                                return MapMultimap.this.j.get(k);
                            }
                            throw new NoSuchElementException();
                        }

                        public void remove() {
                            boolean z = true;
                            if (this.c != 1) {
                                z = false;
                            }
                            CollectPreconditions.e(z);
                            this.c = -1;
                            AnonymousClass1 r0 = AnonymousClass1.this;
                            MapMultimap.this.j.remove(k);
                        }
                    };
                }

                public int size() {
                    return MapMultimap.this.j.containsKey(k) ? 1 : 0;
                }
            };
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        public Set<V> removeAll(Object obj) {
            HashSet hashSet = new HashSet(2);
            Map<K, V> map = this.j;
            if (!map.containsKey(obj)) {
                return hashSet;
            }
            hashSet.add(map.remove(obj));
            return hashSet;
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    public static final class TransformedEntriesListMultimap<K, V1, V2> extends TransformedEntriesMultimap<K, V1, V2> implements ListMultimap<K, V2> {
        public TransformedEntriesListMultimap(ListMultimap<K, V1> listMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(listMultimap, entryTransformer);
        }

        public final Collection h(Object obj, Collection collection) {
            Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.k;
            Preconditions.checkNotNull(entryTransformer);
            return Lists.transform((List) collection, new Function<Object, Object>(obj) {
                public final /* synthetic */ Object f;

                {
                    this.f = r2;
                }

                public Object apply(Object obj) {
                    return EntryTransformer.this.transformEntry(this.f, obj);
                }
            });
        }

        public List<V2> get(K k) {
            Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.k;
            Preconditions.checkNotNull(entryTransformer);
            return Lists.transform((List) this.j.get(k), new Function<Object, Object>(k) {
                public final /* synthetic */ Object f;

                {
                    this.f = r2;
                }

                public Object apply(Object obj) {
                    return EntryTransformer.this.transformEntry(this.f, obj);
                }
            });
        }

        public List<V2> removeAll(Object obj) {
            Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.k;
            Preconditions.checkNotNull(entryTransformer);
            return Lists.transform((List) this.j.removeAll(obj), new Function<Object, Object>(obj) {
                public final /* synthetic */ Object f;

                {
                    this.f = r2;
                }

                public Object apply(Object obj) {
                    return EntryTransformer.this.transformEntry(this.f, obj);
                }
            });
        }

        public List<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    public static class TransformedEntriesMultimap<K, V1, V2> extends AbstractMultimap<K, V2> {
        public final Multimap<K, V1> j;
        public final Maps.EntryTransformer<? super K, ? super V1, V2> k;

        public TransformedEntriesMultimap(Multimap<K, V1> multimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.j = (Multimap) Preconditions.checkNotNull(multimap);
            this.k = (Maps.EntryTransformer) Preconditions.checkNotNull(entryTransformer);
        }

        public final Map<K, Collection<V2>> a() {
            return Maps.transformEntries(this.j.asMap(), new Maps.EntryTransformer<K, Collection<V1>, Collection<V2>>() {
                public Collection<V2> transformEntry(K k, Collection<V1> collection) {
                    return TransformedEntriesMultimap.this.h(k, collection);
                }
            });
        }

        public final Collection<Map.Entry<K, V2>> b() {
            return new AbstractMultimap.Entries();
        }

        public final Set<K> c() {
            return this.j.keySet();
        }

        public void clear() {
            this.j.clear();
        }

        public boolean containsKey(Object obj) {
            return this.j.containsKey(obj);
        }

        public final Multiset<K> d() {
            return this.j.keys();
        }

        public final Collection<V2> e() {
            Collection<Map.Entry<K, V1>> entries = this.j.entries();
            Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.k;
            Preconditions.checkNotNull(entryTransformer);
            return Collections2.transform(entries, new Function<Map.Entry<Object, Object>, Object>() {
                public Object apply(Map.Entry<Object, Object> entry) {
                    return EntryTransformer.this.transformEntry(entry.getKey(), entry.getValue());
                }
            });
        }

        public final java.util.Iterator<Map.Entry<K, V2>> f() {
            java.util.Iterator<Map.Entry<K, V1>> it = this.j.entries().iterator();
            Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.k;
            Preconditions.checkNotNull(entryTransformer);
            return Iterators.transform(it, new Function<Map.Entry<Object, Object>, Map.Entry<Object, Object>>() {
                public Map.Entry<Object, Object> apply(Map.Entry<Object, Object> entry) {
                    EntryTransformer entryTransformer = EntryTransformer.this;
                    Preconditions.checkNotNull(entryTransformer);
                    Preconditions.checkNotNull(entry);
                    return new AbstractMapEntry<Object, Object>(entryTransformer, entry) {
                        public final /* synthetic */ Map.Entry c;
                        public final /* synthetic */ EntryTransformer f;

                        {
                            this.c = r2;
                            this.f = r1;
                        }

                        public Object getKey() {
                            return this.c.getKey();
                        }

                        public Object getValue() {
                            Map.Entry entry = this.c;
                            return this.f.transformEntry(entry.getKey(), entry.getValue());
                        }
                    };
                }
            });
        }

        public Collection<V2> get(K k2) {
            return h(k2, this.j.get(k2));
        }

        public Collection<V2> h(K k2, Collection<V1> collection) {
            Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.k;
            Preconditions.checkNotNull(entryTransformer);
            Maps.AnonymousClass10 r1 = new Function<Object, Object>(k2) {
                public final /* synthetic */ Object f;

                {
                    this.f = r2;
                }

                public Object apply(Object obj) {
                    return EntryTransformer.this.transformEntry(this.f, obj);
                }
            };
            if (collection instanceof List) {
                return Lists.transform((List) collection, r1);
            }
            return Collections2.transform(collection, r1);
        }

        public boolean isEmpty() {
            return this.j.isEmpty();
        }

        public boolean put(K k2, V2 v2) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k2, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object obj, Object obj2) {
            return get(obj).remove(obj2);
        }

        public Collection<V2> removeAll(Object obj) {
            return h(obj, this.j.removeAll(obj));
        }

        public Collection<V2> replaceValues(K k2, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.j.size();
        }

        public boolean putAll(Multimap<? extends K, ? extends V2> multimap) {
            throw new UnsupportedOperationException();
        }
    }

    public static class UnmodifiableListMultimap<K, V> extends UnmodifiableMultimap<K, V> implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0;

        public UnmodifiableListMultimap(ListMultimap<K, V> listMultimap) {
            super(listMultimap);
        }

        public List<V> get(K k) {
            return Collections.unmodifiableList(delegate().get(k));
        }

        public List<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public ListMultimap<K, V> delegate() {
            return (ListMultimap) this.c;
        }
    }

    public static class UnmodifiableMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Multimap<K, V> c;
        public transient Collection<Map.Entry<K, V>> f;
        public transient Multiset<K> g;
        public transient Set<K> h;
        public transient Collection<V> i;
        public transient Map<K, Collection<V>> j;

        public UnmodifiableMultimap(Multimap<K, V> multimap) {
            this.c = (Multimap) Preconditions.checkNotNull(multimap);
        }

        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> map = this.j;
            if (map != null) {
                return map;
            }
            Map<K, Collection<V>> unmodifiableMap = Collections.unmodifiableMap(Maps.transformValues(this.c.asMap(), new Function<Collection<V>, Collection<V>>() {
                public Collection<V> apply(Collection<V> collection) {
                    return Multimaps.a(collection);
                }
            }));
            this.j = unmodifiableMap;
            return unmodifiableMap;
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> collection;
            Collection<Map.Entry<K, V>> collection2 = this.f;
            if (collection2 != null) {
                return collection2;
            }
            Collection<Map.Entry<K, V>> entries = this.c.entries();
            if (entries instanceof Set) {
                collection = new Maps.UnmodifiableEntrySet<>(Collections.unmodifiableSet((Set) entries));
            } else {
                collection = new Maps.UnmodifiableEntries<>(Collections.unmodifiableCollection(entries));
            }
            Collection<Map.Entry<K, V>> collection3 = collection;
            this.f = collection3;
            return collection3;
        }

        public Collection<V> get(K k) {
            return Multimaps.a(this.c.get(k));
        }

        public Set<K> keySet() {
            Set<K> set = this.h;
            if (set != null) {
                return set;
            }
            Set<K> unmodifiableSet = Collections.unmodifiableSet(this.c.keySet());
            this.h = unmodifiableSet;
            return unmodifiableSet;
        }

        public Multiset<K> keys() {
            Multiset<K> multiset = this.g;
            if (multiset != null) {
                return multiset;
            }
            Multiset<K> unmodifiableMultiset = Multisets.unmodifiableMultiset(this.c.keys());
            this.g = unmodifiableMultiset;
            return unmodifiableMultiset;
        }

        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public boolean remove(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public Collection<V> values() {
            Collection<V> collection = this.i;
            if (collection != null) {
                return collection;
            }
            Collection<V> unmodifiableCollection = Collections.unmodifiableCollection(this.c.values());
            this.i = unmodifiableCollection;
            return unmodifiableCollection;
        }

        public Multimap<K, V> delegate() {
            return this.c;
        }

        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }
    }

    public static class UnmodifiableSetMultimap<K, V> extends UnmodifiableMultimap<K, V> implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        public UnmodifiableSetMultimap(SetMultimap<K, V> setMultimap) {
            super(setMultimap);
        }

        public Set<Map.Entry<K, V>> entries() {
            return new Maps.UnmodifiableEntrySet(Collections.unmodifiableSet(delegate().entries()));
        }

        public Set<V> get(K k) {
            return Collections.unmodifiableSet(delegate().get(k));
        }

        public Set<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public SetMultimap<K, V> delegate() {
            return (SetMultimap) this.c;
        }
    }

    public static Collection a(Collection collection) {
        if (collection instanceof SortedSet) {
            return Collections.unmodifiableSortedSet((SortedSet) collection);
        }
        if (collection instanceof Set) {
            return Collections.unmodifiableSet((Set) collection);
        }
        if (collection instanceof List) {
            return Collections.unmodifiableList((List) collection);
        }
        return Collections.unmodifiableCollection(collection);
    }

    public static <K, V> Map<K, List<V>> asMap(ListMultimap<K, V> listMultimap) {
        return listMultimap.asMap();
    }

    public static <K, V> Multimap<K, V> filterEntries(Multimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (multimap instanceof SetMultimap) {
            return filterEntries((SetMultimap) multimap, predicate);
        }
        if (!(multimap instanceof FilteredMultimap)) {
            return new FilteredEntryMultimap((Multimap) Preconditions.checkNotNull(multimap), predicate);
        }
        FilteredMultimap filteredMultimap = (FilteredMultimap) multimap;
        return new FilteredEntryMultimap(filteredMultimap.unfiltered(), Predicates.and(filteredMultimap.entryPredicate(), predicate));
    }

    public static <K, V> Multimap<K, V> filterKeys(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        if (multimap instanceof SetMultimap) {
            return filterKeys((SetMultimap) multimap, predicate);
        }
        if (multimap instanceof ListMultimap) {
            return filterKeys((ListMultimap) multimap, predicate);
        }
        if (multimap instanceof FilteredKeyMultimap) {
            FilteredKeyMultimap filteredKeyMultimap = (FilteredKeyMultimap) multimap;
            return new FilteredKeyMultimap(filteredKeyMultimap.j, Predicates.and(filteredKeyMultimap.k, predicate));
        } else if (!(multimap instanceof FilteredMultimap)) {
            return new FilteredKeyMultimap(multimap, predicate);
        } else {
            FilteredMultimap filteredMultimap = (FilteredMultimap) multimap;
            return new FilteredEntryMultimap(filteredMultimap.unfiltered(), Predicates.and(filteredMultimap.entryPredicate(), Maps.g(predicate)));
        }
    }

    public static <K, V> Multimap<K, V> filterValues(Multimap<K, V> multimap, Predicate<? super V> predicate) {
        return filterEntries(multimap, Maps.k(predicate));
    }

    public static <K, V> SetMultimap<K, V> forMap(Map<K, V> map) {
        return new MapMultimap(map);
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterable<V> iterable, Function<? super V, K> function) {
        return index(iterable.iterator(), function);
    }

    public static <K, V, M extends Multimap<K, V>> M invertFrom(Multimap<? extends V, ? extends K> multimap, M m) {
        Preconditions.checkNotNull(m);
        for (Map.Entry next : multimap.entries()) {
            m.put(next.getValue(), next.getKey());
        }
        return m;
    }

    public static <K, V> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
        return new CustomListMultimap(map, supplier);
    }

    public static <K, V> Multimap<K, V> newMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
        return new CustomMultimap(map, supplier);
    }

    public static <K, V> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
        return new CustomSetMultimap(map, supplier);
    }

    public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
        return new CustomSortedSetMultimap(map, supplier);
    }

    public static <K, V> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> listMultimap) {
        if ((listMultimap instanceof Synchronized.SynchronizedListMultimap) || (listMultimap instanceof BaseImmutableMultimap)) {
            return listMultimap;
        }
        return new Synchronized.SynchronizedListMultimap(listMultimap);
    }

    public static <K, V> Multimap<K, V> synchronizedMultimap(Multimap<K, V> multimap) {
        if ((multimap instanceof Synchronized.SynchronizedMultimap) || (multimap instanceof BaseImmutableMultimap)) {
            return multimap;
        }
        return new Synchronized.SynchronizedMultimap(multimap);
    }

    public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> setMultimap) {
        if ((setMultimap instanceof Synchronized.SynchronizedSetMultimap) || (setMultimap instanceof BaseImmutableMultimap)) {
            return setMultimap;
        }
        return new Synchronized.SynchronizedSetMultimap(setMultimap);
    }

    public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        if (sortedSetMultimap instanceof Synchronized.SynchronizedSortedSetMultimap) {
            return sortedSetMultimap;
        }
        return new Synchronized.SynchronizedSortedSetMultimap(sortedSetMultimap);
    }

    public static <K, V1, V2> Multimap<K, V2> transformEntries(Multimap<K, V1> multimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesMultimap(multimap, entryTransformer);
    }

    public static <K, V1, V2> Multimap<K, V2> transformValues(Multimap<K, V1> multimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function);
        return transformEntries(multimap, new Maps.EntryTransformer<Object, Object, Object>() {
            public Object transformEntry(Object obj, Object obj2) {
                return Function.this.apply(obj2);
            }
        });
    }

    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> listMultimap) {
        return ((listMultimap instanceof UnmodifiableListMultimap) || (listMultimap instanceof ImmutableListMultimap)) ? listMultimap : new UnmodifiableListMultimap(listMultimap);
    }

    public static <K, V> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> multimap) {
        return ((multimap instanceof UnmodifiableMultimap) || (multimap instanceof ImmutableMultimap)) ? multimap : new UnmodifiableMultimap(multimap);
    }

    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> setMultimap) {
        return ((setMultimap instanceof UnmodifiableSetMultimap) || (setMultimap instanceof ImmutableSetMultimap)) ? setMultimap : new UnmodifiableSetMultimap(setMultimap);
    }

    public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        if (sortedSetMultimap instanceof UnmodifiableSortedSetMultimap) {
            return sortedSetMultimap;
        }
        return new UnmodifiableSortedSetMultimap(sortedSetMultimap);
    }

    public static class UnmodifiableSortedSetMultimap<K, V> extends UnmodifiableSetMultimap<K, V> implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0;

        public UnmodifiableSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
            super(sortedSetMultimap);
        }

        public Comparator<? super V> valueComparator() {
            return delegate().valueComparator();
        }

        public SortedSet<V> get(K k) {
            return Collections.unmodifiableSortedSet(delegate().get(k));
        }

        public SortedSet<V> removeAll(Object obj) {
            throw new UnsupportedOperationException();
        }

        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap) super.delegate();
        }
    }

    public static <K, V> Map<K, Set<V>> asMap(SetMultimap<K, V> setMultimap) {
        return setMultimap.asMap();
    }

    public static <K, V> SetMultimap<K, V> filterValues(SetMultimap<K, V> setMultimap, Predicate<? super V> predicate) {
        return filterEntries(setMultimap, Maps.k(predicate));
    }

    public static <K, V> ImmutableListMultimap<K, V> index(java.util.Iterator<V> it, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        while (it.hasNext()) {
            V next = it.next();
            Preconditions.checkNotNull(next, it);
            builder.put(function.apply(next), next);
        }
        return builder.build();
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformEntries(ListMultimap<K, V1> listMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesListMultimap(listMultimap, entryTransformer);
    }

    public static <K, V> Map<K, SortedSet<V>> asMap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return sortedSetMultimap.asMap();
    }

    @Deprecated
    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ImmutableListMultimap<K, V> immutableListMultimap) {
        return (ListMultimap) Preconditions.checkNotNull(immutableListMultimap);
    }

    @Deprecated
    public static <K, V> Multimap<K, V> unmodifiableMultimap(ImmutableMultimap<K, V> immutableMultimap) {
        return (Multimap) Preconditions.checkNotNull(immutableMultimap);
    }

    @Deprecated
    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(ImmutableSetMultimap<K, V> immutableSetMultimap) {
        return (SetMultimap) Preconditions.checkNotNull(immutableSetMultimap);
    }

    public static <K, V> Map<K, Collection<V>> asMap(Multimap<K, V> multimap) {
        return multimap.asMap();
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformValues(ListMultimap<K, V1> listMultimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(function);
        return transformEntries(listMultimap, new Maps.EntryTransformer<Object, Object, Object>() {
            public Object transformEntry(Object obj, Object obj2) {
                return Function.this.apply(obj2);
            }
        });
    }

    public static <K, V> SetMultimap<K, V> filterEntries(SetMultimap<K, V> setMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (!(setMultimap instanceof FilteredSetMultimap)) {
            return new FilteredEntrySetMultimap((SetMultimap) Preconditions.checkNotNull(setMultimap), predicate);
        }
        FilteredSetMultimap filteredSetMultimap = (FilteredSetMultimap) setMultimap;
        return new FilteredEntrySetMultimap(filteredSetMultimap.unfiltered(), Predicates.and(filteredSetMultimap.entryPredicate(), predicate));
    }

    public static <K, V> SetMultimap<K, V> filterKeys(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
        if (setMultimap instanceof FilteredKeySetMultimap) {
            FilteredKeySetMultimap filteredKeySetMultimap = (FilteredKeySetMultimap) setMultimap;
            return new FilteredKeySetMultimap(filteredKeySetMultimap.unfiltered(), Predicates.and(filteredKeySetMultimap.k, predicate));
        } else if (!(setMultimap instanceof FilteredSetMultimap)) {
            return new FilteredKeySetMultimap(setMultimap, predicate);
        } else {
            FilteredSetMultimap filteredSetMultimap = (FilteredSetMultimap) setMultimap;
            return new FilteredEntrySetMultimap(filteredSetMultimap.unfiltered(), Predicates.and(filteredSetMultimap.entryPredicate(), Maps.g(predicate)));
        }
    }

    public static <K, V> ListMultimap<K, V> filterKeys(ListMultimap<K, V> listMultimap, Predicate<? super K> predicate) {
        if (!(listMultimap instanceof FilteredKeyListMultimap)) {
            return new FilteredKeyListMultimap(listMultimap, predicate);
        }
        FilteredKeyListMultimap filteredKeyListMultimap = (FilteredKeyListMultimap) listMultimap;
        return new FilteredKeyListMultimap(filteredKeyListMultimap.unfiltered(), Predicates.and(filteredKeyListMultimap.k, predicate));
    }
}
