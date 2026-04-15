package j$.time;

import java.io.ObjectInputStream;
import java.io.Serializable;

final class a extends b implements Serializable {
    static final a b = new a(ZoneOffset.UTC);
    private static final long serialVersionUID = 6740630888130243051L;
    private final ZoneId a;

    static {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
    }

    a(ZoneId zoneId) {
        this.a = zoneId;
    }

    private void readObject(ObjectInputStream objectInputStream) {
        objectInputStream.defaultReadObject();
    }

    public final ZoneId a() {
        return this.a;
    }

    public final long b() {
        return System.currentTimeMillis();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof a)) {
            return false;
        }
        return this.a.equals(((a) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode() + 1;
    }

    public final String toString() {
        return "SystemClock[" + this.a + "]";
    }
}
