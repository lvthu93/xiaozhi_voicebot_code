package com.google.android.exoplayer2.drm;

import android.net.Uri;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public final class HttpMediaDrmCallback implements MediaDrmCallback {
    public final HttpDataSource.Factory a;
    @Nullable
    public final String b;
    public final boolean c;
    public final HashMap d;

    public HttpMediaDrmCallback(@Nullable String str, HttpDataSource.Factory factory) {
        this(str, false, factory);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] a(com.google.android.exoplayer2.upstream.HttpDataSource.Factory r8, java.lang.String r9, @androidx.annotation.Nullable byte[] r10, java.util.Map<java.lang.String, java.lang.String> r11) throws com.google.android.exoplayer2.drm.MediaDrmCallbackException {
        /*
            com.google.android.exoplayer2.upstream.StatsDataSource r0 = new com.google.android.exoplayer2.upstream.StatsDataSource
            com.google.android.exoplayer2.upstream.HttpDataSource r8 = r8.createDataSource()
            r0.<init>(r8)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r8 = new com.google.android.exoplayer2.upstream.DataSpec$Builder
            r8.<init>()
            com.google.android.exoplayer2.upstream.DataSpec$Builder r8 = r8.setUri((java.lang.String) r9)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r8 = r8.setHttpRequestHeaders(r11)
            r9 = 2
            com.google.android.exoplayer2.upstream.DataSpec$Builder r8 = r8.setHttpMethod(r9)
            com.google.android.exoplayer2.upstream.DataSpec$Builder r8 = r8.setHttpBody(r10)
            r9 = 1
            com.google.android.exoplayer2.upstream.DataSpec$Builder r8 = r8.setFlags(r9)
            com.google.android.exoplayer2.upstream.DataSpec r2 = r8.build()
            r8 = 0
            r10 = r2
            r11 = 0
        L_0x002b:
            com.google.android.exoplayer2.upstream.DataSourceInputStream r1 = new com.google.android.exoplayer2.upstream.DataSourceInputStream     // Catch:{ Exception -> 0x0084 }
            r1.<init>(r0, r10)     // Catch:{ Exception -> 0x0084 }
            byte[] r8 = com.google.android.exoplayer2.util.Util.toByteArray(r1)     // Catch:{ InvalidResponseCodeException -> 0x0038 }
            com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r1)     // Catch:{ Exception -> 0x0084 }
            return r8
        L_0x0038:
            r3 = move-exception
            int r4 = r3.c     // Catch:{ all -> 0x0069 }
            r5 = 307(0x133, float:4.3E-43)
            if (r4 == r5) goto L_0x0043
            r5 = 308(0x134, float:4.32E-43)
            if (r4 != r5) goto L_0x0048
        L_0x0043:
            r4 = 5
            if (r11 >= r4) goto L_0x0048
            r4 = 1
            goto L_0x0049
        L_0x0048:
            r4 = 0
        L_0x0049:
            r5 = 0
            if (r4 != 0) goto L_0x004d
            goto L_0x006b
        L_0x004d:
            java.util.Map<java.lang.String, java.util.List<java.lang.String>> r4 = r3.f     // Catch:{ all -> 0x0069 }
            if (r4 == 0) goto L_0x006b
            java.lang.String r6 = "Location"
            java.lang.Object r4 = r4.get(r6)     // Catch:{ all -> 0x0069 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ all -> 0x0069 }
            if (r4 == 0) goto L_0x006b
            boolean r6 = r4.isEmpty()     // Catch:{ all -> 0x0069 }
            if (r6 != 0) goto L_0x006b
            java.lang.Object r4 = r4.get(r8)     // Catch:{ all -> 0x0069 }
            r5 = r4
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x0069 }
            goto L_0x006b
        L_0x0069:
            r8 = move-exception
            goto L_0x0080
        L_0x006b:
            if (r5 == 0) goto L_0x007f
            int r11 = r11 + 1
            com.google.android.exoplayer2.upstream.DataSpec$Builder r10 = r10.buildUpon()     // Catch:{ all -> 0x0069 }
            com.google.android.exoplayer2.upstream.DataSpec$Builder r10 = r10.setUri((java.lang.String) r5)     // Catch:{ all -> 0x0069 }
            com.google.android.exoplayer2.upstream.DataSpec r10 = r10.build()     // Catch:{ all -> 0x0069 }
            com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r1)     // Catch:{ Exception -> 0x0084 }
            goto L_0x002b
        L_0x007f:
            throw r3     // Catch:{ all -> 0x0069 }
        L_0x0080:
            com.google.android.exoplayer2.util.Util.closeQuietly((java.io.Closeable) r1)     // Catch:{ Exception -> 0x0084 }
            throw r8     // Catch:{ Exception -> 0x0084 }
        L_0x0084:
            r8 = move-exception
            r7 = r8
            com.google.android.exoplayer2.drm.MediaDrmCallbackException r8 = new com.google.android.exoplayer2.drm.MediaDrmCallbackException
            android.net.Uri r9 = r0.getLastOpenedUri()
            java.lang.Object r9 = com.google.android.exoplayer2.util.Assertions.checkNotNull(r9)
            r3 = r9
            android.net.Uri r3 = (android.net.Uri) r3
            java.util.Map r4 = r0.getResponseHeaders()
            long r5 = r0.getBytesRead()
            r1 = r8
            r1.<init>(r2, r3, r4, r5, r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.HttpMediaDrmCallback.a(com.google.android.exoplayer2.upstream.HttpDataSource$Factory, java.lang.String, byte[], java.util.Map):byte[]");
    }

    public void clearAllKeyRequestProperties() {
        synchronized (this.d) {
            this.d.clear();
        }
    }

    public void clearKeyRequestProperty(String str) {
        Assertions.checkNotNull(str);
        synchronized (this.d) {
            this.d.remove(str);
        }
    }

    public byte[] executeKeyRequest(UUID uuid, ExoMediaDrm.KeyRequest keyRequest) throws MediaDrmCallbackException {
        String str;
        String licenseServerUrl = keyRequest.getLicenseServerUrl();
        if (this.c || TextUtils.isEmpty(licenseServerUrl)) {
            licenseServerUrl = this.b;
        }
        if (!TextUtils.isEmpty(licenseServerUrl)) {
            HashMap hashMap = new HashMap();
            UUID uuid2 = C.e;
            if (uuid2.equals(uuid)) {
                str = "text/xml";
            } else if (C.c.equals(uuid)) {
                str = "application/json";
            } else {
                str = "application/octet-stream";
            }
            hashMap.put("Content-Type", str);
            if (uuid2.equals(uuid)) {
                hashMap.put("SOAPAction", "http://schemas.microsoft.com/DRM/2007/03/protocols/AcquireLicense");
            }
            synchronized (this.d) {
                hashMap.putAll(this.d);
            }
            return a(this.a, licenseServerUrl, keyRequest.getData(), hashMap);
        }
        throw new MediaDrmCallbackException(new DataSpec.Builder().setUri(Uri.EMPTY).build(), Uri.EMPTY, ImmutableMap.of(), 0, new IllegalStateException("No license URL"));
    }

    public byte[] executeProvisionRequest(UUID uuid, ExoMediaDrm.ProvisionRequest provisionRequest) throws MediaDrmCallbackException {
        String defaultUrl = provisionRequest.getDefaultUrl();
        String fromUtf8Bytes = Util.fromUtf8Bytes(provisionRequest.getData());
        StringBuilder sb = new StringBuilder(y2.c(fromUtf8Bytes, y2.c(defaultUrl, 15)));
        sb.append(defaultUrl);
        sb.append("&signedRequest=");
        sb.append(fromUtf8Bytes);
        return a(this.a, sb.toString(), (byte[]) null, Collections.emptyMap());
    }

    public void setKeyRequestProperty(String str, String str2) {
        Assertions.checkNotNull(str);
        Assertions.checkNotNull(str2);
        synchronized (this.d) {
            this.d.put(str, str2);
        }
    }

    public HttpMediaDrmCallback(@Nullable String str, boolean z, HttpDataSource.Factory factory) {
        Assertions.checkArgument(!z || !TextUtils.isEmpty(str));
        this.a = factory;
        this.b = str;
        this.c = z;
        this.d = new HashMap();
    }
}
