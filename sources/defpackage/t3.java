package defpackage;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Seeker;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.Util;

/* renamed from: t3  reason: default package */
public final class t3 implements Seeker {
    public final long a;
    public final LongArray b;
    public final LongArray c;
    public long d;

    public t3(long j, long j2, long j3) {
        this.d = j;
        this.a = j3;
        LongArray longArray = new LongArray();
        this.b = longArray;
        LongArray longArray2 = new LongArray();
        this.c = longArray2;
        longArray.add(0);
        longArray2.add(j2);
    }

    public long getDataEndPosition() {
        return this.a;
    }

    public long getDurationUs() {
        return this.d;
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        LongArray longArray = this.b;
        int binarySearchFloor = Util.binarySearchFloor(longArray, j, true, true);
        long j2 = longArray.get(binarySearchFloor);
        LongArray longArray2 = this.c;
        SeekPoint seekPoint = new SeekPoint(j2, longArray2.get(binarySearchFloor));
        if (seekPoint.a == j || binarySearchFloor == longArray.size() - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = binarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(longArray.get(i), longArray2.get(i)));
    }

    public long getTimeUs(long j) {
        return this.b.get(Util.binarySearchFloor(this.c, j, true, true));
    }

    public boolean isSeekable() {
        return true;
    }

    public boolean isTimeUsInIndex(long j) {
        LongArray longArray = this.b;
        return j - longArray.get(longArray.size() - 1) < 100000;
    }

    public void maybeAddSeekPoint(long j, long j2) {
        if (!isTimeUsInIndex(j)) {
            this.b.add(j);
            this.c.add(j2);
        }
    }
}
