package j$.util.stream;

import j$.util.U;
import java.util.function.IntFunction;

final class F2 extends C0090d {
    private final C0085c j;
    private final IntFunction k;
    private final long l;
    private final long m;
    private long n;
    private volatile boolean o;

    F2(F2 f2, U u) {
        super((C0090d) f2, u);
        this.j = f2.j;
        this.k = f2.k;
        this.l = f2.l;
        this.m = f2.m;
    }

    F2(C0085c cVar, C0085c cVar2, U u, IntFunction intFunction, long j2, long j3) {
        super((D0) cVar2, u);
        this.j = cVar;
        this.k = intFunction;
        this.l = j2;
        this.m = j3;
    }

    private long k(long j2) {
        if (this.o) {
            return this.n;
        }
        F2 f2 = (F2) this.d;
        F2 f22 = (F2) this.e;
        if (f2 == null || f22 == null) {
            return this.n;
        }
        long k2 = f2.k(j2);
        return k2 >= j2 ? k2 : k2 + f22.k(j2);
    }

    /* access modifiers changed from: protected */
    public final Object a() {
        long j2 = -1;
        if (d() == null) {
            if (C0129k3.SIZED.E(this.j.j)) {
                j2 = this.j.k0(this.b);
            }
            H0 D0 = this.j.D0(j2, this.k);
            C0182v2 W0 = this.j.W0(this.a.s0(), D0);
            D0 d0 = this.a;
            d0.g0(this.b, d0.J0(W0));
            return D0.build();
        }
        H0 D02 = this.j.D0(-1, this.k);
        if (this.l == 0) {
            C0182v2 W02 = this.j.W0(this.a.s0(), D02);
            D0 d02 = this.a;
            d02.g0(this.b, d02.J0(W02));
        } else {
            this.a.I0(this.b, D02);
        }
        M0 build = D02.build();
        this.n = build.count();
        this.o = true;
        this.b = null;
        return build;
    }

    /* access modifiers changed from: protected */
    public final C0100f e(U u) {
        return new F2(this, u);
    }

    /* access modifiers changed from: protected */
    public final void h() {
        this.i = true;
        if (this.o) {
            f(j());
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: l */
    public final C0112h1 j() {
        return D0.j0(this.j.P0());
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onCompletion(java.util.concurrent.CountedCompleter r15) {
        /*
            r14 = this;
            j$.util.stream.f r0 = r14.d
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0008
            r3 = 1
            goto L_0x0009
        L_0x0008:
            r3 = 0
        L_0x0009:
            r4 = 0
            if (r3 != 0) goto L_0x0090
            j$.util.stream.F2 r0 = (j$.util.stream.F2) r0
            long r6 = r0.n
            j$.util.stream.f r0 = r14.e
            j$.util.stream.F2 r0 = (j$.util.stream.F2) r0
            long r8 = r0.n
            long r6 = r6 + r8
            r14.n = r6
            boolean r0 = r14.i
            if (r0 == 0) goto L_0x0021
            r14.n = r4
            goto L_0x0027
        L_0x0021:
            long r6 = r14.n
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x002d
        L_0x0027:
            j$.util.stream.h1 r0 = r14.j()
        L_0x002b:
            r6 = r0
            goto L_0x0061
        L_0x002d:
            j$.util.stream.f r0 = r14.d
            j$.util.stream.F2 r0 = (j$.util.stream.F2) r0
            long r6 = r0.n
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x0042
            j$.util.stream.f r0 = r14.e
            j$.util.stream.F2 r0 = (j$.util.stream.F2) r0
            java.lang.Object r0 = r0.c()
            j$.util.stream.M0 r0 = (j$.util.stream.M0) r0
            goto L_0x002b
        L_0x0042:
            j$.util.stream.c r0 = r14.j
            j$.util.stream.l3 r0 = r0.P0()
            j$.util.stream.f r3 = r14.d
            j$.util.stream.F2 r3 = (j$.util.stream.F2) r3
            java.lang.Object r3 = r3.c()
            j$.util.stream.M0 r3 = (j$.util.stream.M0) r3
            j$.util.stream.f r6 = r14.e
            j$.util.stream.F2 r6 = (j$.util.stream.F2) r6
            java.lang.Object r6 = r6.c()
            j$.util.stream.M0 r6 = (j$.util.stream.M0) r6
            j$.util.stream.O0 r0 = j$.util.stream.D0.e0(r0, r3, r6)
            goto L_0x002b
        L_0x0061:
            j$.util.stream.f r0 = r14.d()
            if (r0 != 0) goto L_0x0069
            r0 = 1
            goto L_0x006a
        L_0x0069:
            r0 = 0
        L_0x006a:
            if (r0 == 0) goto L_0x008b
            long r7 = r14.m
            int r0 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r0 < 0) goto L_0x0080
            long r7 = r6.count()
            long r9 = r14.l
            long r11 = r14.m
            long r9 = r9 + r11
            long r7 = java.lang.Math.min(r7, r9)
            goto L_0x0082
        L_0x0080:
            long r7 = r14.n
        L_0x0082:
            r9 = r7
            long r7 = r14.l
            java.util.function.IntFunction r11 = r14.k
            j$.util.stream.M0 r6 = r6.i(r7, r9, r11)
        L_0x008b:
            r14.f(r6)
            r14.o = r1
        L_0x0090:
            long r6 = r14.m
            int r0 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r0 < 0) goto L_0x00e8
            j$.util.stream.f r0 = r14.d()
            if (r0 != 0) goto L_0x009e
            r0 = 1
            goto L_0x009f
        L_0x009e:
            r0 = 0
        L_0x009f:
            if (r0 != 0) goto L_0x00e8
            long r3 = r14.l
            long r5 = r14.m
            long r3 = r3 + r5
            boolean r0 = r14.o
            if (r0 == 0) goto L_0x00ad
            long r5 = r14.n
            goto L_0x00b1
        L_0x00ad:
            long r5 = r14.k(r3)
        L_0x00b1:
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 < 0) goto L_0x00b6
            goto L_0x00e3
        L_0x00b6:
            j$.util.stream.f r0 = r14.d()
            j$.util.stream.F2 r0 = (j$.util.stream.F2) r0
            r7 = r14
        L_0x00bd:
            if (r0 == 0) goto L_0x00dd
            j$.util.stream.f r8 = r0.e
            if (r7 != r8) goto L_0x00d3
            j$.util.stream.f r7 = r0.d
            j$.util.stream.F2 r7 = (j$.util.stream.F2) r7
            if (r7 == 0) goto L_0x00d3
            long r7 = r7.k(r3)
            long r5 = r5 + r7
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 < 0) goto L_0x00d3
            goto L_0x00e3
        L_0x00d3:
            j$.util.stream.f r7 = r0.d()
            j$.util.stream.F2 r7 = (j$.util.stream.F2) r7
            r13 = r7
            r7 = r0
            r0 = r13
            goto L_0x00bd
        L_0x00dd:
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 < 0) goto L_0x00e2
            goto L_0x00e3
        L_0x00e2:
            r1 = 0
        L_0x00e3:
            if (r1 == 0) goto L_0x00e8
            r14.i()
        L_0x00e8:
            super.onCompletion(r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.F2.onCompletion(java.util.concurrent.CountedCompleter):void");
    }
}
