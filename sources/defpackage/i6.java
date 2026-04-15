package defpackage;

import android.content.Context;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import defpackage.lf;
import info.dourok.voicebot.java.audio.MusicPlayer;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

/* renamed from: i6  reason: default package */
public final /* synthetic */ class i6 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;

    public /* synthetic */ i6(Object obj, Object obj2, Object obj3, Object obj4, int i2) {
        this.c = i2;
        this.f = obj;
        this.g = obj2;
        this.h = obj3;
        this.i = obj4;
    }

    public final void run() {
        Context context;
        a5 a5Var;
        int i2 = this.c;
        Object obj = this.i;
        Object obj2 = this.h;
        Object obj3 = this.g;
        Object obj4 = this.f;
        switch (i2) {
            case 0:
                ((MediaSourceEventListener) obj3).onUpstreamDiscarded(((MediaSourceEventListener.EventDispatcher) obj4).a, (MediaSource.MediaPeriodId) obj2, (MediaLoadData) obj);
                return;
            case 1:
                x xVar = (x) obj4;
                String str = (String) obj3;
                String str2 = (String) obj2;
                String str3 = (String) obj;
                int i3 = x.aa;
                xVar.getClass();
                try {
                    xVar.g.playFromUrl(str, str2, str3);
                    xVar.e();
                    return;
                } catch (Exception unused) {
                    return;
                }
            default:
                x xVar2 = (x) obj4;
                WebSocket webSocket = (WebSocket) obj3;
                lf.a aVar = (lf.a) obj2;
                String str4 = (String) obj;
                int i4 = x.aa;
                xVar2.getClass();
                try {
                    if (xVar2.g == null && (a5Var = xVar2.f) != null) {
                        xVar2.g = a5Var.f;
                    }
                    if (!(xVar2.g != null || (context = xVar2.c) == null || xVar2.f == null)) {
                        MusicPlayer musicPlayer = new MusicPlayer(context);
                        xVar2.g = musicPlayer;
                        xVar2.f.h(musicPlayer);
                    }
                    MusicPlayer musicPlayer2 = xVar2.g;
                    if (musicPlayer2 == null) {
                        x.bd(webSocket, "MusicPlayer not available");
                        return;
                    }
                    String str5 = aVar.a;
                    String str6 = aVar.b;
                    String str7 = aVar.e;
                    musicPlayer2.playFromUrl(str5, str7, str6);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "play_started");
                    jSONObject.put("success", true);
                    jSONObject.put("title", str7);
                    jSONObject.put("channel", str6);
                    jSONObject.put("video_id", aVar.f);
                    jSONObject.put("thumbnail_url", str4);
                    x.bb(webSocket, jSONObject.toString());
                    return;
                } catch (Exception e) {
                    y2.v(e, new StringBuilder("Play failed: "), webSocket);
                    return;
                }
        }
    }
}
