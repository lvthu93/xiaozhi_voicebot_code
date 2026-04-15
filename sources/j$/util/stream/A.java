package j$.util.stream;

import j$.util.Objects;

final class A extends C0148o2 {
    boolean b;
    C0174u c;
    final /* synthetic */ C0189x d;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    A(C0189x xVar, C0182v2 v2Var) {
        super(v2Var);
        this.d = xVar;
        C0182v2 v2Var2 = this.a;
        Objects.requireNonNull(v2Var2);
        this.c = new C0174u(v2Var2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0024 A[Catch:{ all -> 0x0035, all -> 0x003a }, LOOP:0: B:7:0x0024->B:10:0x0032, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(double r2) {
        /*
            r1 = this;
            j$.util.stream.x r0 = r1.d
            java.lang.Object r0 = r0.t
            java.util.function.DoubleFunction r0 = (java.util.function.DoubleFunction) r0
            java.lang.Object r2 = r0.apply(r2)
            j$.util.stream.I r2 = (j$.util.stream.I) r2
            if (r2 == 0) goto L_0x003f
            boolean r3 = r1.b     // Catch:{ all -> 0x0035 }
            if (r3 != 0) goto L_0x001c
            j$.util.stream.I r3 = r2.sequential()     // Catch:{ all -> 0x0035 }
            j$.util.stream.u r0 = r1.c     // Catch:{ all -> 0x0035 }
            r3.forEach(r0)     // Catch:{ all -> 0x0035 }
            goto L_0x003f
        L_0x001c:
            j$.util.stream.I r3 = r2.sequential()     // Catch:{ all -> 0x0035 }
            j$.util.H r3 = r3.spliterator()     // Catch:{ all -> 0x0035 }
        L_0x0024:
            j$.util.stream.v2 r0 = r1.a     // Catch:{ all -> 0x0035 }
            boolean r0 = r0.e()     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x003f
            j$.util.stream.u r0 = r1.c     // Catch:{ all -> 0x0035 }
            boolean r0 = r3.tryAdvance((java.util.function.DoubleConsumer) r0)     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x0024
            goto L_0x003f
        L_0x0035:
            r3 = move-exception
            r2.close()     // Catch:{ all -> 0x003a }
            goto L_0x003e
        L_0x003a:
            r2 = move-exception
            r3.addSuppressed(r2)
        L_0x003e:
            throw r3
        L_0x003f:
            if (r2 == 0) goto L_0x0044
            r2.close()
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.A.accept(double):void");
    }

    public final void c(long j) {
        this.a.c(-1);
    }

    public final boolean e() {
        this.b = true;
        return this.a.e();
    }
}
