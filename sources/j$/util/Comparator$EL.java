package j$.util;

import java.util.Collections;
import java.util.Comparator;

/* renamed from: j$.util.Comparator$-EL  reason: invalid class name */
public final /* synthetic */ class Comparator$EL {
    public static Comparator a(Comparator comparator, Comparator comparator2) {
        if (comparator instanceof C0062f) {
            return ((C0062f) comparator).thenComparing(comparator2);
        }
        Objects.requireNonNull(comparator2);
        return new C0061e(comparator, comparator2, 0);
    }

    public static Comparator reversed(Comparator comparator) {
        return comparator instanceof C0062f ? ((C0062f) comparator).reversed() : Collections.reverseOrder(comparator);
    }
}
