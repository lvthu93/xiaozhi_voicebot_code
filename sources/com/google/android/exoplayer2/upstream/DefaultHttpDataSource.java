package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.jsoup.helper.HttpConnection;

public class DefaultHttpDataSource extends BaseDataSource implements HttpDataSource {
    public final boolean e;
    public final int f;
    public final int g;
    @Nullable
    public final String h;
    @Nullable
    public final HttpDataSource.RequestProperties i;
    public final HttpDataSource.RequestProperties j;
    @Nullable
    public Predicate<String> k;
    @Nullable
    public DataSpec l;
    @Nullable
    public HttpURLConnection m;
    @Nullable
    public InputStream n;
    public boolean o;
    public int p;
    public long q;
    public long r;

    public static final class Factory implements HttpDataSource.Factory {
        public final HttpDataSource.RequestProperties a = new HttpDataSource.RequestProperties();
        @Nullable
        public TransferListener b;
        @Nullable
        public Predicate<String> c;
        @Nullable
        public String d;
        public int e = 8000;
        public int f = 8000;
        public boolean g;

        @Deprecated
        public final HttpDataSource.RequestProperties getDefaultRequestProperties() {
            return this.a;
        }

        public Factory setAllowCrossProtocolRedirects(boolean z) {
            this.g = z;
            return this;
        }

        public Factory setConnectTimeoutMs(int i) {
            this.e = i;
            return this;
        }

        public Factory setContentTypePredicate(@Nullable Predicate<String> predicate) {
            this.c = predicate;
            return this;
        }

        public Factory setReadTimeoutMs(int i) {
            this.f = i;
            return this;
        }

        public Factory setTransferListener(@Nullable TransferListener transferListener) {
            this.b = transferListener;
            return this;
        }

        public Factory setUserAgent(@Nullable String str) {
            this.d = str;
            return this;
        }

        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.a.clearAndSet(map);
            return this;
        }

        public DefaultHttpDataSource createDataSource() {
            DefaultHttpDataSource defaultHttpDataSource = new DefaultHttpDataSource(this.d, this.e, this.f, this.g, this.a, this.c);
            TransferListener transferListener = this.b;
            if (transferListener != null) {
                defaultHttpDataSource.addTransferListener(transferListener);
            }
            return defaultHttpDataSource;
        }
    }

    @Deprecated
    public DefaultHttpDataSource() {
        this((String) null, 8000, 8000);
    }

    public static URL f(URL url, @Nullable String str) throws IOException {
        String str2;
        if (str != null) {
            URL url2 = new URL(url, str);
            String protocol = url2.getProtocol();
            if ("https".equals(protocol) || "http".equals(protocol)) {
                return url2;
            }
            String valueOf = String.valueOf(protocol);
            if (valueOf.length() != 0) {
                str2 = "Unsupported protocol redirect: ".concat(valueOf);
            } else {
                str2 = new String("Unsupported protocol redirect: ");
            }
            throw new ProtocolException(str2);
        }
        throw new ProtocolException("Null location redirect");
    }

    public static void i(@Nullable HttpURLConnection httpURLConnection, long j2) {
        int i2;
        if (httpURLConnection != null && (i2 = Util.a) >= 19 && i2 <= 20) {
            try {
                InputStream inputStream = httpURLConnection.getInputStream();
                if (j2 == -1) {
                    if (inputStream.read() == -1) {
                        return;
                    }
                } else if (j2 <= 2048) {
                    return;
                }
                String name = inputStream.getClass().getName();
                if ("com.android.okhttp.internal.http.HttpTransport$ChunkedInputStream".equals(name) || "com.android.okhttp.internal.http.HttpTransport$FixedLengthInputStream".equals(name)) {
                    Method declaredMethod = ((Class) Assertions.checkNotNull(inputStream.getClass().getSuperclass())).getDeclaredMethod("unexpectedEndOfInput", new Class[0]);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(inputStream, new Object[0]);
                }
            } catch (Exception unused) {
            }
        }
    }

    public void clearAllRequestProperties() {
        this.j.clear();
    }

    public void clearRequestProperty(String str) {
        Assertions.checkNotNull(str);
        this.j.remove(str);
    }

    public void close() throws HttpDataSource.HttpDataSourceException {
        try {
            InputStream inputStream = this.n;
            if (inputStream != null) {
                long j2 = this.q;
                long j3 = -1;
                if (j2 != -1) {
                    j3 = j2 - this.r;
                }
                i(this.m, j3);
                inputStream.close();
            }
            this.n = null;
            e();
            if (this.o) {
                this.o = false;
                b();
            }
        } catch (IOException e2) {
            throw new HttpDataSource.HttpDataSourceException(e2, (DataSpec) Util.castNonNull(this.l), 3);
        } catch (Throwable th) {
            this.n = null;
            e();
            if (this.o) {
                this.o = false;
                b();
            }
            throw th;
        }
    }

    public final void e() {
        HttpURLConnection httpURLConnection = this.m;
        if (httpURLConnection != null) {
            try {
                httpURLConnection.disconnect();
            } catch (Exception e2) {
                Log.e("DefaultHttpDataSource", "Unexpected error while disconnecting", e2);
            }
            this.m = null;
        }
    }

    public final HttpURLConnection g(DataSpec dataSpec) throws IOException {
        HttpURLConnection h2;
        URL url;
        DataSpec dataSpec2 = dataSpec;
        URL url2 = new URL(dataSpec2.a.toString());
        int i2 = dataSpec2.c;
        byte[] bArr = dataSpec2.d;
        long j2 = dataSpec2.f;
        long j3 = dataSpec2.g;
        boolean isFlagSet = dataSpec2.isFlagSet(1);
        if (!this.e) {
            return h(url2, i2, bArr, j2, j3, isFlagSet, true, dataSpec2.e);
        }
        int i3 = 0;
        while (true) {
            int i4 = i3 + 1;
            if (i3 <= 20) {
                int i5 = i4;
                long j4 = j3;
                long j5 = j2;
                h2 = h(url2, i2, bArr, j2, j3, isFlagSet, false, dataSpec2.e);
                int responseCode = h2.getResponseCode();
                String headerField = h2.getHeaderField("Location");
                if ((i2 == 1 || i2 == 3) && (responseCode == 300 || responseCode == 301 || responseCode == 302 || responseCode == 303 || responseCode == 307 || responseCode == 308)) {
                    h2.disconnect();
                    url = f(url2, headerField);
                } else if (i2 != 2 || (responseCode != 300 && responseCode != 301 && responseCode != 302 && responseCode != 303)) {
                    return h2;
                } else {
                    h2.disconnect();
                    url = f(url2, headerField);
                    i2 = 1;
                    bArr = null;
                }
                url2 = url;
                dataSpec2 = dataSpec;
                i3 = i5;
                j3 = j4;
                j2 = j5;
            } else {
                throw new NoRouteToHostException(y2.d(31, "Too many redirects: ", i4));
            }
        }
        return h2;
    }

    public int getResponseCode() {
        int i2;
        if (this.m == null || (i2 = this.p) <= 0) {
            return -1;
        }
        return i2;
    }

    public Map<String, List<String>> getResponseHeaders() {
        HttpURLConnection httpURLConnection = this.m;
        return httpURLConnection == null ? Collections.emptyMap() : httpURLConnection.getHeaderFields();
    }

    @Nullable
    public Uri getUri() {
        HttpURLConnection httpURLConnection = this.m;
        if (httpURLConnection == null) {
            return null;
        }
        return Uri.parse(httpURLConnection.getURL().toString());
    }

    public final HttpURLConnection h(URL url, int i2, @Nullable byte[] bArr, long j2, long j3, boolean z, boolean z2, Map<String, String> map) throws IOException {
        String str;
        boolean z3;
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setConnectTimeout(this.f);
        httpURLConnection.setReadTimeout(this.g);
        HashMap hashMap = new HashMap();
        HttpDataSource.RequestProperties requestProperties = this.i;
        if (requestProperties != null) {
            hashMap.putAll(requestProperties.getSnapshot());
        }
        hashMap.putAll(this.j.getSnapshot());
        hashMap.putAll(map);
        for (Map.Entry entry : hashMap.entrySet()) {
            httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        String buildRangeRequestHeader = HttpUtil.buildRangeRequestHeader(j2, j3);
        if (buildRangeRequestHeader != null) {
            httpURLConnection.setRequestProperty("Range", buildRangeRequestHeader);
        }
        String str2 = this.h;
        if (str2 != null) {
            httpURLConnection.setRequestProperty("User-Agent", str2);
        }
        if (z) {
            str = "gzip";
        } else {
            str = "identity";
        }
        httpURLConnection.setRequestProperty("Accept-Encoding", str);
        httpURLConnection.setInstanceFollowRedirects(z2);
        if (bArr != null) {
            z3 = true;
        } else {
            z3 = false;
        }
        httpURLConnection.setDoOutput(z3);
        httpURLConnection.setRequestMethod(DataSpec.getStringForHttpMethod(i2));
        if (bArr != null) {
            httpURLConnection.setFixedLengthStreamingMode(bArr.length);
            httpURLConnection.connect();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(bArr);
            outputStream.close();
        } else {
            httpURLConnection.connect();
        }
        return httpURLConnection;
    }

    public long open(DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        byte[] bArr;
        boolean z;
        long j2;
        DataSpec dataSpec2 = dataSpec;
        this.l = dataSpec2;
        this.r = 0;
        this.q = 0;
        c(dataSpec);
        try {
            HttpURLConnection g2 = g(dataSpec);
            this.m = g2;
            try {
                this.p = g2.getResponseCode();
                String responseMessage = g2.getResponseMessage();
                int i2 = this.p;
                long j3 = dataSpec2.f;
                long j4 = dataSpec2.g;
                if (i2 < 200 || i2 > 299) {
                    Map<String, List<String>> headerFields = g2.getHeaderFields();
                    if (this.p == 416 && j3 == HttpUtil.getDocumentSize(g2.getHeaderField("Content-Range"))) {
                        this.o = true;
                        d(dataSpec);
                        if (j4 != -1) {
                            return j4;
                        }
                        return 0;
                    }
                    InputStream errorStream = g2.getErrorStream();
                    if (errorStream != null) {
                        try {
                            bArr = Util.toByteArray(errorStream);
                        } catch (IOException unused) {
                            bArr = Util.f;
                        }
                    } else {
                        bArr = Util.f;
                    }
                    e();
                    HttpDataSource.InvalidResponseCodeException invalidResponseCodeException = new HttpDataSource.InvalidResponseCodeException(this.p, responseMessage, headerFields, dataSpec, bArr);
                    if (this.p == 416) {
                        invalidResponseCodeException.initCause(new DataSourceException(0));
                    }
                    throw invalidResponseCodeException;
                }
                String contentType = g2.getContentType();
                Predicate<String> predicate = this.k;
                if (predicate == null || predicate.apply(contentType)) {
                    if (this.p != 200 || j3 == 0) {
                        j3 = 0;
                    }
                    boolean equalsIgnoreCase = "gzip".equalsIgnoreCase(g2.getHeaderField(HttpConnection.CONTENT_ENCODING));
                    if (equalsIgnoreCase) {
                        this.q = j4;
                    } else if (j4 != -1) {
                        this.q = j4;
                    } else {
                        long contentLength = HttpUtil.getContentLength(g2.getHeaderField("Content-Length"), g2.getHeaderField("Content-Range"));
                        if (contentLength != -1) {
                            j2 = contentLength - j3;
                        } else {
                            j2 = -1;
                        }
                        this.q = j2;
                    }
                    try {
                        this.n = g2.getInputStream();
                        if (equalsIgnoreCase) {
                            this.n = new GZIPInputStream(this.n);
                        }
                        this.o = true;
                        d(dataSpec);
                        if (j3 != 0) {
                            try {
                                byte[] bArr2 = new byte[4096];
                                while (true) {
                                    if (j3 <= 0) {
                                        break;
                                    }
                                    int read = ((InputStream) Util.castNonNull(this.n)).read(bArr2, 0, (int) Math.min(j3, (long) 4096));
                                    if (Thread.currentThread().isInterrupted()) {
                                        throw new InterruptedIOException();
                                    } else if (read == -1) {
                                        z = false;
                                        break;
                                    } else {
                                        j3 -= (long) read;
                                        a(read);
                                    }
                                }
                            } catch (IOException e2) {
                                e();
                                throw new HttpDataSource.HttpDataSourceException(e2, dataSpec2, 1);
                            }
                        }
                        z = true;
                        if (z) {
                            return this.q;
                        }
                        throw new DataSourceException(0);
                    } catch (IOException e3) {
                        e();
                        throw new HttpDataSource.HttpDataSourceException(e3, dataSpec2, 1);
                    }
                } else {
                    e();
                    throw new HttpDataSource.InvalidContentTypeException(contentType, dataSpec2);
                }
            } catch (IOException e4) {
                e();
                throw new HttpDataSource.HttpDataSourceException("Unable to connect", e4, dataSpec2, 1);
            }
        } catch (IOException e5) {
            String message = e5.getMessage();
            if (message == null || !Ascii.toLowerCase(message).matches("cleartext http traffic.*not permitted.*")) {
                throw new HttpDataSource.HttpDataSourceException("Unable to connect", e5, dataSpec2, 1);
            }
            throw new HttpDataSource.CleartextNotPermittedException(e5, dataSpec2);
        }
    }

    public int read(byte[] bArr, int i2, int i3) throws HttpDataSource.HttpDataSourceException {
        if (i3 == 0) {
            return 0;
        }
        try {
            long j2 = this.q;
            if (j2 != -1) {
                long j3 = j2 - this.r;
                if (j3 == 0) {
                    return -1;
                }
                i3 = (int) Math.min((long) i3, j3);
            }
            int read = ((InputStream) Util.castNonNull(this.n)).read(bArr, i2, i3);
            if (read != -1) {
                this.r += (long) read;
                a(read);
                return read;
            }
            return -1;
        } catch (IOException e2) {
            throw new HttpDataSource.HttpDataSourceException(e2, (DataSpec) Util.castNonNull(this.l), 2);
        }
    }

    @Deprecated
    public void setContentTypePredicate(@Nullable Predicate<String> predicate) {
        this.k = predicate;
    }

    public void setRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        this.j.set(str, str2);
    }

    @Deprecated
    public DefaultHttpDataSource(@Nullable String str) {
        this(str, 8000, 8000);
    }

    @Deprecated
    public DefaultHttpDataSource(@Nullable String str, int i2, int i3) {
        this(str, i2, i3, false, (HttpDataSource.RequestProperties) null);
    }

    @Deprecated
    public DefaultHttpDataSource(@Nullable String str, int i2, int i3, boolean z, @Nullable HttpDataSource.RequestProperties requestProperties) {
        this(str, i2, i3, z, requestProperties, (Predicate<String>) null);
    }

    public DefaultHttpDataSource(@Nullable String str, int i2, int i3, boolean z, @Nullable HttpDataSource.RequestProperties requestProperties, @Nullable Predicate<String> predicate) {
        super(true);
        this.h = str;
        this.f = i2;
        this.g = i3;
        this.e = z;
        this.i = requestProperties;
        this.k = predicate;
        this.j = new HttpDataSource.RequestProperties();
    }
}
