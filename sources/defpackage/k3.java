package defpackage;

import android.media.MediaDrm;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;

/* renamed from: k3  reason: default package */
public final /* synthetic */ class k3 implements MediaDrm.OnExpirationUpdateListener {
    public final /* synthetic */ FrameworkMediaDrm a;
    public final /* synthetic */ ExoMediaDrm.OnExpirationUpdateListener b;

    public /* synthetic */ k3(FrameworkMediaDrm frameworkMediaDrm, ExoMediaDrm.OnExpirationUpdateListener onExpirationUpdateListener) {
        this.a = frameworkMediaDrm;
        this.b = onExpirationUpdateListener;
    }

    public final void onExpirationUpdate(MediaDrm mediaDrm, byte[] bArr, long j) {
        FrameworkMediaDrm frameworkMediaDrm = this.a;
        ExoMediaDrm.OnExpirationUpdateListener onExpirationUpdateListener = this.b;
        frameworkMediaDrm.getClass();
        onExpirationUpdateListener.onExpirationUpdate(frameworkMediaDrm, bArr, j);
    }
}
