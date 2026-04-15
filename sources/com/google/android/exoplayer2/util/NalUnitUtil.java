package com.google.android.exoplayer2.util;

import androidx.annotation.Nullable;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class NalUnitUtil {
    public static final byte[] a = {0, 0, 0, 1};
    public static final float[] b = {1.0f, 1.0f, 1.0909091f, 0.90909094f, 1.4545455f, 1.2121212f, 2.1818182f, 1.8181819f, 2.909091f, 2.4242425f, 1.6363636f, 1.3636364f, 1.939394f, 1.6161616f, 1.3333334f, 1.5f, 2.0f};
    public static final Object c = new Object();
    public static int[] d = new int[10];

    public static final class PpsData {
        public final int a;
        public final int b;
        public final boolean c;

        public PpsData(int i, int i2, boolean z) {
            this.a = i;
            this.b = i2;
            this.c = z;
        }
    }

    public static final class SpsData {
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;
        public final int f;
        public final float g;
        public final boolean h;
        public final boolean i;
        public final int j;
        public final int k;
        public final int l;
        public final boolean m;

        public SpsData(int i2, int i3, int i4, int i5, int i6, int i7, float f2, boolean z, boolean z2, int i8, int i9, int i10, boolean z3) {
            this.a = i2;
            this.b = i3;
            this.c = i4;
            this.d = i5;
            this.e = i6;
            this.f = i7;
            this.g = f2;
            this.h = z;
            this.i = z2;
            this.j = i8;
            this.k = i9;
            this.l = i10;
            this.m = z3;
        }
    }

    public static void clearPrefixFlags(boolean[] zArr) {
        zArr[0] = false;
        zArr[1] = false;
        zArr[2] = false;
    }

    public static void discardToSps(ByteBuffer byteBuffer) {
        int position = byteBuffer.position();
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i + 1;
            if (i3 < position) {
                byte b2 = byteBuffer.get(i) & 255;
                if (i2 == 3) {
                    if (b2 == 1 && (byteBuffer.get(i3) & 31) == 7) {
                        ByteBuffer duplicate = byteBuffer.duplicate();
                        duplicate.position(i - 3);
                        duplicate.limit(position);
                        byteBuffer.position(0);
                        byteBuffer.put(duplicate);
                        return;
                    }
                } else if (b2 == 0) {
                    i2++;
                }
                if (b2 != 0) {
                    i2 = 0;
                }
                i = i3;
            } else {
                byteBuffer.clear();
                return;
            }
        }
    }

    public static int findNalUnit(byte[] bArr, int i, int i2, boolean[] zArr) {
        boolean z;
        boolean z2;
        boolean z3;
        int i3 = i2 - i;
        boolean z4 = false;
        if (i3 >= 0) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (i3 == 0) {
            return i2;
        }
        if (zArr[0]) {
            clearPrefixFlags(zArr);
            return i - 3;
        } else if (i3 > 1 && zArr[1] && bArr[i] == 1) {
            clearPrefixFlags(zArr);
            return i - 2;
        } else if (i3 <= 2 || !zArr[2] || bArr[i] != 0 || bArr[i + 1] != 1) {
            int i4 = i2 - 1;
            int i5 = i + 2;
            while (i5 < i4) {
                byte b2 = bArr[i5];
                if ((b2 & 254) == 0) {
                    int i6 = i5 - 2;
                    if (bArr[i6] == 0 && bArr[i5 - 1] == 0 && b2 == 1) {
                        clearPrefixFlags(zArr);
                        return i6;
                    }
                    i5 -= 2;
                }
                i5 += 3;
            }
            if (i3 <= 2 ? i3 != 2 ? !zArr[1] || bArr[i4] != 1 : !(zArr[2] && bArr[i2 - 2] == 0 && bArr[i4] == 1) : !(bArr[i2 - 3] == 0 && bArr[i2 - 2] == 0 && bArr[i4] == 1)) {
                z2 = false;
            } else {
                z2 = true;
            }
            zArr[0] = z2;
            if (i3 <= 1 ? !zArr[2] || bArr[i4] != 0 : !(bArr[i2 - 2] == 0 && bArr[i4] == 0)) {
                z3 = false;
            } else {
                z3 = true;
            }
            zArr[1] = z3;
            if (bArr[i4] == 0) {
                z4 = true;
            }
            zArr[2] = z4;
            return i2;
        } else {
            clearPrefixFlags(zArr);
            return i - 1;
        }
    }

    public static int getH265NalUnitType(byte[] bArr, int i) {
        return (bArr[i + 3] & 126) >> 1;
    }

    public static int getNalUnitType(byte[] bArr, int i) {
        return bArr[i + 3] & 31;
    }

    public static boolean isNalUnitSei(@Nullable String str, byte b2) {
        if ("video/avc".equals(str) && (b2 & 31) == 6) {
            return true;
        }
        if (!"video/hevc".equals(str) || ((b2 & 126) >> 1) != 39) {
            return false;
        }
        return true;
    }

    public static PpsData parsePpsNalUnit(byte[] bArr, int i, int i2) {
        ParsableNalUnitBitArray parsableNalUnitBitArray = new ParsableNalUnitBitArray(bArr, i, i2);
        parsableNalUnitBitArray.skipBits(8);
        int readUnsignedExpGolombCodedInt = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        int readUnsignedExpGolombCodedInt2 = parsableNalUnitBitArray.readUnsignedExpGolombCodedInt();
        parsableNalUnitBitArray.skipBit();
        return new PpsData(readUnsignedExpGolombCodedInt, readUnsignedExpGolombCodedInt2, parsableNalUnitBitArray.readBit());
    }

    /* JADX WARNING: Removed duplicated region for block: B:60:0x00f1  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x014f  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0163  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.util.NalUnitUtil.SpsData parseSpsNalUnit(byte[] r19, int r20, int r21) {
        /*
            com.google.android.exoplayer2.util.ParsableNalUnitBitArray r0 = new com.google.android.exoplayer2.util.ParsableNalUnitBitArray
            r1 = r19
            r2 = r20
            r3 = r21
            r0.<init>(r1, r2, r3)
            r1 = 8
            r0.skipBits(r1)
            int r3 = r0.readBits(r1)
            int r4 = r0.readBits(r1)
            int r5 = r0.readBits(r1)
            int r6 = r0.readUnsignedExpGolombCodedInt()
            r1 = 100
            r2 = 3
            r7 = 1
            if (r3 == r1) goto L_0x004f
            r1 = 110(0x6e, float:1.54E-43)
            if (r3 == r1) goto L_0x004f
            r1 = 122(0x7a, float:1.71E-43)
            if (r3 == r1) goto L_0x004f
            r1 = 244(0xf4, float:3.42E-43)
            if (r3 == r1) goto L_0x004f
            r1 = 44
            if (r3 == r1) goto L_0x004f
            r1 = 83
            if (r3 == r1) goto L_0x004f
            r1 = 86
            if (r3 == r1) goto L_0x004f
            r1 = 118(0x76, float:1.65E-43)
            if (r3 == r1) goto L_0x004f
            r1 = 128(0x80, float:1.794E-43)
            if (r3 == r1) goto L_0x004f
            r1 = 138(0x8a, float:1.93E-43)
            if (r3 != r1) goto L_0x004b
            goto L_0x004f
        L_0x004b:
            r1 = 1
            r2 = 0
            r10 = 0
            goto L_0x009f
        L_0x004f:
            int r1 = r0.readUnsignedExpGolombCodedInt()
            if (r1 != r2) goto L_0x005a
            boolean r8 = r0.readBit()
            goto L_0x005b
        L_0x005a:
            r8 = 0
        L_0x005b:
            r0.readUnsignedExpGolombCodedInt()
            r0.readUnsignedExpGolombCodedInt()
            r0.skipBit()
            boolean r9 = r0.readBit()
            if (r9 == 0) goto L_0x009e
            if (r1 == r2) goto L_0x006f
            r2 = 8
            goto L_0x0071
        L_0x006f:
            r2 = 12
        L_0x0071:
            r9 = 0
        L_0x0072:
            if (r9 >= r2) goto L_0x009e
            boolean r10 = r0.readBit()
            if (r10 == 0) goto L_0x009b
            r10 = 6
            if (r9 >= r10) goto L_0x0080
            r10 = 16
            goto L_0x0082
        L_0x0080:
            r10 = 64
        L_0x0082:
            r11 = 0
            r12 = 8
            r13 = 8
        L_0x0087:
            if (r11 >= r10) goto L_0x009b
            if (r12 == 0) goto L_0x0094
            int r12 = r0.readSignedExpGolombCodedInt()
            int r12 = r12 + r13
            int r12 = r12 + 256
            int r12 = r12 % 256
        L_0x0094:
            if (r12 != 0) goto L_0x0097
            goto L_0x0098
        L_0x0097:
            r13 = r12
        L_0x0098:
            int r11 = r11 + 1
            goto L_0x0087
        L_0x009b:
            int r9 = r9 + 1
            goto L_0x0072
        L_0x009e:
            r10 = r8
        L_0x009f:
            int r2 = r0.readUnsignedExpGolombCodedInt()
            int r12 = r2 + 4
            int r13 = r0.readUnsignedExpGolombCodedInt()
            if (r13 != 0) goto L_0x00b2
            int r2 = r0.readUnsignedExpGolombCodedInt()
            int r2 = r2 + 4
            goto L_0x00d4
        L_0x00b2:
            if (r13 != r7) goto L_0x00d3
            boolean r2 = r0.readBit()
            r0.readSignedExpGolombCodedInt()
            r0.readSignedExpGolombCodedInt()
            int r8 = r0.readUnsignedExpGolombCodedInt()
            long r8 = (long) r8
            r11 = 0
        L_0x00c4:
            long r14 = (long) r11
            int r16 = (r14 > r8 ? 1 : (r14 == r8 ? 0 : -1))
            if (r16 >= 0) goto L_0x00cf
            r0.readUnsignedExpGolombCodedInt()
            int r11 = r11 + 1
            goto L_0x00c4
        L_0x00cf:
            r8 = 0
            r15 = r2
            r14 = 0
            goto L_0x00d7
        L_0x00d3:
            r2 = 0
        L_0x00d4:
            r8 = 0
            r14 = r2
            r15 = 0
        L_0x00d7:
            r0.readUnsignedExpGolombCodedInt()
            r0.skipBit()
            int r2 = r0.readUnsignedExpGolombCodedInt()
            int r2 = r2 + r7
            int r8 = r0.readUnsignedExpGolombCodedInt()
            int r8 = r8 + r7
            boolean r11 = r0.readBit()
            int r9 = 2 - r11
            int r9 = r9 * r8
            if (r11 != 0) goto L_0x00f4
            r0.skipBit()
        L_0x00f4:
            r0.skipBit()
            int r2 = r2 * 16
            int r9 = r9 * 16
            boolean r8 = r0.readBit()
            if (r8 == 0) goto L_0x0135
            int r8 = r0.readUnsignedExpGolombCodedInt()
            int r16 = r0.readUnsignedExpGolombCodedInt()
            int r17 = r0.readUnsignedExpGolombCodedInt()
            int r18 = r0.readUnsignedExpGolombCodedInt()
            if (r1 != 0) goto L_0x0116
            int r1 = 2 - r11
            goto L_0x012a
        L_0x0116:
            r7 = 3
            if (r1 != r7) goto L_0x011d
            r7 = 1
            r19 = 1
            goto L_0x0120
        L_0x011d:
            r7 = 2
            r19 = 2
        L_0x0120:
            r7 = 1
            if (r1 != r7) goto L_0x0124
            r7 = 2
        L_0x0124:
            int r1 = 2 - r11
            int r1 = r1 * r7
            r7 = r19
        L_0x012a:
            int r8 = r8 + r16
            int r8 = r8 * r7
            int r2 = r2 - r8
            int r17 = r17 + r18
            int r17 = r17 * r1
            int r9 = r9 - r17
        L_0x0135:
            r7 = r2
            r8 = r9
            boolean r1 = r0.readBit()
            r2 = 1065353216(0x3f800000, float:1.0)
            if (r1 == 0) goto L_0x0176
            boolean r1 = r0.readBit()
            if (r1 == 0) goto L_0x0176
            r1 = 8
            int r1 = r0.readBits(r1)
            r9 = 255(0xff, float:3.57E-43)
            if (r1 != r9) goto L_0x0163
            r1 = 16
            int r9 = r0.readBits(r1)
            int r0 = r0.readBits(r1)
            if (r9 == 0) goto L_0x0161
            if (r0 == 0) goto L_0x0161
            float r1 = (float) r9
            float r0 = (float) r0
            float r2 = r1 / r0
        L_0x0161:
            r9 = r2
            goto L_0x017a
        L_0x0163:
            r0 = 17
            if (r1 >= r0) goto L_0x016d
            float[] r0 = b
            r0 = r0[r1]
            r9 = r0
            goto L_0x017a
        L_0x016d:
            r0 = 46
            java.lang.String r2 = "Unexpected aspect_ratio_idc value: "
            java.lang.String r9 = "NalUnitUtil"
            defpackage.y2.t(r0, r2, r1, r9)
        L_0x0176:
            r0 = 1065353216(0x3f800000, float:1.0)
            r9 = 1065353216(0x3f800000, float:1.0)
        L_0x017a:
            com.google.android.exoplayer2.util.NalUnitUtil$SpsData r0 = new com.google.android.exoplayer2.util.NalUnitUtil$SpsData
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.NalUnitUtil.parseSpsNalUnit(byte[], int, int):com.google.android.exoplayer2.util.NalUnitUtil$SpsData");
    }

    public static int unescapeStream(byte[] bArr, int i) {
        int i2;
        synchronized (c) {
            int i3 = 0;
            int i4 = 0;
            while (i3 < i) {
                while (true) {
                    if (i3 < i - 2) {
                        if (bArr[i3] == 0 && bArr[i3 + 1] == 0 && bArr[i3 + 2] == 3) {
                            break;
                        }
                        i3++;
                    } else {
                        i3 = i;
                        break;
                    }
                }
                if (i3 < i) {
                    int[] iArr = d;
                    if (iArr.length <= i4) {
                        d = Arrays.copyOf(iArr, iArr.length * 2);
                    }
                    d[i4] = i3;
                    i3 += 3;
                    i4++;
                }
            }
            i2 = i - i4;
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < i4; i7++) {
                int i8 = d[i7] - i6;
                System.arraycopy(bArr, i6, bArr, i5, i8);
                int i9 = i5 + i8;
                int i10 = i9 + 1;
                bArr[i9] = 0;
                i5 = i10 + 1;
                bArr[i10] = 0;
                i6 += i8 + 3;
            }
            System.arraycopy(bArr, i6, bArr, i5, i2 - i5);
        }
        return i2;
    }
}
