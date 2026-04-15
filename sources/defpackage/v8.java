package defpackage;

import com.google.android.exoplayer2.ui.PlayerControlView;

/* renamed from: v8  reason: default package */
public final /* synthetic */ class v8 implements Runnable {
    public final /* synthetic */ int c;
    public final /* synthetic */ PlayerControlView f;

    public /* synthetic */ v8(PlayerControlView playerControlView, int i) {
        this.c = i;
        this.f = playerControlView;
    }

    public final void run() {
        int i = this.c;
        PlayerControlView playerControlView = this.f;
        switch (i) {
            case 0:
                int i2 = PlayerControlView.bg;
                playerControlView.g();
                return;
            default:
                playerControlView.hide();
                return;
        }
    }
}
