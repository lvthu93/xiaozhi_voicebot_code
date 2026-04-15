package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class MediaItem implements Bundleable {
    public static final f2 j = new f2(2);
    public final String c;
    @Nullable
    public final PlaybackProperties f;
    public final LiveConfiguration g;
    public final MediaMetadata h;
    public final ClippingProperties i;

    public static final class AdsConfiguration {
        public final Uri a;
        @Nullable
        public final Object b;

        public AdsConfiguration(Uri uri, Object obj) {
            this.a = uri;
            this.b = obj;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AdsConfiguration)) {
                return false;
            }
            AdsConfiguration adsConfiguration = (AdsConfiguration) obj;
            if (!this.a.equals(adsConfiguration.a) || !Util.areEqual(this.b, adsConfiguration.b)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i;
            int hashCode = this.a.hashCode() * 31;
            Object obj = this.b;
            if (obj != null) {
                i = obj.hashCode();
            } else {
                i = 0;
            }
            return hashCode + i;
        }
    }

    public static final class Builder {
        @Nullable
        public String a;
        public float aa = -3.4028235E38f;
        public float ab = -3.4028235E38f;
        @Nullable
        public Uri b;
        @Nullable
        public String c;
        public long d;
        public long e = Long.MIN_VALUE;
        public boolean f;
        public boolean g;
        public boolean h;
        @Nullable
        public Uri i;
        public Map<String, String> j = Collections.emptyMap();
        @Nullable
        public UUID k;
        public boolean l;
        public boolean m;
        public boolean n;
        public List<Integer> o = Collections.emptyList();
        @Nullable
        public byte[] p;
        public List<StreamKey> q = Collections.emptyList();
        @Nullable
        public String r;
        public List<Subtitle> s = Collections.emptyList();
        @Nullable
        public Uri t;
        @Nullable
        public Object u;
        @Nullable
        public Object v;
        @Nullable
        public MediaMetadata w;
        public long x = -9223372036854775807L;
        public long y = -9223372036854775807L;
        public long z = -9223372036854775807L;

        public MediaItem build() {
            boolean z2;
            PlaybackProperties playbackProperties;
            DrmConfiguration drmConfiguration;
            if (this.i == null || this.k != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            Assertions.checkState(z2);
            Uri uri = this.b;
            AdsConfiguration adsConfiguration = null;
            if (uri != null) {
                String str = this.c;
                UUID uuid = this.k;
                if (uuid != null) {
                    drmConfiguration = new DrmConfiguration(uuid, this.i, this.j, this.l, this.n, this.m, this.o, this.p);
                } else {
                    drmConfiguration = null;
                }
                Uri uri2 = this.t;
                if (uri2 != null) {
                    adsConfiguration = new AdsConfiguration(uri2, this.u);
                }
                playbackProperties = new PlaybackProperties(uri, str, drmConfiguration, adsConfiguration, this.q, this.r, this.s, this.v);
            } else {
                playbackProperties = null;
            }
            String str2 = this.a;
            if (str2 == null) {
                str2 = "";
            }
            String str3 = str2;
            ClippingProperties clippingProperties = new ClippingProperties(this.d, this.e, this.f, this.g, this.h);
            LiveConfiguration liveConfiguration = new LiveConfiguration(this.x, this.y, this.z, this.aa, this.ab);
            MediaMetadata mediaMetadata = this.w;
            if (mediaMetadata == null) {
                mediaMetadata = MediaMetadata.w;
            }
            return new MediaItem(str3, clippingProperties, playbackProperties, liveConfiguration, mediaMetadata);
        }

        public Builder setAdTagUri(@Nullable String str) {
            return setAdTagUri(str != null ? Uri.parse(str) : null);
        }

        public Builder setClipEndPositionMs(long j2) {
            boolean z2;
            if (j2 == Long.MIN_VALUE || j2 >= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            Assertions.checkArgument(z2);
            this.e = j2;
            return this;
        }

        public Builder setClipRelativeToDefaultPosition(boolean z2) {
            this.g = z2;
            return this;
        }

        public Builder setClipRelativeToLiveWindow(boolean z2) {
            this.f = z2;
            return this;
        }

        public Builder setClipStartPositionMs(long j2) {
            boolean z2;
            if (j2 >= 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            Assertions.checkArgument(z2);
            this.d = j2;
            return this;
        }

        public Builder setClipStartsAtKeyFrame(boolean z2) {
            this.h = z2;
            return this;
        }

        public Builder setCustomCacheKey(@Nullable String str) {
            this.r = str;
            return this;
        }

        public Builder setDrmForceDefaultLicenseUri(boolean z2) {
            this.n = z2;
            return this;
        }

        public Builder setDrmKeySetId(@Nullable byte[] bArr) {
            this.p = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
            return this;
        }

        public Builder setDrmLicenseRequestHeaders(@Nullable Map<String, String> map) {
            Map<String, String> map2;
            if (map == null || map.isEmpty()) {
                map2 = Collections.emptyMap();
            } else {
                map2 = Collections.unmodifiableMap(new HashMap(map));
            }
            this.j = map2;
            return this;
        }

        public Builder setDrmLicenseUri(@Nullable Uri uri) {
            this.i = uri;
            return this;
        }

        public Builder setDrmMultiSession(boolean z2) {
            this.l = z2;
            return this;
        }

        public Builder setDrmPlayClearContentWithoutKey(boolean z2) {
            this.m = z2;
            return this;
        }

        public Builder setDrmSessionForClearPeriods(boolean z2) {
            List list;
            if (z2) {
                list = Arrays.asList(new Integer[]{2, 1});
            } else {
                list = Collections.emptyList();
            }
            setDrmSessionForClearTypes(list);
            return this;
        }

        public Builder setDrmSessionForClearTypes(@Nullable List<Integer> list) {
            List<Integer> list2;
            if (list == null || list.isEmpty()) {
                list2 = Collections.emptyList();
            } else {
                list2 = Collections.unmodifiableList(new ArrayList(list));
            }
            this.o = list2;
            return this;
        }

        public Builder setDrmUuid(@Nullable UUID uuid) {
            this.k = uuid;
            return this;
        }

        public Builder setLiveMaxOffsetMs(long j2) {
            this.z = j2;
            return this;
        }

        public Builder setLiveMaxPlaybackSpeed(float f2) {
            this.ab = f2;
            return this;
        }

        public Builder setLiveMinOffsetMs(long j2) {
            this.y = j2;
            return this;
        }

        public Builder setLiveMinPlaybackSpeed(float f2) {
            this.aa = f2;
            return this;
        }

        public Builder setLiveTargetOffsetMs(long j2) {
            this.x = j2;
            return this;
        }

        public Builder setMediaId(String str) {
            this.a = (String) Assertions.checkNotNull(str);
            return this;
        }

        public Builder setMediaMetadata(MediaMetadata mediaMetadata) {
            this.w = mediaMetadata;
            return this;
        }

        public Builder setMimeType(@Nullable String str) {
            this.c = str;
            return this;
        }

        public Builder setStreamKeys(@Nullable List<StreamKey> list) {
            List<StreamKey> list2;
            if (list == null || list.isEmpty()) {
                list2 = Collections.emptyList();
            } else {
                list2 = Collections.unmodifiableList(new ArrayList(list));
            }
            this.q = list2;
            return this;
        }

        public Builder setSubtitles(@Nullable List<Subtitle> list) {
            List<Subtitle> list2;
            if (list == null || list.isEmpty()) {
                list2 = Collections.emptyList();
            } else {
                list2 = Collections.unmodifiableList(new ArrayList(list));
            }
            this.s = list2;
            return this;
        }

        public Builder setTag(@Nullable Object obj) {
            this.v = obj;
            return this;
        }

        public Builder setUri(@Nullable String str) {
            return setUri(str == null ? null : Uri.parse(str));
        }

        public Builder setAdTagUri(@Nullable Uri uri) {
            return setAdTagUri(uri, (Object) null);
        }

        public Builder setDrmLicenseUri(@Nullable String str) {
            this.i = str == null ? null : Uri.parse(str);
            return this;
        }

        public Builder setUri(@Nullable Uri uri) {
            this.b = uri;
            return this;
        }

        public Builder setAdTagUri(@Nullable Uri uri, @Nullable Object obj) {
            this.t = uri;
            this.u = obj;
            return this;
        }
    }

    public static final class ClippingProperties implements Bundleable {
        public static final f2 j = new f2(3);
        public final long c;
        public final long f;
        public final boolean g;
        public final boolean h;
        public final boolean i;

        public /* synthetic */ ClippingProperties() {
            throw null;
        }

        public ClippingProperties(long j2, long j3, boolean z, boolean z2, boolean z3) {
            this.c = j2;
            this.f = j3;
            this.g = z;
            this.h = z2;
            this.i = z3;
        }

        public static String a(int i2) {
            return Integer.toString(i2, 36);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ClippingProperties)) {
                return false;
            }
            ClippingProperties clippingProperties = (ClippingProperties) obj;
            if (this.c == clippingProperties.c && this.f == clippingProperties.f && this.g == clippingProperties.g && this.h == clippingProperties.h && this.i == clippingProperties.i) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            long j2 = this.c;
            long j3 = this.f;
            return (((((((((int) (j2 ^ (j2 >>> 32))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31) + (this.g ? 1 : 0)) * 31) + (this.h ? 1 : 0)) * 31) + (this.i ? 1 : 0);
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(a(0), this.c);
            bundle.putLong(a(1), this.f);
            bundle.putBoolean(a(2), this.g);
            bundle.putBoolean(a(3), this.h);
            bundle.putBoolean(a(4), this.i);
            return bundle;
        }
    }

    public static final class DrmConfiguration {
        public final UUID a;
        @Nullable
        public final Uri b;
        public final Map<String, String> c;
        public final boolean d;
        public final boolean e;
        public final boolean f;
        public final List<Integer> g;
        @Nullable
        public final byte[] h;

        public DrmConfiguration() {
            throw null;
        }

        public DrmConfiguration(UUID uuid, Uri uri, Map map, boolean z, boolean z2, boolean z3, List list, byte[] bArr) {
            Assertions.checkArgument(!z2 || uri != null);
            this.a = uuid;
            this.b = uri;
            this.c = map;
            this.d = z;
            this.f = z2;
            this.e = z3;
            this.g = list;
            this.h = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DrmConfiguration)) {
                return false;
            }
            DrmConfiguration drmConfiguration = (DrmConfiguration) obj;
            if (!this.a.equals(drmConfiguration.a) || !Util.areEqual(this.b, drmConfiguration.b) || !Util.areEqual(this.c, drmConfiguration.c) || this.d != drmConfiguration.d || this.f != drmConfiguration.f || this.e != drmConfiguration.e || !this.g.equals(drmConfiguration.g) || !Arrays.equals(this.h, drmConfiguration.h)) {
                return false;
            }
            return true;
        }

        @Nullable
        public byte[] getKeySetId() {
            byte[] bArr = this.h;
            if (bArr != null) {
                return Arrays.copyOf(bArr, bArr.length);
            }
            return null;
        }

        public int hashCode() {
            int i;
            int hashCode = this.a.hashCode() * 31;
            Uri uri = this.b;
            if (uri != null) {
                i = uri.hashCode();
            } else {
                i = 0;
            }
            int hashCode2 = this.c.hashCode();
            int hashCode3 = this.g.hashCode();
            return Arrays.hashCode(this.h) + ((hashCode3 + ((((((((hashCode2 + ((hashCode + i) * 31)) * 31) + (this.d ? 1 : 0)) * 31) + (this.f ? 1 : 0)) * 31) + (this.e ? 1 : 0)) * 31)) * 31);
        }
    }

    public static final class LiveConfiguration implements Bundleable {
        public static final LiveConfiguration j = new LiveConfiguration(-9223372036854775807L, -9223372036854775807L, -9223372036854775807L, -3.4028235E38f, -3.4028235E38f);
        public static final f2 k = new f2(4);
        public final long c;
        public final long f;
        public final long g;
        public final float h;
        public final float i;

        public LiveConfiguration(long j2, long j3, long j4, float f2, float f3) {
            this.c = j2;
            this.f = j3;
            this.g = j4;
            this.h = f2;
            this.i = f3;
        }

        public static String a(int i2) {
            return Integer.toString(i2, 36);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LiveConfiguration)) {
                return false;
            }
            LiveConfiguration liveConfiguration = (LiveConfiguration) obj;
            if (this.c == liveConfiguration.c && this.f == liveConfiguration.f && this.g == liveConfiguration.g && this.h == liveConfiguration.h && this.i == liveConfiguration.i) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i2;
            long j2 = this.c;
            long j3 = this.f;
            long j4 = this.g;
            int i3 = ((((((int) (j2 ^ (j2 >>> 32))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31) + ((int) (j4 ^ (j4 >>> 32)))) * 31;
            int i4 = 0;
            float f2 = this.h;
            if (f2 != 0.0f) {
                i2 = Float.floatToIntBits(f2);
            } else {
                i2 = 0;
            }
            int i5 = (i3 + i2) * 31;
            float f3 = this.i;
            if (f3 != 0.0f) {
                i4 = Float.floatToIntBits(f3);
            }
            return i5 + i4;
        }

        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putLong(a(0), this.c);
            bundle.putLong(a(1), this.f);
            bundle.putLong(a(2), this.g);
            bundle.putFloat(a(3), this.h);
            bundle.putFloat(a(4), this.i);
            return bundle;
        }
    }

    public static final class PlaybackProperties {
        public final Uri a;
        @Nullable
        public final String b;
        @Nullable
        public final DrmConfiguration c;
        @Nullable
        public final AdsConfiguration d;
        public final List<StreamKey> e;
        @Nullable
        public final String f;
        public final List<Subtitle> g;
        @Nullable
        public final Object h;

        public PlaybackProperties() {
            throw null;
        }

        public PlaybackProperties(Uri uri, String str, DrmConfiguration drmConfiguration, AdsConfiguration adsConfiguration, List list, String str2, List list2, Object obj) {
            this.a = uri;
            this.b = str;
            this.c = drmConfiguration;
            this.d = adsConfiguration;
            this.e = list;
            this.f = str2;
            this.g = list2;
            this.h = obj;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PlaybackProperties)) {
                return false;
            }
            PlaybackProperties playbackProperties = (PlaybackProperties) obj;
            if (!this.a.equals(playbackProperties.a) || !Util.areEqual(this.b, playbackProperties.b) || !Util.areEqual(this.c, playbackProperties.c) || !Util.areEqual(this.d, playbackProperties.d) || !this.e.equals(playbackProperties.e) || !Util.areEqual(this.f, playbackProperties.f) || !this.g.equals(playbackProperties.g) || !Util.areEqual(this.h, playbackProperties.h)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i;
            int i2;
            int i3;
            int i4;
            int hashCode = this.a.hashCode() * 31;
            int i5 = 0;
            String str = this.b;
            if (str == null) {
                i = 0;
            } else {
                i = str.hashCode();
            }
            int i6 = (hashCode + i) * 31;
            DrmConfiguration drmConfiguration = this.c;
            if (drmConfiguration == null) {
                i2 = 0;
            } else {
                i2 = drmConfiguration.hashCode();
            }
            int i7 = (i6 + i2) * 31;
            AdsConfiguration adsConfiguration = this.d;
            if (adsConfiguration == null) {
                i3 = 0;
            } else {
                i3 = adsConfiguration.hashCode();
            }
            int hashCode2 = (this.e.hashCode() + ((i7 + i3) * 31)) * 31;
            String str2 = this.f;
            if (str2 == null) {
                i4 = 0;
            } else {
                i4 = str2.hashCode();
            }
            int hashCode3 = (this.g.hashCode() + ((hashCode2 + i4) * 31)) * 31;
            Object obj = this.h;
            if (obj != null) {
                i5 = obj.hashCode();
            }
            return hashCode3 + i5;
        }
    }

    public static final class Subtitle {
        public final Uri a;
        public final String b;
        @Nullable
        public final String c;
        public final int d;
        public final int e;
        @Nullable
        public final String f;

        public Subtitle(Uri uri, String str, @Nullable String str2) {
            this(uri, str, str2, 0);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Subtitle)) {
                return false;
            }
            Subtitle subtitle = (Subtitle) obj;
            if (!this.a.equals(subtitle.a) || !this.b.equals(subtitle.b) || !Util.areEqual(this.c, subtitle.c) || this.d != subtitle.d || this.e != subtitle.e || !Util.areEqual(this.f, subtitle.f)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i;
            int hashCode = (this.b.hashCode() + (this.a.hashCode() * 31)) * 31;
            int i2 = 0;
            String str = this.c;
            if (str == null) {
                i = 0;
            } else {
                i = str.hashCode();
            }
            int i3 = (((((hashCode + i) * 31) + this.d) * 31) + this.e) * 31;
            String str2 = this.f;
            if (str2 != null) {
                i2 = str2.hashCode();
            }
            return i3 + i2;
        }

        public Subtitle(Uri uri, String str, @Nullable String str2, int i) {
            this(uri, str, str2, i, 0, (String) null);
        }

        public Subtitle(Uri uri, String str, @Nullable String str2, int i, int i2, @Nullable String str3) {
            this.a = uri;
            this.b = str;
            this.c = str2;
            this.d = i;
            this.e = i2;
            this.f = str3;
        }
    }

    public MediaItem(String str, ClippingProperties clippingProperties, @Nullable PlaybackProperties playbackProperties, LiveConfiguration liveConfiguration, MediaMetadata mediaMetadata) {
        this.c = str;
        this.f = playbackProperties;
        this.g = liveConfiguration;
        this.h = mediaMetadata;
        this.i = clippingProperties;
    }

    public static String a(int i2) {
        return Integer.toString(i2, 36);
    }

    public static MediaItem fromUri(String str) {
        return new Builder().setUri(str).build();
    }

    public Builder buildUpon() {
        Builder builder = new Builder();
        ClippingProperties clippingProperties = this.i;
        builder.e = clippingProperties.f;
        builder.f = clippingProperties.g;
        builder.g = clippingProperties.h;
        builder.d = clippingProperties.c;
        builder.h = clippingProperties.i;
        builder.a = this.c;
        builder.w = this.h;
        LiveConfiguration liveConfiguration = this.g;
        builder.x = liveConfiguration.c;
        builder.y = liveConfiguration.f;
        builder.z = liveConfiguration.g;
        builder.aa = liveConfiguration.h;
        builder.ab = liveConfiguration.i;
        PlaybackProperties playbackProperties = this.f;
        if (playbackProperties != null) {
            builder.r = playbackProperties.f;
            builder.c = playbackProperties.b;
            builder.b = playbackProperties.a;
            builder.q = playbackProperties.e;
            builder.s = playbackProperties.g;
            builder.v = playbackProperties.h;
            DrmConfiguration drmConfiguration = playbackProperties.c;
            if (drmConfiguration != null) {
                builder.i = drmConfiguration.b;
                builder.j = drmConfiguration.c;
                builder.l = drmConfiguration.d;
                builder.n = drmConfiguration.f;
                builder.m = drmConfiguration.e;
                builder.o = drmConfiguration.g;
                builder.k = drmConfiguration.a;
                builder.p = drmConfiguration.getKeySetId();
            }
            AdsConfiguration adsConfiguration = playbackProperties.d;
            if (adsConfiguration != null) {
                builder.t = adsConfiguration.a;
                builder.u = adsConfiguration.b;
            }
        }
        return builder;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaItem)) {
            return false;
        }
        MediaItem mediaItem = (MediaItem) obj;
        if (!Util.areEqual(this.c, mediaItem.c) || !this.i.equals(mediaItem.i) || !Util.areEqual(this.f, mediaItem.f) || !Util.areEqual(this.g, mediaItem.g) || !Util.areEqual(this.h, mediaItem.h)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i2;
        int hashCode = this.c.hashCode() * 31;
        PlaybackProperties playbackProperties = this.f;
        if (playbackProperties != null) {
            i2 = playbackProperties.hashCode();
        } else {
            i2 = 0;
        }
        int hashCode2 = this.g.hashCode();
        int hashCode3 = this.i.hashCode();
        return this.h.hashCode() + ((hashCode3 + ((hashCode2 + ((hashCode + i2) * 31)) * 31)) * 31);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(a(0), this.c);
        bundle.putBundle(a(1), this.g.toBundle());
        bundle.putBundle(a(2), this.h.toBundle());
        bundle.putBundle(a(3), this.i.toBundle());
        return bundle;
    }

    public static MediaItem fromUri(Uri uri) {
        return new Builder().setUri(uri).build();
    }
}
