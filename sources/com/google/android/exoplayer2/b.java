package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Renderer;

public final class b implements Renderer.WakeupListener {
    public final /* synthetic */ ExoPlayerImplInternal a;

    public b(ExoPlayerImplInternal exoPlayerImplInternal) {
        this.a = exoPlayerImplInternal;
    }

    public void onSleep(long j) {
        if (j >= 2000) {
            this.a.ak = true;
        }
    }

    public void onWakeup() {
        this.a.k.sendEmptyMessage(2);
    }
}
