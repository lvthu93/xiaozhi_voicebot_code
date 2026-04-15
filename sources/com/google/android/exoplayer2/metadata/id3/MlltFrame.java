package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class MlltFrame extends Id3Frame {
    public static final Parcelable.Creator<MlltFrame> CREATOR = new a();
    public final int f;
    public final int g;
    public final int h;
    public final int[] i;
    public final int[] j;

    public class a implements Parcelable.Creator<MlltFrame> {
        public MlltFrame createFromParcel(Parcel parcel) {
            return new MlltFrame(parcel);
        }

        public MlltFrame[] newArray(int i) {
            return new MlltFrame[i];
        }
    }

    public MlltFrame(int i2, int i3, int i4, int[] iArr, int[] iArr2) {
        super("MLLT");
        this.f = i2;
        this.g = i3;
        this.h = i4;
        this.i = iArr;
        this.j = iArr2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MlltFrame.class != obj.getClass()) {
            return false;
        }
        MlltFrame mlltFrame = (MlltFrame) obj;
        if (this.f == mlltFrame.f && this.g == mlltFrame.g && this.h == mlltFrame.h && Arrays.equals(this.i, mlltFrame.i) && Arrays.equals(this.j, mlltFrame.j)) {
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
        int hashCode = Arrays.hashCode(this.i);
        return Arrays.hashCode(this.j) + ((hashCode + ((((((527 + this.f) * 31) + this.g) * 31) + this.h) * 31)) * 31);
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
        parcel.writeIntArray(this.i);
        parcel.writeIntArray(this.j);
    }

    public MlltFrame(Parcel parcel) {
        super("MLLT");
        this.f = parcel.readInt();
        this.g = parcel.readInt();
        this.h = parcel.readInt();
        this.i = (int[]) Util.castNonNull(parcel.createIntArray());
        this.j = (int[]) Util.castNonNull(parcel.createIntArray());
    }
}
