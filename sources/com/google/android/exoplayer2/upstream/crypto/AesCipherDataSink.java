package com.google.android.exoplayer2.upstream.crypto;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSink;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class AesCipherDataSink implements DataSink {
    public final DataSink a;
    public final byte[] b;
    @Nullable
    public final byte[] c;
    @Nullable
    public AesFlushingCipher d;

    public AesCipherDataSink(byte[] bArr, DataSink dataSink) {
        this(bArr, dataSink, (byte[]) null);
    }

    public void close() throws IOException {
        this.d = null;
        this.a.close();
    }

    public void open(DataSpec dataSpec) throws IOException {
        this.a.open(dataSpec);
        this.d = new AesFlushingCipher(1, this.b, w0.getFNV64Hash(dataSpec.h), dataSpec.b + dataSpec.f);
    }

    public void write(byte[] bArr, int i, int i2) throws IOException {
        DataSink dataSink = this.a;
        byte[] bArr2 = this.c;
        if (bArr2 == null) {
            ((AesFlushingCipher) Util.castNonNull(this.d)).updateInPlace(bArr, i, i2);
            dataSink.write(bArr, i, i2);
            return;
        }
        int i3 = 0;
        while (i3 < i2) {
            int min = Math.min(i2 - i3, bArr2.length);
            ((AesFlushingCipher) Util.castNonNull(this.d)).update(bArr, i + i3, min, this.c, 0);
            dataSink.write(bArr2, 0, min);
            i3 += min;
        }
    }

    public AesCipherDataSink(byte[] bArr, DataSink dataSink, @Nullable byte[] bArr2) {
        this.a = dataSink;
        this.b = bArr;
        this.c = bArr2;
    }
}
