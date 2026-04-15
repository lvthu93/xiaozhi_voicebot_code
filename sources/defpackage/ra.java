package defpackage;

import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.util.RunnableFutureTask;
import java.io.IOException;

/* renamed from: ra  reason: default package */
public final class ra extends RunnableFutureTask<FilterableManifest<Object>, IOException> {
    public final /* synthetic */ DataSource l;
    public final /* synthetic */ DataSpec m;
    public final /* synthetic */ SegmentDownloader n;

    public ra(SegmentDownloader segmentDownloader, CacheDataSource cacheDataSource, DataSpec dataSpec) {
        this.n = segmentDownloader;
        this.l = cacheDataSource;
        this.m = dataSpec;
    }

    public final Object b() throws Exception {
        return (FilterableManifest) ParsingLoadable.load(this.l, this.n.b, this.m, 4);
    }
}
