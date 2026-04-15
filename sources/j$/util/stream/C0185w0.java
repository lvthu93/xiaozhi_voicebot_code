package j$.util.stream;

import j$.lang.a;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.w0  reason: case insensitive filesystem */
final class C0185w0 extends C0199z0 implements C0172t2 {
    C0185w0(A0 a0) {
        super(a0);
    }

    public final void accept(int i) {
        if (!this.a) {
            throw null;
        }
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
}
