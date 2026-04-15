package j$.util.stream;

import j$.util.C0067k;
import j$.util.C0070n;
import j$.util.C0071o;
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
import java.util.stream.DoubleStream;

public final /* synthetic */ class G implements I {
    public final /* synthetic */ DoubleStream a;

    private /* synthetic */ G(DoubleStream doubleStream) {
        this.a = doubleStream;
    }

    public static /* synthetic */ I k(DoubleStream doubleStream) {
        if (doubleStream == null) {
            return null;
        }
        return doubleStream instanceof H ? ((H) doubleStream).a : new G(doubleStream);
    }

    public final /* synthetic */ I a() {
        return k(this.a.filter((DoublePredicate) null));
    }

    public final /* synthetic */ C0071o average() {
        return C0070n.b(this.a.average());
    }

    public final I b(C0075a aVar) {
        return k(this.a.flatMap(new C0075a(aVar, 8)));
    }

    public final /* synthetic */ Stream boxed() {
        return C0109g3.k(this.a.boxed());
    }

    public final /* synthetic */ I c() {
        return k(this.a.map((DoubleUnaryOperator) null));
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

    public final /* synthetic */ I distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof G) {
            obj = ((G) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ C0071o findAny() {
        return C0070n.b(this.a.findAny());
    }

    public final /* synthetic */ C0071o findFirst() {
        return C0070n.b(this.a.findFirst());
    }

    public final /* synthetic */ void forEach(DoubleConsumer doubleConsumer) {
        this.a.forEach(doubleConsumer);
    }

    public final /* synthetic */ void forEachOrdered(DoubleConsumer doubleConsumer) {
        this.a.forEachOrdered(doubleConsumer);
    }

    public final /* synthetic */ boolean h() {
        return this.a.anyMatch((DoublePredicate) null);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ I limit(long j) {
        return k(this.a.limit(j));
    }

    public final /* synthetic */ boolean m() {
        return this.a.allMatch((DoublePredicate) null);
    }

    public final /* synthetic */ Stream mapToObj(DoubleFunction doubleFunction) {
        return C0109g3.k(this.a.mapToObj(doubleFunction));
    }

    public final /* synthetic */ C0071o max() {
        return C0070n.b(this.a.max());
    }

    public final /* synthetic */ C0071o min() {
        return C0070n.b(this.a.min());
    }

    public final /* synthetic */ C0175u0 n() {
        return C0165s0.k(this.a.mapToLong((DoubleToLongFunction) null));
    }

    public final /* synthetic */ C0115i onClose(Runnable runnable) {
        return C0105g.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ I peek(DoubleConsumer doubleConsumer) {
        return k(this.a.peek(doubleConsumer));
    }

    public final /* synthetic */ double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return this.a.reduce(d, doubleBinaryOperator);
    }

    public final /* synthetic */ C0071o reduce(DoubleBinaryOperator doubleBinaryOperator) {
        return C0070n.b(this.a.reduce(doubleBinaryOperator));
    }

    public final /* synthetic */ I skip(long j) {
        return k(this.a.skip(j));
    }

    public final /* synthetic */ I sorted() {
        return k(this.a.sorted());
    }

    public final /* synthetic */ double sum() {
        return this.a.sum();
    }

    public final C0067k summaryStatistics() {
        this.a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.DoubleSummaryStatistics");
    }

    public final /* synthetic */ double[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ C0126k0 u() {
        return C0116i0.k(this.a.mapToInt((DoubleToIntFunction) null));
    }

    public final /* synthetic */ C0115i unordered() {
        return C0105g.k(this.a.unordered());
    }

    public final /* synthetic */ boolean w() {
        return this.a.noneMatch((DoublePredicate) null);
    }
}
