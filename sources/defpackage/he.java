package defpackage;

import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.util.Util;

/* renamed from: he  reason: default package */
public final class he implements SeekMap {
    public final fe a;
    public final int b;
    public final long c;
    public final long d;
    public final long e;

    public he(fe feVar, int i, long j, long j2) {
        this.a = feVar;
        this.b = i;
        this.c = j;
        long j3 = (j2 - j) / ((long) feVar.d);
        this.d = j3;
        this.e = a(j3);
    }

    public final long a(long j) {
        return Util.scaleLargeTimestamp(j * ((long) this.b), 1000000, (long) this.a.c);
    }

    public long getDurationUs() {
        return this.e;
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        fe feVar = this.a;
        long j2 = (((long) feVar.c) * j) / (((long) this.b) * 1000000);
        long j3 = this.d;
        long constrainValue = Util.constrainValue(j2, 0, j3 - 1);
        long j4 = this.c;
        long a2 = a(constrainValue);
        SeekPoint seekPoint = new SeekPoint(a2, (((long) feVar.d) * constrainValue) + j4);
        if (a2 >= j || constrainValue == j3 - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        long j5 = constrainValue + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint(a(j5), (((long) feVar.d) * j5) + j4));
    }

    public boolean isSeekable() {
        return true;
    }
}
