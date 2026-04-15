package j$.util.stream;

import j$.util.C;
import j$.util.C0069m;
import j$.util.C0071o;
import j$.util.C0073q;
import j$.util.N;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.u0  reason: case insensitive filesystem */
public interface C0175u0 extends C0115i {
    C0175u0 a();

    I asDoubleStream();

    C0071o average();

    C0175u0 b(C0075a aVar);

    Stream boxed();

    C0175u0 c();

    Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer);

    long count();

    C0175u0 distinct();

    I e();

    C0073q findAny();

    C0073q findFirst();

    void forEach(LongConsumer longConsumer);

    void forEachOrdered(LongConsumer longConsumer);

    boolean g();

    C iterator();

    boolean j();

    C0175u0 limit(long j);

    Stream mapToObj(LongFunction longFunction);

    C0073q max();

    C0073q min();

    C0175u0 parallel();

    C0175u0 peek(LongConsumer longConsumer);

    boolean r();

    long reduce(long j, LongBinaryOperator longBinaryOperator);

    C0073q reduce(LongBinaryOperator longBinaryOperator);

    C0175u0 sequential();

    C0175u0 skip(long j);

    C0175u0 sorted();

    N spliterator();

    long sum();

    C0069m summaryStatistics();

    long[] toArray();

    C0126k0 v();
}
