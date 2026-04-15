package j$.util.stream;

import j$.util.K;
import j$.util.Q;
import j$.util.U;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.l1  reason: case insensitive filesystem */
final class C0132l1 extends C0079a3 implements J0, F0 {
    C0132l1() {
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public final void accept(int i) {
        super.accept(i);
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        d((Integer) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final Object b() {
        return (int[]) super.b();
    }

    public final J0 build() {
        return this;
    }

    /* renamed from: build  reason: collision with other method in class */
    public final M0 m17build() {
        return this;
    }

    public final void c(long j) {
        clear();
        r(j);
    }

    public final /* synthetic */ void d(Integer num) {
        D0.C(this, num);
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final void end() {
    }

    public final void f(Object obj, int i) {
        super.f((int[]) obj, i);
    }

    public final void g(Object obj) {
        super.g((IntConsumer) obj);
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.Q(this, j, j2);
    }

    public final /* synthetic */ Object[] k(IntFunction intFunction) {
        return D0.I(this, intFunction);
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    public final Q spliterator() {
        return super.spliterator();
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m18spliterator() {
        return super.spliterator();
    }

    public final K u() {
        return super.spliterator();
    }

    /* renamed from: v */
    public final /* synthetic */ void j(Integer[] numArr, int i) {
        D0.K(this, numArr, i);
    }
}
