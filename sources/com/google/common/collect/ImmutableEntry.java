package com.google.common.collect;

import java.io.Serializable;

class ImmutableEntry<K, V> extends AbstractMapEntry<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    public final K c;
    public final V f;

    public ImmutableEntry(K k, V v) {
        this.c = k;
        this.f = v;
    }

    public final K getKey() {
        return this.c;
    }

    public final V getValue() {
        return this.f;
    }

    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
