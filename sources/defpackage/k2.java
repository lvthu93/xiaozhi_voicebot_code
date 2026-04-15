package defpackage;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: k2  reason: default package */
public final /* synthetic */ class k2 implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ int c;

    public /* synthetic */ k2(Object obj, int i, int i2) {
        this.a = i2;
        this.b = obj;
        this.c = i;
    }

    public final void invoke(Object obj) {
        Object obj2;
        int i = this.a;
        int i2 = this.c;
        Object obj3 = this.b;
        switch (i) {
            case 0:
                s8 s8Var = (s8) obj3;
                Player.EventListener eventListener = (Player.EventListener) obj;
                int windowCount = s8Var.a.getWindowCount();
                Timeline timeline = s8Var.a;
                if (windowCount == 1) {
                    obj2 = timeline.getWindow(0, new Timeline.Window()).h;
                } else {
                    obj2 = null;
                }
                eventListener.onTimelineChanged(timeline, obj2, i2);
                eventListener.onTimelineChanged(timeline, i2);
                return;
            case 1:
                ((Player.EventListener) obj).onPlayWhenReadyChanged(((s8) obj3).l, i2);
                return;
            default:
                ((Player.EventListener) obj).onMediaItemTransition((MediaItem) obj3, i2);
                return;
        }
    }
}
