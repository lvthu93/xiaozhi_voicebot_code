package j$.util.stream;

import j$.util.function.Consumer$CC;
import java.util.function.Consumer;

/* renamed from: j$.util.stream.r3  reason: case insensitive filesystem */
final class C0163r3 extends C0168s3 implements Consumer {
    final Object[] b;

    C0163r3(int i) {
        this.b = new Object[i];
    }

    public final void accept(Object obj) {
        int i = this.a;
        this.a = i + 1;
        this.b[i] = obj;
    }

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }
}
