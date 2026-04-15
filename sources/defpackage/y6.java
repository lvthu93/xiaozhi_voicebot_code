package defpackage;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Seeker;
import com.google.android.exoplayer2.metadata.id3.MlltFrame;
import com.google.android.exoplayer2.util.Util;

/* renamed from: y6  reason: default package */
public final class y6 implements Seeker {
    public final long[] a;
    public final long[] b;
    public final long c;

    public y6(long[] jArr, long[] jArr2, long j) {
        this.a = jArr;
        this.b = jArr2;
        this.c = j == -9223372036854775807L ? C.msToUs(jArr2[jArr2.length - 1]) : j;
    }

    public static Pair a(long[] jArr, long[] jArr2, long j) {
        double d;
        int binarySearchFloor = Util.binarySearchFloor(jArr, j, true, true);
        long j2 = jArr[binarySearchFloor];
        long j3 = jArr2[binarySearchFloor];
        int i = binarySearchFloor + 1;
        if (i == jArr.length) {
            return Pair.create(Long.valueOf(j2), Long.valueOf(j3));
        }
        long j4 = jArr[i];
        long j5 = jArr2[i];
        if (j4 == j2) {
            d = 0.0d;
        } else {
            d = (((double) j) - ((double) j2)) / ((double) (j4 - j2));
        }
        return Pair.create(Long.valueOf(j), Long.valueOf(((long) (d * ((double) (j5 - j3)))) + j3));
    }

    public static y6 create(long j, MlltFrame mlltFrame, long j2) {
        int length = mlltFrame.i.length;
        int i = length + 1;
        long[] jArr = new long[i];
        long[] jArr2 = new long[i];
        jArr[0] = j;
        long j3 = 0;
        jArr2[0] = 0;
        for (int i2 = 1; i2 <= length; i2++) {
            int i3 = i2 - 1;
            j += (long) (mlltFrame.g + mlltFrame.i[i3]);
            j3 += (long) (mlltFrame.h + mlltFrame.j[i3]);
            jArr[i2] = j;
            jArr2[i2] = j3;
        }
        return new y6(jArr, jArr2, j2);
    }

    public long getDataEndPosition() {
        return -1;
    }

    public long getDurationUs() {
        return this.c;
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        Pair a2 = a(this.b, this.a, C.usToMs(Util.constrainValue(j, 0, this.c)));
        return new SeekMap.SeekPoints(new SeekPoint(C.msToUs(((Long) a2.first).longValue()), ((Long) a2.second).longValue()));
    }

    public long getTimeUs(long j) {
        return C.msToUs(((Long) a(this.a, this.b, j).second).longValue());
    }

    public boolean isSeekable() {
        return true;
    }
}
