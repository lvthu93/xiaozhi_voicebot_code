package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSink;

@Deprecated
public final class CacheDataSinkFactory implements DataSink.Factory {
    public final Cache a;
    public final long b;
    public final int c;

    public CacheDataSinkFactory(Cache cache, long j) {
        this(cache, j, 20480);
    }

    public DataSink createDataSink() {
        return new CacheDataSink(this.a, this.b, this.c);
    }

    public CacheDataSinkFactory(Cache cache, long j, int i) {
        this.a = cache;
        this.b = j;
        this.c = i;
    }
}
