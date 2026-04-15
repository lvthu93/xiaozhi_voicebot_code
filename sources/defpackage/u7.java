package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: u7  reason: default package */
public final class u7 {
    public a a;
    public b b;

    /* renamed from: u7$a */
    public static class a {
        public String a;
    }

    /* renamed from: u7$b */
    public static class b {
        public String a;
        public String b;
        public String c;
        public String d;
        public String e;
        public String f;
    }

    public static u7 a(JSONObject jSONObject) throws JSONException {
        String str;
        u7 u7Var = new u7();
        if (jSONObject.has("mqtt")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("mqtt");
            b bVar = new b();
            bVar.b = jSONObject2.getString("endpoint");
            bVar.a = jSONObject2.getString("client_id");
            bVar.f = jSONObject2.getString("username");
            bVar.c = jSONObject2.getString("password");
            bVar.d = jSONObject2.getString("publish_topic");
            String str2 = "";
            String optString = jSONObject2.optString("subscribe_topic", str2);
            if (optString.isEmpty() || "null".equals(optString)) {
                String string = jSONObject2.getString("client_id");
                if (string != null && string.contains("@@@")) {
                    String[] split = string.split("@@@");
                    if (split.length >= 2) {
                        str2 = split[1];
                    }
                }
                if (!str2.isEmpty()) {
                    str = "devices/p2p/".concat(str2);
                } else {
                    str = y2.i("devices/p2p/", string);
                }
                optString = str;
            }
            bVar.e = optString;
            u7Var.b = bVar;
        }
        if (jSONObject.has("activation")) {
            JSONObject jSONObject3 = jSONObject.getJSONObject("activation");
            a aVar = new a();
            aVar.a = jSONObject3.getString("code");
            jSONObject3.getString("message");
            u7Var.a = aVar;
        }
        if (jSONObject.has("server_time")) {
            JSONObject jSONObject4 = jSONObject.getJSONObject("server_time");
            jSONObject4.getLong("timestamp");
            jSONObject4.optString("timezone", (String) null);
            jSONObject4.getInt("timezone_offset");
        }
        if (jSONObject.has("firmware")) {
            JSONObject jSONObject5 = jSONObject.getJSONObject("firmware");
            jSONObject5.getString("version");
            jSONObject5.getString("url");
        }
        if (jSONObject.has("websocket")) {
            JSONObject jSONObject6 = jSONObject.getJSONObject("websocket");
            jSONObject6.getString("url");
            jSONObject6.getString("token");
        }
        return u7Var;
    }
}
