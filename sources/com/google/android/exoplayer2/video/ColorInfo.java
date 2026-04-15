package com.google.android.exoplayer2.video;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class ColorInfo implements Parcelable {
    public static final Parcelable.Creator<ColorInfo> CREATOR = new a();
    public final int c;
    public final int f;
    public final int g;
    @Nullable
    public final byte[] h;
    public int i;

    public class a implements Parcelable.Creator<ColorInfo> {
        public ColorInfo createFromParcel(Parcel parcel) {
            return new ColorInfo(parcel);
        }

        public ColorInfo[] newArray(int i) {
            return new ColorInfo[i];
        }
    }

    public ColorInfo(int i2, int i3, int i4, @Nullable byte[] bArr) {
        this.c = i2;
        this.f = i3;
        this.g = i4;
        this.h = bArr;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ColorInfo.class != obj.getClass()) {
            return false;
        }
        ColorInfo colorInfo = (ColorInfo) obj;
        if (this.c == colorInfo.c && this.f == colorInfo.f && this.g == colorInfo.g && Arrays.equals(this.h, colorInfo.h)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.i == 0) {
            this.i = Arrays.hashCode(this.h) + ((((((527 + this.c) * 31) + this.f) * 31) + this.g) * 31);
        }
        return this.i;
    }

    public String toString() {
        boolean z = this.h != null;
        StringBuilder sb = new StringBuilder(55);
        sb.append("ColorInfo(");
        sb.append(this.c);
        sb.append(", ");
        sb.append(this.f);
        sb.append(", ");
        sb.append(this.g);
        sb.append(", ");
        sb.append(z);
        sb.append(")");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        boolean z;
        parcel.writeInt(this.c);
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
        byte[] bArr = this.h;
        if (bArr != null) {
            z = true;
        } else {
            z = false;
        }
        Util.writeBoolean(parcel, z);
        if (bArr != null) {
            parcel.writeByteArray(bArr);
        }
    }

    public ColorInfo(Parcel parcel) {
        this.c = parcel.readInt();
        this.f = parcel.readInt();
        this.g = parcel.readInt();
        this.h = Util.readBoolean(parcel) ? parcel.createByteArray() : null;
    }
}
