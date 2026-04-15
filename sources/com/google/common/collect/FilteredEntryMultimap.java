package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class FilteredEntryMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    public final Multimap<K, V> j;
    public final Predicate<? super Map.Entry<K, V>> k;

    public class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        public AsMap() {
        }

        public final Collection<Collection<V>> a() {
            return new Maps.Values<K, Collection<V>>() {
                public boolean remove(Object obj) {
                    if (!(obj instanceof Collection)) {
                        return false;
                    }
                    Collection collection = (Collection) obj;
                    AsMap asMap = AsMap.this;
                    Iterator<Map.Entry<K, Collection<V>>> it = FilteredEntryMultimap.this.j.asMap().entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry next = it.next();
                        Collection h = FilteredEntryMultimap.h((Collection) next.getValue(), new ValuePredicate(next.getKey()));
                        if (!h.isEmpty() && collection.equals(h)) {
                            if (h.size() == ((Collection) next.getValue()).size()) {
                                it.remove();
                                return true;
                            }
                            h.clear();
                            return true;
                        }
                    }
                    return false;
                }

                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.i(Maps.k(Predicates.in(collection)));
                }

                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.i(Maps.k(Predicates.not(Predicates.in(collection))));
                }
            };
        }

        public void clear() {
            FilteredEntryMultimap.this.clear();
        }

        public boolean containsKey(Object obj) {
            return get(obj) != null;
        }

        public final Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new Maps.EntrySet<K, Collection<V>>() {
                public final Map<K, Collection<V>> a() {
                    return AsMap.this;
                }

                public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                    return new AbstractIterator<Map.Entry<K, Collection<V>>>() {
                        public final Iterator<Map.Entry<K, Collection<V>>> g;

                        {
                            this.g = FilteredEntryMultimap.this.j.asMap().entrySet().iterator();
                        }

                        public final Object computeNext() {
                            Object key;
                            Collection h2;
                            do {
                                Iterator<Map.Entry<K, Collection<V>>> it = this.g;
                                if (it.hasNext()) {
                                    Map.Entry next = it.next();
                                    key = next.getKey();
                                    h2 = FilteredEntryMultimap.h((Collection) next.getValue(), new ValuePredicate(key));
                                } else {
                                    this.c = AbstractIterator.State.DONE;
                                    return null;
                                }
                            } while (h2.isEmpty());
                            return Maps.immutableEntry(key, h2);
                        }
                    };
                }

                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.i(Predicates.in(collection));
                }

                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.i(Predicates.not(Predicates.in(collection)));
                }

                public int size() {
                    return Iterators.size(iterator());
                }
            };
        }

        public final Set<K> createKeySet() {
            return new Maps.KeySet<K, Collection<V>>() {
                public boolean remove(Object obj) {
                    return AsMap.this.remove(obj) != null;
                }

                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.i(Maps.g(Predicates.in(collection)));
                }

                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMultimap.this.i(Maps.g(Predicates.not(Predicates.in(collection))));
                }
            };
        }

        public Collection<V> get(Object obj) {
            FilteredEntryMultimap filteredEntryMultimap = FilteredEntryMultimap.this;
            Collection collection = filteredEntryMultimap.j.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            Collection<V> h2 = FilteredEntryMultimap.h(collection, new ValuePredicate(obj));
            if (h2.isEmpty()) {
                return null;
            }
            return h2;
        }

        public Collection<V> remove(Object obj) {
            FilteredEntryMultimap filteredEntryMultimap = FilteredEntryMultimap.this;
            Collection collection = filteredEntryMultimap.j.asMap().get(obj);
            if (collection == null) {
                return null;
            }
            ArrayList newArrayList = Lists.newArrayList();
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (filteredEntryMultimap.k.apply(Maps.immutableEntry(obj, next))) {
                    it.remove();
                    newArrayList.add(next);
                }
            }
            if (newArrayList.isEmpty()) {
                return null;
            }
            if (filteredEntryMultimap.j instanceof SetMultimap) {
                return Collections.unmodifiableSet(Sets.newLinkedHashSet(newArrayList));
            }
            return Collections.unmodifiableList(newArrayList);
        }
    }

    public class Keys extends Multimaps.Keys<K, V> {
        public Keys() {
            super(FilteredEntryMultimap.this);
        }

        public Set<Multiset.Entry<K>> entrySet() {
            return new Multisets.EntrySet<K>() {
                public final Multiset<K> a() {
                    return Keys.this;
                }

                public Iterator<Multiset.Entry<K>> iterator() {
                    return Keys.this.f();
                }

                public boolean removeAll(Collection<?> collection) {
                    final Predicate<T> in = Predicates.in(collection);
                    return FilteredEntryMultimap.this.i(new Predicate<Map.Entry<Object, Collection<Object>>>() {
                        public boolean apply(Map.Entry<Object, Collection<Object>> entry) {
                            return Predicate.this.apply(Multisets.immutableEntry(entry.getKey(), entry.getValue().size()));
                        }
                    });
                }

                public boolean retainAll(Collection<?> collection) {
                    final Predicate<T> not = Predicates.not(Predicates.in(collection));
                    return FilteredEntryMultimap.this.i(new Predicate<Map.Entry<Object, Collection<Object>>>() {
                        public boolean apply(Map.Entry<Object, Collection<Object>> entry) {
                            return Predicate.this.apply(Multisets.immutableEntry(entry.getKey(), entry.getValue().size()));
                        }
                    });
                }

                public int size() {
                    return FilteredEntryMultimap.this.keySet().size();
                }
            };
        }

        public int remove(Object obj, int i) {
            CollectPreconditions.b(i, "occurrences");
            if (i == 0) {
                return count(obj);
            }
            FilteredEntryMultimap filteredEntryMultimap = FilteredEntryMultimap.this;
            Collection collection = filteredEntryMultimap.j.asMap().get(obj);
            int i2 = 0;
            if (collection == null) {
                return 0;
            }
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (filteredEntryMultimap.k.apply(Maps.immutableEntry(obj, it.next())) && (i2 = i2 + 1) <= i) {
                    it.remove();
                }
            }
            return i2;
        }
    }

    public final class ValuePredicate implements Predicate<V> {
        public final K c;

        public ValuePredicate(K k) {
            this.c = k;
        }

        public boolean apply(V v) {
            return FilteredEntryMultimap.this.k.apply(Maps.immutableEntry(this.c, v));
        }
    }

    public FilteredEntryMultimap(Multimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> predicate) {
        this.j = (Multimap) Preconditions.checkNotNull(multimap);
        this.k = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public static <E> Collection<E> h(Collection<E> collection, Predicate<? super E> predicate) {
        if (collection instanceof Set) {
            return Sets.filter((Set) collection, predicate);
        }
        return Collections2.filter(collection, predicate);
    }

    public final Map<K, Collection<V>> a() {
        return new AsMap();
    }

    public Collection<Map.Entry<K, V>> b() {
        return h(this.j.entries(), this.k);
    }

    public final Set<K> c() {
        return asMap().keySet();
    }

    public void clear() {
        entries().clear();
    }

    public boolean containsKey(Object obj) {
        return asMap().get(obj) != null;
    }

    public final Multiset<K> d() {
        return new Keys();
    }

    public final Collection<V> e() {
        return new FilteredMultimapValues(this);
    }

    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return this.k;
    }

    public final Iterator<Map.Entry<K, V>> f() {
        throw new AssertionError("should never be called");
    }

    public Collection<V> get(K k2) {
        return h(this.j.get(k2), new ValuePredicate(k2));
    }

    public final boolean i(Predicate<? super Map.Entry<K, Collection<V>>> predicate) {
        Iterator<Map.Entry<K, Collection<V>>> it = this.j.asMap().entrySet().iterator();
        boolean z = false;
        while (it.hasNext()) {
            Map.Entry next = it.next();
            Object key = next.getKey();
            Collection h = h((Collection) next.getValue(), new ValuePredicate(key));
            if (!h.isEmpty() && predicate.apply(Maps.immutableEntry(key, h))) {
                if (h.size() == ((Collection) next.getValue()).size()) {
                    it.remove();
                } else {
                    h.clear();
                }
                z = true;
            }
        }
        return z;
    }

    public Collection<V> removeAll(Object obj) {
        Object obj2;
        Object remove = asMap().remove(obj);
        if (this.j instanceof SetMultimap) {
            obj2 = Collections.emptySet();
        } else {
            obj2 = Collections.emptyList();
        }
        return (Collection) MoreObjects.firstNonNull(remove, obj2);
    }

    public int size() {
        return entries().size();
    }

    public Multimap<K, V> unfiltered() {
        return this.j;
    }
}
