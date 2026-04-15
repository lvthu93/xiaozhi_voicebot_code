package defpackage;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.d;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.drm.DrmSessionEventListener;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.common.collect.ImmutableList;
import defpackage.lf;
import info.dourok.voicebot.java.VoiceBotApplication;
import info.dourok.voicebot.java.audio.MusicPlayer;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

/* renamed from: d6  reason: default package */
public final /* synthetic */ class d6 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ Object f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;

    public /* synthetic */ d6(int i, Object obj, Object obj2, Object obj3) {
        this.c = i;
        this.f = obj;
        this.g = obj2;
        this.h = obj3;
    }

    public /* synthetic */ d6(AdsMediaSource.b bVar, MediaSource.MediaPeriodId mediaPeriodId, IOException iOException) {
        this.c = 4;
        this.f = bVar;
        this.h = mediaPeriodId;
        this.g = iOException;
    }

    public final void run() {
        Context context;
        a5 a5Var;
        String str;
        int i = this.c;
        Object obj = this.h;
        Object obj2 = this.g;
        Object obj3 = this.f;
        switch (i) {
            case 0:
                ((d) obj3).c.updateMediaPeriodQueueInfo(((ImmutableList.Builder) obj2).build(), (MediaSource.MediaPeriodId) obj);
                return;
            case 1:
                Format format = (Format) obj2;
                AudioRendererEventListener audioRendererEventListener = ((AudioRendererEventListener.EventDispatcher) obj3).b;
                ((AudioRendererEventListener) Util.castNonNull(audioRendererEventListener)).onAudioInputFormatChanged(format);
                ((AudioRendererEventListener) Util.castNonNull(audioRendererEventListener)).onAudioInputFormatChanged(format, (DecoderReuseEvaluation) obj);
                return;
            case 2:
                DrmSessionEventListener.EventDispatcher eventDispatcher = (DrmSessionEventListener.EventDispatcher) obj3;
                ((DrmSessionEventListener) obj2).onDrmSessionManagerError(eventDispatcher.a, eventDispatcher.b, (Exception) obj);
                return;
            case 3:
                MediaSourceEventListener.EventDispatcher eventDispatcher2 = (MediaSourceEventListener.EventDispatcher) obj3;
                ((MediaSourceEventListener) obj2).onDownstreamFormatChanged(eventDispatcher2.a, eventDispatcher2.b, (MediaLoadData) obj);
                return;
            case 4:
                MediaSource.MediaPeriodId mediaPeriodId = (MediaSource.MediaPeriodId) obj;
                AdsMediaSource adsMediaSource = AdsMediaSource.this;
                adsMediaSource.l.handlePrepareError(adsMediaSource, mediaPeriodId.b, mediaPeriodId.c, (IOException) obj2);
                return;
            case 5:
                Format format2 = (Format) obj2;
                VideoRendererEventListener videoRendererEventListener = ((VideoRendererEventListener.EventDispatcher) obj3).b;
                ((VideoRendererEventListener) Util.castNonNull(videoRendererEventListener)).onVideoInputFormatChanged(format2);
                ((VideoRendererEventListener) Util.castNonNull(videoRendererEventListener)).onVideoInputFormatChanged(format2, (DecoderReuseEvaluation) obj);
                return;
            case 6:
                VoiceBotApplication.lambda$forwardChatTextToAi$5((t9) obj3, (String) obj2, (CountDownLatch) obj);
                return;
            case 7:
                x xVar = (x) obj3;
                String str2 = (String) obj2;
                WebSocket webSocket = (WebSocket) obj;
                int i2 = x.aa;
                xVar.getClass();
                try {
                    lf.a a = lf.a(str2);
                    if (!a.d) {
                        x.bd(webSocket, a.c);
                        return;
                    }
                    Iterator it = xVar.h.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            lf.c cVar = (lf.c) it.next();
                            if (cVar.a.equals(str2)) {
                                str = cVar.e;
                                xVar.k = cVar;
                            }
                        } else {
                            str = null;
                        }
                    }
                    if (str == null) {
                        str = "";
                    }
                    new Handler(Looper.getMainLooper()).post(new i6(xVar, webSocket, a, str, 2));
                    return;
                } catch (Exception e) {
                    y2.v(e, new StringBuilder("Extraction failed: "), webSocket);
                    return;
                }
            case 8:
                x xVar2 = (x) obj3;
                WebSocket webSocket2 = (WebSocket) obj2;
                JSONObject jSONObject = (JSONObject) obj;
                int i3 = x.aa;
                xVar2.getClass();
                try {
                    MusicPlayer musicPlayer = new MusicPlayer(xVar2.c);
                    xVar2.g = musicPlayer;
                    xVar2.f.h(musicPlayer);
                    xVar2.ac(webSocket2, jSONObject);
                    return;
                } catch (Exception e2) {
                    y2.v(e2, new StringBuilder("Failed to create MusicPlayer: "), webSocket2);
                    return;
                }
            default:
                x xVar3 = (x) obj3;
                lf.a aVar = (lf.a) obj2;
                lf.c cVar2 = (lf.c) obj;
                int i4 = x.aa;
                xVar3.getClass();
                try {
                    if (xVar3.g == null && (a5Var = xVar3.f) != null) {
                        xVar3.g = a5Var.f;
                    }
                    if (!(xVar3.g != null || (context = xVar3.c) == null || xVar3.f == null)) {
                        MusicPlayer musicPlayer2 = new MusicPlayer(context);
                        xVar3.g = musicPlayer2;
                        xVar3.f.h(musicPlayer2);
                    }
                    xVar3.f();
                    MusicPlayer musicPlayer3 = xVar3.g;
                    if (musicPlayer3 != null) {
                        musicPlayer3.playFromUrl(aVar.a, aVar.e, aVar.b);
                        xVar3.k = cVar2;
                        return;
                    }
                    return;
                } catch (Exception unused) {
                    return;
                }
        }
    }
}
