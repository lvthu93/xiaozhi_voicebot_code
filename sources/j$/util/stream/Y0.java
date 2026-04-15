package j$.util.stream;

import java.util.function.IntFunction;

abstract class Y0 extends O0 implements L0 {
    Y0(L0 l0, L0 l02) {
        super(l0, l02);
    }

    public final Object b() {
        long count = count();
        if (count < 2147483639) {
            Object newArray = newArray((int) count);
            f(newArray, 0);
            return newArray;
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public final void f(Object obj, int i) {
        M0 m0 = this.a;
        ((L0) m0).f(obj, i);
        ((L0) this.b).f(obj, i + ((int) ((L0) m0).count()));
    }

    public final void g(Object obj) {
        ((L0) this.a).g(obj);
        ((L0) this.b).g(obj);
    }

    public final /* synthetic */ Object[] k(IntFunction intFunction) {
        return D0.I(this, intFunction);
    }

    public final String toString() {
        if (count() < 32) {
            return String.format("%s[%s.%s]", new Object[]{getClass().getName(), this.a, this.b});
        }
        return String.format("%s[size=%d]", new Object[]{getClass().getName(), Long.valueOf(count())});
    }
}
