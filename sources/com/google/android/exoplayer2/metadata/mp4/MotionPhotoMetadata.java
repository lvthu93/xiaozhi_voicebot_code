package com.google.android.exoplayer2.metadata.mp4;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;

public final class MotionPhotoMetadata implements Metadata.Entry {
    public static final Parcelable.Creator<MotionPhotoMetadata> CREATOR = new a();
    public final long c;
    public final long f;
    public final long g;
    public final long h;
    public final long i;

    public class a implements Parcelable.Creator<MotionPhotoMetadata> {
        public MotionPhotoMetadata createFromParcel(Parcel parcel) {
            return new MotionPhotoMetadata(parcel);
        }

        public MotionPhotoMetadata[] newArray(int i) {
            return new MotionPhotoMetadata[i];
        }
    }

    public MotionPhotoMetadata(long j, long j2, long j3, long j4, long j5) {
        this.c = j;
        this.f = j2;
        this.g = j3;
        this.h = j4;
        this.i = j5;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || MotionPhotoMetadata.class != obj.getClass()) {
            return false;
        }
        MotionPhotoMetadata motionPhotoMetadata = (MotionPhotoMetadata) obj;
        if (this.c == motionPhotoMetadata.c && this.f == motionPhotoMetadata.f && this.g == motionPhotoMetadata.g && this.h == motionPhotoMetadata.h && this.i == motionPhotoMetadata.i) {
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
        long j = this.c;
        long j2 = this.f;
        int i2 = (int) (j2 ^ (j2 >>> 32));
        long j3 = this.g;
        int i3 = (int) (j3 ^ (j3 >>> 32));
        long j4 = this.h;
        int i4 = (int) (j4 ^ (j4 >>> 32));
        long j5 = this.i;
        return ((int) ((j5 >>> 32) ^ j5)) + ((i4 + ((i3 + ((i2 + ((((int) (j ^ (j >>> 32))) + 527) * 31)) * 31)) * 31)) * 31);
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(218);
        sb.append("Motion photo metadata: photoStartPosition=");
        sb.append(this.c);
        sb.append(", photoSize=");
        sb.append(this.f);
        sb.append(", photoPresentationTimestampUs=");
        sb.append(this.g);
        sb.append(", videoStartPosition=");
        sb.append(this.h);
        sb.append(", videoSize=");
        sb.append(this.i);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeLong(this.c);
        parcel.writeLong(this.f);
        parcel.writeLong(this.g);
        parcel.writeLong(this.h);
        parcel.writeLong(this.i);
    }

    public MotionPhotoMetadata(Parcel parcel) {
        this.c = parcel.readLong();
        this.f = parcel.readLong();
        this.g = parcel.readLong();
        this.h = parcel.readLong();
        this.i = parcel.readLong();
    }
}
