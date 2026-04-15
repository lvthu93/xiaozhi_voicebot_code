package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ab  reason: default package */
public final class ab {
    public int a;
    public int b;
    public int c;
    public String d;
    public boolean e;
    public String f;
    public int g;
    public String h;
    public String i;

    public ab() {
        this.a = -1;
        this.b = 0;
        this.c = 0;
        this.d = "none";
        this.e = true;
        this.f = "";
        this.g = 100;
        this.h = null;
        this.i = null;
    }

    public static ab a(JSONObject jSONObject) throws JSONException {
        ab abVar = new ab();
        abVar.a = jSONObject.optInt("id", -1);
        abVar.b = jSONObject.getInt("hour");
        abVar.c = jSONObject.getInt("minute");
        abVar.d = jSONObject.optString("repeat", "none");
        abVar.e = jSONObject.optBoolean("enabled", true);
        abVar.f = jSONObject.optString("label", "");
        abVar.g = jSONObject.optInt("volume", 100);
        abVar.h = jSONObject.optString("custom_sound_path", (String) null);
        abVar.i = jSONObject.optString("youtube_song_name", (String) null);
        return abVar;
    }

    public final String b() {
        String str = this.d;
        str.getClass();
        return !str.equals("weekly") ? !str.equals("daily") ? "Một lần" : "Hàng ngày" : "Hàng tuần";
    }

    public final String c() {
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(this.b), Integer.valueOf(this.c)});
    }

    public final JSONObject d() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", this.a);
        jSONObject.put("hour", this.b);
        jSONObject.put("minute", this.c);
        jSONObject.put("repeat", this.d);
        jSONObject.put("enabled", this.e);
        jSONObject.put("label", this.f);
        jSONObject.put("volume", this.g);
        String str = this.h;
        if (str != null) {
            jSONObject.put("custom_sound_path", str);
        }
        String str2 = this.i;
        if (str2 != null) {
            jSONObject.put("youtube_song_name", str2);
        }
        return jSONObject;
    }

    public ab(int i2, int i3, int i4, String str, String str2, int i5) {
        this.a = i2;
        this.b = i3;
        this.c = i4;
        this.d = str == null ? "none" : str;
        this.e = true;
        this.f = str2 == null ? "" : str2;
        this.g = (i5 < 0 || i5 > 100) ? 100 : i5;
        this.h = null;
        this.i = null;
    }
}
