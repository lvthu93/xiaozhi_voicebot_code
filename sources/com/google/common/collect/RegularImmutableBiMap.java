package com.google.common.collect;

import com.google.common.collect.RegularImmutableMap;
import java.util.Map;

final class RegularImmutableBiMap<K, V> extends ImmutableBiMap<K, V> {
    public static final RegularImmutableBiMap<Object, Object> o = new RegularImmutableBiMap<>();
    public final transient int[] j;
    public final transient Object[] k;
    public final transient int l;
    public final transient int m;
    public final transient RegularImmutableBiMap<V, K> n;

    public RegularImmutableBiMap() {
        this.j = null;
        this.k = new Object[0];
        this.l = 0;
        this.m = 0;
        this.n = this;
    }

    public final ImmutableSet<Map.Entry<K, V>> a() {
        return new RegularImmutableMap.EntrySet(this, this.k, this.l, this.m);
    }

    public final ImmutableSet<K> b() {
        return new RegularImmutableMap.KeySet(this, new RegularImmutableMap.KeysOrValuesAsList(this.k, this.l, this.m));
    }

    public final boolean f() {
        return false;
    }

    public V get(Object obj) {
        return RegularImmutableMap.j(this.j, this.k, this.m, this.l, obj);
    }

    public int size() {
        return this.m;
    }

    public ImmutableBiMap<V, K> inverse() {
        return this.n;
    }

    public RegularImmutableBiMap(Object[] objArr, int i) {
        this.k = objArr;
        this.m = i;
        this.l = 0;
        int g = i >= 2 ? ImmutableSet.g(i) : 0;
        this.j = RegularImmutableMap.i(objArr, i, g, 0);
        this.n = new RegularImmutableBiMap<>(RegularImmutableMap.i(objArr, i, g, 1), objArr, i, this);
    }

    public RegularImmutableBiMap(int[] iArr, Object[] objArr, int i, RegularImmutableBiMap<V, K> regularImmutableBiMap) {
        this.j = iArr;
        this.k = objArr;
        this.l = 1;
        this.m = i;
        this.n = regularImmutableBiMap;
    }
}
