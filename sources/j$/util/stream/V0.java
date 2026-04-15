package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.IntFunction;

final class V0 extends Y0 implements I0 {
    V0(I0 i0, I0 i02) {
        super(i0, i02);
    }

    /* renamed from: c */
    public final /* synthetic */ void j(Double[] dArr, int i) {
        D0.J(this, dArr, i);
    }

    public final /* synthetic */ void forEach(Consumer consumer) {
        D0.M(this, consumer);
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.P(this, j, j2);
    }

    public final Object newArray(int i) {
        return new double[i];
    }

    public final Q spliterator() {
        return new C0137m1(this);
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m7spliterator() {
        return new C0137m1(this);
    }
}
