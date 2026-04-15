package defpackage;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.a;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: l2  reason: default package */
public final /* synthetic */ class l2 implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ s8 b;

    public /* synthetic */ l2(s8 s8Var, int i) {
        this.a = i;
        this.b = s8Var;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        s8 s8Var = this.b;
        switch (i) {
            case 0:
                ((Player.EventListener) obj).onPlaybackSuppressionReasonChanged(s8Var.m);
                return;
            case 1:
                ((Player.EventListener) obj).onIsPlayingChanged(a.h(s8Var));
                return;
            case 2:
                ((Player.EventListener) obj).onPlaybackParametersChanged(s8Var.n);
                return;
            case 3:
                ((Player.EventListener) obj).onPlayerError(s8Var.f);
                return;
            case 4:
                ((Player.EventListener) obj).onStaticMetadataChanged(s8Var.j);
                return;
            case 5:
                Player.EventListener eventListener = (Player.EventListener) obj;
                eventListener.onLoadingChanged(s8Var.g);
                eventListener.onIsLoadingChanged(s8Var.g);
                return;
            case 6:
                ((Player.EventListener) obj).onPlayerStateChanged(s8Var.l, s8Var.e);
                return;
            default:
                ((Player.EventListener) obj).onPlaybackStateChanged(s8Var.e);
                return;
        }
    }
}
