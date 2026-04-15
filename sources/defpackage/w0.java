package defpackage;

import androidx.annotation.Nullable;

/* renamed from: w0  reason: default package */
public final class w0 {
    public static long getFNV64Hash(@Nullable String str) {
        long j = 0;
        if (str == null) {
            return 0;
        }
        for (int i = 0; i < str.length(); i++) {
            long charAt = j ^ ((long) str.charAt(i));
            j = charAt + (charAt << 1) + (charAt << 4) + (charAt << 5) + (charAt << 7) + (charAt << 8) + (charAt << 40);
        }
        return j;
    }
}
