package defpackage;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.cache.CacheSpan;
import com.google.android.exoplayer2.util.Assertions;
import java.io.File;
import java.util.regex.Pattern;

/* renamed from: xa  reason: default package */
public final class xa extends CacheSpan {
    public static final Pattern k = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v1\\.exo$", 32);
    public static final Pattern l = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)\\.v2\\.exo$", 32);
    public static final Pattern m = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\.v3\\.exo$", 32);

    public xa(String str, long j, long j2, long j3, @Nullable File file) {
        super(str, j, j2, j3, file);
    }

    @Nullable
    public static xa createCacheEntry(File file, long j, cv cvVar) {
        return createCacheEntry(file, j, -9223372036854775807L, cvVar);
    }

    public static xa createHole(String str, long j, long j2) {
        return new xa(str, j, j2, -9223372036854775807L, (File) null);
    }

    public static xa createLookup(String str, long j) {
        return new xa(str, j, -1, -9223372036854775807L, (File) null);
    }

    public static File getCacheFile(File file, int i, long j, long j2) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(i);
        sb.append(".");
        sb.append(j);
        sb.append(".");
        sb.append(j2);
        sb.append(".v3.exo");
        return new File(file, sb.toString());
    }

    public xa copyWithFileAndLastTouchTimestamp(File file, long j) {
        Assertions.checkState(this.h);
        return new xa(this.c, this.f, this.g, j, file);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0082, code lost:
        if (r16.renameTo(r1) == false) goto L_0x004b;
     */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.xa createCacheEntry(java.io.File r16, long r17, long r19, defpackage.cv r21) {
        /*
            r0 = r21
            java.lang.String r1 = r16.getName()
            java.lang.String r2 = ".v3.exo"
            boolean r2 = r1.endsWith(r2)
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            if (r2 != 0) goto L_0x008f
            java.lang.String r1 = r16.getName()
            java.util.regex.Pattern r2 = l
            java.util.regex.Matcher r2 = r2.matcher(r1)
            boolean r7 = r2.matches()
            if (r7 == 0) goto L_0x0031
            java.lang.String r1 = r2.group(r5)
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.unescapeFileName(r1)
            goto L_0x0049
        L_0x0031:
            java.util.regex.Pattern r2 = k
            java.util.regex.Matcher r2 = r2.matcher(r1)
            boolean r1 = r2.matches()
            if (r1 == 0) goto L_0x0048
            java.lang.String r1 = r2.group(r5)
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            java.lang.String r1 = (java.lang.String) r1
            goto L_0x0049
        L_0x0048:
            r1 = r6
        L_0x0049:
            if (r1 != 0) goto L_0x004d
        L_0x004b:
            r1 = r6
            goto L_0x0085
        L_0x004d:
            java.io.File r7 = r16.getParentFile()
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkStateNotNull(r7)
            r8 = r7
            java.io.File r8 = (java.io.File) r8
            int r9 = r0.assignIdForKey(r1)
            java.lang.String r1 = r2.group(r4)
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            java.lang.String r1 = (java.lang.String) r1
            long r10 = java.lang.Long.parseLong(r1)
            java.lang.String r1 = r2.group(r3)
            java.lang.Object r1 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r1)
            java.lang.String r1 = (java.lang.String) r1
            long r12 = java.lang.Long.parseLong(r1)
            java.io.File r1 = getCacheFile(r8, r9, r10, r12)
            r2 = r16
            boolean r2 = r2.renameTo(r1)
            if (r2 != 0) goto L_0x0085
            goto L_0x004b
        L_0x0085:
            if (r1 != 0) goto L_0x0088
            return r6
        L_0x0088:
            java.lang.String r2 = r1.getName()
            r15 = r1
            r1 = r2
            goto L_0x0092
        L_0x008f:
            r2 = r16
            r15 = r2
        L_0x0092:
            java.util.regex.Pattern r2 = m
            java.util.regex.Matcher r1 = r2.matcher(r1)
            boolean r2 = r1.matches()
            if (r2 != 0) goto L_0x009f
            return r6
        L_0x009f:
            java.lang.String r2 = r1.group(r5)
            java.lang.Object r2 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r2)
            java.lang.String r2 = (java.lang.String) r2
            int r2 = java.lang.Integer.parseInt(r2)
            java.lang.String r8 = r0.getKeyForId(r2)
            if (r8 != 0) goto L_0x00b4
            return r6
        L_0x00b4:
            r9 = -1
            int r0 = (r17 > r9 ? 1 : (r17 == r9 ? 0 : -1))
            if (r0 != 0) goto L_0x00c0
            long r9 = r15.length()
            r11 = r9
            goto L_0x00c2
        L_0x00c0:
            r11 = r17
        L_0x00c2:
            r9 = 0
            int r0 = (r11 > r9 ? 1 : (r11 == r9 ? 0 : -1))
            if (r0 != 0) goto L_0x00c9
            return r6
        L_0x00c9:
            java.lang.String r0 = r1.group(r4)
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
            java.lang.String r0 = (java.lang.String) r0
            long r9 = java.lang.Long.parseLong(r0)
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r0 = (r19 > r4 ? 1 : (r19 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x00f0
            java.lang.String r0 = r1.group(r3)
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)
            java.lang.String r0 = (java.lang.String) r0
            long r0 = java.lang.Long.parseLong(r0)
            r13 = r0
            goto L_0x00f2
        L_0x00f0:
            r13 = r19
        L_0x00f2:
            xa r0 = new xa
            r7 = r0
            r7.<init>(r8, r9, r11, r13, r15)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xa.createCacheEntry(java.io.File, long, long, cv):xa");
    }
}
