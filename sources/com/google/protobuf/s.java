package com.google.protobuf;

public class s {
    public volatile x a;
    public volatile cp b;

    public final int a() {
        int i;
        if (this.b != null) {
            i = this.b.size();
        } else if (this.a != null) {
            i = this.a.getSerializedSize();
        } else {
            i = 0;
        }
        return n0.u(i) + i;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:7|8|9|10|11) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0013 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.protobuf.x b(com.google.protobuf.x r2) {
        /*
            r1 = this;
            com.google.protobuf.x r0 = r1.a
            if (r0 == 0) goto L_0x0005
            goto L_0x001a
        L_0x0005:
            monitor-enter(r1)
            com.google.protobuf.x r0 = r1.a     // Catch:{ all -> 0x001d }
            if (r0 == 0) goto L_0x000c
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            goto L_0x001a
        L_0x000c:
            r1.a = r2     // Catch:{ q -> 0x0013 }
            cp$h r0 = defpackage.cp.f     // Catch:{ q -> 0x0013 }
            r1.b = r0     // Catch:{ q -> 0x0013 }
            goto L_0x0019
        L_0x0013:
            r1.a = r2     // Catch:{ all -> 0x001d }
            cp$h r2 = defpackage.cp.f     // Catch:{ all -> 0x001d }
            r1.b = r2     // Catch:{ all -> 0x001d }
        L_0x0019:
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
        L_0x001a:
            com.google.protobuf.x r2 = r1.a
            return r2
        L_0x001d:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x001d }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.s.b(com.google.protobuf.x):com.google.protobuf.x");
    }

    public final cp c() {
        if (this.b != null) {
            return this.b;
        }
        synchronized (this) {
            if (this.b != null) {
                cp cpVar = this.b;
                return cpVar;
            }
            if (this.a == null) {
                this.b = cp.f;
            } else {
                this.b = this.a.toByteString();
            }
            cp cpVar2 = this.b;
            return cpVar2;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        x xVar = this.a;
        x xVar2 = sVar.a;
        if (xVar == null && xVar2 == null) {
            return c().equals(sVar.c());
        }
        if (xVar != null && xVar2 != null) {
            return xVar.equals(xVar2);
        }
        if (xVar != null) {
            return xVar.equals(sVar.b(xVar.getDefaultInstanceForType()));
        }
        return b(xVar2.getDefaultInstanceForType()).equals(xVar2);
    }

    public int hashCode() {
        return 1;
    }
}
