package com.google.common.collect;

import java.io.Serializable;

final class ImmutableMapKeySet<K, V> extends IndexedImmutableSet<K> {
    public final ImmutableMap<K, V> g;

    public static class KeySetSerializedForm<K> implements Serializable {
        private static final long serialVersionUID = 0;
        public final ImmutableMap<K, ?> c;

        public KeySetSerializedForm(ImmutableMap<K, ?> immutableMap) {
            this.c = immutableMap;
        }

        public Object readResolve() {
            return this.c.keySet();
        }
    }

    public ImmutableMapKeySet(ImmutableMap<K, V> immutableMap) {
        this.g = immutableMap;
    }

    public boolean contains(Object obj) {
        return this.g.containsKey(obj);
    }

    public final K get(int i) {
        return this.g.entrySet().asList().get(i).getKey();
    }

    public final boolean isPartialView() {
        return true;
    }

    public int size() {
        return this.g.size();
    }

    public Object writeReplace() {
        return new KeySetSerializedForm(this.g);
    }

    public UnmodifiableIterator<K> iterator() {
        return this.g.g();
    }
}
