package com.google.common.collect;

import com.google.common.collect.AbstractMapBasedMultimap;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

abstract class AbstractListMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements ListMultimap<K, V> {
    private static final long serialVersionUID = 6588350623831699109L;

    public AbstractListMultimap(Map<K, Collection<V>> map) {
        super(map);
    }

    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final Collection l() {
        return Collections.emptyList();
    }

    public final <E> Collection<E> n(Collection<E> collection) {
        return Collections.unmodifiableList((List) collection);
    }

    public final Collection<V> o(K k, Collection<V> collection) {
        List list = (List) collection;
        if (list instanceof RandomAccess) {
            return new AbstractMapBasedMultimap.RandomAccessWrappedList(this, k, list, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
        }
        return new AbstractMapBasedMultimap.WrappedList(k, list, (AbstractMapBasedMultimap<K, V>.WrappedCollection) null);
    }

    /* renamed from: p */
    public abstract List<V> h();

    public boolean put(K k, V v) {
        return super.put(k, v);
    }

    public List<V> get(K k) {
        return (List) super.get(k);
    }

    public List<V> removeAll(Object obj) {
        return (List) super.removeAll(obj);
    }

    public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (List) super.replaceValues(k, iterable);
    }
}
