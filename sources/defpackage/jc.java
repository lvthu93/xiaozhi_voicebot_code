package defpackage;

/* renamed from: jc  reason: default package */
public final class jc {
    public final int a;
    public final int b;
    public final int c;
    public final int[] d;
    public int[] e = new int[0];
    public boolean f = false;
    public boolean g = false;

    public jc(int[] iArr, int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        int[] iArr2 = new int[iArr.length];
        this.d = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
    }

    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00f0 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean c(int[] r11, int[] r12, int r13, defpackage.s0 r14) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
        L_0x0003:
            if (r1 >= r13) goto L_0x00f4
            r3 = r11[r1]
            r4 = r12[r1]
            r5 = r3 & 255(0xff, float:3.57E-43)
            r6 = r4 & 255(0xff, float:3.57E-43)
            r7 = 1
            r8 = 7
            if (r5 != r8) goto L_0x0013
            r9 = 1
            goto L_0x0014
        L_0x0013:
            r9 = 0
        L_0x0014:
            if (r6 != r8) goto L_0x0018
            r8 = 1
            goto L_0x0019
        L_0x0018:
            r8 = 0
        L_0x0019:
            if (r3 == r4) goto L_0x00ea
            r10 = 5
            if (r9 == 0) goto L_0x0022
            if (r4 != r10) goto L_0x0022
            goto L_0x00ea
        L_0x0022:
            if (r5 == 0) goto L_0x00e8
            if (r6 != 0) goto L_0x0028
            goto L_0x00e8
        L_0x0028:
            if (r3 != r10) goto L_0x002e
            if (r8 == 0) goto L_0x002e
            goto L_0x00eb
        L_0x002e:
            if (r9 == 0) goto L_0x00c4
            if (r8 == 0) goto L_0x00c4
            java.lang.String r5 = defpackage.cg.j(r3, r14)
            java.lang.String r6 = defpackage.cg.j(r4, r14)
            r8 = 2
            java.lang.Object r8 = r14.e(r8)
            java.lang.String r8 = (java.lang.String) r8
            r9 = 4
            java.lang.Object r9 = r14.e(r9)
            java.lang.String r9 = (java.lang.String) r9
            boolean r10 = r5.equals(r8)
            if (r10 == 0) goto L_0x004f
            r5 = r9
        L_0x004f:
            boolean r8 = r6.equals(r8)
            if (r8 == 0) goto L_0x0056
            r6 = r9
        L_0x0056:
            r8 = 46
            r9 = 47
            java.lang.String r5 = r5.replace(r9, r8)     // Catch:{ ClassNotFoundException -> 0x00bd }
            java.lang.Class r5 = java.lang.Class.forName(r5)     // Catch:{ ClassNotFoundException -> 0x00bd }
            java.lang.String r6 = r6.replace(r9, r8)     // Catch:{ ClassNotFoundException -> 0x00b6 }
            java.lang.Class r6 = java.lang.Class.forName(r6)     // Catch:{ ClassNotFoundException -> 0x00b6 }
            boolean r10 = r5.isAssignableFrom(r6)
            if (r10 == 0) goto L_0x0072
            goto L_0x00ea
        L_0x0072:
            boolean r10 = r6.isAssignableFrom(r5)
            if (r10 == 0) goto L_0x007a
            goto L_0x00eb
        L_0x007a:
            boolean r10 = r6.isInterface()
            if (r10 != 0) goto L_0x00ab
            boolean r10 = r5.isInterface()
            if (r10 == 0) goto L_0x0087
            goto L_0x00ab
        L_0x0087:
            java.lang.Class r6 = r6.getSuperclass()
        L_0x008b:
            if (r6 == 0) goto L_0x00c4
            boolean r10 = r6.isAssignableFrom(r5)
            if (r10 == 0) goto L_0x00a6
            java.lang.String r4 = r6.getName()
            int r5 = defpackage.l0.ac
            java.lang.String r4 = r4.replace(r8, r9)
            short r4 = r14.a(r4)
            int r4 = defpackage.cg.b(r4)
            goto L_0x00eb
        L_0x00a6:
            java.lang.Class r6 = r6.getSuperclass()
            goto L_0x008b
        L_0x00ab:
            java.lang.String r4 = "java/lang/Object"
            short r4 = r14.a(r4)
            int r4 = defpackage.cg.b(r4)
            goto L_0x00eb
        L_0x00b6:
            r11 = move-exception
            java.lang.RuntimeException r12 = new java.lang.RuntimeException
            r12.<init>(r11)
            throw r12
        L_0x00bd:
            r11 = move-exception
            java.lang.RuntimeException r12 = new java.lang.RuntimeException
            r12.<init>(r11)
            throw r12
        L_0x00c4:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "bad merge attempt between "
            r12.<init>(r13)
            java.lang.String r13 = defpackage.cg.m(r3, r14)
            r12.append(r13)
            java.lang.String r13 = " and "
            r12.append(r13)
            java.lang.String r13 = defpackage.cg.m(r4, r14)
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            r11.<init>(r12)
            throw r11
        L_0x00e8:
            r4 = 0
            goto L_0x00eb
        L_0x00ea:
            r4 = r3
        L_0x00eb:
            r11[r1] = r4
            if (r3 == r4) goto L_0x00f0
            r2 = 1
        L_0x00f0:
            int r1 = r1 + 1
            goto L_0x0003
        L_0x00f4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.jc.c(int[], int[], int, s0):boolean");
    }

    public final int[] a() {
        int[] iArr = this.d;
        int length = iArr.length - 1;
        while (length >= 0 && iArr[length] == 0 && !cg.k(iArr[length - 1])) {
            length--;
        }
        int i = length + 1;
        int i2 = 0;
        int i3 = i;
        for (int i4 = 0; i4 < i; i4++) {
            if (cg.k(iArr[i4])) {
                i3--;
            }
        }
        int[] iArr2 = new int[i3];
        int i5 = 0;
        while (i2 < i3) {
            iArr2[i2] = iArr[i5];
            if (cg.k(iArr[i5])) {
                i5++;
            }
            i2++;
            i5++;
        }
        return iArr2;
    }

    public final boolean b(int[] iArr, int i, int[] iArr2, int i2, s0 s0Var) {
        boolean z = this.f;
        int[] iArr3 = this.d;
        if (!z) {
            System.arraycopy(iArr, 0, iArr3, 0, i);
            int[] iArr4 = new int[i2];
            this.e = iArr4;
            System.arraycopy(iArr2, 0, iArr4, 0, i2);
            this.f = true;
            return true;
        } else if (iArr3.length == i && this.e.length == i2) {
            boolean c2 = c(iArr3, iArr, i, s0Var);
            boolean c3 = c(this.e, iArr2, i2, s0Var);
            if (c2 || c3) {
                return true;
            }
            return false;
        } else {
            throw new IllegalArgumentException("bad merge attempt");
        }
    }

    public final String toString() {
        return "sb " + this.a;
    }
}
