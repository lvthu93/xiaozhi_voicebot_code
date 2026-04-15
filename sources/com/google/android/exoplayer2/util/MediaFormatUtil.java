package com.google.android.exoplayer2.util;

import android.media.MediaFormat;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.video.ColorInfo;
import java.nio.ByteBuffer;
import java.util.List;

public final class MediaFormatUtil {
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0052, code lost:
        if (r3 != 4) goto L_0x005b;
     */
    @android.annotation.SuppressLint({"InlinedApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.media.MediaFormat createMediaFormatFromFormat(com.google.android.exoplayer2.Format r7) {
        /*
            android.media.MediaFormat r0 = new android.media.MediaFormat
            r0.<init>()
            int r1 = r7.l
            java.lang.String r2 = "bitrate"
            maybeSetInteger(r0, r2, r1)
            java.lang.String r1 = "channel-count"
            int r2 = r7.ac
            maybeSetInteger(r0, r1, r2)
            com.google.android.exoplayer2.video.ColorInfo r1 = r7.ab
            maybeSetColorInfo(r0, r1)
            java.lang.String r1 = "mime"
            java.lang.String r2 = r7.p
            maybeSetString(r0, r1, r2)
            java.lang.String r1 = "codecs-string"
            java.lang.String r2 = r7.m
            maybeSetString(r0, r1, r2)
            java.lang.String r1 = "frame-rate"
            float r2 = r7.w
            maybeSetFloat(r0, r1, r2)
            java.lang.String r1 = "width"
            int r2 = r7.u
            maybeSetInteger(r0, r1, r2)
            java.lang.String r1 = "height"
            int r2 = r7.v
            maybeSetInteger(r0, r1, r2)
            java.util.List<byte[]> r1 = r7.r
            setCsdBuffers(r0, r1)
            r1 = -1
            r2 = 2
            int r3 = r7.ae
            if (r3 != r1) goto L_0x0047
            goto L_0x005b
        L_0x0047:
            java.lang.String r1 = "exo-pcm-encoding-int"
            maybeSetInteger(r0, r1, r3)
            if (r3 == r2) goto L_0x0055
            r1 = 3
            if (r3 == r1) goto L_0x0056
            r1 = 4
            if (r3 == r1) goto L_0x0056
            goto L_0x005b
        L_0x0055:
            r1 = 2
        L_0x0056:
            java.lang.String r3 = "pcm-encoding"
            r0.setInteger(r3, r1)
        L_0x005b:
            java.lang.String r1 = "language"
            java.lang.String r3 = r7.g
            maybeSetString(r0, r1, r3)
            java.lang.String r1 = "max-input-size"
            int r3 = r7.q
            maybeSetInteger(r0, r1, r3)
            java.lang.String r1 = "sample-rate"
            int r3 = r7.ad
            maybeSetInteger(r0, r1, r3)
            java.lang.String r1 = "caption-service-number"
            int r3 = r7.ah
            maybeSetInteger(r0, r1, r3)
            java.lang.String r1 = "rotation-degrees"
            int r3 = r7.x
            r0.setInteger(r1, r3)
            int r1 = r7.h
            r3 = r1 & 4
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x0088
            r3 = 1
            goto L_0x0089
        L_0x0088:
            r3 = 0
        L_0x0089:
            java.lang.String r6 = "is-autoselect"
            r0.setInteger(r6, r3)
            r3 = r1 & 1
            if (r3 == 0) goto L_0x0094
            r3 = 1
            goto L_0x0095
        L_0x0094:
            r3 = 0
        L_0x0095:
            java.lang.String r6 = "is-default"
            r0.setInteger(r6, r3)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x009e
            r4 = 1
        L_0x009e:
            java.lang.String r1 = "is-forced-subtitle"
            r0.setInteger(r1, r4)
            java.lang.String r1 = "encoder-delay"
            int r2 = r7.af
            r0.setInteger(r1, r2)
            java.lang.String r1 = "encoder-padding"
            int r2 = r7.ag
            r0.setInteger(r1, r2)
            java.lang.String r1 = "exo-pixel-width-height-ratio-float"
            float r7 = r7.y
            r0.setFloat(r1, r7)
            r1 = 1073741824(0x40000000, float:2.0)
            r2 = 1065353216(0x3f800000, float:1.0)
            int r3 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r3 >= 0) goto L_0x00c5
            float r2 = (float) r1
            float r7 = r7 * r2
            int r5 = (int) r7
            goto L_0x00d1
        L_0x00c5:
            int r2 = (r7 > r2 ? 1 : (r7 == r2 ? 0 : -1))
            if (r2 <= 0) goto L_0x00d0
            float r2 = (float) r1
            float r2 = r2 / r7
            int r5 = (int) r2
            r1 = r5
            r5 = 1073741824(0x40000000, float:2.0)
            goto L_0x00d1
        L_0x00d0:
            r1 = 1
        L_0x00d1:
            java.lang.String r7 = "sar-width"
            r0.setInteger(r7, r5)
            java.lang.String r7 = "sar-height"
            r0.setInteger(r7, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.MediaFormatUtil.createMediaFormatFromFormat(com.google.android.exoplayer2.Format):android.media.MediaFormat");
    }

    public static void maybeSetByteBuffer(MediaFormat mediaFormat, String str, @Nullable byte[] bArr) {
        if (bArr != null) {
            mediaFormat.setByteBuffer(str, ByteBuffer.wrap(bArr));
        }
    }

    public static void maybeSetColorInfo(MediaFormat mediaFormat, @Nullable ColorInfo colorInfo) {
        if (colorInfo != null) {
            maybeSetInteger(mediaFormat, "color-transfer", colorInfo.g);
            maybeSetInteger(mediaFormat, "color-standard", colorInfo.c);
            maybeSetInteger(mediaFormat, "color-range", colorInfo.f);
            maybeSetByteBuffer(mediaFormat, "hdr-static-info", colorInfo.h);
        }
    }

    public static void maybeSetFloat(MediaFormat mediaFormat, String str, float f) {
        if (f != -1.0f) {
            mediaFormat.setFloat(str, f);
        }
    }

    public static void maybeSetInteger(MediaFormat mediaFormat, String str, int i) {
        if (i != -1) {
            mediaFormat.setInteger(str, i);
        }
    }

    public static void maybeSetString(MediaFormat mediaFormat, String str, @Nullable String str2) {
        if (str2 != null) {
            mediaFormat.setString(str, str2);
        }
    }

    public static void setCsdBuffers(MediaFormat mediaFormat, List<byte[]> list) {
        for (int i = 0; i < list.size(); i++) {
            mediaFormat.setByteBuffer(y2.d(15, "csd-", i), ByteBuffer.wrap(list.get(i)));
        }
    }
}
