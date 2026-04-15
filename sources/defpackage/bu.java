package defpackage;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.util.Util;

/* renamed from: bu  reason: default package */
public final /* synthetic */ class bu implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ AudioRendererEventListener.EventDispatcher f;
    public final /* synthetic */ Exception g;

    public /* synthetic */ bu(AudioRendererEventListener.EventDispatcher eventDispatcher, Exception exc, int i) {
        this.c = i;
        this.f = eventDispatcher;
        this.g = exc;
    }

    public final void run() {
        int i = this.c;
        Exception exc = this.g;
        AudioRendererEventListener.EventDispatcher eventDispatcher = this.f;
        switch (i) {
            case 0:
                ((AudioRendererEventListener) Util.castNonNull(eventDispatcher.b)).onAudioSinkError(exc);
                return;
            default:
                ((AudioRendererEventListener) Util.castNonNull(eventDispatcher.b)).onAudioCodecError(exc);
                return;
        }
    }
}
