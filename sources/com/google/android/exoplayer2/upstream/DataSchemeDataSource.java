package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import android.util.Base64;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import j$.net.URLDecoder;
import java.io.IOException;
import java.util.Map;

public final class DataSchemeDataSource extends BaseDataSource {
    @Nullable
    public DataSpec e;
    @Nullable
    public byte[] f;
    public int g;
    public int h;

    public DataSchemeDataSource() {
        super(false);
    }

    public void close() {
        if (this.f != null) {
            this.f = null;
            b();
        }
        this.e = null;
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public Uri getUri() {
        DataSpec dataSpec = this.e;
        if (dataSpec != null) {
            return dataSpec.a;
        }
        return null;
    }

    public long open(DataSpec dataSpec) throws IOException {
        String str;
        String str2;
        c(dataSpec);
        this.e = dataSpec;
        Uri uri = dataSpec.a;
        String scheme = uri.getScheme();
        if (!"data".equals(scheme)) {
            String valueOf = String.valueOf(scheme);
            if (valueOf.length() != 0) {
                str2 = "Unsupported scheme: ".concat(valueOf);
            } else {
                str2 = new String("Unsupported scheme: ");
            }
            throw new ParserException(str2);
        }
        String[] split = Util.split(uri.getSchemeSpecificPart(), ",");
        if (split.length == 2) {
            String str3 = split[1];
            if (split[0].contains(";base64")) {
                try {
                    this.f = Base64.decode(str3, 0);
                } catch (IllegalArgumentException e2) {
                    String valueOf2 = String.valueOf(str3);
                    if (valueOf2.length() != 0) {
                        str = "Error while parsing Base64 encoded string: ".concat(valueOf2);
                    } else {
                        str = new String("Error while parsing Base64 encoded string: ");
                    }
                    throw new ParserException(str, e2);
                }
            } else {
                this.f = Util.getUtf8Bytes(URLDecoder.decode(str3, Charsets.a.name()));
            }
            byte[] bArr = this.f;
            long j = dataSpec.f;
            if (j <= ((long) bArr.length)) {
                int i = (int) j;
                this.g = i;
                int length = bArr.length - i;
                this.h = length;
                long j2 = dataSpec.g;
                if (j2 != -1) {
                    this.h = (int) Math.min((long) length, j2);
                }
                d(dataSpec);
                if (j2 != -1) {
                    return j2;
                }
                return (long) this.h;
            }
            this.f = null;
            throw new DataSourceException(0);
        }
        String valueOf3 = String.valueOf(uri);
        StringBuilder sb = new StringBuilder(valueOf3.length() + 23);
        sb.append("Unexpected URI format: ");
        sb.append(valueOf3);
        throw new ParserException(sb.toString());
    }

    public int read(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return 0;
        }
        int i3 = this.h;
        if (i3 == 0) {
            return -1;
        }
        int min = Math.min(i2, i3);
        System.arraycopy(Util.castNonNull(this.f), this.g, bArr, i, min);
        this.g += min;
        this.h -= min;
        a(min);
        return min;
    }
}
