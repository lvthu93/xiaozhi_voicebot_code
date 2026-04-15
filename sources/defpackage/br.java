package defpackage;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.util.Util;

/* renamed from: br  reason: default package */
public final /* synthetic */ class br implements Runnable {
    public final /* synthetic */ AudioRendererEventListener.EventDispatcher c;
    public final /* synthetic */ long f;

    public /* synthetic */ br(AudioRendererEventListener.EventDispatcher eventDispatcher, long j) {
        this.c = eventDispatcher;
        this.f = j;
    }

    public final void run() {
        ((AudioRendererEventListener) Util.castNonNull(this.c.b)).onAudioPositionAdvancing(this.f);
    }
}
