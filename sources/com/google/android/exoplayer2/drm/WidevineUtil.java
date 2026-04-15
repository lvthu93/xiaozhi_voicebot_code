package com.google.android.exoplayer2.drm;

public final class WidevineUtil {
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d A[Catch:{ NumberFormatException -> 0x0031 }] */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<java.lang.Long, java.lang.Long> getLicenseDurationRemainingSec(com.google.android.exoplayer2.drm.DrmSession r6) {
        /*
            java.util.Map r6 = r6.queryKeyStatus()
            if (r6 != 0) goto L_0x0008
            r6 = 0
            return r6
        L_0x0008:
            android.util.Pair r0 = new android.util.Pair
            java.lang.String r1 = "LicenseDurationRemaining"
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            java.lang.Object r1 = r6.get(r1)     // Catch:{ NumberFormatException -> 0x001e }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ NumberFormatException -> 0x001e }
            if (r1 == 0) goto L_0x001e
            long r4 = java.lang.Long.parseLong(r1)     // Catch:{ NumberFormatException -> 0x001e }
            goto L_0x001f
        L_0x001e:
            r4 = r2
        L_0x001f:
            java.lang.Long r1 = java.lang.Long.valueOf(r4)
            java.lang.String r4 = "PlaybackDurationRemaining"
            java.lang.Object r6 = r6.get(r4)     // Catch:{ NumberFormatException -> 0x0031 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ NumberFormatException -> 0x0031 }
            if (r6 == 0) goto L_0x0031
            long r2 = java.lang.Long.parseLong(r6)     // Catch:{ NumberFormatException -> 0x0031 }
        L_0x0031:
            java.lang.Long r6 = java.lang.Long.valueOf(r2)
            r0.<init>(r1, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.WidevineUtil.getLicenseDurationRemainingSec(com.google.android.exoplayer2.drm.DrmSession):android.util.Pair");
    }
}
