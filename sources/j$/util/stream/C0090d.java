package j$.util.stream;

import j$.util.U;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: j$.util.stream.d  reason: case insensitive filesystem */
abstract class C0090d extends C0100f {
    protected final AtomicReference h;
    protected volatile boolean i;

    protected C0090d(D0 d0, U u) {
        super(d0, u);
        this.h = new AtomicReference((Object) null);
    }

    protected C0090d(C0090d dVar, U u) {
        super((C0100f) dVar, u);
        this.h = dVar.h;
    }

    public final Object c() {
        if (!(d() == null)) {
            return super.c();
        }
        Object obj = this.h.get();
        return obj == null ? j() : obj;
    }

    public final void compute() {
        Object obj;
        U trySplit;
        U u = this.b;
        long estimateSize = u.estimateSize();
        long j = this.c;
        if (j == 0) {
            j = C0100f.g(estimateSize);
            this.c = j;
        }
        AtomicReference atomicReference = this.h;
        boolean z = false;
        C0090d dVar = this;
        while (true) {
            obj = atomicReference.get();
            if (obj != null) {
                break;
            }
            boolean z2 = dVar.i;
            if (!z2) {
                C0100f d = dVar.d();
                while (true) {
                    C0090d dVar2 = (C0090d) d;
                    if (z2 || dVar2 == null) {
                        break;
                    }
                    z2 = dVar2.i;
                    d = dVar2.d();
                }
            }
            if (z2) {
                obj = dVar.j();
                break;
            } else if (estimateSize <= j || (trySplit = u.trySplit()) == null) {
                obj = dVar.a();
            } else {
                C0090d dVar3 = (C0090d) dVar.e(trySplit);
                dVar.d = dVar3;
                C0090d dVar4 = (C0090d) dVar.e(u);
                dVar.e = dVar4;
                dVar.setPendingCount(1);
                if (z) {
                    u = trySplit;
                    dVar = dVar3;
                    dVar3 = dVar4;
                } else {
                    dVar = dVar4;
                }
                z = !z;
                dVar3.fork();
                estimateSize = u.estimateSize();
            }
        }
        dVar.f(obj);
        dVar.tryComplete();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x000f A[LOOP:0: B:7:0x000f->B:10:0x001b, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void f(java.lang.Object r3) {
        /*
            r2 = this;
            j$.util.stream.f r0 = r2.d()
            if (r0 != 0) goto L_0x0008
            r0 = 1
            goto L_0x0009
        L_0x0008:
            r0 = 0
        L_0x0009:
            if (r0 == 0) goto L_0x001e
            if (r3 == 0) goto L_0x0021
            java.util.concurrent.atomic.AtomicReference r0 = r2.h
        L_0x000f:
            r1 = 0
            boolean r1 = r0.compareAndSet(r1, r3)
            if (r1 == 0) goto L_0x0017
            goto L_0x0021
        L_0x0017:
            java.lang.Object r1 = r0.get()
            if (r1 == 0) goto L_0x000f
            goto L_0x0021
        L_0x001e:
            super.f(r3)
        L_0x0021:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.C0090d.f(java.lang.Object):void");
    }

    public final Object getRawResult() {
        return c();
    }

    /* access modifiers changed from: protected */
    public void h() {
        this.i = true;
    }

    /* access modifiers changed from: protected */
    public final void i() {
        C0090d dVar = this;
        for (C0090d dVar2 = (C0090d) d(); dVar2 != null; dVar2 = (C0090d) dVar2.d()) {
            if (dVar2.d == dVar) {
                C0090d dVar3 = (C0090d) dVar2.e;
                if (!dVar3.i) {
                    dVar3.h();
                }
            }
            dVar = dVar2;
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object j();
}
