package j$.util.stream;

import j$.lang.b;
import j$.util.U;
import java.util.Arrays;

/* renamed from: j$.util.stream.e3  reason: case insensitive filesystem */
abstract class C0099e3 extends C0095e implements Iterable, b {
    Object e = newArray(16);
    Object[] f;

    C0099e3() {
    }

    C0099e3(int i) {
        super(i);
    }

    public Object b() {
        long count = count();
        if (count < 2147483639) {
            Object newArray = newArray((int) count);
            f(newArray, 0);
            return newArray;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void clear() {
        Object[] objArr = this.f;
        if (objArr != null) {
            this.e = objArr[0];
            this.f = null;
            this.d = null;
        }
        this.b = 0;
        this.c = 0;
    }

    public void f(Object obj, int i) {
        long j = (long) i;
        long count = count() + j;
        if (count > ((long) p(obj)) || count < j) {
            throw new IndexOutOfBoundsException("does not fit");
        } else if (this.c == 0) {
            System.arraycopy(this.e, 0, obj, i, this.b);
        } else {
            for (int i2 = 0; i2 < this.c; i2++) {
                Object obj2 = this.f[i2];
                System.arraycopy(obj2, 0, obj, i, p(obj2));
                i += p(this.f[i2]);
            }
            int i3 = this.b;
            if (i3 > 0) {
                System.arraycopy(this.e, 0, obj, i, i3);
            }
        }
    }

    public void g(Object obj) {
        for (int i = 0; i < this.c; i++) {
            Object obj2 = this.f[i];
            o(obj2, 0, p(obj2), obj);
        }
        o(this.e, 0, this.b, obj);
    }

    public abstract Object newArray(int i);

    /* access modifiers changed from: protected */
    public abstract void o(Object obj, int i, int i2, Object obj2);

    /* access modifiers changed from: protected */
    public abstract int p(Object obj);

    /* access modifiers changed from: protected */
    public final int q(long j) {
        if (this.c == 0) {
            if (j < ((long) this.b)) {
                return 0;
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        } else if (j < count()) {
            for (int i = 0; i <= this.c; i++) {
                if (j < this.d[i] + ((long) p(this.f[i]))) {
                    return i;
                }
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        } else {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
    }

    /* access modifiers changed from: protected */
    public final void r(long j) {
        long j2;
        int i = this.c;
        if (i == 0) {
            j2 = (long) p(this.e);
        } else {
            j2 = ((long) p(this.f[i])) + this.d[i];
        }
        if (j > j2) {
            if (this.f == null) {
                Object[] s = s();
                this.f = s;
                this.d = new long[8];
                s[0] = this.e;
            }
            int i2 = this.c;
            while (true) {
                i2++;
                if (j > j2) {
                    Object[] objArr = this.f;
                    if (i2 >= objArr.length) {
                        int length = objArr.length * 2;
                        this.f = Arrays.copyOf(objArr, length);
                        this.d = Arrays.copyOf(this.d, length);
                    }
                    int i3 = this.a;
                    if (!(i2 == 0 || i2 == 1)) {
                        i3 = Math.min((i3 + i2) - 1, 30);
                    }
                    int i4 = 1 << i3;
                    this.f[i2] = newArray(i4);
                    long[] jArr = this.d;
                    int i5 = i2 - 1;
                    jArr[i2] = jArr[i5] + ((long) p(this.f[i5]));
                    j2 += (long) i4;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object[] s();

    public abstract U spliterator();

    /* access modifiers changed from: protected */
    public final void t() {
        long j;
        if (this.b == p(this.e)) {
            if (this.f == null) {
                Object[] s = s();
                this.f = s;
                this.d = new long[8];
                s[0] = this.e;
            }
            int i = this.c;
            int i2 = i + 1;
            Object[] objArr = this.f;
            if (i2 >= objArr.length || objArr[i2] == null) {
                if (i == 0) {
                    j = (long) p(this.e);
                } else {
                    j = ((long) p(objArr[i])) + this.d[i];
                }
                r(j + 1);
            }
            this.b = 0;
            int i3 = this.c + 1;
            this.c = i3;
            this.e = this.f[i3];
        }
    }
}
