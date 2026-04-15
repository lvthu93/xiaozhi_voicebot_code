package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public class ContainerMediaChunk extends BaseMediaChunk {
    public final int o;
    public final long p;
    public final ChunkExtractor q;
    public long r;
    public volatile boolean s;
    public boolean t;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ContainerMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, long j, long j2, long j3, long j4, long j5, int i2, long j6, ChunkExtractor chunkExtractor) {
        super(dataSource, dataSpec, format, i, obj, j, j2, j3, j4, j5);
        this.o = i2;
        this.p = j6;
        this.q = chunkExtractor;
    }

    public final void cancelLoad() {
        this.s = true;
    }

    public long getNextChunkIndex() {
        return this.j + ((long) this.o);
    }

    public boolean isLoadCompleted() {
        return this.t;
    }

    public final void load() throws IOException {
        DefaultExtractorInput defaultExtractorInput;
        long j;
        if (this.r == 0) {
            BaseMediaChunkOutput baseMediaChunkOutput = (BaseMediaChunkOutput) Assertions.checkStateNotNull(this.m);
            baseMediaChunkOutput.setSampleOffsetUs(this.p);
            ChunkExtractor chunkExtractor = this.q;
            long j2 = this.k;
            long j3 = -9223372036854775807L;
            if (j2 == -9223372036854775807L) {
                j = -9223372036854775807L;
            } else {
                j = j2 - this.p;
            }
            long j4 = this.l;
            if (j4 != -9223372036854775807L) {
                j3 = j4 - this.p;
            }
            chunkExtractor.init(baseMediaChunkOutput, j, j3);
        }
        try {
            DataSpec subrange = this.b.subrange(this.r);
            StatsDataSource statsDataSource = this.i;
            defaultExtractorInput = new DefaultExtractorInput(statsDataSource, subrange.f, statsDataSource.open(subrange));
            do {
                if (this.s || !this.q.read(defaultExtractorInput)) {
                    break;
                }
                break;
                break;
            } while (!this.q.read(defaultExtractorInput));
            break;
            this.r = defaultExtractorInput.getPosition() - this.b.f;
            Util.closeQuietly((DataSource) this.i);
            this.t = !this.s;
        } catch (Throwable th) {
            Util.closeQuietly((DataSource) this.i);
            throw th;
        }
    }
}
