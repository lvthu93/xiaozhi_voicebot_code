package com.google.common.collect;

import java.util.Collection;
import java.util.Map;

abstract class HashMultimapGwtSerializationDependencies<K, V> extends AbstractSetMultimap<K, V> {
    public HashMultimapGwtSerializationDependencies(Map<K, Collection<V>> map) {
        super(map);
    }

    public /* bridge */ /* synthetic */ Collection h() {
        return h();
    }
}
