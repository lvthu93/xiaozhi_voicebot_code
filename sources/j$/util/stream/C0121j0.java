package j$.util.stream;

import j$.util.C0070n;
import java.util.IntSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalInt;
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
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/* renamed from: j$.util.stream.j0  reason: case insensitive filesystem */
public final /* synthetic */ class C0121j0 implements IntStream {
    public final /* synthetic */ C0126k0 a;

    private /* synthetic */ C0121j0(C0126k0 k0Var) {
        this.a = k0Var;
    }

    public static /* synthetic */ IntStream k(C0126k0 k0Var) {
        if (k0Var == null) {
            return null;
        }
        return k0Var instanceof C0116i0 ? ((C0116i0) k0Var).a : new C0121j0(k0Var);
    }

    public final /* synthetic */ boolean allMatch(IntPredicate intPredicate) {
        return this.a.i();
    }

    public final /* synthetic */ boolean anyMatch(IntPredicate intPredicate) {
        return this.a.s();
    }

    public final /* synthetic */ DoubleStream asDoubleStream() {
        return H.k(this.a.asDoubleStream());
    }

    public final /* synthetic */ LongStream asLongStream() {
        return C0170t0.k(this.a.asLongStream());
    }

    public final /* synthetic */ OptionalDouble average() {
        return C0070n.f(this.a.average());
    }

    public final /* synthetic */ Stream boxed() {
        return C0114h3.k(this.a.boxed());
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

    public final /* synthetic */ IntStream distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ boolean equals(Object obj) {
        C0126k0 k0Var = this.a;
        if (obj instanceof C0121j0) {
            obj = ((C0121j0) obj).a;
        }
        return k0Var.equals(obj);
    }

    public final /* synthetic */ IntStream filter(IntPredicate intPredicate) {
        return k(this.a.a());
    }

    public final /* synthetic */ OptionalInt findAny() {
        return C0070n.g(this.a.findAny());
    }

    public final /* synthetic */ OptionalInt findFirst() {
        return C0070n.g(this.a.findFirst());
    }

    public final IntStream flatMap(IntFunction intFunction) {
        return k(this.a.q(new T0(intFunction)));
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

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ IntStream limit(long j) {
        return k(this.a.limit(j));
    }

    public final /* synthetic */ IntStream map(IntUnaryOperator intUnaryOperator) {
        return k(this.a.map(intUnaryOperator));
    }

    public final /* synthetic */ DoubleStream mapToDouble(IntToDoubleFunction intToDoubleFunction) {
        return H.k(this.a.d());
    }

    public final /* synthetic */ LongStream mapToLong(IntToLongFunction intToLongFunction) {
        return C0170t0.k(this.a.f());
    }

    public final /* synthetic */ Stream mapToObj(IntFunction intFunction) {
        return C0114h3.k(this.a.mapToObj(intFunction));
    }

    public final /* synthetic */ OptionalInt max() {
        return C0070n.g(this.a.max());
    }

    public final /* synthetic */ OptionalInt min() {
        return C0070n.g(this.a.min());
    }

    public final /* synthetic */ boolean noneMatch(IntPredicate intPredicate) {
        return this.a.l();
    }

    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return C0110h.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ IntStream peek(IntConsumer intConsumer) {
        return k(this.a.peek(intConsumer));
    }

    public final /* synthetic */ int reduce(int i, IntBinaryOperator intBinaryOperator) {
        return this.a.reduce(i, intBinaryOperator);
    }

    public final /* synthetic */ OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        return C0070n.g(this.a.reduce(intBinaryOperator));
    }

    public final /* synthetic */ IntStream skip(long j) {
        return k(this.a.skip(j));
    }

    public final /* synthetic */ IntStream sorted() {
        return k(this.a.sorted());
    }

    public final /* synthetic */ int sum() {
        return this.a.sum();
    }

    public final IntSummaryStatistics summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert to java.util.IntSummaryStatistics");
    }

    public final /* synthetic */ int[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ BaseStream unordered() {
        return C0110h.k(this.a.unordered());
    }
}
