package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: aq  reason: default package */
public final /* synthetic */ class aq implements ListenerSet.Event {
    public final /* synthetic */ AnalyticsListener.EventTime a;
    public final /* synthetic */ long b;

    public /* synthetic */ aq(AnalyticsListener.EventTime eventTime, long j) {
        this.a = eventTime;
        this.b = j;
    }

    public final void invoke(Object obj) {
        ((AnalyticsListener) obj).onAudioPositionAdvancing(this.a, this.b);
    }
}
