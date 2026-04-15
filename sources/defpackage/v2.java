package defpackage;

import com.google.protobuf.j;
import com.google.protobuf.k;

/* renamed from: v2  reason: default package */
public final class v2 {
    public static final k a = new k();
    public static final j<?> b;

    static {
        j<?> jVar;
        Class<?> cls = bb.a;
        try {
            jVar = (j) Class.forName("com.google.protobuf.ExtensionSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            jVar = null;
        }
        b = jVar;
    }
}
