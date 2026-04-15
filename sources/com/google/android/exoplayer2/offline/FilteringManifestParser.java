package com.google.android.exoplayer2.offline;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.offline.FilterableManifest;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public final class FilteringManifestParser<T extends FilterableManifest<T>> implements ParsingLoadable.Parser<T> {
    public final ParsingLoadable.Parser<? extends T> a;
    @Nullable
    public final List<StreamKey> b;

    public FilteringManifestParser(ParsingLoadable.Parser<? extends T> parser, @Nullable List<StreamKey> list) {
        this.a = parser;
        this.b = list;
    }

    public T parse(Uri uri, InputStream inputStream) throws IOException {
        T t = (FilterableManifest) this.a.parse(uri, inputStream);
        List<StreamKey> list = this.b;
        return (list == null || list.isEmpty()) ? t : (FilterableManifest) t.copy(list);
    }
}
