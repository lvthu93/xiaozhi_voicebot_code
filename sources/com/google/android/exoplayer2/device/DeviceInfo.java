package com.google.android.exoplayer2.device;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class DeviceInfo implements Bundleable {
    public static final DeviceInfo h = new DeviceInfo(0, 0, 0);
    public final int c;
    public final int f;
    public final int g;

    @Documented
    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackType {
    }

    public DeviceInfo(int i, int i2, int i3) {
        this.c = i;
        this.f = i2;
        this.g = i3;
    }

    public static String a(int i) {
        return Integer.toString(i, 36);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x000a, code lost:
        r5 = (com.google.android.exoplayer2.device.DeviceInfo) r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@androidx.annotation.Nullable java.lang.Object r5) {
        /*
            r4 = this;
            r0 = 1
            if (r4 != r5) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r5 instanceof com.google.android.exoplayer2.device.DeviceInfo
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            com.google.android.exoplayer2.device.DeviceInfo r5 = (com.google.android.exoplayer2.device.DeviceInfo) r5
            int r1 = r5.c
            int r3 = r4.c
            if (r3 != r1) goto L_0x001f
            int r1 = r4.f
            int r3 = r5.f
            if (r1 != r3) goto L_0x001f
            int r1 = r4.g
            int r5 = r5.g
            if (r1 != r5) goto L_0x001f
            goto L_0x0020
        L_0x001f:
            r0 = 0
        L_0x0020:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.device.DeviceInfo.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return ((((527 + this.c) * 31) + this.f) * 31) + this.g;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt(a(0), this.c);
        bundle.putInt(a(1), this.f);
        bundle.putInt(a(2), this.g);
        return bundle;
    }
}
