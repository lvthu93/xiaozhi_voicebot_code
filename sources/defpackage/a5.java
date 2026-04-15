package defpackage;

import android.content.Context;
import info.dourok.voicebot.java.audio.MusicPlayer;
import info.dourok.voicebot.java.audio.j;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mozilla.javascript.ES6Iterator;

/* renamed from: a5  reason: default package */
public final class a5 {
    public final Context a;
    public final String b = "phicomm-r1-aiboxplus";
    public final HashMap c;
    public f d;
    public g e = null;
    public MusicPlayer f;
    public final ExecutorService g = Executors.newSingleThreadExecutor();
    public j h;
    public MusicPlayer.m i;

    /* renamed from: a5$a */
    public class a implements Runnable {
        public final /* synthetic */ e c;
        public final /* synthetic */ JSONObject f;
        public final /* synthetic */ int g;

        public a(e eVar, JSONObject jSONObject, int i) {
            this.c = eVar;
            this.f = jSONObject;
            this.g = i;
        }

        public final void run() {
            e eVar = this.c;
            JSONObject jSONObject = this.f;
            int i = this.g;
            a5 a5Var = a5.this;
            a5Var.getClass();
            try {
                a5Var.f(i, eVar.b.a(jSONObject));
            } catch (Exception e) {
                a5Var.d(i, "Tool execution failed: " + e.getMessage());
            }
        }
    }

    /* renamed from: a5$b */
    public class b implements Runnable {
        public final /* synthetic */ e c;
        public final /* synthetic */ JSONObject f;
        public final /* synthetic */ int g;

        public b(e eVar, JSONObject jSONObject, int i) {
            this.c = eVar;
            this.f = jSONObject;
            this.g = i;
        }

        public final void run() {
            e eVar = this.c;
            JSONObject jSONObject = this.f;
            int i = this.g;
            a5 a5Var = a5.this;
            a5Var.getClass();
            try {
                a5Var.f(i, eVar.b.a(jSONObject));
            } catch (Exception e) {
                a5Var.d(i, "Tool execution failed: " + e.getMessage());
            }
        }
    }

    /* renamed from: a5$c */
    public class c implements Runnable {
        public final /* synthetic */ JSONObject c;
        public final /* synthetic */ int f;

        public c(e eVar, JSONObject jSONObject, int i) {
            this.c = jSONObject;
            this.f = i;
        }

        public final void run() {
            JSONObject jSONObject = this.c;
            int i = this.f;
            a5 a5Var = a5.this;
            a5Var.getClass();
            try {
                a5Var.f(i, a5Var.b(jSONObject));
            } catch (Exception e) {
                a5Var.d(i, "Tool execution failed: " + e.getMessage());
            }
        }
    }

    /* renamed from: a5$d */
    public class d implements Runnable {
        public final /* synthetic */ e c;
        public final /* synthetic */ JSONObject f;
        public final /* synthetic */ int g;

        public d(e eVar, JSONObject jSONObject, int i) {
            this.c = eVar;
            this.f = jSONObject;
            this.g = i;
        }

        public final void run() {
            e eVar = this.c;
            JSONObject jSONObject = this.f;
            int i = this.g;
            a5 a5Var = a5.this;
            a5Var.getClass();
            try {
                a5Var.f(i, eVar.b.a(jSONObject));
            } catch (Exception e) {
                a5Var.d(i, "Tool execution failed: " + e.getMessage());
            }
        }
    }

    /* renamed from: a5$e */
    public static class e {
        public final String a;
        public final h b;
        public final JSONObject c;
        public final String d;

        public e(String str, String str2, JSONObject jSONObject, h hVar) {
            this.d = str;
            this.a = str2;
            this.c = jSONObject;
            this.b = hVar;
        }
    }

    /* renamed from: a5$f */
    public interface f {
    }

    /* renamed from: a5$g */
    public interface g {
    }

    /* renamed from: a5$h */
    public interface h {
        Object a(JSONObject jSONObject) throws Exception;
    }

    public a5(Context context) {
        String str;
        this.a = context;
        HashMap hashMap = new HashMap();
        this.c = hashMap;
        HashMap hashMap2 = hashMap;
        Object obj = "sendmess";
        String str2 = "Initialized ";
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", "object");
            jSONObject.put("properties", new JSONObject());
            a("self.get_device_status", "Get current device status (audio, network, IP WIFI)", jSONObject, new i5(this));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "object");
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("type", "integer");
            jSONObject4.put("minimum", 0);
            jSONObject4.put("maximum", 100);
            jSONObject3.put("volume", jSONObject4);
            jSONObject2.put("properties", jSONObject3);
            jSONObject2.put("required", new JSONArray().put("volume"));
            a("self.audio_speaker.set_volume", "Set device audio volume (0-100)", jSONObject2, new j5(this));
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("type", "object");
            JSONObject jSONObject6 = new JSONObject();
            JSONObject jSONObject7 = new JSONObject();
            jSONObject7.put("type", "string");
            jSONObject7.put("description", "Song/content name to play");
            jSONObject6.put("song_name", jSONObject7);
            JSONObject jSONObject8 = new JSONObject();
            jSONObject8.put("type", "string");
            jSONObject8.put("description", "Artist/author name (optional)");
            jSONObject6.put("artist_name", jSONObject8);
            jSONObject5.put("properties", jSONObject6);
            jSONObject5.put("required", new JSONArray().put("song_name"));
            a("self.entertainment.play", "播放娱乐内容，包括音乐、故事、有声读物等。当用户要求播放音乐、听歌、讲故事时使用此工具。\n参数: song_name (必需) - 歌曲/内容名称, artist_name (可选) - 演唱者/作者名称", jSONObject5, new k5(this));
            JSONObject jSONObject9 = new JSONObject();
            jSONObject9.put("type", "object");
            JSONObject jSONObject10 = new JSONObject();
            JSONObject jSONObject11 = new JSONObject();
            jSONObject11.put("type", "string");
            jSONObject11.put("description", "Action to perform: 'stop' (default) to stop music, 'next' to play next song, 'previous' to play previous song, 'resume' to resume paused music");
            jSONObject11.put("enum", new JSONArray().put("stop").put(ES6Iterator.NEXT_METHOD).put("previous").put("resume"));
            jSONObject10.put("action", jSONObject11);
            jSONObject9.put("properties", jSONObject10);
            jSONObject9.put("required", new JSONArray());
            a("self.entertainment.stop", "控制音乐播放：停止、下一首、上一首、继续播放。\n适用场景：\n  - 用户要求停止音乐（action='stop'或默认）\n  - 用户要求下一首（action='next'）\n  - 用户要求上一首（action='previous'）\n  - 用户要求继续播放（action='resume'）\n参数:\n  `action` (可选): 操作类型，默认为'stop'。可选值: 'stop', 'next', 'previous', 'resume'", jSONObject9, new l5(this));
            JSONObject jSONObject12 = new JSONObject();
            jSONObject12.put("type", "object");
            JSONObject jSONObject13 = new JSONObject();
            JSONObject jSONObject14 = new JSONObject();
            jSONObject14.put("type", "string");
            jSONObject14.put("description", "Action: 'q' for news list, 'c' for reading article, 'chuyensau' for deep search");
            jSONObject14.put("enum", new JSONArray().put("q").put("c").put("chuyensau"));
            jSONObject13.put("action", jSONObject14);
            JSONObject jSONObject15 = new JSONObject();
            jSONObject15.put("type", "string");
            jSONObject15.put("description", "Query: when action='q' use search term (optional, defaults to 'news'), when action='c' use article number 1-30, when action='chuyensau' use search keywords");
            String str3 = "query";
            jSONObject13.put(str3, jSONObject15);
            jSONObject12.put("properties", jSONObject13);
            jSONObject12.put("required", new JSONArray().put("action"));
            a("self.news.play", "Search news, read articles, or search real-time information. Use action='q' for news list, action='c' with article number 1-30 to read, action='chuyensau' with keywords for real-time search (use when you don't know the answer).", jSONObject12, new m5(this));
            JSONObject jSONObject16 = new JSONObject();
            jSONObject16.put("type", "object");
            JSONObject jSONObject17 = new JSONObject();
            JSONObject jSONObject18 = new JSONObject();
            jSONObject18.put("type", "string");
            jSONObject18.put("description", "Query mode - 'giavang' (gold price), 'usd' (USD rate), or 'all' (default)");
            String str4 = "default";
            jSONObject18.put(str4, "all");
            jSONObject17.put("mode", jSONObject18);
            jSONObject16.put("properties", jSONObject17);
            JSONObject jSONObject19 = new JSONObject();
            jSONObject19.put("type", "object");
            JSONObject jSONObject20 = new JSONObject();
            JSONObject jSONObject21 = new JSONObject();
            jSONObject21.put("type", "string");
            jSONObject21.put("description", "City or province name (e.g., 'Hanoi', 'Ho Chi Minh', 'Da Nang'). If not provided, location will be detected from IP address.");
            jSONObject20.put("city", jSONObject21);
            jSONObject19.put("properties", jSONObject20);
            a("self.weather.get", "获取天气信息。该工具可以:\n1. 获取指定城市/省份的天气（通过参数 `city` 指定）\n2. 自动根据IP地址检测位置并获取当地天气（不提供 `city` 参数时）\n适用场景：用户询问天气、温度、今天天气怎么样等。\n参数:\n  `city`: 城市或省份名称（可选，如 'Hanoi'、'胡志明市'、'岘港'）\n         如果不提供，将根据IP自动检测位置\n返回:\n  当前天气信息，包括温度、天气状况、风速等。", jSONObject19, new n5(this));
            JSONObject jSONObject22 = new JSONObject();
            jSONObject22.put("type", "object");
            JSONObject jSONObject23 = new JSONObject();
            JSONObject jSONObject24 = new JSONObject();
            jSONObject24.put("type", "string");
            jSONObject24.put("description", "Search query for YouTube video/music (song name, artist, etc.)");
            jSONObject23.put(str3, jSONObject24);
            jSONObject22.put("properties", jSONObject23);
            jSONObject22.put("required", new JSONArray().put(str3));
            a("self.youtube_music.play", "在YouTube上搜索并播放音乐/视频的音频。该工具通过Piped API搜索YouTube视频，提取音频流并播放。\n适用场景：\n  - 用户要求播放YouTube上的特定歌曲或视频\n  - 用户想听YouTube上的音乐、MV、演唱会等\n  - 用户指定要从YouTube播放内容\n参数:\n  `query` (必需): 搜索关键词，可以是歌曲名、歌手名、视频标题等\n返回:\n  播放状态信息，包括视频标题、频道名称等。", jSONObject22, new o5(this));
            JSONObject jSONObject25 = new JSONObject();
            jSONObject25.put("type", "object");
            JSONObject jSONObject26 = new JSONObject();
            JSONObject jSONObject27 = new JSONObject();
            jSONObject27.put("type", "string");
            jSONObject27.put("description", "Search query for YouTube playlist (playlist name, etc.)");
            jSONObject26.put(str3, jSONObject27);
            jSONObject25.put("properties", jSONObject26);
            jSONObject25.put("required", new JSONArray().put(str3));
            a("self.youtube_playlist.play", "在YouTube上搜索并播放播放列表。该工具搜索YouTube播放列表，并自动播放播放列表中第一首歌曲。\n适用场景：\n  - 用户要求播放YouTube播放列表\n  - 用户想听播放列表中的音乐\n参数:\n  `query` (必需): 搜索关键词，可以是播放列表名称等\n返回:\n  播放状态信息，包括视频标题、频道名称等。", jSONObject25, new p5(this));
            JSONObject jSONObject28 = new JSONObject();
            jSONObject28.put("type", "object");
            JSONObject jSONObject29 = new JSONObject();
            JSONObject jSONObject30 = new JSONObject();
            jSONObject30.put("type", "string");
            jSONObject30.put("description", "Radio station name or key (e.g., 'VOV1', 'VOV Giao thông', 'VOV Tây Nguyên')");
            String str5 = "station_name";
            jSONObject29.put(str5, jSONObject30);
            jSONObject25.put("properties", jSONObject29);
            jSONObject25.put("required", new JSONArray().put(str5));
            a("self.radio.play", "播放越南之声（VOV）广播电台。该工具支持多个越南广播电台频道。\n支持的电台:\n  - VOV1: 越南之声综合频道（新闻/时事）\n  - VOV2: 越南之声文化音乐频道\n  - VOV3: 越南之声信息娱乐频道\n  - VOV5: 海外越南人频道\n  - VOVGT (VOV Giao thông): 河内交通广播\n  - VOVGT_HCM: 胡志明市交通广播\n  - VOV_ENGLISH: 越南之声英语频道\n  - VOV_MEKONG: 湄公河三角洲频道\n  - VOV_MIENTRUNG: 中部地区频道\n  - VOV_TAYBAC: 西北地区频道\n  - VOV_DONGBAC: 东北地区频道\n  - VOV_TAYNGUYEN: 西原地区频道\n适用场景：用户要求播放广播、听收音机、听VOV等。\n参数:\n  `station_name` (必需): 电台名称或代码，支持部分匹配和拼音变体", jSONObject25, new v4(this));
            JSONObject jSONObject31 = new JSONObject();
            jSONObject31.put("type", "object");
            jSONObject31.put("properties", new JSONObject());
            a("self.radio.stop", "停止当前播放的广播电台", jSONObject31, new w4(this));
            new JSONObject().put("type", "object");
            JSONObject jSONObject32 = new JSONObject();
            JSONObject jSONObject33 = new JSONObject();
            jSONObject33.put("type", "boolean");
            jSONObject33.put("description", "true to enable Bluetooth (receiver mode), false to disable");
            String str6 = "enable";
            jSONObject32.put(str6, jSONObject33);
            jSONObject28.put("properties", jSONObject32);
            jSONObject28.put("required", new JSONArray().put(str6));
            a("self.bluetooth.set_enabled", "启用或禁用蓝牙接收模式。当启用时，设备可作为蓝牙音箱接收来自手机的音频。\nEnable or disable Bluetooth receiver mode. When enabled, device acts as Bluetooth speaker to receive audio from phone.\n参数: enable (必需) - true 开启蓝牙, false 关闭蓝牙", jSONObject28, new x4(this));
            JSONObject jSONObject34 = new JSONObject();
            jSONObject34.put("type", "object");
            jSONObject34.put("properties", new JSONObject());
            a("self.led_speaker.off", "关闭音箱上的LED灯。这是用于关闭音箱设备上的LED指示灯, 不是智能家居的灯具控制。\nTurn off the LED lights on the speaker device. This is for the speaker's LED indicator lights, NOT for smart home lamp/light control.\nTắt đèn LED trên loa. Đây là đèn LED trên thiết bị loa, KHÔNG phải điều khiển đèn smarthome.\nKhi về chế độ idle, LED sẽ không bật lại.", jSONObject34, new y4(this));
            JSONObject jSONObject35 = new JSONObject();
            jSONObject35.put("type", "object");
            JSONObject jSONObject36 = new JSONObject();
            JSONObject jSONObject37 = new JSONObject();
            jSONObject37.put("type", "string");
            jSONObject37.put("description", "Action: 'getlist' to get Zalo contact list, 'sendmess' to send message, 'readmessage' to read received messages");
            Object obj2 = obj;
            jSONObject37.put("enum", new JSONArray().put("getlist").put(obj2).put("readmessage"));
            jSONObject37.put(str4, obj2);
            jSONObject36.put("action", jSONObject37);
            JSONObject jSONObject38 = new JSONObject();
            jSONObject38.put("type", "string");
            jSONObject38.put("description", "Chat ID of recipient (required when action=sendmess)");
            jSONObject36.put("chat_id", jSONObject38);
            JSONObject jSONObject39 = new JSONObject();
            jSONObject39.put("type", "string");
            jSONObject39.put("description", "Message text (required when action=sendmess)");
            jSONObject36.put("text", jSONObject39);
            jSONObject35.put("properties", jSONObject36);
            a("self.send_zalo_message", "Cách dùng:\n  - action=getlist: Lấy danh sách chat_id người nhận tin nhắn, luôn sử dụng trước khi gửi tin nhắn. Nếu user chỉ yêu cầu lấy danh sách thì không cần đọc id cho user\n  - action=sendmess: Gửi tin nhắn, cần chat_id và text. Trước khi sử dụng sendmess thì cần sử dụng getlist để lấy chính xác chat_id của người nhận\n  - action=readmessage: Đọc tất cả tin nhắn Zalo đã nhận được (tối đa 30 tin nhắn gần nhất), trả về mảng các tin nhắn với thời gian, tên người gửi và nội dung. Sau khi đọc thành công, tất cả tin nhắn sẽ bị xóa\nTham số:\n  action: getlist | sendmess | readmessage\n  chat_id: ID người nhận (bắt buộc khi action=sendmess)\n  text: Nội dung tin nhắn (bắt buộc khi action=sendmess)", jSONObject35, new z4(this));
            str = str2 + hashMap2.size() + " device tools (ESP32-compatible)";
        } catch (JSONException unused) {
            str = "Error initializing device tools";
        }
        if (str == null) {
            hashMap2.size();
        }
    }

    public final void a(String str, String str2, JSONObject jSONObject, h hVar) {
        this.c.put(str, new e(str, str2, jSONObject, hVar));
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x003d A[Catch:{ Exception -> 0x0074 }] */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x004b A[Catch:{ Exception -> 0x0074 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final org.json.JSONObject b(org.json.JSONObject r11) {
        /*
            r10 = this;
            java.lang.String r0 = "query"
            java.lang.String r1 = "message"
            java.lang.String r2 = "success"
            java.lang.String r3 = "正在搜索并播放播放列表: "
            java.lang.String r4 = "播放YouTube播放列表失败: "
            java.lang.Thread r5 = java.lang.Thread.currentThread()
            r5.getName()
            r5 = 0
            java.lang.String r6 = ""
            java.lang.String r11 = r11.optString(r0, r6)     // Catch:{ Exception -> 0x0074 }
            java.lang.String r11 = r11.trim()     // Catch:{ Exception -> 0x0074 }
            boolean r6 = r11.isEmpty()     // Catch:{ Exception -> 0x0074 }
            if (r6 != 0) goto L_0x006c
            android.content.Context r6 = r10.a
            r7 = 0
            if (r6 == 0) goto L_0x003a
            android.content.Context r8 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0074 }
            boolean r8 = r8 instanceof info.dourok.voicebot.java.VoiceBotApplication     // Catch:{ Exception -> 0x0074 }
            if (r8 == 0) goto L_0x003a
            android.content.Context r6 = r6.getApplicationContext()     // Catch:{ Exception -> 0x0074 }
            info.dourok.voicebot.java.VoiceBotApplication r6 = (info.dourok.voicebot.java.VoiceBotApplication) r6     // Catch:{ Exception -> 0x0074 }
            x r6 = r6.getAiboxPlusWebSocketServer()     // Catch:{ Exception -> 0x0074 }
            goto L_0x003b
        L_0x003a:
            r6 = r7
        L_0x003b:
            if (r6 != 0) goto L_0x004b
            org.json.JSONObject r11 = new org.json.JSONObject     // Catch:{ Exception -> 0x0074 }
            r11.<init>()     // Catch:{ Exception -> 0x0074 }
            r11.put(r2, r5)     // Catch:{ Exception -> 0x0074 }
            java.lang.String r0 = "YouTube service not available"
            r11.put(r1, r0)     // Catch:{ Exception -> 0x0074 }
            return r11
        L_0x004b:
            org.json.JSONObject r8 = new org.json.JSONObject     // Catch:{ Exception -> 0x0074 }
            r8.<init>()     // Catch:{ Exception -> 0x0074 }
            r8.put(r0, r11)     // Catch:{ Exception -> 0x0074 }
            java.lang.String r0 = "autoplay"
            r9 = 1
            r8.put(r0, r9)     // Catch:{ Exception -> 0x0074 }
            r6.af(r7, r8)     // Catch:{ Exception -> 0x0074 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Exception -> 0x0074 }
            r0.<init>()     // Catch:{ Exception -> 0x0074 }
            r0.put(r2, r9)     // Catch:{ Exception -> 0x0074 }
            java.lang.String r11 = r3.concat(r11)     // Catch:{ Exception -> 0x0074 }
            r0.put(r1, r11)     // Catch:{ Exception -> 0x0074 }
            return r0
        L_0x006c:
            java.lang.IllegalArgumentException r11 = new java.lang.IllegalArgumentException     // Catch:{ Exception -> 0x0074 }
            java.lang.String r0 = "query is required"
            r11.<init>(r0)     // Catch:{ Exception -> 0x0074 }
            throw r11     // Catch:{ Exception -> 0x0074 }
        L_0x0074:
            r11 = move-exception
            r11.getMessage()
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0094 }
            r0.<init>()     // Catch:{ JSONException -> 0x0094 }
            r0.put(r2, r5)     // Catch:{ JSONException -> 0x0094 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0094 }
            r2.<init>(r4)     // Catch:{ JSONException -> 0x0094 }
            java.lang.String r11 = r11.getMessage()     // Catch:{ JSONException -> 0x0094 }
            r2.append(r11)     // Catch:{ JSONException -> 0x0094 }
            java.lang.String r11 = r2.toString()     // Catch:{ JSONException -> 0x0094 }
            r0.put(r1, r11)     // Catch:{ JSONException -> 0x0094 }
            return r0
        L_0x0094:
            r11 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Failed to create error response"
            r0.<init>(r1, r11)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.a5.b(org.json.JSONObject):org.json.JSONObject");
    }

    public final void c(int i2, JSONObject jSONObject) {
        try {
            JSONObject optJSONObject = jSONObject.optJSONObject("params");
            if (optJSONObject == null) {
                d(i2, "Missing params for tool call");
                return;
            }
            String optString = optJSONObject.optString("name");
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("arguments");
            e eVar = (e) this.c.get(optString);
            if (eVar == null) {
                d(i2, "Unknown tool: " + optString);
                return;
            }
            boolean equals = optString.equals("self.entertainment.play");
            ExecutorService executorService = this.g;
            if (equals) {
                executorService.execute(new a(eVar, optJSONObject2, i2));
            } else if (optString.equals("self.youtube_music.play")) {
                executorService.execute(new b(eVar, optJSONObject2, i2));
            } else if (optString.equals("self.youtube_playlist.play")) {
                executorService.execute(new c(eVar, optJSONObject2, i2));
            } else if (optString.equals("self.radio.play")) {
                executorService.execute(new d(eVar, optJSONObject2, i2));
            } else {
                f(i2, eVar.b.a(optJSONObject2));
            }
        } catch (Exception e2) {
            d(i2, "Tool execution failed: " + e2.getMessage());
        }
    }

    public final void d(int i2, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("session_id", "");
            jSONObject.put("type", "mcp");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("jsonrpc", "2.0");
            jSONObject2.put("id", i2);
            JSONObject jSONObject3 = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("type", "text");
            jSONObject4.put("text", str);
            jSONArray.put(jSONObject4);
            jSONObject3.put("content", jSONArray);
            jSONObject3.put("isError", true);
            jSONObject2.put("result", jSONObject3);
            jSONObject.put(MqttServiceConstants.PAYLOAD, jSONObject2);
            jSONObject.toString();
            ((z7) this.e).a.q(jSONObject);
        } catch (JSONException unused) {
        }
    }

    public final void e(int i2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("session_id", "");
            jSONObject.put("type", "mcp");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("jsonrpc", "2.0");
            jSONObject2.put("id", i2);
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("protocolVersion", "2024-11-05");
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("tools", new JSONObject());
            jSONObject3.put("capabilities", jSONObject4);
            JSONObject jSONObject5 = new JSONObject();
            jSONObject5.put("name", this.b);
            jSONObject5.put("version", "4.0.0");
            jSONObject3.put("serverInfo", jSONObject5);
            jSONObject2.put("result", jSONObject3);
            jSONObject.put(MqttServiceConstants.PAYLOAD, jSONObject2);
            this.c.size();
            jSONObject.toString();
            jSONObject4.toString();
            jSONObject5.toString();
            ((z7) this.e).a.q(jSONObject);
        } catch (JSONException unused) {
        }
    }

    public final void f(int i2, Object obj) {
        String str;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("session_id", "");
            jSONObject.put("type", "mcp");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("jsonrpc", "2.0");
            jSONObject2.put("id", i2);
            JSONObject jSONObject3 = new JSONObject();
            if (obj == null) {
                str = "Success";
            } else {
                if (!(obj instanceof JSONObject) && !(obj instanceof JSONArray)) {
                    String.valueOf(obj);
                }
                str = obj.toString();
            }
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("type", "text");
            jSONObject4.put("text", str);
            jSONArray.put(jSONObject4);
            jSONObject3.put("content", jSONArray);
            jSONObject3.put("isError", false);
            jSONObject2.put("result", jSONObject3);
            jSONObject.put(MqttServiceConstants.PAYLOAD, jSONObject2);
            jSONObject.toString();
            ((z7) this.e).a.q(jSONObject);
        } catch (JSONException unused) {
        }
    }

    public final void g(int i2) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("session_id", "");
            jSONObject.put("type", "mcp");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("jsonrpc", "2.0");
            jSONObject2.put("id", i2);
            JSONObject jSONObject3 = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            for (e eVar : this.c.values()) {
                JSONObject jSONObject4 = new JSONObject();
                jSONObject4.put("name", eVar.d);
                jSONObject4.put("description", eVar.a);
                jSONObject4.put("inputSchema", eVar.c);
                jSONArray.put(jSONObject4);
            }
            jSONObject3.put("tools", jSONArray);
            jSONObject2.put("result", jSONObject3);
            jSONObject.put(MqttServiceConstants.PAYLOAD, jSONObject2);
            jSONArray.length();
            jSONObject.toString();
            ((z7) this.e).a.q(jSONObject);
            f fVar = this.d;
            if (fVar != null) {
                x7 x7Var = ((f8) fVar).a;
                x7Var.x = true;
                m1 m1Var = x7Var.k;
                m1 m1Var2 = m1.IDLE;
                if (m1Var == m1Var2) {
                    x7Var.x(m1Var2);
                }
            }
        } catch (JSONException unused) {
        }
    }

    public final void h(MusicPlayer musicPlayer) {
        MusicPlayer.m mVar;
        this.f = musicPlayer;
        if (musicPlayer != null && (mVar = this.i) != null) {
            musicPlayer.setStateController(mVar);
        }
    }
}
