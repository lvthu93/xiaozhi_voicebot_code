package com.google.android.exoplayer2;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ExoTimeoutException extends Exception {

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimeoutOperation {
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ExoTimeoutException(int r2) {
        /*
            r1 = this;
            r0 = 1
            if (r2 == r0) goto L_0x0012
            r0 = 2
            if (r2 == r0) goto L_0x000f
            r0 = 3
            if (r2 == r0) goto L_0x000c
            java.lang.String r2 = "Undefined timeout."
            goto L_0x0014
        L_0x000c:
            java.lang.String r2 = "Detaching surface timed out."
            goto L_0x0014
        L_0x000f:
            java.lang.String r2 = "Setting foreground mode timed out."
            goto L_0x0014
        L_0x0012:
            java.lang.String r2 = "Player release timed out."
        L_0x0014:
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ExoTimeoutException.<init>(int):void");
    }
}
