package com.google.android.exoplayer2.video.spherical;

public final class a {
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01aa  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x01c1 A[SYNTHETIC] */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<com.google.android.exoplayer2.video.spherical.Projection.Mesh> a(com.google.android.exoplayer2.util.ParsableByteArray r27) {
        /*
            r0 = r27
            int r1 = r27.readUnsignedByte()
            r2 = 0
            if (r1 == 0) goto L_0x000a
            return r2
        L_0x000a:
            r1 = 7
            r0.skipBytes(r1)
            int r3 = r27.readInt()
            r4 = 1684433976(0x64666c38, float:1.7002196E22)
            r5 = 1
            if (r3 != r4) goto L_0x0037
            com.google.android.exoplayer2.util.ParsableByteArray r3 = new com.google.android.exoplayer2.util.ParsableByteArray
            r3.<init>()
            java.util.zip.Inflater r4 = new java.util.zip.Inflater
            r4.<init>(r5)
            boolean r0 = com.google.android.exoplayer2.util.Util.inflate(r0, r3, r4)     // Catch:{ all -> 0x0031 }
            if (r0 != 0) goto L_0x002c
            r4.end()
            return r2
        L_0x002c:
            r4.end()
            r0 = r3
            goto L_0x003d
        L_0x0031:
            r0 = move-exception
            r1 = r0
            r4.end()
            throw r1
        L_0x0037:
            r4 = 1918990112(0x72617720, float:4.465801E30)
            if (r3 == r4) goto L_0x003d
            return r2
        L_0x003d:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            int r4 = r0.getPosition()
            int r6 = r0.limit()
        L_0x004a:
            if (r4 >= r6) goto L_0x01c3
            int r7 = r0.readInt()
            int r7 = r7 + r4
            if (r7 <= r4) goto L_0x01c1
            if (r7 <= r6) goto L_0x0057
            goto L_0x01c1
        L_0x0057:
            int r4 = r0.readInt()
            r8 = 1835365224(0x6d657368, float:4.438224E27)
            if (r4 != r8) goto L_0x01b0
            int r4 = r0.readInt()
            r8 = 10000(0x2710, float:1.4013E-41)
            if (r4 <= r8) goto L_0x0069
            goto L_0x007f
        L_0x0069:
            float[] r8 = new float[r4]
            r10 = 0
        L_0x006c:
            if (r10 >= r4) goto L_0x0077
            float r11 = r0.readFloat()
            r8[r10] = r11
            int r10 = r10 + 1
            goto L_0x006c
        L_0x0077:
            int r10 = r0.readInt()
            r11 = 32000(0x7d00, float:4.4842E-41)
            if (r10 <= r11) goto L_0x0087
        L_0x007f:
            r27 = r3
        L_0x0081:
            r19 = r6
        L_0x0083:
            r20 = 1
            goto L_0x0181
        L_0x0087:
            r11 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r13 = java.lang.Math.log(r11)
            r27 = r3
            double r2 = (double) r4
            double r2 = r2 * r11
            double r2 = java.lang.Math.log(r2)
            double r2 = r2 / r13
            double r2 = java.lang.Math.ceil(r2)
            int r2 = (int) r2
            com.google.android.exoplayer2.util.ParsableBitArray r3 = new com.google.android.exoplayer2.util.ParsableBitArray
            byte[] r9 = r0.getData()
            r3.<init>(r9)
            int r9 = r0.getPosition()
            r15 = 8
            int r9 = r9 * 8
            r3.setPosition(r9)
            int r9 = r10 * 5
            float[] r9 = new float[r9]
            r11 = 5
            int[] r12 = new int[r11]
            r15 = 0
            r18 = 0
        L_0x00ba:
            if (r15 >= r10) goto L_0x00e6
            r1 = 0
        L_0x00bd:
            if (r1 >= r11) goto L_0x00e1
            r19 = r12[r1]
            int r20 = r3.readBits(r2)
            int r21 = r20 >> 1
            r11 = r20 & 1
            int r11 = -r11
            r11 = r11 ^ r21
            int r11 = r11 + r19
            if (r11 >= r4) goto L_0x0081
            if (r11 >= 0) goto L_0x00d3
            goto L_0x010f
        L_0x00d3:
            int r19 = r18 + 1
            r20 = r8[r11]
            r9[r18] = r20
            r12[r1] = r11
            int r1 = r1 + 1
            r18 = r19
            r11 = 5
            goto L_0x00bd
        L_0x00e1:
            int r15 = r15 + 1
            r1 = 7
            r11 = 5
            goto L_0x00ba
        L_0x00e6:
            int r1 = r3.getPosition()
            r2 = 7
            int r1 = r1 + r2
            r1 = r1 & -8
            r3.setPosition(r1)
            r1 = 32
            int r4 = r3.readBits(r1)
            com.google.android.exoplayer2.video.spherical.Projection$SubMesh[] r8 = new com.google.android.exoplayer2.video.spherical.Projection.SubMesh[r4]
            r11 = 0
        L_0x00fa:
            if (r11 >= r4) goto L_0x019e
            r12 = 8
            int r15 = r3.readBits(r12)
            int r2 = r3.readBits(r12)
            int r12 = r3.readBits(r1)
            r1 = 128000(0x1f400, float:1.79366E-40)
            if (r12 <= r1) goto L_0x0111
        L_0x010f:
            goto L_0x0081
        L_0x0111:
            r19 = r6
            double r5 = (double) r10
            r16 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r5 = r5 * r16
            double r5 = java.lang.Math.log(r5)
            double r5 = r5 / r13
            double r5 = java.lang.Math.ceil(r5)
            int r5 = (int) r5
            int r6 = r12 * 3
            float[] r6 = new float[r6]
            int r1 = r12 * 2
            float[] r1 = new float[r1]
            r21 = r1
            r1 = 0
            r22 = 0
        L_0x012f:
            if (r1 >= r12) goto L_0x0183
            int r23 = r3.readBits(r5)
            int r24 = r23 >> 1
            r25 = r3
            r20 = 1
            r3 = r23 & 1
            r23 = r4
            r4 = r21
            int r3 = -r3
            r3 = r3 ^ r24
            int r3 = r3 + r22
            if (r3 < 0) goto L_0x0083
            if (r3 < r10) goto L_0x014c
            goto L_0x0083
        L_0x014c:
            int r21 = r1 * 3
            int r22 = r3 * 5
            r24 = r9[r22]
            r6[r21] = r24
            int r24 = r21 + 1
            int r26 = r22 + 1
            r26 = r9[r26]
            r6[r24] = r26
            int r21 = r21 + 2
            int r24 = r22 + 2
            r24 = r9[r24]
            r6[r21] = r24
            int r21 = r1 * 2
            int r24 = r22 + 3
            r24 = r9[r24]
            r4[r21] = r24
            r20 = 1
            int r21 = r21 + 1
            int r22 = r22 + 4
            r22 = r9[r22]
            r4[r21] = r22
            int r1 = r1 + 1
            r22 = r3
            r21 = r4
            r4 = r23
            r3 = r25
            goto L_0x012f
        L_0x0181:
            r1 = 0
            goto L_0x01a7
        L_0x0183:
            r25 = r3
            r23 = r4
            r4 = r21
            r20 = 1
            com.google.android.exoplayer2.video.spherical.Projection$SubMesh r1 = new com.google.android.exoplayer2.video.spherical.Projection$SubMesh
            r1.<init>(r15, r6, r4, r2)
            r8[r11] = r1
            int r11 = r11 + 1
            r6 = r19
            r4 = r23
            r1 = 32
            r2 = 7
            r5 = 1
            goto L_0x00fa
        L_0x019e:
            r19 = r6
            r20 = 1
            com.google.android.exoplayer2.video.spherical.Projection$Mesh r1 = new com.google.android.exoplayer2.video.spherical.Projection$Mesh
            r1.<init>(r8)
        L_0x01a7:
            if (r1 != 0) goto L_0x01aa
            goto L_0x01c1
        L_0x01aa:
            r2 = r27
            r2.add(r1)
            goto L_0x01b5
        L_0x01b0:
            r2 = r3
            r19 = r6
            r20 = 1
        L_0x01b5:
            r0.setPosition(r7)
            r3 = r2
            r4 = r7
            r6 = r19
            r1 = 7
            r2 = 0
            r5 = 1
            goto L_0x004a
        L_0x01c1:
            r2 = 0
            goto L_0x01c4
        L_0x01c3:
            r2 = r3
        L_0x01c4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.spherical.a.a(com.google.android.exoplayer2.util.ParsableByteArray):java.util.ArrayList");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x005c  */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.video.spherical.Projection decode(byte[] r7, int r8) {
        /*
            com.google.android.exoplayer2.util.ParsableByteArray r0 = new com.google.android.exoplayer2.util.ParsableByteArray
            r0.<init>((byte[]) r7)
            r7 = 4
            r1 = 1
            r2 = 0
            r3 = 0
            r0.skipBytes(r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            int r7 = r0.readInt()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            r0.setPosition(r2)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            r4 = 1886547818(0x70726f6a, float:3.0012025E29)
            if (r7 != r4) goto L_0x001a
            r7 = 1
            goto L_0x001b
        L_0x001a:
            r7 = 0
        L_0x001b:
            if (r7 == 0) goto L_0x0052
            r7 = 8
            r0.skipBytes(r7)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            int r7 = r0.getPosition()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            int r4 = r0.limit()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
        L_0x002a:
            if (r7 >= r4) goto L_0x0058
            int r5 = r0.readInt()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            int r5 = r5 + r7
            if (r5 <= r7) goto L_0x0058
            if (r5 <= r4) goto L_0x0036
            goto L_0x0058
        L_0x0036:
            int r7 = r0.readInt()     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            r6 = 2037673328(0x79746d70, float:7.9321256E34)
            if (r7 == r6) goto L_0x004a
            r6 = 1836279920(0x6d736870, float:4.7081947E27)
            if (r7 != r6) goto L_0x0045
            goto L_0x004a
        L_0x0045:
            r0.setPosition(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            r7 = r5
            goto L_0x002a
        L_0x004a:
            r0.setLimit(r5)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            java.util.ArrayList r7 = a(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            goto L_0x0059
        L_0x0052:
            java.util.ArrayList r7 = a(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0057 }
            goto L_0x0059
        L_0x0057:
        L_0x0058:
            r7 = r3
        L_0x0059:
            if (r7 != 0) goto L_0x005c
            return r3
        L_0x005c:
            int r0 = r7.size()
            if (r0 == r1) goto L_0x0078
            r4 = 2
            if (r0 == r4) goto L_0x0066
            return r3
        L_0x0066:
            com.google.android.exoplayer2.video.spherical.Projection r0 = new com.google.android.exoplayer2.video.spherical.Projection
            java.lang.Object r2 = r7.get(r2)
            com.google.android.exoplayer2.video.spherical.Projection$Mesh r2 = (com.google.android.exoplayer2.video.spherical.Projection.Mesh) r2
            java.lang.Object r7 = r7.get(r1)
            com.google.android.exoplayer2.video.spherical.Projection$Mesh r7 = (com.google.android.exoplayer2.video.spherical.Projection.Mesh) r7
            r0.<init>(r2, r7, r8)
            return r0
        L_0x0078:
            com.google.android.exoplayer2.video.spherical.Projection r0 = new com.google.android.exoplayer2.video.spherical.Projection
            java.lang.Object r7 = r7.get(r2)
            com.google.android.exoplayer2.video.spherical.Projection$Mesh r7 = (com.google.android.exoplayer2.video.spherical.Projection.Mesh) r7
            r0.<init>(r7, r8)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.video.spherical.a.decode(byte[], int):com.google.android.exoplayer2.video.spherical.Projection");
    }
}
