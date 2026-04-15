package defpackage;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executors;

/* renamed from: de  reason: default package */
public final class de {
    static {
        Executors.newCachedThreadPool();
        new Handler(Looper.getMainLooper());
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:40|41|42|43) */
    /* JADX WARNING: Can't wrap try/catch for region: R(5:35|36|37|38|39) */
    /* JADX WARNING: Can't wrap try/catch for region: R(6:29|30|44|45|46|47) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x008b */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x0092 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x0099 */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0088 A[SYNTHETIC, Splitter:B:35:0x0088] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x008f A[SYNTHETIC, Splitter:B:40:0x008f] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:42:0x0092=Splitter:B:42:0x0092, B:37:0x008b=Splitter:B:37:0x008b} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r6) {
        /*
            java.lang.String r0 = "https://vov.truongblack.me/stream_pcm?song="
            r1 = 0
            if (r6 == 0) goto L_0x00a1
            boolean r2 = r6.isEmpty()
            if (r2 == 0) goto L_0x000d
            goto L_0x00a1
        L_0x000d:
            java.lang.String r6 = r6.toLowerCase()     // Catch:{ Exception -> 0x00a1 }
            java.net.URL r2 = new java.net.URL     // Catch:{ Exception -> 0x00a1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a1 }
            r3.<init>(r0)     // Catch:{ Exception -> 0x00a1 }
            r3.append(r6)     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r6 = r3.toString()     // Catch:{ Exception -> 0x00a1 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x00a1 }
            java.net.URLConnection r6 = r2.openConnection()     // Catch:{ Exception -> 0x00a1 }
            java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6     // Catch:{ Exception -> 0x00a1 }
            java.lang.String r0 = "GET"
            r6.setRequestMethod(r0)     // Catch:{ all -> 0x009a }
            r0 = 5000(0x1388, float:7.006E-42)
            r6.setConnectTimeout(r0)     // Catch:{ all -> 0x009a }
            r6.setReadTimeout(r0)     // Catch:{ all -> 0x009a }
            int r0 = r6.getResponseCode()     // Catch:{ all -> 0x009a }
            r2 = 200(0xc8, float:2.8E-43)
            if (r0 == r2) goto L_0x0041
            r6.disconnect()     // Catch:{ Exception -> 0x00a1 }
            return r1
        L_0x0041:
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ all -> 0x009a }
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ all -> 0x009a }
            java.io.InputStream r3 = r6.getInputStream()     // Catch:{ all -> 0x009a }
            r2.<init>(r3)     // Catch:{ all -> 0x009a }
            r0.<init>(r2)     // Catch:{ all -> 0x009a }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x007d }
            r2.<init>()     // Catch:{ all -> 0x007d }
        L_0x0054:
            java.lang.String r3 = r0.readLine()     // Catch:{ all -> 0x007d }
            if (r3 == 0) goto L_0x005e
            r2.append(r3)     // Catch:{ all -> 0x007d }
            goto L_0x0054
        L_0x005e:
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x007d }
            if (r2 == 0) goto L_0x007f
            java.lang.String r3 = "\"audio_url\":\""
            int r3 = r2.indexOf(r3)     // Catch:{ all -> 0x007d }
            r4 = -1
            if (r3 == r4) goto L_0x007f
            int r3 = r3 + 13
            java.lang.String r5 = "\""
            int r5 = r2.indexOf(r5, r3)     // Catch:{ all -> 0x007d }
            if (r5 != r4) goto L_0x0078
            goto L_0x007f
        L_0x0078:
            java.lang.String r2 = r2.substring(r3, r5)     // Catch:{ all -> 0x007d }
            goto L_0x0080
        L_0x007d:
            r2 = move-exception
            goto L_0x0096
        L_0x007f:
            r2 = r1
        L_0x0080:
            if (r2 == 0) goto L_0x008f
            boolean r3 = r2.isEmpty()     // Catch:{ all -> 0x007d }
            if (r3 != 0) goto L_0x008f
            r0.close()     // Catch:{ Exception -> 0x008b }
        L_0x008b:
            r6.disconnect()     // Catch:{ Exception -> 0x00a1 }
            return r2
        L_0x008f:
            r0.close()     // Catch:{ Exception -> 0x0092 }
        L_0x0092:
            r6.disconnect()     // Catch:{ Exception -> 0x00a1 }
            goto L_0x00a1
        L_0x0096:
            r0.close()     // Catch:{ Exception -> 0x0099 }
        L_0x0099:
            throw r2     // Catch:{ all -> 0x009a }
        L_0x009a:
            r0 = move-exception
            if (r6 == 0) goto L_0x00a0
            r6.disconnect()     // Catch:{ Exception -> 0x00a1 }
        L_0x00a0:
            throw r0     // Catch:{ Exception -> 0x00a1 }
        L_0x00a1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.de.a(java.lang.String):java.lang.String");
    }
}
