package defpackage;

import android.media.MediaCodec;
import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.SynchronousMediaCodecAdapter;

/* renamed from: be  reason: default package */
public final /* synthetic */ class be implements MediaCodec.OnFrameRenderedListener {
    public final /* synthetic */ int a;
    public final /* synthetic */ MediaCodecAdapter.OnFrameRenderedListener b;
    public final /* synthetic */ MediaCodecAdapter c;

    public /* synthetic */ be(MediaCodecAdapter mediaCodecAdapter, MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener, int i) {
        this.a = i;
        this.c = mediaCodecAdapter;
        this.b = onFrameRenderedListener;
    }

    public final void onFrameRendered(MediaCodec mediaCodec, long j, long j2) {
        switch (this.a) {
            case 0:
                AsynchronousMediaCodecAdapter asynchronousMediaCodecAdapter = (AsynchronousMediaCodecAdapter) this.c;
                MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener = this.b;
                asynchronousMediaCodecAdapter.getClass();
                onFrameRenderedListener.onFrameRendered(asynchronousMediaCodecAdapter, j, j2);
                return;
            default:
                SynchronousMediaCodecAdapter synchronousMediaCodecAdapter = (SynchronousMediaCodecAdapter) this.c;
                MediaCodecAdapter.OnFrameRenderedListener onFrameRenderedListener2 = this.b;
                synchronousMediaCodecAdapter.getClass();
                onFrameRenderedListener2.onFrameRendered(synchronousMediaCodecAdapter, j, j2);
                return;
        }
    }
}
