package j$.util.stream;

import j$.lang.a;
import j$.util.C0068l;
import j$.util.C0069m;
import j$.util.C0072p;
import j$.util.C0073q;
import j$.util.Optional;
import j$.util.function.Consumer$CC;
import j$.util.function.Predicate$CC;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongFunction;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;

public final /* synthetic */ class L implements Predicate, IntBinaryOperator, ObjIntConsumer, BiConsumer, IntFunction, LongBinaryOperator, LongFunction, ObjLongConsumer, Consumer {
    public final /* synthetic */ int a;

    public /* synthetic */ L(int i) {
        this.a = i;
    }

    public final /* synthetic */ BiConsumer a(BiConsumer biConsumer) {
        switch (this.a) {
            case 6:
                return a.a(this, biConsumer);
            default:
                return a.a(this, biConsumer);
        }
    }

    public final void accept(Object obj) {
    }

    public final void accept(Object obj, int i) {
        ((C0068l) obj).accept(i);
    }

    public final void accept(Object obj, long j) {
        ((C0069m) obj).accept(j);
    }

    public final void accept(Object obj, Object obj2) {
        switch (this.a) {
            case 6:
                ((C0068l) obj).a((C0068l) obj2);
                return;
            default:
                ((C0069m) obj).a((C0069m) obj2);
                return;
        }
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$and(this, predicate);
            case 1:
                return Predicate$CC.$default$and(this, predicate);
            default:
                return Predicate$CC.$default$and(this, predicate);
        }
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final Object apply(int i) {
        return Integer.valueOf(i);
    }

    public final Object apply(long j) {
        return Long.valueOf(j);
    }

    public final int b(int i, int i2) {
        switch (this.a) {
            case 3:
                return Math.min(i, i2);
            case 4:
                return i + i2;
            default:
                return Math.max(i, i2);
        }
    }

    public final long f(long j, long j2) {
        switch (this.a) {
            case 9:
                return Math.max(j, j2);
            case 11:
                return j + j2;
            default:
                return Math.min(j, j2);
        }
    }

    public final /* synthetic */ Predicate negate() {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$negate(this);
            case 1:
                return Predicate$CC.$default$negate(this);
            default:
                return Predicate$CC.$default$negate(this);
        }
    }

    public final /* synthetic */ Predicate or(Predicate predicate) {
        switch (this.a) {
            case 0:
                return Predicate$CC.$default$or(this, predicate);
            case 1:
                return Predicate$CC.$default$or(this, predicate);
            default:
                return Predicate$CC.$default$or(this, predicate);
        }
    }

    public final boolean test(Object obj) {
        switch (this.a) {
            case 0:
                return ((C0072p) obj).c();
            case 1:
                return ((C0073q) obj).c();
            default:
                return ((Optional) obj).isPresent();
        }
    }
}
