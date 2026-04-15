package defpackage;

import android.graphics.SurfaceTexture;

/* renamed from: ka  reason: default package */
public final /* synthetic */ class ka implements SurfaceTexture.OnFrameAvailableListener {
    public final /* synthetic */ la c;

    public /* synthetic */ ka(la laVar) {
        this.c = laVar;
    }

    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.c.c.set(true);
    }
}
