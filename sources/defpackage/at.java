package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: at  reason: default package */
public final /* synthetic */ class at implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ LoadEventInfo c;
    public final /* synthetic */ MediaLoadData d;

    public /* synthetic */ at(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, int i) {
        this.a = i;
        this.b = eventTime;
        this.c = loadEventInfo;
        this.d = mediaLoadData;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        MediaLoadData mediaLoadData = this.d;
        LoadEventInfo loadEventInfo = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onLoadCanceled(eventTime, loadEventInfo, mediaLoadData);
                return;
            case 1:
                ((AnalyticsListener) obj).onLoadStarted(eventTime, loadEventInfo, mediaLoadData);
                return;
            default:
                ((AnalyticsListener) obj).onLoadCompleted(eventTime, loadEventInfo, mediaLoadData);
                return;
        }
    }
}
