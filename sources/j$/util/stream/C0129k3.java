package j$.util.stream;

import j$.util.Map;
import j$.util.U;
import java.util.EnumMap;
import java.util.Map;

/* renamed from: j$.util.stream.k3  reason: case insensitive filesystem */
enum C0129k3 {
    DISTINCT(0, r2),
    SORTED(1, r6),
    ORDERED(2, r8),
    SIZED(3, r11),
    SHORT_CIRCUIT(12, r14);
    
    static final int f = 0;
    static final int g = 0;
    static final int h = 0;
    private static final int i = 0;
    private static final int j = 0;
    private static final int k = 0;
    static final int l = 0;
    static final int m = 0;
    static final int n = 0;
    static final int o = 0;
    static final int p = 0;
    static final int q = 0;
    static final int r = 0;
    static final int s = 0;
    static final int t = 0;
    static final int u = 0;
    private final Map a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    static {
        C0124j3 j3Var;
        C0124j3 j3Var2;
        C0124j3 j3Var3;
        int i2;
        C0124j3 j3Var4;
        C0124j3 j3Var5;
        f = l(j3Var);
        g = l(j3Var2);
        h = l(j3Var3);
        l(j3Var4);
        l(j3Var5);
        int i3 = 0;
        for (C0129k3 k3Var : values()) {
            i3 |= k3Var.e;
        }
        i = i3;
        int i4 = g;
        j = i4;
        int i5 = i4 << 1;
        k = i5;
        l = i4 | i5;
        C0129k3 k3Var2 = DISTINCT;
        m = k3Var2.c;
        n = k3Var2.d;
        C0129k3 k3Var3 = SORTED;
        o = k3Var3.c;
        p = k3Var3.d;
        C0129k3 k3Var4 = ORDERED;
        q = k3Var4.c;
        r = k3Var4.d;
        C0129k3 k3Var5 = SIZED;
        s = k3Var5.c;
        t = k3Var5.d;
        u = SHORT_CIRCUIT.c;
    }

    private C0129k3(int i2, C0119i3 i3Var) {
        C0124j3[] values = C0124j3.values();
        int length = values.length;
        int i3 = 0;
        while (true) {
            Map map = i3Var.a;
            if (i3 < length) {
                Map.EL.putIfAbsent(map, values[i3], 0);
                i3++;
            } else {
                this.a = map;
                int i4 = i2 * 2;
                this.b = i4;
                this.c = 1 << i4;
                this.d = 2 << i4;
                this.e = 3 << i4;
                return;
            }
        }
    }

    private static C0119i3 H(C0124j3 j3Var) {
        C0119i3 i3Var = new C0119i3(new EnumMap(C0124j3.class));
        i3Var.a(j3Var);
        return i3Var;
    }

    static int Q(int i2) {
        return i2 & ((~i2) >> 1) & j;
    }

    static int k(int i2, int i3) {
        return i2 | (i3 & (i2 == 0 ? i : ~(((j & i2) << 1) | i2 | ((k & i2) >> 1))));
    }

    private static int l(C0124j3 j3Var) {
        int i2 = 0;
        for (C0129k3 k3Var : values()) {
            i2 |= ((Integer) k3Var.a.get(j3Var)).intValue() << k3Var.b;
        }
        return i2;
    }

    static int m(U u2) {
        int characteristics = u2.characteristics();
        int i2 = characteristics & 4;
        int i3 = f;
        return (i2 == 0 || u2.getComparator() == null) ? characteristics & i3 : characteristics & i3 & -5;
    }

    /* access modifiers changed from: package-private */
    public final boolean E(int i2) {
        int i3 = this.e;
        return (i2 & i3) == i3;
    }

    /* access modifiers changed from: package-private */
    public final boolean p(int i2) {
        return (i2 & this.e) == this.c;
    }
}
