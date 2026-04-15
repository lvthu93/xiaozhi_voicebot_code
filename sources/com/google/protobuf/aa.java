package com.google.protobuf;

import com.google.protobuf.l;
import com.google.protobuf.n;
import com.google.protobuf.r;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public final class aa<T> implements ac<T> {
    public final x a;
    public final ag<?, ?> b;
    public final boolean c;
    public final j<?> d;

    public aa(ag<?, ?> agVar, j<?> jVar, x xVar) {
        this.b = agVar;
        this.c = jVar.e(xVar);
        this.d = jVar;
        this.a = xVar;
    }

    public final void a(T t, T t2) {
        Class<?> cls = ad.a;
        ag<?, ?> agVar = this.b;
        agVar.o(t, agVar.k(agVar.g(t), agVar.g(t2)));
        if (this.c) {
            ad.ab(this.d, t, t2);
        }
    }

    public final void b(T t) {
        this.b.j(t);
        this.d.f(t);
    }

    public final boolean c(T t) {
        return this.d.c(t).i();
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00cf A[EDGE_INSN: B:48:0x00cf->B:36:0x00cf ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void d(T r17, byte[] r18, int r19, int r20, com.google.protobuf.d.a r21) throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            r7 = r18
            r8 = r20
            r9 = r21
            r1 = r17
            com.google.protobuf.n r1 = (com.google.protobuf.n) r1
            com.google.protobuf.ah r2 = r1.unknownFields
            com.google.protobuf.ah r3 = com.google.protobuf.ah.f
            if (r2 != r3) goto L_0x0019
            com.google.protobuf.ah r2 = new com.google.protobuf.ah
            r2.<init>()
            r1.unknownFields = r2
        L_0x0019:
            r10 = r2
            r1 = r17
            com.google.protobuf.n$c r1 = (com.google.protobuf.n.c) r1
            com.google.protobuf.l<com.google.protobuf.n$d> r2 = r1.c
            boolean r3 = r2.b
            if (r3 == 0) goto L_0x002a
            com.google.protobuf.l r2 = r2.clone()
            r1.c = r2
        L_0x002a:
            com.google.protobuf.l<com.google.protobuf.n$d> r11 = r1.c
            r1 = r19
            r2 = 0
        L_0x002f:
            if (r1 >= r8) goto L_0x00dd
            int r3 = com.google.protobuf.d.ai(r7, r1, r9)
            int r1 = r9.a
            r4 = 2
            r5 = 11
            com.google.protobuf.x r6 = r0.a
            com.google.protobuf.j<?> r13 = r0.d
            com.google.protobuf.i r14 = r9.d
            if (r1 == r5) goto L_0x0078
            r5 = r1 & 7
            if (r5 != r4) goto L_0x0073
            int r2 = r1 >>> 3
            com.google.protobuf.n$e r13 = r13.b(r14, r6, r2)
            if (r13 == 0) goto L_0x0066
            f9 r1 = defpackage.f9.c
            com.google.protobuf.x r2 = r13.c
            java.lang.Class r2 = r2.getClass()
            com.google.protobuf.ac r1 = r1.a(r2)
            int r1 = com.google.protobuf.d.p(r1, r7, r3, r8, r9)
            com.google.protobuf.n$d r2 = r13.d
            java.lang.Object r3 = r9.c
            r11.n(r2, r3)
            goto L_0x0071
        L_0x0066:
            r2 = r18
            r4 = r20
            r5 = r10
            r6 = r21
            int r1 = com.google.protobuf.d.ag(r1, r2, r3, r4, r5, r6)
        L_0x0071:
            r2 = r13
            goto L_0x002f
        L_0x0073:
            int r1 = com.google.protobuf.d.ao(r1, r7, r3, r8, r9)
            goto L_0x002f
        L_0x0078:
            r1 = 0
            r5 = 0
        L_0x007a:
            if (r3 >= r8) goto L_0x00cf
            int r3 = com.google.protobuf.d.ai(r7, r3, r9)
            int r15 = r9.a
            int r12 = r15 >>> 3
            r0 = r15 & 7
            if (r12 == r4) goto L_0x00b3
            r4 = 3
            if (r12 == r4) goto L_0x008c
            goto L_0x00c1
        L_0x008c:
            if (r2 == 0) goto L_0x00a6
            f9 r0 = defpackage.f9.c
            com.google.protobuf.x r4 = r2.c
            java.lang.Class r4 = r4.getClass()
            com.google.protobuf.ac r0 = r0.a(r4)
            int r0 = com.google.protobuf.d.p(r0, r7, r3, r8, r9)
            com.google.protobuf.n$d r3 = r2.d
            java.lang.Object r4 = r9.c
            r11.n(r3, r4)
            goto L_0x00bf
        L_0x00a6:
            r4 = 2
            if (r0 != r4) goto L_0x00c1
            int r0 = com.google.protobuf.d.b(r7, r3, r9)
            java.lang.Object r3 = r9.c
            r5 = r3
            cp r5 = (defpackage.cp) r5
            goto L_0x00bf
        L_0x00b3:
            if (r0 != 0) goto L_0x00c1
            int r0 = com.google.protobuf.d.ai(r7, r3, r9)
            int r1 = r9.a
            com.google.protobuf.n$e r2 = r13.b(r14, r6, r1)
        L_0x00bf:
            r3 = r0
            goto L_0x00cb
        L_0x00c1:
            r0 = 12
            if (r15 != r0) goto L_0x00c6
            goto L_0x00cf
        L_0x00c6:
            int r0 = com.google.protobuf.d.ao(r15, r7, r3, r8, r9)
            goto L_0x00bf
        L_0x00cb:
            r4 = 2
            r0 = r16
            goto L_0x007a
        L_0x00cf:
            if (r5 == 0) goto L_0x00d8
            int r0 = r1 << 3
            r1 = 2
            r0 = r0 | r1
            r10.f(r0, r5)
        L_0x00d8:
            r0 = r16
            r1 = r3
            goto L_0x002f
        L_0x00dd:
            if (r1 != r8) goto L_0x00e0
            return
        L_0x00e0:
            com.google.protobuf.q r0 = com.google.protobuf.q.g()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.aa.d(java.lang.Object, byte[], int, int, com.google.protobuf.d$a):void");
    }

    public final void e(T t, y9 y9Var, i iVar) throws IOException {
        ag<?, ?> agVar = this.b;
        ah f = agVar.f(t);
        j<?> jVar = this.d;
        l<?> d2 = jVar.d(t);
        while (true) {
            try {
                g gVar = (g) y9Var;
                if (gVar.a() != Integer.MAX_VALUE) {
                    if (!k(gVar, iVar, jVar, d2, agVar, f)) {
                        break;
                    }
                } else {
                    break;
                }
            } finally {
                agVar.n(t, f);
            }
        }
    }

    public final boolean f(T t, T t2) {
        ag<?, ?> agVar = this.b;
        if (!agVar.g(t).equals(agVar.g(t2))) {
            return false;
        }
        if (!this.c) {
            return true;
        }
        j<?> jVar = this.d;
        return jVar.c(t).equals(jVar.c(t2));
    }

    public final void g(T t, jf jfVar) throws IOException {
        Iterator<Map.Entry<?, Object>> k = this.d.c(t).k();
        while (k.hasNext()) {
            Map.Entry next = k.next();
            l.a aVar = (l.a) next.getKey();
            if (aVar.k() != Cif.n || aVar.c() || aVar.l()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (next instanceof r.a) {
                ((o0) jfVar).m(aVar.a(), ((r.a) next).c.getValue().c());
            } else {
                ((o0) jfVar).m(aVar.a(), next.getValue());
            }
        }
        ag<?, ?> agVar = this.b;
        agVar.r(agVar.g(t), jfVar);
    }

    public final int h(T t) {
        ag<?, ?> agVar = this.b;
        int i = agVar.i(agVar.g(t)) + 0;
        if (!this.c) {
            return i;
        }
        af<T, Object> afVar = this.d.c(t).a;
        int i2 = afVar.f;
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 += l.f(afVar.c(i4));
        }
        for (Map.Entry f : afVar.d()) {
            i3 += l.f(f);
        }
        return i + i3;
    }

    public final T i() {
        x xVar = this.a;
        if (xVar instanceof n) {
            return ((n) xVar).newMutableInstance();
        }
        return xVar.newBuilderForType().buildPartial();
    }

    public final int j(T t) {
        int hashCode = this.b.g(t).hashCode();
        if (this.c) {
            return (hashCode * 53) + this.d.c(t).hashCode();
        }
        return hashCode;
    }

    public final <UT, UB, ET extends l.a<ET>> boolean k(y9 y9Var, i iVar, j<ET> jVar, l<ET> lVar, ag<UT, UB> agVar, UB ub) throws IOException {
        g gVar = (g) y9Var;
        int i = gVar.b;
        x xVar = this.a;
        int i2 = 0;
        if (i == 11) {
            n.e eVar = null;
            cp cpVar = null;
            while (gVar.a() != Integer.MAX_VALUE) {
                int i3 = gVar.b;
                if (i3 != 16) {
                    if (i3 != 26) {
                        if (i3 == 12 || !gVar.ab()) {
                            break;
                        }
                    } else if (eVar != null) {
                        jVar.h(y9Var, eVar, iVar, lVar);
                    } else {
                        cpVar = gVar.e();
                    }
                } else {
                    i2 = gVar.w();
                    eVar = jVar.b(iVar, xVar, i2);
                }
            }
            if (gVar.b == 12) {
                if (cpVar != null) {
                    if (eVar != null) {
                        jVar.i(cpVar, eVar, iVar, lVar);
                    } else {
                        agVar.d(ub, i2, cpVar);
                    }
                }
                return true;
            }
            throw q.a();
        } else if ((i & 7) != 2) {
            return gVar.ab();
        } else {
            n.e b2 = jVar.b(iVar, xVar, i >>> 3);
            if (b2 == null) {
                return agVar.l(0, y9Var, ub);
            }
            jVar.h(y9Var, b2, iVar, lVar);
            return true;
        }
    }
}
