package j$.util.stream;

import j$.lang.a;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.l0  reason: case insensitive filesystem */
public final /* synthetic */ class C0131l0 implements LongConsumer {
    public final /* synthetic */ C0182v2 a;

    public /* synthetic */ C0131l0(C0182v2 v2Var) {
        this.a = v2Var;
    }

    public final void accept(long j) {
        this.a.accept(j);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }
}
