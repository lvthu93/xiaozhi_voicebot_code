package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.util.SparseArray;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;

public class DefaultDownloaderFactory implements DownloaderFactory {
    public static final SparseArray<Constructor<? extends Downloader>> c;
    public final CacheDataSource.Factory a;
    public final Executor b;

    static {
        SparseArray<Constructor<? extends Downloader>> sparseArray = new SparseArray<>();
        try {
            sparseArray.put(0, a(Class.forName("com.google.android.exoplayer2.source.dash.offline.DashDownloader")));
        } catch (ClassNotFoundException unused) {
        }
        try {
            sparseArray.put(2, a(Class.forName("com.google.android.exoplayer2.source.hls.offline.HlsDownloader")));
        } catch (ClassNotFoundException unused2) {
        }
        try {
            sparseArray.put(1, a(Class.forName("com.google.android.exoplayer2.source.smoothstreaming.offline.SsDownloader")));
        } catch (ClassNotFoundException unused3) {
        }
        c = sparseArray;
    }

    @Deprecated
    public DefaultDownloaderFactory(CacheDataSource.Factory factory) {
        this(factory, new b1(0));
    }

    public static Constructor<? extends Downloader> a(Class<?> cls) {
        try {
            return cls.asSubclass(Downloader.class).getConstructor(new Class[]{MediaItem.class, CacheDataSource.Factory.class, Executor.class});
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Downloader constructor missing", e);
        }
    }

    public Downloader createDownloader(DownloadRequest downloadRequest) {
        int inferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(downloadRequest.f, downloadRequest.g);
        Executor executor = this.b;
        CacheDataSource.Factory factory = this.a;
        String str = downloadRequest.j;
        Uri uri = downloadRequest.f;
        if (inferContentTypeForUriAndMimeType == 0 || inferContentTypeForUriAndMimeType == 1 || inferContentTypeForUriAndMimeType == 2) {
            Constructor constructor = c.get(inferContentTypeForUriAndMimeType);
            if (constructor != null) {
                try {
                    return (Downloader) constructor.newInstance(new Object[]{new MediaItem.Builder().setUri(uri).setStreamKeys(downloadRequest.h).setCustomCacheKey(str).setDrmKeySetId(downloadRequest.i).build(), factory, executor});
                } catch (Exception unused) {
                    throw new IllegalStateException(y2.d(61, "Failed to instantiate downloader for content type ", inferContentTypeForUriAndMimeType));
                }
            } else {
                throw new IllegalStateException(y2.d(43, "Module missing for content type ", inferContentTypeForUriAndMimeType));
            }
        } else if (inferContentTypeForUriAndMimeType == 4) {
            return new ProgressiveDownloader(new MediaItem.Builder().setUri(uri).setCustomCacheKey(str).build(), factory, executor);
        } else {
            throw new IllegalArgumentException(y2.d(29, "Unsupported type: ", inferContentTypeForUriAndMimeType));
        }
    }

    public DefaultDownloaderFactory(CacheDataSource.Factory factory, Executor executor) {
        this.a = (CacheDataSource.Factory) Assertions.checkNotNull(factory);
        this.b = (Executor) Assertions.checkNotNull(executor);
    }
}
