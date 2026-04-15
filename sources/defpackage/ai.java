package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ai  reason: default package */
public final /* synthetic */ class ai implements ListenerSet.Event {
    public final /* synthetic */ AnalyticsListener.EventTime a;
    public final /* synthetic */ int b;
    public final /* synthetic */ int c;

    public /* synthetic */ ai(AnalyticsListener.EventTime eventTime, int i, int i2) {
        this.a = eventTime;
        this.b = i;
        this.c = i2;
    }

    public final void invoke(Object obj) {
        ((AnalyticsListener) obj).onSurfaceSizeChanged(this.a, this.b, this.c);
    }
}
