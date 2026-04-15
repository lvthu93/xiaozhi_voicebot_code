package com.google.android.exoplayer2.drm;

import android.net.Uri;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.Map;

public final class DefaultDrmSessionManagerProvider implements DrmSessionManagerProvider {
    public final Object c = new Object();
    @GuardedBy("lock")
    public MediaItem.DrmConfiguration f;
    @GuardedBy("lock")
    public DefaultDrmSessionManager g;
    @Nullable
    public HttpDataSource.Factory h;
    @Nullable
    public String i;

    @RequiresApi(18)
    public final DefaultDrmSessionManager a(MediaItem.DrmConfiguration drmConfiguration) {
        String str;
        HttpDataSource.Factory factory = this.h;
        if (factory == null) {
            factory = new DefaultHttpDataSource.Factory().setUserAgent(this.i);
        }
        Uri uri = drmConfiguration.b;
        if (uri == null) {
            str = null;
        } else {
            str = uri.toString();
        }
        HttpMediaDrmCallback httpMediaDrmCallback = new HttpMediaDrmCallback(str, drmConfiguration.f, factory);
        for (Map.Entry next : drmConfiguration.c.entrySet()) {
            httpMediaDrmCallback.setKeyRequestProperty((String) next.getKey(), (String) next.getValue());
        }
        DefaultDrmSessionManager build = new DefaultDrmSessionManager.Builder().setUuidAndExoMediaDrmProvider(drmConfiguration.a, FrameworkMediaDrm.d).setMultiSession(drmConfiguration.d).setPlayClearSamplesWithoutKeys(drmConfiguration.e).setUseDrmSessionsForClearContent(y3.c(drmConfiguration.g)).build(httpMediaDrmCallback);
        build.setMode(0, drmConfiguration.getKeySetId());
        return build;
    }

    public DrmSessionManager get(MediaItem mediaItem) {
        DrmSessionManager drmSessionManager;
        Assertions.checkNotNull(mediaItem.f);
        MediaItem.DrmConfiguration drmConfiguration = mediaItem.f.c;
        if (drmConfiguration == null || Util.a < 18) {
            return DrmSessionManager.a;
        }
        synchronized (this.c) {
            if (!Util.areEqual(drmConfiguration, this.f)) {
                this.f = drmConfiguration;
                this.g = a(drmConfiguration);
            }
            drmSessionManager = (DrmSessionManager) Assertions.checkNotNull(this.g);
        }
        return drmSessionManager;
    }

    public void setDrmHttpDataSourceFactory(@Nullable HttpDataSource.Factory factory) {
        this.h = factory;
    }

    public void setDrmUserAgent(@Nullable String str) {
        this.i = str;
    }
}
