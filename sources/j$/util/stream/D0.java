package j$.util.stream;

import j$.util.C0065i;
import j$.util.H;
import j$.util.K;
import j$.util.N;
import j$.util.Objects;
import j$.util.U;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.stream.Collector;

public abstract /* synthetic */ class D0 implements U3 {
    private static final C0107g1 a = new C0107g1();
    private static final J0 b = new C0097e1();
    private static final K0 c = new C0102f1();
    private static final I0 d = new C0092d1();
    /* access modifiers changed from: private */
    public static final int[] e = new int[0];
    /* access modifiers changed from: private */
    public static final long[] f = new long[0];
    /* access modifiers changed from: private */
    public static final double[] g = new double[0];

    public /* synthetic */ D0() {
    }

    public /* synthetic */ D0(C0134l3 l3Var) {
    }

    public static void A(C0167s2 s2Var, Double d2) {
        if (!X3.a) {
            s2Var.accept(d2.doubleValue());
        } else {
            X3.a(s2Var.getClass(), "{0} calling Sink.OfDouble.accept(Double)");
            throw null;
        }
    }

    public static B0 A0(A0 a0) {
        Objects.requireNonNull(null);
        Objects.requireNonNull(a0);
        return new B0(C0134l3.INT_VALUE, a0, new C0150p(1, a0, (Object) null));
    }

    public static C0175u0 B0(C0085c cVar, long j, long j2) {
        if (j >= 0) {
            return new B2(cVar, l0(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static void C(C0172t2 t2Var, Integer num) {
        if (!X3.a) {
            t2Var.accept(num.intValue());
        } else {
            X3.a(t2Var.getClass(), "{0} calling Sink.OfInt.accept(Integer)");
            throw null;
        }
    }

    public static B0 C0(A0 a0) {
        Objects.requireNonNull(null);
        Objects.requireNonNull(a0);
        return new B0(C0134l3.LONG_VALUE, a0, new C0150p(4, a0, (Object) null));
    }

    public static void E(C0177u2 u2Var, Long l) {
        if (!X3.a) {
            u2Var.accept(l.longValue());
        } else {
            X3.a(u2Var.getClass(), "{0} calling Sink.OfLong.accept(Long)");
            throw null;
        }
    }

    public static B0 E0(A0 a0, Predicate predicate) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(a0);
        return new B0(C0134l3.REFERENCE, a0, new C0150p(2, a0, predicate));
    }

    public static Stream F0(C0085c cVar, long j, long j2) {
        if (j >= 0) {
            return new C0192x2(cVar, l0(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static void G() {
        throw new IllegalStateException("called wrong accept method");
    }

    public static void H() {
        throw new IllegalStateException("called wrong accept method");
    }

    public static Stream H0(U u, boolean z) {
        Objects.requireNonNull(u);
        return new C0128k2(u, C0129k3.m(u), z);
    }

    public static Object[] I(L0 l0, IntFunction intFunction) {
        if (X3.a) {
            X3.a(l0.getClass(), "{0} calling Node.OfPrimitive.asArray");
            throw null;
        } else if (l0.count() < 2147483639) {
            Object[] objArr = (Object[]) intFunction.apply((int) l0.count());
            l0.j(objArr, 0);
            return objArr;
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static void J(I0 i0, Double[] dArr, int i) {
        if (!X3.a) {
            double[] dArr2 = (double[]) i0.b();
            for (int i2 = 0; i2 < dArr2.length; i2++) {
                dArr[i + i2] = Double.valueOf(dArr2[i2]);
            }
            return;
        }
        X3.a(i0.getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");
        throw null;
    }

    public static void K(J0 j0, Integer[] numArr, int i) {
        if (!X3.a) {
            int[] iArr = (int[]) j0.b();
            for (int i2 = 0; i2 < iArr.length; i2++) {
                numArr[i + i2] = Integer.valueOf(iArr[i2]);
            }
            return;
        }
        X3.a(j0.getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");
        throw null;
    }

    public static void L(K0 k0, Long[] lArr, int i) {
        if (!X3.a) {
            long[] jArr = (long[]) k0.b();
            for (int i2 = 0; i2 < jArr.length; i2++) {
                lArr[i + i2] = Long.valueOf(jArr[i2]);
            }
            return;
        }
        X3.a(k0.getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");
        throw null;
    }

    public static void M(I0 i0, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            i0.g((DoubleConsumer) consumer);
        } else if (!X3.a) {
            ((H) i0.spliterator()).forEachRemaining(consumer);
        } else {
            X3.a(i0.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
            throw null;
        }
    }

    public static void N(J0 j0, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            j0.g((IntConsumer) consumer);
        } else if (!X3.a) {
            ((K) j0.spliterator()).forEachRemaining(consumer);
        } else {
            X3.a(j0.getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)");
            throw null;
        }
    }

    public static void O(K0 k0, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            k0.g((LongConsumer) consumer);
        } else if (!X3.a) {
            ((N) k0.spliterator()).forEachRemaining(consumer);
        } else {
            X3.a(k0.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
            throw null;
        }
    }

    public static I0 P(I0 i0, long j, long j2) {
        if (j == 0 && j2 == i0.count()) {
            return i0;
        }
        long j3 = j2 - j;
        H h = (H) i0.spliterator();
        E0 h0 = h0(j3);
        h0.c(j3);
        for (int i = 0; ((long) i) < j && h.tryAdvance((DoubleConsumer) new A3(1)); i++) {
        }
        if (j2 == i0.count()) {
            h.forEachRemaining((DoubleConsumer) h0);
        } else {
            for (int i2 = 0; ((long) i2) < j3 && h.tryAdvance((DoubleConsumer) h0); i2++) {
            }
        }
        h0.end();
        return h0.build();
    }

    public static J0 Q(J0 j0, long j, long j2) {
        if (j == 0 && j2 == j0.count()) {
            return j0;
        }
        long j3 = j2 - j;
        K k = (K) j0.spliterator();
        F0 t0 = t0(j3);
        t0.c(j3);
        for (int i = 0; ((long) i) < j && k.tryAdvance((IntConsumer) new C3(1)); i++) {
        }
        if (j2 == j0.count()) {
            k.forEachRemaining((IntConsumer) t0);
        } else {
            for (int i2 = 0; ((long) i2) < j3 && k.tryAdvance((IntConsumer) t0); i2++) {
            }
        }
        t0.end();
        return t0.build();
    }

    public static K0 R(K0 k0, long j, long j2) {
        if (j == 0 && j2 == k0.count()) {
            return k0;
        }
        long j3 = j2 - j;
        N n = (N) k0.spliterator();
        G0 v0 = v0(j3);
        v0.c(j3);
        for (int i = 0; ((long) i) < j && n.tryAdvance((LongConsumer) new E3(1)); i++) {
        }
        if (j2 == k0.count()) {
            n.forEachRemaining((LongConsumer) v0);
        } else {
            for (int i2 = 0; ((long) i2) < j3 && n.tryAdvance((LongConsumer) v0); i2++) {
            }
        }
        v0.end();
        return v0.build();
    }

    public static M0 S(M0 m0, long j, long j2, IntFunction intFunction) {
        if (j == 0 && j2 == m0.count()) {
            return m0;
        }
        U spliterator = m0.spliterator();
        long j3 = j2 - j;
        H0 Z = Z(j3, intFunction);
        Z.c(j3);
        for (int i = 0; ((long) i) < j && spliterator.tryAdvance(new L(15)); i++) {
        }
        if (j2 == m0.count()) {
            spliterator.forEachRemaining(Z);
        } else {
            for (int i2 = 0; ((long) i2) < j3 && spliterator.tryAdvance(Z); i2++) {
            }
        }
        Z.end();
        return Z.build();
    }

    static long W(long j, long j2, long j3) {
        if (j >= 0) {
            return Math.max(-1, Math.min(j - j2, j3));
        }
        return -1;
    }

    static long X(long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        if (j3 >= 0) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    static U Y(C0134l3 l3Var, U u, long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        long j4 = j3 >= 0 ? j3 : Long.MAX_VALUE;
        int i = E2.a[l3Var.ordinal()];
        if (i == 1) {
            return new H3(u, j, j4);
        }
        if (i == 2) {
            return new D3((K) u, j, j4);
        }
        if (i == 3) {
            return new F3((N) u, j, j4);
        }
        if (i == 4) {
            return new B3((H) u, j, j4);
        }
        StringBuilder sb = new StringBuilder("Unknown shape ");
        C0134l3 l3Var2 = l3Var;
        sb.append(l3Var);
        throw new IllegalStateException(sb.toString());
    }

    static H0 Z(long j, IntFunction intFunction) {
        return (j < 0 || j >= 2147483639) ? new A1() : new C0117i1(j, intFunction);
    }

    public static M0 a0(D0 d0, U u, boolean z, IntFunction intFunction) {
        long k0 = d0.k0(u);
        if (k0 < 0 || !u.hasCharacteristics(16384)) {
            M0 m0 = (M0) new S0(u, d0, intFunction).invoke();
            return z ? m0(m0, intFunction) : m0;
        } else if (k0 < 2147483639) {
            Object[] objArr = (Object[]) intFunction.apply((int) k0);
            new C0196y1(u, d0, objArr).invoke();
            return new P0(objArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static I0 b0(D0 d0, U u, boolean z) {
        long k0 = d0.k0(u);
        if (k0 < 0 || !u.hasCharacteristics(16384)) {
            I0 i0 = (I0) new S0(0, u, d0).invoke();
            return z ? n0(i0) : i0;
        } else if (k0 < 2147483639) {
            double[] dArr = new double[((int) k0)];
            new C0181v1(u, d0, dArr).invoke();
            return new C0077a1(dArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static J0 c0(D0 d0, U u, boolean z) {
        long k0 = d0.k0(u);
        if (k0 < 0 || !u.hasCharacteristics(16384)) {
            J0 j0 = (J0) new S0(1, u, d0).invoke();
            return z ? o0(j0) : j0;
        } else if (k0 < 2147483639) {
            int[] iArr = new int[((int) k0)];
            new C0186w1(u, d0, iArr).invoke();
            return new C0122j1(iArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    public static K0 d0(D0 d0, U u, boolean z) {
        long k0 = d0.k0(u);
        if (k0 < 0 || !u.hasCharacteristics(16384)) {
            K0 k02 = (K0) new S0(2, u, d0).invoke();
            return z ? p0(k02) : k02;
        } else if (k0 < 2147483639) {
            long[] jArr = new long[((int) k0)];
            new C0191x1(u, d0, jArr).invoke();
            return new C0166s1(jArr);
        } else {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
    }

    static O0 e0(C0134l3 l3Var, M0 m0, M0 m02) {
        int i = N0.a[l3Var.ordinal()];
        if (i == 1) {
            return new Z0(m0, m02);
        }
        if (i == 2) {
            return new W0((J0) m0, (J0) m02);
        }
        if (i == 3) {
            return new X0((K0) m0, (K0) m02);
        }
        if (i == 4) {
            return new V0((I0) m0, (I0) m02);
        }
        throw new IllegalStateException("Unknown shape " + l3Var);
    }

    static E0 h0(long j) {
        return (j < 0 || j >= 2147483639) ? new C0087c1() : new C0082b1(j);
    }

    public static I i0(H h) {
        return new C(h, C0129k3.m(h));
    }

    static C0112h1 j0(C0134l3 l3Var) {
        Object obj;
        int i = N0.a[l3Var.ordinal()];
        if (i == 1) {
            return a;
        }
        if (i == 2) {
            obj = b;
        } else if (i == 3) {
            obj = c;
        } else if (i == 4) {
            obj = d;
        } else {
            throw new IllegalStateException("Unknown shape " + l3Var);
        }
        return (C0112h1) obj;
    }

    private static int l0(long j) {
        return (j != -1 ? C0129k3.u : 0) | C0129k3.t;
    }

    public static M0 m0(M0 m0, IntFunction intFunction) {
        if (m0.n() <= 0) {
            return m0;
        }
        long count = m0.count();
        if (count < 2147483639) {
            Object[] objArr = (Object[]) intFunction.apply((int) count);
            new C1(m0, objArr).invoke();
            return new P0(objArr);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public static I0 n0(I0 i0) {
        if (i0.n() <= 0) {
            return i0;
        }
        long count = i0.count();
        if (count < 2147483639) {
            double[] dArr = new double[((int) count)];
            new B1(i0, dArr).invoke();
            return new C0077a1(dArr);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public static J0 o0(J0 j0) {
        if (j0.n() <= 0) {
            return j0;
        }
        long count = j0.count();
        if (count < 2147483639) {
            int[] iArr = new int[((int) count)];
            new B1(j0, iArr).invoke();
            return new C0122j1(iArr);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public static K0 p0(K0 k0) {
        if (k0.n() <= 0) {
            return k0;
        }
        long count = k0.count();
        if (count < 2147483639) {
            long[] jArr = new long[((int) count)];
            new B1(k0, jArr).invoke();
            return new C0166s1(jArr);
        }
        throw new IllegalArgumentException("Stream size exceeds max array size");
    }

    public static Set q0(Set set) {
        if (set == null || set.isEmpty()) {
            return set;
        }
        HashSet hashSet = new HashSet();
        Object next = set.iterator().next();
        if (next instanceof C0125k) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                try {
                    hashSet.add(C0120j.b((C0125k) it.next()));
                } catch (ClassCastException e2) {
                    C0065i.a(e2, "java.util.stream.Collector.Characteristics");
                    throw null;
                }
            }
            return hashSet;
        } else if (next instanceof Collector.Characteristics) {
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                try {
                    hashSet.add(C0120j.a((Collector.Characteristics) it2.next()));
                } catch (ClassCastException e3) {
                    C0065i.a(e3, "java.util.stream.Collector.Characteristics");
                    throw null;
                }
            }
            return hashSet;
        } else {
            C0065i.a(next.getClass(), "java.util.stream.Collector.Characteristics");
            throw null;
        }
    }

    public static C0075a r0(Function function) {
        return new C0075a(function, 9);
    }

    static F0 t0(long j) {
        return (j < 0 || j >= 2147483639) ? new C0132l1() : new C0127k1(j);
    }

    public static C0126k0 u0(K k) {
        return new C0096e0(k, C0129k3.m(k));
    }

    static G0 v0(long j) {
        return (j < 0 || j >= 2147483639) ? new C0176u1() : new C0171t1(j);
    }

    public static C0175u0 w0(N n) {
        return new C0146o0(n, C0129k3.m(n));
    }

    public static I x0(C0085c cVar, long j, long j2) {
        if (j >= 0) {
            return new D2(cVar, l0(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static B0 y0(A0 a0) {
        Objects.requireNonNull(null);
        Objects.requireNonNull(a0);
        return new B0(C0134l3.DOUBLE_VALUE, a0, new C0150p(3, a0, (Object) null));
    }

    public static void z() {
        throw new IllegalStateException("called wrong accept method");
    }

    public static C0126k0 z0(C0085c cVar, long j, long j2) {
        if (j >= 0) {
            return new C0201z2(cVar, l0(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    /* access modifiers changed from: package-private */
    public abstract H0 D0(long j, IntFunction intFunction);

    public abstract Y1 G0();

    /* access modifiers changed from: package-private */
    public abstract C0182v2 I0(U u, C0182v2 v2Var);

    /* access modifiers changed from: package-private */
    public abstract C0182v2 J0(C0182v2 v2Var);

    /* access modifiers changed from: package-private */
    public abstract void f0(U u, C0182v2 v2Var);

    /* access modifiers changed from: package-private */
    public abstract boolean g0(U u, C0182v2 v2Var);

    public Object k(D0 d0, U u) {
        return ((Y1) new C0103f2(this, d0, u).invoke()).get();
    }

    /* access modifiers changed from: package-private */
    public abstract long k0(U u);

    public /* synthetic */ int o() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public abstract int s0();

    public Object y(D0 d0, U u) {
        Y1 G0 = G0();
        d0.I0(u, G0);
        return G0.get();
    }
}
