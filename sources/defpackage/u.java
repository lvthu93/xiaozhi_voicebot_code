package defpackage;

import android.content.Context;
import info.dourok.voicebot.java.audio.MusicPlayer;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

/* renamed from: u  reason: default package */
public final /* synthetic */ class u implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ x f;
    public final /* synthetic */ WebSocket g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ int i;

    public /* synthetic */ u(x xVar, WebSocket webSocket, Object obj, int i2, int i3) {
        this.c = i3;
        this.f = xVar;
        this.g = webSocket;
        this.h = obj;
        this.i = i2;
    }

    public final void run() {
        Context context;
        Context context2;
        int i2 = this.c;
        int i3 = this.i;
        WebSocket webSocket = this.g;
        x xVar = this.f;
        Object obj = this.h;
        switch (i2) {
            case 0:
                JSONObject jSONObject = (JSONObject) obj;
                a5 a5Var = xVar.f;
                if (a5Var != null) {
                    MusicPlayer musicPlayer = a5Var.f;
                    xVar.g = musicPlayer;
                    if (musicPlayer != null) {
                        xVar.ac(webSocket, jSONObject);
                        return;
                    } else {
                        xVar.ay(webSocket, jSONObject, i3 + 1);
                        return;
                    }
                } else {
                    x.bd(webSocket, "MCP Device Server not available.");
                    return;
                }
            case 1:
                JSONObject jSONObject2 = (JSONObject) obj;
                a5 a5Var2 = xVar.f;
                if (a5Var2 != null) {
                    xVar.g = a5Var2.f;
                }
                if (!(xVar.g != null || (context2 = xVar.c) == null || a5Var2 == null)) {
                    MusicPlayer musicPlayer2 = new MusicPlayer(context2);
                    xVar.g = musicPlayer2;
                    MusicPlayer.m mVar = xVar.f.i;
                    if (mVar != null) {
                        musicPlayer2.setStateController(mVar);
                    }
                    xVar.f.h(xVar.g);
                }
                if (xVar.g != null) {
                    xVar.ad(webSocket, jSONObject2);
                    return;
                } else {
                    xVar.az(webSocket, jSONObject2, i3 + 1);
                    return;
                }
            default:
                String str = (String) obj;
                a5 a5Var3 = xVar.f;
                if (a5Var3 != null) {
                    xVar.g = a5Var3.f;
                }
                if (!(xVar.g != null || (context = xVar.c) == null || a5Var3 == null)) {
                    MusicPlayer musicPlayer3 = new MusicPlayer(context);
                    xVar.g = musicPlayer3;
                    MusicPlayer.m mVar2 = xVar.f.i;
                    if (mVar2 != null) {
                        musicPlayer3.setStateController(mVar2);
                    }
                    xVar.f.h(xVar.g);
                }
                if (xVar.g != null) {
                    xVar.aw(webSocket, str);
                    return;
                } else {
                    xVar.ba(webSocket, i3 + 1, str);
                    return;
                }
        }
    }
}
