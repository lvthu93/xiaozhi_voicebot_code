package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: az  reason: default package */
public final /* synthetic */ class az implements ListenerSet.Event {
    public final /* synthetic */ AnalyticsListener.EventTime a;
    public final /* synthetic */ Object b;
    public final /* synthetic */ long c;

    public /* synthetic */ az(AnalyticsListener.EventTime eventTime, Object obj, long j) {
        this.a = eventTime;
        this.b = obj;
        this.c = j;
    }

    public final void invoke(Object obj) {
        ((AnalyticsListener) obj).onRenderedFirstFrame(this.a, this.b, this.c);
    }
}
