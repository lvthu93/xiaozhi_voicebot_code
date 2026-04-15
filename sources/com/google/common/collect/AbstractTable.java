package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Table;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class AbstractTable<R, C, V> implements Table<R, C, V> {
    public transient Set<Table.Cell<R, C, V>> c;
    public transient Collection<V> f;

    public class CellSet extends AbstractSet<Table.Cell<R, C, V>> {
        public CellSet() {
        }

        public void clear() {
            AbstractTable.this.clear();
        }

        public boolean contains(Object obj) {
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            Map map = (Map) Maps.i(AbstractTable.this.rowMap(), cell.getRowKey());
            if (map == null) {
                return false;
            }
            if (Collections2.d(Maps.immutableEntry(cell.getColumnKey(), cell.getValue()), map.entrySet())) {
                return true;
            }
            return false;
        }

        public Iterator<Table.Cell<R, C, V>> iterator() {
            return AbstractTable.this.a();
        }

        public boolean remove(Object obj) {
            boolean z;
            if (!(obj instanceof Table.Cell)) {
                return false;
            }
            Table.Cell cell = (Table.Cell) obj;
            Map map = (Map) Maps.i(AbstractTable.this.rowMap(), cell.getRowKey());
            if (map == null) {
                return false;
            }
            Set entrySet = map.entrySet();
            Map.Entry immutableEntry = Maps.immutableEntry(cell.getColumnKey(), cell.getValue());
            Preconditions.checkNotNull(entrySet);
            try {
                z = entrySet.remove(immutableEntry);
            } catch (ClassCastException | NullPointerException unused) {
                z = false;
            }
            if (z) {
                return true;
            }
            return false;
        }

        public int size() {
            return AbstractTable.this.size();
        }
    }

    public class Values extends AbstractCollection<V> {
        public Values() {
        }

        public void clear() {
            AbstractTable.this.clear();
        }

        public boolean contains(Object obj) {
            return AbstractTable.this.containsValue(obj);
        }

        public Iterator<V> iterator() {
            return AbstractTable.this.d();
        }

        public int size() {
            return AbstractTable.this.size();
        }
    }

    public abstract Iterator<Table.Cell<R, C, V>> a();

    public Set<Table.Cell<R, C, V>> b() {
        return new CellSet();
    }

    public Collection<V> c() {
        return new Values();
    }

    public Set<Table.Cell<R, C, V>> cellSet() {
        Set<Table.Cell<R, C, V>> set = this.c;
        if (set != null) {
            return set;
        }
        Set<Table.Cell<R, C, V>> b = b();
        this.c = b;
        return b;
    }

    public void clear() {
        Iterators.b(cellSet().iterator());
    }

    public Set<C> columnKeySet() {
        return columnMap().keySet();
    }

    public boolean contains(Object obj, Object obj2) {
        Map map = (Map) Maps.i(rowMap(), obj);
        if (map == null || !Maps.h(map, obj2)) {
            return false;
        }
        return true;
    }

    public boolean containsColumn(Object obj) {
        return Maps.h(columnMap(), obj);
    }

    public boolean containsRow(Object obj) {
        return Maps.h(rowMap(), obj);
    }

    public boolean containsValue(Object obj) {
        for (Map containsValue : rowMap().values()) {
            if (containsValue.containsValue(obj)) {
                return true;
            }
        }
        return false;
    }

    public Iterator<V> d() {
        return new TransformedIterator<Table.Cell<R, C, V>, V>(cellSet().iterator()) {
            public final Object a(Object obj) {
                return ((Table.Cell) obj).getValue();
            }
        };
    }

    public boolean equals(Object obj) {
        Function<? extends Map<?, ?>, ? extends Map<?, ?>> function = Tables.a;
        if (obj == this) {
            return true;
        }
        if (obj instanceof Table) {
            return cellSet().equals(((Table) obj).cellSet());
        }
        return false;
    }

    public V get(Object obj, Object obj2) {
        Map map = (Map) Maps.i(rowMap(), obj);
        if (map == null) {
            return null;
        }
        return Maps.i(map, obj2);
    }

    public int hashCode() {
        return cellSet().hashCode();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public V put(R r, C c2, V v) {
        return row(r).put(c2, v);
    }

    public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
        for (Table.Cell next : table.cellSet()) {
            put(next.getRowKey(), next.getColumnKey(), next.getValue());
        }
    }

    public V remove(Object obj, Object obj2) {
        Map map = (Map) Maps.i(rowMap(), obj);
        if (map == null) {
            return null;
        }
        Preconditions.checkNotNull(map);
        try {
            return map.remove(obj2);
        } catch (ClassCastException | NullPointerException unused) {
            return null;
        }
    }

    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    public String toString() {
        return rowMap().toString();
    }

    public Collection<V> values() {
        Collection<V> collection = this.f;
        if (collection != null) {
            return collection;
        }
        Collection<V> c2 = c();
        this.f = c2;
        return c2;
    }
}
