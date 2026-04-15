package com.google.android.exoplayer2.source.chunk;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Chunk implements Loader.Loadable {
    public final long a = LoadEventInfo.getNewId();
    public final DataSpec b;
    public final int c;
    public final Format d;
    public final int e;
    @Nullable
    public final Object f;
    public final long g;
    public final long h;
    public final StatsDataSource i;

    public Chunk(DataSource dataSource, DataSpec dataSpec, int i2, Format format, int i3, @Nullable Object obj, long j, long j2) {
        this.i = new StatsDataSource(dataSource);
        this.b = (DataSpec) Assertions.checkNotNull(dataSpec);
        this.c = i2;
        this.d = format;
        this.e = i3;
        this.f = obj;
        this.g = j;
        this.h = j2;
    }

    public final long bytesLoaded() {
        return this.i.getBytesRead();
    }

    public abstract /* synthetic */ void cancelLoad();

    public final long getDurationUs() {
        return this.h - this.g;
    }

    public final Map<String, List<String>> getResponseHeaders() {
        return this.i.getLastResponseHeaders();
    }

    public final Uri getUri() {
        return this.i.getLastOpenedUri();
    }

    public abstract /* synthetic */ void load() throws IOException;
}
