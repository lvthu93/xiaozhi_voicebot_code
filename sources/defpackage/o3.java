package defpackage;

import com.google.protobuf.n;

/* renamed from: o3  reason: default package */
public final class o3 implements o6 {
    public static final o3 a = new o3();

    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Class<?>, java.lang.Class] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final defpackage.n6 a(java.lang.Class<?> r4) {
        /*
            r3 = this;
            java.lang.Class<com.google.protobuf.n> r0 = com.google.protobuf.n.class
            boolean r1 = r0.isAssignableFrom(r4)
            if (r1 == 0) goto L_0x0028
            java.lang.Class r0 = r4.asSubclass(r0)     // Catch:{ Exception -> 0x0017 }
            com.google.protobuf.n r0 = com.google.protobuf.n.getDefaultInstance(r0)     // Catch:{ Exception -> 0x0017 }
            java.lang.Object r0 = r0.buildMessageInfo()     // Catch:{ Exception -> 0x0017 }
            n6 r0 = (defpackage.n6) r0     // Catch:{ Exception -> 0x0017 }
            return r0
        L_0x0017:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r4 = r4.getName()
            java.lang.String r2 = "Unable to get message info for "
            java.lang.String r4 = r2.concat(r4)
            r1.<init>(r4, r0)
            throw r1
        L_0x0028:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r4 = r4.getName()
            java.lang.String r1 = "Unsupported message type: "
            java.lang.String r4 = r1.concat(r4)
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o3.a(java.lang.Class):n6");
    }

    public final boolean b(Class<?> cls) {
        return n.class.isAssignableFrom(cls);
    }
}
