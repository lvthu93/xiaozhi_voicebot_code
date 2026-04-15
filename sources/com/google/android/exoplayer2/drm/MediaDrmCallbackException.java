package com.google.android.exoplayer2.drm;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.DataSpec;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class MediaDrmCallbackException extends IOException {
    public final DataSpec c;
    public final Uri f;
    public final Map<String, List<String>> g;
    public final long h;

    public MediaDrmCallbackException(DataSpec dataSpec, Uri uri, Map<String, List<String>> map, long j, Throwable th) {
        super(th);
        this.c = dataSpec;
        this.f = uri;
        this.g = map;
        this.h = j;
    }
}
