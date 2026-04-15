package j$.util;

import java.io.Serializable;

/* renamed from: j$.util.i  reason: case insensitive filesystem */
public final class C0065i extends RuntimeException {
    public C0065i(String str) {
        super(str);
    }

    public static void a(Serializable serializable, String str) {
        throw new C0065i("Unsupported " + str + " :" + serializable);
    }
}
