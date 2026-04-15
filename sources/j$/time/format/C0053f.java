package j$.time.format;

import java.util.ArrayList;

/* renamed from: j$.time.format.f  reason: case insensitive filesystem */
final class C0053f implements C0054g {
    private final C0054g[] a;
    private final boolean b;

    C0053f(ArrayList arrayList, boolean z) {
        this((C0054g[]) arrayList.toArray(new C0054g[arrayList.size()]), z);
    }

    C0053f(C0054g[] gVarArr, boolean z) {
        this.a = gVarArr;
        this.b = z;
    }

    public final C0053f a() {
        return !this.b ? this : new C0053f(this.a, false);
    }

    public final boolean k(z zVar, StringBuilder sb) {
        int length = sb.length();
        boolean z = this.b;
        if (z) {
            zVar.g();
        }
        try {
            for (C0054g k : this.a) {
                if (!k.k(zVar, sb)) {
                    sb.setLength(length);
                    return true;
                }
            }
            if (z) {
                zVar.a();
            }
            return true;
        } finally {
            if (z) {
                zVar.a();
            }
        }
    }

    public final int l(x xVar, CharSequence charSequence, int i) {
        boolean z = this.b;
        C0054g[] gVarArr = this.a;
        if (z) {
            xVar.r();
            int i2 = i;
            for (C0054g l : gVarArr) {
                i2 = l.l(xVar, charSequence, i2);
                if (i2 < 0) {
                    xVar.f(false);
                    return i;
                }
            }
            xVar.f(true);
            return i2;
        }
        for (C0054g l2 : gVarArr) {
            i = l2.l(xVar, charSequence, i);
            if (i < 0) {
                break;
            }
        }
        return i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        C0054g[] gVarArr = this.a;
        if (gVarArr != null) {
            boolean z = this.b;
            sb.append(z ? "[" : "(");
            for (C0054g append : gVarArr) {
                sb.append(append);
            }
            sb.append(z ? "]" : ")");
        }
        return sb.toString();
    }
}
