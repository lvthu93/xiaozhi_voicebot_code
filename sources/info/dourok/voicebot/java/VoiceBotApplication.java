package info.dourok.voicebot.java;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import info.dourok.voicebot.java.audio.OpusDecoder;
import info.dourok.voicebot.java.audio.OpusEncoder;
import info.dourok.voicebot.java.audio.e;
import info.dourok.voicebot.java.services.AlarmService;
import info.dourok.voicebot.java.services.OtaService;
import info.dourok.voicebot.java.services.ZaloAuthService;
import info.dourok.voicebot.java.services.ZaloMessageStorage;
import info.dourok.voicebot.java.services.ZaloMqttClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VoiceBotApplication extends Application {
    private static final String PREF_TIKTOK_REPLY_ENABLED = "tiktok_reply_enabled";
    private static final String PREF_TIKTOK_USER = "tiktok_user";
    private static final String TAG = "VoiceBotApplication";
    private x aiboxPlusWebSocketServer;
    /* access modifiers changed from: private */
    public boolean alarmsRescheduled = false;
    private final BlockingQueue<String> chatQueue = new LinkedBlockingQueue();
    private v0 controlWebServer;
    private h1 dependencies;
    private boolean isChatQueueWorkerRunning = false;
    private String latestServerVersion = null;
    private boolean tiktokReplyEnabled = false;
    /* access modifiers changed from: private */
    public Handler versionCheckHandler = null;
    private Runnable versionCheckRunnable = null;
    private ZaloAuthService zaloAuthService;
    private ExecutorService zaloExecutor;
    private ZaloMessageStorage zaloMessageStorage;
    private ZaloMqttClient zaloMqttClient;

    public class a implements Runnable {
        public final /* synthetic */ Handler c;

        public a(Handler handler) {
            this.c = handler;
        }

        public final void run() {
            Handler handler = this.c;
            VoiceBotApplication voiceBotApplication = VoiceBotApplication.this;
            if (voiceBotApplication.alarmsRescheduled) {
                Log.d(VoiceBotApplication.TAG, "Alarms already rescheduled, stopping year check");
                return;
            }
            try {
                int i = Calendar.getInstance().get(1);
                Log.d(VoiceBotApplication.TAG, "Checking current year: " + i);
                if (i > 2024) {
                    new AlarmService(voiceBotApplication).rescheduleAllAlarms();
                    voiceBotApplication.alarmsRescheduled = true;
                    return;
                }
                Log.d(VoiceBotApplication.TAG, "Year not updated yet (" + i + "), will check again in 5 seconds");
                handler.postDelayed(this, 5000);
            } catch (Exception unused) {
                if (!voiceBotApplication.alarmsRescheduled) {
                    handler.postDelayed(this, 5000);
                }
            }
        }
    }

    public class b implements Runnable {
        public b() {
        }

        public final void run() {
            VoiceBotApplication voiceBotApplication = VoiceBotApplication.this;
            voiceBotApplication.checkVersionAndDownload();
            if (voiceBotApplication.versionCheckHandler != null) {
                voiceBotApplication.versionCheckHandler.postDelayed(this, 300000);
            }
        }
    }

    /* access modifiers changed from: private */
    public void checkVersionAndDownload() {
        new Thread(new ud(this, 1)).start();
    }

    private int compareVersions(String str, String str2) {
        int i;
        int i2;
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int max = Math.max(split.length, split2.length);
        for (int i3 = 0; i3 < max; i3++) {
            if (i3 < split.length) {
                i = Integer.parseInt(split[i3]);
            } else {
                i = 0;
            }
            if (i3 < split2.length) {
                i2 = Integer.parseInt(split2[i3]);
            } else {
                i2 = 0;
            }
            if (i < i2) {
                return -1;
            }
            if (i > i2) {
                return 1;
            }
        }
        return 0;
    }

    private int countNonWhitespaceChars(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                i++;
            }
        }
        return i;
    }

    private void downloadFile(String str, String str2) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() != 200) {
                httpURLConnection.getResponseCode();
                return;
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            File file = new File(str2);
            file.getParentFile().mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int read = inputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileOutputStream.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return;
                }
            }
        } catch (Exception unused) {
        }
    }

    private void enqueueChatForAi(String str) {
        if (str != null) {
            String trim = str.trim();
            if (!trim.isEmpty()) {
                this.chatQueue.offer(trim);
                startChatQueueWorkerIfNeeded();
            }
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:22|23|24) */
    /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        java.lang.Thread.currentThread().interrupt();
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0041 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x0070 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x0084 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x00a3 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:71:0x00e6 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:85:0x010c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void forwardChatTextToAi(java.lang.String r19) {
        /*
            r18 = this;
            if (r19 == 0) goto L_0x0123
            java.lang.String r0 = r19.trim()     // Catch:{ Exception -> 0x0120 }
            boolean r0 = r0.isEmpty()     // Catch:{ Exception -> 0x0120 }
            if (r0 == 0) goto L_0x000e
            goto L_0x0123
        L_0x000e:
            r0 = r18
            h1 r1 = r0.dependencies     // Catch:{ Exception -> 0x0122 }
            if (r1 == 0) goto L_0x0019
            t9 r1 = r1.i()     // Catch:{ Exception -> 0x0122 }
            goto L_0x001a
        L_0x0019:
            r1 = 0
        L_0x001a:
            if (r1 != 0) goto L_0x001d
            return
        L_0x001d:
            m1 r2 = r1.b()     // Catch:{ Exception -> 0x0122 }
            m1 r3 = defpackage.m1.SPEAKING
            r4 = 200(0xc8, double:9.9E-322)
            r6 = 60000(0xea60, double:2.9644E-319)
            if (r2 != r3) goto L_0x004b
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            long r8 = r8 + r6
        L_0x002f:
            m1 r10 = r1.b()     // Catch:{ Exception -> 0x0122 }
            if (r10 != r3) goto L_0x0048
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            int r12 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r12 >= 0) goto L_0x0048
            java.lang.Thread.sleep(r4)     // Catch:{ InterruptedException -> 0x0041 }
            goto L_0x002f
        L_0x0041:
            java.lang.Thread r8 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0122 }
            r8.interrupt()     // Catch:{ Exception -> 0x0122 }
        L_0x0048:
            r1.b()     // Catch:{ Exception -> 0x0122 }
        L_0x004b:
            m1 r8 = defpackage.m1.IDLE     // Catch:{ Exception -> 0x0122 }
            r9 = 5000(0x1388, double:2.4703E-320)
            r11 = 100
            m1 r13 = defpackage.m1.LISTENING
            if (r2 != r8) goto L_0x008c
            r1.e()     // Catch:{ Exception -> 0x0122 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
        L_0x005c:
            m1 r2 = r1.b()     // Catch:{ Exception -> 0x0122 }
            if (r2 == r13) goto L_0x0077
            long r16 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            long r16 = r16 - r14
            int r2 = (r16 > r9 ? 1 : (r16 == r9 ? 0 : -1))
            if (r2 >= 0) goto L_0x0077
            java.lang.Thread.sleep(r11)     // Catch:{ InterruptedException -> 0x0070 }
            goto L_0x005c
        L_0x0070:
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0122 }
            r2.interrupt()     // Catch:{ Exception -> 0x0122 }
        L_0x0077:
            m1 r2 = r1.b()     // Catch:{ Exception -> 0x0122 }
            if (r2 == r13) goto L_0x007e
            goto L_0x008c
        L_0x007e:
            r14 = 500(0x1f4, double:2.47E-321)
            java.lang.Thread.sleep(r14)     // Catch:{ InterruptedException -> 0x0084 }
            goto L_0x008c
        L_0x0084:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0122 }
            r1.interrupt()     // Catch:{ Exception -> 0x0122 }
            return
        L_0x008c:
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            long r14 = r14 + r9
        L_0x0091:
            m1 r2 = r1.b()     // Catch:{ Exception -> 0x0122 }
            if (r2 == r13) goto L_0x00aa
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            int r2 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1))
            if (r2 >= 0) goto L_0x00aa
            java.lang.Thread.sleep(r11)     // Catch:{ InterruptedException -> 0x00a3 }
            goto L_0x0091
        L_0x00a3:
            java.lang.Thread r2 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0122 }
            r2.interrupt()     // Catch:{ Exception -> 0x0122 }
        L_0x00aa:
            java.lang.String r2 = r19.trim()     // Catch:{ Exception -> 0x0122 }
            android.os.Handler r8 = new android.os.Handler     // Catch:{ Exception -> 0x0122 }
            android.os.Looper r9 = android.os.Looper.getMainLooper()     // Catch:{ Exception -> 0x0122 }
            r8.<init>(r9)     // Catch:{ Exception -> 0x0122 }
            java.util.concurrent.CountDownLatch r9 = new java.util.concurrent.CountDownLatch     // Catch:{ Exception -> 0x0122 }
            r10 = 1
            r9.<init>(r10)     // Catch:{ Exception -> 0x0122 }
            d6 r10 = new d6     // Catch:{ Exception -> 0x0122 }
            r13 = 6
            r10.<init>(r13, r1, r2, r9)     // Catch:{ Exception -> 0x0122 }
            r8.post(r10)     // Catch:{ Exception -> 0x0122 }
            java.util.concurrent.TimeUnit r2 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ InterruptedException -> 0x0118 }
            r13 = 2
            r9.await(r13, r2)     // Catch:{ InterruptedException -> 0x0118 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            r13 = 10000(0x2710, double:4.9407E-320)
            long r8 = r8 + r13
        L_0x00d4:
            m1 r2 = r1.b()     // Catch:{ Exception -> 0x0122 }
            if (r2 == r3) goto L_0x00ee
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            int r2 = (r13 > r8 ? 1 : (r13 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x00ee
            java.lang.Thread.sleep(r11)     // Catch:{ InterruptedException -> 0x00e6 }
            goto L_0x00d4
        L_0x00e6:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0122 }
            r1.interrupt()     // Catch:{ Exception -> 0x0122 }
            return
        L_0x00ee:
            m1 r2 = r1.b()     // Catch:{ Exception -> 0x0122 }
            if (r2 == r3) goto L_0x00f5
            return
        L_0x00f5:
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            long r8 = r8 + r6
        L_0x00fa:
            m1 r2 = r1.b()     // Catch:{ Exception -> 0x0122 }
            if (r2 != r3) goto L_0x0114
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0122 }
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 >= 0) goto L_0x0114
            java.lang.Thread.sleep(r4)     // Catch:{ InterruptedException -> 0x010c }
            goto L_0x00fa
        L_0x010c:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0122 }
            r1.interrupt()     // Catch:{ Exception -> 0x0122 }
            return
        L_0x0114:
            r1.b()     // Catch:{ Exception -> 0x0122 }
            goto L_0x0122
        L_0x0118:
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ Exception -> 0x0122 }
            r1.interrupt()     // Catch:{ Exception -> 0x0122 }
            return
        L_0x0120:
            r0 = r18
        L_0x0122:
            return
        L_0x0123:
            r0 = r18
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.VoiceBotApplication.forwardChatTextToAi(java.lang.String):void");
    }

    public static h1 getDependencies(Application application) {
        return h1.d(application);
    }

    private void handleZaloMessage(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("type", "");
            String optString2 = jSONObject.optString("source", "");
            if ("chat".equals(optString)) {
                if (!this.tiktokReplyEnabled) {
                    Log.d(TAG, "TikTok reply is disabled, ignoring chat payload");
                    return;
                }
                String trim = jSONObject.optString("user", "").trim();
                String trim2 = jSONObject.optString("comment", "").trim();
                if (!trim2.isEmpty() && countNonWhitespaceChars(trim) + countNonWhitespaceChars(trim2) <= 33) {
                    if (!trim.isEmpty()) {
                        trim2 = trim + ": " + trim2;
                    }
                    enqueueChatForAi(trim2);
                }
            } else if (!"zalo_notification".equals(optString)) {
            } else {
                if ("zalo".equals(optString2)) {
                    String optString3 = jSONObject.optString("sender", "");
                    String optString4 = jSONObject.optString("text", "");
                    String optString5 = jSONObject.optString("chat_id", "");
                    String optString6 = jSONObject.optString("message_id", "");
                    long optLong = jSONObject.optLong("time", System.currentTimeMillis());
                    String optString7 = jSONObject.optString("audio_url", "");
                    ZaloMessageStorage zaloMessageStorage2 = this.zaloMessageStorage;
                    if (zaloMessageStorage2 != null) {
                        zaloMessageStorage2.saveMessage(optString3, optString4, optString5, optString6, optLong, optString7);
                    }
                    if (optString7 != null && !optString7.isEmpty()) {
                        playZaloAudio(optString7);
                    }
                }
            }
        } catch (JSONException unused) {
        }
    }

    /* access modifiers changed from: private */
    public void lambda$checkVersionAndDownload$1() {
        try {
            String b2 = oa.b("https://r1.truongblack.me/version");
            if (b2 == null) {
                return;
            }
            if (!b2.isEmpty()) {
                JSONObject jSONObject = new JSONObject(b2);
                String string = jSONObject.getString("version");
                JSONArray jSONArray = jSONObject.getJSONArray("files");
                if (jSONArray.length() != 0) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(0);
                    String string2 = jSONObject2.getString("name");
                    String string3 = jSONObject2.getString("url");
                    this.latestServerVersion = string;
                    x xVar = this.aiboxPlusWebSocketServer;
                    if (xVar != null) {
                        for (WebSocket next : xVar.t) {
                            if (next != null && next.isOpen()) {
                                xVar.v(next);
                            }
                        }
                    }
                    int compareVersions = compareVersions("4.0.0", string);
                    String str = "/sdcard/" + string2;
                    File file = new File(str);
                    if (compareVersions < 0) {
                        if (!file.exists()) {
                            downloadFile(string3, str);
                        }
                    } else if (compareVersions == 0 && file.exists()) {
                        file.delete();
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public static void lambda$forwardChatTextToAi$5(t9 t9Var, String str, CountDownLatch countDownLatch) {
        try {
            i9 i9Var = t9Var.f.get();
            if (i9Var != null) {
                if (str == null) {
                    str = "";
                }
                i9Var.d.execute(new p9(i9Var, str));
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            countDownLatch.countDown();
            throw th;
        }
        countDownLatch.countDown();
    }

    /* access modifiers changed from: private */
    public static /* synthetic */ void lambda$playZaloAudio$8(String str) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(str);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new vd());
            mediaPlayer.setOnErrorListener(new wd());
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void lambda$startAiboxPlusWebSocketServer$0() {
        try {
            h1 h1Var = this.dependencies;
            if (h1Var != null) {
                this.aiboxPlusWebSocketServer.f = h1Var.e();
                Log.d(TAG, "McpDeviceServer set on AiboxPlusWebSocketServer");
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$startChatQueueWorkerIfNeeded$4() {
        while (true) {
            try {
                String poll = this.chatQueue.poll();
                if (poll == null) {
                    break;
                }
                forwardChatTextToAi(poll);
            } catch (Throwable th) {
                synchronized (this) {
                    this.isChatQueueWorkerRunning = false;
                    if (!this.chatQueue.isEmpty()) {
                        startChatQueueWorkerIfNeeded();
                    }
                    throw th;
                }
            }
        }
        synchronized (this) {
            this.isChatQueueWorkerRunning = false;
            if (!this.chatQueue.isEmpty()) {
                startChatQueueWorkerIfNeeded();
            }
        }
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$startZaloMqtt$2(String str, String str2) {
        handleZaloMessage(str2);
    }

    /* access modifiers changed from: private */
    public /* synthetic */ void lambda$startZaloMqtt$3() {
        try {
            ZaloAuthService.ZaloAuthResult authenticate = this.zaloAuthService.authenticate(true);
            if (authenticate.success) {
                String mqttUrl = this.zaloAuthService.getMqttUrl();
                String mqttPassword = this.zaloAuthService.getMqttPassword();
                String str = authenticate.userTiktok;
                if (str != null && !str.isEmpty()) {
                    getSharedPreferences("voicebot_prefs", 0).edit().putString(PREF_TIKTOK_USER, str).apply();
                }
                if (mqttUrl.isEmpty()) {
                    return;
                }
                if (!mqttPassword.isEmpty()) {
                    int indexOf = mqttUrl.indexOf(58);
                    int i = 1883;
                    if (indexOf > 0) {
                        String substring = mqttUrl.substring(0, indexOf);
                        try {
                            i = Integer.parseInt(mqttUrl.substring(indexOf + 1));
                        } catch (NumberFormatException unused) {
                        }
                        mqttUrl = substring;
                    }
                    String d = new c2(this).d();
                    if (d == null) {
                        return;
                    }
                    if (!d.isEmpty()) {
                        ZaloMqttClient zaloMqttClient2 = new ZaloMqttClient(this);
                        this.zaloMqttClient = zaloMqttClient2;
                        zaloMqttClient2.setMessageCallback(new i2(12, this));
                        this.zaloMqttClient.start(mqttUrl, i, d, mqttPassword);
                    }
                }
            }
        } catch (Exception unused2) {
        }
    }

    private void playZaloAudio(String str) {
        new Thread(new qb(9, str)).start();
    }

    private boolean sendTiktokReplyControlMessage(String str) {
        c2 c2Var;
        String d;
        ZaloMqttClient zaloMqttClient2 = this.zaloMqttClient;
        if (zaloMqttClient2 != null && zaloMqttClient2.isConnected()) {
            try {
                h1 h1Var = this.dependencies;
                if (h1Var != null) {
                    c2Var = h1Var.c();
                } else {
                    c2Var = null;
                }
                if (!(c2Var == null || (d = c2Var.d()) == null)) {
                    if (!d.isEmpty()) {
                        String upperCase = d.replace(":", "_").toUpperCase();
                        String trim = getSharedPreferences("voicebot_prefs", 0).getString(PREF_TIKTOK_USER, "").trim();
                        if (trim.isEmpty()) {
                            return false;
                        }
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("action", str);
                        jSONObject.put("username", trim);
                        jSONObject.put("topic", "devices/p2p/" + upperCase);
                        return this.zaloMqttClient.publish("device/tiktok/server", jSONObject.toString());
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private void startAiboxPlusWebSocketServer() {
        Log.d(TAG, "startAiboxPlusWebSocketServer() called");
        try {
            Log.d(TAG, "Creating AiboxPlusWebSocketServer instance...");
            this.aiboxPlusWebSocketServer = new x(this);
            new Handler(Looper.getMainLooper()).postDelayed(new ud(this, 3), 1000);
            this.aiboxPlusWebSocketServer.start();
        } catch (Exception e) {
            throw e;
        } catch (Exception e2) {
            e2.getMessage();
        }
    }

    private void startAlarmRescheduleChecker() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new a(handler), 10000);
    }

    private synchronized void startChatQueueWorkerIfNeeded() {
        if (!this.isChatQueueWorkerRunning) {
            this.isChatQueueWorkerRunning = true;
            if (this.zaloExecutor == null) {
                this.zaloExecutor = Executors.newSingleThreadExecutor();
            }
            this.zaloExecutor.execute(new ud(this, 0));
        }
    }

    private void startControlWebServer() {
        Log.d(TAG, "startControlWebServer() called");
        try {
            Log.d(TAG, "Creating ControlWebServer instance...");
            this.controlWebServer = new v0(this);
            Log.d(TAG, "ControlWebServer instance created, calling startServer()...");
            this.controlWebServer.m();
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e2) {
            e2.getMessage();
        }
    }

    private void startVersionChecker() {
        try {
            Handler handler = new Handler(Looper.getMainLooper());
            this.versionCheckHandler = handler;
            b bVar = new b();
            this.versionCheckRunnable = bVar;
            handler.postDelayed(bVar, 30000);
        } catch (Exception unused) {
        }
    }

    private void startZaloMqtt() {
        this.zaloExecutor = Executors.newSingleThreadExecutor();
        this.zaloAuthService = new ZaloAuthService(this);
        this.zaloExecutor.execute(new ud(this, 2));
    }

    public x getAiboxPlusWebSocketServer() {
        return this.aiboxPlusWebSocketServer;
    }

    public String getLatestServerVersion() {
        return this.latestServerVersion;
    }

    public String getTiktokUser() {
        return getSharedPreferences("voicebot_prefs", 0).getString(PREF_TIKTOK_USER, "");
    }

    public ZaloMqttClient getZaloMqttClient() {
        return this.zaloMqttClient;
    }

    public boolean isTiktokReplyEnabled() {
        return this.tiktokReplyEnabled;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(21:0|c|8|(2:9|10)|13|(3:15|16|(14:22|39|40|41|42|43|44|45|46|47|48|49|50|52))|23|(2:24|(2:26|(1:61)(7:62|30|(1:32)|66|33|(1:35)|36))(1:63))|37|39|40|41|42|43|44|45|46|47|(2:48|49)|50|52) */
    /* JADX WARNING: Can't wrap try/catch for region: R(23:0|c|8|9|10|13|(3:15|16|(14:22|39|40|41|42|43|44|45|46|47|48|49|50|52))|23|(2:24|(2:26|(1:61)(7:62|30|(1:32)|66|33|(1:35)|36))(1:63))|37|39|40|41|42|43|44|45|46|47|48|49|50|52) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x00e2 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x00e5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x00e8 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x00eb */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate() {
        /*
            r9 = this;
            super.onCreate()
            java.lang.String r0 = "VoiceBotApplication"
            java.lang.String r1 = "VoiceBot Application starting..."
            android.util.Log.d(r0, r1)
            java.lang.Class<oa> r0 = defpackage.oa.class
            monitor-enter(r0)
            java.lang.Class<oa> r1 = defpackage.oa.class
            monitor-enter(r1)     // Catch:{ all -> 0x010e }
            monitor-exit(r1)     // Catch:{ all -> 0x010b }
            monitor-exit(r0)
            android.content.Context r0 = r9.getApplicationContext()
            info.dourok.voicebot.java.data.model.a.a = r0
            android.content.Context r0 = r0.getApplicationContext()
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x0029 }
            java.lang.String r1 = "wifi"
            java.lang.Object r0 = r0.getSystemService(r1)     // Catch:{ Exception -> 0x0029 }
            android.net.wifi.WifiManager r0 = (android.net.wifi.WifiManager) r0     // Catch:{ Exception -> 0x0029 }
            goto L_0x002a
        L_0x0029:
            r0 = 0
        L_0x002a:
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x004c
            android.net.wifi.WifiInfo r0 = r0.getConnectionInfo()     // Catch:{ Exception -> 0x00a8 }
            if (r0 == 0) goto L_0x004c
            java.lang.String r3 = r0.getMacAddress()     // Catch:{ Exception -> 0x00a8 }
            if (r3 == 0) goto L_0x004c
            java.lang.String r3 = r0.getMacAddress()     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r4 = "02:00:00:00:00:00"
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x00a8 }
            if (r3 != 0) goto L_0x004c
            java.lang.String r0 = r0.getMacAddress()     // Catch:{ Exception -> 0x00a8 }
            goto L_0x00d7
        L_0x004c:
            java.util.Enumeration r0 = java.net.NetworkInterface.getNetworkInterfaces()     // Catch:{ Exception -> 0x00a8 }
            java.util.ArrayList r0 = java.util.Collections.list(r0)     // Catch:{ Exception -> 0x00a8 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ Exception -> 0x00a8 }
        L_0x0058:
            boolean r3 = r0.hasNext()     // Catch:{ Exception -> 0x00a8 }
            if (r3 == 0) goto L_0x00a8
            java.lang.Object r3 = r0.next()     // Catch:{ Exception -> 0x00a8 }
            java.net.NetworkInterface r3 = (java.net.NetworkInterface) r3     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r4 = r3.getName()     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r5 = "wlan0"
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ Exception -> 0x00a8 }
            if (r4 == 0) goto L_0x0058
            byte[] r3 = r3.getHardwareAddress()     // Catch:{ Exception -> 0x00a8 }
            if (r3 == 0) goto L_0x0058
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a8 }
            r0.<init>()     // Catch:{ Exception -> 0x00a8 }
            int r4 = r3.length     // Catch:{ Exception -> 0x00a8 }
            r5 = 0
        L_0x007d:
            if (r5 >= r4) goto L_0x0095
            byte r6 = r3[r5]     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r7 = "%02X:"
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x00a8 }
            java.lang.Byte r6 = java.lang.Byte.valueOf(r6)     // Catch:{ Exception -> 0x00a8 }
            r8[r1] = r6     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r6 = java.lang.String.format(r7, r8)     // Catch:{ Exception -> 0x00a8 }
            r0.append(r6)     // Catch:{ Exception -> 0x00a8 }
            int r5 = r5 + 1
            goto L_0x007d
        L_0x0095:
            int r3 = r0.length()     // Catch:{ Exception -> 0x00a8 }
            if (r3 <= 0) goto L_0x00a3
            int r3 = r0.length()     // Catch:{ Exception -> 0x00a8 }
            int r3 = r3 - r2
            r0.deleteCharAt(r3)     // Catch:{ Exception -> 0x00a8 }
        L_0x00a3:
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00a8 }
            goto L_0x00d7
        L_0x00a8:
            java.util.Random r0 = new java.util.Random
            r0.<init>()
            r3 = 3
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r4 = 256(0x100, float:3.59E-43)
            int r5 = r0.nextInt(r4)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r3[r1] = r5
            int r5 = r0.nextInt(r4)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r3[r2] = r5
            int r0 = r0.nextInt(r4)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2 = 2
            r3[r2] = r0
            java.lang.String r0 = "02:00:00:%02x:%02x:%02x"
            java.lang.String r0 = java.lang.String.format(r0, r3)
        L_0x00d7:
            info.dourok.voicebot.java.data.model.a.b = r0
            h1 r0 = defpackage.h1.d(r9)
            r9.dependencies = r0
            r9.startControlWebServer()     // Catch:{ Exception -> 0x00e2 }
        L_0x00e2:
            r9.startAiboxPlusWebSocketServer()     // Catch:{ Exception -> 0x00e5 }
        L_0x00e5:
            r9.startAlarmRescheduleChecker()     // Catch:{ Exception -> 0x00e8 }
        L_0x00e8:
            r9.startVersionChecker()     // Catch:{ Exception -> 0x00eb }
        L_0x00eb:
            info.dourok.voicebot.java.services.ZaloMessageStorage r0 = new info.dourok.voicebot.java.services.ZaloMessageStorage     // Catch:{ Exception -> 0x00f5 }
            r0.<init>(r9)     // Catch:{ Exception -> 0x00f5 }
            r9.zaloMessageStorage = r0     // Catch:{ Exception -> 0x00f5 }
            r9.startZaloMqtt()     // Catch:{ Exception -> 0x00f5 }
        L_0x00f5:
            java.lang.String r0 = "voicebot_prefs"
            android.content.SharedPreferences r0 = r9.getSharedPreferences(r0, r1)
            java.lang.String r2 = "tiktok_reply_enabled"
            boolean r1 = r0.getBoolean(r2, r1)
            r9.tiktokReplyEnabled = r1
            java.lang.String r1 = "tiktok_user"
            java.lang.String r2 = ""
            r0.getString(r1, r2)
            return
        L_0x010b:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x010b }
            throw r2     // Catch:{ all -> 0x010e }
        L_0x010e:
            r1 = move-exception
            monitor-exit(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.VoiceBotApplication.onCreate():void");
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.dependencies.getClass();
        h1.f();
        System.gc();
    }

    public void onTerminate() {
        ExecutorService executorService;
        super.onTerminate();
        Log.d(TAG, "Application terminating...");
        v0 v0Var = this.controlWebServer;
        if (v0Var != null) {
            v0Var.stop();
            this.controlWebServer = null;
        }
        x xVar = this.aiboxPlusWebSocketServer;
        if (xVar != null) {
            try {
                xVar.stop();
                this.aiboxPlusWebSocketServer = null;
            } catch (Exception unused) {
            }
        }
        h1 h1Var = this.dependencies;
        if (h1Var != null) {
            k1 k1Var = h1Var.d;
            if (!(k1Var == null || (executorService = k1Var.c) == null || executorService.isShutdown())) {
                executorService.shutdown();
            }
            info.dourok.voicebot.java.audio.b bVar = h1Var.b;
            if (bVar != null) {
                bVar.a();
                e eVar = bVar.a;
                if (eVar != null) {
                    eVar.a();
                }
                OpusEncoder opusEncoder = bVar.c;
                if (opusEncoder != null) {
                    opusEncoder.d();
                }
                OpusDecoder opusDecoder = bVar.b;
                if (opusDecoder != null) {
                    opusDecoder.c();
                }
            }
            e eVar2 = h1Var.c;
            if (eVar2 != null) {
                eVar2.a();
            }
            OpusEncoder opusEncoder2 = h1Var.h;
            if (opusEncoder2 != null) {
                opusEncoder2.d();
            }
            OpusDecoder opusDecoder2 = h1Var.g;
            if (opusDecoder2 != null) {
                opusDecoder2.c();
            }
            i9 i9Var = h1Var.j;
            if (i9Var != null) {
                i9Var.b();
                h1Var.j.c();
            }
            t9 t9Var = h1Var.k;
            if (t9Var != null && !t9Var.l) {
                t9Var.l = true;
                i9 andSet = t9Var.f.getAndSet((Object) null);
                if (andSet != null) {
                    try {
                        andSet.b();
                        andSet.c();
                    } catch (Exception unused2) {
                    }
                }
                t9Var.a = null;
                t9Var.b = null;
            }
            OtaService otaService = h1Var.i;
            if (otaService != null) {
                otaService.dispose();
            }
            i9 i9Var2 = h1Var.j;
            if (i9Var2 != null) {
                i9Var2.b();
                h1Var.j.c();
            }
            t9 t9Var2 = h1Var.k;
            if (t9Var2 != null && !t9Var2.l) {
                t9Var2.l = true;
                i9 andSet2 = t9Var2.f.getAndSet((Object) null);
                if (andSet2 != null) {
                    try {
                        andSet2.b();
                        andSet2.c();
                    } catch (Exception unused3) {
                    }
                }
                t9Var2.a = null;
                t9Var2.b = null;
            }
            OtaService otaService2 = h1Var.i;
            if (otaService2 != null) {
                otaService2.dispose();
            }
            ExecutorService executorService2 = h1Var.n;
            if (executorService2 != null && !executorService2.isShutdown()) {
                executorService2.shutdown();
            }
        }
    }

    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        this.dependencies.getClass();
        h1.f();
        if (i == 15) {
            System.gc();
        } else if (i == 20) {
            Log.d(TAG, "UI hidden - trimming UI resources");
        } else if (i == 40) {
            Log.d(TAG, "App backgrounded - trimming background resources");
        } else if (i == 60) {
            System.gc();
        } else if (i == 80) {
            System.gc();
        }
    }

    public boolean setTiktokReplyEnabled(boolean z) {
        if (z && getSharedPreferences("voicebot_prefs", 0).getString(PREF_TIKTOK_USER, "").trim().isEmpty()) {
            return false;
        }
        this.tiktokReplyEnabled = z;
        getSharedPreferences("voicebot_prefs", 0).edit().putBoolean(PREF_TIKTOK_REPLY_ENABLED, z).apply();
        if (z) {
            return sendTiktokReplyControlMessage("open");
        }
        boolean sendTiktokReplyControlMessage = sendTiktokReplyControlMessage("close");
        synchronized (this.chatQueue) {
            this.chatQueue.clear();
        }
        return sendTiktokReplyControlMessage;
    }

    public void setTiktokUser(String str) {
        if (str == null) {
            str = "";
        }
        getSharedPreferences("voicebot_prefs", 0).edit().putString(PREF_TIKTOK_USER, str.trim()).apply();
    }

    public h1 getDependencies() {
        return this.dependencies;
    }
}
