package com.google.android.exoplayer2.source.chunk;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;

public abstract class MediaChunk extends Chunk {
    public final long j;

    public MediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int i, @Nullable Object obj, long j2, long j3, long j4) {
        super(dataSource, dataSpec, 1, format, i, obj, j2, j3);
        Assertions.checkNotNull(format);
        this.j = j4;
    }

    public abstract /* synthetic */ void cancelLoad();

    public long getNextChunkIndex() {
        long j2 = this.j;
        if (j2 != -1) {
            return 1 + j2;
        }
        return -1;
    }

    public abstract boolean isLoadCompleted();

    public abstract /* synthetic */ void load() throws IOException;
}
