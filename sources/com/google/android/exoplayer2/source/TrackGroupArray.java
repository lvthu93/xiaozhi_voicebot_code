package com.google.android.exoplayer2.source;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import java.util.Arrays;

public final class TrackGroupArray implements Parcelable {
    public static final Parcelable.Creator<TrackGroupArray> CREATOR = new a();
    public static final TrackGroupArray h = new TrackGroupArray(new TrackGroup[0]);
    public final int c;
    public final TrackGroup[] f;
    public int g;

    public class a implements Parcelable.Creator<TrackGroupArray> {
        public TrackGroupArray createFromParcel(Parcel parcel) {
            return new TrackGroupArray(parcel);
        }

        public TrackGroupArray[] newArray(int i) {
            return new TrackGroupArray[i];
        }
    }

    public TrackGroupArray(TrackGroup... trackGroupArr) {
        this.f = trackGroupArr;
        this.c = trackGroupArr.length;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TrackGroupArray.class != obj.getClass()) {
            return false;
        }
        TrackGroupArray trackGroupArray = (TrackGroupArray) obj;
        if (this.c != trackGroupArray.c || !Arrays.equals(this.f, trackGroupArray.f)) {
            return false;
        }
        return true;
    }

    public TrackGroup get(int i) {
        return this.f[i];
    }

    public int hashCode() {
        if (this.g == 0) {
            this.g = Arrays.hashCode(this.f);
        }
        return this.g;
    }

    public int indexOf(TrackGroup trackGroup) {
        for (int i = 0; i < this.c; i++) {
            if (this.f[i] == trackGroup) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.c == 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = this.c;
        parcel.writeInt(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            parcel.writeParcelable(this.f[i3], 0);
        }
    }

    public TrackGroupArray(Parcel parcel) {
        int readInt = parcel.readInt();
        this.c = readInt;
        this.f = new TrackGroup[readInt];
        for (int i = 0; i < this.c; i++) {
            this.f[i] = (TrackGroup) parcel.readParcelable(TrackGroup.class.getClassLoader());
        }
    }
}
