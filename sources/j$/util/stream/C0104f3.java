package j$.util.stream;

import j$.lang.b;
import j$.util.Objects;
import j$.util.U;
import j$.util.function.Consumer$CC;
import j$.util.i0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.f3  reason: case insensitive filesystem */
class C0104f3 extends C0095e implements Consumer, Iterable, b {
    protected Object[] e = new Object[(1 << 4)];
    protected Object[][] f;

    C0104f3() {
    }

    public void accept(Object obj) {
        long j;
        int i = this.b;
        Object[] objArr = this.e;
        if (i == objArr.length) {
            if (this.f == null) {
                Object[][] objArr2 = new Object[8][];
                this.f = objArr2;
                this.d = new long[8];
                objArr2[0] = objArr;
            }
            int i2 = this.c;
            int i3 = i2 + 1;
            Object[][] objArr3 = this.f;
            if (i3 >= objArr3.length || objArr3[i3] == null) {
                if (i2 == 0) {
                    j = (long) objArr.length;
                } else {
                    j = ((long) objArr3[i2].length) + this.d[i2];
                }
                o(j + 1);
            }
            this.b = 0;
            int i4 = this.c + 1;
            this.c = i4;
            this.e = this.f[i4];
        }
        Object[] objArr4 = this.e;
        int i5 = this.b;
        this.b = i5 + 1;
        objArr4[i5] = obj;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final void clear() {
        Object[][] objArr = this.f;
        if (objArr != null) {
            this.e = objArr[0];
            int i = 0;
            while (true) {
                Object[] objArr2 = this.e;
                if (i >= objArr2.length) {
                    break;
                }
                objArr2[i] = null;
                i++;
            }
            this.f = null;
            this.d = null;
        } else {
            for (int i2 = 0; i2 < this.b; i2++) {
                this.e[i2] = null;
            }
        }
        this.b = 0;
        this.c = 0;
    }

    public void forEach(Consumer consumer) {
        for (int i = 0; i < this.c; i++) {
            for (Object accept : this.f[i]) {
                consumer.accept(accept);
            }
        }
        for (int i2 = 0; i2 < this.b; i2++) {
            consumer.accept(this.e[i2]);
        }
    }

    public final Iterator iterator() {
        return i0.i(spliterator());
    }

    /* access modifiers changed from: protected */
    public final void o(long j) {
        long j2;
        int i = this.c;
        if (i == 0) {
            j2 = (long) this.e.length;
        } else {
            j2 = ((long) this.f[i].length) + this.d[i];
        }
        if (j > j2) {
            if (this.f == null) {
                Object[][] objArr = new Object[8][];
                this.f = objArr;
                this.d = new long[8];
                objArr[0] = this.e;
            }
            while (true) {
                i++;
                if (j > j2) {
                    Object[][] objArr2 = this.f;
                    if (i >= objArr2.length) {
                        int length = objArr2.length * 2;
                        this.f = (Object[][]) Arrays.copyOf(objArr2, length);
                        this.d = Arrays.copyOf(this.d, length);
                    }
                    int i2 = this.a;
                    if (!(i == 0 || i == 1)) {
                        i2 = Math.min((i2 + i) - 1, 30);
                    }
                    int i3 = 1 << i2;
                    Object[][] objArr3 = this.f;
                    objArr3[i] = new Object[i3];
                    long[] jArr = this.d;
                    int i4 = i - 1;
                    jArr[i] = jArr[i4] + ((long) objArr3[i4].length);
                    j2 += (long) i3;
                } else {
                    return;
                }
            }
        }
    }

    public U spliterator() {
        return new W2(this, 0, this.c, 0, this.b);
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        Objects.requireNonNull(arrayList);
        forEach(new C0075a(arrayList, 11));
        String obj = arrayList.toString();
        return "SpinedBuffer:" + obj;
    }
}
