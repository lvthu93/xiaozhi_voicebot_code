package j$.time.temporal;

import j$.time.LocalDate;
import j$.time.d;

enum g implements q {
    ;
    
    /* access modifiers changed from: private */
    public static final int[] a = null;

    static {
        a = new int[]{0, 90, 181, 273, 0, 91, 182, 274};
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0056, code lost:
        if ((r0 == -3 || (r0 == -2 && r5.r())) == false) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int R(j$.time.LocalDate r5) {
        /*
            j$.time.d r0 = r5.U()
            int r0 = r0.ordinal()
            int r1 = r5.V()
            r2 = 1
            int r1 = r1 - r2
            int r0 = 3 - r0
            int r0 = r0 + r1
            int r3 = r0 / 7
            int r3 = r3 * 7
            int r0 = r0 - r3
            r3 = -3
            int r0 = r0 + r3
            if (r0 >= r3) goto L_0x001c
            int r0 = r0 + 7
        L_0x001c:
            if (r1 >= r0) goto L_0x003f
            r0 = 180(0xb4, float:2.52E-43)
            j$.time.LocalDate r5 = r5.n0(r0)
            r0 = -1
            j$.time.LocalDate r5 = r5.j0(r0)
            int r5 = V(r5)
            int r5 = W(r5)
            long r0 = (long) r5
            r2 = 1
            j$.time.temporal.u r5 = j$.time.temporal.u.j(r2, r0)
            long r0 = r5.d()
            int r5 = (int) r0
            goto L_0x005b
        L_0x003f:
            int r1 = r1 - r0
            int r1 = r1 / 7
            int r1 = r1 + r2
            r4 = 53
            if (r1 != r4) goto L_0x0059
            if (r0 == r3) goto L_0x0055
            r3 = -2
            if (r0 != r3) goto L_0x0053
            boolean r5 = r5.r()
            if (r5 == 0) goto L_0x0053
            goto L_0x0055
        L_0x0053:
            r5 = 0
            goto L_0x0056
        L_0x0055:
            r5 = 1
        L_0x0056:
            if (r5 != 0) goto L_0x0059
            goto L_0x005a
        L_0x0059:
            r2 = r1
        L_0x005a:
            r5 = r2
        L_0x005b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.temporal.g.R(j$.time.LocalDate):int");
    }

    static u U(LocalDate localDate) {
        return u.j(1, (long) W(V(localDate)));
    }

    /* access modifiers changed from: private */
    public static int V(LocalDate localDate) {
        int X = localDate.X();
        int V = localDate.V();
        if (V <= 3) {
            return V - localDate.U().ordinal() < -2 ? X - 1 : X;
        }
        if (V < 363) {
            return X;
        }
        return ((V - 363) - (localDate.r() ? 1 : 0)) - localDate.U().ordinal() >= 0 ? X + 1 : X;
    }

    /* access modifiers changed from: private */
    public static int W(int i) {
        LocalDate of = LocalDate.of(i, 1, 1);
        if (of.U() != d.THURSDAY) {
            return (of.U() != d.WEDNESDAY || !of.r()) ? 52 : 53;
        }
        return 53;
    }

    public final boolean isDateBased() {
        return true;
    }

    public final boolean isTimeBased() {
        return false;
    }
}
