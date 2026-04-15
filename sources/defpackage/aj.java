package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.util.ListenerSet;
import java.io.IOException;

/* renamed from: aj  reason: default package */
public final /* synthetic */ class aj implements ListenerSet.Event {
    public final /* synthetic */ AnalyticsListener.EventTime a;
    public final /* synthetic */ LoadEventInfo b;
    public final /* synthetic */ MediaLoadData c;
    public final /* synthetic */ IOException d;
    public final /* synthetic */ boolean e;

    public /* synthetic */ aj(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z) {
        this.a = eventTime;
        this.b = loadEventInfo;
        this.c = mediaLoadData;
        this.d = iOException;
        this.e = z;
    }

    public final void invoke(Object obj) {
        ((AnalyticsListener) obj).onLoadError(this.a, this.b, this.c, this.d, this.e);
    }
}
