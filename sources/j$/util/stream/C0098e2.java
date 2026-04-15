package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.e2  reason: case insensitive filesystem */
abstract class C0098e2 extends Z1 implements Y1 {
    long b;

    C0098e2() {
    }

    public /* synthetic */ void accept(double d) {
        D0.z();
        throw null;
    }

    public /* synthetic */ void accept(int i) {
        D0.G();
        throw null;
    }

    public /* synthetic */ void accept(long j) {
        D0.H();
        throw null;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    public final void c(long j) {
        this.b = 0;
    }

    public final /* synthetic */ boolean e() {
        return false;
    }

    public final /* synthetic */ void end() {
    }
}
