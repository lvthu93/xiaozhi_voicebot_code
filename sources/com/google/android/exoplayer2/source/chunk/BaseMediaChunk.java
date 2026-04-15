package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

public abstract class BaseMediaChunk extends MediaChunk {
    public final long k;
    public final long l;
    public BaseMediaChunkOutput m;
    public int[] n;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BaseMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, long j, long j2, long j3, long j4, long j5) {
        super(dataSource, dataSpec, format, i, obj, j, j2, j5);
        this.k = j3;
        this.l = j4;
    }

    public abstract /* synthetic */ void cancelLoad();

    public final int getFirstSampleIndex(int i) {
        return ((int[]) Assertions.checkStateNotNull(this.n))[i];
    }

    public void init(BaseMediaChunkOutput baseMediaChunkOutput) {
        this.m = baseMediaChunkOutput;
        this.n = baseMediaChunkOutput.getWriteIndices();
    }

    public abstract /* synthetic */ void load() throws IOException;
}
