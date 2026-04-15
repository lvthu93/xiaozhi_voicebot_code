package j$.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.g  reason: case insensitive filesystem */
enum C0063g implements Comparator, C0062f {
    ;

    private C0063g() {
    }

    public final int compare(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo((Comparable) obj2);
    }

    public final Comparator reversed() {
        return Collections.reverseOrder();
    }

    public final Comparator thenComparing(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return new C0061e(this, comparator, 0);
    }

    public final Comparator thenComparing(Function function) {
        return Comparator$EL.a(this, Comparator$CC.comparing(function));
    }

    public final Comparator thenComparing(Function function, Comparator comparator) {
        return Comparator$EL.a(this, Comparator$CC.comparing(function, comparator));
    }

    public final Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return Comparator$EL.a(this, new C0060d(toDoubleFunction, 0));
    }

    public final Comparator thenComparingInt(ToIntFunction toIntFunction) {
        return Comparator$EL.a(this, Comparator$CC.comparingInt(toIntFunction));
    }

    public final Comparator thenComparingLong(ToLongFunction toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return Comparator$EL.a(this, new C0060d(toLongFunction, 2));
    }
}
