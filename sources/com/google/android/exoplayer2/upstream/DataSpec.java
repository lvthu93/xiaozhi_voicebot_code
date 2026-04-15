package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DataSpec {
    public final Uri a;
    public final long b;
    public final int c;
    @Nullable
    public final byte[] d;
    public final Map<String, String> e;
    public final long f;
    public final long g;
    @Nullable
    public final String h;
    public final int i;
    @Nullable
    public final Object j;

    public static final class Builder {
        @Nullable
        public Uri a;
        public long b;
        public int c;
        @Nullable
        public byte[] d;
        public Map<String, String> e;
        public long f;
        public long g;
        @Nullable
        public String h;
        public int i;
        @Nullable
        public Object j;

        public Builder() {
            this.c = 1;
            this.e = Collections.emptyMap();
            this.g = -1;
        }

        public DataSpec build() {
            Assertions.checkStateNotNull(this.a, "The uri must be set.");
            return new DataSpec(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
        }

        public Builder setCustomData(@Nullable Object obj) {
            this.j = obj;
            return this;
        }

        public Builder setFlags(int i2) {
            this.i = i2;
            return this;
        }

        public Builder setHttpBody(@Nullable byte[] bArr) {
            this.d = bArr;
            return this;
        }

        public Builder setHttpMethod(int i2) {
            this.c = i2;
            return this;
        }

        public Builder setHttpRequestHeaders(Map<String, String> map) {
            this.e = map;
            return this;
        }

        public Builder setKey(@Nullable String str) {
            this.h = str;
            return this;
        }

        public Builder setLength(long j2) {
            this.g = j2;
            return this;
        }

        public Builder setPosition(long j2) {
            this.f = j2;
            return this;
        }

        public Builder setUri(String str) {
            this.a = Uri.parse(str);
            return this;
        }

        public Builder setUriPositionOffset(long j2) {
            this.b = j2;
            return this;
        }

        public Builder setUri(Uri uri) {
            this.a = uri;
            return this;
        }

        public Builder(DataSpec dataSpec) {
            this.a = dataSpec.a;
            this.b = dataSpec.b;
            this.c = dataSpec.c;
            this.d = dataSpec.d;
            this.e = dataSpec.e;
            this.f = dataSpec.f;
            this.g = dataSpec.g;
            this.h = dataSpec.h;
            this.i = dataSpec.i;
            this.j = dataSpec.j;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethod {
    }

    public DataSpec(Uri uri) {
        this(uri, 0, -1);
    }

    public static String getStringForHttpMethod(int i2) {
        if (i2 == 1) {
            return "GET";
        }
        if (i2 == 2) {
            return "POST";
        }
        if (i2 == 3) {
            return "HEAD";
        }
        throw new IllegalStateException();
    }

    public Builder buildUpon() {
        return new Builder(this);
    }

    public final String getHttpMethodString() {
        return getStringForHttpMethod(this.c);
    }

    public boolean isFlagSet(int i2) {
        return (this.i & i2) == i2;
    }

    public DataSpec subrange(long j2) {
        long j3 = this.g;
        long j4 = -1;
        if (j3 != -1) {
            j4 = j3 - j2;
        }
        return subrange(j2, j4);
    }

    public String toString() {
        String httpMethodString = getHttpMethodString();
        String valueOf = String.valueOf(this.a);
        int length = valueOf.length() + y2.c(httpMethodString, 70);
        String str = this.h;
        StringBuilder l = y2.l(y2.c(str, length), "DataSpec[", httpMethodString, " ", valueOf);
        l.append(", ");
        l.append(this.f);
        l.append(", ");
        l.append(this.g);
        l.append(", ");
        l.append(str);
        l.append(", ");
        l.append(this.i);
        l.append("]");
        return l.toString();
    }

    public DataSpec withAdditionalHeaders(Map<String, String> map) {
        HashMap hashMap = new HashMap(this.e);
        hashMap.putAll(map);
        return new DataSpec(this.a, this.b, this.c, this.d, hashMap, this.f, this.g, this.h, this.i, this.j);
    }

    public DataSpec withRequestHeaders(Map<String, String> map) {
        return new DataSpec(this.a, this.b, this.c, this.d, map, this.f, this.g, this.h, this.i, this.j);
    }

    public DataSpec withUri(Uri uri) {
        return new DataSpec(uri, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
    }

    public DataSpec(Uri uri, long j2, long j3) {
        this(uri, 0, 1, (byte[]) null, Collections.emptyMap(), j2, j3, (String) null, 0, (Object) null);
    }

    public DataSpec subrange(long j2, long j3) {
        if (j2 == 0 && this.g == j3) {
            return this;
        }
        return new DataSpec(this.a, this.b, this.c, this.d, this.e, this.f + j2, j3, this.h, this.i, this.j);
    }

    @Deprecated
    public DataSpec(Uri uri, int i2) {
        this(uri, 0, -1, (String) null, i2);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, @Nullable String str) {
        this(uri, j2, j2, j3, str, 0);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, @Nullable String str, int i2) {
        this(uri, j2, j2, j3, str, i2);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, @Nullable String str, int i2, Map<String, String> map) {
        this(uri, 1, (byte[]) null, j2, j2, j3, str, i2, map);
    }

    @Deprecated
    public DataSpec(Uri uri, long j2, long j3, long j4, @Nullable String str, int i2) {
        this(uri, (byte[]) null, j2, j3, j4, str, i2);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public DataSpec(Uri uri, @Nullable byte[] bArr, long j2, long j3, long j4, @Nullable String str, int i2) {
        this(uri, bArr != null ? 2 : 1, bArr, j2, j3, j4, str, i2);
    }

    @Deprecated
    public DataSpec(Uri uri, int i2, @Nullable byte[] bArr, long j2, long j3, long j4, @Nullable String str, int i3) {
        this(uri, i2, bArr, j2, j3, j4, str, i3, Collections.emptyMap());
    }

    @Deprecated
    public DataSpec(Uri uri, int i2, @Nullable byte[] bArr, long j2, long j3, long j4, @Nullable String str, int i3, Map<String, String> map) {
        this(uri, j2 - j3, i2, bArr, map, j3, j4, str, i3, (Object) null);
    }

    public DataSpec(Uri uri, long j2, int i2, @Nullable byte[] bArr, Map<String, String> map, long j3, long j4, @Nullable String str, int i3, @Nullable Object obj) {
        long j5 = j2;
        byte[] bArr2 = bArr;
        long j6 = j3;
        long j7 = j4;
        boolean z = true;
        Assertions.checkArgument(j5 + j6 >= 0);
        Assertions.checkArgument(j6 >= 0);
        if (j7 <= 0 && j7 != -1) {
            z = false;
        }
        Assertions.checkArgument(z);
        this.a = uri;
        this.b = j5;
        this.c = i2;
        this.d = (bArr2 == null || bArr2.length == 0) ? null : bArr2;
        this.e = Collections.unmodifiableMap(new HashMap(map));
        this.f = j6;
        this.g = j7;
        this.h = str;
        this.i = i3;
        this.j = obj;
    }
}
