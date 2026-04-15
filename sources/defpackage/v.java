package defpackage;

import android.os.Handler;
import android.os.Looper;
import info.dourok.voicebot.java.services.ZingMp3Service;
import org.java_websocket.WebSocket;

/* renamed from: v  reason: default package */
public final /* synthetic */ class v implements Runnable {
    public final /* synthetic */ x c;
    public final /* synthetic */ String f;
    public final /* synthetic */ WebSocket g;
    public final /* synthetic */ String h;
    public final /* synthetic */ String i;
    public final /* synthetic */ String j;

    public /* synthetic */ v(x xVar, String str, WebSocket webSocket, String str2, String str3, String str4) {
        this.c = xVar;
        this.f = str;
        this.g = webSocket;
        this.h = str2;
        this.i = str3;
        this.j = str4;
    }

    public final void run() {
        String str = this.f;
        WebSocket webSocket = this.g;
        String str2 = this.h;
        String str3 = this.i;
        String str4 = this.j;
        x xVar = this.c;
        xVar.getClass();
        try {
            String streamLink = new ZingMp3Service().getStreamLink(str);
            if (streamLink != null) {
                if (!streamLink.isEmpty()) {
                    new Handler(Looper.getMainLooper()).post(new w(xVar, streamLink, str2, str3, str4, str, webSocket));
                    return;
                }
            }
            x.bd(webSocket, "Không thể lấy stream URL cho bài hát");
        } catch (Exception e) {
            y2.v(e, new StringBuilder("Failed to get stream link: "), webSocket);
        }
    }
}
