package com.google.android.exoplayer2.upstream.cache;

import java.util.TreeSet;

public final class LeastRecentlyUsedCacheEvictor implements CacheEvictor {
    public final long a;
    public final TreeSet<CacheSpan> b = new TreeSet<>(new db(12));
    public long c;

    public LeastRecentlyUsedCacheEvictor(long j) {
        this.a = j;
    }

    public void onCacheInitialized() {
    }

    public void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        TreeSet<CacheSpan> treeSet = this.b;
        treeSet.add(cacheSpan);
        this.c += cacheSpan.g;
        while (this.c + 0 > this.a && !treeSet.isEmpty()) {
            cache.removeSpan(treeSet.first());
        }
    }

    public void onSpanRemoved(Cache cache, CacheSpan cacheSpan) {
        this.b.remove(cacheSpan);
        this.c -= cacheSpan.g;
    }

    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
        onSpanRemoved(cache, cacheSpan);
        onSpanAdded(cache, cacheSpan2);
    }

    public void onStartFile(Cache cache, String str, long j, long j2) {
        if (j2 != -1) {
            while (this.c + j2 > this.a) {
                TreeSet<CacheSpan> treeSet = this.b;
                if (!treeSet.isEmpty()) {
                    cache.removeSpan(treeSet.first());
                } else {
                    return;
                }
            }
        }
    }

    public boolean requiresCacheSpanTouches() {
        return true;
    }
}
