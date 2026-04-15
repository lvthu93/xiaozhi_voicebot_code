package com.google.common.base;

import com.google.common.base.Platform;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

public final class Enums {
    public static final WeakHashMap a = new WeakHashMap();

    public static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Class<T> g;

        public StringConverter(Class<T> cls) {
            this.g = (Class) Preconditions.checkNotNull(cls);
        }

        public final Object d(Object obj) {
            return ((Enum) obj).name();
        }

        public final Object e(Object obj) {
            return Enum.valueOf(this.g, (String) obj);
        }

        public boolean equals(Object obj) {
            if (obj instanceof StringConverter) {
                return this.g.equals(((StringConverter) obj).g);
            }
            return false;
        }

        public int hashCode() {
            return this.g.hashCode();
        }

        public String toString() {
            return "Enums.stringConverter(" + this.g.getName() + ".class)";
        }
    }

    public static Field getField(Enum<?> enumR) {
        try {
            return enumR.getDeclaringClass().getDeclaredField(enumR.name());
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> cls, String str) {
        HashMap hashMap;
        Preconditions.checkNotNull(cls);
        Preconditions.checkNotNull(str);
        Platform.JdkPatternCompiler jdkPatternCompiler = Platform.a;
        WeakHashMap weakHashMap = a;
        synchronized (weakHashMap) {
            Map map = (Map) weakHashMap.get(cls);
            hashMap = map;
            if (map == null) {
                HashMap hashMap2 = new HashMap();
                Iterator<E> it = EnumSet.allOf(cls).iterator();
                while (it.hasNext()) {
                    Enum enumR = (Enum) it.next();
                    hashMap2.put(enumR.name(), new WeakReference(enumR));
                }
                a.put(cls, hashMap2);
                hashMap = hashMap2;
            }
        }
        WeakReference weakReference = (WeakReference) hashMap.get(str);
        if (weakReference == null) {
            return Optional.absent();
        }
        return Optional.of(cls.cast(weakReference.get()));
    }

    public static <T extends Enum<T>> Converter<String, T> stringConverter(Class<T> cls) {
        return new StringConverter(cls);
    }
}
