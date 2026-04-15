package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ao  reason: default package */
public final /* synthetic */ class ao implements ListenerSet.Event {
    public final /* synthetic */ AnalyticsListener.EventTime a;
    public final /* synthetic */ float b;

    public /* synthetic */ ao(AnalyticsListener.EventTime eventTime, float f) {
        this.a = eventTime;
        this.b = f;
    }

    public final void invoke(Object obj) {
        ((AnalyticsListener) obj).onVolumeChanged(this.a, this.b);
    }
}
