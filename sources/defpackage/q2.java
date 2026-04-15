package defpackage;

import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.a;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: q2  reason: default package */
public final /* synthetic */ class q2 implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ q2(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        Object obj2 = this.b;
        switch (i) {
            case 0:
                ((Player.EventListener) obj).onMediaMetadataChanged(((a) obj2).ad);
                return;
            case 1:
                ((Player.EventListener) obj).onAvailableCommandsChanged(((a) obj2).ac);
                return;
            default:
                ((Player.EventListener) obj).onMediaMetadataChanged((MediaMetadata) obj2);
                return;
        }
    }
}
