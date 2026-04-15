package com.google.android.exoplayer2.source;

import androidx.annotation.Nullable;

public class MediaPeriodId {
    public final Object a;
    public final int b;
    public final int c;
    public final long d;
    public final int e;

    public MediaPeriodId(Object obj) {
        this(obj, -1);
    }

    public MediaPeriodId copyWithPeriodUid(Object obj) {
        if (this.a.equals(obj)) {
            return this;
        }
        return new MediaPeriodId(obj, this.b, this.c, this.d, this.e);
    }

    public MediaPeriodId copyWithWindowSequenceNumber(long j) {
        if (this.d == j) {
            return this;
        }
        return new MediaPeriodId(this.a, this.b, this.c, j, this.e);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaPeriodId)) {
            return false;
        }
        MediaPeriodId mediaPeriodId = (MediaPeriodId) obj;
        if (this.a.equals(mediaPeriodId.a) && this.b == mediaPeriodId.b && this.c == mediaPeriodId.c && this.d == mediaPeriodId.d && this.e == mediaPeriodId.e) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((this.a.hashCode() + 527) * 31) + this.b) * 31) + this.c) * 31) + ((int) this.d)) * 31) + this.e;
    }

    public boolean isAd() {
        return this.b != -1;
    }

    public MediaPeriodId(Object obj, long j) {
        this(obj, -1, -1, j, -1);
    }

    public MediaPeriodId(Object obj, long j, int i) {
        this(obj, -1, -1, j, i);
    }

    public MediaPeriodId(Object obj, int i, int i2, long j) {
        this(obj, i, i2, j, -1);
    }

    public MediaPeriodId(MediaPeriodId mediaPeriodId) {
        this.a = mediaPeriodId.a;
        this.b = mediaPeriodId.b;
        this.c = mediaPeriodId.c;
        this.d = mediaPeriodId.d;
        this.e = mediaPeriodId.e;
    }

    public MediaPeriodId(Object obj, int i, int i2, long j, int i3) {
        this.a = obj;
        this.b = i;
        this.c = i2;
        this.d = j;
        this.e = i3;
    }
}
