package defpackage;

/* renamed from: s  reason: default package */
public final /* synthetic */ class s implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ String f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;
    public final /* synthetic */ Object j;

    public /* synthetic */ s(Object obj, String str, Object obj2, Object obj3, Object obj4, int i2) {
        this.c = i2;
        this.g = obj;
        this.f = str;
        this.h = obj2;
        this.i = obj3;
        this.j = obj4;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:53|54|55|56) */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0171, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x0173, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r2 = new org.json.JSONObject();
        r2.put("success", false);
        r2.put("message", "Lỗi: " + r0.getMessage());
        r5.set(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0193, code lost:
        r4.countDown();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0196, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x015f */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0171 A[ExcHandler: all (r0v5 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:40:0x00e4] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r18 = this;
            r1 = r18
            int r0 = r1.c
            java.lang.String r2 = ""
            r3 = 0
            java.lang.Object r4 = r1.j
            java.lang.Object r5 = r1.i
            java.lang.Object r6 = r1.h
            java.lang.String r7 = r1.f
            java.lang.Object r8 = r1.g
            switch(r0) {
                case 0: goto L_0x0016;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x00d1
        L_0x0016:
            r10 = r8
            x r10 = (defpackage.x) r10
            java.lang.String r6 = (java.lang.String) r6
            org.java_websocket.WebSocket r5 = (org.java_websocket.WebSocket) r5
            r11 = r4
            info.dourok.voicebot.java.audio.MusicPlayer r11 = (info.dourok.voicebot.java.audio.MusicPlayer) r11
            r10.getClass()
            java.lang.String r0 = "Không thể lấy stream URL cho bài hát: "
            java.lang.String r4 = "Không tìm thấy bài hát: "
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5 }
            r8.<init>()     // Catch:{ Exception -> 0x00c5 }
            r8.append(r7)     // Catch:{ Exception -> 0x00c5 }
            if (r6 == 0) goto L_0x003f
            boolean r9 = r6.isEmpty()     // Catch:{ Exception -> 0x00c5 }
            if (r9 != 0) goto L_0x003f
            java.lang.String r9 = " "
            r8.append(r9)     // Catch:{ Exception -> 0x00c5 }
            r8.append(r6)     // Catch:{ Exception -> 0x00c5 }
        L_0x003f:
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x00c5 }
            info.dourok.voicebot.java.services.ZingMp3Service r9 = new info.dourok.voicebot.java.services.ZingMp3Service     // Catch:{ Exception -> 0x00c5 }
            r9.<init>()     // Catch:{ Exception -> 0x00c5 }
            java.util.List r8 = r9.searchSongs(r8)     // Catch:{ Exception -> 0x00c5 }
            if (r8 == 0) goto L_0x00b5
            boolean r12 = r8.isEmpty()     // Catch:{ Exception -> 0x00c5 }
            if (r12 == 0) goto L_0x0056
            goto L_0x00b5
        L_0x0056:
            java.lang.Object r3 = r8.get(r3)     // Catch:{ Exception -> 0x00c5 }
            info.dourok.voicebot.java.services.ZingMp3Service$ZingMp3Song r3 = (info.dourok.voicebot.java.services.ZingMp3Service.ZingMp3Song) r3     // Catch:{ Exception -> 0x00c5 }
            java.lang.String r4 = r3.id     // Catch:{ Exception -> 0x00c5 }
            java.lang.String r8 = r3.thumb     // Catch:{ Exception -> 0x00c5 }
            if (r8 == 0) goto L_0x0064
            r15 = r8
            goto L_0x0065
        L_0x0064:
            r15 = r2
        L_0x0065:
            java.lang.String r12 = r9.getStreamLink(r4)     // Catch:{ Exception -> 0x00c5 }
            if (r12 == 0) goto L_0x00a5
            boolean r2 = r12.isEmpty()     // Catch:{ Exception -> 0x00c5 }
            if (r2 == 0) goto L_0x0072
            goto L_0x00a5
        L_0x0072:
            java.lang.String r0 = r3.title     // Catch:{ Exception -> 0x00c5 }
            if (r0 == 0) goto L_0x007e
            boolean r0 = r0.isEmpty()     // Catch:{ Exception -> 0x00c5 }
            if (r0 != 0) goto L_0x007e
            java.lang.String r7 = r3.title     // Catch:{ Exception -> 0x00c5 }
        L_0x007e:
            r13 = r7
            java.lang.String r0 = r3.artistName     // Catch:{ Exception -> 0x00c5 }
            if (r0 == 0) goto L_0x008d
            boolean r0 = r0.isEmpty()     // Catch:{ Exception -> 0x00c5 }
            if (r0 != 0) goto L_0x008d
            java.lang.String r0 = r3.artistName     // Catch:{ Exception -> 0x00c5 }
            r14 = r0
            goto L_0x008e
        L_0x008d:
            r14 = r6
        L_0x008e:
            android.os.Handler r0 = new android.os.Handler     // Catch:{ Exception -> 0x00c5 }
            android.os.Looper r2 = android.os.Looper.getMainLooper()     // Catch:{ Exception -> 0x00c5 }
            r0.<init>(r2)     // Catch:{ Exception -> 0x00c5 }
            t r2 = new t     // Catch:{ Exception -> 0x00c5 }
            r9 = r2
            r16 = r4
            r17 = r5
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17)     // Catch:{ Exception -> 0x00c5 }
            r0.post(r2)     // Catch:{ Exception -> 0x00c5 }
            goto L_0x00d0
        L_0x00a5:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x00c5 }
            r2.append(r7)     // Catch:{ Exception -> 0x00c5 }
            java.lang.String r0 = r2.toString()     // Catch:{ Exception -> 0x00c5 }
            defpackage.x.bd(r5, r0)     // Catch:{ Exception -> 0x00c5 }
            goto L_0x00d0
        L_0x00b5:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c5 }
            r0.<init>(r4)     // Catch:{ Exception -> 0x00c5 }
            r0.append(r7)     // Catch:{ Exception -> 0x00c5 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00c5 }
            defpackage.x.bd(r5, r0)     // Catch:{ Exception -> 0x00c5 }
            goto L_0x00d0
        L_0x00c5:
            r0 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "Search failed: "
            r2.<init>(r3)
            defpackage.y2.v(r0, r2, r5)
        L_0x00d0:
            return
        L_0x00d1:
            a5 r8 = (defpackage.a5) r8
            org.json.JSONObject r6 = (org.json.JSONObject) r6
            java.util.concurrent.atomic.AtomicReference r5 = (java.util.concurrent.atomic.AtomicReference) r5
            java.util.concurrent.CountDownLatch r4 = (java.util.concurrent.CountDownLatch) r4
            r8.getClass()
            java.lang.String r0 = "response"
            java.lang.String r9 = "message"
            java.lang.String r10 = "success"
            java.lang.String r11 = "Lỗi: "
            info.dourok.voicebot.java.services.ZaloMessageService r12 = new info.dourok.voicebot.java.services.ZaloMessageService     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            android.content.Context r8 = r8.a     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r12.<init>(r8)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.<init>()     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r13 = "getlist"
            boolean r7 = r13.equals(r7)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            if (r7 == 0) goto L_0x010e
            info.dourok.voicebot.java.services.ZaloMessageService$ZaloListResult r0 = r12.getZaloList()     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            boolean r2 = r0.success     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r10, r2)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r2 = "zalo_list"
            org.json.JSONArray r6 = r0.zaloList     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r2, r6)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r0 = r0.message     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r9, r0)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            goto L_0x016d
        L_0x010e:
            java.lang.String r7 = "chat_id"
            java.lang.String r7 = r6.optString(r7, r2)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r13 = "text"
            java.lang.String r2 = r6.optString(r13, r2)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            boolean r6 = r7.isEmpty()     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            if (r6 != 0) goto L_0x0165
            boolean r6 = r2.isEmpty()     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            if (r6 == 0) goto L_0x0127
            goto L_0x0165
        L_0x0127:
            info.dourok.voicebot.java.services.ZaloMessageService$ZaloSendMessageResult r2 = r12.sendMessage(r7, r2)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            boolean r6 = r2.success     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r10, r6)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r6 = "status_code"
            int r7 = r2.statusCode     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r6, r7)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r6 = "message_id"
            java.lang.String r7 = r2.messageId     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r6, r7)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r6 = "date"
            java.lang.String r7 = r2.date     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r6, r7)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r6 = r2.message     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r9, r6)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r6 = r2.responseJson     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            if (r6 == 0) goto L_0x016d
            boolean r6 = r6.isEmpty()     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            if (r6 != 0) goto L_0x016d
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x015f }
            java.lang.String r7 = r2.responseJson     // Catch:{ JSONException -> 0x015f }
            r6.<init>(r7)     // Catch:{ JSONException -> 0x015f }
            r8.put(r0, r6)     // Catch:{ JSONException -> 0x015f }
            goto L_0x016d
        L_0x015f:
            java.lang.String r2 = r2.responseJson     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            r8.put(r0, r2)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            goto L_0x016d
        L_0x0165:
            r8.put(r10, r3)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            java.lang.String r0 = "Thiếu chat_id hoặc text (cần khi action=sendmess)"
            r8.put(r9, r0)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
        L_0x016d:
            r5.set(r8)     // Catch:{ Exception -> 0x0173, all -> 0x0171 }
            goto L_0x0197
        L_0x0171:
            r0 = move-exception
            goto L_0x0193
        L_0x0173:
            r0 = move-exception
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            r2.<init>()     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            r2.put(r10, r3)     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            r3.<init>(r11)     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            r3.append(r0)     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            java.lang.String r0 = r3.toString()     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            r2.put(r9, r0)     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            r5.set(r2)     // Catch:{ JSONException -> 0x0197, all -> 0x0171 }
            goto L_0x0197
        L_0x0193:
            r4.countDown()
            throw r0
        L_0x0197:
            r4.countDown()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.s.run():void");
    }
}
