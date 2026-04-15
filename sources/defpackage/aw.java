package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: aw  reason: default package */
public final /* synthetic */ class aw implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ boolean c;
    public final /* synthetic */ int d;

    public /* synthetic */ aw(AnalyticsListener.EventTime eventTime, boolean z, int i, int i2) {
        this.a = i2;
        this.b = eventTime;
        this.c = z;
        this.d = i;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        int i2 = this.d;
        boolean z = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onPlayWhenReadyChanged(eventTime, z, i2);
                return;
            default:
                ((AnalyticsListener) obj).onPlayerStateChanged(eventTime, z, i2);
                return;
        }
    }
}
