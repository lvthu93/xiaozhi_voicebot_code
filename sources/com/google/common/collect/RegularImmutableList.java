package com.google.common.collect;

import com.google.common.base.Preconditions;

class RegularImmutableList<E> extends ImmutableList<E> {
    public static final ImmutableList<Object> i = new RegularImmutableList(new Object[0], 0);
    public final transient Object[] g;
    public final transient int h;

    public RegularImmutableList(Object[] objArr, int i2) {
        this.g = objArr;
        this.h = i2;
    }

    public final int a(int i2, Object[] objArr) {
        Object[] objArr2 = this.g;
        int i3 = this.h;
        System.arraycopy(objArr2, 0, objArr, i2, i3);
        return i2 + i3;
    }

    public final Object[] b() {
        return this.g;
    }

    public final int c() {
        return this.h;
    }

    public final int f() {
        return 0;
    }

    public E get(int i2) {
        Preconditions.checkElementIndex(i2, this.h);
        return this.g[i2];
    }

    public final boolean isPartialView() {
        return false;
    }

    public int size() {
        return this.h;
    }
}
