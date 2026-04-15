package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ag  reason: default package */
public final /* synthetic */ class ag implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;

    public /* synthetic */ ag(AnalyticsListener.EventTime eventTime, int i) {
        this.a = i;
        this.b = eventTime;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onSeekProcessed(eventTime);
                return;
            case 1:
                ((AnalyticsListener) obj).onDrmKeysRemoved(eventTime);
                return;
            case 2:
                ((AnalyticsListener) obj).onDrmKeysLoaded(eventTime);
                return;
            case 3:
                ((AnalyticsListener) obj).onDrmKeysRestored(eventTime);
                return;
            case 4:
                ((AnalyticsListener) obj).onPlayerReleased(eventTime);
                return;
            case 5:
                ((AnalyticsListener) obj).onSeekStarted(eventTime);
                return;
            default:
                ((AnalyticsListener) obj).onDrmSessionReleased(eventTime);
                return;
        }
    }
}
