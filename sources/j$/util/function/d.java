package j$.util.function;

import j$.lang.a;
import java.util.function.IntConsumer;

public final /* synthetic */ class d implements IntConsumer {
    public final /* synthetic */ IntConsumer a;
    public final /* synthetic */ IntConsumer b;

    public /* synthetic */ d(IntConsumer intConsumer, IntConsumer intConsumer2) {
        this.a = intConsumer;
        this.b = intConsumer2;
    }

    public final void accept(int i) {
        this.a.accept(i);
        this.b.accept(i);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }
}
