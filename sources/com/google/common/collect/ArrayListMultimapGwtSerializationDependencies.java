package com.google.common.collect;

import java.util.Collection;
import java.util.Map;

abstract class ArrayListMultimapGwtSerializationDependencies<K, V> extends AbstractListMultimap<K, V> {
    public ArrayListMultimapGwtSerializationDependencies(Map<K, Collection<V>> map) {
        super(map);
    }

    public /* bridge */ /* synthetic */ Collection h() {
        return h();
    }
}
