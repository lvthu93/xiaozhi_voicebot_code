package j$.time.format;

import j$.time.chrono.m;
import j$.time.chrono.t;
import j$.time.temporal.p;
import j$.time.temporal.q;

final class s implements C0054g {
    private final q a;
    private final G b;
    private final B c;
    private volatile k d;

    s(q qVar, G g, B b2) {
        this.a = qVar;
        this.b = g;
        this.c = b2;
    }

    public final boolean k(z zVar, StringBuilder sb) {
        Long e = zVar.e(this.a);
        if (e == null) {
            return false;
        }
        m mVar = (m) zVar.d().H(p.e());
        String f = (mVar == null || mVar == t.d) ? this.c.f(this.a, e.longValue(), this.b, zVar.c()) : this.c.e(mVar, this.a, e.longValue(), this.b, zVar.c());
        if (f == null) {
            if (this.d == null) {
                this.d = new k(this.a, 1, 19, F.NORMAL);
            }
            return this.d.k(zVar, sb);
        }
        sb.append(f);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0075, code lost:
        return r14.o(r2, r3, r16, r1 + r7);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int l(j$.time.format.x r14, java.lang.CharSequence r15, int r16) {
        /*
            r13 = this;
            r0 = r13
            r7 = r16
            int r1 = r15.length()
            if (r7 < 0) goto L_0x00d8
            if (r7 > r1) goto L_0x00d8
            boolean r1 = r14.l()
            if (r1 == 0) goto L_0x0014
            j$.time.format.G r1 = r0.b
            goto L_0x0015
        L_0x0014:
            r1 = 0
        L_0x0015:
            j$.time.chrono.m r8 = r14.h()
            j$.time.format.B r2 = r0.c
            j$.time.temporal.q r9 = r0.a
            if (r8 == 0) goto L_0x002d
            j$.time.chrono.t r3 = j$.time.chrono.t.d
            if (r8 != r3) goto L_0x0024
            goto L_0x002d
        L_0x0024:
            java.util.Locale r3 = r14.i()
            java.util.Iterator r1 = r2.g(r8, r9, r1, r3)
            goto L_0x0035
        L_0x002d:
            java.util.Locale r3 = r14.i()
            java.util.Iterator r1 = r2.h(r9, r1, r3)
        L_0x0035:
            r10 = r1
            if (r10 == 0) goto L_0x00bd
        L_0x0038:
            boolean r1 = r10.hasNext()
            if (r1 == 0) goto L_0x0076
            java.lang.Object r1 = r10.next()
            r11 = r1
            java.util.Map$Entry r11 = (java.util.Map.Entry) r11
            java.lang.Object r1 = r11.getKey()
            r12 = r1
            java.lang.String r12 = (java.lang.String) r12
            r3 = 0
            int r6 = r12.length()
            r1 = r14
            r2 = r12
            r4 = r15
            r5 = r16
            boolean r1 = r1.s(r2, r3, r4, r5, r6)
            if (r1 == 0) goto L_0x0038
            j$.time.temporal.q r2 = r0.a
            java.lang.Object r1 = r11.getValue()
            java.lang.Long r1 = (java.lang.Long) r1
            long r3 = r1.longValue()
            int r1 = r12.length()
        L_0x006c:
            int r6 = r1 + r7
            r1 = r14
            r5 = r16
            int r1 = r1.o(r2, r3, r5, r6)
            return r1
        L_0x0076:
            j$.time.temporal.a r1 = j$.time.temporal.a.ERA
            if (r9 != r1) goto L_0x00b5
            boolean r1 = r14.l()
            if (r1 != 0) goto L_0x00b5
            java.util.List r1 = r8.t()
            java.util.Iterator r8 = r1.iterator()
        L_0x0088:
            boolean r1 = r8.hasNext()
            if (r1 == 0) goto L_0x00b5
            java.lang.Object r1 = r8.next()
            r9 = r1
            j$.time.chrono.n r9 = (j$.time.chrono.n) r9
            java.lang.String r10 = r9.toString()
            r3 = 0
            int r6 = r10.length()
            r1 = r14
            r2 = r10
            r4 = r15
            r5 = r16
            boolean r1 = r1.s(r2, r3, r4, r5, r6)
            if (r1 == 0) goto L_0x0088
            j$.time.temporal.q r2 = r0.a
            int r1 = r9.getValue()
            long r3 = (long) r1
            int r1 = r10.length()
            goto L_0x006c
        L_0x00b5:
            boolean r1 = r14.l()
            if (r1 == 0) goto L_0x00bd
            int r1 = ~r7
            return r1
        L_0x00bd:
            j$.time.format.k r1 = r0.d
            if (r1 != 0) goto L_0x00cf
            j$.time.format.k r1 = new j$.time.format.k
            j$.time.temporal.q r2 = r0.a
            j$.time.format.F r3 = j$.time.format.F.NORMAL
            r4 = 1
            r5 = 19
            r1.<init>(r2, r4, r5, r3)
            r0.d = r1
        L_0x00cf:
            j$.time.format.k r1 = r0.d
            r2 = r14
            r3 = r15
            int r1 = r1.l(r14, r15, r7)
            return r1
        L_0x00d8:
            java.lang.IndexOutOfBoundsException r1 = new java.lang.IndexOutOfBoundsException
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.format.s.l(j$.time.format.x, java.lang.CharSequence, int):int");
    }

    public final String toString() {
        G g = G.FULL;
        q qVar = this.a;
        G g2 = this.b;
        if (g2 == g) {
            return "Text(" + qVar + ")";
        }
        return "Text(" + qVar + "," + g2 + ")";
    }
}
