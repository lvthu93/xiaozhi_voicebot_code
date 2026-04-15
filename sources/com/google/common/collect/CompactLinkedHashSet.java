package com.google.common.collect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

class CompactLinkedHashSet<E> extends CompactHashSet<E> {
    public transient int[] l;
    public transient int[] m;
    public transient int n;
    public transient int o;

    public CompactLinkedHashSet() {
    }

    public static <E> CompactLinkedHashSet<E> create() {
        return new CompactLinkedHashSet<>();
    }

    public static <E> CompactLinkedHashSet<E> createWithExpectedSize(int i) {
        return new CompactLinkedHashSet<>(i);
    }

    public final int a(int i, int i2) {
        return i == size() ? i2 : i;
    }

    public final int b() {
        return this.n;
    }

    public final int c(int i) {
        return this.m[i];
    }

    public void clear() {
        super.clear();
        this.n = -2;
        this.o = -2;
        Arrays.fill(this.l, -1);
        Arrays.fill(this.m, -1);
    }

    public final void f(int i) {
        super.f(i);
        int[] iArr = new int[i];
        this.l = iArr;
        this.m = new int[i];
        Arrays.fill(iArr, -1);
        Arrays.fill(this.m, -1);
        this.n = -2;
        this.o = -2;
    }

    public final void g(int i, int i2, Object obj) {
        super.g(i, i2, obj);
        o(this.o, i);
        o(i, -2);
    }

    public final void j(int i) {
        int size = size() - 1;
        super.j(i);
        o(this.l[i], this.m[i]);
        if (size != i) {
            o(this.l[size], i);
            o(i, this.m[size]);
        }
        this.l[size] = -1;
        this.m[size] = -1;
    }

    public final void l(int i) {
        super.l(i);
        int[] iArr = this.l;
        int length = iArr.length;
        this.l = Arrays.copyOf(iArr, i);
        this.m = Arrays.copyOf(this.m, i);
        if (length < i) {
            Arrays.fill(this.l, length, i, -1);
            Arrays.fill(this.m, length, i, -1);
        }
    }

    public final void o(int i, int i2) {
        if (i == -2) {
            this.n = i2;
        } else {
            this.m[i] = i2;
        }
        if (i2 == -2) {
            this.o = i;
        } else {
            this.l[i2] = i;
        }
    }

    public Object[] toArray() {
        Object[] objArr = new Object[size()];
        ObjectArrays.b(this, objArr);
        return objArr;
    }

    public CompactLinkedHashSet(int i) {
        super(i);
    }

    public static <E> CompactLinkedHashSet<E> create(Collection<? extends E> collection) {
        CompactLinkedHashSet<E> createWithExpectedSize = createWithExpectedSize(collection.size());
        createWithExpectedSize.addAll(collection);
        return createWithExpectedSize;
    }

    public <T> T[] toArray(T[] tArr) {
        return ObjectArrays.c(this, tArr);
    }

    public static <E> CompactLinkedHashSet<E> create(E... eArr) {
        CompactLinkedHashSet<E> createWithExpectedSize = createWithExpectedSize(eArr.length);
        Collections.addAll(createWithExpectedSize, eArr);
        return createWithExpectedSize;
    }
}
