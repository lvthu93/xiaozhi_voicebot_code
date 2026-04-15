package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: av  reason: default package */
public final /* synthetic */ class av implements ListenerSet.Event {
    public final /* synthetic */ int a = 0;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ long c;
    public final /* synthetic */ int d;

    public /* synthetic */ av(AnalyticsListener.EventTime eventTime, int i, long j) {
        this.b = eventTime;
        this.d = i;
        this.c = j;
    }

    public /* synthetic */ av(AnalyticsListener.EventTime eventTime, long j, int i) {
        this.b = eventTime;
        this.c = j;
        this.d = i;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        long j = this.c;
        int i2 = this.d;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onDroppedVideoFrames(eventTime, i2, j);
                return;
            default:
                ((AnalyticsListener) obj).onVideoFrameProcessingOffset(eventTime, j, i2);
                return;
        }
    }
}
