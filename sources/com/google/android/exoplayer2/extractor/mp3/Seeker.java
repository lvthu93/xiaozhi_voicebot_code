package com.google.android.exoplayer2.extractor.mp3;

import com.google.android.exoplayer2.extractor.SeekMap;

public interface Seeker extends SeekMap {

    public static class UnseekableSeeker extends SeekMap.Unseekable implements Seeker {
        public UnseekableSeeker() {
            super(-9223372036854775807L);
        }

        public long getDataEndPosition() {
            return -1;
        }

        public long getTimeUs(long j) {
            return 0;
        }
    }

    long getDataEndPosition();

    /* synthetic */ long getDurationUs();

    /* synthetic */ SeekMap.SeekPoints getSeekPoints(long j);

    long getTimeUs(long j);

    /* synthetic */ boolean isSeekable();
}
