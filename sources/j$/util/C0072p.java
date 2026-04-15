package j$.util;

import java.util.NoSuchElementException;

/* renamed from: j$.util.p  reason: case insensitive filesystem */
public final class C0072p {
    private static final C0072p c = new C0072p();
    private final boolean a;
    private final int b;

    private C0072p() {
        this.a = false;
        this.b = 0;
    }

    private C0072p(int i) {
        this.a = true;
        this.b = i;
    }

    public static C0072p a() {
        return c;
    }

    public static C0072p d(int i) {
        return new C0072p(i);
    }

    public final int b() {
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
        if (!(obj instanceof C0072p)) {
            return false;
        }
        C0072p pVar = (C0072p) obj;
        boolean z = this.a;
        if (!z || !pVar.a) {
            if (z == pVar.a) {
                return true;
            }
        } else if (this.b == pVar.b) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        if (this.a) {
            return this.b;
        }
        return 0;
    }

    public final String toString() {
        if (!this.a) {
            return "OptionalInt.empty";
        }
        return String.format("OptionalInt[%s]", new Object[]{Integer.valueOf(this.b)});
    }
}
