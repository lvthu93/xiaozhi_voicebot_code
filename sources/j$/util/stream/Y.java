package j$.util.stream;

import j$.util.U;
import java.util.concurrent.CountedCompleter;

final class Y extends CountedCompleter {
    private U a;
    private final C0182v2 b;
    private final D0 c;
    private long d;

    Y(D0 d0, U u, C0182v2 v2Var) {
        super((CountedCompleter) null);
        this.b = v2Var;
        this.c = d0;
        this.a = u;
        this.d = 0;
    }

    Y(Y y, U u) {
        super(y);
        this.a = u;
        this.b = y.b;
        this.d = y.d;
        this.c = y.c;
    }

    public final void compute() {
        U trySplit;
        U u = this.a;
        long estimateSize = u.estimateSize();
        long j = this.d;
        if (j == 0) {
            j = C0100f.g(estimateSize);
            this.d = j;
        }
        boolean p = C0129k3.SHORT_CIRCUIT.p(this.c.s0());
        C0182v2 v2Var = this.b;
        boolean z = false;
        Y y = this;
        while (true) {
            if (p && v2Var.e()) {
                break;
            } else if (estimateSize <= j || (trySplit = u.trySplit()) == null) {
                y.c.f0(u, v2Var);
            } else {
                Y y2 = new Y(y, trySplit);
                y.addToPendingCount(1);
                if (z) {
                    u = trySplit;
                } else {
                    Y y3 = y;
                    y = y2;
                    y2 = y3;
                }
                z = !z;
                y.fork();
                y = y2;
                estimateSize = u.estimateSize();
            }
        }
        y.c.f0(u, v2Var);
        y.a = null;
        y.propagateCompletion();
    }
}
