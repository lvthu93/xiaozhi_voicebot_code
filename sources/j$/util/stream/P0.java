package j$.util.stream;

import j$.util.U;
import j$.util.i0;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntFunction;

class P0 implements M0 {
    final Object[] a;
    int b;

    P0(long j, IntFunction intFunction) {
        if (j < 2147483639) {
            this.a = (Object[]) intFunction.apply((int) j);
            this.b = 0;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    P0(Object[] objArr) {
        this.a = objArr;
        this.b = objArr.length;
    }

    public final M0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final long count() {
        return (long) this.b;
    }

    public final void forEach(Consumer consumer) {
        for (int i = 0; i < this.b; i++) {
            consumer.accept(this.a[i]);
        }
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.S(this, j, j2, intFunction);
    }

    public final void j(Object[] objArr, int i) {
        System.arraycopy(this.a, 0, objArr, i, this.b);
    }

    public final Object[] k(IntFunction intFunction) {
        Object[] objArr = this.a;
        if (objArr.length == this.b) {
            return objArr;
        }
        throw new IllegalStateException();
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    public final U spliterator() {
        return i0.m(this.a, 0, this.b);
    }

    public String toString() {
        Object[] objArr = this.a;
        return String.format("ArrayNode[%d][%s]", new Object[]{Integer.valueOf(objArr.length - this.b), Arrays.toString(objArr)});
    }
}
