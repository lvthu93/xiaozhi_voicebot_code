package j$.util.stream;

import j$.lang.a;
import j$.util.U;
import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.x1  reason: case insensitive filesystem */
final class C0191x1 extends C0200z1 implements C0177u2 {
    private final long[] h;

    C0191x1(U u, D0 d0, long[] jArr) {
        super(jArr.length, u, d0);
        this.h = jArr;
    }

    C0191x1(C0191x1 x1Var, U u, long j, long j2) {
        super(x1Var, u, j, j2, x1Var.h.length);
        this.h = x1Var.h;
    }

    /* access modifiers changed from: package-private */
    public final C0200z1 a(U u, long j, long j2) {
        return new C0191x1(this, u, j, j2);
    }

    public final void accept(long j) {
        int i = this.f;
        if (i < this.g) {
            long[] jArr = this.h;
            this.f = i + 1;
            jArr[i] = j;
            return;
        }
        throw new IndexOutOfBoundsException(Integer.toString(this.f));
    }

    public final /* bridge */ /* synthetic */ void accept(Object obj) {
        l((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return a.d(this, longConsumer);
    }

    public final /* synthetic */ void l(Long l) {
        D0.E(this, l);
    }
}
