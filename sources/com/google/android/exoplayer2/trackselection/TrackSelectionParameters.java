package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.accessibility.CaptioningManager;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Locale;

public class TrackSelectionParameters implements Parcelable {
    public static final Parcelable.Creator<TrackSelectionParameters> CREATOR = new a();
    public final ImmutableList<String> c;
    public final int f;
    public final ImmutableList<String> g;
    public final int h;
    public final boolean i;
    public final int j;

    public class a implements Parcelable.Creator<TrackSelectionParameters> {
        public TrackSelectionParameters createFromParcel(Parcel parcel) {
            return new TrackSelectionParameters(parcel);
        }

        public TrackSelectionParameters[] newArray(int i) {
            return new TrackSelectionParameters[i];
        }
    }

    static {
        new Builder().build();
    }

    public TrackSelectionParameters(ImmutableList<String> immutableList, int i2, ImmutableList<String> immutableList2, int i3, boolean z, int i4) {
        this.c = immutableList;
        this.f = i2;
        this.g = immutableList2;
        this.h = i3;
        this.i = z;
        this.j = i4;
    }

    public static TrackSelectionParameters getDefaults(Context context) {
        return new Builder(context).build();
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TrackSelectionParameters trackSelectionParameters = (TrackSelectionParameters) obj;
        if (this.c.equals(trackSelectionParameters.c) && this.f == trackSelectionParameters.f && this.g.equals(trackSelectionParameters.g) && this.h == trackSelectionParameters.h && this.i == trackSelectionParameters.i && this.j == trackSelectionParameters.j) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.g.hashCode() + ((((this.c.hashCode() + 31) * 31) + this.f) * 31)) * 31) + this.h) * 31) + (this.i ? 1 : 0)) * 31) + this.j;
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeList(this.c);
        parcel.writeInt(this.f);
        parcel.writeList(this.g);
        parcel.writeInt(this.h);
        Util.writeBoolean(parcel, this.i);
        parcel.writeInt(this.j);
    }

    public static class Builder {
        public ImmutableList<String> a;
        public int b;
        public ImmutableList<String> c;
        public int d;
        public boolean e;
        public int f;

        public Builder(Context context) {
            this();
            setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
        }

        public TrackSelectionParameters build() {
            return new TrackSelectionParameters(this.a, this.b, this.c, this.d, this.e, this.f);
        }

        public Builder setDisabledTextTrackSelectionFlags(int i) {
            this.f = i;
            return this;
        }

        public Builder setPreferredAudioLanguage(@Nullable String str) {
            if (str == null) {
                return setPreferredAudioLanguages(new String[0]);
            }
            return setPreferredAudioLanguages(str);
        }

        public Builder setPreferredAudioLanguages(String... strArr) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (String checkNotNull : (String[]) Assertions.checkNotNull(strArr)) {
                builder.add((Object) Util.normalizeLanguageCode((String) Assertions.checkNotNull(checkNotNull)));
            }
            this.a = builder.build();
            return this;
        }

        public Builder setPreferredAudioRoleFlags(int i) {
            this.b = i;
            return this;
        }

        public Builder setPreferredTextLanguage(@Nullable String str) {
            if (str == null) {
                return setPreferredTextLanguages(new String[0]);
            }
            return setPreferredTextLanguages(str);
        }

        public Builder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            CaptioningManager captioningManager;
            int i = Util.a;
            if (i >= 19 && ((i >= 23 || Looper.myLooper() != null) && (captioningManager = (CaptioningManager) context.getSystemService("captioning")) != null && captioningManager.isEnabled())) {
                this.d = 1088;
                Locale locale = captioningManager.getLocale();
                if (locale != null) {
                    this.c = ImmutableList.of(Util.getLocaleLanguageTag(locale));
                }
            }
            return this;
        }

        public Builder setPreferredTextLanguages(String... strArr) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (String checkNotNull : (String[]) Assertions.checkNotNull(strArr)) {
                builder.add((Object) Util.normalizeLanguageCode((String) Assertions.checkNotNull(checkNotNull)));
            }
            this.c = builder.build();
            return this;
        }

        public Builder setPreferredTextRoleFlags(int i) {
            this.d = i;
            return this;
        }

        public Builder setSelectUndeterminedTextLanguage(boolean z) {
            this.e = z;
            return this;
        }

        @Deprecated
        public Builder() {
            this.a = ImmutableList.of();
            this.b = 0;
            this.c = ImmutableList.of();
            this.d = 0;
            this.e = false;
            this.f = 0;
        }

        public Builder(TrackSelectionParameters trackSelectionParameters) {
            this.a = trackSelectionParameters.c;
            this.b = trackSelectionParameters.f;
            this.c = trackSelectionParameters.g;
            this.d = trackSelectionParameters.h;
            this.e = trackSelectionParameters.i;
            this.f = trackSelectionParameters.j;
        }
    }

    public TrackSelectionParameters(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readList(arrayList, (ClassLoader) null);
        this.c = ImmutableList.copyOf(arrayList);
        this.f = parcel.readInt();
        ArrayList arrayList2 = new ArrayList();
        parcel.readList(arrayList2, (ClassLoader) null);
        this.g = ImmutableList.copyOf(arrayList2);
        this.h = parcel.readInt();
        this.i = Util.readBoolean(parcel);
        this.j = parcel.readInt();
    }
}
