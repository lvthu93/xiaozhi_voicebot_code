package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Synchronized;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public final class Tables {
    public static final Function<? extends Map<?, ?>, ? extends Map<?, ?>> a = new Function<Map<Object, Object>, Map<Object, Object>>() {
        public Map<Object, Object> apply(Map<Object, Object> map) {
            return Collections.unmodifiableMap(map);
        }
    };

    public static abstract class AbstractCell<R, C, V> implements Table.Cell<R, C, V> {
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            if (!Objects.equal(getRowKey(), cell.getRowKey()) || !Objects.equal(getColumnKey(), cell.getColumnKey()) || !Objects.equal(getValue(), cell.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(getRowKey(), getColumnKey(), getValue());
        }

        public String toString() {
            return "(" + getRowKey() + "," + getColumnKey() + ")=" + getValue();
        }
    }

    public static final class ImmutableCell<R, C, V> extends AbstractCell<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final R c;
        public final C f;
        public final V g;

        public ImmutableCell(R r, C c2, V v) {
            this.c = r;
            this.f = c2;
            this.g = v;
        }

        public C getColumnKey() {
            return this.f;
        }

        public R getRowKey() {
            return this.c;
        }

        public V getValue() {
            return this.g;
        }
    }

    public static class TransformedTable<R, C, V1, V2> extends AbstractTable<R, C, V2> {
        public final Table<R, C, V1> g;
        public final Function<? super V1, V2> h;

        public TransformedTable(Table<R, C, V1> table, Function<? super V1, V2> function) {
            this.g = (Table) Preconditions.checkNotNull(table);
            this.h = (Function) Preconditions.checkNotNull(function);
        }

        public final Iterator<Table.Cell<R, C, V2>> a() {
            return Iterators.transform(this.g.cellSet().iterator(), new Function<Table.Cell<Object, Object, Object>, Table.Cell<Object, Object, Object>>() {
                public Table.Cell<Object, Object, Object> apply(Table.Cell<Object, Object, Object> cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), TransformedTable.this.h.apply(cell.getValue()));
                }
            });
        }

        public final Collection<V2> c() {
            return Collections2.transform(this.g.values(), this.h);
        }

        public void clear() {
            this.g.clear();
        }

        public Map<R, V2> column(C c) {
            return Maps.transformValues(this.g.column(c), this.h);
        }

        public Set<C> columnKeySet() {
            return this.g.columnKeySet();
        }

        public Map<C, Map<R, V2>> columnMap() {
            return Maps.transformValues(this.g.columnMap(), new Function<Map<R, V1>, Map<R, V2>>() {
                public Map<R, V2> apply(Map<R, V1> map) {
                    return Maps.transformValues(map, TransformedTable.this.h);
                }
            });
        }

        public boolean contains(Object obj, Object obj2) {
            return this.g.contains(obj, obj2);
        }

        public V2 get(Object obj, Object obj2) {
            if (!contains(obj, obj2)) {
                return null;
            }
            return this.h.apply(this.g.get(obj, obj2));
        }

        public V2 put(R r, C c, V2 v2) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }

        public V2 remove(Object obj, Object obj2) {
            if (!contains(obj, obj2)) {
                return null;
            }
            return this.h.apply(this.g.remove(obj, obj2));
        }

        public Map<C, V2> row(R r) {
            return Maps.transformValues(this.g.row(r), this.h);
        }

        public Set<R> rowKeySet() {
            return this.g.rowKeySet();
        }

        public Map<R, Map<C, V2>> rowMap() {
            return Maps.transformValues(this.g.rowMap(), new Function<Map<C, V1>, Map<C, V2>>() {
                public Map<C, V2> apply(Map<C, V1> map) {
                    return Maps.transformValues(map, TransformedTable.this.h);
                }
            });
        }

        public int size() {
            return this.g.size();
        }
    }

    public static class TransposeTable<C, R, V> extends AbstractTable<C, R, V> {
        public static final Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>> h = new Function<Table.Cell<?, ?, ?>, Table.Cell<?, ?, ?>>() {
            public Table.Cell<?, ?, ?> apply(Table.Cell<?, ?, ?> cell) {
                return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
            }
        };
        public final Table<R, C, V> g;

        public TransposeTable(Table<R, C, V> table) {
            this.g = (Table) Preconditions.checkNotNull(table);
        }

        public final Iterator<Table.Cell<C, R, V>> a() {
            return Iterators.transform(this.g.cellSet().iterator(), h);
        }

        public void clear() {
            this.g.clear();
        }

        public Map<C, V> column(R r) {
            return this.g.row(r);
        }

        public Set<R> columnKeySet() {
            return this.g.rowKeySet();
        }

        public Map<R, Map<C, V>> columnMap() {
            return this.g.rowMap();
        }

        public boolean contains(Object obj, Object obj2) {
            return this.g.contains(obj2, obj);
        }

        public boolean containsColumn(Object obj) {
            return this.g.containsRow(obj);
        }

        public boolean containsRow(Object obj) {
            return this.g.containsColumn(obj);
        }

        public boolean containsValue(Object obj) {
            return this.g.containsValue(obj);
        }

        public V get(Object obj, Object obj2) {
            return this.g.get(obj2, obj);
        }

        public V put(C c, R r, V v) {
            return this.g.put(r, c, v);
        }

        public void putAll(Table<? extends C, ? extends R, ? extends V> table) {
            this.g.putAll(Tables.transpose(table));
        }

        public V remove(Object obj, Object obj2) {
            return this.g.remove(obj2, obj);
        }

        public Map<R, V> row(C c) {
            return this.g.column(c);
        }

        public Set<C> rowKeySet() {
            return this.g.columnKeySet();
        }

        public Map<C, Map<R, V>> rowMap() {
            return this.g.columnMap();
        }

        public int size() {
            return this.g.size();
        }

        public Collection<V> values() {
            return this.g.values();
        }
    }

    public static final class UnmodifiableRowSortedMap<R, C, V> extends UnmodifiableTable<R, C, V> implements RowSortedTable<R, C, V> {
        private static final long serialVersionUID = 0;

        public UnmodifiableRowSortedMap(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
            super(rowSortedTable);
        }

        public final Table a() {
            return (RowSortedTable) this.c;
        }

        public final Object delegate() {
            return (RowSortedTable) this.c;
        }

        public SortedSet<R> rowKeySet() {
            return Collections.unmodifiableSortedSet(((RowSortedTable) this.c).rowKeySet());
        }

        public SortedMap<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableSortedMap(Maps.transformValues(((RowSortedTable) this.c).rowMap(), Tables.a));
        }
    }

    public static class UnmodifiableTable<R, C, V> extends ForwardingTable<R, C, V> implements Serializable {
        private static final long serialVersionUID = 0;
        public final Table<? extends R, ? extends C, ? extends V> c;

        public UnmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
            this.c = (Table) Preconditions.checkNotNull(table);
        }

        /* renamed from: a */
        public Table<R, C, V> delegate() {
            return this.c;
        }

        public Set<Table.Cell<R, C, V>> cellSet() {
            return Collections.unmodifiableSet(super.cellSet());
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public Map<R, V> column(C c2) {
            return Collections.unmodifiableMap(super.column(c2));
        }

        public Set<C> columnKeySet() {
            return Collections.unmodifiableSet(super.columnKeySet());
        }

        public Map<C, Map<R, V>> columnMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.columnMap(), Tables.a));
        }

        public V put(R r, C c2, V v) {
            throw new UnsupportedOperationException();
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            throw new UnsupportedOperationException();
        }

        public V remove(Object obj, Object obj2) {
            throw new UnsupportedOperationException();
        }

        public Map<C, V> row(R r) {
            return Collections.unmodifiableMap(super.row(r));
        }

        public Set<R> rowKeySet() {
            return Collections.unmodifiableSet(super.rowKeySet());
        }

        public Map<R, Map<C, V>> rowMap() {
            return Collections.unmodifiableMap(Maps.transformValues(super.rowMap(), Tables.a));
        }

        public Collection<V> values() {
            return Collections.unmodifiableCollection(super.values());
        }
    }

    public static <R, C, V> Table.Cell<R, C, V> immutableCell(R r, C c, V v) {
        return new ImmutableCell(r, c, v);
    }

    public static <R, C, V> Table<R, C, V> newCustomTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkNotNull(supplier);
        return new StandardTable(map, supplier);
    }

    public static <R, C, V> Table<R, C, V> synchronizedTable(Table<R, C, V> table) {
        return new Synchronized.SynchronizedTable(table);
    }

    public static <R, C, V1, V2> Table<R, C, V2> transformValues(Table<R, C, V1> table, Function<? super V1, V2> function) {
        return new TransformedTable(table, function);
    }

    public static <R, C, V> Table<C, R, V> transpose(Table<R, C, V> table) {
        return table instanceof TransposeTable ? ((TransposeTable) table).g : new TransposeTable(table);
    }

    public static <R, C, V> RowSortedTable<R, C, V> unmodifiableRowSortedTable(RowSortedTable<R, ? extends C, ? extends V> rowSortedTable) {
        return new UnmodifiableRowSortedMap(rowSortedTable);
    }

    public static <R, C, V> Table<R, C, V> unmodifiableTable(Table<? extends R, ? extends C, ? extends V> table) {
        return new UnmodifiableTable(table);
    }
}
