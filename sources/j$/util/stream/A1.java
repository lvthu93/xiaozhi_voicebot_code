package j$.util.stream;

import j$.util.U;
import java.util.function.Consumer;
import java.util.function.IntFunction;

final class A1 extends C0104f3 implements M0, H0 {
    A1() {
    }

    public final M0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final void accept(Object obj) {
        super.accept(obj);
    }

    public final M0 build() {
        return this;
    }

    public final void c(long j) {
        clear();
        o(j);
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final void end() {
    }

    public final void forEach(Consumer consumer) {
        super.forEach(consumer);
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.S(this, j, j2, intFunction);
    }

    public final void j(Object[] objArr, int i) {
        long j = (long) i;
        long count = count() + j;
        if (count > ((long) objArr.length) || count < j) {
            throw new IndexOutOfBoundsException("does not fit");
        } else if (this.c == 0) {
            System.arraycopy(this.e, 0, objArr, i, this.b);
        } else {
            for (int i2 = 0; i2 < this.c; i2++) {
                Object[] objArr2 = this.f[i2];
                System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
                i += this.f[i2].length;
            }
            int i3 = this.b;
            if (i3 > 0) {
                System.arraycopy(this.e, 0, objArr, i, i3);
            }
        }
    }

    public final Object[] k(IntFunction intFunction) {
        long count = count();
        if (count < 2147483639) {
            Object[] objArr = (Object[]) intFunction.apply((int) count);
            j(objArr, 0);
            return objArr;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    public final U spliterator() {
        return super.spliterator();
    }
}
