package j$.util.stream;

import j$.time.c;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;

/* renamed from: j$.util.stream.m0  reason: case insensitive filesystem */
final class C0136m0 extends C0158q2 {
    public final /* synthetic */ int b;
    final /* synthetic */ C0085c c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0136m0(C0085c cVar, C0182v2 v2Var, int i) {
        super(v2Var);
        this.b = i;
        this.c = cVar;
    }

    public final void accept(long j) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        C0085c cVar = this.c;
        switch (i) {
            case 0:
                v2Var.accept(((LongFunction) ((C0184w) cVar).t).apply(j));
                return;
            case 1:
                v2Var.accept((double) j);
                return;
            case 2:
                c.b(((C0198z) cVar).t);
                throw null;
            case 3:
                c.b(((C0194y) cVar).t);
                throw null;
            case 4:
                c.b(((C0189x) cVar).t);
                throw null;
            case 5:
                c.b(((C0198z) cVar).t);
                throw null;
            default:
                ((LongConsumer) ((C0198z) cVar).t).accept(j);
                v2Var.accept(j);
                return;
        }
    }

    public final void c(long j) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        switch (i) {
            case 5:
                v2Var.c(-1);
                return;
            default:
                v2Var.c(j);
                return;
        }
    }
}
