package j$.util.stream;

import j$.util.C0070n;
import j$.util.T;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
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
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/* renamed from: j$.util.stream.h3  reason: case insensitive filesystem */
public final /* synthetic */ class C0114h3 implements Stream {
    public final /* synthetic */ Stream a;

    private /* synthetic */ C0114h3(Stream stream) {
        this.a = stream;
    }

    public static /* synthetic */ Stream k(Stream stream) {
        if (stream == null) {
            return null;
        }
        return stream instanceof C0109g3 ? ((C0109g3) stream).a : new C0114h3(stream);
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

    public final /* synthetic */ Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        return this.a.collect(supplier, biConsumer, biConsumer2);
    }

    public final /* synthetic */ Object collect(Collector collector) {
        return this.a.collect(C0130l.a(collector));
    }

    public final /* synthetic */ long count() {
        return this.a.count();
    }

    public final /* synthetic */ Stream distinct() {
        return k(this.a.distinct());
    }

    public final /* synthetic */ boolean equals(Object obj) {
        Stream stream = this.a;
        if (obj instanceof C0114h3) {
            obj = ((C0114h3) obj).a;
        }
        return stream.equals(obj);
    }

    public final /* synthetic */ Stream filter(Predicate predicate) {
        return k(this.a.filter(predicate));
    }

    public final /* synthetic */ Optional findAny() {
        return C0070n.e(this.a.findAny());
    }

    public final /* synthetic */ Optional findFirst() {
        return C0070n.e(this.a.findFirst());
    }

    public final /* synthetic */ Stream flatMap(Function function) {
        return k(this.a.flatMap(D0.r0(function)));
    }

    public final /* synthetic */ DoubleStream flatMapToDouble(Function function) {
        return H.k(this.a.x(D0.r0(function)));
    }

    public final /* synthetic */ IntStream flatMapToInt(Function function) {
        return C0121j0.k(this.a.t(D0.r0(function)));
    }

    public final /* synthetic */ LongStream flatMapToLong(Function function) {
        return C0170t0.k(this.a.p(D0.r0(function)));
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

    public final /* synthetic */ DoubleStream mapToDouble(ToDoubleFunction toDoubleFunction) {
        return H.k(this.a.mapToDouble(toDoubleFunction));
    }

    public final /* synthetic */ IntStream mapToInt(ToIntFunction toIntFunction) {
        return C0121j0.k(this.a.mapToInt(toIntFunction));
    }

    public final /* synthetic */ LongStream mapToLong(ToLongFunction toLongFunction) {
        return C0170t0.k(this.a.mapToLong(toLongFunction));
    }

    public final /* synthetic */ Optional max(Comparator comparator) {
        return C0070n.e(this.a.max(comparator));
    }

    public final /* synthetic */ Optional min(Comparator comparator) {
        return C0070n.e(this.a.min(comparator));
    }

    public final /* synthetic */ boolean noneMatch(Predicate predicate) {
        return this.a.noneMatch(predicate);
    }

    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return C0110h.k(this.a.onClose(runnable));
    }

    public final /* synthetic */ BaseStream parallel() {
        return C0110h.k(this.a.parallel());
    }

    public final /* synthetic */ Stream peek(Consumer consumer) {
        return k(this.a.peek(consumer));
    }

    public final /* synthetic */ Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        return this.a.reduce(obj, biFunction, binaryOperator);
    }

    public final /* synthetic */ Object reduce(Object obj, BinaryOperator binaryOperator) {
        return this.a.reduce(obj, binaryOperator);
    }

    public final /* synthetic */ Optional reduce(BinaryOperator binaryOperator) {
        return C0070n.e(this.a.reduce(binaryOperator));
    }

    public final /* synthetic */ BaseStream sequential() {
        return C0110h.k(this.a.sequential());
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

    public final /* synthetic */ Spliterator spliterator() {
        return T.a(this.a.spliterator());
    }

    public final /* synthetic */ Object[] toArray() {
        return this.a.toArray();
    }

    public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return this.a.toArray(intFunction);
    }

    public final /* synthetic */ BaseStream unordered() {
        return C0110h.k(this.a.unordered());
    }
}
