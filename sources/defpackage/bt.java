package defpackage;

import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Util;

/* renamed from: bt  reason: default package */
public final /* synthetic */ class bt implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ int f;
    public final /* synthetic */ long g;
    public final /* synthetic */ long h;
    public final /* synthetic */ Object i;

    public /* synthetic */ bt(Object obj, int i2, long j, long j2, int i3) {
        this.c = i3;
        this.i = obj;
        this.f = i2;
        this.g = j;
        this.h = j2;
    }

    public final void run() {
        int i2 = this.c;
        Object obj = this.i;
        switch (i2) {
            case 0:
                ((AudioRendererEventListener) Util.castNonNull(((AudioRendererEventListener.EventDispatcher) obj).b)).onAudioUnderrun(this.f, this.g, this.h);
                return;
            default:
                ((BandwidthMeter.EventListener.EventDispatcher.a) obj).b.onBandwidthSample(this.f, this.g, this.h);
                return;
        }
    }
}
