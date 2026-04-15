package defpackage;

import androidx.annotation.IntRange;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.util.Assertions;

/* renamed from: ch  reason: default package */
public final class ch extends DecoderInputBuffer {
    public long m;
    public int n;
    public int o = 32;

    public ch() {
        super(2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0049, code lost:
        if ((r0.remaining() + r3.position()) > 3072000) goto L_0x0028;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean append(com.google.android.exoplayer2.decoder.DecoderInputBuffer r5) {
        /*
            r4 = this;
            boolean r0 = r5.isEncrypted()
            r1 = 1
            r0 = r0 ^ r1
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
            boolean r0 = r5.hasSupplementalData()
            r0 = r0 ^ r1
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
            boolean r0 = r5.isEndOfStream()
            r0 = r0 ^ r1
            com.google.android.exoplayer2.util.Assertions.checkArgument(r0)
            boolean r0 = r4.hasSamples()
            r2 = 0
            if (r0 != 0) goto L_0x0022
        L_0x0020:
            r0 = 1
            goto L_0x004c
        L_0x0022:
            int r0 = r4.n
            int r3 = r4.o
            if (r0 < r3) goto L_0x002a
        L_0x0028:
            r0 = 0
            goto L_0x004c
        L_0x002a:
            boolean r0 = r5.isDecodeOnly()
            boolean r3 = r4.isDecodeOnly()
            if (r0 == r3) goto L_0x0035
            goto L_0x0028
        L_0x0035:
            java.nio.ByteBuffer r0 = r5.g
            if (r0 == 0) goto L_0x0020
            java.nio.ByteBuffer r3 = r4.g
            if (r3 == 0) goto L_0x0020
            int r3 = r3.position()
            int r0 = r0.remaining()
            int r0 = r0 + r3
            r3 = 3072000(0x2ee000, float:4.304789E-39)
            if (r0 <= r3) goto L_0x0020
            goto L_0x0028
        L_0x004c:
            if (r0 != 0) goto L_0x004f
            return r2
        L_0x004f:
            int r0 = r4.n
            int r2 = r0 + 1
            r4.n = r2
            if (r0 != 0) goto L_0x0064
            long r2 = r5.i
            r4.i = r2
            boolean r0 = r5.isKeyFrame()
            if (r0 == 0) goto L_0x0064
            r4.setFlags(r1)
        L_0x0064:
            boolean r0 = r5.isDecodeOnly()
            if (r0 == 0) goto L_0x006f
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r4.setFlags(r0)
        L_0x006f:
            java.nio.ByteBuffer r0 = r5.g
            if (r0 == 0) goto L_0x007f
            int r2 = r0.remaining()
            r4.ensureSpaceForWrite(r2)
            java.nio.ByteBuffer r2 = r4.g
            r2.put(r0)
        L_0x007f:
            long r2 = r5.i
            r4.m = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ch.append(com.google.android.exoplayer2.decoder.DecoderInputBuffer):boolean");
    }

    public void clear() {
        super.clear();
        this.n = 0;
    }

    public long getFirstSampleTimeUs() {
        return this.i;
    }

    public long getLastSampleTimeUs() {
        return this.m;
    }

    public int getSampleCount() {
        return this.n;
    }

    public boolean hasSamples() {
        return this.n > 0;
    }

    public void setMaxSampleCount(@IntRange(from = 1) int i) {
        boolean z;
        if (i > 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkArgument(z);
        this.o = i;
    }
}
