package j$.util;

import java.util.Iterator;

public abstract class i0 {
    private static final U a = new e0();
    private static final K b = new c0();
    private static final N c = new d0();
    private static final H d = new b0();

    private static void a(int i, int i2, int i3) {
        if (i2 > i3) {
            throw new ArrayIndexOutOfBoundsException("origin(" + i2 + ") > fence(" + i3 + ")");
        } else if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException(i2);
        } else if (i3 > i) {
            throw new ArrayIndexOutOfBoundsException(i3);
        }
    }

    public static H b() {
        return d;
    }

    public static K c() {
        return b;
    }

    public static N d() {
        return c;
    }

    public static U e() {
        return a;
    }

    public static C0203u f(H h) {
        Objects.requireNonNull(h);
        return new Y(h);
    }

    public static C0207y g(K k) {
        Objects.requireNonNull(k);
        return new W(k);
    }

    public static C h(N n) {
        Objects.requireNonNull(n);
        return new X(n);
    }

    public static Iterator i(U u) {
        Objects.requireNonNull(u);
        return new V(u);
    }

    public static H j(double[] dArr, int i, int i2) {
        a(((double[]) Objects.requireNonNull(dArr)).length, i, i2);
        return new a0(dArr, i, i2, 1040);
    }

    public static K k(int[] iArr, int i, int i2) {
        a(((int[]) Objects.requireNonNull(iArr)).length, i, i2);
        return new f0(iArr, i, i2, 1040);
    }

    public static N l(long[] jArr, int i, int i2) {
        a(((long[]) Objects.requireNonNull(jArr)).length, i, i2);
        return new h0(jArr, i, i2, 1040);
    }

    public static U m(Object[] objArr, int i, int i2) {
        a(((Object[]) Objects.requireNonNull(objArr)).length, i, i2);
        return new Z(objArr, i, i2, 1040);
    }

    public static U n(Iterator it) {
        return new g0((Iterator) Objects.requireNonNull(it));
    }
}
