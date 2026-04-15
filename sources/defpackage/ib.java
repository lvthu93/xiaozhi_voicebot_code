package defpackage;

import j$.util.function.IntUnaryOperator$CC;
import java.util.function.IntUnaryOperator;

/* renamed from: ib  reason: default package */
public final /* synthetic */ class ib implements IntUnaryOperator {
    public final /* synthetic */ int a;

    public /* synthetic */ ib(int i) {
        this.a = i;
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
        switch (this.a) {
            case 0:
                return i + 10;
            default:
                return 10;
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
