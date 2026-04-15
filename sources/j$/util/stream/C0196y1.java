package j$.util.stream;

import j$.util.U;

/* renamed from: j$.util.stream.y1  reason: case insensitive filesystem */
final class C0196y1 extends C0200z1 {
    private final Object[] h;

    C0196y1(U u, D0 d0, Object[] objArr) {
        super(objArr.length, u, d0);
        this.h = objArr;
    }

    C0196y1(C0196y1 y1Var, U u, long j, long j2) {
        super(y1Var, u, j, j2, y1Var.h.length);
        this.h = y1Var.h;
    }

    /* access modifiers changed from: package-private */
    public final C0200z1 a(U u, long j, long j2) {
        return new C0196y1(this, u, j, j2);
    }

    public final void accept(Object obj) {
        int i = this.f;
        if (i < this.g) {
            Object[] objArr = this.h;
            this.f = i + 1;
            objArr[i] = obj;
            return;
        }
        throw new IndexOutOfBoundsException(Integer.toString(this.f));
    }
}
