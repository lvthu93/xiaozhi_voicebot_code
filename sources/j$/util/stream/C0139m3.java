package j$.util.stream;

import j$.util.C0057b;
import j$.util.U;
import java.util.Comparator;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.m3  reason: case insensitive filesystem */
abstract class C0139m3 implements U {
    final boolean a;
    final D0 b;
    private Supplier c;
    U d;
    C0182v2 e;
    C0075a f;
    long g;
    C0095e h;
    boolean i;

    C0139m3(D0 d0, U u, boolean z) {
        this.b = d0;
        this.c = null;
        this.d = u;
        this.a = z;
    }

    C0139m3(D0 d0, C0075a aVar, boolean z) {
        this.b = d0;
        this.c = aVar;
        this.d = null;
        this.a = z;
    }

    private boolean b() {
        while (this.h.count() == 0) {
            if (this.e.e() || !this.f.a()) {
                if (this.i) {
                    return false;
                }
                this.e.end();
                this.i = true;
            }
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final boolean a() {
        C0095e eVar = this.h;
        boolean z = false;
        if (eVar != null) {
            long j = this.g + 1;
            this.g = j;
            if (j < eVar.count()) {
                z = true;
            }
            if (z) {
                return z;
            }
            this.g = 0;
            this.h.clear();
            return b();
        } else if (this.i) {
            return false;
        } else {
            c();
            d();
            this.g = 0;
            this.e.c(this.d.getExactSizeIfKnown());
            return b();
        }
    }

    /* access modifiers changed from: package-private */
    public final void c() {
        if (this.d == null) {
            this.d = (U) this.c.get();
            this.c = null;
        }
    }

    public final int characteristics() {
        c();
        int Q = C0129k3.Q(this.b.s0()) & C0129k3.f;
        return (Q & 64) != 0 ? (Q & -16449) | (this.d.characteristics() & 16448) : Q;
    }

    /* access modifiers changed from: package-private */
    public abstract void d();

    /* access modifiers changed from: package-private */
    public abstract C0139m3 e(U u);

    public final long estimateSize() {
        c();
        return this.d.estimateSize();
    }

    public final Comparator getComparator() {
        if (C0057b.e(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }

    public final long getExactSizeIfKnown() {
        c();
        if (C0129k3.SIZED.p(this.b.s0())) {
            return this.d.getExactSizeIfKnown();
        }
        return -1;
    }

    public final /* synthetic */ boolean hasCharacteristics(int i2) {
        return C0057b.e(this, i2);
    }

    public final String toString() {
        return String.format("%s[%s]", new Object[]{getClass().getName(), this.d});
    }

    public U trySplit() {
        if (!this.a || this.h != null || this.i) {
            return null;
        }
        c();
        U trySplit = this.d.trySplit();
        if (trySplit == null) {
            return null;
        }
        return e(trySplit);
    }
}
