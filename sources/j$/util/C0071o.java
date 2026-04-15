package j$.util;

import java.util.NoSuchElementException;

/* renamed from: j$.util.o  reason: case insensitive filesystem */
public final class C0071o {
    private static final C0071o c = new C0071o();
    private final boolean a;
    private final double b;

    private C0071o() {
        this.a = false;
        this.b = Double.NaN;
    }

    private C0071o(double d) {
        this.a = true;
        this.b = d;
    }

    public static C0071o a() {
        return c;
    }

    public static C0071o d(double d) {
        return new C0071o(d);
    }

    public final double b() {
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
        if (!(obj instanceof C0071o)) {
            return false;
        }
        C0071o oVar = (C0071o) obj;
        boolean z = this.a;
        if (!z || !oVar.a) {
            if (z == oVar.a) {
                return true;
            }
        } else if (Double.compare(this.b, oVar.b) == 0) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        if (!this.a) {
            return 0;
        }
        long doubleToLongBits = Double.doubleToLongBits(this.b);
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }

    public final String toString() {
        if (!this.a) {
            return "OptionalDouble.empty";
        }
        return String.format("OptionalDouble[%s]", new Object[]{Double.valueOf(this.b)});
    }
}
