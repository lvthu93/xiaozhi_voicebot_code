package j$.util.stream;

import j$.util.DesugarArrays;
import j$.util.Optional;
import j$.util.i0;
import java.util.Comparator;
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

public interface Stream<T> extends C0115i {

    /* renamed from: j$.util.stream.Stream$-CC  reason: invalid class name */
    public final /* synthetic */ class CC<T> {
        public static <T> Stream<T> empty() {
            return D0.H0(i0.e(), false);
        }

        public static <T> Stream<T> of(T t) {
            return D0.H0(new T3(t), false);
        }

        public static <T> Stream<T> of(T... tArr) {
            return DesugarArrays.stream(tArr);
        }
    }

    boolean allMatch(Predicate predicate);

    boolean anyMatch(Predicate<? super T> predicate);

    <R, A> R collect(Collector<? super T, A, R> collector);

    Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2);

    long count();

    Stream<T> distinct();

    Stream<T> filter(Predicate<? super T> predicate);

    Optional<T> findAny();

    Optional<T> findFirst();

    <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> function);

    void forEach(Consumer<? super T> consumer);

    void forEachOrdered(Consumer<? super T> consumer);

    Stream<T> limit(long j);

    <R> Stream<R> map(Function<? super T, ? extends R> function);

    I mapToDouble(ToDoubleFunction toDoubleFunction);

    C0126k0 mapToInt(ToIntFunction toIntFunction);

    C0175u0 mapToLong(ToLongFunction toLongFunction);

    Optional max(Comparator comparator);

    Optional min(Comparator comparator);

    boolean noneMatch(Predicate predicate);

    C0175u0 p(C0075a aVar);

    Stream peek(Consumer consumer);

    Optional<T> reduce(BinaryOperator<T> binaryOperator);

    Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator);

    T reduce(T t, BinaryOperator<T> binaryOperator);

    Stream<T> skip(long j);

    Stream sorted();

    Stream sorted(Comparator comparator);

    C0126k0 t(C0075a aVar);

    Object[] toArray();

    <A> A[] toArray(IntFunction<A[]> intFunction);

    I x(C0075a aVar);
}
