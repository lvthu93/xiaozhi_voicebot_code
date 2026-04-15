package j$.util;

import j$.lang.a;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.z  reason: case insensitive filesystem */
public final /* synthetic */ class C0208z implements LongConsumer {
    public final /* synthetic */ Consumer a;

    public /* synthetic */ C0208z(Consumer consumer) {
        this.a = consumer;
    }

    public final void accept(long j) {
        this.a.accept(Long.valueOf(j));
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }
}
