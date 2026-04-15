package com.google.android.exoplayer2.upstream;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpUtil {
    public static final Pattern a = Pattern.compile("bytes (\\d+)-(\\d+)/(?:\\d+|\\*)");
    public static final Pattern b = Pattern.compile("bytes (?:(?:\\d+-\\d+)|\\*)/(\\d+)");

    @Nullable
    public static String buildRangeRequestHeader(long j, long j2) {
        if (j == 0 && j2 == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder("bytes=");
        sb.append(j);
        sb.append("-");
        if (j2 != -1) {
            sb.append((j + j2) - 1);
        }
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0034  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getContentLength(@androidx.annotation.Nullable java.lang.String r9, @androidx.annotation.Nullable java.lang.String r10) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            java.lang.String r1 = "]"
            java.lang.String r2 = "HttpUtil"
            if (r0 != 0) goto L_0x002c
            long r3 = java.lang.Long.parseLong(r9)     // Catch:{ NumberFormatException -> 0x000f }
            goto L_0x002e
        L_0x000f:
            r0 = 28
            int r0 = defpackage.y2.c(r9, r0)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r0)
            java.lang.String r0 = "Unexpected Content-Length ["
            r3.append(r0)
            r3.append(r9)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            com.google.android.exoplayer2.util.Log.e(r2, r0)
        L_0x002c:
            r3 = -1
        L_0x002e:
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 != 0) goto L_0x00c2
            java.util.regex.Pattern r0 = a
            java.util.regex.Matcher r0 = r0.matcher(r10)
            boolean r5 = r0.matches()
            if (r5 == 0) goto L_0x00c2
            r5 = 2
            java.lang.String r5 = r0.group(r5)     // Catch:{ NumberFormatException -> 0x00a5 }
            java.lang.Object r5 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r5)     // Catch:{ NumberFormatException -> 0x00a5 }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ NumberFormatException -> 0x00a5 }
            long r5 = java.lang.Long.parseLong(r5)     // Catch:{ NumberFormatException -> 0x00a5 }
            r7 = 1
            java.lang.String r0 = r0.group(r7)     // Catch:{ NumberFormatException -> 0x00a5 }
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)     // Catch:{ NumberFormatException -> 0x00a5 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ NumberFormatException -> 0x00a5 }
            long r7 = java.lang.Long.parseLong(r0)     // Catch:{ NumberFormatException -> 0x00a5 }
            long r5 = r5 - r7
            r7 = 1
            long r5 = r5 + r7
            r7 = 0
            int r0 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r0 >= 0) goto L_0x006a
            r3 = r5
            goto L_0x00c2
        L_0x006a:
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 == 0) goto L_0x00c2
            java.lang.String r0 = java.lang.String.valueOf(r9)     // Catch:{ NumberFormatException -> 0x00a5 }
            int r0 = r0.length()     // Catch:{ NumberFormatException -> 0x00a5 }
            int r0 = r0 + 26
            java.lang.String r7 = java.lang.String.valueOf(r10)     // Catch:{ NumberFormatException -> 0x00a5 }
            int r7 = r7.length()     // Catch:{ NumberFormatException -> 0x00a5 }
            int r0 = r0 + r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x00a5 }
            r7.<init>(r0)     // Catch:{ NumberFormatException -> 0x00a5 }
            java.lang.String r0 = "Inconsistent headers ["
            r7.append(r0)     // Catch:{ NumberFormatException -> 0x00a5 }
            r7.append(r9)     // Catch:{ NumberFormatException -> 0x00a5 }
            java.lang.String r9 = "] ["
            r7.append(r9)     // Catch:{ NumberFormatException -> 0x00a5 }
            r7.append(r10)     // Catch:{ NumberFormatException -> 0x00a5 }
            r7.append(r1)     // Catch:{ NumberFormatException -> 0x00a5 }
            java.lang.String r9 = r7.toString()     // Catch:{ NumberFormatException -> 0x00a5 }
            com.google.android.exoplayer2.util.Log.w(r2, r9)     // Catch:{ NumberFormatException -> 0x00a5 }
            long r3 = java.lang.Math.max(r3, r5)     // Catch:{ NumberFormatException -> 0x00a5 }
            goto L_0x00c2
        L_0x00a5:
            r9 = 27
            int r9 = defpackage.y2.c(r10, r9)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r9)
            java.lang.String r9 = "Unexpected Content-Range ["
            r0.append(r9)
            r0.append(r10)
            r0.append(r1)
            java.lang.String r9 = r0.toString()
            com.google.android.exoplayer2.util.Log.e(r2, r9)
        L_0x00c2:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.HttpUtil.getContentLength(java.lang.String, java.lang.String):long");
    }

    public static long getDocumentSize(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        Matcher matcher = b.matcher(str);
        if (matcher.matches()) {
            return Long.parseLong((String) Assertions.checkNotNull(matcher.group(1)));
        }
        return -1;
    }
}
