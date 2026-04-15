package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.z0  reason: case insensitive filesystem */
abstract class C0199z0 implements C0182v2 {
    boolean a;
    boolean b;

    C0199z0(A0 a0) {
        this.b = !a0.b;
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

    public final /* synthetic */ void c(long j) {
    }

    public final boolean e() {
        return this.a;
    }

    public final /* synthetic */ void end() {
    }
}
