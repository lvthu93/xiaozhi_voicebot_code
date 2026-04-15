package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

public final class CachedRegionTracker implements Cache.Listener {
    public final Cache a;
    public final String b;
    public final ChunkIndex c;
    public final TreeSet<a> d = new TreeSet<>();
    public final a e = new a(0, 0);

    public static class a implements Comparable<a> {
        public long c;
        public long f;
        public int g;

        public a(long j, long j2) {
            this.c = j;
            this.f = j2;
        }

        public int compareTo(a aVar) {
            return Util.compareLong(this.c, aVar.c);
        }
    }

    public CachedRegionTracker(Cache cache, String str, ChunkIndex chunkIndex) {
        this.a = cache;
        this.b = str;
        this.c = chunkIndex;
        synchronized (this) {
            Iterator<CacheSpan> descendingIterator = cache.addListener(str, this).descendingIterator();
            while (descendingIterator.hasNext()) {
                a(descendingIterator.next());
            }
        }
    }

    public final void a(CacheSpan cacheSpan) {
        boolean z;
        long j = cacheSpan.f;
        a aVar = new a(j, cacheSpan.g + j);
        TreeSet<a> treeSet = this.d;
        a floor = treeSet.floor(aVar);
        a ceiling = treeSet.ceiling(aVar);
        boolean z2 = false;
        if (floor == null || floor.f != aVar.c) {
            z = false;
        } else {
            z = true;
        }
        if (ceiling != null && aVar.f == ceiling.c) {
            z2 = true;
        }
        if (z2) {
            if (z) {
                floor.f = ceiling.f;
                floor.g = ceiling.g;
            } else {
                aVar.f = ceiling.f;
                aVar.g = ceiling.g;
                treeSet.add(aVar);
            }
            treeSet.remove(ceiling);
            return;
        }
        ChunkIndex chunkIndex = this.c;
        if (z) {
            floor.f = aVar.f;
            int i = floor.g;
            while (i < chunkIndex.a - 1) {
                int i2 = i + 1;
                if (chunkIndex.c[i2] > floor.f) {
                    break;
                }
                i = i2;
            }
            floor.g = i;
            return;
        }
        int binarySearch = Arrays.binarySearch(chunkIndex.c, aVar.f);
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 2;
        }
        aVar.g = binarySearch;
        treeSet.add(aVar);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0051, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int getRegionEndTimeMs(long r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a r0 = r8.e     // Catch:{ all -> 0x0052 }
            r0.c = r9     // Catch:{ all -> 0x0052 }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a> r1 = r8.d     // Catch:{ all -> 0x0052 }
            java.lang.Object r0 = r1.floor(r0)     // Catch:{ all -> 0x0052 }
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a r0 = (com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.a) r0     // Catch:{ all -> 0x0052 }
            r1 = -1
            if (r0 == 0) goto L_0x0050
            long r2 = r0.f     // Catch:{ all -> 0x0052 }
            int r4 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r4 > 0) goto L_0x0050
            int r9 = r0.g     // Catch:{ all -> 0x0052 }
            if (r9 != r1) goto L_0x001b
            goto L_0x0050
        L_0x001b:
            com.google.android.exoplayer2.extractor.ChunkIndex r10 = r8.c     // Catch:{ all -> 0x0052 }
            int r0 = r10.a     // Catch:{ all -> 0x0052 }
            int r0 = r0 + -1
            if (r9 != r0) goto L_0x0034
            long[] r0 = r10.c     // Catch:{ all -> 0x0052 }
            r4 = r0[r9]     // Catch:{ all -> 0x0052 }
            int[] r0 = r10.b     // Catch:{ all -> 0x0052 }
            r0 = r0[r9]     // Catch:{ all -> 0x0052 }
            long r0 = (long) r0
            long r4 = r4 + r0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x0034
            monitor-exit(r8)
            r9 = -2
            return r9
        L_0x0034:
            long[] r0 = r10.d     // Catch:{ all -> 0x0052 }
            r4 = r0[r9]     // Catch:{ all -> 0x0052 }
            long[] r0 = r10.c     // Catch:{ all -> 0x0052 }
            r6 = r0[r9]     // Catch:{ all -> 0x0052 }
            long r2 = r2 - r6
            long r4 = r4 * r2
            int[] r0 = r10.b     // Catch:{ all -> 0x0052 }
            r0 = r0[r9]     // Catch:{ all -> 0x0052 }
            long r0 = (long) r0     // Catch:{ all -> 0x0052 }
            long r4 = r4 / r0
            long[] r10 = r10.e     // Catch:{ all -> 0x0052 }
            r9 = r10[r9]     // Catch:{ all -> 0x0052 }
            long r9 = r9 + r4
            r0 = 1000(0x3e8, double:4.94E-321)
            long r9 = r9 / r0
            int r10 = (int) r9
            monitor-exit(r8)
            return r10
        L_0x0050:
            monitor-exit(r8)
            return r1
        L_0x0052:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.getRegionEndTimeMs(long):int");
    }

    public synchronized void onSpanAdded(Cache cache, CacheSpan cacheSpan) {
        a(cacheSpan);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0060, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void onSpanRemoved(com.google.android.exoplayer2.upstream.cache.Cache r7, com.google.android.exoplayer2.upstream.cache.CacheSpan r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a r7 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a     // Catch:{ all -> 0x0061 }
            long r0 = r8.f     // Catch:{ all -> 0x0061 }
            long r2 = r8.g     // Catch:{ all -> 0x0061 }
            long r2 = r2 + r0
            r7.<init>(r0, r2)     // Catch:{ all -> 0x0061 }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a> r8 = r6.d     // Catch:{ all -> 0x0061 }
            java.lang.Object r8 = r8.floor(r7)     // Catch:{ all -> 0x0061 }
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a r8 = (com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.a) r8     // Catch:{ all -> 0x0061 }
            if (r8 != 0) goto L_0x001e
            java.lang.String r7 = "CachedRegionTracker"
            java.lang.String r8 = "Removed a span we were not aware of"
            com.google.android.exoplayer2.util.Log.e(r7, r8)     // Catch:{ all -> 0x0061 }
            monitor-exit(r6)
            return
        L_0x001e:
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a> r0 = r6.d     // Catch:{ all -> 0x0061 }
            r0.remove(r8)     // Catch:{ all -> 0x0061 }
            long r0 = r8.c     // Catch:{ all -> 0x0061 }
            long r2 = r7.c     // Catch:{ all -> 0x0061 }
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 >= 0) goto L_0x0046
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a r4 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a     // Catch:{ all -> 0x0061 }
            r4.<init>(r0, r2)     // Catch:{ all -> 0x0061 }
            com.google.android.exoplayer2.extractor.ChunkIndex r0 = r6.c     // Catch:{ all -> 0x0061 }
            long[] r0 = r0.c     // Catch:{ all -> 0x0061 }
            long r1 = r4.f     // Catch:{ all -> 0x0061 }
            int r0 = java.util.Arrays.binarySearch(r0, r1)     // Catch:{ all -> 0x0061 }
            if (r0 >= 0) goto L_0x003f
            int r0 = -r0
            int r0 = r0 + -2
        L_0x003f:
            r4.g = r0     // Catch:{ all -> 0x0061 }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a> r0 = r6.d     // Catch:{ all -> 0x0061 }
            r0.add(r4)     // Catch:{ all -> 0x0061 }
        L_0x0046:
            long r0 = r8.f     // Catch:{ all -> 0x0061 }
            long r2 = r7.f     // Catch:{ all -> 0x0061 }
            int r7 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r7 <= 0) goto L_0x005f
            com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a r7 = new com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a     // Catch:{ all -> 0x0061 }
            r4 = 1
            long r2 = r2 + r4
            r7.<init>(r2, r0)     // Catch:{ all -> 0x0061 }
            int r8 = r8.g     // Catch:{ all -> 0x0061 }
            r7.g = r8     // Catch:{ all -> 0x0061 }
            java.util.TreeSet<com.google.android.exoplayer2.upstream.cache.CachedRegionTracker$a> r8 = r6.d     // Catch:{ all -> 0x0061 }
            r8.add(r7)     // Catch:{ all -> 0x0061 }
        L_0x005f:
            monitor-exit(r6)
            return
        L_0x0061:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CachedRegionTracker.onSpanRemoved(com.google.android.exoplayer2.upstream.cache.Cache, com.google.android.exoplayer2.upstream.cache.CacheSpan):void");
    }

    public void onSpanTouched(Cache cache, CacheSpan cacheSpan, CacheSpan cacheSpan2) {
    }

    public void release() {
        this.a.removeListener(this.b, this);
    }
}
