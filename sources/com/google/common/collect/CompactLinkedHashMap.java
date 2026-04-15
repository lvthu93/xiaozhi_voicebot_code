package com.google.common.collect;

import java.util.Arrays;

class CompactLinkedHashMap<K, V> extends CompactHashMap<K, V> {
    public transient long[] q;
    public transient int r;
    public transient int s;
    public final boolean t;

    public CompactLinkedHashMap() {
        this(3);
    }

    public static <K, V> CompactLinkedHashMap<K, V> create() {
        return new CompactLinkedHashMap<>();
    }

    public static <K, V> CompactLinkedHashMap<K, V> createWithExpectedSize(int i) {
        return new CompactLinkedHashMap<>(i);
    }

    public final void b(int i) {
        if (this.t) {
            long j = this.q[i];
            m((int) (j >>> 32), (int) j);
            m(this.s, i);
            m(i, -2);
            this.j++;
        }
    }

    public final int c(int i, int i2) {
        return i >= size() ? i2 : i;
    }

    public void clear() {
        super.clear();
        this.r = -2;
        this.s = -2;
    }

    public final int d() {
        return this.r;
    }

    public final int e(int i) {
        return (int) this.q[i];
    }

    public final void g(int i) {
        super.g(i);
        this.r = -2;
        this.s = -2;
        long[] jArr = new long[i];
        this.q = jArr;
        Arrays.fill(jArr, -1);
    }

    public final void h(int i, K k, V v, int i2) {
        super.h(i, k, v, i2);
        m(this.s, i);
        m(i, -2);
    }

    public final void i(int i) {
        int size = size() - 1;
        long j = this.q[i];
        m((int) (j >>> 32), (int) j);
        if (i < size) {
            m((int) (this.q[size] >>> 32), i);
            m(i, (int) this.q[size]);
        }
        super.i(i);
    }

    public final void k(int i) {
        super.k(i);
        this.q = Arrays.copyOf(this.q, i);
    }

    public final void m(int i, int i2) {
        if (i == -2) {
            this.r = i2;
        } else {
            long[] jArr = this.q;
            jArr[i] = (jArr[i] & -4294967296L) | (((long) i2) & 4294967295L);
        }
        if (i2 == -2) {
            this.s = i;
            return;
        }
        long[] jArr2 = this.q;
        jArr2[i2] = (4294967295L & jArr2[i2]) | (((long) i) << 32);
    }

    public CompactLinkedHashMap(int i) {
        super(i);
        this.t = false;
    }
}
