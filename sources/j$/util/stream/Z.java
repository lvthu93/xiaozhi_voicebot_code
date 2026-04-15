package j$.util.stream;

import j$.lang.a;
import java.util.function.IntConsumer;

public final /* synthetic */ class Z implements IntConsumer {
    public final /* synthetic */ C0182v2 a;

    public /* synthetic */ Z(C0182v2 v2Var) {
        this.a = v2Var;
    }

    public final void accept(int i) {
        this.a.accept(i);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }
}
