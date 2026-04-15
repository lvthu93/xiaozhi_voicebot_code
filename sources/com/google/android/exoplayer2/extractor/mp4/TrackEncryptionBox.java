package com.google.android.exoplayer2.extractor.mp4;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.extractor.TrackOutput;

public final class TrackEncryptionBox {
    public final boolean a;
    @Nullable
    public final String b;
    public final TrackOutput.CryptoData c;
    public final int d;
    @Nullable
    public final byte[] e;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
        if (r6.equals("cbc1") == false) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TrackEncryptionBox(boolean r5, @androidx.annotation.Nullable java.lang.String r6, int r7, byte[] r8, int r9, int r10, @androidx.annotation.Nullable byte[] r11) {
        /*
            r4 = this;
            r4.<init>()
            r0 = 0
            r1 = 1
            if (r7 != 0) goto L_0x0009
            r2 = 1
            goto L_0x000a
        L_0x0009:
            r2 = 0
        L_0x000a:
            if (r11 != 0) goto L_0x000e
            r3 = 1
            goto L_0x000f
        L_0x000e:
            r3 = 0
        L_0x000f:
            r2 = r2 ^ r3
            com.google.android.exoplayer2.util.Assertions.checkArgument(r2)
            r4.a = r5
            r4.b = r6
            r4.d = r7
            r4.e = r11
            com.google.android.exoplayer2.extractor.TrackOutput$CryptoData r5 = new com.google.android.exoplayer2.extractor.TrackOutput$CryptoData
            if (r6 != 0) goto L_0x0020
            goto L_0x007b
        L_0x0020:
            int r7 = r6.hashCode()
            r11 = 2
            r2 = -1
            switch(r7) {
                case 3046605: goto L_0x004c;
                case 3046671: goto L_0x0041;
                case 3049879: goto L_0x0036;
                case 3049895: goto L_0x002b;
                default: goto L_0x0029;
            }
        L_0x0029:
            r0 = -1
            goto L_0x0055
        L_0x002b:
            java.lang.String r7 = "cens"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x0034
            goto L_0x0029
        L_0x0034:
            r0 = 3
            goto L_0x0055
        L_0x0036:
            java.lang.String r7 = "cenc"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x003f
            goto L_0x0029
        L_0x003f:
            r0 = 2
            goto L_0x0055
        L_0x0041:
            java.lang.String r7 = "cbcs"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x004a
            goto L_0x0029
        L_0x004a:
            r0 = 1
            goto L_0x0055
        L_0x004c:
            java.lang.String r7 = "cbc1"
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x0055
            goto L_0x0029
        L_0x0055:
            switch(r0) {
                case 0: goto L_0x007a;
                case 1: goto L_0x007a;
                case 2: goto L_0x007b;
                case 3: goto L_0x007b;
                default: goto L_0x0058;
            }
        L_0x0058:
            int r7 = r6.length()
            int r7 = r7 + 68
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>(r7)
            java.lang.String r7 = "Unsupported protection scheme type '"
            r11.append(r7)
            r11.append(r6)
            java.lang.String r6 = "'. Assuming AES-CTR crypto mode."
            r11.append(r6)
            java.lang.String r6 = r11.toString()
            java.lang.String r7 = "TrackEncryptionBox"
            com.google.android.exoplayer2.util.Log.w(r7, r6)
            goto L_0x007b
        L_0x007a:
            r1 = 2
        L_0x007b:
            r5.<init>(r1, r8, r9, r10)
            r4.c = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.TrackEncryptionBox.<init>(boolean, java.lang.String, int, byte[], int, int, byte[]):void");
    }
}
