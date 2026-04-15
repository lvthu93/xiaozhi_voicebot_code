package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class GeobFrame extends Id3Frame {
    public static final Parcelable.Creator<GeobFrame> CREATOR = new a();
    public final String f;
    public final String g;
    public final String h;
    public final byte[] i;

    public class a implements Parcelable.Creator<GeobFrame> {
        public GeobFrame createFromParcel(Parcel parcel) {
            return new GeobFrame(parcel);
        }

        public GeobFrame[] newArray(int i) {
            return new GeobFrame[i];
        }
    }

    public GeobFrame(String str, String str2, String str3, byte[] bArr) {
        super("GEOB");
        this.f = str;
        this.g = str2;
        this.h = str3;
        this.i = bArr;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || GeobFrame.class != obj.getClass()) {
            return false;
        }
        GeobFrame geobFrame = (GeobFrame) obj;
        if (!Util.areEqual(this.f, geobFrame.f) || !Util.areEqual(this.g, geobFrame.g) || !Util.areEqual(this.h, geobFrame.h) || !Arrays.equals(this.i, geobFrame.i)) {
            return false;
        }
        return true;
    }

    @Nullable
    public /* bridge */ /* synthetic */ byte[] getWrappedMetadataBytes() {
        return q6.a(this);
    }

    @Nullable
    public /* bridge */ /* synthetic */ Format getWrappedMetadataFormat() {
        return q6.b(this);
    }

    public int hashCode() {
        int i2;
        int i3;
        int i4 = 0;
        String str = this.f;
        if (str != null) {
            i2 = str.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (527 + i2) * 31;
        String str2 = this.g;
        if (str2 != null) {
            i3 = str2.hashCode();
        } else {
            i3 = 0;
        }
        int i6 = (i5 + i3) * 31;
        String str3 = this.h;
        if (str3 != null) {
            i4 = str3.hashCode();
        }
        return Arrays.hashCode(this.i) + ((i6 + i4) * 31);
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public String toString() {
        String str = this.c;
        int c = y2.c(str, 36);
        String str2 = this.f;
        int c2 = y2.c(str2, c);
        String str3 = this.g;
        int c3 = y2.c(str3, c2);
        String str4 = this.h;
        StringBuilder l = y2.l(y2.c(str4, c3), str, ": mimeType=", str2, ", filename=");
        l.append(str3);
        l.append(", description=");
        l.append(str4);
        return l.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        parcel.writeByteArray(this.i);
    }

    public GeobFrame(Parcel parcel) {
        super("GEOB");
        this.f = (String) Util.castNonNull(parcel.readString());
        this.g = (String) Util.castNonNull(parcel.readString());
        this.h = (String) Util.castNonNull(parcel.readString());
        this.i = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
