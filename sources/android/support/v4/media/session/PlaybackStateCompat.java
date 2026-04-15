package android.support.v4.media.session;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

@SuppressLint({"BanParcelableUsage"})
public final class PlaybackStateCompat implements Parcelable {
    public static final Parcelable.Creator<PlaybackStateCompat> CREATOR = new a();
    public final int c;
    public final long f;
    public final long g;
    public final float h;
    public final long i;
    public final int j;
    public final CharSequence k;
    public final long l;
    public final ArrayList m;
    public final long n;
    public final Bundle o;

    public class a implements Parcelable.Creator<PlaybackStateCompat> {
        public final Object createFromParcel(Parcel parcel) {
            return new PlaybackStateCompat(parcel);
        }

        public final Object[] newArray(int i) {
            return new PlaybackStateCompat[i];
        }
    }

    public PlaybackStateCompat(int i2, long j2, long j3, float f2, long j4, CharSequence charSequence, long j5, ArrayList arrayList, long j6, Bundle bundle) {
        this.c = i2;
        this.f = j2;
        this.g = j3;
        this.h = f2;
        this.i = j4;
        this.j = 0;
        this.k = charSequence;
        this.l = j5;
        this.m = new ArrayList(arrayList);
        this.n = j6;
        this.o = bundle;
    }

    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "PlaybackState {state=" + this.c + ", position=" + this.f + ", buffered position=" + this.g + ", speed=" + this.h + ", updated=" + this.l + ", actions=" + this.i + ", error code=" + this.j + ", error message=" + this.k + ", custom actions=" + this.m + ", active item id=" + this.n + "}";
    }

    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.c);
        parcel.writeLong(this.f);
        parcel.writeFloat(this.h);
        parcel.writeLong(this.l);
        parcel.writeLong(this.g);
        parcel.writeLong(this.i);
        TextUtils.writeToParcel(this.k, parcel, i2);
        parcel.writeTypedList(this.m);
        parcel.writeLong(this.n);
        parcel.writeBundle(this.o);
        parcel.writeInt(this.j);
    }

    public static final class CustomAction implements Parcelable {
        public static final Parcelable.Creator<CustomAction> CREATOR = new a();
        public final String c;
        public final CharSequence f;
        public final int g;
        public final Bundle h;

        public class a implements Parcelable.Creator<CustomAction> {
            public final Object createFromParcel(Parcel parcel) {
                return new CustomAction(parcel);
            }

            public final Object[] newArray(int i) {
                return new CustomAction[i];
            }
        }

        public CustomAction(String str, CharSequence charSequence, int i, Bundle bundle) {
            this.c = str;
            this.f = charSequence;
            this.g = i;
            this.h = bundle;
        }

        public final int describeContents() {
            return 0;
        }

        public final String toString() {
            return "Action:mName='" + this.f + ", mIcon=" + this.g + ", mExtras=" + this.h;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.c);
            TextUtils.writeToParcel(this.f, parcel, i);
            parcel.writeInt(this.g);
            parcel.writeBundle(this.h);
        }

        public CustomAction(Parcel parcel) {
            this.c = parcel.readString();
            this.f = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.g = parcel.readInt();
            this.h = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        }
    }

    public PlaybackStateCompat(Parcel parcel) {
        this.c = parcel.readInt();
        this.f = parcel.readLong();
        this.h = parcel.readFloat();
        this.l = parcel.readLong();
        this.g = parcel.readLong();
        this.i = parcel.readLong();
        this.k = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.m = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.n = parcel.readLong();
        this.o = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
        this.j = parcel.readInt();
    }
}
