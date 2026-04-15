package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.FlacStreamMetadata;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class FlacSeekTableSeekMap implements SeekMap {
    public final FlacStreamMetadata a;
    public final long b;

    public FlacSeekTableSeekMap(FlacStreamMetadata flacStreamMetadata, long j) {
        this.a = flacStreamMetadata;
        this.b = j;
    }

    public long getDurationUs() {
        return this.a.getDurationUs();
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        long j2;
        long j3 = j;
        FlacStreamMetadata flacStreamMetadata = this.a;
        Assertions.checkStateNotNull(flacStreamMetadata.k);
        FlacStreamMetadata.SeekTable seekTable = flacStreamMetadata.k;
        long[] jArr = seekTable.a;
        int binarySearchFloor = Util.binarySearchFloor(jArr, flacStreamMetadata.getSampleNumber(j3), true, false);
        long j4 = 0;
        if (binarySearchFloor == -1) {
            j2 = 0;
        } else {
            j2 = jArr[binarySearchFloor];
        }
        long[] jArr2 = seekTable.b;
        if (binarySearchFloor != -1) {
            j4 = jArr2[binarySearchFloor];
        }
        long j5 = (j2 * 1000000) / ((long) flacStreamMetadata.e);
        long j6 = this.b;
        SeekPoint seekPoint = new SeekPoint(j5, j4 + j6);
        if (seekPoint.a == j3 || binarySearchFloor == jArr.length - 1) {
            return new SeekMap.SeekPoints(seekPoint);
        }
        int i = binarySearchFloor + 1;
        return new SeekMap.SeekPoints(seekPoint, new SeekPoint((jArr[i] * 1000000) / ((long) flacStreamMetadata.e), j6 + jArr2[i]));
    }

    public boolean isSeekable() {
        return true;
    }
}
