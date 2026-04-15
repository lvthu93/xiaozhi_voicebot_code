package com.google.android.exoplayer2.ext.okhttp;

import android.net.Uri;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.upstream.BaseDataSource;
import com.google.android.exoplayer2.upstream.DataSourceException;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.HttpUtil;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.base.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpDataSource extends BaseDataSource implements HttpDataSource {
    public final Call.Factory e;
    public final HttpDataSource.RequestProperties f;
    @Nullable
    public final String g;
    @Nullable
    public final CacheControl h;
    @Nullable
    public final HttpDataSource.RequestProperties i;
    @Nullable
    public Predicate<String> j;
    @Nullable
    public DataSpec k;
    @Nullable
    public Response l;
    @Nullable
    public InputStream m;
    public boolean n;
    public long o;
    public long p;

    public static final class Factory implements HttpDataSource.Factory {
        public final HttpDataSource.RequestProperties a = new HttpDataSource.RequestProperties();
        public final Call.Factory b;
        @Nullable
        public String c;
        @Nullable
        public TransferListener d;
        @Nullable
        public CacheControl e;
        @Nullable
        public Predicate<String> f;

        public Factory(Call.Factory factory) {
            this.b = factory;
        }

        @Deprecated
        public final HttpDataSource.RequestProperties getDefaultRequestProperties() {
            return this.a;
        }

        public Factory setCacheControl(@Nullable CacheControl cacheControl) {
            this.e = cacheControl;
            return this;
        }

        public Factory setContentTypePredicate(@Nullable Predicate<String> predicate) {
            this.f = predicate;
            return this;
        }

        public Factory setTransferListener(@Nullable TransferListener transferListener) {
            this.d = transferListener;
            return this;
        }

        public Factory setUserAgent(@Nullable String str) {
            this.c = str;
            return this;
        }

        public final Factory setDefaultRequestProperties(Map<String, String> map) {
            this.a.clearAndSet(map);
            return this;
        }

        public OkHttpDataSource createDataSource() {
            OkHttpDataSource okHttpDataSource = new OkHttpDataSource(this.b, this.c, this.e, this.a, this.f);
            TransferListener transferListener = this.d;
            if (transferListener != null) {
                okHttpDataSource.addTransferListener(transferListener);
            }
            return okHttpDataSource;
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.okhttp");
    }

    @Deprecated
    public OkHttpDataSource(Call.Factory factory) {
        this(factory, (String) null);
    }

    public void clearAllRequestProperties() {
        this.f.clear();
    }

    public void clearRequestProperty(String str) {
        Assertions.checkNotNull(str);
        this.f.remove(str);
    }

    public void close() throws HttpDataSource.HttpDataSourceException {
        if (this.n) {
            this.n = false;
            b();
            e();
        }
    }

    public final void e() {
        Response response = this.l;
        if (response != null) {
            ((ResponseBody) Assertions.checkNotNull(response.body())).close();
            this.l = null;
        }
        this.m = null;
    }

    public int getResponseCode() {
        Response response = this.l;
        if (response == null) {
            return -1;
        }
        return response.code();
    }

    public Map<String, List<String>> getResponseHeaders() {
        Response response = this.l;
        return response == null ? Collections.emptyMap() : response.headers().toMultimap();
    }

    @Nullable
    public Uri getUri() {
        Response response = this.l;
        if (response == null) {
            return null;
        }
        return Uri.parse(response.request().url().toString());
    }

    public long open(DataSpec dataSpec) throws HttpDataSource.HttpDataSourceException {
        String str;
        boolean z;
        byte[] bArr;
        DataSpec dataSpec2 = dataSpec;
        this.k = dataSpec2;
        this.p = 0;
        this.o = 0;
        c(dataSpec);
        long j2 = dataSpec2.f;
        HttpUrl parse = HttpUrl.parse(dataSpec2.a.toString());
        if (parse != null) {
            Request.Builder url = new Request.Builder().url(parse);
            CacheControl cacheControl = this.h;
            if (cacheControl != null) {
                url.cacheControl(cacheControl);
            }
            HashMap hashMap = new HashMap();
            HttpDataSource.RequestProperties requestProperties = this.i;
            if (requestProperties != null) {
                hashMap.putAll(requestProperties.getSnapshot());
            }
            hashMap.putAll(this.f.getSnapshot());
            hashMap.putAll(dataSpec2.e);
            for (Map.Entry entry : hashMap.entrySet()) {
                url.header((String) entry.getKey(), (String) entry.getValue());
            }
            String buildRangeRequestHeader = HttpUtil.buildRangeRequestHeader(j2, dataSpec2.g);
            if (buildRangeRequestHeader != null) {
                url.addHeader("Range", buildRangeRequestHeader);
            }
            String str2 = this.g;
            if (str2 != null) {
                url.addHeader("User-Agent", str2);
            }
            if (!dataSpec2.isFlagSet(1)) {
                url.addHeader("Accept-Encoding", "identity");
            }
            RequestBody requestBody = null;
            byte[] bArr2 = dataSpec2.d;
            if (bArr2 != null) {
                requestBody = RequestBody.create((MediaType) null, bArr2);
            } else if (dataSpec2.c == 2) {
                requestBody = RequestBody.create((MediaType) null, Util.f);
            }
            url.method(dataSpec.getHttpMethodString(), requestBody);
            try {
                Response execute = this.e.newCall(url.build()).execute();
                this.l = execute;
                ResponseBody responseBody = (ResponseBody) Assertions.checkNotNull(execute.body());
                this.m = responseBody.byteStream();
                int code = execute.code();
                boolean isSuccessful = execute.isSuccessful();
                long j3 = -1;
                long j4 = dataSpec2.g;
                long j5 = dataSpec2.f;
                if (isSuccessful) {
                    MediaType contentType = responseBody.contentType();
                    if (contentType != null) {
                        str = contentType.toString();
                    } else {
                        str = "";
                    }
                    Predicate<String> predicate = this.j;
                    if (predicate == null || predicate.apply(str)) {
                        if (code != 200 || j5 == 0) {
                            j5 = 0;
                        }
                        if (j4 != -1) {
                            this.o = j4;
                        } else {
                            long contentLength = responseBody.contentLength();
                            if (contentLength != -1) {
                                j3 = contentLength - j5;
                            }
                            this.o = j3;
                        }
                        this.n = true;
                        d(dataSpec);
                        if (j5 != 0) {
                            try {
                                byte[] bArr3 = new byte[4096];
                                while (true) {
                                    if (j5 <= 0) {
                                        break;
                                    }
                                    int read = ((InputStream) Util.castNonNull(this.m)).read(bArr3, 0, (int) Math.min(j5, (long) 4096));
                                    if (Thread.currentThread().isInterrupted()) {
                                        throw new InterruptedIOException();
                                    } else if (read == -1) {
                                        z = false;
                                        break;
                                    } else {
                                        j5 -= (long) read;
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
                            return this.o;
                        }
                        throw new DataSourceException(0);
                    }
                    e();
                    throw new HttpDataSource.InvalidContentTypeException(str, dataSpec2);
                } else if (code == 416 && j5 == HttpUtil.getDocumentSize(execute.headers().get("Content-Range"))) {
                    this.n = true;
                    d(dataSpec);
                    if (j4 != -1) {
                        return j4;
                    }
                    return 0;
                } else {
                    try {
                        bArr = Util.toByteArray((InputStream) Assertions.checkNotNull(this.m));
                    } catch (IOException unused) {
                        bArr = Util.f;
                    }
                    Map<String, List<String>> multimap = execute.headers().toMultimap();
                    e();
                    HttpDataSource.InvalidResponseCodeException invalidResponseCodeException = new HttpDataSource.InvalidResponseCodeException(code, execute.message(), multimap, dataSpec, bArr);
                    if (code == 416) {
                        invalidResponseCodeException.initCause(new DataSourceException(0));
                    }
                    throw invalidResponseCodeException;
                }
            } catch (IOException e3) {
                String message = e3.getMessage();
                if (message == null || !Ascii.toLowerCase(message).matches("cleartext communication.*not permitted.*")) {
                    throw new HttpDataSource.HttpDataSourceException("Unable to connect", e3, dataSpec2, 1);
                }
                throw new HttpDataSource.CleartextNotPermittedException(e3, dataSpec2);
            }
        } else {
            throw new HttpDataSource.HttpDataSourceException("Malformed URL", dataSpec2, 1);
        }
    }

    public int read(byte[] bArr, int i2, int i3) throws HttpDataSource.HttpDataSourceException {
        if (i3 == 0) {
            return 0;
        }
        try {
            long j2 = this.o;
            if (j2 != -1) {
                long j3 = j2 - this.p;
                if (j3 == 0) {
                    return -1;
                }
                i3 = (int) Math.min((long) i3, j3);
            }
            int read = ((InputStream) Util.castNonNull(this.m)).read(bArr, i2, i3);
            if (read != -1) {
                this.p += (long) read;
                a(read);
                return read;
            }
            return -1;
        } catch (IOException e2) {
            throw new HttpDataSource.HttpDataSourceException(e2, (DataSpec) Assertions.checkNotNull(this.k), 2);
        }
    }

    @Deprecated
    public void setContentTypePredicate(@Nullable Predicate<String> predicate) {
        this.j = predicate;
    }

    public void setRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        this.f.set(str, str2);
    }

    @Deprecated
    public OkHttpDataSource(Call.Factory factory, @Nullable String str) {
        this(factory, str, (CacheControl) null, (HttpDataSource.RequestProperties) null);
    }

    @Deprecated
    public OkHttpDataSource(Call.Factory factory, @Nullable String str, @Nullable CacheControl cacheControl, @Nullable HttpDataSource.RequestProperties requestProperties) {
        this(factory, str, cacheControl, requestProperties, (Predicate<String>) null);
    }

    public OkHttpDataSource(Call.Factory factory, @Nullable String str, @Nullable CacheControl cacheControl, @Nullable HttpDataSource.RequestProperties requestProperties, @Nullable Predicate<String> predicate) {
        super(true);
        this.e = (Call.Factory) Assertions.checkNotNull(factory);
        this.g = str;
        this.h = cacheControl;
        this.i = requestProperties;
        this.j = predicate;
        this.f = new HttpDataSource.RequestProperties();
    }
}
