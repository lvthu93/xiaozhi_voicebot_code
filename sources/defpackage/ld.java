package defpackage;

import com.google.android.exoplayer2.extractor.ExtractorInput;
import java.io.IOException;

/* renamed from: ld  reason: default package */
public final class ld {
    public static final long[] d = {128, 64, 32, 16, 8, 4, 2, 1};
    public final byte[] a = new byte[8];
    public int b;
    public int c;

    public static long assembleVarint(byte[] bArr, int i, boolean z) {
        long j = ((long) bArr[0]) & 255;
        if (z) {
            j &= ~d[i - 1];
        }
        for (int i2 = 1; i2 < i; i2++) {
            j = (j << 8) | (((long) bArr[i2]) & 255);
        }
        return j;
    }

    public static int parseUnsignedVarintLength(int i) {
        for (int i2 = 0; i2 < 8; i2++) {
            if ((d[i2] & ((long) i)) != 0) {
                return i2 + 1;
            }
        }
        return -1;
    }

    public int getLastLength() {
        return this.c;
    }

    public long readUnsignedVarint(ExtractorInput extractorInput, boolean z, boolean z2, int i) throws IOException {
        int i2 = this.b;
        byte[] bArr = this.a;
        if (i2 == 0) {
            if (!extractorInput.readFully(bArr, 0, 1, z)) {
                return -1;
            }
            int parseUnsignedVarintLength = parseUnsignedVarintLength(bArr[0] & 255);
            this.c = parseUnsignedVarintLength;
            if (parseUnsignedVarintLength != -1) {
                this.b = 1;
            } else {
                throw new IllegalStateException("No valid varint length mask found");
            }
        }
        int i3 = this.c;
        if (i3 > i) {
            this.b = 0;
            return -2;
        }
        if (i3 != 1) {
            extractorInput.readFully(bArr, 1, i3 - 1);
        }
        this.b = 0;
        return assembleVarint(bArr, this.c, z2);
    }

    public void reset() {
        this.b = 0;
        this.c = 0;
    }
}
