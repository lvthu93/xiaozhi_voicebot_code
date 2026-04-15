package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public final class AssetDataSource extends BaseDataSource {
    public final AssetManager e;
    @Nullable
    public Uri f;
    @Nullable
    public InputStream g;
    public long h;
    public boolean i;

    public static final class AssetDataSourceException extends IOException {
        public AssetDataSourceException(IOException iOException) {
            super(iOException);
        }
    }

    public AssetDataSource(Context context) {
        super(false);
        this.e = context.getAssets();
    }

    public void close() throws AssetDataSourceException {
        this.f = null;
        try {
            InputStream inputStream = this.g;
            if (inputStream != null) {
                inputStream.close();
            }
            this.g = null;
            if (this.i) {
                this.i = false;
                b();
            }
        } catch (IOException e2) {
            throw new AssetDataSourceException(e2);
        } catch (Throwable th) {
            this.g = null;
            if (this.i) {
                this.i = false;
                b();
            }
            throw th;
        }
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public Uri getUri() {
        return this.f;
    }

    public long open(DataSpec dataSpec) throws AssetDataSourceException {
        try {
            Uri uri = dataSpec.a;
            long j = dataSpec.f;
            this.f = uri;
            String str = (String) Assertions.checkNotNull(uri.getPath());
            if (str.startsWith("/android_asset/")) {
                str = str.substring(15);
            } else if (str.startsWith(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                str = str.substring(1);
            }
            c(dataSpec);
            InputStream open = this.e.open(str, 1);
            this.g = open;
            if (open.skip(j) >= j) {
                long j2 = dataSpec.g;
                if (j2 != -1) {
                    this.h = j2;
                } else {
                    long available = (long) this.g.available();
                    this.h = available;
                    if (available == 2147483647L) {
                        this.h = -1;
                    }
                }
                this.i = true;
                d(dataSpec);
                return this.h;
            }
            throw new DataSourceException(0);
        } catch (IOException e2) {
            throw new AssetDataSourceException(e2);
        }
    }

    public int read(byte[] bArr, int i2, int i3) throws AssetDataSourceException {
        if (i3 == 0) {
            return 0;
        }
        long j = this.h;
        if (j == 0) {
            return -1;
        }
        if (j != -1) {
            try {
                i3 = (int) Math.min(j, (long) i3);
            } catch (IOException e2) {
                throw new AssetDataSourceException(e2);
            }
        }
        int read = ((InputStream) Util.castNonNull(this.g)).read(bArr, i2, i3);
        if (read == -1) {
            return -1;
        }
        long j2 = this.h;
        if (j2 != -1) {
            this.h = j2 - ((long) read);
        }
        a(read);
        return read;
    }
}
