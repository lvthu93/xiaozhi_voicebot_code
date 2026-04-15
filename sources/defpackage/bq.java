package defpackage;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.util.Util;

/* renamed from: bq  reason: default package */
public final /* synthetic */ class bq implements Runnable {
    public final /* synthetic */ AudioRendererEventListener.EventDispatcher c;
    public final /* synthetic */ boolean f;

    public /* synthetic */ bq(AudioRendererEventListener.EventDispatcher eventDispatcher, boolean z) {
        this.c = eventDispatcher;
        this.f = z;
    }

    public final void run() {
        ((AudioRendererEventListener) Util.castNonNull(this.c.b)).onSkipSilenceEnabledChanged(this.f);
    }
}
