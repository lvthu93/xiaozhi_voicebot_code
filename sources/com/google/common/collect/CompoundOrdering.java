package com.google.common.collect;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

final class CompoundOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    public final Comparator<? super T>[] c;

    public CompoundOrdering(Comparator<? super T> comparator, Comparator<? super T> comparator2) {
        this.c = (Comparator[]) new Comparator[]{comparator, comparator2};
    }

    public int compare(T t, T t2) {
        int i = 0;
        while (true) {
            Comparator<? super T>[] comparatorArr = this.c;
            if (i >= comparatorArr.length) {
                return 0;
            }
            int compare = comparatorArr[i].compare(t, t2);
            if (compare != 0) {
                return compare;
            }
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CompoundOrdering) {
            return Arrays.equals(this.c, ((CompoundOrdering) obj).c);
        }
        return false;
    }

    public int hashCode() {
        return Arrays.hashCode(this.c);
    }

    public String toString() {
        return y2.k(new StringBuilder("Ordering.compound("), Arrays.toString(this.c), ")");
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [java.lang.Iterable<? extends java.util.Comparator<? super T>>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public CompoundOrdering(java.lang.Iterable<? extends java.util.Comparator<? super T>> r3) {
        /*
            r2 = this;
            r2.<init>()
            r0 = 0
            java.util.Comparator[] r0 = new java.util.Comparator[r0]
            boolean r1 = r3 instanceof java.util.Collection
            if (r1 == 0) goto L_0x000d
            java.util.Collection r3 = (java.util.Collection) r3
            goto L_0x0015
        L_0x000d:
            java.util.Iterator r3 = r3.iterator()
            java.util.ArrayList r3 = com.google.common.collect.Lists.newArrayList(r3)
        L_0x0015:
            java.lang.Object[] r3 = r3.toArray(r0)
            java.util.Comparator[] r3 = (java.util.Comparator[]) r3
            r2.c = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.CompoundOrdering.<init>(java.lang.Iterable):void");
    }
}
