package defpackage;

import android.media.MediaDrm;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;

/* renamed from: l3  reason: default package */
public final /* synthetic */ class l3 implements MediaDrm.OnEventListener {
    public final /* synthetic */ FrameworkMediaDrm a;
    public final /* synthetic */ ExoMediaDrm.OnEventListener b;

    public /* synthetic */ l3(FrameworkMediaDrm frameworkMediaDrm, ExoMediaDrm.OnEventListener onEventListener) {
        this.a = frameworkMediaDrm;
        this.b = onEventListener;
    }

    public final void onEvent(MediaDrm mediaDrm, byte[] bArr, int i, int i2, byte[] bArr2) {
        ExoMediaDrm.OnEventListener onEventListener = this.b;
        FrameworkMediaDrm frameworkMediaDrm = this.a;
        frameworkMediaDrm.getClass();
        onEventListener.onEvent(frameworkMediaDrm, bArr, i, i2, bArr2);
    }
}
