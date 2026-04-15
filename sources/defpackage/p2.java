package defpackage;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: p2  reason: default package */
public final /* synthetic */ class p2 implements ListenerSet.Event {
    public final /* synthetic */ int a;

    public /* synthetic */ p2(int i) {
        this.a = i;
    }

    public final void invoke(Object obj) {
        ((Player.EventListener) obj).onRepeatModeChanged(this.a);
    }
}
