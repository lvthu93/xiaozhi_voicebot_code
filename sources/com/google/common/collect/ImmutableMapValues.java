package com.google.common.collect;

import java.io.Serializable;
import java.util.Map;

final class ImmutableMapValues<K, V> extends ImmutableCollection<V> {
    public final ImmutableMap<K, V> f;

    public static class SerializedForm<V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final ImmutableMap<?, V> c;

        public SerializedForm(ImmutableMap<?, V> immutableMap) {
            this.c = immutableMap;
        }

        public Object readResolve() {
            return this.c.values();
        }
    }

    public ImmutableMapValues(ImmutableMap<K, V> immutableMap) {
        this.f = immutableMap;
    }

    public ImmutableList<V> asList() {
        final ImmutableList<Map.Entry<K, V>> asList = this.f.entrySet().asList();
        return new ImmutableList<V>() {
            public V get(int i) {
                return ((Map.Entry) ImmutableList.this.get(i)).getValue();
            }

            public final boolean isPartialView() {
                return true;
            }

            public int size() {
                return ImmutableList.this.size();
            }
        };
    }

    public boolean contains(Object obj) {
        return obj != null && Iterators.contains(iterator(), obj);
    }

    public final boolean isPartialView() {
        return true;
    }

    public int size() {
        return this.f.size();
    }

    public Object writeReplace() {
        return new SerializedForm(this.f);
    }

    public UnmodifiableIterator<V> iterator() {
        return new UnmodifiableIterator<V>(this) {
            public final UnmodifiableIterator<Map.Entry<K, V>> c;

            {
                this.c = r1.f.entrySet().iterator();
            }

            public boolean hasNext() {
                return this.c.hasNext();
            }

            public V next() {
                return this.c.next().getValue();
            }
        };
    }
}
