package defpackage;

import android.util.SparseArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Consumer;

/* renamed from: kb  reason: default package */
public final class kb<V> {
    public int a;
    public final SparseArray<V> b;
    public final Consumer<V> c;

    public kb() {
        this(new z6(14));
    }

    public void appendSpan(int i, V v) {
        boolean z;
        int i2 = this.a;
        boolean z2 = false;
        SparseArray<V> sparseArray = this.b;
        if (i2 == -1) {
            if (sparseArray.size() == 0) {
                z = true;
            } else {
                z = false;
            }
            Assertions.checkState(z);
            this.a = 0;
        }
        if (sparseArray.size() > 0) {
            int keyAt = sparseArray.keyAt(sparseArray.size() - 1);
            if (i >= keyAt) {
                z2 = true;
            }
            Assertions.checkArgument(z2);
            if (keyAt == i) {
                this.c.accept(sparseArray.valueAt(sparseArray.size() - 1));
            }
        }
        sparseArray.append(i, v);
    }

    public void clear() {
        int i = 0;
        while (true) {
            SparseArray<V> sparseArray = this.b;
            if (i < sparseArray.size()) {
                this.c.accept(sparseArray.valueAt(i));
                i++;
            } else {
                this.a = -1;
                sparseArray.clear();
                return;
            }
        }
    }

    public void discardFrom(int i) {
        int i2;
        SparseArray<V> sparseArray = this.b;
        int size = sparseArray.size() - 1;
        while (size >= 0 && i < sparseArray.keyAt(size)) {
            this.c.accept(sparseArray.valueAt(size));
            sparseArray.removeAt(size);
            size--;
        }
        if (sparseArray.size() > 0) {
            i2 = Math.min(this.a, sparseArray.size() - 1);
        } else {
            i2 = -1;
        }
        this.a = i2;
    }

    public void discardTo(int i) {
        int i2 = 0;
        while (true) {
            SparseArray<V> sparseArray = this.b;
            if (i2 < sparseArray.size() - 1) {
                int i3 = i2 + 1;
                if (i >= sparseArray.keyAt(i3)) {
                    this.c.accept(sparseArray.valueAt(i2));
                    sparseArray.removeAt(i2);
                    int i4 = this.a;
                    if (i4 > 0) {
                        this.a = i4 - 1;
                    }
                    i2 = i3;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public V get(int i) {
        SparseArray<V> sparseArray;
        if (this.a == -1) {
            this.a = 0;
        }
        while (true) {
            int i2 = this.a;
            sparseArray = this.b;
            if (i2 > 0 && i < sparseArray.keyAt(i2)) {
                this.a--;
            }
        }
        while (this.a < sparseArray.size() - 1 && i >= sparseArray.keyAt(this.a + 1)) {
            this.a++;
        }
        return sparseArray.valueAt(this.a);
    }

    public V getEndValue() {
        SparseArray<V> sparseArray = this.b;
        return sparseArray.valueAt(sparseArray.size() - 1);
    }

    public boolean isEmpty() {
        return this.b.size() == 0;
    }

    public kb(Consumer<V> consumer) {
        this.b = new SparseArray<>();
        this.c = consumer;
        this.a = -1;
    }
}
