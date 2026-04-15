package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class StatsDataSource implements DataSource {
    public final DataSource a;
    public long b;
    public Uri c = Uri.EMPTY;
    public Map<String, List<String>> d = Collections.emptyMap();

    public StatsDataSource(DataSource dataSource) {
        this.a = (DataSource) Assertions.checkNotNull(dataSource);
    }

    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.a.addTransferListener(transferListener);
    }

    public void close() throws IOException {
        this.a.close();
    }

    public long getBytesRead() {
        return this.b;
    }

    public Uri getLastOpenedUri() {
        return this.c;
    }

    public Map<String, List<String>> getLastResponseHeaders() {
        return this.d;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.a.getResponseHeaders();
    }

    @Nullable
    public Uri getUri() {
        return this.a.getUri();
    }

    public long open(DataSpec dataSpec) throws IOException {
        this.c = dataSpec.a;
        this.d = Collections.emptyMap();
        long open = this.a.open(dataSpec);
        this.c = (Uri) Assertions.checkNotNull(getUri());
        this.d = getResponseHeaders();
        return open;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int read = this.a.read(bArr, i, i2);
        if (read != -1) {
            this.b += (long) read;
        }
        return read;
    }

    public void resetBytesRead() {
        this.b = 0;
    }
}
