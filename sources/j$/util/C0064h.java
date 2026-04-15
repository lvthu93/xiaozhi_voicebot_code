package j$.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.h  reason: case insensitive filesystem */
final class C0064h implements Comparator, Serializable, C0062f {
    private static final long serialVersionUID = -7569533591570686392L;
    private final boolean a;
    private final Comparator b;

    C0064h(boolean z, Comparator comparator) {
        this.a = z;
        this.b = comparator;
    }

    public final int compare(Object obj, Object obj2) {
        boolean z = this.a;
        if (obj == null) {
            if (obj2 == null) {
                return 0;
            }
            return z ? -1 : 1;
        } else if (obj2 == null) {
            return z ? 1 : -1;
        } else {
            Comparator comparator = this.b;
            if (comparator == null) {
                return 0;
            }
            return comparator.compare(obj, obj2);
        }
    }

    public final Comparator reversed() {
        boolean z = !this.a;
        Comparator comparator = this.b;
        return new C0064h(z, comparator == null ? null : Comparator$EL.reversed(comparator));
    }

    public final Comparator thenComparing(Comparator comparator) {
        Objects.requireNonNull(comparator);
        Comparator comparator2 = this.b;
        if (comparator2 != null) {
            comparator = Comparator$EL.a(comparator2, comparator);
        }
        return new C0064h(this.a, comparator);
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
