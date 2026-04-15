package com.google.protobuf;

import j$.util.Iterator;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public final class r extends s {

    public static class a<K> implements Map.Entry<K, Object> {
        public final Map.Entry<K, r> c;

        public a() {
            throw null;
        }

        public a(Map.Entry entry) {
            this.c = entry;
        }

        public final K getKey() {
            return this.c.getKey();
        }

        public final Object getValue() {
            r value = this.c.getValue();
            if (value == null) {
                return null;
            }
            return value.b((x) null);
        }

        public final Object setValue(Object obj) {
            if (obj instanceof x) {
                r value = this.c.getValue();
                x xVar = value.a;
                value.b = null;
                value.a = (x) obj;
                return xVar;
            }
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }
    }

    public static class b<K> implements Iterator<Map.Entry<K, Object>>, j$.util.Iterator {
        public final Iterator<Map.Entry<K, Object>> c;

        public b(Iterator<Map.Entry<K, Object>> it) {
            this.c = it;
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public final boolean hasNext() {
            return this.c.hasNext();
        }

        public final Object next() {
            Map.Entry next = this.c.next();
            if (next.getValue() instanceof r) {
                return new a(next);
            }
            return next;
        }

        public final void remove() {
            this.c.remove();
        }
    }

    public final boolean equals(Object obj) {
        return b((x) null).equals(obj);
    }

    public final int hashCode() {
        return b((x) null).hashCode();
    }

    public final String toString() {
        return b((x) null).toString();
    }
}
