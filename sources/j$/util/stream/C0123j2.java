package j$.util.stream;

/* renamed from: j$.util.stream.j2  reason: case insensitive filesystem */
final class C0123j2 extends C0162r2 {
    boolean b;
    final /* synthetic */ C0118i2 c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C0123j2(C0118i2 i2Var, C0182v2 v2Var) {
        super(v2Var);
        this.c = i2Var;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0026 A[Catch:{ all -> 0x0033, all -> 0x0038 }, LOOP:0: B:9:0x0026->B:12:0x0030, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void accept(java.lang.Object r4) {
        /*
            r3 = this;
            j$.util.stream.i2 r0 = r3.c
            java.util.function.Function r0 = r0.t
            java.lang.Object r4 = r0.apply(r4)
            j$.util.stream.Stream r4 = (j$.util.stream.Stream) r4
            if (r4 == 0) goto L_0x003d
            boolean r0 = r3.b     // Catch:{ all -> 0x0033 }
            j$.util.stream.v2 r1 = r3.a
            if (r0 != 0) goto L_0x001c
            j$.util.stream.i r0 = r4.sequential()     // Catch:{ all -> 0x0033 }
            j$.util.stream.Stream r0 = (j$.util.stream.Stream) r0     // Catch:{ all -> 0x0033 }
            r0.forEach(r1)     // Catch:{ all -> 0x0033 }
            goto L_0x003d
        L_0x001c:
            j$.util.stream.i r0 = r4.sequential()     // Catch:{ all -> 0x0033 }
            j$.util.stream.Stream r0 = (j$.util.stream.Stream) r0     // Catch:{ all -> 0x0033 }
            j$.util.U r0 = r0.spliterator()     // Catch:{ all -> 0x0033 }
        L_0x0026:
            boolean r2 = r1.e()     // Catch:{ all -> 0x0033 }
            if (r2 != 0) goto L_0x003d
            boolean r2 = r0.tryAdvance(r1)     // Catch:{ all -> 0x0033 }
            if (r2 != 0) goto L_0x0026
            goto L_0x003d
        L_0x0033:
            r0 = move-exception
            r4.close()     // Catch:{ all -> 0x0038 }
            goto L_0x003c
        L_0x0038:
            r4 = move-exception
            r0.addSuppressed(r4)
        L_0x003c:
            throw r0
        L_0x003d:
            if (r4 == 0) goto L_0x0042
            r4.close()
        L_0x0042:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.C0123j2.accept(java.lang.Object):void");
    }

    public final void c(long j) {
        this.a.c(-1);
    }

    public final boolean e() {
        this.b = true;
        return this.a.e();
    }
}
