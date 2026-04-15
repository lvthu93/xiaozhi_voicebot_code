package defpackage;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.util.Util;

/* renamed from: bs  reason: default package */
public final /* synthetic */ class bs implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ AudioRendererEventListener.EventDispatcher f;
    public final /* synthetic */ DecoderCounters g;

    public /* synthetic */ bs(AudioRendererEventListener.EventDispatcher eventDispatcher, DecoderCounters decoderCounters, int i) {
        this.c = i;
        this.f = eventDispatcher;
        this.g = decoderCounters;
    }

    public final void run() {
        int i = this.c;
        DecoderCounters decoderCounters = this.g;
        AudioRendererEventListener.EventDispatcher eventDispatcher = this.f;
        switch (i) {
            case 0:
                eventDispatcher.getClass();
                decoderCounters.ensureUpdated();
                ((AudioRendererEventListener) Util.castNonNull(eventDispatcher.b)).onAudioDisabled(decoderCounters);
                return;
            default:
                ((AudioRendererEventListener) Util.castNonNull(eventDispatcher.b)).onAudioEnabled(decoderCounters);
                return;
        }
    }
}
