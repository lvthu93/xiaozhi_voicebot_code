package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FilteredKeyMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
    public final Multimap<K, V> j;
    public final Predicate<? super K> k;

    public static class AddRejectingList<K, V> extends ForwardingList<V> {
        public final K c;

        public AddRejectingList(K k) {
            this.c = k;
        }

        public final Collection a() {
            return Collections.emptyList();
        }

        public boolean add(V v) {
            add(0, v);
            return true;
        }

        public boolean addAll(Collection<? extends V> collection) {
            addAll(0, collection);
            return true;
        }

        public final Object delegate() {
            return Collections.emptyList();
        }

        public final List<V> g() {
            return Collections.emptyList();
        }

        public void add(int i, V v) {
            Preconditions.checkPositionIndex(i, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.c);
        }

        public boolean addAll(int i, Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            Preconditions.checkPositionIndex(i, 0);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.c);
        }
    }

    public static class AddRejectingSet<K, V> extends ForwardingSet<V> {
        public final K c;

        public AddRejectingSet(K k) {
            this.c = k;
        }

        public final Collection a() {
            return Collections.emptySet();
        }

        public boolean add(V v) {
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.c);
        }

        public boolean addAll(Collection<? extends V> collection) {
            Preconditions.checkNotNull(collection);
            throw new IllegalArgumentException("Key does not satisfy predicate: " + this.c);
        }

        public final Object delegate() {
            return Collections.emptySet();
        }

        public final Set<V> g() {
            return Collections.emptySet();
        }
    }

    public class Entries extends ForwardingCollection<Map.Entry<K, V>> {
        public Entries() {
        }

        /* renamed from: a */
        public final Collection<Map.Entry<K, V>> delegate() {
            FilteredKeyMultimap filteredKeyMultimap = FilteredKeyMultimap.this;
            return Collections2.filter(filteredKeyMultimap.j.entries(), filteredKeyMultimap.entryPredicate());
        }

        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            FilteredKeyMultimap filteredKeyMultimap = FilteredKeyMultimap.this;
            if (!filteredKeyMultimap.j.containsKey(entry.getKey()) || !filteredKeyMultimap.k.apply(entry.getKey())) {
                return false;
            }
            return filteredKeyMultimap.j.remove(entry.getKey(), entry.getValue());
        }
    }

    public FilteredKeyMultimap(Multimap<K, V> multimap, Predicate<? super K> predicate) {
        this.j = (Multimap) Preconditions.checkNotNull(multimap);
        this.k = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public final Map<K, Collection<V>> a() {
        return Maps.filterKeys(this.j.asMap(), this.k);
    }

    public Collection<Map.Entry<K, V>> b() {
        return new Entries();
    }

    public final Set<K> c() {
        return Sets.filter(this.j.keySet(), this.k);
    }

    public void clear() {
        keySet().clear();
    }

    public boolean containsKey(Object obj) {
        if (this.j.containsKey(obj)) {
            return this.k.apply(obj);
        }
        return false;
    }

    public final Multiset<K> d() {
        return Multisets.filter(this.j.keys(), this.k);
    }

    public final Collection<V> e() {
        return new FilteredMultimapValues(this);
    }

    public Predicate<? super Map.Entry<K, V>> entryPredicate() {
        return Maps.g(this.k);
    }

    public final Iterator<Map.Entry<K, V>> f() {
        throw new AssertionError("should never be called");
    }

    public Collection<V> get(K k2) {
        boolean apply = this.k.apply(k2);
        Multimap<K, V> multimap = this.j;
        if (apply) {
            return multimap.get(k2);
        }
        if (multimap instanceof SetMultimap) {
            return new AddRejectingSet(k2);
        }
        return new AddRejectingList(k2);
    }

    public Collection<V> removeAll(Object obj) {
        boolean containsKey = containsKey(obj);
        Multimap<K, V> multimap = this.j;
        if (containsKey) {
            return multimap.removeAll(obj);
        }
        if (multimap instanceof SetMultimap) {
            return ImmutableSet.of();
        }
        return ImmutableList.of();
    }

    public int size() {
        int i = 0;
        for (Collection size : asMap().values()) {
            i += size.size();
        }
        return i;
    }

    public Multimap<K, V> unfiltered() {
        return this.j;
    }
}
