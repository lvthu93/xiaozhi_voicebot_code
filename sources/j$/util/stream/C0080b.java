package j$.util.stream;

import j$.desugar.sun.nio.fs.c;
import j$.lang.a;
import j$.util.function.Function$CC;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.stream.b  reason: case insensitive filesystem */
public final /* synthetic */ class C0080b implements IntFunction, Function, BinaryOperator, ObjDoubleConsumer, BiConsumer, Supplier, ToDoubleFunction, ToIntFunction, ObjIntConsumer, ToLongFunction, ObjLongConsumer, LongFunction {
    public final /* synthetic */ int a;

    public /* synthetic */ C0080b(int i) {
        this.a = i;
    }

    public final /* synthetic */ BiConsumer a(BiConsumer biConsumer) {
        switch (this.a) {
            case 7:
                return a.a(this, biConsumer);
            case 10:
                return a.a(this, biConsumer);
            case 21:
                return a.a(this, biConsumer);
            default:
                return a.a(this, biConsumer);
        }
    }

    public final void accept(Object obj, double d) {
        switch (this.a) {
            case 6:
                double[] dArr = (double[]) obj;
                Collectors.a(dArr, d);
                dArr[2] = dArr[2] + d;
                return;
            default:
                double[] dArr2 = (double[]) obj;
                dArr2[2] = dArr2[2] + 1.0d;
                Collectors.a(dArr2, d);
                dArr2[3] = dArr2[3] + d;
                return;
        }
    }

    public final void accept(Object obj, int i) {
        long[] jArr = (long[]) obj;
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + ((long) i);
    }

    public final void accept(Object obj, long j) {
        long[] jArr = (long[]) obj;
        jArr[0] = jArr[0] + 1;
        jArr[1] = jArr[1] + j;
    }

    public final void accept(Object obj, Object obj2) {
        switch (this.a) {
            case 7:
                double[] dArr = (double[]) obj;
                double[] dArr2 = (double[]) obj2;
                Collectors.a(dArr, dArr2[0]);
                Collectors.a(dArr, dArr2[1]);
                dArr[2] = dArr[2] + dArr2[2];
                return;
            case 10:
                double[] dArr3 = (double[]) obj;
                double[] dArr4 = (double[]) obj2;
                Collectors.a(dArr3, dArr4[0]);
                Collectors.a(dArr3, dArr4[1]);
                dArr3[2] = dArr3[2] + dArr4[2];
                dArr3[3] = dArr3[3] + dArr4[3];
                return;
            case 21:
                long[] jArr = (long[]) obj;
                long[] jArr2 = (long[]) obj2;
                jArr[0] = jArr[0] + jArr2[0];
                jArr[1] = jArr[1] + jArr2[1];
                return;
            default:
                long[] jArr3 = (long[]) obj;
                long[] jArr4 = (long[]) obj2;
                jArr3[0] = jArr3[0] + jArr4[0];
                jArr3[1] = jArr3[1] + jArr4[1];
                return;
        }
    }

    public final Object apply(int i) {
        switch (this.a) {
            case 0:
                return new Object[i];
            case 5:
                return new Double[i];
            case 17:
                int i2 = X.h;
                return new Object[i];
            case 22:
                return new Integer[i];
            case 24:
                return new Long[i];
            default:
                return new Object[i];
        }
    }

    public final Object apply(long j) {
        return D0.h0(j);
    }

    public final Object apply(Object obj) {
        switch (this.a) {
            case 1:
                Set set = Collectors.a;
                return obj;
            default:
                Set set2 = Collectors.a;
                return c.a(((List) obj).toArray());
        }
    }

    public final Object apply(Object obj, Object obj2) {
        switch (this.a) {
            case 2:
                List list = (List) obj;
                Set set = Collectors.a;
                list.addAll((List) obj2);
                return list;
            default:
                List list2 = (List) obj;
                Set set2 = Collectors.a;
                list2.addAll((List) obj2);
                return list2;
        }
    }

    public final double applyAsDouble(Object obj) {
        return ((Double) obj).doubleValue();
    }

    public final int applyAsInt(Object obj) {
        return ((Integer) obj).intValue();
    }

    public final long applyAsLong(Object obj) {
        return ((Long) obj).longValue();
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.a) {
            case 1:
                return Function$CC.$default$compose(this, function);
            default:
                return Function$CC.$default$compose(this, function);
        }
    }

    public final Object get() {
        switch (this.a) {
            case 8:
                return new double[4];
            case 12:
                return new double[3];
            case 13:
                return new K();
            case 14:
                return new M();
            case 15:
                return new N();
            case 16:
                return new O();
            case 19:
                return new long[2];
            default:
                return new long[2];
        }
    }
}
