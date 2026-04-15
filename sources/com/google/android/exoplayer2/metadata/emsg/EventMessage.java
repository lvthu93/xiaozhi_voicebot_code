package com.google.android.exoplayer2.metadata.emsg;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;

public final class EventMessage implements Metadata.Entry {
    public static final Parcelable.Creator<EventMessage> CREATOR = new a();
    public static final Format k = new Format.Builder().setSampleMimeType("application/id3").build();
    public static final Format l = new Format.Builder().setSampleMimeType("application/x-scte35").build();
    public final String c;
    public final String f;
    public final long g;
    public final long h;
    public final byte[] i;
    public int j;

    public class a implements Parcelable.Creator<EventMessage> {
        public EventMessage createFromParcel(Parcel parcel) {
            return new EventMessage(parcel);
        }

        public EventMessage[] newArray(int i) {
            return new EventMessage[i];
        }
    }

    public EventMessage(String str, String str2, long j2, long j3, byte[] bArr) {
        this.c = str;
        this.f = str2;
        this.g = j2;
        this.h = j3;
        this.i = bArr;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || EventMessage.class != obj.getClass()) {
            return false;
        }
        EventMessage eventMessage = (EventMessage) obj;
        if (this.g != eventMessage.g || this.h != eventMessage.h || !Util.areEqual(this.c, eventMessage.c) || !Util.areEqual(this.f, eventMessage.f) || !Arrays.equals(this.i, eventMessage.i)) {
            return false;
        }
        return true;
    }

    @Nullable
    public byte[] getWrappedMetadataBytes() {
        if (getWrappedMetadataFormat() != null) {
            return this.i;
        }
        return null;
    }

    @Nullable
    public Format getWrappedMetadataFormat() {
        String str = this.c;
        str.getClass();
        char c2 = 65535;
        switch (str.hashCode()) {
            case -1468477611:
                if (str.equals("urn:scte:scte35:2014:bin")) {
                    c2 = 0;
                    break;
                }
                break;
            case -795945609:
                if (str.equals("https://aomedia.org/emsg/ID3")) {
                    c2 = 1;
                    break;
                }
                break;
            case 1303648457:
                if (str.equals("https://developer.apple.com/streaming/emsg-id3")) {
                    c2 = 2;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                return l;
            case 1:
            case 2:
                return k;
            default:
                return null;
        }
    }

    public int hashCode() {
        int i2;
        if (this.j == 0) {
            int i3 = 0;
            String str = this.c;
            if (str != null) {
                i2 = str.hashCode();
            } else {
                i2 = 0;
            }
            int i4 = (527 + i2) * 31;
            String str2 = this.f;
            if (str2 != null) {
                i3 = str2.hashCode();
            }
            long j2 = this.g;
            long j3 = this.h;
            this.j = Arrays.hashCode(this.i) + ((((((i4 + i3) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + ((int) (j3 ^ (j3 >>> 32)))) * 31);
        }
        return this.j;
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public String toString() {
        String str = this.c;
        int c2 = y2.c(str, 79);
        String str2 = this.f;
        StringBuilder sb = new StringBuilder(y2.c(str2, c2));
        sb.append("EMSG: scheme=");
        sb.append(str);
        sb.append(", id=");
        sb.append(this.h);
        sb.append(", durationMs=");
        sb.append(this.g);
        sb.append(", value=");
        sb.append(str2);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.c);
        parcel.writeString(this.f);
        parcel.writeLong(this.g);
        parcel.writeLong(this.h);
        parcel.writeByteArray(this.i);
    }

    public EventMessage(Parcel parcel) {
        this.c = (String) Util.castNonNull(parcel.readString());
        this.f = (String) Util.castNonNull(parcel.readString());
        this.g = parcel.readLong();
        this.h = parcel.readLong();
        this.i = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
