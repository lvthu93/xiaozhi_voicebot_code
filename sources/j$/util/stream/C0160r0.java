package j$.util.stream;

import j$.util.C;
import j$.util.C0069m;
import j$.util.C0071o;
import j$.util.C0073q;
import j$.util.N;
import j$.util.Objects;
import j$.util.U;
import j$.util.i0;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.r0  reason: case insensitive filesystem */
abstract class C0160r0 extends C0085c implements C0175u0 {
    C0160r0(U u, int i) {
        super(u, i, false);
    }

    C0160r0(C0085c cVar, int i) {
        super(cVar, i);
    }

    /* access modifiers changed from: private */
    public static N c1(U u) {
        if (u instanceof N) {
            return (N) u;
        }
        if (X3.a) {
            X3.a(C0085c.class, "using LongStream.adapt(Spliterator<Long> s)");
            throw null;
        }
        throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
    }

    /* access modifiers changed from: package-private */
    public final H0 D0(long j, IntFunction intFunction) {
        return D0.v0(j);
    }

    /* access modifiers changed from: package-private */
    public final M0 N0(D0 d0, U u, boolean z, IntFunction intFunction) {
        return D0.d0(d0, u, z);
    }

    /* access modifiers changed from: package-private */
    public final boolean O0(U u, C0182v2 v2Var) {
        LongConsumer longConsumer;
        boolean e;
        N c1 = c1(u);
        if (v2Var instanceof LongConsumer) {
            longConsumer = (LongConsumer) v2Var;
        } else if (!X3.a) {
            Objects.requireNonNull(v2Var);
            longConsumer = new C0131l0(v2Var);
        } else {
            X3.a(C0085c.class, "using LongStream.adapt(Sink<Long> s)");
            throw null;
        }
        do {
            e = v2Var.e();
            if (e || !c1.tryAdvance(longConsumer)) {
                return e;
            }
            e = v2Var.e();
            break;
        } while (!c1.tryAdvance(longConsumer));
        return e;
    }

    /* access modifiers changed from: package-private */
    public final C0134l3 P0() {
        return C0134l3.LONG_VALUE;
    }

    /* access modifiers changed from: package-private */
    public final U Z0(D0 d0, C0075a aVar, boolean z) {
        return new z3(d0, aVar, z);
    }

    public final C0175u0 a() {
        Objects.requireNonNull(null);
        return new C0198z(this, C0129k3.t, (Object) null, 4);
    }

    public final I asDoubleStream() {
        return new B(this, C0129k3.n, 2);
    }

    public final C0071o average() {
        long[] jArr = (long[]) collect(new C0080b(25), new C0080b(26), new C0080b(27));
        long j = jArr[0];
        return j > 0 ? C0071o.d(((double) jArr[1]) / ((double) j)) : C0071o.a();
    }

    public final C0175u0 b(C0075a aVar) {
        Objects.requireNonNull(aVar);
        return new C0198z(this, C0129k3.p | C0129k3.n | C0129k3.t, aVar, 3);
    }

    public final Stream boxed() {
        return new C0184w(this, 0, new L(10), 2);
    }

    public final C0175u0 c() {
        Objects.requireNonNull(null);
        return new C0198z(this, C0129k3.p | C0129k3.n, (Object) null, 2);
    }

    public final Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        C0169t tVar = new C0169t(biConsumer, 2);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objLongConsumer);
        Objects.requireNonNull(tVar);
        return L0(new I1(C0134l3.LONG_VALUE, tVar, objLongConsumer, supplier, 0));
    }

    public final long count() {
        return ((Long) L0(new K1(C0134l3.LONG_VALUE, 0))).longValue();
    }

    /* renamed from: d1 */
    public final C0175u0 unordered() {
        return !R0() ? this : new C0081b0(this, C0129k3.r, 1);
    }

    public final C0175u0 distinct() {
        return ((C0143n2) ((C0143n2) boxed()).distinct()).mapToLong(new C0080b(23));
    }

    public final I e() {
        Objects.requireNonNull(null);
        return new C0189x(this, C0129k3.p | C0129k3.n, (Object) null, 5);
    }

    public final C0073q findAny() {
        return (C0073q) L0(N.d);
    }

    public final C0073q findFirst() {
        return (C0073q) L0(N.c);
    }

    public void forEach(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        L0(new U(longConsumer, false));
    }

    public void forEachOrdered(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        L0(new U(longConsumer, true));
    }

    public final boolean g() {
        return ((Boolean) L0(D0.C0(A0.NONE))).booleanValue();
    }

    public final C iterator() {
        return i0.h(spliterator());
    }

    public final boolean j() {
        return ((Boolean) L0(D0.C0(A0.ANY))).booleanValue();
    }

    public final C0175u0 limit(long j) {
        if (j >= 0) {
            return D0.B0(this, 0, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final Stream mapToObj(LongFunction longFunction) {
        Objects.requireNonNull(longFunction);
        return new C0184w(this, C0129k3.p | C0129k3.n, longFunction, 2);
    }

    public final C0073q max() {
        return reduce(new L(9));
    }

    public final C0073q min() {
        return reduce(new L(14));
    }

    public final C0175u0 peek(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new C0198z(this, 0, longConsumer, 5);
    }

    public final boolean r() {
        return ((Boolean) L0(D0.C0(A0.ALL))).booleanValue();
    }

    public final long reduce(long j, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return ((Long) L0(new E1(C0134l3.LONG_VALUE, longBinaryOperator, j))).longValue();
    }

    public final C0073q reduce(LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return (C0073q) L0(new G1(C0134l3.LONG_VALUE, longBinaryOperator, 0));
    }

    public final C0175u0 skip(long j) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : D0.B0(this, j, -1);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final C0175u0 sorted() {
        return new P2(this);
    }

    public final N spliterator() {
        return c1(super.spliterator());
    }

    public final long sum() {
        return reduce(0, new L(11));
    }

    public final C0069m summaryStatistics() {
        return (C0069m) collect(new R0(17), new L(12), new L(13));
    }

    public final long[] toArray() {
        return (long[]) D0.p0((K0) M0(new C0080b(24))).b();
    }

    public final C0126k0 v() {
        Objects.requireNonNull(null);
        return new C0194y(this, C0129k3.p | C0129k3.n, (Object) null, 5);
    }
}
