package defpackage;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: j2  reason: default package */
public final /* synthetic */ class j2 implements ListenerSet.Event {
    public final /* synthetic */ boolean a;

    public /* synthetic */ j2(boolean z) {
        this.a = z;
    }

    public final void invoke(Object obj) {
        ((Player.EventListener) obj).onShuffleModeEnabledChanged(this.a);
    }
}
