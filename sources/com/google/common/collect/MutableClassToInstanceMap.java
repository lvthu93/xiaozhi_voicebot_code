package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class MutableClassToInstanceMap<B> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
    public final Map<Class<? extends B>, B> c;

    public static final class SerializedForm<B> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Map<Class<? extends B>, B> c;

        public SerializedForm(Map<Class<? extends B>, B> map) {
            this.c = map;
        }

        public Object readResolve() {
            return MutableClassToInstanceMap.create(this.c);
        }
    }

    public MutableClassToInstanceMap(Map<Class<? extends B>, B> map) {
        this.c = (Map) Preconditions.checkNotNull(map);
    }

    public static <B> MutableClassToInstanceMap<B> create() {
        return new MutableClassToInstanceMap<>(new HashMap());
    }

    private Object writeReplace() {
        return new SerializedForm(this.c);
    }

    public final Map<Class<? extends B>, B> a() {
        return this.c;
    }

    public final Object delegate() {
        return this.c;
    }

    public Set<Map.Entry<Class<? extends B>, B>> entrySet() {
        return new ForwardingSet<Map.Entry<Class<? extends B>, B>>() {
            /* renamed from: g */
            public final Set<Map.Entry<Class<? extends B>, B>> delegate() {
                return MutableClassToInstanceMap.this.c.entrySet();
            }

            public Iterator<Map.Entry<Class<? extends B>, B>> iterator() {
                return new TransformedIterator<Map.Entry<Class<? extends B>, B>, Map.Entry<Class<? extends B>, B>>(delegate().iterator()) {
                    public final Object a(Object obj) {
                        return new ForwardingMapEntry<Class<Object>, Object>((Map.Entry) obj) {
                            public final /* synthetic */ Map.Entry c;

                            {
                                this.c = r1;
                            }

                            public final Map.Entry<Class<Object>, Object> a() {
                                return this.c;
                            }

                            public final Object delegate() {
                                return this.c;
                            }

                            public Object setValue(Object obj) {
                                Class cls = (Class) getKey();
                                Map<Class<?>, Class<?>> map = b9.a;
                                Preconditions.checkNotNull(cls);
                                Class cls2 = b9.a.get(cls);
                                if (cls2 != null) {
                                    cls = cls2;
                                }
                                return super.setValue(cls.cast(obj));
                            }
                        };
                    }
                };
            }

            public Object[] toArray() {
                return c();
            }

            public <T> T[] toArray(T[] tArr) {
                return ObjectArrays.c(this, tArr);
            }
        };
    }

    public <T extends B> T getInstance(Class<T> cls) {
        Object obj = get(cls);
        Map<Class<?>, Class<?>> map = b9.a;
        Preconditions.checkNotNull(cls);
        Class<T> cls2 = b9.a.get(cls);
        if (cls2 != null) {
            cls = cls2;
        }
        return cls.cast(obj);
    }

    public void putAll(Map<? extends Class<? extends B>, ? extends B> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            Class cls = (Class) entry.getKey();
            Object value = entry.getValue();
            Map<Class<?>, Class<?>> map2 = b9.a;
            Preconditions.checkNotNull(cls);
            Class cls2 = b9.a.get(cls);
            if (cls2 != null) {
                cls = cls2;
            }
            cls.cast(value);
        }
        super.putAll(linkedHashMap);
    }

    public <T extends B> T putInstance(Class<T> cls, T t) {
        Object put = put(cls, t);
        Map<Class<?>, Class<?>> map = b9.a;
        Preconditions.checkNotNull(cls);
        Class<T> cls2 = b9.a.get(cls);
        if (cls2 != null) {
            cls = cls2;
        }
        return cls.cast(put);
    }

    public static <B> MutableClassToInstanceMap<B> create(Map<Class<? extends B>, B> map) {
        return new MutableClassToInstanceMap<>(map);
    }

    public B put(Class<? extends B> cls, B b) {
        Map<Class<?>, Class<?>> map = b9.a;
        Preconditions.checkNotNull(cls);
        Class<? extends B> cls2 = b9.a.get(cls);
        if (cls2 == null) {
            cls2 = cls;
        }
        return super.put(cls, cls2.cast(b));
    }
}
