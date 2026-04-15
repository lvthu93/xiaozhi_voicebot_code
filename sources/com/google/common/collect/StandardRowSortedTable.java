package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

class StandardRowSortedTable<R, C, V> extends StandardTable<R, C, V> implements RowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0;

    public class RowSortedMap extends StandardTable<R, C, V>.RowMap implements SortedMap<R, Map<C, V>> {
        public RowSortedMap() {
            super();
        }

        public Comparator<? super R> comparator() {
            return ((SortedMap) StandardRowSortedTable.this.g).comparator();
        }

        public final Set createKeySet() {
            return new Maps.SortedKeySet(this);
        }

        public R firstKey() {
            return ((SortedMap) StandardRowSortedTable.this.g).firstKey();
        }

        public SortedMap<R, Map<C, V>> headMap(R r) {
            Preconditions.checkNotNull(r);
            StandardRowSortedTable standardRowSortedTable = StandardRowSortedTable.this;
            return new StandardRowSortedTable(((SortedMap) standardRowSortedTable.g).headMap(r), standardRowSortedTable.h).rowMap();
        }

        public R lastKey() {
            return ((SortedMap) StandardRowSortedTable.this.g).lastKey();
        }

        public SortedMap<R, Map<C, V>> subMap(R r, R r2) {
            Preconditions.checkNotNull(r);
            Preconditions.checkNotNull(r2);
            StandardRowSortedTable standardRowSortedTable = StandardRowSortedTable.this;
            return new StandardRowSortedTable(((SortedMap) standardRowSortedTable.g).subMap(r, r2), standardRowSortedTable.h).rowMap();
        }

        public SortedMap<R, Map<C, V>> tailMap(R r) {
            Preconditions.checkNotNull(r);
            StandardRowSortedTable standardRowSortedTable = StandardRowSortedTable.this;
            return new StandardRowSortedTable(((SortedMap) standardRowSortedTable.g).tailMap(r), standardRowSortedTable.h).rowMap();
        }

        public SortedSet<R> keySet() {
            return (SortedSet) super.keySet();
        }
    }

    public StandardRowSortedTable(SortedMap<R, Map<C, V>> sortedMap, Supplier<? extends Map<C, V>> supplier) {
        super(sortedMap, supplier);
    }

    public final Map g() {
        return new RowSortedMap();
    }

    public SortedSet<R> rowKeySet() {
        return (SortedSet) rowMap().keySet();
    }

    public SortedMap<R, Map<C, V>> rowMap() {
        return (SortedMap) super.rowMap();
    }
}
