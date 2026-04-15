package j$.util.stream;

import j$.util.C0069m;
import j$.util.C0070n;
import j$.util.C0071o;
import j$.util.C0073q;
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
import java.util.stream.LongStream;

/* renamed from: j$.util.stream.s0  reason: case insensitive filesystem */
public final /* synthetic */ class C0165s0 implements C0175u0 {
    public final /* synthetic */ LongStream a;

    private /* synthetic */ C0165s0(LongStream longStream) {
        this.a = longStream;
    }

    public static /* synthetic */ C0175u0 k(LongStream longStream) {
        if (longStream == null) {
            return null;
        }
        return longStream instanceof C0170t0 ? ((C0170t0) longStream).a : new C0165s0(longStream);
    }

    public final /* synthetic */ C0175u0 a() {
        return k(this.a.filter((LongPredicate) null));
    }

    public final /* synthetic */ I asDoubleStream() {
        return G.k(this.a.asDoubleStream());
    }

    public final /* synthetic */ C0071o average() {
        return C0070n.b(this.a.average());
    }

    public final C0175u0 b(C0075a aVar) {
        return k(this.a.flatMap(new C0075a(aVar, 10)));
    }

    public final /* synthetic */ Stream boxed() {
        return C0109g3.k(this.a.boxed());
    }

    public final /* synthetic */ C0175u0 c() {
        return k(this.a.map((LongUnaryOperator) null));
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

    public final /* synthetic */ C0175u0 distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ I e() {
        return G.k(this.a.mapToDouble((LongToDoubleFunction) null));
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof C0165s0) {
            obj = ((C0165s0) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ C0073q findAny() {
        return C0070n.d(this.a.findAny());
    }

    public final /* synthetic */ C0073q findFirst() {
        return C0070n.d(this.a.findFirst());
    }

    public final /* synthetic */ void forEach(LongConsumer longConsumer) {
        this.a.forEach(longConsumer);
    }

    public final /* synthetic */ void forEachOrdered(LongConsumer longConsumer) {
        this.a.forEachOrdered(longConsumer);
    }

    public final /* synthetic */ boolean g() {
        return this.a.noneMatch((LongPredicate) null);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ boolean j() {
        return this.a.anyMatch((LongPredicate) null);
    }

    public final /* synthetic */ C0175u0 limit(long j) {
        return k(this.a.limit(j));
    }

    public final /* synthetic */ Stream mapToObj(LongFunction longFunction) {
        return C0109g3.k(this.a.mapToObj(longFunction));
    }

    public final /* synthetic */ C0073q max() {
        return C0070n.d(this.a.max());
    }

    public final /* synthetic */ C0073q min() {
        return C0070n.d(this.a.min());
    }

    public final /* synthetic */ C0115i onClose(Runnable runnable) {
        return C0105g.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ C0175u0 peek(LongConsumer longConsumer) {
        return k(this.a.peek(longConsumer));
    }

    public final /* synthetic */ boolean r() {
        return this.a.allMatch((LongPredicate) null);
    }

    public final /* synthetic */ long reduce(long j, LongBinaryOperator longBinaryOperator) {
        return this.a.reduce(j, longBinaryOperator);
    }

    public final /* synthetic */ C0073q reduce(LongBinaryOperator longBinaryOperator) {
        return C0070n.d(this.a.reduce(longBinaryOperator));
    }

    public final /* synthetic */ C0175u0 skip(long j) {
        return k(this.a.skip(j));
    }

    public final /* synthetic */ C0175u0 sorted() {
        return k(this.a.sorted());
    }

    public final /* synthetic */ long sum() {
        return this.a.sum();
    }

    public final C0069m summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.LongSummaryStatistics");
    }

    public final /* synthetic */ long[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ C0115i unordered() {
        return C0105g.k(this.a.unordered());
    }

    public final /* synthetic */ C0126k0 v() {
        return C0116i0.k(this.a.mapToInt((LongToIntFunction) null));
    }
}
