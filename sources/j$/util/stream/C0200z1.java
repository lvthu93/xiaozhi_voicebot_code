package j$.util.stream;

import j$.util.U;
import j$.util.function.Consumer$CC;
import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.z1  reason: case insensitive filesystem */
abstract class C0200z1 extends CountedCompleter implements C0182v2 {
    protected final U a;
    protected final D0 b;
    protected final long c;
    protected long d;
    protected long e;
    protected int f;
    protected int g;

    C0200z1(int i, U u, D0 d0) {
        this.a = u;
        this.b = d0;
        this.c = C0100f.g(u.estimateSize());
        this.d = 0;
        this.e = (long) i;
    }

    C0200z1(C0200z1 z1Var, U u, long j, long j2, int i) {
        super(z1Var);
        this.a = u;
        this.b = z1Var.b;
        this.c = z1Var.c;
        this.d = j;
        this.e = j2;
        if (j < 0 || j2 < 0 || (j + j2) - 1 >= ((long) i)) {
            throw new IllegalArgumentException(String.format("offset and length interval [%d, %d + %d) is not within array size interval [0, %d)", new Object[]{Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i)}));
        }
    }

    /* access modifiers changed from: package-private */
    public abstract C0200z1 a(U u, long j, long j2);

    public /* synthetic */ void accept(double d2) {
        D0.z();
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final void c(long j) {
        long j2 = this.e;
        if (j <= j2) {
            int i = (int) this.d;
            this.f = i;
            this.g = i + ((int) j2);
            return;
        }
        throw new IllegalStateException("size passed to Sink.begin exceeds array length");
    }

    public final void compute() {
        U trySplit;
        U u = this.a;
        C0200z1 z1Var = this;
        while (u.estimateSize() > z1Var.c && (trySplit = u.trySplit()) != null) {
            z1Var.setPendingCount(1);
            long estimateSize = trySplit.estimateSize();
            z1Var.a(trySplit, z1Var.d, estimateSize).fork();
            z1Var = z1Var.a(u, z1Var.d + estimateSize, z1Var.e - estimateSize);
        }
        z1Var.b.I0(u, z1Var);
        z1Var.propagateCompletion();
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }
}
