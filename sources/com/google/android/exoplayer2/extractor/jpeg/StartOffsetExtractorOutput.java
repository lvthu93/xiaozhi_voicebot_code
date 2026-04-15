package com.google.android.exoplayer2.extractor.jpeg;

import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.TrackOutput;

public final class StartOffsetExtractorOutput implements ExtractorOutput {
    public final long c;
    public final ExtractorOutput f;

    public class a implements SeekMap {
        public final /* synthetic */ SeekMap a;

        public a(SeekMap seekMap) {
            this.a = seekMap;
        }

        public long getDurationUs() {
            return this.a.getDurationUs();
        }

        public SeekMap.SeekPoints getSeekPoints(long j) {
            SeekMap.SeekPoints seekPoints = this.a.getSeekPoints(j);
            SeekPoint seekPoint = seekPoints.a;
            long j2 = seekPoint.a;
            long j3 = seekPoint.b;
            StartOffsetExtractorOutput startOffsetExtractorOutput = StartOffsetExtractorOutput.this;
            SeekPoint seekPoint2 = new SeekPoint(j2, j3 + startOffsetExtractorOutput.c);
            SeekPoint seekPoint3 = seekPoints.b;
            return new SeekMap.SeekPoints(seekPoint2, new SeekPoint(seekPoint3.a, seekPoint3.b + startOffsetExtractorOutput.c));
        }

        public boolean isSeekable() {
            return this.a.isSeekable();
        }
    }

    public StartOffsetExtractorOutput(long j, ExtractorOutput extractorOutput) {
        this.c = j;
        this.f = extractorOutput;
    }

    public void endTracks() {
        this.f.endTracks();
    }

    public void seekMap(SeekMap seekMap) {
        this.f.seekMap(new a(seekMap));
    }

    public TrackOutput track(int i, int i2) {
        return this.f.track(i, i2);
    }
}
