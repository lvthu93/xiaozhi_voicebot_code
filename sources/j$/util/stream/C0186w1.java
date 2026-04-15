package j$.util.stream;

import j$.lang.a;
import j$.util.U;
import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.w1  reason: case insensitive filesystem */
final class C0186w1 extends C0200z1 implements C0172t2 {
    private final int[] h;

    C0186w1(U u, D0 d0, int[] iArr) {
        super(iArr.length, u, d0);
        this.h = iArr;
    }

    C0186w1(C0186w1 w1Var, U u, long j, long j2) {
        super(w1Var, u, j, j2, w1Var.h.length);
        this.h = w1Var.h;
    }

    /* access modifiers changed from: package-private */
    public final C0200z1 a(U u, long j, long j2) {
        return new C0186w1(this, u, j, j2);
    }

    public final void accept(int i) {
        int i2 = this.f;
        if (i2 < this.g) {
            int[] iArr = this.h;
            this.f = i2 + 1;
            iArr[i2] = i;
            return;
        }
        throw new IndexOutOfBoundsException(Integer.toString(this.f));
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
