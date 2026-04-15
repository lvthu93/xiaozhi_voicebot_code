package j$.util.stream;

import j$.lang.a;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.p3  reason: case insensitive filesystem */
final class C0154p3 extends C0159q3 implements LongConsumer {
    final long[] c;

    C0154p3(int i) {
        this.c = new long[i];
    }

    public final void a(Object obj, long j) {
        LongConsumer longConsumer = (LongConsumer) obj;
        for (int i = 0; ((long) i) < j; i++) {
            longConsumer.accept(this.c[i]);
        }
    }

    public final void accept(long j) {
        int i = this.b;
        this.b = i + 1;
        this.c[i] = j;
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }
}
