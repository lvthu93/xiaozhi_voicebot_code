package j$.util.stream;

import j$.util.Objects;
import j$.util.Optional;
import j$.util.U;
import j$.util.function.a;
import j$.util.i0;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.stream.n2  reason: case insensitive filesystem */
abstract class C0143n2 extends C0085c implements Stream {
    C0143n2(U u, int i, boolean z) {
        super(u, i, z);
    }

    C0143n2(C0085c cVar, int i) {
        super(cVar, i);
    }

    /* access modifiers changed from: package-private */
    public final H0 D0(long j, IntFunction intFunction) {
        return D0.Z(j, intFunction);
    }

    /* access modifiers changed from: package-private */
    public final M0 N0(D0 d0, U u, boolean z, IntFunction intFunction) {
        return D0.a0(d0, u, z, intFunction);
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    final boolean O0(j$.util.U r3, j$.util.stream.C0182v2 r4) {
        /*
            r2 = this;
        L_0x0000:
            boolean r0 = r4.e()
            if (r0 != 0) goto L_0x000c
            boolean r1 = r3.tryAdvance(r4)
            if (r1 != 0) goto L_0x0000
        L_0x000c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.C0143n2.O0(j$.util.U, j$.util.stream.v2):boolean");
    }

    /* access modifiers changed from: package-private */
    public final C0134l3 P0() {
        return C0134l3.REFERENCE;
    }

    /* access modifiers changed from: package-private */
    public final U Z0(D0 d0, C0075a aVar, boolean z) {
        return new R3(d0, aVar, z);
    }

    public final boolean allMatch(Predicate predicate) {
        return ((Boolean) L0(D0.E0(A0.ALL, predicate))).booleanValue();
    }

    public final boolean anyMatch(Predicate predicate) {
        return ((Boolean) L0(D0.E0(A0.ANY, predicate))).booleanValue();
    }

    /* renamed from: b1 */
    public final Stream unordered() {
        return !R0() ? this : new C0113h2(this, C0129k3.r);
    }

    public final Object collect(Collector collector) {
        Object obj;
        if (!isParallel() || !collector.characteristics().contains(C0125k.CONCURRENT) || (R0() && !collector.characteristics().contains(C0125k.UNORDERED))) {
            Supplier supplier = ((Collector) Objects.requireNonNull(collector)).supplier();
            obj = L0(new P1(C0134l3.REFERENCE, collector.combiner(), collector.accumulator(), supplier, collector));
        } else {
            obj = collector.supplier().get();
            forEach(new C0150p(5, collector.accumulator(), obj));
        }
        return collector.characteristics().contains(C0125k.IDENTITY_FINISH) ? obj : collector.finisher().apply(obj);
    }

    public final Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return L0(new I1(C0134l3.REFERENCE, biConsumer2, biConsumer, supplier, 3));
    }

    public final long count() {
        return ((Long) L0(new K1(C0134l3.REFERENCE, 2))).longValue();
    }

    public final Stream distinct() {
        return new C0164s(this, C0129k3.m | C0129k3.t);
    }

    public final Stream filter(Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new C0184w(this, C0129k3.t, predicate, 4);
    }

    public final Optional findAny() {
        return (Optional) L0(O.d);
    }

    public final Optional findFirst() {
        return (Optional) L0(O.c);
    }

    public final Stream flatMap(Function function) {
        Objects.requireNonNull(function);
        return new C0118i2(this, C0129k3.p | C0129k3.n | C0129k3.t, function, 1);
    }

    public void forEach(Consumer consumer) {
        Objects.requireNonNull(consumer);
        L0(new V(consumer, false));
    }

    public void forEachOrdered(Consumer consumer) {
        Objects.requireNonNull(consumer);
        L0(new V(consumer, true));
    }

    public final Iterator iterator() {
        return i0.i(spliterator());
    }

    public final Stream limit(long j) {
        if (j >= 0) {
            return D0.F0(this, 0, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final Stream map(Function function) {
        Objects.requireNonNull(function);
        return new C0118i2(this, C0129k3.p | C0129k3.n, function, 0);
    }

    public final I mapToDouble(ToDoubleFunction toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return new C0189x(this, C0129k3.p | C0129k3.n, toDoubleFunction, 6);
    }

    public final C0126k0 mapToInt(ToIntFunction toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return new C0194y(this, C0129k3.p | C0129k3.n, toIntFunction, 6);
    }

    public final C0175u0 mapToLong(ToLongFunction toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return new C0198z(this, C0129k3.p | C0129k3.n, toLongFunction, 7);
    }

    public final Optional max(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return reduce(new a(comparator, 0));
    }

    public final Optional min(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return reduce(new a(comparator, 1));
    }

    public final boolean noneMatch(Predicate predicate) {
        return ((Boolean) L0(D0.E0(A0.NONE, predicate))).booleanValue();
    }

    public final C0175u0 p(C0075a aVar) {
        Objects.requireNonNull(aVar);
        return new C0198z(this, C0129k3.p | C0129k3.n | C0129k3.t, aVar, 6);
    }

    public final Stream peek(Consumer consumer) {
        Objects.requireNonNull(consumer);
        return new C0184w(this, 0, consumer, 3);
    }

    public final Optional reduce(BinaryOperator binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        return (Optional) L0(new G1(C0134l3.REFERENCE, binaryOperator, 2));
    }

    public final Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(binaryOperator);
        return L0(new I1(C0134l3.REFERENCE, binaryOperator, biFunction, obj, 2));
    }

    public final Object reduce(Object obj, BinaryOperator binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        Objects.requireNonNull(binaryOperator);
        return L0(new I1(C0134l3.REFERENCE, binaryOperator, binaryOperator, obj, 2));
    }

    public final Stream skip(long j) {
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : D0.F0(this, j, -1);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    public final Stream sorted() {
        return new Q2(this);
    }

    public final Stream sorted(Comparator comparator) {
        return new Q2(this, comparator);
    }

    public final C0126k0 t(C0075a aVar) {
        Objects.requireNonNull(aVar);
        return new C0194y(this, C0129k3.p | C0129k3.n | C0129k3.t, aVar, 7);
    }

    public final Object[] toArray() {
        return toArray(new R0(6));
    }

    public final Object[] toArray(IntFunction intFunction) {
        return D0.m0(M0(intFunction), intFunction).k(intFunction);
    }

    public final I x(C0075a aVar) {
        Objects.requireNonNull(aVar);
        return new C0189x(this, C0129k3.p | C0129k3.n | C0129k3.t, aVar, 7);
    }
}
