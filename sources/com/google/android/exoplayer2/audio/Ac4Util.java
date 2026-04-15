package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.nio.ByteBuffer;

public final class Ac4Util {
    public static final int[] a = {2002, 2000, 1920, 1601, 1600, 1001, 1000, 960, 800, 800, 480, 400, 400, 2048};

    public static final class SyncFrameInfo {
        public final int a;
        public final int b = 2;
        public final int c;
        public final int d;

        public SyncFrameInfo(int i, int i2, int i3) {
            this.a = i;
            this.c = i2;
            this.d = i3;
        }
    }

    public static void getAc4SampleHeader(int i, ParsableByteArray parsableByteArray) {
        parsableByteArray.reset(7);
        byte[] data = parsableByteArray.getData();
        data[0] = -84;
        data[1] = 64;
        data[2] = -1;
        data[3] = -1;
        data[4] = (byte) ((i >> 16) & 255);
        data[5] = (byte) ((i >> 8) & 255);
        data[6] = (byte) (i & 255);
    }

    public static Format parseAc4AnnexEFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        int i;
        parsableByteArray.skipBytes(1);
        if (((parsableByteArray.readUnsignedByte() & 32) >> 5) == 1) {
            i = 48000;
        } else {
            i = 44100;
        }
        return new Format.Builder().setId(str).setSampleMimeType("audio/ac4").setChannelCount(2).setSampleRate(i).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseAc4SyncframeAudioSampleCount(ByteBuffer byteBuffer) {
        byte[] bArr = new byte[16];
        int position = byteBuffer.position();
        byteBuffer.get(bArr);
        byteBuffer.position(position);
        return parseAc4SyncframeInfo(new ParsableBitArray(bArr)).d;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0085, code lost:
        if (r9 != 11) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x008a, code lost:
        if (r9 != 11) goto L_0x0093;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x008f, code lost:
        if (r9 != 8) goto L_0x0093;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.audio.Ac4Util.SyncFrameInfo parseAc4SyncframeInfo(com.google.android.exoplayer2.util.ParsableBitArray r9) {
        /*
            r0 = 16
            int r1 = r9.readBits(r0)
            int r0 = r9.readBits(r0)
            r2 = 65535(0xffff, float:9.1834E-41)
            r3 = 4
            if (r0 != r2) goto L_0x0018
            r0 = 24
            int r0 = r9.readBits(r0)
            r2 = 7
            goto L_0x0019
        L_0x0018:
            r2 = 4
        L_0x0019:
            int r0 = r0 + r2
            r2 = 44097(0xac41, float:6.1793E-41)
            if (r1 != r2) goto L_0x0021
            int r0 = r0 + 2
        L_0x0021:
            r1 = 2
            int r2 = r9.readBits(r1)
            r4 = 3
            if (r2 != r4) goto L_0x0032
        L_0x0029:
            r9.readBits(r1)
            boolean r2 = r9.readBit()
            if (r2 != 0) goto L_0x0029
        L_0x0032:
            r2 = 10
            int r2 = r9.readBits(r2)
            boolean r5 = r9.readBit()
            if (r5 == 0) goto L_0x0047
            int r5 = r9.readBits(r4)
            if (r5 <= 0) goto L_0x0047
            r9.skipBits(r1)
        L_0x0047:
            boolean r5 = r9.readBit()
            r6 = 44100(0xac44, float:6.1797E-41)
            r7 = 48000(0xbb80, float:6.7262E-41)
            if (r5 == 0) goto L_0x0057
            r5 = 48000(0xbb80, float:6.7262E-41)
            goto L_0x005a
        L_0x0057:
            r5 = 44100(0xac44, float:6.1797E-41)
        L_0x005a:
            int r9 = r9.readBits(r3)
            int[] r8 = a
            if (r5 != r6) goto L_0x0069
            r6 = 13
            if (r9 != r6) goto L_0x0069
            r9 = r8[r9]
            goto L_0x0096
        L_0x0069:
            if (r5 != r7) goto L_0x0095
            r6 = 14
            if (r9 >= r6) goto L_0x0095
            r6 = r8[r9]
            int r2 = r2 % 5
            r7 = 1
            r8 = 8
            if (r2 == r7) goto L_0x008d
            r7 = 11
            if (r2 == r1) goto L_0x0088
            if (r2 == r4) goto L_0x008d
            if (r2 == r3) goto L_0x0081
            goto L_0x0093
        L_0x0081:
            if (r9 == r4) goto L_0x0091
            if (r9 == r8) goto L_0x0091
            if (r9 != r7) goto L_0x0093
            goto L_0x0091
        L_0x0088:
            if (r9 == r8) goto L_0x0091
            if (r9 != r7) goto L_0x0093
            goto L_0x0091
        L_0x008d:
            if (r9 == r4) goto L_0x0091
            if (r9 != r8) goto L_0x0093
        L_0x0091:
            int r6 = r6 + 1
        L_0x0093:
            r9 = r6
            goto L_0x0096
        L_0x0095:
            r9 = 0
        L_0x0096:
            com.google.android.exoplayer2.audio.Ac4Util$SyncFrameInfo r1 = new com.google.android.exoplayer2.audio.Ac4Util$SyncFrameInfo
            r1.<init>(r5, r0, r9)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.Ac4Util.parseAc4SyncframeInfo(com.google.android.exoplayer2.util.ParsableBitArray):com.google.android.exoplayer2.audio.Ac4Util$SyncFrameInfo");
    }

    public static int parseAc4SyncframeSize(byte[] bArr, int i) {
        int i2 = 7;
        if (bArr.length < 7) {
            return -1;
        }
        byte b = ((bArr[2] & 255) << 8) | (bArr[3] & 255);
        if (b == 65535) {
            b = ((bArr[4] & 255) << 16) | ((bArr[5] & 255) << 8) | (bArr[6] & 255);
        } else {
            i2 = 4;
        }
        if (i == 44097) {
            i2 += 2;
        }
        return b + i2;
    }
}
