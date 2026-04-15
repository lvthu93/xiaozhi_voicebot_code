package info.dourok.voicebot.java.services;

import j$.net.URLEncoder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZingMp3Service {
    private static final String API_KEY = "88265e23d4284f25963e6eedac8fbfa3";
    private static final String BASE_URL = "https://zingmp3.vn";
    private static final String SEARCH_URL = "https://ac.zingmp3.vn/v1/web/featured";
    private static final String SEARCH_VERSION = "1.17.2";
    private static final String SECRET_KEY = "2aa2d1c561e809b267f3638c4a307aab";
    private static final String STREAM_PATH = "/api/v2/song/get/streaming";
    private static final String TAG = "ZingMp3Service";
    private static final String VERSION = "1.6.34";
    private final OkHttpClient client;

    public static class ZingMp3Song {
        public String artistName = "";
        public int duration = 0;
        public String id = "";
        public String lyricLink = "";
        public String thumb = "";
        public String title = "";
    }

    public ZingMp3Service() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.client = builder.connectTimeout(10, timeUnit).readTimeout(10, timeUnit).build();
    }

    private static void addSuppressedBackport(Throwable th, Throwable th2) {
        Class<Throwable> cls = Throwable.class;
        try {
            cls.getDeclaredMethod("addSuppressed", new Class[]{cls}).invoke(th, new Object[]{th2});
        } catch (Exception unused) {
        }
    }

    private String generateSignature(String str, String str2, String str3) {
        String hashSHA256 = hashSHA256("ctime=" + str3 + "id=" + str2 + "version=1.6.34");
        if (hashSHA256 == null) {
            return null;
        }
        return hmacSHA512(y2.x(str, hashSHA256), SECRET_KEY);
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x00ad */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String getCookie() {
        /*
            r8 = this;
            java.lang.String r0 = "zmp3_rqid="
            java.lang.String r1 = "https://zingmp3.vn"
            java.lang.String r2 = ""
            java.lang.String r3 = "Got cookie: "
            okhttp3.OkHttpClient r4 = r8.client     // Catch:{ IOException -> 0x00ae }
            okhttp3.Request$Builder r5 = new okhttp3.Request$Builder     // Catch:{ IOException -> 0x00ae }
            r5.<init>()     // Catch:{ IOException -> 0x00ae }
            okhttp3.Request$Builder r5 = r5.url((java.lang.String) r1)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r6 = "User-Agent"
            java.lang.String r7 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:144.0) Gecko/20100101 Firefox/144.0"
            okhttp3.Request$Builder r5 = r5.header(r6, r7)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r6 = "Accept"
            java.lang.String r7 = "*/*"
            okhttp3.Request$Builder r5 = r5.header(r6, r7)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r6 = "Accept-Language"
            java.lang.String r7 = "en-US,en;q=0.5"
            okhttp3.Request$Builder r5 = r5.header(r6, r7)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r6 = "Referer"
            java.lang.String r7 = "https://zingmp3.vn/"
            okhttp3.Request$Builder r5 = r5.header(r6, r7)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r6 = "Origin"
            okhttp3.Request$Builder r1 = r5.header(r6, r1)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r5 = "Sec-Fetch-Dest"
            java.lang.String r6 = "empty"
            okhttp3.Request$Builder r1 = r1.header(r5, r6)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r5 = "Sec-Fetch-Mode"
            java.lang.String r6 = "cors"
            okhttp3.Request$Builder r1 = r1.header(r5, r6)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r5 = "Sec-Fetch-Site"
            java.lang.String r6 = "same-site"
            okhttp3.Request$Builder r1 = r1.header(r5, r6)     // Catch:{ IOException -> 0x00ae }
            java.lang.String r5 = "Connection"
            java.lang.String r6 = "keep-alive"
            okhttp3.Request$Builder r1 = r1.header(r5, r6)     // Catch:{ IOException -> 0x00ae }
            okhttp3.Request r1 = r1.build()     // Catch:{ IOException -> 0x00ae }
            okhttp3.Call r1 = r4.newCall(r1)     // Catch:{ IOException -> 0x00ae }
            okhttp3.Response r1 = r1.execute()     // Catch:{ IOException -> 0x00ae }
            boolean r4 = r1.isSuccessful()     // Catch:{ all -> 0x00a7 }
            if (r4 == 0) goto L_0x00a3
            java.lang.String r4 = "Set-Cookie"
            java.lang.String r4 = r1.header(r4)     // Catch:{ all -> 0x00a7 }
            if (r4 == 0) goto L_0x00a3
            boolean r5 = r4.contains(r0)     // Catch:{ all -> 0x00a7 }
            if (r5 == 0) goto L_0x00a3
            int r0 = r4.indexOf(r0)     // Catch:{ all -> 0x00a7 }
            java.lang.String r5 = ";"
            int r5 = r4.indexOf(r5, r0)     // Catch:{ all -> 0x00a7 }
            r6 = -1
            if (r5 != r6) goto L_0x008a
            int r5 = r4.length()     // Catch:{ all -> 0x00a7 }
        L_0x008a:
            java.lang.String r0 = r4.substring(r0, r5)     // Catch:{ all -> 0x00a7 }
            java.lang.String r4 = "ZingMp3Service"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a7 }
            r5.<init>(r3)     // Catch:{ all -> 0x00a7 }
            r5.append(r0)     // Catch:{ all -> 0x00a7 }
            java.lang.String r3 = r5.toString()     // Catch:{ all -> 0x00a7 }
            android.util.Log.d(r4, r3)     // Catch:{ all -> 0x00a7 }
            r1.close()     // Catch:{ all -> 0x00a2 }
        L_0x00a2:
            return r0
        L_0x00a3:
            r1.close()     // Catch:{ all -> 0x00a6 }
        L_0x00a6:
            return r2
        L_0x00a7:
            r0 = move-exception
            if (r1 == 0) goto L_0x00ad
            r1.close()     // Catch:{ all -> 0x00ad }
        L_0x00ad:
            throw r0     // Catch:{ IOException -> 0x00ae }
        L_0x00ae:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.services.ZingMp3Service.getCookie():java.lang.String");
    }

    private String hashSHA256(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    private String hmacSHA512(String str, String str2) {
        try {
            Mac instance = Mac.getInstance("HmacSHA512");
            instance.init(new SecretKeySpec(str2.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            byte[] doFinal = instance.doFinal(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : doFinal) {
                String hexString = Integer.toHexString(b & 255);
                if (hexString.length() == 1) {
                    sb.append('0');
                }
                sb.append(hexString);
            }
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:56:0x0106 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getStreamLink(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.String r0 = "&version=1.6.34&apiKey=88265e23d4284f25963e6eedac8fbfa3&sig="
            java.lang.String r1 = "https://zingmp3.vn/api/v2/song/get/streaming?id="
            r2 = 0
            if (r10 == 0) goto L_0x0107
            boolean r3 = r10.isEmpty()
            if (r3 == 0) goto L_0x000f
            goto L_0x0107
        L_0x000f:
            java.lang.String r3 = r9.getCookie()     // Catch:{ IOException | JSONException -> 0x0107 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ IOException | JSONException -> 0x0107 }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
            java.lang.String r6 = "/api/v2/song/get/streaming"
            java.lang.String r7 = java.lang.String.valueOf(r4)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r6 = r9.generateSignature(r6, r10, r7)     // Catch:{ IOException | JSONException -> 0x0107 }
            if (r6 == 0) goto L_0x0107
            boolean r7 = r6.isEmpty()     // Catch:{ IOException | JSONException -> 0x0107 }
            if (r7 != 0) goto L_0x0107
            okhttp3.Request$Builder r7 = new okhttp3.Request$Builder     // Catch:{ IOException | JSONException -> 0x0107 }
            r7.<init>()     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException | JSONException -> 0x0107 }
            r8.<init>(r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            r8.append(r10)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r10 = "&ctime="
            r8.append(r10)     // Catch:{ IOException | JSONException -> 0x0107 }
            r8.append(r4)     // Catch:{ IOException | JSONException -> 0x0107 }
            r8.append(r0)     // Catch:{ IOException | JSONException -> 0x0107 }
            r8.append(r6)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r10 = r8.toString()     // Catch:{ IOException | JSONException -> 0x0107 }
            okhttp3.Request$Builder r10 = r7.url((java.lang.String) r10)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "User-Agent"
            java.lang.String r1 = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:144.0) Gecko/20100101 Firefox/144.0"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Accept"
            java.lang.String r1 = "*/*"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Accept-Language"
            java.lang.String r1 = "en-US,en;q=0.5"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Referer"
            java.lang.String r1 = "https://zingmp3.vn/"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Origin"
            java.lang.String r1 = "https://zingmp3.vn"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Sec-Fetch-Dest"
            java.lang.String r1 = "empty"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Sec-Fetch-Mode"
            java.lang.String r1 = "cors"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Sec-Fetch-Site"
            java.lang.String r1 = "same-site"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            java.lang.String r0 = "Connection"
            java.lang.String r1 = "keep-alive"
            okhttp3.Request$Builder r10 = r10.header(r0, r1)     // Catch:{ IOException | JSONException -> 0x0107 }
            if (r3 == 0) goto L_0x00a4
            boolean r0 = r3.isEmpty()     // Catch:{ IOException | JSONException -> 0x0107 }
            if (r0 != 0) goto L_0x00a4
            java.lang.String r0 = "Cookie"
            r10.header(r0, r3)     // Catch:{ IOException | JSONException -> 0x0107 }
        L_0x00a4:
            okhttp3.OkHttpClient r0 = r9.client     // Catch:{ IOException | JSONException -> 0x0107 }
            okhttp3.Request r10 = r10.build()     // Catch:{ IOException | JSONException -> 0x0107 }
            okhttp3.Call r10 = r0.newCall(r10)     // Catch:{ IOException | JSONException -> 0x0107 }
            okhttp3.Response r10 = r10.execute()     // Catch:{ IOException | JSONException -> 0x0107 }
            boolean r0 = r10.isSuccessful()     // Catch:{ all -> 0x0100 }
            if (r0 != 0) goto L_0x00bf
            r10.code()     // Catch:{ all -> 0x0100 }
            r10.close()     // Catch:{ all -> 0x00be }
        L_0x00be:
            return r2
        L_0x00bf:
            okhttp3.ResponseBody r0 = r10.body()     // Catch:{ all -> 0x0100 }
            java.lang.String r0 = r0.string()     // Catch:{ all -> 0x0100 }
            boolean r1 = r0.isEmpty()     // Catch:{ all -> 0x0100 }
            if (r1 == 0) goto L_0x00d1
            r10.close()     // Catch:{ all -> 0x00d0 }
        L_0x00d0:
            return r2
        L_0x00d1:
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ all -> 0x0100 }
            r1.<init>(r0)     // Catch:{ all -> 0x0100 }
            java.lang.String r0 = "data"
            org.json.JSONObject r0 = r1.optJSONObject(r0)     // Catch:{ all -> 0x0100 }
            if (r0 != 0) goto L_0x00e2
            r10.close()     // Catch:{ all -> 0x00e1 }
        L_0x00e1:
            return r2
        L_0x00e2:
            java.lang.String r1 = "128"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.optString(r1, r3)     // Catch:{ all -> 0x0100 }
            boolean r1 = r0.isEmpty()     // Catch:{ all -> 0x0100 }
            if (r1 == 0) goto L_0x00f4
            r10.close()     // Catch:{ all -> 0x00f3 }
        L_0x00f3:
            return r2
        L_0x00f4:
            java.lang.String r1 = "a128-z3.zmdcdn.me"
            java.lang.String r3 = "vnno-ne-2-tf-a128-z3.zmdcdn.me"
            java.lang.String r0 = r0.replace(r1, r3)     // Catch:{ all -> 0x0100 }
            r10.close()     // Catch:{ all -> 0x00ff }
        L_0x00ff:
            return r0
        L_0x0100:
            r0 = move-exception
            if (r10 == 0) goto L_0x0106
            r10.close()     // Catch:{ all -> 0x0106 }
        L_0x0106:
            throw r0     // Catch:{ IOException | JSONException -> 0x0107 }
        L_0x0107:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.services.ZingMp3Service.getStreamLink(java.lang.String):java.lang.String");
    }

    public List<ZingMp3Song> searchSongs(String str) {
        Response execute;
        JSONArray optJSONArray;
        JSONObject optJSONObject;
        ArrayList arrayList = new ArrayList();
        if (str != null && !str.isEmpty()) {
            try {
                OkHttpClient okHttpClient = this.client;
                Request.Builder builder = new Request.Builder();
                execute = okHttpClient.newCall(builder.url("https://ac.zingmp3.vn/v1/web/featured?query=" + URLEncoder.encode(str, "UTF-8") + "&allowCorrect=1&ctime=" + (System.currentTimeMillis() / 1000) + "&version=1.17.2").header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:144.0) Gecko/20100101 Firefox/144.0").header("Accept", "*/*").header("Accept-Language", "en-US,en;q=0.5").header("Referer", "https://zingmp3.vn/").header("Origin", BASE_URL).header("Sec-Fetch-Dest", "empty").header("Sec-Fetch-Mode", "cors").header("Sec-Fetch-Site", "same-site").header("Connection", "keep-alive").build()).execute();
                if (!execute.isSuccessful()) {
                    execute.code();
                    execute.close();
                    return arrayList;
                }
                String string = execute.body().string();
                if (string.isEmpty()) {
                    execute.close();
                    return arrayList;
                }
                JSONObject optJSONObject2 = new JSONObject(string).optJSONObject("data");
                if (optJSONObject2 == null) {
                    execute.close();
                    return arrayList;
                }
                JSONArray optJSONArray2 = optJSONObject2.optJSONArray("items");
                if (optJSONArray2 == null) {
                    execute.close();
                    return arrayList;
                }
                int i = 0;
                while (true) {
                    if (i >= optJSONArray2.length()) {
                        break;
                    }
                    JSONObject optJSONObject3 = optJSONArray2.optJSONObject(i);
                    if (!(optJSONObject3 == null || (optJSONArray = optJSONObject3.optJSONArray("items")) == null)) {
                        for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                            JSONObject optJSONObject4 = optJSONArray.optJSONObject(i2);
                            if (optJSONObject4 != null) {
                                String optString = optJSONObject4.optString("link", "");
                                if (optString.startsWith("/bai-hat/") || optString.contains("zingmp3.vn/bai-hat/")) {
                                    ZingMp3Song zingMp3Song = new ZingMp3Song();
                                    zingMp3Song.id = optJSONObject4.optString("id", "");
                                    zingMp3Song.title = optJSONObject4.optString("title", "");
                                    zingMp3Song.thumb = optJSONObject4.optString("thumb", "");
                                    zingMp3Song.lyricLink = optJSONObject4.optString("lyricLink", "");
                                    zingMp3Song.duration = optJSONObject4.optInt("duration", 0);
                                    JSONArray optJSONArray3 = optJSONObject4.optJSONArray("artists");
                                    if (!(optJSONArray3 == null || optJSONArray3.length() <= 0 || (optJSONObject = optJSONArray3.optJSONObject(0)) == null)) {
                                        zingMp3Song.artistName = optJSONObject.optString("name", "");
                                    }
                                    if (!zingMp3Song.id.isEmpty()) {
                                        arrayList.add(zingMp3Song);
                                    }
                                }
                            }
                        }
                        if (!arrayList.isEmpty()) {
                            break;
                        }
                    }
                    i++;
                }
                arrayList.size();
                execute.close();
            } catch (IOException | JSONException unused) {
            } catch (Throwable th) {
                if (execute != null) {
                    execute.close();
                }
                throw th;
            }
        }
        return arrayList;
    }
}
