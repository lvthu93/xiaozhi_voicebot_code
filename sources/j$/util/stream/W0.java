package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import java.util.function.Consumer;
import java.util.function.IntFunction;

final class W0 extends Y0 implements J0 {
    W0(J0 j0, J0 j02) {
        super(j0, j02);
    }

    /* renamed from: c */
    public final /* synthetic */ void j(Integer[] numArr, int i) {
        D0.K(this, numArr, i);
    }

    public final /* synthetic */ void forEach(Consumer consumer) {
        D0.N(this, consumer);
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.Q(this, j, j2);
    }

    public final Object newArray(int i) {
        return new int[i];
    }

    public final Q spliterator() {
        return new C0142n1(this);
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m8spliterator() {
        return new C0142n1(this);
    }
}
