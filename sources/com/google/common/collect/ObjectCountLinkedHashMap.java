package com.google.common.collect;

import java.util.Arrays;

class ObjectCountLinkedHashMap<K> extends ObjectCountHashMap<K> {
    public transient long[] i;
    public transient int j;
    public transient int k;

    public ObjectCountLinkedHashMap() {
        this(3);
    }

    public static <K> ObjectCountLinkedHashMap<K> create() {
        return new ObjectCountLinkedHashMap<>();
    }

    public static <K> ObjectCountLinkedHashMap<K> createWithExpectedSize(int i2) {
        return new ObjectCountLinkedHashMap<>(i2);
    }

    public final int b() {
        int i2 = this.j;
        if (i2 == -2) {
            return -1;
        }
        return i2;
    }

    public void clear() {
        super.clear();
        this.j = -2;
        this.k = -2;
    }

    public final void f(int i2) {
        super.f(i2);
        this.j = -2;
        this.k = -2;
        long[] jArr = new long[i2];
        this.i = jArr;
        Arrays.fill(jArr, -1);
    }

    public final void g(int i2, int i3, int i4, Object obj) {
        super.g(i2, i3, i4, obj);
        o(this.k, i2);
        o(i2, -2);
    }

    public final void h(int i2) {
        int i3 = this.c - 1;
        long j2 = this.i[i2];
        o((int) (j2 >>> 32), (int) j2);
        if (i2 < i3) {
            o((int) (this.i[i3] >>> 32), i2);
            o(i2, (int) this.i[i3]);
        }
        super.h(i2);
    }

    public final int i(int i2) {
        int i3 = (int) this.i[i2];
        if (i3 == -2) {
            return -1;
        }
        return i3;
    }

    public final int j(int i2, int i3) {
        return i2 == this.c ? i3 : i2;
    }

    public final void m(int i2) {
        super.m(i2);
        long[] jArr = this.i;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, i2);
        this.i = copyOf;
        Arrays.fill(copyOf, length, i2, -1);
    }

    public final void o(int i2, int i3) {
        if (i2 == -2) {
            this.j = i3;
        } else {
            long[] jArr = this.i;
            jArr[i2] = (jArr[i2] & -4294967296L) | (((long) i3) & 4294967295L);
        }
        if (i3 == -2) {
            this.k = i2;
            return;
        }
        long[] jArr2 = this.i;
        jArr2[i3] = (4294967295L & jArr2[i3]) | (((long) i2) << 32);
    }

    public ObjectCountLinkedHashMap(int i2) {
        super(i2, 0);
    }

    public ObjectCountLinkedHashMap(ObjectCountHashMap<K> objectCountHashMap) {
        f(objectCountHashMap.c);
        int b = objectCountHashMap.b();
        while (b != -1) {
            put(objectCountHashMap.c(b), objectCountHashMap.d(b));
            b = objectCountHashMap.i(b);
        }
    }
}
