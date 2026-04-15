package j$.util.stream;

import j$.util.C0067k;
import j$.util.C0071o;
import j$.util.C0203u;
import j$.util.H;
import j$.util.Objects;
import j$.util.U;
import j$.util.i0;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

abstract class F extends C0085c implements I {
    F(U u, int i) {
        super(u, i, false);
    }

    F(C0085c cVar, int i) {
        super(cVar, i);
    }

    /* access modifiers changed from: private */
    public static H c1(U u) {
        if (u instanceof H) {
            return (H) u;
        }
        if (X3.a) {
            X3.a(C0085c.class, "using DoubleStream.adapt(Spliterator<Double> s)");
            throw null;
        }
        throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
    }

    /* access modifiers changed from: package-private */
    public final H0 D0(long j, IntFunction intFunction) {
        return D0.h0(j);
    }

    /* access modifiers changed from: package-private */
    public final M0 N0(D0 d0, U u, boolean z, IntFunction intFunction) {
        return D0.b0(d0, u, z);
    }

    /* access modifiers changed from: package-private */
    public final boolean O0(U u, C0182v2 v2Var) {
        DoubleConsumer doubleConsumer;
        boolean e;
        H c1 = c1(u);
        if (v2Var instanceof DoubleConsumer) {
            doubleConsumer = (DoubleConsumer) v2Var;
        } else if (!X3.a) {
            Objects.requireNonNull(v2Var);
            doubleConsumer = new C0174u(v2Var);
        } else {
            X3.a(C0085c.class, "using DoubleStream.adapt(Sink<Double> s)");
            throw null;
        }
        do {
            e = v2Var.e();
            if (e || !c1.tryAdvance(doubleConsumer)) {
                return e;
            }
            e = v2Var.e();
            break;
        } while (!c1.tryAdvance(doubleConsumer));
        return e;
    }

    /* access modifiers changed from: package-private */
    public final C0134l3 P0() {
        return C0134l3.DOUBLE_VALUE;
    }

    /* access modifiers changed from: package-private */
    public final U Z0(D0 d0, C0075a aVar, boolean z) {
        return new C0183v3(d0, aVar, z);
    }

    public final I a() {
        Objects.requireNonNull(null);
        return new C0189x(this, C0129k3.t, (Object) null, 2);
    }

    public final C0071o average() {
        double[] dArr = (double[]) collect(new C0080b(8), new C0080b(9), new C0080b(10));
        if (dArr[2] <= 0.0d) {
            return C0071o.a();
        }
        Set set = Collectors.a;
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        if (Double.isNaN(d) && Double.isInfinite(d2)) {
            d = d2;
        }
        return C0071o.d(d / dArr[2]);
    }

    public final I b(C0075a aVar) {
        Objects.requireNonNull(aVar);
        return new C0189x(this, C0129k3.p | C0129k3.n | C0129k3.t, aVar, 1);
    }

    public final Stream boxed() {
        return new C0184w(this, 0, new R0(26), 0);
    }

    public final I c() {
        Objects.requireNonNull(null);
        return new C0189x(this, C0129k3.p | C0129k3.n, (Object) null, 0);
    }

    public final Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        C0169t tVar = new C0169t(biConsumer, 0);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objDoubleConsumer);
        Objects.requireNonNull(tVar);
        return L0(new I1(C0134l3.DOUBLE_VALUE, tVar, objDoubleConsumer, supplier, 1));
    }

    public final long count() {
        return ((Long) L0(new K1(C0134l3.DOUBLE_VALUE, 1))).longValue();
    }

    /* renamed from: d1 */
    public final I unordered() {
        return !R0() ? this : new B(this, C0129k3.r, 0);
    }

    public final I distinct() {
        return ((C0143n2) ((C0143n2) boxed()).distinct()).mapToDouble(new C0080b(11));
    }

    public final C0071o findAny() {
        return (C0071o) L0(K.d);
    }

    public final C0071o findFirst() {
        return (C0071o) L0(K.c);
    }

    public void forEach(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        L0(new S(doubleConsumer, false));
    }

    public void forEachOrdered(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        L0(new S(doubleConsumer, true));
    }

    public final boolean h() {
        return ((Boolean) L0(D0.y0(A0.ANY))).booleanValue();
    }

    public final C0203u iterator() {
        return i0.f(spliterator());
    }

    public final I limit(long j) {
        if (j >= 0) {
            return D0.x0(this, 0, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final boolean m() {
        return ((Boolean) L0(D0.y0(A0.ALL))).booleanValue();
    }

    public final Stream mapToObj(DoubleFunction doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return new C0184w(this, C0129k3.p | C0129k3.n, doubleFunction, 0);
    }

    public final C0071o max() {
        return reduce(new R0(25));
    }

    public final C0071o min() {
        return reduce(new R0(24));
    }

    public final C0175u0 n() {
        Objects.requireNonNull(null);
        return new C0198z(this, C0129k3.p | C0129k3.n, (Object) null, 0);
    }

    public final I peek(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new C0189x(this, 0, doubleConsumer, 3);
    }

    public final double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return ((Double) L0(new M1(C0134l3.DOUBLE_VALUE, doubleBinaryOperator, d))).doubleValue();
    }

    public final C0071o reduce(DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return (C0071o) L0(new G1(C0134l3.DOUBLE_VALUE, doubleBinaryOperator, 1));
    }

    public final I skip(long j) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : D0.x0(this, j, -1);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final I sorted() {
        return new N2(this);
    }

    public final H spliterator() {
        return c1(super.spliterator());
    }

    public final double sum() {
        double[] dArr = (double[]) collect(new C0080b(12), new C0080b(6), new C0080b(7));
        Set set = Collectors.a;
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        return (!Double.isNaN(d) || !Double.isInfinite(d2)) ? d : d2;
    }

    public final C0067k summaryStatistics() {
        return (C0067k) collect(new R0(12), new R0(27), new R0(28));
    }

    public final double[] toArray() {
        return (double[]) D0.n0((I0) M0(new C0080b(5))).b();
    }

    public final C0126k0 u() {
        Objects.requireNonNull(null);
        return new C0194y(this, C0129k3.p | C0129k3.n, (Object) null, 0);
    }

    public final boolean w() {
        return ((Boolean) L0(D0.y0(A0.NONE))).booleanValue();
    }
}
