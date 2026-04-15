package com.google.android.exoplayer2.upstream.crypto;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class AesCipherDataSource implements DataSource {
    public final DataSource a;
    public final byte[] b;
    @Nullable
    public AesFlushingCipher c;

    public AesCipherDataSource(byte[] bArr, DataSource dataSource) {
        this.a = dataSource;
        this.b = bArr;
    }

    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.a.addTransferListener(transferListener);
    }

    public void close() throws IOException {
        this.c = null;
        this.a.close();
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.a.getResponseHeaders();
    }

    @Nullable
    public Uri getUri() {
        return this.a.getUri();
    }

    public long open(DataSpec dataSpec) throws IOException {
        long open = this.a.open(dataSpec);
        this.c = new AesFlushingCipher(2, this.b, w0.getFNV64Hash(dataSpec.h), dataSpec.b + dataSpec.f);
        return open;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (i2 == 0) {
            return 0;
        }
        int read = this.a.read(bArr, i, i2);
        if (read == -1) {
            return -1;
        }
        ((AesFlushingCipher) Util.castNonNull(this.c)).updateInPlace(bArr, i, read);
        return read;
    }
}
