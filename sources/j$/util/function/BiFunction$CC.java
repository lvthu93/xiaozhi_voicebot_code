package j$.util.function;

import j$.util.Objects;
import j$.util.concurrent.u;
import java.util.function.BiFunction;
import java.util.function.Function;

/* renamed from: j$.util.function.BiFunction$-CC  reason: invalid class name */
public final /* synthetic */ class BiFunction$CC<T, U, R> {
    public static BiFunction $default$andThen(BiFunction biFunction, Function function) {
        Objects.requireNonNull(function);
        return new u(biFunction, function);
    }
}
