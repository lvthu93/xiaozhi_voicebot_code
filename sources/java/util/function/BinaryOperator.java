package java.util.function;

public interface BinaryOperator<T> extends BiFunction<T, T, T> {
    /* synthetic */ BiFunction andThen(Function function);

    /* synthetic */ Object apply(Object obj, Object obj2);
}
