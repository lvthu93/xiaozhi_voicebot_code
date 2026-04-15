package j$.util.stream;

import j$.util.Objects;
import j$.util.U;
import java.util.function.IntFunction;

/* renamed from: j$.util.stream.c  reason: case insensitive filesystem */
abstract class C0085c extends D0 implements C0115i {
    private final C0085c h;
    private final C0085c i;
    protected final int j;
    private C0085c k;
    private int l;
    private int m;
    private U n;
    private boolean o;
    private boolean p;
    private Runnable q;
    private boolean r;

    C0085c(U u, int i2, boolean z) {
        this.i = null;
        this.n = u;
        this.h = this;
        int i3 = C0129k3.g & i2;
        this.j = i3;
        this.m = (~(i3 << 1)) & C0129k3.l;
        this.l = 0;
        this.r = z;
    }

    C0085c(C0085c cVar, int i2) {
        if (!cVar.o) {
            cVar.o = true;
            cVar.k = this;
            this.i = cVar;
            this.j = C0129k3.h & i2;
            this.m = C0129k3.k(i2, cVar.m);
            C0085c cVar2 = cVar.h;
            this.h = cVar2;
            if (V0()) {
                cVar2.p = true;
            }
            this.l = cVar.l + 1;
            return;
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    private U X0(int i2) {
        int i3;
        int i4;
        C0085c cVar = this.h;
        U u = cVar.n;
        if (u != null) {
            cVar.n = null;
            if (cVar.r && cVar.p) {
                C0085c cVar2 = cVar.k;
                int i5 = 1;
                while (cVar != this) {
                    int i6 = cVar2.j;
                    if (cVar2.V0()) {
                        if (C0129k3.SHORT_CIRCUIT.p(i6)) {
                            i6 &= ~C0129k3.u;
                        }
                        u = cVar2.U0(cVar, u);
                        if (u.hasCharacteristics(64)) {
                            i4 = (~C0129k3.t) & i6;
                            i3 = C0129k3.s;
                        } else {
                            i4 = (~C0129k3.s) & i6;
                            i3 = C0129k3.t;
                        }
                        i6 = i3 | i4;
                        i5 = 0;
                    }
                    cVar2.l = i5;
                    cVar2.m = C0129k3.k(i6, cVar.m);
                    i5++;
                    C0085c cVar3 = cVar2;
                    cVar2 = cVar2.k;
                    cVar = cVar3;
                }
            }
            if (i2 != 0) {
                this.m = C0129k3.k(i2, this.m);
            }
            return u;
        }
        throw new IllegalStateException("source already consumed or closed");
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 I0(U u, C0182v2 v2Var) {
        f0(u, J0((C0182v2) Objects.requireNonNull(v2Var)));
        return v2Var;
    }

    /* access modifiers changed from: package-private */
    public final C0182v2 J0(C0182v2 v2Var) {
        Objects.requireNonNull(v2Var);
        C0085c cVar = this;
        while (cVar.l > 0) {
            C0085c cVar2 = cVar.i;
            v2Var = cVar.W0(cVar2.m, v2Var);
            cVar = cVar2;
        }
        return v2Var;
    }

    /* access modifiers changed from: package-private */
    public final M0 K0(U u, boolean z, IntFunction intFunction) {
        if (this.h.r) {
            return N0(this, u, z, intFunction);
        }
        H0 D0 = D0(k0(u), intFunction);
        I0(u, D0);
        return D0.build();
    }

    /* access modifiers changed from: package-private */
    public final Object L0(U3 u3) {
        if (!this.o) {
            this.o = true;
            return this.h.r ? u3.k(this, X0(u3.o())) : u3.y(this, X0(u3.o()));
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    /* access modifiers changed from: package-private */
    public final M0 M0(IntFunction intFunction) {
        C0085c cVar;
        if (!this.o) {
            this.o = true;
            if (!this.h.r || (cVar = this.i) == null || !V0()) {
                return K0(X0(0), true, intFunction);
            }
            this.l = 0;
            return T0(cVar.X0(0), cVar, intFunction);
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    /* access modifiers changed from: package-private */
    public abstract M0 N0(D0 d0, U u, boolean z, IntFunction intFunction);

    /* access modifiers changed from: package-private */
    public abstract boolean O0(U u, C0182v2 v2Var);

    /* access modifiers changed from: package-private */
    public abstract C0134l3 P0();

    /* access modifiers changed from: package-private */
    public final C0134l3 Q0() {
        C0085c cVar = this;
        while (cVar.l > 0) {
            cVar = cVar.i;
        }
        return cVar.P0();
    }

    /* access modifiers changed from: package-private */
    public final boolean R0() {
        return C0129k3.ORDERED.p(this.m);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ U S0() {
        return X0(0);
    }

    /* access modifiers changed from: package-private */
    public M0 T0(U u, C0085c cVar, IntFunction intFunction) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    /* access modifiers changed from: package-private */
    public U U0(C0085c cVar, U u) {
        return T0(u, cVar, new C0080b(0)).spliterator();
    }

    /* access modifiers changed from: package-private */
    public abstract boolean V0();

    /* access modifiers changed from: package-private */
    public abstract C0182v2 W0(int i2, C0182v2 v2Var);

    /* access modifiers changed from: package-private */
    public final U Y0() {
        C0085c cVar = this.h;
        if (this != cVar) {
            throw new IllegalStateException();
        } else if (!this.o) {
            this.o = true;
            U u = cVar.n;
            if (u != null) {
                cVar.n = null;
                return u;
            }
            throw new IllegalStateException("source already consumed or closed");
        } else {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
    }

    /* access modifiers changed from: package-private */
    public abstract U Z0(D0 d0, C0075a aVar, boolean z);

    /* access modifiers changed from: package-private */
    public final U a1(U u) {
        return this.l == 0 ? u : Z0(this, new C0075a(u, 0), this.h.r);
    }

    public final void close() {
        this.o = true;
        this.n = null;
        C0085c cVar = this.h;
        Runnable runnable = cVar.q;
        if (runnable != null) {
            cVar.q = null;
            runnable.run();
        }
    }

    /* access modifiers changed from: package-private */
    public final void f0(U u, C0182v2 v2Var) {
        Objects.requireNonNull(v2Var);
        if (!C0129k3.SHORT_CIRCUIT.p(this.m)) {
            v2Var.c(u.getExactSizeIfKnown());
            u.forEachRemaining(v2Var);
            v2Var.end();
            return;
        }
        g0(u, v2Var);
    }

    /* access modifiers changed from: package-private */
    public final boolean g0(U u, C0182v2 v2Var) {
        C0085c cVar = this;
        while (cVar.l > 0) {
            cVar = cVar.i;
        }
        v2Var.c(u.getExactSizeIfKnown());
        boolean O0 = cVar.O0(u, v2Var);
        v2Var.end();
        return O0;
    }

    public final boolean isParallel() {
        return this.h.r;
    }

    /* access modifiers changed from: package-private */
    public final long k0(U u) {
        if (C0129k3.SIZED.p(this.m)) {
            return u.getExactSizeIfKnown();
        }
        return -1;
    }

    public final C0115i onClose(Runnable runnable) {
        if (!this.o) {
            Objects.requireNonNull(runnable);
            C0085c cVar = this.h;
            Runnable runnable2 = cVar.q;
            if (runnable2 != null) {
                runnable = new S3(runnable2, runnable);
            }
            cVar.q = runnable;
            return this;
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }

    public final C0115i parallel() {
        this.h.r = true;
        return this;
    }

    /* access modifiers changed from: package-private */
    public final int s0() {
        return this.m;
    }

    public final C0115i sequential() {
        this.h.r = false;
        return this;
    }

    public U spliterator() {
        if (!this.o) {
            this.o = true;
            C0085c cVar = this.h;
            if (this != cVar) {
                return Z0(this, new C0075a(this, 1), cVar.r);
            }
            U u = cVar.n;
            if (u != null) {
                cVar.n = null;
                return u;
            }
            throw new IllegalStateException("source already consumed or closed");
        }
        throw new IllegalStateException("stream has already been operated upon or closed");
    }
}
