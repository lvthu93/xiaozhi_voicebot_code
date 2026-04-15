package com.google.android.exoplayer2.offline;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;

public final class StreamKey implements Comparable<StreamKey>, Parcelable {
    public static final Parcelable.Creator<StreamKey> CREATOR = new a();
    public final int c;
    public final int f;
    public final int g;

    public class a implements Parcelable.Creator<StreamKey> {
        public StreamKey createFromParcel(Parcel parcel) {
            return new StreamKey(parcel);
        }

        public StreamKey[] newArray(int i) {
            return new StreamKey[i];
        }
    }

    public StreamKey(int i, int i2) {
        this(0, i, i2);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || StreamKey.class != obj.getClass()) {
            return false;
        }
        StreamKey streamKey = (StreamKey) obj;
        if (this.c == streamKey.c && this.f == streamKey.f && this.g == streamKey.g) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.c * 31) + this.f) * 31) + this.g;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(35);
        sb.append(this.c);
        sb.append(".");
        sb.append(this.f);
        sb.append(".");
        sb.append(this.g);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.c);
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
    }

    public StreamKey(int i, int i2, int i3) {
        this.c = i;
        this.f = i2;
        this.g = i3;
    }

    public int compareTo(StreamKey streamKey) {
        int i = this.c - streamKey.c;
        if (i != 0) {
            return i;
        }
        int i2 = this.f - streamKey.f;
        return i2 == 0 ? this.g - streamKey.g : i2;
    }

    public StreamKey(Parcel parcel) {
        this.c = parcel.readInt();
        this.f = parcel.readInt();
        this.g = parcel.readInt();
    }
}
