package j$.util.function;

import j$.lang.a;
import java.util.function.DoubleConsumer;

public final /* synthetic */ class b implements DoubleConsumer {
    public final /* synthetic */ DoubleConsumer a;
    public final /* synthetic */ DoubleConsumer b;

    public /* synthetic */ b(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2) {
        this.a = doubleConsumer;
        this.b = doubleConsumer2;
    }

    public final void accept(double d) {
        this.a.accept(d);
        this.b.accept(d);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }
}
