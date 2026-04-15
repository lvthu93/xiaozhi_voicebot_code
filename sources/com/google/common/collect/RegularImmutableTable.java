package com.google.common.collect;

import com.google.common.collect.Table;

abstract class RegularImmutableTable<R, C, V> extends ImmutableTable<R, C, V> {

    public final class CellSet extends IndexedImmutableSet<Table.Cell<R, C, V>> {
        public CellSet() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:2:0x0005, code lost:
            r5 = (com.google.common.collect.Table.Cell) r5;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean contains(java.lang.Object r5) {
            /*
                r4 = this;
                boolean r0 = r5 instanceof com.google.common.collect.Table.Cell
                r1 = 0
                if (r0 == 0) goto L_0x0022
                com.google.common.collect.Table$Cell r5 = (com.google.common.collect.Table.Cell) r5
                java.lang.Object r0 = r5.getRowKey()
                java.lang.Object r2 = r5.getColumnKey()
                com.google.common.collect.RegularImmutableTable r3 = com.google.common.collect.RegularImmutableTable.this
                java.lang.Object r0 = r3.get(r0, r2)
                if (r0 == 0) goto L_0x0022
                java.lang.Object r5 = r5.getValue()
                boolean r5 = r0.equals(r5)
                if (r5 == 0) goto L_0x0022
                r1 = 1
            L_0x0022:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableTable.CellSet.contains(java.lang.Object):boolean");
        }

        public final Object get(int i) {
            return RegularImmutableTable.this.j(i);
        }

        public final boolean isPartialView() {
            return false;
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }
    }

    public final class Values extends ImmutableList<V> {
        public Values() {
        }

        public V get(int i) {
            return RegularImmutableTable.this.k(i);
        }

        public final boolean isPartialView() {
            return true;
        }

        public int size() {
            return RegularImmutableTable.this.size();
        }
    }

    public static <R, C, V> RegularImmutableTable<R, C, V> i(ImmutableList<Table.Cell<R, C, V>> immutableList, ImmutableSet<R> immutableSet, ImmutableSet<C> immutableSet2) {
        return ((long) immutableList.size()) > (((long) immutableSet.size()) * ((long) immutableSet2.size())) / 2 ? new DenseImmutableTable(immutableList, immutableSet, immutableSet2) : new SparseImmutableTable(immutableList, immutableSet, immutableSet2);
    }

    /* renamed from: f */
    public final ImmutableSet<Table.Cell<R, C, V>> b() {
        return isEmpty() ? ImmutableSet.of() : new CellSet();
    }

    /* renamed from: h */
    public final ImmutableCollection<V> c() {
        return isEmpty() ? ImmutableList.of() : new Values();
    }

    public abstract Table.Cell<R, C, V> j(int i);

    public abstract V k(int i);
}
