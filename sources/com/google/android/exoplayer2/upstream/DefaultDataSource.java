package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class DefaultDataSource implements DataSource {
    public final Context a;
    public final ArrayList b;
    public final DataSource c;
    @Nullable
    public FileDataSource d;
    @Nullable
    public AssetDataSource e;
    @Nullable
    public ContentDataSource f;
    @Nullable
    public DataSource g;
    @Nullable
    public UdpDataSource h;
    @Nullable
    public DataSchemeDataSource i;
    @Nullable
    public RawResourceDataSource j;
    @Nullable
    public DataSource k;

    public DefaultDataSource(Context context, boolean z) {
        this(context, (String) null, 8000, 8000, z);
    }

    public static void b(@Nullable DataSource dataSource, TransferListener transferListener) {
        if (dataSource != null) {
            dataSource.addTransferListener(transferListener);
        }
    }

    public final void a(DataSource dataSource) {
        int i2 = 0;
        while (true) {
            ArrayList arrayList = this.b;
            if (i2 < arrayList.size()) {
                dataSource.addTransferListener((TransferListener) arrayList.get(i2));
                i2++;
            } else {
                return;
            }
        }
    }

    public void addTransferListener(TransferListener transferListener) {
        Assertions.checkNotNull(transferListener);
        this.c.addTransferListener(transferListener);
        this.b.add(transferListener);
        b(this.d, transferListener);
        b(this.e, transferListener);
        b(this.f, transferListener);
        b(this.g, transferListener);
        b(this.h, transferListener);
        b(this.i, transferListener);
        b(this.j, transferListener);
    }

    public void close() throws IOException {
        DataSource dataSource = this.k;
        if (dataSource != null) {
            try {
                dataSource.close();
            } finally {
                this.k = null;
            }
        }
    }

    public Map<String, List<String>> getResponseHeaders() {
        DataSource dataSource = this.k;
        return dataSource == null ? Collections.emptyMap() : dataSource.getResponseHeaders();
    }

    @Nullable
    public Uri getUri() {
        DataSource dataSource = this.k;
        if (dataSource == null) {
            return null;
        }
        return dataSource.getUri();
    }

    public long open(DataSpec dataSpec) throws IOException {
        boolean z;
        if (this.k == null) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        String scheme = dataSpec.a.getScheme();
        Uri uri = dataSpec.a;
        boolean isLocalFileUri = Util.isLocalFileUri(uri);
        Context context = this.a;
        if (isLocalFileUri) {
            String path = uri.getPath();
            if (path == null || !path.startsWith("/android_asset/")) {
                if (this.d == null) {
                    FileDataSource fileDataSource = new FileDataSource();
                    this.d = fileDataSource;
                    a(fileDataSource);
                }
                this.k = this.d;
            } else {
                if (this.e == null) {
                    AssetDataSource assetDataSource = new AssetDataSource(context);
                    this.e = assetDataSource;
                    a(assetDataSource);
                }
                this.k = this.e;
            }
        } else if ("asset".equals(scheme)) {
            if (this.e == null) {
                AssetDataSource assetDataSource2 = new AssetDataSource(context);
                this.e = assetDataSource2;
                a(assetDataSource2);
            }
            this.k = this.e;
        } else if ("content".equals(scheme)) {
            if (this.f == null) {
                ContentDataSource contentDataSource = new ContentDataSource(context);
                this.f = contentDataSource;
                a(contentDataSource);
            }
            this.k = this.f;
        } else {
            boolean equals = "rtmp".equals(scheme);
            DataSource dataSource = this.c;
            if (equals) {
                if (this.g == null) {
                    try {
                        DataSource dataSource2 = (DataSource) Class.forName("com.google.android.exoplayer2.ext.rtmp.RtmpDataSource").getConstructor(new Class[0]).newInstance(new Object[0]);
                        this.g = dataSource2;
                        a(dataSource2);
                    } catch (ClassNotFoundException unused) {
                        Log.w("DefaultDataSource", "Attempting to play RTMP stream without depending on the RTMP extension");
                    } catch (Exception e2) {
                        throw new RuntimeException("Error instantiating RTMP extension", e2);
                    }
                    if (this.g == null) {
                        this.g = dataSource;
                    }
                }
                this.k = this.g;
            } else if ("udp".equals(scheme)) {
                if (this.h == null) {
                    UdpDataSource udpDataSource = new UdpDataSource();
                    this.h = udpDataSource;
                    a(udpDataSource);
                }
                this.k = this.h;
            } else if ("data".equals(scheme)) {
                if (this.i == null) {
                    DataSchemeDataSource dataSchemeDataSource = new DataSchemeDataSource();
                    this.i = dataSchemeDataSource;
                    a(dataSchemeDataSource);
                }
                this.k = this.i;
            } else if ("rawresource".equals(scheme) || "android.resource".equals(scheme)) {
                if (this.j == null) {
                    RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(context);
                    this.j = rawResourceDataSource;
                    a(rawResourceDataSource);
                }
                this.k = this.j;
            } else {
                this.k = dataSource;
            }
        }
        return this.k.open(dataSpec);
    }

    public int read(byte[] bArr, int i2, int i3) throws IOException {
        return ((DataSource) Assertions.checkNotNull(this.k)).read(bArr, i2, i3);
    }

    public DefaultDataSource(Context context, @Nullable String str, boolean z) {
        this(context, str, 8000, 8000, z);
    }

    public DefaultDataSource(Context context, @Nullable String str, int i2, int i3, boolean z) {
        this(context, (DataSource) new DefaultHttpDataSource.Factory().setUserAgent(str).setConnectTimeoutMs(i2).setReadTimeoutMs(i3).setAllowCrossProtocolRedirects(z).createDataSource());
    }

    public DefaultDataSource(Context context, DataSource dataSource) {
        this.a = context.getApplicationContext();
        this.c = (DataSource) Assertions.checkNotNull(dataSource);
        this.b = new ArrayList();
    }
}
