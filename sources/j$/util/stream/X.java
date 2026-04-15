package j$.util.stream;

import j$.util.U;
import j$.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountedCompleter;

final class X extends CountedCompleter {
    public static final /* synthetic */ int h = 0;
    private final D0 a;
    private U b;
    private final long c;
    private final ConcurrentHashMap d;
    private final C0182v2 e;
    private final X f;
    private M0 g;

    protected X(D0 d0, U u, C0182v2 v2Var) {
        super((CountedCompleter) null);
        this.a = d0;
        this.b = u;
        this.c = C0100f.g(u.estimateSize());
        this.d = new ConcurrentHashMap(Math.max(16, C0100f.b() << 1), 0.75f, 1);
        this.e = v2Var;
        this.f = null;
    }

    X(X x, U u, X x2) {
        super(x);
        this.a = x.a;
        this.b = u;
        this.c = x.c;
        this.d = x.d;
        this.e = x.e;
        this.f = x2;
    }

    public final void compute() {
        U trySplit;
        U u = this.b;
        long j = this.c;
        boolean z = false;
        X x = this;
        while (u.estimateSize() > j && (trySplit = u.trySplit()) != null) {
            X x2 = new X(x, trySplit, x.f);
            X x3 = new X(x, u, x2);
            x.addToPendingCount(1);
            x3.addToPendingCount(1);
            x.d.put(x2, x3);
            if (x.f != null) {
                x2.addToPendingCount(1);
                if (x.d.replace(x.f, x, x2)) {
                    x.addToPendingCount(-1);
                } else {
                    x2.addToPendingCount(-1);
                }
            }
            if (z) {
                u = trySplit;
                x = x2;
                x2 = x3;
            } else {
                x = x3;
            }
            z = !z;
            x2.fork();
        }
        if (x.getPendingCount() > 0) {
            C0080b bVar = new C0080b(17);
            D0 d0 = x.a;
            H0 D0 = d0.D0(d0.k0(u), bVar);
            x.a.I0(u, D0);
            x.g = D0.build();
            x.b = null;
        }
        x.tryComplete();
    }

    public final void onCompletion(CountedCompleter countedCompleter) {
        M0 m0 = this.g;
        if (m0 != null) {
            m0.forEach(this.e);
            this.g = null;
        } else {
            U u = this.b;
            if (u != null) {
                this.a.I0(u, this.e);
                this.b = null;
            }
        }
        X x = (X) this.d.remove(this);
        if (x != null) {
            x.tryComplete();
        }
    }
}
