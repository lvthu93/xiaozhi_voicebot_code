package com.google.android.exoplayer2.metadata.flac;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class PictureFrame implements Metadata.Entry {
    public static final Parcelable.Creator<PictureFrame> CREATOR = new a();
    public final int c;
    public final String f;
    public final String g;
    public final int h;
    public final int i;
    public final int j;
    public final int k;
    public final byte[] l;

    public class a implements Parcelable.Creator<PictureFrame> {
        public PictureFrame createFromParcel(Parcel parcel) {
            return new PictureFrame(parcel);
        }

        public PictureFrame[] newArray(int i) {
            return new PictureFrame[i];
        }
    }

    public PictureFrame(int i2, String str, String str2, int i3, int i4, int i5, int i6, byte[] bArr) {
        this.c = i2;
        this.f = str;
        this.g = str2;
        this.h = i3;
        this.i = i4;
        this.j = i5;
        this.k = i6;
        this.l = bArr;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PictureFrame.class != obj.getClass()) {
            return false;
        }
        PictureFrame pictureFrame = (PictureFrame) obj;
        if (this.c == pictureFrame.c && this.f.equals(pictureFrame.f) && this.g.equals(pictureFrame.g) && this.h == pictureFrame.h && this.i == pictureFrame.i && this.j == pictureFrame.j && this.k == pictureFrame.k && Arrays.equals(this.l, pictureFrame.l)) {
            return true;
        }
        return false;
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
        int hashCode = this.f.hashCode();
        int hashCode2 = this.g.hashCode();
        return Arrays.hashCode(this.l) + ((((((((((hashCode2 + ((hashCode + ((527 + this.c) * 31)) * 31)) * 31) + this.h) * 31) + this.i) * 31) + this.j) * 31) + this.k) * 31);
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public String toString() {
        String str = this.f;
        int c2 = y2.c(str, 32);
        String str2 = this.g;
        StringBuilder sb = new StringBuilder(y2.c(str2, c2));
        sb.append("Picture: mimeType=");
        sb.append(str);
        sb.append(", description=");
        sb.append(str2);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.c);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeInt(this.h);
        parcel.writeInt(this.i);
        parcel.writeInt(this.j);
        parcel.writeInt(this.k);
        parcel.writeByteArray(this.l);
    }

    public PictureFrame(Parcel parcel) {
        this.c = parcel.readInt();
        this.f = (String) Util.castNonNull(parcel.readString());
        this.g = (String) Util.castNonNull(parcel.readString());
        this.h = parcel.readInt();
        this.i = parcel.readInt();
        this.j = parcel.readInt();
        this.k = parcel.readInt();
        this.l = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
