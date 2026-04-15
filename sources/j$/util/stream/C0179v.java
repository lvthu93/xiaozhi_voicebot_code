package j$.util.stream;

import j$.time.c;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;

/* renamed from: j$.util.stream.v  reason: case insensitive filesystem */
final class C0179v extends C0148o2 {
    public final /* synthetic */ int b;
    final /* synthetic */ C0085c c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0179v(C0085c cVar, C0182v2 v2Var, int i) {
        super(v2Var);
        this.b = i;
        this.c = cVar;
    }

    public final void accept(double d) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        C0085c cVar = this.c;
        switch (i) {
            case 0:
                v2Var.accept(((DoubleFunction) ((C0184w) cVar).t).apply(d));
                return;
            case 1:
                c.b(((C0189x) cVar).t);
                throw null;
            case 2:
                c.b(((C0194y) cVar).t);
                throw null;
            case 3:
                c.b(((C0198z) cVar).t);
                throw null;
            case 4:
                c.b(((C0189x) cVar).t);
                throw null;
            default:
                ((DoubleConsumer) ((C0189x) cVar).t).accept(d);
                v2Var.accept(d);
                return;
        }
    }

    public final void c(long j) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        switch (i) {
            case 4:
                v2Var.c(-1);
                return;
            default:
                v2Var.c(j);
                return;
        }
    }
}
