package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class ApicFrame extends Id3Frame {
    public static final Parcelable.Creator<ApicFrame> CREATOR = new a();
    public final String f;
    @Nullable
    public final String g;
    public final int h;
    public final byte[] i;

    public class a implements Parcelable.Creator<ApicFrame> {
        public ApicFrame createFromParcel(Parcel parcel) {
            return new ApicFrame(parcel);
        }

        public ApicFrame[] newArray(int i) {
            return new ApicFrame[i];
        }
    }

    public ApicFrame(String str, @Nullable String str2, int i2, byte[] bArr) {
        super("APIC");
        this.f = str;
        this.g = str2;
        this.h = i2;
        this.i = bArr;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ApicFrame.class != obj.getClass()) {
            return false;
        }
        ApicFrame apicFrame = (ApicFrame) obj;
        if (this.h != apicFrame.h || !Util.areEqual(this.f, apicFrame.f) || !Util.areEqual(this.g, apicFrame.g) || !Arrays.equals(this.i, apicFrame.i)) {
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
        int i3 = (527 + this.h) * 31;
        int i4 = 0;
        String str = this.f;
        if (str != null) {
            i2 = str.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i3 + i2) * 31;
        String str2 = this.g;
        if (str2 != null) {
            i4 = str2.hashCode();
        }
        return Arrays.hashCode(this.i) + ((i5 + i4) * 31);
    }

    public void populateMediaMetadata(MediaMetadata.Builder builder) {
        builder.setArtworkData(this.i);
    }

    public String toString() {
        String str = this.c;
        int c = y2.c(str, 25);
        String str2 = this.f;
        int c2 = y2.c(str2, c);
        String str3 = this.g;
        StringBuilder l = y2.l(y2.c(str3, c2), str, ": mimeType=", str2, ", description=");
        l.append(str3);
        return l.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeInt(this.h);
        parcel.writeByteArray(this.i);
    }

    public ApicFrame(Parcel parcel) {
        super("APIC");
        this.f = (String) Util.castNonNull(parcel.readString());
        this.g = parcel.readString();
        this.h = parcel.readInt();
        this.i = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
