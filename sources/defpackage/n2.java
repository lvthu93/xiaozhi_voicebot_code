package defpackage;

import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: n2  reason: default package */
public final /* synthetic */ class n2 implements ListenerSet.Event {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ int b;
    public final /* synthetic */ Object c;
    public final /* synthetic */ Bundleable d;

    public /* synthetic */ n2(Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        this.b = i;
        this.c = positionInfo;
        this.d = positionInfo2;
    }

    public /* synthetic */ n2(AnalyticsListener.EventTime eventTime, MediaItem mediaItem, int i) {
        this.c = eventTime;
        this.d = mediaItem;
        this.b = i;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        int i2 = this.b;
        Bundleable bundleable = this.d;
        Object obj2 = this.c;
        switch (i) {
            case 0:
                Player.EventListener eventListener = (Player.EventListener) obj;
                eventListener.onPositionDiscontinuity(i2);
                eventListener.onPositionDiscontinuity((Player.PositionInfo) obj2, (Player.PositionInfo) bundleable, i2);
                return;
            default:
                ((AnalyticsListener) obj).onMediaItemTransition((AnalyticsListener.EventTime) obj2, (MediaItem) bundleable, i2);
                return;
        }
    }
}
