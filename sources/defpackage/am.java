package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: am  reason: default package */
public final /* synthetic */ class am implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ MediaLoadData c;

    public /* synthetic */ am(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData, int i) {
        this.a = i;
        this.b = eventTime;
        this.c = mediaLoadData;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        MediaLoadData mediaLoadData = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onUpstreamDiscarded(eventTime, mediaLoadData);
                return;
            default:
                ((AnalyticsListener) obj).onDownstreamFormatChanged(eventTime, mediaLoadData);
                return;
        }
    }
}
