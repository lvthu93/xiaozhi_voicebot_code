package android.support.v4.media;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint({"BanParcelableUsage"})
public final class MediaDescriptionCompat implements Parcelable {
    public static final Parcelable.Creator<MediaDescriptionCompat> CREATOR = new a();
    public final String c;
    public final CharSequence f;
    public final CharSequence g;
    public final CharSequence h;
    public final Bitmap i;
    public final Uri j;
    public final Bundle k;
    public final Uri l;
    public MediaDescription m;

    public class a implements Parcelable.Creator<MediaDescriptionCompat> {
        public final Object createFromParcel(Parcel parcel) {
            return MediaDescriptionCompat.a(MediaDescription.CREATOR.createFromParcel(parcel));
        }

        public final Object[] newArray(int i) {
            return new MediaDescriptionCompat[i];
        }
    }

    public MediaDescriptionCompat() {
        throw null;
    }

    public MediaDescriptionCompat(String str, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Bitmap bitmap, Uri uri, Bundle bundle, Uri uri2) {
        this.c = str;
        this.f = charSequence;
        this.g = charSequence2;
        this.h = charSequence3;
        this.i = bitmap;
        this.j = uri;
        this.k = bundle;
        this.l = uri2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.support.v4.media.MediaDescriptionCompat a(java.lang.Object r14) {
        /*
            r0 = 0
            if (r14 == 0) goto L_0x0068
            int r1 = android.os.Build.VERSION.SDK_INT
            android.media.MediaDescription r14 = (android.media.MediaDescription) r14
            java.lang.String r3 = r14.getMediaId()
            java.lang.CharSequence r4 = r14.getTitle()
            java.lang.CharSequence r5 = r14.getSubtitle()
            java.lang.CharSequence r6 = r14.getDescription()
            android.graphics.Bitmap r7 = r14.getIconBitmap()
            android.net.Uri r8 = r14.getIconUri()
            android.os.Bundle r2 = r14.getExtras()
            if (r2 == 0) goto L_0x002e
            android.support.v4.media.session.MediaSessionCompat.a(r2)
            r2.isEmpty()     // Catch:{ BadParcelableException -> 0x002c }
            goto L_0x002e
        L_0x002c:
            r2 = r0
        L_0x002e:
            java.lang.String r9 = "android.support.v4.media.description.MEDIA_URI"
            if (r2 == 0) goto L_0x0039
            android.os.Parcelable r10 = r2.getParcelable(r9)
            android.net.Uri r10 = (android.net.Uri) r10
            goto L_0x003a
        L_0x0039:
            r10 = r0
        L_0x003a:
            if (r10 == 0) goto L_0x0053
            java.lang.String r11 = "android.support.v4.media.description.NULL_BUNDLE_FLAG"
            boolean r12 = r2.containsKey(r11)
            if (r12 == 0) goto L_0x004d
            int r12 = r2.size()
            r13 = 2
            if (r12 != r13) goto L_0x004d
            r9 = r0
            goto L_0x0054
        L_0x004d:
            r2.remove(r9)
            r2.remove(r11)
        L_0x0053:
            r9 = r2
        L_0x0054:
            if (r10 == 0) goto L_0x0057
            goto L_0x0060
        L_0x0057:
            r2 = 23
            if (r1 < r2) goto L_0x005f
            android.net.Uri r0 = r14.getMediaUri()
        L_0x005f:
            r10 = r0
        L_0x0060:
            android.support.v4.media.MediaDescriptionCompat r0 = new android.support.v4.media.MediaDescriptionCompat
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10)
            r0.m = r14
        L_0x0068:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.MediaDescriptionCompat.a(java.lang.Object):android.support.v4.media.MediaDescriptionCompat");
    }

    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return this.f + ", " + this.g + ", " + this.h;
    }

    public final void writeToParcel(Parcel parcel, int i2) {
        int i3 = Build.VERSION.SDK_INT;
        MediaDescription mediaDescription = this.m;
        if (mediaDescription == null) {
            MediaDescription.Builder builder = new MediaDescription.Builder();
            builder.setMediaId(this.c);
            builder.setTitle(this.f);
            builder.setSubtitle(this.g);
            builder.setDescription(this.h);
            builder.setIconBitmap(this.i);
            builder.setIconUri(this.j);
            Uri uri = this.l;
            Bundle bundle = this.k;
            if (i3 < 23 && uri != null) {
                if (bundle == null) {
                    bundle = new Bundle();
                    bundle.putBoolean("android.support.v4.media.description.NULL_BUNDLE_FLAG", true);
                }
                bundle.putParcelable("android.support.v4.media.description.MEDIA_URI", uri);
            }
            builder.setExtras(bundle);
            if (i3 >= 23) {
                builder.setMediaUri(uri);
            }
            mediaDescription = builder.build();
            this.m = mediaDescription;
        }
        mediaDescription.writeToParcel(parcel, i2);
    }
}
