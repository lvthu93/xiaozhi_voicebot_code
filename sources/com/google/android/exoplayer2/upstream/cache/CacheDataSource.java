package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DummyDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.PriorityDataSource;
import com.google.android.exoplayer2.upstream.TeeDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class CacheDataSource implements DataSource {
    public final Cache a;
    public final DataSource b;
    @Nullable
    public final TeeDataSource c;
    public final DataSource d;
    public final CacheKeyFactory e;
    @Nullable
    public final EventListener f;
    public final boolean g;
    public final boolean h;
    public final boolean i;
    @Nullable
    public Uri j;
    @Nullable
    public DataSpec k;
    @Nullable
    public DataSpec l;
    @Nullable
    public DataSource m;
    public long n;
    public long o;
    public long p;
    @Nullable
    public CacheSpan q;
    public boolean r;
    public boolean s;
    public long t;
    public long u;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface CacheIgnoredReason {
    }

    public interface EventListener {
        void onCacheIgnored(int i);

        void onCachedBytesRead(long j, long j2);
    }

    public static final class Factory implements DataSource.Factory {
        public Cache a;
        public DataSource.Factory b = new FileDataSource.Factory();
        @Nullable
        public DataSink.Factory c;
        public CacheKeyFactory d = CacheKeyFactory.e;
        public boolean e;
        @Nullable
        public DataSource.Factory f;
        @Nullable
        public PriorityTaskManager g;
        public int h;
        public int i;
        @Nullable
        public EventListener j;

        public final CacheDataSource a(@Nullable DataSource dataSource, int i2, int i3) {
            DataSink dataSink;
            Cache cache = (Cache) Assertions.checkNotNull(this.a);
            if (this.e || dataSource == null) {
                dataSink = null;
            } else {
                DataSink.Factory factory = this.c;
                if (factory != null) {
                    dataSink = factory.createDataSink();
                } else {
                    dataSink = new CacheDataSink.Factory().setCache(cache).createDataSink();
                }
            }
            return new CacheDataSource(cache, dataSource, this.b.createDataSource(), dataSink, this.d, i2, this.g, i3, this.j);
        }

        public CacheDataSource createDataSourceForDownloading() {
            DataSource dataSource;
            DataSource.Factory factory = this.f;
            if (factory != null) {
                dataSource = factory.createDataSource();
            } else {
                dataSource = null;
            }
            return a(dataSource, this.i | 1, NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }

        public CacheDataSource createDataSourceForRemovingDownload() {
            return a((DataSource) null, this.i | 1, NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }

        @Nullable
        public Cache getCache() {
            return this.a;
        }

        public CacheKeyFactory getCacheKeyFactory() {
            return this.d;
        }

        @Nullable
        public PriorityTaskManager getUpstreamPriorityTaskManager() {
            return this.g;
        }

        public Factory setCache(Cache cache) {
            this.a = cache;
            return this;
        }

        public Factory setCacheKeyFactory(CacheKeyFactory cacheKeyFactory) {
            this.d = cacheKeyFactory;
            return this;
        }

        public Factory setCacheReadDataSourceFactory(DataSource.Factory factory) {
            this.b = factory;
            return this;
        }

        public Factory setCacheWriteDataSinkFactory(@Nullable DataSink.Factory factory) {
            boolean z;
            this.c = factory;
            if (factory == null) {
                z = true;
            } else {
                z = false;
            }
            this.e = z;
            return this;
        }

        public Factory setEventListener(@Nullable EventListener eventListener) {
            this.j = eventListener;
            return this;
        }

        public Factory setFlags(int i2) {
            this.i = i2;
            return this;
        }

        public Factory setUpstreamDataSourceFactory(@Nullable DataSource.Factory factory) {
            this.f = factory;
            return this;
        }

        public Factory setUpstreamPriority(int i2) {
            this.h = i2;
            return this;
        }

        public Factory setUpstreamPriorityTaskManager(@Nullable PriorityTaskManager priorityTaskManager) {
            this.g = priorityTaskManager;
            return this;
        }

        public CacheDataSource createDataSource() {
            DataSource.Factory factory = this.f;
            return a(factory != null ? factory.createDataSource() : null, this.i, this.h);
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource) {
        this(cache, dataSource, 0);
    }

    public final void a() throws IOException {
        Cache cache = this.a;
        DataSource dataSource = this.m;
        if (dataSource != null) {
            try {
                dataSource.close();
            } finally {
                this.l = null;
                this.m = null;
                CacheSpan cacheSpan = this.q;
                if (cacheSpan != null) {
                    cache.releaseHoleSpan(cacheSpan);
                    this.q = null;
                }
            }
        }
    }

    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.b.addTransferListener(transferListener);
        this.d.addTransferListener(transferListener);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00f7  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x014f  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0172  */
    /* JADX WARNING: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(com.google.android.exoplayer2.upstream.DataSpec r21, boolean r22) throws java.io.IOException {
        /*
            r20 = this;
            r1 = r20
            r0 = r21
            java.lang.String r2 = r0.h
            java.lang.Object r2 = com.google.android.exoplayer2.util.Util.castNonNull(r2)
            java.lang.String r2 = (java.lang.String) r2
            boolean r3 = r1.s
            if (r3 == 0) goto L_0x0012
            r3 = 0
            goto L_0x003a
        L_0x0012:
            boolean r3 = r1.g
            if (r3 == 0) goto L_0x002f
            com.google.android.exoplayer2.upstream.cache.Cache r3 = r1.a     // Catch:{ InterruptedException -> 0x0022 }
            long r5 = r1.o     // Catch:{ InterruptedException -> 0x0022 }
            long r7 = r1.p     // Catch:{ InterruptedException -> 0x0022 }
            r4 = r2
            com.google.android.exoplayer2.upstream.cache.CacheSpan r3 = r3.startReadWrite(r4, r5, r7)     // Catch:{ InterruptedException -> 0x0022 }
            goto L_0x003a
        L_0x0022:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
            java.io.InterruptedIOException r0 = new java.io.InterruptedIOException
            r0.<init>()
            throw r0
        L_0x002f:
            com.google.android.exoplayer2.upstream.cache.Cache r3 = r1.a
            long r5 = r1.o
            long r7 = r1.p
            r4 = r2
            com.google.android.exoplayer2.upstream.cache.CacheSpan r3 = r3.startReadWriteNonBlocking(r4, r5, r7)
        L_0x003a:
            com.google.android.exoplayer2.upstream.DataSource r4 = r1.b
            com.google.android.exoplayer2.upstream.TeeDataSource r5 = r1.c
            r6 = -1
            com.google.android.exoplayer2.upstream.cache.Cache r8 = r1.a
            com.google.android.exoplayer2.upstream.DataSource r10 = r1.d
            if (r3 != 0) goto L_0x0062
            com.google.android.exoplayer2.upstream.DataSpec$Builder r11 = r21.buildUpon()
            long r12 = r1.o
            com.google.android.exoplayer2.upstream.DataSpec$Builder r11 = r11.setPosition(r12)
            long r12 = r1.p
            com.google.android.exoplayer2.upstream.DataSpec$Builder r11 = r11.setLength(r12)
            com.google.android.exoplayer2.upstream.DataSpec r11 = r11.build()
            r17 = r4
            r18 = r5
            r16 = r10
            goto L_0x00db
        L_0x0062:
            boolean r11 = r3.h
            long r12 = r3.g
            if (r11 == 0) goto L_0x00a5
            java.io.File r11 = r3.i
            java.lang.Object r11 = com.google.android.exoplayer2.util.Util.castNonNull(r11)
            java.io.File r11 = (java.io.File) r11
            android.net.Uri r11 = android.net.Uri.fromFile(r11)
            long r14 = r1.o
            r16 = r10
            long r9 = r3.f
            long r14 = r14 - r9
            long r12 = r12 - r14
            r17 = r4
            r18 = r5
            long r4 = r1.p
            int r19 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r19 == 0) goto L_0x008a
            long r12 = java.lang.Math.min(r12, r4)
        L_0x008a:
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r21.buildUpon()
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r4.setUri((android.net.Uri) r11)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r4.setUriPositionOffset(r9)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r4.setPosition(r14)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r4.setLength(r12)
            com.google.android.exoplayer2.upstream.DataSpec r11 = r4.build()
            r10 = r17
            goto L_0x00db
        L_0x00a5:
            r17 = r4
            r18 = r5
            r16 = r10
            boolean r4 = r3.isOpenEnded()
            if (r4 == 0) goto L_0x00b4
            long r12 = r1.p
            goto L_0x00be
        L_0x00b4:
            long r4 = r1.p
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 == 0) goto L_0x00be
            long r12 = java.lang.Math.min(r12, r4)
        L_0x00be:
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r21.buildUpon()
            long r9 = r1.o
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r4.setPosition(r9)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r4 = r4.setLength(r12)
            com.google.android.exoplayer2.upstream.DataSpec r11 = r4.build()
            if (r18 == 0) goto L_0x00d5
            r10 = r18
            goto L_0x00db
        L_0x00d5:
            r8.releaseHoleSpan(r3)
            r10 = r16
            r3 = 0
        L_0x00db:
            boolean r4 = r1.s
            if (r4 != 0) goto L_0x00ea
            r4 = r16
            if (r10 != r4) goto L_0x00ec
            long r12 = r1.o
            r14 = 102400(0x19000, double:5.05923E-319)
            long r12 = r12 + r14
            goto L_0x00f1
        L_0x00ea:
            r4 = r16
        L_0x00ec:
            r12 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
        L_0x00f1:
            r1.u = r12
            r5 = 0
            r9 = 1
            if (r22 == 0) goto L_0x011a
            com.google.android.exoplayer2.upstream.DataSource r12 = r1.m
            if (r12 != r4) goto L_0x00fd
            r12 = 1
            goto L_0x00fe
        L_0x00fd:
            r12 = 0
        L_0x00fe:
            com.google.android.exoplayer2.util.Assertions.checkState(r12)
            if (r10 != r4) goto L_0x0104
            return
        L_0x0104:
            r20.a()     // Catch:{ all -> 0x0108 }
            goto L_0x011a
        L_0x0108:
            r0 = move-exception
            r2 = r0
            java.lang.Object r0 = com.google.android.exoplayer2.util.Util.castNonNull(r3)
            com.google.android.exoplayer2.upstream.cache.CacheSpan r0 = (com.google.android.exoplayer2.upstream.cache.CacheSpan) r0
            boolean r0 = r0.isHoleSpan()
            if (r0 == 0) goto L_0x0119
            r8.releaseHoleSpan(r3)
        L_0x0119:
            throw r2
        L_0x011a:
            if (r3 == 0) goto L_0x0124
            boolean r4 = r3.isHoleSpan()
            if (r4 == 0) goto L_0x0124
            r1.q = r3
        L_0x0124:
            r1.m = r10
            r1.l = r11
            r3 = 0
            r1.n = r3
            long r3 = r10.open(r11)
            com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations r12 = new com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations
            r12.<init>()
            long r13 = r11.g
            int r11 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r11 != 0) goto L_0x0147
            int r11 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r11 == 0) goto L_0x0147
            r1.p = r3
            long r6 = r1.o
            long r6 = r6 + r3
            com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations.setContentLength(r12, r6)
        L_0x0147:
            com.google.android.exoplayer2.upstream.DataSource r3 = r1.m
            r4 = r17
            if (r3 != r4) goto L_0x014f
            r3 = 1
            goto L_0x0150
        L_0x014f:
            r3 = 0
        L_0x0150:
            r3 = r3 ^ r9
            if (r3 == 0) goto L_0x0169
            android.net.Uri r3 = r10.getUri()
            r1.j = r3
            android.net.Uri r0 = r0.a
            boolean r0 = r0.equals(r3)
            r0 = r0 ^ r9
            if (r0 == 0) goto L_0x0165
            android.net.Uri r0 = r1.j
            goto L_0x0166
        L_0x0165:
            r0 = 0
        L_0x0166:
            com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations.setRedirectedUri(r12, r0)
        L_0x0169:
            com.google.android.exoplayer2.upstream.DataSource r0 = r1.m
            r3 = r18
            if (r0 != r3) goto L_0x0170
            r5 = 1
        L_0x0170:
            if (r5 == 0) goto L_0x0175
            r8.applyContentMetadataMutations(r2, r12)
        L_0x0175:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheDataSource.b(com.google.android.exoplayer2.upstream.DataSpec, boolean):void");
    }

    public void close() throws IOException {
        boolean z;
        this.k = null;
        this.j = null;
        this.o = 0;
        EventListener eventListener = this.f;
        if (eventListener != null && this.t > 0) {
            eventListener.onCachedBytesRead(this.a.getCacheSpace(), this.t);
            this.t = 0;
        }
        try {
            a();
        } catch (Throwable th) {
            if (this.m == this.b) {
                z = true;
            } else {
                z = false;
            }
            if (z || (th instanceof Cache.CacheException)) {
                this.r = true;
            }
            throw th;
        }
    }

    public Cache getCache() {
        return this.a;
    }

    public CacheKeyFactory getCacheKeyFactory() {
        return this.e;
    }

    public Map<String, List<String>> getResponseHeaders() {
        boolean z;
        if (this.m == this.b) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return this.d.getResponseHeaders();
        }
        return Collections.emptyMap();
    }

    @Nullable
    public Uri getUri() {
        return this.j;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0049 A[Catch:{ all -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004b A[Catch:{ all -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005d A[Catch:{ all -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0060 A[Catch:{ all -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0080 A[Catch:{ all -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x009d A[Catch:{ all -> 0x00a1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x009e A[Catch:{ all -> 0x00a1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long open(com.google.android.exoplayer2.upstream.DataSpec r16) throws java.io.IOException {
        /*
            r15 = this;
            r1 = r15
            r0 = r16
            com.google.android.exoplayer2.upstream.cache.Cache r2 = r1.a
            r3 = 0
            com.google.android.exoplayer2.upstream.cache.CacheKeyFactory r5 = r1.e     // Catch:{ all -> 0x00a1 }
            java.lang.String r5 = r5.buildCacheKey(r0)     // Catch:{ all -> 0x00a1 }
            com.google.android.exoplayer2.upstream.DataSpec$Builder r6 = r16.buildUpon()     // Catch:{ all -> 0x00a1 }
            long r7 = r0.f
            com.google.android.exoplayer2.upstream.DataSpec$Builder r6 = r6.setKey(r5)     // Catch:{ all -> 0x00a1 }
            com.google.android.exoplayer2.upstream.DataSpec r6 = r6.build()     // Catch:{ all -> 0x00a1 }
            r1.k = r6     // Catch:{ all -> 0x00a1 }
            android.net.Uri r9 = r6.a     // Catch:{ all -> 0x00a1 }
            com.google.android.exoplayer2.upstream.cache.ContentMetadata r10 = r2.getContentMetadata(r5)     // Catch:{ all -> 0x00a1 }
            android.net.Uri r10 = defpackage.t0.b(r10)     // Catch:{ all -> 0x00a1 }
            if (r10 == 0) goto L_0x0029
            r9 = r10
        L_0x0029:
            r1.j = r9     // Catch:{ all -> 0x00a1 }
            r1.o = r7     // Catch:{ all -> 0x00a1 }
            boolean r9 = r1.h     // Catch:{ all -> 0x00a1 }
            r10 = -1
            r11 = -1
            long r13 = r0.g
            if (r9 == 0) goto L_0x003c
            boolean r0 = r1.r     // Catch:{ all -> 0x00a1 }
            if (r0 == 0) goto L_0x003c
            r0 = 0
            goto L_0x0047
        L_0x003c:
            boolean r0 = r1.i     // Catch:{ all -> 0x00a1 }
            if (r0 == 0) goto L_0x0046
            int r0 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x0046
            r0 = 1
            goto L_0x0047
        L_0x0046:
            r0 = -1
        L_0x0047:
            if (r0 == r10) goto L_0x004b
            r9 = 1
            goto L_0x004c
        L_0x004b:
            r9 = 0
        L_0x004c:
            r1.s = r9     // Catch:{ all -> 0x00a1 }
            if (r9 == 0) goto L_0x0057
            com.google.android.exoplayer2.upstream.cache.CacheDataSource$EventListener r9 = r1.f     // Catch:{ all -> 0x00a1 }
            if (r9 == 0) goto L_0x0057
            r9.onCacheIgnored(r0)     // Catch:{ all -> 0x00a1 }
        L_0x0057:
            boolean r0 = r1.s     // Catch:{ all -> 0x00a1 }
            r9 = 0
            if (r0 == 0) goto L_0x0060
            r1.p = r11     // Catch:{ all -> 0x00a1 }
            goto L_0x007c
        L_0x0060:
            com.google.android.exoplayer2.upstream.cache.ContentMetadata r0 = r2.getContentMetadata(r5)     // Catch:{ all -> 0x00a1 }
            long r4 = defpackage.t0.a(r0)     // Catch:{ all -> 0x00a1 }
            r1.p = r4     // Catch:{ all -> 0x00a1 }
            int r0 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x007c
            long r4 = r4 - r7
            r1.p = r4     // Catch:{ all -> 0x00a1 }
            int r0 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r0 < 0) goto L_0x0076
            goto L_0x007c
        L_0x0076:
            com.google.android.exoplayer2.upstream.DataSourceException r0 = new com.google.android.exoplayer2.upstream.DataSourceException     // Catch:{ all -> 0x00a1 }
            r0.<init>(r3)     // Catch:{ all -> 0x00a1 }
            throw r0     // Catch:{ all -> 0x00a1 }
        L_0x007c:
            int r0 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x008e
            long r4 = r1.p     // Catch:{ all -> 0x00a1 }
            int r7 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r7 != 0) goto L_0x0088
            r4 = r13
            goto L_0x008c
        L_0x0088:
            long r4 = java.lang.Math.min(r4, r13)     // Catch:{ all -> 0x00a1 }
        L_0x008c:
            r1.p = r4     // Catch:{ all -> 0x00a1 }
        L_0x008e:
            long r4 = r1.p     // Catch:{ all -> 0x00a1 }
            int r7 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r7 > 0) goto L_0x0098
            int r7 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r7 != 0) goto L_0x009b
        L_0x0098:
            r15.b(r6, r3)     // Catch:{ all -> 0x00a1 }
        L_0x009b:
            if (r0 == 0) goto L_0x009e
            goto L_0x00a0
        L_0x009e:
            long r13 = r1.p     // Catch:{ all -> 0x00a1 }
        L_0x00a0:
            return r13
        L_0x00a1:
            r0 = move-exception
            com.google.android.exoplayer2.upstream.DataSource r4 = r1.m
            com.google.android.exoplayer2.upstream.DataSource r5 = r1.b
            if (r4 != r5) goto L_0x00a9
            r3 = 1
        L_0x00a9:
            if (r3 != 0) goto L_0x00af
            boolean r3 = r0 instanceof com.google.android.exoplayer2.upstream.cache.Cache.CacheException
            if (r3 == 0) goto L_0x00b2
        L_0x00af:
            r2 = 1
            r1.r = r2
        L_0x00b2:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheDataSource.open(com.google.android.exoplayer2.upstream.DataSpec):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(byte[] r16, int r17, int r18) throws java.io.IOException {
        /*
            r15 = this;
            r1 = r15
            r0 = r18
            com.google.android.exoplayer2.upstream.DataSource r2 = r1.b
            com.google.android.exoplayer2.upstream.DataSpec r3 = r1.k
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r3)
            com.google.android.exoplayer2.upstream.DataSpec r3 = (com.google.android.exoplayer2.upstream.DataSpec) r3
            com.google.android.exoplayer2.upstream.DataSpec r4 = r1.l
            java.lang.Object r4 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r4)
            com.google.android.exoplayer2.upstream.DataSpec r4 = (com.google.android.exoplayer2.upstream.DataSpec) r4
            r5 = 0
            if (r0 != 0) goto L_0x0019
            return r5
        L_0x0019:
            long r6 = r1.p
            r8 = -1
            r9 = 0
            int r11 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r11 != 0) goto L_0x0023
            return r8
        L_0x0023:
            r6 = 1
            long r11 = r1.o     // Catch:{ all -> 0x00be }
            long r13 = r1.u     // Catch:{ all -> 0x00be }
            int r7 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r7 < 0) goto L_0x002f
            r15.b(r3, r6)     // Catch:{ all -> 0x00be }
        L_0x002f:
            com.google.android.exoplayer2.upstream.DataSource r7 = r1.m     // Catch:{ all -> 0x00be }
            java.lang.Object r7 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r7)     // Catch:{ all -> 0x00be }
            com.google.android.exoplayer2.upstream.DataSource r7 = (com.google.android.exoplayer2.upstream.DataSource) r7     // Catch:{ all -> 0x00be }
            r11 = r16
            r12 = r17
            int r7 = r7.read(r11, r12, r0)     // Catch:{ all -> 0x00be }
            r13 = -1
            if (r7 == r8) goto L_0x0067
            com.google.android.exoplayer2.upstream.DataSource r0 = r1.m     // Catch:{ all -> 0x00be }
            if (r0 != r2) goto L_0x0049
            r0 = 1
            goto L_0x004a
        L_0x0049:
            r0 = 0
        L_0x004a:
            if (r0 == 0) goto L_0x0052
            long r3 = r1.t     // Catch:{ all -> 0x00be }
            long r8 = (long) r7     // Catch:{ all -> 0x00be }
            long r3 = r3 + r8
            r1.t = r3     // Catch:{ all -> 0x00be }
        L_0x0052:
            long r3 = r1.o     // Catch:{ all -> 0x00be }
            long r8 = (long) r7     // Catch:{ all -> 0x00be }
            long r3 = r3 + r8
            r1.o = r3     // Catch:{ all -> 0x00be }
            long r3 = r1.n     // Catch:{ all -> 0x00be }
            long r3 = r3 + r8
            r1.n = r3     // Catch:{ all -> 0x00be }
            long r3 = r1.p     // Catch:{ all -> 0x00be }
            int r0 = (r3 > r13 ? 1 : (r3 == r13 ? 0 : -1))
            if (r0 == 0) goto L_0x00af
            long r3 = r3 - r8
            r1.p = r3     // Catch:{ all -> 0x00be }
            goto L_0x00af
        L_0x0067:
            com.google.android.exoplayer2.upstream.DataSource r8 = r1.m     // Catch:{ all -> 0x00be }
            if (r8 != r2) goto L_0x006d
            r8 = 1
            goto L_0x006e
        L_0x006d:
            r8 = 0
        L_0x006e:
            r8 = r8 ^ r6
            if (r8 == 0) goto L_0x00a2
            long r5 = r4.g     // Catch:{ all -> 0x00be }
            int r4 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r4 == 0) goto L_0x007d
            long r13 = r1.n     // Catch:{ all -> 0x00be }
            int r4 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r4 >= 0) goto L_0x00a2
        L_0x007d:
            java.lang.String r0 = r3.h     // Catch:{ all -> 0x00be }
            java.lang.Object r0 = com.google.android.exoplayer2.util.Util.castNonNull(r0)     // Catch:{ all -> 0x00be }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x00be }
            r1.p = r9     // Catch:{ all -> 0x00be }
            com.google.android.exoplayer2.upstream.DataSource r3 = r1.m     // Catch:{ all -> 0x00be }
            com.google.android.exoplayer2.upstream.TeeDataSource r4 = r1.c     // Catch:{ all -> 0x00be }
            if (r3 != r4) goto L_0x008f
            r3 = 1
            goto L_0x0090
        L_0x008f:
            r3 = 0
        L_0x0090:
            if (r3 == 0) goto L_0x00af
            com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations r3 = new com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations     // Catch:{ all -> 0x00be }
            r3.<init>()     // Catch:{ all -> 0x00be }
            long r4 = r1.o     // Catch:{ all -> 0x00be }
            com.google.android.exoplayer2.upstream.cache.ContentMetadataMutations.setContentLength(r3, r4)     // Catch:{ all -> 0x00be }
            com.google.android.exoplayer2.upstream.cache.Cache r4 = r1.a     // Catch:{ all -> 0x00be }
            r4.applyContentMetadataMutations(r0, r3)     // Catch:{ all -> 0x00be }
            goto L_0x00af
        L_0x00a2:
            long r4 = r1.p     // Catch:{ all -> 0x00be }
            int r6 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r6 > 0) goto L_0x00b0
            r9 = -1
            int r6 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r6 != 0) goto L_0x00af
            goto L_0x00b0
        L_0x00af:
            return r7
        L_0x00b0:
            r15.a()     // Catch:{ all -> 0x00be }
            r4 = 0
            r15.b(r3, r4)     // Catch:{ all -> 0x00bc }
            int r0 = r15.read(r16, r17, r18)     // Catch:{ all -> 0x00bc }
            return r0
        L_0x00bc:
            r0 = move-exception
            goto L_0x00c0
        L_0x00be:
            r0 = move-exception
            r4 = 0
        L_0x00c0:
            com.google.android.exoplayer2.upstream.DataSource r3 = r1.m
            if (r3 != r2) goto L_0x00c6
            r5 = 1
            goto L_0x00c7
        L_0x00c6:
            r5 = 0
        L_0x00c7:
            if (r5 != 0) goto L_0x00cd
            boolean r2 = r0 instanceof com.google.android.exoplayer2.upstream.cache.Cache.CacheException
            if (r2 == 0) goto L_0x00d0
        L_0x00cd:
            r2 = 1
            r1.r = r2
        L_0x00d0:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheDataSource.read(byte[], int, int):int");
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource, int i2) {
        this(cache, dataSource, new FileDataSource(), new CacheDataSink(cache, 5242880), i2, (EventListener) null);
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource, DataSource dataSource2, @Nullable DataSink dataSink, int i2, @Nullable EventListener eventListener) {
        this(cache, dataSource, dataSource2, dataSink, i2, eventListener, (CacheKeyFactory) null);
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource, DataSource dataSource2, @Nullable DataSink dataSink, int i2, @Nullable EventListener eventListener, @Nullable CacheKeyFactory cacheKeyFactory) {
        this(cache, dataSource, dataSource2, dataSink, cacheKeyFactory, i2, (PriorityTaskManager) null, 0, eventListener);
    }

    public CacheDataSource(Cache cache, @Nullable DataSource dataSource, DataSource dataSource2, @Nullable DataSink dataSink, @Nullable CacheKeyFactory cacheKeyFactory, int i2, @Nullable PriorityTaskManager priorityTaskManager, int i3, @Nullable EventListener eventListener) {
        this.a = cache;
        this.b = dataSource2;
        this.e = cacheKeyFactory == null ? CacheKeyFactory.e : cacheKeyFactory;
        boolean z = false;
        this.g = (i2 & 1) != 0;
        this.h = (i2 & 2) != 0;
        this.i = (i2 & 4) != 0 ? true : z;
        TeeDataSource teeDataSource = null;
        if (dataSource != null) {
            dataSource = priorityTaskManager != null ? new PriorityDataSource(dataSource, priorityTaskManager, i3) : dataSource;
            this.d = dataSource;
            this.c = dataSink != null ? new TeeDataSource(dataSource, dataSink) : teeDataSource;
        } else {
            this.d = DummyDataSource.a;
            this.c = null;
        }
        this.f = eventListener;
    }
}
