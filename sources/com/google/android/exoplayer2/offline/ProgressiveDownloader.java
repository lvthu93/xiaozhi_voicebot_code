package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.offline.Downloader;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public final class ProgressiveDownloader implements Downloader {
    public final Executor a;
    public final DataSpec b;
    public final CacheDataSource c;
    public final CacheWriter d;
    @Nullable
    public final PriorityTaskManager e;
    @Nullable
    public Downloader.ProgressListener f;
    public volatile a g;
    public volatile boolean h;

    public class a extends RunnableFutureTask<Void, IOException> {
        public a() {
        }

        public final void a() {
            ProgressiveDownloader.this.d.cancel();
        }

        public final Object b() throws Exception {
            ProgressiveDownloader.this.d.cache();
            return null;
        }
    }

    @Deprecated
    public ProgressiveDownloader(Uri uri, @Nullable String str, CacheDataSource.Factory factory) {
        this(uri, str, factory, new b1(2));
    }

    public void cancel() {
        this.h = true;
        a aVar = this.g;
        if (aVar != null) {
            aVar.cancel(true);
        }
    }

    public void download(@Nullable Downloader.ProgressListener progressListener) throws IOException, InterruptedException {
        this.f = progressListener;
        this.g = new a();
        PriorityTaskManager priorityTaskManager = this.e;
        if (priorityTaskManager != null) {
            priorityTaskManager.add(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }
        boolean z = false;
        while (!z) {
            try {
                if (this.h) {
                    break;
                }
                PriorityTaskManager priorityTaskManager2 = this.e;
                if (priorityTaskManager2 != null) {
                    priorityTaskManager2.proceed(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                }
                this.a.execute(this.g);
                this.g.get();
                z = true;
            } catch (ExecutionException e2) {
                Throwable th = (Throwable) Assertions.checkNotNull(e2.getCause());
                if (!(th instanceof PriorityTaskManager.PriorityTooLowException)) {
                    if (!(th instanceof IOException)) {
                        Util.sneakyThrow(th);
                    } else {
                        throw ((IOException) th);
                    }
                }
            } catch (Throwable th2) {
                this.g.blockUntilFinished();
                PriorityTaskManager priorityTaskManager3 = this.e;
                if (priorityTaskManager3 != null) {
                    priorityTaskManager3.remove(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                }
                throw th2;
            }
        }
        this.g.blockUntilFinished();
        PriorityTaskManager priorityTaskManager4 = this.e;
        if (priorityTaskManager4 != null) {
            priorityTaskManager4.remove(NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
        }
    }

    public void remove() {
        CacheDataSource cacheDataSource = this.c;
        cacheDataSource.getCache().removeResource(cacheDataSource.getCacheKeyFactory().buildCacheKey(this.b));
    }

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory) {
        this(mediaItem, factory, (Executor) new b1(3));
    }

    @Deprecated
    public ProgressiveDownloader(Uri uri, @Nullable String str, CacheDataSource.Factory factory, Executor executor) {
        this(new MediaItem.Builder().setUri(uri).setCustomCacheKey(str).build(), factory, executor);
    }

    public ProgressiveDownloader(MediaItem mediaItem, CacheDataSource.Factory factory, Executor executor) {
        this.a = (Executor) Assertions.checkNotNull(executor);
        Assertions.checkNotNull(mediaItem.f);
        DataSpec.Builder builder = new DataSpec.Builder();
        MediaItem.PlaybackProperties playbackProperties = mediaItem.f;
        DataSpec build = builder.setUri(playbackProperties.a).setKey(playbackProperties.f).setFlags(4).build();
        this.b = build;
        CacheDataSource createDataSourceForDownloading = factory.createDataSourceForDownloading();
        this.c = createDataSourceForDownloading;
        this.d = new CacheWriter(createDataSourceForDownloading, build, (byte[]) null, new i2(6, this));
        this.e = factory.getUpstreamPriorityTaskManager();
    }
}
