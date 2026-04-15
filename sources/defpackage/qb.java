package defpackage;

import android.graphics.SurfaceTexture;
import android.media.MediaCodec;
import android.view.Surface;
import com.google.android.exoplayer2.StreamVolumeManager;
import com.google.android.exoplayer2.drm.DefaultDrmSession;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.offline.DownloadHelper;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.video.spherical.SphericalGLSurfaceView;
import info.dourok.voicebot.java.VoiceBotApplication;
import info.dourok.voicebot.java.services.ZaloMqttClient;
import java.util.Iterator;
import org.java_websocket.WebSocket;

/* renamed from: qb  reason: default package */
public final /* synthetic */ class qb implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;

    public /* synthetic */ qb(int i, Object obj) {
        this.c = i;
        this.f = obj;
    }

    public final void run() {
        int i = this.c;
        Object obj = this.f;
        switch (i) {
            case 0:
                ((StreamVolumeManager) obj).b();
                return;
            case 1:
                DefaultDrmSessionManager.c cVar = (DefaultDrmSessionManager.c) obj;
                if (!cVar.g) {
                    DrmSession drmSession = cVar.f;
                    if (drmSession != null) {
                        drmSession.release(cVar.c);
                    }
                    DefaultDrmSessionManager.this.n.remove(cVar);
                    cVar.g = true;
                    return;
                }
                return;
            case 2:
                ((DefaultDrmSession) obj).release((DrmSessionEventListener.EventDispatcher) null);
                return;
            case 3:
                ((MediaCodec) obj).start();
                return;
            case 4:
                DownloadHelper downloadHelper = (DownloadHelper) obj;
                ((DownloadHelper.Callback) Assertions.checkNotNull(downloadHelper.i)).onPrepared(downloadHelper);
                return;
            case 5:
                ((DownloadService.b) obj).a();
                return;
            case 6:
                int i2 = DefaultTimeBar.at;
                ((DefaultTimeBar) obj).d(false);
                return;
            case 7:
                int i3 = StyledPlayerControlView.cf;
                ((StyledPlayerControlView) obj).j();
                return;
            case 8:
                SphericalGLSurfaceView sphericalGLSurfaceView = (SphericalGLSurfaceView) obj;
                Surface surface = sphericalGLSurfaceView.l;
                if (surface != null) {
                    Iterator<SphericalGLSurfaceView.VideoSurfaceListener> it = sphericalGLSurfaceView.c.iterator();
                    while (it.hasNext()) {
                        it.next().onVideoSurfaceDestroyed(surface);
                    }
                }
                SurfaceTexture surfaceTexture = sphericalGLSurfaceView.k;
                if (surfaceTexture != null) {
                    surfaceTexture.release();
                }
                if (surface != null) {
                    surface.release();
                }
                sphericalGLSurfaceView.k = null;
                sphericalGLSurfaceView.l = null;
                return;
            case 9:
                VoiceBotApplication.lambda$playZaloAudio$8((String) obj);
                return;
            case 10:
                int i4 = x.aa;
                ((x) obj).ab((WebSocket) null);
                return;
            case 11:
                aa aaVar = (aa) obj;
                aaVar.getClass();
                try {
                    Thread.sleep(200);
                    aaVar.close();
                    return;
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return;
                }
            default:
                ((ZaloMqttClient) obj).lambda$scheduleReconnect$0();
                return;
        }
    }
}
