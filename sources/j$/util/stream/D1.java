package j$.util.stream;

import java.util.concurrent.CountedCompleter;

abstract class D1 extends CountedCompleter {
    protected final M0 a;
    protected final int b;

    D1(D1 d1, M0 m0, int i) {
        super(d1);
        this.a = m0;
        this.b = i;
    }

    D1(M0 m0) {
        this.a = m0;
        this.b = 0;
    }

    /* access modifiers changed from: package-private */
    public abstract void a();

    /* access modifiers changed from: package-private */
    public abstract C1 b(int i, int i2);

    public final void compute() {
        D1 d1 = this;
        while (d1.a.n() != 0) {
            d1.setPendingCount(d1.a.n() - 1);
            int i = 0;
            int i2 = 0;
            while (i < d1.a.n() - 1) {
                C1 b2 = d1.b(i, d1.b + i2);
                i2 = (int) (((long) i2) + b2.a.count());
                b2.fork();
                i++;
            }
            d1 = d1.b(i, d1.b + i2);
        }
        d1.a();
        d1.propagateCompletion();
    }
}
