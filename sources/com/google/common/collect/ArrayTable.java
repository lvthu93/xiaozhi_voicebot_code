package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArrayTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    public final ImmutableList<R> g;
    public final ImmutableList<C> h;
    public final ImmutableMap<R, Integer> i;
    public final ImmutableMap<C, Integer> j;
    public final V[][] k;
    public transient ArrayTable<R, C, V>.ColumnMap l;
    public transient ArrayTable<R, C, V>.RowMap m;

    public static abstract class ArrayMap<K, V> extends Maps.IteratorBasedAbstractMap<K, V> {
        public final ImmutableMap<K, Integer> c;

        public ArrayMap() {
            throw null;
        }

        public ArrayMap(ImmutableMap immutableMap) {
            this.c = immutableMap;
        }

        public final Iterator<Map.Entry<K, V>> a() {
            return new AbstractIndexedListIterator<Map.Entry<K, V>>(size()) {
                public final Object get(int i) {
                    ArrayMap arrayMap = ArrayMap.this;
                    Preconditions.checkElementIndex(i, arrayMap.size());
                    return new AbstractMapEntry<Object, Object>(i) {
                        public final /* synthetic */ int c;

                        {
                            this.c = r2;
                        }

                        public Object getKey() {
                            return ArrayMap.this.c.keySet().asList().get(this.c);
                        }

                        public Object getValue() {
                            return ArrayMap.this.c(this.c);
                        }

                        public Object setValue(Object obj) {
                            return ArrayMap.this.d(this.c, obj);
                        }
                    };
                }
            };
        }

        public abstract String b();

        public abstract V c(int i);

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public boolean containsKey(Object obj) {
            return this.c.containsKey(obj);
        }

        public abstract V d(int i, V v);

        public V get(Object obj) {
            Integer num = this.c.get(obj);
            if (num == null) {
                return null;
            }
            return c(num.intValue());
        }

        public boolean isEmpty() {
            return this.c.isEmpty();
        }

        public Set<K> keySet() {
            return this.c.keySet();
        }

        public V put(K k, V v) {
            ImmutableMap<K, Integer> immutableMap = this.c;
            Integer num = immutableMap.get(k);
            if (num != null) {
                return d(num.intValue(), v);
            }
            throw new IllegalArgumentException(b() + " " + k + " not in " + immutableMap.keySet());
        }

        public V remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.c.size();
        }
    }

    public class Column extends ArrayMap<R, V> {
        public final int f;

        public Column(int i) {
            super(ArrayTable.this.i);
            this.f = i;
        }

        public final String b() {
            return "Row";
        }

        public final V c(int i) {
            return ArrayTable.this.at(i, this.f);
        }

        public final V d(int i, V v) {
            return ArrayTable.this.set(i, this.f, v);
        }
    }

    public class ColumnMap extends ArrayMap<C, Map<R, V>> {
        public ColumnMap() {
            super(ArrayTable.this.j);
        }

        public final String b() {
            return "Column";
        }

        public final Object c(int i) {
            return new Column(i);
        }

        public final Object d(int i, Object obj) {
            Map map = (Map) obj;
            throw new UnsupportedOperationException();
        }

        public Map<R, V> put(C c, Map<R, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    public class Row extends ArrayMap<C, V> {
        public final int f;

        public Row(int i) {
            super(ArrayTable.this.j);
            this.f = i;
        }

        public final String b() {
            return "Column";
        }

        public final V c(int i) {
            return ArrayTable.this.at(this.f, i);
        }

        public final V d(int i, V v) {
            return ArrayTable.this.set(this.f, i, v);
        }
    }

    public class RowMap extends ArrayMap<R, Map<C, V>> {
        public RowMap() {
            super(ArrayTable.this.i);
        }

        public final String b() {
            return "Row";
        }

        public final Object c(int i) {
            return new Row(i);
        }

        public final Object d(int i, Object obj) {
            Map map = (Map) obj;
            throw new UnsupportedOperationException();
        }

        public Map<C, V> put(R r, Map<C, V> map) {
            throw new UnsupportedOperationException();
        }
    }

    public ArrayTable() {
        throw null;
    }

    public ArrayTable(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        ImmutableList<R> copyOf = ImmutableList.copyOf(iterable);
        this.g = copyOf;
        ImmutableList<C> copyOf2 = ImmutableList.copyOf(iterable2);
        this.h = copyOf2;
        Preconditions.checkArgument(copyOf.isEmpty() == copyOf2.isEmpty());
        this.i = Maps.e(copyOf);
        this.j = Maps.e(copyOf2);
        int size = copyOf.size();
        int[] iArr = new int[2];
        iArr[1] = copyOf2.size();
        iArr[0] = size;
        this.k = (Object[][]) Array.newInstance(Object.class, iArr);
        eraseAll();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Iterable<? extends R> iterable, Iterable<? extends C> iterable2) {
        return new ArrayTable<>(iterable, iterable2);
    }

    public final Iterator<Table.Cell<R, C, V>> a() {
        return new AbstractIndexedListIterator<Table.Cell<R, C, V>>(size()) {
            public final Object get(int i) {
                ArrayTable arrayTable = ArrayTable.this;
                arrayTable.getClass();
                return new Tables.AbstractCell<Object, Object, Object>(i) {
                    public final int c;
                    public final int f;

                    {
                        ImmutableList<C> immutableList = ArrayTable.this.h;
                        this.c = r3 / immutableList.size();
                        this.f = r3 % immutableList.size();
                    }

                    public Object getColumnKey() {
                        return ArrayTable.this.h.get(this.f);
                    }

                    public Object getRowKey() {
                        return ArrayTable.this.g.get(this.c);
                    }

                    public Object getValue() {
                        return ArrayTable.this.at(this.c, this.f);
                    }
                };
            }
        };
    }

    public V at(int i2, int i3) {
        Preconditions.checkElementIndex(i2, this.g.size());
        Preconditions.checkElementIndex(i3, this.h.size());
        return this.k[i2][i3];
    }

    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public Map<R, V> column(C c) {
        Preconditions.checkNotNull(c);
        Integer num = this.j.get(c);
        if (num == null) {
            return ImmutableMap.of();
        }
        return new Column(num.intValue());
    }

    public ImmutableList<C> columnKeyList() {
        return this.h;
    }

    public Map<C, Map<R, V>> columnMap() {
        ArrayTable<R, C, V>.ColumnMap columnMap = this.l;
        if (columnMap != null) {
            return columnMap;
        }
        ArrayTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.l = columnMap2;
        return columnMap2;
    }

    public boolean contains(Object obj, Object obj2) {
        return containsRow(obj) && containsColumn(obj2);
    }

    public boolean containsColumn(Object obj) {
        return this.j.containsKey(obj);
    }

    public boolean containsRow(Object obj) {
        return this.i.containsKey(obj);
    }

    public boolean containsValue(Object obj) {
        for (V[] vArr : this.k) {
            for (V equal : r0[r3]) {
                if (Objects.equal(obj, equal)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final Iterator<V> d() {
        return new AbstractIndexedListIterator<V>(size()) {
            public final V get(int i) {
                ArrayTable arrayTable = ArrayTable.this;
                ImmutableList<C> immutableList = arrayTable.h;
                return arrayTable.at(i / immutableList.size(), i % immutableList.size());
            }
        };
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public V erase(Object obj, Object obj2) {
        Integer num = this.i.get(obj);
        Integer num2 = this.j.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return set(num.intValue(), num2.intValue(), (Object) null);
    }

    public void eraseAll() {
        for (V[] fill : this.k) {
            Arrays.fill(fill, (Object) null);
        }
    }

    public V get(Object obj, Object obj2) {
        Integer num = this.i.get(obj);
        Integer num2 = this.j.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return at(num.intValue(), num2.intValue());
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public boolean isEmpty() {
        return this.g.isEmpty() || this.h.isEmpty();
    }

    public V put(R r, C c, V v) {
        boolean z;
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c);
        Integer num = this.i.get(r);
        boolean z2 = true;
        if (num != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Row %s not in %s", (Object) r, (Object) this.g);
        Integer num2 = this.j.get(c);
        if (num2 == null) {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "Column %s not in %s", (Object) c, (Object) this.h);
        return set(num.intValue(), num2.intValue(), v);
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        super.putAll(table);
    }

    @Deprecated
    public V remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    public Map<C, V> row(R r) {
        Preconditions.checkNotNull(r);
        Integer num = this.i.get(r);
        if (num == null) {
            return ImmutableMap.of();
        }
        return new Row(num.intValue());
    }

    public ImmutableList<R> rowKeyList() {
        return this.g;
    }

    public Map<R, Map<C, V>> rowMap() {
        ArrayTable<R, C, V>.RowMap rowMap = this.m;
        if (rowMap != null) {
            return rowMap;
        }
        ArrayTable<R, C, V>.RowMap rowMap2 = new RowMap();
        this.m = rowMap2;
        return rowMap2;
    }

    public V set(int i2, int i3, V v) {
        Preconditions.checkElementIndex(i2, this.g.size());
        Preconditions.checkElementIndex(i3, this.h.size());
        V[] vArr = this.k[i2];
        V v2 = vArr[i3];
        vArr[i3] = v;
        return v2;
    }

    public int size() {
        return this.h.size() * this.g.size();
    }

    public V[][] toArray(Class<V> cls) {
        ImmutableList<R> immutableList = this.g;
        V[][] vArr = (Object[][]) Array.newInstance(cls, new int[]{immutableList.size(), this.h.size()});
        for (int i2 = 0; i2 < immutableList.size(); i2++) {
            V[] vArr2 = this.k[i2];
            System.arraycopy(vArr2, 0, vArr[i2], 0, vArr2.length);
        }
        return vArr;
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public Collection<V> values() {
        return super.values();
    }

    public static <R, C, V> ArrayTable<R, C, V> create(Table<R, C, V> table) {
        if (table instanceof ArrayTable) {
            return new ArrayTable<>((ArrayTable) table);
        }
        ArrayTable<R, C, V> arrayTable = new ArrayTable<>(table.rowKeySet(), table.columnKeySet());
        arrayTable.putAll(table);
        return arrayTable;
    }

    public ImmutableSet<C> columnKeySet() {
        return this.j.keySet();
    }

    public ImmutableSet<R> rowKeySet() {
        return this.i.keySet();
    }

    public ArrayTable(ArrayTable<R, C, V> arrayTable) {
        ImmutableList<R> immutableList = arrayTable.g;
        this.g = immutableList;
        ImmutableList<C> immutableList2 = arrayTable.h;
        this.h = immutableList2;
        this.i = arrayTable.i;
        this.j = arrayTable.j;
        int size = immutableList.size();
        int[] iArr = new int[2];
        iArr[1] = immutableList2.size();
        iArr[0] = size;
        V[][] vArr = (Object[][]) Array.newInstance(Object.class, iArr);
        this.k = vArr;
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            V[] vArr2 = arrayTable.k[i2];
            System.arraycopy(vArr2, 0, vArr[i2], 0, vArr2.length);
        }
    }
}
