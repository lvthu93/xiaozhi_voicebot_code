package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import j$.util.Iterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

class StandardTable<R, C, V> extends AbstractTable<R, C, V> implements Serializable {
    private static final long serialVersionUID = 0;
    @GwtTransient
    public final Map<R, Map<C, V>> g;
    @GwtTransient
    public final Supplier<? extends Map<C, V>> h;
    public transient Set<C> i;
    public transient Map<R, Map<C, V>> j;
    public transient StandardTable<R, C, V>.ColumnMap k;

    public class CellIterator implements Iterator<Table.Cell<R, C, V>>, j$.util.Iterator {
        public final Iterator<Map.Entry<R, Map<C, V>>> c;
        public Map.Entry<R, Map<C, V>> f;
        public Iterator<Map.Entry<C, V>> g = Iterators.EmptyModifiableIterator.c;

        public CellIterator(StandardTable standardTable) {
            this.c = standardTable.g.entrySet().iterator();
        }

        public final /* synthetic */ void forEachRemaining(Consumer consumer) {
            Iterator.CC.$default$forEachRemaining(this, consumer);
        }

        public boolean hasNext() {
            return this.c.hasNext() || this.g.hasNext();
        }

        public void remove() {
            this.g.remove();
            if (this.f.getValue().isEmpty()) {
                this.c.remove();
                this.f = null;
            }
        }

        public Table.Cell<R, C, V> next() {
            if (!this.g.hasNext()) {
                Map.Entry<R, Map<C, V>> next = this.c.next();
                this.f = next;
                this.g = next.getValue().entrySet().iterator();
            }
            Map.Entry next2 = this.g.next();
            return Tables.immutableCell(this.f.getKey(), next2.getKey(), next2.getValue());
        }
    }

    public class Column extends Maps.ViewCachingAbstractMap<R, V> {
        public final C h;

        public class EntrySet extends Sets.ImprovedAbstractSet<Map.Entry<R, V>> {
            public EntrySet() {
            }

            public void clear() {
                Column.this.b(Predicates.alwaysTrue());
            }

            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                Column column = Column.this;
                StandardTable standardTable = StandardTable.this;
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    standardTable.getClass();
                    return false;
                } else if (value.equals(standardTable.get(key, column.h))) {
                    return true;
                } else {
                    return false;
                }
            }

            public boolean isEmpty() {
                Column column = Column.this;
                return !StandardTable.this.containsColumn(column.h);
            }

            public java.util.Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }

            /* JADX WARNING: Removed duplicated region for block: B:10:0x002a  */
            /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean remove(java.lang.Object r7) {
                /*
                    r6 = this;
                    boolean r0 = r7 instanceof java.util.Map.Entry
                    r1 = 0
                    if (r0 == 0) goto L_0x002e
                    java.util.Map$Entry r7 = (java.util.Map.Entry) r7
                    com.google.common.collect.StandardTable$Column r0 = com.google.common.collect.StandardTable.Column.this
                    com.google.common.collect.StandardTable r2 = com.google.common.collect.StandardTable.this
                    java.lang.Object r3 = r7.getKey()
                    java.lang.Object r7 = r7.getValue()
                    r4 = 1
                    C r0 = r0.h
                    if (r7 == 0) goto L_0x0024
                    java.lang.Object r5 = r2.get(r3, r0)
                    boolean r7 = r7.equals(r5)
                    if (r7 == 0) goto L_0x0027
                    r7 = 1
                    goto L_0x0028
                L_0x0024:
                    r2.getClass()
                L_0x0027:
                    r7 = 0
                L_0x0028:
                    if (r7 == 0) goto L_0x002e
                    r2.remove(r3, r0)
                    r1 = 1
                L_0x002e:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.StandardTable.Column.EntrySet.remove(java.lang.Object):boolean");
            }

            public boolean retainAll(Collection<?> collection) {
                return Column.this.b(Predicates.not(Predicates.in(collection)));
            }

            public int size() {
                Column column = Column.this;
                int i = 0;
                for (Map<C, V> containsKey : StandardTable.this.g.values()) {
                    if (containsKey.containsKey(column.h)) {
                        i++;
                    }
                }
                return i;
            }
        }

        public class EntrySetIterator extends AbstractIterator<Map.Entry<R, V>> {
            public final java.util.Iterator<Map.Entry<R, Map<C, V>>> g;

            public EntrySetIterator() {
                this.g = StandardTable.this.g.entrySet().iterator();
            }

            public final Object computeNext() {
                final Map.Entry next;
                do {
                    java.util.Iterator<Map.Entry<R, Map<C, V>>> it = this.g;
                    if (it.hasNext()) {
                        next = it.next();
                    } else {
                        this.c = AbstractIterator.State.DONE;
                        return null;
                    }
                } while (!((Map) next.getValue()).containsKey(Column.this.h));
                return new AbstractMapEntry<Object, Object>() {
                    public Object getKey() {
                        return next.getKey();
                    }

                    public Object getValue() {
                        return ((Map) next.getValue()).get(Column.this.h);
                    }

                    public Object setValue(Object obj) {
                        return ((Map) next.getValue()).put(Column.this.h, Preconditions.checkNotNull(obj));
                    }
                };
            }
        }

        public class KeySet extends Maps.KeySet<R, V> {
            public KeySet() {
                super(Column.this);
            }

            public boolean contains(Object obj) {
                Column column = Column.this;
                return StandardTable.this.contains(obj, column.h);
            }

            public boolean remove(Object obj) {
                Column column = Column.this;
                return StandardTable.this.remove(obj, column.h) != null;
            }

            public boolean retainAll(Collection<?> collection) {
                return Column.this.b(Maps.g(Predicates.not(Predicates.in(collection))));
            }
        }

        public class Values extends Maps.Values<R, V> {
            public Values() {
                super(Column.this);
            }

            public boolean remove(Object obj) {
                if (obj != null) {
                    if (Column.this.b(Maps.k(Predicates.equalTo(obj)))) {
                        return true;
                    }
                }
                return false;
            }

            public boolean removeAll(Collection<?> collection) {
                return Column.this.b(Maps.k(Predicates.in(collection)));
            }

            public boolean retainAll(Collection<?> collection) {
                return Column.this.b(Maps.k(Predicates.not(Predicates.in(collection))));
            }
        }

        public Column(C c) {
            this.h = Preconditions.checkNotNull(c);
        }

        public final Collection<V> a() {
            return new Values();
        }

        public final boolean b(Predicate<? super Map.Entry<R, V>> predicate) {
            java.util.Iterator<Map.Entry<R, Map<C, V>>> it = StandardTable.this.g.entrySet().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map.Entry next = it.next();
                Map map = (Map) next.getValue();
                C c = this.h;
                Object obj = map.get(c);
                if (obj != null && predicate.apply(Maps.immutableEntry(next.getKey(), obj))) {
                    map.remove(c);
                    if (map.isEmpty()) {
                        it.remove();
                    }
                    z = true;
                }
            }
            return z;
        }

        public boolean containsKey(Object obj) {
            return StandardTable.this.contains(obj, this.h);
        }

        public final Set<Map.Entry<R, V>> createEntrySet() {
            return new EntrySet();
        }

        public final Set<R> createKeySet() {
            return new KeySet();
        }

        public V get(Object obj) {
            return StandardTable.this.get(obj, this.h);
        }

        public V put(R r, V v) {
            return StandardTable.this.put(r, this.h, v);
        }

        public V remove(Object obj) {
            return StandardTable.this.remove(obj, this.h);
        }
    }

    public class ColumnKeyIterator extends AbstractIterator<C> {
        public final Map<C, V> g;
        public final java.util.Iterator<Map<C, V>> h;
        public java.util.Iterator<Map.Entry<C, V>> i = Iterators.ArrayItr.i;

        public ColumnKeyIterator(StandardTable standardTable) {
            this.g = (Map) standardTable.h.get();
            this.h = standardTable.g.values().iterator();
        }

        public final C computeNext() {
            while (true) {
                if (this.i.hasNext()) {
                    Map.Entry next = this.i.next();
                    Object key = next.getKey();
                    Map<C, V> map = this.g;
                    if (!map.containsKey(key)) {
                        map.put(next.getKey(), next.getValue());
                        return next.getKey();
                    }
                } else {
                    java.util.Iterator<Map<C, V>> it = this.h;
                    if (it.hasNext()) {
                        this.i = it.next().entrySet().iterator();
                    } else {
                        this.c = AbstractIterator.State.DONE;
                        return null;
                    }
                }
            }
        }
    }

    public class ColumnKeySet extends StandardTable<R, C, V>.TableSet<C> {
        public ColumnKeySet() {
            super();
        }

        public boolean contains(Object obj) {
            return StandardTable.this.containsColumn(obj);
        }

        public java.util.Iterator<C> iterator() {
            return StandardTable.this.f();
        }

        public boolean remove(Object obj) {
            boolean z = false;
            if (obj == null) {
                return false;
            }
            java.util.Iterator<Map<C, V>> it = StandardTable.this.g.values().iterator();
            while (it.hasNext()) {
                Map next = it.next();
                if (next.keySet().remove(obj)) {
                    if (next.isEmpty()) {
                        it.remove();
                    }
                    z = true;
                }
            }
            return z;
        }

        public boolean removeAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            java.util.Iterator<Map<C, V>> it = StandardTable.this.g.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map next = it.next();
                if (Iterators.removeAll(next.keySet().iterator(), collection)) {
                    if (next.isEmpty()) {
                        it.remove();
                    }
                    z = true;
                }
            }
            return z;
        }

        public boolean retainAll(Collection<?> collection) {
            Preconditions.checkNotNull(collection);
            java.util.Iterator<Map<C, V>> it = StandardTable.this.g.values().iterator();
            boolean z = false;
            while (it.hasNext()) {
                Map next = it.next();
                if (next.keySet().retainAll(collection)) {
                    if (next.isEmpty()) {
                        it.remove();
                    }
                    z = true;
                }
            }
            return z;
        }

        public int size() {
            return Iterators.size(iterator());
        }
    }

    public class ColumnMap extends Maps.ViewCachingAbstractMap<C, Map<R, V>> {

        public class ColumnMapEntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<C, Map<R, V>>> {
            public ColumnMapEntrySet() {
                super();
            }

            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                ColumnMap columnMap = ColumnMap.this;
                if (StandardTable.this.containsColumn(entry.getKey())) {
                    return columnMap.get(entry.getKey()).equals(entry.getValue());
                }
                return false;
            }

            public java.util.Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                Set columnKeySet = StandardTable.this.columnKeySet();
                return new TransformedIterator<Object, Map.Entry<Object, Object>>(columnKeySet.iterator(), new Function<C, Map<R, V>>() {
                    public Map<R, V> apply(C c2) {
                        return StandardTable.this.column(c2);
                    }
                }) {
                    public final /* synthetic */ Function f;

                    {
                        this.f = r2;
                    }

                    public final Object a(Object obj) {
                        return Maps.immutableEntry(obj, this.f.apply(obj));
                    }
                };
            }

            public boolean remove(Object obj) {
                if (!contains(obj)) {
                    return false;
                }
                StandardTable.e(StandardTable.this, ((Map.Entry) obj).getKey());
                return true;
            }

            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                return Sets.d(this, collection.iterator());
            }

            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                ColumnMap columnMap = ColumnMap.this;
                java.util.Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    StandardTable standardTable = StandardTable.this;
                    if (!collection.contains(Maps.immutableEntry(next, standardTable.column(next)))) {
                        StandardTable.e(standardTable, next);
                        z = true;
                    }
                }
                return z;
            }

            public int size() {
                return StandardTable.this.columnKeySet().size();
            }
        }

        public class ColumnMapValues extends Maps.Values<C, Map<R, V>> {
            public ColumnMapValues() {
                super(ColumnMap.this);
            }

            public boolean remove(Object obj) {
                ColumnMap columnMap = ColumnMap.this;
                for (Map.Entry entry : columnMap.entrySet()) {
                    if (((Map) entry.getValue()).equals(obj)) {
                        StandardTable.e(StandardTable.this, entry.getKey());
                        return true;
                    }
                }
                return false;
            }

            public boolean removeAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                ColumnMap columnMap = ColumnMap.this;
                java.util.Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    StandardTable standardTable = StandardTable.this;
                    if (collection.contains(standardTable.column(next))) {
                        StandardTable.e(standardTable, next);
                        z = true;
                    }
                }
                return z;
            }

            public boolean retainAll(Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                ColumnMap columnMap = ColumnMap.this;
                java.util.Iterator it = Lists.newArrayList(StandardTable.this.columnKeySet().iterator()).iterator();
                boolean z = false;
                while (it.hasNext()) {
                    Object next = it.next();
                    StandardTable standardTable = StandardTable.this;
                    if (!collection.contains(standardTable.column(next))) {
                        StandardTable.e(standardTable, next);
                        z = true;
                    }
                }
                return z;
            }
        }

        public ColumnMap() {
        }

        public final Collection<Map<R, V>> a() {
            return new ColumnMapValues();
        }

        public boolean containsKey(Object obj) {
            return StandardTable.this.containsColumn(obj);
        }

        public Set<Map.Entry<C, Map<R, V>>> createEntrySet() {
            return new ColumnMapEntrySet();
        }

        public Set<C> keySet() {
            return StandardTable.this.columnKeySet();
        }

        public Map<R, V> get(Object obj) {
            StandardTable standardTable = StandardTable.this;
            if (standardTable.containsColumn(obj)) {
                return standardTable.column(obj);
            }
            return null;
        }

        public Map<R, V> remove(Object obj) {
            StandardTable standardTable = StandardTable.this;
            if (standardTable.containsColumn(obj)) {
                return StandardTable.e(standardTable, obj);
            }
            return null;
        }
    }

    public class Row extends Maps.IteratorBasedAbstractMap<C, V> {
        public final R c;
        public Map<C, V> f;

        public Row(R r) {
            this.c = Preconditions.checkNotNull(r);
        }

        public final java.util.Iterator<Map.Entry<C, V>> a() {
            Map b = b();
            if (b == null) {
                return Iterators.EmptyModifiableIterator.c;
            }
            final java.util.Iterator it = b.entrySet().iterator();
            return new Object() {
                public final /* synthetic */ void forEachRemaining(Consumer consumer) {
                    Iterator.CC.$default$forEachRemaining(this, consumer);
                }

                public boolean hasNext() {
                    return it.hasNext();
                }

                public void remove() {
                    it.remove();
                    Row.this.d();
                }

                public Map.Entry<C, V> next() {
                    Row.this.getClass();
                    return new ForwardingMapEntry<Object, Object>((Map.Entry) it.next()) {
                        public final /* synthetic */ Map.Entry c;

                        {
                            this.c = r1;
                        }

                        public final Map.Entry<Object, Object> a() {
                            return this.c;
                        }

                        public final Object delegate() {
                            return this.c;
                        }

                        public boolean equals(Object obj) {
                            if (obj instanceof Map.Entry) {
                                Map.Entry entry = (Map.Entry) obj;
                                if (!Objects.equal(getKey(), entry.getKey()) || !Objects.equal(getValue(), entry.getValue())) {
                                    return false;
                                }
                                return true;
                            }
                            return false;
                        }

                        public Object setValue(Object obj) {
                            return super.setValue(Preconditions.checkNotNull(obj));
                        }
                    };
                }
            };
        }

        public Map<C, V> b() {
            Map<C, V> map = this.f;
            if (map != null && (!map.isEmpty() || !StandardTable.this.g.containsKey(this.c))) {
                return this.f;
            }
            Map<C, V> c2 = c();
            this.f = c2;
            return c2;
        }

        public Map<C, V> c() {
            return StandardTable.this.g.get(this.c);
        }

        public void clear() {
            Map b = b();
            if (b != null) {
                b.clear();
            }
            d();
        }

        public boolean containsKey(Object obj) {
            Map b = b();
            if (obj == null || b == null || !Maps.h(b, obj)) {
                return false;
            }
            return true;
        }

        public void d() {
            if (b() != null && this.f.isEmpty()) {
                StandardTable.this.g.remove(this.c);
                this.f = null;
            }
        }

        public V get(Object obj) {
            Map b = b();
            if (obj == null || b == null) {
                return null;
            }
            return Maps.i(b, obj);
        }

        public V put(C c2, V v) {
            Preconditions.checkNotNull(c2);
            Preconditions.checkNotNull(v);
            Map<C, V> map = this.f;
            if (map == null || map.isEmpty()) {
                return StandardTable.this.put(this.c, c2, v);
            }
            return this.f.put(c2, v);
        }

        public V remove(Object obj) {
            Map b = b();
            V v = null;
            if (b == null) {
                return null;
            }
            Preconditions.checkNotNull(b);
            try {
                v = b.remove(obj);
            } catch (ClassCastException | NullPointerException unused) {
            }
            d();
            return v;
        }

        public int size() {
            Map b = b();
            if (b == null) {
                return 0;
            }
            return b.size();
        }
    }

    public class RowMap extends Maps.ViewCachingAbstractMap<R, Map<C, V>> {

        public class EntrySet extends StandardTable<R, C, V>.TableSet<Map.Entry<R, Map<C, V>>> {
            public EntrySet() {
                super();
            }

            public boolean contains(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getKey() == null || !(entry.getValue() instanceof Map) || !Collections2.d(entry, StandardTable.this.g.entrySet())) {
                    return false;
                }
                return true;
            }

            public java.util.Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                Set<R> keySet = StandardTable.this.g.keySet();
                return new TransformedIterator<Object, Map.Entry<Object, Object>>(keySet.iterator(), new Function<R, Map<C, V>>() {
                    public Map<C, V> apply(R r) {
                        return StandardTable.this.row(r);
                    }
                }) {
                    public final /* synthetic */ Function f;

                    {
                        this.f = r2;
                    }

                    public final Object a(Object obj) {
                        return Maps.immutableEntry(obj, this.f.apply(obj));
                    }
                };
            }

            public boolean remove(Object obj) {
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getKey() == null || !(entry.getValue() instanceof Map) || !StandardTable.this.g.entrySet().remove(entry)) {
                    return false;
                }
                return true;
            }

            public int size() {
                return StandardTable.this.g.size();
            }
        }

        public RowMap() {
        }

        public boolean containsKey(Object obj) {
            return StandardTable.this.containsRow(obj);
        }

        public final Set<Map.Entry<R, Map<C, V>>> createEntrySet() {
            return new EntrySet();
        }

        public Map<C, V> get(Object obj) {
            StandardTable standardTable = StandardTable.this;
            if (standardTable.containsRow(obj)) {
                return standardTable.row(obj);
            }
            return null;
        }

        public Map<C, V> remove(Object obj) {
            if (obj == null) {
                return null;
            }
            return StandardTable.this.g.remove(obj);
        }
    }

    public abstract class TableSet<T> extends Sets.ImprovedAbstractSet<T> {
        public TableSet() {
        }

        public void clear() {
            StandardTable.this.g.clear();
        }

        public boolean isEmpty() {
            return StandardTable.this.g.isEmpty();
        }
    }

    public StandardTable(Map<R, Map<C, V>> map, Supplier<? extends Map<C, V>> supplier) {
        this.g = map;
        this.h = supplier;
    }

    public static LinkedHashMap e(StandardTable standardTable, Object obj) {
        standardTable.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        java.util.Iterator<Map.Entry<R, Map<C, V>>> it = standardTable.g.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            Object remove = ((Map) next.getValue()).remove(obj);
            if (remove != null) {
                linkedHashMap.put(next.getKey(), remove);
                if (((Map) next.getValue()).isEmpty()) {
                    it.remove();
                }
            }
        }
        return linkedHashMap;
    }

    public final java.util.Iterator<Table.Cell<R, C, V>> a() {
        return new CellIterator(this);
    }

    public Set<Table.Cell<R, C, V>> cellSet() {
        return super.cellSet();
    }

    public void clear() {
        this.g.clear();
    }

    public Map<R, V> column(C c) {
        return new Column(c);
    }

    public Set<C> columnKeySet() {
        Set<C> set = this.i;
        if (set != null) {
            return set;
        }
        ColumnKeySet columnKeySet = new ColumnKeySet();
        this.i = columnKeySet;
        return columnKeySet;
    }

    public Map<C, Map<R, V>> columnMap() {
        StandardTable<R, C, V>.ColumnMap columnMap = this.k;
        if (columnMap != null) {
            return columnMap;
        }
        StandardTable<R, C, V>.ColumnMap columnMap2 = new ColumnMap();
        this.k = columnMap2;
        return columnMap2;
    }

    public boolean contains(Object obj, Object obj2) {
        return (obj == null || obj2 == null || !super.contains(obj, obj2)) ? false : true;
    }

    public boolean containsColumn(Object obj) {
        if (obj == null) {
            return false;
        }
        for (Map<C, V> h2 : this.g.values()) {
            if (Maps.h(h2, obj)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsRow(Object obj) {
        return obj != null && Maps.h(this.g, obj);
    }

    public boolean containsValue(Object obj) {
        return obj != null && super.containsValue(obj);
    }

    public java.util.Iterator<C> f() {
        return new ColumnKeyIterator(this);
    }

    public Map<R, Map<C, V>> g() {
        return new RowMap();
    }

    public V get(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        return super.get(obj, obj2);
    }

    public boolean isEmpty() {
        return this.g.isEmpty();
    }

    public V put(R r, C c, V v) {
        Preconditions.checkNotNull(r);
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(v);
        Map<R, Map<C, V>> map = this.g;
        Map map2 = map.get(r);
        if (map2 == null) {
            map2 = (Map) this.h.get();
            map.put(r, map2);
        }
        return map2.put(c, v);
    }

    public V remove(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return null;
        }
        Map map = this.g;
        Map map2 = (Map) Maps.i(map, obj);
        if (map2 == null) {
            return null;
        }
        V remove = map2.remove(obj2);
        if (map2.isEmpty()) {
            map.remove(obj);
        }
        return remove;
    }

    public Map<C, V> row(R r) {
        return new Row(r);
    }

    public Set<R> rowKeySet() {
        return rowMap().keySet();
    }

    public Map<R, Map<C, V>> rowMap() {
        Map<R, Map<C, V>> map = this.j;
        if (map != null) {
            return map;
        }
        Map<R, Map<C, V>> g2 = g();
        this.j = g2;
        return g2;
    }

    public int size() {
        int i2 = 0;
        for (Map<C, V> size : this.g.values()) {
            i2 += size.size();
        }
        return i2;
    }

    public Collection<V> values() {
        return super.values();
    }
}
