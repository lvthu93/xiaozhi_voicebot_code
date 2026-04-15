package j$.util.stream;

import j$.util.Q;
import j$.util.U;
import j$.util.i0;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.f1  reason: case insensitive filesystem */
final class C0102f1 extends C0112h1 implements K0 {
    C0102f1() {
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final Object b() {
        return D0.f;
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

    public final Q spliterator() {
        return i0.d();
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m15spliterator() {
        return i0.d();
    }
}
