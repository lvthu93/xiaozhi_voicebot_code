package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.mozilla.javascript.ES6Iterator;

public abstract class ImmutableTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {

    public static final class Builder<R, C, V> {
        public final ArrayList a = Lists.newArrayList();
        public Comparator<? super R> b;
        public Comparator<? super C> c;

        public ImmutableTable<R, C, V> build() {
            ImmutableSet<E> immutableSet;
            ImmutableSet<E> immutableSet2;
            ArrayList<Table.Cell> arrayList = this.a;
            int size = arrayList.size();
            if (size == 0) {
                return ImmutableTable.of();
            }
            if (size != 1) {
                Comparator<? super R> comparator = this.b;
                Comparator<? super C> comparator2 = this.c;
                Preconditions.checkNotNull(arrayList);
                if (!(comparator == null && comparator2 == null)) {
                    Collections.sort(arrayList, new Comparator<Table.Cell<Object, Object, Object>>(comparator, comparator2) {
                        public final /* synthetic */ Comparator c;
                        public final /* synthetic */ Comparator f;

                        {
                            this.c = r1;
                            this.f = r2;
                        }

                        public int compare(Table.Cell<Object, Object, Object> cell, Table.Cell<Object, Object, Object> cell2) {
                            int i;
                            Comparator comparator = this.c;
                            if (comparator == null) {
                                i = 0;
                            } else {
                                i = comparator.compare(cell.getRowKey(), cell2.getRowKey());
                            }
                            if (i != 0) {
                                return i;
                            }
                            Comparator comparator2 = this.f;
                            if (comparator2 == null) {
                                return 0;
                            }
                            return comparator2.compare(cell.getColumnKey(), cell2.getColumnKey());
                        }
                    });
                }
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                ImmutableList copyOf = ImmutableList.copyOf(arrayList);
                for (Table.Cell cell : arrayList) {
                    linkedHashSet.add(cell.getRowKey());
                    linkedHashSet2.add(cell.getColumnKey());
                }
                if (comparator == null) {
                    immutableSet = ImmutableSet.copyOf(linkedHashSet);
                } else {
                    immutableSet = ImmutableSet.copyOf(ImmutableList.sortedCopyOf(comparator, linkedHashSet));
                }
                if (comparator2 == null) {
                    immutableSet2 = ImmutableSet.copyOf(linkedHashSet2);
                } else {
                    immutableSet2 = ImmutableSet.copyOf(ImmutableList.sortedCopyOf(comparator2, linkedHashSet2));
                }
                return RegularImmutableTable.i(copyOf, immutableSet, immutableSet2);
            }
            Table.Cell cell2 = (Table.Cell) Iterables.getOnlyElement(arrayList);
            return new SingletonImmutableTable(cell2.getRowKey(), cell2.getColumnKey(), cell2.getValue());
        }

        public Builder<R, C, V> orderColumnsBy(Comparator<? super C> comparator) {
            this.c = (Comparator) Preconditions.checkNotNull(comparator, "columnComparator");
            return this;
        }

        public Builder<R, C, V> orderRowsBy(Comparator<? super R> comparator) {
            this.b = (Comparator) Preconditions.checkNotNull(comparator, "rowComparator");
            return this;
        }

        public Builder<R, C, V> put(R r, C c2, V v) {
            this.a.add(ImmutableTable.e(r, c2, v));
            return this;
        }

        public Builder<R, C, V> putAll(Table<? extends R, ? extends C, ? extends V> table) {
            for (Table.Cell<? extends R, ? extends C, ? extends V> put : table.cellSet()) {
                put(put);
            }
            return this;
        }

        public Builder<R, C, V> put(Table.Cell<? extends R, ? extends C, ? extends V> cell) {
            if (cell instanceof Tables.ImmutableCell) {
                Preconditions.checkNotNull(cell.getRowKey(), "row");
                Preconditions.checkNotNull(cell.getColumnKey(), "column");
                Preconditions.checkNotNull(cell.getValue(), ES6Iterator.VALUE_PROPERTY);
                this.a.add(cell);
            } else {
                put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return this;
        }
    }

    public static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        public final Object[] c;
        public final Object[] f;
        public final Object[] g;
        public final int[] h;
        public final int[] i;

        public SerializedForm(Object[] objArr, Object[] objArr2, Object[] objArr3, int[] iArr, int[] iArr2) {
            this.c = objArr;
            this.f = objArr2;
            this.g = objArr3;
            this.h = iArr;
            this.i = iArr2;
        }

        public static SerializedForm a(ImmutableTable<?, ?, ?> immutableTable, int[] iArr, int[] iArr2) {
            return new SerializedForm(immutableTable.rowKeySet().toArray(), immutableTable.columnKeySet().toArray(), immutableTable.values().toArray(), iArr, iArr2);
        }

        public Object readResolve() {
            Object[] objArr = this.g;
            if (objArr.length == 0) {
                return ImmutableTable.of();
            }
            int length = objArr.length;
            Object[] objArr2 = this.f;
            Object[] objArr3 = this.c;
            if (length == 1) {
                return ImmutableTable.of(objArr3[0], objArr2[0], objArr[0]);
            }
            ImmutableList.Builder builder = new ImmutableList.Builder(objArr.length);
            for (int i2 = 0; i2 < objArr.length; i2++) {
                builder.add((Object) ImmutableTable.e(objArr3[this.h[i2]], objArr2[this.i[i2]], objArr[i2]));
            }
            return RegularImmutableTable.i(builder.build(), ImmutableSet.copyOf((E[]) objArr3), ImmutableSet.copyOf((E[]) objArr2));
        }
    }

    public static <R, C, V> Builder<R, C, V> builder() {
        return new Builder<>();
    }

    public static <R, C, V> ImmutableTable<R, C, V> copyOf(Table<? extends R, ? extends C, ? extends V> table) {
        if (table instanceof ImmutableTable) {
            return (ImmutableTable) table;
        }
        Set<Table.Cell<? extends R, ? extends C, ? extends V>> cellSet = table.cellSet();
        Builder builder = builder();
        for (Table.Cell put : cellSet) {
            builder.put(put);
        }
        return builder.build();
    }

    public static <R, C, V> Table.Cell<R, C, V> e(R r, C c, V v) {
        return Tables.immutableCell(Preconditions.checkNotNull(r, "rowKey"), Preconditions.checkNotNull(c, "columnKey"), Preconditions.checkNotNull(v, ES6Iterator.VALUE_PROPERTY));
    }

    public static <R, C, V> ImmutableTable<R, C, V> of() {
        return SparseImmutableTable.k;
    }

    public final Iterator a() {
        throw new AssertionError("should never be called");
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public abstract ImmutableMap<C, Map<R, V>> columnMap();

    public boolean contains(Object obj, Object obj2) {
        return get(obj, obj2) != null;
    }

    public /* bridge */ /* synthetic */ boolean containsColumn(Object obj) {
        return super.containsColumn(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsRow(Object obj) {
        return super.containsRow(obj);
    }

    public boolean containsValue(Object obj) {
        return values().contains(obj);
    }

    public final Iterator<V> d() {
        throw new AssertionError("should never be called");
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* renamed from: f */
    public abstract ImmutableSet<Table.Cell<R, C, V>> b();

    public abstract SerializedForm g();

    public /* bridge */ /* synthetic */ Object get(Object obj, Object obj2) {
        return super.get(obj, obj2);
    }

    /* renamed from: h */
    public abstract ImmutableCollection<V> c();

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    @Deprecated
    public final V put(R r, C c, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final V remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    public abstract ImmutableMap<R, Map<C, V>> rowMap();

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public final Object writeReplace() {
        return g();
    }

    public static <R, C, V> ImmutableTable<R, C, V> of(R r, C c, V v) {
        return new SingletonImmutableTable(r, c, v);
    }

    public ImmutableSet<Table.Cell<R, C, V>> cellSet() {
        return (ImmutableSet) super.cellSet();
    }

    public ImmutableMap<R, V> column(C c) {
        Preconditions.checkNotNull(c, "columnKey");
        return (ImmutableMap) MoreObjects.firstNonNull((ImmutableMap) columnMap().get(c), ImmutableMap.of());
    }

    public ImmutableSet<C> columnKeySet() {
        return columnMap().keySet();
    }

    public ImmutableMap<C, V> row(R r) {
        Preconditions.checkNotNull(r, "rowKey");
        return (ImmutableMap) MoreObjects.firstNonNull((ImmutableMap) rowMap().get(r), ImmutableMap.of());
    }

    public ImmutableSet<R> rowKeySet() {
        return rowMap().keySet();
    }

    public ImmutableCollection<V> values() {
        return (ImmutableCollection) super.values();
    }
}
