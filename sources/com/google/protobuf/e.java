package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.p;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

public final class e extends c<Boolean> implements p.a, RandomAccess, a9 {
    public static final boolean[] h;
    public static final e i;
    public boolean[] f;
    public int g;

    static {
        boolean[] zArr = new boolean[0];
        h = zArr;
        i = new e(zArr, 0, false);
    }

    public e() {
        this(h, 0, true);
    }

    public final void add(int i2, Object obj) {
        int i3;
        boolean booleanValue = ((Boolean) obj).booleanValue();
        a();
        if (i2 < 0 || i2 > (i3 = this.g)) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
        boolean[] zArr = this.f;
        if (i3 < zArr.length) {
            System.arraycopy(zArr, i2, zArr, i2 + 1, i3 - i2);
        } else {
            boolean[] zArr2 = new boolean[y2.b(zArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, zArr2, 0, i2);
            System.arraycopy(this.f, i2, zArr2, i2 + 1, this.g - i2);
            this.f = zArr2;
        }
        this.f[i2] = booleanValue;
        this.g++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        a();
        Charset charset = p.a;
        collection.getClass();
        if (!(collection instanceof e)) {
            return super.addAll(collection);
        }
        e eVar = (e) collection;
        int i2 = eVar.g;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.g;
        if (ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - i3 >= i2) {
            int i4 = i3 + i2;
            boolean[] zArr = this.f;
            if (i4 > zArr.length) {
                this.f = Arrays.copyOf(zArr, i4);
            }
            System.arraycopy(eVar.f, 0, this.f, this.g, eVar.g);
            this.g = i4;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final void b(boolean z) {
        a();
        int i2 = this.g;
        boolean[] zArr = this.f;
        if (i2 == zArr.length) {
            boolean[] zArr2 = new boolean[y2.b(zArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, zArr2, 0, this.g);
            this.f = zArr2;
        }
        boolean[] zArr3 = this.f;
        int i3 = this.g;
        this.g = i3 + 1;
        zArr3[i3] = z;
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
        if (!(obj instanceof e)) {
            return super.equals(obj);
        }
        e eVar = (e) obj;
        if (this.g != eVar.g) {
            return false;
        }
        boolean[] zArr = eVar.f;
        for (int i2 = 0; i2 < this.g; i2++) {
            if (this.f[i2] != zArr[i2]) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: f */
    public final e h(int i2) {
        boolean[] zArr;
        if (i2 >= this.g) {
            if (i2 == 0) {
                zArr = h;
            } else {
                zArr = Arrays.copyOf(this.f, i2);
            }
            return new e(zArr, this.g, true);
        }
        throw new IllegalArgumentException();
    }

    public final Object get(int i2) {
        c(i2);
        return Boolean.valueOf(this.f[i2]);
    }

    public final int hashCode() {
        int i2;
        int i3 = 1;
        for (int i4 = 0; i4 < this.g; i4++) {
            int i5 = i3 * 31;
            boolean z = this.f[i4];
            Charset charset = p.a;
            if (z) {
                i2 = 1231;
            } else {
                i2 = 1237;
            }
            i3 = i5 + i2;
        }
        return i3;
    }

    public final int indexOf(Object obj) {
        if (!(obj instanceof Boolean)) {
            return -1;
        }
        boolean booleanValue = ((Boolean) obj).booleanValue();
        int i2 = this.g;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.f[i3] == booleanValue) {
                return i3;
            }
        }
        return -1;
    }

    public final Object remove(int i2) {
        a();
        c(i2);
        boolean[] zArr = this.f;
        boolean z = zArr[i2];
        int i3 = this.g;
        if (i2 < i3 - 1) {
            System.arraycopy(zArr, i2 + 1, zArr, i2, (i3 - i2) - 1);
        }
        this.g--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    public final void removeRange(int i2, int i3) {
        a();
        if (i3 >= i2) {
            boolean[] zArr = this.f;
            System.arraycopy(zArr, i3, zArr, i2, this.g - i3);
            this.g -= i3 - i2;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final Object set(int i2, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        a();
        c(i2);
        boolean[] zArr = this.f;
        boolean z = zArr[i2];
        zArr[i2] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final int size() {
        return this.g;
    }

    public e(boolean[] zArr, int i2, boolean z) {
        super(z);
        this.f = zArr;
        this.g = i2;
    }

    public final boolean add(Object obj) {
        b(((Boolean) obj).booleanValue());
        return true;
    }
}
