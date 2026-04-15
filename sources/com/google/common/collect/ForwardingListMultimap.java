package com.google.common.collect;

import java.util.List;

public abstract class ForwardingListMultimap<K, V> extends ForwardingMultimap<K, V> implements ListMultimap<K, V> {
    public abstract ListMultimap<K, V> delegate();

    public List<V> get(K k) {
        return delegate().get(k);
    }

    public List<V> removeAll(Object obj) {
        return delegate().removeAll(obj);
    }

    public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return delegate().replaceValues(k, iterable);
    }
}
