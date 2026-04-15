package com.google.android.exoplayer2;

import android.os.Bundle;
import com.google.common.base.Objects;

public final class HeartRating extends Rating {
    public static final f2 h = new f2(1);
    public final boolean f;
    public final boolean g;

    public HeartRating() {
        this.f = false;
        this.g = false;
    }

    public static String a(int i) {
        return Integer.toString(i, 36);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0006, code lost:
        r4 = (com.google.android.exoplayer2.HeartRating) r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@androidx.annotation.Nullable java.lang.Object r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof com.google.android.exoplayer2.HeartRating
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            com.google.android.exoplayer2.HeartRating r4 = (com.google.android.exoplayer2.HeartRating) r4
            boolean r0 = r4.g
            boolean r2 = r3.g
            if (r2 != r0) goto L_0x0015
            boolean r0 = r3.f
            boolean r4 = r4.f
            if (r0 != r4) goto L_0x0015
            r1 = 1
        L_0x0015:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.HeartRating.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return Objects.hashCode(Boolean.valueOf(this.f), Boolean.valueOf(this.g));
    }

    public boolean isHeart() {
        return this.g;
    }

    public boolean isRated() {
        return this.f;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), 0);
        bundle.putBoolean(a(1), this.f);
        bundle.putBoolean(a(2), this.g);
        return bundle;
    }

    public HeartRating(boolean z) {
        this.f = true;
        this.g = z;
    }
}
