package j$.util.stream;

import j$.util.C0068l;
import j$.util.C0070n;
import j$.util.C0071o;
import j$.util.C0072p;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/* renamed from: j$.util.stream.i0  reason: case insensitive filesystem */
public final /* synthetic */ class C0116i0 implements C0126k0 {
    public final /* synthetic */ IntStream a;

    private /* synthetic */ C0116i0(IntStream intStream) {
        this.a = intStream;
    }

    public static /* synthetic */ C0126k0 k(IntStream intStream) {
        if (intStream == null) {
            return null;
        }
        return intStream instanceof C0121j0 ? ((C0121j0) intStream).a : new C0116i0(intStream);
    }

    public final /* synthetic */ C0126k0 a() {
        return k(this.a.filter((IntPredicate) null));
    }

    public final /* synthetic */ I asDoubleStream() {
        return G.k(this.a.asDoubleStream());
    }

    public final /* synthetic */ C0175u0 asLongStream() {
        return C0165s0.k(this.a.asLongStream());
    }

    public final /* synthetic */ C0071o average() {
        return C0070n.b(this.a.average());
    }

    public final /* synthetic */ Stream boxed() {
        return C0109g3.k(this.a.boxed());
    }

    public final /* synthetic */ void close() {
        this.a.close();
    }

    public final /* synthetic */ Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
        return this.a.collect(supplier, objIntConsumer, biConsumer);
    }

    public final /* synthetic */ long count() {
        return this.a.count();
    }

    public final /* synthetic */ I d() {
        return G.k(this.a.mapToDouble((IntToDoubleFunction) null));
    }

    public final /* synthetic */ C0126k0 distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof C0116i0) {
            obj = ((C0116i0) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ C0175u0 f() {
        return C0165s0.k(this.a.mapToLong((IntToLongFunction) null));
    }

    public final /* synthetic */ C0072p findAny() {
        return C0070n.c(this.a.findAny());
    }

    public final /* synthetic */ C0072p findFirst() {
        return C0070n.c(this.a.findFirst());
    }

    public final /* synthetic */ void forEach(IntConsumer intConsumer) {
        this.a.forEach(intConsumer);
    }

    public final /* synthetic */ void forEachOrdered(IntConsumer intConsumer) {
        this.a.forEachOrdered(intConsumer);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean i() {
        return this.a.allMatch((IntPredicate) null);
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ boolean l() {
        return this.a.noneMatch((IntPredicate) null);
    }

    public final /* synthetic */ C0126k0 limit(long j) {
        return k(this.a.limit(j));
    }

    public final /* synthetic */ C0126k0 map(IntUnaryOperator intUnaryOperator) {
        return k(this.a.map(intUnaryOperator));
    }

    public final /* synthetic */ Stream mapToObj(IntFunction intFunction) {
        return C0109g3.k(this.a.mapToObj(intFunction));
    }

    public final /* synthetic */ C0072p max() {
        return C0070n.c(this.a.max());
    }

    public final /* synthetic */ C0072p min() {
        return C0070n.c(this.a.min());
    }

    public final /* synthetic */ C0115i onClose(Runnable runnable) {
        return C0105g.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ C0126k0 peek(IntConsumer intConsumer) {
        return k(this.a.peek(intConsumer));
    }

    public final C0126k0 q(T0 t0) {
        return k(this.a.flatMap(new T0(t0)));
    }

    public final /* synthetic */ int reduce(int i, IntBinaryOperator intBinaryOperator) {
        return this.a.reduce(i, intBinaryOperator);
    }

    public final /* synthetic */ C0072p reduce(IntBinaryOperator intBinaryOperator) {
        return C0070n.c(this.a.reduce(intBinaryOperator));
    }

    public final /* synthetic */ boolean s() {
        return this.a.anyMatch((IntPredicate) null);
    }

    public final /* synthetic */ C0126k0 skip(long j) {
        return k(this.a.skip(j));
    }

    public final /* synthetic */ C0126k0 sorted() {
        return k(this.a.sorted());
    }

    public final /* synthetic */ int sum() {
        return this.a.sum();
    }

    public final C0068l summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.IntSummaryStatistics");
    }

    public final /* synthetic */ int[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ C0115i unordered() {
        return C0105g.k(this.a.unordered());
    }
}
