package defpackage;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoTimeoutException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: m2  reason: default package */
public final /* synthetic */ class m2 implements ListenerSet.Event {
    public final /* synthetic */ int a;

    public /* synthetic */ m2(int i) {
        this.a = i;
    }

    public final void invoke(Object obj) {
        switch (this.a) {
            case 0:
                ((Player.EventListener) obj).onSeekProcessed();
                return;
            default:
                ((Player.EventListener) obj).onPlayerError(ExoPlaybackException.createForRenderer(new ExoTimeoutException(1)));
                return;
        }
    }
}
