package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.io.Serializable;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

final class ImmutableEnumMap<K extends Enum<K>, V> extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
    public final transient EnumMap<K, V> j;

    public static class EnumSerializedForm<K extends Enum<K>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final EnumMap<K, V> c;

        public EnumSerializedForm(EnumMap<K, V> enumMap) {
            this.c = enumMap;
        }

        public Object readResolve() {
            return new ImmutableEnumMap(this.c);
        }
    }

    public ImmutableEnumMap(EnumMap<K, V> enumMap) {
        this.j = enumMap;
        Preconditions.checkArgument(!enumMap.isEmpty());
    }

    public boolean containsKey(Object obj) {
        return this.j.containsKey(obj);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ImmutableEnumMap) {
            obj = ((ImmutableEnumMap) obj).j;
        }
        return this.j.equals(obj);
    }

    public final boolean f() {
        return false;
    }

    public final UnmodifiableIterator<K> g() {
        return Iterators.unmodifiableIterator(this.j.keySet().iterator());
    }

    public V get(Object obj) {
        return this.j.get(obj);
    }

    public final UnmodifiableIterator<Map.Entry<K, V>> h() {
        return new UnmodifiableIterator<Map.Entry<Object, Object>>(this.j.entrySet().iterator()) {
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

    public int size() {
        return this.j.size();
    }

    public Object writeReplace() {
        return new EnumSerializedForm(this.j);
    }
}
