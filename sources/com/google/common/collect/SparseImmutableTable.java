package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import java.util.LinkedHashMap;
import java.util.Map;

final class SparseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    public static final ImmutableTable<Object, Object, Object> k = new SparseImmutableTable(ImmutableList.of(), ImmutableSet.of(), ImmutableSet.of());
    public final ImmutableMap<R, ImmutableMap<C, V>> g;
    public final ImmutableMap<C, ImmutableMap<R, V>> h;
    public final int[] i;
    public final int[] j;

    public SparseImmutableTable(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        boolean z;
        ImmutableMap<R, Integer> e = Maps.e(immutableSet);
        LinkedHashMap newLinkedHashMap = Maps.newLinkedHashMap();
        UnmodifiableIterator<R> it = immutableSet.iterator();
        while (it.hasNext()) {
            newLinkedHashMap.put(it.next(), new LinkedHashMap());
        }
        LinkedHashMap newLinkedHashMap2 = Maps.newLinkedHashMap();
        UnmodifiableIterator<C> it2 = immutableSet2.iterator();
        while (it2.hasNext()) {
            newLinkedHashMap2.put(it2.next(), new LinkedHashMap());
        }
        int[] iArr = new int[immutableList.size()];
        int[] iArr2 = new int[immutableList.size()];
        int i2 = 0;
        while (i2 < immutableList.size()) {
            Table.Cell cell = immutableList.get(i2);
            Object rowKey = cell.getRowKey();
            Object columnKey = cell.getColumnKey();
            Object value = cell.getValue();
            iArr[i2] = e.get(rowKey).intValue();
            Map map = (Map) newLinkedHashMap.get(rowKey);
            iArr2[i2] = map.size();
            Object put = map.put(columnKey, value);
            if (put == null) {
                z = true;
            } else {
                z = false;
            }
            ImmutableMap<R, Integer> immutableMap = e;
            Object obj = rowKey;
            Preconditions.checkArgument(z, "Duplicate key: (row=%s, column=%s), values: [%s, %s].", rowKey, columnKey, value, put);
            ((Map) newLinkedHashMap2.get(columnKey)).put(obj, value);
            i2++;
            e = immutableMap;
        }
        this.i = iArr;
        this.j = iArr2;
        ImmutableMap.Builder builder = new ImmutableMap.Builder(newLinkedHashMap.size());
        for (Map.Entry entry : newLinkedHashMap.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf((Map) entry.getValue()));
        }
        this.g = builder.build();
        ImmutableMap.Builder builder2 = new ImmutableMap.Builder(newLinkedHashMap2.size());
        for (Map.Entry entry2 : newLinkedHashMap2.entrySet()) {
            builder2.put(entry2.getKey(), ImmutableMap.copyOf((Map) entry2.getValue()));
        }
        this.h = builder2.build();
    }

    public final ImmutableTable.SerializedForm g() {
        ImmutableMap e = Maps.e(columnKeySet());
        int[] iArr = new int[cellSet().size()];
        UnmodifiableIterator it = cellSet().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            iArr[i2] = ((Integer) e.get(((Table.Cell) it.next()).getColumnKey())).intValue();
            i2++;
        }
        return ImmutableTable.SerializedForm.a(this, this.i, iArr);
    }

    public final Table.Cell<R, C, V> j(int i2) {
        Map.Entry entry = this.g.entrySet().asList().get(this.i[i2]);
        Map.Entry entry2 = (Map.Entry) ((ImmutableMap) entry.getValue()).entrySet().asList().get(this.j[i2]);
        return ImmutableTable.e(entry.getKey(), entry2.getKey(), entry2.getValue());
    }

    public final V k(int i2) {
        int i3 = this.i[i2];
        return this.g.values().asList().get(i3).values().asList().get(this.j[i2]);
    }

    public int size() {
        return this.i.length;
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.copyOf(this.h);
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.copyOf(this.g);
    }
}
