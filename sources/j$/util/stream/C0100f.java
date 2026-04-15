package j$.util.stream;

import j$.util.U;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;

/* renamed from: j$.util.stream.f  reason: case insensitive filesystem */
abstract class C0100f extends CountedCompleter {
    private static final int g = (ForkJoinPool.getCommonPoolParallelism() << 2);
    protected final D0 a;
    protected U b;
    protected long c;
    protected C0100f d;
    protected C0100f e;
    private Object f;

    protected C0100f(D0 d0, U u) {
        super((CountedCompleter) null);
        this.a = d0;
        this.b = u;
        this.c = 0;
    }

    protected C0100f(C0100f fVar, U u) {
        super(fVar);
        this.b = u;
        this.a = fVar.a;
        this.c = fVar.c;
    }

    public static int b() {
        return g;
    }

    public static long g(long j) {
        long j2 = j / ((long) g);
        if (j2 > 0) {
            return j2;
        }
        return 1;
    }

    /* access modifiers changed from: protected */
    public abstract Object a();

    /* access modifiers changed from: protected */
    public Object c() {
        return this.f;
    }

    public void compute() {
        U trySplit;
        U u = this.b;
        long estimateSize = u.estimateSize();
        long j = this.c;
        if (j == 0) {
            j = g(estimateSize);
            this.c = j;
        }
        boolean z = false;
        C0100f fVar = this;
        while (estimateSize > j && (trySplit = u.trySplit()) != null) {
            C0100f e2 = fVar.e(trySplit);
            fVar.d = e2;
            C0100f e3 = fVar.e(u);
            fVar.e = e3;
            fVar.setPendingCount(1);
            if (z) {
                u = trySplit;
                fVar = e2;
                e2 = e3;
            } else {
                fVar = e3;
            }
            z = !z;
            e2.fork();
            estimateSize = u.estimateSize();
        }
        fVar.f(fVar.a());
        fVar.tryComplete();
    }

    /* access modifiers changed from: protected */
    public final C0100f d() {
        return (C0100f) getCompleter();
    }

    /* access modifiers changed from: protected */
    public abstract C0100f e(U u);

    /* access modifiers changed from: protected */
    public void f(Object obj) {
        this.f = obj;
    }

    public Object getRawResult() {
        return this.f;
    }

    public void onCompletion(CountedCompleter countedCompleter) {
        this.b = null;
        this.e = null;
        this.d = null;
    }

    /* access modifiers changed from: protected */
    public final void setRawResult(Object obj) {
        if (obj != null) {
            throw new IllegalStateException();
        }
    }
}
