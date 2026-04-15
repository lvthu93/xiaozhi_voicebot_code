package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import java.util.Map;

class SingletonImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {
    public final R g;
    public final C h;
    public final V i;

    public SingletonImmutableTable() {
        throw null;
    }

    public SingletonImmutableTable(R r, C c, V v) {
        this.g = Preconditions.checkNotNull(r);
        this.h = Preconditions.checkNotNull(c);
        this.i = Preconditions.checkNotNull(v);
    }

    /* renamed from: f */
    public final ImmutableSet<Table.Cell<R, C, V>> b() {
        return ImmutableSet.of(ImmutableTable.e(this.g, this.h, this.i));
    }

    public final ImmutableTable.SerializedForm g() {
        return ImmutableTable.SerializedForm.a(this, new int[]{0}, new int[]{0});
    }

    /* renamed from: h */
    public final ImmutableCollection<V> c() {
        return ImmutableSet.of(this.i);
    }

    public int size() {
        return 1;
    }

    public ImmutableMap<R, V> column(C c) {
        Preconditions.checkNotNull(c);
        if (containsColumn(c)) {
            return ImmutableMap.of(this.g, this.i);
        }
        return ImmutableMap.of();
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.of(this.h, ImmutableMap.of(this.g, this.i));
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.of(this.g, ImmutableMap.of(this.h, this.i));
    }
}
