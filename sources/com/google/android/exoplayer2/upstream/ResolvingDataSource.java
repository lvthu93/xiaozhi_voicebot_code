package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class ResolvingDataSource implements DataSource {
    public final DataSource a;
    public final Resolver b;
    public boolean c;

    public static final class Factory implements DataSource.Factory {
        public final DataSource.Factory a;
        public final Resolver b;

        public Factory(DataSource.Factory factory, Resolver resolver) {
            this.a = factory;
            this.b = resolver;
        }

        public ResolvingDataSource createDataSource() {
            return new ResolvingDataSource(this.a.createDataSource(), this.b);
        }
    }

    public interface Resolver {
        DataSpec resolveDataSpec(DataSpec dataSpec) throws IOException;

        Uri resolveReportedUri(Uri uri);
    }

    public ResolvingDataSource(DataSource dataSource, Resolver resolver) {
        this.a = dataSource;
        this.b = resolver;
    }

    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.a.addTransferListener(transferListener);
    }

    public void close() throws IOException {
        if (this.c) {
            this.c = false;
            this.a.close();
        }
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.a.getResponseHeaders();
    }

    @Nullable
    public Uri getUri() {
        Uri uri = this.a.getUri();
        if (uri == null) {
            return null;
        }
        return this.b.resolveReportedUri(uri);
    }

    public long open(DataSpec dataSpec) throws IOException {
        DataSpec resolveDataSpec = this.b.resolveDataSpec(dataSpec);
        this.c = true;
        return this.a.open(resolveDataSpec);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.a.read(bArr, i, i2);
    }
}
