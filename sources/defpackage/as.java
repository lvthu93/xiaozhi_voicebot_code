package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: as  reason: default package */
public final /* synthetic */ class as implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ int c;

    public /* synthetic */ as(AnalyticsListener.EventTime eventTime, int i, int i2) {
        this.a = i2;
        this.b = eventTime;
        this.c = i;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        int i2 = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onPlaybackSuppressionReasonChanged(eventTime, i2);
                return;
            case 1:
                ((AnalyticsListener) obj).onRepeatModeChanged(eventTime, i2);
                return;
            case 2:
                ((AnalyticsListener) obj).onTimelineChanged(eventTime, i2);
                return;
            case 3:
                AnalyticsListener analyticsListener = (AnalyticsListener) obj;
                analyticsListener.onDrmSessionAcquired(eventTime);
                analyticsListener.onDrmSessionAcquired(eventTime, i2);
                return;
            case 4:
                ((AnalyticsListener) obj).onPlaybackStateChanged(eventTime, i2);
                return;
            default:
                ((AnalyticsListener) obj).onAudioSessionIdChanged(eventTime, i2);
                return;
        }
    }
}
