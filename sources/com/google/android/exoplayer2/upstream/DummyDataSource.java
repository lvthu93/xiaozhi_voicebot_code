package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

public final class DummyDataSource implements DataSource {
    public static final DummyDataSource a = new DummyDataSource();

    public void addTransferListener(TransferListener transferListener) {
    }

    public void close() {
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public Uri getUri() {
        return null;
    }

    public long open(DataSpec dataSpec) throws IOException {
        throw new IOException("DummyDataSource cannot be opened");
    }

    public int read(byte[] bArr, int i, int i2) {
        throw new UnsupportedOperationException();
    }
}
