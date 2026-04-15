package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ActivityChooserView;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.upstream.DataReader;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class SingleSampleMediaChunk extends BaseMediaChunk {
    public final int o;
    public final Format p;
    public long q;
    public boolean r;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, long j, long j2, long j3, int i2, Format format2) {
        super(dataSource, dataSpec, format, i, obj, j, j2, -9223372036854775807L, -9223372036854775807L, j3);
        this.o = i2;
        this.p = format2;
    }

    public void cancelLoad() {
    }

    public boolean isLoadCompleted() {
        return this.r;
    }

    /* JADX INFO: finally extract failed */
    public void load() throws IOException {
        StatsDataSource statsDataSource = this.i;
        BaseMediaChunkOutput baseMediaChunkOutput = (BaseMediaChunkOutput) Assertions.checkStateNotNull(this.m);
        baseMediaChunkOutput.setSampleOffsetUs(0);
        TrackOutput track = baseMediaChunkOutput.track(0, this.o);
        track.format(this.p);
        try {
            long open = statsDataSource.open(this.b.subrange(this.q));
            if (open != -1) {
                open += this.q;
            }
            DefaultExtractorInput defaultExtractorInput = new DefaultExtractorInput(this.i, this.q, open);
            for (int i = 0; i != -1; i = track.sampleData((DataReader) defaultExtractorInput, (int) ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, true)) {
                this.q += (long) i;
            }
            track.sampleMetadata(this.g, 1, (int) this.q, 0, (TrackOutput.CryptoData) null);
            Util.closeQuietly((DataSource) statsDataSource);
            this.r = true;
        } catch (Throwable th) {
            Util.closeQuietly((DataSource) statsDataSource);
            throw th;
        }
    }
}
