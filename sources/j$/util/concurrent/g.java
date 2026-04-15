package j$.util.concurrent;

final class g extends l {
    final l[] e;

    g(l[] lVarArr) {
        super(-1, (Object) null, (Object) null);
        this.e = lVarArr;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0029, code lost:
        if ((r0 instanceof j$.util.concurrent.g) == false) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002b, code lost:
        r0 = ((j$.util.concurrent.g) r0).e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0034, code lost:
        return r0.a(r5, r6);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final j$.util.concurrent.l a(java.lang.Object r5, int r6) {
        /*
            r4 = this;
            j$.util.concurrent.l[] r0 = r4.e
        L_0x0002:
            r1 = 0
            if (r5 == 0) goto L_0x0039
            if (r0 == 0) goto L_0x0039
            int r2 = r0.length
            if (r2 == 0) goto L_0x0039
            int r2 = r2 + -1
            r2 = r2 & r6
            j$.util.concurrent.l r0 = j$.util.concurrent.ConcurrentHashMap.l(r0, r2)
            if (r0 != 0) goto L_0x0014
            goto L_0x0039
        L_0x0014:
            int r2 = r0.a
            if (r2 != r6) goto L_0x0025
            java.lang.Object r3 = r0.b
            if (r3 == r5) goto L_0x0024
            if (r3 == 0) goto L_0x0025
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x0025
        L_0x0024:
            return r0
        L_0x0025:
            if (r2 >= 0) goto L_0x0035
            boolean r1 = r0 instanceof j$.util.concurrent.g
            if (r1 == 0) goto L_0x0030
            j$.util.concurrent.g r0 = (j$.util.concurrent.g) r0
            j$.util.concurrent.l[] r0 = r0.e
            goto L_0x0002
        L_0x0030:
            j$.util.concurrent.l r5 = r0.a(r5, r6)
            return r5
        L_0x0035:
            j$.util.concurrent.l r0 = r0.d
            if (r0 != 0) goto L_0x0014
        L_0x0039:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.concurrent.g.a(java.lang.Object, int):j$.util.concurrent.l");
    }
}
