package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.p;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

public final class u extends c<Long> implements p.h, RandomAccess, a9 {
    public static final long[] h;
    public static final u i;
    public long[] f;
    public int g;

    static {
        long[] jArr = new long[0];
        h = jArr;
        i = new u(jArr, 0, false);
    }

    public u() {
        this(h, 0, true);
    }

    public final void add(int i2, Object obj) {
        int i3;
        long longValue = ((Long) obj).longValue();
        a();
        if (i2 < 0 || i2 > (i3 = this.g)) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
        long[] jArr = this.f;
        if (i3 < jArr.length) {
            System.arraycopy(jArr, i2, jArr, i2 + 1, i3 - i2);
        } else {
            long[] jArr2 = new long[y2.b(jArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, jArr2, 0, i2);
            System.arraycopy(this.f, i2, jArr2, i2 + 1, this.g - i2);
            this.f = jArr2;
        }
        this.f[i2] = longValue;
        this.g++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Long> collection) {
        a();
        Charset charset = p.a;
        collection.getClass();
        if (!(collection instanceof u)) {
            return super.addAll(collection);
        }
        u uVar = (u) collection;
        int i2 = uVar.g;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.g;
        if (ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - i3 >= i2) {
            int i4 = i3 + i2;
            long[] jArr = this.f;
            if (i4 > jArr.length) {
                this.f = Arrays.copyOf(jArr, i4);
            }
            System.arraycopy(uVar.f, 0, this.f, this.g, uVar.g);
            this.g = i4;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final void b(long j) {
        a();
        int i2 = this.g;
        long[] jArr = this.f;
        if (i2 == jArr.length) {
            long[] jArr2 = new long[y2.b(jArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, jArr2, 0, this.g);
            this.f = jArr2;
        }
        long[] jArr3 = this.f;
        int i3 = this.g;
        this.g = i3 + 1;
        jArr3[i3] = j;
    }

    public final void c(int i2) {
        if (i2 < 0 || i2 >= this.g) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
    }

    public final boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof u)) {
            return super.equals(obj);
        }
        u uVar = (u) obj;
        if (this.g != uVar.g) {
            return false;
        }
        long[] jArr = uVar.f;
        for (int i2 = 0; i2 < this.g; i2++) {
            if (this.f[i2] != jArr[i2]) {
                return false;
            }
        }
        return true;
    }

    public final long f(int i2) {
        c(i2);
        return this.f[i2];
    }

    /* renamed from: g */
    public final u h(int i2) {
        long[] jArr;
        if (i2 >= this.g) {
            if (i2 == 0) {
                jArr = h;
            } else {
                jArr = Arrays.copyOf(this.f, i2);
            }
            return new u(jArr, this.g, true);
        }
        throw new IllegalArgumentException();
    }

    public final Object get(int i2) {
        return Long.valueOf(f(i2));
    }

    public final int hashCode() {
        int i2 = 1;
        for (int i3 = 0; i3 < this.g; i3++) {
            i2 = (i2 * 31) + p.a(this.f[i3]);
        }
        return i2;
    }

    public final int indexOf(Object obj) {
        if (!(obj instanceof Long)) {
            return -1;
        }
        long longValue = ((Long) obj).longValue();
        int i2 = this.g;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.f[i3] == longValue) {
                return i3;
            }
        }
        return -1;
    }

    public final Object remove(int i2) {
        a();
        c(i2);
        long[] jArr = this.f;
        long j = jArr[i2];
        int i3 = this.g;
        if (i2 < i3 - 1) {
            System.arraycopy(jArr, i2 + 1, jArr, i2, (i3 - i2) - 1);
        }
        this.g--;
        this.modCount++;
        return Long.valueOf(j);
    }

    public final void removeRange(int i2, int i3) {
        a();
        if (i3 >= i2) {
            long[] jArr = this.f;
            System.arraycopy(jArr, i3, jArr, i2, this.g - i3);
            this.g -= i3 - i2;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final Object set(int i2, Object obj) {
        long longValue = ((Long) obj).longValue();
        a();
        c(i2);
        long[] jArr = this.f;
        long j = jArr[i2];
        jArr[i2] = longValue;
        return Long.valueOf(j);
    }

    public final int size() {
        return this.g;
    }

    public u(long[] jArr, int i2, boolean z) {
        super(z);
        this.f = jArr;
        this.g = i2;
    }

    public final boolean add(Object obj) {
        b(((Long) obj).longValue());
        return true;
    }
}
