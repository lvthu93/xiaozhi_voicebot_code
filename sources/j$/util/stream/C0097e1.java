package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import j$.util.i0;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.e1  reason: case insensitive filesystem */
final class C0097e1 extends C0112h1 implements J0 {
    C0097e1() {
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final Object b() {
        return D0.e;
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

    public final Q spliterator() {
        return i0.c();
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m14spliterator() {
        return i0.c();
    }
}
