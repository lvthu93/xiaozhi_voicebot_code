package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.p;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

public final class o extends c<Integer> implements p.g, RandomAccess, a9 {
    public static final int[] h;
    public static final o i;
    public int[] f;
    public int g;

    static {
        int[] iArr = new int[0];
        h = iArr;
        i = new o(iArr, 0, false);
    }

    public o() {
        this(h, 0, true);
    }

    public final void add(int i2, Object obj) {
        int i3;
        int intValue = ((Integer) obj).intValue();
        a();
        if (i2 < 0 || i2 > (i3 = this.g)) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
        int[] iArr = this.f;
        if (i3 < iArr.length) {
            System.arraycopy(iArr, i2, iArr, i2 + 1, i3 - i2);
        } else {
            int[] iArr2 = new int[y2.b(iArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, iArr2, 0, i2);
            System.arraycopy(this.f, i2, iArr2, i2 + 1, this.g - i2);
            this.f = iArr2;
        }
        this.f[i2] = intValue;
        this.g++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        a();
        Charset charset = p.a;
        collection.getClass();
        if (!(collection instanceof o)) {
            return super.addAll(collection);
        }
        o oVar = (o) collection;
        int i2 = oVar.g;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.g;
        if (ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - i3 >= i2) {
            int i4 = i3 + i2;
            int[] iArr = this.f;
            if (i4 > iArr.length) {
                this.f = Arrays.copyOf(iArr, i4);
            }
            System.arraycopy(oVar.f, 0, this.f, this.g, oVar.g);
            this.g = i4;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final void b(int i2) {
        a();
        int i3 = this.g;
        int[] iArr = this.f;
        if (i3 == iArr.length) {
            int[] iArr2 = new int[y2.b(iArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, iArr2, 0, this.g);
            this.f = iArr2;
        }
        int[] iArr3 = this.f;
        int i4 = this.g;
        this.g = i4 + 1;
        iArr3[i4] = i2;
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
        if (!(obj instanceof o)) {
            return super.equals(obj);
        }
        o oVar = (o) obj;
        if (this.g != oVar.g) {
            return false;
        }
        int[] iArr = oVar.f;
        for (int i2 = 0; i2 < this.g; i2++) {
            if (this.f[i2] != iArr[i2]) {
                return false;
            }
        }
        return true;
    }

    public final int f(int i2) {
        c(i2);
        return this.f[i2];
    }

    /* renamed from: g */
    public final o h(int i2) {
        int[] iArr;
        if (i2 >= this.g) {
            if (i2 == 0) {
                iArr = h;
            } else {
                iArr = Arrays.copyOf(this.f, i2);
            }
            return new o(iArr, this.g, true);
        }
        throw new IllegalArgumentException();
    }

    public final Object get(int i2) {
        return Integer.valueOf(f(i2));
    }

    public final int hashCode() {
        int i2 = 1;
        for (int i3 = 0; i3 < this.g; i3++) {
            i2 = (i2 * 31) + this.f[i3];
        }
        return i2;
    }

    public final int indexOf(Object obj) {
        if (!(obj instanceof Integer)) {
            return -1;
        }
        int intValue = ((Integer) obj).intValue();
        int i2 = this.g;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.f[i3] == intValue) {
                return i3;
            }
        }
        return -1;
    }

    public final Object remove(int i2) {
        a();
        c(i2);
        int[] iArr = this.f;
        int i3 = iArr[i2];
        int i4 = this.g;
        if (i2 < i4 - 1) {
            System.arraycopy(iArr, i2 + 1, iArr, i2, (i4 - i2) - 1);
        }
        this.g--;
        this.modCount++;
        return Integer.valueOf(i3);
    }

    public final void removeRange(int i2, int i3) {
        a();
        if (i3 >= i2) {
            int[] iArr = this.f;
            System.arraycopy(iArr, i3, iArr, i2, this.g - i3);
            this.g -= i3 - i2;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final Object set(int i2, Object obj) {
        int intValue = ((Integer) obj).intValue();
        a();
        c(i2);
        int[] iArr = this.f;
        int i3 = iArr[i2];
        iArr[i2] = intValue;
        return Integer.valueOf(i3);
    }

    public final int size() {
        return this.g;
    }

    public o(int[] iArr, int i2, boolean z) {
        super(z);
        this.f = iArr;
        this.g = i2;
    }

    public final boolean add(Object obj) {
        b(((Integer) obj).intValue());
        return true;
    }
}
