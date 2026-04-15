package j$.util.stream;

import j$.util.Objects;

/* renamed from: j$.util.stream.c0  reason: case insensitive filesystem */
final class C0086c0 extends C0153p2 {
    boolean b;
    Z c;
    final /* synthetic */ C0194y d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0086c0(C0194y yVar, C0182v2 v2Var) {
        super(v2Var);
        this.d = yVar;
        C0182v2 v2Var2 = this.a;
        Objects.requireNonNull(v2Var2);
        this.c = new Z(v2Var2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0024 A[Catch:{ all -> 0x0035, all -> 0x003a }, LOOP:0: B:7:0x0024->B:10:0x0032, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(int r3) {
        /*
            r2 = this;
            j$.util.stream.y r0 = r2.d
            java.lang.Object r0 = r0.t
            java.util.function.IntFunction r0 = (java.util.function.IntFunction) r0
            java.lang.Object r3 = r0.apply(r3)
            j$.util.stream.k0 r3 = (j$.util.stream.C0126k0) r3
            if (r3 == 0) goto L_0x003f
            boolean r0 = r2.b     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x001c
            j$.util.stream.k0 r0 = r3.sequential()     // Catch:{ all -> 0x0035 }
            j$.util.stream.Z r1 = r2.c     // Catch:{ all -> 0x0035 }
            r0.forEach(r1)     // Catch:{ all -> 0x0035 }
            goto L_0x003f
        L_0x001c:
            j$.util.stream.k0 r0 = r3.sequential()     // Catch:{ all -> 0x0035 }
            j$.util.K r0 = r0.spliterator()     // Catch:{ all -> 0x0035 }
        L_0x0024:
            j$.util.stream.v2 r1 = r2.a     // Catch:{ all -> 0x0035 }
            boolean r1 = r1.e()     // Catch:{ all -> 0x0035 }
            if (r1 != 0) goto L_0x003f
            j$.util.stream.Z r1 = r2.c     // Catch:{ all -> 0x0035 }
            boolean r1 = r0.tryAdvance((java.util.function.IntConsumer) r1)     // Catch:{ all -> 0x0035 }
            if (r1 != 0) goto L_0x0024
            goto L_0x003f
        L_0x0035:
            r0 = move-exception
            r3.close()     // Catch:{ all -> 0x003a }
            goto L_0x003e
        L_0x003a:
            r3 = move-exception
            r0.addSuppressed(r3)
        L_0x003e:
            throw r0
        L_0x003f:
            if (r3 == 0) goto L_0x0044
            r3.close()
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.C0086c0.accept(int):void");
    }

    public final void c(long j) {
        this.a.c(-1);
    }

    public final boolean e() {
        this.b = true;
        return this.a.e();
    }
}
