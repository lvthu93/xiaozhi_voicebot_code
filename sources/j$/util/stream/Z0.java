package j$.util.stream;

import j$.util.Objects;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.IntFunction;

final class Z0 extends O0 {
    Z0(M0 m0, M0 m02) {
        super(m0, m02);
    }

    public final void forEach(Consumer consumer) {
        this.a.forEach(consumer);
        this.b.forEach(consumer);
    }

    public final M0 i(long j, long j2, IntFunction intFunction) {
        if (j == 0 && j2 == count()) {
            return this;
        }
        long count = this.a.count();
        if (j >= count) {
            return this.b.i(j - count, j2 - count, intFunction);
        }
        if (j2 <= count) {
            return this.a.i(j, j2, intFunction);
        }
        IntFunction intFunction2 = intFunction;
        return D0.e0(C0134l3.REFERENCE, this.a.i(j, count, intFunction2), this.b.i(0, j2 - count, intFunction2));
    }

    public final void j(Object[] objArr, int i) {
        Objects.requireNonNull(objArr);
        M0 m0 = this.a;
        m0.j(objArr, i);
        this.b.j(objArr, i + ((int) m0.count()));
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

    public final U spliterator() {
        return new C0157q1(this);
    }

    public final String toString() {
        if (count() < 32) {
            return String.format("ConcNode[%s.%s]", new Object[]{this.a, this.b});
        }
        return String.format("ConcNode[size=%d]", new Object[]{Long.valueOf(count())});
    }
}
