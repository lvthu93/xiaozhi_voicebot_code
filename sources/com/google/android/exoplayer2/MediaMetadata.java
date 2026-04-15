package com.google.android.exoplayer2;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Objects;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

public final class MediaMetadata implements Bundleable {
    public static final MediaMetadata w = new Builder().build();
    public static final f2 x = new f2(5);
    @Nullable
    public final CharSequence c;
    @Nullable
    public final CharSequence f;
    @Nullable
    public final CharSequence g;
    @Nullable
    public final CharSequence h;
    @Nullable
    public final CharSequence i;
    @Nullable
    public final CharSequence j;
    @Nullable
    public final CharSequence k;
    @Nullable
    public final Uri l;
    @Nullable
    public final Rating m;
    @Nullable
    public final Rating n;
    @Nullable
    public final byte[] o;
    @Nullable
    public final Uri p;
    @Nullable
    public final Integer q;
    @Nullable
    public final Integer r;
    @Nullable
    public final Integer s;
    @Nullable
    public final Boolean t;
    @Nullable
    public final Integer u;
    @Nullable
    public final Bundle v;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface FolderType {
    }

    public MediaMetadata(Builder builder) {
        this.c = builder.a;
        this.f = builder.b;
        this.g = builder.c;
        this.h = builder.d;
        this.i = builder.e;
        this.j = builder.f;
        this.k = builder.g;
        this.l = builder.h;
        this.m = builder.i;
        this.n = builder.j;
        this.o = builder.k;
        this.p = builder.l;
        this.q = builder.m;
        this.r = builder.n;
        this.s = builder.o;
        this.t = builder.p;
        this.u = builder.q;
        this.v = builder.r;
    }

    public static String a(int i2) {
        return Integer.toString(i2, 36);
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MediaMetadata.class != obj.getClass()) {
            return false;
        }
        MediaMetadata mediaMetadata = (MediaMetadata) obj;
        if (!Util.areEqual(this.c, mediaMetadata.c) || !Util.areEqual(this.f, mediaMetadata.f) || !Util.areEqual(this.g, mediaMetadata.g) || !Util.areEqual(this.h, mediaMetadata.h) || !Util.areEqual(this.i, mediaMetadata.i) || !Util.areEqual(this.j, mediaMetadata.j) || !Util.areEqual(this.k, mediaMetadata.k) || !Util.areEqual(this.l, mediaMetadata.l) || !Util.areEqual(this.m, mediaMetadata.m) || !Util.areEqual(this.n, mediaMetadata.n) || !Arrays.equals(this.o, mediaMetadata.o) || !Util.areEqual(this.p, mediaMetadata.p) || !Util.areEqual(this.q, mediaMetadata.q) || !Util.areEqual(this.r, mediaMetadata.r) || !Util.areEqual(this.s, mediaMetadata.s) || !Util.areEqual(this.t, mediaMetadata.t) || !Util.areEqual(this.u, mediaMetadata.u)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.c, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, Integer.valueOf(Arrays.hashCode(this.o)), this.p, this.q, this.r, this.s, this.t, this.u);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putCharSequence(a(0), this.c);
        bundle.putCharSequence(a(1), this.f);
        bundle.putCharSequence(a(2), this.g);
        bundle.putCharSequence(a(3), this.h);
        bundle.putCharSequence(a(4), this.i);
        bundle.putCharSequence(a(5), this.j);
        bundle.putCharSequence(a(6), this.k);
        bundle.putParcelable(a(7), this.l);
        bundle.putByteArray(a(10), this.o);
        bundle.putParcelable(a(11), this.p);
        Rating rating = this.m;
        if (rating != null) {
            bundle.putBundle(a(8), rating.toBundle());
        }
        Rating rating2 = this.n;
        if (rating2 != null) {
            bundle.putBundle(a(9), rating2.toBundle());
        }
        Integer num = this.q;
        if (num != null) {
            bundle.putInt(a(12), num.intValue());
        }
        Integer num2 = this.r;
        if (num2 != null) {
            bundle.putInt(a(13), num2.intValue());
        }
        Integer num3 = this.s;
        if (num3 != null) {
            bundle.putInt(a(14), num3.intValue());
        }
        Boolean bool = this.t;
        if (bool != null) {
            bundle.putBoolean(a(15), bool.booleanValue());
        }
        Integer num4 = this.u;
        if (num4 != null) {
            bundle.putInt(a(16), num4.intValue());
        }
        Bundle bundle2 = this.v;
        if (bundle2 != null) {
            bundle.putBundle(a(1000), bundle2);
        }
        return bundle;
    }

    public static final class Builder {
        @Nullable
        public CharSequence a;
        @Nullable
        public CharSequence b;
        @Nullable
        public CharSequence c;
        @Nullable
        public CharSequence d;
        @Nullable
        public CharSequence e;
        @Nullable
        public CharSequence f;
        @Nullable
        public CharSequence g;
        @Nullable
        public Uri h;
        @Nullable
        public Rating i;
        @Nullable
        public Rating j;
        @Nullable
        public byte[] k;
        @Nullable
        public Uri l;
        @Nullable
        public Integer m;
        @Nullable
        public Integer n;
        @Nullable
        public Integer o;
        @Nullable
        public Boolean p;
        @Nullable
        public Integer q;
        @Nullable
        public Bundle r;

        public Builder() {
        }

        public Builder(MediaMetadata mediaMetadata) {
            this.a = mediaMetadata.c;
            this.b = mediaMetadata.f;
            this.c = mediaMetadata.g;
            this.d = mediaMetadata.h;
            this.e = mediaMetadata.i;
            this.f = mediaMetadata.j;
            this.g = mediaMetadata.k;
            this.h = mediaMetadata.l;
            this.i = mediaMetadata.m;
            this.j = mediaMetadata.n;
            this.k = mediaMetadata.o;
            this.l = mediaMetadata.p;
            this.m = mediaMetadata.q;
            this.n = mediaMetadata.r;
            this.o = mediaMetadata.s;
            this.p = mediaMetadata.t;
            this.q = mediaMetadata.u;
            this.r = mediaMetadata.v;
        }

        public MediaMetadata build() {
            return new MediaMetadata(this);
        }

        public Builder populateFromMetadata(Metadata metadata) {
            for (int i2 = 0; i2 < metadata.length(); i2++) {
                metadata.get(i2).populateMediaMetadata(this);
            }
            return this;
        }

        public Builder setAlbumArtist(@Nullable CharSequence charSequence) {
            this.d = charSequence;
            return this;
        }

        public Builder setAlbumTitle(@Nullable CharSequence charSequence) {
            this.c = charSequence;
            return this;
        }

        public Builder setArtist(@Nullable CharSequence charSequence) {
            this.b = charSequence;
            return this;
        }

        public Builder setArtworkData(@Nullable byte[] bArr) {
            this.k = bArr == null ? null : (byte[]) bArr.clone();
            return this;
        }

        public Builder setArtworkUri(@Nullable Uri uri) {
            this.l = uri;
            return this;
        }

        public Builder setDescription(@Nullable CharSequence charSequence) {
            this.g = charSequence;
            return this;
        }

        public Builder setDisplayTitle(@Nullable CharSequence charSequence) {
            this.e = charSequence;
            return this;
        }

        public Builder setExtras(@Nullable Bundle bundle) {
            this.r = bundle;
            return this;
        }

        public Builder setFolderType(@Nullable Integer num) {
            this.o = num;
            return this;
        }

        public Builder setIsPlayable(@Nullable Boolean bool) {
            this.p = bool;
            return this;
        }

        public Builder setMediaUri(@Nullable Uri uri) {
            this.h = uri;
            return this;
        }

        public Builder setOverallRating(@Nullable Rating rating) {
            this.j = rating;
            return this;
        }

        public Builder setSubtitle(@Nullable CharSequence charSequence) {
            this.f = charSequence;
            return this;
        }

        public Builder setTitle(@Nullable CharSequence charSequence) {
            this.a = charSequence;
            return this;
        }

        public Builder setTotalTrackCount(@Nullable Integer num) {
            this.n = num;
            return this;
        }

        public Builder setTrackNumber(@Nullable Integer num) {
            this.m = num;
            return this;
        }

        public Builder setUserRating(@Nullable Rating rating) {
            this.i = rating;
            return this;
        }

        public Builder setYear(@Nullable Integer num) {
            this.q = num;
            return this;
        }

        public Builder populateFromMetadata(List<Metadata> list) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                Metadata metadata = list.get(i2);
                for (int i3 = 0; i3 < metadata.length(); i3++) {
                    metadata.get(i3).populateMediaMetadata(this);
                }
            }
            return this;
        }
    }
}
