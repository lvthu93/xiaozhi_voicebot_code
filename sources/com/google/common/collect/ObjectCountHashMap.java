package com.google.common.collect;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multisets;
import java.util.Arrays;

class ObjectCountHashMap<K> {
    public transient Object[] a;
    public transient int[] b;
    public transient int c;
    public transient int d;
    public transient int[] e;
    public transient long[] f;
    public transient float g;
    public transient int h;

    public class MapEntry extends Multisets.AbstractEntry<K> {
        public final K c;
        public int f;

        public MapEntry(int i) {
            this.c = ObjectCountHashMap.this.a[i];
            this.f = i;
        }

        public final void a() {
            int i = this.f;
            K k = this.c;
            ObjectCountHashMap objectCountHashMap = ObjectCountHashMap.this;
            if (i == -1 || i >= objectCountHashMap.c || !Objects.equal(k, objectCountHashMap.a[i])) {
                this.f = objectCountHashMap.e(k);
            }
        }

        public int getCount() {
            a();
            int i = this.f;
            if (i == -1) {
                return 0;
            }
            return ObjectCountHashMap.this.b[i];
        }

        public K getElement() {
            return this.c;
        }

        public int setCount(int i) {
            a();
            int i2 = this.f;
            ObjectCountHashMap objectCountHashMap = ObjectCountHashMap.this;
            if (i2 == -1) {
                objectCountHashMap.put(this.c, i);
                return 0;
            }
            int[] iArr = objectCountHashMap.b;
            int i3 = iArr[i2];
            iArr[i2] = i;
            return i3;
        }
    }

    public ObjectCountHashMap() {
        f(3);
    }

    public static <K> ObjectCountHashMap<K> create() {
        return new ObjectCountHashMap<>();
    }

    public static <K> ObjectCountHashMap<K> createWithExpectedSize(int i) {
        return new ObjectCountHashMap<>(i);
    }

    public final void a(int i) {
        if (i > this.f.length) {
            m(i);
        }
        if (i >= this.h) {
            n(Math.max(2, Integer.highestOneBit(i - 1) << 1));
        }
    }

    public int b() {
        return this.c == 0 ? -1 : 0;
    }

    public final K c(int i) {
        Preconditions.checkElementIndex(i, this.c);
        return this.a[i];
    }

    public void clear() {
        this.d++;
        Arrays.fill(this.a, 0, this.c, (Object) null);
        Arrays.fill(this.b, 0, this.c, 0);
        Arrays.fill(this.e, -1);
        Arrays.fill(this.f, -1);
        this.c = 0;
    }

    public boolean containsKey(Object obj) {
        return e(obj) != -1;
    }

    public final int d(int i) {
        Preconditions.checkElementIndex(i, this.c);
        return this.b[i];
    }

    public final int e(Object obj) {
        int c2 = Hashing.c(obj);
        int[] iArr = this.e;
        int i = iArr[(iArr.length - 1) & c2];
        while (i != -1) {
            long j = this.f[i];
            if (((int) (j >>> 32)) == c2 && Objects.equal(obj, this.a[i])) {
                return i;
            }
            i = (int) j;
        }
        return -1;
    }

    public void f(int i) {
        boolean z;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Initial capacity must be non-negative");
        Preconditions.checkArgument(true, "Illegal load factor");
        int a2 = Hashing.a(i, (double) 1.0f);
        int[] iArr = new int[a2];
        Arrays.fill(iArr, -1);
        this.e = iArr;
        this.g = 1.0f;
        this.a = new Object[i];
        this.b = new int[i];
        long[] jArr = new long[i];
        Arrays.fill(jArr, -1);
        this.f = jArr;
        this.h = Math.max(1, (int) (((float) a2) * 1.0f));
    }

    public void g(int i, int i2, int i3, Object obj) {
        this.f[i] = (((long) i3) << 32) | 4294967295L;
        this.a[i] = obj;
        this.b[i] = i2;
    }

    public int get(Object obj) {
        int e2 = e(obj);
        if (e2 == -1) {
            return 0;
        }
        return this.b[e2];
    }

    public void h(int i) {
        int i2 = this.c - 1;
        if (i < i2) {
            Object[] objArr = this.a;
            objArr[i] = objArr[i2];
            int[] iArr = this.b;
            iArr[i] = iArr[i2];
            objArr[i2] = null;
            iArr[i2] = 0;
            long[] jArr = this.f;
            long j = jArr[i2];
            jArr[i] = j;
            jArr[i2] = -1;
            int[] iArr2 = this.e;
            int length = ((int) (j >>> 32)) & (iArr2.length - 1);
            int i3 = iArr2[length];
            if (i3 == i2) {
                iArr2[length] = i;
                return;
            }
            while (true) {
                long[] jArr2 = this.f;
                long j2 = jArr2[i3];
                int i4 = (int) j2;
                if (i4 == i2) {
                    jArr2[i3] = (j2 & -4294967296L) | (4294967295L & ((long) i));
                    return;
                }
                i3 = i4;
            }
        } else {
            this.a[i] = null;
            this.b[i] = 0;
            this.f[i] = -1;
        }
    }

    public int i(int i) {
        int i2 = i + 1;
        if (i2 < this.c) {
            return i2;
        }
        return -1;
    }

    public int j(int i, int i2) {
        return i - 1;
    }

    public final int k(Object obj, int i) {
        int[] iArr = this.e;
        int length = (iArr.length - 1) & i;
        int i2 = iArr[length];
        if (i2 == -1) {
            return 0;
        }
        int i3 = -1;
        while (true) {
            if (((int) (this.f[i2] >>> 32)) != i || !Objects.equal(obj, this.a[i2])) {
                int i4 = (int) this.f[i2];
                if (i4 == -1) {
                    return 0;
                }
                int i5 = i4;
                i3 = i2;
                i2 = i5;
            } else {
                int i6 = this.b[i2];
                if (i3 == -1) {
                    this.e[length] = (int) this.f[i2];
                } else {
                    long[] jArr = this.f;
                    jArr[i3] = (jArr[i3] & -4294967296L) | (4294967295L & ((long) ((int) jArr[i2])));
                }
                h(i2);
                this.c--;
                this.d++;
                return i6;
            }
        }
    }

    public final int l(int i) {
        return k(this.a[i], (int) (this.f[i] >>> 32));
    }

    public void m(int i) {
        this.a = Arrays.copyOf(this.a, i);
        this.b = Arrays.copyOf(this.b, i);
        long[] jArr = this.f;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, i);
        if (i > length) {
            Arrays.fill(copyOf, length, i, -1);
        }
        this.f = copyOf;
    }

    public final void n(int i) {
        if (this.e.length >= 1073741824) {
            this.h = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            return;
        }
        int i2 = ((int) (((float) i) * this.g)) + 1;
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        long[] jArr = this.f;
        int i3 = i - 1;
        for (int i4 = 0; i4 < this.c; i4++) {
            int i5 = (int) (jArr[i4] >>> 32);
            int i6 = i5 & i3;
            int i7 = iArr[i6];
            iArr[i6] = i4;
            jArr[i4] = (((long) i5) << 32) | (((long) i7) & 4294967295L);
        }
        this.h = i2;
        this.e = iArr;
    }

    public int put(K k, int i) {
        CollectPreconditions.d(i, "count");
        long[] jArr = this.f;
        Object[] objArr = this.a;
        int[] iArr = this.b;
        int c2 = Hashing.c(k);
        int[] iArr2 = this.e;
        int length = (iArr2.length - 1) & c2;
        int i2 = this.c;
        int i3 = iArr2[length];
        if (i3 == -1) {
            iArr2[length] = i2;
        } else {
            while (true) {
                long j = jArr[i3];
                if (((int) (j >>> 32)) != c2 || !Objects.equal(k, objArr[i3])) {
                    int i4 = (int) j;
                    if (i4 == -1) {
                        jArr[i3] = (-4294967296L & j) | (4294967295L & ((long) i2));
                        break;
                    }
                    i3 = i4;
                } else {
                    int i5 = iArr[i3];
                    iArr[i3] = i;
                    return i5;
                }
            }
        }
        int i6 = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        if (i2 != Integer.MAX_VALUE) {
            int i7 = i2 + 1;
            int length2 = this.f.length;
            if (i7 > length2) {
                int max = Math.max(1, length2 >>> 1) + length2;
                if (max >= 0) {
                    i6 = max;
                }
                if (i6 != length2) {
                    m(i6);
                }
            }
            g(i2, i, c2, k);
            this.c = i7;
            if (i2 >= this.h) {
                n(this.e.length * 2);
            }
            this.d++;
            return 0;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    public int remove(Object obj) {
        return k(obj, Hashing.c(obj));
    }

    public ObjectCountHashMap(ObjectCountHashMap<? extends K> objectCountHashMap) {
        f(objectCountHashMap.c);
        int b2 = objectCountHashMap.b();
        while (b2 != -1) {
            put(objectCountHashMap.c(b2), objectCountHashMap.d(b2));
            b2 = objectCountHashMap.i(b2);
        }
    }

    public ObjectCountHashMap(int i) {
        this(i, 0);
    }

    public ObjectCountHashMap(int i, int i2) {
        f(i);
    }
}
