package com.google.android.exoplayer2.upstream;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.HttpDataSource;

@Deprecated
public final class DefaultHttpDataSourceFactory extends HttpDataSource.BaseFactory {
    @Nullable
    public final String b;
    @Nullable
    public final TransferListener c;
    public final int d;
    public final int e;
    public final boolean f;

    public DefaultHttpDataSourceFactory() {
        this((String) null);
    }

    public final HttpDataSource a(HttpDataSource.RequestProperties requestProperties) {
        DefaultHttpDataSource defaultHttpDataSource = new DefaultHttpDataSource(this.b, this.d, this.e, this.f, requestProperties);
        TransferListener transferListener = this.c;
        if (transferListener != null) {
            defaultHttpDataSource.addTransferListener(transferListener);
        }
        return defaultHttpDataSource;
    }

    public DefaultHttpDataSourceFactory(@Nullable String str) {
        this(str, (TransferListener) null);
    }

    public DefaultHttpDataSourceFactory(@Nullable String str, @Nullable TransferListener transferListener) {
        this(str, transferListener, 8000, 8000, false);
    }

    public DefaultHttpDataSourceFactory(@Nullable String str, int i, int i2, boolean z) {
        this(str, (TransferListener) null, i, i2, z);
    }

    public DefaultHttpDataSourceFactory(@Nullable String str, @Nullable TransferListener transferListener, int i, int i2, boolean z) {
        this.b = str;
        this.c = transferListener;
        this.d = i;
        this.e = i2;
        this.f = z;
    }
}
