package com.google.android.exoplayer2.upstream.cache;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSpec;

public final class CacheWriter {
    public final CacheDataSource a;
    public final Cache b;
    public final DataSpec c;
    public final String d;
    public final byte[] e;
    @Nullable
    public final ProgressListener f;
    public long g;
    public long h;
    public long i;
    public volatile boolean j;

    public interface ProgressListener {
        void onProgress(long j, long j2, long j3);
    }

    public CacheWriter(CacheDataSource cacheDataSource, DataSpec dataSpec, @Nullable byte[] bArr, @Nullable ProgressListener progressListener) {
        this.a = cacheDataSource;
        this.b = cacheDataSource.getCache();
        this.c = dataSpec;
        this.e = bArr == null ? new byte[131072] : bArr;
        this.f = progressListener;
        this.d = cacheDataSource.getCacheKeyFactory().buildCacheKey(dataSpec);
        this.g = dataSpec.f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00fa A[Catch:{ IOException -> 0x0117 }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00fb A[Catch:{ IOException -> 0x0117 }] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x011f A[Catch:{ IOException -> 0x0117 }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x015b A[Catch:{ IOException -> 0x0117 }] */
    @androidx.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cache() throws java.io.IOException {
        /*
            r21 = this;
            r1 = r21
            boolean r0 = r1.j
            if (r0 != 0) goto L_0x0198
            com.google.android.exoplayer2.upstream.cache.Cache r2 = r1.b
            java.lang.String r3 = r1.d
            com.google.android.exoplayer2.upstream.DataSpec r0 = r1.c
            long r4 = r0.f
            long r6 = r0.g
            long r2 = r2.getCachedBytes(r3, r4, r6)
            r1.i = r2
            long r2 = r0.g
            r4 = -1
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x0024
            long r6 = r0.f
            long r6 = r6 + r2
            r1.h = r6
            goto L_0x0037
        L_0x0024:
            com.google.android.exoplayer2.upstream.cache.Cache r2 = r1.b
            java.lang.String r3 = r1.d
            com.google.android.exoplayer2.upstream.cache.ContentMetadata r2 = r2.getContentMetadata(r3)
            long r2 = defpackage.t0.a(r2)
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x0035
            r2 = r4
        L_0x0035:
            r1.h = r2
        L_0x0037:
            com.google.android.exoplayer2.upstream.cache.CacheWriter$ProgressListener r6 = r1.f
            if (r6 == 0) goto L_0x0050
            long r2 = r1.h
            int r7 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r7 != 0) goto L_0x0043
            r7 = r4
            goto L_0x0049
        L_0x0043:
            com.google.android.exoplayer2.upstream.DataSpec r7 = r1.c
            long r7 = r7.f
            long r2 = r2 - r7
            r7 = r2
        L_0x0049:
            long r9 = r1.i
            r11 = 0
            r6.onProgress(r7, r9, r11)
        L_0x0050:
            long r2 = r1.h
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 == 0) goto L_0x005e
            long r6 = r1.g
            int r8 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r8 >= 0) goto L_0x005d
            goto L_0x005e
        L_0x005d:
            return
        L_0x005e:
            boolean r2 = r1.j
            if (r2 != 0) goto L_0x0192
            long r2 = r1.h
            r6 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 != 0) goto L_0x006f
            r12 = r6
            goto L_0x0073
        L_0x006f:
            long r8 = r1.g
            long r2 = r2 - r8
            r12 = r2
        L_0x0073:
            com.google.android.exoplayer2.upstream.cache.Cache r8 = r1.b
            java.lang.String r9 = r1.d
            long r10 = r1.g
            long r2 = r8.getCachedLength(r9, r10, r12)
            r8 = 0
            int r10 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r10 <= 0) goto L_0x008c
            long r6 = r1.g
            long r6 = r6 + r2
            r1.g = r6
            r17 = r4
            goto L_0x018e
        L_0x008c:
            long r2 = -r2
            int r8 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x0092
            r2 = r4
        L_0x0092:
            long r6 = r1.g
            long r8 = r6 + r2
            long r10 = r1.h
            r12 = 1
            r13 = 0
            int r14 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r14 == 0) goto L_0x00a5
            int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r8 != 0) goto L_0x00a3
            goto L_0x00a5
        L_0x00a3:
            r8 = 0
            goto L_0x00a6
        L_0x00a5:
            r8 = 1
        L_0x00a6:
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r9 = r1.a
            int r10 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r10 == 0) goto L_0x00c4
            com.google.android.exoplayer2.upstream.DataSpec$Builder r10 = r0.buildUpon()
            com.google.android.exoplayer2.upstream.DataSpec$Builder r10 = r10.setPosition(r6)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r2 = r10.setLength(r2)
            com.google.android.exoplayer2.upstream.DataSpec r2 = r2.build()
            long r2 = r9.open(r2)     // Catch:{ IOException -> 0x00c1 }
            goto L_0x00c6
        L_0x00c1:
            com.google.android.exoplayer2.util.Util.closeQuietly((com.google.android.exoplayer2.upstream.DataSource) r9)
        L_0x00c4:
            r2 = r4
            r12 = 0
        L_0x00c6:
            if (r12 != 0) goto L_0x00ed
            boolean r2 = r1.j
            if (r2 != 0) goto L_0x00e7
            com.google.android.exoplayer2.upstream.DataSpec$Builder r2 = r0.buildUpon()
            com.google.android.exoplayer2.upstream.DataSpec$Builder r2 = r2.setPosition(r6)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r2 = r2.setLength(r4)
            com.google.android.exoplayer2.upstream.DataSpec r2 = r2.build()
            long r2 = r9.open(r2)     // Catch:{ IOException -> 0x00e1 }
            goto L_0x00ed
        L_0x00e1:
            r0 = move-exception
            r2 = r0
            com.google.android.exoplayer2.util.Util.closeQuietly((com.google.android.exoplayer2.upstream.DataSource) r9)
            throw r2
        L_0x00e7:
            java.io.InterruptedIOException r0 = new java.io.InterruptedIOException
            r0.<init>()
            throw r0
        L_0x00ed:
            if (r8 == 0) goto L_0x011a
            int r10 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r10 == 0) goto L_0x011a
            long r2 = r2 + r6
            long r10 = r1.h     // Catch:{ IOException -> 0x0117 }
            int r12 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r12 != 0) goto L_0x00fb
            goto L_0x011a
        L_0x00fb:
            r1.h = r2     // Catch:{ IOException -> 0x0117 }
            com.google.android.exoplayer2.upstream.cache.CacheWriter$ProgressListener r14 = r1.f     // Catch:{ IOException -> 0x0117 }
            if (r14 == 0) goto L_0x011a
            int r10 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r10 != 0) goto L_0x0107
            r15 = r4
            goto L_0x010d
        L_0x0107:
            com.google.android.exoplayer2.upstream.DataSpec r10 = r1.c     // Catch:{ IOException -> 0x0117 }
            long r10 = r10.f     // Catch:{ IOException -> 0x0117 }
            long r2 = r2 - r10
            r15 = r2
        L_0x010d:
            long r2 = r1.i     // Catch:{ IOException -> 0x0117 }
            r19 = 0
            r17 = r2
            r14.onProgress(r15, r17, r19)     // Catch:{ IOException -> 0x0117 }
            goto L_0x011a
        L_0x0117:
            r0 = move-exception
            goto L_0x0181
        L_0x011a:
            r2 = 0
            r3 = 0
        L_0x011c:
            r10 = -1
            if (r2 == r10) goto L_0x0159
            boolean r2 = r1.j     // Catch:{ IOException -> 0x0117 }
            if (r2 != 0) goto L_0x0153
            byte[] r2 = r1.e     // Catch:{ IOException -> 0x0117 }
            int r11 = r2.length     // Catch:{ IOException -> 0x0117 }
            int r2 = r9.read(r2, r13, r11)     // Catch:{ IOException -> 0x0117 }
            if (r2 == r10) goto L_0x011c
            long r10 = (long) r2     // Catch:{ IOException -> 0x0117 }
            long r14 = r1.i     // Catch:{ IOException -> 0x0117 }
            long r14 = r14 + r10
            r1.i = r14     // Catch:{ IOException -> 0x0117 }
            com.google.android.exoplayer2.upstream.cache.CacheWriter$ProgressListener r12 = r1.f     // Catch:{ IOException -> 0x0117 }
            if (r12 == 0) goto L_0x014e
            r16 = r14
            long r13 = r1.h     // Catch:{ IOException -> 0x0117 }
            int r15 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r15 != 0) goto L_0x013f
            goto L_0x0145
        L_0x013f:
            com.google.android.exoplayer2.upstream.DataSpec r15 = r1.c     // Catch:{ IOException -> 0x0117 }
            long r4 = r15.f     // Catch:{ IOException -> 0x0117 }
            long r13 = r13 - r4
            r4 = r13
        L_0x0145:
            r17 = r16
            r14 = r12
            r15 = r4
            r19 = r10
            r14.onProgress(r15, r17, r19)     // Catch:{ IOException -> 0x0117 }
        L_0x014e:
            int r3 = r3 + r2
            r4 = -1
            r13 = 0
            goto L_0x011c
        L_0x0153:
            java.io.InterruptedIOException r0 = new java.io.InterruptedIOException     // Catch:{ IOException -> 0x0117 }
            r0.<init>()     // Catch:{ IOException -> 0x0117 }
            throw r0     // Catch:{ IOException -> 0x0117 }
        L_0x0159:
            if (r8 == 0) goto L_0x0185
            long r4 = (long) r3     // Catch:{ IOException -> 0x0117 }
            long r4 = r4 + r6
            long r10 = r1.h     // Catch:{ IOException -> 0x0117 }
            int r2 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x0164
            goto L_0x0185
        L_0x0164:
            r1.h = r4     // Catch:{ IOException -> 0x0117 }
            com.google.android.exoplayer2.upstream.cache.CacheWriter$ProgressListener r10 = r1.f     // Catch:{ IOException -> 0x0117 }
            if (r10 == 0) goto L_0x0185
            r17 = -1
            int r2 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r2 != 0) goto L_0x0173
            r11 = r17
            goto L_0x0179
        L_0x0173:
            com.google.android.exoplayer2.upstream.DataSpec r2 = r1.c     // Catch:{ IOException -> 0x0117 }
            long r11 = r2.f     // Catch:{ IOException -> 0x0117 }
            long r4 = r4 - r11
            r11 = r4
        L_0x0179:
            long r13 = r1.i     // Catch:{ IOException -> 0x0117 }
            r15 = 0
            r10.onProgress(r11, r13, r15)     // Catch:{ IOException -> 0x0117 }
            goto L_0x0187
        L_0x0181:
            com.google.android.exoplayer2.util.Util.closeQuietly((com.google.android.exoplayer2.upstream.DataSource) r9)
            throw r0
        L_0x0185:
            r17 = -1
        L_0x0187:
            r9.close()
            long r2 = (long) r3
            long r6 = r6 + r2
            r1.g = r6
        L_0x018e:
            r4 = r17
            goto L_0x0050
        L_0x0192:
            java.io.InterruptedIOException r0 = new java.io.InterruptedIOException
            r0.<init>()
            throw r0
        L_0x0198:
            java.io.InterruptedIOException r0 = new java.io.InterruptedIOException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheWriter.cache():void");
    }

    public void cancel() {
        this.j = true;
    }
}
