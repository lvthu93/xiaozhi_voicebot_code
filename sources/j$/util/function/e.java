package j$.util.function;

import java.util.function.IntUnaryOperator;

public final /* synthetic */ class e implements IntUnaryOperator {
    public final /* synthetic */ int a;
    public final /* synthetic */ IntUnaryOperator b;
    public final /* synthetic */ IntUnaryOperator c;

    public /* synthetic */ e(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2, int i) {
        this.a = i;
        this.b = intUnaryOperator;
        this.c = intUnaryOperator2;
    }

    public final /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
        switch (this.a) {
            case 0:
                return IntUnaryOperator$CC.$default$andThen(this, intUnaryOperator);
            default:
                return IntUnaryOperator$CC.$default$andThen(this, intUnaryOperator);
        }
    }

    public final int applyAsInt(int i) {
        int i2 = this.a;
        IntUnaryOperator intUnaryOperator = this.b;
        IntUnaryOperator intUnaryOperator2 = this.c;
        switch (i2) {
            case 0:
                return intUnaryOperator.applyAsInt(intUnaryOperator2.applyAsInt(i));
            default:
                return intUnaryOperator2.applyAsInt(intUnaryOperator.applyAsInt(i));
        }
    }

    public final /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
        switch (this.a) {
            case 0:
                return IntUnaryOperator$CC.$default$compose(this, intUnaryOperator);
            default:
                return IntUnaryOperator$CC.$default$compose(this, intUnaryOperator);
        }
    }
}
