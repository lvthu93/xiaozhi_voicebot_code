package j$.util.stream;

import j$.util.C0070n;
import java.util.LongSummaryStatistics;
import java.util.OptionalDouble;
import java.util.OptionalLong;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/* renamed from: j$.util.stream.t0  reason: case insensitive filesystem */
public final /* synthetic */ class C0170t0 implements LongStream {
    public final /* synthetic */ C0175u0 a;

    private /* synthetic */ C0170t0(C0175u0 u0Var) {
        this.a = u0Var;
    }

    public static /* synthetic */ LongStream k(C0175u0 u0Var) {
        if (u0Var == null) {
            return null;
        }
        return u0Var instanceof C0165s0 ? ((C0165s0) u0Var).a : new C0170t0(u0Var);
    }

    public final /* synthetic */ boolean allMatch(LongPredicate longPredicate) {
        return this.a.r();
    }

    public final /* synthetic */ boolean anyMatch(LongPredicate longPredicate) {
        return this.a.j();
    }

    public final /* synthetic */ DoubleStream asDoubleStream() {
        return H.k(this.a.asDoubleStream());
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

    public final /* synthetic */ Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
        return this.a.collect(supplier, objLongConsumer, biConsumer);
    }

    public final /* synthetic */ long count() {
        return this.a.count();
    }

    public final /* synthetic */ LongStream distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ boolean equals(Object obj) {
        C0175u0 u0Var = this.a;
        if (obj instanceof C0170t0) {
            obj = ((C0170t0) obj).a;
        }
        return u0Var.equals(obj);
    }

    public final /* synthetic */ LongStream filter(LongPredicate longPredicate) {
        return k(this.a.a());
    }

    public final /* synthetic */ OptionalLong findAny() {
        return C0070n.h(this.a.findAny());
    }

    public final /* synthetic */ OptionalLong findFirst() {
        return C0070n.h(this.a.findFirst());
    }

    public final LongStream flatMap(LongFunction longFunction) {
        return k(this.a.b(new C0075a(longFunction, 10)));
    }

    public final /* synthetic */ void forEach(LongConsumer longConsumer) {
        this.a.forEach(longConsumer);
    }

    public final /* synthetic */ void forEachOrdered(LongConsumer longConsumer) {
        this.a.forEachOrdered(longConsumer);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ LongStream limit(long j) {
        return k(this.a.limit(j));
    }

    public final /* synthetic */ LongStream map(LongUnaryOperator longUnaryOperator) {
        return k(this.a.c());
    }

    public final /* synthetic */ DoubleStream mapToDouble(LongToDoubleFunction longToDoubleFunction) {
        return H.k(this.a.e());
    }

    public final /* synthetic */ IntStream mapToInt(LongToIntFunction longToIntFunction) {
        return C0121j0.k(this.a.v());
    }

    public final /* synthetic */ Stream mapToObj(LongFunction longFunction) {
        return C0114h3.k(this.a.mapToObj(longFunction));
    }

    public final /* synthetic */ OptionalLong max() {
        return C0070n.h(this.a.max());
    }

    public final /* synthetic */ OptionalLong min() {
        return C0070n.h(this.a.min());
    }

    public final /* synthetic */ boolean noneMatch(LongPredicate longPredicate) {
        return this.a.g();
    }

    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return C0110h.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ LongStream peek(LongConsumer longConsumer) {
        return k(this.a.peek(longConsumer));
    }

    public final /* synthetic */ long reduce(long j, LongBinaryOperator longBinaryOperator) {
        return this.a.reduce(j, longBinaryOperator);
    }

    public final /* synthetic */ OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
        return C0070n.h(this.a.reduce(longBinaryOperator));
    }

    public final /* synthetic */ LongStream skip(long j) {
        return k(this.a.skip(j));
    }

    public final /* synthetic */ LongStream sorted() {
        return k(this.a.sorted());
    }

    public final /* synthetic */ long sum() {
        return this.a.sum();
    }

    public final LongSummaryStatistics summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert to java.util.LongSummaryStatistics");
    }

    public final /* synthetic */ long[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ BaseStream unordered() {
        return C0110h.k(this.a.unordered());
    }
}
