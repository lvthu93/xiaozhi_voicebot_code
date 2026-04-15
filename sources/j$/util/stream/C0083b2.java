package j$.util.stream;

import j$.lang.a;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.b2  reason: case insensitive filesystem */
final class C0083b2 extends C0098e2 implements C0172t2 {
    C0083b2() {
    }

    public final void accept(int i) {
        this.b++;
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        d((Integer) obj);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return a.c(this, intConsumer);
    }

    public final /* synthetic */ void d(Integer num) {
        D0.C(this, num);
    }

    public final Object get() {
        return Long.valueOf(this.b);
    }

    public final void h(Y1 y1) {
        this.b += ((C0098e2) y1).b;
    }
}
