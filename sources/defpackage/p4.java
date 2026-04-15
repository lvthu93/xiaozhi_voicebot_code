package defpackage;

import com.google.protobuf.p;
import java.nio.charset.Charset;

/* renamed from: p4  reason: default package */
public final class p4 {
    public static final a b = new a();
    public final o6 a;

    /* renamed from: p4$a */
    public class a implements o6 {
        public final n6 a(Class<?> cls) {
            throw new IllegalStateException("This should never be called.");
        }

        public final boolean b(Class<?> cls) {
            return false;
        }
    }

    /* renamed from: p4$b */
    public static class b implements o6 {
        public final o6[] a;

        public b(o6... o6VarArr) {
            this.a = o6VarArr;
        }

        public final n6 a(Class<?> cls) {
            for (o6 o6Var : this.a) {
                if (o6Var.b(cls)) {
                    return o6Var.a(cls);
                }
            }
            throw new UnsupportedOperationException("No factory is available for message type: ".concat(cls.getName()));
        }

        public final boolean b(Class<?> cls) {
            for (o6 b : this.a) {
                if (b.b(cls)) {
                    return true;
                }
            }
            return false;
        }
    }

    public p4() {
        o6 o6Var;
        o6[] o6VarArr = new o6[2];
        o6VarArr[0] = o3.a;
        Class<?> cls = bb.a;
        try {
            o6Var = (o6) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception unused) {
            o6Var = b;
        }
        o6VarArr[1] = o6Var;
        b bVar = new b(o6VarArr);
        Charset charset = p.a;
        this.a = bVar;
    }
}
