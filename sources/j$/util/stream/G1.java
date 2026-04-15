package j$.util.stream;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

final class G1 extends D0 {
    public final /* synthetic */ int h;
    final /* synthetic */ Object i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ G1(C0134l3 l3Var, Object obj, int i2) {
        super(l3Var);
        this.h = i2;
        this.i = obj;
    }

    public final Y1 G0() {
        int i2 = this.h;
        Object obj = this.i;
        switch (i2) {
            case 0:
                return new X1((LongBinaryOperator) obj);
            case 1:
                return new J1((DoubleBinaryOperator) obj);
            case 2:
                return new O1((BinaryOperator) obj);
            default:
                return new U1((IntBinaryOperator) obj);
        }
    }
}
