package j$.util.stream;

import j$.util.N;
import j$.util.Q;
import j$.util.U;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.u1  reason: case insensitive filesystem */
final class C0176u1 extends C0089c3 implements K0, G0 {
    C0176u1() {
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final void accept(long j) {
        super.accept(j);
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        l((Long) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final Object b() {
        return (long[]) super.b();
    }

    public final K0 build() {
        return this;
    }

    /* renamed from: build  reason: collision with other method in class */
    public final M0 m20build() {
        return this;
    }

    public final void c(long j) {
        clear();
        r(j);
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final void end() {
    }

    public final void f(Object obj, int i) {
        super.f((long[]) obj, i);
    }

    public final void g(Object obj) {
        super.g((LongConsumer) obj);
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.R(this, j, j2);
    }

    public final /* synthetic */ Object[] k(IntFunction intFunction) {
        return D0.I(this, intFunction);
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    public final Q spliterator() {
        return super.spliterator();
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m21spliterator() {
        return super.spliterator();
    }

    public final N u() {
        return super.spliterator();
    }

    /* renamed from: v */
    public final /* synthetic */ void j(Long[] lArr, int i) {
        D0.L(this, lArr, i);
    }
}
