package defpackage;

import android.os.HandlerThread;
import com.google.android.exoplayer2.mediacodec.AsynchronousMediaCodecAdapter;
import com.google.common.base.Supplier;

/* renamed from: bf  reason: default package */
public final /* synthetic */ class bf implements Supplier {
    public final /* synthetic */ int c;
    public final /* synthetic */ int f;

    public /* synthetic */ bf(int i, int i2) {
        this.c = i2;
        this.f = i;
    }

    public final Object get() {
        int i = this.c;
        int i2 = this.f;
        switch (i) {
            case 0:
                return new HandlerThread(AsynchronousMediaCodecAdapter.a(i2, "ExoPlayer:MediaCodecAsyncAdapter:"));
            default:
                return new HandlerThread(AsynchronousMediaCodecAdapter.a(i2, "ExoPlayer:MediaCodecQueueingThread:"));
        }
    }
}
