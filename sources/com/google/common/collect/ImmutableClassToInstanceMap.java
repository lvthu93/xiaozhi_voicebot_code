package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.io.Serializable;
import java.util.Map;

public final class ImmutableClassToInstanceMap<B> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
    public static final ImmutableClassToInstanceMap<Object> f = new ImmutableClassToInstanceMap<>(ImmutableMap.of());
    public final ImmutableMap<Class<? extends B>, B> c;

    public static final class Builder<B> {
        public final ImmutableMap.Builder<Class<? extends B>, B> a = ImmutableMap.builder();

        public ImmutableClassToInstanceMap<B> build() {
            ImmutableMap<Class<? extends B>, B> build = this.a.build();
            if (build.isEmpty()) {
                return ImmutableClassToInstanceMap.of();
            }
            return new ImmutableClassToInstanceMap<>(build);
        }

        public <T extends B> Builder<B> put(Class<T> cls, T t) {
            this.a.put(cls, t);
            return this;
        }

        public <T extends B> Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> map) {
            for (Map.Entry next : map.entrySet()) {
                Class cls = (Class) next.getKey();
                Object value = next.getValue();
                Map<Class<?>, Class<?>> map2 = b9.a;
                Preconditions.checkNotNull(cls);
                Class cls2 = b9.a.get(cls);
                if (cls2 == null) {
                    cls2 = cls;
                }
                this.a.put(cls, cls2.cast(value));
            }
            return this;
        }
    }

    public ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> immutableMap) {
        this.c = immutableMap;
    }

    public static <B> Builder<B> builder() {
        return new Builder<>();
    }

    public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
        if (map instanceof ImmutableClassToInstanceMap) {
            return (ImmutableClassToInstanceMap) map;
        }
        return new Builder().putAll(map).build();
    }

    public static <B> ImmutableClassToInstanceMap<B> of() {
        return f;
    }

    public final Map<Class<? extends B>, B> a() {
        return this.c;
    }

    public final Object delegate() {
        return this.c;
    }

    public <T extends B> T getInstance(Class<T> cls) {
        return this.c.get(Preconditions.checkNotNull(cls));
    }

    @Deprecated
    public <T extends B> T putInstance(Class<T> cls, T t) {
        throw new UnsupportedOperationException();
    }

    public Object readResolve() {
        return isEmpty() ? of() : this;
    }

    public static <B, T extends B> ImmutableClassToInstanceMap<B> of(Class<T> cls, T t) {
        return new ImmutableClassToInstanceMap<>(ImmutableMap.of(cls, t));
    }
}
