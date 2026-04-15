package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

public class TreeBasedTable<R, C, V> extends StandardRowSortedTable<R, C, V> {
    private static final long serialVersionUID = 0;
    public final Comparator<? super C> l;

    public static class Factory<C, V> implements Supplier<TreeMap<C, V>>, Serializable {
        private static final long serialVersionUID = 0;
        public final Comparator<? super C> c;

        public Factory(Comparator<? super C> comparator) {
            this.c = comparator;
        }

        public TreeMap<C, V> get() {
            return new TreeMap<>(this.c);
        }
    }

    public class TreeRow extends StandardTable<R, C, V>.Row implements SortedMap<C, V> {
        public final C h;
        public final C i;
        public transient SortedMap<C, V> j;

        public TreeRow() {
            throw null;
        }

        public TreeRow(R r, C c, C c2) {
            super(r);
            this.h = c;
            this.i = c2;
            Preconditions.checkArgument(c == null || c2 == null || comparator().compare(c, c2) <= 0);
        }

        public final Map b() {
            return (SortedMap) super.b();
        }

        public final Map c() {
            SortedMap f = f();
            if (f == null) {
                return null;
            }
            C c = this.h;
            if (c != null) {
                f = f.tailMap(c);
            }
            C c2 = this.i;
            if (c2 != null) {
                return f.headMap(c2);
            }
            return f;
        }

        public Comparator<? super C> comparator() {
            return TreeBasedTable.this.columnComparator();
        }

        public boolean containsKey(Object obj) {
            return e(obj) && super.containsKey(obj);
        }

        public final void d() {
            if (f() != null && this.j.isEmpty()) {
                TreeBasedTable.this.g.remove(this.c);
                this.j = null;
                this.f = null;
            }
        }

        public final boolean e(Object obj) {
            C c;
            C c2;
            if (obj == null || (((c = this.h) != null && comparator().compare(c, obj) > 0) || ((c2 = this.i) != null && comparator().compare(c2, obj) <= 0))) {
                return false;
            }
            return true;
        }

        public final SortedMap<C, V> f() {
            SortedMap<C, V> sortedMap = this.j;
            R r = this.c;
            TreeBasedTable treeBasedTable = TreeBasedTable.this;
            if (sortedMap == null || (sortedMap.isEmpty() && treeBasedTable.g.containsKey(r))) {
                this.j = (SortedMap) treeBasedTable.g.get(r);
            }
            return this.j;
        }

        public C firstKey() {
            if (((SortedMap) super.b()) != null) {
                return ((SortedMap) super.b()).firstKey();
            }
            throw new NoSuchElementException();
        }

        public SortedMap<C, V> headMap(C c) {
            Preconditions.checkArgument(e(Preconditions.checkNotNull(c)));
            return new TreeRow(this.c, this.h, c);
        }

        public C lastKey() {
            if (((SortedMap) super.b()) != null) {
                return ((SortedMap) super.b()).lastKey();
            }
            throw new NoSuchElementException();
        }

        public V put(C c, V v) {
            Preconditions.checkArgument(e(Preconditions.checkNotNull(c)));
            return super.put(c, v);
        }

        public SortedMap<C, V> subMap(C c, C c2) {
            boolean z;
            if (!e(Preconditions.checkNotNull(c)) || !e(Preconditions.checkNotNull(c2))) {
                z = false;
            } else {
                z = true;
            }
            Preconditions.checkArgument(z);
            return new TreeRow(this.c, c, c2);
        }

        public SortedMap<C, V> tailMap(C c) {
            Preconditions.checkArgument(e(Preconditions.checkNotNull(c)));
            return new TreeRow(this.c, c, this.i);
        }

        public SortedSet<C> keySet() {
            return new Maps.SortedKeySet(this);
        }
    }

    public TreeBasedTable(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        super(new TreeMap(comparator), new Factory(comparator2));
        this.l = comparator2;
    }

    public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
        return new TreeBasedTable<>(Ordering.natural(), Ordering.natural());
    }

    public /* bridge */ /* synthetic */ Set cellSet() {
        return super.cellSet();
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ Map column(Object obj) {
        return super.column(obj);
    }

    @Deprecated
    public Comparator<? super C> columnComparator() {
        return this.l;
    }

    public /* bridge */ /* synthetic */ Set columnKeySet() {
        return super.columnKeySet();
    }

    public /* bridge */ /* synthetic */ Map columnMap() {
        return super.columnMap();
    }

    public /* bridge */ /* synthetic */ boolean contains(Object obj, Object obj2) {
        return super.contains(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean containsColumn(Object obj) {
        return super.containsColumn(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsRow(Object obj) {
        return super.containsRow(obj);
    }

    public /* bridge */ /* synthetic */ boolean containsValue(Object obj) {
        return super.containsValue(obj);
    }

    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final Iterator<C> f() {
        final Comparator columnComparator = columnComparator();
        final UnmodifiableIterator<T> mergeSorted = Iterators.mergeSorted(Iterables.transform(this.g.values(), new Function<Map<C, V>, Iterator<C>>() {
            public Iterator<C> apply(Map<C, V> map) {
                return map.keySet().iterator();
            }
        }), columnComparator);
        return new AbstractIterator<C>() {
            public C g;

            public final C computeNext() {
                C next;
                boolean z;
                do {
                    Iterator it = mergeSorted;
                    if (it.hasNext()) {
                        next = it.next();
                        C c = this.g;
                        if (c == null || columnComparator.compare(next, c) != 0) {
                            z = false;
                            continue;
                        } else {
                            z = true;
                            continue;
                        }
                    } else {
                        this.g = null;
                        this.c = AbstractIterator.State.DONE;
                        return null;
                    }
                } while (z);
                this.g = next;
                return next;
            }
        };
    }

    public /* bridge */ /* synthetic */ Object get(Object obj, Object obj2) {
        return super.get(obj, obj2);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2, Object obj3) {
        return super.put(obj, obj2, obj3);
    }

    public /* bridge */ /* synthetic */ void putAll(Table table) {
        super.putAll(table);
    }

    public /* bridge */ /* synthetic */ Object remove(Object obj, Object obj2) {
        return super.remove(obj, obj2);
    }

    @Deprecated
    public Comparator<? super R> rowComparator() {
        return rowKeySet().comparator();
    }

    public /* bridge */ /* synthetic */ int size() {
        return super.size();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ Collection values() {
        return super.values();
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(Comparator<? super R> comparator, Comparator<? super C> comparator2) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(comparator2);
        return new TreeBasedTable<>(comparator, comparator2);
    }

    public SortedMap<C, V> row(R r) {
        return new TreeRow(r, null, null);
    }

    public SortedSet<R> rowKeySet() {
        return super.rowKeySet();
    }

    public SortedMap<R, Map<C, V>> rowMap() {
        return super.rowMap();
    }

    public static <R, C, V> TreeBasedTable<R, C, V> create(TreeBasedTable<R, C, ? extends V> treeBasedTable) {
        TreeBasedTable<R, C, V> treeBasedTable2 = new TreeBasedTable<>(treeBasedTable.rowComparator(), treeBasedTable.columnComparator());
        treeBasedTable2.putAll(treeBasedTable);
        return treeBasedTable2;
    }
}
