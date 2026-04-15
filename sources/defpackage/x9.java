package defpackage;

import com.google.protobuf.x;

/* renamed from: x9  reason: default package */
public final class x9 implements n6 {
    public final x a;
    public final String b;
    public final Object[] c;
    public final int d;

    public x9(x xVar, String str, Object[] objArr) {
        this.a = xVar;
        this.b = str;
        this.c = objArr;
        char charAt = str.charAt(0);
        if (charAt < 55296) {
            this.d = charAt;
            return;
        }
        char c2 = charAt & 8191;
        int i = 13;
        int i2 = 1;
        while (true) {
            int i3 = i2 + 1;
            char charAt2 = str.charAt(i2);
            if (charAt2 >= 55296) {
                c2 |= (charAt2 & 8191) << i;
                i += 13;
                i2 = i3;
            } else {
                this.d = c2 | (charAt2 << i);
                return;
            }
        }
    }

    public final boolean a() {
        return (this.d & 2) == 2;
    }

    public final x b() {
        return this.a;
    }

    public final int c() {
        int i = this.d;
        if ((i & 1) != 0) {
            return 1;
        }
        return (i & 4) == 4 ? 3 : 2;
    }

    public final Object[] d() {
        return this.c;
    }

    public final String e() {
        return this.b;
    }
}
