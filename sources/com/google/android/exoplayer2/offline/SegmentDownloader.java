package com.google.android.exoplayer2.offline;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public abstract class SegmentDownloader<M extends FilterableManifest<M>> implements Downloader {
    public final DataSpec a;
    public final ParsingLoadable.Parser<M> b;
    public final ArrayList<StreamKey> c;
    public final CacheDataSource.Factory d;
    public final Cache e;
    public final CacheKeyFactory f;
    @Nullable
    public final PriorityTaskManager g;
    public final Executor h;
    public final ArrayList<RunnableFutureTask<?, ?>> i = new ArrayList<>();
    public volatile boolean j;

    public static class Segment implements Comparable<Segment> {
        public final long c;
        public final DataSpec f;

        public Segment(long j, DataSpec dataSpec) {
            this.c = j;
            this.f = dataSpec;
        }

        public int compareTo(Segment segment) {
            return Util.compareLong(this.c, segment.c);
        }
    }

    public static final class a implements CacheWriter.ProgressListener {
        public final Downloader.ProgressListener c;
        public final long f;
        public final int g;
        public long h;
        public int i;

        public a(Downloader.ProgressListener progressListener, long j, int i2, long j2, int i3) {
            this.c = progressListener;
            this.f = j;
            this.g = i2;
            this.h = j2;
            this.i = i3;
        }

        public final float a() {
            long j = this.f;
            if (j != -1 && j != 0) {
                return (((float) this.h) * 100.0f) / ((float) j);
            }
            int i2 = this.g;
            if (i2 != 0) {
                return (((float) this.i) * 100.0f) / ((float) i2);
            }
            return -1.0f;
        }

        public void onProgress(long j, long j2, long j3) {
            long j4 = this.h + j3;
            this.h = j4;
            this.c.onProgress(this.f, j4, a());
        }

        public void onSegmentDownloaded() {
            this.i++;
            this.c.onProgress(this.f, this.h, a());
        }
    }

    public static final class b extends RunnableFutureTask<Void, IOException> {
        public final Segment l;
        public final CacheDataSource m;
        @Nullable
        public final a n;
        public final byte[] o;
        public final CacheWriter p;

        public b(Segment segment, CacheDataSource cacheDataSource, @Nullable a aVar, byte[] bArr) {
            this.l = segment;
            this.m = cacheDataSource;
            this.n = aVar;
            this.o = bArr;
            this.p = new CacheWriter(cacheDataSource, segment.f, bArr, aVar);
        }

        public final void a() {
            this.p.cancel();
        }

        public final Object b() throws Exception {
            this.p.cache();
            a aVar = this.n;
            if (aVar == null) {
                return null;
            }
            aVar.onSegmentDownloaded();
            return null;
        }
    }

    public SegmentDownloader(MediaItem mediaItem, ParsingLoadable.Parser<M> parser, CacheDataSource.Factory factory, Executor executor) {
        Assertions.checkNotNull(mediaItem.f);
        MediaItem.PlaybackProperties playbackProperties = mediaItem.f;
        this.a = new DataSpec.Builder().setUri(playbackProperties.a).setFlags(1).build();
        this.b = parser;
        this.c = new ArrayList<>(playbackProperties.e);
        this.d = factory;
        this.h = executor;
        this.e = (Cache) Assertions.checkNotNull(factory.getCache());
        this.f = factory.getCacheKeyFactory();
        this.g = factory.getUpstreamPriorityTaskManager();
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0086  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void c(java.util.List<com.google.android.exoplayer2.offline.SegmentDownloader.Segment> r19, com.google.android.exoplayer2.upstream.cache.CacheKeyFactory r20) {
        /*
            r0 = r19
            java.util.HashMap r1 = new java.util.HashMap
            r1.<init>()
            r3 = 0
            r4 = 0
        L_0x0009:
            int r5 = r19.size()
            if (r3 >= r5) goto L_0x00bf
            java.lang.Object r5 = r0.get(r3)
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r5 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r5
            com.google.android.exoplayer2.upstream.DataSpec r6 = r5.f
            r7 = r20
            java.lang.String r6 = r7.buildCacheKey(r6)
            java.lang.Object r8 = r1.get(r6)
            java.lang.Integer r8 = (java.lang.Integer) r8
            if (r8 != 0) goto L_0x0027
            r9 = 0
            goto L_0x0031
        L_0x0027:
            int r9 = r8.intValue()
            java.lang.Object r9 = r0.get(r9)
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r9 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r9
        L_0x0031:
            if (r9 == 0) goto L_0x00ad
            r10 = 20000000(0x1312d00, double:9.881313E-317)
            long r12 = r9.c
            long r10 = r10 + r12
            long r14 = r5.c
            int r16 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r16 > 0) goto L_0x00ad
            com.google.android.exoplayer2.upstream.DataSpec r9 = r9.f
            android.net.Uri r10 = r9.a
            com.google.android.exoplayer2.upstream.DataSpec r11 = r5.f
            android.net.Uri r14 = r11.a
            boolean r10 = r10.equals(r14)
            r14 = -1
            r16 = r3
            if (r10 == 0) goto L_0x0082
            long r2 = r9.g
            int r17 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1))
            if (r17 == 0) goto L_0x0082
            long r14 = r9.f
            long r14 = r14 + r2
            long r2 = r11.f
            int r18 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r18 != 0) goto L_0x0082
            java.lang.String r2 = r9.h
            java.lang.String r3 = r11.h
            boolean r2 = com.google.android.exoplayer2.util.Util.areEqual(r2, r3)
            if (r2 == 0) goto L_0x0082
            int r2 = r9.i
            int r3 = r11.i
            if (r2 != r3) goto L_0x0082
            int r2 = r9.c
            int r3 = r11.c
            if (r2 != r3) goto L_0x0082
            java.util.Map<java.lang.String, java.lang.String> r2 = r9.e
            java.util.Map<java.lang.String, java.lang.String> r3 = r11.e
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0082
            r2 = 1
            goto L_0x0083
        L_0x0082:
            r2 = 0
        L_0x0083:
            if (r2 != 0) goto L_0x0086
            goto L_0x00af
        L_0x0086:
            long r2 = r11.g
            r5 = -1
            int r11 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r11 != 0) goto L_0x0090
            r14 = r5
            goto L_0x0094
        L_0x0090:
            long r5 = r9.g
            long r14 = r5 + r2
        L_0x0094:
            r2 = 0
            com.google.android.exoplayer2.upstream.DataSpec r2 = r9.subrange(r2, r14)
            java.lang.Object r3 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r8)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r5 = new com.google.android.exoplayer2.offline.SegmentDownloader$Segment
            r5.<init>(r12, r2)
            r0.set(r3, r5)
            goto L_0x00bb
        L_0x00ad:
            r16 = r3
        L_0x00af:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            r1.put(r6, r2)
            r0.set(r4, r5)
            int r4 = r4 + 1
        L_0x00bb:
            int r3 = r16 + 1
            goto L_0x0009
        L_0x00bf:
            int r1 = r19.size()
            com.google.android.exoplayer2.util.Util.removeRange(r0, r4, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.c(java.util.List, com.google.android.exoplayer2.upstream.cache.CacheKeyFactory):void");
    }

    public final FilterableManifest a(CacheDataSource cacheDataSource, DataSpec dataSpec, boolean z) throws InterruptedException, IOException {
        Object obj;
        ra raVar = new ra(this, cacheDataSource, dataSpec);
        if (z) {
            raVar.run();
            try {
                obj = raVar.get();
            } catch (ExecutionException e2) {
                Throwable th = (Throwable) Assertions.checkNotNull(e2.getCause());
                if (!(th instanceof IOException)) {
                    Util.sneakyThrow(e2);
                } else {
                    throw ((IOException) th);
                }
            }
            return (FilterableManifest) obj;
        }
        while (!this.j) {
            PriorityTaskManager priorityTaskManager = this.g;
            if (priorityTaskManager != null) {
                priorityTaskManager.proceed(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
            }
            synchronized (this.i) {
                if (!this.j) {
                    this.i.add(raVar);
                } else {
                    throw new InterruptedException();
                }
            }
            this.h.execute(raVar);
            try {
                obj = raVar.get();
                return (FilterableManifest) obj;
            } catch (ExecutionException e3) {
                Throwable th2 = (Throwable) Assertions.checkNotNull(e3.getCause());
                if (!(th2 instanceof PriorityTaskManager.PriorityTooLowException)) {
                    if (!(th2 instanceof IOException)) {
                        Util.sneakyThrow(e3);
                    } else {
                        throw ((IOException) th2);
                    }
                }
            } finally {
                raVar.blockUntilFinished();
                e(raVar);
            }
        }
        throw new InterruptedException();
    }

    public abstract List b() throws IOException, InterruptedException;

    public void cancel() {
        synchronized (this.i) {
            this.j = true;
            for (int i2 = 0; i2 < this.i.size(); i2++) {
                this.i.get(i2).cancel(true);
            }
        }
    }

    public final void d(int i2) {
        synchronized (this.i) {
            this.i.remove(i2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:102:0x01b9 A[LOOP:5: B:100:0x01b1->B:102:0x01b9, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x01d2 A[LOOP:6: B:104:0x01d0->B:105:0x01d2, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01e7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void download(@androidx.annotation.Nullable com.google.android.exoplayer2.offline.Downloader.ProgressListener r26) throws java.io.IOException, java.lang.InterruptedException {
        /*
            r25 = this;
            r1 = r25
            java.util.ArrayDeque r2 = new java.util.ArrayDeque
            r2.<init>()
            java.util.ArrayDeque r3 = new java.util.ArrayDeque
            r3.<init>()
            com.google.android.exoplayer2.util.PriorityTaskManager r0 = r1.g
            r4 = -1000(0xfffffffffffffc18, float:NaN)
            if (r0 == 0) goto L_0x0015
            r0.add(r4)
        L_0x0015:
            r5 = 0
            r6 = 1
            com.google.android.exoplayer2.upstream.cache.CacheDataSource$Factory r0 = r1.d     // Catch:{ all -> 0x01ae }
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r0 = r0.createDataSourceForDownloading()     // Catch:{ all -> 0x01ae }
            com.google.android.exoplayer2.upstream.DataSpec r7 = r1.a     // Catch:{ all -> 0x01ae }
            com.google.android.exoplayer2.offline.FilterableManifest r0 = r1.a(r0, r7, r5)     // Catch:{ all -> 0x01ae }
            java.util.ArrayList<com.google.android.exoplayer2.offline.StreamKey> r7 = r1.c     // Catch:{ all -> 0x01ae }
            boolean r7 = r7.isEmpty()     // Catch:{ all -> 0x01ae }
            if (r7 != 0) goto L_0x0033
            java.util.ArrayList<com.google.android.exoplayer2.offline.StreamKey> r7 = r1.c     // Catch:{ all -> 0x01ae }
            java.lang.Object r0 = r0.copy(r7)     // Catch:{ all -> 0x01ae }
            com.google.android.exoplayer2.offline.FilterableManifest r0 = (com.google.android.exoplayer2.offline.FilterableManifest) r0     // Catch:{ all -> 0x01ae }
        L_0x0033:
            java.util.List r0 = r25.b()     // Catch:{ all -> 0x01ae }
            java.util.Collections.sort(r0)     // Catch:{ all -> 0x01ae }
            com.google.android.exoplayer2.upstream.cache.CacheKeyFactory r7 = r1.f     // Catch:{ all -> 0x01ae }
            c(r0, r7)     // Catch:{ all -> 0x01ae }
            int r12 = r0.size()     // Catch:{ all -> 0x01ae }
            int r7 = r0.size()     // Catch:{ all -> 0x01ae }
            int r7 = r7 - r6
            r8 = 0
            r10 = r8
            r13 = r10
            r15 = 0
        L_0x004d:
            if (r7 < 0) goto L_0x00af
            java.lang.Object r8 = r0.get(r7)     // Catch:{ all -> 0x00aa }
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r8 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r8     // Catch:{ all -> 0x00aa }
            com.google.android.exoplayer2.upstream.DataSpec r8 = r8.f     // Catch:{ all -> 0x00aa }
            com.google.android.exoplayer2.upstream.cache.CacheKeyFactory r9 = r1.f     // Catch:{ all -> 0x00aa }
            java.lang.String r9 = r9.buildCacheKey(r8)     // Catch:{ all -> 0x00aa }
            long r5 = r8.g     // Catch:{ all -> 0x00aa }
            r22 = -1
            int r16 = (r5 > r22 ? 1 : (r5 == r22 ? 0 : -1))
            if (r16 != 0) goto L_0x0077
            com.google.android.exoplayer2.upstream.cache.Cache r4 = r1.e     // Catch:{ all -> 0x01ae }
            com.google.android.exoplayer2.upstream.cache.ContentMetadata r4 = r4.getContentMetadata(r9)     // Catch:{ all -> 0x01ae }
            long r16 = defpackage.t0.a(r4)     // Catch:{ all -> 0x01ae }
            int r4 = (r16 > r22 ? 1 : (r16 == r22 ? 0 : -1))
            if (r4 == 0) goto L_0x0077
            long r4 = r8.f     // Catch:{ all -> 0x01ae }
            long r5 = r16 - r4
        L_0x0077:
            com.google.android.exoplayer2.upstream.cache.Cache r4 = r1.e     // Catch:{ all -> 0x00aa }
            r24 = r2
            long r1 = r8.f     // Catch:{ all -> 0x00aa }
            r16 = r4
            r17 = r9
            r18 = r1
            r20 = r5
            long r1 = r16.getCachedBytes(r17, r18, r20)     // Catch:{ all -> 0x00aa }
            long r13 = r13 + r1
            int r4 = (r5 > r22 ? 1 : (r5 == r22 ? 0 : -1))
            if (r4 == 0) goto L_0x009d
            int r4 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r4 != 0) goto L_0x0097
            int r15 = r15 + 1
            r0.remove(r7)     // Catch:{ all -> 0x00aa }
        L_0x0097:
            int r1 = (r10 > r22 ? 1 : (r10 == r22 ? 0 : -1))
            if (r1 == 0) goto L_0x009f
            long r10 = r10 + r5
            goto L_0x009f
        L_0x009d:
            r10 = r22
        L_0x009f:
            int r7 = r7 + -1
            r1 = r25
            r2 = r24
            r4 = -1000(0xfffffffffffffc18, float:NaN)
            r5 = 0
            r6 = 1
            goto L_0x004d
        L_0x00aa:
            r0 = move-exception
            r4 = r25
            goto L_0x01b0
        L_0x00af:
            r24 = r2
            if (r26 == 0) goto L_0x00bc
            com.google.android.exoplayer2.offline.SegmentDownloader$a r1 = new com.google.android.exoplayer2.offline.SegmentDownloader$a     // Catch:{ all -> 0x00aa }
            r8 = r1
            r9 = r26
            r8.<init>(r9, r10, r12, r13, r15)     // Catch:{ all -> 0x00aa }
            goto L_0x00bd
        L_0x00bc:
            r1 = 0
        L_0x00bd:
            r2 = r24
            r2.addAll(r0)     // Catch:{ all -> 0x00aa }
            r4 = r25
        L_0x00c4:
            boolean r0 = r4.j     // Catch:{ all -> 0x016f }
            if (r0 != 0) goto L_0x0171
            boolean r0 = r2.isEmpty()     // Catch:{ all -> 0x016f }
            if (r0 != 0) goto L_0x0171
            com.google.android.exoplayer2.util.PriorityTaskManager r0 = r4.g     // Catch:{ all -> 0x016f }
            if (r0 == 0) goto L_0x00d7
            r5 = -1000(0xfffffffffffffc18, float:NaN)
            r0.proceed(r5)     // Catch:{ all -> 0x016f }
        L_0x00d7:
            boolean r0 = r3.isEmpty()     // Catch:{ all -> 0x016f }
            if (r0 != 0) goto L_0x00e8
            java.lang.Object r0 = r3.removeFirst()     // Catch:{ all -> 0x016f }
            com.google.android.exoplayer2.offline.SegmentDownloader$b r0 = (com.google.android.exoplayer2.offline.SegmentDownloader.b) r0     // Catch:{ all -> 0x016f }
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r5 = r0.m     // Catch:{ all -> 0x016f }
            byte[] r0 = r0.o     // Catch:{ all -> 0x016f }
            goto L_0x00f2
        L_0x00e8:
            com.google.android.exoplayer2.upstream.cache.CacheDataSource$Factory r0 = r4.d     // Catch:{ all -> 0x016f }
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r5 = r0.createDataSourceForDownloading()     // Catch:{ all -> 0x016f }
            r0 = 131072(0x20000, float:1.83671E-40)
            byte[] r0 = new byte[r0]     // Catch:{ all -> 0x016f }
        L_0x00f2:
            java.lang.Object r6 = r2.removeFirst()     // Catch:{ all -> 0x016f }
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r6 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r6     // Catch:{ all -> 0x016f }
            com.google.android.exoplayer2.offline.SegmentDownloader$b r7 = new com.google.android.exoplayer2.offline.SegmentDownloader$b     // Catch:{ all -> 0x016f }
            r7.<init>(r6, r5, r1, r0)     // Catch:{ all -> 0x016f }
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r5 = r4.i     // Catch:{ all -> 0x016f }
            monitor-enter(r5)     // Catch:{ all -> 0x016f }
            boolean r0 = r4.j     // Catch:{ all -> 0x016c }
            if (r0 != 0) goto L_0x0166
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r0 = r4.i     // Catch:{ all -> 0x016c }
            r0.add(r7)     // Catch:{ all -> 0x016c }
            monitor-exit(r5)     // Catch:{ all -> 0x016c }
            java.util.concurrent.Executor r0 = r4.h     // Catch:{ all -> 0x016f }
            r0.execute(r7)     // Catch:{ all -> 0x016f }
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r0 = r4.i     // Catch:{ all -> 0x016f }
            int r0 = r0.size()     // Catch:{ all -> 0x016f }
            r5 = 1
            int r0 = r0 - r5
            r5 = r0
        L_0x0118:
            if (r5 < 0) goto L_0x0161
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r0 = r4.i     // Catch:{ all -> 0x016f }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x016f }
            r6 = r0
            com.google.android.exoplayer2.offline.SegmentDownloader$b r6 = (com.google.android.exoplayer2.offline.SegmentDownloader.b) r6     // Catch:{ all -> 0x016f }
            boolean r0 = r2.isEmpty()     // Catch:{ all -> 0x016f }
            if (r0 != 0) goto L_0x012f
            boolean r0 = r6.isDone()     // Catch:{ all -> 0x016f }
            if (r0 == 0) goto L_0x015b
        L_0x012f:
            r6.get()     // Catch:{ ExecutionException -> 0x0139 }
            r4.d(r5)     // Catch:{ ExecutionException -> 0x0139 }
            r3.addLast(r6)     // Catch:{ ExecutionException -> 0x0139 }
            goto L_0x015b
        L_0x0139:
            r0 = move-exception
            java.lang.Throwable r0 = r0.getCause()     // Catch:{ all -> 0x016f }
            java.lang.Object r0 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r0)     // Catch:{ all -> 0x016f }
            java.lang.Throwable r0 = (java.lang.Throwable) r0     // Catch:{ all -> 0x016f }
            boolean r8 = r0 instanceof com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException     // Catch:{ all -> 0x016f }
            if (r8 == 0) goto L_0x0154
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r0 = r6.l     // Catch:{ all -> 0x016f }
            r2.addFirst(r0)     // Catch:{ all -> 0x016f }
            r4.d(r5)     // Catch:{ all -> 0x016f }
            r3.addLast(r6)     // Catch:{ all -> 0x016f }
            goto L_0x015b
        L_0x0154:
            boolean r6 = r0 instanceof java.io.IOException     // Catch:{ all -> 0x016f }
            if (r6 != 0) goto L_0x015e
            com.google.android.exoplayer2.util.Util.sneakyThrow(r0)     // Catch:{ all -> 0x016f }
        L_0x015b:
            int r5 = r5 + -1
            goto L_0x0118
        L_0x015e:
            java.io.IOException r0 = (java.io.IOException) r0     // Catch:{ all -> 0x016f }
            throw r0     // Catch:{ all -> 0x016f }
        L_0x0161:
            r7.blockUntilStarted()     // Catch:{ all -> 0x016f }
            goto L_0x00c4
        L_0x0166:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException     // Catch:{ all -> 0x016c }
            r0.<init>()     // Catch:{ all -> 0x016c }
            throw r0     // Catch:{ all -> 0x016c }
        L_0x016c:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x016c }
            throw r0     // Catch:{ all -> 0x016f }
        L_0x016f:
            r0 = move-exception
            goto L_0x01b0
        L_0x0171:
            r5 = 0
        L_0x0172:
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r0 = r4.i
            int r0 = r0.size()
            if (r5 >= r0) goto L_0x0189
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r0 = r4.i
            java.lang.Object r0 = r0.get(r5)
            com.google.android.exoplayer2.util.RunnableFutureTask r0 = (com.google.android.exoplayer2.util.RunnableFutureTask) r0
            r1 = 1
            r0.cancel(r1)
            int r5 = r5 + 1
            goto L_0x0172
        L_0x0189:
            r1 = 1
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r0 = r4.i
            int r0 = r0.size()
            int r0 = r0 - r1
        L_0x0191:
            if (r0 < 0) goto L_0x01a4
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r1 = r4.i
            java.lang.Object r1 = r1.get(r0)
            com.google.android.exoplayer2.util.RunnableFutureTask r1 = (com.google.android.exoplayer2.util.RunnableFutureTask) r1
            r1.blockUntilFinished()
            r4.d(r0)
            int r0 = r0 + -1
            goto L_0x0191
        L_0x01a4:
            com.google.android.exoplayer2.util.PriorityTaskManager r0 = r4.g
            if (r0 == 0) goto L_0x01ad
            r1 = -1000(0xfffffffffffffc18, float:NaN)
            r0.remove(r1)
        L_0x01ad:
            return
        L_0x01ae:
            r0 = move-exception
            r4 = r1
        L_0x01b0:
            r5 = 0
        L_0x01b1:
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r1 = r4.i
            int r1 = r1.size()
            if (r5 >= r1) goto L_0x01c8
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r1 = r4.i
            java.lang.Object r1 = r1.get(r5)
            com.google.android.exoplayer2.util.RunnableFutureTask r1 = (com.google.android.exoplayer2.util.RunnableFutureTask) r1
            r2 = 1
            r1.cancel(r2)
            int r5 = r5 + 1
            goto L_0x01b1
        L_0x01c8:
            r2 = 1
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r1 = r4.i
            int r1 = r1.size()
            int r1 = r1 - r2
        L_0x01d0:
            if (r1 < 0) goto L_0x01e3
            java.util.ArrayList<com.google.android.exoplayer2.util.RunnableFutureTask<?, ?>> r2 = r4.i
            java.lang.Object r2 = r2.get(r1)
            com.google.android.exoplayer2.util.RunnableFutureTask r2 = (com.google.android.exoplayer2.util.RunnableFutureTask) r2
            r2.blockUntilFinished()
            r4.d(r1)
            int r1 = r1 + -1
            goto L_0x01d0
        L_0x01e3:
            com.google.android.exoplayer2.util.PriorityTaskManager r1 = r4.g
            if (r1 == 0) goto L_0x01ec
            r2 = -1000(0xfffffffffffffc18, float:NaN)
            r1.remove(r2)
        L_0x01ec:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.download(com.google.android.exoplayer2.offline.Downloader$ProgressListener):void");
    }

    public final void e(ra raVar) {
        synchronized (this.i) {
            this.i.remove(raVar);
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x002f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void remove() {
        /*
            r6 = this;
            com.google.android.exoplayer2.upstream.cache.CacheKeyFactory r0 = r6.f
            com.google.android.exoplayer2.upstream.cache.Cache r1 = r6.e
            com.google.android.exoplayer2.upstream.DataSpec r2 = r6.a
            com.google.android.exoplayer2.upstream.cache.CacheDataSource$Factory r3 = r6.d
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r3 = r3.createDataSourceForRemovingDownload()
            r4 = 1
            r6.a(r3, r2, r4)     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            java.util.List r3 = r6.b()     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            r4 = 0
        L_0x0015:
            int r5 = r3.size()     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            if (r4 >= r5) goto L_0x0036
            java.lang.Object r5 = r3.get(r4)     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            com.google.android.exoplayer2.offline.SegmentDownloader$Segment r5 = (com.google.android.exoplayer2.offline.SegmentDownloader.Segment) r5     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            com.google.android.exoplayer2.upstream.DataSpec r5 = r5.f     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            java.lang.String r5 = r0.buildCacheKey(r5)     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            r1.removeResource(r5)     // Catch:{ InterruptedException -> 0x002f, Exception -> 0x0036 }
            int r4 = r4 + 1
            goto L_0x0015
        L_0x002d:
            r3 = move-exception
            goto L_0x003e
        L_0x002f:
            java.lang.Thread r3 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x002d }
            r3.interrupt()     // Catch:{ all -> 0x002d }
        L_0x0036:
            java.lang.String r0 = r0.buildCacheKey(r2)
            r1.removeResource(r0)
            return
        L_0x003e:
            java.lang.String r0 = r0.buildCacheKey(r2)
            r1.removeResource(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.SegmentDownloader.remove():void");
    }
}
