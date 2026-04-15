package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import j$.util.i0;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.d1  reason: case insensitive filesystem */
final class C0092d1 extends C0112h1 implements I0 {
    C0092d1() {
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final Object b() {
        return D0.g;
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

    public final Q spliterator() {
        return i0.b();
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m13spliterator() {
        return i0.b();
    }
}
