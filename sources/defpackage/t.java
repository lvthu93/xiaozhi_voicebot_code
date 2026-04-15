package defpackage;

import android.content.Context;
import info.dourok.voicebot.java.audio.MusicPlayer;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

/* renamed from: t  reason: default package */
public final /* synthetic */ class t implements Runnable {
    public final /* synthetic */ x c;
    public final /* synthetic */ MusicPlayer f;
    public final /* synthetic */ String g;
    public final /* synthetic */ String h;
    public final /* synthetic */ String i;
    public final /* synthetic */ String j;
    public final /* synthetic */ String k;
    public final /* synthetic */ WebSocket l;

    public /* synthetic */ t(x xVar, MusicPlayer musicPlayer, String str, String str2, String str3, String str4, String str5, WebSocket webSocket) {
        this.c = xVar;
        this.f = musicPlayer;
        this.g = str;
        this.h = str2;
        this.i = str3;
        this.j = str4;
        this.k = str5;
        this.l = webSocket;
    }

    public final void run() {
        Context context;
        String str = this.g;
        String str2 = this.h;
        String str3 = this.i;
        String str4 = this.j;
        String str5 = this.k;
        x xVar = this.c;
        xVar.getClass();
        MusicPlayer musicPlayer = this.f;
        WebSocket webSocket = this.l;
        if (musicPlayer == null) {
            try {
                a5 a5Var = xVar.f;
                if (a5Var != null) {
                    musicPlayer = a5Var.f;
                }
            } catch (Exception e) {
                y2.v(e, new StringBuilder("Failed to play: "), webSocket);
                return;
            }
        }
        if (!(musicPlayer != null || (context = xVar.c) == null || xVar.f == null)) {
            musicPlayer = new MusicPlayer(context);
            MusicPlayer.m mVar = xVar.f.i;
            if (mVar != null) {
                musicPlayer.setStateController(mVar);
            }
            xVar.f.h(musicPlayer);
        }
        xVar.g = musicPlayer;
        xVar.f();
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
    }
}
