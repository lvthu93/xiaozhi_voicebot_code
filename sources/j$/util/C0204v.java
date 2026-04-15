package j$.util;

import j$.lang.a;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.v  reason: case insensitive filesystem */
public final /* synthetic */ class C0204v implements IntConsumer {
    public final /* synthetic */ Consumer a;

    public /* synthetic */ C0204v(Consumer consumer) {
        this.a = consumer;
    }

    public final void accept(int i) {
        this.a.accept(Integer.valueOf(i));
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }
}
