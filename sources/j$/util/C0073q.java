package j$.util;

import java.util.NoSuchElementException;

/* renamed from: j$.util.q  reason: case insensitive filesystem */
public final class C0073q {
    private static final C0073q c = new C0073q();
    private final boolean a;
    private final long b;

    private C0073q() {
        this.a = false;
        this.b = 0;
    }

    private C0073q(long j) {
        this.a = true;
        this.b = j;
    }

    public static C0073q a() {
        return c;
    }

    public static C0073q d(long j) {
        return new C0073q(j);
    }

    public final long b() {
        if (this.a) {
            return this.b;
        }
        throw new NoSuchElementException("No value present");
    }

    public final boolean c() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C0073q)) {
            return false;
        }
        C0073q qVar = (C0073q) obj;
        boolean z = this.a;
        if (!z || !qVar.a) {
            if (z == qVar.a) {
                return true;
            }
        } else if (this.b == qVar.b) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        if (!this.a) {
            return 0;
        }
        long j = this.b;
        return (int) (j ^ (j >>> 32));
    }

    public final String toString() {
        if (!this.a) {
            return "OptionalLong.empty";
        }
        return String.format("OptionalLong[%s]", new Object[]{Long.valueOf(this.b)});
    }
}
