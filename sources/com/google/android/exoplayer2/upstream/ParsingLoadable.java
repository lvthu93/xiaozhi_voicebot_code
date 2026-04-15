package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class ParsingLoadable<T> implements Loader.Loadable {
    public final DataSpec a;
    public final StatsDataSource b;
    public final Parser<? extends T> c;
    @Nullable
    public volatile T d;

    public interface Parser<T> {
        T parse(Uri uri, InputStream inputStream) throws IOException;
    }

    public ParsingLoadable(DataSource dataSource, Uri uri, int i, Parser<? extends T> parser) {
        this(dataSource, new DataSpec.Builder().setUri(uri).setFlags(1).build(), i, parser);
    }

    public static <T> T load(DataSource dataSource, Parser<? extends T> parser, Uri uri, int i) throws IOException {
        ParsingLoadable parsingLoadable = new ParsingLoadable(dataSource, uri, i, parser);
        parsingLoadable.load();
        return Assertions.checkNotNull(parsingLoadable.getResult());
    }

    public long bytesLoaded() {
        return this.b.getBytesRead();
    }

    public final void cancelLoad() {
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.b.getLastResponseHeaders();
    }

    @Nullable
    public final T getResult() {
        return this.d;
    }

    public Uri getUri() {
        return this.b.getLastOpenedUri();
    }

    public ParsingLoadable(DataSource dataSource, DataSpec dataSpec, int i, Parser<? extends T> parser) {
        this.b = new StatsDataSource(dataSource);
        this.a = dataSpec;
        this.c = parser;
        LoadEventInfo.getNewId();
    }

    public static <T> T load(DataSource dataSource, Parser<? extends T> parser, DataSpec dataSpec, int i) throws IOException {
        ParsingLoadable parsingLoadable = new ParsingLoadable(dataSource, dataSpec, i, parser);
        parsingLoadable.load();
        return Assertions.checkNotNull(parsingLoadable.getResult());
    }

    public final void load() throws IOException {
        this.b.resetBytesRead();
        DataSourceInputStream dataSourceInputStream = new DataSourceInputStream(this.b, this.a);
        try {
            dataSourceInputStream.open();
            this.d = this.c.parse((Uri) Assertions.checkNotNull(this.b.getUri()), dataSourceInputStream);
        } finally {
            Util.closeQuietly((Closeable) dataSourceInputStream);
        }
    }
}
