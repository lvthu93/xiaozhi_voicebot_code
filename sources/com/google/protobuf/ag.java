package com.google.protobuf;

import java.io.IOException;

public abstract class ag<T, B> {
    public abstract void a(int i, int i2, Object obj);

    public abstract void b(int i, long j, Object obj);

    public abstract void c(int i, Object obj, Object obj2);

    public abstract void d(B b, int i, cp cpVar);

    public abstract void e(int i, long j, Object obj);

    public abstract ah f(Object obj);

    public abstract ah g(Object obj);

    public abstract int h(T t);

    public abstract int i(T t);

    public abstract void j(Object obj);

    public abstract ah k(Object obj, Object obj2);

    /* JADX WARNING: Removed duplicated region for block: B:20:0x003e A[LOOP:0: B:20:0x003e->B:23:0x004b, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean l(int r7, defpackage.y9 r8, java.lang.Object r9) throws java.io.IOException {
        /*
            r6 = this;
            com.google.protobuf.g r8 = (com.google.protobuf.g) r8
            int r0 = r8.b
            int r1 = r0 >>> 3
            r0 = r0 & 7
            r2 = 1
            if (r0 == 0) goto L_0x0076
            if (r0 == r2) goto L_0x006e
            r3 = 2
            if (r0 == r3) goto L_0x0066
            r3 = 3
            r4 = 4
            if (r0 == r3) goto L_0x0032
            if (r0 == r4) goto L_0x0029
            r7 = 5
            if (r0 != r7) goto L_0x0021
            int r7 = r8.i()
            r6.a(r1, r7, r9)
            return r2
        L_0x0021:
            int r7 = com.google.protobuf.q.f
            com.google.protobuf.q$a r7 = new com.google.protobuf.q$a
            r7.<init>()
            throw r7
        L_0x0029:
            if (r7 == 0) goto L_0x002d
            r7 = 0
            return r7
        L_0x002d:
            com.google.protobuf.q r7 = com.google.protobuf.q.a()
            throw r7
        L_0x0032:
            com.google.protobuf.ah r0 = r6.m()
            int r3 = r1 << 3
            r3 = r3 | r4
            int r7 = r7 + r2
            r4 = 100
            if (r7 >= r4) goto L_0x005e
        L_0x003e:
            int r4 = r8.a()
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 == r5) goto L_0x004d
            boolean r4 = r6.l(r7, r8, r0)
            if (r4 != 0) goto L_0x003e
        L_0x004d:
            int r7 = r8.b
            if (r3 != r7) goto L_0x0059
            com.google.protobuf.ah r7 = r6.q(r0)
            r6.c(r1, r9, r7)
            return r2
        L_0x0059:
            com.google.protobuf.q r7 = com.google.protobuf.q.a()
            throw r7
        L_0x005e:
            com.google.protobuf.q r7 = new com.google.protobuf.q
            java.lang.String r8 = "Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit."
            r7.<init>((java.lang.String) r8)
            throw r7
        L_0x0066:
            cp r7 = r8.e()
            r6.d(r9, r1, r7)
            return r2
        L_0x006e:
            long r7 = r8.k()
            r6.b(r1, r7, r9)
            return r2
        L_0x0076:
            long r7 = r8.p()
            r6.e(r1, r7, r9)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.ag.l(int, y9, java.lang.Object):boolean");
    }

    public abstract ah m();

    public abstract void n(Object obj, B b);

    public abstract void o(Object obj, T t);

    public abstract void p();

    public abstract ah q(Object obj);

    public abstract void r(T t, jf jfVar) throws IOException;

    public abstract void s(T t, jf jfVar) throws IOException;
}
