package j$.util.stream;

import j$.lang.a;
import j$.util.U;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.v1  reason: case insensitive filesystem */
final class C0181v1 extends C0200z1 implements C0167s2 {
    private final double[] h;

    C0181v1(U u, D0 d0, double[] dArr) {
        super(dArr.length, u, d0);
        this.h = dArr;
    }

    C0181v1(C0181v1 v1Var, U u, long j, long j2) {
        super(v1Var, u, j, j2, v1Var.h.length);
        this.h = v1Var.h;
    }

    /* access modifiers changed from: package-private */
    public final C0200z1 a(U u, long j, long j2) {
        return new C0181v1(this, u, j, j2);
    }

    public final void accept(double d) {
        int i = this.f;
        if (i < this.g) {
            double[] dArr = this.h;
            this.f = i + 1;
            dArr[i] = d;
            return;
        }
        throw new IndexOutOfBoundsException(Integer.toString(this.f));
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        m((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }
}
