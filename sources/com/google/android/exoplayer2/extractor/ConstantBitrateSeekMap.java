package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Util;

public class ConstantBitrateSeekMap implements SeekMap {
    public final long a;
    public final long b;
    public final int c;
    public final long d;
    public final int e;
    public final long f;

    public ConstantBitrateSeekMap(long j, long j2, int i, int i2) {
        this.a = j;
        this.b = j2;
        this.c = i2 == -1 ? 1 : i2;
        this.e = i;
        if (j == -1) {
            this.d = -1;
            this.f = -9223372036854775807L;
            return;
        }
        long j3 = j - j2;
        this.d = j3;
        this.f = ((Math.max(0, j3) * 8) * 1000000) / ((long) i);
    }

    public long getDurationUs() {
        return this.f;
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        long j2 = this.b;
        long j3 = this.d;
        if (j3 == -1) {
            return new SeekMap.SeekPoints(new SeekPoint(0, j2));
        }
        int i = this.c;
        long j4 = (long) i;
        long constrainValue = j2 + Util.constrainValue((((((long) this.e) * j) / 8000000) / j4) * j4, 0, j3 - j4);
        long timeUsAtPosition = getTimeUsAtPosition(constrainValue);
        SeekPoint seekPoint = new SeekPoint(timeUsAtPosition, constrainValue);
        if (timeUsAtPosition >= j || ((long) i) + constrainValue >= this.a) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        long j5 = constrainValue + ((long) i);
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(getTimeUsAtPosition(j5), j5));
    }

    public long getTimeUsAtPosition(long j) {
        return ((Math.max(0, j - this.b) * 8) * 1000000) / ((long) this.e);
    }

    public boolean isSeekable() {
        return this.d != -1;
    }
}
