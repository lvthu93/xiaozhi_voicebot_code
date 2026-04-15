package com.google.android.exoplayer2.source.chunk;

import com.google.android.exoplayer2.upstream.DataSpec;

public abstract class BaseMediaChunkIterator implements MediaChunkIterator {
    public final long a;
    public final long b;
    public long c;

    public BaseMediaChunkIterator(long j, long j2) {
        this.a = j;
        this.b = j2;
        reset();
    }

    public abstract /* synthetic */ long getChunkEndTimeUs();

    public abstract /* synthetic */ long getChunkStartTimeUs();

    public abstract /* synthetic */ DataSpec getDataSpec();

    public boolean isEnded() {
        return this.c > this.b;
    }

    public boolean next() {
        this.c++;
        return !isEnded();
    }

    public void reset() {
        this.c = this.a - 1;
    }
}
