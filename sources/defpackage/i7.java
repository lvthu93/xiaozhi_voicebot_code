package defpackage;

/* renamed from: i7  reason: default package */
public final class i7 {
    public static final g7 a;
    public static final h7 b = new h7();

    static {
        g7 g7Var;
        Class<?> cls = bb.a;
        try {
            g7Var = (g7) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            g7Var = null;
        }
        a = g7Var;
    }
}
