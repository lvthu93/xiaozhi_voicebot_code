package defpackage;

import defpackage.a5;

/* renamed from: x4  reason: default package */
public final class x4 implements a5.h {
    public final /* synthetic */ a5 a;

    public x4(a5 a5Var) {
        this.a = a5Var;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0029 A[SYNTHETIC, Splitter:B:12:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x003a A[Catch:{ JSONException -> 0x0067 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(org.json.JSONObject r9) {
        /*
            r8 = this;
            a5 r0 = r8.a
            r0.getClass()
            java.lang.String r1 = "enable"
            r2 = 1
            boolean r9 = r9.optBoolean(r1, r2)     // Catch:{ JSONException -> 0x0067 }
            android.content.Context r0 = r0.a
            r3 = 0
            if (r0 == 0) goto L_0x0024
            android.content.Context r4 = r0.getApplicationContext()     // Catch:{ JSONException -> 0x0067 }
            boolean r4 = r4 instanceof info.dourok.voicebot.java.VoiceBotApplication     // Catch:{ JSONException -> 0x0067 }
            if (r4 == 0) goto L_0x0024
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ JSONException -> 0x0067 }
            info.dourok.voicebot.java.VoiceBotApplication r0 = (info.dourok.voicebot.java.VoiceBotApplication) r0     // Catch:{ JSONException -> 0x0067 }
            x r0 = r0.getAiboxPlusWebSocketServer()     // Catch:{ JSONException -> 0x0067 }
            goto L_0x0025
        L_0x0024:
            r0 = r3
        L_0x0025:
            java.lang.String r4 = "success"
            if (r0 != 0) goto L_0x003a
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0067 }
            r9.<init>()     // Catch:{ JSONException -> 0x0067 }
            r0 = 0
            r9.put(r4, r0)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r0 = "error"
            java.lang.String r1 = "WebSocket server not available for Bluetooth control"
            r9.put(r0, r1)     // Catch:{ JSONException -> 0x0067 }
            goto L_0x0066
        L_0x003a:
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0067 }
            r5.<init>()     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r6 = "action"
            java.lang.String r7 = "bluetooth_set_enabled"
            r5.put(r6, r7)     // Catch:{ JSONException -> 0x0067 }
            r5.put(r1, r9)     // Catch:{ JSONException -> 0x0067 }
            r0.n(r3, r5)     // Catch:{ JSONException -> 0x0067 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0067 }
            r0.<init>()     // Catch:{ JSONException -> 0x0067 }
            r0.put(r4, r2)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r1 = "message"
            if (r9 == 0) goto L_0x005b
            java.lang.String r2 = "蓝牙控制命令已发送 / Bluetooth enable command sent via WebSocket"
            goto L_0x005d
        L_0x005b:
            java.lang.String r2 = "蓝牙控制命令已发送 / Bluetooth disable command sent via WebSocket"
        L_0x005d:
            r0.put(r1, r2)     // Catch:{ JSONException -> 0x0067 }
            java.lang.String r1 = "enabled"
            r0.put(r1, r9)     // Catch:{ JSONException -> 0x0067 }
            r9 = r0
        L_0x0066:
            return r9
        L_0x0067:
            r9 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Failed to control Bluetooth"
            r0.<init>(r1, r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x4.a(org.json.JSONObject):java.lang.Object");
    }
}
