package defpackage;

import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.util.ListenerSet;

/* renamed from: ar  reason: default package */
public final /* synthetic */ class ar implements ListenerSet.Event {
    public final /* synthetic */ int a;
    public final /* synthetic */ AnalyticsListener.EventTime b;
    public final /* synthetic */ boolean c;

    public /* synthetic */ ar(AnalyticsListener.EventTime eventTime, boolean z, int i) {
        this.a = i;
        this.b = eventTime;
        this.c = z;
    }

    public final void invoke(Object obj) {
        int i = this.a;
        boolean z = this.c;
        AnalyticsListener.EventTime eventTime = this.b;
        switch (i) {
            case 0:
                ((AnalyticsListener) obj).onSkipSilenceEnabledChanged(eventTime, z);
                return;
            case 1:
                ((AnalyticsListener) obj).onShuffleModeChanged(eventTime, z);
                return;
            case 2:
                AnalyticsListener analyticsListener = (AnalyticsListener) obj;
                analyticsListener.onLoadingChanged(eventTime, z);
                analyticsListener.onIsLoadingChanged(eventTime, z);
                return;
            default:
                ((AnalyticsListener) obj).onIsPlayingChanged(eventTime, z);
                return;
        }
    }
}
