package defpackage;

/* renamed from: sa  reason: default package */
public final class sa {
    public static qa a;
    public static long b;

    public static void a(qa qaVar) {
        if (qaVar.f != null || qaVar.g != null) {
            throw new IllegalArgumentException();
        } else if (!qaVar.d) {
            synchronized (sa.class) {
                long j = b + 8192;
                if (j <= 65536) {
                    b = j;
                    qaVar.f = a;
                    qaVar.c = 0;
                    qaVar.b = 0;
                    a = qaVar;
                }
            }
        }
    }

    public static qa b() {
        synchronized (sa.class) {
            qa qaVar = a;
            if (qaVar == null) {
                return new qa();
            }
            a = qaVar.f;
            qaVar.f = null;
            b -= 8192;
            return qaVar;
        }
    }
}
