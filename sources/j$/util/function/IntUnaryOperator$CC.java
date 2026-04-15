package j$.util.function;

import j$.util.Objects;
import java.util.function.IntUnaryOperator;

/* renamed from: j$.util.function.IntUnaryOperator$-CC  reason: invalid class name */
public final /* synthetic */ class IntUnaryOperator$CC {
    public static IntUnaryOperator $default$andThen(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        Objects.requireNonNull(intUnaryOperator2);
        return new e(intUnaryOperator, intUnaryOperator2, 1);
    }

    public static IntUnaryOperator $default$compose(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2) {
        Objects.requireNonNull(intUnaryOperator2);
        return new e(intUnaryOperator, intUnaryOperator2, 0);
    }
}
