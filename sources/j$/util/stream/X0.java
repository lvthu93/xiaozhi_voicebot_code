package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.IntFunction;

final class X0 extends Y0 implements K0 {
    X0(K0 k0, K0 k02) {
        super(k0, k02);
    }

    /* renamed from: c */
    public final /* synthetic */ void j(Long[] lArr, int i) {
        D0.L(this, lArr, i);
    }

    public final /* synthetic */ void forEach(Consumer consumer) {
        D0.O(this, consumer);
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.R(this, j, j2);
    }

    public final Object newArray(int i) {
        return new long[i];
    }

    public final Q spliterator() {
        return new C0147o1(this);
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m9spliterator() {
        return new C0147o1(this);
    }
}
