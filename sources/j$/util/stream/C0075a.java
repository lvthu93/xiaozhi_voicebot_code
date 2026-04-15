package j$.util.stream;

import j$.util.C0065i;
import j$.util.Map;
import j$.util.U;
import j$.util.function.Consumer$CC;
import j$.util.function.Function$CC;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.LongFunction;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/* renamed from: j$.util.stream.a  reason: case insensitive filesystem */
public final /* synthetic */ class C0075a implements Supplier, BinaryOperator, Consumer, DoubleFunction, Function, LongFunction {
    public final /* synthetic */ int a;
    public Object b;

    public /* synthetic */ C0075a(Object obj, int i) {
        this.a = i;
        this.b = obj;
    }

    public final boolean a() {
        switch (this.a) {
            case 4:
                C0183v3 v3Var = (C0183v3) this.b;
                return v3Var.d.tryAdvance(v3Var.e);
            case 5:
                C0193x3 x3Var = (C0193x3) this.b;
                return x3Var.d.tryAdvance(x3Var.e);
            case 6:
                z3 z3Var = (z3) this.b;
                return z3Var.d.tryAdvance(z3Var.e);
            default:
                R3 r3 = (R3) this.b;
                return r3.d.tryAdvance(r3.e);
        }
    }

    public final void accept(Object obj) {
        switch (this.a) {
            case 3:
                ((C0182v2) this.b).accept(obj);
                return;
            default:
                ((List) this.b).add(obj);
                return;
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 3:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }

    public final Object apply(double d) {
        Object apply = ((DoubleFunction) this.b).apply(d);
        if (apply == null) {
            return null;
        }
        if (apply instanceof I) {
            return H.k((I) apply);
        }
        if (apply instanceof DoubleStream) {
            return G.k((DoubleStream) apply);
        }
        C0065i.a(apply.getClass(), "java.util.stream.DoubleStream");
        throw null;
    }

    public final Object apply(long j) {
        Object apply = ((LongFunction) this.b).apply(j);
        if (apply == null) {
            return null;
        }
        if (apply instanceof C0175u0) {
            return C0170t0.k((C0175u0) apply);
        }
        if (apply instanceof LongStream) {
            return C0165s0.k((LongStream) apply);
        }
        C0065i.a(apply.getClass(), "java.util.stream.LongStream");
        throw null;
    }

    public final Object apply(Object obj) {
        Object apply = ((Function) this.b).apply(obj);
        if (apply == null) {
            return null;
        }
        if (apply instanceof Stream) {
            return C0114h3.k((Stream) apply);
        }
        if (apply instanceof Stream) {
            return C0109g3.k((Stream) apply);
        }
        if (apply instanceof C0126k0) {
            return C0121j0.k((C0126k0) apply);
        }
        if (apply instanceof IntStream) {
            return C0116i0.k((IntStream) apply);
        }
        if (apply instanceof I) {
            return H.k((I) apply);
        }
        if (apply instanceof DoubleStream) {
            return G.k((DoubleStream) apply);
        }
        if (apply instanceof C0175u0) {
            return C0170t0.k((C0175u0) apply);
        }
        if (apply instanceof LongStream) {
            return C0165s0.k((LongStream) apply);
        }
        C0065i.a(apply.getClass(), "java.util.stream.*Stream");
        throw null;
    }

    public final Object apply(Object obj, Object obj2) {
        BinaryOperator binaryOperator = (BinaryOperator) this.b;
        Map map = (Map) obj;
        Set set = Collectors.a;
        for (Map.Entry entry : ((Map) obj2).entrySet()) {
            Map.EL.a(map, entry.getKey(), entry.getValue(), binaryOperator);
        }
        return map;
    }

    public final /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }

    public final Object get() {
        switch (this.a) {
            case 0:
                return (U) this.b;
            default:
                return ((C0085c) this.b).S0();
        }
    }
}
