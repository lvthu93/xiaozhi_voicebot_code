package com.google.android.exoplayer2;

import android.os.Bundle;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import com.google.android.exoplayer2.util.Assertions;
import com.google.common.base.Objects;

public final class StarRating extends Rating {
    public static final f2 h = new f2(10);
    @IntRange(from = 1)
    public final int f;
    public final float g;

    public StarRating(@IntRange(from = 1) int i) {
        Assertions.checkArgument(i > 0, "maxStars must be a positive integer");
        this.f = i;
        this.g = -1.0f;
    }

    public static String a(int i) {
        return Integer.toString(i, 36);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
        r4 = (com.google.android.exoplayer2.StarRating) r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@androidx.annotation.Nullable java.lang.Object r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof com.google.android.exoplayer2.StarRating
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            com.google.android.exoplayer2.StarRating r4 = (com.google.android.exoplayer2.StarRating) r4
            int r0 = r4.f
            int r2 = r3.f
            if (r2 != r0) goto L_0x0017
            float r0 = r3.g
            float r4 = r4.g
            int r4 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r4 != 0) goto L_0x0017
            r1 = 1
        L_0x0017:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.StarRating.equals(java.lang.Object):boolean");
    }

    @IntRange(from = 1)
    public int getMaxStars() {
        return this.f;
    }

    public float getStarRating() {
        return this.g;
    }

    public int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.f), Float.valueOf(this.g));
    }

    public boolean isRated() {
        return this.g != -1.0f;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), 2);
        bundle.putInt(a(1), this.f);
        bundle.putFloat(a(2), this.g);
        return bundle;
    }

    public StarRating(@IntRange(from = 1) int i, @FloatRange(from = 0.0d) float f2) {
        boolean z = true;
        Assertions.checkArgument(i > 0, "maxStars must be a positive integer");
        Assertions.checkArgument((f2 < 0.0f || f2 > ((float) i)) ? false : z, "starRating is out of range [0, maxStars]");
        this.f = i;
        this.g = f2;
    }
}
