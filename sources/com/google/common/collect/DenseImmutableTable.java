package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import java.lang.reflect.Array;
import java.util.Map;

final class DenseImmutableTable<R, C, V> extends RegularImmutableTable<R, C, V> {
    public final ImmutableMap<R, Integer> g;
    public final ImmutableMap<C, Integer> h;
    public final ImmutableMap<R, ImmutableMap<C, V>> i;
    public final ImmutableMap<C, ImmutableMap<R, V>> j;
    public final int[] k;
    public final int[] l;
    public final V[][] m;
    public final int[] n;
    public final int[] o;

    public final class Column extends ImmutableArrayMap<R, V> {
        public final int k;

        public Column(int i) {
            super(DenseImmutableTable.this.l[i]);
            this.k = i;
        }

        public final boolean f() {
            return true;
        }

        public final V i(int i) {
            return DenseImmutableTable.this.m[i][this.k];
        }

        public final ImmutableMap<R, Integer> j() {
            return DenseImmutableTable.this.g;
        }
    }

    public final class ColumnMap extends ImmutableArrayMap<C, ImmutableMap<R, V>> {
        public ColumnMap() {
            super(DenseImmutableTable.this.l.length);
        }

        public final boolean f() {
            return false;
        }

        public final Object i(int i) {
            return new Column(i);
        }

        public final ImmutableMap<C, Integer> j() {
            return DenseImmutableTable.this.h;
        }
    }

    public static abstract class ImmutableArrayMap<K, V> extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
        public final int j;

        public ImmutableArrayMap(int i) {
            this.j = i;
        }

        public final ImmutableSet<K> b() {
            boolean z;
            if (this.j == j().size()) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return j().keySet();
            }
            return new ImmutableMapKeySet(this);
        }

        public V get(Object obj) {
            Integer num = (Integer) j().get(obj);
            if (num == null) {
                return null;
            }
            return i(num.intValue());
        }

        public final UnmodifiableIterator<Map.Entry<K, V>> h() {
            return new AbstractIterator<Map.Entry<K, V>>() {
                public int g = -1;
                public final int h;

                {
                    this.h = ImmutableArrayMap.this.j().size();
                }

                public final Object computeNext() {
                    ImmutableArrayMap immutableArrayMap;
                    Object i2;
                    do {
                        int i3 = this.g + 1;
                        this.g = i3;
                        if (i3 < this.h) {
                            immutableArrayMap = ImmutableArrayMap.this;
                            i2 = immutableArrayMap.i(i3);
                        } else {
                            this.c = AbstractIterator.State.DONE;
                            return null;
                        }
                    } while (i2 == null);
                    return Maps.immutableEntry(immutableArrayMap.j().keySet().asList().get(this.g), i2);
                }
            };
        }

        public abstract V i(int i);

        public abstract ImmutableMap<K, Integer> j();

        public int size() {
            return this.j;
        }
    }

    public final class Row extends ImmutableArrayMap<C, V> {
        public final int k;

        public Row(int i) {
            super(DenseImmutableTable.this.k[i]);
            this.k = i;
        }

        public final boolean f() {
            return true;
        }

        public final V i(int i) {
            return DenseImmutableTable.this.m[this.k][i];
        }

        public final ImmutableMap<C, Integer> j() {
            return DenseImmutableTable.this.h;
        }
    }

    public final class RowMap extends ImmutableArrayMap<R, ImmutableMap<C, V>> {
        public RowMap() {
            super(DenseImmutableTable.this.k.length);
        }

        public final boolean f() {
            return false;
        }

        public final Object i(int i) {
            return new Row(i);
        }

        public final ImmutableMap<R, Integer> j() {
            return DenseImmutableTable.this.g;
        }
    }

    public DenseImmutableTable(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        boolean z;
        int size = immutableSet.size();
        int[] iArr = new int[2];
        iArr[1] = immutableSet2.size();
        iArr[0] = size;
        this.m = (Object[][]) Array.newInstance(Object.class, iArr);
        ImmutableMap<R, Integer> e = Maps.e(immutableSet);
        this.g = e;
        ImmutableMap<C, Integer> e2 = Maps.e(immutableSet2);
        this.h = e2;
        this.k = new int[e.size()];
        this.l = new int[e2.size()];
        int[] iArr2 = new int[immutableList.size()];
        int[] iArr3 = new int[immutableList.size()];
        for (int i2 = 0; i2 < immutableList.size(); i2++) {
            Table.Cell cell = immutableList.get(i2);
            Object rowKey = cell.getRowKey();
            Object columnKey = cell.getColumnKey();
            int intValue = this.g.get(rowKey).intValue();
            int intValue2 = this.h.get(columnKey).intValue();
            V v = this.m[intValue][intValue2];
            Object value = cell.getValue();
            if (v == null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkArgument(z, "Duplicate key: (row=%s, column=%s), values: [%s, %s].", rowKey, columnKey, value, v);
            this.m[intValue][intValue2] = cell.getValue();
            int[] iArr4 = this.k;
            iArr4[intValue] = iArr4[intValue] + 1;
            int[] iArr5 = this.l;
            iArr5[intValue2] = iArr5[intValue2] + 1;
            iArr2[i2] = intValue;
            iArr3[i2] = intValue2;
        }
        this.n = iArr2;
        this.o = iArr3;
        this.i = new RowMap();
        this.j = new ColumnMap();
    }

    public final ImmutableTable.SerializedForm g() {
        return ImmutableTable.SerializedForm.a(this, this.n, this.o);
    }

    public V get(Object obj, Object obj2) {
        Integer num = this.g.get(obj);
        Integer num2 = this.h.get(obj2);
        if (num == null || num2 == null) {
            return null;
        }
        return this.m[num.intValue()][num2.intValue()];
    }

    public final Table.Cell<R, C, V> j(int i2) {
        int i3 = this.n[i2];
        int i4 = this.o[i2];
        return ImmutableTable.e(rowKeySet().asList().get(i3), columnKeySet().asList().get(i4), this.m[i3][i4]);
    }

    public final V k(int i2) {
        return this.m[this.n[i2]][this.o[i2]];
    }

    public int size() {
        return this.n.length;
    }

    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.copyOf(this.j);
    }

    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.copyOf(this.i);
    }
}
