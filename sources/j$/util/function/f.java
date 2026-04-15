package j$.util.function;

import j$.lang.a;
import java.util.function.LongConsumer;

public final /* synthetic */ class f implements LongConsumer {
    public final /* synthetic */ LongConsumer a;
    public final /* synthetic */ LongConsumer b;

    public /* synthetic */ f(LongConsumer longConsumer, LongConsumer longConsumer2) {
        this.a = longConsumer;
        this.b = longConsumer2;
    }

    public final void accept(long j) {
        this.a.accept(j);
        this.b.accept(j);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }
}
