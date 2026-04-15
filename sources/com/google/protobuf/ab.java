package com.google.protobuf;

import com.google.protobuf.p;
import java.util.Arrays;
import java.util.RandomAccess;

public final class ab<E> extends c<E> implements RandomAccess {
    public static final Object[] h;
    public static final ab<Object> i;
    public E[] f;
    public int g;

    static {
        Object[] objArr = new Object[0];
        h = objArr;
        i = new ab<>(0, false, objArr);
    }

    public ab() {
        this(0, true, h);
    }

    public final boolean add(E e) {
        a();
        int i2 = this.g;
        E[] eArr = this.f;
        if (i2 == eArr.length) {
            this.f = Arrays.copyOf(this.f, y2.b(eArr.length, 3, 2, 1, 10));
        }
        E[] eArr2 = this.f;
        int i3 = this.g;
        this.g = i3 + 1;
        eArr2[i3] = e;
        this.modCount++;
        return true;
    }

    public final void b(int i2) {
        if (i2 < 0 || i2 >= this.g) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
    }

    public final E get(int i2) {
        b(i2);
        return this.f[i2];
    }

    public final p.i h(int i2) {
        Object[] objArr;
        if (i2 >= this.g) {
            if (i2 == 0) {
                objArr = h;
            } else {
                objArr = Arrays.copyOf(this.f, i2);
            }
            return new ab(this.g, true, objArr);
        }
        throw new IllegalArgumentException();
    }

    public final E remove(int i2) {
        a();
        b(i2);
        E[] eArr = this.f;
        E e = eArr[i2];
        int i3 = this.g;
        if (i2 < i3 - 1) {
            System.arraycopy(eArr, i2 + 1, eArr, i2, (i3 - i2) - 1);
        }
        this.g--;
        this.modCount++;
        return e;
    }

    public final E set(int i2, E e) {
        a();
        b(i2);
        E[] eArr = this.f;
        E e2 = eArr[i2];
        eArr[i2] = e;
        this.modCount++;
        return e2;
    }

    public final int size() {
        return this.g;
    }

    public ab(int i2, boolean z, Object[] objArr) {
        super(z);
        this.f = objArr;
        this.g = i2;
    }

    public final void add(int i2, E e) {
        int i3;
        a();
        if (i2 < 0 || i2 > (i3 = this.g)) {
            StringBuilder n = y2.n("Index:", i2, ", Size:");
            n.append(this.g);
            throw new IndexOutOfBoundsException(n.toString());
        }
        E[] eArr = this.f;
        if (i3 < eArr.length) {
            System.arraycopy(eArr, i2, eArr, i2 + 1, i3 - i2);
        } else {
            E[] eArr2 = new Object[y2.b(eArr.length, 3, 2, 1, 10)];
            System.arraycopy(this.f, 0, eArr2, 0, i2);
            System.arraycopy(this.f, i2, eArr2, i2 + 1, this.g - i2);
            this.f = eArr2;
        }
        this.f[i2] = e;
        this.g++;
        this.modCount++;
    }
}
