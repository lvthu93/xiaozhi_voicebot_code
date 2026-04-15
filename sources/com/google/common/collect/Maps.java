package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Sets;
import com.google.common.collect.Synchronized;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;

public final class Maps {

    public static abstract class AbstractFilteredMap<K, V> extends ViewCachingAbstractMap<K, V> {
        public final Map<K, V> h;
        public final Predicate<? super Map.Entry<K, V>> i;

        public AbstractFilteredMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            this.h = map;
            this.i = predicate;
        }

        public final Collection<V> a() {
            return new FilteredMapValues(this, this.h, this.i);
        }

        public final boolean b(Object obj, V v) {
            return this.i.apply(Maps.immutableEntry(obj, v));
        }

        public boolean containsKey(Object obj) {
            Map<K, V> map = this.h;
            return map.containsKey(obj) && b(obj, map.get(obj));
        }

        public V get(Object obj) {
            V v = this.h.get(obj);
            if (v == null || !b(obj, v)) {
                return null;
            }
            return v;
        }

        public boolean isEmpty() {
            return entrySet().isEmpty();
        }

        public V put(K k, V v) {
            Preconditions.checkArgument(b(k, v));
            return this.h.put(k, v);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            for (Map.Entry next : map.entrySet()) {
                Preconditions.checkArgument(b(next.getKey(), next.getValue()));
            }
            this.h.putAll(map);
        }

        public V remove(Object obj) {
            if (containsKey(obj)) {
                return this.h.remove(obj);
            }
            return null;
        }
    }

    public static class AsMapView<K, V> extends ViewCachingAbstractMap<K, V> {
        public final Set<K> h;
        public final Function<? super K, V> i;

        public AsMapView(Set<K> set, Function<? super K, V> function) {
            this.h = (Set) Preconditions.checkNotNull(set);
            this.i = (Function) Preconditions.checkNotNull(function);
        }

        public final Collection<V> a() {
            return Collections2.transform(this.h, this.i);
        }

        public Set<K> b() {
            return this.h;
        }

        public void clear() {
            b().clear();
        }

        public boolean containsKey(Object obj) {
            return b().contains(obj);
        }

        public final Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet<K, V>() {
                public final Map<K, V> a() {
                    return AsMapView.this;
                }

                public Iterator<Map.Entry<K, V>> iterator() {
                    AsMapView asMapView = AsMapView.this;
                    return new TransformedIterator<Object, Map.Entry<Object, Object>>(asMapView.b().iterator(), asMapView.i) {
                        public final /* synthetic */ Function f;

                        {
                            this.f = r2;
                        }

                        public final Object a(Object obj) {
                            return Maps.immutableEntry(obj, this.f.apply(obj));
                        }
                    };
                }
            };
        }

        public Set<K> createKeySet() {
            return new ForwardingSet<Object>(b()) {
                public final /* synthetic */ Set c;

                {
                    this.c = r1;
                }

                public final Collection a() {
                    return this.c;
                }

                public boolean add(Object obj) {
                    throw new UnsupportedOperationException();
                }

                public boolean addAll(Collection<Object> collection) {
                    throw new UnsupportedOperationException();
                }

                public final Object delegate() {
                    return this.c;
                }

                public final Set<Object> g() {
                    return this.c;
                }
            };
        }

        public V get(Object obj) {
            if (Collections2.d(obj, b())) {
                return this.i.apply(obj);
            }
            return null;
        }

        public V remove(Object obj) {
            if (b().remove(obj)) {
                return this.i.apply(obj);
            }
            return null;
        }

        public int size() {
            return b().size();
        }
    }

    public static final class BiMapConverter<A, B> extends Converter<A, B> implements Serializable {
        private static final long serialVersionUID = 0;
        public final BiMap<A, B> g;

        public BiMapConverter(BiMap<A, B> biMap) {
            this.g = (BiMap) Preconditions.checkNotNull(biMap);
        }

        public final A d(B b) {
            boolean z;
            A a = this.g.inverse().get(b);
            if (a != null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "No non-null mapping present for input: %s", (Object) b);
            return a;
        }

        public final B e(A a) {
            boolean z;
            B b = this.g.get(a);
            if (b != null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "No non-null mapping present for input: %s", (Object) a);
            return b;
        }

        public boolean equals(Object obj) {
            if (obj instanceof BiMapConverter) {
                return this.g.equals(((BiMapConverter) obj).g);
            }
            return false;
        }

        public int hashCode() {
            return this.g.hashCode();
        }

        public String toString() {
            return "Maps.asConverter(" + this.g + ")";
        }
    }

    public static abstract class DescendingMap<K, V> extends ForwardingMap<K, V> implements NavigableMap<K, V> {
        public transient Ordering c;
        public transient Set<Map.Entry<K, V>> f;
        public transient NavigableSet<K> g;

        public final Map<K, V> a() {
            return f();
        }

        public abstract Iterator<Map.Entry<K, V>> c();

        public Map.Entry<K, V> ceilingEntry(K k) {
            return f().floorEntry(k);
        }

        public K ceilingKey(K k) {
            return f().floorKey(k);
        }

        public Comparator<? super K> comparator() {
            Ordering ordering = this.c;
            if (ordering != null) {
                return ordering;
            }
            Comparator comparator = f().comparator();
            if (comparator == null) {
                comparator = Ordering.natural();
            }
            Ordering reverse = Ordering.from(comparator).reverse();
            this.c = reverse;
            return reverse;
        }

        public final Object delegate() {
            return f();
        }

        public NavigableSet<K> descendingKeySet() {
            return f().navigableKeySet();
        }

        public NavigableMap<K, V> descendingMap() {
            return f();
        }

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set = this.f;
            if (set != null) {
                return set;
            }
            AnonymousClass1EntrySetImpl r0 = new EntrySet<Object, Object>() {
                public final Map<Object, Object> a() {
                    return DescendingMap.this;
                }

                public Iterator<Map.Entry<Object, Object>> iterator() {
                    return DescendingMap.this.c();
                }
            };
            this.f = r0;
            return r0;
        }

        public abstract NavigableMap<K, V> f();

        public Map.Entry<K, V> firstEntry() {
            return f().lastEntry();
        }

        public K firstKey() {
            return f().lastKey();
        }

        public Map.Entry<K, V> floorEntry(K k) {
            return f().ceilingEntry(k);
        }

        public K floorKey(K k) {
            return f().ceilingKey(k);
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            return f().tailMap(k, z).descendingMap();
        }

        public Map.Entry<K, V> higherEntry(K k) {
            return f().lowerEntry(k);
        }

        public K higherKey(K k) {
            return f().lowerKey(k);
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public Map.Entry<K, V> lastEntry() {
            return f().firstEntry();
        }

        public K lastKey() {
            return f().firstKey();
        }

        public Map.Entry<K, V> lowerEntry(K k) {
            return f().higherEntry(k);
        }

        public K lowerKey(K k) {
            return f().higherKey(k);
        }

        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> navigableSet = this.g;
            if (navigableSet != null) {
                return navigableSet;
            }
            NavigableKeySet navigableKeySet = new NavigableKeySet(this);
            this.g = navigableKeySet;
            return navigableKeySet;
        }

        public Map.Entry<K, V> pollFirstEntry() {
            return f().pollLastEntry();
        }

        public Map.Entry<K, V> pollLastEntry() {
            return f().pollFirstEntry();
        }

        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return f().subMap(k2, z2, k, z).descendingMap();
        }

        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return f().headMap(k, z).descendingMap();
        }

        public String toString() {
            return Maps.j(this);
        }

        public Collection<V> values() {
            return new Values(this);
        }

        public SortedMap<K, V> headMap(K k) {
            return headMap(k, false);
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            return subMap(k, true, k2, false);
        }

        public SortedMap<K, V> tailMap(K k) {
            return tailMap(k, true);
        }
    }

    public enum EntryFunction implements Function<Map.Entry<?, ?>, Object> {
        ;

        /* access modifiers changed from: public */
        EntryFunction() {
            throw null;
        }
    }

    public static abstract class EntrySet<K, V> extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
        public abstract Map<K, V> a();

        public void clear() {
            a().clear();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object i = Maps.i(a(), key);
            if (!Objects.equal(i, entry.getValue())) {
                return false;
            }
            if (i != null || a().containsKey(key)) {
                return true;
            }
            return false;
        }

        public boolean isEmpty() {
            return a().isEmpty();
        }

        public boolean remove(Object obj) {
            if (contains(obj)) {
                return a().keySet().remove(((Map.Entry) obj).getKey());
            }
            return false;
        }

        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                return Sets.d(this, collection.iterator());
            }
        }

        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
            } catch (UnsupportedOperationException unused) {
                HashSet newHashSetWithExpectedSize = Sets.newHashSetWithExpectedSize(collection.size());
                for (Object next : collection) {
                    if (contains(next)) {
                        newHashSetWithExpectedSize.add(((Map.Entry) next).getKey());
                    }
                }
                return a().keySet().retainAll(newHashSetWithExpectedSize);
            }
        }

        public int size() {
            return a().size();
        }
    }

    public interface EntryTransformer<K, V1, V2> {
        V2 transformEntry(K k, V1 v1);
    }

    public static final class FilteredEntryBiMap<K, V> extends FilteredEntryMap<K, V> implements BiMap<K, V> {
        public final BiMap<V, K> k;

        public FilteredEntryBiMap(BiMap<K, V> biMap, final Predicate<? super Map.Entry<K, V>> predicate) {
            super(biMap, predicate);
            this.k = new FilteredEntryBiMap(biMap.inverse(), new Predicate<Map.Entry<Object, Object>>() {
                public boolean apply(Map.Entry<Object, Object> entry) {
                    return Predicate.this.apply(Maps.immutableEntry(entry.getValue(), entry.getKey()));
                }
            }, this);
        }

        public V forcePut(K k2, V v) {
            Preconditions.checkArgument(b(k2, v));
            return ((BiMap) this.h).forcePut(k2, v);
        }

        public BiMap<V, K> inverse() {
            return this.k;
        }

        public Set<V> values() {
            return this.k.keySet();
        }

        public FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate, BiMap<V, K> biMap2) {
            super(biMap, predicate);
            this.k = biMap2;
        }
    }

    public static class FilteredEntryMap<K, V> extends AbstractFilteredMap<K, V> {
        public final Set<Map.Entry<K, V>> j;

        public class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
            public EntrySet() {
            }

            public final Collection a() {
                return FilteredEntryMap.this.j;
            }

            public final Object delegate() {
                return FilteredEntryMap.this.j;
            }

            public final Set<Map.Entry<K, V>> g() {
                return FilteredEntryMap.this.j;
            }

            public Iterator<Map.Entry<K, V>> iterator() {
                return new TransformedIterator<Map.Entry<K, V>, Map.Entry<K, V>>(FilteredEntryMap.this.j.iterator()) {
                    public final Object a(Object obj) {
                        final Map.Entry entry = (Map.Entry) obj;
                        return new ForwardingMapEntry<Object, Object>() {
                            public final Map.Entry<Object, Object> a() {
                                return entry;
                            }

                            public final Object delegate() {
                                return entry;
                            }

                            public Object setValue(Object obj) {
                                Preconditions.checkArgument(FilteredEntryMap.this.b(getKey(), obj));
                                return super.setValue(obj);
                            }
                        };
                    }
                };
            }
        }

        public class KeySet extends KeySet<K, V> {
            public KeySet() {
                super(FilteredEntryMap.this);
            }

            public boolean remove(Object obj) {
                FilteredEntryMap filteredEntryMap = FilteredEntryMap.this;
                if (!filteredEntryMap.containsKey(obj)) {
                    return false;
                }
                filteredEntryMap.h.remove(obj);
                return true;
            }

            public boolean removeAll(Collection<?> collection) {
                FilteredEntryMap filteredEntryMap = FilteredEntryMap.this;
                return FilteredEntryMap.c(filteredEntryMap.h, filteredEntryMap.i, collection);
            }

            public boolean retainAll(Collection<?> collection) {
                FilteredEntryMap filteredEntryMap = FilteredEntryMap.this;
                return FilteredEntryMap.d(filteredEntryMap.h, filteredEntryMap.i, collection);
            }

            public Object[] toArray() {
                return Lists.newArrayList(iterator()).toArray();
            }

            public <T> T[] toArray(T[] tArr) {
                return Lists.newArrayList(iterator()).toArray(tArr);
            }
        }

        public FilteredEntryMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map, predicate);
            this.j = Sets.filter(map.entrySet(), this.i);
        }

        public static <K, V> boolean c(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry next = it.next();
                if (predicate.apply(next) && collection.contains(next.getKey())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public static <K, V> boolean d(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry next = it.next();
                if (predicate.apply(next) && !collection.contains(next.getKey())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public final Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet();
        }

        public Set<K> createKeySet() {
            return new KeySet();
        }
    }

    public static class FilteredEntryNavigableMap<K, V> extends AbstractNavigableMap<K, V> {
        public final NavigableMap<K, V> c;
        public final Predicate<? super Map.Entry<K, V>> f;
        public final Map<K, V> g;

        public FilteredEntryNavigableMap(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
            this.c = (NavigableMap) Preconditions.checkNotNull(navigableMap);
            this.f = predicate;
            this.g = new FilteredEntryMap(navigableMap, predicate);
        }

        public final Iterator<Map.Entry<K, V>> a() {
            return Iterators.filter(this.c.entrySet().iterator(), this.f);
        }

        public final Iterator<Map.Entry<K, V>> b() {
            return Iterators.filter(this.c.descendingMap().entrySet().iterator(), this.f);
        }

        public void clear() {
            ((AbstractMap) this.g).clear();
        }

        public Comparator<? super K> comparator() {
            return this.c.comparator();
        }

        public boolean containsKey(Object obj) {
            return this.g.containsKey(obj);
        }

        public NavigableMap<K, V> descendingMap() {
            return Maps.filterEntries(this.c.descendingMap(), this.f);
        }

        public Set<Map.Entry<K, V>> entrySet() {
            return this.g.entrySet();
        }

        public V get(Object obj) {
            return this.g.get(obj);
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.filterEntries(this.c.headMap(k, z), this.f);
        }

        public boolean isEmpty() {
            return !Iterables.any(this.c.entrySet(), this.f);
        }

        public NavigableSet<K> navigableKeySet() {
            return new NavigableKeySet<K, V>(this) {
                public boolean removeAll(Collection<?> collection) {
                    FilteredEntryNavigableMap filteredEntryNavigableMap = FilteredEntryNavigableMap.this;
                    return FilteredEntryMap.c(filteredEntryNavigableMap.c, filteredEntryNavigableMap.f, collection);
                }

                public boolean retainAll(Collection<?> collection) {
                    FilteredEntryNavigableMap filteredEntryNavigableMap = FilteredEntryNavigableMap.this;
                    return FilteredEntryMap.d(filteredEntryNavigableMap.c, filteredEntryNavigableMap.f, collection);
                }
            };
        }

        public Map.Entry<K, V> pollFirstEntry() {
            return (Map.Entry) Iterables.a(this.c.entrySet(), this.f);
        }

        public Map.Entry<K, V> pollLastEntry() {
            return (Map.Entry) Iterables.a(this.c.descendingMap().entrySet(), this.f);
        }

        public V put(K k, V v) {
            return this.g.put(k, v);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            this.g.putAll(map);
        }

        public V remove(Object obj) {
            return this.g.remove(obj);
        }

        public int size() {
            return ((AbstractMap) this.g).size();
        }

        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
            return Maps.filterEntries(this.c.subMap(k, z, k2, z2), this.f);
        }

        public NavigableMap<K, V> tailMap(K k, boolean z) {
            return Maps.filterEntries(this.c.tailMap(k, z), this.f);
        }

        public Collection<V> values() {
            return new FilteredMapValues(this, this.c, this.f);
        }
    }

    public static class FilteredEntrySortedMap<K, V> extends FilteredEntryMap<K, V> implements SortedMap<K, V> {

        public class SortedKeySet extends FilteredEntryMap<K, V>.KeySet implements SortedSet<K> {
            public SortedKeySet() {
                super();
            }

            public Comparator<? super K> comparator() {
                return ((SortedMap) FilteredEntrySortedMap.this.h).comparator();
            }

            public K first() {
                return FilteredEntrySortedMap.this.firstKey();
            }

            public SortedSet<K> headSet(K k) {
                return (SortedSet) FilteredEntrySortedMap.this.headMap(k).keySet();
            }

            public K last() {
                return FilteredEntrySortedMap.this.lastKey();
            }

            public SortedSet<K> subSet(K k, K k2) {
                return (SortedSet) FilteredEntrySortedMap.this.subMap(k, k2).keySet();
            }

            public SortedSet<K> tailSet(K k) {
                return (SortedSet) FilteredEntrySortedMap.this.tailMap(k).keySet();
            }
        }

        public FilteredEntrySortedMap(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(sortedMap, predicate);
        }

        public Comparator<? super K> comparator() {
            return ((SortedMap) this.h).comparator();
        }

        public final Set createKeySet() {
            return new SortedKeySet();
        }

        public K firstKey() {
            return keySet().iterator().next();
        }

        public SortedMap<K, V> headMap(K k) {
            return new FilteredEntrySortedMap(((SortedMap) this.h).headMap(k), this.i);
        }

        public K lastKey() {
            Map<K, V> map = this.h;
            SortedMap sortedMap = (SortedMap) map;
            while (true) {
                K lastKey = sortedMap.lastKey();
                if (b(lastKey, map.get(lastKey))) {
                    return lastKey;
                }
                sortedMap = ((SortedMap) map).headMap(lastKey);
            }
        }

        public SortedMap<K, V> subMap(K k, K k2) {
            return new FilteredEntrySortedMap(((SortedMap) this.h).subMap(k, k2), this.i);
        }

        public SortedMap<K, V> tailMap(K k) {
            return new FilteredEntrySortedMap(((SortedMap) this.h).tailMap(k), this.i);
        }

        public SortedSet<K> keySet() {
            return (SortedSet) super.keySet();
        }
    }

    public static class FilteredKeyMap<K, V> extends AbstractFilteredMap<K, V> {
        public final Predicate<? super K> j;

        public FilteredKeyMap(Map<K, V> map, Predicate<? super K> predicate, Predicate<? super Map.Entry<K, V>> predicate2) {
            super(map, predicate2);
            this.j = predicate;
        }

        public boolean containsKey(Object obj) {
            return this.h.containsKey(obj) && this.j.apply(obj);
        }

        public final Set<Map.Entry<K, V>> createEntrySet() {
            return Sets.filter(this.h.entrySet(), this.i);
        }

        public final Set<K> createKeySet() {
            return Sets.filter(this.h.keySet(), this.j);
        }
    }

    public static final class FilteredMapValues<K, V> extends Values<K, V> {
        public final Map<K, V> f;
        public final Predicate<? super Map.Entry<K, V>> g;

        public FilteredMapValues(Map<K, V> map, Map<K, V> map2, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map);
            this.f = map2;
            this.g = predicate;
        }

        public boolean remove(Object obj) {
            Iterator<Map.Entry<K, V>> it = this.f.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry next = it.next();
                if (this.g.apply(next) && Objects.equal(next.getValue(), obj)) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }

        public boolean removeAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = this.f.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry next = it.next();
                if (this.g.apply(next) && collection.contains(next.getValue())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> it = this.f.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry next = it.next();
                if (this.g.apply(next) && !collection.contains(next.getValue())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        public <T> T[] toArray(T[] tArr) {
            return Lists.newArrayList(iterator()).toArray(tArr);
        }
    }

    public static abstract class IteratorBasedAbstractMap<K, V> extends AbstractMap<K, V> {
        public abstract Iterator<Map.Entry<K, V>> a();

        public void clear() {
            Iterators.b(a());
        }

        public Set<Map.Entry<K, V>> entrySet() {
            return new EntrySet<K, V>() {
                public final Map<K, V> a() {
                    return IteratorBasedAbstractMap.this;
                }

                public Iterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedAbstractMap.this.a();
                }
            };
        }

        public abstract int size();
    }

    public static class KeySet<K, V> extends Sets.ImprovedAbstractSet<K> {
        public final Map<K, V> c;

        public KeySet(Map<K, V> map) {
            this.c = (Map) Preconditions.checkNotNull(map);
        }

        public Map<K, V> a() {
            return this.c;
        }

        public void clear() {
            a().clear();
        }

        public boolean contains(Object obj) {
            return a().containsKey(obj);
        }

        public boolean isEmpty() {
            return a().isEmpty();
        }

        public Iterator<K> iterator() {
            return new TransformedIterator<Map.Entry<Object, Object>, Object>(a().entrySet().iterator()) {
                public final Object a(Object obj) {
                    return ((Map.Entry) obj).getKey();
                }
            };
        }

        public boolean remove(Object obj) {
            if (!contains(obj)) {
                return false;
            }
            a().remove(obj);
            return true;
        }

        public int size() {
            return a().size();
        }
    }

    public static class MapDifferenceImpl<K, V> implements MapDifference<K, V> {
        public final Map<K, V> a;
        public final Map<K, V> b;
        public final Map<K, V> c;
        public final Map<K, MapDifference.ValueDifference<V>> d;

        public MapDifferenceImpl(AbstractMap abstractMap, AbstractMap abstractMap2, AbstractMap abstractMap3, AbstractMap abstractMap4) {
            this.a = Maps.a(abstractMap);
            this.b = Maps.a(abstractMap2);
            this.c = Maps.a(abstractMap3);
            this.d = Maps.a(abstractMap4);
        }

        public boolean areEqual() {
            return this.a.isEmpty() && this.b.isEmpty() && this.d.isEmpty();
        }

        public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return this.d;
        }

        public Map<K, V> entriesInCommon() {
            return this.c;
        }

        public Map<K, V> entriesOnlyOnLeft() {
            return this.a;
        }

        public Map<K, V> entriesOnlyOnRight() {
            return this.b;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof MapDifference)) {
                return false;
            }
            MapDifference mapDifference = (MapDifference) obj;
            if (!entriesOnlyOnLeft().equals(mapDifference.entriesOnlyOnLeft()) || !entriesOnlyOnRight().equals(mapDifference.entriesOnlyOnRight()) || !entriesInCommon().equals(mapDifference.entriesInCommon()) || !entriesDiffering().equals(mapDifference.entriesDiffering())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering());
        }

        public String toString() {
            if (areEqual()) {
                return "equal";
            }
            StringBuilder sb = new StringBuilder("not equal");
            Map<K, V> map = this.a;
            if (!map.isEmpty()) {
                sb.append(": only on left=");
                sb.append(map);
            }
            Map<K, V> map2 = this.b;
            if (!map2.isEmpty()) {
                sb.append(": only on right=");
                sb.append(map2);
            }
            Map<K, MapDifference.ValueDifference<V>> map3 = this.d;
            if (!map3.isEmpty()) {
                sb.append(": value differences=");
                sb.append(map3);
            }
            return sb.toString();
        }
    }

    public static final class NavigableAsMapView<K, V> extends AbstractNavigableMap<K, V> {
        public final NavigableSet<K> c;
        public final Function<? super K, V> f;

        public NavigableAsMapView(NavigableSet<K> navigableSet, Function<? super K, V> function) {
            this.c = (NavigableSet) Preconditions.checkNotNull(navigableSet);
            this.f = (Function) Preconditions.checkNotNull(function);
        }

        public final Iterator<Map.Entry<K, V>> a() {
            return new TransformedIterator<Object, Map.Entry<Object, Object>>(this.c.iterator(), this.f) {
                public final /* synthetic */ Function f;

                {
                    this.f = r2;
                }

                public final Object a(Object obj) {
                    return Maps.immutableEntry(obj, this.f.apply(obj));
                }
            };
        }

        public final Iterator<Map.Entry<K, V>> b() {
            return descendingMap().entrySet().iterator();
        }

        public void clear() {
            this.c.clear();
        }

        public Comparator<? super K> comparator() {
            return this.c.comparator();
        }

        public NavigableMap<K, V> descendingMap() {
            return Maps.asMap(this.c.descendingSet(), this.f);
        }

        public V get(Object obj) {
            if (Collections2.d(obj, this.c)) {
                return this.f.apply(obj);
            }
            return null;
        }

        public NavigableMap<K, V> headMap(K k, boolean z) {
            return Maps.asMap(this.c.headSet(k, z), this.f);
        }

        public NavigableSet<K> navigableKeySet() {
            return new ForwardingNavigableSet<Object>(this.c) {
                public final /* synthetic */ NavigableSet c;

                {
                    this.c = r1;
                }

                public final Collection a() {
                    return this.c;
                }

                public boolean add(Object obj) {
                    throw new UnsupportedOperationException();
                }

                public boolean addAll(Collection<Object> collection) {
                    throw new UnsupportedOperationException();
                }

                public final Object delegate() {
                    return this.c;
                }

                public NavigableSet<Object> descendingSet() {
                    return 

                    public static class SortedAsMapView<K, V> extends AsMapView<K, V> implements SortedMap<K, V> {
                        public SortedAsMapView(SortedSet<K> sortedSet, Function<? super K, V> function) {
                            super(sortedSet, function);
                        }

                        public final Set b() {
                            return (SortedSet) this.h;
                        }

                        public Comparator<? super K> comparator() {
                            return ((SortedSet) this.h).comparator();
                        }

                        public K firstKey() {
                            return ((SortedSet) this.h).first();
                        }

                        public SortedMap<K, V> headMap(K k) {
                            return Maps.asMap(((SortedSet) this.h).headSet(k), this.i);
                        }

                        public Set<K> keySet() {
                            return new ForwardingSortedSet<Object>((SortedSet) this.h) {
                                public final /* synthetic */ SortedSet c;

                                {
                                    this.c = r1;
                                }

                                public final Collection a() {
                                    return this.c;
                                }

                                public boolean add(Object obj) {
                                    throw new UnsupportedOperationException();
                                }

                                public boolean addAll(Collection<Object> collection) {
                                    throw new UnsupportedOperationException();
                                }

                                public final Object delegate() {
                                    return this.c;
                                }

                                public final Set g() {
                                    return this.c;
                                }

                                public SortedSet<Object> headSet(Object obj) {
                                    return 

                                    public static class SortedKeySet<K, V> extends KeySet<K, V> implements SortedSet<K> {
                                        public SortedKeySet(SortedMap<K, V> sortedMap) {
                                            super(sortedMap);
                                        }

                                        /* renamed from: b */
                                        public SortedMap<K, V> a() {
                                            return (SortedMap) this.c;
                                        }

                                        public Comparator<? super K> comparator() {
                                            return a().comparator();
                                        }

                                        public K first() {
                                            return a().firstKey();
                                        }

                                        public SortedSet<K> headSet(K k) {
                                            return new SortedKeySet(a().headMap(k));
                                        }

                                        public K last() {
                                            return a().lastKey();
                                        }

                                        public SortedSet<K> subSet(K k, K k2) {
                                            return new SortedKeySet(a().subMap(k, k2));
                                        }

                                        public SortedSet<K> tailSet(K k) {
                                            return new SortedKeySet(a().tailMap(k));
                                        }
                                    }

                                    public static class SortedMapDifferenceImpl<K, V> extends MapDifferenceImpl<K, V> implements SortedMapDifference<K, V> {
                                        public SortedMapDifferenceImpl(TreeMap treeMap, TreeMap treeMap2, TreeMap treeMap3, TreeMap treeMap4) {
                                            super(treeMap, treeMap2, treeMap3, treeMap4);
                                        }

                                        public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
                                            return (SortedMap) super.entriesDiffering();
                                        }

                                        public SortedMap<K, V> entriesInCommon() {
                                            return (SortedMap) super.entriesInCommon();
                                        }

                                        public SortedMap<K, V> entriesOnlyOnLeft() {
                                            return (SortedMap) super.entriesOnlyOnLeft();
                                        }

                                        public SortedMap<K, V> entriesOnlyOnRight() {
                                            return (SortedMap) super.entriesOnlyOnRight();
                                        }
                                    }

                                    public static class TransformedEntriesMap<K, V1, V2> extends IteratorBasedAbstractMap<K, V2> {
                                        public final Map<K, V1> c;
                                        public final EntryTransformer<? super K, ? super V1, V2> f;

                                        public TransformedEntriesMap(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
                                            this.c = (Map) Preconditions.checkNotNull(map);
                                            this.f = (EntryTransformer) Preconditions.checkNotNull(entryTransformer);
                                        }

                                        public final Iterator<Map.Entry<K, V2>> a() {
                                            Iterator<Map.Entry<K, V1>> it = this.c.entrySet().iterator();
                                            EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.f;
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

                                        public void clear() {
                                            this.c.clear();
                                        }

                                        public boolean containsKey(Object obj) {
                                            return this.c.containsKey(obj);
                                        }

                                        public V2 get(Object obj) {
                                            Map<K, V1> map = this.c;
                                            V1 v1 = map.get(obj);
                                            if (v1 != null || map.containsKey(obj)) {
                                                return this.f.transformEntry(obj, v1);
                                            }
                                            return null;
                                        }

                                        public Set<K> keySet() {
                                            return this.c.keySet();
                                        }

                                        public V2 remove(Object obj) {
                                            Map<K, V1> map = this.c;
                                            if (map.containsKey(obj)) {
                                                return this.f.transformEntry(obj, map.remove(obj));
                                            }
                                            return null;
                                        }

                                        public int size() {
                                            return this.c.size();
                                        }

                                        public Collection<V2> values() {
                                            return new Values(this);
                                        }
                                    }

                                    public static class TransformedEntriesNavigableMap<K, V1, V2> extends TransformedEntriesSortedMap<K, V1, V2> implements NavigableMap<K, V2> {
                                        public TransformedEntriesNavigableMap(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
                                            super(navigableMap, entryTransformer);
                                        }

                                        /* renamed from: c */
                                        public final NavigableMap<K, V1> b() {
                                            return (NavigableMap) ((SortedMap) this.c);
                                        }

                                        public Map.Entry<K, V2> ceilingEntry(K k) {
                                            return d(b().ceilingEntry(k));
                                        }

                                        public K ceilingKey(K k) {
                                            return b().ceilingKey(k);
                                        }

                                        public final Map.Entry<K, V2> d(Map.Entry<K, V1> entry) {
                                            if (entry == null) {
                                                return null;
                                            }
                                            EntryTransformer<? super K, ? super V1, V2> entryTransformer = this.f;
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

                                        public NavigableSet<K> descendingKeySet() {
                                            return b().descendingKeySet();
                                        }

                                        public NavigableMap<K, V2> descendingMap() {
                                            return Maps.transformEntries(b().descendingMap(), this.f);
                                        }

                                        public Map.Entry<K, V2> firstEntry() {
                                            return d(b().firstEntry());
                                        }

                                        public Map.Entry<K, V2> floorEntry(K k) {
                                            return d(b().floorEntry(k));
                                        }

                                        public K floorKey(K k) {
                                            return b().floorKey(k);
                                        }

                                        public Map.Entry<K, V2> higherEntry(K k) {
                                            return d(b().higherEntry(k));
                                        }

                                        public K higherKey(K k) {
                                            return b().higherKey(k);
                                        }

                                        public Map.Entry<K, V2> lastEntry() {
                                            return d(b().lastEntry());
                                        }

                                        public Map.Entry<K, V2> lowerEntry(K k) {
                                            return d(b().lowerEntry(k));
                                        }

                                        public K lowerKey(K k) {
                                            return b().lowerKey(k);
                                        }

                                        public NavigableSet<K> navigableKeySet() {
                                            return b().navigableKeySet();
                                        }

                                        public Map.Entry<K, V2> pollFirstEntry() {
                                            return d(b().pollFirstEntry());
                                        }

                                        public Map.Entry<K, V2> pollLastEntry() {
                                            return d(b().pollLastEntry());
                                        }

                                        public NavigableMap<K, V2> headMap(K k) {
                                            return headMap(k, false);
                                        }

                                        public NavigableMap<K, V2> subMap(K k, boolean z, K k2, boolean z2) {
                                            return Maps.transformEntries(b().subMap(k, z, k2, z2), this.f);
                                        }

                                        public NavigableMap<K, V2> tailMap(K k) {
                                            return tailMap(k, true);
                                        }

                                        public NavigableMap<K, V2> headMap(K k, boolean z) {
                                            return Maps.transformEntries(b().headMap(k, z), this.f);
                                        }

                                        public NavigableMap<K, V2> tailMap(K k, boolean z) {
                                            return Maps.transformEntries(b().tailMap(k, z), this.f);
                                        }

                                        public NavigableMap<K, V2> subMap(K k, K k2) {
                                            return subMap(k, true, k2, false);
                                        }
                                    }

                                    public static class TransformedEntriesSortedMap<K, V1, V2> extends TransformedEntriesMap<K, V1, V2> implements SortedMap<K, V2> {
                                        public TransformedEntriesSortedMap(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
                                            super(sortedMap, entryTransformer);
                                        }

                                        public SortedMap<K, V1> b() {
                                            return (SortedMap) this.c;
                                        }

                                        public Comparator<? super K> comparator() {
                                            return b().comparator();
                                        }

                                        public K firstKey() {
                                            return b().firstKey();
                                        }

                                        public SortedMap<K, V2> headMap(K k) {
                                            return Maps.transformEntries(b().headMap(k), this.f);
                                        }

                                        public K lastKey() {
                                            return b().lastKey();
                                        }

                                        public SortedMap<K, V2> subMap(K k, K k2) {
                                            return Maps.transformEntries(b().subMap(k, k2), this.f);
                                        }

                                        public SortedMap<K, V2> tailMap(K k) {
                                            return Maps.transformEntries(b().tailMap(k), this.f);
                                        }
                                    }

                                    public static class UnmodifiableBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
                                        private static final long serialVersionUID = 0;
                                        public final Map<K, V> c;
                                        public final BiMap<? extends K, ? extends V> f;
                                        public BiMap<V, K> g;
                                        public transient Set<V> h;

                                        public UnmodifiableBiMap(BiMap<? extends K, ? extends V> biMap, BiMap<V, K> biMap2) {
                                            this.c = Collections.unmodifiableMap(biMap);
                                            this.f = biMap;
                                            this.g = biMap2;
                                        }

                                        public final Map<K, V> a() {
                                            return this.c;
                                        }

                                        public final Object delegate() {
                                            return this.c;
                                        }

                                        public V forcePut(K k, V v) {
                                            throw new UnsupportedOperationException();
                                        }

                                        public BiMap<V, K> inverse() {
                                            BiMap<V, K> biMap = this.g;
                                            if (biMap != null) {
                                                return biMap;
                                            }
                                            UnmodifiableBiMap unmodifiableBiMap = new UnmodifiableBiMap(this.f.inverse(), this);
                                            this.g = unmodifiableBiMap;
                                            return unmodifiableBiMap;
                                        }

                                        public Set<V> values() {
                                            Set<V> set = this.h;
                                            if (set != null) {
                                                return set;
                                            }
                                            Set<V> unmodifiableSet = Collections.unmodifiableSet(this.f.values());
                                            this.h = unmodifiableSet;
                                            return unmodifiableSet;
                                        }
                                    }

                                    public static class UnmodifiableEntries<K, V> extends ForwardingCollection<Map.Entry<K, V>> {
                                        public final Collection<Map.Entry<K, V>> c;

                                        public UnmodifiableEntries(Collection<Map.Entry<K, V>> collection) {
                                            this.c = collection;
                                        }

                                        public final Collection<Map.Entry<K, V>> a() {
                                            return this.c;
                                        }

                                        public final Object delegate() {
                                            return this.c;
                                        }

                                        public Iterator<Map.Entry<K, V>> iterator() {
                                            return new UnmodifiableIterator<Map.Entry<Object, Object>>(this.c.iterator()) {
                                                public final /* synthetic */ Iterator c;

                                                {
                                                    this.c = r1;
                                                }

                                                public boolean hasNext() {
                                                    return this.c.hasNext();
                                                }

                                                public Map.Entry<Object, Object> next() {
                                                    Map.Entry entry = (Map.Entry) this.c.next();
                                                    Preconditions.checkNotNull(entry);
                                                    return new AbstractMapEntry<Object, Object>(entry) {
                                                        public Object getKey() {
                                                            return r1.getKey();
                                                        }

                                                        public Object getValue() {
                                                            return r1.getValue();
                                                        }
                                                    };
                                                }
                                            };
                                        }

                                        public <T> T[] toArray(T[] tArr) {
                                            return ObjectArrays.c(this, tArr);
                                        }

                                        public Object[] toArray() {
                                            return c();
                                        }
                                    }

                                    public static class UnmodifiableEntrySet<K, V> extends UnmodifiableEntries<K, V> implements Set<Map.Entry<K, V>> {
                                        public UnmodifiableEntrySet(Set<Map.Entry<K, V>> set) {
                                            super(set);
                                        }

                                        public boolean equals(Object obj) {
                                            return Sets.a(this, obj);
                                        }

                                        public int hashCode() {
                                            return Sets.b(this);
                                        }
                                    }

                                    public static class UnmodifiableNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V>, Serializable {
                                        public final NavigableMap<K, ? extends V> c;
                                        public transient UnmodifiableNavigableMap<K, V> f;

                                        public UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
                                            this.c = navigableMap;
                                        }

                                        /* renamed from: c */
                                        public final SortedMap<K, V> delegate() {
                                            return Collections.unmodifiableSortedMap(this.c);
                                        }

                                        public Map.Entry<K, V> ceilingEntry(K k) {
                                            return Maps.b(this.c.ceilingEntry(k));
                                        }

                                        public K ceilingKey(K k) {
                                            return this.c.ceilingKey(k);
                                        }

                                        public NavigableSet<K> descendingKeySet() {
                                            return Sets.unmodifiableNavigableSet(this.c.descendingKeySet());
                                        }

                                        public NavigableMap<K, V> descendingMap() {
                                            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap = this.f;
                                            if (unmodifiableNavigableMap != null) {
                                                return unmodifiableNavigableMap;
                                            }
                                            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap2 = new UnmodifiableNavigableMap<>(this.c.descendingMap(), this);
                                            this.f = unmodifiableNavigableMap2;
                                            return unmodifiableNavigableMap2;
                                        }

                                        public Map.Entry<K, V> firstEntry() {
                                            return Maps.b(this.c.firstEntry());
                                        }

                                        public Map.Entry<K, V> floorEntry(K k) {
                                            return Maps.b(this.c.floorEntry(k));
                                        }

                                        public K floorKey(K k) {
                                            return this.c.floorKey(k);
                                        }

                                        public SortedMap<K, V> headMap(K k) {
                                            return headMap(k, false);
                                        }

                                        public Map.Entry<K, V> higherEntry(K k) {
                                            return Maps.b(this.c.higherEntry(k));
                                        }

                                        public K higherKey(K k) {
                                            return this.c.higherKey(k);
                                        }

                                        public Set<K> keySet() {
                                            return navigableKeySet();
                                        }

                                        public Map.Entry<K, V> lastEntry() {
                                            return Maps.b(this.c.lastEntry());
                                        }

                                        public Map.Entry<K, V> lowerEntry(K k) {
                                            return Maps.b(this.c.lowerEntry(k));
                                        }

                                        public K lowerKey(K k) {
                                            return this.c.lowerKey(k);
                                        }

                                        public NavigableSet<K> navigableKeySet() {
                                            return Sets.unmodifiableNavigableSet(this.c.navigableKeySet());
                                        }

                                        public final Map.Entry<K, V> pollFirstEntry() {
                                            throw new UnsupportedOperationException();
                                        }

                                        public final Map.Entry<K, V> pollLastEntry() {
                                            throw new UnsupportedOperationException();
                                        }

                                        public SortedMap<K, V> subMap(K k, K k2) {
                                            return subMap(k, true, k2, false);
                                        }

                                        public SortedMap<K, V> tailMap(K k) {
                                            return tailMap(k, true);
                                        }

                                        public NavigableMap<K, V> headMap(K k, boolean z) {
                                            return Maps.unmodifiableNavigableMap(this.c.headMap(k, z));
                                        }

                                        public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
                                            return Maps.unmodifiableNavigableMap(this.c.subMap(k, z, k2, z2));
                                        }

                                        public NavigableMap<K, V> tailMap(K k, boolean z) {
                                            return Maps.unmodifiableNavigableMap(this.c.tailMap(k, z));
                                        }

                                        public UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap, UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap) {
                                            this.c = navigableMap;
                                            this.f = unmodifiableNavigableMap;
                                        }
                                    }

                                    public static class ValueDifferenceImpl<V> implements MapDifference.ValueDifference<V> {
                                        public final V a;
                                        public final V b;

                                        public ValueDifferenceImpl(V v, V v2) {
                                            this.a = v;
                                            this.b = v2;
                                        }

                                        public boolean equals(Object obj) {
                                            if (!(obj instanceof MapDifference.ValueDifference)) {
                                                return false;
                                            }
                                            MapDifference.ValueDifference valueDifference = (MapDifference.ValueDifference) obj;
                                            if (!Objects.equal(this.a, valueDifference.leftValue()) || !Objects.equal(this.b, valueDifference.rightValue())) {
                                                return false;
                                            }
                                            return true;
                                        }

                                        public int hashCode() {
                                            return Objects.hashCode(this.a, this.b);
                                        }

                                        public V leftValue() {
                                            return this.a;
                                        }

                                        public V rightValue() {
                                            return this.b;
                                        }

                                        public String toString() {
                                            return "(" + this.a + ", " + this.b + ")";
                                        }
                                    }

                                    public static class Values<K, V> extends AbstractCollection<V> {
                                        public final Map<K, V> c;

                                        public Values(Map<K, V> map) {
                                            this.c = (Map) Preconditions.checkNotNull(map);
                                        }

                                        public void clear() {
                                            this.c.clear();
                                        }

                                        public boolean contains(Object obj) {
                                            return this.c.containsValue(obj);
                                        }

                                        public boolean isEmpty() {
                                            return this.c.isEmpty();
                                        }

                                        public Iterator<V> iterator() {
                                            return new TransformedIterator<Map.Entry<Object, Object>, Object>(this.c.entrySet().iterator()) {
                                                public final Object a(Object obj) {
                                                    return ((Map.Entry) obj).getValue();
                                                }
                                            };
                                        }

                                        public boolean remove(Object obj) {
                                            try {
                                                return super.remove(obj);
                                            } catch (UnsupportedOperationException unused) {
                                                Map<K, V> map = this.c;
                                                for (Map.Entry next : map.entrySet()) {
                                                    if (Objects.equal(obj, next.getValue())) {
                                                        map.remove(next.getKey());
                                                        return true;
                                                    }
                                                }
                                                return false;
                                            }
                                        }

                                        public boolean removeAll(Collection<?> collection) {
                                            try {
                                                return super.removeAll((Collection) Preconditions.checkNotNull(collection));
                                            } catch (UnsupportedOperationException unused) {
                                                HashSet newHashSet = Sets.newHashSet();
                                                Map<K, V> map = this.c;
                                                for (Map.Entry next : map.entrySet()) {
                                                    if (collection.contains(next.getValue())) {
                                                        newHashSet.add(next.getKey());
                                                    }
                                                }
                                                return map.keySet().removeAll(newHashSet);
                                            }
                                        }

                                        public boolean retainAll(Collection<?> collection) {
                                            try {
                                                return super.retainAll((Collection) Preconditions.checkNotNull(collection));
                                            } catch (UnsupportedOperationException unused) {
                                                HashSet newHashSet = Sets.newHashSet();
                                                Map<K, V> map = this.c;
                                                for (Map.Entry next : map.entrySet()) {
                                                    if (collection.contains(next.getValue())) {
                                                        newHashSet.add(next.getKey());
                                                    }
                                                }
                                                return map.keySet().retainAll(newHashSet);
                                            }
                                        }

                                        public int size() {
                                            return this.c.size();
                                        }
                                    }

                                    public static abstract class ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {
                                        public transient Set<Map.Entry<K, V>> c;
                                        public transient Set<K> f;
                                        public transient Collection<V> g;

                                        public Collection<V> a() {
                                            return new Values(this);
                                        }

                                        public abstract Set<Map.Entry<K, V>> createEntrySet();

                                        public Set<K> createKeySet() {
                                            return new KeySet(this);
                                        }

                                        public Set<Map.Entry<K, V>> entrySet() {
                                            Set<Map.Entry<K, V>> set = this.c;
                                            if (set != null) {
                                                return set;
                                            }
                                            Set<Map.Entry<K, V>> createEntrySet = createEntrySet();
                                            this.c = createEntrySet;
                                            return createEntrySet;
                                        }

                                        public Set<K> keySet() {
                                            Set<K> set = this.f;
                                            if (set != null) {
                                                return set;
                                            }
                                            Set<K> createKeySet = createKeySet();
                                            this.f = createKeySet;
                                            return createKeySet;
                                        }

                                        public Collection<V> values() {
                                            Collection<V> collection = this.g;
                                            if (collection != null) {
                                                return collection;
                                            }
                                            Collection<V> a = a();
                                            this.g = a;
                                            return a;
                                        }
                                    }

                                    public static Map a(AbstractMap abstractMap) {
                                        if (abstractMap instanceof SortedMap) {
                                            return Collections.unmodifiableSortedMap((SortedMap) abstractMap);
                                        }
                                        return Collections.unmodifiableMap(abstractMap);
                                    }

                                    public static <A, B> Converter<A, B> asConverter(BiMap<A, B> biMap) {
                                        return new BiMapConverter(biMap);
                                    }

                                    public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
                                        return new AsMapView(set, function);
                                    }

                                    public static Map.Entry b(final Map.Entry entry) {
                                        if (entry == null) {
                                            return null;
                                        }
                                        Preconditions.checkNotNull(entry);
                                        return new AbstractMapEntry<Object, Object>() {
                                            public Object getKey() {
                                                return entry.getKey();
                                            }

                                            public Object getValue() {
                                                return entry.getValue();
                                            }
                                        };
                                    }

                                    public static int c(int i) {
                                        if (i < 3) {
                                            CollectPreconditions.b(i, "expectedSize");
                                            return i + 1;
                                        } else if (i < 1073741824) {
                                            return (int) ((((float) i) / 0.75f) + 1.0f);
                                        } else {
                                            return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                                        }
                                    }

                                    public static void d(Map map, Map map2, Equivalence equivalence, AbstractMap abstractMap, AbstractMap abstractMap2, AbstractMap abstractMap3, AbstractMap abstractMap4) {
                                        for (Map.Entry entry : map.entrySet()) {
                                            Object key = entry.getKey();
                                            Object value = entry.getValue();
                                            if (map2.containsKey(key)) {
                                                Object remove = abstractMap2.remove(key);
                                                if (equivalence.equivalent(value, remove)) {
                                                    abstractMap3.put(key, value);
                                                } else {
                                                    abstractMap4.put(key, new ValueDifferenceImpl(value, remove));
                                                }
                                            } else {
                                                abstractMap.put(key, value);
                                            }
                                        }
                                    }

                                    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2) {
                                        if (map instanceof SortedMap) {
                                            return difference((SortedMap) map, map2);
                                        }
                                        return difference(map, map2, Equivalence.equals());
                                    }

                                    public static <E> ImmutableMap<E, Integer> e(Collection<E> collection) {
                                        ImmutableMap.Builder builder = new ImmutableMap.Builder(collection.size());
                                        int i = 0;
                                        for (E put : collection) {
                                            builder.put(put, Integer.valueOf(i));
                                            i++;
                                        }
                                        return builder.build();
                                    }

                                    public static <K> K f(Map.Entry<K, ?> entry) {
                                        if (entry == null) {
                                            return null;
                                        }
                                        return entry.getKey();
                                    }

                                    public static <K, V> Map<K, V> filterEntries(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
                                        Preconditions.checkNotNull(predicate);
                                        if (!(map instanceof AbstractFilteredMap)) {
                                            return new FilteredEntryMap((Map) Preconditions.checkNotNull(map), predicate);
                                        }
                                        AbstractFilteredMap abstractFilteredMap = (AbstractFilteredMap) map;
                                        return new FilteredEntryMap(abstractFilteredMap.h, Predicates.and(abstractFilteredMap.i, predicate));
                                    }

                                    public static <K, V> Map<K, V> filterKeys(Map<K, V> map, Predicate<? super K> predicate) {
                                        Preconditions.checkNotNull(predicate);
                                        Predicate<Map.Entry<K, ?>> g = g(predicate);
                                        if (!(map instanceof AbstractFilteredMap)) {
                                            return new FilteredKeyMap((Map) Preconditions.checkNotNull(map), predicate, g);
                                        }
                                        AbstractFilteredMap abstractFilteredMap = (AbstractFilteredMap) map;
                                        return new FilteredEntryMap(abstractFilteredMap.h, Predicates.and(abstractFilteredMap.i, g));
                                    }

                                    public static <K, V> Map<K, V> filterValues(Map<K, V> map, Predicate<? super V> predicate) {
                                        return filterEntries(map, k(predicate));
                                    }

                                    public static ImmutableMap<String, String> fromProperties(Properties properties) {
                                        ImmutableMap.Builder builder = ImmutableMap.builder();
                                        Enumeration<?> propertyNames = properties.propertyNames();
                                        while (propertyNames.hasMoreElements()) {
                                            String str = (String) propertyNames.nextElement();
                                            builder.put(str, properties.getProperty(str));
                                        }
                                        return builder.build();
                                    }

                                    public static <K> Predicate<Map.Entry<K, ?>> g(Predicate<? super K> predicate) {
                                        return Predicates.compose(predicate, EntryFunction.c);
                                    }

                                    public static boolean h(Map<?, ?> map, Object obj) {
                                        Preconditions.checkNotNull(map);
                                        try {
                                            return map.containsKey(obj);
                                        } catch (ClassCastException | NullPointerException unused) {
                                            return false;
                                        }
                                    }

                                    public static <V> V i(Map<?, V> map, Object obj) {
                                        Preconditions.checkNotNull(map);
                                        try {
                                            return map.get(obj);
                                        } catch (ClassCastException | NullPointerException unused) {
                                            return null;
                                        }
                                    }

                                    public static <K, V> Map.Entry<K, V> immutableEntry(K k, V v) {
                                        return new ImmutableEntry(k, v);
                                    }

                                    public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> map) {
                                        if (map instanceof ImmutableEnumMap) {
                                            return (ImmutableEnumMap) map;
                                        }
                                        Iterator<Map.Entry<K, ? extends V>> it = map.entrySet().iterator();
                                        if (!it.hasNext()) {
                                            return ImmutableMap.of();
                                        }
                                        Map.Entry next = it.next();
                                        Enum enumR = (Enum) next.getKey();
                                        Object value = next.getValue();
                                        CollectPreconditions.a(enumR, value);
                                        EnumMap enumMap = new EnumMap(enumR.getDeclaringClass());
                                        enumMap.put(enumR, value);
                                        while (it.hasNext()) {
                                            Map.Entry next2 = it.next();
                                            Enum enumR2 = (Enum) next2.getKey();
                                            Object value2 = next2.getValue();
                                            CollectPreconditions.a(enumR2, value2);
                                            enumMap.put(enumR2, value2);
                                        }
                                        int size = enumMap.size();
                                        if (size == 0) {
                                            return ImmutableMap.of();
                                        }
                                        if (size != 1) {
                                            return new ImmutableEnumMap(enumMap);
                                        }
                                        Map.Entry entry = (Map.Entry) Iterables.getOnlyElement(enumMap.entrySet());
                                        return ImmutableMap.of(entry.getKey(), entry.getValue());
                                    }

                                    public static String j(Map<?, ?> map) {
                                        int size = map.size();
                                        CollectPreconditions.b(size, "size");
                                        StringBuilder sb = new StringBuilder((int) Math.min(((long) size) * 8, 1073741824));
                                        sb.append('{');
                                        boolean z = true;
                                        for (Map.Entry next : map.entrySet()) {
                                            if (!z) {
                                                sb.append(", ");
                                            }
                                            sb.append(next.getKey());
                                            sb.append('=');
                                            sb.append(next.getValue());
                                            z = false;
                                        }
                                        sb.append('}');
                                        return sb.toString();
                                    }

                                    public static <V> Predicate<Map.Entry<?, V>> k(Predicate<? super V> predicate) {
                                        return Predicates.compose(predicate, EntryFunction.f);
                                    }

                                    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
                                        return new ConcurrentHashMap();
                                    }

                                    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> cls) {
                                        return new EnumMap<>((Class) Preconditions.checkNotNull(cls));
                                    }

                                    public static <K, V> HashMap<K, V> newHashMap() {
                                        return new HashMap<>();
                                    }

                                    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int i) {
                                        return new HashMap<>(c(i));
                                    }

                                    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
                                        return new IdentityHashMap<>();
                                    }

                                    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
                                        return new LinkedHashMap<>();
                                    }

                                    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int i) {
                                        return new LinkedHashMap<>(c(i));
                                    }

                                    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
                                        return new TreeMap<>();
                                    }

                                    public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> navigableMap, Range<K> range) {
                                        boolean z;
                                        boolean z2;
                                        boolean z3 = true;
                                        if (navigableMap.comparator() != null && navigableMap.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
                                            if (navigableMap.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0) {
                                                z2 = true;
                                            } else {
                                                z2 = false;
                                            }
                                            Preconditions.checkArgument(z2, "map is using a custom comparator which is inconsistent with the natural ordering.");
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
                                            return navigableMap.subMap(lowerEndpoint, z, upperEndpoint, z3);
                                        } else if (range.hasLowerBound()) {
                                            K lowerEndpoint2 = range.lowerEndpoint();
                                            if (range.lowerBoundType() != boundType) {
                                                z3 = false;
                                            }
                                            return navigableMap.tailMap(lowerEndpoint2, z3);
                                        } else if (!range.hasUpperBound()) {
                                            return (NavigableMap) Preconditions.checkNotNull(navigableMap);
                                        } else {
                                            K upperEndpoint2 = range.upperEndpoint();
                                            if (range.upperBoundType() != boundType) {
                                                z3 = false;
                                            }
                                            return navigableMap.headMap(upperEndpoint2, z3);
                                        }
                                    }

                                    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> biMap) {
                                        if ((biMap instanceof Synchronized.SynchronizedBiMap) || (biMap instanceof ImmutableBiMap)) {
                                            return biMap;
                                        }
                                        return new Synchronized.SynchronizedBiMap(biMap, (Object) null, (BiMap) null);
                                    }

                                    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
                                        return new Synchronized.SynchronizedNavigableMap(navigableMap, (Object) null);
                                    }

                                    public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> iterable, Function<? super K, V> function) {
                                        return toMap(iterable.iterator(), function);
                                    }

                                    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
                                        return new TransformedEntriesMap(map, entryTransformer);
                                    }

                                    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> map, final Function<? super V1, V2> function) {
                                        Preconditions.checkNotNull(function);
                                        return transformEntries(map, new EntryTransformer<Object, Object, Object>() {
                                            public Object transformEntry(Object obj, Object obj2) {
                                                return Function.this.apply(obj2);
                                            }
                                        });
                                    }

                                    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> iterable, Function<? super V, K> function) {
                                        return uniqueIndex(iterable.iterator(), function);
                                    }

                                    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> biMap) {
                                        return new UnmodifiableBiMap(biMap, (BiMap) null);
                                    }

                                    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
                                        Preconditions.checkNotNull(navigableMap);
                                        if (navigableMap instanceof UnmodifiableNavigableMap) {
                                            return navigableMap;
                                        }
                                        return new UnmodifiableNavigableMap(navigableMap);
                                    }

                                    public static class NavigableKeySet<K, V> extends SortedKeySet<K, V> implements NavigableSet<K> {
                                        public NavigableKeySet(NavigableMap<K, V> navigableMap) {
                                            super(navigableMap);
                                        }

                                        public final Map a() {
                                            return (NavigableMap) this.c;
                                        }

                                        public final SortedMap b() {
                                            return (NavigableMap) this.c;
                                        }

                                        public K ceiling(K k) {
                                            return ((NavigableMap) this.c).ceilingKey(k);
                                        }

                                        public Iterator<K> descendingIterator() {
                                            return descendingSet().iterator();
                                        }

                                        public NavigableSet<K> descendingSet() {
                                            return ((NavigableMap) this.c).descendingKeySet();
                                        }

                                        public K floor(K k) {
                                            return ((NavigableMap) this.c).floorKey(k);
                                        }

                                        public NavigableSet<K> headSet(K k, boolean z) {
                                            return ((NavigableMap) this.c).headMap(k, z).navigableKeySet();
                                        }

                                        public K higher(K k) {
                                            return ((NavigableMap) this.c).higherKey(k);
                                        }

                                        public K lower(K k) {
                                            return ((NavigableMap) this.c).lowerKey(k);
                                        }

                                        public K pollFirst() {
                                            return Maps.f(((NavigableMap) this.c).pollFirstEntry());
                                        }

                                        public K pollLast() {
                                            return Maps.f(((NavigableMap) this.c).pollLastEntry());
                                        }

                                        public NavigableSet<K> subSet(K k, boolean z, K k2, boolean z2) {
                                            return ((NavigableMap) this.c).subMap(k, z, k2, z2).navigableKeySet();
                                        }

                                        public NavigableSet<K> tailSet(K k, boolean z) {
                                            return ((NavigableMap) this.c).tailMap(k, z).navigableKeySet();
                                        }

                                        public SortedSet<K> headSet(K k) {
                                            return headSet(k, false);
                                        }

                                        public SortedSet<K> subSet(K k, K k2) {
                                            return subSet(k, true, k2, false);
                                        }

                                        public SortedSet<K> tailSet(K k) {
                                            return tailSet(k, true);
                                        }
                                    }

                                    public static <K, V> SortedMap<K, V> asMap(SortedSet<K> sortedSet, Function<? super K, V> function) {
                                        return new SortedAsMapView(sortedSet, function);
                                    }

                                    public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> sortedMap, Predicate<? super V> predicate) {
                                        return filterEntries(sortedMap, k(predicate));
                                    }

                                    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
                                        return new EnumMap<>(map);
                                    }

                                    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
                                        return new HashMap<>(map);
                                    }

                                    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
                                        return new LinkedHashMap<>(map);
                                    }

                                    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
                                        return new TreeMap<>(sortedMap);
                                    }

                                    public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> it, Function<? super K, V> function) {
                                        Preconditions.checkNotNull(function);
                                        LinkedHashMap newLinkedHashMap = newLinkedHashMap();
                                        while (it.hasNext()) {
                                            K next = it.next();
                                            newLinkedHashMap.put(next, function.apply(next));
                                        }
                                        return ImmutableMap.copyOf(newLinkedHashMap);
                                    }

                                    public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
                                        return new TransformedEntriesSortedMap(sortedMap, entryTransformer);
                                    }

                                    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> it, Function<? super V, K> function) {
                                        Preconditions.checkNotNull(function);
                                        ImmutableMap.Builder builder = ImmutableMap.builder();
                                        while (it.hasNext()) {
                                            V next = it.next();
                                            builder.put(function.apply(next), next);
                                        }
                                        try {
                                            return builder.build();
                                        } catch (IllegalArgumentException e) {
                                            throw new IllegalArgumentException(e.getMessage() + ". To index multiple values under a key, use Multimaps.index.");
                                        }
                                    }

                                    public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> navigableSet, Function<? super K, V> function) {
                                        return new NavigableAsMapView(navigableSet, function);
                                    }

                                    public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> navigableMap, Predicate<? super V> predicate) {
                                        return filterEntries(navigableMap, k(predicate));
                                    }

                                    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(Comparator<C> comparator) {
                                        return new TreeMap<>(comparator);
                                    }

                                    public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
                                        return new TransformedEntriesNavigableMap(navigableMap, entryTransformer);
                                    }

                                    public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> biMap, Predicate<? super V> predicate) {
                                        return filterEntries(biMap, k(predicate));
                                    }

                                    public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> navigableMap, final Function<? super V1, V2> function) {
                                        Preconditions.checkNotNull(function);
                                        return transformEntries(navigableMap, new EntryTransformer<Object, Object, Object>() {
                                            public Object transformEntry(Object obj, Object obj2) {
                                                return Function.this.apply(obj2);
                                            }
                                        });
                                    }

                                    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence) {
                                        Preconditions.checkNotNull(equivalence);
                                        LinkedHashMap newLinkedHashMap = newLinkedHashMap();
                                        LinkedHashMap linkedHashMap = new LinkedHashMap(map2);
                                        LinkedHashMap newLinkedHashMap2 = newLinkedHashMap();
                                        LinkedHashMap newLinkedHashMap3 = newLinkedHashMap();
                                        d(map, map2, equivalence, newLinkedHashMap, linkedHashMap, newLinkedHashMap2, newLinkedHashMap3);
                                        return new MapDifferenceImpl(newLinkedHashMap, linkedHashMap, newLinkedHashMap2, newLinkedHashMap3);
                                    }

                                    public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
                                        Preconditions.checkNotNull(predicate);
                                        if (!(sortedMap instanceof FilteredEntrySortedMap)) {
                                            return new FilteredEntrySortedMap((SortedMap) Preconditions.checkNotNull(sortedMap), predicate);
                                        }
                                        FilteredEntrySortedMap filteredEntrySortedMap = (FilteredEntrySortedMap) sortedMap;
                                        return new FilteredEntrySortedMap((SortedMap) filteredEntrySortedMap.h, Predicates.and(filteredEntrySortedMap.i, predicate));
                                    }

                                    public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> sortedMap, final Function<? super V1, V2> function) {
                                        Preconditions.checkNotNull(function);
                                        return transformEntries(sortedMap, new EntryTransformer<Object, Object, Object>() {
                                            public Object transformEntry(Object obj, Object obj2) {
                                                return Function.this.apply(obj2);
                                            }
                                        });
                                    }

                                    public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> sortedMap, Predicate<? super K> predicate) {
                                        return filterEntries(sortedMap, g(predicate));
                                    }

                                    public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> navigableMap, Predicate<? super K> predicate) {
                                        return filterEntries(navigableMap, g(predicate));
                                    }

                                    public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> biMap, Predicate<? super K> predicate) {
                                        Preconditions.checkNotNull(predicate);
                                        return filterEntries(biMap, g(predicate));
                                    }

                                    public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> sortedMap, Map<? extends K, ? extends V> map) {
                                        Preconditions.checkNotNull(sortedMap);
                                        Preconditions.checkNotNull(map);
                                        Comparator<? super K> comparator = sortedMap.comparator();
                                        if (comparator == null) {
                                            comparator = Ordering.natural();
                                        }
                                        TreeMap<K, V> newTreeMap = newTreeMap(comparator);
                                        TreeMap<K, V> newTreeMap2 = newTreeMap(comparator);
                                        newTreeMap2.putAll(map);
                                        TreeMap<K, V> newTreeMap3 = newTreeMap(comparator);
                                        TreeMap<K, V> newTreeMap4 = newTreeMap(comparator);
                                        d(sortedMap, map, Equivalence.equals(), newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
                                        return new SortedMapDifferenceImpl(newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
                                    }

                                    public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
                                        Preconditions.checkNotNull(predicate);
                                        if (!(navigableMap instanceof FilteredEntryNavigableMap)) {
                                            return new FilteredEntryNavigableMap((NavigableMap) Preconditions.checkNotNull(navigableMap), predicate);
                                        }
                                        FilteredEntryNavigableMap filteredEntryNavigableMap = (FilteredEntryNavigableMap) navigableMap;
                                        return new FilteredEntryNavigableMap(filteredEntryNavigableMap.c, Predicates.and(filteredEntryNavigableMap.f, predicate));
                                    }

                                    public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
                                        Preconditions.checkNotNull(biMap);
                                        Preconditions.checkNotNull(predicate);
                                        if (!(biMap instanceof FilteredEntryBiMap)) {
                                            return new FilteredEntryBiMap(biMap, predicate);
                                        }
                                        FilteredEntryBiMap filteredEntryBiMap = (FilteredEntryBiMap) biMap;
                                        return new FilteredEntryBiMap((BiMap) filteredEntryBiMap.h, Predicates.and(filteredEntryBiMap.i, predicate));
                                    }
                                }
