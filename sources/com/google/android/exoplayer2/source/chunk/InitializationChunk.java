package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class InitializationChunk extends Chunk {
    public final ChunkExtractor j;
    public ChunkExtractor.TrackOutputProvider k;
    public long l;
    public volatile boolean m;

    public InitializationChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, ChunkExtractor chunkExtractor) {
        super(dataSource, dataSpec, 2, format, i, obj, -9223372036854775807L, -9223372036854775807L);
        this.j = chunkExtractor;
    }

    public void cancelLoad() {
        this.m = true;
    }

    public void init(ChunkExtractor.TrackOutputProvider trackOutputProvider) {
        this.k = trackOutputProvider;
    }

    public void load() throws IOException {
        DefaultExtractorInput defaultExtractorInput;
        if (this.l == 0) {
            this.j.init(this.k, -9223372036854775807L, -9223372036854775807L);
        }
        try {
            DataSpec subrange = this.b.subrange(this.l);
            StatsDataSource statsDataSource = this.i;
            defaultExtractorInput = new DefaultExtractorInput(statsDataSource, subrange.f, statsDataSource.open(subrange));
            do {
                if (this.m || !this.j.read(defaultExtractorInput)) {
                    break;
                }
                break;
                break;
            } while (!this.j.read(defaultExtractorInput));
            break;
            this.l = defaultExtractorInput.getPosition() - this.b.f;
            Util.closeQuietly((DataSource) this.i);
        } catch (Throwable th) {
            Util.closeQuietly((DataSource) this.i);
            throw th;
        }
    }
}
