package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class PriorityDataSource implements DataSource {
    public final DataSource a;
    public final PriorityTaskManager b;
    public final int c;

    public PriorityDataSource(DataSource dataSource, PriorityTaskManager priorityTaskManager, int i) {
        this.a = (DataSource) Assertions.checkNotNull(dataSource);
        this.b = (PriorityTaskManager) Assertions.checkNotNull(priorityTaskManager);
        this.c = i;
    }

    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.a.addTransferListener(transferListener);
    }

    public void close() throws IOException {
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
        this.b.proceedOrThrow(this.c);
        return this.a.open(dataSpec);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        this.b.proceedOrThrow(this.c);
        return this.a.read(bArr, i, i2);
    }
}
