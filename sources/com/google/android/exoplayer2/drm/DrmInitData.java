package com.google.android.exoplayer2.drm;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public final class DrmInitData implements Comparator<SchemeData>, Parcelable {
    public static final Parcelable.Creator<DrmInitData> CREATOR = new a();
    public final SchemeData[] c;
    public int f;
    @Nullable
    public final String g;
    public final int h;

    public static final class SchemeData implements Parcelable {
        public static final Parcelable.Creator<SchemeData> CREATOR = new a();
        public int c;
        public final UUID f;
        @Nullable
        public final String g;
        public final String h;
        @Nullable
        public final byte[] i;

        public class a implements Parcelable.Creator<SchemeData> {
            public SchemeData createFromParcel(Parcel parcel) {
                return new SchemeData(parcel);
            }

            public SchemeData[] newArray(int i) {
                return new SchemeData[i];
            }
        }

        public SchemeData(UUID uuid, String str, @Nullable byte[] bArr) {
            this(uuid, (String) null, str, bArr);
        }

        public boolean canReplace(SchemeData schemeData) {
            return hasData() && !schemeData.hasData() && matches(schemeData.f);
        }

        public SchemeData copyWithData(@Nullable byte[] bArr) {
            return new SchemeData(this.f, this.g, this.h, bArr);
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof SchemeData)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            SchemeData schemeData = (SchemeData) obj;
            if (!Util.areEqual(this.g, schemeData.g) || !Util.areEqual(this.h, schemeData.h) || !Util.areEqual(this.f, schemeData.f) || !Arrays.equals(this.i, schemeData.i)) {
                return false;
            }
            return true;
        }

        public boolean hasData() {
            return this.i != null;
        }

        public int hashCode() {
            int i2;
            if (this.c == 0) {
                int hashCode = this.f.hashCode() * 31;
                String str = this.g;
                if (str == null) {
                    i2 = 0;
                } else {
                    i2 = str.hashCode();
                }
                int hashCode2 = this.h.hashCode();
                this.c = Arrays.hashCode(this.i) + ((hashCode2 + ((hashCode + i2) * 31)) * 31);
            }
            return this.c;
        }

        public boolean matches(UUID uuid) {
            UUID uuid2 = C.a;
            UUID uuid3 = this.f;
            return uuid2.equals(uuid3) || uuid.equals(uuid3);
        }

        public void writeToParcel(Parcel parcel, int i2) {
            UUID uuid = this.f;
            parcel.writeLong(uuid.getMostSignificantBits());
            parcel.writeLong(uuid.getLeastSignificantBits());
            parcel.writeString(this.g);
            parcel.writeString(this.h);
            parcel.writeByteArray(this.i);
        }

        public SchemeData(UUID uuid, @Nullable String str, String str2, @Nullable byte[] bArr) {
            this.f = (UUID) Assertions.checkNotNull(uuid);
            this.g = str;
            this.h = (String) Assertions.checkNotNull(str2);
            this.i = bArr;
        }

        public SchemeData(Parcel parcel) {
            this.f = new UUID(parcel.readLong(), parcel.readLong());
            this.g = parcel.readString();
            this.h = (String) Util.castNonNull(parcel.readString());
            this.i = parcel.createByteArray();
        }
    }

    public class a implements Parcelable.Creator<DrmInitData> {
        public DrmInitData createFromParcel(Parcel parcel) {
            return new DrmInitData(parcel);
        }

        public DrmInitData[] newArray(int i) {
            return new DrmInitData[i];
        }
    }

    public DrmInitData(List<SchemeData> list) {
        this((String) null, false, (SchemeData[]) list.toArray(new SchemeData[0]));
    }

    @Nullable
    public static DrmInitData createSessionCreationData(@Nullable DrmInitData drmInitData, @Nullable DrmInitData drmInitData2) {
        String str;
        boolean z;
        ArrayList arrayList = new ArrayList();
        if (drmInitData != null) {
            for (SchemeData schemeData : drmInitData.c) {
                if (schemeData.hasData()) {
                    arrayList.add(schemeData);
                }
            }
            str = drmInitData.g;
        } else {
            str = null;
        }
        if (drmInitData2 != null) {
            if (str == null) {
                str = drmInitData2.g;
            }
            int size = arrayList.size();
            for (SchemeData schemeData2 : drmInitData2.c) {
                if (schemeData2.hasData()) {
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            z = false;
                            break;
                        } else if (((SchemeData) arrayList.get(i)).f.equals(schemeData2.f)) {
                            z = true;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (!z) {
                        arrayList.add(schemeData2);
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new DrmInitData(str, (List<SchemeData>) arrayList);
    }

    public DrmInitData copyWithSchemeType(@Nullable String str) {
        if (Util.areEqual(this.g, str)) {
            return this;
        }
        return new DrmInitData(str, false, this.c);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DrmInitData.class != obj.getClass()) {
            return false;
        }
        DrmInitData drmInitData = (DrmInitData) obj;
        if (!Util.areEqual(this.g, drmInitData.g) || !Arrays.equals(this.c, drmInitData.c)) {
            return false;
        }
        return true;
    }

    public SchemeData get(int i) {
        return this.c[i];
    }

    public int hashCode() {
        int i;
        if (this.f == 0) {
            String str = this.g;
            if (str == null) {
                i = 0;
            } else {
                i = str.hashCode();
            }
            this.f = (i * 31) + Arrays.hashCode(this.c);
        }
        return this.f;
    }

    public DrmInitData merge(DrmInitData drmInitData) {
        boolean z;
        String str;
        String str2 = this.g;
        if (str2 == null || (str = drmInitData.g) == null || TextUtils.equals(str2, str)) {
            z = true;
        } else {
            z = false;
        }
        Assertions.checkState(z);
        if (str2 == null) {
            str2 = drmInitData.g;
        }
        return new DrmInitData(str2, (SchemeData[]) Util.nullSafeArrayConcatenation(this.c, drmInitData.c));
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.g);
        parcel.writeTypedArray(this.c, 0);
    }

    public DrmInitData(@Nullable String str, List<SchemeData> list) {
        this(str, false, (SchemeData[]) list.toArray(new SchemeData[0]));
    }

    public int compare(SchemeData schemeData, SchemeData schemeData2) {
        UUID uuid = C.a;
        if (uuid.equals(schemeData.f)) {
            return uuid.equals(schemeData2.f) ? 0 : 1;
        }
        return schemeData.f.compareTo(schemeData2.f);
    }

    public DrmInitData(SchemeData... schemeDataArr) {
        this((String) null, schemeDataArr);
    }

    public DrmInitData(@Nullable String str, SchemeData... schemeDataArr) {
        this(str, true, schemeDataArr);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: com.google.android.exoplayer2.drm.DrmInitData$SchemeData[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DrmInitData(@androidx.annotation.Nullable java.lang.String r1, boolean r2, com.google.android.exoplayer2.drm.DrmInitData.SchemeData... r3) {
        /*
            r0 = this;
            r0.<init>()
            r0.g = r1
            if (r2 == 0) goto L_0x000e
            java.lang.Object r1 = r3.clone()
            r3 = r1
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData[] r3 = (com.google.android.exoplayer2.drm.DrmInitData.SchemeData[]) r3
        L_0x000e:
            r0.c = r3
            int r1 = r3.length
            r0.h = r1
            java.util.Arrays.sort(r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.DrmInitData.<init>(java.lang.String, boolean, com.google.android.exoplayer2.drm.DrmInitData$SchemeData[]):void");
    }

    public DrmInitData(Parcel parcel) {
        this.g = parcel.readString();
        SchemeData[] schemeDataArr = (SchemeData[]) Util.castNonNull((SchemeData[]) parcel.createTypedArray(SchemeData.CREATOR));
        this.c = schemeDataArr;
        this.h = schemeDataArr.length;
    }
}
