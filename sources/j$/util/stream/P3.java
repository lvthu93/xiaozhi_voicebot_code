package j$.util.stream;

import j$.util.U;
import java.util.concurrent.atomic.AtomicLong;

abstract class P3 {
    protected final U a;
    protected final boolean b;
    protected final int c;
    private final long d;
    private final AtomicLong e;

    P3(U u, long j, long j2) {
        this.a = u;
        long j3 = 0;
        int i = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
        this.b = i < 0;
        this.d = i >= 0 ? j2 : j3;
        this.c = 128;
        this.e = new AtomicLong(i >= 0 ? j + j2 : j);
    }

    P3(U u, P3 p3) {
        this.a = u;
        this.b = p3.b;
        this.e = p3.e;
        this.d = p3.d;
        this.c = p3.c;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final long a(long r11) {
        /*
            r10 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicLong r0 = r10.e
            long r1 = r0.get()
            boolean r3 = r10.b
            r4 = 0
            int r6 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x0013
            if (r3 == 0) goto L_0x0011
            goto L_0x0012
        L_0x0011:
            r11 = r4
        L_0x0012:
            return r11
        L_0x0013:
            long r6 = java.lang.Math.min(r1, r11)
            int r8 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r8 <= 0) goto L_0x0023
            long r8 = r1 - r6
            boolean r0 = r0.compareAndSet(r1, r8)
            if (r0 == 0) goto L_0x0000
        L_0x0023:
            if (r3 == 0) goto L_0x002b
            long r11 = r11 - r6
            long r11 = java.lang.Math.max(r11, r4)
            return r11
        L_0x002b:
            long r11 = r10.d
            int r0 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r0 <= 0) goto L_0x0038
            long r1 = r1 - r11
            long r6 = r6 - r1
            long r11 = java.lang.Math.max(r6, r4)
            return r11
        L_0x0038:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.P3.a(long):long");
    }

    /* access modifiers changed from: protected */
    public abstract U b(U u);

    public final int characteristics() {
        return this.a.characteristics() & -16465;
    }

    public final long estimateSize() {
        return this.a.estimateSize();
    }

    /* access modifiers changed from: protected */
    public final O3 f() {
        return this.e.get() > 0 ? O3.MAYBE_MORE : this.b ? O3.UNLIMITED : O3.NO_MORE;
    }

    public final U trySplit() {
        U trySplit;
        if (this.e.get() == 0 || (trySplit = this.a.trySplit()) == null) {
            return null;
        }
        return b(trySplit);
    }
}
