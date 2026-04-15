package com.google.android.exoplayer2.audio;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import org.mozilla.javascript.Token;

public final class Ac3Util {
    public static final int[] a = {1, 2, 3, 6};
    public static final int[] b = {48000, 44100, 32000};
    public static final int[] c = {24000, 22050, 16000};
    public static final int[] d = {2, 1, 2, 3, 3, 4, 4, 5};
    public static final int[] e = {32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, 320, 384, 448, 512, 576, 640};
    public static final int[] f = {69, 87, 104, 121, Token.USE_STACK, 174, 208, 243, 278, 348, 417, 487, 557, 696, 835, 975, 1114, 1253, 1393};

    public static final class SyncFrameInfo {
        @Nullable
        public final String a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface StreamType {
        }

        public SyncFrameInfo(String str, int i, int i2, int i3, int i4) {
            this.a = str;
            this.c = i;
            this.b = i2;
            this.d = i3;
            this.e = i4;
        }
    }

    public static int a(int i, int i2) {
        int i3 = i2 / 2;
        if (i < 0 || i >= 3 || i2 < 0 || i3 >= 19) {
            return -1;
        }
        int i4 = b[i];
        if (i4 == 44100) {
            return ((i2 % 2) + f[i3]) * 2;
        }
        int i5 = e[i3];
        if (i4 == 32000) {
            return i5 * 6;
        }
        return i5 * 4;
    }

    public static int findTrueHdSyncframeOffset(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int limit = byteBuffer.limit() - 10;
        for (int i = position; i <= limit; i++) {
            if ((Util.getBigEndianInt(byteBuffer, i + 4) & -2) == -126718022) {
                return i - position;
            }
        }
        return -1;
    }

    public static Format parseAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        int i = b[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = d[(readUnsignedByte & 56) >> 3];
        if ((readUnsignedByte & 4) != 0) {
            i2++;
        }
        return new Format.Builder().setId(str).setSampleMimeType("audio/ac3").setChannelCount(i2).setSampleRate(i).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseAc3SyncframeAudioSampleCount(ByteBuffer byteBuffer) {
        boolean z;
        int i = 3;
        if (((byteBuffer.get(byteBuffer.position() + 5) & 248) >> 3) > 10) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return 1536;
        }
        if (((byteBuffer.get(byteBuffer.position() + 4) & 192) >> 6) != 3) {
            i = (byteBuffer.get(byteBuffer.position() + 4) & 48) >> 4;
        }
        return a[i] * 256;
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x018c  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01a8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.audio.Ac3Util.SyncFrameInfo parseAc3SyncframeInfo(com.google.android.exoplayer2.util.ParsableBitArray r20) {
        /*
            r0 = r20
            int r1 = r20.getPosition()
            r2 = 40
            r0.skipBits(r2)
            r2 = 5
            int r3 = r0.readBits(r2)
            r5 = 1
            r6 = 10
            if (r3 <= r6) goto L_0x0017
            r3 = 1
            goto L_0x0018
        L_0x0017:
            r3 = 0
        L_0x0018:
            r0.setPosition(r1)
            int[] r1 = d
            int[] r7 = b
            r8 = 3
            r9 = -1
            r10 = 8
            r12 = 2
            if (r3 == 0) goto L_0x0228
            r3 = 16
            r0.skipBits(r3)
            int r13 = r0.readBits(r12)
            if (r13 == 0) goto L_0x003a
            if (r13 == r5) goto L_0x0038
            if (r13 == r12) goto L_0x0036
            goto L_0x003b
        L_0x0036:
            r9 = 2
            goto L_0x003b
        L_0x0038:
            r9 = 1
            goto L_0x003b
        L_0x003a:
            r9 = 0
        L_0x003b:
            r0.skipBits(r8)
            r13 = 11
            int r13 = r0.readBits(r13)
            int r13 = r13 + r5
            int r13 = r13 * 2
            int r14 = r0.readBits(r12)
            if (r14 != r8) goto L_0x0058
            int[] r7 = c
            int r15 = r0.readBits(r12)
            r7 = r7[r15]
            r4 = 6
            r15 = 3
            goto L_0x0064
        L_0x0058:
            int r15 = r0.readBits(r12)
            int[] r16 = a
            r16 = r16[r15]
            r7 = r7[r14]
            r4 = r16
        L_0x0064:
            int r11 = r4 * 256
            int r12 = r0.readBits(r8)
            boolean r19 = r20.readBit()
            r1 = r1[r12]
            int r1 = r1 + r19
            r0.skipBits(r6)
            boolean r6 = r20.readBit()
            if (r6 == 0) goto L_0x007e
            r0.skipBits(r10)
        L_0x007e:
            if (r12 != 0) goto L_0x008c
            r0.skipBits(r2)
            boolean r6 = r20.readBit()
            if (r6 == 0) goto L_0x008c
            r0.skipBits(r10)
        L_0x008c:
            if (r9 != r5) goto L_0x0097
            boolean r6 = r20.readBit()
            if (r6 == 0) goto L_0x0097
            r0.skipBits(r3)
        L_0x0097:
            boolean r3 = r20.readBit()
            r6 = 4
            if (r3 == 0) goto L_0x01bd
            r3 = 2
            if (r12 <= r3) goto L_0x00a4
            r0.skipBits(r3)
        L_0x00a4:
            r18 = r12 & 1
            if (r18 == 0) goto L_0x00af
            if (r12 <= r3) goto L_0x00af
            r3 = 6
            r0.skipBits(r3)
            goto L_0x00b0
        L_0x00af:
            r3 = 6
        L_0x00b0:
            r17 = r12 & 4
            if (r17 == 0) goto L_0x00b7
            r0.skipBits(r3)
        L_0x00b7:
            if (r19 == 0) goto L_0x00c2
            boolean r3 = r20.readBit()
            if (r3 == 0) goto L_0x00c2
            r0.skipBits(r2)
        L_0x00c2:
            if (r9 != 0) goto L_0x01bd
            boolean r3 = r20.readBit()
            if (r3 == 0) goto L_0x00cf
            r3 = 6
            r0.skipBits(r3)
            goto L_0x00d0
        L_0x00cf:
            r3 = 6
        L_0x00d0:
            if (r12 != 0) goto L_0x00db
            boolean r17 = r20.readBit()
            if (r17 == 0) goto L_0x00db
            r0.skipBits(r3)
        L_0x00db:
            boolean r17 = r20.readBit()
            if (r17 == 0) goto L_0x00e4
            r0.skipBits(r3)
        L_0x00e4:
            r3 = 2
            int r10 = r0.readBits(r3)
            if (r10 != r5) goto L_0x00f1
            r0.skipBits(r2)
        L_0x00ee:
            r5 = 2
            goto L_0x018a
        L_0x00f1:
            if (r10 != r3) goto L_0x00f9
            r3 = 12
            r0.skipBits(r3)
            goto L_0x00ee
        L_0x00f9:
            if (r10 != r8) goto L_0x00ee
            int r3 = r0.readBits(r2)
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x015f
            r0.skipBits(r2)
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x0111
            r0.skipBits(r6)
        L_0x0111:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x011a
            r0.skipBits(r6)
        L_0x011a:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x0123
            r0.skipBits(r6)
        L_0x0123:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x012c
            r0.skipBits(r6)
        L_0x012c:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x0135
            r0.skipBits(r6)
        L_0x0135:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x013e
            r0.skipBits(r6)
        L_0x013e:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x0147
            r0.skipBits(r6)
        L_0x0147:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x015f
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x0156
            r0.skipBits(r6)
        L_0x0156:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x015f
            r0.skipBits(r6)
        L_0x015f:
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x017e
            r0.skipBits(r2)
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x017e
            r10 = 7
            r0.skipBits(r10)
            boolean r10 = r20.readBit()
            if (r10 == 0) goto L_0x017e
            r10 = 8
            r0.skipBits(r10)
            goto L_0x0180
        L_0x017e:
            r10 = 8
        L_0x0180:
            r5 = 2
            int r3 = r3 + r5
            int r3 = r3 * 8
            r0.skipBits(r3)
            r20.byteAlign()
        L_0x018a:
            if (r12 >= r5) goto L_0x01a2
            boolean r3 = r20.readBit()
            r5 = 14
            if (r3 == 0) goto L_0x0197
            r0.skipBits(r5)
        L_0x0197:
            if (r12 != 0) goto L_0x01a2
            boolean r3 = r20.readBit()
            if (r3 == 0) goto L_0x01a2
            r0.skipBits(r5)
        L_0x01a2:
            boolean r3 = r20.readBit()
            if (r3 == 0) goto L_0x01bd
            if (r15 != 0) goto L_0x01ae
            r0.skipBits(r2)
            goto L_0x01bd
        L_0x01ae:
            r3 = 0
        L_0x01af:
            if (r3 >= r4) goto L_0x01bd
            boolean r5 = r20.readBit()
            if (r5 == 0) goto L_0x01ba
            r0.skipBits(r2)
        L_0x01ba:
            int r3 = r3 + 1
            goto L_0x01af
        L_0x01bd:
            boolean r3 = r20.readBit()
            if (r3 == 0) goto L_0x01f0
            r0.skipBits(r2)
            r2 = 2
            if (r12 != r2) goto L_0x01cc
            r0.skipBits(r6)
        L_0x01cc:
            r3 = 6
            if (r12 < r3) goto L_0x01d2
            r0.skipBits(r2)
        L_0x01d2:
            boolean r2 = r20.readBit()
            if (r2 == 0) goto L_0x01de
            r2 = 8
            r0.skipBits(r2)
            goto L_0x01e0
        L_0x01de:
            r2 = 8
        L_0x01e0:
            if (r12 != 0) goto L_0x01eb
            boolean r3 = r20.readBit()
            if (r3 == 0) goto L_0x01eb
            r0.skipBits(r2)
        L_0x01eb:
            if (r14 >= r8) goto L_0x01f0
            r20.skipBit()
        L_0x01f0:
            if (r9 != 0) goto L_0x01f7
            if (r15 == r8) goto L_0x01f7
            r20.skipBit()
        L_0x01f7:
            r2 = 2
            if (r9 != r2) goto L_0x0207
            if (r15 == r8) goto L_0x0202
            boolean r2 = r20.readBit()
            if (r2 == 0) goto L_0x0207
        L_0x0202:
            r2 = 6
            r0.skipBits(r2)
            goto L_0x0208
        L_0x0207:
            r2 = 6
        L_0x0208:
            boolean r3 = r20.readBit()
            if (r3 == 0) goto L_0x0220
            int r2 = r0.readBits(r2)
            r3 = 1
            if (r2 != r3) goto L_0x0220
            r2 = 8
            int r0 = r0.readBits(r2)
            if (r0 != r3) goto L_0x0220
            java.lang.String r0 = "audio/eac3-joc"
            goto L_0x0222
        L_0x0220:
            java.lang.String r0 = "audio/eac3"
        L_0x0222:
            r4 = r0
            r5 = r1
            r6 = r7
            r8 = r11
            r7 = r13
            goto L_0x0276
        L_0x0228:
            r2 = 32
            r0.skipBits(r2)
            r2 = 2
            int r3 = r0.readBits(r2)
            if (r3 != r8) goto L_0x0236
            r2 = 0
            goto L_0x0238
        L_0x0236:
            java.lang.String r2 = "audio/ac3"
        L_0x0238:
            r4 = 6
            int r4 = r0.readBits(r4)
            int r13 = a(r3, r4)
            r4 = 8
            r0.skipBits(r4)
            int r4 = r0.readBits(r8)
            r5 = r4 & 1
            if (r5 == 0) goto L_0x0256
            r5 = 1
            if (r4 == r5) goto L_0x0256
            r5 = 2
            r0.skipBits(r5)
            goto L_0x0257
        L_0x0256:
            r5 = 2
        L_0x0257:
            r6 = r4 & 4
            if (r6 == 0) goto L_0x025e
            r0.skipBits(r5)
        L_0x025e:
            if (r4 != r5) goto L_0x0263
            r0.skipBits(r5)
        L_0x0263:
            if (r3 >= r8) goto L_0x0267
            r9 = r7[r3]
        L_0x0267:
            boolean r0 = r20.readBit()
            r1 = r1[r4]
            int r1 = r1 + r0
            r11 = 1536(0x600, float:2.152E-42)
            r5 = r1
            r4 = r2
            r6 = r9
            r7 = r13
            r8 = 1536(0x600, float:2.152E-42)
        L_0x0276:
            com.google.android.exoplayer2.audio.Ac3Util$SyncFrameInfo r0 = new com.google.android.exoplayer2.audio.Ac3Util$SyncFrameInfo
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.audio.Ac3Util.parseAc3SyncframeInfo(com.google.android.exoplayer2.util.ParsableBitArray):com.google.android.exoplayer2.audio.Ac3Util$SyncFrameInfo");
    }

    public static int parseAc3SyncframeSize(byte[] bArr) {
        boolean z;
        if (bArr.length < 6) {
            return -1;
        }
        if (((bArr[5] & 248) >> 3) > 10) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return (((bArr[3] & 255) | ((bArr[2] & 7) << 8)) + 1) * 2;
        }
        byte b2 = bArr[4];
        return a((b2 & 192) >> 6, b2 & 63);
    }

    public static Format parseEAc3AnnexFFormat(ParsableByteArray parsableByteArray, String str, String str2, @Nullable DrmInitData drmInitData) {
        String str3;
        parsableByteArray.skipBytes(2);
        int i = b[(parsableByteArray.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i2 = d[(readUnsignedByte & 14) >> 1];
        if ((readUnsignedByte & 1) != 0) {
            i2++;
        }
        if (((parsableByteArray.readUnsignedByte() & 30) >> 1) > 0 && (2 & parsableByteArray.readUnsignedByte()) != 0) {
            i2 += 2;
        }
        if (parsableByteArray.bytesLeft() <= 0 || (parsableByteArray.readUnsignedByte() & 1) == 0) {
            str3 = "audio/eac3";
        } else {
            str3 = "audio/eac3-joc";
        }
        return new Format.Builder().setId(str).setSampleMimeType(str3).setChannelCount(i2).setSampleRate(i).setDrmInitData(drmInitData).setLanguage(str2).build();
    }

    public static int parseTrueHdSyncframeAudioSampleCount(byte[] bArr) {
        boolean z = false;
        if (bArr[4] == -8 && bArr[5] == 114 && bArr[6] == 111) {
            byte b2 = bArr[7];
            if ((b2 & 254) == 186) {
                if ((b2 & 255) == 187) {
                    z = true;
                }
                return 40 << ((bArr[z ? (char) 9 : 8] >> 4) & 7);
            }
        }
        return 0;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(ByteBuffer byteBuffer, int i) {
        return 40 << ((byteBuffer.get((byteBuffer.position() + i) + ((byteBuffer.get((byteBuffer.position() + i) + 7) & 255) == 187 ? 9 : 8)) >> 4) & 7);
    }
}
