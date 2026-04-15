package com.google.android.exoplayer2.mediacodec;

import android.graphics.Point;
import android.media.MediaCodecInfo;
import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

public final class MediaCodecInfo {
    public final String a;
    public final String b;
    public final String c;
    @Nullable
    public final MediaCodecInfo.CodecCapabilities d;
    public final boolean e;
    public final boolean f;
    public final boolean g;

    @VisibleForTesting
    public MediaCodecInfo(String str, String str2, String str3, @Nullable MediaCodecInfo.CodecCapabilities codecCapabilities, boolean z, boolean z2) {
        this.a = (String) Assertions.checkNotNull(str);
        this.b = str2;
        this.c = str3;
        this.d = codecCapabilities;
        this.e = z;
        this.f = z2;
        this.g = MimeTypes.isVideo(str2);
    }

    @RequiresApi(21)
    public static boolean a(MediaCodecInfo.VideoCapabilities videoCapabilities, int i, int i2, double d2) {
        int widthAlignment = videoCapabilities.getWidthAlignment();
        int heightAlignment = videoCapabilities.getHeightAlignment();
        Point point = new Point(Util.ceilDivide(i, widthAlignment) * widthAlignment, Util.ceilDivide(i2, heightAlignment) * heightAlignment);
        int i3 = point.x;
        int i4 = point.y;
        if (d2 == -1.0d || d2 < 1.0d) {
            return videoCapabilities.isSizeSupported(i3, i4);
        }
        return videoCapabilities.areSizeAndRateSupported(i3, i4, Math.floor(d2));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006f, code lost:
        if (r14 != false) goto L_0x0074;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x005e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.mediacodec.MediaCodecInfo newInstance(java.lang.String r7, java.lang.String r8, java.lang.String r9, @androidx.annotation.Nullable android.media.MediaCodecInfo.CodecCapabilities r10, boolean r11, boolean r12, boolean r13, boolean r14, boolean r15) {
        /*
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r11 = new com.google.android.exoplayer2.mediacodec.MediaCodecInfo
            r12 = 1
            r13 = 0
            if (r14 != 0) goto L_0x0048
            if (r10 == 0) goto L_0x0048
            int r14 = com.google.android.exoplayer2.util.Util.a
            r0 = 19
            if (r14 < r0) goto L_0x0018
            java.lang.String r0 = "adaptive-playback"
            boolean r0 = r10.isFeatureSupported(r0)
            if (r0 == 0) goto L_0x0018
            r0 = 1
            goto L_0x0019
        L_0x0018:
            r0 = 0
        L_0x0019:
            if (r0 == 0) goto L_0x0048
            r0 = 22
            if (r14 > r0) goto L_0x0043
            java.lang.String r14 = com.google.android.exoplayer2.util.Util.d
            java.lang.String r0 = "ODROID-XU3"
            boolean r0 = r0.equals(r14)
            if (r0 != 0) goto L_0x0031
            java.lang.String r0 = "Nexus 10"
            boolean r14 = r0.equals(r14)
            if (r14 == 0) goto L_0x0043
        L_0x0031:
            java.lang.String r14 = "OMX.Exynos.AVC.Decoder"
            boolean r14 = r14.equals(r7)
            if (r14 != 0) goto L_0x0041
            java.lang.String r14 = "OMX.Exynos.AVC.Decoder.secure"
            boolean r14 = r14.equals(r7)
            if (r14 == 0) goto L_0x0043
        L_0x0041:
            r14 = 1
            goto L_0x0044
        L_0x0043:
            r14 = 0
        L_0x0044:
            if (r14 != 0) goto L_0x0048
            r5 = 1
            goto L_0x0049
        L_0x0048:
            r5 = 0
        L_0x0049:
            r14 = 21
            if (r10 == 0) goto L_0x005c
            int r0 = com.google.android.exoplayer2.util.Util.a
            if (r0 < r14) goto L_0x005b
            java.lang.String r0 = "tunneled-playback"
            boolean r0 = r10.isFeatureSupported(r0)
            if (r0 == 0) goto L_0x005b
            r0 = 1
            goto L_0x005c
        L_0x005b:
            r0 = 0
        L_0x005c:
            if (r15 != 0) goto L_0x0074
            if (r10 == 0) goto L_0x0072
            int r15 = com.google.android.exoplayer2.util.Util.a
            if (r15 < r14) goto L_0x006e
            java.lang.String r14 = "secure-playback"
            boolean r14 = r10.isFeatureSupported(r14)
            if (r14 == 0) goto L_0x006e
            r14 = 1
            goto L_0x006f
        L_0x006e:
            r14 = 0
        L_0x006f:
            if (r14 == 0) goto L_0x0072
            goto L_0x0074
        L_0x0072:
            r6 = 0
            goto L_0x0075
        L_0x0074:
            r6 = 1
        L_0x0075:
            r0 = r11
            r1 = r7
            r2 = r8
            r3 = r9
            r4 = r10
            r0.<init>(r1, r2, r3, r4, r5, r6)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecInfo.newInstance(java.lang.String, java.lang.String, java.lang.String, android.media.MediaCodecInfo$CodecCapabilities, boolean, boolean, boolean, boolean, boolean):com.google.android.exoplayer2.mediacodec.MediaCodecInfo");
    }

    @RequiresApi(21)
    @Nullable
    public Point alignVideoSizeV21(int i, int i2) {
        MediaCodecInfo.VideoCapabilities videoCapabilities;
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.d;
        if (codecCapabilities == null || (videoCapabilities = codecCapabilities.getVideoCapabilities()) == null) {
            return null;
        }
        int widthAlignment = videoCapabilities.getWidthAlignment();
        int heightAlignment = videoCapabilities.getHeightAlignment();
        return new Point(Util.ceilDivide(i, widthAlignment) * widthAlignment, Util.ceilDivide(i2, heightAlignment) * heightAlignment);
    }

    public final void b(String str) {
        String str2 = Util.e;
        int c2 = y2.c(str, 20);
        String str3 = this.a;
        int c3 = y2.c(str3, c2);
        String str4 = this.b;
        StringBuilder l = y2.l(y2.c(str2, y2.c(str4, c3)), "NoSupport [", str, "] [", str3);
        l.append(", ");
        l.append(str4);
        l.append("] [");
        l.append(str2);
        l.append("]");
        Log.d("MediaCodecInfo", l.toString());
    }

    public DecoderReuseEvaluation canReuseCodec(Format format, Format format2) {
        int i;
        int i2;
        boolean z = false;
        if (!Util.areEqual(format.p, format2.p)) {
            i = 8;
        } else {
            i = 0;
        }
        if (this.g) {
            if (format.x != format2.x) {
                i |= 1024;
            }
            if (!this.e && !(format.u == format2.u && format.v == format2.v)) {
                i |= 512;
            }
            if (!Util.areEqual(format.ab, format2.ab)) {
                i |= 2048;
            }
            if (Util.d.startsWith("SM-T230") && "OMX.MARVELL.VIDEO.HW.CODA7542DECODER".equals(this.a)) {
                z = true;
            }
            if (z && !format.initializationDataEquals(format2)) {
                i |= 2;
            }
            if (i == 0) {
                String str = this.a;
                if (format.initializationDataEquals(format2)) {
                    i2 = 3;
                } else {
                    i2 = 2;
                }
                return new DecoderReuseEvaluation(str, format, format2, i2, 0);
            }
        } else {
            if (format.ac != format2.ac) {
                i |= 4096;
            }
            if (format.ad != format2.ad) {
                i |= 8192;
            }
            if (format.ae != format2.ae) {
                i |= 16384;
            }
            String str2 = this.b;
            if (i == 0 && "audio/mp4a-latm".equals(str2)) {
                Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
                Pair<Integer, Integer> codecProfileAndLevel2 = MediaCodecUtil.getCodecProfileAndLevel(format2);
                if (!(codecProfileAndLevel == null || codecProfileAndLevel2 == null)) {
                    int intValue = ((Integer) codecProfileAndLevel.first).intValue();
                    int intValue2 = ((Integer) codecProfileAndLevel2.first).intValue();
                    if (intValue == 42 && intValue2 == 42) {
                        return new DecoderReuseEvaluation(this.a, format, format2, 3, 0);
                    }
                }
            }
            if (!format.initializationDataEquals(format2)) {
                i |= 32;
            }
            if ("audio/opus".equals(str2)) {
                i |= 2;
            }
            if (i == 0) {
                return new DecoderReuseEvaluation(this.a, format, format2, 1, 0);
            }
        }
        return new DecoderReuseEvaluation(this.a, format, format2, 0, i);
    }

    public int getMaxSupportedInstances() {
        MediaCodecInfo.CodecCapabilities codecCapabilities;
        if (Util.a < 23 || (codecCapabilities = this.d) == null) {
            return -1;
        }
        return codecCapabilities.getMaxSupportedInstances();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r0.profileLevels;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.media.MediaCodecInfo.CodecProfileLevel[] getProfileLevels() {
        /*
            r1 = this;
            android.media.MediaCodecInfo$CodecCapabilities r0 = r1.d
            if (r0 == 0) goto L_0x0008
            android.media.MediaCodecInfo$CodecProfileLevel[] r0 = r0.profileLevels
            if (r0 != 0) goto L_0x000b
        L_0x0008:
            r0 = 0
            android.media.MediaCodecInfo$CodecProfileLevel[] r0 = new android.media.MediaCodecInfo.CodecProfileLevel[r0]
        L_0x000b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecInfo.getProfileLevels():android.media.MediaCodecInfo$CodecProfileLevel[]");
    }

    @RequiresApi(21)
    public boolean isAudioChannelCountSupportedV21(int i) {
        int i2;
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.d;
        if (codecCapabilities == null) {
            b("channelCount.caps");
            return false;
        }
        MediaCodecInfo.AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            b("channelCount.aCaps");
            return false;
        }
        int maxInputChannelCount = audioCapabilities.getMaxInputChannelCount();
        if (maxInputChannelCount <= 1 && (Util.a < 26 || maxInputChannelCount <= 0)) {
            String str = this.b;
            if (!"audio/mpeg".equals(str) && !"audio/3gpp".equals(str) && !"audio/amr-wb".equals(str) && !"audio/mp4a-latm".equals(str) && !"audio/vorbis".equals(str) && !"audio/opus".equals(str) && !"audio/raw".equals(str) && !"audio/flac".equals(str) && !"audio/g711-alaw".equals(str) && !"audio/g711-mlaw".equals(str) && !"audio/gsm".equals(str)) {
                if ("audio/ac3".equals(str)) {
                    i2 = 6;
                } else if ("audio/eac3".equals(str)) {
                    i2 = 16;
                } else {
                    i2 = 30;
                }
                String str2 = this.a;
                StringBuilder sb = new StringBuilder(y2.c(str2, 59));
                sb.append("AssumedMaxChannelAdjustment: ");
                sb.append(str2);
                sb.append(", [");
                sb.append(maxInputChannelCount);
                sb.append(" to ");
                sb.append(i2);
                sb.append("]");
                Log.w("MediaCodecInfo", sb.toString());
                maxInputChannelCount = i2;
            }
        }
        if (maxInputChannelCount >= i) {
            return true;
        }
        StringBuilder sb2 = new StringBuilder(33);
        sb2.append("channelCount.support, ");
        sb2.append(i);
        b(sb2.toString());
        return false;
    }

    @RequiresApi(21)
    public boolean isAudioSampleRateSupportedV21(int i) {
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.d;
        if (codecCapabilities == null) {
            b("sampleRate.caps");
            return false;
        }
        MediaCodecInfo.AudioCapabilities audioCapabilities = codecCapabilities.getAudioCapabilities();
        if (audioCapabilities == null) {
            b("sampleRate.aCaps");
            return false;
        } else if (audioCapabilities.isSampleRateSupported(i)) {
            return true;
        } else {
            StringBuilder sb = new StringBuilder(31);
            sb.append("sampleRate.support, ");
            sb.append(i);
            b(sb.toString());
            return false;
        }
    }

    public boolean isCodecSupported(Format format) {
        String str;
        String mediaMimeType;
        int i;
        int i2;
        MediaCodecInfo.VideoCapabilities videoCapabilities;
        String str2 = format.m;
        if (str2 == null || (str = this.b) == null || (mediaMimeType = MimeTypes.getMediaMimeType(str2)) == null) {
            return true;
        }
        boolean equals = str.equals(mediaMimeType);
        String str3 = format.m;
        if (!equals) {
            StringBuilder sb = new StringBuilder(mediaMimeType.length() + y2.c(str3, 13));
            sb.append("codec.mime ");
            sb.append(str3);
            sb.append(", ");
            sb.append(mediaMimeType);
            b(sb.toString());
            return false;
        }
        Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
        if (codecProfileAndLevel == null) {
            return true;
        }
        int intValue = ((Integer) codecProfileAndLevel.first).intValue();
        int intValue2 = ((Integer) codecProfileAndLevel.second).intValue();
        if (!this.g && intValue != 42) {
            return true;
        }
        MediaCodecInfo.CodecProfileLevel[] profileLevels = getProfileLevels();
        if (Util.a <= 23 && "video/x-vnd.on2.vp9".equals(str) && profileLevels.length == 0) {
            MediaCodecInfo.CodecCapabilities codecCapabilities = this.d;
            if (codecCapabilities == null || (videoCapabilities = codecCapabilities.getVideoCapabilities()) == null) {
                i = 0;
            } else {
                i = videoCapabilities.getBitrateRange().getUpper().intValue();
            }
            if (i >= 180000000) {
                i2 = 1024;
            } else if (i >= 120000000) {
                i2 = 512;
            } else if (i >= 60000000) {
                i2 = 256;
            } else if (i >= 30000000) {
                i2 = 128;
            } else if (i >= 18000000) {
                i2 = 64;
            } else if (i >= 12000000) {
                i2 = 32;
            } else if (i >= 7200000) {
                i2 = 16;
            } else if (i >= 3600000) {
                i2 = 8;
            } else if (i >= 1800000) {
                i2 = 4;
            } else if (i >= 800000) {
                i2 = 2;
            } else {
                i2 = 1;
            }
            MediaCodecInfo.CodecProfileLevel codecProfileLevel = new MediaCodecInfo.CodecProfileLevel();
            codecProfileLevel.profile = 1;
            codecProfileLevel.level = i2;
            profileLevels = new MediaCodecInfo.CodecProfileLevel[]{codecProfileLevel};
        }
        for (MediaCodecInfo.CodecProfileLevel codecProfileLevel2 : profileLevels) {
            if (codecProfileLevel2.profile == intValue && codecProfileLevel2.level >= intValue2) {
                return true;
            }
        }
        StringBuilder sb2 = new StringBuilder(mediaMimeType.length() + y2.c(str3, 22));
        sb2.append("codec.profileLevel, ");
        sb2.append(str3);
        sb2.append(", ");
        sb2.append(mediaMimeType);
        b(sb2.toString());
        return false;
    }

    public boolean isFormatSupported(Format format) throws MediaCodecUtil.DecoderQueryException {
        int i;
        boolean z = false;
        if (!isCodecSupported(format)) {
            return false;
        }
        if (this.g) {
            int i2 = format.u;
            if (i2 <= 0 || (i = format.v) <= 0) {
                return true;
            }
            if (Util.a >= 21) {
                return isVideoSizeAndRateSupportedV21(i2, i, (double) format.w);
            }
            if (i2 * i <= MediaCodecUtil.maxH264DecodableFrameSize()) {
                z = true;
            }
            if (!z) {
                StringBuilder sb = new StringBuilder(40);
                sb.append("legacyFrameSize, ");
                sb.append(format.u);
                sb.append("x");
                sb.append(i);
                b(sb.toString());
            }
            return z;
        }
        if (Util.a >= 21) {
            int i3 = format.ad;
            if (i3 != -1 && !isAudioSampleRateSupportedV21(i3)) {
                return false;
            }
            int i4 = format.ac;
            if (i4 == -1 || isAudioChannelCountSupportedV21(i4)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean isHdr10PlusOutOfBandMetadataSupported() {
        if (Util.a >= 29 && "video/x-vnd.on2.vp9".equals(this.b)) {
            for (MediaCodecInfo.CodecProfileLevel codecProfileLevel : getProfileLevels()) {
                if (codecProfileLevel.profile == 16384) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSeamlessAdaptationSupported(Format format) {
        if (this.g) {
            return this.e;
        }
        Pair<Integer, Integer> codecProfileAndLevel = MediaCodecUtil.getCodecProfileAndLevel(format);
        return codecProfileAndLevel != null && ((Integer) codecProfileAndLevel.first).intValue() == 42;
    }

    @RequiresApi(21)
    public boolean isVideoSizeAndRateSupportedV21(int i, int i2, double d2) {
        boolean z;
        MediaCodecInfo.CodecCapabilities codecCapabilities = this.d;
        if (codecCapabilities == null) {
            b("sizeAndRate.caps");
            return false;
        }
        MediaCodecInfo.VideoCapabilities videoCapabilities = codecCapabilities.getVideoCapabilities();
        if (videoCapabilities == null) {
            b("sizeAndRate.vCaps");
            return false;
        }
        if (!a(videoCapabilities, i, i2, d2)) {
            if (i < i2) {
                String str = this.a;
                if (!"OMX.MTK.VIDEO.DECODER.HEVC".equals(str) || !"mcv5a".equals(Util.b)) {
                    z = true;
                } else {
                    z = false;
                }
                if (z && a(videoCapabilities, i2, i, d2)) {
                    StringBuilder sb = new StringBuilder(69);
                    sb.append("sizeAndRate.rotated, ");
                    sb.append(i);
                    sb.append("x");
                    sb.append(i2);
                    sb.append("x");
                    sb.append(d2);
                    String sb2 = sb.toString();
                    String str2 = Util.e;
                    int c2 = y2.c(str, y2.c(sb2, 25));
                    String str3 = this.b;
                    StringBuilder l = y2.l(y2.c(str2, y2.c(str3, c2)), "AssumedSupport [", sb2, "] [", str);
                    l.append(", ");
                    l.append(str3);
                    l.append("] [");
                    l.append(str2);
                    l.append("]");
                    Log.d("MediaCodecInfo", l.toString());
                }
            }
            StringBuilder sb3 = new StringBuilder(69);
            sb3.append("sizeAndRate.support, ");
            sb3.append(i);
            sb3.append("x");
            sb3.append(i2);
            sb3.append("x");
            sb3.append(d2);
            b(sb3.toString());
            return false;
        }
        return true;
    }

    public String toString() {
        return this.a;
    }

    @Deprecated
    public boolean isSeamlessAdaptationSupported(Format format, Format format2, boolean z) {
        if (!z && format.ab != null && format2.ab == null) {
            format2 = format2.buildUpon().setColorInfo(format.ab).build();
        }
        int i = canReuseCodec(format, format2).d;
        return i == 2 || i == 3;
    }
}
