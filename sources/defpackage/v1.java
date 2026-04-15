package defpackage;

import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.util.ListenerSet;
import info.dourok.voicebot.java.audio.MusicPlayer;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

/* renamed from: v1  reason: default package */
public final /* synthetic */ class v1 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    public /* synthetic */ v1(int i, Object obj, Object obj2, int i2) {
        this.c = i2;
        this.g = obj;
        this.f = i;
        this.h = obj2;
    }

    public /* synthetic */ v1(DrmSessionEventListener.EventDispatcher eventDispatcher, DrmSessionEventListener drmSessionEventListener, int i) {
        this.c = 0;
        this.g = eventDispatcher;
        this.h = drmSessionEventListener;
        this.f = i;
    }

    public final void run() {
        a5 a5Var;
        int i = this.c;
        int i2 = this.f;
        Object obj = this.h;
        Object obj2 = this.g;
        switch (i) {
            case 0:
                DrmSessionEventListener.EventDispatcher eventDispatcher = (DrmSessionEventListener.EventDispatcher) obj2;
                DrmSessionEventListener drmSessionEventListener = (DrmSessionEventListener) obj;
                int i3 = eventDispatcher.a;
                MediaSource.MediaPeriodId mediaPeriodId = eventDispatcher.b;
                drmSessionEventListener.onDrmSessionAcquired(i3, mediaPeriodId);
                drmSessionEventListener.onDrmSessionAcquired(i3, mediaPeriodId, i2);
                return;
            case 1:
                ListenerSet.Event event = (ListenerSet.Event) obj;
                Iterator it = ((CopyOnWriteArraySet) obj2).iterator();
                while (it.hasNext()) {
                    ((ListenerSet.a) it.next()).invoke(i2, event);
                }
                return;
            default:
                x xVar = (x) obj2;
                WebSocket webSocket = (WebSocket) obj;
                int i4 = x.aa;
                xVar.getClass();
                try {
                    if (xVar.g == null && (a5Var = xVar.f) != null) {
                        xVar.g = a5Var.f;
                    }
                    xVar.f();
                    MusicPlayer musicPlayer = xVar.g;
                    if (musicPlayer != null) {
                        musicPlayer.seekTo((long) (i2 * 1000));
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("type", "seek");
                        jSONObject.put("success", true);
                        jSONObject.put("position", i2);
                        x.bb(webSocket, jSONObject.toString());
                        return;
                    }
                    x.bd(webSocket, "MusicPlayer not available");
                    return;
                } catch (Exception e) {
                    y2.v(e, new StringBuilder("Failed to seek: "), webSocket);
                    return;
                }
        }
    }
}
