package j$.util.stream;

import j$.util.U;

final class C0 extends C0090d {
    private final B0 j;

    C0(B0 b0, D0 d0, U u) {
        super(d0, u);
        this.j = b0;
    }

    C0(C0 c0, U u) {
        super((C0090d) c0, u);
        this.j = c0.j;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x0026 A[LOOP:0: B:5:0x0026->B:8:0x0031, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a() {
        /*
            r4 = this;
            j$.util.stream.D0 r0 = r4.a
            j$.util.stream.B0 r1 = r4.j
            java.util.function.Supplier r1 = r1.b
            java.lang.Object r1 = r1.get()
            j$.util.stream.z0 r1 = (j$.util.stream.C0199z0) r1
            j$.util.U r2 = r4.b
            r0.I0(r2, r1)
            boolean r0 = r1.b
            j$.util.stream.B0 r1 = r4.j
            j$.util.stream.A0 r1 = r1.a
            boolean r1 = r1.b
            r2 = 0
            if (r0 != r1) goto L_0x0033
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            if (r0 == 0) goto L_0x0033
            java.util.concurrent.atomic.AtomicReference r1 = r4.h
        L_0x0026:
            boolean r3 = r1.compareAndSet(r2, r0)
            if (r3 == 0) goto L_0x002d
            goto L_0x0033
        L_0x002d:
            java.lang.Object r3 = r1.get()
            if (r3 == 0) goto L_0x0026
        L_0x0033:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.C0.a():java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public final C0100f e(U u) {
        return new C0(this, u);
    }

    /* access modifiers changed from: protected */
    public final Object j() {
        return Boolean.valueOf(!this.j.a.b);
    }
}
