package j$.util.stream;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.stream.g2  reason: case insensitive filesystem */
final class C0108g2 extends C0162r2 {
    public final /* synthetic */ int b;
    final /* synthetic */ C0085c c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0108g2(C0085c cVar, C0182v2 v2Var, int i) {
        super(v2Var);
        this.b = i;
        this.c = cVar;
    }

    public final void accept(Object obj) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        C0085c cVar = this.c;
        switch (i) {
            case 0:
                ((Consumer) ((C0184w) cVar).t).accept(obj);
                v2Var.accept(obj);
                return;
            case 1:
                if (((Predicate) ((C0184w) cVar).t).test(obj)) {
                    v2Var.accept(obj);
                    return;
                }
                return;
            case 2:
                v2Var.accept(((C0118i2) cVar).t.apply(obj));
                return;
            case 3:
                v2Var.accept(((ToIntFunction) ((C0194y) cVar).t).applyAsInt(obj));
                return;
            case 4:
                v2Var.accept(((C0080b) ((ToLongFunction) ((C0198z) cVar).t)).applyAsLong(obj));
                return;
            default:
                v2Var.accept(((C0080b) ((ToDoubleFunction) ((C0189x) cVar).t)).applyAsDouble(obj));
                return;
        }
    }

    public final void c(long j) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        switch (i) {
            case 1:
                v2Var.c(-1);
                return;
            default:
                v2Var.c(j);
                return;
        }
    }
}
