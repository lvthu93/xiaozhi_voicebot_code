package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class ChapterFrame extends Id3Frame {
    public static final Parcelable.Creator<ChapterFrame> CREATOR = new a();
    public final String f;
    public final int g;
    public final int h;
    public final long i;
    public final long j;
    public final Id3Frame[] k;

    public class a implements Parcelable.Creator<ChapterFrame> {
        public ChapterFrame createFromParcel(Parcel parcel) {
            return new ChapterFrame(parcel);
        }

        public ChapterFrame[] newArray(int i) {
            return new ChapterFrame[i];
        }
    }

    public ChapterFrame(String str, int i2, int i3, long j2, long j3, Id3Frame[] id3FrameArr) {
        super("CHAP");
        this.f = str;
        this.g = i2;
        this.h = i3;
        this.i = j2;
        this.j = j3;
        this.k = id3FrameArr;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ChapterFrame.class != obj.getClass()) {
            return false;
        }
        ChapterFrame chapterFrame = (ChapterFrame) obj;
        if (this.g == chapterFrame.g && this.h == chapterFrame.h && this.i == chapterFrame.i && this.j == chapterFrame.j && Util.areEqual(this.f, chapterFrame.f) && Arrays.equals(this.k, chapterFrame.k)) {
            return true;
        }
        return false;
    }

    public Id3Frame getSubFrame(int i2) {
        return this.k[i2];
    }

    public int getSubFrameCount() {
        return this.k.length;
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
        int i3 = (((((((527 + this.g) * 31) + this.h) * 31) + ((int) this.i)) * 31) + ((int) this.j)) * 31;
        String str = this.f;
        if (str != null) {
            i2 = str.hashCode();
        } else {
            i2 = 0;
        }
        return i3 + i2;
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.h);
        parcel.writeLong(this.i);
        parcel.writeLong(this.j);
        Id3Frame[] id3FrameArr = this.k;
        parcel.writeInt(id3FrameArr.length);
        for (Id3Frame writeParcelable : id3FrameArr) {
            parcel.writeParcelable(writeParcelable, 0);
        }
    }

    public ChapterFrame(Parcel parcel) {
        super("CHAP");
        this.f = (String) Util.castNonNull(parcel.readString());
        this.g = parcel.readInt();
        this.h = parcel.readInt();
        this.i = parcel.readLong();
        this.j = parcel.readLong();
        int readInt = parcel.readInt();
        this.k = new Id3Frame[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            this.k[i2] = (Id3Frame) parcel.readParcelable(Id3Frame.class.getClassLoader());
        }
    }
}
