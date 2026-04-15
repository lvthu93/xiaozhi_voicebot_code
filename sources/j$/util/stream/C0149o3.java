package j$.util.stream;

import j$.lang.a;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.o3  reason: case insensitive filesystem */
final class C0149o3 extends C0159q3 implements IntConsumer {
    final int[] c;

    C0149o3(int i) {
        this.c = new int[i];
    }

    public final void a(Object obj, long j) {
        IntConsumer intConsumer = (IntConsumer) obj;
        for (int i = 0; ((long) i) < j; i++) {
            intConsumer.accept(this.c[i]);
        }
    }

    public final void accept(int i) {
        int i2 = this.b;
        this.b = i2 + 1;
        this.c[i2] = i;
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }
}
