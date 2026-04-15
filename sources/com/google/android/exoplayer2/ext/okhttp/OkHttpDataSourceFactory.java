package com.google.android.exoplayer2.ext.okhttp;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import okhttp3.CacheControl;
import okhttp3.Call;

@Deprecated
public final class OkHttpDataSourceFactory extends HttpDataSource.BaseFactory {
    public final Call.Factory b;
    @Nullable
    public final String c;
    @Nullable
    public final TransferListener d;
    @Nullable
    public final CacheControl e;

    public OkHttpDataSourceFactory(Call.Factory factory) {
        this(factory, (String) null, (TransferListener) null, (CacheControl) null);
    }

    public final HttpDataSource a(HttpDataSource.RequestProperties requestProperties) {
        OkHttpDataSource okHttpDataSource = new OkHttpDataSource(this.b, this.c, this.e, requestProperties);
        TransferListener transferListener = this.d;
        if (transferListener != null) {
            okHttpDataSource.addTransferListener(transferListener);
        }
        return okHttpDataSource;
    }

    public OkHttpDataSourceFactory(Call.Factory factory, @Nullable String str) {
        this(factory, str, (TransferListener) null, (CacheControl) null);
    }

    public OkHttpDataSourceFactory(Call.Factory factory, @Nullable String str, @Nullable CacheControl cacheControl) {
        this(factory, str, (TransferListener) null, cacheControl);
    }

    public OkHttpDataSourceFactory(Call.Factory factory, @Nullable String str, @Nullable TransferListener transferListener) {
        this(factory, str, transferListener, (CacheControl) null);
    }

    public OkHttpDataSourceFactory(Call.Factory factory, @Nullable String str, @Nullable TransferListener transferListener, @Nullable CacheControl cacheControl) {
        this.b = factory;
        this.c = str;
        this.d = transferListener;
        this.e = cacheControl;
    }
}
