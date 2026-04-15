package defpackage;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ap  reason: default package */
public final /* synthetic */ class ap implements ListenerSet.Event {
    public final /* synthetic */ AnalyticsListener.EventTime a;
    public final /* synthetic */ int b;
    public final /* synthetic */ Player.PositionInfo c;
    public final /* synthetic */ Player.PositionInfo d;

    public /* synthetic */ ap(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i) {
        this.a = eventTime;
        this.b = i;
        this.c = positionInfo;
        this.d = positionInfo2;
    }

    public final void invoke(Object obj) {
        AnalyticsListener analyticsListener = (AnalyticsListener) obj;
        AnalyticsListener.EventTime eventTime = this.a;
        int i = this.b;
        analyticsListener.onPositionDiscontinuity(eventTime, i);
        analyticsListener.onPositionDiscontinuity(eventTime, this.c, this.d, i);
    }
}
