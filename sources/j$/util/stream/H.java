package j$.util.stream;

import j$.util.C0070n;
import java.util.DoubleSummaryStatistics;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final /* synthetic */ class H implements DoubleStream {
    public final /* synthetic */ I a;

    private /* synthetic */ H(I i) {
        this.a = i;
    }

    public static /* synthetic */ DoubleStream k(I i) {
        if (i == null) {
            return null;
        }
        return i instanceof G ? ((G) i).a : new H(i);
    }

    public final /* synthetic */ boolean allMatch(DoublePredicate doublePredicate) {
        return this.a.m();
    }

    public final /* synthetic */ boolean anyMatch(DoublePredicate doublePredicate) {
        return this.a.h();
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

    public final /* synthetic */ Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
        return this.a.collect(supplier, objDoubleConsumer, biConsumer);
    }

    public final /* synthetic */ long count() {
        return this.a.count();
    }

    public final /* synthetic */ DoubleStream distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ boolean equals(Object obj) {
        I i = this.a;
        if (obj instanceof H) {
            obj = ((H) obj).a;
        }
        return i.equals(obj);
    }

    public final /* synthetic */ DoubleStream filter(DoublePredicate doublePredicate) {
        return k(this.a.a());
    }

    public final /* synthetic */ OptionalDouble findAny() {
        return C0070n.f(this.a.findAny());
    }

    public final /* synthetic */ OptionalDouble findFirst() {
        return C0070n.f(this.a.findFirst());
    }

    public final DoubleStream flatMap(DoubleFunction doubleFunction) {
        return k(this.a.b(new C0075a(doubleFunction, 8)));
    }

    public final /* synthetic */ void forEach(DoubleConsumer doubleConsumer) {
        this.a.forEach(doubleConsumer);
    }

    public final /* synthetic */ void forEachOrdered(DoubleConsumer doubleConsumer) {
        this.a.forEachOrdered(doubleConsumer);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ DoubleStream limit(long j) {
        return k(this.a.limit(j));
    }

    public final /* synthetic */ DoubleStream map(DoubleUnaryOperator doubleUnaryOperator) {
        return k(this.a.c());
    }

    public final /* synthetic */ IntStream mapToInt(DoubleToIntFunction doubleToIntFunction) {
        return C0121j0.k(this.a.u());
    }

    public final /* synthetic */ LongStream mapToLong(DoubleToLongFunction doubleToLongFunction) {
        return C0170t0.k(this.a.n());
    }

    public final /* synthetic */ Stream mapToObj(DoubleFunction doubleFunction) {
        return C0114h3.k(this.a.mapToObj(doubleFunction));
    }

    public final /* synthetic */ OptionalDouble max() {
        return C0070n.f(this.a.max());
    }

    public final /* synthetic */ OptionalDouble min() {
        return C0070n.f(this.a.min());
    }

    public final /* synthetic */ boolean noneMatch(DoublePredicate doublePredicate) {
        return this.a.w();
    }

    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return C0110h.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ DoubleStream peek(DoubleConsumer doubleConsumer) {
        return k(this.a.peek(doubleConsumer));
    }

    public final /* synthetic */ double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return this.a.reduce(d, doubleBinaryOperator);
    }

    public final /* synthetic */ OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
        return C0070n.f(this.a.reduce(doubleBinaryOperator));
    }

    public final /* synthetic */ DoubleStream skip(long j) {
        return k(this.a.skip(j));
    }

    public final /* synthetic */ DoubleStream sorted() {
        return k(this.a.sorted());
    }

    public final /* synthetic */ double sum() {
        return this.a.sum();
    }

    public final DoubleSummaryStatistics summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert to java.util.DoubleSummaryStatistics");
    }

    public final /* synthetic */ double[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ BaseStream unordered() {
        return C0110h.k(this.a.unordered());
    }
}
