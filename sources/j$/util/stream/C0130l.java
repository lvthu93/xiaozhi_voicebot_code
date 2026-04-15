package j$.util.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* renamed from: j$.util.stream.l  reason: case insensitive filesystem */
public final /* synthetic */ class C0130l implements Collector {
    public final /* synthetic */ Collector a;

    private /* synthetic */ C0130l(Collector collector) {
        this.a = collector;
    }

    public static /* synthetic */ Collector a(Collector collector) {
        if (collector == null) {
            return null;
        }
        return collector instanceof C0135m ? ((C0135m) collector).a : new C0130l(collector);
    }

    public final /* synthetic */ BiConsumer accumulator() {
        return this.a.accumulator();
    }

    public final /* synthetic */ Set characteristics() {
        return D0.q0(this.a.characteristics());
    }

    public final /* synthetic */ BinaryOperator combiner() {
        return this.a.combiner();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        if (obj instanceof C0130l) {
            obj = ((C0130l) obj).a;
        }
        return this.a.equals(obj);
    }

    public final /* synthetic */ Function finisher() {
        return this.a.finisher();
    }

    public final /* synthetic */ int hashCode() {
        return this.a.hashCode();
    }

    public final /* synthetic */ Supplier supplier() {
        return this.a.supplier();
    }
}
