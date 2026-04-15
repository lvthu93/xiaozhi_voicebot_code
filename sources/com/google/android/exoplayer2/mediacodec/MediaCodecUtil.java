package com.google.android.exoplayer2.mediacodec;

import android.annotation.SuppressLint;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.text.TextUtils;
import androidx.annotation.CheckResult;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@SuppressLint({"InlinedApi"})
public final class MediaCodecUtil {
    public static final Pattern a = Pattern.compile("^\\D?(\\d+)$");
    @GuardedBy("MediaCodecUtil.class")
    public static final HashMap<a, List<MediaCodecInfo>> b = new HashMap<>();
    public static int c = -1;

    public static class DecoderQueryException extends Exception {
        public DecoderQueryException(Exception exc) {
            super("Failed to query underlying media codecs", exc);
        }
    }

    public static final class a {
        public final String a;
        public final boolean b;
        public final boolean c;

        public a(String str, boolean z, boolean z2) {
            this.a = str;
            this.b = z;
            this.c = z2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != a.class) {
                return false;
            }
            a aVar = (a) obj;
            if (TextUtils.equals(this.a, aVar.a) && this.b == aVar.b && this.c == aVar.c) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i;
            int hashCode = (this.a.hashCode() + 31) * 31;
            int i2 = 1231;
            if (this.b) {
                i = 1231;
            } else {
                i = 1237;
            }
            int i3 = (hashCode + i) * 31;
            if (!this.c) {
                i2 = 1237;
            }
            return i3 + i2;
        }
    }

    public interface b {
        int getCodecCount();

        MediaCodecInfo getCodecInfoAt(int i);

        boolean isFeatureRequired(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities);

        boolean isFeatureSupported(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities);

        boolean secureDecodersExplicit();
    }

    public static final class c implements b {
        public int getCodecCount() {
            return MediaCodecList.getCodecCount();
        }

        public MediaCodecInfo getCodecInfoAt(int i) {
            return MediaCodecList.getCodecInfoAt(i);
        }

        public boolean isFeatureRequired(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            return false;
        }

        public boolean isFeatureSupported(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            if (!"secure-playback".equals(str) || !"video/avc".equals(str2)) {
                return false;
            }
            return true;
        }

        public boolean secureDecodersExplicit() {
            return false;
        }
    }

    @RequiresApi(21)
    public static final class d implements b {
        public final int a;
        @Nullable
        public MediaCodecInfo[] b;

        public d(boolean z, boolean z2) {
            int i;
            if (z || z2) {
                i = 1;
            } else {
                i = 0;
            }
            this.a = i;
        }

        public int getCodecCount() {
            if (this.b == null) {
                this.b = new MediaCodecList(this.a).getCodecInfos();
            }
            return this.b.length;
        }

        public MediaCodecInfo getCodecInfoAt(int i) {
            if (this.b == null) {
                this.b = new MediaCodecList(this.a).getCodecInfos();
            }
            return this.b[i];
        }

        public boolean isFeatureRequired(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            return codecCapabilities.isFeatureRequired(str);
        }

        public boolean isFeatureSupported(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities) {
            return codecCapabilities.isFeatureSupported(str);
        }

        public boolean secureDecodersExplicit() {
            return true;
        }
    }

    public interface e<T> {
        int getScore(T t);
    }

    public static void a(String str, ArrayList arrayList) {
        if ("audio/raw".equals(str)) {
            if (Util.a < 26 && Util.b.equals("R9") && arrayList.size() == 1 && ((MediaCodecInfo) arrayList.get(0)).a.equals("OMX.MTK.AUDIO.DECODER.RAW")) {
                arrayList.add(MediaCodecInfo.newInstance("OMX.google.raw.decoder", "audio/raw", "audio/raw", (MediaCodecInfo.CodecCapabilities) null, false, true, false, false, false));
            }
            Collections.sort(arrayList, new x5(0, new z6(8)));
        }
        int i = Util.a;
        if (i < 21 && arrayList.size() > 1) {
            String str2 = ((MediaCodecInfo) arrayList.get(0)).a;
            if ("OMX.SEC.mp3.dec".equals(str2) || "OMX.SEC.MP3.Decoder".equals(str2) || "OMX.brcm.audio.mp3.decoder".equals(str2)) {
                Collections.sort(arrayList, new x5(0, new z6(9)));
            }
        }
        if (i < 30 && arrayList.size() > 1 && "OMX.qti.audio.decoder.flac".equals(((MediaCodecInfo) arrayList.get(0)).a)) {
            arrayList.add((MediaCodecInfo) arrayList.remove(0));
        }
    }

    @Nullable
    public static String b(MediaCodecInfo mediaCodecInfo, String str, String str2) {
        for (String str3 : mediaCodecInfo.getSupportedTypes()) {
            if (str3.equalsIgnoreCase(str2)) {
                return str3;
            }
        }
        if (str2.equals("video/dolby-vision")) {
            if ("OMX.MS.HEVCDV.Decoder".equals(str)) {
                return "video/hevcdv";
            }
            if ("OMX.RTK.video.decoder".equals(str) || "OMX.realtek.video.decoder.tunneled".equals(str)) {
                return "video/dv_hevc";
            }
            return null;
        } else if (str2.equals("audio/alac") && "OMX.lge.alac.decoder".equals(str)) {
            return "audio/x-lg-alac";
        } else {
            if (!str2.equals("audio/flac") || !"OMX.lge.flac.decoder".equals(str)) {
                return null;
            }
            return "audio/x-lg-flac";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x0120 A[SYNTHETIC, Splitter:B:72:0x0120] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0154 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<com.google.android.exoplayer2.mediacodec.MediaCodecInfo> c(com.google.android.exoplayer2.mediacodec.MediaCodecUtil.a r22, com.google.android.exoplayer2.mediacodec.MediaCodecUtil.b r23) throws com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException {
        /*
            r1 = r22
            r2 = r23
            java.lang.String r3 = "secure-playback"
            java.lang.String r4 = "tunneled-playback"
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Exception -> 0x0188 }
            r5.<init>()     // Catch:{ Exception -> 0x0188 }
            java.lang.String r15 = r1.a     // Catch:{ Exception -> 0x0188 }
            int r14 = r23.getCodecCount()     // Catch:{ Exception -> 0x0188 }
            boolean r13 = r23.secureDecodersExplicit()     // Catch:{ Exception -> 0x0188 }
            r16 = 0
            r12 = 0
        L_0x001a:
            if (r12 >= r14) goto L_0x0187
            android.media.MediaCodecInfo r0 = r2.getCodecInfoAt(r12)     // Catch:{ Exception -> 0x0188 }
            int r6 = com.google.android.exoplayer2.util.Util.a     // Catch:{ Exception -> 0x0188 }
            r7 = 29
            if (r6 < r7) goto L_0x002e
            boolean r9 = r0.isAlias()     // Catch:{ Exception -> 0x0188 }
            if (r9 == 0) goto L_0x002e
            r9 = 1
            goto L_0x002f
        L_0x002e:
            r9 = 0
        L_0x002f:
            if (r9 == 0) goto L_0x0032
            goto L_0x006d
        L_0x0032:
            java.lang.String r11 = r0.getName()     // Catch:{ Exception -> 0x0188 }
            boolean r9 = d(r0, r11, r13, r15)     // Catch:{ Exception -> 0x0188 }
            if (r9 != 0) goto L_0x003d
            goto L_0x006d
        L_0x003d:
            java.lang.String r10 = b(r0, r11, r15)     // Catch:{ Exception -> 0x0188 }
            if (r10 != 0) goto L_0x0044
            goto L_0x006d
        L_0x0044:
            android.media.MediaCodecInfo$CodecCapabilities r9 = r0.getCapabilitiesForType(r10)     // Catch:{ Exception -> 0x010f }
            boolean r17 = r2.isFeatureSupported(r4, r10, r9)     // Catch:{ Exception -> 0x010f }
            boolean r18 = r2.isFeatureRequired(r4, r10, r9)     // Catch:{ Exception -> 0x010f }
            boolean r8 = r1.c     // Catch:{ Exception -> 0x010f }
            if (r8 != 0) goto L_0x0056
            if (r18 != 0) goto L_0x006d
        L_0x0056:
            if (r8 == 0) goto L_0x005b
            if (r17 != 0) goto L_0x005b
            goto L_0x006d
        L_0x005b:
            boolean r8 = r2.isFeatureSupported(r3, r10, r9)     // Catch:{ Exception -> 0x010f }
            boolean r17 = r2.isFeatureRequired(r3, r10, r9)     // Catch:{ Exception -> 0x010f }
            boolean r7 = r1.b
            if (r7 != 0) goto L_0x0069
            if (r17 != 0) goto L_0x006d
        L_0x0069:
            if (r7 == 0) goto L_0x0074
            if (r8 != 0) goto L_0x0074
        L_0x006d:
            r20 = r12
            r21 = r13
            r2 = r14
            goto L_0x0149
        L_0x0074:
            r1 = 29
            if (r6 < r1) goto L_0x007f
            boolean r1 = r0.isHardwareAccelerated()     // Catch:{ Exception -> 0x010f }
            r17 = 1
            goto L_0x0087
        L_0x007f:
            boolean r1 = e(r0)     // Catch:{ Exception -> 0x010f }
            r17 = 1
            r1 = r1 ^ 1
        L_0x0087:
            boolean r19 = e(r0)     // Catch:{ Exception -> 0x010f }
            r2 = 29
            if (r6 < r2) goto L_0x0094
            boolean r0 = r0.isVendor()     // Catch:{ Exception -> 0x010f }
            goto L_0x00b9
        L_0x0094:
            java.lang.String r0 = r0.getName()     // Catch:{ Exception -> 0x010f }
            java.lang.String r0 = com.google.common.base.Ascii.toLowerCase((java.lang.String) r0)     // Catch:{ Exception -> 0x010f }
            java.lang.String r2 = "omx.google."
            boolean r2 = r0.startsWith(r2)     // Catch:{ Exception -> 0x010f }
            if (r2 != 0) goto L_0x00b5
            java.lang.String r2 = "c2.android."
            boolean r2 = r0.startsWith(r2)     // Catch:{ Exception -> 0x010f }
            if (r2 != 0) goto L_0x00b5
            java.lang.String r2 = "c2.google."
            boolean r0 = r0.startsWith(r2)     // Catch:{ Exception -> 0x010f }
            if (r0 != 0) goto L_0x00b5
            goto L_0x00b7
        L_0x00b5:
            r17 = 0
        L_0x00b7:
            r0 = r17
        L_0x00b9:
            if (r13 == 0) goto L_0x00bd
            if (r7 == r8) goto L_0x00c1
        L_0x00bd:
            if (r13 != 0) goto L_0x00e1
            if (r7 != 0) goto L_0x00e1
        L_0x00c1:
            r2 = 0
            r17 = 0
            r6 = r11
            r7 = r15
            r8 = r10
            r18 = r10
            r10 = r1
            r1 = r11
            r11 = r19
            r20 = r12
            r12 = r0
            r21 = r13
            r13 = r2
            r2 = r14
            r14 = r17
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r0 = com.google.android.exoplayer2.mediacodec.MediaCodecInfo.newInstance(r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ Exception -> 0x00df }
            r5.add(r0)     // Catch:{ Exception -> 0x00df }
            goto L_0x0149
        L_0x00df:
            r0 = move-exception
            goto L_0x0118
        L_0x00e1:
            r18 = r10
            r20 = r12
            r21 = r13
            r2 = r14
            r14 = r11
            if (r21 != 0) goto L_0x0149
            if (r8 == 0) goto L_0x0149
            java.lang.String r6 = java.lang.String.valueOf(r14)     // Catch:{ Exception -> 0x010c }
            java.lang.String r7 = ".secure"
            java.lang.String r6 = r6.concat(r7)     // Catch:{ Exception -> 0x010c }
            r13 = 0
            r17 = 1
            r7 = r15
            r8 = r18
            r10 = r1
            r11 = r19
            r12 = r0
            r1 = r14
            r14 = r17
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r0 = com.google.android.exoplayer2.mediacodec.MediaCodecInfo.newInstance(r6, r7, r8, r9, r10, r11, r12, r13, r14)     // Catch:{ Exception -> 0x00df }
            r5.add(r0)     // Catch:{ Exception -> 0x00df }
            return r5
        L_0x010c:
            r0 = move-exception
            r1 = r14
            goto L_0x0118
        L_0x010f:
            r0 = move-exception
            r18 = r10
            r1 = r11
            r20 = r12
            r21 = r13
            r2 = r14
        L_0x0118:
            int r6 = com.google.android.exoplayer2.util.Util.a     // Catch:{ Exception -> 0x0188 }
            r7 = 23
            java.lang.String r8 = "MediaCodecUtil"
            if (r6 > r7) goto L_0x0154
            boolean r6 = r5.isEmpty()     // Catch:{ Exception -> 0x0188 }
            if (r6 != 0) goto L_0x0154
            java.lang.String r0 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x0188 }
            int r0 = r0.length()     // Catch:{ Exception -> 0x0188 }
            int r0 = r0 + 46
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0188 }
            r6.<init>(r0)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r0 = "Skipping codec "
            r6.append(r0)     // Catch:{ Exception -> 0x0188 }
            r6.append(r1)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r0 = " (failed to query capabilities)"
            r6.append(r0)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r0 = r6.toString()     // Catch:{ Exception -> 0x0188 }
            com.google.android.exoplayer2.util.Log.e(r8, r0)     // Catch:{ Exception -> 0x0188 }
        L_0x0149:
            int r12 = r20 + 1
            r1 = r22
            r14 = r2
            r13 = r21
            r2 = r23
            goto L_0x001a
        L_0x0154:
            java.lang.String r2 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x0188 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x0188 }
            int r2 = r2 + 25
            int r3 = r18.length()     // Catch:{ Exception -> 0x0188 }
            int r2 = r2 + r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0188 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r2 = "Failed to query codec "
            r3.append(r2)     // Catch:{ Exception -> 0x0188 }
            r3.append(r1)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r1 = " ("
            r3.append(r1)     // Catch:{ Exception -> 0x0188 }
            r1 = r18
            r3.append(r1)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r1 = ")"
            r3.append(r1)     // Catch:{ Exception -> 0x0188 }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x0188 }
            com.google.android.exoplayer2.util.Log.e(r8, r1)     // Catch:{ Exception -> 0x0188 }
            throw r0     // Catch:{ Exception -> 0x0188 }
        L_0x0187:
            return r5
        L_0x0188:
            r0 = move-exception
            com.google.android.exoplayer2.mediacodec.MediaCodecUtil$DecoderQueryException r1 = new com.google.android.exoplayer2.mediacodec.MediaCodecUtil$DecoderQueryException
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.c(com.google.android.exoplayer2.mediacodec.MediaCodecUtil$a, com.google.android.exoplayer2.mediacodec.MediaCodecUtil$b):java.util.ArrayList");
    }

    public static synchronized void clearDecoderInfoCache() {
        synchronized (MediaCodecUtil.class) {
            b.clear();
        }
    }

    public static boolean d(MediaCodecInfo mediaCodecInfo, String str, boolean z, String str2) {
        if (mediaCodecInfo.isEncoder() || (!z && str.endsWith(".secure"))) {
            return false;
        }
        int i = Util.a;
        if (i < 21 && ("CIPAACDecoder".equals(str) || "CIPMP3Decoder".equals(str) || "CIPVorbisDecoder".equals(str) || "CIPAMRNBDecoder".equals(str) || "AACDecoder".equals(str) || "MP3Decoder".equals(str))) {
            return false;
        }
        if (i < 18 && "OMX.MTK.AUDIO.DECODER.AAC".equals(str)) {
            String str3 = Util.b;
            if ("a70".equals(str3) || ("Xiaomi".equals(Util.c) && str3.startsWith("HM"))) {
                return false;
            }
        }
        if (i == 16 && "OMX.qcom.audio.decoder.mp3".equals(str)) {
            String str4 = Util.b;
            if ("dlxu".equals(str4) || "protou".equals(str4) || "ville".equals(str4) || "villeplus".equals(str4) || "villec2".equals(str4) || str4.startsWith("gee") || "C6602".equals(str4) || "C6603".equals(str4) || "C6606".equals(str4) || "C6616".equals(str4) || "L36h".equals(str4) || "SO-02E".equals(str4)) {
                return false;
            }
        }
        if (i == 16 && "OMX.qcom.audio.decoder.aac".equals(str)) {
            String str5 = Util.b;
            if ("C1504".equals(str5) || "C1505".equals(str5) || "C1604".equals(str5) || "C1605".equals(str5)) {
                return false;
            }
        }
        if (i < 24 && (("OMX.SEC.aac.dec".equals(str) || "OMX.Exynos.AAC.Decoder".equals(str)) && "samsung".equals(Util.c))) {
            String str6 = Util.b;
            if (str6.startsWith("zeroflte") || str6.startsWith("zerolte") || str6.startsWith("zenlte") || "SC-05G".equals(str6) || "marinelteatt".equals(str6) || "404SC".equals(str6) || "SC-04G".equals(str6) || "SCV31".equals(str6)) {
                return false;
            }
        }
        if (i <= 19 && "OMX.SEC.vp8.dec".equals(str) && "samsung".equals(Util.c)) {
            String str7 = Util.b;
            if (str7.startsWith("d2") || str7.startsWith("serrano") || str7.startsWith("jflte") || str7.startsWith("santos") || str7.startsWith("t0")) {
                return false;
            }
        }
        if (i <= 19 && Util.b.startsWith("jflte") && "OMX.qcom.video.decoder.vp8".equals(str)) {
            return false;
        }
        if (!"audio/eac3-joc".equals(str2) || !"OMX.MTK.AUDIO.DECODER.DSPAC3".equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean e(MediaCodecInfo mediaCodecInfo) {
        if (Util.a >= 29) {
            return mediaCodecInfo.isSoftwareOnly();
        }
        String lowerCase = Ascii.toLowerCase(mediaCodecInfo.getName());
        if (lowerCase.startsWith("arc.")) {
            return false;
        }
        if (lowerCase.startsWith("omx.google.") || lowerCase.startsWith("omx.ffmpeg.") || ((lowerCase.startsWith("omx.sec.") && lowerCase.contains(".sw.")) || lowerCase.equals("omx.qcom.video.decoder.hevcswvdec") || lowerCase.startsWith("c2.android.") || lowerCase.startsWith("c2.google.") || (!lowerCase.startsWith("omx.") && !lowerCase.startsWith("c2.")))) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:552:0x0905, code lost:
        r0 = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:556:0x090f, code lost:
        if (r1 != r0) goto L_0x0919;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:557:0x0911, code lost:
        defpackage.y2.t(30, "Unknown AV1 level: ", r2, "MediaCodecUtil");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:589:?, code lost:
        return new android.util.Pair<>(java.lang.Integer.valueOf(r15), java.lang.Integer.valueOf(r1));
     */
    /* JADX WARNING: Removed duplicated region for block: B:271:0x042b A[Catch:{ NumberFormatException -> 0x043b }] */
    /* JADX WARNING: Removed duplicated region for block: B:575:? A[RETURN, SYNTHETIC] */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> getCodecProfileAndLevel(com.google.android.exoplayer2.Format r15) {
        /*
            java.lang.String r0 = r15.m
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            java.lang.String r1 = "\\."
            java.lang.String[] r0 = r0.split(r1)
            java.lang.String r1 = "video/dolby-vision"
            java.lang.String r2 = r15.p
            boolean r1 = r1.equals(r2)
            r2 = 512(0x200, float:7.175E-43)
            r3 = 256(0x100, float:3.59E-43)
            r4 = 128(0x80, float:1.794E-43)
            java.lang.String r5 = "MediaCodecUtil"
            r6 = 3
            r7 = 1
            java.lang.String r8 = r15.m
            if (r1 == 0) goto L_0x0264
            int r15 = r0.length
            java.lang.String r1 = "Ignoring malformed Dolby Vision codec string: "
            if (r15 >= r6) goto L_0x0040
            java.lang.String r15 = java.lang.String.valueOf(r8)
            int r0 = r15.length()
            if (r0 == 0) goto L_0x0036
            java.lang.String r15 = r1.concat(r15)
            goto L_0x003b
        L_0x0036:
            java.lang.String r15 = new java.lang.String
            r15.<init>(r1)
        L_0x003b:
            com.google.android.exoplayer2.util.Log.w(r5, r15)
            goto L_0x025c
        L_0x0040:
            java.util.regex.Pattern r15 = a
            r6 = r0[r7]
            java.util.regex.Matcher r15 = r15.matcher(r6)
            boolean r6 = r15.matches()
            if (r6 != 0) goto L_0x0067
            java.lang.String r15 = java.lang.String.valueOf(r8)
            int r0 = r15.length()
            if (r0 == 0) goto L_0x005d
            java.lang.String r15 = r1.concat(r15)
            goto L_0x0062
        L_0x005d:
            java.lang.String r15 = new java.lang.String
            r15.<init>(r1)
        L_0x0062:
            com.google.android.exoplayer2.util.Log.w(r5, r15)
            goto L_0x025c
        L_0x0067:
            java.lang.String r15 = r15.group(r7)
            java.lang.String r1 = "09"
            java.lang.String r6 = "08"
            java.lang.String r7 = "07"
            java.lang.String r8 = "06"
            java.lang.String r9 = "05"
            java.lang.String r10 = "04"
            java.lang.String r11 = "03"
            java.lang.String r12 = "02"
            java.lang.String r13 = "01"
            if (r15 != 0) goto L_0x0081
            goto L_0x012c
        L_0x0081:
            int r14 = r15.hashCode()
            switch(r14) {
                case 1536: goto L_0x00df;
                case 1537: goto L_0x00d6;
                case 1538: goto L_0x00cd;
                case 1539: goto L_0x00c4;
                case 1540: goto L_0x00bb;
                case 1541: goto L_0x00b2;
                case 1542: goto L_0x00a9;
                case 1543: goto L_0x00a0;
                case 1544: goto L_0x0096;
                case 1545: goto L_0x008a;
                default: goto L_0x0088;
            }
        L_0x0088:
            goto L_0x00ea
        L_0x008a:
            boolean r14 = r15.equals(r1)
            if (r14 != 0) goto L_0x0092
            goto L_0x00ea
        L_0x0092:
            r14 = 9
            goto L_0x00eb
        L_0x0096:
            boolean r14 = r15.equals(r6)
            if (r14 != 0) goto L_0x009d
            goto L_0x00ea
        L_0x009d:
            r14 = 8
            goto L_0x00eb
        L_0x00a0:
            boolean r14 = r15.equals(r7)
            if (r14 != 0) goto L_0x00a7
            goto L_0x00ea
        L_0x00a7:
            r14 = 7
            goto L_0x00eb
        L_0x00a9:
            boolean r14 = r15.equals(r8)
            if (r14 != 0) goto L_0x00b0
            goto L_0x00ea
        L_0x00b0:
            r14 = 6
            goto L_0x00eb
        L_0x00b2:
            boolean r14 = r15.equals(r9)
            if (r14 != 0) goto L_0x00b9
            goto L_0x00ea
        L_0x00b9:
            r14 = 5
            goto L_0x00eb
        L_0x00bb:
            boolean r14 = r15.equals(r10)
            if (r14 != 0) goto L_0x00c2
            goto L_0x00ea
        L_0x00c2:
            r14 = 4
            goto L_0x00eb
        L_0x00c4:
            boolean r14 = r15.equals(r11)
            if (r14 != 0) goto L_0x00cb
            goto L_0x00ea
        L_0x00cb:
            r14 = 3
            goto L_0x00eb
        L_0x00cd:
            boolean r14 = r15.equals(r12)
            if (r14 != 0) goto L_0x00d4
            goto L_0x00ea
        L_0x00d4:
            r14 = 2
            goto L_0x00eb
        L_0x00d6:
            boolean r14 = r15.equals(r13)
            if (r14 != 0) goto L_0x00dd
            goto L_0x00ea
        L_0x00dd:
            r14 = 1
            goto L_0x00eb
        L_0x00df:
            java.lang.String r14 = "00"
            boolean r14 = r15.equals(r14)
            if (r14 != 0) goto L_0x00e8
            goto L_0x00ea
        L_0x00e8:
            r14 = 0
            goto L_0x00eb
        L_0x00ea:
            r14 = -1
        L_0x00eb:
            switch(r14) {
                case 0: goto L_0x0126;
                case 1: goto L_0x0120;
                case 2: goto L_0x011a;
                case 3: goto L_0x0113;
                case 4: goto L_0x010c;
                case 5: goto L_0x0105;
                case 6: goto L_0x00fe;
                case 7: goto L_0x00f9;
                case 8: goto L_0x00f4;
                case 9: goto L_0x00ef;
                default: goto L_0x00ee;
            }
        L_0x00ee:
            goto L_0x012c
        L_0x00ef:
            java.lang.Integer r14 = java.lang.Integer.valueOf(r2)
            goto L_0x012d
        L_0x00f4:
            java.lang.Integer r14 = java.lang.Integer.valueOf(r3)
            goto L_0x012d
        L_0x00f9:
            java.lang.Integer r14 = java.lang.Integer.valueOf(r4)
            goto L_0x012d
        L_0x00fe:
            r14 = 64
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            goto L_0x012d
        L_0x0105:
            r14 = 32
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            goto L_0x012d
        L_0x010c:
            r14 = 16
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            goto L_0x012d
        L_0x0113:
            r14 = 8
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            goto L_0x012d
        L_0x011a:
            r14 = 4
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            goto L_0x012d
        L_0x0120:
            r14 = 2
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            goto L_0x012d
        L_0x0126:
            r14 = 1
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            goto L_0x012d
        L_0x012c:
            r14 = 0
        L_0x012d:
            if (r14 != 0) goto L_0x014a
            java.lang.String r15 = java.lang.String.valueOf(r15)
            int r0 = r15.length()
            java.lang.String r1 = "Unknown Dolby Vision profile string: "
            if (r0 == 0) goto L_0x0140
            java.lang.String r15 = r1.concat(r15)
            goto L_0x0145
        L_0x0140:
            java.lang.String r15 = new java.lang.String
            r15.<init>(r1)
        L_0x0145:
            com.google.android.exoplayer2.util.Log.w(r5, r15)
            goto L_0x025c
        L_0x014a:
            r15 = 2
            r15 = r0[r15]
            if (r15 != 0) goto L_0x0151
            goto L_0x0240
        L_0x0151:
            int r0 = r15.hashCode()
            switch(r0) {
                case 1537: goto L_0x01b0;
                case 1538: goto L_0x01a7;
                case 1539: goto L_0x019e;
                case 1540: goto L_0x0195;
                case 1541: goto L_0x018a;
                case 1542: goto L_0x017f;
                case 1543: goto L_0x0174;
                case 1544: goto L_0x0169;
                case 1545: goto L_0x015d;
                default: goto L_0x0158;
            }
        L_0x0158:
            switch(r0) {
                case 1567: goto L_0x01dd;
                case 1568: goto L_0x01d1;
                case 1569: goto L_0x01c5;
                case 1570: goto L_0x01b9;
                default: goto L_0x015b;
            }
        L_0x015b:
            goto L_0x01e9
        L_0x015d:
            boolean r0 = r15.equals(r1)
            if (r0 != 0) goto L_0x0165
            goto L_0x01e9
        L_0x0165:
            r0 = 8
            goto L_0x01ea
        L_0x0169:
            boolean r0 = r15.equals(r6)
            if (r0 != 0) goto L_0x0171
            goto L_0x01e9
        L_0x0171:
            r0 = 7
            goto L_0x01ea
        L_0x0174:
            boolean r0 = r15.equals(r7)
            if (r0 != 0) goto L_0x017c
            goto L_0x01e9
        L_0x017c:
            r0 = 6
            goto L_0x01ea
        L_0x017f:
            boolean r0 = r15.equals(r8)
            if (r0 != 0) goto L_0x0187
            goto L_0x01e9
        L_0x0187:
            r0 = 5
            goto L_0x01ea
        L_0x018a:
            boolean r0 = r15.equals(r9)
            if (r0 != 0) goto L_0x0192
            goto L_0x01e9
        L_0x0192:
            r0 = 4
            goto L_0x01ea
        L_0x0195:
            boolean r0 = r15.equals(r10)
            if (r0 != 0) goto L_0x019c
            goto L_0x01e9
        L_0x019c:
            r0 = 3
            goto L_0x01ea
        L_0x019e:
            boolean r0 = r15.equals(r11)
            if (r0 != 0) goto L_0x01a5
            goto L_0x01e9
        L_0x01a5:
            r0 = 2
            goto L_0x01ea
        L_0x01a7:
            boolean r0 = r15.equals(r12)
            if (r0 != 0) goto L_0x01ae
            goto L_0x01e9
        L_0x01ae:
            r0 = 1
            goto L_0x01ea
        L_0x01b0:
            boolean r0 = r15.equals(r13)
            if (r0 != 0) goto L_0x01b7
            goto L_0x01e9
        L_0x01b7:
            r0 = 0
            goto L_0x01ea
        L_0x01b9:
            java.lang.String r0 = "13"
            boolean r0 = r15.equals(r0)
            if (r0 != 0) goto L_0x01c2
            goto L_0x01e9
        L_0x01c2:
            r0 = 12
            goto L_0x01ea
        L_0x01c5:
            java.lang.String r0 = "12"
            boolean r0 = r15.equals(r0)
            if (r0 != 0) goto L_0x01ce
            goto L_0x01e9
        L_0x01ce:
            r0 = 11
            goto L_0x01ea
        L_0x01d1:
            java.lang.String r0 = "11"
            boolean r0 = r15.equals(r0)
            if (r0 != 0) goto L_0x01da
            goto L_0x01e9
        L_0x01da:
            r0 = 10
            goto L_0x01ea
        L_0x01dd:
            java.lang.String r0 = "10"
            boolean r0 = r15.equals(r0)
            if (r0 != 0) goto L_0x01e6
            goto L_0x01e9
        L_0x01e6:
            r0 = 9
            goto L_0x01ea
        L_0x01e9:
            r0 = -1
        L_0x01ea:
            switch(r0) {
                case 0: goto L_0x023a;
                case 1: goto L_0x0234;
                case 2: goto L_0x022e;
                case 3: goto L_0x0227;
                case 4: goto L_0x0220;
                case 5: goto L_0x0219;
                case 6: goto L_0x0212;
                case 7: goto L_0x020d;
                case 8: goto L_0x0208;
                case 9: goto L_0x0203;
                case 10: goto L_0x01fc;
                case 11: goto L_0x01f5;
                case 12: goto L_0x01ee;
                default: goto L_0x01ed;
            }
        L_0x01ed:
            goto L_0x0240
        L_0x01ee:
            r0 = 4096(0x1000, float:5.74E-42)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x01f5:
            r0 = 2048(0x800, float:2.87E-42)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x01fc:
            r0 = 1024(0x400, float:1.435E-42)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x0203:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r2)
            goto L_0x0241
        L_0x0208:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r3)
            goto L_0x0241
        L_0x020d:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r4)
            goto L_0x0241
        L_0x0212:
            r0 = 64
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x0219:
            r0 = 32
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x0220:
            r0 = 16
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x0227:
            r0 = 8
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x022e:
            r0 = 4
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x0234:
            r0 = 2
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x023a:
            r0 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            goto L_0x0241
        L_0x0240:
            r0 = 0
        L_0x0241:
            if (r0 != 0) goto L_0x025e
            java.lang.String r15 = java.lang.String.valueOf(r15)
            int r0 = r15.length()
            java.lang.String r1 = "Unknown Dolby Vision level string: "
            if (r0 == 0) goto L_0x0254
            java.lang.String r15 = r1.concat(r15)
            goto L_0x0259
        L_0x0254:
            java.lang.String r15 = new java.lang.String
            r15.<init>(r1)
        L_0x0259:
            com.google.android.exoplayer2.util.Log.w(r5, r15)
        L_0x025c:
            r15 = 0
            goto L_0x0263
        L_0x025e:
            android.util.Pair r15 = new android.util.Pair
            r15.<init>(r14, r0)
        L_0x0263:
            return r15
        L_0x0264:
            r1 = 0
            r1 = r0[r1]
            r1.getClass()
            int r6 = r1.hashCode()
            switch(r6) {
                case 3004662: goto L_0x02b4;
                case 3006243: goto L_0x02a9;
                case 3006244: goto L_0x029e;
                case 3199032: goto L_0x0293;
                case 3214780: goto L_0x0288;
                case 3356560: goto L_0x027d;
                case 3624515: goto L_0x0272;
                default: goto L_0x0271;
            }
        L_0x0271:
            goto L_0x02bf
        L_0x0272:
            java.lang.String r6 = "vp09"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x027b
            goto L_0x02bf
        L_0x027b:
            r1 = 6
            goto L_0x02c0
        L_0x027d:
            java.lang.String r6 = "mp4a"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x0286
            goto L_0x02bf
        L_0x0286:
            r1 = 5
            goto L_0x02c0
        L_0x0288:
            java.lang.String r6 = "hvc1"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x0291
            goto L_0x02bf
        L_0x0291:
            r1 = 4
            goto L_0x02c0
        L_0x0293:
            java.lang.String r6 = "hev1"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x029c
            goto L_0x02bf
        L_0x029c:
            r1 = 3
            goto L_0x02c0
        L_0x029e:
            java.lang.String r6 = "avc2"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x02a7
            goto L_0x02bf
        L_0x02a7:
            r1 = 2
            goto L_0x02c0
        L_0x02a9:
            java.lang.String r6 = "avc1"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x02b2
            goto L_0x02bf
        L_0x02b2:
            r1 = 1
            goto L_0x02c0
        L_0x02b4:
            java.lang.String r6 = "av01"
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x02bd
            goto L_0x02bf
        L_0x02bd:
            r1 = 0
            goto L_0x02c0
        L_0x02bf:
            r1 = -1
        L_0x02c0:
            switch(r1) {
                case 0: goto L_0x0849;
                case 1: goto L_0x0718;
                case 2: goto L_0x0718;
                case 3: goto L_0x0454;
                case 4: goto L_0x0454;
                case 5: goto L_0x039f;
                case 6: goto L_0x02c5;
                default: goto L_0x02c3;
            }
        L_0x02c3:
            r15 = 0
            return r15
        L_0x02c5:
            r15 = 0
            int r1 = r0.length
            java.lang.String r2 = "Ignoring malformed VP9 codec string: "
            r3 = 3
            if (r1 >= r3) goto L_0x02e5
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x02db
            java.lang.String r0 = r2.concat(r0)
            goto L_0x02e0
        L_0x02db:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x02e0:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
            goto L_0x039e
        L_0x02e5:
            r1 = 1
            r3 = r0[r1]     // Catch:{ NumberFormatException -> 0x0386 }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ NumberFormatException -> 0x0386 }
            r4 = 2
            r0 = r0[r4]     // Catch:{ NumberFormatException -> 0x0386 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0386 }
            if (r3 == 0) goto L_0x0305
            if (r3 == r1) goto L_0x0303
            if (r3 == r4) goto L_0x0301
            r1 = 3
            if (r3 == r1) goto L_0x02fe
            r1 = -1
            goto L_0x0306
        L_0x02fe:
            r1 = 8
            goto L_0x0306
        L_0x0301:
            r1 = 4
            goto L_0x0306
        L_0x0303:
            r1 = 2
            goto L_0x0306
        L_0x0305:
            r1 = 1
        L_0x0306:
            r2 = -1
            if (r1 != r2) goto L_0x0312
            r0 = 32
            java.lang.String r1 = "Unknown VP9 profile: "
            defpackage.y2.t(r0, r1, r3, r5)
            goto L_0x039e
        L_0x0312:
            r2 = 30
            r3 = 10
            if (r0 == r3) goto L_0x036e
            r3 = 11
            if (r0 == r3) goto L_0x036b
            r3 = 20
            if (r0 == r3) goto L_0x0368
            r3 = 21
            if (r0 == r3) goto L_0x0364
            if (r0 == r2) goto L_0x0360
            r3 = 31
            if (r0 == r3) goto L_0x035c
            r3 = 40
            if (r0 == r3) goto L_0x0358
            r3 = 41
            if (r0 == r3) goto L_0x0354
            r3 = 50
            if (r0 == r3) goto L_0x0350
            r3 = 51
            if (r0 == r3) goto L_0x034c
            switch(r0) {
                case 60: goto L_0x0348;
                case 61: goto L_0x0344;
                case 62: goto L_0x0340;
                default: goto L_0x033d;
            }
        L_0x033d:
            r4 = -1
            r3 = -1
            goto L_0x0370
        L_0x0340:
            r3 = 8192(0x2000, float:1.14794E-41)
            r4 = -1
            goto L_0x0370
        L_0x0344:
            r4 = -1
            r3 = 4096(0x1000, float:5.74E-42)
            goto L_0x0370
        L_0x0348:
            r4 = -1
            r3 = 2048(0x800, float:2.87E-42)
            goto L_0x0370
        L_0x034c:
            r4 = -1
            r3 = 512(0x200, float:7.175E-43)
            goto L_0x0370
        L_0x0350:
            r4 = -1
            r3 = 256(0x100, float:3.59E-43)
            goto L_0x0370
        L_0x0354:
            r4 = -1
            r3 = 128(0x80, float:1.794E-43)
            goto L_0x0370
        L_0x0358:
            r4 = -1
            r3 = 64
            goto L_0x0370
        L_0x035c:
            r4 = -1
            r3 = 32
            goto L_0x0370
        L_0x0360:
            r4 = -1
            r3 = 16
            goto L_0x0370
        L_0x0364:
            r4 = -1
            r3 = 8
            goto L_0x0370
        L_0x0368:
            r4 = -1
            r3 = 4
            goto L_0x0370
        L_0x036b:
            r4 = -1
            r3 = 2
            goto L_0x0370
        L_0x036e:
            r4 = -1
            r3 = 1
        L_0x0370:
            if (r3 != r4) goto L_0x0378
            java.lang.String r1 = "Unknown VP9 level: "
            defpackage.y2.t(r2, r1, r0, r5)
            goto L_0x039e
        L_0x0378:
            android.util.Pair r15 = new android.util.Pair
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            r15.<init>(r0, r1)
            goto L_0x039e
        L_0x0386:
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x0396
            java.lang.String r0 = r2.concat(r0)
            goto L_0x039b
        L_0x0396:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x039b:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
        L_0x039e:
            return r15
        L_0x039f:
            r15 = 0
            int r1 = r0.length
            java.lang.String r2 = "Ignoring malformed MP4A codec string: "
            r3 = 3
            if (r1 == r3) goto L_0x03bf
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x03b5
            java.lang.String r0 = r2.concat(r0)
            goto L_0x03ba
        L_0x03b5:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x03ba:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
            goto L_0x0453
        L_0x03bf:
            r1 = 1
            r1 = r0[r1]     // Catch:{ NumberFormatException -> 0x043b }
            r3 = 16
            int r1 = java.lang.Integer.parseInt(r1, r3)     // Catch:{ NumberFormatException -> 0x043b }
            java.lang.String r1 = com.google.android.exoplayer2.util.MimeTypes.getMimeTypeFromMp4ObjectType(r1)     // Catch:{ NumberFormatException -> 0x043b }
            java.lang.String r3 = "audio/mp4a-latm"
            boolean r1 = r3.equals(r1)     // Catch:{ NumberFormatException -> 0x043b }
            if (r1 == 0) goto L_0x0453
            r1 = 2
            r0 = r0[r1]     // Catch:{ NumberFormatException -> 0x043b }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x043b }
            r1 = 17
            if (r0 == r1) goto L_0x0424
            r1 = 20
            if (r0 == r1) goto L_0x041f
            r1 = 23
            if (r0 == r1) goto L_0x041a
            r1 = 29
            if (r0 == r1) goto L_0x0415
            r1 = 39
            if (r0 == r1) goto L_0x0410
            r1 = 42
            if (r0 == r1) goto L_0x040b
            switch(r0) {
                case 1: goto L_0x0408;
                case 2: goto L_0x0405;
                case 3: goto L_0x0402;
                case 4: goto L_0x03ff;
                case 5: goto L_0x03fc;
                case 6: goto L_0x03f9;
                default: goto L_0x03f6;
            }     // Catch:{ NumberFormatException -> 0x043b }
        L_0x03f6:
            r0 = -1
            r1 = -1
            goto L_0x0429
        L_0x03f9:
            r0 = -1
            r1 = 6
            goto L_0x0429
        L_0x03fc:
            r0 = -1
            r1 = 5
            goto L_0x0429
        L_0x03ff:
            r0 = -1
            r1 = 4
            goto L_0x0429
        L_0x0402:
            r0 = -1
            r1 = 3
            goto L_0x0429
        L_0x0405:
            r0 = -1
            r1 = 2
            goto L_0x0429
        L_0x0408:
            r0 = -1
            r1 = 1
            goto L_0x0429
        L_0x040b:
            r0 = 42
            r1 = 42
            goto L_0x0428
        L_0x0410:
            r0 = 39
            r1 = 39
            goto L_0x0428
        L_0x0415:
            r0 = 29
            r1 = 29
            goto L_0x0428
        L_0x041a:
            r0 = 23
            r1 = 23
            goto L_0x0428
        L_0x041f:
            r0 = 20
            r1 = 20
            goto L_0x0428
        L_0x0424:
            r0 = 17
            r1 = 17
        L_0x0428:
            r0 = -1
        L_0x0429:
            if (r1 == r0) goto L_0x0453
            android.util.Pair r0 = new android.util.Pair     // Catch:{ NumberFormatException -> 0x043b }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ NumberFormatException -> 0x043b }
            r3 = 0
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ NumberFormatException -> 0x043b }
            r0.<init>(r1, r3)     // Catch:{ NumberFormatException -> 0x043b }
            r15 = r0
            goto L_0x0453
        L_0x043b:
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x044b
            java.lang.String r0 = r2.concat(r0)
            goto L_0x0450
        L_0x044b:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x0450:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
        L_0x0453:
            return r15
        L_0x0454:
            r15 = 0
            int r1 = r0.length
            java.lang.String r6 = "Ignoring malformed HEVC codec string: "
            r7 = 4
            if (r1 >= r7) goto L_0x0474
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x046a
            java.lang.String r0 = r6.concat(r0)
            goto L_0x046f
        L_0x046a:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r6)
        L_0x046f:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
            goto L_0x0717
        L_0x0474:
            java.util.regex.Pattern r1 = a
            r7 = 1
            r7 = r0[r7]
            java.util.regex.Matcher r1 = r1.matcher(r7)
            boolean r7 = r1.matches()
            if (r7 != 0) goto L_0x049c
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x0492
            java.lang.String r0 = r6.concat(r0)
            goto L_0x0497
        L_0x0492:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r6)
        L_0x0497:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
            goto L_0x0717
        L_0x049c:
            r6 = 1
            java.lang.String r1 = r1.group(r6)
            java.lang.String r6 = "1"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x04ab
            r1 = 1
            goto L_0x04b4
        L_0x04ab:
            java.lang.String r6 = "2"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x06fe
            r1 = 2
        L_0x04b4:
            r6 = 3
            r0 = r0[r6]
            if (r0 != 0) goto L_0x04bb
            goto L_0x06d7
        L_0x04bb:
            int r6 = r0.hashCode()
            switch(r6) {
                case 70821: goto L_0x060d;
                case 70914: goto L_0x0602;
                case 70917: goto L_0x05f7;
                case 71007: goto L_0x05ec;
                case 71010: goto L_0x05e1;
                case 74665: goto L_0x05d6;
                case 74758: goto L_0x05cb;
                case 74761: goto L_0x05c0;
                case 74851: goto L_0x05b2;
                case 74854: goto L_0x05a4;
                case 2193639: goto L_0x0596;
                case 2193642: goto L_0x0588;
                case 2193732: goto L_0x057a;
                case 2193735: goto L_0x056c;
                case 2193738: goto L_0x055e;
                case 2193825: goto L_0x0550;
                case 2193828: goto L_0x0542;
                case 2193831: goto L_0x0534;
                case 2312803: goto L_0x0526;
                case 2312806: goto L_0x0518;
                case 2312896: goto L_0x050a;
                case 2312899: goto L_0x04fc;
                case 2312902: goto L_0x04ee;
                case 2312989: goto L_0x04e0;
                case 2312992: goto L_0x04d2;
                case 2312995: goto L_0x04c4;
                default: goto L_0x04c2;
            }
        L_0x04c2:
            goto L_0x0618
        L_0x04c4:
            java.lang.String r6 = "L186"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x04ce
            goto L_0x0618
        L_0x04ce:
            r6 = 25
            goto L_0x0619
        L_0x04d2:
            java.lang.String r6 = "L183"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x04dc
            goto L_0x0618
        L_0x04dc:
            r6 = 24
            goto L_0x0619
        L_0x04e0:
            java.lang.String r6 = "L180"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x04ea
            goto L_0x0618
        L_0x04ea:
            r6 = 23
            goto L_0x0619
        L_0x04ee:
            java.lang.String r6 = "L156"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x04f8
            goto L_0x0618
        L_0x04f8:
            r6 = 22
            goto L_0x0619
        L_0x04fc:
            java.lang.String r6 = "L153"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0506
            goto L_0x0618
        L_0x0506:
            r6 = 21
            goto L_0x0619
        L_0x050a:
            java.lang.String r6 = "L150"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0514
            goto L_0x0618
        L_0x0514:
            r6 = 20
            goto L_0x0619
        L_0x0518:
            java.lang.String r6 = "L123"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0522
            goto L_0x0618
        L_0x0522:
            r6 = 19
            goto L_0x0619
        L_0x0526:
            java.lang.String r6 = "L120"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0530
            goto L_0x0618
        L_0x0530:
            r6 = 18
            goto L_0x0619
        L_0x0534:
            java.lang.String r6 = "H186"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x053e
            goto L_0x0618
        L_0x053e:
            r6 = 17
            goto L_0x0619
        L_0x0542:
            java.lang.String r6 = "H183"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x054c
            goto L_0x0618
        L_0x054c:
            r6 = 16
            goto L_0x0619
        L_0x0550:
            java.lang.String r6 = "H180"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x055a
            goto L_0x0618
        L_0x055a:
            r6 = 15
            goto L_0x0619
        L_0x055e:
            java.lang.String r6 = "H156"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0568
            goto L_0x0618
        L_0x0568:
            r6 = 14
            goto L_0x0619
        L_0x056c:
            java.lang.String r6 = "H153"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0576
            goto L_0x0618
        L_0x0576:
            r6 = 13
            goto L_0x0619
        L_0x057a:
            java.lang.String r6 = "H150"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0584
            goto L_0x0618
        L_0x0584:
            r6 = 12
            goto L_0x0619
        L_0x0588:
            java.lang.String r6 = "H123"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0592
            goto L_0x0618
        L_0x0592:
            r6 = 11
            goto L_0x0619
        L_0x0596:
            java.lang.String r6 = "H120"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05a0
            goto L_0x0618
        L_0x05a0:
            r6 = 10
            goto L_0x0619
        L_0x05a4:
            java.lang.String r6 = "L93"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05ae
            goto L_0x0618
        L_0x05ae:
            r6 = 9
            goto L_0x0619
        L_0x05b2:
            java.lang.String r6 = "L90"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05bc
            goto L_0x0618
        L_0x05bc:
            r6 = 8
            goto L_0x0619
        L_0x05c0:
            java.lang.String r6 = "L63"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05c9
            goto L_0x0618
        L_0x05c9:
            r6 = 7
            goto L_0x0619
        L_0x05cb:
            java.lang.String r6 = "L60"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05d4
            goto L_0x0618
        L_0x05d4:
            r6 = 6
            goto L_0x0619
        L_0x05d6:
            java.lang.String r6 = "L30"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05df
            goto L_0x0618
        L_0x05df:
            r6 = 5
            goto L_0x0619
        L_0x05e1:
            java.lang.String r6 = "H93"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05ea
            goto L_0x0618
        L_0x05ea:
            r6 = 4
            goto L_0x0619
        L_0x05ec:
            java.lang.String r6 = "H90"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x05f5
            goto L_0x0618
        L_0x05f5:
            r6 = 3
            goto L_0x0619
        L_0x05f7:
            java.lang.String r6 = "H63"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0600
            goto L_0x0618
        L_0x0600:
            r6 = 2
            goto L_0x0619
        L_0x0602:
            java.lang.String r6 = "H60"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x060b
            goto L_0x0618
        L_0x060b:
            r6 = 1
            goto L_0x0619
        L_0x060d:
            java.lang.String r6 = "H30"
            boolean r6 = r0.equals(r6)
            if (r6 != 0) goto L_0x0616
            goto L_0x0618
        L_0x0616:
            r6 = 0
            goto L_0x0619
        L_0x0618:
            r6 = -1
        L_0x0619:
            switch(r6) {
                case 0: goto L_0x06d1;
                case 1: goto L_0x06ca;
                case 2: goto L_0x06c3;
                case 3: goto L_0x06be;
                case 4: goto L_0x06b9;
                case 5: goto L_0x06b3;
                case 6: goto L_0x06ad;
                case 7: goto L_0x06a6;
                case 8: goto L_0x069f;
                case 9: goto L_0x069a;
                case 10: goto L_0x0693;
                case 11: goto L_0x068c;
                case 12: goto L_0x0684;
                case 13: goto L_0x067d;
                case 14: goto L_0x0676;
                case 15: goto L_0x066e;
                case 16: goto L_0x0666;
                case 17: goto L_0x065e;
                case 18: goto L_0x0656;
                case 19: goto L_0x064e;
                case 20: goto L_0x0646;
                case 21: goto L_0x063e;
                case 22: goto L_0x0636;
                case 23: goto L_0x062e;
                case 24: goto L_0x0626;
                case 25: goto L_0x061e;
                default: goto L_0x061c;
            }
        L_0x061c:
            goto L_0x06d7
        L_0x061e:
            r2 = 16777216(0x1000000, float:2.3509887E-38)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0626:
            r2 = 4194304(0x400000, float:5.877472E-39)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x062e:
            r2 = 1048576(0x100000, float:1.469368E-39)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0636:
            r2 = 262144(0x40000, float:3.67342E-40)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x063e:
            r2 = 65536(0x10000, float:9.18355E-41)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0646:
            r2 = 16384(0x4000, float:2.2959E-41)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x064e:
            r2 = 4096(0x1000, float:5.74E-42)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0656:
            r2 = 1024(0x400, float:1.435E-42)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x065e:
            r2 = 33554432(0x2000000, float:9.403955E-38)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0666:
            r2 = 8388608(0x800000, float:1.17549435E-38)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x066e:
            r2 = 2097152(0x200000, float:2.938736E-39)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0676:
            r2 = 524288(0x80000, float:7.34684E-40)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x067d:
            r2 = 131072(0x20000, float:1.83671E-40)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0684:
            r2 = 32768(0x8000, float:4.5918E-41)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x068c:
            r2 = 8192(0x2000, float:1.14794E-41)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x0693:
            r2 = 2048(0x800, float:2.87E-42)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x069a:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r3)
            goto L_0x06d8
        L_0x069f:
            r2 = 64
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06a6:
            r2 = 16
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06ad:
            r2 = 4
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06b3:
            r2 = 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06b9:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06be:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r4)
            goto L_0x06d8
        L_0x06c3:
            r2 = 32
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06ca:
            r2 = 8
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06d1:
            r2 = 2
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            goto L_0x06d8
        L_0x06d7:
            r2 = r15
        L_0x06d8:
            if (r2 != 0) goto L_0x06f4
            java.lang.String r0 = java.lang.String.valueOf(r0)
            int r1 = r0.length()
            java.lang.String r2 = "Unknown HEVC level string: "
            if (r1 == 0) goto L_0x06eb
            java.lang.String r0 = r2.concat(r0)
            goto L_0x06f0
        L_0x06eb:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x06f0:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
            goto L_0x0717
        L_0x06f4:
            android.util.Pair r15 = new android.util.Pair
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
            r15.<init>(r0, r2)
            goto L_0x0717
        L_0x06fe:
            java.lang.String r0 = java.lang.String.valueOf(r1)
            int r1 = r0.length()
            java.lang.String r2 = "Unknown HEVC profile string: "
            if (r1 == 0) goto L_0x070f
            java.lang.String r0 = r2.concat(r0)
            goto L_0x0714
        L_0x070f:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x0714:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
        L_0x0717:
            return r15
        L_0x0718:
            r15 = 0
            int r1 = r0.length
            java.lang.String r2 = "Ignoring malformed AVC codec string: "
            r3 = 2
            if (r1 >= r3) goto L_0x0738
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x072e
            java.lang.String r0 = r2.concat(r0)
            goto L_0x0733
        L_0x072e:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x0733:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
            goto L_0x0848
        L_0x0738:
            r1 = 1
            r3 = r0[r1]     // Catch:{ NumberFormatException -> 0x0830 }
            int r3 = r3.length()     // Catch:{ NumberFormatException -> 0x0830 }
            r4 = 6
            if (r3 != r4) goto L_0x075c
            r3 = r0[r1]     // Catch:{ NumberFormatException -> 0x0830 }
            r4 = 0
            r6 = 2
            java.lang.String r3 = r3.substring(r4, r6)     // Catch:{ NumberFormatException -> 0x0830 }
            r4 = 16
            int r3 = java.lang.Integer.parseInt(r3, r4)     // Catch:{ NumberFormatException -> 0x0830 }
            r0 = r0[r1]     // Catch:{ NumberFormatException -> 0x0830 }
            r1 = 4
            java.lang.String r0 = r0.substring(r1)     // Catch:{ NumberFormatException -> 0x0830 }
            int r0 = java.lang.Integer.parseInt(r0, r4)     // Catch:{ NumberFormatException -> 0x0830 }
            goto L_0x076e
        L_0x075c:
            int r1 = r0.length     // Catch:{ NumberFormatException -> 0x0830 }
            r3 = 3
            if (r1 < r3) goto L_0x0818
            r1 = 1
            r1 = r0[r1]     // Catch:{ NumberFormatException -> 0x0830 }
            int r3 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x0830 }
            r1 = 2
            r0 = r0[r1]     // Catch:{ NumberFormatException -> 0x0830 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0830 }
        L_0x076e:
            r1 = 66
            if (r3 == r1) goto L_0x079c
            r1 = 77
            if (r3 == r1) goto L_0x079a
            r1 = 88
            if (r3 == r1) goto L_0x0798
            r1 = 100
            if (r3 == r1) goto L_0x0795
            r1 = 110(0x6e, float:1.54E-43)
            if (r3 == r1) goto L_0x0792
            r1 = 122(0x7a, float:1.71E-43)
            if (r3 == r1) goto L_0x078f
            r1 = 244(0xf4, float:3.42E-43)
            if (r3 == r1) goto L_0x078c
            r1 = -1
            goto L_0x079d
        L_0x078c:
            r1 = 64
            goto L_0x079d
        L_0x078f:
            r1 = 32
            goto L_0x079d
        L_0x0792:
            r1 = 16
            goto L_0x079d
        L_0x0795:
            r1 = 8
            goto L_0x079d
        L_0x0798:
            r1 = 4
            goto L_0x079d
        L_0x079a:
            r1 = 2
            goto L_0x079d
        L_0x079c:
            r1 = 1
        L_0x079d:
            r2 = -1
            if (r1 != r2) goto L_0x07a9
            r0 = 32
            java.lang.String r1 = "Unknown AVC profile: "
            defpackage.y2.t(r0, r1, r3, r5)
            goto L_0x0848
        L_0x07a9:
            switch(r0) {
                case 10: goto L_0x07c6;
                case 11: goto L_0x07c3;
                case 12: goto L_0x07bf;
                case 13: goto L_0x07bb;
                default: goto L_0x07ac;
            }
        L_0x07ac:
            switch(r0) {
                case 20: goto L_0x07d1;
                case 21: goto L_0x07cd;
                case 22: goto L_0x07c9;
                default: goto L_0x07af;
            }
        L_0x07af:
            switch(r0) {
                case 30: goto L_0x07de;
                case 31: goto L_0x07da;
                case 32: goto L_0x07d5;
                default: goto L_0x07b2;
            }
        L_0x07b2:
            switch(r0) {
                case 40: goto L_0x07eb;
                case 41: goto L_0x07e7;
                case 42: goto L_0x07e2;
                default: goto L_0x07b5;
            }
        L_0x07b5:
            switch(r0) {
                case 50: goto L_0x07fb;
                case 51: goto L_0x07f4;
                case 52: goto L_0x07ef;
                default: goto L_0x07b8;
            }
        L_0x07b8:
            r2 = -1
            r3 = -1
            goto L_0x0800
        L_0x07bb:
            r2 = -1
            r3 = 16
            goto L_0x0800
        L_0x07bf:
            r2 = -1
            r3 = 8
            goto L_0x0800
        L_0x07c3:
            r2 = -1
            r3 = 4
            goto L_0x0800
        L_0x07c6:
            r2 = -1
            r3 = 1
            goto L_0x0800
        L_0x07c9:
            r2 = -1
            r3 = 128(0x80, float:1.794E-43)
            goto L_0x0800
        L_0x07cd:
            r2 = -1
            r3 = 64
            goto L_0x0800
        L_0x07d1:
            r2 = -1
            r3 = 32
            goto L_0x0800
        L_0x07d5:
            r2 = 1024(0x400, float:1.435E-42)
            r3 = 1024(0x400, float:1.435E-42)
            goto L_0x07ff
        L_0x07da:
            r2 = -1
            r3 = 512(0x200, float:7.175E-43)
            goto L_0x0800
        L_0x07de:
            r2 = -1
            r3 = 256(0x100, float:3.59E-43)
            goto L_0x0800
        L_0x07e2:
            r2 = 8192(0x2000, float:1.14794E-41)
            r3 = 8192(0x2000, float:1.14794E-41)
            goto L_0x07ff
        L_0x07e7:
            r2 = -1
            r3 = 4096(0x1000, float:5.74E-42)
            goto L_0x0800
        L_0x07eb:
            r2 = -1
            r3 = 2048(0x800, float:2.87E-42)
            goto L_0x0800
        L_0x07ef:
            r2 = 65536(0x10000, float:9.18355E-41)
            r3 = 65536(0x10000, float:9.18355E-41)
            goto L_0x07ff
        L_0x07f4:
            r2 = 32768(0x8000, float:4.5918E-41)
            r3 = 32768(0x8000, float:4.5918E-41)
            goto L_0x07ff
        L_0x07fb:
            r2 = 16384(0x4000, float:2.2959E-41)
            r3 = 16384(0x4000, float:2.2959E-41)
        L_0x07ff:
            r2 = -1
        L_0x0800:
            if (r3 != r2) goto L_0x080a
            r1 = 30
            java.lang.String r2 = "Unknown AVC level: "
            defpackage.y2.t(r1, r2, r0, r5)
            goto L_0x0848
        L_0x080a:
            android.util.Pair r15 = new android.util.Pair
            java.lang.Integer r0 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r3)
            r15.<init>(r0, r1)
            goto L_0x0848
        L_0x0818:
            java.lang.String r0 = java.lang.String.valueOf(r8)     // Catch:{ NumberFormatException -> 0x0830 }
            int r1 = r0.length()     // Catch:{ NumberFormatException -> 0x0830 }
            if (r1 == 0) goto L_0x0827
            java.lang.String r0 = r2.concat(r0)     // Catch:{ NumberFormatException -> 0x0830 }
            goto L_0x082c
        L_0x0827:
            java.lang.String r0 = new java.lang.String     // Catch:{ NumberFormatException -> 0x0830 }
            r0.<init>(r2)     // Catch:{ NumberFormatException -> 0x0830 }
        L_0x082c:
            com.google.android.exoplayer2.util.Log.w(r5, r0)     // Catch:{ NumberFormatException -> 0x0830 }
            goto L_0x0848
        L_0x0830:
            java.lang.String r0 = java.lang.String.valueOf(r8)
            int r1 = r0.length()
            if (r1 == 0) goto L_0x0840
            java.lang.String r0 = r2.concat(r0)
            goto L_0x0845
        L_0x0840:
            java.lang.String r0 = new java.lang.String
            r0.<init>(r2)
        L_0x0845:
            com.google.android.exoplayer2.util.Log.w(r5, r0)
        L_0x0848:
            return r15
        L_0x0849:
            r1 = 0
            int r2 = r0.length
            java.lang.String r3 = "Ignoring malformed AV1 codec string: "
            r4 = 4
            if (r2 >= r4) goto L_0x0869
            java.lang.String r15 = java.lang.String.valueOf(r8)
            int r0 = r15.length()
            if (r0 == 0) goto L_0x085f
            java.lang.String r15 = r3.concat(r15)
            goto L_0x0864
        L_0x085f:
            java.lang.String r15 = new java.lang.String
            r15.<init>(r3)
        L_0x0864:
            com.google.android.exoplayer2.util.Log.w(r5, r15)
            goto L_0x0941
        L_0x0869:
            r1 = 1
            r1 = r0[r1]     // Catch:{ NumberFormatException -> 0x0928 }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x0928 }
            r2 = 2
            r4 = r0[r2]     // Catch:{ NumberFormatException -> 0x0928 }
            r6 = 0
            java.lang.String r2 = r4.substring(r6, r2)     // Catch:{ NumberFormatException -> 0x0928 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x0928 }
            r4 = 3
            r0 = r0[r4]     // Catch:{ NumberFormatException -> 0x0928 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ NumberFormatException -> 0x0928 }
            if (r1 == 0) goto L_0x088e
            r15 = 32
            java.lang.String r0 = "Unknown AV1 profile: "
            defpackage.y2.t(r15, r0, r1, r5)
            goto L_0x0940
        L_0x088e:
            r1 = 8
            if (r0 == r1) goto L_0x08a1
            r1 = 10
            if (r0 == r1) goto L_0x089f
            r15 = 34
            java.lang.String r1 = "Unknown AV1 bit depth: "
            defpackage.y2.t(r15, r1, r0, r5)
            goto L_0x0940
        L_0x089f:
            r1 = 8
        L_0x08a1:
            if (r0 != r1) goto L_0x08a5
            r15 = 1
            goto L_0x08b9
        L_0x08a5:
            com.google.android.exoplayer2.video.ColorInfo r15 = r15.ab
            if (r15 == 0) goto L_0x08b8
            byte[] r0 = r15.h
            if (r0 != 0) goto L_0x08b5
            int r15 = r15.g
            r0 = 7
            if (r15 == r0) goto L_0x08b5
            r0 = 6
            if (r15 != r0) goto L_0x08b8
        L_0x08b5:
            r15 = 4096(0x1000, float:5.74E-42)
            goto L_0x08b9
        L_0x08b8:
            r15 = 2
        L_0x08b9:
            switch(r2) {
                case 0: goto L_0x090d;
                case 1: goto L_0x090a;
                case 2: goto L_0x0907;
                case 3: goto L_0x0905;
                case 4: goto L_0x0901;
                case 5: goto L_0x08fd;
                case 6: goto L_0x08f9;
                case 7: goto L_0x08f5;
                case 8: goto L_0x08f1;
                case 9: goto L_0x08ed;
                case 10: goto L_0x08ea;
                case 11: goto L_0x08e6;
                case 12: goto L_0x08e2;
                case 13: goto L_0x08df;
                case 14: goto L_0x08dc;
                case 15: goto L_0x08d8;
                case 16: goto L_0x08d5;
                case 17: goto L_0x08d2;
                case 18: goto L_0x08cf;
                case 19: goto L_0x08cc;
                case 20: goto L_0x08c9;
                case 21: goto L_0x08c6;
                case 22: goto L_0x08c3;
                case 23: goto L_0x08c0;
                default: goto L_0x08bc;
            }
        L_0x08bc:
            r0 = -1
            r1 = -1
            goto L_0x090f
        L_0x08c0:
            r1 = 8388608(0x800000, float:1.17549435E-38)
            goto L_0x0905
        L_0x08c3:
            r1 = 4194304(0x400000, float:5.877472E-39)
            goto L_0x0905
        L_0x08c6:
            r1 = 2097152(0x200000, float:2.938736E-39)
            goto L_0x0905
        L_0x08c9:
            r1 = 1048576(0x100000, float:1.469368E-39)
            goto L_0x0905
        L_0x08cc:
            r1 = 524288(0x80000, float:7.34684E-40)
            goto L_0x0905
        L_0x08cf:
            r1 = 262144(0x40000, float:3.67342E-40)
            goto L_0x0905
        L_0x08d2:
            r1 = 131072(0x20000, float:1.83671E-40)
            goto L_0x0905
        L_0x08d5:
            r1 = 65536(0x10000, float:9.18355E-41)
            goto L_0x0905
        L_0x08d8:
            r1 = 32768(0x8000, float:4.5918E-41)
            goto L_0x0905
        L_0x08dc:
            r1 = 16384(0x4000, float:2.2959E-41)
            goto L_0x0905
        L_0x08df:
            r1 = 8192(0x2000, float:1.14794E-41)
            goto L_0x0905
        L_0x08e2:
            r0 = -1
            r1 = 4096(0x1000, float:5.74E-42)
            goto L_0x090f
        L_0x08e6:
            r0 = -1
            r1 = 2048(0x800, float:2.87E-42)
            goto L_0x090f
        L_0x08ea:
            r1 = 1024(0x400, float:1.435E-42)
            goto L_0x0905
        L_0x08ed:
            r0 = -1
            r1 = 512(0x200, float:7.175E-43)
            goto L_0x090f
        L_0x08f1:
            r0 = -1
            r1 = 256(0x100, float:3.59E-43)
            goto L_0x090f
        L_0x08f5:
            r0 = -1
            r1 = 128(0x80, float:1.794E-43)
            goto L_0x090f
        L_0x08f9:
            r0 = -1
            r1 = 64
            goto L_0x090f
        L_0x08fd:
            r0 = -1
            r1 = 32
            goto L_0x090f
        L_0x0901:
            r0 = -1
            r1 = 16
            goto L_0x090f
        L_0x0905:
            r0 = -1
            goto L_0x090f
        L_0x0907:
            r0 = -1
            r1 = 4
            goto L_0x090f
        L_0x090a:
            r0 = -1
            r1 = 2
            goto L_0x090f
        L_0x090d:
            r0 = -1
            r1 = 1
        L_0x090f:
            if (r1 != r0) goto L_0x0919
            r15 = 30
            java.lang.String r0 = "Unknown AV1 level: "
            defpackage.y2.t(r15, r0, r2, r5)
            goto L_0x0940
        L_0x0919:
            android.util.Pair r0 = new android.util.Pair
            java.lang.Integer r15 = java.lang.Integer.valueOf(r15)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0.<init>(r15, r1)
            r1 = r0
            goto L_0x0941
        L_0x0928:
            java.lang.String r15 = java.lang.String.valueOf(r8)
            int r0 = r15.length()
            if (r0 == 0) goto L_0x0938
            java.lang.String r15 = r3.concat(r15)
            goto L_0x093d
        L_0x0938:
            java.lang.String r15 = new java.lang.String
            r15.<init>(r3)
        L_0x093d:
            com.google.android.exoplayer2.util.Log.w(r5, r15)
        L_0x0940:
            r1 = 0
        L_0x0941:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getCodecProfileAndLevel(com.google.android.exoplayer2.Format):android.util.Pair");
    }

    @Nullable
    public static MediaCodecInfo getDecoderInfo(String str, boolean z, boolean z2) throws DecoderQueryException {
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(str, z, z2);
        if (decoderInfos.isEmpty()) {
            return null;
        }
        return decoderInfos.get(0);
    }

    public static synchronized List<MediaCodecInfo> getDecoderInfos(String str, boolean z, boolean z2) throws DecoderQueryException {
        b bVar;
        synchronized (MediaCodecUtil.class) {
            a aVar = new a(str, z, z2);
            HashMap<a, List<MediaCodecInfo>> hashMap = b;
            List<MediaCodecInfo> list = hashMap.get(aVar);
            if (list != null) {
                return list;
            }
            int i = Util.a;
            if (i >= 21) {
                bVar = new d(z, z2);
            } else {
                bVar = new c();
            }
            ArrayList<MediaCodecInfo> c2 = c(aVar, bVar);
            if (z && c2.isEmpty() && 21 <= i && i <= 23) {
                c2 = c(aVar, new c());
                if (!c2.isEmpty()) {
                    String str2 = c2.get(0).a;
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 63 + String.valueOf(str2).length());
                    sb.append("MediaCodecList API didn't list secure decoder for: ");
                    sb.append(str);
                    sb.append(". Assuming: ");
                    sb.append(str2);
                    Log.w("MediaCodecUtil", sb.toString());
                }
            }
            a(str, c2);
            List<MediaCodecInfo> unmodifiableList = Collections.unmodifiableList(c2);
            hashMap.put(aVar, unmodifiableList);
            return unmodifiableList;
        }
    }

    @CheckResult
    public static List<MediaCodecInfo> getDecoderInfosSortedByFormatSupport(List<MediaCodecInfo> list, Format format) {
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList, new x5(0, new i2(4, format)));
        return arrayList;
    }

    @Nullable
    public static MediaCodecInfo getDecryptOnlyDecoderInfo() throws DecoderQueryException {
        return getDecoderInfo("audio/raw", false, false);
    }

    public static int maxH264DecodableFrameSize() throws DecoderQueryException {
        int i;
        int i2;
        if (c == -1) {
            int i3 = 0;
            MediaCodecInfo decoderInfo = getDecoderInfo("video/avc", false, false);
            if (decoderInfo != null) {
                MediaCodecInfo.CodecProfileLevel[] profileLevels = decoderInfo.getProfileLevels();
                int length = profileLevels.length;
                int i4 = 0;
                while (i3 < length) {
                    int i5 = profileLevels[i3].level;
                    if (i5 != 1 && i5 != 2) {
                        switch (i5) {
                            case 8:
                            case 16:
                            case 32:
                                i2 = 101376;
                                break;
                            case 64:
                                i2 = 202752;
                                break;
                            case 128:
                            case 256:
                                i2 = 414720;
                                break;
                            case 512:
                                i2 = 921600;
                                break;
                            case 1024:
                                i2 = 1310720;
                                break;
                            case 2048:
                            case 4096:
                                i2 = 2097152;
                                break;
                            case 8192:
                                i2 = 2228224;
                                break;
                            case 16384:
                                i2 = 5652480;
                                break;
                            case 32768:
                            case 65536:
                                i2 = 9437184;
                                break;
                            case 131072:
                            case 262144:
                            case 524288:
                                i2 = 35651584;
                                break;
                            default:
                                i2 = -1;
                                break;
                        }
                    } else {
                        i2 = 25344;
                    }
                    i4 = Math.max(i2, i4);
                    i3++;
                }
                if (Util.a >= 21) {
                    i = 345600;
                } else {
                    i = 172800;
                }
                i3 = Math.max(i4, i);
            }
            c = i3;
        }
        return c;
    }

    public static void warmDecoderInfoCache(String str, boolean z, boolean z2) {
        try {
            getDecoderInfos(str, z, z2);
        } catch (DecoderQueryException e2) {
            Log.e("MediaCodecUtil", "Codec warming failed", e2);
        }
    }
}
