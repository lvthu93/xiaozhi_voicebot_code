package defpackage;

import android.content.Context;
import info.dourok.voicebot.java.audio.MusicPlayer;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

/* renamed from: w  reason: default package */
public final /* synthetic */ class w implements Runnable {
    public final /* synthetic */ x c;
    public final /* synthetic */ String f;
    public final /* synthetic */ String g;
    public final /* synthetic */ String h;
    public final /* synthetic */ String i;
    public final /* synthetic */ String j;
    public final /* synthetic */ WebSocket k;

    public /* synthetic */ w(x xVar, String str, String str2, String str3, String str4, String str5, WebSocket webSocket) {
        this.c = xVar;
        this.f = str;
        this.g = str2;
        this.h = str3;
        this.i = str4;
        this.j = str5;
        this.k = webSocket;
    }

    public final void run() {
        Context context;
        a5 a5Var;
        String str = this.f;
        String str2 = this.g;
        String str3 = this.h;
        String str4 = this.i;
        String str5 = this.j;
        WebSocket webSocket = this.k;
        x xVar = this.c;
        xVar.getClass();
        try {
            MusicPlayer musicPlayer = xVar.g;
            if (musicPlayer == null && (a5Var = xVar.f) != null) {
                musicPlayer = a5Var.f;
            }
            if (!(musicPlayer != null || (context = xVar.c) == null || xVar.f == null)) {
                musicPlayer = new MusicPlayer(context);
                MusicPlayer.m mVar = xVar.f.i;
                if (mVar != null) {
                    musicPlayer.setStateController(mVar);
                }
                xVar.f.h(musicPlayer);
            }
            if (musicPlayer != null) {
                musicPlayer.playFromUrl(str, str2, str3);
                xVar.l = str2;
                xVar.m = str3;
                xVar.n = str4;
                xVar.o = str5;
                xVar.k = null;
                xVar.e();
                if (webSocket != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "play_zing");
                    jSONObject.put("success", true);
                    jSONObject.put("song_name", str2);
                    jSONObject.put("artist_name", str3);
                    x.bb(webSocket, jSONObject.toString());
                    return;
                }
                return;
            }
            x.bd(webSocket, "MusicPlayer not available");
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Failed to play: "), webSocket);
        }
    }
}
