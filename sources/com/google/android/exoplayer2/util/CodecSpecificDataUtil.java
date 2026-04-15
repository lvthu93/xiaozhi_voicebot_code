package com.google.android.exoplayer2.util;

import android.util.Pair;
import java.util.Collections;
import java.util.List;

public final class CodecSpecificDataUtil {
    public static final byte[] a = {0, 0, 0, 1};
    public static final String[] b = {"", "A", "B", "C"};

    public static String buildAvcCodecString(int i, int i2, int i3) {
        return String.format("avc1.%02X%02X%02X", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
    }

    public static List<byte[]> buildCea708InitializationData(boolean z) {
        return Collections.singletonList(z ? new byte[]{1} : new byte[]{0});
    }

    public static String buildHevcCodecStringFromSps(ParsableNalUnitBitArray parsableNalUnitBitArray) {
        char c;
        parsableNalUnitBitArray.skipBits(24);
        int readBits = parsableNalUnitBitArray.readBits(2);
        boolean readBit = parsableNalUnitBitArray.readBit();
        int readBits2 = parsableNalUnitBitArray.readBits(5);
        int i = 0;
        for (int i2 = 0; i2 < 32; i2++) {
            if (parsableNalUnitBitArray.readBit()) {
                i |= 1 << i2;
            }
        }
        int i3 = 6;
        int[] iArr = new int[6];
        for (int i4 = 0; i4 < 6; i4++) {
            iArr[i4] = parsableNalUnitBitArray.readBits(8);
        }
        int readBits3 = parsableNalUnitBitArray.readBits(8);
        Object[] objArr = new Object[5];
        objArr[0] = b[readBits];
        objArr[1] = Integer.valueOf(readBits2);
        objArr[2] = Integer.valueOf(i);
        if (readBit) {
            c = 'H';
        } else {
            c = 'L';
        }
        objArr[3] = Character.valueOf(c);
        objArr[4] = Integer.valueOf(readBits3);
        StringBuilder sb = new StringBuilder(Util.formatInvariant("hvc1.%s%d.%X.%c%d", objArr));
        while (i3 > 0 && iArr[i3 - 1] == 0) {
            i3--;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            sb.append(String.format(".%02X", new Object[]{Integer.valueOf(iArr[i5])}));
        }
        return sb.toString();
    }

    public static byte[] buildNalUnit(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[(i2 + 4)];
        System.arraycopy(a, 0, bArr2, 0, 4);
        System.arraycopy(bArr, i, bArr2, 4, i2);
        return bArr2;
    }

    public static Pair<Integer, Integer> parseAlacAudioSpecificConfig(byte[] bArr) {
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        parsableByteArray.setPosition(9);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.setPosition(20);
        return Pair.create(Integer.valueOf(parsableByteArray.readUnsignedIntToInt()), Integer.valueOf(readUnsignedByte));
    }

    public static boolean parseCea708InitializationData(List<byte[]> list) {
        if (list.size() == 1 && list.get(0).length == 1 && list.get(0)[0] == 1) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x004c A[LOOP:2: B:13:0x0030->B:25:0x004c, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0050 A[EDGE_INSN: B:41:0x0050->B:27:0x0050 ?: BREAK  , SYNTHETIC] */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[][] splitNalUnits(byte[] r11) {
        /*
            int r0 = r11.length
            r1 = 0
            int r0 = r0 - r1
            byte[] r2 = a
            r3 = 4
            r4 = 1
            if (r0 > r3) goto L_0x000b
        L_0x0009:
            r0 = 0
            goto L_0x001b
        L_0x000b:
            r0 = 0
        L_0x000c:
            if (r0 >= r3) goto L_0x001a
            int r5 = r1 + r0
            byte r5 = r11[r5]
            byte r6 = r2[r0]
            if (r5 == r6) goto L_0x0017
            goto L_0x0009
        L_0x0017:
            int r0 = r0 + 1
            goto L_0x000c
        L_0x001a:
            r0 = 1
        L_0x001b:
            if (r0 != 0) goto L_0x001f
            r11 = 0
            return r11
        L_0x001f:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r5 = 0
        L_0x0025:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r5)
            r0.add(r6)
            int r5 = r5 + r3
            int r6 = r11.length
            int r6 = r6 + -4
        L_0x0030:
            r7 = -1
            if (r5 > r6) goto L_0x004f
            int r8 = r11.length
            int r8 = r8 - r5
            if (r8 > r3) goto L_0x0039
        L_0x0037:
            r8 = 0
            goto L_0x0049
        L_0x0039:
            r8 = 0
        L_0x003a:
            if (r8 >= r3) goto L_0x0048
            int r9 = r5 + r8
            byte r9 = r11[r9]
            byte r10 = r2[r8]
            if (r9 == r10) goto L_0x0045
            goto L_0x0037
        L_0x0045:
            int r8 = r8 + 1
            goto L_0x003a
        L_0x0048:
            r8 = 1
        L_0x0049:
            if (r8 == 0) goto L_0x004c
            goto L_0x0050
        L_0x004c:
            int r5 = r5 + 1
            goto L_0x0030
        L_0x004f:
            r5 = -1
        L_0x0050:
            if (r5 != r7) goto L_0x0025
            int r2 = r0.size()
            byte[][] r2 = new byte[r2][]
            r3 = 0
        L_0x0059:
            int r4 = r0.size()
            if (r3 >= r4) goto L_0x0089
            java.lang.Object r4 = r0.get(r3)
            java.lang.Integer r4 = (java.lang.Integer) r4
            int r4 = r4.intValue()
            int r5 = r0.size()
            int r5 = r5 + r7
            if (r3 >= r5) goto L_0x007d
            int r5 = r3 + 1
            java.lang.Object r5 = r0.get(r5)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            goto L_0x007e
        L_0x007d:
            int r5 = r11.length
        L_0x007e:
            int r5 = r5 - r4
            byte[] r6 = new byte[r5]
            java.lang.System.arraycopy(r11, r4, r6, r1, r5)
            r2[r3] = r6
            int r3 = r3 + 1
            goto L_0x0059
        L_0x0089:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.CodecSpecificDataUtil.splitNalUnits(byte[]):byte[][]");
    }
}
