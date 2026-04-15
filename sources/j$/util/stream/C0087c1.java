package j$.util.stream;

import j$.util.H;
import j$.util.Q;
import j$.util.U;
import j$.util.function.Consumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.c1  reason: case insensitive filesystem */
final class C0087c1 extends Y2 implements I0, E0 {
    C0087c1() {
    }

    public final L0 a(int i) {
        throw new IndexOutOfBoundsException();
    }

    public final void accept(double d) {
        super.accept(d);
    }

    public final /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public final /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        m((Double) obj);
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final Object b() {
        return (double[]) super.b();
    }

    public final I0 build() {
        return this;
    }

    /* renamed from: build  reason: collision with other method in class */
    public final M0 m11build() {
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
        super.f((double[]) obj, i);
    }

    public final void g(Object obj) {
        super.g((DoubleConsumer) obj);
    }

    public final /* synthetic */ M0 i(long j, long j2, IntFunction intFunction) {
        return D0.P(this, j, j2);
    }

    public final /* synthetic */ Object[] k(IntFunction intFunction) {
        return D0.I(this, intFunction);
    }

    public final /* synthetic */ void m(Double d) {
        D0.A(this, d);
    }

    public final /* synthetic */ int n() {
        return 0;
    }

    public final Q spliterator() {
        return super.spliterator();
    }

    /* renamed from: spliterator  reason: collision with other method in class */
    public final U m12spliterator() {
        return super.spliterator();
    }

    public final H u() {
        return super.spliterator();
    }

    /* renamed from: v */
    public final /* synthetic */ void j(Double[] dArr, int i) {
        D0.J(this, dArr, i);
    }
}
