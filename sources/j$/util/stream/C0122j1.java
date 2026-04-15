package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import j$.util.i0;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.j1  reason: case insensitive filesystem */
class C0122j1 implements J0 {
    final int[] a;
    int b;

    C0122j1(long j) {
        if (j < 2147483639) {
            this.a = new int[((int) j)];
            this.b = 0;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    C0122j1(int[] iArr) {
        this.a = iArr;
        this.b = iArr.length;
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final Object b() {
        int[] iArr = this.a;
        int length = iArr.length;
        int i = this.b;
        return length == i ? iArr : Arrays.copyOf(iArr, i);
    }

    public final long count() {
        return (long) this.b;
    }

    public final void f(Object obj, int i) {
        int i2 = this.b;
        System.arraycopy(this.a, 0, (int[]) obj, i, i2);
    }

    public final /* synthetic */ void forEach(Consumer consumer) {
        D0.N(this, consumer);
    }

    public final void g(Object obj) {
        IntConsumer intConsumer = (IntConsumer) obj;
        for (int i = 0; i < this.b; i++) {
            intConsumer.accept(this.a[i]);
        }
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.Q(this, j, j2);
    }

    public final /* synthetic */ Object[] k(IntFunction intFunction) {
        return D0.I(this, intFunction);
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    /* renamed from: o */
    public final /* synthetic */ void j(Integer[] numArr, int i) {
        D0.K(this, numArr, i);
    }

    public final Q spliterator() {
        return i0.k(this.a, 0, this.b);
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m16spliterator() {
        return i0.k(this.a, 0, this.b);
    }

    public String toString() {
        int[] iArr = this.a;
        return String.format("IntArrayNode[%d][%s]", new Object[]{Integer.valueOf(iArr.length - this.b), Arrays.toString(iArr)});
    }
}
