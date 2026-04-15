package com.google.common.collect;

import java.io.Serializable;
import java.util.Map;

abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {

    public static class EntrySetSerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final ImmutableMap<K, V> c;

        public EntrySetSerializedForm(ImmutableMap<K, V> immutableMap) {
            this.c = immutableMap;
        }

        public Object readResolve() {
            return this.c.entrySet();
        }
    }

    public static final class RegularEntrySet<K, V> extends ImmutableMapEntrySet<K, V> {
        public final int a(int i, Object[] objArr) {
            throw null;
        }

        public final ImmutableList<Map.Entry<K, V>> k() {
            return null;
        }

        public final ImmutableMap<K, V> m() {
            return null;
        }

        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            throw null;
        }
    }

    public boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Object obj2 = m().get(entry.getKey());
        if (obj2 == null || !obj2.equals(entry.getValue())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return m().hashCode();
    }

    public final boolean isPartialView() {
        return m().f();
    }

    public final boolean l() {
        return m().e();
    }

    public abstract ImmutableMap<K, V> m();

    public int size() {
        return m().size();
    }

    public Object writeReplace() {
        return new EntrySetSerializedForm(m());
    }
}
