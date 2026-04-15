package defpackage;

import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* renamed from: sd  reason: default package */
public final /* synthetic */ class sd implements Runnable {
    public final /* synthetic */ VideoRendererEventListener.EventDispatcher c;
    public final /* synthetic */ Object f;
    public final /* synthetic */ long g;

    public /* synthetic */ sd(VideoRendererEventListener.EventDispatcher eventDispatcher, Object obj, long j) {
        this.c = eventDispatcher;
        this.f = obj;
        this.g = j;
    }

    public final void run() {
        ((VideoRendererEventListener) Util.castNonNull(this.c.b)).onRenderedFirstFrame(this.f, this.g);
    }
}
