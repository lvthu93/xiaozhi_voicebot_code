package j$.util.stream;

import j$.util.C0068l;
import j$.util.C0071o;
import j$.util.C0072p;
import j$.util.C0207y;
import j$.util.K;
import j$.util.Objects;
import j$.util.U;
import j$.util.i0;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.h0  reason: case insensitive filesystem */
abstract class C0111h0 extends C0085c implements C0126k0 {
    C0111h0(U u, int i) {
        super(u, i, false);
    }

    C0111h0(C0085c cVar, int i) {
        super(cVar, i);
    }

    /* access modifiers changed from: private */
    public static K c1(U u) {
        if (u instanceof K) {
            return (K) u;
        }
        if (X3.a) {
            X3.a(C0085c.class, "using IntStream.adapt(Spliterator<Integer> s)");
            throw null;
        }
        throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
    }

    /* access modifiers changed from: package-private */
    public final H0 D0(long j, IntFunction intFunction) {
        return D0.t0(j);
    }

    /* access modifiers changed from: package-private */
    public final M0 N0(D0 d0, U u, boolean z, IntFunction intFunction) {
        return D0.c0(d0, u, z);
    }

    /* access modifiers changed from: package-private */
    public final boolean O0(U u, C0182v2 v2Var) {
        IntConsumer intConsumer;
        boolean e;
        K c1 = c1(u);
        if (v2Var instanceof IntConsumer) {
            intConsumer = (IntConsumer) v2Var;
        } else if (!X3.a) {
            Objects.requireNonNull(v2Var);
            intConsumer = new Z(v2Var);
        } else {
            X3.a(C0085c.class, "using IntStream.adapt(Sink<Integer> s)");
            throw null;
        }
        do {
            e = v2Var.e();
            if (e || !c1.tryAdvance(intConsumer)) {
                return e;
            }
            e = v2Var.e();
            break;
        } while (!c1.tryAdvance(intConsumer));
        return e;
    }

    /* access modifiers changed from: package-private */
    public final C0134l3 P0() {
        return C0134l3.INT_VALUE;
    }

    /* access modifiers changed from: package-private */
    public final U Z0(D0 d0, C0075a aVar, boolean z) {
        return new C0193x3(d0, aVar, z);
    }

    public final C0126k0 a() {
        Objects.requireNonNull(null);
        return new C0194y(this, C0129k3.t, (Object) null, 4);
    }

    public final I asDoubleStream() {
        return new B(this, 0, 1);
    }

    public final C0175u0 asLongStream() {
        return new C0081b0(this, 0, 0);
    }

    public final C0071o average() {
        long[] jArr = (long[]) collect(new C0080b(19), new C0080b(20), new C0080b(21));
        long j = jArr[0];
        return j > 0 ? C0071o.d(((double) jArr[1]) / ((double) j)) : C0071o.a();
    }

    public final Stream boxed() {
        return new C0184w(this, 0, new L(7), 1);
    }

    public final Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        C0169t tVar = new C0169t(biConsumer, 1);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objIntConsumer);
        Objects.requireNonNull(tVar);
        return L0(new I1(C0134l3.INT_VALUE, tVar, objIntConsumer, supplier, 4));
    }

    public final long count() {
        return ((Long) L0(new K1(C0134l3.INT_VALUE, 3))).longValue();
    }

    public final I d() {
        Objects.requireNonNull(null);
        return new C0189x(this, C0129k3.p | C0129k3.n, (Object) null, 4);
    }

    /* renamed from: d1 */
    public final C0126k0 unordered() {
        return !R0() ? this : new C0091d0(this, C0129k3.r);
    }

    public final C0126k0 distinct() {
        return ((C0143n2) ((C0143n2) boxed()).distinct()).mapToInt(new C0080b(18));
    }

    public final C0175u0 f() {
        Objects.requireNonNull(null);
        return new C0198z(this, C0129k3.p | C0129k3.n, (Object) null, 1);
    }

    public final C0072p findAny() {
        return (C0072p) L0(M.d);
    }

    public final C0072p findFirst() {
        return (C0072p) L0(M.c);
    }

    public void forEach(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        L0(new T(intConsumer, false));
    }

    public void forEachOrdered(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        L0(new T(intConsumer, true));
    }

    public final boolean i() {
        return ((Boolean) L0(D0.A0(A0.ALL))).booleanValue();
    }

    public final C0207y iterator() {
        return i0.g(spliterator());
    }

    public final boolean l() {
        return ((Boolean) L0(D0.A0(A0.NONE))).booleanValue();
    }

    public final C0126k0 limit(long j) {
        if (j >= 0) {
            return D0.z0(this, 0, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final C0126k0 map(IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new C0194y(this, C0129k3.p | C0129k3.n, intUnaryOperator, 2);
    }

    public final Stream mapToObj(IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        return new C0184w(this, C0129k3.p | C0129k3.n, intFunction, 1);
    }

    public final C0072p max() {
        return reduce(new L(8));
    }

    public final C0072p min() {
        return reduce(new L(3));
    }

    public final C0126k0 peek(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new C0194y(this, 0, intConsumer, 1);
    }

    public final C0126k0 q(T0 t0) {
        Objects.requireNonNull(t0);
        return new C0194y(this, C0129k3.p | C0129k3.n | C0129k3.t, t0, 3);
    }

    public final int reduce(int i, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return ((Integer) L0(new T1(C0134l3.INT_VALUE, intBinaryOperator, i))).intValue();
    }

    public final C0072p reduce(IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return (C0072p) L0(new G1(C0134l3.INT_VALUE, intBinaryOperator, 3));
    }

    public final boolean s() {
        return ((Boolean) L0(D0.A0(A0.ANY))).booleanValue();
    }

    public final C0126k0 skip(long j) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : D0.z0(this, j, -1);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final C0126k0 sorted() {
        return new O2(this);
    }

    public final K spliterator() {
        return c1(super.spliterator());
    }

    public final int sum() {
        return reduce(0, new L(4));
    }

    public final C0068l summaryStatistics() {
        return (C0068l) collect(new R0(16), new L(5), new L(6));
    }

    public final int[] toArray() {
        return (int[]) D0.o0((J0) M0(new C0080b(22))).b();
    }
}
