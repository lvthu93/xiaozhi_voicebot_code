package com.google.android.exoplayer2.metadata.mp4;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class MdtaMetadataEntry implements Metadata.Entry {
    public static final Parcelable.Creator<MdtaMetadataEntry> CREATOR = new a();
    public final String c;
    public final byte[] f;
    public final int g;
    public final int h;

    public class a implements Parcelable.Creator<MdtaMetadataEntry> {
        public MdtaMetadataEntry createFromParcel(Parcel parcel) {
            return new MdtaMetadataEntry(parcel);
        }

        public MdtaMetadataEntry[] newArray(int i) {
            return new MdtaMetadataEntry[i];
        }
    }

    public MdtaMetadataEntry(String str, byte[] bArr, int i, int i2) {
        this.c = str;
        this.f = bArr;
        this.g = i;
        this.h = i2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MdtaMetadataEntry.class != obj.getClass()) {
            return false;
        }
        MdtaMetadataEntry mdtaMetadataEntry = (MdtaMetadataEntry) obj;
        if (!this.c.equals(mdtaMetadataEntry.c) || !Arrays.equals(this.f, mdtaMetadataEntry.f) || this.g != mdtaMetadataEntry.g || this.h != mdtaMetadataEntry.h) {
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
        return ((((Arrays.hashCode(this.f) + ((this.c.hashCode() + 527) * 31)) * 31) + this.g) * 31) + this.h;
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public String toString() {
        String valueOf = String.valueOf(this.c);
        return valueOf.length() != 0 ? "mdta: key=".concat(valueOf) : new String("mdta: key=");
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.c);
        parcel.writeByteArray(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
    }

    public MdtaMetadataEntry(Parcel parcel) {
        this.c = (String) Util.castNonNull(parcel.readString());
        this.f = (byte[]) Util.castNonNull(parcel.createByteArray());
        this.g = parcel.readInt();
        this.h = parcel.readInt();
    }
}
