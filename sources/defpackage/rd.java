package defpackage;

import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* renamed from: rd  reason: default package */
public final /* synthetic */ class rd implements Runnable {
    public final /* synthetic */ int c = 1;
    public final /* synthetic */ VideoRendererEventListener.EventDispatcher f;
    public final /* synthetic */ int g;
    public final /* synthetic */ long h;

    public /* synthetic */ rd(VideoRendererEventListener.EventDispatcher eventDispatcher, int i, long j) {
        this.f = eventDispatcher;
        this.g = i;
        this.h = j;
    }

    public /* synthetic */ rd(VideoRendererEventListener.EventDispatcher eventDispatcher, long j, int i) {
        this.f = eventDispatcher;
        this.h = j;
        this.g = i;
    }

    public final void run() {
        int i = this.c;
        int i2 = this.g;
        long j = this.h;
        VideoRendererEventListener.EventDispatcher eventDispatcher = this.f;
        switch (i) {
            case 0:
                ((VideoRendererEventListener) Util.castNonNull(eventDispatcher.b)).onVideoFrameProcessingOffset(j, i2);
                return;
            default:
                ((VideoRendererEventListener) Util.castNonNull(eventDispatcher.b)).onDroppedFrames(i2, j);
                return;
        }
    }
}
