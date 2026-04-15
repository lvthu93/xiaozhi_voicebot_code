package j$.util.stream;

import j$.lang.a;
import j$.util.C0067k;
import j$.util.C0068l;
import j$.util.C0069m;
import j$.util.C0071o;
import j$.util.function.Consumer$CC;
import j$.util.function.Function$CC;
import j$.util.function.Predicate$CC;
import j$.util.j0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final /* synthetic */ class R0 implements BinaryOperator, LongFunction, IntFunction, Consumer, Supplier, BiConsumer, Function, DoubleBinaryOperator, DoubleFunction, ObjDoubleConsumer, Predicate {
    public final /* synthetic */ int a;

    public /* synthetic */ R0(int i) {
        this.a = i;
    }

    public final /* synthetic */ BiConsumer a(BiConsumer biConsumer) {
        switch (this.a) {
            case 13:
                return a.a(this, biConsumer);
            case 20:
                return a.a(this, biConsumer);
            case 22:
                return a.a(this, biConsumer);
            case 23:
                return a.a(this, biConsumer);
            default:
                return a.a(this, biConsumer);
        }
    }

    public final void accept(Object obj) {
    }

    public final void accept(Object obj, double d) {
        ((C0067k) obj).accept(d);
    }

    public final void accept(Object obj, Object obj2) {
        switch (this.a) {
            case 13:
                ((j0) obj).a((CharSequence) obj2);
                return;
            case 20:
                ((List) obj).add(obj2);
                return;
            case 22:
                ((LinkedHashSet) obj).add(obj2);
                return;
            case 23:
                ((LinkedHashSet) obj).addAll((LinkedHashSet) obj2);
                return;
            default:
                ((C0067k) obj).a((C0067k) obj2);
                return;
        }
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        return Predicate$CC.$default$and(this, predicate);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        switch (this.a) {
            case 10:
                return Consumer$CC.$default$andThen(this, consumer);
            default:
                return Consumer$CC.$default$andThen(this, consumer);
        }
    }

    public final Object apply(double d) {
        return Double.valueOf(d);
    }

    public final Object apply(int i) {
        switch (this.a) {
            case 6:
                return new Object[i];
            case 7:
                return new Integer[i];
            case 8:
                return new Long[i];
            default:
                return new Double[i];
        }
    }

    public final Object apply(long j) {
        switch (this.a) {
            case 1:
                return D0.t0(j);
            default:
                return D0.v0(j);
        }
    }

    public final Object apply(Object obj) {
        return ((j0) obj).toString();
    }

    public final Object apply(Object obj, Object obj2) {
        switch (this.a) {
            case 0:
                return new V0((I0) obj, (I0) obj2);
            case 2:
                return new W0((J0) obj, (J0) obj2);
            case 4:
                return new X0((K0) obj, (K0) obj2);
            case 5:
                return new Z0((M0) obj, (M0) obj2);
            default:
                j0 j0Var = (j0) obj;
                j0Var.d((j0) obj2);
                return j0Var;
        }
    }

    public final double b(double d, double d2) {
        switch (this.a) {
            case 24:
                return Math.min(d, d2);
            default:
                return Math.max(d, d2);
        }
    }

    public final /* synthetic */ Function compose(Function function) {
        return Function$CC.$default$compose(this, function);
    }

    public final Object get() {
        switch (this.a) {
            case 12:
                return new C0067k();
            case 16:
                return new C0068l();
            case 17:
                return new C0069m();
            case 18:
                return new HashMap();
            case 19:
                return new ArrayList();
            default:
                return new LinkedHashSet();
        }
    }

    public final /* synthetic */ Predicate negate() {
        return Predicate$CC.$default$negate(this);
    }

    public final /* synthetic */ Predicate or(Predicate predicate) {
        return Predicate$CC.$default$or(this, predicate);
    }

    public final boolean test(Object obj) {
        return ((C0071o) obj).c();
    }
}
