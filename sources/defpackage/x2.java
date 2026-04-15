package defpackage;

/* renamed from: x2  reason: default package */
public final class x2 {
    public final String a;
    public final String b;
    public final String c;
    public int d = -1;

    public x2(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof x2)) {
            return false;
        }
        x2 x2Var = (x2) obj;
        if (!this.a.equals(x2Var.a) || !this.b.equals(x2Var.b) || !this.c.equals(x2Var.c)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        if (this.d == -1) {
            this.d = (this.a.hashCode() ^ this.b.hashCode()) ^ this.c.hashCode();
        }
        return this.d;
    }
}
