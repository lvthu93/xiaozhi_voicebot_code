package defpackage;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* renamed from: bv  reason: default package */
public final /* synthetic */ class bv implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ String f;
    public final /* synthetic */ long g;
    public final /* synthetic */ long h;
    public final /* synthetic */ Object i;

    public /* synthetic */ bv(Object obj, String str, long j, long j2, int i2) {
        this.c = i2;
        this.i = obj;
        this.f = str;
        this.g = j;
        this.h = j2;
    }

    public final void run() {
        int i2 = this.c;
        Object obj = this.i;
        switch (i2) {
            case 0:
                ((AudioRendererEventListener) Util.castNonNull(((AudioRendererEventListener.EventDispatcher) obj).b)).onAudioDecoderInitialized(this.f, this.g, this.h);
                return;
            default:
                ((VideoRendererEventListener) Util.castNonNull(((VideoRendererEventListener.EventDispatcher) obj).b)).onVideoDecoderInitialized(this.f, this.g, this.h);
                return;
        }
    }
}
