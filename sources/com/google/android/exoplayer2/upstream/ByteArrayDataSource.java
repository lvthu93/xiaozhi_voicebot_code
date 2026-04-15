package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.io.IOException;
import java.util.Map;

public final class ByteArrayDataSource extends BaseDataSource {
    public final byte[] e;
    @Nullable
    public Uri f;
    public int g;
    public int h;
    public boolean i;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ByteArrayDataSource(byte[] bArr) {
        super(false);
        boolean z = false;
        Assertions.checkNotNull(bArr);
        Assertions.checkArgument(bArr.length > 0 ? true : z);
        this.e = bArr;
    }

    public void close() {
        if (this.i) {
            this.i = false;
            b();
        }
        this.f = null;
    }

    public /* bridge */ /* synthetic */ Map getResponseHeaders() {
        return y0.a(this);
    }

    @Nullable
    public Uri getUri() {
        return this.f;
    }

    public long open(DataSpec dataSpec) throws IOException {
        this.f = dataSpec.a;
        c(dataSpec);
        byte[] bArr = this.e;
        long j = dataSpec.f;
        if (j <= ((long) bArr.length)) {
            this.g = (int) j;
            int length = bArr.length - ((int) j);
            this.h = length;
            long j2 = dataSpec.g;
            if (j2 != -1) {
                this.h = (int) Math.min((long) length, j2);
            }
            this.i = true;
            d(dataSpec);
            if (j2 != -1) {
                return j2;
            }
            return (long) this.h;
        }
        throw new DataSourceException(0);
    }

    public int read(byte[] bArr, int i2, int i3) {
        if (i3 == 0) {
            return 0;
        }
        int i4 = this.h;
        if (i4 == 0) {
            return -1;
        }
        int min = Math.min(i3, i4);
        System.arraycopy(this.e, this.g, bArr, i2, min);
        this.g += min;
        this.h -= min;
        a(min);
        return min;
    }
}
