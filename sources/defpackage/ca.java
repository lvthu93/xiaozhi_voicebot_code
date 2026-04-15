package defpackage;

import j$.util.Optional;
import j$.util.function.BiFunction$CC;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/* renamed from: ca  reason: default package */
public final /* synthetic */ class ca implements BinaryOperator {
    public final /* synthetic */ int a;

    public /* synthetic */ ca(int i) {
        this.a = i;
    }

    public final /* synthetic */ BiFunction andThen(Function function) {
        switch (this.a) {
            case 0:
                return BiFunction$CC.$default$andThen(this, function);
            case 1:
                return BiFunction$CC.$default$andThen(this, function);
            default:
                return BiFunction$CC.$default$andThen(this, function);
        }
    }

    public final Object apply(Object obj, Object obj2) {
        switch (this.a) {
            case 1:
                return ((Optional) obj).or(new rf(1, (Optional) obj2));
            default:
                String str = (String) obj;
                return (String) obj2;
        }
    }
}
