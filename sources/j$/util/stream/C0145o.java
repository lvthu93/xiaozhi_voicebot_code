package j$.util.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.o  reason: case insensitive filesystem */
final class C0145o implements Collector {
    private final Supplier a;
    private final BiConsumer b;
    private final BinaryOperator c;
    private final Function d;
    private final Set e;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    C0145o(R0 r0, BiConsumer biConsumer, BinaryOperator binaryOperator, Set set) {
        this(r0, biConsumer, binaryOperator, new C0080b(1), set);
        Set set2 = Collectors.a;
    }

    C0145o(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Function function, Set set) {
        this.a = supplier;
        this.b = biConsumer;
        this.c = binaryOperator;
        this.d = function;
        this.e = set;
    }

    public final BiConsumer accumulator() {
        return this.b;
    }

    public final Set characteristics() {
        return this.e;
    }

    public final BinaryOperator combiner() {
        return this.c;
    }

    public final Function finisher() {
        return this.d;
    }

    public final Supplier supplier() {
        return this.a;
    }
}
