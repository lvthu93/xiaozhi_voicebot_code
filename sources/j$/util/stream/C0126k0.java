package j$.util.stream;

import j$.util.C0068l;
import j$.util.C0071o;
import j$.util.C0072p;
import j$.util.C0207y;
import j$.util.K;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.k0  reason: case insensitive filesystem */
public interface C0126k0 extends C0115i {
    C0126k0 a();

    I asDoubleStream();

    C0175u0 asLongStream();

    C0071o average();

    Stream boxed();

    Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer);

    long count();

    I d();

    C0126k0 distinct();

    C0175u0 f();

    C0072p findAny();

    C0072p findFirst();

    void forEach(IntConsumer intConsumer);

    void forEachOrdered(IntConsumer intConsumer);

    boolean i();

    C0207y iterator();

    boolean l();

    C0126k0 limit(long j);

    C0126k0 map(IntUnaryOperator intUnaryOperator);

    Stream mapToObj(IntFunction intFunction);

    C0072p max();

    C0072p min();

    C0126k0 parallel();

    C0126k0 peek(IntConsumer intConsumer);

    C0126k0 q(T0 t0);

    int reduce(int i, IntBinaryOperator intBinaryOperator);

    C0072p reduce(IntBinaryOperator intBinaryOperator);

    boolean s();

    C0126k0 sequential();

    C0126k0 skip(long j);

    C0126k0 sorted();

    K spliterator();

    int sum();

    C0068l summaryStatistics();

    int[] toArray();
}
