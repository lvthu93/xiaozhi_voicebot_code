package com.google.android.exoplayer2.source;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import java.util.Arrays;

public final class TrackGroup implements Parcelable {
    public static final Parcelable.Creator<TrackGroup> CREATOR = new a();
    public final int c;
    public final Format[] f;
    public int g;

    public class a implements Parcelable.Creator<TrackGroup> {
        public TrackGroup createFromParcel(Parcel parcel) {
            return new TrackGroup(parcel);
        }

        public TrackGroup[] newArray(int i) {
            return new TrackGroup[i];
        }
    }

    public TrackGroup(Format... formatArr) {
        int i = 1;
        Assertions.checkState(formatArr.length > 0);
        this.f = formatArr;
        this.c = formatArr.length;
        String str = formatArr[0].g;
        str = (str == null || str.equals("und")) ? "" : str;
        int i2 = formatArr[0].i | 16384;
        while (i < formatArr.length) {
            String str2 = formatArr[i].g;
            if (!str.equals((str2 == null || str2.equals("und")) ? "" : str2)) {
                a("languages", i, formatArr[0].g, formatArr[i].g);
                return;
            } else if (i2 != (formatArr[i].i | 16384)) {
                a("role flags", i, Integer.toBinaryString(formatArr[0].i), Integer.toBinaryString(formatArr[i].i));
                return;
            } else {
                i++;
            }
        }
    }

    public static void a(String str, int i, @Nullable String str2, @Nullable String str3) {
        StringBuilder l = y2.l(y2.c(str3, y2.c(str2, str.length() + 78)), "Different ", str, " combined in one TrackGroup: '", str2);
        l.append("' (track 0) and '");
        l.append(str3);
        l.append("' (track ");
        l.append(i);
        l.append(")");
        Log.e("TrackGroup", "", new IllegalStateException(l.toString()));
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || TrackGroup.class != obj.getClass()) {
            return false;
        }
        TrackGroup trackGroup = (TrackGroup) obj;
        if (this.c != trackGroup.c || !Arrays.equals(this.f, trackGroup.f)) {
            return false;
        }
        return true;
    }

    public Format getFormat(int i) {
        return this.f[i];
    }

    public int hashCode() {
        if (this.g == 0) {
            this.g = 527 + Arrays.hashCode(this.f);
        }
        return this.g;
    }

    public int indexOf(Format format) {
        int i = 0;
        while (true) {
            Format[] formatArr = this.f;
            if (i >= formatArr.length) {
                return -1;
            }
            if (format == formatArr[i]) {
                return i;
            }
            i++;
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = this.c;
        parcel.writeInt(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            parcel.writeParcelable(this.f[i3], 0);
        }
    }

    public TrackGroup(Parcel parcel) {
        int readInt = parcel.readInt();
        this.c = readInt;
        this.f = new Format[readInt];
        for (int i = 0; i < this.c; i++) {
            this.f[i] = (Format) parcel.readParcelable(Format.class.getClassLoader());
        }
    }
}
