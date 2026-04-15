package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

abstract class P implements V3 {
    boolean a;
    Object b;

    P() {
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

    /* renamed from: accept */
    public final void m(Object obj) {
        if (!this.a) {
            this.a = true;
            this.b = obj;
        }
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
