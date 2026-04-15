package j$.util.stream;

import j$.util.C0067k;
import j$.util.C0071o;
import j$.util.C0203u;
import j$.util.H;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

public interface I extends C0115i {
    I a();

    C0071o average();

    I b(C0075a aVar);

    Stream boxed();

    I c();

    Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer);

    long count();

    I distinct();

    C0071o findAny();

    C0071o findFirst();

    void forEach(DoubleConsumer doubleConsumer);

    void forEachOrdered(DoubleConsumer doubleConsumer);

    boolean h();

    C0203u iterator();

    I limit(long j);

    boolean m();

    Stream mapToObj(DoubleFunction doubleFunction);

    C0071o max();

    C0071o min();

    C0175u0 n();

    I parallel();

    I peek(DoubleConsumer doubleConsumer);

    double reduce(double d, DoubleBinaryOperator doubleBinaryOperator);

    C0071o reduce(DoubleBinaryOperator doubleBinaryOperator);

    I sequential();

    I skip(long j);

    I sorted();

    H spliterator();

    double sum();

    C0067k summaryStatistics();

    double[] toArray();

    C0126k0 u();

    boolean w();
}
