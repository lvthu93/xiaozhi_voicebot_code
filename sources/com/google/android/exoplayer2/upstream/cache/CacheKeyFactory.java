package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;

public interface CacheKeyFactory {
    public static final z6 e = new z6(19);

    String buildCacheKey(DataSpec dataSpec);
}
