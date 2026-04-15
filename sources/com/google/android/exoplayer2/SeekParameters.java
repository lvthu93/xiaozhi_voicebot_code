package com.google.android.exoplayer2;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class SeekParameters {
    public static final SeekParameters c;
    public final long a;
    public final long b;

    static {
        SeekParameters seekParameters = new SeekParameters(0, 0);
        new SeekParameters(Long.MAX_VALUE, Long.MAX_VALUE);
        new SeekParameters(Long.MAX_VALUE, 0);
        new SeekParameters(0, Long.MAX_VALUE);
        c = seekParameters;
    }

    public SeekParameters(long j, long j2) {
        boolean z;
        boolean z2 = true;
        if (j >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        Assertions.checkArgument(j2 < 0 ? false : z2);
        this.a = j;
        this.b = j2;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SeekParameters.class != obj.getClass()) {
            return false;
        }
        SeekParameters seekParameters = (SeekParameters) obj;
        if (this.a == seekParameters.a && this.b == seekParameters.b) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((int) this.a) * 31) + ((int) this.b);
    }

    public long resolveSeekPositionUs(long j, long j2, long j3) {
        boolean z;
        long j4 = this.a;
        if (j4 == 0 && this.b == 0) {
            return j;
        }
        long subtractWithOverflowDefault = Util.subtractWithOverflowDefault(j, j4, Long.MIN_VALUE);
        long addWithOverflowDefault = Util.addWithOverflowDefault(j, this.b, Long.MAX_VALUE);
        boolean z2 = true;
        if (subtractWithOverflowDefault > j2 || j2 > addWithOverflowDefault) {
            z = false;
        } else {
            z = true;
        }
        if (subtractWithOverflowDefault > j3 || j3 > addWithOverflowDefault) {
            z2 = false;
        }
        if (!z || !z2) {
            if (z) {
                return j2;
            }
            if (z2) {
                return j3;
            }
            return subtractWithOverflowDefault;
        } else if (Math.abs(j2 - j) <= Math.abs(j3 - j)) {
            return j2;
        } else {
            return j3;
        }
    }
}
