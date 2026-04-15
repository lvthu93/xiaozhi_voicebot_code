package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class ChapterTocFrame extends Id3Frame {
    public static final Parcelable.Creator<ChapterTocFrame> CREATOR = new a();
    public final String f;
    public final boolean g;
    public final boolean h;
    public final String[] i;
    public final Id3Frame[] j;

    public class a implements Parcelable.Creator<ChapterTocFrame> {
        public ChapterTocFrame createFromParcel(Parcel parcel) {
            return new ChapterTocFrame(parcel);
        }

        public ChapterTocFrame[] newArray(int i) {
            return new ChapterTocFrame[i];
        }
    }

    public ChapterTocFrame(String str, boolean z, boolean z2, String[] strArr, Id3Frame[] id3FrameArr) {
        super("CTOC");
        this.f = str;
        this.g = z;
        this.h = z2;
        this.i = strArr;
        this.j = id3FrameArr;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ChapterTocFrame.class != obj.getClass()) {
            return false;
        }
        ChapterTocFrame chapterTocFrame = (ChapterTocFrame) obj;
        if (this.g != chapterTocFrame.g || this.h != chapterTocFrame.h || !Util.areEqual(this.f, chapterTocFrame.f) || !Arrays.equals(this.i, chapterTocFrame.i) || !Arrays.equals(this.j, chapterTocFrame.j)) {
            return false;
        }
        return true;
    }

    public Id3Frame getSubFrame(int i2) {
        return this.j[i2];
    }

    public int getSubFrameCount() {
        return this.j.length;
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
        int i3 = (((true + (this.g ? 1 : 0)) * 31) + (this.h ? 1 : 0)) * 31;
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
        parcel.writeByte(this.g ? (byte) 1 : 0);
        parcel.writeByte(this.h ? (byte) 1 : 0);
        parcel.writeStringArray(this.i);
        Id3Frame[] id3FrameArr = this.j;
        parcel.writeInt(id3FrameArr.length);
        for (Id3Frame writeParcelable : id3FrameArr) {
            parcel.writeParcelable(writeParcelable, 0);
        }
    }

    public ChapterTocFrame(Parcel parcel) {
        super("CTOC");
        this.f = (String) Util.castNonNull(parcel.readString());
        boolean z = true;
        this.g = parcel.readByte() != 0;
        this.h = parcel.readByte() == 0 ? false : z;
        this.i = (String[]) Util.castNonNull(parcel.createStringArray());
        int readInt = parcel.readInt();
        this.j = new Id3Frame[readInt];
        for (int i2 = 0; i2 < readInt; i2++) {
            this.j[i2] = (Id3Frame) parcel.readParcelable(Id3Frame.class.getClassLoader());
        }
    }
}
