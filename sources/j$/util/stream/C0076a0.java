package j$.util.stream;

import j$.time.c;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/* renamed from: j$.util.stream.a0  reason: case insensitive filesystem */
final class C0076a0 extends C0153p2 {
    public final /* synthetic */ int b;
    final /* synthetic */ C0085c c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0076a0(C0085c cVar, C0182v2 v2Var, int i) {
        super(v2Var);
        this.b = i;
        this.c = cVar;
    }

    public final void accept(int i) {
        int i2 = this.b;
        C0182v2 v2Var = this.a;
        C0085c cVar = this.c;
        switch (i2) {
            case 0:
                v2Var.accept(((IntFunction) ((C0184w) cVar).t).apply(i));
                return;
            case 1:
                ((IntConsumer) ((C0194y) cVar).t).accept(i);
                v2Var.accept(i);
                return;
            case 2:
                v2Var.accept((long) i);
                return;
            case 3:
                v2Var.accept((double) i);
                return;
            case 4:
                v2Var.accept(((IntUnaryOperator) ((C0194y) cVar).t).applyAsInt(i));
                return;
            case 5:
                c.b(((C0198z) cVar).t);
                throw null;
            case 6:
                c.b(((C0189x) cVar).t);
                throw null;
            default:
                c.b(((C0194y) cVar).t);
                throw null;
        }
    }

    public final void c(long j) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        switch (i) {
            case 7:
                v2Var.c(-1);
                return;
            default:
                v2Var.c(j);
                return;
        }
    }
}
