package com.google.common.collect;

import com.google.common.collect.AbstractMapBasedMultimap;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;

abstract class AbstractSortedSetMultimap<K, V> extends AbstractSetMultimap<K, V> implements SortedSetMultimap<K, V> {
    private static final long serialVersionUID = 430848587173315748L;

    public AbstractSortedSetMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    public final Collection l() {
        SortedSet r = p();
        if (r instanceof NavigableSet) {
            return Sets.unmodifiableNavigableSet((NavigableSet) r);
        }
        return Collections.unmodifiableSortedSet(r);
    }

    public final Collection n(Collection collection) {
        if (collection instanceof NavigableSet) {
            return Sets.unmodifiableNavigableSet((NavigableSet) collection);
        }
        return Collections.unmodifiableSortedSet((SortedSet) collection);
    }

    public final Collection<V> o(K k, Collection<V> collection) {
        if (collection instanceof NavigableSet) {
            return new AbstractMapBasedMultimap.WrappedNavigableSet(k, (NavigableSet) collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
        }
        return new AbstractMapBasedMultimap.WrappedSortedSet(k, (SortedSet) collection, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
    }

    public final Set q() {
        SortedSet r = p();
        if (r instanceof NavigableSet) {
            return Sets.unmodifiableNavigableSet((NavigableSet) r);
        }
        return Collections.unmodifiableSortedSet(r);
    }

    /* renamed from: r */
    public abstract SortedSet<V> p();

    public Collection<V> values() {
        return super.values();
    }

    public SortedSet<V> get(K k) {
        return (SortedSet) super.get((Object) k);
    }

    public SortedSet<V> removeAll(Object obj) {
        return (SortedSet) super.removeAll(obj);
    }

    public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (SortedSet) super.replaceValues((Object) k, (Iterable) iterable);
    }
}
