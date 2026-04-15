package j$.util.stream;

import j$.util.Objects;

/* renamed from: j$.util.stream.q  reason: case insensitive filesystem */
final class C0155q extends C0162r2 {
    public final /* synthetic */ int b = 0;
    boolean c;
    Object d;
    final /* synthetic */ C0085c e;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C0155q(C0164s sVar, C0182v2 v2Var) {
        super(v2Var);
        this.e = sVar;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C0155q(C0189x xVar, C0182v2 v2Var) {
        super(v2Var);
        this.e = xVar;
        C0182v2 v2Var2 = this.a;
        Objects.requireNonNull(v2Var2);
        this.d = new C0174u(v2Var2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C0155q(C0194y yVar, C0182v2 v2Var) {
        super(v2Var);
        this.e = yVar;
        C0182v2 v2Var2 = this.a;
        Objects.requireNonNull(v2Var2);
        this.d = new Z(v2Var2);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C0155q(C0198z zVar, C0182v2 v2Var) {
        super(v2Var);
        this.e = zVar;
        C0182v2 v2Var2 = this.a;
        Objects.requireNonNull(v2Var2);
        this.d = new C0131l0(v2Var2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0078 A[Catch:{ all -> 0x0089, all -> 0x008e }, LOOP:1: B:28:0x0078->B:31:0x0086, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00da A[Catch:{ all -> 0x00eb, all -> 0x00f0 }, LOOP:2: B:56:0x00da->B:59:0x00e8, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0031 A[Catch:{ all -> 0x0042, all -> 0x0047 }, LOOP:0: B:9:0x0031->B:12:0x003f, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(java.lang.Object r4) {
        /*
            r3 = this;
            int r0 = r3.b
            j$.util.stream.c r1 = r3.e
            j$.util.stream.v2 r2 = r3.a
            switch(r0) {
                case 0: goto L_0x0099;
                case 1: goto L_0x0052;
                case 2: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x00b4
        L_0x000b:
            j$.util.stream.y r1 = (j$.util.stream.C0194y) r1
            java.lang.Object r0 = r1.t
            java.util.function.Function r0 = (java.util.function.Function) r0
            java.lang.Object r4 = r0.apply(r4)
            j$.util.stream.k0 r4 = (j$.util.stream.C0126k0) r4
            if (r4 == 0) goto L_0x004c
            boolean r0 = r3.c     // Catch:{ all -> 0x0042 }
            if (r0 != 0) goto L_0x0029
            j$.util.stream.k0 r0 = r4.sequential()     // Catch:{ all -> 0x0042 }
            java.lang.Object r1 = r3.d     // Catch:{ all -> 0x0042 }
            java.util.function.IntConsumer r1 = (java.util.function.IntConsumer) r1     // Catch:{ all -> 0x0042 }
            r0.forEach(r1)     // Catch:{ all -> 0x0042 }
            goto L_0x004c
        L_0x0029:
            j$.util.stream.k0 r0 = r4.sequential()     // Catch:{ all -> 0x0042 }
            j$.util.K r0 = r0.spliterator()     // Catch:{ all -> 0x0042 }
        L_0x0031:
            boolean r1 = r2.e()     // Catch:{ all -> 0x0042 }
            if (r1 != 0) goto L_0x004c
            java.lang.Object r1 = r3.d     // Catch:{ all -> 0x0042 }
            java.util.function.IntConsumer r1 = (java.util.function.IntConsumer) r1     // Catch:{ all -> 0x0042 }
            boolean r1 = r0.tryAdvance((java.util.function.IntConsumer) r1)     // Catch:{ all -> 0x0042 }
            if (r1 != 0) goto L_0x0031
            goto L_0x004c
        L_0x0042:
            r0 = move-exception
            r4.close()     // Catch:{ all -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r4 = move-exception
            r0.addSuppressed(r4)
        L_0x004b:
            throw r0
        L_0x004c:
            if (r4 == 0) goto L_0x0051
            r4.close()
        L_0x0051:
            return
        L_0x0052:
            j$.util.stream.z r1 = (j$.util.stream.C0198z) r1
            java.lang.Object r0 = r1.t
            java.util.function.Function r0 = (java.util.function.Function) r0
            java.lang.Object r4 = r0.apply(r4)
            j$.util.stream.u0 r4 = (j$.util.stream.C0175u0) r4
            if (r4 == 0) goto L_0x0093
            boolean r0 = r3.c     // Catch:{ all -> 0x0089 }
            if (r0 != 0) goto L_0x0070
            j$.util.stream.u0 r0 = r4.sequential()     // Catch:{ all -> 0x0089 }
            java.lang.Object r1 = r3.d     // Catch:{ all -> 0x0089 }
            java.util.function.LongConsumer r1 = (java.util.function.LongConsumer) r1     // Catch:{ all -> 0x0089 }
            r0.forEach(r1)     // Catch:{ all -> 0x0089 }
            goto L_0x0093
        L_0x0070:
            j$.util.stream.u0 r0 = r4.sequential()     // Catch:{ all -> 0x0089 }
            j$.util.N r0 = r0.spliterator()     // Catch:{ all -> 0x0089 }
        L_0x0078:
            boolean r1 = r2.e()     // Catch:{ all -> 0x0089 }
            if (r1 != 0) goto L_0x0093
            java.lang.Object r1 = r3.d     // Catch:{ all -> 0x0089 }
            java.util.function.LongConsumer r1 = (java.util.function.LongConsumer) r1     // Catch:{ all -> 0x0089 }
            boolean r1 = r0.tryAdvance((java.util.function.LongConsumer) r1)     // Catch:{ all -> 0x0089 }
            if (r1 != 0) goto L_0x0078
            goto L_0x0093
        L_0x0089:
            r0 = move-exception
            r4.close()     // Catch:{ all -> 0x008e }
            goto L_0x0092
        L_0x008e:
            r4 = move-exception
            r0.addSuppressed(r4)
        L_0x0092:
            throw r0
        L_0x0093:
            if (r4 == 0) goto L_0x0098
            r4.close()
        L_0x0098:
            return
        L_0x0099:
            if (r4 != 0) goto L_0x00a4
            boolean r4 = r3.c
            if (r4 != 0) goto L_0x00b3
            r4 = 1
            r3.c = r4
            r4 = 0
            goto L_0x00ae
        L_0x00a4:
            java.lang.Object r0 = r3.d
            if (r0 == 0) goto L_0x00ae
            boolean r0 = r4.equals(r0)
            if (r0 != 0) goto L_0x00b3
        L_0x00ae:
            r3.d = r4
            r2.accept(r4)
        L_0x00b3:
            return
        L_0x00b4:
            j$.util.stream.x r1 = (j$.util.stream.C0189x) r1
            java.lang.Object r0 = r1.t
            java.util.function.Function r0 = (java.util.function.Function) r0
            java.lang.Object r4 = r0.apply(r4)
            j$.util.stream.I r4 = (j$.util.stream.I) r4
            if (r4 == 0) goto L_0x00f5
            boolean r0 = r3.c     // Catch:{ all -> 0x00eb }
            if (r0 != 0) goto L_0x00d2
            j$.util.stream.I r0 = r4.sequential()     // Catch:{ all -> 0x00eb }
            java.lang.Object r1 = r3.d     // Catch:{ all -> 0x00eb }
            java.util.function.DoubleConsumer r1 = (java.util.function.DoubleConsumer) r1     // Catch:{ all -> 0x00eb }
            r0.forEach(r1)     // Catch:{ all -> 0x00eb }
            goto L_0x00f5
        L_0x00d2:
            j$.util.stream.I r0 = r4.sequential()     // Catch:{ all -> 0x00eb }
            j$.util.H r0 = r0.spliterator()     // Catch:{ all -> 0x00eb }
        L_0x00da:
            boolean r1 = r2.e()     // Catch:{ all -> 0x00eb }
            if (r1 != 0) goto L_0x00f5
            java.lang.Object r1 = r3.d     // Catch:{ all -> 0x00eb }
            java.util.function.DoubleConsumer r1 = (java.util.function.DoubleConsumer) r1     // Catch:{ all -> 0x00eb }
            boolean r1 = r0.tryAdvance((java.util.function.DoubleConsumer) r1)     // Catch:{ all -> 0x00eb }
            if (r1 != 0) goto L_0x00da
            goto L_0x00f5
        L_0x00eb:
            r0 = move-exception
            r4.close()     // Catch:{ all -> 0x00f0 }
            goto L_0x00f4
        L_0x00f0:
            r4 = move-exception
            r0.addSuppressed(r4)
        L_0x00f4:
            throw r0
        L_0x00f5:
            if (r4 == 0) goto L_0x00fa
            r4.close()
        L_0x00fa:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.C0155q.accept(java.lang.Object):void");
    }

    public final void c(long j) {
        int i = this.b;
        C0182v2 v2Var = this.a;
        switch (i) {
            case 0:
                this.c = false;
                this.d = null;
                v2Var.c(-1);
                return;
            case 1:
                v2Var.c(-1);
                return;
            case 2:
                v2Var.c(-1);
                return;
            default:
                v2Var.c(-1);
                return;
        }
    }

    public final boolean e() {
        int i = this.b;
        C0182v2 v2Var = this.a;
        switch (i) {
            case 1:
                this.c = true;
                return v2Var.e();
            case 2:
                this.c = true;
                return v2Var.e();
            case 3:
                this.c = true;
                return v2Var.e();
            default:
                return super.e();
        }
    }

    public final void end() {
        switch (this.b) {
            case 0:
                this.c = false;
                this.d = null;
                this.a.end();
                return;
            default:
                super.end();
                return;
        }
    }
}
