package com.google.android.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.util.Util;

public final class CommentFrame extends Id3Frame {
    public static final Parcelable.Creator<CommentFrame> CREATOR = new a();
    public final String f;
    public final String g;
    public final String h;

    public class a implements Parcelable.Creator<CommentFrame> {
        public CommentFrame createFromParcel(Parcel parcel) {
            return new CommentFrame(parcel);
        }

        public CommentFrame[] newArray(int i) {
            return new CommentFrame[i];
        }
    }

    public CommentFrame(String str, String str2, String str3) {
        super("COMM");
        this.f = str;
        this.g = str2;
        this.h = str3;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || CommentFrame.class != obj.getClass()) {
            return false;
        }
        CommentFrame commentFrame = (CommentFrame) obj;
        if (!Util.areEqual(this.g, commentFrame.g) || !Util.areEqual(this.f, commentFrame.f) || !Util.areEqual(this.h, commentFrame.h)) {
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
        int i;
        int i2;
        int i3 = 0;
        String str = this.f;
        if (str != null) {
            i = str.hashCode();
        } else {
            i = 0;
        }
        int i4 = (527 + i) * 31;
        String str2 = this.g;
        if (str2 != null) {
            i2 = str2.hashCode();
        } else {
            i2 = 0;
        }
        int i5 = (i4 + i2) * 31;
        String str3 = this.h;
        if (str3 != null) {
            i3 = str3.hashCode();
        }
        return i5 + i3;
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public String toString() {
        String str = this.c;
        int c = y2.c(str, 25);
        String str2 = this.f;
        int c2 = y2.c(str2, c);
        String str3 = this.g;
        StringBuilder l = y2.l(y2.c(str3, c2), str, ": language=", str2, ", description=");
        l.append(str3);
        return l.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.c);
        parcel.writeString(this.f);
        parcel.writeString(this.h);
    }

    public CommentFrame(Parcel parcel) {
        super("COMM");
        this.f = (String) Util.castNonNull(parcel.readString());
        this.g = (String) Util.castNonNull(parcel.readString());
        this.h = (String) Util.castNonNull(parcel.readString());
    }
}
