package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public abstract class BaseDataSource implements DataSource {
    public final boolean a;
    public final ArrayList<TransferListener> b = new ArrayList<>(1);
    public int c;
    @Nullable
    public DataSpec d;

    public BaseDataSource(boolean z) {
        this.a = z;
    }

    public final void a(int i) {
        DataSpec dataSpec = (DataSpec) Util.castNonNull(this.d);
        for (int i2 = 0; i2 < this.c; i2++) {
            this.b.get(i2).onBytesTransferred(this, dataSpec, this.a, i);
        }
    }

    public final void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        ArrayList<TransferListener> arrayList = this.b;
        if (!arrayList.contains(transferListener)) {
            arrayList.add(transferListener);
            this.c++;
        }
    }

    public final void b() {
        DataSpec dataSpec = (DataSpec) Util.castNonNull(this.d);
        for (int i = 0; i < this.c; i++) {
            this.b.get(i).onTransferEnd(this, dataSpec, this.a);
        }
        this.d = null;
    }

    public final void c(DataSpec dataSpec) {
        for (int i = 0; i < this.c; i++) {
            this.b.get(i).onTransferInitializing(this, dataSpec, this.a);
        }
    }

    public abstract /* synthetic */ void close() throws IOException;

    public final void d(DataSpec dataSpec) {
        this.d = dataSpec;
        for (int i = 0; i < this.c; i++) {
            this.b.get(i).onTransferStart(this, dataSpec, this.a);
        }
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public abstract /* synthetic */ Uri getUri();

    public abstract /* synthetic */ long open(DataSpec dataSpec) throws IOException;

    public abstract /* synthetic */ int read(byte[] bArr, int i, int i2) throws IOException;
}
