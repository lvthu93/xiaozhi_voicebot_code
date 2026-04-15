package com.google.android.exoplayer2;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.UnsupportedMediaCrypto;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

public final class Format implements Parcelable {
    public static final Parcelable.Creator<Format> CREATOR = new a();
    public final int aa;
    @Nullable
    public final ColorInfo ab;
    public final int ac;
    public final int ad;
    public final int ae;
    public final int af;
    public final int ag;
    public final int ah;
    @Nullable
    public final Class<? extends ExoMediaCrypto> ai;
    public int aj;
    @Nullable
    public final String c;
    @Nullable
    public final String f;
    @Nullable
    public final String g;
    public final int h;
    public final int i;
    public final int j;
    public final int k;
    public final int l;
    @Nullable
    public final String m;
    @Nullable
    public final Metadata n;
    @Nullable
    public final String o;
    @Nullable
    public final String p;
    public final int q;
    public final List<byte[]> r;
    @Nullable
    public final DrmInitData s;
    public final long t;
    public final int u;
    public final int v;
    public final float w;
    public final int x;
    public final float y;
    @Nullable
    public final byte[] z;

    public static final class Builder {
        @Nullable
        public String a;
        public int aa;
        public int ab;
        public int ac;
        @Nullable
        public Class<? extends ExoMediaCrypto> ad;
        @Nullable
        public String b;
        @Nullable
        public String c;
        public int d;
        public int e;
        public int f;
        public int g;
        @Nullable
        public String h;
        @Nullable
        public Metadata i;
        @Nullable
        public String j;
        @Nullable
        public String k;
        public int l;
        @Nullable
        public List<byte[]> m;
        @Nullable
        public DrmInitData n;
        public long o;
        public int p;
        public int q;
        public float r;
        public int s;
        public float t;
        @Nullable
        public byte[] u;
        public int v;
        @Nullable
        public ColorInfo w;
        public int x;
        public int y;
        public int z;

        public Builder() {
            this.f = -1;
            this.g = -1;
            this.l = -1;
            this.o = Long.MAX_VALUE;
            this.p = -1;
            this.q = -1;
            this.r = -1.0f;
            this.t = 1.0f;
            this.v = -1;
            this.x = -1;
            this.y = -1;
            this.z = -1;
            this.ac = -1;
        }

        public Format build() {
            return new Format(this);
        }

        public Builder setAccessibilityChannel(int i2) {
            this.ac = i2;
            return this;
        }

        public Builder setAverageBitrate(int i2) {
            this.f = i2;
            return this;
        }

        public Builder setChannelCount(int i2) {
            this.x = i2;
            return this;
        }

        public Builder setCodecs(@Nullable String str) {
            this.h = str;
            return this;
        }

        public Builder setColorInfo(@Nullable ColorInfo colorInfo) {
            this.w = colorInfo;
            return this;
        }

        public Builder setContainerMimeType(@Nullable String str) {
            this.j = str;
            return this;
        }

        public Builder setDrmInitData(@Nullable DrmInitData drmInitData) {
            this.n = drmInitData;
            return this;
        }

        public Builder setEncoderDelay(int i2) {
            this.aa = i2;
            return this;
        }

        public Builder setEncoderPadding(int i2) {
            this.ab = i2;
            return this;
        }

        public Builder setExoMediaCryptoType(@Nullable Class<? extends ExoMediaCrypto> cls) {
            this.ad = cls;
            return this;
        }

        public Builder setFrameRate(float f2) {
            this.r = f2;
            return this;
        }

        public Builder setHeight(int i2) {
            this.q = i2;
            return this;
        }

        public Builder setId(@Nullable String str) {
            this.a = str;
            return this;
        }

        public Builder setInitializationData(@Nullable List<byte[]> list) {
            this.m = list;
            return this;
        }

        public Builder setLabel(@Nullable String str) {
            this.b = str;
            return this;
        }

        public Builder setLanguage(@Nullable String str) {
            this.c = str;
            return this;
        }

        public Builder setMaxInputSize(int i2) {
            this.l = i2;
            return this;
        }

        public Builder setMetadata(@Nullable Metadata metadata) {
            this.i = metadata;
            return this;
        }

        public Builder setPcmEncoding(int i2) {
            this.z = i2;
            return this;
        }

        public Builder setPeakBitrate(int i2) {
            this.g = i2;
            return this;
        }

        public Builder setPixelWidthHeightRatio(float f2) {
            this.t = f2;
            return this;
        }

        public Builder setProjectionData(@Nullable byte[] bArr) {
            this.u = bArr;
            return this;
        }

        public Builder setRoleFlags(int i2) {
            this.e = i2;
            return this;
        }

        public Builder setRotationDegrees(int i2) {
            this.s = i2;
            return this;
        }

        public Builder setSampleMimeType(@Nullable String str) {
            this.k = str;
            return this;
        }

        public Builder setSampleRate(int i2) {
            this.y = i2;
            return this;
        }

        public Builder setSelectionFlags(int i2) {
            this.d = i2;
            return this;
        }

        public Builder setStereoMode(int i2) {
            this.v = i2;
            return this;
        }

        public Builder setSubsampleOffsetUs(long j2) {
            this.o = j2;
            return this;
        }

        public Builder setWidth(int i2) {
            this.p = i2;
            return this;
        }

        public Builder setId(int i2) {
            this.a = Integer.toString(i2);
            return this;
        }

        public Builder(Format format) {
            this.a = format.c;
            this.b = format.f;
            this.c = format.g;
            this.d = format.h;
            this.e = format.i;
            this.f = format.j;
            this.g = format.k;
            this.h = format.m;
            this.i = format.n;
            this.j = format.o;
            this.k = format.p;
            this.l = format.q;
            this.m = format.r;
            this.n = format.s;
            this.o = format.t;
            this.p = format.u;
            this.q = format.v;
            this.r = format.w;
            this.s = format.x;
            this.t = format.y;
            this.u = format.z;
            this.v = format.aa;
            this.w = format.ab;
            this.x = format.ac;
            this.y = format.ad;
            this.z = format.ae;
            this.aa = format.af;
            this.ab = format.ag;
            this.ac = format.ah;
            this.ad = format.ai;
        }
    }

    public class a implements Parcelable.Creator<Format> {
        public Format createFromParcel(Parcel parcel) {
            return new Format(parcel);
        }

        public Format[] newArray(int i) {
            return new Format[i];
        }
    }

    public Format(Builder builder) {
        this.c = builder.a;
        this.f = builder.b;
        this.g = Util.normalizeLanguageCode(builder.c);
        this.h = builder.d;
        this.i = builder.e;
        int i2 = builder.f;
        this.j = i2;
        int i3 = builder.g;
        this.k = i3;
        this.l = i3 != -1 ? i3 : i2;
        this.m = builder.h;
        this.n = builder.i;
        this.o = builder.j;
        this.p = builder.k;
        this.q = builder.l;
        List<byte[]> list = builder.m;
        this.r = list == null ? Collections.emptyList() : list;
        DrmInitData drmInitData = builder.n;
        this.s = drmInitData;
        this.t = builder.o;
        this.u = builder.p;
        this.v = builder.q;
        this.w = builder.r;
        int i4 = builder.s;
        int i5 = 0;
        this.x = i4 == -1 ? 0 : i4;
        float f2 = builder.t;
        this.y = f2 == -1.0f ? 1.0f : f2;
        this.z = builder.u;
        this.aa = builder.v;
        this.ab = builder.w;
        this.ac = builder.x;
        this.ad = builder.y;
        this.ae = builder.z;
        int i6 = builder.aa;
        this.af = i6 == -1 ? 0 : i6;
        int i7 = builder.ab;
        this.ag = i7 != -1 ? i7 : i5;
        this.ah = builder.ac;
        Class<? extends ExoMediaCrypto> cls = builder.ad;
        if (cls != null || drmInitData == null) {
            this.ai = cls;
        } else {
            this.ai = UnsupportedMediaCrypto.class;
        }
    }

    @Deprecated
    public static Format createAudioContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable Metadata metadata, int i2, int i3, int i4, @Nullable List<byte[]> list, int i5, int i6, @Nullable String str6) {
        return new Builder().setId(str).setLabel(str2).setLanguage(str6).setSelectionFlags(i5).setRoleFlags(i6).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str5).setMetadata(metadata).setContainerMimeType(str3).setSampleMimeType(str4).setInitializationData(list).setChannelCount(i3).setSampleRate(i4).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i6, @Nullable String str4) {
        return new Builder().setId(str).setLanguage(str4).setSelectionFlags(i6).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setChannelCount(i4).setSampleRate(i5).build();
    }

    @Deprecated
    public static Format createContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i2, int i3, int i4, @Nullable String str6) {
        return new Builder().setId(str).setLabel(str2).setLanguage(str6).setSelectionFlags(i3).setRoleFlags(i4).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str5).setContainerMimeType(str3).setSampleMimeType(str4).build();
    }

    @Deprecated
    public static Format createImageSampleFormat(@Nullable String str, @Nullable String str2, int i2, @Nullable List<byte[]> list, @Nullable String str3) {
        return new Builder().setId(str).setLanguage(str3).setSelectionFlags(i2).setSampleMimeType(str2).setInitializationData(list).build();
    }

    @Deprecated
    public static Format createSampleFormat(@Nullable String str, @Nullable String str2) {
        return new Builder().setId(str).setSampleMimeType(str2).build();
    }

    @Deprecated
    public static Format createTextContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i2, int i3, int i4, @Nullable String str6) {
        return new Builder().setId(str).setLabel(str2).setLanguage(str6).setSelectionFlags(i3).setRoleFlags(i4).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str5).setContainerMimeType(str3).setSampleMimeType(str4).build();
    }

    @Deprecated
    public static Format createTextSampleFormat(@Nullable String str, @Nullable String str2, int i2, @Nullable String str3) {
        return new Builder().setId(str).setLanguage(str3).setSelectionFlags(i2).setSampleMimeType(str2).build();
    }

    @Deprecated
    public static Format createVideoContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable Metadata metadata, int i2, int i3, int i4, float f2, @Nullable List<byte[]> list, int i5, int i6) {
        return new Builder().setId(str).setLabel(str2).setSelectionFlags(i5).setRoleFlags(i6).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str5).setMetadata(metadata).setContainerMimeType(str3).setSampleMimeType(str4).setInitializationData(list).setWidth(i3).setHeight(i4).setFrameRate(f2).build();
    }

    @Deprecated
    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, float f2, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData) {
        return new Builder().setId(str).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setWidth(i4).setHeight(i5).setFrameRate(f2).build();
    }

    public static String toLogString(@Nullable Format format) {
        int i2;
        if (format == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("id=");
        sb.append(format.c);
        sb.append(", mimeType=");
        sb.append(format.p);
        int i3 = format.l;
        if (i3 != -1) {
            sb.append(", bitrate=");
            sb.append(i3);
        }
        String str = format.m;
        if (str != null) {
            sb.append(", codecs=");
            sb.append(str);
        }
        DrmInitData drmInitData = format.s;
        if (drmInitData != null) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            for (int i4 = 0; i4 < drmInitData.h; i4++) {
                UUID uuid = drmInitData.get(i4).f;
                if (uuid.equals(C.b)) {
                    linkedHashSet.add("cenc");
                } else if (uuid.equals(C.c)) {
                    linkedHashSet.add("clearkey");
                } else if (uuid.equals(C.e)) {
                    linkedHashSet.add("playready");
                } else if (uuid.equals(C.d)) {
                    linkedHashSet.add("widevine");
                } else if (uuid.equals(C.a)) {
                    linkedHashSet.add("universal");
                } else {
                    String valueOf = String.valueOf(uuid);
                    StringBuilder sb2 = new StringBuilder(valueOf.length() + 10);
                    sb2.append("unknown (");
                    sb2.append(valueOf);
                    sb2.append(")");
                    linkedHashSet.add(sb2.toString());
                }
            }
            sb.append(", drm=[");
            sb.append(Joiner.on(',').join((Iterable<?>) linkedHashSet));
            sb.append(']');
        }
        int i5 = format.u;
        if (!(i5 == -1 || (i2 = format.v) == -1)) {
            sb.append(", res=");
            sb.append(i5);
            sb.append("x");
            sb.append(i2);
        }
        float f2 = format.w;
        if (f2 != -1.0f) {
            sb.append(", fps=");
            sb.append(f2);
        }
        int i6 = format.ac;
        if (i6 != -1) {
            sb.append(", channels=");
            sb.append(i6);
        }
        int i7 = format.ad;
        if (i7 != -1) {
            sb.append(", sample_rate=");
            sb.append(i7);
        }
        String str2 = format.g;
        if (str2 != null) {
            sb.append(", language=");
            sb.append(str2);
        }
        String str3 = format.f;
        if (str3 != null) {
            sb.append(", label=");
            sb.append(str3);
        }
        if ((format.i & 16384) != 0) {
            sb.append(", trick-play-track");
        }
        return sb.toString();
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    @Deprecated
    public Format copyWithBitrate(int i2) {
        return buildUpon().setAverageBitrate(i2).setPeakBitrate(i2).build();
    }

    @Deprecated
    public Format copyWithDrmInitData(@Nullable DrmInitData drmInitData) {
        return buildUpon().setDrmInitData(drmInitData).build();
    }

    public Format copyWithExoMediaCryptoType(@Nullable Class<? extends ExoMediaCrypto> cls) {
        return buildUpon().setExoMediaCryptoType(cls).build();
    }

    @Deprecated
    public Format copyWithFrameRate(float f2) {
        return buildUpon().setFrameRate(f2).build();
    }

    @Deprecated
    public Format copyWithGaplessInfo(int i2, int i3) {
        return buildUpon().setEncoderDelay(i2).setEncoderPadding(i3).build();
    }

    @Deprecated
    public Format copyWithLabel(@Nullable String str) {
        return buildUpon().setLabel(str).build();
    }

    @Deprecated
    public Format copyWithManifestFormatInfo(Format format) {
        return withManifestFormatInfo(format);
    }

    @Deprecated
    public Format copyWithMaxInputSize(int i2) {
        return buildUpon().setMaxInputSize(i2).build();
    }

    @Deprecated
    public Format copyWithMetadata(@Nullable Metadata metadata) {
        return buildUpon().setMetadata(metadata).build();
    }

    @Deprecated
    public Format copyWithSubsampleOffsetUs(long j2) {
        return buildUpon().setSubsampleOffsetUs(j2).build();
    }

    @Deprecated
    public Format copyWithVideoSize(int i2, int i3) {
        return buildUpon().setWidth(i2).setHeight(i3).build();
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        int i2;
        if (this == obj) {
            return true;
        }
        if (obj == null || Format.class != obj.getClass()) {
            return false;
        }
        Format format = (Format) obj;
        int i3 = this.aj;
        if ((i3 == 0 || (i2 = format.aj) == 0 || i3 == i2) && this.h == format.h && this.i == format.i && this.j == format.j && this.k == format.k && this.q == format.q && this.t == format.t && this.u == format.u && this.v == format.v && this.x == format.x && this.aa == format.aa && this.ac == format.ac && this.ad == format.ad && this.ae == format.ae && this.af == format.af && this.ag == format.ag && this.ah == format.ah && Float.compare(this.w, format.w) == 0 && Float.compare(this.y, format.y) == 0 && Util.areEqual(this.ai, format.ai) && Util.areEqual(this.c, format.c) && Util.areEqual(this.f, format.f) && Util.areEqual(this.m, format.m) && Util.areEqual(this.o, format.o) && Util.areEqual(this.p, format.p) && Util.areEqual(this.g, format.g) && Arrays.equals(this.z, format.z) && Util.areEqual(this.n, format.n) && Util.areEqual(this.ab, format.ab) && Util.areEqual(this.s, format.s) && initializationDataEquals(format)) {
            return true;
        }
        return false;
    }

    public int getPixelCount() {
        int i2;
        int i3 = this.u;
        if (i3 == -1 || (i2 = this.v) == -1) {
            return -1;
        }
        return i3 * i2;
    }

    public int hashCode() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        if (this.aj == 0) {
            int i9 = 0;
            String str = this.c;
            if (str == null) {
                i2 = 0;
            } else {
                i2 = str.hashCode();
            }
            int i10 = (527 + i2) * 31;
            String str2 = this.f;
            if (str2 != null) {
                i3 = str2.hashCode();
            } else {
                i3 = 0;
            }
            int i11 = (i10 + i3) * 31;
            String str3 = this.g;
            if (str3 == null) {
                i4 = 0;
            } else {
                i4 = str3.hashCode();
            }
            int i12 = (((((((((i11 + i4) * 31) + this.h) * 31) + this.i) * 31) + this.j) * 31) + this.k) * 31;
            String str4 = this.m;
            if (str4 == null) {
                i5 = 0;
            } else {
                i5 = str4.hashCode();
            }
            int i13 = (i12 + i5) * 31;
            Metadata metadata = this.n;
            if (metadata == null) {
                i6 = 0;
            } else {
                i6 = metadata.hashCode();
            }
            int i14 = (i13 + i6) * 31;
            String str5 = this.o;
            if (str5 == null) {
                i7 = 0;
            } else {
                i7 = str5.hashCode();
            }
            int i15 = (i14 + i7) * 31;
            String str6 = this.p;
            if (str6 == null) {
                i8 = 0;
            } else {
                i8 = str6.hashCode();
            }
            int floatToIntBits = Float.floatToIntBits(this.w);
            int floatToIntBits2 = (((((((((((((((Float.floatToIntBits(this.y) + ((((floatToIntBits + ((((((((((i15 + i8) * 31) + this.q) * 31) + ((int) this.t)) * 31) + this.u) * 31) + this.v) * 31)) * 31) + this.x) * 31)) * 31) + this.aa) * 31) + this.ac) * 31) + this.ad) * 31) + this.ae) * 31) + this.af) * 31) + this.ag) * 31) + this.ah) * 31;
            Class<? extends ExoMediaCrypto> cls = this.ai;
            if (cls != null) {
                i9 = cls.hashCode();
            }
            this.aj = floatToIntBits2 + i9;
        }
        return this.aj;
    }

    public boolean initializationDataEquals(Format format) {
        List<byte[]> list = this.r;
        if (list.size() != format.r.size()) {
            return false;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (!Arrays.equals(list.get(i2), format.r.get(i2))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        String str = this.c;
        int c2 = y2.c(str, 104);
        String str2 = this.f;
        int c3 = y2.c(str2, c2);
        String str3 = this.o;
        int c4 = y2.c(str3, c3);
        String str4 = this.p;
        int c5 = y2.c(str4, c4);
        String str5 = this.m;
        int c6 = y2.c(str5, c5);
        String str6 = this.g;
        StringBuilder l2 = y2.l(y2.c(str6, c6), "Format(", str, ", ", str2);
        l2.append(", ");
        l2.append(str3);
        l2.append(", ");
        l2.append(str4);
        l2.append(", ");
        l2.append(str5);
        l2.append(", ");
        l2.append(this.l);
        l2.append(", ");
        l2.append(str6);
        l2.append(", [");
        l2.append(this.u);
        l2.append(", ");
        l2.append(this.v);
        l2.append(", ");
        l2.append(this.w);
        l2.append("], [");
        l2.append(this.ac);
        l2.append(", ");
        l2.append(this.ad);
        l2.append("])");
        return l2.toString();
    }

    public Format withManifestFormatInfo(Format format) {
        String str;
        if (this == format) {
            return this;
        }
        int trackType = MimeTypes.getTrackType(this.p);
        String str2 = format.c;
        String str3 = format.f;
        if (str3 == null) {
            str3 = this.f;
        }
        if (!(trackType == 3 || trackType == 1) || (str = format.g) == null) {
            str = this.g;
        }
        int i2 = this.j;
        if (i2 == -1) {
            i2 = format.j;
        }
        int i3 = this.k;
        if (i3 == -1) {
            i3 = format.k;
        }
        String str4 = this.m;
        if (str4 == null) {
            String codecsOfType = Util.getCodecsOfType(format.m, trackType);
            if (Util.splitCodecs(codecsOfType).length == 1) {
                str4 = codecsOfType;
            }
        }
        Metadata metadata = format.n;
        Metadata metadata2 = this.n;
        if (metadata2 != null) {
            metadata = metadata2.copyWithAppendedEntriesFrom(metadata);
        }
        float f2 = this.w;
        if (f2 == -1.0f && trackType == 2) {
            f2 = format.w;
        }
        int i4 = this.h | format.h;
        int i5 = this.i | format.i;
        return buildUpon().setId(str2).setLabel(str3).setLanguage(str).setSelectionFlags(i4).setRoleFlags(i5).setAverageBitrate(i2).setPeakBitrate(i3).setCodecs(str4).setMetadata(metadata).setDrmInitData(DrmInitData.createSessionCreationData(format.s, this.s)).setFrameRate(f2).build();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.c);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeInt(this.h);
        parcel.writeInt(this.i);
        parcel.writeInt(this.j);
        parcel.writeInt(this.k);
        parcel.writeString(this.m);
        boolean z2 = false;
        parcel.writeParcelable(this.n, 0);
        parcel.writeString(this.o);
        parcel.writeString(this.p);
        parcel.writeInt(this.q);
        List<byte[]> list = this.r;
        int size = list.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            parcel.writeByteArray(list.get(i3));
        }
        parcel.writeParcelable(this.s, 0);
        parcel.writeLong(this.t);
        parcel.writeInt(this.u);
        parcel.writeInt(this.v);
        parcel.writeFloat(this.w);
        parcel.writeInt(this.x);
        parcel.writeFloat(this.y);
        byte[] bArr = this.z;
        if (bArr != null) {
            z2 = true;
        }
        Util.writeBoolean(parcel, z2);
        if (bArr != null) {
            parcel.writeByteArray(bArr);
        }
        parcel.writeInt(this.aa);
        parcel.writeParcelable(this.ab, i2);
        parcel.writeInt(this.ac);
        parcel.writeInt(this.ad);
        parcel.writeInt(this.ae);
        parcel.writeInt(this.af);
        parcel.writeInt(this.ag);
        parcel.writeInt(this.ah);
    }

    @Deprecated
    public static Format createTextSampleFormat(@Nullable String str, @Nullable String str2, int i2, @Nullable String str3, int i3, long j2, @Nullable List<byte[]> list) {
        return new Builder().setId(str).setLanguage(str3).setSelectionFlags(i2).setSampleMimeType(str2).setInitializationData(list).setSubsampleOffsetUs(j2).setAccessibilityChannel(i3).build();
    }

    @Deprecated
    public static Format createTextContainerFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, int i2, int i3, int i4, @Nullable String str6, int i5) {
        return new Builder().setId(str).setLabel(str2).setLanguage(str6).setSelectionFlags(i3).setRoleFlags(i4).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str5).setContainerMimeType(str3).setSampleMimeType(str4).setAccessibilityChannel(i5).build();
    }

    @Deprecated
    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, float f2, @Nullable List<byte[]> list, int i6, float f3, @Nullable DrmInitData drmInitData) {
        return new Builder().setId(str).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setWidth(i4).setHeight(i5).setFrameRate(f2).setRotationDegrees(i6).setPixelWidthHeightRatio(f3).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, int i6, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i7, @Nullable String str4) {
        return new Builder().setId(str).setLanguage(str4).setSelectionFlags(i7).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setChannelCount(i4).setSampleRate(i5).setPcmEncoding(i6).build();
    }

    @Deprecated
    public static Format createVideoSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, float f2, @Nullable List<byte[]> list, int i6, float f3, @Nullable byte[] bArr, int i7, @Nullable ColorInfo colorInfo, @Nullable DrmInitData drmInitData) {
        return new Builder().setId(str).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setWidth(i4).setHeight(i5).setFrameRate(f2).setRotationDegrees(i6).setPixelWidthHeightRatio(f3).setProjectionData(bArr).setStereoMode(i7).setColorInfo(colorInfo).build();
    }

    @Deprecated
    public static Format createAudioSampleFormat(@Nullable String str, @Nullable String str2, @Nullable String str3, int i2, int i3, int i4, int i5, int i6, int i7, int i8, @Nullable List<byte[]> list, @Nullable DrmInitData drmInitData, int i9, @Nullable String str4, @Nullable Metadata metadata) {
        return new Builder().setId(str).setLanguage(str4).setSelectionFlags(i9).setAverageBitrate(i2).setPeakBitrate(i2).setCodecs(str3).setMetadata(metadata).setSampleMimeType(str2).setMaxInputSize(i3).setInitializationData(list).setDrmInitData(drmInitData).setChannelCount(i4).setSampleRate(i5).setPcmEncoding(i6).setEncoderDelay(i7).setEncoderPadding(i8).build();
    }

    public Format(Parcel parcel) {
        this.c = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readInt();
        this.i = parcel.readInt();
        int readInt = parcel.readInt();
        this.j = readInt;
        int readInt2 = parcel.readInt();
        this.k = readInt2;
        this.l = readInt2 != -1 ? readInt2 : readInt;
        this.m = parcel.readString();
        this.n = (Metadata) parcel.readParcelable(Metadata.class.getClassLoader());
        this.o = parcel.readString();
        this.p = parcel.readString();
        this.q = parcel.readInt();
        int readInt3 = parcel.readInt();
        this.r = new ArrayList(readInt3);
        for (int i2 = 0; i2 < readInt3; i2++) {
            this.r.add((byte[]) Assertions.checkNotNull(parcel.createByteArray()));
        }
        DrmInitData drmInitData = (DrmInitData) parcel.readParcelable(DrmInitData.class.getClassLoader());
        this.s = drmInitData;
        this.t = parcel.readLong();
        this.u = parcel.readInt();
        this.v = parcel.readInt();
        this.w = parcel.readFloat();
        this.x = parcel.readInt();
        this.y = parcel.readFloat();
        Class cls = null;
        this.z = Util.readBoolean(parcel) ? parcel.createByteArray() : null;
        this.aa = parcel.readInt();
        this.ab = (ColorInfo) parcel.readParcelable(ColorInfo.class.getClassLoader());
        this.ac = parcel.readInt();
        this.ad = parcel.readInt();
        this.ae = parcel.readInt();
        this.af = parcel.readInt();
        this.ag = parcel.readInt();
        this.ah = parcel.readInt();
        this.ai = drmInitData != null ? UnsupportedMediaCrypto.class : cls;
    }
}
