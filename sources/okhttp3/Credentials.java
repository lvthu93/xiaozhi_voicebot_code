package okhttp3;

import java.nio.charset.Charset;
import okhttp3.internal.Util;

public final class Credentials {
    private Credentials() {
    }

    public static String basic(String str, String str2) {
        return basic(str, str2, Util.ISO_8859_1);
    }

    public static String basic(String str, String str2, Charset charset) {
        String str3 = str + ":" + str2;
        char[] cArr = cq.h;
        if (str3 == null) {
            throw new IllegalArgumentException("s == null");
        } else if (charset != null) {
            return y2.i("Basic ", new cq(str3.getBytes(charset)).b());
        } else {
            throw new IllegalArgumentException("charset == null");
        }
    }
}
