package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface HttpDataSource extends DataSource {

    public static abstract class BaseFactory implements Factory {
        public final RequestProperties a = new RequestProperties();

        public abstract HttpDataSource a(RequestProperties requestProperties);

        @Deprecated
        public final RequestProperties getDefaultRequestProperties() {
            return this.a;
        }

        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.a.clearAndSet(map);
            return this;
        }

        public final HttpDataSource createDataSource() {
            return a(this.a);
        }
    }

    public static final class CleartextNotPermittedException extends HttpDataSourceException {
        public CleartextNotPermittedException(IOException iOException, DataSpec dataSpec) {
            super("Cleartext HTTP traffic not permitted. See https://exoplayer.dev/issues/cleartext-not-permitted", iOException, dataSpec, 1);
        }
    }

    public interface Factory extends DataSource.Factory {
        /* bridge */ /* synthetic */ DataSource createDataSource();

        HttpDataSource createDataSource();

        @Deprecated
        RequestProperties getDefaultRequestProperties();

        Factory setDefaultRequestProperties(Map<String, String> map);
    }

    public static class HttpDataSourceException extends IOException {

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface Type {
        }

        public HttpDataSourceException(DataSpec dataSpec, int i) {
        }

        public HttpDataSourceException(String str, DataSpec dataSpec, int i) {
            super(str);
        }

        public HttpDataSourceException(IOException iOException, DataSpec dataSpec, int i) {
            super(iOException);
        }

        public HttpDataSourceException(String str, IOException iOException, DataSpec dataSpec, int i) {
            super(str, iOException);
        }
    }

    public static final class InvalidContentTypeException extends HttpDataSourceException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public InvalidContentTypeException(java.lang.String r3, com.google.android.exoplayer2.upstream.DataSpec r4) {
            /*
                r2 = this;
                java.lang.String r3 = java.lang.String.valueOf(r3)
                int r0 = r3.length()
                java.lang.String r1 = "Invalid content type: "
                if (r0 == 0) goto L_0x0011
                java.lang.String r3 = r1.concat(r3)
                goto L_0x0016
            L_0x0011:
                java.lang.String r3 = new java.lang.String
                r3.<init>(r1)
            L_0x0016:
                r0 = 1
                r2.<init>((java.lang.String) r3, (com.google.android.exoplayer2.upstream.DataSpec) r4, (int) r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.HttpDataSource.InvalidContentTypeException.<init>(java.lang.String, com.google.android.exoplayer2.upstream.DataSpec):void");
        }
    }

    /* synthetic */ void addTransferListener(TransferListener transferListener);

    void clearAllRequestProperties();

    void clearRequestProperty(String str);

    void close() throws HttpDataSourceException;

    int getResponseCode();

    Map<String, List<String>> getResponseHeaders();

    @Nullable
    /* synthetic */ Uri getUri();

    long open(DataSpec dataSpec) throws HttpDataSourceException;

    int read(byte[] bArr, int i, int i2) throws HttpDataSourceException;

    void setRequestProperty(String str, String str2);

    public static final class RequestProperties {
        public final HashMap a = new HashMap();
        @Nullable
        public Map<String, String> b;

        public synchronized void clear() {
            this.b = null;
            this.a.clear();
        }

        public synchronized void clearAndSet(Map<String, String> map) {
            this.b = null;
            this.a.clear();
            this.a.putAll(map);
        }

        public synchronized Map<String, String> getSnapshot() {
            if (this.b == null) {
                this.b = Collections.unmodifiableMap(new HashMap(this.a));
            }
            return this.b;
        }

        public synchronized void remove(String str) {
            this.b = null;
            this.a.remove(str);
        }

        public synchronized void set(String str, String str2) {
            this.b = null;
            this.a.put(str, str2);
        }

        public synchronized void set(Map<String, String> map) {
            this.b = null;
            this.a.putAll(map);
        }
    }

    public static final class InvalidResponseCodeException extends HttpDataSourceException {
        public final int c;
        public final Map<String, List<String>> f;

        public InvalidResponseCodeException(int i, @Nullable String str, Map<String, List<String>> map, DataSpec dataSpec, byte[] bArr) {
            super(y2.d(26, "Response code: ", i), dataSpec, 1);
            this.c = i;
            this.f = map;
        }

        @Deprecated
        public InvalidResponseCodeException(int i, Map<String, List<String>> map, DataSpec dataSpec) {
            this(i, (String) null, map, dataSpec, Util.f);
        }

        @Deprecated
        public InvalidResponseCodeException(int i, @Nullable String str, Map<String, List<String>> map, DataSpec dataSpec) {
            this(i, str, map, dataSpec, Util.f);
        }
    }
}
