package j$.util.stream;

import j$.lang.a;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.n3  reason: case insensitive filesystem */
final class C0144n3 extends C0159q3 implements DoubleConsumer {
    final double[] c;

    C0144n3(int i) {
        this.c = new double[i];
    }

    /* access modifiers changed from: package-private */
    public final void a(Object obj, long j) {
        DoubleConsumer doubleConsumer = (DoubleConsumer) obj;
        for (int i = 0; ((long) i) < j; i++) {
            doubleConsumer.accept(this.c[i]);
        }
    }

    public final void accept(double d) {
        int i = this.b;
        this.b = i + 1;
        this.c[i] = d;
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return a.b(this, doubleConsumer);
    }
}
