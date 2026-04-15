package info.dourok.voicebot.java.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.PowerManager;

public class AlarmReceiver extends BroadcastReceiver {
    public static MediaPlayer a = null;
    public static PowerManager.WakeLock b = null;
    public static int c = -1;

    public static void b() {
        MediaPlayer mediaPlayer = a;
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    a.stop();
                }
                a.release();
            } catch (Exception unused) {
            }
            a = null;
        }
    }

    public static void c(Context context) {
        AudioManager audioManager;
        MediaPlayer mediaPlayer = a;
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    a.stop();
                }
                a.release();
            } catch (Exception unused) {
            }
            a = null;
        }
        if (!(c < 0 || context == null || (audioManager = (AudioManager) context.getSystemService("audio")) == null)) {
            audioManager.setStreamVolume(3, c, 0);
            c = -1;
        }
        PowerManager.WakeLock wakeLock = b;
        if (wakeLock != null && wakeLock.isHeld()) {
            b.release();
            b = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0088 A[Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.content.Context r7, int r8, java.lang.String r9) {
        /*
            r6 = this;
            android.media.MediaPlayer r0 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            if (r0 == 0) goto L_0x0017
            boolean r0 = r0.isPlaying()     // Catch:{ Exception -> 0x0014 }
            if (r0 == 0) goto L_0x000f
            android.media.MediaPlayer r0 = a     // Catch:{ Exception -> 0x0014 }
            r0.stop()     // Catch:{ Exception -> 0x0014 }
        L_0x000f:
            android.media.MediaPlayer r0 = a     // Catch:{ Exception -> 0x0014 }
            r0.release()     // Catch:{ Exception -> 0x0014 }
        L_0x0014:
            r0 = 0
            a = r0     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
        L_0x0017:
            android.media.MediaPlayer r0 = new android.media.MediaPlayer     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r0.<init>()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            a = r0     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            java.lang.String r0 = "sounds/alarm.mp3"
            if (r9 == 0) goto L_0x0056
            boolean r1 = r9.isEmpty()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            if (r1 != 0) goto L_0x0056
            java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r1.<init>(r9)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            boolean r1 = r1.exists()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            if (r1 == 0) goto L_0x0039
            android.media.MediaPlayer r0 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r0.setDataSource(r9)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            goto L_0x0072
        L_0x0039:
            android.content.res.AssetManager r9 = r7.getAssets()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.content.res.AssetFileDescriptor r9 = r9.openFd(r0)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.media.MediaPlayer r0 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            java.io.FileDescriptor r1 = r9.getFileDescriptor()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            long r2 = r9.getStartOffset()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            long r4 = r9.getLength()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r0.setDataSource(r1, r2, r4)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r9.close()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            goto L_0x0072
        L_0x0056:
            android.content.res.AssetManager r9 = r7.getAssets()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.content.res.AssetFileDescriptor r9 = r9.openFd(r0)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.media.MediaPlayer r0 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            java.io.FileDescriptor r1 = r9.getFileDescriptor()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            long r2 = r9.getStartOffset()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            long r4 = r9.getLength()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r0.setDataSource(r1, r2, r4)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r9.close()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
        L_0x0072:
            android.media.MediaPlayer r9 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r0 = 1
            r9.setLooping(r0)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.media.MediaPlayer r9 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r1 = 3
            r9.setAudioStreamType(r1)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            java.lang.String r9 = "audio"
            java.lang.Object r9 = r7.getSystemService(r9)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.media.AudioManager r9 = (android.media.AudioManager) r9     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            if (r9 == 0) goto L_0x00a0
            int r2 = r9.getStreamVolume(r1)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            c = r2     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            int r2 = r9.getStreamMaxVolume(r1)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            int r2 = r2 * r8
            int r2 = r2 / 100
            if (r2 >= r0) goto L_0x009b
            if (r8 <= 0) goto L_0x009b
            goto L_0x009c
        L_0x009b:
            r0 = r2
        L_0x009c:
            r2 = 0
            r9.setStreamVolume(r1, r0, r2)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
        L_0x00a0:
            android.media.MediaPlayer r9 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            ac r0 = new ac     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r0.<init>(r6, r7)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r9.setOnCompletionListener(r0)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.media.MediaPlayer r7 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            ad r9 = new ad     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r9.<init>(r6)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r7.setOnErrorListener(r9)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.media.MediaPlayer r7 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r7.prepareAsync()     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            android.media.MediaPlayer r7 = a     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            ae r9 = new ae     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r9.<init>(r6, r8)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            r7.setOnPreparedListener(r9)     // Catch:{ IOException -> 0x00c8, Exception -> 0x00c4 }
            goto L_0x00cb
        L_0x00c4:
            b()
            goto L_0x00cb
        L_0x00c8:
            b()
        L_0x00cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.receivers.AlarmReceiver.a(android.content.Context, int, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:91:0x0191  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x019e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onReceive(android.content.Context r21, android.content.Intent r22) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = r22
            java.lang.String r3 = r22.getAction()
            java.lang.String r4 = "info.dourok.voicebot.ALARM_TRIGGER"
            boolean r3 = r4.equals(r3)
            if (r3 == 0) goto L_0x01a3
            java.lang.String r3 = "alarm_id"
            r4 = -1
            int r4 = r2.getIntExtra(r3, r4)
            java.lang.String r5 = "alarm_label"
            java.lang.String r2 = r2.getStringExtra(r5)
            java.lang.String r6 = "power"
            java.lang.Object r7 = r1.getSystemService(r6)
            android.os.PowerManager r7 = (android.os.PowerManager) r7
            r8 = 600000(0x927c0, double:2.964394E-318)
            r10 = 1
            if (r7 == 0) goto L_0x0038
            java.lang.String r11 = "AlarmReceiver::WakeLock"
            android.os.PowerManager$WakeLock r7 = r7.newWakeLock(r10, r11)
            b = r7
            r7.acquire(r8)
        L_0x0038:
            info.dourok.voicebot.java.services.AlarmService r7 = new info.dourok.voicebot.java.services.AlarmService
            r7.<init>(r1)
            ab r11 = r7.getAlarmById(r4)
            if (r11 == 0) goto L_0x0048
            java.lang.String r12 = r11.c()
            goto L_0x004a
        L_0x0048:
            java.lang.String r12 = "00:00"
        L_0x004a:
            if (r11 == 0) goto L_0x004f
            int r14 = r11.g
            goto L_0x0051
        L_0x004f:
            r14 = 100
        L_0x0051:
            java.lang.String r15 = "none"
            if (r11 == 0) goto L_0x0058
            java.lang.String r8 = r11.d
            goto L_0x0059
        L_0x0058:
            r8 = r15
        L_0x0059:
            if (r11 == 0) goto L_0x005e
            java.lang.String r9 = r11.h
            goto L_0x005f
        L_0x005e:
            r9 = 0
        L_0x005f:
            if (r11 == 0) goto L_0x0064
            java.lang.String r10 = r11.i
            goto L_0x0065
        L_0x0064:
            r10 = 0
        L_0x0065:
            r13 = 0
            if (r10 == 0) goto L_0x00f1
            boolean r19 = r10.isEmpty()
            if (r19 != 0) goto L_0x00f1
            java.lang.String r2 = "audio"
            java.lang.Object r2 = r1.getSystemService(r2)     // Catch:{ Exception -> 0x00e7 }
            android.media.AudioManager r2 = (android.media.AudioManager) r2     // Catch:{ Exception -> 0x00e7 }
            if (r2 == 0) goto L_0x0091
            r3 = 3
            int r5 = r2.getStreamVolume(r3)     // Catch:{ Exception -> 0x00e7 }
            c = r5     // Catch:{ Exception -> 0x00e7 }
            int r5 = r2.getStreamMaxVolume(r3)     // Catch:{ Exception -> 0x00e7 }
            int r5 = r5 * r14
            r9 = 100
            int r5 = r5 / r9
            r9 = 1
            if (r5 >= r9) goto L_0x008e
            if (r14 <= 0) goto L_0x008e
            r5 = 1
        L_0x008e:
            r2.setStreamVolume(r3, r5, r13)     // Catch:{ Exception -> 0x00e7 }
        L_0x0091:
            java.lang.Object r2 = r1.getSystemService(r6)     // Catch:{ Exception -> 0x00e7 }
            android.os.PowerManager r2 = (android.os.PowerManager) r2     // Catch:{ Exception -> 0x00e7 }
            if (r2 == 0) goto L_0x00a8
            java.lang.String r3 = "AlarmReceiver::YouTubeAlarm"
            r5 = 1
            android.os.PowerManager$WakeLock r2 = r2.newWakeLock(r5, r3)     // Catch:{ Exception -> 0x00e7 }
            b = r2     // Catch:{ Exception -> 0x00e7 }
            r5 = 600000(0x927c0, double:2.964394E-318)
            r2.acquire(r5)     // Catch:{ Exception -> 0x00e7 }
        L_0x00a8:
            android.content.Context r2 = r21.getApplicationContext()     // Catch:{ Exception -> 0x00e7 }
            boolean r2 = r2 instanceof info.dourok.voicebot.java.VoiceBotApplication     // Catch:{ Exception -> 0x00e7 }
            if (r2 == 0) goto L_0x00e2
            android.content.Context r2 = r21.getApplicationContext()     // Catch:{ Exception -> 0x00e7 }
            info.dourok.voicebot.java.VoiceBotApplication r2 = (info.dourok.voicebot.java.VoiceBotApplication) r2     // Catch:{ Exception -> 0x00e7 }
            x r2 = r2.getAiboxPlusWebSocketServer()     // Catch:{ Exception -> 0x00e7 }
            if (r2 == 0) goto L_0x00dd
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x00d8 }
            r3.<init>()     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r5 = "action"
            java.lang.String r6 = "search_songs"
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r5 = "query"
            r3.put(r5, r10)     // Catch:{ Exception -> 0x00d8 }
            java.lang.String r5 = "autoplay"
            r6 = 1
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00d8 }
            r5 = 0
            r2.ag(r5, r3)     // Catch:{ Exception -> 0x00d8 }
            goto L_0x00eb
        L_0x00d8:
            r2 = 0
            r0.a(r1, r14, r2)     // Catch:{ Exception -> 0x00e8 }
            goto L_0x00eb
        L_0x00dd:
            r2 = 0
            r0.a(r1, r14, r2)     // Catch:{ Exception -> 0x00e8 }
            goto L_0x00eb
        L_0x00e2:
            r2 = 0
            r0.a(r1, r14, r2)     // Catch:{ Exception -> 0x00e8 }
            goto L_0x00eb
        L_0x00e7:
            r2 = 0
        L_0x00e8:
            r0.a(r1, r14, r2)
        L_0x00eb:
            r16 = r7
            r17 = r11
            goto L_0x018b
        L_0x00f1:
            android.content.Context r6 = r21.getApplicationContext()
            boolean r6 = r6 instanceof info.dourok.voicebot.java.VoiceBotApplication
            if (r6 == 0) goto L_0x0180
            android.content.Context r6 = r21.getApplicationContext()
            info.dourok.voicebot.java.VoiceBotApplication r6 = (info.dourok.voicebot.java.VoiceBotApplication) r6
            x r6 = r6.getAiboxPlusWebSocketServer()
            if (r6 == 0) goto L_0x0180
            java.util.Set<org.java_websocket.WebSocket> r10 = r6.t
            java.lang.String r13 = " - "
            r16 = r7
            java.lang.String r7 = "Báo thức: "
            r17 = r11
            java.lang.Integer r11 = java.lang.Integer.valueOf(r4)     // Catch:{ JSONException -> 0x017d }
            r6.q = r11     // Catch:{ JSONException -> 0x017d }
            r6.r = r2     // Catch:{ JSONException -> 0x017d }
            r6.s = r12     // Catch:{ JSONException -> 0x017d }
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x017d }
            r6.<init>()     // Catch:{ JSONException -> 0x017d }
            java.lang.String r11 = "type"
            r18 = r8
            java.lang.String r8 = "alarm_triggered"
            r6.put(r11, r8)     // Catch:{ JSONException -> 0x0186 }
            r6.put(r3, r4)     // Catch:{ JSONException -> 0x0186 }
            java.lang.String r3 = ""
            if (r2 == 0) goto L_0x0130
            r8 = r2
            goto L_0x0131
        L_0x0130:
            r8 = r3
        L_0x0131:
            r6.put(r5, r8)     // Catch:{ JSONException -> 0x0186 }
            java.lang.String r5 = "time"
            r6.put(r5, r12)     // Catch:{ JSONException -> 0x0186 }
            java.lang.String r5 = "message"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0186 }
            r8.<init>(r7)     // Catch:{ JSONException -> 0x0186 }
            r8.append(r12)     // Catch:{ JSONException -> 0x0186 }
            if (r2 == 0) goto L_0x014f
            boolean r7 = r2.isEmpty()     // Catch:{ JSONException -> 0x0186 }
            if (r7 != 0) goto L_0x014f
            java.lang.String r3 = r13.concat(r2)     // Catch:{ JSONException -> 0x0186 }
        L_0x014f:
            r8.append(r3)     // Catch:{ JSONException -> 0x0186 }
            java.lang.String r2 = r8.toString()     // Catch:{ JSONException -> 0x0186 }
            r6.put(r5, r2)     // Catch:{ JSONException -> 0x0186 }
            java.lang.String r2 = r6.toString()     // Catch:{ JSONException -> 0x0186 }
            java.util.Iterator r3 = r10.iterator()     // Catch:{ JSONException -> 0x0186 }
        L_0x0161:
            boolean r5 = r3.hasNext()     // Catch:{ JSONException -> 0x0186 }
            if (r5 == 0) goto L_0x0179
            java.lang.Object r5 = r3.next()     // Catch:{ JSONException -> 0x0186 }
            org.java_websocket.WebSocket r5 = (org.java_websocket.WebSocket) r5     // Catch:{ JSONException -> 0x0186 }
            if (r5 == 0) goto L_0x0161
            boolean r6 = r5.isOpen()     // Catch:{ JSONException -> 0x0186 }
            if (r6 == 0) goto L_0x0161
            defpackage.x.bb(r5, r2)     // Catch:{ JSONException -> 0x0186 }
            goto L_0x0161
        L_0x0179:
            r10.size()     // Catch:{ JSONException -> 0x0186 }
            goto L_0x0186
        L_0x017d:
            r18 = r8
            goto L_0x0186
        L_0x0180:
            r16 = r7
            r18 = r8
            r17 = r11
        L_0x0186:
            r0.a(r1, r14, r9)
            r8 = r18
        L_0x018b:
            boolean r1 = r15.equals(r8)
            if (r1 == 0) goto L_0x019e
            if (r17 == 0) goto L_0x01a3
            r1 = r17
            r2 = 0
            r1.e = r2
            r2 = r16
            r2.updateAlarm(r1)
            goto L_0x01a3
        L_0x019e:
            r2 = r16
            r2.rescheduleAlarmAfterTrigger(r4)
        L_0x01a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: info.dourok.voicebot.java.receivers.AlarmReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }
}
