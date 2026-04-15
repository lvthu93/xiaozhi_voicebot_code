package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import j$.util.i0;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.a1  reason: case insensitive filesystem */
class C0077a1 implements I0 {
    final double[] a;
    int b;

    C0077a1(long j) {
        if (j < 2147483639) {
            this.a = new double[((int) j)];
            this.b = 0;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    C0077a1(double[] dArr) {
        this.a = dArr;
        this.b = dArr.length;
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final Object b() {
        double[] dArr = this.a;
        int length = dArr.length;
        int i = this.b;
        return length == i ? dArr : Arrays.copyOf(dArr, i);
    }

    public final long count() {
        return (long) this.b;
    }

    public final void f(Object obj, int i) {
        int i2 = this.b;
        System.arraycopy(this.a, 0, (double[]) obj, i, i2);
    }

    public final /* synthetic */ void forEach(Consumer consumer) {
        D0.M(this, consumer);
    }

    public final void g(Object obj) {
        DoubleConsumer doubleConsumer = (DoubleConsumer) obj;
        for (int i = 0; i < this.b; i++) {
            doubleConsumer.accept(this.a[i]);
        }
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.P(this, j, j2);
    }

    public final /* synthetic */ Object[] k(IntFunction intFunction) {
        return D0.I(this, intFunction);
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    /* renamed from: o */
    public final /* synthetic */ void j(Double[] dArr, int i) {
        D0.J(this, dArr, i);
    }

    public final Q spliterator() {
        return i0.j(this.a, 0, this.b);
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m10spliterator() {
        return i0.j(this.a, 0, this.b);
    }

    public String toString() {
        double[] dArr = this.a;
        return String.format("DoubleArrayNode[%d][%s]", new Object[]{Integer.valueOf(dArr.length - this.b), Arrays.toString(dArr)});
    }
}
