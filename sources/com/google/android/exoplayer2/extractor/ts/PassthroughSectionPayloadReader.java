package com.google.android.exoplayer2.extractor.ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;

public final class PassthroughSectionPayloadReader implements SectionPayloadReader {
    public Format a;
    public TimestampAdjuster b;
    public TrackOutput c;

    public PassthroughSectionPayloadReader(String str) {
        this.a = new Format.Builder().setSampleMimeType(str).build();
    }

    public void consume(ParsableByteArray parsableByteArray) {
        Assertions.checkStateNotNull(this.b);
        Util.castNonNull(this.c);
        long timestampOffsetUs = this.b.getTimestampOffsetUs();
        if (timestampOffsetUs != -9223372036854775807L) {
            Format format = this.a;
            if (timestampOffsetUs != format.t) {
                Format build = format.buildUpon().setSubsampleOffsetUs(timestampOffsetUs).build();
                this.a = build;
                this.c.format(build);
            }
            int bytesLeft = parsableByteArray.bytesLeft();
            this.c.sampleData(parsableByteArray, bytesLeft);
            this.c.sampleMetadata(this.b.getLastAdjustedTimestampUs(), 1, bytesLeft, 0, (TrackOutput.CryptoData) null);
        }
    }

    public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.b = timestampAdjuster;
        trackIdGenerator.generateNewId();
        TrackOutput track = extractorOutput.track(trackIdGenerator.getTrackId(), 5);
        this.c = track;
        track.format(this.a);
    }
}
