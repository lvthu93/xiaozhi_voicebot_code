package j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

final class I1 extends D0 {
    public final /* synthetic */ int h;
    final /* synthetic */ Object i;
    final /* synthetic */ Object j;
    final /* synthetic */ Object k;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ I1(C0134l3 l3Var, Object obj, Object obj2, Object obj3, int i2) {
        super(l3Var);
        this.h = i2;
        this.i = obj;
        this.k = obj2;
        this.j = obj3;
    }

    public final Y1 G0() {
        int i2 = this.h;
        Object obj = this.i;
        Object obj2 = this.k;
        Object obj3 = this.j;
        switch (i2) {
            case 0:
                return new F1((Supplier) obj3, (ObjLongConsumer) obj2, (BinaryOperator) obj);
            case 1:
                return new L1((Supplier) obj3, (ObjDoubleConsumer) obj2, (BinaryOperator) obj);
            case 2:
                return new N1(obj3, (BiFunction) obj2, (BinaryOperator) obj);
            case 3:
                return new R1((Supplier) obj3, (BiConsumer) obj2, (BiConsumer) obj);
            default:
                return new V1((Supplier) obj3, (ObjIntConsumer) obj2, (BinaryOperator) obj);
        }
    }
}
