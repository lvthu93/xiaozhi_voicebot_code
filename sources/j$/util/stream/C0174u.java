package j$.util.stream;

import j$.lang.a;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.u  reason: case insensitive filesystem */
public final /* synthetic */ class C0174u implements DoubleConsumer {
    public final /* synthetic */ C0182v2 a;

    public /* synthetic */ C0174u(C0182v2 v2Var) {
        this.a = v2Var;
    }

    public final void accept(double d) {
        this.a.accept(d);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }
}
