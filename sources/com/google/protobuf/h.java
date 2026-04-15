package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.p;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

public final class h extends c<Double> implements p.b, RandomAccess, a9 {
    public static final double[] h;
    public static final h i;
    public double[] f;
    public int g;

    static {
        double[] dArr = new double[0];
        h = dArr;
        i = new h(dArr, 0, false);
    }

    public h() {
        this(h, 0, true);
    }

    public final void add(int i2, Object obj) {
        int i3;
        double doubleValue = ((Double) obj).doubleValue();
        a();
        if (i2 < 0 || i2 > (i3 = this.g)) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
        double[] dArr = this.f;
        if (i3 < dArr.length) {
            System.arraycopy(dArr, i2, dArr, i2 + 1, i3 - i2);
        } else {
            double[] dArr2 = new double[y2.b(dArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, dArr2, 0, i2);
            System.arraycopy(this.f, i2, dArr2, i2 + 1, this.g - i2);
            this.f = dArr2;
        }
        this.f[i2] = doubleValue;
        this.g++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        a();
        Charset charset = p.a;
        collection.getClass();
        if (!(collection instanceof h)) {
            return super.addAll(collection);
        }
        h hVar = (h) collection;
        int i2 = hVar.g;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.g;
        if (ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - i3 >= i2) {
            int i4 = i3 + i2;
            double[] dArr = this.f;
            if (i4 > dArr.length) {
                this.f = Arrays.copyOf(dArr, i4);
            }
            System.arraycopy(hVar.f, 0, this.f, this.g, hVar.g);
            this.g = i4;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final void b(double d) {
        a();
        int i2 = this.g;
        double[] dArr = this.f;
        if (i2 == dArr.length) {
            double[] dArr2 = new double[y2.b(dArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, dArr2, 0, this.g);
            this.f = dArr2;
        }
        double[] dArr3 = this.f;
        int i3 = this.g;
        this.g = i3 + 1;
        dArr3[i3] = d;
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
        if (!(obj instanceof h)) {
            return super.equals(obj);
        }
        h hVar = (h) obj;
        if (this.g != hVar.g) {
            return false;
        }
        double[] dArr = hVar.f;
        for (int i2 = 0; i2 < this.g; i2++) {
            if (Double.doubleToLongBits(this.f[i2]) != Double.doubleToLongBits(dArr[i2])) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: f */
    public final h h(int i2) {
        double[] dArr;
        if (i2 >= this.g) {
            if (i2 == 0) {
                dArr = h;
            } else {
                dArr = Arrays.copyOf(this.f, i2);
            }
            return new h(dArr, this.g, true);
        }
        throw new IllegalArgumentException();
    }

    public final Object get(int i2) {
        c(i2);
        return Double.valueOf(this.f[i2]);
    }

    public final int hashCode() {
        int i2 = 1;
        for (int i3 = 0; i3 < this.g; i3++) {
            i2 = (i2 * 31) + p.a(Double.doubleToLongBits(this.f[i3]));
        }
        return i2;
    }

    public final int indexOf(Object obj) {
        if (!(obj instanceof Double)) {
            return -1;
        }
        double doubleValue = ((Double) obj).doubleValue();
        int i2 = this.g;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.f[i3] == doubleValue) {
                return i3;
            }
        }
        return -1;
    }

    public final Object remove(int i2) {
        a();
        c(i2);
        double[] dArr = this.f;
        double d = dArr[i2];
        int i3 = this.g;
        if (i2 < i3 - 1) {
            System.arraycopy(dArr, i2 + 1, dArr, i2, (i3 - i2) - 1);
        }
        this.g--;
        this.modCount++;
        return Double.valueOf(d);
    }

    public final void removeRange(int i2, int i3) {
        a();
        if (i3 >= i2) {
            double[] dArr = this.f;
            System.arraycopy(dArr, i3, dArr, i2, this.g - i3);
            this.g -= i3 - i2;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final Object set(int i2, Object obj) {
        double doubleValue = ((Double) obj).doubleValue();
        a();
        c(i2);
        double[] dArr = this.f;
        double d = dArr[i2];
        dArr[i2] = doubleValue;
        return Double.valueOf(d);
    }

    public final int size() {
        return this.g;
    }

    public h(double[] dArr, int i2, boolean z) {
        super(z);
        this.f = dArr;
        this.g = i2;
    }

    public final boolean add(Object obj) {
        b(((Double) obj).doubleValue());
        return true;
    }
}
