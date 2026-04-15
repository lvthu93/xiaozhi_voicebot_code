package com.google.android.exoplayer2.offline;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class DownloadRequest implements Parcelable {
    public static final Parcelable.Creator<DownloadRequest> CREATOR = new a();
    public final String c;
    public final Uri f;
    @Nullable
    public final String g;
    public final List<StreamKey> h;
    @Nullable
    public final byte[] i;
    @Nullable
    public final String j;
    public final byte[] k;

    public static class Builder {
        public final String a;
        public final Uri b;
        @Nullable
        public String c;
        @Nullable
        public List<StreamKey> d;
        @Nullable
        public byte[] e;
        @Nullable
        public String f;
        @Nullable
        public byte[] g;

        public Builder(String str, Uri uri) {
            this.a = str;
            this.b = uri;
        }

        public DownloadRequest build() {
            String str = this.a;
            Uri uri = this.b;
            String str2 = this.c;
            List list = this.d;
            if (list == null) {
                list = ImmutableList.of();
            }
            return new DownloadRequest(str, uri, str2, list, this.e, this.f, this.g);
        }

        public Builder setCustomCacheKey(@Nullable String str) {
            this.f = str;
            return this;
        }

        public Builder setData(@Nullable byte[] bArr) {
            this.g = bArr;
            return this;
        }

        public Builder setKeySetId(@Nullable byte[] bArr) {
            this.e = bArr;
            return this;
        }

        public Builder setMimeType(@Nullable String str) {
            this.c = str;
            return this;
        }

        public Builder setStreamKeys(@Nullable List<StreamKey> list) {
            this.d = list;
            return this;
        }
    }

    public static class UnsupportedRequestException extends IOException {
    }

    public class a implements Parcelable.Creator<DownloadRequest> {
        public DownloadRequest createFromParcel(Parcel parcel) {
            return new DownloadRequest(parcel);
        }

        public DownloadRequest[] newArray(int i) {
            return new DownloadRequest[i];
        }
    }

    public DownloadRequest(String str, Uri uri, @Nullable String str2, List<StreamKey> list, @Nullable byte[] bArr, @Nullable String str3, @Nullable byte[] bArr2) {
        int inferContentTypeForUriAndMimeType = Util.inferContentTypeForUriAndMimeType(uri, str2);
        boolean z = true;
        if (inferContentTypeForUriAndMimeType == 0 || inferContentTypeForUriAndMimeType == 2 || inferContentTypeForUriAndMimeType == 1) {
            z = str3 != null ? false : z;
            StringBuilder sb = new StringBuilder(49);
            sb.append("customCacheKey must be null for type: ");
            sb.append(inferContentTypeForUriAndMimeType);
            Assertions.checkArgument(z, sb.toString());
        }
        this.c = str;
        this.f = uri;
        this.g = str2;
        ArrayList arrayList = new ArrayList(list);
        Collections.sort(arrayList);
        this.h = Collections.unmodifiableList(arrayList);
        this.i = bArr != null ? Arrays.copyOf(bArr, bArr.length) : null;
        this.j = str3;
        this.k = bArr2 != null ? Arrays.copyOf(bArr2, bArr2.length) : Util.f;
    }

    public DownloadRequest copyWithId(String str) {
        return new DownloadRequest(str, this.f, this.g, this.h, this.i, this.j, this.k);
    }

    public DownloadRequest copyWithKeySetId(@Nullable byte[] bArr) {
        return new DownloadRequest(this.c, this.f, this.g, this.h, bArr, this.j, this.k);
    }

    public DownloadRequest copyWithMergedRequest(DownloadRequest downloadRequest) {
        ArrayList arrayList;
        Assertions.checkArgument(this.c.equals(downloadRequest.c));
        List<StreamKey> list = this.h;
        if (!list.isEmpty()) {
            List<StreamKey> list2 = downloadRequest.h;
            if (!list2.isEmpty()) {
                ArrayList arrayList2 = new ArrayList(list);
                for (int i2 = 0; i2 < list2.size(); i2++) {
                    StreamKey streamKey = list2.get(i2);
                    if (!arrayList2.contains(streamKey)) {
                        arrayList2.add(streamKey);
                    }
                }
                arrayList = arrayList2;
                return new DownloadRequest(this.c, downloadRequest.f, downloadRequest.g, arrayList, downloadRequest.i, downloadRequest.j, downloadRequest.k);
            }
        }
        arrayList = Collections.emptyList();
        return new DownloadRequest(this.c, downloadRequest.f, downloadRequest.g, arrayList, downloadRequest.i, downloadRequest.j, downloadRequest.k);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DownloadRequest)) {
            return false;
        }
        DownloadRequest downloadRequest = (DownloadRequest) obj;
        if (!this.c.equals(downloadRequest.c) || !this.f.equals(downloadRequest.f) || !Util.areEqual(this.g, downloadRequest.g) || !this.h.equals(downloadRequest.h) || !Arrays.equals(this.i, downloadRequest.i) || !Util.areEqual(this.j, downloadRequest.j) || !Arrays.equals(this.k, downloadRequest.k)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i2;
        int hashCode = (this.f.hashCode() + (this.c.hashCode() * 31 * 31)) * 31;
        int i3 = 0;
        String str = this.g;
        if (str != null) {
            i2 = str.hashCode();
        } else {
            i2 = 0;
        }
        int hashCode2 = this.h.hashCode();
        int hashCode3 = (Arrays.hashCode(this.i) + ((hashCode2 + ((hashCode + i2) * 31)) * 31)) * 31;
        String str2 = this.j;
        if (str2 != null) {
            i3 = str2.hashCode();
        }
        return Arrays.hashCode(this.k) + ((hashCode3 + i3) * 31);
    }

    public MediaItem toMediaItem() {
        return new MediaItem.Builder().setMediaId(this.c).setUri(this.f).setCustomCacheKey(this.j).setMimeType(this.g).setStreamKeys(this.h).setDrmKeySetId(this.i).build();
    }

    public String toString() {
        String str = this.g;
        int c2 = y2.c(str, 1);
        String str2 = this.c;
        StringBuilder sb = new StringBuilder(y2.c(str2, c2));
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(this.c);
        parcel.writeString(this.f.toString());
        parcel.writeString(this.g);
        List<StreamKey> list = this.h;
        parcel.writeInt(list.size());
        for (int i3 = 0; i3 < list.size(); i3++) {
            parcel.writeParcelable(list.get(i3), 0);
        }
        parcel.writeByteArray(this.i);
        parcel.writeString(this.j);
        parcel.writeByteArray(this.k);
    }

    public DownloadRequest(Parcel parcel) {
        this.c = (String) Util.castNonNull(parcel.readString());
        this.f = Uri.parse((String) Util.castNonNull(parcel.readString()));
        this.g = parcel.readString();
        int readInt = parcel.readInt();
        ArrayList arrayList = new ArrayList(readInt);
        for (int i2 = 0; i2 < readInt; i2++) {
            arrayList.add((StreamKey) parcel.readParcelable(StreamKey.class.getClassLoader()));
        }
        this.h = Collections.unmodifiableList(arrayList);
        this.i = parcel.createByteArray();
        this.j = parcel.readString();
        this.k = (byte[]) Util.castNonNull(parcel.createByteArray());
    }
}
