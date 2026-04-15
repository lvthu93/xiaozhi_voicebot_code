package android.support.v4.media.session;

import android.annotation.SuppressLint;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.versionedparcelable.VersionedParcelable;

public final class MediaSessionCompat {

    @SuppressLint({"BanParcelableUsage"})
    public static final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new a();
        public final ResultReceiver c;

        public class a implements Parcelable.Creator<ResultReceiverWrapper> {
            public final Object createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            public final Object[] newArray(int i) {
                return new ResultReceiverWrapper[i];
            }
        }

        public ResultReceiverWrapper(Parcel parcel) {
            this.c = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public final int describeContents() {
            return 0;
        }

        public final void writeToParcel(Parcel parcel, int i) {
            this.c.writeToParcel(parcel, i);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new a();
        public final Object c = new Object();
        public final Object f;
        @GuardedBy("mLock")
        public b g;
        @GuardedBy("mLock")
        public VersionedParcelable h;

        public class a implements Parcelable.Creator<Token> {
            public final Object createFromParcel(Parcel parcel) {
                return new Token(parcel.readParcelable((ClassLoader) null), (b) null);
            }

            public final Object[] newArray(int i) {
                return new Token[i];
            }
        }

        public Token(Parcelable parcelable, b bVar) {
            this.f = parcelable;
            this.g = bVar;
            this.h = null;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public static Token a(Parcelable parcelable, b bVar) {
            if (parcelable == null) {
                return null;
            }
            if (parcelable instanceof MediaSession.Token) {
                return new Token(parcelable, bVar);
            }
            throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final b b() {
            b bVar;
            synchronized (this.c) {
                bVar = this.g;
            }
            return bVar;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public final void c(b bVar) {
            synchronized (this.c) {
                this.g = bVar;
            }
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public final void d(VersionedParcelable versionedParcelable) {
            synchronized (this.c) {
                this.h = versionedParcelable;
            }
        }

        public final int describeContents() {
            return 0;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            Object obj2 = this.f;
            if (obj2 != null) {
                Object obj3 = token.f;
                if (obj3 == null) {
                    return false;
                }
                return obj2.equals(obj3);
            } else if (token.f == null) {
                return true;
            } else {
                return false;
            }
        }

        public final int hashCode() {
            Object obj = this.f;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable((Parcelable) this.f, i);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static void a(@Nullable Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MediaSessionCompat.class.getClassLoader());
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    public static final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new a();
        public final MediaDescriptionCompat c;
        public final long f;

        public class a implements Parcelable.Creator<QueueItem> {
            public final Object createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            public final Object[] newArray(int i) {
                return new QueueItem[i];
            }
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null");
            } else if (j != -1) {
                this.c = mediaDescriptionCompat;
                this.f = j;
            } else {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
        }

        public final int describeContents() {
            return 0;
        }

        public final String toString() {
            return "MediaSession.QueueItem {Description=" + this.c + ", Id=" + this.f + " }";
        }

        public final void writeToParcel(Parcel parcel, int i) {
            this.c.writeToParcel(parcel, i);
            parcel.writeLong(this.f);
        }

        public QueueItem(Parcel parcel) {
            this.c = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.f = parcel.readLong();
        }
    }
}
