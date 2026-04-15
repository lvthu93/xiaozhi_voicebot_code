package j$.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class DesugarCollections {
    public static final Class a;
    private static final Field b;
    private static final Field c;
    /* access modifiers changed from: private */
    public static final Constructor d;
    /* access modifiers changed from: private */
    public static final Constructor e;

    static {
        Field field;
        Field field2;
        Constructor<?> constructor;
        Class<?> cls = Collections.synchronizedCollection(new ArrayList()).getClass();
        a = cls;
        Collections.synchronizedList(new LinkedList()).getClass();
        Constructor<?> constructor2 = null;
        try {
            field = cls.getDeclaredField("mutex");
        } catch (NoSuchFieldException unused) {
            field = null;
        }
        b = field;
        if (field != null) {
            field.setAccessible(true);
        }
        try {
            field2 = cls.getDeclaredField("c");
        } catch (NoSuchFieldException unused2) {
            field2 = null;
        }
        c = field2;
        if (field2 != null) {
            field2.setAccessible(true);
        }
        Class<Object> cls2 = Object.class;
        try {
            constructor = Collections.synchronizedSet(new HashSet()).getClass().getDeclaredConstructor(new Class[]{Set.class, cls2});
        } catch (NoSuchMethodException unused3) {
            constructor = null;
        }
        e = constructor;
        if (constructor != null) {
            constructor.setAccessible(true);
        }
        try {
            constructor2 = cls.getDeclaredConstructor(new Class[]{Collection.class, cls2});
        } catch (NoSuchMethodException unused4) {
        }
        d = constructor2;
        if (constructor2 != null) {
            constructor2.setAccessible(true);
        }
    }

    static boolean c(Collection collection, Predicate predicate) {
        boolean removeIf;
        Field field = b;
        if (field == null) {
            try {
                return Collection$EL.removeIf((Collection) c.get(collection), predicate);
            } catch (IllegalAccessException e2) {
                throw new Error("Runtime illegal access in synchronized collection removeIf fall-back.", e2);
            }
        } else {
            try {
                synchronized (field.get(collection)) {
                    removeIf = Collection$EL.removeIf((Collection) c.get(collection), predicate);
                }
                return removeIf;
            } catch (IllegalAccessException e3) {
                throw new Error("Runtime illegal access in synchronized collection removeIf.", e3);
            }
        }
    }

    public static <K, V> Map<K, V> synchronizedMap(Map<K, V> map) {
        return new C0066j(map);
    }
}
