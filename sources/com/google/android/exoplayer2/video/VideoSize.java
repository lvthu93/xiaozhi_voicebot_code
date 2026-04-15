package com.google.android.exoplayer2.video;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import com.google.android.exoplayer2.Bundleable;

public final class VideoSize implements Bundleable {
    public static final VideoSize i = new VideoSize(0, 0);
    @IntRange(from = 0)
    public final int c;
    @IntRange(from = 0)
    public final int f;
    @IntRange(from = 0, to = 359)
    public final int g;
    @FloatRange(from = 0.0d, fromInclusive = false)
    public final float h;

    public VideoSize(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3) {
        this(i2, i3, 0, 1.0f);
    }

    public static String a(int i2) {
        return Integer.toString(i2, 36);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0009, code lost:
        r5 = (com.google.android.exoplayer2.video.VideoSize) r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@androidx.annotation.Nullable java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.exoplayer2.video.VideoSize
            r2 = 0
            if (r1 == 0) goto L_0x0028
            com.google.android.exoplayer2.video.VideoSize r5 = (com.google.android.exoplayer2.video.VideoSize) r5
            int r1 = r5.c
            int r3 = r4.c
            if (r3 != r1) goto L_0x0026
            int r1 = r4.f
            int r3 = r5.f
            if (r1 != r3) goto L_0x0026
            int r1 = r4.g
            int r3 = r5.g
            if (r1 != r3) goto L_0x0026
            float r1 = r4.h
            float r5 = r5.h
            int r5 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r5 != 0) goto L_0x0026
            goto L_0x0027
        L_0x0026:
            r0 = 0
        L_0x0027:
            return r0
        L_0x0028:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.VideoSize.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return Float.floatToRawIntBits(this.h) + ((((((217 + this.c) * 31) + this.f) * 31) + this.g) * 31);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), this.c);
        bundle.putInt(a(1), this.f);
        bundle.putInt(a(2), this.g);
        bundle.putFloat(a(3), this.h);
        return bundle;
    }

    public VideoSize(@IntRange(from = 0) int i2, @IntRange(from = 0) int i3, @IntRange(from = 0, to = 359) int i4, @FloatRange(from = 0.0d, fromInclusive = false) float f2) {
        this.c = i2;
        this.f = i3;
        this.g = i4;
        this.h = f2;
    }
}
