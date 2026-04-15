package j$.util.stream;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public final class Collectors {
    static final Set a;
    static final Set b = Collections.emptySet();

    static {
        C0125k kVar = C0125k.CONCURRENT;
        C0125k kVar2 = C0125k.UNORDERED;
        C0125k kVar3 = C0125k.IDENTITY_FINISH;
        Collections.unmodifiableSet(EnumSet.of(kVar, kVar2, kVar3));
        Collections.unmodifiableSet(EnumSet.of(kVar, kVar2));
        a = Collections.unmodifiableSet(EnumSet.of(kVar3));
        Collections.unmodifiableSet(EnumSet.of(kVar2, kVar3));
        Collections.unmodifiableSet(EnumSet.of(kVar2));
    }

    static void a(double[] dArr, double d) {
        double d2 = d - dArr[1];
        double d3 = dArr[0];
        double d4 = d3 + d2;
        dArr[1] = (d4 - d3) - d2;
        dArr[0] = d4;
    }

    public static Collector<CharSequence, ?, String> joining(CharSequence charSequence) {
        return new C0145o(new C0140n(charSequence, "", ""), new R0(13), new R0(14), new R0(15), b);
    }

    public static <T> Collector<T, ?, List<T>> toList() {
        return new C0145o(new R0(19), new R0(20), new C0080b(2), a);
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> function, Function<? super T, ? extends U> function2, BinaryOperator<U> binaryOperator) {
        return new C0145o(new R0(18), new C0140n(function, function2, binaryOperator), new C0075a(binaryOperator, 2), a);
    }

    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return new C0145o(new R0(19), new R0(20), new C0080b(3), new C0080b(4), b);
    }
}
