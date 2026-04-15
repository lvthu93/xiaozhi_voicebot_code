package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

final class CartesianList<E> extends AbstractList<List<E>> implements RandomAccess {
    public static final /* synthetic */ int g = 0;
    public final transient ImmutableList<List<E>> c;
    public final transient int[] f;

    public CartesianList(ImmutableList<List<E>> immutableList) {
        boolean z;
        this.c = immutableList;
        int[] iArr = new int[(immutableList.size() + 1)];
        iArr[immutableList.size()] = 1;
        try {
            for (int size = immutableList.size() - 1; size >= 0; size--) {
                int i = iArr[size + 1];
                int size2 = immutableList.get(size).size();
                long j = ((long) i) * ((long) size2);
                int i2 = (int) j;
                if (j == ((long) i2)) {
                    z = true;
                } else {
                    z = false;
                }
                cg.d(z, "checkedMultiply", i, size2);
                iArr[size] = i2;
            }
            this.f = iArr;
        } catch (ArithmeticException unused) {
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
        }
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public int indexOf(Object obj) {
        if (!(obj instanceof List)) {
            return -1;
        }
        List list = (List) obj;
        int size = list.size();
        ImmutableList<List<E>> immutableList = this.c;
        if (size != immutableList.size()) {
            return -1;
        }
        ListIterator listIterator = list.listIterator();
        int i = 0;
        while (listIterator.hasNext()) {
            int nextIndex = listIterator.nextIndex();
            int indexOf = immutableList.get(nextIndex).indexOf(listIterator.next());
            if (indexOf == -1) {
                return -1;
            }
            i += indexOf * this.f[nextIndex + 1];
        }
        return i;
    }

    public int size() {
        return this.f[0];
    }

    public ImmutableList<E> get(final int i) {
        Preconditions.checkElementIndex(i, size());
        return new ImmutableList<E>() {
            public E get(int i) {
                Preconditions.checkElementIndex(i, size());
                CartesianList cartesianList = CartesianList.this;
                return cartesianList.c.get(i).get((i / cartesianList.f[i + 1]) % cartesianList.c.get(i).size());
            }

            public final boolean isPartialView() {
                return true;
            }

            public int size() {
                return CartesianList.this.c.size();
            }
        };
    }
}
