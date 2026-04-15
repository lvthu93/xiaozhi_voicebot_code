package defpackage;

import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* renamed from: qd  reason: default package */
public final /* synthetic */ class qd implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ VideoRendererEventListener.EventDispatcher f;
    public final /* synthetic */ DecoderCounters g;

    public /* synthetic */ qd(VideoRendererEventListener.EventDispatcher eventDispatcher, DecoderCounters decoderCounters, int i) {
        this.c = i;
        this.f = eventDispatcher;
        this.g = decoderCounters;
    }

    public final void run() {
        int i = this.c;
        DecoderCounters decoderCounters = this.g;
        VideoRendererEventListener.EventDispatcher eventDispatcher = this.f;
        switch (i) {
            case 0:
                eventDispatcher.getClass();
                decoderCounters.ensureUpdated();
                ((VideoRendererEventListener) Util.castNonNull(eventDispatcher.b)).onVideoDisabled(decoderCounters);
                return;
            default:
                ((VideoRendererEventListener) Util.castNonNull(eventDispatcher.b)).onVideoEnabled(decoderCounters);
                return;
        }
    }
}
