package com.google.common.collect;

final class RegularImmutableSet<E> extends ImmutableSet<E> {
    public static final RegularImmutableSet<Object> l = new RegularImmutableSet(new Object[0], 0, (Object[]) null, 0, 0);
    public final transient Object[] g;
    public final transient Object[] h;
    public final transient int i;
    public final transient int j;
    public final transient int k;

    public RegularImmutableSet(Object[] objArr, int i2, Object[] objArr2, int i3, int i4) {
        this.g = objArr;
        this.h = objArr2;
        this.i = i3;
        this.j = i2;
        this.k = i4;
    }

    public final int a(int i2, Object[] objArr) {
        Object[] objArr2 = this.g;
        int i3 = this.k;
        System.arraycopy(objArr2, 0, objArr, i2, i3);
        return i2 + i3;
    }

    public final Object[] b() {
        return this.g;
    }

    public final int c() {
        return this.k;
    }

    public boolean contains(Object obj) {
        Object[] objArr;
        if (obj == null || (objArr = this.h) == null) {
            return false;
        }
        int c = Hashing.c(obj);
        while (true) {
            int i2 = c & this.i;
            Object obj2 = objArr[i2];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            c = i2 + 1;
        }
    }

    public final int f() {
        return 0;
    }

    public int hashCode() {
        return this.j;
    }

    public final boolean isPartialView() {
        return false;
    }

    public final ImmutableList<E> k() {
        return ImmutableList.g(this.k, this.g);
    }

    public final boolean l() {
        return true;
    }

    public int size() {
        return this.k;
    }

    public UnmodifiableIterator<E> iterator() {
        return asList().iterator();
    }
}
