package defpackage;

import defpackage.a5;

/* renamed from: o5  reason: default package */
public final class o5 implements a5.h {
    public final /* synthetic */ a5 a;

    public o5(a5 a5Var) {
        this.a = a5Var;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0043 A[Catch:{ Exception -> 0x009b }] */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0051 A[SYNTHETIC, Splitter:B:13:0x0051] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(org.json.JSONObject r10) {
        /*
            r9 = this;
            a5 r0 = r9.a
            r0.getClass()
            java.lang.String r1 = "message"
            java.lang.String r2 = "success"
            java.lang.String r3 = "Đang tìm kiếm và phát YouTube: "
            java.lang.String r4 = "Failed to connect to YouTube service: "
            java.lang.String r5 = "播放YouTube音乐失败: "
            java.lang.Thread r6 = java.lang.Thread.currentThread()
            r6.getName()
            r6 = 0
            java.lang.String r7 = "query"
            java.lang.String r8 = ""
            java.lang.String r10 = r10.optString(r7, r8)     // Catch:{ Exception -> 0x009b }
            java.lang.String r10 = r10.trim()     // Catch:{ Exception -> 0x009b }
            boolean r7 = r10.isEmpty()     // Catch:{ Exception -> 0x009b }
            if (r7 != 0) goto L_0x0093
            android.content.Context r0 = r0.a
            if (r0 == 0) goto L_0x0040
            android.content.Context r7 = r0.getApplicationContext()     // Catch:{ Exception -> 0x009b }
            boolean r7 = r7 instanceof info.dourok.voicebot.java.VoiceBotApplication     // Catch:{ Exception -> 0x009b }
            if (r7 == 0) goto L_0x0040
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x009b }
            info.dourok.voicebot.java.VoiceBotApplication r0 = (info.dourok.voicebot.java.VoiceBotApplication) r0     // Catch:{ Exception -> 0x009b }
            x r0 = r0.getAiboxPlusWebSocketServer()     // Catch:{ Exception -> 0x009b }
            goto L_0x0041
        L_0x0040:
            r0 = 0
        L_0x0041:
            if (r0 != 0) goto L_0x0051
            org.json.JSONObject r10 = new org.json.JSONObject     // Catch:{ Exception -> 0x009b }
            r10.<init>()     // Catch:{ Exception -> 0x009b }
            r10.put(r2, r6)     // Catch:{ Exception -> 0x009b }
            java.lang.String r0 = "YouTube service not available"
            r10.put(r1, r0)     // Catch:{ Exception -> 0x009b }
            goto L_0x00bb
        L_0x0051:
            java.net.URI r0 = new java.net.URI     // Catch:{ Exception -> 0x0076 }
            java.lang.String r7 = "ws://localhost:8082"
            r0.<init>(r7)     // Catch:{ Exception -> 0x0076 }
            d5 r7 = new d5     // Catch:{ Exception -> 0x0076 }
            r7.<init>(r0, r10)     // Catch:{ Exception -> 0x0076 }
            r7.connect()     // Catch:{ Exception -> 0x0076 }
            r7 = 500(0x1f4, double:2.47E-321)
            java.lang.Thread.sleep(r7)     // Catch:{ Exception -> 0x0076 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0076 }
            r0.<init>()     // Catch:{ Exception -> 0x0076 }
            r7 = 1
            r0.put(r2, r7)     // Catch:{ Exception -> 0x0076 }
            java.lang.String r10 = r3.concat(r10)     // Catch:{ Exception -> 0x0076 }
            r0.put(r1, r10)     // Catch:{ Exception -> 0x0076 }
            goto L_0x00ba
        L_0x0076:
            r10 = move-exception
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x009b }
            r0.<init>()     // Catch:{ Exception -> 0x009b }
            r0.put(r2, r6)     // Catch:{ Exception -> 0x009b }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009b }
            r3.<init>(r4)     // Catch:{ Exception -> 0x009b }
            java.lang.String r10 = r10.getMessage()     // Catch:{ Exception -> 0x009b }
            r3.append(r10)     // Catch:{ Exception -> 0x009b }
            java.lang.String r10 = r3.toString()     // Catch:{ Exception -> 0x009b }
            r0.put(r1, r10)     // Catch:{ Exception -> 0x009b }
            goto L_0x00ba
        L_0x0093:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x009b }
            java.lang.String r0 = "query is required"
            r10.<init>(r0)     // Catch:{ Exception -> 0x009b }
            throw r10     // Catch:{ Exception -> 0x009b }
        L_0x009b:
            r10 = move-exception
            r10.getMessage()
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00bc }
            r0.<init>()     // Catch:{ JSONException -> 0x00bc }
            r0.put(r2, r6)     // Catch:{ JSONException -> 0x00bc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00bc }
            r2.<init>(r5)     // Catch:{ JSONException -> 0x00bc }
            java.lang.String r10 = r10.getMessage()     // Catch:{ JSONException -> 0x00bc }
            r2.append(r10)     // Catch:{ JSONException -> 0x00bc }
            java.lang.String r10 = r2.toString()     // Catch:{ JSONException -> 0x00bc }
            r0.put(r1, r10)     // Catch:{ JSONException -> 0x00bc }
        L_0x00ba:
            r10 = r0
        L_0x00bb:
            return r10
        L_0x00bc:
            r10 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Failed to create error response"
            r0.<init>(r1, r10)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o5.a(org.json.JSONObject):java.lang.Object");
    }
}
