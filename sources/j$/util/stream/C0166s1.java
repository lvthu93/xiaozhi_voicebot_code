package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import j$.util.i0;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.s1  reason: case insensitive filesystem */
class C0166s1 implements K0 {
    final long[] a;
    int b;

    C0166s1(long j) {
        if (j < 2147483639) {
            this.a = new long[((int) j)];
            this.b = 0;
            return;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    C0166s1(long[] jArr) {
        this.a = jArr;
        this.b = jArr.length;
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final Object b() {
        long[] jArr = this.a;
        int length = jArr.length;
        int i = this.b;
        return length == i ? jArr : Arrays.copyOf(jArr, i);
    }

    public final long count() {
        return (long) this.b;
    }

    public final void f(Object obj, int i) {
        int i2 = this.b;
        System.arraycopy(this.a, 0, (long[]) obj, i, i2);
    }

    public final /* synthetic */ void forEach(Consumer consumer) {
        D0.O(this, consumer);
    }

    public final void g(Object obj) {
        LongConsumer longConsumer = (LongConsumer) obj;
        for (int i = 0; i < this.b; i++) {
            longConsumer.accept(this.a[i]);
        }
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.R(this, j, j2);
    }

    public final /* synthetic */ Object[] k(IntFunction intFunction) {
        return D0.I(this, intFunction);
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    /* renamed from: o */
    public final /* synthetic */ void j(Long[] lArr, int i) {
        D0.L(this, lArr, i);
    }

    public final Q spliterator() {
        return i0.l(this.a, 0, this.b);
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m19spliterator() {
        return i0.l(this.a, 0, this.b);
    }

    public String toString() {
        long[] jArr = this.a;
        return String.format("LongArrayNode[%d][%s]", new Object[]{Integer.valueOf(jArr.length - this.b), Arrays.toString(jArr)});
    }
}
