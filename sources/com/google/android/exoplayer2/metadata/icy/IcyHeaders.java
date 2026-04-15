package com.google.android.exoplayer2.metadata.icy;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

public final class IcyHeaders implements Metadata.Entry {
    public static final Parcelable.Creator<IcyHeaders> CREATOR = new a();
    public final int c;
    @Nullable
    public final String f;
    @Nullable
    public final String g;
    @Nullable
    public final String h;
    public final boolean i;
    public final int j;

    public class a implements Parcelable.Creator<IcyHeaders> {
        public IcyHeaders createFromParcel(Parcel parcel) {
            return new IcyHeaders(parcel);
        }

        public IcyHeaders[] newArray(int i) {
            return new IcyHeaders[i];
        }
    }

    public IcyHeaders(int i2, @Nullable String str, @Nullable String str2, @Nullable String str3, boolean z, int i3) {
        Assertions.checkArgument(i3 == -1 || i3 > 0);
        this.c = i2;
        this.f = str;
        this.g = str2;
        this.h = str3;
        this.i = z;
        this.j = i3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0080  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00fd  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.exoplayer2.metadata.icy.IcyHeaders parse(java.util.Map<java.lang.String, java.util.List<java.lang.String>> r13) {
        /*
            java.lang.String r0 = "Invalid metadata interval: "
            java.lang.String r1 = "icy-br"
            java.lang.Object r1 = r13.get(r1)
            java.util.List r1 = (java.util.List) r1
            java.lang.String r2 = "IcyHeaders"
            r3 = 1
            r4 = 0
            r5 = -1
            if (r1 == 0) goto L_0x005f
            java.lang.Object r1 = r1.get(r4)
            java.lang.String r1 = (java.lang.String) r1
            int r6 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x0041 }
            int r6 = r6 * 1000
            if (r6 <= 0) goto L_0x0021
            r1 = 1
            goto L_0x003d
        L_0x0021:
            java.lang.String r7 = "Invalid bitrate: "
            java.lang.String r8 = java.lang.String.valueOf(r1)     // Catch:{ NumberFormatException -> 0x003f }
            int r9 = r8.length()     // Catch:{ NumberFormatException -> 0x003f }
            if (r9 == 0) goto L_0x0032
            java.lang.String r7 = r7.concat(r8)     // Catch:{ NumberFormatException -> 0x003f }
            goto L_0x0038
        L_0x0032:
            java.lang.String r8 = new java.lang.String     // Catch:{ NumberFormatException -> 0x003f }
            r8.<init>(r7)     // Catch:{ NumberFormatException -> 0x003f }
            r7 = r8
        L_0x0038:
            com.google.android.exoplayer2.util.Log.w(r2, r7)     // Catch:{ NumberFormatException -> 0x003f }
            r1 = 0
            r6 = -1
        L_0x003d:
            r7 = r6
            goto L_0x0061
        L_0x003f:
            goto L_0x0043
        L_0x0041:
            r6 = -1
        L_0x0043:
            java.lang.String r1 = java.lang.String.valueOf(r1)
            int r7 = r1.length()
            java.lang.String r8 = "Invalid bitrate header: "
            if (r7 == 0) goto L_0x0054
            java.lang.String r1 = r8.concat(r1)
            goto L_0x0059
        L_0x0054:
            java.lang.String r1 = new java.lang.String
            r1.<init>(r8)
        L_0x0059:
            com.google.android.exoplayer2.util.Log.w(r2, r1)
            r7 = r6
            r1 = 0
            goto L_0x0061
        L_0x005f:
            r1 = 0
            r7 = -1
        L_0x0061:
            java.lang.String r6 = "icy-genre"
            java.lang.Object r6 = r13.get(r6)
            java.util.List r6 = (java.util.List) r6
            r8 = 0
            if (r6 == 0) goto L_0x0075
            java.lang.Object r1 = r6.get(r4)
            java.lang.String r1 = (java.lang.String) r1
            r9 = r1
            r1 = 1
            goto L_0x0076
        L_0x0075:
            r9 = r8
        L_0x0076:
            java.lang.String r6 = "icy-name"
            java.lang.Object r6 = r13.get(r6)
            java.util.List r6 = (java.util.List) r6
            if (r6 == 0) goto L_0x0089
            java.lang.Object r1 = r6.get(r4)
            java.lang.String r1 = (java.lang.String) r1
            r10 = r1
            r1 = 1
            goto L_0x008a
        L_0x0089:
            r10 = r8
        L_0x008a:
            java.lang.String r6 = "icy-url"
            java.lang.Object r6 = r13.get(r6)
            java.util.List r6 = (java.util.List) r6
            if (r6 == 0) goto L_0x009d
            java.lang.Object r1 = r6.get(r4)
            java.lang.String r1 = (java.lang.String) r1
            r11 = r1
            r1 = 1
            goto L_0x009e
        L_0x009d:
            r11 = r8
        L_0x009e:
            java.lang.String r6 = "icy-pub"
            java.lang.Object r6 = r13.get(r6)
            java.util.List r6 = (java.util.List) r6
            if (r6 == 0) goto L_0x00b7
            java.lang.Object r1 = r6.get(r4)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r6 = "1"
            boolean r1 = r1.equals(r6)
            r12 = r1
            r1 = 1
            goto L_0x00b8
        L_0x00b7:
            r12 = 0
        L_0x00b8:
            java.lang.String r6 = "icy-metaint"
            java.lang.Object r13 = r13.get(r6)
            java.util.List r13 = (java.util.List) r13
            if (r13 == 0) goto L_0x0105
            java.lang.Object r13 = r13.get(r4)
            java.lang.String r13 = (java.lang.String) r13
            int r4 = java.lang.Integer.parseInt(r13)     // Catch:{ NumberFormatException -> 0x00ed }
            if (r4 <= 0) goto L_0x00d0
            r5 = r4
            goto L_0x00e8
        L_0x00d0:
            java.lang.String r3 = java.lang.String.valueOf(r13)     // Catch:{ NumberFormatException -> 0x00ea }
            int r6 = r3.length()     // Catch:{ NumberFormatException -> 0x00ea }
            if (r6 == 0) goto L_0x00df
            java.lang.String r3 = r0.concat(r3)     // Catch:{ NumberFormatException -> 0x00ea }
            goto L_0x00e4
        L_0x00df:
            java.lang.String r3 = new java.lang.String     // Catch:{ NumberFormatException -> 0x00ea }
            r3.<init>(r0)     // Catch:{ NumberFormatException -> 0x00ea }
        L_0x00e4:
            com.google.android.exoplayer2.util.Log.w(r2, r3)     // Catch:{ NumberFormatException -> 0x00ea }
            r3 = r1
        L_0x00e8:
            r1 = r3
            goto L_0x0105
        L_0x00ea:
            r5 = r4
            goto L_0x00ee
        L_0x00ed:
        L_0x00ee:
            java.lang.String r13 = java.lang.String.valueOf(r13)
            int r3 = r13.length()
            if (r3 == 0) goto L_0x00fd
            java.lang.String r13 = r0.concat(r13)
            goto L_0x0102
        L_0x00fd:
            java.lang.String r13 = new java.lang.String
            r13.<init>(r0)
        L_0x0102:
            com.google.android.exoplayer2.util.Log.w(r2, r13)
        L_0x0105:
            if (r1 == 0) goto L_0x0113
            com.google.android.exoplayer2.metadata.icy.IcyHeaders r13 = new com.google.android.exoplayer2.metadata.icy.IcyHeaders
            r6 = r13
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r5
            r6.<init>(r7, r8, r9, r10, r11, r12)
            r8 = r13
        L_0x0113:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.metadata.icy.IcyHeaders.parse(java.util.Map):com.google.android.exoplayer2.metadata.icy.IcyHeaders");
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || IcyHeaders.class != obj.getClass()) {
            return false;
        }
        IcyHeaders icyHeaders = (IcyHeaders) obj;
        if (this.c != icyHeaders.c || !Util.areEqual(this.f, icyHeaders.f) || !Util.areEqual(this.g, icyHeaders.g) || !Util.areEqual(this.h, icyHeaders.h) || this.i != icyHeaders.i || this.j != icyHeaders.j) {
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
        int i3;
        int i4 = (527 + this.c) * 31;
        int i5 = 0;
        String str = this.f;
        if (str != null) {
            i2 = str.hashCode();
        } else {
            i2 = 0;
        }
        int i6 = (i4 + i2) * 31;
        String str2 = this.g;
        if (str2 != null) {
            i3 = str2.hashCode();
        } else {
            i3 = 0;
        }
        int i7 = (i6 + i3) * 31;
        String str3 = this.h;
        if (str3 != null) {
            i5 = str3.hashCode();
        }
        return ((((i7 + i5) * 31) + (this.i ? 1 : 0)) * 31) + this.j;
    }

    public /* bridge */ /* synthetic */ void populateMediaMetadata(MediaMetadata.Builder builder) {
        q6.c(this, builder);
    }

    public String toString() {
        String str = this.g;
        int c2 = y2.c(str, 80);
        String str2 = this.f;
        StringBuilder l = y2.l(y2.c(str2, c2), "IcyHeaders: name=\"", str, "\", genre=\"", str2);
        l.append("\", bitrate=");
        l.append(this.c);
        l.append(", metadataInterval=");
        l.append(this.j);
        return l.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeInt(this.c);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        parcel.writeString(this.h);
        Util.writeBoolean(parcel, this.i);
        parcel.writeInt(this.j);
    }

    public IcyHeaders(Parcel parcel) {
        this.c = parcel.readInt();
        this.f = parcel.readString();
        this.g = parcel.readString();
        this.h = parcel.readString();
        this.i = Util.readBoolean(parcel);
        this.j = parcel.readInt();
    }
}
