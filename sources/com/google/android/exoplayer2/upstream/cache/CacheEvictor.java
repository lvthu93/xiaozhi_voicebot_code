package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.cache.Cache;

public interface CacheEvictor extends Cache.Listener {
    void onCacheInitialized();

    /* synthetic */ void onSpanAdded(Cache cache, CacheSpan cacheSpan);

    /* synthetic */ void onSpanRemoved(Cache cache, CacheSpan cacheSpan);

    /* synthetic */ void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2);

    void onStartFile(Cache cache, String str, long j, long j2);

    boolean requiresCacheSpanTouches();
}
