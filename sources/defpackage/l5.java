package defpackage;

import defpackage.a5;

/* renamed from: l5  reason: default package */
public final class l5 implements a5.h {
    public final /* synthetic */ a5 a;

    public l5(a5 a5Var) {
        this.a = a5Var;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0034 A[SYNTHETIC, Splitter:B:12:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0043 A[Catch:{ JSONException -> 0x00a5 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(org.json.JSONObject r9) {
        /*
            r8 = this;
            a5 r0 = r8.a
            r0.getClass()
            java.lang.String r1 = "action"
            java.lang.String r2 = "stop"
            java.lang.String r9 = r9.optString(r1, r2)     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r9 = r9.trim()     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r9 = r9.toLowerCase()     // Catch:{ JSONException -> 0x00a5 }
            android.content.Context r1 = r0.a
            r2 = 0
            if (r1 == 0) goto L_0x002d
            android.content.Context r3 = r1.getApplicationContext()     // Catch:{ JSONException -> 0x00a5 }
            boolean r3 = r3 instanceof info.dourok.voicebot.java.VoiceBotApplication     // Catch:{ JSONException -> 0x00a5 }
            if (r3 == 0) goto L_0x002d
            android.content.Context r1 = r1.getApplicationContext()     // Catch:{ JSONException -> 0x00a5 }
            info.dourok.voicebot.java.VoiceBotApplication r1 = (info.dourok.voicebot.java.VoiceBotApplication) r1     // Catch:{ JSONException -> 0x00a5 }
            x r1 = r1.getAiboxPlusWebSocketServer()     // Catch:{ JSONException -> 0x00a5 }
            goto L_0x002e
        L_0x002d:
            r1 = r2
        L_0x002e:
            java.lang.String r3 = "message"
            java.lang.String r4 = "success"
            if (r1 != 0) goto L_0x0043
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00a5 }
            r9.<init>()     // Catch:{ JSONException -> 0x00a5 }
            r0 = 0
            r9.put(r4, r0)     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r0 = "音乐服务不可用"
            r9.put(r3, r0)     // Catch:{ JSONException -> 0x00a5 }
            goto L_0x00a4
        L_0x0043:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00a5 }
            r5.<init>()     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r6 = "next"
            boolean r6 = r6.equals(r9)     // Catch:{ JSONException -> 0x00a5 }
            r7 = 1
            if (r6 == 0) goto L_0x005d
            r1.ab(r2)     // Catch:{ JSONException -> 0x00a5 }
            r5.put(r4, r7)     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r9 = "已切换到下一首"
            r5.put(r3, r9)     // Catch:{ JSONException -> 0x00a5 }
            goto L_0x00a3
        L_0x005d:
            java.lang.String r6 = "previous"
            boolean r6 = r6.equals(r9)     // Catch:{ JSONException -> 0x00a5 }
            if (r6 == 0) goto L_0x0071
            r1.ae(r2)     // Catch:{ JSONException -> 0x00a5 }
            r5.put(r4, r7)     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r9 = "已切换到上一首"
            r5.put(r3, r9)     // Catch:{ JSONException -> 0x00a5 }
            goto L_0x00a3
        L_0x0071:
            java.lang.String r6 = "resume"
            boolean r9 = r6.equals(r9)     // Catch:{ JSONException -> 0x00a5 }
            if (r9 == 0) goto L_0x0094
            android.os.Handler r9 = new android.os.Handler     // Catch:{ JSONException -> 0x00a5 }
            android.os.Looper r0 = android.os.Looper.getMainLooper()     // Catch:{ JSONException -> 0x00a5 }
            r9.<init>(r0)     // Catch:{ JSONException -> 0x00a5 }
            o r0 = new o     // Catch:{ JSONException -> 0x00a5 }
            r6 = 2
            r0.<init>(r6, r1, r2)     // Catch:{ JSONException -> 0x00a5 }
            r9.post(r0)     // Catch:{ JSONException -> 0x00a5 }
            r5.put(r4, r7)     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r9 = "已继续播放"
            r5.put(r3, r9)     // Catch:{ JSONException -> 0x00a5 }
            goto L_0x00a3
        L_0x0094:
            info.dourok.voicebot.java.audio.MusicPlayer r9 = r0.f     // Catch:{ JSONException -> 0x00a5 }
            if (r9 == 0) goto L_0x009b
            r9.stop()     // Catch:{ JSONException -> 0x00a5 }
        L_0x009b:
            r5.put(r4, r7)     // Catch:{ JSONException -> 0x00a5 }
            java.lang.String r9 = "音乐已停止"
            r5.put(r3, r9)     // Catch:{ JSONException -> 0x00a5 }
        L_0x00a3:
            r9 = r5
        L_0x00a4:
            return r9
        L_0x00a5:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Failed to execute entertainment action"
            r0.<init>(r1, r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.l5.a(org.json.JSONObject):java.lang.Object");
    }
}
