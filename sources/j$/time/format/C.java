package j$.time.format;

import j$.util.concurrent.ConcurrentHashMap;

public final class C {
    public static final C a = new C();

    static {
        new ConcurrentHashMap(16, 0.75f, 2);
    }

    private C() {
    }

    /* access modifiers changed from: package-private */
    public final int a(char c) {
        int i = c - '0';
        if (i < 0 || i > 9) {
            return -1;
        }
        return i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C)) {
            return false;
        }
        ((C) obj).getClass();
        return true;
    }

    public final int hashCode() {
        return 182;
    }

    public final String toString() {
        return "DecimalStyle[0+-.]";
    }
}
