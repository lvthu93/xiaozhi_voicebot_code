package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>> extends AbstractBiMap<K, V> {
    private static final long serialVersionUID = 0;
    public transient Class<K> j;
    public transient Class<V> k;

    public EnumBiMap(Class<K> cls, Class<V> cls2) {
        super(new EnumMap(cls), (AbstractMap) new EnumMap(cls2));
        this.j = cls;
        this.k = cls2;
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> cls, Class<V> cls2) {
        return new EnumBiMap<>(cls, cls2);
    }

    public static <K extends Enum<K>> Class<K> k(Map<K, ?> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap) map).keyType();
        }
        if (map instanceof EnumHashBiMap) {
            return ((EnumHashBiMap) map).keyType();
        }
        Preconditions.checkArgument(!map.isEmpty());
        return ((Enum) map.keySet().iterator().next()).getDeclaringClass();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.j = (Class) objectInputStream.readObject();
        this.k = (Class) objectInputStream.readObject();
        j(new EnumMap(this.j), new EnumMap(this.k));
        Serialization.b(this, objectInputStream, objectInputStream.readInt());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.j);
        objectOutputStream.writeObject(this.k);
        Serialization.e(this, objectOutputStream);
    }

    public final Object c(Object obj) {
        return (Enum) Preconditions.checkNotNull((Enum) obj);
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public final Object f(Object obj) {
        return (Enum) Preconditions.checkNotNull((Enum) obj);
    }

    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    public Class<K> keyType() {
        return this.j;
    }

    public /* bridge */ /* synthetic */ void putAll(Map map) {
        super.putAll(map);
    }

    public Class<V> valueType() {
        return this.k;
    }

    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> map) {
        Class<V> cls;
        Class<K> k2 = k(map);
        if (map instanceof EnumBiMap) {
            cls = ((EnumBiMap) map).k;
        } else {
            Preconditions.checkArgument(!map.isEmpty());
            cls = ((Enum) map.values().iterator().next()).getDeclaringClass();
        }
        EnumBiMap<K, V> create = create(k2, cls);
        create.putAll(map);
        return create;
    }
}
