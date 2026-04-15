package j$.util;

import j$.lang.a;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

public final /* synthetic */ class r implements DoubleConsumer {
    public final /* synthetic */ Consumer a;

    public /* synthetic */ r(Consumer consumer) {
        this.a = consumer;
    }

    public final void accept(double d) {
        this.a.accept(Double.valueOf(d));
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }
}
