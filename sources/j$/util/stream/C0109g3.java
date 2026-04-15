package j$.util.stream;

import j$.util.C0070n;
import j$.util.Optional;
import j$.util.S;
import j$.util.U;
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
import java.util.stream.Stream;

/* renamed from: j$.util.stream.g3  reason: case insensitive filesystem */
public final /* synthetic */ class C0109g3 implements Stream {
    public final /* synthetic */ Stream a;

    private /* synthetic */ C0109g3(Stream stream) {
        this.a = stream;
    }

    public static /* synthetic */ Stream k(Stream stream) {
        if (stream == null) {
            return null;
        }
        return stream instanceof C0114h3 ? ((C0114h3) stream).a : new C0109g3(stream);
    }

    public final /* synthetic */ boolean allMatch(Predicate predicate) {
        return this.a.allMatch(predicate);
    }

    public final /* synthetic */ boolean anyMatch(Predicate predicate) {
        return this.a.anyMatch(predicate);
    }

    public final /* synthetic */ void close() {
        this.a.close();
    }

    public final /* synthetic */ Object collect(Collector collector) {
        return this.a.collect(C0135m.a(collector));
    }

    public final /* synthetic */ Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        return this.a.collect(supplier, biConsumer, biConsumer2);
    }

    public final /* synthetic */ long count() {
        return this.a.count();
    }

    public final /* synthetic */ Stream distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof C0109g3) {
            obj = ((C0109g3) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ Stream filter(Predicate predicate) {
        return k(this.a.filter(predicate));
    }

    public final /* synthetic */ Optional findAny() {
        return C0070n.a(this.a.findAny());
    }

    public final /* synthetic */ Optional findFirst() {
        return C0070n.a(this.a.findFirst());
    }

    public final /* synthetic */ Stream flatMap(Function function) {
        return k(this.a.flatMap(D0.r0(function)));
    }

    public final /* synthetic */ void forEach(Consumer consumer) {
        this.a.forEach(consumer);
    }

    public final /* synthetic */ void forEachOrdered(Consumer consumer) {
        this.a.forEachOrdered(consumer);
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ boolean isParallel() {
        return this.a.isParallel();
    }

    public final /* synthetic */ Iterator iterator() {
        return this.a.iterator();
    }

    public final /* synthetic */ Stream limit(long j) {
        return k(this.a.limit(j));
    }

    public final /* synthetic */ Stream map(Function function) {
        return k(this.a.map(function));
    }

    public final /* synthetic */ I mapToDouble(ToDoubleFunction toDoubleFunction) {
        return G.k(this.a.mapToDouble(toDoubleFunction));
    }

    public final /* synthetic */ C0126k0 mapToInt(ToIntFunction toIntFunction) {
        return C0116i0.k(this.a.mapToInt(toIntFunction));
    }

    public final /* synthetic */ C0175u0 mapToLong(ToLongFunction toLongFunction) {
        return C0165s0.k(this.a.mapToLong(toLongFunction));
    }

    public final /* synthetic */ Optional max(Comparator comparator) {
        return C0070n.a(this.a.max(comparator));
    }

    public final /* synthetic */ Optional min(Comparator comparator) {
        return C0070n.a(this.a.min(comparator));
    }

    public final /* synthetic */ boolean noneMatch(Predicate predicate) {
        return this.a.noneMatch(predicate);
    }

    public final /* synthetic */ C0115i onClose(Runnable runnable) {
        return C0105g.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ C0175u0 p(C0075a aVar) {
        return C0165s0.k(this.a.flatMapToLong(D0.r0(aVar)));
    }

    public final /* synthetic */ C0115i parallel() {
        return C0105g.k(this.a.parallel());
    }

    public final /* synthetic */ Stream peek(Consumer consumer) {
        return k(this.a.peek(consumer));
    }

    public final /* synthetic */ Optional reduce(BinaryOperator binaryOperator) {
        return C0070n.a(this.a.reduce(binaryOperator));
    }

    public final /* synthetic */ Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        return this.a.reduce(obj, biFunction, binaryOperator);
    }

    public final /* synthetic */ Object reduce(Object obj, BinaryOperator binaryOperator) {
        return this.a.reduce(obj, binaryOperator);
    }

    public final /* synthetic */ C0115i sequential() {
        return C0105g.k(this.a.sequential());
    }

    public final /* synthetic */ Stream skip(long j) {
        return k(this.a.skip(j));
    }

    public final /* synthetic */ Stream sorted() {
        return k(this.a.sorted());
    }

    public final /* synthetic */ Stream sorted(Comparator comparator) {
        return k(this.a.sorted(comparator));
    }

    public final /* synthetic */ U spliterator() {
        return S.a(this.a.spliterator());
    }

    public final /* synthetic */ C0126k0 t(C0075a aVar) {
        return C0116i0.k(this.a.flatMapToInt(D0.r0(aVar)));
    }

    public final /* synthetic */ Object[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return this.a.toArray(intFunction);
    }

    public final /* synthetic */ C0115i unordered() {
        return C0105g.k(this.a.unordered());
    }

    public final /* synthetic */ I x(C0075a aVar) {
        return G.k(this.a.flatMapToDouble(D0.r0(aVar)));
    }
}
