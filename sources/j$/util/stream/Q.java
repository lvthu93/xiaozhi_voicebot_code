package j$.util.stream;

import j$.util.U;

final class Q extends C0090d {
    private final J j;
    private final boolean k;

    Q(J j2, boolean z, D0 d0, U u) {
        super(d0, u);
        this.k = z;
        this.j = j2;
    }

    Q(Q q, U u) {
        super((C0090d) q, u);
        this.k = q.k;
        this.j = q.j;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0044 A[LOOP:2: B:21:0x0044->B:24:0x004f, LOOP_START] */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x001e A[LOOP:0: B:4:0x001e->B:7:0x0029, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a() {
        /*
            r5 = this;
            j$.util.stream.D0 r0 = r5.a
            j$.util.stream.J r1 = r5.j
            java.util.function.Supplier r1 = r1.d
            java.lang.Object r1 = r1.get()
            j$.util.stream.V3 r1 = (j$.util.stream.V3) r1
            j$.util.U r2 = r5.b
            r0.I0(r2, r1)
            java.lang.Object r0 = r1.get()
            boolean r1 = r5.k
            r2 = 0
            if (r1 != 0) goto L_0x002c
            if (r0 == 0) goto L_0x002b
            java.util.concurrent.atomic.AtomicReference r1 = r5.h
        L_0x001e:
            boolean r3 = r1.compareAndSet(r2, r0)
            if (r3 == 0) goto L_0x0025
            goto L_0x002b
        L_0x0025:
            java.lang.Object r3 = r1.get()
            if (r3 == 0) goto L_0x001e
        L_0x002b:
            return r2
        L_0x002c:
            if (r0 == 0) goto L_0x0056
            r1 = r5
        L_0x002f:
            if (r1 == 0) goto L_0x003f
            j$.util.stream.f r3 = r1.d()
            if (r3 == 0) goto L_0x003d
            j$.util.stream.f r4 = r3.d
            if (r4 == r1) goto L_0x003d
            r1 = 0
            goto L_0x0040
        L_0x003d:
            r1 = r3
            goto L_0x002f
        L_0x003f:
            r1 = 1
        L_0x0040:
            if (r1 == 0) goto L_0x0052
            java.util.concurrent.atomic.AtomicReference r1 = r5.h
        L_0x0044:
            boolean r3 = r1.compareAndSet(r2, r0)
            if (r3 == 0) goto L_0x004b
            goto L_0x0055
        L_0x004b:
            java.lang.Object r3 = r1.get()
            if (r3 == 0) goto L_0x0044
            goto L_0x0055
        L_0x0052:
            r5.i()
        L_0x0055:
            return r0
        L_0x0056:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.Q.a():java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public final C0100f e(U u) {
        return new Q(this, u);
    }

    /* access modifiers changed from: protected */
    public final Object j() {
        return this.j.b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0035 A[LOOP:2: B:19:0x0035->B:22:0x0040, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onCompletion(java.util.concurrent.CountedCompleter r7) {
        /*
            r6 = this;
            boolean r0 = r6.k
            if (r0 == 0) goto L_0x004f
            j$.util.stream.f r0 = r6.d
            j$.util.stream.Q r0 = (j$.util.stream.Q) r0
            r1 = 0
            r2 = r1
        L_0x000a:
            if (r0 == r2) goto L_0x004f
            java.lang.Object r2 = r0.c()
            if (r2 == 0) goto L_0x0047
            j$.util.stream.J r3 = r6.j
            java.util.function.Predicate r3 = r3.c
            boolean r3 = r3.test(r2)
            if (r3 == 0) goto L_0x0047
            r6.f(r2)
            r0 = r6
        L_0x0020:
            if (r0 == 0) goto L_0x0030
            j$.util.stream.f r3 = r0.d()
            if (r3 == 0) goto L_0x002e
            j$.util.stream.f r4 = r3.d
            if (r4 == r0) goto L_0x002e
            r0 = 0
            goto L_0x0031
        L_0x002e:
            r0 = r3
            goto L_0x0020
        L_0x0030:
            r0 = 1
        L_0x0031:
            if (r0 == 0) goto L_0x0043
            java.util.concurrent.atomic.AtomicReference r0 = r6.h
        L_0x0035:
            boolean r3 = r0.compareAndSet(r1, r2)
            if (r3 == 0) goto L_0x003c
            goto L_0x004f
        L_0x003c:
            java.lang.Object r3 = r0.get()
            if (r3 == 0) goto L_0x0035
            goto L_0x004f
        L_0x0043:
            r6.i()
            goto L_0x004f
        L_0x0047:
            j$.util.stream.f r2 = r6.e
            j$.util.stream.Q r2 = (j$.util.stream.Q) r2
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x000a
        L_0x004f:
            super.onCompletion(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.Q.onCompletion(java.util.concurrent.CountedCompleter):void");
    }
}
