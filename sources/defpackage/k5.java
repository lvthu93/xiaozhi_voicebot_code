package defpackage;

import defpackage.a5;

/* renamed from: k5  reason: default package */
public final class k5 implements a5.h {
    public final /* synthetic */ a5 a;

    public k5(a5 a5Var) {
        this.a = a5Var;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0045 A[Catch:{ Exception -> 0x009f }] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0089 A[Catch:{ Exception -> 0x009f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object a(org.json.JSONObject r12) {
        /*
            r11 = this;
            a5 r0 = r11.a
            r0.getClass()
            java.lang.String r1 = ""
            java.lang.String r2 = "message"
            java.lang.String r3 = "success"
            java.lang.String r4 = "找到歌曲，开始播放: "
            java.lang.String r5 = "播放失败: "
            r6 = 0
            java.lang.String r7 = "song_name"
            java.lang.String r7 = r12.optString(r7, r1)     // Catch:{ Exception -> 0x009f }
            java.lang.String r7 = r7.trim()     // Catch:{ Exception -> 0x009f }
            java.lang.String r8 = "artist_name"
            java.lang.String r12 = r12.optString(r8, r1)     // Catch:{ Exception -> 0x009f }
            java.lang.String r12 = r12.trim()     // Catch:{ Exception -> 0x009f }
            boolean r1 = r7.isEmpty()     // Catch:{ Exception -> 0x009f }
            if (r1 != 0) goto L_0x0097
            android.content.Context r0 = r0.a
            r1 = 0
            if (r0 == 0) goto L_0x0042
            android.content.Context r8 = r0.getApplicationContext()     // Catch:{ Exception -> 0x009f }
            boolean r8 = r8 instanceof info.dourok.voicebot.java.VoiceBotApplication     // Catch:{ Exception -> 0x009f }
            if (r8 == 0) goto L_0x0042
            android.content.Context r0 = r0.getApplicationContext()     // Catch:{ Exception -> 0x009f }
            info.dourok.voicebot.java.VoiceBotApplication r0 = (info.dourok.voicebot.java.VoiceBotApplication) r0     // Catch:{ Exception -> 0x009f }
            x r0 = r0.getAiboxPlusWebSocketServer()     // Catch:{ Exception -> 0x009f }
            goto L_0x0043
        L_0x0042:
            r0 = r1
        L_0x0043:
            if (r0 == 0) goto L_0x0089
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x009f }
            r8.<init>()     // Catch:{ Exception -> 0x009f }
            r8.append(r7)     // Catch:{ Exception -> 0x009f }
            boolean r9 = r12.isEmpty()     // Catch:{ Exception -> 0x009f }
            if (r9 != 0) goto L_0x005b
            java.lang.String r9 = " "
            r8.append(r9)     // Catch:{ Exception -> 0x009f }
            r8.append(r12)     // Catch:{ Exception -> 0x009f }
        L_0x005b:
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ Exception -> 0x009f }
            r12.<init>()     // Catch:{ Exception -> 0x009f }
            java.lang.String r9 = "action"
            java.lang.String r10 = "search_zing"
            r12.put(r9, r10)     // Catch:{ Exception -> 0x009f }
            java.lang.String r9 = "query"
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x009f }
            r12.put(r9, r8)     // Catch:{ Exception -> 0x009f }
            java.lang.String r8 = "autoplay"
            r9 = 1
            r12.put(r8, r9)     // Catch:{ Exception -> 0x009f }
            r0.ah(r1, r12)     // Catch:{ Exception -> 0x009f }
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ Exception -> 0x009f }
            r12.<init>()     // Catch:{ Exception -> 0x009f }
            r12.put(r3, r9)     // Catch:{ Exception -> 0x009f }
            java.lang.String r0 = r4.concat(r7)     // Catch:{ Exception -> 0x009f }
            r12.put(r2, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x00bf
        L_0x0089:
            org.json.JSONObject r12 = new org.json.JSONObject     // Catch:{ Exception -> 0x009f }
            r12.<init>()     // Catch:{ Exception -> 0x009f }
            r12.put(r3, r6)     // Catch:{ Exception -> 0x009f }
            java.lang.String r0 = "Zing MP3 playback không khả dụng (server không phản hồi)"
            r12.put(r2, r0)     // Catch:{ Exception -> 0x009f }
            goto L_0x00bf
        L_0x0097:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x009f }
            java.lang.String r0 = "song_name is required"
            r12.<init>(r0)     // Catch:{ Exception -> 0x009f }
            throw r12     // Catch:{ Exception -> 0x009f }
        L_0x009f:
            r12 = move-exception
            r12.getMessage()
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00c0 }
            r0.<init>()     // Catch:{ JSONException -> 0x00c0 }
            r0.put(r3, r6)     // Catch:{ JSONException -> 0x00c0 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x00c0 }
            r1.<init>(r5)     // Catch:{ JSONException -> 0x00c0 }
            java.lang.String r12 = r12.getMessage()     // Catch:{ JSONException -> 0x00c0 }
            r1.append(r12)     // Catch:{ JSONException -> 0x00c0 }
            java.lang.String r12 = r1.toString()     // Catch:{ JSONException -> 0x00c0 }
            r0.put(r2, r12)     // Catch:{ JSONException -> 0x00c0 }
            r12 = r0
        L_0x00bf:
            return r12
        L_0x00c0:
            r12 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Failed to create error response"
            r0.<init>(r1, r12)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.k5.a(org.json.JSONObject):java.lang.Object");
    }
}
