package com.google.android.exoplayer2.decoder;

import android.media.MediaCodec;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class CryptoInfo {
    @Nullable
    public byte[] a;
    @Nullable
    public byte[] b;
    public int c;
    @Nullable
    public int[] d;
    @Nullable
    public int[] e;
    public int f;
    public int g;
    public int h;
    public final MediaCodec.CryptoInfo i;
    @Nullable
    public final a j;

    @RequiresApi(24)
    public static final class a {
        public final MediaCodec.CryptoInfo a;
        public final MediaCodec.CryptoInfo.Pattern b = new MediaCodec.CryptoInfo.Pattern(0, 0);

        public a(MediaCodec.CryptoInfo cryptoInfo) {
            this.a = cryptoInfo;
        }
    }

    public CryptoInfo() {
        a aVar;
        MediaCodec.CryptoInfo cryptoInfo = new MediaCodec.CryptoInfo();
        this.i = cryptoInfo;
        if (Util.a >= 24) {
            aVar = new a(cryptoInfo);
        } else {
            aVar = null;
        }
        this.j = aVar;
    }

    public MediaCodec.CryptoInfo getFrameworkCryptoInfo() {
        return this.i;
    }

    public void increaseClearDataFirstSubSampleBy(int i2) {
        if (i2 != 0) {
            if (this.d == null) {
                int[] iArr = new int[1];
                this.d = iArr;
                this.i.numBytesOfClearData = iArr;
            }
            int[] iArr2 = this.d;
            iArr2[0] = iArr2[0] + i2;
        }
    }

    public void set(int i2, int[] iArr, int[] iArr2, byte[] bArr, byte[] bArr2, int i3, int i4, int i5) {
        this.f = i2;
        this.d = iArr;
        this.e = iArr2;
        this.b = bArr;
        this.a = bArr2;
        this.c = i3;
        this.g = i4;
        this.h = i5;
        MediaCodec.CryptoInfo cryptoInfo = this.i;
        cryptoInfo.numSubSamples = i2;
        cryptoInfo.numBytesOfClearData = iArr;
        cryptoInfo.numBytesOfEncryptedData = iArr2;
        cryptoInfo.key = bArr;
        cryptoInfo.iv = bArr2;
        cryptoInfo.mode = i3;
        if (Util.a >= 24) {
            a aVar = (a) Assertions.checkNotNull(this.j);
            MediaCodec.CryptoInfo.Pattern pattern = aVar.b;
            pattern.set(i4, i5);
            aVar.a.setPattern(pattern);
        }
    }
}
