package com.google.protobuf;

import androidx.appcompat.widget.ActivityChooserView;
import com.google.protobuf.p;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

public final class m extends c<Float> implements p.f, RandomAccess, a9 {
    public static final float[] h;
    public static final m i;
    public float[] f;
    public int g;

    static {
        float[] fArr = new float[0];
        h = fArr;
        i = new m(0, false, fArr);
    }

    public m() {
        this(0, true, h);
    }

    public final void add(int i2, Object obj) {
        int i3;
        float floatValue = ((Float) obj).floatValue();
        a();
        if (i2 < 0 || i2 > (i3 = this.g)) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
        float[] fArr = this.f;
        if (i3 < fArr.length) {
            System.arraycopy(fArr, i2, fArr, i2 + 1, i3 - i2);
        } else {
            float[] fArr2 = new float[y2.b(fArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, fArr2, 0, i2);
            System.arraycopy(this.f, i2, fArr2, i2 + 1, this.g - i2);
            this.f = fArr2;
        }
        this.f[i2] = floatValue;
        this.g++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Float> collection) {
        a();
        Charset charset = p.a;
        collection.getClass();
        if (!(collection instanceof m)) {
            return super.addAll(collection);
        }
        m mVar = (m) collection;
        int i2 = mVar.g;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.g;
        if (ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED - i3 >= i2) {
            int i4 = i3 + i2;
            float[] fArr = this.f;
            if (i4 > fArr.length) {
                this.f = Arrays.copyOf(fArr, i4);
            }
            System.arraycopy(mVar.f, 0, this.f, this.g, mVar.g);
            this.g = i4;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final void b(float f2) {
        a();
        int i2 = this.g;
        float[] fArr = this.f;
        if (i2 == fArr.length) {
            float[] fArr2 = new float[y2.b(fArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, fArr2, 0, this.g);
            this.f = fArr2;
        }
        float[] fArr3 = this.f;
        int i3 = this.g;
        this.g = i3 + 1;
        fArr3[i3] = f2;
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
        if (!(obj instanceof m)) {
            return super.equals(obj);
        }
        m mVar = (m) obj;
        if (this.g != mVar.g) {
            return false;
        }
        float[] fArr = mVar.f;
        for (int i2 = 0; i2 < this.g; i2++) {
            if (Float.floatToIntBits(this.f[i2]) != Float.floatToIntBits(fArr[i2])) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: f */
    public final m h(int i2) {
        float[] fArr;
        if (i2 >= this.g) {
            if (i2 == 0) {
                fArr = h;
            } else {
                fArr = Arrays.copyOf(this.f, i2);
            }
            return new m(this.g, true, fArr);
        }
        throw new IllegalArgumentException();
    }

    public final Object get(int i2) {
        c(i2);
        return Float.valueOf(this.f[i2]);
    }

    public final int hashCode() {
        int i2 = 1;
        for (int i3 = 0; i3 < this.g; i3++) {
            i2 = (i2 * 31) + Float.floatToIntBits(this.f[i3]);
        }
        return i2;
    }

    public final int indexOf(Object obj) {
        if (!(obj instanceof Float)) {
            return -1;
        }
        float floatValue = ((Float) obj).floatValue();
        int i2 = this.g;
        for (int i3 = 0; i3 < i2; i3++) {
            if (this.f[i3] == floatValue) {
                return i3;
            }
        }
        return -1;
    }

    public final Object remove(int i2) {
        a();
        c(i2);
        float[] fArr = this.f;
        float f2 = fArr[i2];
        int i3 = this.g;
        if (i2 < i3 - 1) {
            System.arraycopy(fArr, i2 + 1, fArr, i2, (i3 - i2) - 1);
        }
        this.g--;
        this.modCount++;
        return Float.valueOf(f2);
    }

    public final void removeRange(int i2, int i3) {
        a();
        if (i3 >= i2) {
            float[] fArr = this.f;
            System.arraycopy(fArr, i3, fArr, i2, this.g - i3);
            this.g -= i3 - i2;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public final Object set(int i2, Object obj) {
        float floatValue = ((Float) obj).floatValue();
        a();
        c(i2);
        float[] fArr = this.f;
        float f2 = fArr[i2];
        fArr[i2] = floatValue;
        return Float.valueOf(f2);
    }

    public final int size() {
        return this.g;
    }

    public m(int i2, boolean z, float[] fArr) {
        super(z);
        this.f = fArr;
        this.g = i2;
    }

    public final boolean add(Object obj) {
        b(((Float) obj).floatValue());
        return true;
    }
}
