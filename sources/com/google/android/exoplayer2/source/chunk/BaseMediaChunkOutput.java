package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.util.Log;

public final class BaseMediaChunkOutput implements ChunkExtractor.TrackOutputProvider {
    public final int[] a;
    public final SampleQueue[] b;

    public BaseMediaChunkOutput(int[] iArr, SampleQueue[] sampleQueueArr) {
        this.a = iArr;
        this.b = sampleQueueArr;
    }

    public int[] getWriteIndices() {
        SampleQueue[] sampleQueueArr = this.b;
        int[] iArr = new int[sampleQueueArr.length];
        for (int i = 0; i < sampleQueueArr.length; i++) {
            iArr[i] = sampleQueueArr[i].getWriteIndex();
        }
        return iArr;
    }

    public void setSampleOffsetUs(long j) {
        for (SampleQueue sampleOffsetUs : this.b) {
            sampleOffsetUs.setSampleOffsetUs(j);
        }
    }

    public TrackOutput track(int i, int i2) {
        int i3 = 0;
        while (true) {
            int[] iArr = this.a;
            if (i3 >= iArr.length) {
                StringBuilder sb = new StringBuilder(36);
                sb.append("Unmatched track of type: ");
                sb.append(i2);
                Log.e("BaseMediaChunkOutput", sb.toString());
                return new DummyTrackOutput();
            } else if (i2 == iArr[i3]) {
                return this.b[i3];
            } else {
                i3++;
            }
        }
    }
}
