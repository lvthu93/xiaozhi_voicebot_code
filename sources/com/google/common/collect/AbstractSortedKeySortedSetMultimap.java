package com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

abstract class AbstractSortedKeySortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V> {
    public AbstractSortedKeySortedSetMultimap(TreeMap treeMap) {
        super(treeMap);
    }

    public final Set<K> c() {
        return k();
    }

    public /* bridge */ /* synthetic */ Collection h() {
        return p();
    }

    public /* bridge */ /* synthetic */ Set p() {
        return p();
    }

    public SortedMap<K, Collection<V>> asMap() {
        return (SortedMap) super.asMap();
    }

    public SortedSet<K> keySet() {
        return (SortedSet) super.keySet();
    }
}
